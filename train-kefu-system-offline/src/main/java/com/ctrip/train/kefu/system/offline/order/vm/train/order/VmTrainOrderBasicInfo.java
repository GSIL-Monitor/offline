package com.ctrip.train.kefu.system.offline.order.vm.train.order;


import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单基本信息模块
 */
@Getter
@Setter
public class VmTrainOrderBasicInfo {

    private Long orderId;
    private String partnerOrderId;
    //private Integer orderSource;
    private String orderSourceName;  //订单渠道  缩减版 因为长度有限
    private String orderSourceNameD;  //订单渠道 详情
    private String orderState;  //订单状态
    private int orderStateInt;
    private int orderType;   //车票类型 订单类型
    private String ticketTypeStr;   //车票类型
    private int ticketType; //车票类型
    private String uid;
    private String userLoginName;   //预订用户   //铁友app渠道展示userid，其他展示userloginName
    private String contactName;  //联系人姓名

    private BigDecimal orderPrice;  //订单金额

    private String payType;  //支付方式
    private String paymentType;   //支付信息 支付方式对应的支付信息
    private String memberLevel;   //会员等级
    private String contactMobShen; //神盾解密联系方式
    private String contactMob; //联系方式
    private String contactMobSafe; //联系方式 加密
    private String contactEmail; //邮箱地址
    private String partherName; //渠道
    private String outboundNum;  //外呼码
    private String countryCode;   //联系人手机国家码
    private String userAccount;  //出票帐号
    private BigDecimal diffPrice;   //优惠券抵用金额
    private String orderTime;    //预定日期

    private Boolean isCtripWalletUser; //是否是携程钱宝用户
    private String agentName;    //配送票代售点名称
    private String packageName;   //套餐名称
    private String packageInfo;
    private List<String> orderStateInfo;   //订单状态相关信息
    private List<String> orderStateInfoRed;   //订单状态相关信息
    private String cancelBtn;   //取消订单按钮
    // 多程 去程 改签Token / 单程改签Token
    private String firstSeqGQToken;
    // 多程 返程 改签Token
    private String secondSeqGQToken;

    private Boolean isemployeeFamily;   //员工家园
    private String payRemark;
    private Boolean isIncludeCalculate;   //是否神算
    private Boolean isSuperVip;   //是否超级会员
    private Integer twoRecommendType;   //二推类型   1：私人定制二推  2：一般二推 3：可推荐更换座席
    private Boolean isNeedCancelTwoRec;  //是否可以取消二推
}
