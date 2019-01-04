package com.ctrip.train.kefu.system.offline.order.controller.train;

import com.ctrip.train.kefu.system.offline.common.controller.BaseController;
import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import com.ctrip.train.kefu.system.offline.order.enums.train.ButtonEnums;
import com.ctrip.train.kefu.system.offline.order.service.TrainOrderService;
import com.ctrip.train.kefu.system.offline.order.service.impl.train.dmService.DmTrainOrderTicketsInfoService;
import com.ctrip.train.kefu.system.offline.order.vm.train.order.VmTrainButton;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ButtonController extends BaseController{
    @Autowired
    private DmTrainOrderTicketsInfoService dmTrainOrderTicketsInfoService;

    @Autowired
    private TrainOrderService trainOrderServiceImpl ;

    @RequestMapping("/myOperate")
    public String importantNotice(Map<String,Object> moderl){


        return "/order/train/order/operate";
    }

    @RequestMapping("/evidence")
    public String evidence(Map<String,Object> moderl){
        return "/order/train/order/evidence";
    }

//    @RequestMapping("/trainsRefund")
//    public String trainsRefund(Map<String,Object> moder){
//
//        moder.put("","");
//        return "/order/train/refund/apply";
//    }
    @RequestMapping("/tickets")
    public String trainsTickets(Map<String,Object> model,String orderNumber){
        model.put("tickets", dmTrainOrderTicketsInfoService.getVmOrderTicketsInfo(trainOrderServiceImpl.getOrderDetail(orderNumber)));
        return "/order/train/order/tickets";
    }

}
