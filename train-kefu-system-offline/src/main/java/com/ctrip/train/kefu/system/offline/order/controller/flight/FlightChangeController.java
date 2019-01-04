package com.ctrip.train.kefu.system.offline.order.controller.flight;

import com.ctrip.soa.framework.soa.tieyouflightvendor.v1.RebookResponseType;
import com.ctrip.train.kefu.system.client.pojo.flight.FlightChangeDetailRequest;
import com.ctrip.train.kefu.system.offline.common.controller.BaseController;
import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import com.ctrip.train.kefu.system.offline.order.service.FlightChangeService;
import com.ctrip.train.kefu.system.offline.order.vm.flight.change.VmFlightChangeCollect;
import com.ctrip.train.kefu.system.offline.order.vm.flight.change.VmFlightSerach;
import com.ctrip.train.kefu.system.offline.order.vm.flight.change.VmRebookRequest;
import common.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

/**
 * 机票改签
 */
@Controller
@RequestMapping("order/flight/change")
public class FlightChangeController extends BaseController{

    @Autowired
    private FlightChangeService flightChangeService;

    @RequestMapping("/index")
    public String flightChangeIndex(Map<String, Object> model,String orderId,String ctripUid, String tyUerid){
        FlightChangeDetailRequest request =new FlightChangeDetailRequest();
        request.setCtripUId(ctripUid);
        request.setTyUserId(tyUerid);
        request.setOperateType(1);
        request.setOrderNumber(orderId);
        VmFlightChangeCollect vmFlightChangeCollect = flightChangeService.getFlightChangeDetail(request);
        model.put("flightChange",vmFlightChangeCollect.getFlightChangeDetail());
        model.put("msgContent",vmFlightChangeCollect.getMsgConteng());
        model.put("vmRequest",request);
        model.put("ctripUId",ctripUid);
        model.put("tyUserId",tyUerid);
        model.put("ordernumber",orderId);
        model.put("datetimenow", DateUtils.format(new Date(),DateUtils.YMD_UNDERLINED));
        return "order/flight/change/change";
    }

    @PostMapping("/search")
    @ResponseBody
    public JsonResult searchChangeFlight(@RequestBody VmFlightSerach request)  {
        return flightChangeService.getRebookFlightList(request);
    }

    @RequestMapping("/pushchange")
    @ResponseBody
    public JsonResult pushFlightChange(@RequestBody VmRebookRequest request)  {
        request.setOperatorName(getEmpName());
        RebookResponseType response = flightChangeService.pushFlightChange(request);

        if(response !=null){
            if(response.getResultCode() == 1){
                return JsonResult.ok().putData("msg",response.getResultMessage());
            }else{
                return JsonResult.fail().putData("msg",response.getResultMessage());
            }

        }else{
            return JsonResult.fail().putData("msg","未知错误");
        }
    }
    
}
