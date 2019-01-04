package com.ctrip.train.kefu.system.offline.two.controller;

import com.ctrip.train.kefu.system.offline.common.controller.BaseController;
import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import com.ctrip.train.kefu.system.offline.two.service.TwoTaskService;
import com.ctrip.train.kefu.system.offline.two.service.TwoTripService;
import com.ctrip.train.kefu.system.offline.two.vm.VMS2S;
import com.ctrip.train.kefu.system.offline.two.vm.VMTwo;
import com.ctrip.train.ticketagent.service.client.UpdateDeliveryOrderForSecPushResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/two")
public class TwoController extends BaseController {


    @Autowired
    private TwoTripService twoTripService;

    @Autowired
    private TwoTaskService twoTaskService;


    @GetMapping("/personal")
    public  String personal (Map<String, Object> model,String orderId)   {

        VMTwo vmTwo=twoTripService.getOfflineOrderDetailByOrderId(orderId);
        model.put("contactAddress",vmTwo.getContactAddress());
        model.put("contactMobile",vmTwo.getContactMobile());
        model.put("contactName",vmTwo.getContactName());
        model.put("from",vmTwo.getFromCity());
        model.put("orderId",vmTwo.getOrderId());
        model.put("searchS2Ss",vmTwo.getSearchS2S());
        model.put("searchS2SAfter",vmTwo.getSearchS2SAfter());
        model.put("searchS2SBeform",vmTwo.getSearchS2Sbefore());
        model.put("searchS2SNums",vmTwo.getSearchS2SNum());
        model.put("seatNo",vmTwo.getSeatNo());
        model.put("ticketNum",vmTwo.getTicketNum());//车票张数
        model.put("ticketTime",vmTwo.getTicketTime());
        model.put("to",vmTwo.getToCity());
        model.put("TrainNumber",vmTwo.getTrainNumber());
        model.put("checkTrains",vmTwo.getCheckTrain());
        model.put("partnerOrderId",vmTwo.getPartnerOrderId());
        model.put("partnerName",vmTwo.getPartnerName());

        model.put("date",vmTwo.getDate());
        return "/two/personal";
    }

    /**
     * 接受推荐
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/tryTwoPush")
    @ResponseBody
    public JsonResult tryTwoPush(@RequestBody Map<String,String> request) {
        request.put("operator",getEmpName());
        request.put("operatorNum",getEid());
        UpdateDeliveryOrderForSecPushResponse responseType=twoTripService.tryTwoPush(request);
        return JsonResult.ok().putData("response",responseType);
    }

    @PostMapping("/recommendCheck")
    @ResponseBody
    public JsonResult recommendCheck(@RequestBody Map<String,String> requestType)   {
        VMS2S responseType = twoTripService.searchS2S(requestType);
        return JsonResult.ok().putData("response",responseType);
    }

    /**
     * 根据日期站站查询
     * @param requestType
     * @return
     * @throws Exception
     */
    @PostMapping("/queryS2SByDate")
    @ResponseBody
    public JsonResult queryS2SByDate (@RequestBody Map requestType) {
        VMTwo responseType = twoTripService.queryTrainsByDate(requestType);
        return JsonResult.ok().putData("response",responseType);
    }
    /**
     * 无票更改，取消推荐
     * @param requestType
     * @return
     * @throws Exception
     */
    @PostMapping("/twoPushNoTicket")
    @ResponseBody
    public JsonResult twoPushNoTicket (@RequestBody Map<String,String> requestType) {
        requestType.put("operator",getEmpName());
        requestType.put("operatorNum",getEid());
        return JsonResult.ok().putData("response",twoTripService.twoPushNoTicket(requestType));
    }
    /**
     * 根据订单号查询余票信息
     * @param requestType
     * @return
     * @throws Exception
     */
    @PostMapping("/CheckTrainTicket")
    @ResponseBody
    public JsonResult checkTrainTicket(@RequestBody Map requestType) {
        return JsonResult.ok().putData("response",twoTripService.queryticketLeft(requestType));
    }

    /**
     * 增加余票查询任务
     * @param requestType
     * @return
     * @throws Exception
     */
    @PostMapping("/addCheckTrain")
    @ResponseBody
    public JsonResult addCheckTrain (@RequestBody Map requestType) {
        return JsonResult.ok().putData("response",twoTripService.addTraincheck(requestType));
    }
    /**
     * 暂存订单
     * @param requestType
     * @return
     * @throws Exception
     */
    @PostMapping("/cachingOrder")
    @ResponseBody
    public JsonResult cachingOrder(@RequestBody Map<String,Object> requestType) {

        requestType.put("reasonType","设置订单暂缓");
        requestType.put("actionName","设置订单暂存");
        requestType.put("operator",getEmpName());
        requestType.put("operatorNum",getEid());
        int s=twoTaskService.updateScmTwoTripTable(requestType);
        if(s==0){
            return JsonResult.ok().putData("response","未找到数据");
        }else {
            return JsonResult.ok().putData("response",s);
        }
    }
}
