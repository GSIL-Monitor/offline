package com.ctrip.train.kefu.system.offline.common.aspect;

import com.ctrip.train.kefu.system.offline.common.aspect.annotation.Auth;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class AuthAspect {

    @Pointcut("@annotation(com.ctrip.train.kefu.system.offline.common.aspect.annotation.Auth)")
    public void AuthCut() {
    }

    @Before("@annotation(auth)")
    public  void  doBefore(JoinPoint point, Auth auth) throws IOException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();


        List<String> permCode = new ArrayList<>();
        permCode.add("test");

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //判断是否有权限
        if (permCode.stream().noneMatch(p -> p.equals(auth.key()))) {
            response.sendRedirect(request.getContextPath() + "/common/error/401");
        }
    }
}
