package com.ctrip.train.kefu.system.offline.order.service.impl.train;

import com.alibaba.fastjson.JSON;
import com.ctrip.market.membercard.MemberCardGetUserRightsResponseType;
import com.ctrip.market.soa.rightsInterestService.SelectUserRightsInterestResponseType;
//import com.ctrip.soa.train.xproductservice.v1.SearchProductByUidResponseType;
import com.ctrip.soa.train.javaxproductservice.v1.SearchProductByUidResponseType;
import com.ctrip.train.kefu.system.client.offline.train.MemberCardContract;
import com.ctrip.train.kefu.system.client.offline.train.OrderContract;
import com.ctrip.train.kefu.system.offline.order.service.MemberCardService;
import com.ctrip.train.kefu.system.offline.order.vm.VmMemberCardRights;
import common.log.CLogger;
import common.util.DateUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jian_ji on 2018/7/2.
 */
@Service
public class MemberCardServiceImpl implements MemberCardService {

    @Autowired
    private MemberCardContract mmberCardContract;

    @Autowired
    private OrderContract orderContract;

    public List<VmMemberCardRights> memberCardGetUserRightsByUid(String uid, String cardcode){
        List<VmMemberCardRights> vm = new ArrayList<VmMemberCardRights>();
        MemberCardGetUserRightsResponseType response = mmberCardContract.memberCardGetUserRights(uid,cardcode);
        if(response.getResultCode()!=0 || response.getUserRightsList() == null){
            return vm;
        }
        ModelMapper modelMapper = new ModelMapper();
        List<VmMemberCardRights> tempvm = modelMapper.map(response.getUserRightsList(),new TypeToken<List<VmMemberCardRights>>() {}.getType());
        if (tempvm != null && tempvm.size() > 0)
        {
            tempvm.forEach(t -> {
                if (t.getRightsBuCode() != null && t.getRightsBuCode().equals("TRAINS")) {
                    vm.add(0, t);
                } else {
                    vm.add(t);
                }
            });
        }
        return vm;

    }

    /**
     * 是否可以退会员
     * @param uid
     * @param cardcode
     * @return
     * @throws Exception
     */
    public boolean memberCardGetUserRights(String uid, String cardcode,List<VmMemberCardRights> listMemberCardRights,List<String> listOrderid,StringBuilder sbUseInfo){

        //20181010使用数量读取切回高博接口
        Boolean blrtn = true;
        SelectUserRightsInterestResponseType mresponse = mmberCardContract.selectUserRightsInterest(uid);
        if (mresponse != null && mresponse.getUserRightsList().size() > 0) {   //范国华接口
            for (int i = 0; i < mresponse.getUserRightsList().size(); i++) {
                if (mresponse.getUserRightsList().get(i).getProductType() == 13 && mresponse.getUserRightsList().get(i).getStatus() != 1) {
                    listOrderid.add(mresponse.getUserRightsList().get(i).getExtendMap().get("OrderID"));
                }
            }
        }
        if (listMemberCardRights != null && listMemberCardRights.size() > 0) {     //高博接口数据
            for (int i = 0; i < listMemberCardRights.size(); i++) {
                if (listMemberCardRights.get(i).useCount > 0) {
                    blrtn = false;
                    sbUseInfo.append(String.format("%s使用次数:%s,",listMemberCardRights.get(i).getRightsName(),listMemberCardRights.get(i).getUseCount()));
                }
            }
        }else{
            blrtn=false;
        }
        return blrtn;
    }

    public String cancelMemberCard(Integer productid, String operator, String orderid,String fromorderid){
        com.ctrip.soa.train.xproductservice.v1.RefundAppendProductResponseType response = orderContract.refundAppendProdunt(productid, orderid, operator);
        if (response != null) {
            Map<String,String> loggerInfo = new HashMap<String, String>();
            loggerInfo.put("response",JSON.toJSONString(response));
            CLogger.info("cancelMemberCard", orderid, loggerInfo);
            if(!orderid.equals(fromorderid)) {
                Map<String, String> maplog = new HashMap<String, String>();
//              map.put("partnerOrderId",orderid);//合作方订单号
                maplog.put("orderId", fromorderid);//订单号
                maplog.put("comment", ("退超级会员申请成功，关联订单：") + orderid);   //内容
                maplog.put("operator", operator);  //操作人
                maplog.put("reasonType", "退超级会员申请" );//操作原因
                maplog.put("actionName", "退超级会员申请");//日志操作类型
                orderContract.addOrderLog(maplog);
            }
            if (response.getRetCode() == 0) {
                return "退会员申请成功";
            } else {
                return "退会员申请失败：" + response.getMessage();
            }
        }
        return "退会员失败：响应订单中心接口失败！";
    }

    /**
     * 通过UID查询是超级会员的订单号
     * @return
     * @throws Exception
     */
    public SearchProductByUidResponseType searchProductByUid(String uid){
        return orderContract.searchProductByUid(uid);
    }
}
