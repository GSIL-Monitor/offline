package com.ctrip.train.kefu.system.offline.order.service.impl.train;

import com.ctrip.train.kefu.system.offline.order.dao.ExTrainScmTwoTask;
import com.ctrip.train.kefu.system.offline.order.service.TrainScmTwoTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainScmTwoTaskServiceImpl implements TrainScmTwoTaskService {
    @Autowired
    private ExTrainScmTwoTask exTrainScmTwoTask;

    @Override
    public int searchScmTwoTaskCount(String partnerOrderId) {
        return exTrainScmTwoTask.searchScmTwoTaskCount(partnerOrderId);
    }
}
