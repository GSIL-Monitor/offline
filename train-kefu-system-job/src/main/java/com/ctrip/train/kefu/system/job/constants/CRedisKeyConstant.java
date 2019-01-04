package com.ctrip.train.kefu.system.job.constants;

public class CRedisKeyConstant {
    //员工在线状态
    public final static String STAFF_ONLINE_STATE_KEY="STAFF_ONLINE_STATE_KEY_%s";//+EID
    //员工信息
    public final static String STAFF_INFO_KEY="STAFF_INFO_KEY_%s"; //+EID
    //员工存在未开始处理通知的标志
    public final static String NOTICE_NOT_HANDLED_BY_STAFF="NOTICE_NOT_HAND_BY_STAFFER_%s";//eid
    //系统枚举
    public final static String SMALL_ENUM_BY_TID="SMALL_ENUM_BY_TID_%s";//tid

}
