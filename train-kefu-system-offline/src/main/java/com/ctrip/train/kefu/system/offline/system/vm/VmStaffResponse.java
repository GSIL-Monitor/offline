package com.ctrip.train.kefu.system.offline.system.vm;

import com.ctrip.train.kefu.system.offline.system.domain.StaffRoleResult;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VmStaffResponse {
    private int id;
    private String staffNum;
    private String staffName;
    //通知 投诉 领班 电话
    private String eventType;
    //归属产品线
    private String productLine;
    //职位
    private Long positionId;
    private String positionName;
    private String roleId;
    //客服小组
    private String belongGroup;

    private String noticeWaitLimit;

    private String complainWaitLimit;

    private String email;

    //角色
    private List<StaffRoleResult> roles;

    private String rolesName;

}
