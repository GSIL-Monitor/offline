package com.ctrip.train.kefu.system.api.service.order.train;

import common.qmq.QmqProducer;
import org.springframework.stereotype.Service;
import qunar.tc.qmq.Message;
import qunar.tc.qmq.producer.MessageProducerProvider;

@Service
public class CouponsService {

    /**
     * 发送退套餐申请到队列
     * @param orderid 订单id
     * @param uid 用户id
     */
    public void CancelCoupons(String orderid,String uid)
    {
        MessageProducerProvider provider=QmqProducer.getIntance();
        if (provider!=null) {
            Message message = provider.generateMessage("train.offline.cancelPackage");
            message.setProperty("orderid", orderid);
            message.setProperty("uid", uid);
            provider.sendMessage(message, QmqProducer.MyMessageSendStateListener.instance);
        }
    }
}
