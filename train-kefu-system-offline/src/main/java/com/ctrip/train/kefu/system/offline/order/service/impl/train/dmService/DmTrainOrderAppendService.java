package com.ctrip.train.kefu.system.offline.order.service.impl.train.dmService;

import com.ctrip.soa.train.trainordercentreservice.v1.CouponInfo;
import com.ctrip.soa.train.trainordercentreservice.v1.CouponModel;
import com.ctrip.soa.train.trainordercentreservice.v1.OrderAppendProductInfo;
import com.ctrip.soa.train.trainordercentreservice.v1.OrderDetailResponseType;
import com.ctrip.train.kefu.system.offline.order.config.MappingConfig;
import com.ctrip.train.kefu.system.offline.order.domain.train.DmTrainOrderDetail;
import com.ctrip.train.kefu.system.offline.order.domain.train.order.DmPassengersInfo;
import com.ctrip.train.kefu.system.offline.order.domain.train.order.DmTrainCouponInfo;
import com.ctrip.train.kefu.system.offline.order.domain.train.order.DmTrainOrderAppend;
import com.ctrip.train.kefu.system.offline.order.enums.train.AppendStateEnum;
import com.ctrip.train.kefu.system.offline.order.enums.train.CouponStateEnum;
import com.ctrip.train.kefu.system.offline.order.vm.train.order.*;
import common.log.CLogger;
import common.util.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DmTrainOrderAppendService {

    /**
     * 获取订单DoMain_附加产品信息
     */
    public List<DmTrainOrderAppend> getDmOrderAppend(OrderDetailResponseType orderDetailResponseType) {
        List<DmTrainOrderAppend>  dm = new ArrayList<> ();
        Map<String, String> dic = new HashMap<>();
        dic.put("orderNumber", orderDetailResponseType.getOrderMaster().getPartnerOrderId());
        try {
            List<OrderAppendProductInfo> passenferProducts=orderDetailResponseType.getAppendProductList();
            if (passenferProducts!=null&&passenferProducts.size()>0){
                ModelMapper modelMapper=new ModelMapper();
                dm= modelMapper.map(passenferProducts,new TypeToken<List<DmTrainOrderAppend>>() {}.getType());
            }
            return dm;
        } catch (Exception ex) {
            CLogger.error("获取订单DoMain_附加产品信息", ex, dic);
            return null;
        }
    }

    public List<DmTrainCouponInfo> getDmOrderCouponInfo(OrderDetailResponseType orderDetailResponseType) {
        List<DmTrainCouponInfo> dm = new ArrayList<>();
        Map<String, String> dic = new HashMap<>();
        dic.put("orderNumber", orderDetailResponseType.getOrderMaster().getPartnerOrderId());
        try {
            List<CouponModel> marketCouponList = orderDetailResponseType.getMarketCouponList();
            if (marketCouponList != null && marketCouponList.size() > 0) {
                ModelMapper modelMapper = new ModelMapper();
                dm = modelMapper.map(marketCouponList, new TypeToken<List<DmTrainCouponInfo>>() {
                }.getType());
            }
            List<CouponInfo> couponList = orderDetailResponseType.getCouponInfos();   //黄斌说CouponInfo是使用，marketCouponList是售卖，CouponInfo都是CouponType=10的，
            if (couponList != null && couponList.size() > 0) {
                ModelMapper modelMapper = new ModelMapper();
                modelMapper.addMappings(MappingConfig.MapCopuonList);
                dm.addAll(modelMapper.map(couponList, new TypeToken<List<DmTrainCouponInfo>>() {
                }.getType()));
            }
            return dm;
        } catch (Exception ex) {
            CLogger.error("获取订单DoMain_优惠券套餐", ex, dic);
            return null;
        }
    }
    private VmTrainProduct getVmTrainProduct(OrderAppendProductInfo passenferProduct) {
        VmTrainProduct vtp=new VmTrainProduct();
        vtp.setAppendExt(passenferProduct.getAppendExt());
        vtp.setAppendState(passenferProduct.getAppendState());
        vtp.setComment(passenferProduct.getComment());
        vtp.setIsFree(passenferProduct.getIsFree());
        vtp.setMapProductId(passenferProduct.getMapproductId());
        vtp.setPassengerId(passenferProduct.getPassengerId());
        vtp.setProductId(passenferProduct.getProductId());
        vtp.setProductNum(passenferProduct.getProductNum());
        vtp.setRespStr(passenferProduct.getRespStr());
        vtp.setProductSubtype(passenferProduct.getProductSubtype());
        vtp.setProductPrice(passenferProduct.getProductPrice());
        vtp.setProductType(passenferProduct.getProductType());
        vtp.setProductTitle(passenferProduct.getProductTitle());
        return vtp;
    }
    /**
     * 获取页面实体信息_附加产品信息
     */
    public VmTrainOrderAppend getVmOrderAppend(DmTrainOrderDetail dmTrainOrderDetail) {
        VmTrainOrderAppend vm = new VmTrainOrderAppend();

        Map<String, String> dic = new HashMap<>();
        dic.put("orderNumber", dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerOrderId());
        try {
            List<VmAppendDtPackage> tempApDtPackageList = getApPaDetailList(dmTrainOrderDetail.getDmTrainOrderAppend(), dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerName(),dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderId(), dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerOrderId());

            if (tempApDtPackageList != null && tempApDtPackageList.stream().anyMatch(T -> T.getProductTitle().equals("抢票加速包") && StringUtils.isNotEmpty(T.getOperate()))
                    && (dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderState() == 4 || dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderState() == 5 || dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderState() == 6)
                    ) {
                if (tempApDtPackageList.stream().anyMatch(T -> T.getProductId().equals("9999") && StringUtils.isNotEmpty(T.getOperate()))) {
                    vm.setIsCanRefundGrab(true);
                }
                vm.setIsCanRefAllGrab(true);
            }
            vm.setAppendPackageDtlist(tempApDtPackageList);
            vm.setAppendPackagelist(getApPackageList(tempApDtPackageList));
            vm.setPackageInfo(getPackageInfo(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPackageInfo()));
            vm.setPackageName(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPackageName());
            vm.setAppendInsurancelist(getVmAppendInsurance(dmTrainOrderDetail));   //抢票险新接口 里面是放在 AppendProductList 的
            vm.setAppendCouponlist(getAppendCouponlist(dmTrainOrderDetail));

            BigDecimal totalAppendPrice = vm.getAppendPackageDtlist().stream().map(T->T.getProductPrice().multiply(new BigDecimal(T.getProductNum()))).reduce(BigDecimal.ZERO,BigDecimal::add);
            totalAppendPrice = totalAppendPrice.add(vm.getAppendCouponlist().stream().map(T->T.getCouponPrice()).reduce(BigDecimal.ZERO,BigDecimal::add));
            totalAppendPrice = totalAppendPrice.add(vm.getAppendInsurancelist().stream().map(T->T.getProductPrice().multiply(new BigDecimal(T.getProductNum()))).reduce(BigDecimal.ZERO,BigDecimal::add));
            vm.setAppendPrice(totalAppendPrice);
            return vm;
        } catch (Exception ex) {
            CLogger.error("获取页面实体信息_附加产品信息", ex, dic);
            return null;
        }
    }

    /**
     *
     * @param dmTrainOrderAppend
     * @return
     */
    private List<VmAppendDtPackage> getApPaDetailList(List<DmTrainOrderAppend> dmTrainOrderAppend,String partnerName,Long orderNumber,String partnerOrderId){
        List<VmAppendDtPackage> tempApPackageDtList = new ArrayList<>();
        try {
            if (dmTrainOrderAppend != null && dmTrainOrderAppend.size() > 0) {
                for (DmTrainOrderAppend DmAppend : dmTrainOrderAppend) {
                    VmAppendDtPackage tempAppend = new VmAppendDtPackage();
                    tempAppend.setProductPrice(DmAppend.getProductPrice());   //产品单价
                    tempAppend.setProductNum(DmAppend.getProductNum()); //产品数量
                    tempAppend.setRespStr(DmAppend.getRespStr());
                    tempAppend.setAppendID(DmAppend.getAppendId());
                    if (DmAppend.getProductId() == 9999) {
                        tempAppend.setIsDefault("默认");  //默认非默认
                    } else if (DmAppend.getProductId() == 9996 || DmAppend.getProductId() == 9997 || DmAppend.getProductId() == 9998) {
                        tempAppend.setIsDefault("非默认");
                    }
                    tempAppend.setAppendState(AppendStateEnum.convertVendor(DmAppend.getAppendState()).getName());   //状态
                    tempAppend.setProductId(DmAppend.getProductId());  //产品ID
                    tempAppend.setProductTitle(DmAppend.getProductTitle());  //产品名称
                    tempAppend.setProductType(DmAppend.getProductType()); //产品类型 附加产品类型I保险 G礼品卡 C优惠券 V VIP休息室 T门票 O其他

                    if (DmAppend.getAppendState().equals("40") && !DmAppend.getProductSubtype().equals("CtripVip") && !partnerName.equals("qunarsync"))//迪士尼不允许退款
                    {
                        tempAppend.setOperate("<a href=\"javascript:void(0);\" onclick=\"RefundAppendProduct('" + partnerOrderId + "','" + DmAppend.getAppendId() + "')\">退款</a>");
                    }
//                if (DmAppend.getProductSubtype().equals("DelayIns")) {   //延误险已经没有了，应该不会走这段逻辑
//                    tempAppend.setOperate(" <a href=\"javascript:void(0);\" onclick=\"appendInfo.queryInsuceanceState('" + orderNumber + "','" + DmAppend.getRespStr() + "','2')\">查看理赔状态</a>");
//                }

                    if (DmAppend.getProductSubtype() != null) {
                        if (DmAppend.getProductSubtype().equals("GrabIns") || DmAppend.getProductSubtype().equals("NorIns") || DmAppend.getProductSubtype().equals("GiftCard"))//抢票险、正常保险、礼品卡不显示
                        {
                            continue;
                        }
                    }
                    if (DmAppend.getProductSubtype() != null && DmAppend.getProductSubtype().equals("Transfer") && StringUtils.isNotEmpty(tempAppend.getRespStr())) {
                        tempAppend.setRespStr(String.format("<a href=\"http://order.car.ctripcorp.com/Car-Order-Process/Order/Detail?orderId=%s\" target=\"_blank\">%s</a>", tempAppend.getRespStr(), tempAppend.getRespStr()));
                    }

                    tempApPackageDtList.add(tempAppend);
                }
            }
        } catch (Exception ex) {
            CLogger.error("getApPaDetailList", ex);
        }
        return tempApPackageDtList;
    }

    private List<VmAppendPackage> getApPackageList(List<VmAppendDtPackage> dmTrainOrderAppend){
        List<VmAppendPackage> tempApPackageList = new ArrayList<>();
        if (dmTrainOrderAppend != null && dmTrainOrderAppend.size() > 0) {

            List<Integer> listProductID = dmTrainOrderAppend.stream().map(p -> p.getProductId()).distinct().collect(Collectors.toList());
            for (Integer productId : listProductID) {
                List<VmAppendDtPackage> listOne = dmTrainOrderAppend.stream().filter(p -> p.getProductId().equals(productId)).collect(Collectors.toList());
                BigDecimal amt = new BigDecimal(0);
                Integer pnum = 0;
                if (listOne != null && listOne.size() > 0) {
                    for (VmAppendDtPackage item : listOne) {
                        amt = amt.add(item.getProductPrice().multiply(new BigDecimal(item.getProductNum())));
                        pnum += item.getProductNum();
                    }
                }

//                    List<DmTrainOrderAppend> freelist = listOne.stream().filter(p -> p.getIsFree() == 1).collect(Collectors.toList());
//                    String freetag = "";
//                    if (freelist != null && freelist.size() > 0) {
//                        if (freelist.size() == listOne.size()) {
//                            freetag = "(免费)";
//                        } else {
//                            freetag = "(含免费)";
//                        }
//                    }

                String isDefault = "";
                if (productId == 9999) {
                    isDefault = "默认";
                } else if (productId == 9996 || productId == 9997 || productId == 9998) {
                    isDefault = "非默认";
                }
                VmAppendPackage tempVmAppend = new VmAppendPackage();
                tempVmAppend.setProductId(productId);
                if (listOne != null && listOne.size() > 0) {
                    tempVmAppend.setProductPrice(listOne.get(0).getProductPrice());   //产品单价
                    tempVmAppend.setAppendState(listOne.get(0).getAppendState());   //状态
                    tempVmAppend.setProductType(listOne.get(0).getProductType()); //产品类型 附加产品类型I保险 G礼品卡 C优惠券 V VIP休息室 T门票 O其他
                    tempVmAppend.setProductTitle(listOne.get(0).getProductTitle());  //产品名称
                }

                tempVmAppend.setProductNum(pnum); //产品数量
                tempVmAppend.setTotalPrice(amt);   //总金额
                tempVmAppend.setIsDefault(isDefault);  //默认非默认
                tempApPackageList.add(tempVmAppend);
            }
        }
        return tempApPackageList;
    }


    /**
     * 抢票险
     * @param dmTrainOrderDetail
     * @return
     */
    private List<VmAppendInsurance> getVmAppendInsurance(DmTrainOrderDetail dmTrainOrderDetail) {
        List<VmAppendInsurance> vmAppendInsList = new ArrayList<>();
        List<DmTrainOrderAppend> listOne = dmTrainOrderDetail.getDmTrainOrderAppend().stream().filter(p -> p.getProductType().equals("I")).collect(Collectors.toList());
        if (listOne != null && listOne.size() > 0) {
            for(DmTrainOrderAppend orderAppend:listOne) {
                VmAppendInsurance tempVmAppend = new VmAppendInsurance();
                tempVmAppend.setProductPrice(orderAppend.getProductPrice());   //产品单价
                tempVmAppend.setProductState(AppendStateEnum.convertVendor(orderAppend.getAppendState()).getName());   //状态
                tempVmAppend.setProductType(orderAppend.getProductType()); //产品类型 附加产品类型I保险 G礼品卡 C优惠券 V VIP休息室 T门票 O其他
                tempVmAppend.setProductId(orderAppend.getProductId());
                tempVmAppend.setProductTitle(orderAppend.getProductTitle());  //产品名称
                tempVmAppend.setProductNum(orderAppend.getProductNum());
                if (dmTrainOrderDetail.getDmPassengersInfo() != null && dmTrainOrderDetail.getDmPassengersInfo().size() > 0) {
                    DmPassengersInfo tempdmPassengersInfo = dmTrainOrderDetail.getDmPassengersInfo().stream().filter(T -> T.getPassengerId().equals(orderAppend.getPassengerId())).findFirst().get();
                    if (tempdmPassengersInfo != null) {
                        tempVmAppend.setPassportType(tempdmPassengersInfo.getPassportType());
                        tempVmAppend.setPassportNumber(tempdmPassengersInfo.getPassportNumber());
                        tempVmAppend.setPassengerName(tempdmPassengersInfo.getRealName());
                        tempVmAppend.setBirthDay(tempdmPassengersInfo.getBirthday());
                    }
                }
                tempVmAppend.setInsuranceId(orderAppend.getRespStr());
                vmAppendInsList.add(tempVmAppend);
            }
        }
        return vmAppendInsList;
    }


    /**
     * 获取优惠券信息
     * @param dmTrainOrderDetail
     * @return
     */
    private List<VmAppendCoupon> getAppendCouponlist(DmTrainOrderDetail dmTrainOrderDetail) {
        List<VmAppendCoupon> tempappendCouponlist = new ArrayList<>();
        if (dmTrainOrderDetail != null && dmTrainOrderDetail.getDmTrainCouponInfo() != null && dmTrainOrderDetail.getDmTrainCouponInfo().size() > 0) {
            for (DmTrainCouponInfo trainCouponInfo : dmTrainOrderDetail.getDmTrainCouponInfo()) {
                VmAppendCoupon tempappendCoupon = new VmAppendCoupon();
                tempappendCoupon.setCouponTypeName(getCouponTypeName(dmTrainOrderDetail, trainCouponInfo.getCouponType(),trainCouponInfo.getCouponPolicyNo()));   //优惠券名称
                tempappendCoupon.setCouponCode(trainCouponInfo.getCouponCode());   //优惠券码;
                tempappendCoupon.setCouponId(trainCouponInfo.getCouponId());
                tempappendCoupon.setCouponPrice(trainCouponInfo.getCouponPrice());     //优惠券金额;
                tempappendCoupon.setCouponRealPrice(trainCouponInfo.getCouponRealPrice()); //优惠券使用金额;
                tempappendCoupon.setCouponUsageTime(trainCouponInfo.getCouponUsageTime());  //优惠券出成功时间;
                tempappendCoupon.setCouponState(trainCouponInfo.getCouponState()); //优惠券状态;
                tempappendCoupon.setCouponStateName(CouponStateEnum.convertVendor(trainCouponInfo.getCouponState()).getName());
                tempappendCoupon.setCouponTicketFeedesc("");    //抵扣类型;
                tempappendCoupon.setIsCanRefund(getIsCanRefund(trainCouponInfo));
                tempappendCouponlist.add(tempappendCoupon);
            }
        }
        return tempappendCouponlist;
    }


    /**
     * 获取优惠券名称
     *
     * @param dmTrainOrderDetail
     * @param couponType
     * @return
     */
    private String getCouponTypeName(DmTrainOrderDetail dmTrainOrderDetail, String couponType, String couponPolicyNo) {
        String couponTypeName = StringUtils.EMPTY;
        if(StringUtils.isEmpty(couponType)){
            return couponTypeName;
        }
        if (couponType.equals("10")) {
            if (StringUtils.isNotEmpty(couponPolicyNo) && couponPolicyNo.equals("81298"))   //策略使用号
            {
                couponTypeName = "超级会员返现券";
            } else {
                couponTypeName = "火车票优惠券";
            }
        } else if (couponType.equals("20")) {
            if (StringUtils.isNotEmpty(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPackageName())) {
                couponTypeName = dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPackageName();
            } else {
                couponTypeName = "酒店优惠券";
            }
        }
        return couponTypeName;
    }

    /**
     * 是否可以退套餐
     * @param trainCouponInfo
     * @return
     */
    private Boolean getIsCanRefund(DmTrainCouponInfo trainCouponInfo){
        boolean tempIsRefund = false;
        if(StringUtils.isEmpty(trainCouponInfo.getCouponType()) || trainCouponInfo.getCouponState() == null){
            return tempIsRefund;
        }
        if (trainCouponInfo.getCouponType().equals("20") && (trainCouponInfo.getCouponState().equals(35) || trainCouponInfo.getCouponState().equals(30) || trainCouponInfo.getCouponState().equals(40)))//已发放,未发放，发放失败，酒店优惠券增加操作
        {
            tempIsRefund= true;
        }
        return tempIsRefund;
    }

    /**
     * 套餐信息的html格式处理
     * @param packageInfo
     * @return
     */
    private String getPackageInfo(String packageInfo){
        String packageInfoHtml;
        List<String> tempPackageInfo = new ArrayList<>();
        Arrays.asList(packageInfo.split("\n")).stream().forEach(T -> {
            if (T.contains("|")) {
                List<String> templist = Arrays.asList(T.split("\\|"));
                if (templist.size() > 1) {
                    tempPackageInfo.add(String.format("<a target=\"_blank\" href=\"%s\">%s</a>", templist.get(1), templist.get(0)));
                } else {
                    tempPackageInfo.add(templist.get(0));
                }
            } else {
                tempPackageInfo.add(T);
            }
        });
        packageInfoHtml = StringUtils.join(tempPackageInfo, "<br/>");
        return packageInfoHtml;
    }
}
