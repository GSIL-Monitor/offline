package com.ctrip.train.kefu.system.offline.order.service.impl.train.dmService;

import com.ctrip.soa.train.traindata.phenixdataapiservice.v1.GetAllStationsResponseType;
import com.ctrip.soa.train.traindata.phenixdataapiservice.v1.GetStopStationsResponseType;
import com.ctrip.soa.train.traindata.phenixdataapiservice.v1.Station;
import com.ctrip.soa.train.traindata.phenixdataapiservice.v1.StopStation;
import com.ctrip.soa.train.trainordercentreservice.v1.*;
import com.ctrip.train.kefu.system.client.offline.train.StationContract;
import com.ctrip.train.kefu.system.client.offline.train.TrainProductServiceContract;
import com.ctrip.train.kefu.system.client.offline.train.TrainTicketAgentServiceContract;
import com.ctrip.train.kefu.system.offline.common.utils.OrderHelper;
import com.ctrip.train.kefu.system.offline.common.utils.TrainTicketUtils;
import com.ctrip.train.kefu.system.offline.order.domain.train.DmTrainOrderDetail;
import com.ctrip.train.kefu.system.offline.order.domain.train.order.*;
import com.ctrip.train.kefu.system.offline.order.vm.train.order.*;
import com.ctrip.train.kefu.system.offline.order.vm.train.refund.VmRefundSxfRequest;
import com.ctrip.train.kefu.system.offline.order.vm.train.refund.VmRefundSxfResponse;
import com.ctrip.train.product.contract.product.contract.FastServiceDTO;
import com.ctrip.train.product.contract.product.contract.FastServicePointV2ResponseType;
import com.ctrip.train.ticketagent.service.client.CounterTicketResponse;
import common.log.CLogger;
import common.util.DateUtils;
import common.util.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DmTrainOrderTicketsInfoService {

    @Autowired
    private TrainTicketAgentServiceContract trainTicketAgentServiceContract;

    @Autowired
    private TrainProductServiceContract trainProductServiceContract;

    @Autowired
    private StationContract stationContract;


    /**
     * 获取订单DoMain_行程乘客信息
     */
    public DmTrainOrderTicketsInfo getDmOrderTicketsInfo(OrderDetailResponseType orderDetailResponseType) {
        DmTrainOrderTicketsInfo dm = new DmTrainOrderTicketsInfo();
        Map<String, String> dic = new HashMap<>();
        dic.put("orderNumber", orderDetailResponseType.getOrderMaster().getPartnerOrderId());
        try {
            //订单类型
            //0 单程 1往返 2多程 票类型
            int ticketType=orderDetailResponseType.getOrderMaster().getTicketType();
            dm.setTicketType(ticketType);
            dm.setOrderType(orderDetailResponseType.getOrderMaster().getOrderType());

            //实际出票列表
            List<RealTicketDetailInfo> RealTicketDetails=orderDetailResponseType.getOrderRealTickets();
            //行程信息车次信息
            List<OrderTicketModel> orderTicketModels =orderDetailResponseType.getOrderTickets();
            //乘客信息
            List<PassengerModel> passengers= orderDetailResponseType.getPassengers();

            List<TicketResultDetailInfo> ticketResult = orderDetailResponseType.getOrderTicketResults();
            BigDecimal totalPrice=new BigDecimal(0);
            if (ticketResult!=null&&ticketResult.size()>0){
                for (TicketResultDetailInfo temp:ticketResult){
                    totalPrice=totalPrice.add(temp.getTotalTicketPrice());
                }
            }
            dm.setTotalFare(totalPrice);
            ModelMapper modelMapper=new ModelMapper();
            dm.setRealTickets(modelMapper.map(RealTicketDetails,new TypeToken <List<DmRealTicketDetailInfo>>() {}.getType()));
            dm.setOrderTicketModels(modelMapper.map(orderTicketModels,new TypeToken <List<DmOrderTicketModel>>() {}.getType()));
            dm.setPassengerModels(modelMapper.map(passengers,new TypeToken <List<DmPassengerModel>>() {}.getType()));

//            dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderFlag();
            // 配送信息
            OrderDeliveryInfo orderDeliveryInfo = orderDetailResponseType.getDeliveryInfo();
            if (orderDeliveryInfo!=null){
                //配送信息
                dm.setOrderDeliveryInfo(modelMapper.map(orderDeliveryInfo,new TypeToken <DmOrderDeliveryInfo>() {}.getType()));
            }
            dm.setIsSleep(RealTicketDetails.stream().anyMatch(T->T.equals("卧") || T.equals("铺")));
            //改签信息 暂无检票口
            List<OrderrescheduleTicketInfo> orderrescheduleTicket = orderDetailResponseType.getOrderrescheduleTicketInfos();
            if(orderrescheduleTicket!=null&&orderrescheduleTicket.size()>0){
                //改签票
                dm.setChangeTicketInfo(modelMapper.map(orderrescheduleTicket,new TypeToken <List<DmChangeTrainTicketInfo>>() {}.getType()));
            }
            return dm;
        } catch (Exception ex) {
            CLogger.error("获取订单DoMain_行程乘客信息", ex, dic);
            return null;
        }
    }

    private VmTrainRoute getVmTrainRoute(DmOrderTicketModel orderTicketModel) {
        VmTrainRoute vtr= new VmTrainRoute();
        //到达时间
        String arrivalTime= DateUtils.format(orderTicketModel.getArrivalDateTime().getTime(),DateUtils.YMDHM_UNDERLINED);
        //出发时间
        String goTime=orderTicketModel.getTicketDate()+" "+orderTicketModel.getTicketTime();
        //耗时
        String timeConsum=DateUtils.getDateSpanHourAndMin(orderTicketModel.getArrivalDateTime().getTime(),
                             DateUtils.parseDate(goTime,DateUtils.YMDHMS_UNDERLINED));

        vtr.setArrivalDateTime(arrivalTime.split(" ")[1]);
        vtr.setArriveStationName(orderTicketModel.getArriveStationName());
        vtr.setDepartStationName(orderTicketModel.getDepartStationName());
        vtr.setElectronicNum(StringUtils.isBlank(orderTicketModel.getElectronicNum())?null:orderTicketModel.getElectronicNum());
        vtr.setOrderTicketId(orderTicketModel.getTicketID());
        vtr.setSaleFeePrice(orderTicketModel.getSaleFeePrice());
        vtr.setTicketCheck(orderTicketModel.getTicketCheck());
        vtr.setTicketCount(orderTicketModel.getTicketCount());
        vtr.setTrainNumber(orderTicketModel.getTrainNumber());
        vtr.setTicketPrice(orderTicketModel.getTicketPrice());
        if(!DateUtils.format(orderTicketModel.getPreSaleTime().getTime(),DateUtils.YMDHMS_UNDERLINED).equals("0001-01-01 08:00:00")){
            vtr.setPreSaleTime(DateUtils.format(orderTicketModel.getPreSaleTime().getTime(),DateUtils.YMDHMS_UNDERLINED));
        }
        String[] dates=orderTicketModel.getTicketDate().split("-");
        vtr.setVmTicketDate(dates[1]+"/"+dates[2]);
        vtr.setTicketDate(orderTicketModel.getTicketDate());
        String[] ticketTime=orderTicketModel.getTicketTime().split(":");
        vtr.setTicketTime(ticketTime[0]+":"+ticketTime[1]);
        vtr.setTimeConsuming(timeConsum);
        vtr.setSectionNumber(orderTicketModel.getSectionNumber());
        vtr.setTrainStatus(DateUtils.parseDate(orderTicketModel.getTicketDate()+" "+orderTicketModel.getTicketTime(),DateUtils.YMDHMS_UNDERLINED)
                .after(DateUtils.currentTime())?"":"已过发车时间");

        return vtr;
    }
    /**
     * 行程
     */
    private String getSectionStr(int ticketType,int sectionNumber) {
        if(ticketType == 0 &&sectionNumber == 1){
            return "单程";
        }else if(ticketType == 1 &&sectionNumber == 1){
            return "去程";
        }else if(ticketType == 1 &&sectionNumber == 2){
            return "返程";
        }else if(ticketType == 2 &&sectionNumber == 1){
            return "第一程";
        }else if(ticketType == 2 &&sectionNumber == 2){
            return "第二程";
        }else if(ticketType == 2 &&sectionNumber == 3){
            return "第三程";
        }
        return "未识别";
    }
    private VmTrainTicket getVmTrainByPassengerId(List<DmPassengerModel> passengers, DmRealTicketDetailInfo realTicket,String fromStationName,String toStationName) {
        DmPassengerModel passenger=passengers.stream().filter(p->
                p.getPassengerId()==realTicket.getPassengerInfoId()).collect(Collectors.toList()).get(0);
        VmTrainTicket vtt=new VmTrainTicket();
        //车票信息
        vtt.setOrderId(realTicket.getOrderId());
        vtt.setOrderTicketId(realTicket.getOrderTicketID());
        vtt.setPartnerName(realTicket.getPartnerName());
        vtt.setSeatName(realTicket.getSeatName()+" "+realTicket.getCarriageNo()+"车厢"+realTicket.getSeatNumber());
        vtt.setTicketState(realTicket.getTicketState());
        //退票失败原因
        if(realTicket.getTicketState()==3)
            vtt.setReturnRemark(realTicket.getReturnFailRemark());
        //真实票价
        vtt.setRealTicketPrice(realTicket.getTicketPrice());
        //订单票价
        vtt.setTicketPrice(realTicket.getTicketPrice());
        vtt.setQuickPass(realTicket.getQuickPass());
        //乘客信息
        vtt.setPassengerId(passenger.getPassengerId());
        vtt.setPartnerName(passenger.getPartnerName());
        vtt.setPassengerType(passenger.getPassengerType());
        vtt.setPassportType(passenger.getPassportType());
//        vtt.setPassportNumber(passenger.getPassportNumber());
        vtt.setPassportNumber(OrderHelper.SafeUserPassportNo(passenger.getPassportNumber(),true));
        vtt.setRealName(passenger.getRealName());
        boolean flag = TrainTicketUtils.isEnableReturnTicket(false,
                String.valueOf(realTicket.getTicketState()),fromStationName,toStationName,realTicket.getTicketDate()+" "+realTicket.getTicketTime()).getKey();
        //是否可退票
        vtt.setEnableReturnTicket(flag);
        vtt.setLongTrainNo(realTicket.getLongTrainNo());
        //计算退票手续费
        if (flag){
            VmRefundSxfRequest request = new VmRefundSxfRequest();
            request.setTicketDate(DateUtils.parseDate(realTicket.getTicketDate()+" "+realTicket.getTicketTime(),DateUtils.YMDHMS_UNDERLINED));
            request.setTicketType(0);
            request.setTotalPrice(realTicket.getTicketPrice());
            //1-停运
            request.setNoHasSxf(realTicket.getTrainStatus()==1?true:false);
//            request.setTicketDate(DateUtils.parseDate("2018-12-28 17:30:00"));
            VmRefundSxfResponse response=TrainTicketUtils.getRefundSxf(request);
            vtt.setHandlingFee(response.getXsfPrice().doubleValue()>0?response.getXsfPrice().toString():"0");
            vtt.setHandlingPercentage(String.valueOf(response.getXsfRate()));
            vtt.setRefundPrice(response.getKtPrice().toString());
        }
        return vtt;
    }
    private List<VmTrainTicket> getVmTrainByTicketId(List<DmPassengerModel> passengers, long orderTicketId) {
        List<DmPassengerModel> passengerList=passengers.stream().filter(p->
                p.getOrderTicketId()==orderTicketId).collect(Collectors.toList());
        //车票信息
//        vtt.setOrderId(realTicket.getOrderId());
//        vtt.setOrderTicketId(realTicket.getOrderTicketID());
//        vtt.setPartnerName(realTicket.getPartnerName());
//        vtt.setSeatName(realTicket.getSeatName());
//        vtt.setTicketState(realTicket.getTicketState());
//        //真实票价
//        vtt.setRealTicketPrice(realTicket.getTicketPrice());
//        //订单票价
//        vtt.setTicketPrice(realTicket.getTicketPrice());
        //乘客信息
        List<VmTrainTicket> vmList=new ArrayList<>();
        for (DmPassengerModel passenger:passengerList){
            VmTrainTicket vtt=new VmTrainTicket();
            //退票
//            vtt.setTicketState("");
            vtt.setPassengerId(passenger.getPassengerId());
            vtt.setPartnerName(passenger.getPartnerName());
            vtt.setPassengerType(passenger.getPassengerType());
            vtt.setPassportType(passenger.getPassportType());
            vtt.setPassportNumber(OrderHelper.SafeUserPassportNo(passenger.getPassportNumber(),true));
            vtt.setRealName(passenger.getRealName());
            vmList.add(vtt);
        }
        return vmList;
    }

    /**
     * 判断是否柜台票
     * @return
     */
//    private Boolean getisCoutnerTicket(String partnerOrderId,String partnerName,int orderType) {
//        Boolean isCoutnerTicket = false;
//        if (orderType == 0) {
//            if ((partnerName.equals("qunarsync") || partnerName.equals("qunar"))&& !partnerOrderId.contains("ofctrip"))
//                partnerOrderId = partnerOrderId + "ofctrip";
//            CounterTicketResponse response = trainTicketAgentServiceContract.getIscounterTicket(partnerOrderId);
//            if(response!= null && response.getRetCode() != null && response.getRetCode().equals(0)){
//                isCoutnerTicket = true;
//            }
//        }
//        return isCoutnerTicket;
//    }

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

    /**
     * 获取页面实体信息_行程乘客信息
     */
    public VmTrainOrderTicketsInfo getVmOrderTicketsInfo(DmTrainOrderDetail dmTrainOrderDetail) {

        VmTrainOrderTicketsInfo vm = new VmTrainOrderTicketsInfo();
        Map<String, String> dic = new HashMap<>();
        DmTrainOrderBasicInfo basicInfo=dmTrainOrderDetail.getDmTrainOrderBasicInfo();
        dic.put("orderNumber", basicInfo.getPartnerOrderId());
        vm.setOrderStatus(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getOrderState());
        vm.setTotalFare(dmTrainOrderDetail.getDmTrainOrderTicketsInfo().getTotalFare());
        try {
            //行程乘客信息
            // ticketStatus 0未退 1已退 2退票中 3退票失败 4改签中 5 改签成功 5
            DmTrainOrderTicketsInfo ticketsInfo =  dmTrainOrderDetail.getDmTrainOrderTicketsInfo();

            vm.setTicketType(ticketsInfo.getTicketType());
            //行程信息
            List<DmOrderTicketModel> ticketModels=ticketsInfo.getOrderTicketModels();

            //真实出票信息
            List<DmRealTicketDetailInfo> realTickets=ticketsInfo.getRealTickets();
            vm.setCoutnerTicket(getisCoutnerTicket(dmTrainOrderDetail));
            //乘客信息
            List<DmPassengerModel> passenger = ticketsInfo.getPassengerModels();
            //配送信息
//            vm.setOrderDelivery(modelMapper.map(ticketsInfo.getOrderDeliveryInfo(),new TypeToken <VmOrderDeliveryInfo>() {}.getType()));

            if (ticketsInfo.getOrderDeliveryInfo()!=null){
                DmOrderDeliveryInfo orderDeliveryInfo=ticketsInfo.getOrderDeliveryInfo();
                VmOrderDeliveryInfo vodi= new VmOrderDeliveryInfo();
                vodi.setAgentName(orderDeliveryInfo.getAgentName());
                vodi.setAreaName(orderDeliveryInfo.getAreaName());
                vodi.setDeliverCompany(orderDeliveryInfo.getDeliverCompany());
                vodi.setDeliveryAddress(orderDeliveryInfo.getDeliveryAddress());
                vodi.setDeliveryNumber(orderDeliveryInfo.getDeliveryNumber());
                vodi.setDeliveryPrice(orderDeliveryInfo.getDeliveryPrice());
                vodi.setDeliveryState(orderDeliveryInfo.getDeliveryState());
//                boolean isCoutnerTicket = getisCoutnerTicket(basicInfo.getPartnerOrderId(),basicInfo.getPartnerName(),basicInfo.getOrderType());
                //判断是否是柜台票
                if(ticketModels!=null&&ticketModels.size()>0){
                    //柜台票展示配送地址
                    DmOrderTicketModel temp=ticketModels.get(0);
                    FastServicePointV2ResponseType responseType=trainProductServiceContract.getStationImageUrl(temp.getDepartStationName(),
                            DateUtils.format(DateUtils.parseDate(temp.getTicketDate()+" "+temp.getTicketTime(),DateUtils.YMDHMS_UNDERLINED),DateUtils.YMDHMS));
                    List<FastServiceDTO> templist=responseType.getFastServiceList();
                    if (templist!=null&&templist.size()>0){
                        vodi.setAddressPicUrl(templist.get(0).getPicUrl());
                    }
                }
//                vodi.setTicketFlag(isCoutnerTicket?1:0);
                vm.setOrderDelivery(vodi);
            }
            //车站地址
            GetAllStationsResponseType stations=stationContract.getAllStations();
            List<Station> stationAddress =stations.getStations();
            //改签票信息
            List<DmChangeTrainTicketInfo> changeTrainTickets=ticketsInfo.getChangeTicketInfo();
            if (changeTrainTickets!=null&&changeTrainTickets.size()>0){
                //改签票模型实体
                List<VmTrainRoute> vmchangeTickets = getVmChangeTrainTicketInfos(ticketsInfo.getTicketType()
                        ,ticketModels,realTickets , passenger, changeTrainTickets,stationAddress);
                vm.setChangeTickets(vmchangeTickets);
            }

            BigDecimal saleFeePrice =BigDecimal.ZERO;
            if (ticketModels!=null&&ticketModels.size()>0){
                List<VmTrainRoute> vmTrainRoutes=new ArrayList<>();
                //行程信息
                for (DmOrderTicketModel ticketModel:ticketModels){
                    //配送票服务费
                    saleFeePrice=saleFeePrice.add(ticketModel.getSaleFeePrice());
                    VmTrainRoute route = getVmTrainRoute(ticketModel);
                    //车站地址
                    if (stationAddress!=null&&stationAddress.size()>0){
                        List<Station> arrive= stationAddress.stream().filter(s->s.getStationName()
                                .equals(route.getArriveStationName())).collect(Collectors.toList());
                        List<Station> depart=stationAddress.stream().filter(s->s.getStationName()
                                .equals(route.getDepartStationName())).collect(Collectors.toList());
                        if(arrive!=null&&arrive.size()>0)
                            route.setArriveAddress(arrive.get(0).getAddress());
                        if (depart!=null&&depart.size()>0)
                            route.setDepartAddress(depart.get(0).getAddress());
                    }
                    route.setSectionStr(getSectionStr(ticketsInfo.getTicketType(),ticketModel.getSectionNumber()));
                    //经停
                    List<VmResponseS2S> s2sList=searchStopStations(ticketModel.getTrainNumber(),route.getTicketDate(),
                                        route.getArriveStationName(),route.getDepartStationName());
                    route.setStopStations(s2sList);
                    //票信息
                    List<DmRealTicketDetailInfo> tempRealTickets= realTickets.stream().filter(
                                t->t.getOrderTicketID()==ticketModel.getOrderTicketId()).collect(Collectors.toList());
                    //备注
                    route.setAcceptSeat(ticketModel.getAcceptSeat());
                    List<VmTrainTicket> vmTrainTickets=new ArrayList<>();
                    if (tempRealTickets!=null&&tempRealTickets.size()>0){
                        //订单状态 部分改签 全部改签  //0未退 1已退 2退票中 3退票失败 4改签中 5 改签成功
//                        tempRealTickets.stream().filter(t->t.getTicketState()==)
                        for (DmRealTicketDetailInfo tempRealTicket:tempRealTickets ){
                            //乘客信息
                            VmTrainTicket vmTickets=getVmTrainByPassengerId(passenger,tempRealTicket,ticketModel.getDepartStationName(),ticketModel.getArriveStationName());
                            vmTrainTickets.add(vmTickets);
                        }
                    }else {
                        vmTrainTickets=getVmTrainByTicketId(passenger,ticketModel.getOrderTicketId());
                    }
                    route.setTrainTickets(vmTrainTickets);
                    vmTrainRoutes.add(route);
                }
                //多程 //发异常件要求的字段
                if (dmTrainOrderDetail.getDmTrainOrderBasicInfo().getTicketType()==1){
                    //billid=0,|0,|0,|1,|1,|1,| 三张去程票 三张返程票
                    StringBuilder childBillId = new StringBuilder();
                    for (int j=0;vmTrainRoutes.size()>j;j++){
                        if (j==0){
                            for (int i=0;vmTrainRoutes.get(0).getTrainTickets().size()>i;i++)
                                childBillId.append("0,|");
                        }else {
                            for (int i=0;vmTrainRoutes.get(0).getTrainTickets().size()>i;i++)
                                childBillId.append("1,|");
                        }
                    }
                    vm.setChildBillId(childBillId.toString());
                }
                vm.setSaleFeePrice(String.valueOf(saleFeePrice));
                vm.setTrainRoutes(vmTrainRoutes);
            }
        } catch (Exception ex) {
            CLogger.error("获取页面实体信息_行程乘客信息", ex, dic);
        }
        return vm;
    }

    /**
     * 行程车票信息模型
     * @param ticketType
     * @param ticketModels
     * @param realTickets
     * @param passenger
     * @param changeTrainTickets
     * @return
     */
    private List<VmTrainRoute> getVmChangeTrainTicketInfos(int ticketType,List<DmOrderTicketModel> ticketModels,
                                 List<DmRealTicketDetailInfo> realTickets, List<DmPassengerModel> passenger,
                                 List<DmChangeTrainTicketInfo> changeTrainTickets,List<Station> stationAddress) {
        List<VmTrainRoute> vmchangeTickets=new ArrayList<>();
        for (DmChangeTrainTicketInfo changeTicket:changeTrainTickets){
            VmTrainRoute route =new VmTrainRoute();
            String[] departTimes =DateUtils.format(DateUtils.parseDate(changeTicket.getRescheduleDepartTime(),
                    DateUtils.YMDHM_UNDERLINED),DateUtils.YMDHM_UNDERLINED).split(" ");
            String[] arriveTimes = DateUtils.format(DateUtils.parseDate(changeTicket.getRescheduleArriveTime(),
                    DateUtils.YMDHM_UNDERLINED),DateUtils.YMDHM_UNDERLINED).split(" ");
            //行程信息
            route.setArriveStationName(changeTicket.getArriveStationName());
            route.setArrivalDateTime(arriveTimes[1]);
            route.setDepartStationName(changeTicket.getDepartStationName());
            String[] dates=departTimes[0].split("-");
            route.setVmTicketDate(dates[1]+"/"+dates[2]);//日期
            route.setTicketDate(departTimes[0]);//日期
            route.setTicketTime(departTimes[1]);//时间
            route.setTrainNumber(changeTicket.getRescheduleTrainNumber());
            //耗时
            String timeConsum=DateUtils.getDateSpanHourAndMin(
                    DateUtils.parseDate(changeTicket.getRescheduleArriveTime(), DateUtils.YMDHM_UNDERLINED),
                    DateUtils.parseDate(changeTicket.getRescheduleDepartTime(),DateUtils.YMDHMS_UNDERLINED));
            route.setTimeConsuming(timeConsum);

            //车站地址
            if (stationAddress!=null&&stationAddress.size()>0){
                List<Station> depart=stationAddress.stream().filter(s->s.getStationName()
                        .equals(route.getDepartStationName())).collect(Collectors.toList());
                List<Station> arrive= stationAddress.stream().filter(s->s.getStationName()
                        .equals(route.getArriveStationName())).collect(Collectors.toList());
                if(arrive!=null&&arrive.size()>0)
                    route.setArriveAddress(arrive.get(0).getAddress());
                if (depart!=null&&depart.size()>0)
                    route.setDepartAddress(depart.get(0).getAddress());
            }
            //经停
            List<VmResponseS2S> s2sList=searchStopStations(changeTicket.getRescheduleTrainNumber(),departTimes[0],
                    route.getArriveStationName(),route.getDepartStationName());
            route.setStopStations(s2sList);
            List<RescheduleRealTicketInfo> tickets=changeTicket.getRescheduleRealTicketInfos();
            //改签票判断行程 坑
            long orderTicketID = realTickets.stream().filter(rt->rt.getOrderRealTicketId()==tickets.get(0).getRealTicketId()).collect(Collectors.toList()).get(0).getOrderTicketID();
            int sectionNumber =ticketModels.stream().filter(t->t.getOrderTicketId()==orderTicketID).collect(Collectors.toList()).get(0).getSectionNumber();
            route.setSectionStr(getSectionStr(ticketType,sectionNumber));
            //票信息
            List<VmTrainTicket> changeTemp = getVmTrainTickets(passenger, changeTicket, tickets,changeTicket.getRescheduleDepartTime(),
                    changeTicket.getDepartStationName(),changeTicket.getArriveStationName());
            //改签成功
            //用户多位乘客 可能会出现只改签一位  或者只改签两位  你能记改签成功的人数
            //失败的 和原下单人数保持一致
            //车票张数
            long successcount=changeTemp.stream().filter(t->t.getTicketState()==40).count();
            if(successcount>0){
                route.setTicketCount(Integer.valueOf(String.valueOf(successcount)));
            }else {
                route.setTicketCount(changeTicket.getRescheduleRealTicketInfos().stream()
                    .collect(Collectors.groupingBy(RescheduleRealTicketInfo::getPassengerInfoId)).size());
            }
            route.setTrainTickets(changeTemp);
//            route.setTrainStatus(DateUtils.parseDate("2018-12-11 14:48:00",DateUtils.YMDHMS_UNDERLINED).before(DateUtils.addMinutes(DateUtils.currentTime(),-30)););
            route.setTrainStatus(DateUtils.parseDate(changeTicket.getRescheduleDepartTime(),DateUtils.YMDHMS_UNDERLINED).after(DateUtils.currentTime())?"已过发车时间":"");


            vmchangeTickets.add(route);
        }
        return vmchangeTickets;
    }

    /**
     * 车票模型
     * @param passenger
     * @param changeTicket
     * @param tickets
     * @return
     */
    private List<VmTrainTicket> getVmTrainTickets(List<DmPassengerModel> passenger, DmChangeTrainTicketInfo changeTicket,
                                                  List<RescheduleRealTicketInfo> tickets,String rescheduleDepartTime,String fromStationName,String toStationName) {
        List<VmTrainTicket> changeTemp=new ArrayList<>();
        for (RescheduleRealTicketInfo ticket:tickets){
            DmPassengerModel  pg=passenger.stream().filter(p->p.getPassengerId()==ticket.getPassengerInfoId()).collect(Collectors.toList()).get(0);
            VmTrainTicket vtt=new VmTrainTicket();
            //车票信息
            vtt.setOrderId(changeTicket.getOrderId());
            vtt.setOrderTicketId(pg.getOrderTicketId());
//                        vtt.setPartnerName(p.getPartnerName());
            if (StringUtils.isNotBlank(ticket.getRescheduleRealSeatName())){
                vtt.setSeatName(ticket.getRescheduleRealSeatName()+" "+ticket.getRescheduleCarriageNo()+"车厢"+ticket.getRescheduleSeatNo());
            }
            vtt.setTicketState(ticket.getRescheduleState());
            //真实票价
            vtt.setRealTicketPrice(ticket.getRescheduleRealPrice());
            //订单票价
            vtt.setTicketPrice(ticket.getRescheduleTicketPrice());
            vtt.setReturnRemark(ticket.getReason());
            //乘客信息
            vtt.setPassengerId(pg.getPassengerId());
            vtt.setPartnerName(pg.getPartnerName());
            vtt.setPassengerType(pg.getPassengerType());
            vtt.setPassportType(pg.getPassportType());
            vtt.setPassportNumber(OrderHelper.SafeUserPassportNo(pg.getPassportNumber(),true));
            vtt.setRealName(pg.getRealName());

            vtt.setLongTrainNo(ticket.getRescheduleLongTrainNum());

            vtt.setRescheduleType(ticket.getRescheduleType());

            //是否可退票
            boolean flag=TrainTicketUtils.isEnableReturnTicket(false,
                    String.valueOf(ticket.getRescheduleState()),fromStationName,toStationName,rescheduleDepartTime).getKey();
            vtt.setEnableReturnTicket(flag);
            //计算退票手续费
            if(flag){
                VmRefundSxfRequest request = new VmRefundSxfRequest();
                request.setTicketDate(DateUtils.parseDate(rescheduleDepartTime,DateUtils.YMDHMS_UNDERLINED));
                request.setTicketType(1);
                //1-停运
                request.setNoHasSxf(ticket.getTrainStatus()==1?true:false);
                request.setTotalPrice(ticket.getRescheduleRealPrice());
                request.setChangeSuccessDate(changeTicket.getRescheduleSuccessTime().getTime());
                request.setChangeTicketDate(DateUtils.parseDate(changeTicket.getRescheduleDepartTime(),DateUtils.YMDHMS_UNDERLINED));
//                request.setTicketDate(DateUtils.parseDate("2018-12-28 17:30:00"));
                VmRefundSxfResponse response=TrainTicketUtils.getRefundSxf(request);
                vtt.setHandlingFee(response.getXsfPrice().doubleValue()>0?response.getXsfPrice().toString():"0");
                vtt.setHandlingPercentage(String.valueOf(response.getXsfRate()));
                vtt.setRefundPrice(response.getKtPrice().toString());
            }
            changeTemp.add(vtt);
        }
        return changeTemp;
    }

    private VmChangeTrainTicketInfo getVmChangeTrainTicketInfo(DmChangeTrainTicketInfo changeTicket) {
        VmChangeTrainTicketInfo vcinfo=new VmChangeTrainTicketInfo();
        vcinfo.setArriveStationName(changeTicket.getArriveStationName());
        vcinfo.setDepartStationName(changeTicket.getDepartStationName());
        vcinfo.setOrderId(changeTicket.getOrderId());
        vcinfo.setRescheduleArriveTime(changeTicket.getRescheduleArriveTime());
        vcinfo.setRescheduleDepartTime(changeTicket.getRescheduleDepartTime());
        vcinfo.setRescheduleSuccessTime(changeTicket.getRescheduleSuccessTime());
        vcinfo.setRescheduleTrainNumber(changeTicket.getRescheduleTrainNumber());
        return vcinfo;
    }

    /**
     * 经停信息
     * @param trainNo
     * @param ticketDate
     * @param arrivalStation
     * @param departStation
     * @return
     */
    private List<VmResponseS2S> searchStopStations(String trainNo,String ticketDate,String arrivalStation,String departStation){
        List<VmResponseS2S> returnList= new ArrayList<>();
        GetStopStationsResponseType responseType=stationContract.getStopStations(trainNo,ticketDate);
        List<StopStation> stations= responseType.getStopStations();
        if (stations!=null&&stations.size()>0){
            int depart=Integer.valueOf(stations.stream().filter(
                    s->s.getStationName().equals(departStation)).collect(Collectors.toList()).get(0).getStationNo());
            int arrival=Integer.valueOf(stations.stream().filter(
                    s->s.getStationName().equals(arrivalStation)).collect(Collectors.toList()).get(0).getStationNo());
            for (StopStation station:stations){
                VmResponseS2S vms2s=new VmResponseS2S();
                vms2s.setArrivalTime(station.getArrivalTime());
                vms2s.setStartTime(station.getStartTime());
                vms2s.setStationName(station.getStationName());
                vms2s.setStationNo(station.getStationNo());
                vms2s.setStopMinutes(station.getStopMinutes());
                if (depart<Integer.valueOf(station.getStationNo())
                        &&arrival > Integer.valueOf(station.getStationNo())){
                    vms2s.setStationFlag(0);
                }else if(depart==Integer.valueOf(station.getStationNo())){
                    vms2s.setStationFlag(2);
                }else if(arrival==Integer.valueOf(station.getStationNo())){
                    vms2s.setStationFlag(3);
                }else {
                    vms2s.setStationFlag(1);
                }
                returnList.add(vms2s);
            }
        }
        return returnList;
    }

}
