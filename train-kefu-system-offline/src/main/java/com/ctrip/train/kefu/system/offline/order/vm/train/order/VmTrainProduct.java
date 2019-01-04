package com.ctrip.train.kefu.system.offline.order.vm.train.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class VmTrainProduct {
    private long productId;
    private String mapProductId;
    private BigDecimal productPrice;
    private int productNum;
    private String productType;
    private String appendState;
    private String productSubtype;
    private String productTitle;
    private String appendExt;
    private String respStr;
    private String comment;
    private int isFree;
    private long passengerId;
}
