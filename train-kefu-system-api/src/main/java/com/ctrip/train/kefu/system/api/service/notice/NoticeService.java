package com.ctrip.train.kefu.system.api.service.notice;


import com.ctrip.train.kefu.system.api.contract.NoticeExisRequestType;
import com.ctrip.train.kefu.system.api.contract.NoticeExisResponseType;
import com.ctrip.train.kefu.system.api.contract.SendNoticeRequestType;
import com.ctrip.train.kefu.system.api.contract.SendNoticeResponseType;
import com.ctrip.train.kefu.system.api.dao.notice.ExtNotice;
import com.ctrip.train.kefu.system.api.domain.notice.enums.NoticeStateEnum;
import com.ctrip.train.kefu.system.api.infrastructure.constants.Result;
import common.log.CLogger;
import common.util.DateUtils;
import dao.ctrip.ctrainpps.entity.NoticeComplainInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.List;

@Service
public class NoticeService {


    @Autowired
    private ExtNotice notice;

    /**
     * 发送通知
     */
    public SendNoticeResponseType sendNotice(SendNoticeRequestType request){

        SendNoticeResponseType response =new  SendNoticeResponseType();

        validateParams(response,request);
        if (response.getMsg()!=null&&!response.getMsg().isEmpty()){
             return response;
        }
        try {
            //region 验证重复发送
            NoticeComplainInfo info=new  NoticeComplainInfo();
            info.setOrderID(request.getOrderID());
            info.setNoticeType(request.getNoticeType());
            info.setNoticeSecondType(request.getNoticeSecondType());
            info.setComplainSource(request.getNoticeSource());
            info.setEnterUser(request.getEnterUser());

            // 验证通知是否已经存在，防止重复发送
            List lst= notice.getNotiList(info);
            if (lst!=null&&lst.size()>0){
                response.setCode(Result.FAIL);
                response.setMsg("通知已存在");
                return response;
            }
            // endregion

            // modelmapper 异常，手动赋值
            NoticeComplainInfo model =new  NoticeComplainInfo();
            mapNotice(model,request);

            if (notice.insert(model)>0){
                response.setCode(Result.SUCCESS);
            }
            else {
                response.setCode(Result.FAIL);
                response.setMsg("通知发送失败");
            }
        }catch (SQLException e) {
            response.setCode(Result.FAIL);
            response.setMsg("插入数据异常");
        }
        return response;
    }

    /**
     * 初始化数据
     */
    private void mapNotice(NoticeComplainInfo model, SendNoticeRequestType request){
        model.setNoticeType(request.getNoticeType());
        model.setEnterUser(request.getEnterUser());
        model.setEmergeState(request.getEmergeState());
        model.setOrderID(request.getOrderID());
        model.setNoticeSecondType(request.getNoticeSecondType());
        model.setEnvenType(request.getEnvenType());
        model.setContactPhone(request.getContactPhone());
        model.setContactUser(request.getContactUser());
        model.setContents(request.getContents());
        model.setProductLine(request.getProductLine());
        model.setSendTime(DateUtils.getCurFullTimestamp());
        model.setEnterDate(DateUtils.getCurFullTimestamp());
        model.setComplainSource(request.getNoticeSource());
        model.setOrderType(request.getOrderType());
        model.setDataSource(2070);
        model.setNoticeState(NoticeStateEnum.Wait.getState());
        if (request.getAppointedProcessTime()!=null){
            model.setAppointedProcessTime(DateUtils.parseTimestamp(request.getAppointedProcessTime()));
        }
        model.setIsDelete(0);
    }

    /**
     * 验证参数
     * @return
     */
    private void validateParams(SendNoticeResponseType response, SendNoticeRequestType request){
        if (request==null){
            response.setCode(Result.FAIL);
            response.setMsg(Result.ParamIsEmpty("request"));
            return;
        }

        if (request.getOrderID()==null||request.getOrderID().isEmpty()){
            response.setCode(Result.FAIL);
            response.setMsg(Result.ParamIsEmpty("OrderID"));
            return;
        }

        if (request.getEmergeState()==null){
            response.setCode(Result.FAIL);
            response.setMsg(Result.ParamIsEmpty("EmergeState"));
            return;
        }

        if (request.getEnvenType()==null){
            response.setCode(Result.FAIL);
            response.setMsg(Result.ParamIsEmpty("EnvenType"));
            return;
        }

        if (request.getNoticeSource()==null){
            response.setCode(Result.FAIL);
            response.setMsg(Result.ParamIsEmpty("NoticeSource"));
            return;
        }

        if (request.getProductLine()==null||request.getProductLine().isEmpty()){
            response.setCode(Result.FAIL);
            response.setMsg(Result.ParamIsEmpty("ProductLine"));
        }
    }

    /**
     * 通知是否重复发送
     */
    public NoticeExisResponseType noticeExis(NoticeExisRequestType request){
        NoticeExisResponseType respose=new  NoticeExisResponseType();
        if (request.getOrderID()==null){
            respose.setExis(false);
            return respose;
        }
        NoticeComplainInfo model=new  NoticeComplainInfo();
        model.setOrderID(request.getOrderID());
        model.setNoticeType(request.getNoticeType());
        model.setNoticeSecondType(request.getNoticeSecondType());
        model.setComplainSource(request.getNoticeSource());
        model.setEnterUser(request.getEnterUser());
        try {
            List lst= notice.getNotiList(model);
            if (lst!=null&&lst.size()>0){
                respose.setExis(true);
            }else {
                respose.setExis(false);
            }
        }
        catch (Exception ex){
            CLogger.error("noticeExis",ex);
        }
        return  respose;
    }
}
