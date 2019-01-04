package com.ctrip.train.kefu.system.job.worker.service.order.train;

import com.alibaba.fastjson.JSON;
import com.ctrip.soa.train.trainordercentreservice.offline.v1.RefundCouponResponseType;
import com.ctrip.train.kefu.system.client.offline.train.OrderContract;
import com.ctrip.train.kefu.system.job.worker.dao.order.ExtOrderAsyncoperate;
import com.ctrip.train.kefu.system.job.worker.entity.order.train.DMCancelCoupons;
import common.log.CLogger;
import dao.ctrip.ctrainpps.entity.OrderAsyncoperate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CancelCouponsService {

    @Autowired
    private ExtOrderAsyncoperate orderAsyncoperate;

    @Autowired
    private OrderContract orderContract;

    /**
     * 退套餐业务
     * @return
     */
    public void CancelCoupons(){
        List<OrderAsyncoperate> list= orderAsyncoperate.getOrderAsyncoperates();
        if (list!=null&&list.size()>0) {
            for (OrderAsyncoperate p : list) {
                if (p.getRequest() == null) {
                    continue;
                }
                DMCancelCoupons dm =  JSON.parseObject(p.getRequest(),DMCancelCoupons.class);
                String jsonRespose = "";
                if (dm == null || dm.getCouponIds().size() == 0) {
                    orderAsyncoperate.updateOrderAsyncoperate(p.getId(),
                            "无可退催优惠卷套餐", 2,"RefundCoupon");
                    continue;
                }

                for(String item:dm.getCouponIds()){
                    RefundCouponResponseType responseType = orderContract.refundCoupon(dm.getOrderForm(), dm.getOrderid(), item, dm.getUid());
                    try {
                        Thread.sleep(900);
                    } catch (InterruptedException e) {
                        CLogger.error("CancelCoupons",e);
                    }

                    if ((jsonRespose==null||jsonRespose.isEmpty()) &&responseType!=null&&responseType.getRetCode()==0){
                        jsonRespose = responseType.getMsg();
                        if (jsonRespose==null||jsonRespose.isEmpty()){
                            jsonRespose = responseType.getMessage();
                        }

                        p.setState(1);
                    }

                    if (responseType!=null&&responseType.getRetCode()!=0){
                        jsonRespose = responseType.getMsg();
                        p.setState(2);
                    }

                }

                //jsonRespose 不可为空，否则无法修改数据
                if (jsonRespose==null||jsonRespose.isEmpty()){
                    jsonRespose = "接口无返回信息";
                }
                orderAsyncoperate.updateOrderAsyncoperate(p.getId(), jsonRespose, p.getState(),"RefundCoupon");
            }
        }
    }
}
