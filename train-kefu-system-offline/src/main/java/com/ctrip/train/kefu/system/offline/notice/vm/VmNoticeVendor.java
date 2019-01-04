package com.ctrip.train.kefu.system.offline.notice.vm;

import com.ctrip.train.kefu.system.offline.notice.enums.VendorEnum;
import dao.ctrip.ctrainpps.entity.ScmSmallEnum;

import java.util.List;

public class VmNoticeVendor {
    /**
     * 通知主键
     */
    private  long noticeId;

    /**
     * 订单ID
     */
    private  String orderId;

    /**
     * 紧急程度
     */
    private  Integer emergencyState;

    /**
     * 供应商
     */
    private VendorEnum verdor;

    /**
     * 供应商Code
     */
    private String verdorCode;

    /**
     * 问题备注
     */
    private  String contents;

    /**
     * 一级分类
     */
    private  Integer noticeType;

    /**
     * 二级分类
     */
    private  Integer noticeSecondType;

    /**
     * 预约时间
     */
    private  String appointedProcessTime;

    /**
     * 图片信息
     */
    private  List<VmNoticeImage> vmNoticeImages;

    /**
     * 一级分类列表
     */
    private List<ScmSmallEnum> noticeTypeList;

    /**
     * 二级分类列表
     */
    private List<ScmSmallEnum> noticeSecondTypeList;

    public long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(long noticeId) {
        this.noticeId = noticeId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public VendorEnum getVerdor() {
        return verdor;
    }

    public void setVerdor(VendorEnum verdor) {
        this.verdor = verdor;
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public Integer getNoticeSecondType() {
        return noticeSecondType;
    }

    public void setNoticeSecondType(Integer noticeSecondType) {
        this.noticeSecondType = noticeSecondType;
    }

    public List<ScmSmallEnum> getNoticeTypeList() {
        return noticeTypeList;
    }

    public void setNoticeTypeList(List<ScmSmallEnum> noticeTypeList) {
        this.noticeTypeList = noticeTypeList;
    }

    public List<ScmSmallEnum> getNoticeSecondTypeList() {
        return noticeSecondTypeList;
    }

    public void setNoticeSecondTypeList(List<ScmSmallEnum> noticeSecondTypeList) {
        this.noticeSecondTypeList = noticeSecondTypeList;
    }

    public String getVerdorCode() {
        return verdorCode;
    }

    public void setVerdorCode(String verdorCode) {
        this.verdorCode = verdorCode;
    }

    public Integer getEmergencyState() {
        return emergencyState;
    }

    public void setEmergencyState(Integer emergencyState) {
        this.emergencyState = emergencyState;
    }

    public String getAppointedProcessTime() {
        return appointedProcessTime;
    }

    public void setAppointedProcessTime(String appointedProcessTime) {
        this.appointedProcessTime = appointedProcessTime;
    }

    public List<VmNoticeImage> getVmNoticeImages() {
        return vmNoticeImages;
    }

    public void setVmNoticeImages(List<VmNoticeImage> vmNoticeImages) {
        this.vmNoticeImages = vmNoticeImages;
    }
}
