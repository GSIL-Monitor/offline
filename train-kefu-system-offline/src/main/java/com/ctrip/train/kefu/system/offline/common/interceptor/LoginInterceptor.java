package com.ctrip.train.kefu.system.offline.common.interceptor;

import com.ctrip.train.kefu.system.offline.common.aspect.annotation.Auth;
import com.ctrip.train.kefu.system.offline.common.aspect.annotation.Module;
import com.ctrip.train.kefu.system.offline.common.domain.OfflineStaffInfo;
import com.ctrip.train.kefu.system.offline.common.service.EmpInfosService;
import com.ctrip.train.kefu.system.offline.common.service.ModulePermsService;
import com.ctrip.train.kefu.system.offline.common.component.StaffInfoComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StaffInfoComponent staffInfo;

    @Autowired
    private EmpInfosService empInfosBusiness;

    @Autowired
    private ModulePermsService modulePermsBusiness;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws IOException {

        //登录信息
        OfflineStaffInfo empEntity = empInfosBusiness.getStaffInfo(httpServletRequest, httpServletResponse);
        if(empEntity==null){
            return false;
        }

        staffInfo.setEmpEntity(empEntity);

        //验证模块权限
        String moduleCode= hasModule(handler);
        if (moduleCode!=null){
           boolean result = modulePermsBusiness.hasModulePerms(moduleCode,empEntity);
           if (!result){
               httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/common/error/401");
           }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {
        if(modelAndView!=null){
            modelAndView.addObject("ctx", httpServletRequest.getContextPath());
            if (staffInfo.getEmpEntity()!=null){
                modelAndView.addObject("empEntity", staffInfo.getEmpEntity().getStaffBasicInfo());
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        staffInfo.removeEmpEntity();
    }

    /**
     * 是否有权限
     */
    private String hasModule(Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod != null) {
            // 获取方法上的注解
            Module module = handlerMethod.getBeanType().getAnnotation(Module.class);
            // 如果方法上的注解为空 则获取类的注解
            if (module != null) {
                return module.Code();
            }
            else {
                Auth auth = handlerMethod.getBeanType().getAnnotation(Auth.class);
                if (auth!=null){
                    return auth.key();
                }
            }
        }
        return null;
    }
}
