package com.ctrip.train.kefu.system.offline.common.domain;

import com.ctrip.offlineBase.LoginState.EmpsInformationEntity;
import dao.ctrip.ctrainchat.entity.OfflinePermission;
import dao.ctrip.ctrainchat.entity.OfflineRole;
import dao.ctrip.ctrainchat.entity.OfflineStaffRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 登录员工信息
 */
@Getter
@Setter
public class OfflineStaffInfo {

    /**
     * 基础信息
     */
    private  EmpsInformationEntity staffBasicInfo;

    /**
     * 所属角色
     */
    private List<OfflineRole> staffRoles;


    /**
     * 拥有权限
     */
    private  List<OfflinePermission> staffPermission;
}
