package com.ctrip.train.kefu.system.offline.order.controller.train;

import com.ctrip.train.kefu.system.offline.common.controller.BaseController;
import com.ctrip.train.kefu.system.offline.common.utils.AESUtil;
import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import com.ctrip.train.kefu.system.offline.common.utils.TrainGrabLevel;
import com.ctrip.train.kefu.system.offline.order.domain.train.DmTrainOrderDetail;
import com.ctrip.train.kefu.system.offline.order.service.ScmNoteService;
import com.ctrip.train.kefu.system.offline.order.service.TrainOrderService;
import com.ctrip.train.kefu.system.offline.order.service.impl.train.dmService.*;
import com.ctrip.train.kefu.system.offline.order.vm.train.order.*;
import common.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order/train")
public class TrainOrderController extends BaseController {

    @Autowired
    private DmTrainOrderAppendService dmTrainOrderAppendService;

    @Autowired
    private DmTrainOrderBasicInfoService dmTrainOrderBasicInfoService;

    @Autowired
    private DmTrainOrderGrabTaskService dmTrainOrderGrabTaskService;

    @Autowired
    private DmTrainOrderOperateService dmTrainOrderOperateService;

    @Autowired
    private DmTrainOrderTicketsInfoService dmTrainOrderTicketsInfoService;

    @Autowired
    private TrainOrderService trainOrderServiceImpl ;
    @Autowired
    private ScmNoteService ScmNoteServiceImpl;
    @RequestMapping("/detail")
    public String detail(Map<String, Object> model, String orderNumber) {
        DmTrainOrderDetail orderDetailInfo = trainOrderServiceImpl.getOrderDetail(orderNumber);

        model.put("eid",getEid());
        //右侧菜单栏
        model.put("operate", dmTrainOrderOperateService.getVmOrderOperate(orderDetailInfo,getPermission()));
        //车票相关信息
        model.put("tickets", dmTrainOrderTicketsInfoService.getVmOrderTicketsInfo(orderDetailInfo));
        VmTrainOrderBasicInfo basic=dmTrainOrderBasicInfoService.getVmOrderBasicInfo(orderDetailInfo,getEid());
        model.put("basic",basic);
        VmTrainOrderGrabTask temp=dmTrainOrderGrabTaskService.getVmOrderGrabTask(orderDetailInfo);
        int level=trainOrderServiceImpl.getTrainOrderPackageLevel(orderNumber).intValue();
        temp.setSpeedLevel(TrainGrabLevel.getGrabLevel(level,basic.getPartherName()));
        //抢票任务
        model.put("tasks", temp);
        VmTrainOrderAppend append= dmTrainOrderAppendService.getVmOrderAppend(orderDetailInfo);
        if(append!=null) {
            model.put("orderPriceSum", basic.getOrderPrice().add(append.getAppendPrice()));
        }
        model.put("append", append);
        model.put("loginfoList", dmTrainOrderBasicInfoService.getLogInfo(orderNumber));
        return "/order/train/detail";
    }

    @RequestMapping("/applyRefund")
    public String applyRefund(Map<String, Object> model, String orderNumber) {
        model.put("orderNumber",orderNumber);
        model.put("tickets", dmTrainOrderTicketsInfoService.getVmOrderTicketsInfo(trainOrderServiceImpl.getOrderDetail(orderNumber)));
        return "/order/train/applyRefund";
    }

    /**
     * 详情页面增加备注
     */
    @ResponseBody
    @RequestMapping(value = "/addRemarks", produces = "application/json", method = RequestMethod.POST)
    public JsonResult addRemarks(@RequestBody  VmResAddRemark request){
        if (ScmNoteServiceImpl.addRemarks(request.getOrderId(),request.getRemarks(),getEmpName())==1){
            return JsonResult.ok();
        }
        return JsonResult.fail();
    }

