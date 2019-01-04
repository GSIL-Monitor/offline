package com.ctrip.train.kefu.system.offline.common.component;

import com.ctrip.framework.vi.NotFoundException;
import common.log.CLogger;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ExceptionComponent {

    @ExceptionHandler(value = UnauthorizedException.class)//处理访问方法时权限不足问题
    public void unauthorizedHandler(HttpServletRequest req, HttpServletResponse response,Exception e) throws IOException {
         response.sendRedirect(req.getContextPath()+"/common/error/401");
    }

    @ExceptionHandler(value = NotFoundException.class)
    public void notFoundExceptionHandler(HttpServletRequest req, HttpServletResponse response,Exception e) throws IOException {
        response.sendRedirect(req.getContextPath()+"/common/error/404");
    }

    @ExceptionHandler(value = NullPointerException.class)//处理访问方法时权限不足问题
    public void exceptionHandler(HttpServletRequest req, HttpServletResponse response,Exception e) throws IOException {
        CLogger.error("NullPointerException",e);
        response.sendRedirect(req.getContextPath()+"/common/error/500");
    }
}

