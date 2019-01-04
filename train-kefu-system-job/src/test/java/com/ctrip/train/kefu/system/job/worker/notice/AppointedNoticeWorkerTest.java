package com.ctrip.train.kefu.system.job.worker.notice;

import com.ctrip.train.kefu.system.job.worker.workers.notice.AppointedNoticeWorker;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import qunar.tc.schedule.Parameter;

import java.util.Date;
import java.util.Map;

public class AppointedNoticeWorkerTest extends BaseTest {

    @Autowired
    public AppointedNoticeWorker appointedNoticeCheckWorker;
    @Test
    public void doWorker() {
        Parameter parameter= new Parameter() {
            @Override
            public String getJobName() {
                return "ssssss";
            }

            @Override
            public Date getCreatedDate() {
                return null;
            }

            @Override
            public String getString(String s) {
                return "10";
            }

            @Override
            public <T> T getProperty(String s, Class<T> aClass) {
                return null;
            }

            @Override
            public Map<String, String> getLastResultInWorkflow() {
                return null;
            }

            @Override
            public int shardId() {
                return 0;
            }

            @Override
            public int shards() {
                return 0;
            }
        };
        appointedNoticeCheckWorker.doWorker(parameter);
    }
}