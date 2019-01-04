package com.ctrip.train.kefu.system.offline.order.manager;

import com.ctrip.train.kefu.system.offline.order.domain.train.DmTrainOrderDetail;
import com.ctrip.train.kefu.system.offline.order.enums.train.ButtonEnums;
import com.ctrip.train.kefu.system.offline.order.service.TrainScmTwoTaskService;
import com.ctrip.train.kefu.system.offline.order.service.impl.train.TrainScmTwoTaskServiceImpl;
import com.ctrip.train.kefu.system.offline.order.vm.train.order.VmTrainButton;
import common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ButtonManager {

    @Autowired
    private TrainScmTwoTaskService trainScmTwoTaskServiceImpl;

    public VmTrainButton trainButtonInfo(DmTrainOrderDetail dmTrainOrderDetail,List<String> permList){

        VmTrainButton vtb= new VmTrainButton();

        vtb.setIn12306(permList.contains(ButtonEnums.In12306.getCode()));

//        vtb.setImportantNotice(permList.contains(ButtonEnums.ImportantNotice.getCode()));

//        vtb.setExEvent(permList.contains(ButtonEnums.ExEvent.getCode()));

        vtb.setCertificate(permList.contains(ButtonEnums.Certificate.getCode()));

        vtb.setUnbindAccount(permList.contains(ButtonEnums.UnbindAccount.getCode()));

        vtb.setInvoice(permList.contains(ButtonEnums.Invoice.getCode()));

        vtb.setAddAdvance(permList.contains(ButtonEnums.AddAdvance.getCode()));

//        vtb.setNotice119(permList.contains(ButtonEnums.Notice119.getCode()));
        // TODO
//        vtb.setSaleNotice(permList.contains(ButtonEnums.SaleNotice.getCode())
//                            &&saleNotice("",""));
        vtb.setTwoPush(permList.contains(ButtonEnums.TwoPush.getCode())
                            &&twoPush("",""));
        return vtb;
    }

    //权限判断 + 订单铺位类型是否是卧铺
    private boolean twoPush(String partnerOrderId,String seatName){
        //查看二推任务表是否有数据
        int count=trainScmTwoTaskServiceImpl.searchScmTwoTaskCount(partnerOrderId);
        if ((seatName.contains("卧")||seatName.contains("铺"))&&count>0)
            return true;
        return false;
    }

    //权限判断 + 判断订单是不是119 and EnvenType=5 and NoticeState<=83 and OrderID
//    private boolean notice119(){
//        String code = ButtonEnums.Notice119.getCode();
//
//        return false;
//    }

    //代售点通知
    // 检查是否存在代售点
    // string.IsNullOrEmpty(response.AgentName) && response.OrderFrom != "qunarsync"
    private boolean saleNotice(String agentName,String orderFrom){
        if(StringUtils.isBlank(agentName)&&!orderFrom.equals("qunarsync"))
            return false;
        return true;
    }

    /**
     * 取消订单权限判断
     */
    public static String CancelOrderButton(String orderStatus, String orderId, String ticketType, String partnerName)
    {
        if (ticketType.equals("0"))//配送票
        {
            if (orderStatus.equals( "2") || orderStatus.equals( "1"))
            {

                return "<a href='javascript:void(0)' onclick='CancelOrderPage("+"{0}"+","+"{1}"+")' id='CancelOrder' style='text-decoration: underline; text-align: left; color: #fff; line-height: normal;float:right'>取消订单</a>&nbsp;&nbsp;  ";
            }
            else if (orderStatus.equals("3"))//已提取可以申请取消订单
            {
                return "<a href='javascript:void(0)' onclick='ApplyCancelOrder("+"{0}"+")' id='CancelOrder' style='text-decoration: underline; text-align: left; color: #fff; line-height: normal;font-size: 13px; float:right'>申请取消已提取订单</a>&nbsp;&nbsp;  ";
            }
            else
            {
                return "";
            }
        }else {
            if (orderStatus.equals("2") || orderStatus.equals("1") || orderStatus.equals("3")) {
                return "<a href='javascript:void(0)' onclick='CancelOrderPage("+"{0}"+","+"{1}"+")' id='CancelOrder' style='text-decoration: underline; text-align: left; color: #fff; line-height: normal;float:right'>取消订单</a>&nbsp;&nbsp;  ";
            }
            else {
                return "";
            }
        }
    }
}
