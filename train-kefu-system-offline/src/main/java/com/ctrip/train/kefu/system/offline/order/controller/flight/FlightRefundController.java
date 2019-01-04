package com.ctrip.train.kefu.system.offline.order.controller.flight;

import com.ctrip.train.kefu.system.offline.common.controller.BaseController;
import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import com.ctrip.train.kefu.system.offline.order.service.FlightRefundService;
import com.ctrip.train.kefu.system.offline.order.vm.flight.refund.VmFlightRefund;
import com.ctrip.train.kefu.system.offline.order.vm.flight.refund.dto.DtoRefund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("order/flight/refund")
public class FlightRefundController extends BaseController {

    @Autowired
    private FlightRefundService service;


    @RequestMapping("/detail")
    public  String index(Map<String, Object> model ,String orderId,String ctripUid,String tyUerid)  {
        DtoRefund refund = new DtoRefund();
        refund.setOrderid(orderId);
        refund.setCtripUid(ctripUid);
        refund.setOperatorName(getEmpName());
        refund.setTyUerid(tyUerid);
        model.put("refundDetail", service.getRefundDetail(refund));
        return "order/flight/refund/index";
    }

    @PostMapping
    @ResponseBody
    public JsonResult refund(@RequestBody VmFlightRefund refund) {
        refund.setOperateName(getOpUser());
        Map<String,String> map= service.refundDetail(refund);
        String result="";
        for(String key : map.keySet()){
            result = map.get(key);
        }
        return  JsonResult.ok().putData("result",result);
    }

}
