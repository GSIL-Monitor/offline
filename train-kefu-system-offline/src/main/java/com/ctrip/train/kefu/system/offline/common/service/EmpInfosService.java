package com.ctrip.train.kefu.system.offline.common.service;

import com.alibaba.fastjson.JSON;
import com.ctrip.framework.foundation.Foundation;
import com.ctrip.offlineBase.LoginState.EmpsInformationEntity;
import com.ctrip.offlineBase.LoginState.LoginStateBase;
import com.ctrip.train.kefu.system.offline.common.dao.ExtOfflinePermission;
import com.ctrip.train.kefu.system.offline.common.dao.ExtOfflineRole;
import com.ctrip.train.kefu.system.offline.common.domain.OfflineStaffInfo;
import dao.ctrip.ctrainchat.entity.OfflinePermission;
import dao.ctrip.ctrainchat.entity.OfflineRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpInfosService {

    @Autowired
    private ExtOfflineRole extOfflineRole;

    @Autowired
    private ExtOfflinePermission extOfflinePermission;


    /**
     * 获取用户基础信息，角色信息，权限信息
     */
    public OfflineStaffInfo getStaffInfo(HttpServletRequest request, HttpServletResponse response){

        EmpsInformationEntity empEntity;
        if(Foundation.server().getEnv().isFAT()){
            empEntity= JSON.parseObject("{\"Remark\":\"\",\"Group_Name\":\"\",\"Eid\":\"S67600\",\"EmpName\":\"苏勇\"," +
                            "\"Dept\":\"SNSB\",\"DeptName\":\"火车票研发部\",\"Branch\":\"2\",\"BranchName\":\"上海\"," +
                            "\"Email\":\"ying_zhou@ctrip.com\",\"GroupCode\":\" \",\"TelOperatorNo\":\"00000\"," +
                            "\"UserNo\":\"S67600\",\"ExtNo\":\"\",\"EmpType\":\"B\"}",
                    EmpsInformationEntity.class
            );
        }
        else{
            empEntity= LoginStateBase.GetEmpsInformation(request, response);
        }

        if (empEntity!=null) {
            OfflineStaffInfo staffInfo = new OfflineStaffInfo();
            staffInfo.setStaffBasicInfo(empEntity);

            String userNum = empEntity.getUserNo();
            if (userNum == null || userNum.isEmpty()) {
                userNum = empEntity.getEid();
            }
            //获取用户角色
            List<OfflineRole> roles = extOfflineRole.getStaffRoles(userNum);
            if (roles!=null&&roles.size()>0){
                staffInfo.setStaffRoles(roles);

                List<Long> roleIds=roles.stream().map(OfflineRole::getRoleId).collect(Collectors.toList());
                if (roleIds!=null){
                    //获取权限
                    List<OfflinePermission> permissions=extOfflinePermission.getStaffPermission(roleIds);
                    if (permissions!=null){
                        staffInfo.setStaffPermission(permissions);
                    }
                }
            }
            return  staffInfo;
        }
        return null;
    }
}
