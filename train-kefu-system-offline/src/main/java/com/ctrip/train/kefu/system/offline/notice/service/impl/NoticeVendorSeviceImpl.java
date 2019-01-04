package com.ctrip.train.kefu.system.offline.notice.service.impl;

import com.ctrip.soa.train.tieyoubookingservice.v1.GetOfflineOrderDetailInnerV2Response;
import com.ctrip.train.kefu.system.client.offline.flight.FlightOrderContract;
import com.ctrip.train.kefu.system.offline.notice.dao.ExtNoitceVendor;
import com.ctrip.train.kefu.system.offline.notice.dao.ExtNoticeComplainInfo;
import com.ctrip.train.kefu.system.offline.notice.dao.ExtOperateInfo;
import com.ctrip.train.kefu.system.offline.notice.enums.NoticeOperateTypeEnum;
import com.ctrip.train.kefu.system.offline.notice.enums.ProductLineEnum;
import com.ctrip.train.kefu.system.offline.notice.enums.VendorEnum;
import com.ctrip.train.kefu.system.offline.notice.service.NoticeVendorSevice;
import com.ctrip.train.kefu.system.offline.notice.vm.VmNoticeVendor;
import common.log.CLogger;
import common.util.DateUtils;
import dao.ctrip.ctrainpps.entity.NoticeComplainInfo;
import dao.ctrip.ctrainpps.entity.NoticeVendor;
import dao.ctrip.ctrainpps.entity.OperateInfo;
import dao.ctrip.ctrainpps.entity.ScmSmallEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class NoticeVendorSeviceImpl implements NoticeVendorSevice {

    @Autowired
    private ExtNoitceVendor  noitceVendor;

    @Autowired
    private ExtNoticeComplainInfo noitce;

    @Autowired
    private  NoticeEnumServiceImpl enumService;

    @Autowired
    private FlightOrderContract orderContract;

    @Autowired
    private ExtOperateInfo extOperateInfo;

    /**
     * 供应商通知数据
     * @return
     */
    public VmNoticeVendor getNoitceVendor(Long noticeId) {
        if (noticeId == null) {
            return new VmNoticeVendor();
        }
        VmNoticeVendor vm = new VmNoticeVendor();
        try {
            NoticeComplainInfo info = noitce.queryByPk(noticeId);
            vm.setNoticeId(noticeId);
            vm.setOrderId(info.getOrderID());
            vm.setContents(info.getContents());
            vm.setNoticeType(info.getNoticeType());
            vm.setNoticeSecondType(info.getNoticeSecondType());
            vm.setEmergencyState(info.getEmergeState());

            //产品线枚举
            ProductLineEnum productLineEnum = ProductLineEnum.convertByCode(info.getProductLine());

            //初始化供应商数据
            GetOfflineOrderDetailInnerV2Response orderDetail = orderContract.getOrderDedetail(info.getOrderID());
            if (orderDetail != null && orderDetail.getOrderInfo() != null) {
                if (orderDetail.getOrderInfo().getVendorCode().equals(VendorEnum.ctrip.getCode())) {
                    if (orderDetail.getOrderInfo().getOrderSourceFlag() == 0) {
                        vm.setVerdor(VendorEnum.ctrip);
                        vm.setVerdorCode(VendorEnum.ctrip.getCode());
                    } else {
                        vm.setVerdor(VendorEnum.Intlctrip);
                        vm.setVerdorCode(VendorEnum.Intlctrip.getCode());
                    }
                } else if (orderDetail.getOrderInfo().getVendorCode().equals(VendorEnum.Qunar.getCode())
                        &&orderDetail.getOrderInfo().getOrderSourceFlagDesc().equals("自由行")) {
                    vm.setVerdor(VendorEnum.QunarWine);
                    vm.setVerdorCode(VendorEnum.QunarWine.getCode());
                }
                else {
                    VendorEnum vendorEnum = VendorEnum.convertVendor(orderDetail.getOrderInfo().getVendorCode());
                    if (vendorEnum != null) {
                        vm.setVerdor(vendorEnum);
                        vm.setVerdorCode(vendorEnum.getCode());
                    }
                }
            }

            //通知类型枚举数据
            List<ScmSmallEnum> scmSmallEnumList = enumService.GetNoticeTypeEnum(info.getProductLine());
            if (scmSmallEnumList != null && scmSmallEnumList.size() > 0) {
                //一级分类
                List<ScmSmallEnum> lst = scmSmallEnumList.stream()
                        .filter(p -> p.getFieldType().equals(productLineEnum.getNoticeType())
                                && p.getFkUpperTid().equals(0L)
                                && p.getIsDeleted().equals(String.valueOf(0)))
                        .collect(Collectors.toList());
                vm.setNoticeTypeList(lst);
                //二级分类
                List<ScmSmallEnum> lstSecond = scmSmallEnumList.stream()
                        .filter(p -> p.getFkUpperTid().equals(Long.valueOf(info.getNoticeType())))
                        .collect(Collectors.toList());
                vm.setNoticeSecondTypeList(lstSecond);
            }

        } catch (Exception ex) {
            CLogger.error("noitce", ex);
        }
        return vm;
    }

    /**
     * 保存供应商通知数据
     * @return
     */
    public boolean sendVendor(VmNoticeVendor vm,String sendCode,String sendName) throws SQLException {
        NoticeComplainInfo info = noitce.queryByPk(vm.getNoticeId());
        if (info == null) {
            return false;
        }
        info.setNoticeType(vm.getNoticeType());
        info.setNoticeSecondType(vm.getNoticeSecondType());
        info.setEmergeState(vm.getEmergencyState());

        //更新通知数据
        noitce.update(info);


        NoticeVendor vendor = new NoticeVendor();

        AtomicReference<String> imageContent= new AtomicReference<>("");
        //通知内容加上图片信息
        if (vm.getVmNoticeImages()!=null&&vm.getVmNoticeImages().size()>0) {
            vm.getVmNoticeImages().forEach(p -> {
                imageContent.updateAndGet(q -> q + "<p><a href='" + p.getImageUrl() + "' target='_blank'>" + p.getImageName() + "</a></p>");
            });
        }

        vendor.setContents(vm.getContents()+imageContent);
        vendor.setNoticeId(vm.getNoticeId());

        //供应商信息
        VendorEnum vendorEnum = VendorEnum.convertVendor(vm.getVerdorCode());
        vendor.setVerdorCode(vendorEnum.getCode());
        vendor.setVerdorName(vendorEnum.getName());
        vendor.setAppointedProcessTime(DateUtils.parseTimestamp(vm.getAppointedProcessTime()));
        //初始化通知操作数据
        vendor.setOpUserType(1);
        vendor.setOpUser(vendor.getSendName());
        vendor.setOpTime(DateUtils.getCurFullTimestamp());

        //发送人信息
        vendor.setSendCode(sendCode);
        vendor.setSendName(sendName);
        vendor.setSendTime(DateUtils.getCurFullTimestamp());
        vendor.setSendType(1);
        vendor.setIsDelete(0);

        //插入供应商通知数据
        if (noitceVendor.insert(vendor) > 0) {
            //添加一条转供应商的记录
            OperateInfo operateInfo=new OperateInfo();
            operateInfo.setTid(info.getID());
            operateInfo.setOperateComment(vendor.getContents());
            operateInfo.setOperateUser(sendCode+"("+sendName+")");
            operateInfo.setOperateType(NoticeOperateTypeEnum.Transfer.getState());
            operateInfo.setOperateSource(1);
            operateInfo.setOperateTime(DateUtils.getCurFullTimestamp());
            extOperateInfo.insert(operateInfo);
            return true;
        }
        return false;
    }

    /**
     * 是否已经存在供应商通知
     */
    public boolean exisNoitceVendor(Long noticeId) throws SQLException {
        NoticeVendor noticeVendor=new NoticeVendor();
        noticeVendor.setNoticeId(noticeId);
        List<NoticeVendor> lst=noitceVendor.queryBy(noticeVendor);
        if (lst!=null&&lst.size()>0){
            return  true;
        }
        return  false;
    }
}
