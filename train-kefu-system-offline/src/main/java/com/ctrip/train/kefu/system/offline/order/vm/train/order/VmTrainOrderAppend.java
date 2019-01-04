package com.ctrip.train.kefu.system.offline.order.vm.train.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class VmTrainOrderAppend {

    private String packageName;   //套餐名称
    private String packageInfo;
    private Boolean isCanRefundGrab;    //是否可以退默认加速包
    private Boolean isCanRefAllGrab;     //退加速包
    private BigDecimal appendPrice;   //附属产品金额
    private List<VmAppendPackage> appendPackagelist;   //附属产品（加速包）
    private List<VmAppendDtPackage> appendPackageDtlist;   //附属产品详情（加速包）
    private List<VmAppendCoupon> appendCouponlist;
    private List<VmAppendInsurance> appendInsurancelist;    //抢票险

}

