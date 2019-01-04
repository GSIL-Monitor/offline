package com.ctrip.train.kefu.system.offline.order.service.impl.flight;


import com.alibaba.fastjson.JSON;
import com.ctrip.soa.framework.soa.tieyouflightvendor.v1.*;
import com.ctrip.train.kefu.system.client.offline.flight.FlightRefundContract;
import com.ctrip.train.kefu.system.client.pojo.flight.FlightRefundDetailRequset;
import com.ctrip.train.kefu.system.offline.order.service.FlightRefundService;
import com.ctrip.train.kefu.system.offline.order.vm.flight.refund.*;
import com.ctrip.train.kefu.system.offline.order.vm.flight.refund.dto.DtoRefund;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.log.CLogger;
import common.util.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class FlightRefundServiceImpl implements FlightRefundService {

    @Autowired
    private FlightRefundContract flightRefundContract;

    /**
     * 查询退票详情接口
     * @param refund
     * @return
     * @throws Exception
     */
    public VmFlightRefundDetail  getRefundDetail(DtoRefund refund) {

        VmFlightRefundDetail vm = new VmFlightRefundDetail();
        vm.setOperatorName(refund.getOperatorName());
        vm.setCtripUid(refund.getCtripUid());
        vm.setOrderid(refund.getOrderid());
        vm.setTyUerid(refund.getTyUerid());
        vm.setIsFirstRefund(true);
        vm.setIsNonComfirmOrder(false);


        //获取详情信息
        FlightRefundConditionResponseType response = getDetail(refund);

        //初始化数据
        if (response == null) {
            return vm;
        }

        if (response.getData2() == null) {
            vm.setMessage(response.getResultMessage());
            return vm;
        }

        Data2 order = response.getData2();
        vm.setContactMobile(order.getContactMobile());
        vm.setOperatorName(refund.getOperatorName());
        vm.setHasRefundInvoice(order.getHasRefundPriceInvoice());
        vm.setHasPaidDelivery(order.getDeliveryHasPaid());

        // 行程
        List<VmSegment> segments = new ArrayList<>();

        AtomicReference<Integer> segmentNUm= new AtomicReference<>(0);//航段num

        //初始化行程 根据起飞时间排序是为了计算中转时候走的航段
        response.getData2().getSegmentList().stream()
                .sorted(Comparator.comparing(RefundSegmentDTO::getDepartCityName))
                .collect(Collectors.toList())
                .forEach(p -> {
                    if (p.getTickets() == null) {
                        return;
                    }
                    VmSegment vmSegment = new VmSegment();
                    segmentNUm.updateAndGet(v -> v + 1);
                    //初始化航程信息
                    getSegmentInfo(vmSegment, p, response.getData2().getOrderType(), segmentNUm.get());

                    // 乘客
                    List<VmTickets> tickets = new ArrayList<>();
                    //初始化乘客信息
                    getTicketInfo(tickets, p.getTickets(), vm);

                    vmSegment.setVmTickets(tickets);

                    segments.add(vmSegment);
                });
        vm.setVmSegmentsList(segments);
        return vm;
    }

    /**
     * 初始化航程信息
     */
    private  void  getSegmentInfo(VmSegment vmSegment,RefundSegmentDTO dto,int  orderType,int segmentNUm) {
        vmSegment.setFilghtNumber(dto.getFilghtNumber());
        vmSegment.setArriveCityName(dto.getArriveCityName());
        vmSegment.setDepartCityName(dto.getDepartCityName());
        vmSegment.setSegmentNo(dto.getSegmentNo());
        vmSegment.setVendorOrderNumber(dto.getVendorOrderNumber());
        if (dto.getRefundReasons()!=null&&dto.getRefundReasons().size()>0){
            Optional<RefundReasonDTO> reasonDTO= dto.getRefundReasons().stream().findFirst();
            reasonDTO.ifPresent(refundReasonDTO -> vmSegment.setReasonId(refundReasonDTO.getReasonId()));
        }

        if (orderType==4) { //中转订单根据航段显示
            vmSegment.setSegmentTag("中转第" + segmentNUm + "程");
        }
        else {
            if (dto.getSegmentTag() == 1) {
                vmSegment.setSegmentTag("去程");
            } else if (dto.getSegmentTag() == 2) {
                vmSegment.setSegmentTag("返程");
            } else {
                vmSegment.setSegmentTag("单程");
            }
        }
        vmSegment.setVendorOrderNumber(dto.getVendorOrderNumber());
        vmSegment.setDepartDateTime(dto.getDepartDateTime());
        if (Objects.requireNonNull(DateUtils.parseDate(dto.getDepartDateTime(), DateUtils.YMDHM_UNDERLINED))
                .after(DateUtils.getCurDate())){
            vmSegment.setHasDepart(false);
        }
        else {
            vmSegment.setHasDepart(true);
        }
    }

    /**
     * 初始化乘客tikect信息
     */
    private  void  getTicketInfo(List<VmTickets>  tickets,List<RefundTicketDTO> refundTickets,VmFlightRefundDetail vm) {
        refundTickets.forEach((RefundTicketDTO g) -> {
            VmTickets vmTickets = new VmTickets();
            vmTickets.setVendorOrderNumber(g.getVendorOrderNumber());
            vmTickets.setPassengerName(g.getPassengerName());
            vmTickets.setPassengerType(g.getPassengerType());
            vmTickets.setStateDesc(g.getStateDesc());
            vmTickets.setPassengerId(g.getPassengerId());
            vmTickets.setCanRefund(g.getCanRefund());

            //是否是未确认订单
            if (g.getRefundPriceDetails()==null&&g.getPriceDetails()==null){
                vm.setIsNonComfirmOrder(true);
            }
            //是否首次退票
            if (vmTickets.getStateDesc().equals("退票中")||
                vmTickets.getStateDesc().equals("已退票")){
                vm.setIsFirstRefund(false);
            }

            //订单费用
            getReturnPrice(g,vmTickets,vm);

            tickets.add(vmTickets);
        });
    }

    /**
     * 退票
     */
    public Map<String,String> refundDetail(VmFlightRefund refund) {

        ConcurrentHashMap<String,String> map = new ConcurrentHashMap<>();
        if (refund==null){
            map.put("","无相关订单信息");
            return  map;
        }
        //退票详情
        FlightRefundConditionResponseType respose=getDetail(refund.getRefund());
        if (respose==null){
            map.put(refund.getRefund().getOrderid(),"无相关订单信息");
            return  map;
        }

        ReturnTicketRequestType request=new  ReturnTicketRequestType();
        Data3 data3=new  Data3();
        data3.setCtripUId(refund.getRefund().getCtripUid());
        data3.setContactPhone(respose.getData2().getContactMobile());
        data3.setOrderNumber(refund.getRefund().getOrderid());
        data3.setOrderType(respose.getData2().getOrderType());
        data3.setTyUserId(refund.getRefund().getTyUerid());
        data3.setOperatorName(refund.getOperateName());
        data3.setSource("offline");
        if (refund.getTickets()!=null&&refund.getTickets().size()>0){
            Optional<VmTickets>  vmTickets= refund.getTickets().stream().findFirst();
            vmTickets.ifPresent(p -> data3.setReasonId(p.getReasonId()));
        }

        //填写了退票凭据
        if (refund.getContactName()!=null&&!refund.getContactName().isEmpty()) {
            getInvoiceInfo(data3, refund);// 发票信息
            getDelivery(data3, refund);// 配送信息
        }

        getReturnSegments(data3,refund,respose);   // 退票信息
        request.setData3(data3);
        //退票
        refund(request,  map);
        return   map;
    }


    /**
     * 退票申请
     */
    public void refund(ReturnTicketRequestType request, ConcurrentHashMap<String,String> map){
        try {
            ObjectMapper mapper = new ObjectMapper();
            CLogger.info("flightRefundContract",mapper.writeValueAsString(request));
            ReturnTicketResponseType response=flightRefundContract.sendFlightRefund(request);
            if (response!=null&&response.getResultCode()!=1){
                map.put(request.getData3().getOrderNumber(),response.getResultMessage());
            }
            else {
                map.put(request.getData3().getOrderNumber(), "退票申请提交成功");
            }
        }
        catch (Exception ex){
            CLogger.error("flightreturnTicket",ex.getMessage());
        }
    }

    /**
     * 初始化发票信息
     */
     private  void  getInvoiceInfo(Data3 data3,VmFlightRefund refund){
         CreateOrderInvoiceType invoiceType=new  CreateOrderInvoiceType();
         invoiceType.setInvoiceTitle(refund.getInvoiceTitle());
         invoiceType.setInvoiceType(refund.getInvoiceType());
         invoiceType.setTaxNumber(refund.getTaxNumber());
         data3.setInvoiceInfo(invoiceType);
     }

    /**
     * 初始化配送信息
     */
    private  void  getDelivery(Data3 data3,VmFlightRefund refund) {
        //配送信息
        CreateOrderDeliveryType deliveryType = new CreateOrderDeliveryType();
        deliveryType.setAddress(refund.getAddress());
        deliveryType.setCity(refund.getCity());
        deliveryType.setContactName(refund.getContactName());
        deliveryType.setContactPhone(refund.getContactPhone());
        deliveryType.setDeliveryType(refund.getDeliveryType());
        deliveryType.setDistrict(refund.getDistrict());
        deliveryType.setEmail(refund.getEmail());
        deliveryType.setProvince(refund.getProvince());
        data3.setDeliveryInfo(deliveryType);
    }

    /**
     * 初始化退票航程信息
     */
    private  void  getReturnSegments(Data3 data3,VmFlightRefund refund,FlightRefundConditionResponseType respose){
        if (respose==null||respose.getData2()==null
            ||respose.getData2().getSegmentList()==null){
            return;
        }
        List<ReturnSegmentDTO> segmentDTOS=new ArrayList<>();
        if (refund!=null&&refund.getTickets()!=null) {
            refund.getTickets().forEach(p->{
                Optional<RefundSegmentDTO> segment =respose.getData2().getSegmentList()
                        .stream()
                        .filter(q->q.getTickets()
                                .stream()
                                .anyMatch(r->r.getPassengerName().equals(p.getPassengerName())
                                        &&r.getVendorOrderNumber().equals(p.getVendorOrderNumber()))
                                &&q.getSegmentNo()==p.getSegmentNo())
                        .findFirst();

                if (segment.isPresent()){
                    ReturnSegmentDTO segmentDTO=new  ReturnSegmentDTO();
                    segmentDTO.setChangeOrderId(segment.get().getChangeOrderId());
                    segmentDTO.setFilghtNumber(segment.get().getFilghtNumber());
                    segmentDTO.setPassengerName(p.getPassengerName());
                    segmentDTO.setPassengerId(p.getPassengerId());
                    segmentDTO.setSegmentNo(segment.get().getSegmentNo());
                    segmentDTO.setSpecialEvent(segment.get().getSpecialEvent());
                    segmentDTO.setSequence(segment.get().getSequence());
                    segmentDTO.setSubId(segment.get().getSubId());
                    segmentDTO.setVendorOrderNumber(refund.getTickets().get(0).getVendorOrderNumber());
                    segmentDTOS.add(segmentDTO);
                }
            });
        }
        data3.setReturnSegments(segmentDTOS);
    }

    /**
     * 初始化订单费用信息
     */
    private  void  getReturnPrice(RefundTicketDTO g,VmTickets vmTickets, VmFlightRefundDetail vm){

        //机票接口的垃圾代码，导致offline必须兼容
        //页面需要展示单张票的燃油费和票价，这些接口返回在list里面，只能从list的一条数据中取值
        //而且类型只能通过文案是判断
        String titleValue = "";
        if (g.getPassengerType().contains("成人")) {
            titleValue = "成人";
        } else if (g.getPassengerType().contains("儿童")) {
            titleValue = "儿童";
        } else if (g.getPassengerType().contains("婴儿")) {
            titleValue = "婴儿";
        }

        String finalTitleValue = titleValue;
        if (g.getPriceDetails()!=null) {

            //支付总金额
            vmTickets.setTotalPrice(g.getPriceDetails().stream()
                    .filter(p->p.getType().equals("P"))//乘客纬度
                    .map(PriceDetailDTO::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, BigDecimal.ROUND_HALF_UP).toString());

            List<VmInsurance> vmInsurances=new ArrayList<>();
            g.getPriceDetails().forEach(p->{
                //机建+燃油
                if (p.getTitle().equals(String.format("机建+燃油(%s)", finalTitleValue))){
                    vmTickets.setBuildingFuelPrice(p.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                }
                //机票款
                else if (p.getTitle().equals(String.format("%s票", finalTitleValue))){
                    vmTickets.setTicketsPrice(p.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                }
                //代金券
                else if (p.getTitle().equals("代金券")){
                    vmTickets.setVoucherPrice(p.getPrice().abs().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                }
                //保险
                else if (p.getTitle().contains("险")){
                    VmInsurance vmInsurance=new  VmInsurance();
                    vmInsurance.setInsuranceName(p.getTitle());
                    vmInsurance.setInsurancePrice(p.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                    vmInsurances.add(vmInsurance);
                }
                vmTickets.setInsurances(vmInsurances);
            });
        }

        if (g.getRefundPriceDetails()!=null){
            //退票费
            Optional<PriceDetailDTO> refundPrice = g.getRefundPriceDetails().stream()
                    .filter(m -> m.getTitle().equals(String.format("退票费(%s)", finalTitleValue)))
                    .findFirst();
            refundPrice.ifPresent(m ->{
                //是否有退票费
                if (m.getPrice().abs().compareTo(BigDecimal.ZERO) > 0){
                    vm.setHasRefundPrice(true);
                }
                //初始化退票费
                vmTickets.setRefundPrice(m.getPrice().abs().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            });

            //返现金额
            Optional<PriceDetailDTO> callBackPrice = g.getRefundPriceDetails().stream()
                    .filter(m -> m.getTitle().equals("返现"))
                    .findFirst();
            callBackPrice.ifPresent(m -> vmTickets.setCashBackPrice(m.getPrice().abs().setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
        }
    }


    /**
     * 获取退票详情方法
     * @param refund
     * @return
     */
    private FlightRefundConditionResponseType  getDetail(DtoRefund refund){

        //获取图片详情
        ModelMapper modelMapper=new ModelMapper();
        FlightRefundDetailRequset requset=modelMapper.map(refund,FlightRefundDetailRequset.class);
        try {
            return flightRefundContract.getFlightRefundDetail(requset);
        }
        catch (Exception ex){
            CLogger.error("获取机票退票详情",ex.getMessage());
        }

        return null;
    }
}
