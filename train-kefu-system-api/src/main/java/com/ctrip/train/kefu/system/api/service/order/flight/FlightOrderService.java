package com.ctrip.train.kefu.system.api.service.order.flight;

import com.ctrip.train.kefu.system.api.contract.FlightExOrderRequsetType;
import com.ctrip.train.kefu.system.api.contract.FlightExOrderResponseType;
import com.ctrip.train.kefu.system.api.contract.OfflineUpdateFlightContactRequestType;
import com.ctrip.train.kefu.system.api.contract.OfflineUpdateFlightContactResponseType;
import com.ctrip.train.kefu.system.api.dao.order.ExtFlightNoticeEx;
import com.ctrip.train.kefu.system.api.infrastructure.constants.Result;

import com.ctrip.train.kefu.system.client.offline.flight.FlightOrderContract;
import common.log.CLogger;
import common.util.ValidateUtil;
import dao.ctrip.ctrainchat.entity.AirNoticeExcetion;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Timestamp;

@Service
public class FlightOrderService {
    @Autowired
    private ExtFlightNoticeEx extFlightNoticeEx;

    @Autowired
    private FlightOrderContract flightOrderContract;

    /**
     * 发送机票异常通知
     * @param requset
     * @return
     */
    public FlightExOrderResponseType SendFlightNoticeEx(FlightExOrderRequsetType requset){
        FlightExOrderResponseType response= new FlightExOrderResponseType();
        //region 验证请求参数
        if (requset==null){
            response.setCode(Result.FAIL);
            response.setMsg(Result.ParamIsEmpty("request"));
            return  response;
        }
        if (requset.getOrderID()==null||requset.getOrderID().isEmpty()){
            response.setCode(Result.FAIL);
            response.setMsg(Result.ParamIsEmpty("OrderID"));
            return  response;
        }
        if(requset.getExOrderType()==null){
            response.setCode(Result.FAIL);
            response.setMsg(Result.ParamIsEmpty("ExOrderType"));
            return  response;
        }
        if(requset.getLatestTicketingTime()==null){
            response.setCode(Result.FAIL);
            response.setMsg(Result.ParamIsEmpty("LatestTicketingTime"));
            return  response;
        }
        if (requset.getProcessingRemark()==null||requset.getProcessingRemark().isEmpty()){
            response.setCode(Result.FAIL);
            response.setMsg(Result.ParamIsEmpty("ProcessingRemark"));
            return  response;
        }
        if (requset.getSupplier()==null||requset.getSupplier().isEmpty()){
            response.setCode(Result.FAIL);
            response.setMsg(Result.ParamIsEmpty("Supplier"));
            return  response;
        }
        if (requset.getSendOrderTime()==null||requset.getSendOrderTime().isEmpty()){
            response.setCode(Result.FAIL);
            response.setMsg(Result.ParamIsEmpty("ProcessingRemark"));
            return  response;
        }
        if (requset.getTakeoffTime()==null||requset.getTakeoffTime().isEmpty()){
            response.setCode(Result.FAIL);
            response.setMsg(Result.ParamIsEmpty("TakeoffTime"));
            return  response;
        }
        if (requset.getProductLine()==null){
            response.setCode(Result.FAIL);
            response.setMsg(Result.ParamIsEmpty("ProductLine"));
            return  response;
        }
        AirNoticeExcetion info=new AirNoticeExcetion();
        info.setOrderId(requset.getOrderID());
        info.setSupplier(requset.getSupplier());
        info.setExOrderType(requset.getExOrderType());
        info.setLatestTicketingTime(Timestamp.valueOf(requset.getLatestTicketingTime()));
        info.setEnterUser(requset.getEnterUser());
        info.setProductLine(requset.getProductLine());
        try {
            //判断是否重复提交
            if(extFlightNoticeEx.getExtAirNoticeEx(info).size()==0){
                info.setTakeoffTime(Timestamp.valueOf(requset.getTakeoffTime()));
                info.setSendOrderTime(Timestamp.valueOf(requset.getSendOrderTime()));
                info.setProcessingRemark(requset.getProcessingRemark());
                if (extFlightNoticeEx.insert(info)==1){
                    response.setCode(Result.SUCCESS);
                    response.setMsg("发送通知成功");
                    return  response;
                }
                response.setCode(Result.FAIL);
                response.setMsg("发送通知失败");
                return  response;
            }else {
                response.setCode(Result.FAIL);
                response.setMsg("发送通知重复");
                return  response;
            }
        } catch (SQLException e) {
            CLogger.error("dao:insertExtAirNotice", e);
        }
        return null;
    }


    /**
     * 机票修改手机号码
     * @param request
     * @return
     */
    public OfflineUpdateFlightContactResponseType offlineUpdateFlightContact(OfflineUpdateFlightContactRequestType request) {

        OfflineUpdateFlightContactResponseType response = new OfflineUpdateFlightContactResponseType();

        //region 验证请求参数
        if (request == null) {
            response.setCode(Result.FAIL);
            response.setMsg(Result.ParamIsEmpty("request"));
            return response;
        }

        if (request.getOrderId() == null || request.getOrderId().isEmpty()) {
            response.setCode(Result.FAIL);
            response.setMsg(Result.ParamIsEmpty("OrderID"));
            return response;
        }

        if (request.getMobilePhone() == null || request.getMobilePhone().isEmpty()) {
            response.setCode(Result.FAIL);
            response.setMsg(Result.ParamIsEmpty("MobilePhone"));
            return response;
        }

        if (!ValidateUtil.isPhoneNumber(request.getMobilePhone())) {
            response.setCode(Result.FAIL);
            response.setMsg("手机号格式不正确");
            return response;
        }

        if (request.getEid() == null || request.getEid().isEmpty()) {
            response.setCode(Result.FAIL);
            response.setMsg(Result.ParamIsEmpty("eid"));
            return response;
        }
        try {
            Pair<Boolean, String> result = flightOrderContract.updateFlightPhone(request.getMobilePhone(), request.getOrderId(), request.getEid());
            if (result.getKey()) {
                response.setCode(Result.SUCCESS);
                response.setMsg(result.getValue());
            } else {
                response.setCode(Result.FAIL);
                response.setMsg(result.getValue());
            }

            return response;
        } catch (Exception ex) {
            CLogger.error("offlineUpdateFlightContact", ex);
        }
        return null;
    }

}
