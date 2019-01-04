package com.ctrip.train.kefu.system.offline.order.service.impl.train;

import com.alibaba.fastjson.JSON;
import com.ctrip.soa.train.javaxproductservice.v1.GetOrderInvoiceByPartnerOrderIdResponseType;
import com.ctrip.soa.train.javaxproductservice.v1.XpOrderInvoice;
import com.ctrip.soa.train.trainordercentreservice.offline.v1.*;
import com.ctrip.soa.train.xproductservice.v1.ApplyCommonEleterInvoiceRequestType;
import com.ctrip.soa.train.xproductservice.v1.ApplyCommonEleterInvoiceResponseType;
import com.ctrip.soa.train.xproductservice.v1.ElecInvoiceDTO;
import com.ctrip.train.kefu.system.client.offline.common.ShenDunContract;
import com.ctrip.train.kefu.system.client.offline.train.OrderContract;
import com.ctrip.train.kefu.system.client.pojo.train.RepairInvoiceRequestPojo;
import com.ctrip.train.kefu.system.offline.order.enums.train.EleterInvoiceEnum;
import com.ctrip.train.kefu.system.offline.order.enums.train.InvoiceTypeEnum;
import com.ctrip.train.kefu.system.offline.order.enums.train.XProductTypeEnum;
import com.ctrip.train.kefu.system.offline.order.service.InvoiceService;
import com.ctrip.train.kefu.system.offline.order.vm.VmDelivery;
import com.ctrip.train.kefu.system.offline.order.vm.VmInvoiceDetail;
import com.ctrip.train.kefu.system.offline.order.vm.VmInvoiceInfo;
import com.ctrip.train.kefu.system.offline.order.vm.VmInvoiceOrderInfo;
import common.log.CLogger;
import common.util.DateUtils;
import common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tempuri.livechatservice.CoreInfoKeyType;
import org.tempuri.livechatservice.SingleEncryptRequestType;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by jian_ji on 2018/7/2.
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private OrderContract orderContract;

    @Autowired
    private ShenDunContract shenDunContract;

    /**
     * 开电子发票
     * @param vmInvoiceDetail  发票详情信息
     * @return
     * @throws Exception
     */
    public List<String> createInvoice(VmInvoiceDetail vmInvoiceDetail,String eid){
        List<String> msgList = new ArrayList<String>();   //因为循环跑发票接口，所以把错误日志放到一个list里面
        String superPartherName = "";
        if (vmInvoiceDetail.getOrderList().size() == 0) {
            msgList.add("没有勾选需要补寄的订单");
            return msgList;
        }
//        if (TitleRBCorp.Checked && isNullOrEmpty(vmInvoiceDetail.getPayer()))
//        {
//            msg = "抬头类型为公司时纳税人识别码不能为空";
//        }else
        if (isNullOrEmpty(vmInvoiceDetail.getTitle()) || isNullOrEmpty(vmInvoiceDetail.getMail())) {
            msgList.add("抬头且邮箱不能为空");
            return msgList;
        } else if (!checkMailAlert(vmInvoiceDetail.getMail())) {
            msgList.add("邮箱格式不正确！");
            return msgList;
        }

        for (VmInvoiceOrderInfo item : vmInvoiceDetail.getOrderList()) {

            //查询发票状态，有发票的需要红冲
            GetOrderInvoiceByPartnerOrderIdResponseType response = orderContract.getOrderInvoiceByPartnerOrderId(item.getOrderNumber(), 0);
            if (response != null && response.getRetCode() == 0 && checkInvoiceType(response.getInvoiceList(),item.getInvoiceType())) {
                orderContract.applyRedEleterInvoice(item.getOrderNumber());   //开过电子发票的需要先红冲
            }

            if((item.getInvoiceType() & InvoiceTypeEnum.InvoiceMemberCardType.getCode()) == InvoiceTypeEnum.InvoiceMemberCardType.getCode()){
                superPartherName = item.getPartnerName();
            }

            if (InvoiceTypeEnum.InvoiceMemberCardType.getCode().equals(item.getInvoiceType())) {   //如果只勾选了超级会员，直接下个循环
                continue;
            }

            StringBuilder msgInfo = new StringBuilder();
            RepairInvoiceRequestPojo request = new RepairInvoiceRequestPojo();
            vmTranform(request,vmInvoiceDetail,item,eid);
            //X产品
            repairInvoice(msgInfo,request,item.getInvoiceType(),InvoiceTypeEnum.InvoiceXproductType,"11");

            //正常保险
            repairInvoice(msgInfo,request,item.getInvoiceType(),InvoiceTypeEnum.InvoiceInsuranceType,"1");

            //抢票险
            repairInvoice(msgInfo,request,item.getInvoiceType(),InvoiceTypeEnum.InvoiceRobType,"6");

            if (msgInfo.length() > 0) {
                msgInfo.insert(0,item.getOrderNumber());
                msgList.add(String.format("%s电子发票申请成功",msgInfo.toString()));
            }
        }

        //超级会员电子发票
        if(!isNullOrEmpty(vmInvoiceDetail.getSuperOrderNumber())){
            boolean result = createMemberCardInvoice(vmInvoiceDetail,eid,superPartherName);
            if(result){
                msgList.add("超级会员电子发票申请成功;");
            }
        }

        CLogger.info("开具电子发票", String.format("%s,操作人:%s", msgList, eid));
        return msgList;
    }

    private void repairInvoice(StringBuilder msgInfo,RepairInvoiceRequestPojo request,int invoiceType,InvoiceTypeEnum invoiceTypeenum,String productType){
        if((invoiceType & invoiceTypeenum.getCode()) == invoiceTypeenum.getCode()){
            request.setProductType(productType);
            if(orderContract.repairInvoice(request)){
                msgInfo.append(String.format("%s,",invoiceTypeenum.getName()));
            }
        }
    }

    /**
     * 开超级会员电子发票
     * @returnString
     * @throws Exception
     */
    private boolean createMemberCardInvoice(VmInvoiceDetail vmInvoiceDetail,String eid,String superPartherName){
        Map<String,String> log = new HashMap<String,String>();
        log.put("OrderNumber",vmInvoiceDetail.getSuperOrderNumber());
        final int SuperPrice = 88;
        final int SuperProductType =12;
        try
        {
            try {
                BigDecimal orderPrice = new BigDecimal(SuperPrice);
                String ticketDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
                if (!vmInvoiceDetail.getSuperProductId().equals("-110")) {
                    OfflineOrderDetailResponseType orderResponse = orderContract.getOrderDedetail(vmInvoiceDetail.getSuperOrderNumber());
                    if (orderResponse != null) {
                        try {
                            for (OfflinePassengerInfo item : orderResponse.getOfflinePassengers()) {
                                orderPrice = orderPrice.add(new BigDecimal(item.getOrderTicketPrice()));
                            }
                            ticketDate = orderResponse.getOfflinePassengers().stream()   //取最大值
                                    .sorted(Comparator.comparing(OfflinePassengerInfo::getTicketTime))
                                    .collect(Collectors.toList()).get(0).getTicketTime();
                        } catch (Exception ex) {
                            CLogger.error("超级会员开电子发票", ex, log);
                        }
                    }
                }
                ApplyCommonEleterInvoiceRequestType request = new ApplyCommonEleterInvoiceRequestType();
                request.setPartnerOrderId(vmInvoiceDetail.getSuperOrderNumber());
                request.setPartnerName(superPartherName);
                request.setBusinessType(0);
                ElecInvoiceDTO elecInvoiceDTO = new ElecInvoiceDTO();
                elecInvoiceDTO.setInvoiceTitle(vmInvoiceDetail.getTitle());
                elecInvoiceDTO.setTaxpayNumber(vmInvoiceDetail.getPayer());
                elecInvoiceDTO.setOpenBank(vmInvoiceDetail.getOpenBank());
                elecInvoiceDTO.setAccoutNumber(vmInvoiceDetail.getBankNumber());
                elecInvoiceDTO.setOrderPrice(orderPrice);
                elecInvoiceDTO.setXProductType(SuperProductType);
                elecInvoiceDTO.setTicketDate(ticketDate);
                elecInvoiceDTO.setProductName("超级会员");
                elecInvoiceDTO.setTotalCount(1);
                elecInvoiceDTO.setTotalAmount(new BigDecimal(SuperPrice));
                elecInvoiceDTO.setBuyerName(vmInvoiceDetail.getTitle());
                elecInvoiceDTO.setReceiveEmail(vmInvoiceDetail.getMail());
                elecInvoiceDTO.setUid(vmInvoiceDetail.getSuperuid());
                elecInvoiceDTO.setOperator(eid);
                List<ElecInvoiceDTO> elecInvoiceDTOList = new ArrayList<ElecInvoiceDTO>();
                elecInvoiceDTOList.add(elecInvoiceDTO);
                request.setInvoiceDTO(elecInvoiceDTOList);

                StringBuilder strLog = new StringBuilder();
                strLog.append(".........参数_");
                strLog.append(JSON.toJSON(request));
                ApplyCommonEleterInvoiceResponseType response = orderContract.getApplyCommonEleterInvoice(request);
                strLog.append(".........结果_");
                strLog.append(JSON.toJSON(response));
                CLogger.info("超级会员开电子发票", strLog.toString(), log);
                if (response.getRetCode() == 0) {
                    return true;
                } else {
                    CLogger.info("超级会员开电子发票失败", response.getMessage(), log);
                }
            } catch (Exception ex) {
                CLogger.error("超级会员开电子发票", ex.getMessage(), log);
            }
        }
        catch (Exception ex)
        {
            CLogger.error("超级会员开电子发票", ex, log);
        }
        return false;
    }

    public List<VmInvoiceOrderInfo> getOrderList(String mobile,String superVipOrderNumber,List<VmInvoiceInfo> vmInvoiceInfolist,String source){

        try {
            if(mobile.equals("13000000000")){
                return null;
            }

            final int TempPageSize =25;
            final int TempEMonth =2;
            final int TempSMonth =-3;
            //加一个电话号码的神盾加密
            List<OfflineOrderInfo> orderInfosList = new ArrayList<OfflineOrderInfo>();
            OfflineOrderListRequestType request = new OfflineOrderListRequestType();

            SingleEncryptRequestType shenDunrequest = new SingleEncryptRequestType();
            shenDunrequest.setStrNeedEncrypt(mobile);
            shenDunrequest.setKeyType(CoreInfoKeyType.Phone);
            mobile = shenDunContract.singleEncrypt(shenDunrequest);

            request.setContactMobile(mobile);
            request.setPageNo(1);
            request.setPageSize(0);
            OfflineOrderListResponseType uor = orderContract.getUserOrderListResponse(request);

            if (uor != null && uor.getOrderCount() > 0) {
                for (int i = 1; i <= Math.ceil(((double) uor.getOrderCount()) / TempPageSize); i++) {
                    request.setPageNo(i);
                    request.setPageSize(TempPageSize);
                    OfflineOrderListResponseType pageOrders = orderContract.getUserOrderListResponse(request);
                    orderInfosList.addAll(pageOrders.getOfflineOrders());
                }
            }

            orderInfosList = orderInfosList.stream()   //此处参考offline的查询页面，但是把只取有效订单的逻辑拿掉，因为已取消的订单也有可能有附属产品
                    .filter(q -> (DateUtils.parseDate(q.getOfflineTicketItems().get(0).getTicketTime()).compareTo(DateUtils.addMonths(new Date(), TempEMonth)) == -1
                            && DateUtils.parseDate(q.getOfflineTicketItems().get(0).getTicketTime()).compareTo(DateUtils.addMonths(new Date(), TempSMonth)) != -1))
                    .collect(Collectors.toList());

            if (source != null && source.equals("outpagesource")) {
                orderInfosList = orderInfosList.stream()   //此处参考offline的查询页面，但是把只取有效订单的逻辑拿掉，因为已取消的订单也有可能有附属产品
                        .filter(q -> !q.getPartnerName().equals("Ctrip.Train"))
                        .collect(Collectors.toList());
            }
            orderContract.getUserOrderListResponse(request);
            List<VmInvoiceOrderInfo> vm = new ArrayList<VmInvoiceOrderInfo>();
            for (OfflineOrderInfo item : orderInfosList) {
                VmInvoiceOrderInfo tempInfo = new VmInvoiceOrderInfo();
                tempInfo.setOrderNumber(item.getOrderNumber());
                tempInfo.setTicketTime(item.getOfflineTicketItems().get(0).getTicketTime());
                tempInfo.setFromStationName(item.getOfflineTicketItems().get(0).getFromStationName());
                tempInfo.setToStationName(item.getOfflineTicketItems().get(0).getToStationName());
                tempInfo.setPartnerName(item.getPartnerName());
                String btnHtml = getEInvoicebtnHtml(item.getOrderNumber(),superVipOrderNumber,vmInvoiceInfolist);
                tempInfo.setInvoiceTypebtn(btnHtml);    //显示的按钮
                if(item.getOrderNumber().equals(superVipOrderNumber) || btnHtml.length() > 0) {
                    vm.add(tempInfo);
                }
            }
            return vm;
        } catch (Exception ex) {
            return null;
        }
    }

    public VmDelivery getDelivery(String orderId){
        VmDelivery deliveryEntity = new VmDelivery();
        OfflineOrderDetailResponseType response = orderContract.getOrderDedetail(orderId);
        if(response != null &&response.getOfflineDeliver() != null){
            deliveryEntity.setInvoiceMailAddress(StringUtils.defaultString(response.getOfflineDeliver().getInsuranceUserAddress()).replace("\n",""));
            deliveryEntity.setZipCode(StringUtils.defaultString(response.getOfflineDeliver().getInsuranceUserZipCode()));
            deliveryEntity.setAddresseeName(StringUtils.defaultString(response.getOfflineDeliver().getInsuranceUserName()));
            deliveryEntity.setReceiverMobile(StringUtils.defaultString(response.getOfflineDeliver().getInsuranceUserTel()));
        }
        return deliveryEntity;
    }

    /**
     * 获取发票订单详情
     *
     * @return
     */
    public List<VmInvoiceInfo> getInvoiceList(GetOrderInvoiceByPartnerOrderIdResponseType response) {
        List<VmInvoiceInfo> invoicelist = new ArrayList<VmInvoiceInfo>();
        try {
            if (response != null && response.getRetCode() == 0) {
                List<XpOrderInvoice> tempinvoicelist = new ArrayList<>();

                response.getInvoiceList().stream().sorted(Comparator.comparing(XpOrderInvoice::getInvDate).reversed()).collect(Collectors.toList()).forEach(t -> {
                    if (t.getPrintState() == EleterInvoiceEnum.WaitOpenInvoice.getCode()) {  //申请状态下，InvDate 空的，
                        tempinvoicelist.add(0, t);
                    } else {
                        tempinvoicelist.add(t);
                    }
                });

                for(XpOrderInvoice item:tempinvoicelist){
                    VmInvoiceInfo invoiceinfo = new VmInvoiceInfo();
                    StringBuilder paths = new StringBuilder("http://trains.ctrip.com/OrderService/ShowEleterInvoice2.aspx?path=");
                    invoiceinfo.setInvoiceState(EleterInvoiceEnum.convertVendor(item.getPrintState()).getName());
                    invoiceinfo.setInvoiceTime((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(item.getInvDate().getTime()));
                    invoiceinfo.setInvoiceType(XProductTypeEnum.convertVendor(item.getXProductType()).getName());
                    invoiceinfo.setOperator(item.getOperator());
                    invoiceinfo.setPdfUrl(isNullOrEmpty(item.getPdfUrl()) ? "" : paths.append(item.getPdfUrl()).toString());
                    invoiceinfo.setTotalAmount(item.getTotalAmount());
                    invoiceinfo.setInvoiceTitle(item.getBuyerName());
                    invoiceinfo.setReceiveEmail(item.getReceiveEmail());
                    invoiceinfo.setTaxpayNumber(item.getTaxpayNumber());
                    invoiceinfo.setReceiveAddress(item.getReceiveAddress());
                    invoiceinfo.setReceiverMobile(item.getReceiverMobile());
                    invoiceinfo.setOpenBank(item.getOpenBank());
                    invoiceinfo.setOrderNumber(item.getPartnerOrderId());
                 invoicelist.add(invoiceinfo);
                }
                return invoicelist;
            }
        } catch (Exception ex) {
            CLogger.error("getInvoiceList",ex);
        }
        return null;
    }

    /**
     * 判断附属产品
     * @param orderId
     * @return
     */
    private String getEInvoicebtnHtml(String orderId,String superVipOrderNumber,List<VmInvoiceInfo> vmInvoiceInfolist)
    {
        StringBuilder btnHtml = new StringBuilder();
        Map<Integer,Integer> invoiceStateList = getInvoiceState(orderId,vmInvoiceInfolist);  //获取该订单所有发票的状态
        OfflineOrderDetailResponseType orderResponse = orderContract.getOrderDedetail(orderId);

        if(orderResponse == null || orderResponse.getOrderDetail() == null){
            return "";
        }
        if(!(orderResponse.getOrderDetail().getOrderFrom().equals("tieyou") || orderResponse.getOrderDetail().getOrderFrom().equals("zhixing") || orderResponse.getOrderDetail().getOrderFrom().equals("Ctrip.Train"))){
            return "";
        }

        List<CouponModel> couponInfoList = orderResponse.getCoupons().stream().filter(q -> !q.getCouponType().equals("10"))
                .collect(Collectors.toList());

        boolean orderAppendStatus = ((orderResponse.getOrderappendproducts() != null && orderResponse.getOrderappendproducts().size()>0
                && orderResponse.getOrderappendproducts().stream().anyMatch(T->T.getIsFree() != 1
                && !T.getProductSubtype().equals("CtripVip")
                && T.getProductPrice().compareTo(BigDecimal.ZERO) == 1 && (T.getAppendState() == 1 || T.getAppendState() == 40))));
        boolean couponStatus = (couponInfoList != null && couponInfoList.size() > 0 && couponInfoList.stream().anyMatch(T -> T.getCouponState() == 35));

        if (orderAppendStatus || couponStatus ) //只取35已发放的
        {
            btnHtml.append(String.format("<input %s type=\"checkbox\" name=\"chinvoice\" value=\"1\"/><span class=\"sptitle\">附属产品</span> %s",getInvoicestate(invoiceStateList.get(11)),getInvoicename(invoiceStateList.get(11))));//附加产品
        }

        if(orderResponse.getOfflinePassengers() != null && orderResponse.getOfflinePassengers().size() >0){
            for(OfflinePassengerInfo item : orderResponse.getOfflinePassengers()){
                if (item.getOfflineInsurances() != null && item.getOfflineInsurances().size() > 0)
                {
                    if(item.getOfflineInsurances().stream().anyMatch(t->t.getInsuranceClass() == 0 && t.getInsuranceState().equals("1"))){   //普通保险
                        btnHtml.append(String.format("<input %s type=\"checkbox\" name=\"chinvoice\" value=\"2\"/><span class=\"sptitle\">正常保险</span> %s",getInvoicestate(invoiceStateList.get(1)),getInvoicename(invoiceStateList.get(1))));
                    }

                    if(item.getOfflineInsurances().stream().anyMatch(t->t.getInsuranceClass() == 1 && t.getInsuranceState().equals("1"))){   //抢票保险
                        btnHtml.append(String.format("<input %s type=\"checkbox\" name=\"chinvoice\" value=\"8\"/><span class=\"sptitle\">抢票险</span> %s",getInvoicestate(invoiceStateList.get(6)),getInvoicename(invoiceStateList.get(6))));
                    }
                }
            }
        }
        if(superVipOrderNumber!= null && orderId.equals(superVipOrderNumber)){
            btnHtml.append(String.format("<input %s type=\"checkbox\" id=\"chinvoicesuper\" name=\"chinvoice\" value=\"4\"/><span class=\"sptitle\">超级会员</span> %s",getInvoicestate(invoiceStateList.get(12)),getInvoicename(invoiceStateList.get(12))));
        }
        return btnHtml.toString();
    }

    private boolean checkInvoiceType(List<XpOrderInvoice> invoiceList, int invoiceType) {
        for (XpOrderInvoice item : invoiceList) {
            switch (item.getXProductType()) {
                case 1:    //保险
                    if((invoiceType & InvoiceTypeEnum.InvoiceInsuranceType.getCode()) == InvoiceTypeEnum.InvoiceInsuranceType.getCode()){
                        return true;
                    }
                    break;
                case 6:    //抢票险
                    if((invoiceType & InvoiceTypeEnum.InvoiceRobType.getCode()) == InvoiceTypeEnum.InvoiceRobType.getCode()){
                        return true;
                    }
                    break;
                case 11:    //附属产品
                    if((invoiceType & InvoiceTypeEnum.InvoiceXproductType.getCode()) == InvoiceTypeEnum.InvoiceXproductType.getCode()){
                        return true;
                    }
                    break;
                case 12:    //超级会员
                    if((invoiceType & InvoiceTypeEnum.InvoiceMemberCardType.getCode()) == InvoiceTypeEnum.InvoiceMemberCardType.getCode()){
                        return true;
                    }
                    break;
                default:
                    break;
            }
        }
        return false;
    }

    /**
     * 加载发票状态，用于显示
     * @param orderId
     * @return
     */
    private Map<Integer,Integer> getInvoiceState(String orderId,List<VmInvoiceInfo> vmInvoiceInfolist){
        Map<Integer,Integer> mapInvoiceState = new HashMap<Integer,Integer>();
        mapInvoiceState.put(XProductTypeEnum.XInsurance.getCode(),0);
        mapInvoiceState.put(XProductTypeEnum.XQiangPiaoInsurance.getCode(),0);
        mapInvoiceState.put(XProductTypeEnum.XProductElecteric.getCode(),0);
        mapInvoiceState.put(XProductTypeEnum.XProductCtripVip.getCode(),0);
         //查询发票状态，
        GetOrderInvoiceByPartnerOrderIdResponseType invoiceresponse = orderContract.getOrderInvoiceByPartnerOrderId(orderId, 0);

        List<VmInvoiceInfo> tempInvoiceInfo = getInvoiceList(invoiceresponse);
        if (tempInvoiceInfo != null && tempInvoiceInfo.size() > 0) {
            vmInvoiceInfolist.addAll(tempInvoiceInfo);
        }

        if (invoiceresponse != null && invoiceresponse.getRetCode() == 0 && invoiceresponse.getInvoiceList() != null) {
            List<XpOrderInvoice> tempList = invoiceresponse.getInvoiceList().stream()
                    .sorted(Comparator.comparing(XpOrderInvoice::getInvDate))
                    .collect(Collectors.toList());

            for (XpOrderInvoice item : tempList) {
                switch (item.getXProductType()) {
                    case 1:    //保险
                            mapInvoiceState.put(XProductTypeEnum.XInsurance.getCode(),item.getPrintState());
                        break;
                    case 6:    //抢票险
                            mapInvoiceState.put(XProductTypeEnum.XQiangPiaoInsurance.getCode(),item.getPrintState());
                        break;
                    case 11:    //附属产品
                            mapInvoiceState.put(XProductTypeEnum.XProductElecteric.getCode(),item.getPrintState());
                        break;
                    case 12:    //超级会员
                            mapInvoiceState.put(XProductTypeEnum.XProductCtripVip.getCode(), item.getPrintState());
                        break;
                    default:
                        break;
                }
            }

            List<Integer> tempListType =Arrays.asList(1,6,11,12);    //因为申请状态没有 InvDate ,无法排序，所以有申请状态的，就算这个发票在申请中
            for(Integer temp : tempListType) {
                if(tempList.stream().anyMatch(T->EleterInvoiceEnum.WaitOpenInvoice.getCode().equals(T.getPrintState()) && temp.equals(T.getXProductType()))){
                    mapInvoiceState.put(temp,EleterInvoiceEnum.WaitOpenInvoice.getCode());
                }
            }
        }
        return mapInvoiceState;
    }

    /**
     * 实体转化
     */
    private void vmTranform(RepairInvoiceRequestPojo request,VmInvoiceDetail vmInvoiceDetail,VmInvoiceOrderInfo item,String eid){
        request.setName("");
        request.setInvoiceType(3);
        request.setMobile(vmInvoiceDetail.getTel());
        request.setEmail(vmInvoiceDetail.getMail());
        request.setZip("");
        request.setAddress(vmInvoiceDetail.getAddress());
        request.setInvoiceTitle(vmInvoiceDetail.getTitle());
        request.setPayerNumber(vmInvoiceDetail.getPayer());
        request.setOpenBank(vmInvoiceDetail.getOpenBank());
        request.setBankNumber(vmInvoiceDetail.getBankNumber());
        request.setOperatorName(eid);
        request.setOrderNumber(item.getOrderNumber());
        request.setPartnerName(item.getPartnerName());
    }

    /**
     * 读取发票的状态值
     * @param printState
     * @return
     */
    private String getInvoicename(Integer printState){
        String invoiceName = "";
        if(printState!=null && printState > 0){
            invoiceName= String.format("<span style=\"color:red;\">(%s)</span>",EleterInvoiceEnum.convertVendor(printState).getName());
        }
        return invoiceName;
    }

    /**
     * 根据状态设置按钮 只读 属性
     * @param printState
     * @return
     */
    private String getInvoicestate(Integer printState) {
        String invoiceState = "";
//        if (printState.equals(EleterInvoiceEnum.WaitOpenInvoice.getCode())) {   //等待开票状态也让他可以申请，相当于修改开票信息
//            invoiceState = "disabled=\"disabled\"";
//        }
        return invoiceState;
    }

    private boolean checkMailAlert(String mail)
    {
        if (mail == null)
            return false;
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(mail);
        return m.matches();
    }

    private  boolean isNullOrEmpty(String param) {
        return param == null || param.trim().length() == 0;
    }
}
