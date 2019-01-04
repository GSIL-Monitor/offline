package com.ctrip.train.kefu.system.offline.notice.vm;

import dao.ctrip.ctrainpps.entity.NoticeComplainInfo;
import dao.ctrip.ctrainpps.entity.NoticeVendor;
import dao.ctrip.ctrainpps.entity.OperateInfo;

import java.util.List;

public class VmNoticeDetail  extends NoticeComplainInfo{

    private List<OperateInfo> operateInfos;

    private String noticeTypoStr;

    private NoticeVendor noticeVendor;

    public List<OperateInfo> getOperateInfos() {
        return operateInfos;
    }

    public void setOperateInfos(List<OperateInfo> operateInfos) {
        this.operateInfos = operateInfos;
    }

    public String getNoticeTypoStr() {
        return noticeTypoStr;
    }

    public void setNoticeTypoStr(String noticeTypoStr) {
        this.noticeTypoStr = noticeTypoStr;
    }

    public NoticeVendor getNoticeVendor() {
        return noticeVendor;
    }

    public void setNoticeVendor(NoticeVendor noticeVendor) {
        this.noticeVendor = noticeVendor;
    }
}
