package com.ctrip.train.kefu.system.api.aspect;

import com.ctriposs.baiji.rpc.common.HasResponseStatus;
import com.ctriposs.baiji.rpc.common.types.AckCodeType;
import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import common.log.CLogger;
import common.log.ESLogger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class ESlogAspect {

    @Around("execution(public * com.ctrip.train.kefu.system.api.soa.OfflineServiceImpl.*(" +
            "!com.ctriposs.baiji.rpc.common.types.CheckHealthRequestType)) && args(request)")
    public Object arround(ProceedingJoinPoint proceedingJoinPoint, Object request) {

        Object response = null;
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        // transaction duration
        Stopwatch watch = Stopwatch.createStarted();

        try {
            response = proceedingJoinPoint.proceed(new Object[]{request});
            if (response != null && response instanceof HasResponseStatus) {
                logMethod(method, request, response);
            }
        } catch (Throwable throwable) {
            Gson gson =new Gson();
            CLogger.info("ESlogAspect",gson.toJson(request));
            CLogger.error("ESlogAspect", throwable.getMessage());
        } finally {
            ESLogger.log(method.getName(), watch.stop().elapsed(TimeUnit.MILLISECONDS), request, response);
        }
        return response;
    }



    private void logMethod(Method method, Object request, Object response) {
        try {
            AckCodeType ack = AckCodeType.Warning;
            if (response != null && response instanceof HasResponseStatus) {
                ack = AckCodeType.Success;
                HasResponseStatus responseStatus = (HasResponseStatus) response;
                if (responseStatus.getResponseStatus() != null)
                    ack = responseStatus.getResponseStatus().getAck();
            }
            //只有返回Success才会记录日志
            if (!AckCodeType.Success.equals(ack))
                return;
            Gson gson =new Gson();
            CLogger.info(method.getName(), gson.toJson(request));
            CLogger.info(method.getName(), gson.toJson(response));
        } catch (Exception ex) {
            CLogger.warn("logging_error", ex);
        }
    }
}

