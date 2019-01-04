package com.ctrip.train.kefu.system.offline.order.config;


import com.ctrip.soa.train.javaxproductservice.v1.XpOrderInvoice;
import com.ctrip.soa.train.trainordercentreservice.v1.CouponInfo;
import com.ctrip.train.kefu.system.offline.order.domain.train.order.DmTrainCouponInfo;
import com.ctrip.train.kefu.system.offline.order.vm.VmInvoiceInfo;
import org.modelmapper.*;


public class MappingConfig {
    public static PropertyMap<XpOrderInvoice, VmInvoiceInfo> MapInvoiceList = new PropertyMap<XpOrderInvoice, VmInvoiceInfo>() {
        @Override
        protected void configure() {
        }
    };
    public static PropertyMap<CouponInfo,DmTrainCouponInfo> MapCopuonList=new  PropertyMap<CouponInfo,DmTrainCouponInfo>(){
        @Override
        protected void configure(){
            map("10",destination.getCouponType());
        }
    };
}


