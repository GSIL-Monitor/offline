package com.ctrip.train.kefu.system.offline.order.controller.train;

import com.ctrip.soa.train.javaxproductservice.v1.OrderAppendproductServiceInfo;
import com.ctrip.soa.train.javaxproductservice.v1.SearchProductByUidResponseType;
import com.ctrip.train.kefu.system.offline.common.controller.BaseController;
import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import com.ctrip.train.kefu.system.offline.order.service.MemberCardService;
import com.ctrip.train.kefu.system.offline.order.vm.VmMemberCardRights;
import common.log.CLogger;
import common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by jian_ji on 2018/6/29.
 */

@Controller
@RequestMapping("/train/order/")
public class TrainMemberController extends BaseController {

    @Autowired MemberCardService memberCardService;

    @GetMapping("/membercarddetail")
    public  String memberCardDetail (Map<String, Object> model,String uid,Long orderflag,String cardcode){
        boolean ismemberCard = false;
        StringBuilder sbUseInfo = new StringBuilder();
        List<String> listOrderid = new ArrayList<String>();
        List<VmMemberCardRights> vm = memberCardService.memberCardGetUserRightsByUid(uid, cardcode);
        ismemberCard = memberCardService.memberCardGetUserRights(uid, cardcode, vm, listOrderid,sbUseInfo);
        //String useInfo  = StringUtils.join(vm.stream().filter(T-> T.getUseCount() > 0).map(VmMemberCardRights::getRightsName).collect(Collectors.toList()),",");
        model.put("memberCardlist", vm);
        model.put("ismemberCard", ismemberCard);
        model.put("listorderid", listOrderid);
        model.put("useInfo", sbUseInfo.toString());
        return "order/train/membercarddetail";
    }

    @PostMapping("/cancelmembercard")
    @ResponseBody
    public JsonResult cancelMemberCard(Integer productid, String uid, String cardcode,String orderid,String operator){
        try {
            operator = URLDecoder.decode(operator, "UTF-8");
            List<String> listOrderid = new ArrayList<String>();
            StringBuilder sbUseInfo = new StringBuilder();
            List<VmMemberCardRights> vm = memberCardService.memberCardGetUserRightsByUid(uid, cardcode);
            if (cardcode != null && !cardcode.isEmpty() && uid != null && !uid.isEmpty() && memberCardService.memberCardGetUserRights(uid, cardcode, vm, listOrderid,sbUseInfo)) {
                SearchProductByUidResponseType response = memberCardService.searchProductByUid(uid);
                if (response != null && response.getAppendList() != null && response.getAppendList().size() > 0) {
                    List<OrderAppendproductServiceInfo> appendTemp = response.getAppendList().stream()
                            .filter(q -> q.getAppendState() == 40 || q.getAppendState() == 70)
                            .collect(Collectors.toList());
                    if (appendTemp != null && appendTemp.size() > 0) {
                        String strResult = memberCardService.cancelMemberCard(appendTemp.get(0).getProductId(), operator, appendTemp.get(0).getPartnerOrderId(), orderid);
                        return JsonResult.ok().putData("msg", strResult);
                    }
                }
                return JsonResult.ok().putData("msg", "退会员失败:未能找到会员关联订单号");

            }
        }catch(Exception ex){
            CLogger.error("cancelMemberCard",ex);
        }
        return JsonResult.fail().putData("msg", "退会员失败");
    }
}
