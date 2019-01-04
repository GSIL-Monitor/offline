package com.ctrip.train.kefu.system.offline.order.vm.train.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmTrainButton {

    //重要通知
//    private boolean importantNotice;
    //异常件
//    private boolean exEvent;
    //119
//    private boolean notice119;
    //凭证
    private boolean certificate;
    //发票
    private boolean invoice;
    //二推
    private boolean twoPush;
    //代售点通知
//    private boolean saleNotice;
    //添加垫付
    private boolean addAdvance;
    //In12306
    private boolean in12306;
    //解绑账号
    private boolean unbindAccount;
    //电话数量
    private int ivrPhoneNumber;
    //在线咨询数量
    private int chatCount;
    private int importantNoticeCount;
}
