package com.ctrip.train.kefu.system.offline.order.service.impl.train.dmService;

import com.alibaba.fastjson.JSON;
import com.ctrip.payment.router.dataserver.GetAllPaymentwayListResponse;
import com.ctrip.payment.router.dataserver.Paymentway;
import com.ctrip.payment.router.dataserver.PaymentwayCatalog;
import com.ctrip.payment.soa.cwallet.account.server.model.IsUserAuthedRsp;
import com.ctrip.soa.framework.soa.recommendservice.v1.DeliveryTypeEnum;
import com.ctrip.soa.framework.soa.recommendservice.v1.IsCanInsertRecommendResponseType;
import com.ctrip.soa.platform.accountsecurityservice.integrationgrade.v1.GetCurrentLevelResponse;
import com.ctrip.soa.train.ctripticketingservice.v1.QueryRefundTicketResultV20ResponseType;
import com.ctrip.soa.train.trainordercentreservice.offline.v1.OfflineOrderLogListResponseType;
import com.ctrip.soa.train.trainordercentreservice.v1.OrderDetailResponseType;
import com.ctrip.soa.train.trainordercentreservice.v1.OrderMasterInfo;
import com.ctrip.soa.train.trainordercentreservice.v1.PassengerModel;
import com.ctrip.sp.emergency.contract.GetFamilyNumberByPhoneResponseType;
import com.ctrip.train.kefu.system.client.offline.common.PaymentContract;
import com.ctrip.train.kefu.system.client.offline.common.PlatformContract;
import com.ctrip.train.kefu.system.client.offline.common.ShenDunContract;
import com.ctrip.train.kefu.system.client.offline.train.*;
import com.ctrip.train.kefu.system.offline.common.utils.AESUtil;
import com.ctrip.train.kefu.system.offline.common.utils.EncryptUtil;
import com.ctrip.train.kefu.system.offline.common.utils.OrderHelper;
import com.ctrip.train.kefu.system.offline.order.dao.ExtScmTwoTaskTableDao;
import com.ctrip.train.kefu.system.offline.order.domain.train.DmTrainOrderDetail;
import com.ctrip.train.kefu.system.offline.order.domain.train.order.*;
import com.ctrip.train.kefu.system.offline.order.enums.train.OrderStateEnum;
import com.ctrip.train.kefu.system.offline.order.vm.train.order.PayWayItemInfo;
import com.ctrip.train.kefu.system.offline.order.vm.train.order.VmTrainLog;
import com.ctrip.train.kefu.system.offline.order.vm.train.order.VmTrainOrderBasicInfo;
import com.ctrip.train.ticketagent.service.client.CounterTicketResponse;
import com.ctrip.train.tyusercore.service.client.GetUserVipDetailInfoForOfflineResponseType;
import common.log.CLogger;
import common.util.DateUtils;
import common.util.StringUtils;
import dao.ctrip.ctrainpps.dao.OfflinePartnerinfoDao;
import dao.ctrip.ctrainpps.entity.OfflinePartnerinfo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tempuri.livechatservice.CoreInfoKeyType;
import org.tempuri.livechatservice.SingleDecryptRequestType;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DmTrainOrderBasicInfoService {

    @Autowired
    private PlatformContract platformContract;

    @Autowired
    private TyUserCoreServiceContract tyUserCoreServiceContract;

    @Autowired
    private TrainTicketAgentServiceContract trainTicketAgentServiceContract;

    @Autowired
    private OfflinePartnerinfoDao offlinePartnerinfoDao;

    @Autowired
    private ExtScmTwoTaskTableDao extScmTwoTaskTableDao;

    @Autowired
    private OrderContract orderContract;


    private final String key="-!@QWaszx#^GDFUN" + DateUtils.format(new Date(),DateUtils.YMD);

    @Autowired
    private ShenDunContract shenDunContract;

    @Autowired
    private EmergencyServiceContract emergencyServiceContract;

    @Autowired
    private AccountSoa2ServerContract accountSoa2ServerContract;

    @Autowired
    private CtripticketingserviceContract ctripticketingserviceContract;

    @Autowired
    private IvrContract ivrContract;

    /**
     * 获取订单DoMain_基本信息
     */
    public DmTrainOrderBasicInfo getDmOrderBasicInfo(OrderDetailResponseType orderDetailResponseType) {
        DmTrainOrderBasicInfo dm = new DmTrainOrderBasicInfo();
        Map<String, String> dic = new HashMap<>();
        try {
            if (orderDetailResponseType != null) {
                OrderMasterInfo masterInfo=orderDetailResponseType.getOrderMaster();
                dic.put("orderNumber", masterInfo.getPartnerOrderId());
                dm.setActivityState(masterInfo.getActivityState());
                dm.setAddonProductsFlag(masterInfo.getAddonProductsFlag());
                dm.setAppVersion(masterInfo.getAppVersion());
                dm.setBookSourceID(masterInfo.getBookSourceID());
                dm.setChannelName(masterInfo.getChannelName());
                dm.setComment(masterInfo.getComment());
                dm.setCommunicationState(masterInfo.getCommunicationState());
                dm.setContactEmail(masterInfo.getContactEmail());
                dm.setContactMobile(masterInfo.getContactMobile());
                dm.setContactName(masterInfo.getContactName());
                dm.setCreateTime(masterInfo.getCreateTime());
                dm.setDataChangeLastTime(masterInfo.getDataChangeLastTime());
                dm.setElectricAccount(masterInfo.getElectricAccount());
                dm.setInsuranceInvoice(masterInfo.getInsuranceInvoice());
                dm.setIsQuickBooking(masterInfo.getIsQuickBooking());
                dm.setLanguage(masterInfo.getLanguage());
                dm.setLoginName(masterInfo.getLoginName());
                dm.setOrderFlag(masterInfo.getOrderFlag());
                dm.setOrderFlagV2(masterInfo.getOrderFlagV2());
                dm.setOrderFlagV3(masterInfo.getOrderFlagV3());
                dm.setOrderId(masterInfo.getOrderId());
                dm.setOrderPrice(masterInfo.getOrderPrice());
                dm.setOrderState(masterInfo.getOrderState());
                dm.setOrderTime(masterInfo.getOrderTime());
                dm.setOrderType(masterInfo.getOrderType());
                dm.setPartnerName(masterInfo.getPartnerName());
                dm.setPartnerOrderId(String.valueOf(masterInfo.getPartnerOrderId()));
                dm.setPayState(masterInfo.getPayState());
                dm.setReturnTicketState(masterInfo.getReturnTicketState());
                dm.setServerFrom(masterInfo.getServerFrom());
                dm.setOrderSource(masterInfo.getTerminalType());
                dm.setTicketCheckState(masterInfo.getTicketCheckState());
                dm.setTicketOffsetFee(masterInfo.getTicketOffsetFee());
                dm.setTicketType(masterInfo.getTicketType());
                dm.setUserID(masterInfo.getUserID());
                dm.setUserAccount(masterInfo.getElectricAccount());

                if(orderDetailResponseType.getTrainorderExtInfo() != null){
                    dm.setCountryCode(orderDetailResponseType.getTrainorderExtInfo().getCountryCode());
                    dm.setPackageName(orderDetailResponseType.getTrainorderExtInfo().getPackageName());
                    dm.setPackageInfo(orderDetailResponseType.getTrainorderExtInfo().getPackageInfo());
                }
                if(orderDetailResponseType.getPaymentInfo() != null){
                    dm.setPayType(orderDetailResponseType.getPaymentInfo().getPayChannel());
                    if(StringUtils.isNotEmpty(orderDetailResponseType.getPaymentInfo().getPayTime())) {
                        dm.setPayTime(DateUtils.parseDate(orderDetailResponseType.getPaymentInfo().getPayTime(),DateUtils.YMDHMS_UNDERLINED));
                    }
                }
            }
        } catch (Exception ex) {
            CLogger.error("获取订单DoMain_基本信息", ex, dic);
        }
        return dm;
    }

    /**
     * 获取
     */
    public List<DmPassengersInfo> getDmPassengersInfo(OrderDetailResponseType orderDetailResponseType) {
        List<DmPassengersInfo>  dm = new ArrayList<> ();
        Map<String, String> dic = new HashMap<>();
        dic.put("orderNumber", orderDetailResponseType.getOrderMaster().getPartnerOrderId());
        try {
            List<PassengerModel> passenferProducts=orderDetailResponseType.getPassengers();
            if (passenferProducts!=null&&passenferProducts.size()>0){
                ModelMapper modelMapper=new ModelMapper();
                dm= modelMapper.map(passenferProducts,new TypeToken<List<DmPassengersInfo>>() {}.getType());
            }
            return dm;
        } catch (Exception ex) {
            CLogger.error("获取订单DoMain_附加产品信息", ex, dic);
            return null;
        }
    }
    /**
     * 获取页面实体信息_基本信息
     */

    public VmTrainOrderBasicInfo getVmOrderBasicInfo(DmTrainOrderDetail dmTrainOrderDetail ,String eId) {

        VmTrainOrderBasicInfo vm = new VmTrainOrderBasicInfo();
        Map<String, String> dic = new HashMap<>();
        try {
            if (dmTrainOrderDetail != null) {
                dic.put("orderNumber", dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerOrderId());
                String uid = getUserId(dmTrainOrderDetail);
                Boolean isCoutnerTicket = getisCoutnerTicket(dmTrainOrderDetail);  //是否柜台票
                vm.setOrderId(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderId());
                vm.setPartnerOrderId(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerOrderId());
                getorderSource(dmTrainOrderDetail,vm);
                vm.setOrderType(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderType());   //车票类型 int
                vm.setTicketTypeStr(getTicketType(dmTrainOrderDetail,isCoutnerTicket));//车票类型Str
                vm.setUid(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getLoginName());
                vm.setUserLoginName(OrderHelper.SafeUserPassportNo(uid, false));  //预订用户  铁友app渠道展示userid，其他展示userloginName
                vm.setContactName(OrderHelper.SafeUserPassportNo(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getContactName(), false));  //联系人姓名
//
                vm.setPayType(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPayType());  //支付方式
                vm.setPaymentType(getPayInfo(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPayType()));  //支付信息
                vm.setMemberLevel(getUserLevel(dmTrainOrderDetail));   //会员等级
                vm.setContactMobSafe(OrderHelper.SafeUserPassportNo(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getContactMobile(),true)); //联系方式
                vm.setContactMob(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getContactMobile()); //联系方式


                vm.setContactMobShen(modileDec(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getContactMobile(),eId));
                vm.setContactEmail(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getContactEmail()); //邮箱地址
                vm.setPartherName(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerName());
                vm.setOutboundNum(OrderHelper.getOutboundNum(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerName()));
                vm.setCountryCode(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getCountryCode());
                vm.setUserAccount(getUserAccount(dmTrainOrderDetail.getDmTrainOrderTicketsInfo().getRealTickets()));
                vm.setOrderTime(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderTime());
                vm.setDiffPrice(getDiffPrice(dmTrainOrderDetail));
                vm.setOrderState(getOrderStateDet(dmTrainOrderDetail));
                vm.setOrderStateInt(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderState());
                vm.setOrderStateInfo(getOrderStateInfo(dmTrainOrderDetail));
                vm.setTwoRecommendType(getTwoRecommendType(dmTrainOrderDetail));
                vm.setIsNeedCancelTwoRec(getisNeedCancelTwoRec(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerOrderId()));
                vm.setOrderStateInfoRed(getOrderStateInfoRed(dmTrainOrderDetail));
                vm.setOrderPrice(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderPrice());
                vm.setCancelBtn(getCancelBtn(dmTrainOrderDetail.getDmTrainOrderBasicInfo()));
                vm.setIsemployeeFamily(isEmployeeFamily(vm.getContactMobShen()));
                vm.setIsCtripWalletUser(checkIsCtripAuthed(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerName(), dmTrainOrderDetail.getDmTrainOrderBasicInfo().getLoginName()));
                vm.setPayRemark(getPayMentRemark(dmTrainOrderDetail));
                vm.setIsIncludeCalculate(getIsIncludeCalculate(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerOrderId()));
                vm.setTicketType(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getTicketType());

                //改签token
                List<DmOrderTicketModel> ticketModels= dmTrainOrderDetail.getDmTrainOrderTicketsInfo().getOrderTicketModels();
                if (ticketModels!=null&&ticketModels.size()>0){
                    for (int i=0;i< ticketModels.size();i++){
                        String date = String.format("%s&%s&%s&%s&%s&%s",
                                vm.getOrderId(), vm.getUserLoginName(),
                                vm.getPartherName(),ticketModels.get(i).getOrderTicketId(),
                                eId, DateUtils.format(new Date()));
                        String token= AESUtil.encrypt(date,key);
                        if (i==0){
                            vm.setFirstSeqGQToken(java.net.URLEncoder.encode(token,   "utf-8"));
                        }else if (i==1){
                            vm.setSecondSeqGQToken(java.net.URLEncoder.encode(token,   "utf-8"));
                        }
                    }
                }
            }
        } catch (Exception ex) {
            CLogger.error("获取页面实体信息_基本信息", ex, dic);
        }
        return vm;
    }




    /**
     * 获取操作日志
     * @param orderId
     * @return
     */
    public List<VmTrainLog> getLogInfo(String orderId){
        List<VmTrainLog> logList = new ArrayList<>();
        OfflineOrderLogListResponseType response = orderContract.getOfflineOrderLogList(orderId);
        if (response != null && response.getLogs() != null && response.getLogs().size() > 0) {
            ModelMapper modelMapper=new ModelMapper();
            logList= modelMapper.map(response.getLogs(),new TypeToken<List<VmTrainLog>>(){}.getType());
        }
        return logList;
    }

    /**
     * 是否神算
     * @param orderId
     * @return
     */
    private Boolean getIsIncludeCalculate(String orderId){
        Boolean isIncludeCalculate = false;
        QueryRefundTicketResultV20ResponseType response = ctripticketingserviceContract.getQueryRefundTicketResult(orderId);
        if (null != response && response.getRefundTicketResult() != null && response.getRefundTicketResult().size() > 0) {
            isIncludeCalculate = response.getRefundTicketResult().stream().anyMatch(T -> T.getDealType() != 0);
        }
        return isIncludeCalculate;
    }

    /**
     *
     * @param dmTrainOrderDetail
     * @return
     */
    private String getPayMentRemark(DmTrainOrderDetail dmTrainOrderDetail){

        String payRemark = StringUtils.EMPTY;
        Long orderFlag = Long.valueOf(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderFlag());
        Long orderFlagV3 = Long.valueOf(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderFlagV3());

        if ((orderFlag & 512) != 0)
        {
            payRemark = "前置支付";
        }
        if ((orderFlag & 4194304) == 4194304)
        {
            payRemark = "信用付";
        }
        if ((orderFlag & 4194304) == 4194304 && (dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderType() == 6 || dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderType() == 0))
        {
            payRemark = "信用抢";
        }

        if ((orderFlag & 4194304) == 4194304 && dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderType() == 2)
        {
            payRemark = "夜间代购信用付";
        }
        if ((orderFlagV3 & 512) == 512) {
            //	先抢后付标记 10
            payRemark += " 先抢后付";
        }
        if ((orderFlagV3 & 1024) == 1024) {
            //	先付后抢标记 11
            payRemark += " 先付后抢";
        }
        return payRemark;
    }

    private Boolean checkIsCtripAuthed(String partnerName, String merchantUid){
        Boolean isCtripAuthed = false;
        if (StringUtils.isNotEmpty(partnerName) && partnerName.toLowerCase().equals("ctrip.train")) {
            IsUserAuthedRsp response = accountSoa2ServerContract.isCtripUserAuthed(merchantUid, "CTRP");
            if(response!=null && response.isAuthed != null){
                isCtripAuthed = response.isAuthed;
            }
        }
        return isCtripAuthed;
    }

    /**
     * 是否员工家属
     * @param phone
     * @return
     */
    private Boolean isEmployeeFamily(String phone) {
        Boolean isEmployeeFamily = false;
        try {
            GetFamilyNumberByPhoneResponseType response = emergencyServiceContract.getFamilyNumberByPhone(phone);
            CLogger.info("isEmployeeFamily", JSON.toJSONString(response), new HashMap<String, String>() {{
                put("phone", phone);
            }});
            if (response != null && response.getResult().equals(0) && response.getEmpInfo() != null) {
                if (StringUtils.isNotEmpty(response.getEmpInfo().getEmpcode())) {
                    isEmployeeFamily = true;
                }
            }
        }catch (Exception ex){
            CLogger.error("isEmployeeFamily",ex);
        }
        return isEmployeeFamily;
    }

    /**
     * 神盾解密
     * @param mobile
     * @return
     */
    private String modileDec(String mobile,String eid) {
        SingleDecryptRequestType sdrt = new SingleDecryptRequestType();
        sdrt.setKeyType(CoreInfoKeyType.Phone);
        sdrt.setStrNeedEncrypt(mobile);
        sdrt.setEid(eid);
        return shenDunContract.singleDecrypt(sdrt);
    }

    /**
     * 获取取消订单按钮
     * @param dmTrainOrderBasicInfo
     * @return
     */
    private String getCancelBtn(DmTrainOrderBasicInfo dmTrainOrderBasicInfo){
        String btnHtml = "";

        if (dmTrainOrderBasicInfo != null) {
            if (dmTrainOrderBasicInfo.getOrderType() == 0)//配送票
            {
                if ((dmTrainOrderBasicInfo.getOrderState() == OrderStateEnum.WaitPay.getCode() || dmTrainOrderBasicInfo.getOrderState() == OrderStateEnum.WaitTicket.getCode()) || (dmTrainOrderBasicInfo.getTicketType() != 0 && dmTrainOrderBasicInfo.getOrderState() == OrderStateEnum.Ticketing.getCode())) {
                    btnHtml = String.format("<a class='a' href='javascript:void(0)' onclick='CancelOrderPage(\"%s\",\"%s\")' id='CancelOrder'>取消订单</a>", dmTrainOrderBasicInfo.getPartnerOrderId(), dmTrainOrderBasicInfo.getPartnerName());
                } else if (dmTrainOrderBasicInfo.getOrderType() == 0 && dmTrainOrderBasicInfo.getOrderState() == OrderStateEnum.Ticketing.getCode())//已提取可以申请取消订单
                {
                    btnHtml = String.format("<a class='a' href='javascript:void(0)' onclick='tickagent.applyCancelOrder(\"%s\")' id='CancelOrder' >申请取消已提取订单</a>", dmTrainOrderBasicInfo.getPartnerOrderId());
                }

            }
        }
        return btnHtml;
    }

    /**
     * 获取出票帐号
     * @param realTickets
     * @return
     */
    private String getUserAccount(List<DmRealTicketDetailInfo> realTickets){
        String userAccount = StringUtils.EMPTY;
        if (realTickets != null && realTickets.size() > 0) {
            userAccount = StringUtils.join(realTickets.stream().map(T -> T.getUserId12306()).distinct().collect(Collectors.toList()),",");
        }
        return userAccount;
    }

    private String getOrderStateDet(DmTrainOrderDetail dmTrainOrderDetail){
        if(dmTrainOrderDetail.getDmTrainOrderTicketsInfo().getOrderTicketModels() != null && dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderState() == OrderStateEnum.Cancel.getCode()){
            if(dmTrainOrderDetail.getDmTrainOrderTicketsInfo().getOrderTicketModels().stream().allMatch(tt -> tt.getTicketState() == 1)){
                return "无票";
            }
        }
        return OrderStateEnum.convertVendor(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderState()).getName();
    }

    private List<String> getOrderStateInfo(DmTrainOrderDetail dmTrainOrderDetail){
        List<String> orderStateInfo = new ArrayList<>();
        try {

            if ((Long.valueOf(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderFlagV2()) & 536870912) == 536870912) {
                orderStateInfo.add("配送票无票转24H抢票");
            }

            if ((Long.valueOf(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderFlag()) & 262144) == 262144) {
                orderStateInfo.add("延迟出票");
            }

            if ((Long.valueOf(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderFlagV2()) & 134217728) == 134217728) {
                orderStateInfo.add("扣位失败代购转配送");
            }
            String twoRecommendInfo = getTwoRecommendInfo(dmTrainOrderDetail);
            if(StringUtils.isNotEmpty(twoRecommendInfo)){
                orderStateInfo.add(twoRecommendInfo);
            }
        } catch (Exception ex) {
            CLogger.info("getOrderStateInfo", ex);
        }
        return orderStateInfo;
    }
    private List<String> getOrderStateInfoRed(DmTrainOrderDetail dmTrainOrderDetail){
        List<String> orderStateInfo = new ArrayList<>();

        if (dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderType() != 6 && dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderType() != 0  // 非抢票 配送票
                && dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderState() == OrderStateEnum.Ticketing.getCode()) // 已提取状态
        {
            if(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPayTime() != null && DateUtils.addMinutes(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPayTime(), 15).before(new Date())){
                orderStateInfo.add(String.format("支付%s分钟未获取到出票结果", DateUtils.getDateSpanMinute(new Date(),dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPayTime())));
            }
        }
        return orderStateInfo;
    }

    /**
     * 二推状态
     * @param dmTrainOrderDetail
     * @return
     */
    private String getTwoRecommendInfo(DmTrainOrderDetail dmTrainOrderDetail){
        String twoRecommendInfo = StringUtils.EMPTY;
        if (extScmTwoTaskTableDao.isTwoRecommend(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerOrderId()))
        {
            if (!(extScmTwoTaskTableDao.isTwoRecommendForPersonal(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerOrderId()) && dmTrainOrderDetail.getDmTrainOrderTicketsInfo().getIsSleep()))
            {
                twoRecommendInfo = "二推(待沟通)";
            }
        }
        if (extScmTwoTaskTableDao.isNeedCancelTwoRecommend(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerOrderId()))
        {
            twoRecommendInfo = "二推(释放失败)";
        }
        if (extScmTwoTaskTableDao.isTwoRecommending(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerOrderId()))
        {
            if (!(extScmTwoTaskTableDao.isTwoRecommendForPersonal(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerOrderId()) && dmTrainOrderDetail.getDmTrainOrderTicketsInfo().getIsSleep()))
            {
                twoRecommendInfo = "二推(已分配)";
            }
        }
        return twoRecommendInfo;
    }

    /**
     * 是否二推
     * @param //二推类型   1：私人定制二推  2：一般二推 3：可推荐更换座席
     * @return
     */
    private Integer getTwoRecommendType(DmTrainOrderDetail dmTrainOrderDetail){
        Integer twoRecommendType = 0;
        try {
            if (extScmTwoTaskTableDao.isTwoRecommend(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerOrderId()) || extScmTwoTaskTableDao.isTwoRecommending(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerOrderId())) {
                if (extScmTwoTaskTableDao.isTwoRecommendForPersonal(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerOrderId()) && dmTrainOrderDetail.getDmTrainOrderTicketsInfo().getIsSleep()) {
                    twoRecommendType = 1;
                } else {
                    twoRecommendType = getRecommendType(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerOrderId());
                }
            }
        }catch(Exception ex){
            CLogger.info("getTwoRecommendType",ex.toString());
        }
        return twoRecommendType;
    }

    /**
     * 获取可二次推荐类型
     * @param orderId
     * @return
     */
    private Integer getRecommendType(String orderId)
    {
        Integer recommendType = 0;
        try
        {
            IsCanInsertRecommendResponseType canRecommend = ivrContract.isCanInsertRecommend(orderId);
            if (canRecommend != null && (canRecommend.getIsCanRecommend() == 1 || canRecommend.getMessage().equals("去哪儿订单不进入推荐！"))) //可推荐
            {
                if (canRecommend.getCanRecommendDetail() != null) //可推荐
                {
                    if(canRecommend.getCanRecommendDetail().getDeliveryType().equals(DeliveryTypeEnum.Delivery)){
                        recommendType = 3;
                    }else{
                        recommendType = 2;
                    }
                }
            }
        }
        catch (Exception ex)
        {
            CLogger.error("GetCanBeRecommendType",ex);
        }
        return recommendType;
    }

    /**
     * 是否可以取消二推
     * @param orderId
     * @return
     */
    private boolean getisNeedCancelTwoRec(String orderId){
        Boolean isNeedCancelTwoRecommend = false;
        if (extScmTwoTaskTableDao.isNeedCancelTwoRecommend(orderId))
        {
            isNeedCancelTwoRecommend = true;
        }
        return isNeedCancelTwoRecommend;
    }

    private String getUserId(DmTrainOrderDetail dmTrainOrderDetail) {
        String userLoginName = "";
        if ((dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerName().equals("tieyou")
                || dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerName().equals("zhixing"))
                && dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderSource() == 1) {
            userLoginName = dmTrainOrderDetail.getDmTrainOrderBasicInfo().getUserID();
            //SetZhixinMember(response.OrderFrom, _UserLoginName);
        } else {
            userLoginName = dmTrainOrderDetail.getDmTrainOrderBasicInfo().getLoginName();
        }
        return userLoginName;
    }

    /**
     * 获取用户等级
     * @param dmTrainOrderDetail
     * @return
     */
    private String getUserLevel(DmTrainOrderDetail dmTrainOrderDetail){
        String userLevel = "";
        if (dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerName().equals("Ctrip.Train"))
        {
            userLevel = getUserLevel(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getLoginName());
        } else if ((dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerName().equals("tieyou")
                || dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerName().equals("zhixing"))
                && dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderSource() == 1) {
            userLevel = getZhixinMember(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerName(),dmTrainOrderDetail.getDmTrainOrderBasicInfo().getUserID(),dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerOrderId());
        }
        return userLevel;
    }

    /** 订单详情页获取车票类型
     *
     * @param dmTrainOrderDetail
     * @param isCounterTicket   是否柜台票
     * @return
     */
    public static String getTicketType(DmTrainOrderDetail dmTrainOrderDetail, boolean isCounterTicket)
    {
        String outTicketType = "";

        if (dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderFlag().equals("16"))
        {
            if (isCounterTicket)
                outTicketType = "柜台票云抢票";
            else
                outTicketType = "配送票云抢票";
        }
        else if (dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderType() == 0)
        {
            if (isCounterTicket)
                outTicketType = "柜台票";
            else
                outTicketType = "配送票";
        }

        else if (dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderType() == 1 && dmTrainOrderDetail.getDmTrainOrderBasicInfo().getTicketType() == 0)
        {
            outTicketType = "团体票";
        }
        else if (dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderType() == 2)
        {
            outTicketType = "电子票";

            if (dmTrainOrderDetail.getDmTrainOrderTicketsInfo() != null &&  dmTrainOrderDetail.getDmTrainOrderTicketsInfo().getChangeTicketInfo() !=null &&  dmTrainOrderDetail.getDmTrainOrderTicketsInfo().getChangeTicketInfo().size() > 0)
            {
                for(DmChangeTrainTicketInfo ticket :dmTrainOrderDetail.getDmTrainOrderTicketsInfo().getChangeTicketInfo())
                {
                    if (ticket.getRescheduleRealTicketInfos() != null && ticket.getRescheduleRealTicketInfos().stream().anyMatch(T->T.getRescheduleType() == 1)) {
                        outTicketType = "电子票(改签抢)";
                        break;
                    }
                }
            }
        }
        else if (dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderType() == 5)
        {
            outTicketType = "纯直连票";
        }
        else if (dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderType() == 6)
        {
            outTicketType = "捡漏抢";

            if(dmTrainOrderDetail.getDmTrainOrderGrabTask()!= null && dmTrainOrderDetail.getDmTrainOrderGrabTask().getCrossStationInfo()!= null && dmTrainOrderDetail.getDmTrainOrderGrabTask().getCrossStationInfo().size() > 0){
                outTicketType = "跨站抢";
            }
            else if ((Long.valueOf(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderFlagV2()) & 262144) != 0)
            {
                outTicketType = "预约票";
            }
            else if (dmTrainOrderDetail.getDmTrainOrderTicketsInfo() != null &&  dmTrainOrderDetail.getDmTrainOrderTicketsInfo().getChangeTicketInfo() !=null &&  dmTrainOrderDetail.getDmTrainOrderTicketsInfo().getChangeTicketInfo().size() > 0)
        {
            for(DmChangeTrainTicketInfo ticket :dmTrainOrderDetail.getDmTrainOrderTicketsInfo().getChangeTicketInfo())
                {

                    if (ticket.getRescheduleRealTicketInfos() != null && ticket.getRescheduleRealTicketInfos().stream().anyMatch(T->T.getRescheduleType() == 1)) {
                        outTicketType = "改签抢";
                        break;
                    }

                    if (ticket.getRescheduleRealTicketInfos() != null && ticket.getRescheduleRealTicketInfos().stream().anyMatch(T->T.getRescheduleType() == 2))
                    {
                        outTicketType = "保底抢";
                        break;
                    }

                    // todo 字段来源需确认
                    dmTrainOrderDetail.getDmTrainOrderTicketsInfo().getOrderTicketModels().get(0).getTicketID();
//
                    if (ticket.getRescheduleRealTicketInfos().stream().anyMatch(T->
                            dmTrainOrderDetail.getDmTrainOrderTicketsInfo().getOrderTicketModels().stream().anyMatch(F->F.getTicketID() == T.getRealTicketId() && F.getRecLinePrepaidMoney().compareTo(BigDecimal.ZERO) == 1))){
                        outTicketType = "跨站抢";
//                        break;
                    }
                }
            }
        }
        else if (dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderType() == 7)
        {
            outTicketType = "直连代付";
        }
//        if (dmTrainOrderDetail.getDmTrainOrderBasicInfo().getArchive == 1)    李琴说没有了
//        {
//            outTicketType += "-半直连票";
//        }
        if (dmTrainOrderDetail.getDmTrainOrderBasicInfo().getTicketType() == 0)
        {
            outTicketType += "(单程票)";
        }
        else if (dmTrainOrderDetail.getDmTrainOrderBasicInfo().getTicketType() == 1)
        {
            outTicketType += "(往返票)";
        }
        else if (dmTrainOrderDetail.getDmTrainOrderBasicInfo().getTicketType() == 2)
        {
            outTicketType += "(联程票)";
        }
        return outTicketType;
    }

    /**
     * 获取用户等级
     * @param
     * @param UID
     * @return
     */
    public String getUserLevel(String UID)
    {
        //以前是先判断userlevel的，新的接口没找到这个字段，先拿
//        String level = @"<label style='padding: 5px;font-weight:bold;'>【{0}】</label>";
//        if (!string.IsNullOrEmpty(userLevel))
//        {
//            return string.Format(level, userLevel);
//        }
        String userLevel = "";
        if (StringUtils.isNotEmpty(UID)) {
            List<String> uidList = Arrays.asList(UID);
            GetCurrentLevelResponse response = platformContract.getCurrentLevel(uidList);
            if (response != null && response.getResultCode() != null && response.getResultCode().equals("Success")) {
                if (response.getCurrentLevelItems() != null && response.getCurrentLevelItems().size() > 0) {
                    String userLevelId = response.getCurrentLevelItems().get(0).getCurLevelCode();
                    switch (userLevelId) {
                        case "0":
                            userLevel = "【普通会员】";
                            break;
                        case "10":
                            userLevel = "【金牌会员】";
                            break;
                        case "20":
                            userLevel = "【白金会员】";
                            break;
                        case "30":
                            userLevel = "【钻石会员】";
                            break;
                    }
                }
            }
        }
        return userLevel;
    }

    private BigDecimal getDiffPrice(DmTrainOrderDetail dmTrainOrderDetail){
        BigDecimal diffPrice = new BigDecimal(0);
        try {
            diffPrice = dmTrainOrderDetail.getDmTrainOrderTicketsInfo().getTotalFare().subtract(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderPrice());
        } catch (Exception ex) {
            CLogger.info("getDiffPrice", ex);
        }
        return diffPrice;
    }

    /**
     * 获取订单渠道
     *
     * @return
     */
    private void getorderSource(DmTrainOrderDetail dmTrainOrderDetail,VmTrainOrderBasicInfo vm) {
        String orderSourceName = StringUtils.EMPTY;
        String orderSourceNameD = StringUtils.EMPTY;
        DmTrainOrderBasicInfo basicInfo = dmTrainOrderDetail.getDmTrainOrderBasicInfo();
        try {
            OfflinePartnerinfo offlinePartnerinfo = new OfflinePartnerinfo();
            offlinePartnerinfo.setPartnerSourceName(basicInfo.getPartnerName());
            offlinePartnerinfo.setPartnerType(1);
            List<OfflinePartnerinfo> oflinePartnerinfolist = offlinePartnerinfoDao.queryBy(offlinePartnerinfo);//获取分销商信息

            if (oflinePartnerinfolist == null || oflinePartnerinfolist.size() == 0) {
                orderSourceName += basicInfo.getPartnerName();
                orderSourceNameD += basicInfo.getPartnerName();
            } else {
                orderSourceName += oflinePartnerinfolist.get(0).getPartnerTargetName();
                orderSourceNameD += oflinePartnerinfolist.get(0).getPartnerTargetName();
            }
            if (basicInfo.getPartnerName().equals("tieyou.businesstravel")) {
                orderSourceName += "/" + basicInfo.getChannelName();
                orderSourceNameD += "/" + basicInfo.getChannelName();
            }
            orderSourceName += "(" + getOrderSourceById(basicInfo.getOrderSource()) + ")";
            orderSourceNameD += basicInfo.getChannelName() + "(" + getOrderSourceById(basicInfo.getOrderSource()) ;

            if(StringUtils.isNotEmpty(basicInfo.getServerFrom())){
                orderSourceNameD += "/" + basicInfo.getServerFrom();
            }

            if(StringUtils.isNotEmpty(basicInfo.getAppVersion())){
                orderSourceNameD += basicInfo.getAppVersion();
            }

            orderSourceNameD += ")";

        } catch (Exception ex) {
            CLogger.info("getorderSource", ex);
        }


        vm.setOrderSourceName(orderSourceName);
        vm.setOrderSourceNameD(orderSourceNameD);
    }


    private String getCountryCode(DmTrainOrderDetail dmTrainOrderDetail) {
        String countryCode = "";
        if (StringUtils.isNotEmpty(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getCountryCode()) && !dmTrainOrderDetail.getDmTrainOrderBasicInfo().getCountryCode().equals("86")) {
            countryCode = "00" + dmTrainOrderDetail.getDmTrainOrderBasicInfo().getCountryCode();
        }
        return countryCode;
    }


    /**
     * 判断是否柜台票
     *
     * @return
     */
    private Boolean getisCoutnerTicket(DmTrainOrderDetail dmTrainOrderDetail) {
        Boolean isCoutnerTicket = false;
        if (dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderType() == 0) {
            String partnerOrderId = dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerOrderId();

            if ((dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerName().equals("qunarsync") || dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerName().equals("qunar"))
                    && !partnerOrderId.contains("ofctrip"))
            {
                partnerOrderId = partnerOrderId + "ofctrip";
            }
            CounterTicketResponse response = trainTicketAgentServiceContract.getIscounterTicket(partnerOrderId);
            if(response!= null && response.getRetCode() != null && response.getRetCode().equals(0)){
                isCoutnerTicket = true;
            }
        }
        return isCoutnerTicket;
    }

    //region  OrderHelper  订单帮助类


    //endregion

    private String getOrderSourceById(Integer id){
        String ordersourcename = "";
        switch (id)
        {
            case 0:
                ordersourcename = "pc";
                break;
            case 1:
                ordersourcename = "app";
                break;
            case 2:
                ordersourcename = "H5";
                break;
            default:
                break;
        }
        return ordersourcename;
    }


    //region 获取支付信息

    /**
     * 获取支付信息
     * @param paymentType
     * @return
     */
    public String getPayInfo(String paymentType)
    {
        List<PayWayItemInfo> BANKS = getBanks();
        if (StringUtils.isNotEmpty(paymentType))
        {
            if (paymentType.toUpperCase().equals("CCARD"))//2017-11-17 修复
            {
                return "银行卡支付";
            }
            List<PayWayItemInfo> info = BANKS.stream().filter(p -> p.getPaymentWayID().toUpperCase().contains(paymentType.toUpperCase()))
                    .collect(Collectors.toList());

            if (info != null && info.size() > 0)
            {
                return info.get(0).getPaymentWayName();
            }
            else
            {
                return paymentType;
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    private List<PayWayItemInfo> getBanks(){
        List<PayWayItemInfo> paymentwayList = new ArrayList<PayWayItemInfo>();
        PaymentContract paymentContract = new PaymentContract();
        GetAllPaymentwayListResponse response = paymentContract.getAllPaymentwayList();
        if (null != response && response.getPaymentwayCatalogList() != null && response.getPaymentwayCatalogList().size() > 0) {
            for (PaymentwayCatalog Payment : response.getPaymentwayCatalogList()) {
                if (null != Payment && Payment.getPaymentwayList() != null && Payment.getPaymentwayList().size() > 0) {
                    for (Paymentway Pay : Payment.getPaymentwayList()) {
                        paymentwayList.add(getPayWayItemInfo(Pay.getPaymentwayId(), Pay.getPaySystemName()));
                    }
                }
            }
        }
        paymentwayList.add(getPayWayItemInfo("WechatScanCode", "微信支付"));
        paymentwayList.add(getPayWayItemInfo("TravelMoney", "游票支付"));
        return paymentwayList;
    }

    private static PayWayItemInfo getPayWayItemInfo(String PaymentWayID,String PaymentWayName){
        PayWayItemInfo tempPayWayItemInfo = new PayWayItemInfo();
        tempPayWayItemInfo.setPaymentWayID(PaymentWayID);
        tempPayWayItemInfo.setPaymentWayName(PaymentWayName);
        return tempPayWayItemInfo;
    }
    //endregion


    //region   判断智行会员信息

    private String getZhixinMember(String partnerName, String tyUserID, String orderID) {
        String zhixingLevel = "";
        try {
            if (StringUtils.isNumeric(tyUserID)) {
                GetUserVipDetailInfoForOfflineResponseType response = tyUserCoreServiceContract.getUserVipDetailInfoForOffline(null, null, Integer.parseInt(tyUserID), partnerName);
                if (response != null && response.getResultCode() == 1 && response.getVipInfos() != null) {
                    String zhixingUrl = String.format("http://admin.train.ctripcorp.com/TrainOrderProcess/TieyouMember/VipInfo.aspx?tyUID=%s&partnername=%s&orderid=%s"
                            , tyUserID
                            , partnerName
                            , orderID);

                    zhixingLevel = String.format("<a href=\"%s\" target=\"_blank\">【%s】</a>", zhixingUrl, response.getVipInfos().getVipName());
                }
            }
        }catch(Exception ex){
            CLogger.info("getZhixinMember",ex);
        }
        return zhixingLevel;
    }
    //endregion


}
