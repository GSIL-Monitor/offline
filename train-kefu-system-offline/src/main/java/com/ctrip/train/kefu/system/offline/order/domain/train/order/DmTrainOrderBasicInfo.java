package com.ctrip.train.kefu.system.offline.order.domain.train.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter

public class DmTrainOrderBasicInfo {
    private int activityState;
    private String addonProductsFlag;
    private String appVersion;//版本号
    private String bookSourceID;//来源渠道ID (1901,1903)
    private String channelName;//订单渠道
    private String comment;
    private int communicationState;
    private String contactEmail;//联系人邮箱
    private String contactMobile;//联系人手机
    private String contactName;//联系人姓名
    private String createTime;
    private String dataChangeLastTime;
    private String electricAccount;
    private int insuranceInvoice;//是否需要寄送发票(0、不需要寄送；1、需要寄送)
    private String isQuickBooking;
    private String language;
    private String loginName;
    private String orderFlag;//1:已删除，2：已改签，4：已补票，8：使用了优惠券，16：配送票检漏票，32：会员标志位
    private String orderFlagV2;//1:已删除，2：已改签，4：已补票，8：使用了优惠券，16：配送票检漏票，32：会员标志位
    private String orderFlagV3;
    private long orderId;//携程订单号
    private BigDecimal orderPrice;
    private int orderState;//订单状态.......................................................此处注意对比和原接口中的返回值是否一致
    private String orderTime;//预定日期-时间
    private int orderType;//车票类型 0配送票 2电子票 6 抢票
    private String partnerName;
    private String partnerOrderId;//供应商订单号
    private int payState;
    private String returnTicketState;
    private String serverFrom;//订单来源client/android/91and(新增接口中)、trains.ctrip.com
    private int orderSource;//终端类型app、pc、wap
    private String ticketCheckState;
    private BigDecimal ticketOffsetFee;
    private int ticketType;
    private String userID;//用户登陆ID
    private String payType; //支付方式
    private String countryCode;   //联系人手机国家码
    private String userAccount;  //12306登录名
    private String packageName;  //套餐名称
    private String packageInfo;
    private Date payTime;  //支付时间
}
