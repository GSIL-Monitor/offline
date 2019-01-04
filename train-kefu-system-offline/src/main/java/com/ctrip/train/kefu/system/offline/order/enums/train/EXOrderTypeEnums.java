package com.ctrip.train.kefu.system.offline.order.enums.train;

import java.util.Arrays;

/**
 * 异常订单类型
 */
public enum EXOrderTypeEnums {
    //超时未出票、超时未改签、超时未退票、票号异常、出票失败、改签失败、退票失败
    TimeoutNotIssued{
        @Override public int getState() { return 70; }
        @Override public String getName() { return "超时未出票"; }
    },
    TimeoutNotChange{
        @Override public int getState() { return 71; }
        @Override public String getName() { return "超时未改签"; }
    },
    TimeoutNotRefund{
        @Override public int getState() { return 72; }
        @Override public String getName() { return "超时未退票"; }
    },
    TicketNumEX{
        @Override public int getState() { return 73; }
        @Override public String getName() { return "票号异常"; }
    },
    IssuedFail{
        @Override public int getState() { return 74; }
        @Override public String getName() { return "出票失败"; }
    },
    ChangeFail{
        @Override public int getState() { return 75; }
        @Override public String getName() { return "改签失败"; }
    },
    RefundFail{
        @Override public int getState() { return 76; }
        @Override public String getName() { return "退票失败"; }
    };
    public static EXOrderTypeEnums convertEXOrderType(int state){
        return Arrays.stream(EXOrderTypeEnums.values()).filter(it->it.getState()==state).findFirst().orElse(null);
    }
    public abstract int getState();
    public abstract String getName();

}
