package com.ctrip.train.kefu.system.api.service.vendor.notice.domainService;

import com.ctrip.train.kefu.system.api.contract.Operate;
import com.ctrip.train.kefu.system.api.contract.ScmEnum;
import com.ctrip.train.kefu.system.api.contract.VendorNotice;
import com.ctrip.train.kefu.system.api.dao.notice.ExtNoitceVendor;
import com.ctrip.train.kefu.system.api.dao.notice.ExtOperateInfo;
import com.ctrip.train.kefu.system.api.domain.notice.DmNoticeVendor;
import com.ctrip.train.kefu.system.api.domain.notice.enums.NoticeStateEnum;
import common.log.CLogger;
import common.util.DateUtils;
import dao.ctrip.ctrainpps.entity.NoticeComplainInfo;
import dao.ctrip.ctrainpps.entity.NoticeVendor;
import dao.ctrip.ctrainpps.entity.OperateInfo;
import dao.ctrip.ctrainpps.entity.ScmSmallEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DmNoticeService {

    @Autowired
    private ExtOperateInfo operateInfo;

    @Autowired
    private ExtNoitceVendor noitceVendor;

    /**
     * 初始化操作记录
     * @param vm
     * @return
     */
    public List<VendorNotice>  initVendorOperateInfos(List<VendorNotice> vm)  {
        //初始化操作记录
        if (vm!=null&&vm.size()>0){
            List<OperateInfo> lst=operateInfo.getOperateInfos(vm.stream().map(VendorNotice::getNoticeId).collect(Collectors.toList()));
            if (lst!=null) {
                lst = lst.stream()
                        .sorted(Comparator.comparing(OperateInfo::getOperateTime))
                        .collect(Collectors.toList());

                //映射模型
                List<Operate> vmOperate = mapOperate(lst);
                vm.forEach(p -> {
                    List<Operate> operate = vmOperate.stream().filter(q -> q.getNoticeId().equals(p.getNoticeId())).collect(Collectors.toList());
                    if (operate != null) {
                        p.setOperateInfos(operate);
                    }
                });
            }
        }
        return vm;
    }

    /**
     * DBOperateInfo换VM
     */
    public   List<Operate> mapOperate(List<OperateInfo> list){
        List<Operate> operates =new ArrayList<>();
        if (list!=null) {
            list.forEach(p -> {
                Operate operate = new Operate();
                operate.setId(p.getOid());
                operate.setNoticeId(p.getTid());
                operate.setOperateComment(p.getOperateComment());
                operate.setOperateTime(DateUtils.format(p.getOperateTime(), DateUtils.YMDHMS_UNDERLINED));
                operate.setOperateType(p.getOperateType());
                operate.setOperateUser(p.getOperateUser());
                operates.add(operate);
            });
        }
        return  operates;
    }

    /**
     * VM换DBOperateInfo
     */
    public  OperateInfo mapOperate(Operate operate) {
        OperateInfo operateInfo = new OperateInfo();
        if (operate != null) {
            operateInfo.setOperateSource(2);
            operateInfo.setOperateType(operate.getOperateType());
            operateInfo.setOperateTime(DateUtils.parseTimestamp(operate.getOperateTime()));
            operateInfo.setOperateUser(operate.getOperateUser());
            operateInfo.setOperateComment(operate.getOperateComment());
            operateInfo.setTid(operate.getNoticeId());
        }
        return operateInfo;
    }

    /**
     * vm转换dmNotice
     */
    public NoticeComplainInfo mapNoticeComlainInfo(VendorNotice notice){
        NoticeComplainInfo info =new NoticeComplainInfo();
        info.setSendTime(DateUtils.getCurFullTimestamp());
        info.setNoticeSecondType(notice.getNoticeSecondType());
        info.setNoticeType(notice.getNoticeType());
        info.setOrderID(notice.getOrderId());
        info.setEmergeState(notice.getEmergeState());
        info.setContents(notice.getContents());

        if (notice.getAppointedProcessTime()!=null&&
                !notice.getAppointedProcessTime().isEmpty()) {
            //预约时间
            info.setAppointedProcessTime(DateUtils.parseTimestamp(notice.getAppointedProcessTime()));
        }
        info.setEnterUser(notice.getSendName());
        info.setEnterDate(DateUtils.getCurFullTimestamp());
        info.setSendTime(DateUtils.getCurFullTimestamp());
        info.setNoticeState(NoticeStateEnum.Wait.getState());
        info.setEnvenType(1);
        info.setComplainSource(6147);//来源
        info.setDataSource(79);//渠道
        return  info;
    }

    /**
     * DmNoticeVendor转换vm
     */
    public VendorNotice mapVendorNotice(DmNoticeVendor p){
        VendorNotice notice=new VendorNotice();
        notice.setNoticeType(p.getNoticeType());
        notice.setSendName(p.getSendName());
        notice.setAppointedProcessTime(DateUtils.format(p.getAppointedProcessTime(), DateUtils.YMDHMS_UNDERLINED));
        notice.setCompleteTime(DateUtils.format(p.getCompleteTime(), DateUtils.YMDHMS_UNDERLINED));
        notice.setContents(p.getContents());
        notice.setEmergeState(p.getEmergeState());
        notice.setId(p.getId());
        notice.setNoticeId(p.getNoticeId());
        notice.setNoticeSecondType(p.getNoticeSecondType());
        notice.setNoticeState(p.getNoticeState());
        notice.setOpCount(p.getOpCount());
        notice.setOrderId(p.getOrderId());
        notice.setSendCode(p.getSendCode());
        notice.setSendTime(DateUtils.format(p.getSendTime(), DateUtils.YMDHMS_UNDERLINED));
        notice.setSendType(p.getSendType());
        notice.setVendorCode(p.getVerdorCode());
        notice.setVendorName(p.getVerdorName());
        return notice;
    }

    /**
     * DmNoticeVendor转换vm
     */
    public List<VendorNotice> mapVendorNotice(List<DmNoticeVendor> list){
        List<VendorNotice> notices =new ArrayList<>();
        if (list!=null){
            list.forEach(p->{
                VendorNotice notice=new VendorNotice();
                notice.setNoticeType(p.getNoticeType());
                notice.setSendName(p.getSendName());
                notice.setAppointedProcessTime(DateUtils.format(p.getAppointedProcessTime(), DateUtils.YMDHMS_UNDERLINED));
                notice.setCompleteTime(DateUtils.format(p.getCompleteTime(), DateUtils.YMDHMS_UNDERLINED));
                notice.setContents(p.getContents());
                notice.setEmergeState(p.getEmergeState());
                notice.setId(p.getId());
                notice.setNoticeId(p.getNoticeId());
                notice.setNoticeSecondType(p.getNoticeSecondType());
                notice.setNoticeState(p.getNoticeState());
                notice.setOpCount(p.getOpCount());
                notice.setOrderId(p.getOrderId());
                notice.setSendCode(p.getSendCode());
                notice.setSendTime(DateUtils.format(p.getSendTime(), DateUtils.YMDHMS_UNDERLINED));
                notice.setSendType(p.getSendType());
                notice.setVendorCode(p.getVerdorCode());
                notice.setVendorName(p.getVerdorName());
                notice.setOpUser(p.getOpUser());
                notice.setOpTime(DateUtils.format(p.getOpTime(), DateUtils.YMDHMS_UNDERLINED));
                notice.setOpUseTyper(p.getOpUserType());
                notices.add(notice);
            });
        }
        return notices;
    }

    /**
     * DmScmEnum转换vm
     */
   public  List<ScmEnum> mapScmEnum(List<ScmSmallEnum> list){
       List<ScmEnum> scmEnums =new ArrayList<>();
       if (list!=null){
           list.forEach(p->{
               ScmEnum scmEnum=new ScmEnum();
               scmEnum.setFieldName(p.getFieldName());
               scmEnum.setFieldType(p.getFieldType());
               scmEnum.setFieldValue(p.getFieldValue());
               scmEnum.setFkUpperTid(p.getFkUpperTid());
               scmEnum.setTid(p.getTid());
               scmEnums.add(scmEnum);
           });
       }
       return scmEnums;
   }

    /**
     * 供应商添加通知操作记录
     */
    public void insertOperate(Long noticeid, String content, String opuser, int type,int source)  {
        OperateInfo model=new  OperateInfo();
        model.setTid(noticeid);
        model.setOperateComment(content);
        model.setOperateUser(opuser);
        model.setOperateTime(DateUtils.getCurFullTimestamp());
        model.setOperateType(type);
        model.setOperateSource(source);
        try {
            NoticeVendor vendor=new  NoticeVendor();
            vendor.setOpUser(model.getOperateUser());
            vendor.setOpTime(DateUtils.getCurFullTimestamp());
            vendor.setNoticeId(noticeid);
            //更新通知操作数据
            if (model.getOperateSource()==2) {
                vendor.setOpUserType(2);
            }else {
                vendor.setOpUserType(1);
            }
            noitceVendor.updateOpdate(vendor);
            operateInfo.insert(model);
        }
        catch (Exception ex)
        {
            CLogger.warn("insertOperate",ex);
        }
    }
}

