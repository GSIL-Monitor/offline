package com.ctrip.train.kefu.system.offline.notice.vm.notice;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class VmOperateInfo {

    private Long oid;

    private Timestamp operateTime;

    // 通知或投诉ID
    private Long tid;

    private String operateUser;

    private String operateComment;

    // 备注类型： 81仅备注、82暂缓、83解决、94转投诉、100转领班、101交班
    private Integer operateType;

    private Integer operateSource;
}
