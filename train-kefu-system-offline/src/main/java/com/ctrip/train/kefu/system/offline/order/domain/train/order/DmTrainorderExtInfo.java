package com.ctrip.train.kefu.system.offline.order.domain.train.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Calendar;

//订单扩展信息表
@Getter
@Setter
public class DmTrainorderExtInfo {

    //检漏截至日期
    private String leakCutOffTime;
    //套餐名称
    private String packageName;
    //套餐详情

    private String packageInfo;
    //用于统计的各种标记

    private long statisticsSign;
    //快捷支付额度

    private BigDecimal quickPayAmount;
    //最后支付时间

    private Calendar timeoutTime;
    //联盟ID

    private int allianceID;
    //站点ID

    private int sid;
    //是否超时支付取消,  0不是 1 是

    private int isCancelByOverTime;
    //客户端信息

    private String mediaClientDesc;
    //国家码

    private String countryCode;
    //汇率

    private String exchangeRate;
    //币种

    private String currency;
    //推荐渠道

    private String recommdSource;

    private int packageId;
    //距离发车前多少时间的偏移量 单位(分钟)
    private int offsetTicketTime;
    //预订前端大字符串
    private String frontExt;
}
