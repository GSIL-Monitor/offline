package com.ctrip.train.kefu.system.job.worker.order;

import com.ctrip.train.kefu.system.job.worker.notice.BaseTest;
import com.ctrip.train.kefu.system.job.worker.workers.order.train.ActivityFxWorker;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import qunar.tc.schedule.Parameter;

import java.util.Date;
import java.util.Map;

public class ActivityFxWorkerTest extends BaseTest {

    @Autowired
    private ActivityFxWorker activityFxWorker;

    @Test
    public void doWorker() {
        Parameter parameter = new Parameter() {
            @Override
            public String getJobName() {
                return "ActivityFxWorker";
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
        activityFxWorker.doWorker(parameter);
    }
}