package com.ctrip.train.kefu.system.job.aspect;

import com.alibaba.fastjson.JSON;
import common.log.CLogger;
import common.util.DateUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import qunar.tc.schedule.Parameter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * write the common log of job
 * and record the cost time of the job
 * @author ying_zhou 2017/11/4 21:17
 */
@Component
@Aspect
public class WorkerLogger {

    @Around("execution(* com.ctrip.train.kefu.system.job.worker.BaseWorker.doWorker(..))  && args(parameter)")
    public void before(ProceedingJoinPoint proceedingJoinPoint,Parameter parameter){
        CLogger.info(String.format("[%s]",parameter.getJobName()),"Job start");
        Date start= DateUtils.currentTime();
        try {
            proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            Map<String,String> dic=new HashMap<>();
            dic.put("parameter", JSON.toJSONString(parameter));
            CLogger.error("[%s]",throwable.getMessage(),dic);
        }
        Date end=DateUtils.currentTime();
        Map<String,String> dic=new HashMap<>();
        dic.put("time_cost",DateUtils.getDiffSecond(end,start)+"S");
        CLogger.info(String.format("[%s]",parameter.getJobName()),"Job finish" ,dic);
    }

}
