package com.ctrip.train.kefu.system.api.domain.notice;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 查功供应商通知条件
 */
@Getter
@Setter
public class DmNoticeVendorCondition {

    private  String verdorCode;

    private  String orderId;

    private  String sendName;

    private  String startTime;

    private  String endTime;

    private  Integer sendType;

    private List<Integer> noticeStates;

    private  Integer noticeType;

    private  Integer opUserType;

    private  Integer noticeStatus;
}
