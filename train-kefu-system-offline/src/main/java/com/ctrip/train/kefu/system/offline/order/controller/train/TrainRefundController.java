package com.ctrip.train.kefu.system.offline.order.controller.train;


import com.ctrip.train.kefu.system.offline.common.controller.BaseController;
import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import com.ctrip.train.kefu.system.offline.order.domain.train.DmTrainOrderDetail;
import com.ctrip.train.kefu.system.offline.order.service.impl.train.TrainOrderServiceImpl;
import com.ctrip.train.kefu.system.offline.order.service.impl.train.dmService.DmTrainOrderTicketsInfoService;
import com.ctrip.train.kefu.system.offline.order.vm.train.order.VmApplyRefund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("order/train/refund")
public class TrainRefundController extends BaseController{

    @Autowired
    private TrainOrderServiceImpl trainOrderServiceImpl;

    @Autowired
    private DmTrainOrderTicketsInfoService dmTrainOrderTicketsInfoService;

    @RequestMapping("/apply")
    public String index(Map<String, Object> model,String orderId)  {
        DmTrainOrderDetail orderDetailInfo = trainOrderServiceImpl.getOrderDetail(orderId);
        model.put("tickets", dmTrainOrderTicketsInfoService.getVmOrderTicketsInfo(orderDetailInfo));
        return "order/train/refund/apply";
    }

    /**
     * 退票详情
     * @param model
     * @param orderId
     * @return
     */
    @RequestMapping("/getRefundInfo")
    public String getRefundInfo(Map<String,Object> model,String orderId) {
        DmTrainOrderDetail orderDetailInfo = trainOrderServiceImpl.getOrderDetail(orderId);
        model.put("orderId",orderDetailInfo.getDmTrainOrderBasicInfo().getPartnerOrderId());
        model.put("partnerName",orderDetailInfo.getDmTrainOrderBasicInfo().getPartnerName());
        model.put("tickets", dmTrainOrderTicketsInfoService.getVmOrderTicketsInfo(orderDetailInfo));
        return "order/train/refund/apply";
    }

    /**
     * 申请退票
     */
    @RequestMapping("/applyRefundTicket")
    @ResponseBody
    public JsonResult applyRefund(@RequestBody VmApplyRefund request) {
        request.setOperator(getOpUser());
        return JsonResult.ok(trainOrderServiceImpl.applyRefundTicket(request));
    }

}
