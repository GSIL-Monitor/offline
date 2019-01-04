package com.ctrip.train.kefu.system.job.worker;

import qunar.tc.schedule.Parameter;

public abstract class BaseWorker {
    public abstract void doWorker(Parameter parameter);
}
