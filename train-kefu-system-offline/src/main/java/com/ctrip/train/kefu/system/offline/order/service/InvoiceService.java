package com.ctrip.train.kefu.system.offline.order.service;

import com.ctrip.train.kefu.system.offline.order.vm.VmDelivery;
import com.ctrip.train.kefu.system.offline.order.vm.VmInvoiceDetail;
import com.ctrip.train.kefu.system.offline.order.vm.VmInvoiceInfo;
import com.ctrip.train.kefu.system.offline.order.vm.VmInvoiceOrderInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jian_ji on 2018/7/2.
 */
@Service
public interface InvoiceService {

    /**
     * 开电子发票
     * @param vmInvoiceDetail
     * @param eid
     * @return
     * @throws Exception
     */
    List<String> createInvoice(VmInvoiceDetail vmInvoiceDetail, String eid);

    /**
     * 获取关联订单号
     * @param mobile
     * @return
     */
    List<VmInvoiceOrderInfo> getOrderList(String mobile,String superVipOrderNumber,List<VmInvoiceInfo> vmInvoiceInfolist,String source);

    /**
     * 混合补给发票需要传一个 地址相关的内容过去
     * @param orderId
     * @return
     */
    VmDelivery getDelivery(String orderId);

//    /**
//     * 获取发票订单详情
//     * @return
//     */
//    List<VmInvoiceInfo> getInvoiceList(String partnerOrderId);
}
