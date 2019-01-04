package com.ctrip.train.kefu.system.offline.common.controller;

import com.ctrip.offlineBase.LoginState.EmpsInformationEntity;
import com.ctrip.train.kefu.system.offline.common.component.StaffInfoComponent;
import com.ctrip.train.kefu.system.offline.common.domain.OfflineStaffInfo;
import dao.ctrip.ctrainchat.entity.OfflinePermission;
import dao.ctrip.ctrainchat.entity.OfflineRole;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author su
 */
public abstract class BaseController {

    @Autowired
    private StaffInfoComponent staffInfoComponent;

    /**
     * 当前登录人基础信息
     */
    protected EmpsInformationEntity getEmpInfo(){
        OfflineStaffInfo empEntity = staffInfoComponent.getEmpEntity();
        if (empEntity!=null){
            return  empEntity.getStaffBasicInfo();
        }
        return null;
    }

    /**
     * 员工编号
     */
    protected String getEid(){
        EmpsInformationEntity empEntity= getEmpInfo();
        if(empEntity!=null) return empEntity.getEid();
        else return "unauthorized";
    }

    /**
     * 姓名
     */
    protected String getEmpName(){
        EmpsInformationEntity empEntity= getEmpInfo();
        if(empEntity!=null) return empEntity.getEmpName();
        else return "unnamed_user";
    }

    /**
     * 获取角色
     */
    protected List<OfflineRole>  getRole(){
        OfflineStaffInfo empEntity = staffInfoComponent.getEmpEntity();
        if (empEntity!=null){
            return  empEntity.getStaffRoles();
        }
        return null;
    }


    /**
     * 获取权限
     */
    protected List<OfflinePermission> getPermission(){
        OfflineStaffInfo staffInfo = staffInfoComponent.getEmpEntity();
        if (staffInfo!=null){
            return  staffInfo.getStaffPermission();
        }
        return null;
    }

    /**
     * 是否有某个权限
     */
    protected  boolean hasPermission(String permCode){
        OfflineStaffInfo staffInfo = staffInfoComponent.getEmpEntity();
        if (staffInfo!=null && staffInfo.getStaffPermission()!=null){
            return  staffInfo.getStaffPermission().stream().anyMatch(p->p.getPermCode().equals(permCode));
        }
        return false;
    }

    /**
     * 是否有某个角色
     */
    protected  boolean hasRole(Long roleCode){
        OfflineStaffInfo staffInfo = staffInfoComponent.getEmpEntity();
        if (staffInfo!=null&& staffInfo.getStaffRoles()!=null){
            return  staffInfo.getStaffRoles().stream().anyMatch(p-> p.getId().equals(roleCode));
        }
        return false;
    }

    protected String getOpUser(){
        return String.format("%s(%s)",getEid(),getEmpName());
    }

    /**
     * 重定向至404页面
     * @param response
     * @throws IOException
     */
    protected void redirectTo404(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath()+"/error/404");
    }
    /**
     * 重定向至401页面
     * @param response
     * @throws IOException
     */
    protected void redirectTo401(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath()+"/error/401");
    }
}
