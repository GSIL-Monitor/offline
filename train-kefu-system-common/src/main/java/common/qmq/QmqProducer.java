package common.qmq;


import common.log.CLogger;
import qunar.tc.qmq.Message;
import qunar.tc.qmq.MessageSendStateListener;
import qunar.tc.qmq.producer.MessageProducerProvider;
import qunar.tc.qmq.producer.sender.DefaultRouterManagerDispatcher;
import qunar.tc.qmq.producer.sender.NettyRouterManager;

public class QmqProducer {
    public static class MyMessageSendStateListener implements MessageSendStateListener {

        public static MyMessageSendStateListener instance = new MyMessageSendStateListener();

        @Override
        public void onSuccess(Message message) {
            System.out.println("onSuc" + message.getMessageId());
        }

        @Override
        public void onFailed(Message message) {
            System.out.println("onFailed" + message.getMessageId());
        }
    }


    public static MessageProducerProvider getIntance() {
        try {
            MessageProducerProvider producer = new MessageProducerProvider(new DefaultRouterManagerDispatcher(new NettyRouterManager()));
            producer.init();
            return  producer;
        }
        catch (Exception ex) {
            CLogger.error("QmqProducer", ex);
            return null;
        }

    }
}

