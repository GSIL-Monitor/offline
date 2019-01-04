package com.ctrip.train.kefu.system.offline.order.service.impl.flight;

import com.ctrip.soa.framework.soa.tieyouflightvendor.v1.*;
import com.ctrip.train.kefu.system.client.offline.flight.FlightChangeContract;
import com.ctrip.train.kefu.system.client.pojo.flight.FlightChangeDetailRequest;
import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import com.ctrip.train.kefu.system.offline.order.service.FlightChangeService;
import com.ctrip.train.kefu.system.offline.order.vm.flight.change.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlightChangeServiceImpl implements FlightChangeService {

    @Autowired
    private FlightChangeContract flightChangeContract;

    @Override
    public VmFlightChangeCollect getFlightChangeDetail(FlightChangeDetailRequest request) {
        VmFlightChangeCollect vmFlightChangeCollect = new VmFlightChangeCollect();
        List <VmFlightChange> vmFlightList=new ArrayList<>();
        FlightRebookConditionResponseType responseType=flightChangeContract.getFlightChangeDetail(request);
        if(responseType == null) {
            vmFlightChangeCollect.setMsgConteng("查询接口有误");
        }else if (responseType.getResultCode()==1){
            List<OrderRebookConditionType> rebookList= responseType.getData7().getOrderRebookCondition();
            if(rebookList!=null&&rebookList.size()>0){
                //多航程分开展示
                for(OrderRebookConditionType orderChangeInfo:rebookList){
                    List<FlightRebookConditionSegmentType> changeInfo= orderChangeInfo.getSegmentList();
                    //航班信息
                    for (FlightRebookConditionSegmentType segment:changeInfo){
                        VmFlightChange vfc = new VmFlightChange();
                        List<FlightRebookConditionPassengerInfoType> passengers=segment.getPassengerInfoList();
                        //航班
                        VmFlightChangeDetail flight = getVmFlightChangeDetail(orderChangeInfo, segment);
                        //乘客信息
                        List<VmFlightChangePassenger> passengerList=new ArrayList();
                        for (FlightRebookConditionPassengerInfoType passenger:passengers){
                            VmFlightChangePassenger vmPassenger = getVmFlightChangePassenger(passenger);
                            passengerList.add(vmPassenger);
                        }
                        vfc.setOrderId(orderChangeInfo.getOrderid());
                        vfc.setFlightDetails(flight);
                        vfc.setPassengers(passengerList);
                        vmFlightList.add(vfc);
                    }
                }
                vmFlightChangeCollect.setFlightChangeDetail(vmFlightList);
            }
        }else{
            vmFlightChangeCollect.setMsgConteng(responseType.getResultMessage());
        }
        return vmFlightChangeCollect;
    }

    private VmFlightChangePassenger getVmFlightChangePassenger(FlightRebookConditionPassengerInfoType passenger) {
        VmFlightChangePassenger vmPassenger=new VmFlightChangePassenger();
        vmPassenger.setPassengerName(passenger.getPassengerName());
        vmPassenger.setPassengerType(passenger.getPassengerType());
        vmPassenger.setRemarks(passenger.getUnRebookableReason());
        vmPassenger.setCardType(passenger.getCardType());
        vmPassenger.setCardNo(passenger.getCardNo());
        vmPassenger.setRebookType(passenger.getRebookType());
        vmPassenger.setGender(passenger.getGender());
        vmPassenger.setBirthday(passenger.getBirthday());

        return vmPassenger;
    }

    private VmFlightChangeDetail getVmFlightChangeDetail(OrderRebookConditionType orderChangeInfo, FlightRebookConditionSegmentType changeInfo) {
        VmFlightChangeDetail flight=new VmFlightChangeDetail();
        FlightRebookConditionFlightBasicInformationType flightInfo=changeInfo.getFlightBasicInfo();

        flight.setFromPortName(flightInfo.getDepartCityName() + flightInfo.getDepartPortName());
        flight.setToPortName(flightInfo.getArriveCityName() + flightInfo.getArrivePortName());
        flight.setFlightNo(flightInfo.getFlightNo());
        flight.setTakeoffTime(flightInfo.getTakeoffTime());
        flight.setRescheduleRefundRemark(flightInfo.getRescheduleRefundRemark());
        flight.setClassGrade(flightInfo.getClassGrade());
        //航段 单程 往返
        flight.setTripType(orderChangeInfo.getTicketInfo().getTripType());

        flight.setArriveCityCode(flightInfo.getArriveCityCode());
        flight.setDepartCityCode(flightInfo.getDepartCityCode());
        flight.setAirlineCode(flightInfo.getAirLineCode());

        flight.setRebookAble(changeInfo.getRebookAble());
        flight.setSegmentNo(changeInfo.getSegmentNo());
        flight.setSequence(changeInfo.getSequence());
        if(changeInfo.getRebookInfo()!=null) {
            flight.setRebookMinDaysBefore(changeInfo.getRebookInfo().getRebookMinDaysBefore());
            flight.setRebookMinDaysAfter(changeInfo.getRebookInfo().getRebookMinDaysAfter());
            flight.setRebookDateAfter(changeInfo.getRebookInfo().getRebookDateAfter());
            flight.setRebookDateBefore(changeInfo.getRebookInfo().getRebookDateBefore());
            flight.setFreeDateAfter(changeInfo.getRebookInfo().getFreeDateAfter());
            flight.setFreeDateBefore(changeInfo.getRebookInfo().getFreeDateBefore());
        }
        flight.setSplitOrderSequence(changeInfo.getSplitOrderSequence());
        flight.setSegmentName(getSegmentNameDetail(orderChangeInfo.getTicketInfo().getTripType(),changeInfo.getSplitOrderSequence()));
        return flight;
    }

    @Override
    public JsonResult getRebookFlightList(VmFlightSerach request) {

        ModelMapper mapper=new ModelMapper();
        GetRebookFlightListRequestType requestType =new GetRebookFlightListRequestType();
        requestType.setDepartDate(request.getDepartDate());
        requestType.setArriveCityCode(request.getArriveCityCode());
        requestType.setDepartCityCode(request.getDepartCityCode());
        requestType.setOrderid(request.getOrderid());
        requestType.setFlightSegments(mapper.map(request.getSegments(),new TypeToken<List<FlightSegment>>(){}.getType()));
        requestType.setPassengerList(mapper.map(request.getPassengers(),new TypeToken<List<FlightRebookPassengerInfo>>(){}.getType()));
        requestType.setRebookInfo(mapper.map(request.getRebookInformation(),new TypeToken<FlightRebookConditionRebookInformation>(){}.getType()));
        GetRebookFlightListResponseType responseType=flightChangeContract.getRebookFlightList(requestType);
        if(responseType == null){
            return JsonResult.fail().putData("msg","可改航班列表获取失败");
        }
        if(responseType.getResultCode()==1)
            return JsonResult.ok().putData("response",responseType.getRebookFlightList());
        return JsonResult.fail().putData("msg",responseType.getResultMessage());
    }

    @Override
    public RebookResponseType pushFlightChange(VmRebookRequest request) {
        RebookRequestType requestType=new RebookRequestType();
        Data4 data4 =new Data4();
        data4.setCtripUId(request.getCtripUId());
        data4.setOrderId(request.getOrderId());
        data4.setTyUserId(request.getTyUserId());
        data4.setOperatorName(request.getOperatorName());
        data4.setSource(request.getSource());
        data4.setFlightData(request.getFlightData());
        data4.setSubClassData(request.getSubClassData());

        List<RebookPassengerType> temprebookPassengerList = new ArrayList<RebookPassengerType>();
        if (request.getPassengers() != null && request.getPassengers().size() > 0) {
            for (VmFlightPassenger item : request.getPassengers()) {
                RebookPassengerType temprebookPassenger = new RebookPassengerType();
                temprebookPassenger.setPassengerName(item.getPassengerName());
                temprebookPassenger.setPassengerType(item.getPassengerType());
                temprebookPassenger.setCardType(item.getCardType());
                temprebookPassenger.setCardNo(item.getCardNo());
                temprebookPassenger.setGender(item.getGender());
                temprebookPassenger.setBirthday(item.getBirthday());
                temprebookPassenger.setRebookType(item.getRebookType());
                temprebookPassengerList.add(temprebookPassenger);
            }
        }

        data4.setRebookPassengers(temprebookPassengerList);

        FlightRebookConditionRebookInformation tempRebookInfo = new FlightRebookConditionRebookInformation();
        if(request.getRebookInformation() != null){
            tempRebookInfo.setSegmentNo(request.getRebookInformation().getSegmentNo());
            tempRebookInfo.setSequence(request.getRebookInformation().getSequence());
            tempRebookInfo.setRebookMinDaysBefore(request.getRebookInformation().getRebookMinDaysBefore());
            tempRebookInfo.setRebookMinDaysAfter(request.getRebookInformation().getRebookMinDaysAfter());
            tempRebookInfo.setRebookAble(request.getRebookInformation().getRebookAble());
            tempRebookInfo.setRebookDateAfter(request.getRebookInformation().getRebookDateAfter());
            tempRebookInfo.setRebookDateBefore(request.getRebookInformation().getRebookDateBefore());
            tempRebookInfo.setFreeDateAfter(request.getRebookInformation().getFreeDateAfter());
            tempRebookInfo.setFreeDateBefore(request.getRebookInformation().getFreeDateBefore());

        }
        data4.setRebookInfo(tempRebookInfo);

        //data4.setChangeOrderID();  非必填
        //data4.setInvoiceInfo();

        requestType.setData4(data4);
        return flightChangeContract.pushFlightChange(requestType);
    }

    private String getSegmentNameDetail(Integer tripType,Integer splitOrderSequence){
        String segmentName = "";
        switch(tripType){
            case 1:
                segmentName = "单程";
                break;
            case 2:
                if (splitOrderSequence == 1) {
                    segmentName = "去程";
                } else if (splitOrderSequence == 2) {
                    segmentName = "返程";
                }
                break;
            case 4:
                if (splitOrderSequence == 1) {
                    segmentName = "第一程";
                } else if (splitOrderSequence == 2) {
                    segmentName = "第二程";
                }
                break;
            default:
                //...;
                break;
        }
        return segmentName;
    }
}