    /**
     *  获取12306帐号密码
     * @param username
     * @param orderId
     * @return
     */
    @PostMapping("/basic/searchPassword")
    @ResponseBody
    public JsonResult searchPassword(String username, String orderId) {
        String result = trainOrderServiceImpl.searchPassword(username, orderId, getOpUser());
        return JsonResult.ok().putData("msg", result);
    }
    /**
     *  查询附加产品信息
     * @param action
     * @param productID
     * @return
     */
    @PostMapping("/append/appendProductInfo")
    @ResponseBody
    public JsonResult appendProductInfo(String action, Integer productID) {
        String result = trainOrderServiceImpl.appendProductInfo(action, productID);
        return JsonResult.ok().putData("msg", result);
    }

    /**
     *  退优惠券套餐
     * @return
     */
    @PostMapping("/append/refundCoupon")
    @ResponseBody
    public JsonResult refundCoupon(String orderNumber,String partnerName,String couponId) {
        String result = trainOrderServiceImpl.refundCoupon(partnerName,orderNumber ,couponId ,getEid());
         return JsonResult.ok().putData("msg",result);
    }

    /**
     * 批量退默认加速包
     * @param orderNumber
     * @return
     */
    @PostMapping("/append/refundGrabProductId")
    @ResponseBody
    public JsonResult RefundGrabProductId(String orderNumber)
    {
        return JsonResult.ok().putData("msg",trainOrderServiceImpl.refundGrabProductId(orderNumber,getEid()));
    }

    /**
     * 退加速包
     * @param orderNumber
     * @return
     */
    @PostMapping("/append/refundGrabBags")
    @ResponseBody
    public JsonResult refundGrabBags(String orderNumber)
    {
        return JsonResult.ok().putData("msg",trainOrderServiceImpl.refundAllGrabBag(orderNumber,getEid()));
    }

    /**
     *  查经停
     * @return
     */
    @PostMapping("/searchS2S")
    @ResponseBody
    public JsonResult searchS2S(@RequestBody VmRequestSearchS2S request) {
        List<VmResponseS2S> responseS2S=trainOrderServiceImpl.searchS2S(request);
        if (responseS2S!=null&&responseS2S.size()>0)
            return JsonResult.ok().putData("response",responseS2S);
        return JsonResult.fail();
    }

    /**
     *  查车站地址
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/stationaddress", produces = "application/json", method = RequestMethod.POST)
    public JsonResult searchStationsAddress(@RequestBody VmRequestSearchS2S request) {
        return JsonResult.ok().putData("msg",trainOrderServiceImpl.getStationsAddress(request.getArriveStationName()));
    }

    /**
     * 删除火车票订单
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deletetrainorder", produces = "application/json", method = RequestMethod.POST)
    public JsonResult deleteTrainOrder(String orderNumber,String partnerName,String userAccount) {
        return JsonResult.ok().putData("msg",trainOrderServiceImpl.trainOrderDelete(orderNumber, getEmpName(),partnerName, userAccount));
    }

    /**
     *  查看抢票险状态
     * @return
     */
    @PostMapping("/append/queryinsuceance")
    @ResponseBody
    public JsonResult queryInsuceanceState(String orderNumber,String insuranceNos) {
        return JsonResult.ok().putData("msg",trainOrderServiceImpl.getInsuranceState(orderNumber,insuranceNos));
    }

    /**
     * 查看地址图片
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getImageUrl", produces = "application/json", method = RequestMethod.GET)
    public JsonResult getStationImageUrl(String orderNumber,String partnerName) {
        return JsonResult.ok().putData("msg",trainOrderServiceImpl.trainOrderDelete(orderNumber, getEmpName(),partnerName, getEid()));
    }

    /**
     *  取消订单
     * @return
     */
    @PostMapping("/tickets/cancelOrder")
    @ResponseBody
    public JsonResult cancelOrder(String orderNumber) {
        return JsonResult.ok().putData("msg",trainOrderServiceImpl.cancelOrder(orderNumber,getOpUser()));
    }
}
