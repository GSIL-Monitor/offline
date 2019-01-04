package com.ctrip.train.kefu.system.offline.order.service;

public interface TrainScmTwoTaskService {
    /**
     * 查看是否存在二推任务
     * @param partnerOrderId
     * @return
     */
    int searchScmTwoTaskCount(String partnerOrderId);

}
