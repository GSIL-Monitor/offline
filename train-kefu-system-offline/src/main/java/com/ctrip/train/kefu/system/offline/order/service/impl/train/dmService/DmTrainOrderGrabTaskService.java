package com.ctrip.train.kefu.system.offline.order.service.impl.train.dmService;

import com.ctrip.soa.train.trainordercentreservice.v1.*;
import com.ctrip.train.kefu.system.offline.order.domain.train.DmTrainOrderDetail;
import com.ctrip.train.kefu.system.offline.order.domain.train.order.*;
import com.ctrip.train.kefu.system.offline.order.enums.train.CtripLevelEnum;
import com.ctrip.train.kefu.system.offline.order.enums.train.TieyouLevelEnum;
import com.ctrip.train.kefu.system.offline.order.vm.train.order.VmTrainGrabLevel;
import com.ctrip.train.kefu.system.offline.order.vm.train.order.VmTrainOrderGrabTask;
import common.log.CLogger;
import common.util.DateUtils;
import common.util.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DmTrainOrderGrabTaskService {

    /**
     * 获取订单DoMain_抢票信息
     */
    public DmTrainOrderGrabTask getDmOrderGrabTask(OrderDetailResponseType orderDetailResponseType) {
        DmTrainOrderGrabTask dm = new DmTrainOrderGrabTask();
        Map<String, String> dic = new HashMap<>();
        dic.put("orderNumber", orderDetailResponseType.getOrderMaster().getPartnerOrderId());
        try {
            //行程信息车次信息
            List<OrderTicketModel> orderTicketModels =orderDetailResponseType.getOrderTickets();
            ModelMapper modelMapper=new ModelMapper();
            OrderGrabInfo orderGrabInfo=orderDetailResponseType.getGrabInfo();
            TrainorderExtInfo trainorderExtInfo = orderDetailResponseType.getTrainorderExtInfo();
            List<TrainCrossStationGrabTicketInfo> tempCrossStaions = orderDetailResponseType.getCrossStationGrabTicketList();
            dm.setOrderTicketModels(modelMapper.map(orderTicketModels,new TypeToken<List<DmOrderTicketModel>>() {}.getType()));
            dm.setCrossStationInfo(modelMapper.map(tempCrossStaions,new TypeToken<List<DmCrossStationInfo>>() {}.getType()));
            dm.setTrainOrderExtInfo(modelMapper.map(trainorderExtInfo,new TypeToken<DmTrainorderExtInfo>() {}.getType()));
            dm.setOrderGrabInfo(modelMapper.map(orderGrabInfo,new TypeToken<DmOrderGrabInfo>() {}.getType()));
        } catch (Exception ex) {
            CLogger.error("获取订单DoMain_抢票信息", ex, dic);
        }
        return dm;
    }

    /**
     * 获取页面实体信息_抢票信息
     */
    public VmTrainOrderGrabTask getVmOrderGrabTask(DmTrainOrderDetail dmTrainOrderDetail) {
        VmTrainOrderGrabTask vm = new VmTrainOrderGrabTask();
        Map<String, String> dic = new HashMap<>();
        String orderNumber=dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerOrderId();
        dic.put("orderNumber", orderNumber);
        try {
            String partnerName=dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerName();
            List<VmTrainGrabLevel> levels1 = getTrainGrabLevels(partnerName);
            vm.setLevels(levels1);
            //单程票票才有抢票任务
            DmTrainOrderGrabTask orderGrabTask = dmTrainOrderDetail.getDmTrainOrderGrabTask();
            List<DmOrderTicketModel> orderTicket = orderGrabTask.getOrderTicketModels();
            if(orderTicket!=null&&orderTicket.size()>0){
                DmOrderTicketModel ticket = orderTicket.get(0);
                vm.setAcceptDepartDates(ticket.getAcceptDepartDates());
                vm.setAcceptSeat(StringUtils.isEmpty(ticket.getAcceptSeat())?null: ticket.getAcceptSeat());
                vm.setAllAccpetTrainNumber(StringUtils.isEmpty(ticket.getAllAccpetTrainNumber())?null:ticket.getAllAccpetTrainNumber());
                List<DmChangeTrainTicketInfo> tempChange=dmTrainOrderDetail.getDmTrainOrderTicketsInfo().getChangeTicketInfo();
                if (tempChange != null && tempChange.size() > 0) {
                    vm.setTicketType("改签票");
                } else {
                    vm.setTicketType("原票");
                }
                if (dmTrainOrderDetail.getDmTrainOrderGrabTask().getOrderGrabInfo()!=null){
                    //临近接受
                    vm.setAcceptTimeRanges(dmTrainOrderDetail.getDmTrainOrderGrabTask().getOrderGrabInfo().getGrabNearTime());
                }
                vm.setCreateTime(DateUtils.format(ticket.getCreateTime().getTime(),DateUtils.YMDHM_UNDERLINED));

                if(orderGrabTask.getTrainOrderExtInfo().getLeakCutOffTime() != null) {
                    //捡漏截止时间
//                    String[] cutOffTimes = DateUtils.format(DateUtils.parseDate(
//                            orderGrabTask.getTrainOrderExtInfo().getLeakCutOffTime(), DateUtils.YMDHMS_UNDERLINED),
//                            DateUtils.YMDHMS_UNDERLINED).split(" ");
//                    vm.setLeakCutOffTime(cutOffTimes[0].split("-")[1] + "." + cutOffTimes[0].split("-")[2] + " " + cutOffTimes[1]);
                    vm.setLeakCutOffTime(DateUtils.format(DateUtils.parseDate(
                            orderGrabTask.getTrainOrderExtInfo().getLeakCutOffTime(), DateUtils.YMDHMS_UNDERLINED),
                            DateUtils.YMDHMS_UNDERLINED));
                }
                List<DmCrossStationInfo> crossStations= orderGrabTask.getCrossStationInfo();
                if(crossStations!=null&&crossStations.size()>0){
                    List<String> manyStation=new ArrayList<>();
                    List<String> lessStation=new ArrayList<>();
                    for (DmCrossStationInfo crossStation: crossStations){
                        //多抢出发站
                        if (crossStation.getRecommendDepStationType()>0)
                            manyStation.add(crossStation.getRecommendDepStation());
                        //多抢到达站
                        if (crossStation.getRecommendArrStationType()>0){
                            //抢票到达站
                            manyStation.add(crossStation.getRecommendArrStation());
                        }else if (crossStation.getRecommendArrStationType()<0){
                            //少抢到达站
                            lessStation.add(crossStation.getRecommendArrStation());
                        }
                    }
                    vm.setCrossStation(manyStation.size()>0?"多抢至"+String.join("、", manyStation)+";":""
                            + (lessStation.size()>0?"少抢至"+String.join("、", lessStation):""));
                }
            }
        } catch (Exception ex) {
            CLogger.info("获取页面实体信息_抢票信息", ex, dic);
        }
        return vm;
    }

    /**
     * 火车票等级信息
     * @param partnerName
     * @return
     */
    private List<VmTrainGrabLevel> getTrainGrabLevels(String partnerName) {
        List<VmTrainGrabLevel> levels=new ArrayList<>();
        if (partnerName.equalsIgnoreCase("Ctrip.Train")){
            CtripLevelEnum[] ctripLevelEnum=CtripLevelEnum.values();
            List<CtripLevelEnum> ctripLevels= Arrays.stream(ctripLevelEnum).collect(Collectors.toList());
            for (CtripLevelEnum ctripLevel: ctripLevels){
                VmTrainGrabLevel vml=new VmTrainGrabLevel();
                vml.setAmount(ctripLevel.getCode());
                vml.setLevelName(ctripLevel.getName());
                levels.add(vml);
            }
        }else if(partnerName.equalsIgnoreCase("tieyou")||partnerName.equalsIgnoreCase("zhixing")){
            TieyouLevelEnum[] tieyouLevelEnum=TieyouLevelEnum.values();
            List<TieyouLevelEnum> temp= Arrays.stream(tieyouLevelEnum).collect(Collectors.toList());
            for (TieyouLevelEnum t: temp){
                VmTrainGrabLevel vml=new VmTrainGrabLevel();
                vml.setAmount(t.getCode());
                vml.setLevelName(t.getName());
                levels.add(vml);
            }
        }else if(partnerName.toLowerCase().contains("qunar")){
            //去哪儿

        }
        return levels;
    }
}
