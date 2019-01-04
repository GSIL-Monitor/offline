package com.ctrip.train.kefu.system.offline.order.domain.train.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
/**
 * 附加产品信息
 */
public class DmTrainOrderAppend {

    private int productId;  //落地系统产品

    private String mapproductId; //实际产品id

    private BigDecimal productPrice; //附加产品金额

    private int productNum;//预订份数

    private String productType; //附加产品类型（I保险 G礼品卡 C优惠券 VVIP休息室 O其他）

    private String productSubtype;//附件产品子类型

    private String productTitle; //改签后真实坐席

    private String appendExt;//产品名称

    private String appendState;//扩展字段

    private String respStr;//0：初始；10：处理中；20：处理成功；30：处理失败

    private String comment;//处理成功结果集

    private int isFree;//是否免费 0:收费 1:免费

    private long extOrderId;//附属子订单号

    private long passengerId;//乘客号

    private long appendId;

}
