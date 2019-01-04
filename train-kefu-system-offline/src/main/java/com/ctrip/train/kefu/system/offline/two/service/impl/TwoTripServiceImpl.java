package com.ctrip.train.kefu.system.offline.two.service.impl;

import com.ctrip.soa.train.traindata.phenixdataapiservice.v1.SearchS2SResponseType;
import com.ctrip.soa.train.traindata.phenixdataapiservice.v1.SeatInfo;
import com.ctrip.soa.train.traindata.phenixdataapiservice.v1.TrainInfo;
import com.ctrip.soa.train.trainordercentreservice.offline.v1.OfflineOrderDetailResponseType;
import com.ctrip.soa.train.trainordercentreservice.offline.v1.OfflinePassengerInfo;
import com.ctrip.soa.train.trainordercentreservice.offline.v1.OrderDetailInfo;
import com.ctrip.soa.train.trainordercentreservice.v1.DeliveryRecommendChangeOrderResponseType;
import com.ctrip.train.kefu.system.client.offline.train.OrderContract;
import com.ctrip.train.kefu.system.client.offline.train.StationContract;
import com.ctrip.train.kefu.system.client.offline.train.TwoContract;
import com.ctrip.train.kefu.system.client.pojo.train.StationContractPojo;
import com.ctrip.train.kefu.system.client.pojo.train.TwoContractCheckPojo;
import com.ctrip.train.kefu.system.client.pojo.train.TwoContractPojo;
import com.ctrip.train.kefu.system.offline.two.dao.ExtScmTwoTask;
import com.ctrip.train.kefu.system.offline.two.dao.ExtScmTwoTrip;
import com.ctrip.train.kefu.system.offline.two.service.TwoTripService;
import com.ctrip.train.kefu.system.offline.two.vm.VMS2S;
import com.ctrip.train.kefu.system.offline.two.vm.VMTwo;
import com.ctrip.train.ticketagent.service.client.*;
import common.log.CLogger;
import common.util.DateUtils;
import common.util.MD5Utils;
import dao.ctrip.ctrainpps.entity.ScmTwoTripTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TwoTripServiceImpl implements TwoTripService {

    @Autowired
    private OrderContract orderContract;

    @Autowired
    private StationContract stationContract;

    @Autowired
    private TwoContract twoContract;

    @Autowired
    public ExtScmTwoTrip extScmTwoTrip;

    @Autowired
    private ExtScmTwoTask extScmTwoTask;

    final String md5Uid = "ctrip.dreamworks";
    final String md5key = "151e79da7961dc2813748528ef7069b8";
    /**
     * 根据订单号获取scmtwotrip
     * @param orderId
     * @return
     */
    public List<ScmTwoTripTable> getScmTwoTripList(String orderId) {
        return extScmTwoTrip.getScmTwoTripList(orderId);
    }

    public VMTwo getOfflineOrderDetailByOrderId(String orderId) {
        OfflineOrderDetailResponseType oodr=orderContract.getOrderDedetail(orderId);
        OrderDetailInfo odi=oodr.getOrderDetail();
        List<OfflinePassengerInfo> oplist=oodr.getOfflinePassengers();
        StationContractPojo scp = new StationContractPojo();
        VMTwo vmTwo =new VMTwo();
        //地址
        vmTwo.setContactAddress(oodr.getOfflineDeliver().getDeliverAddress());
        if(oplist.size()>0){
            String from=oplist.get(0).getFromStationName();
            String to=oplist.get(0).getToStationName();
//            String to=oplist.get(0).getToCityName();
            String trainNumber=oplist.get(0).getTrainNumber();
//            String trainNumber="K353";
            String ticketTime=oplist.get(0).getTicketTime();
            String seatNo=oplist.get(0).getSeatName().substring(0,2);
            int ticketNum=oplist.size();
            //二推车次信息
            scp.setUser(md5Uid);
            scp.setFromCity(from);
            scp.setToCity(to);
            //转成unix时间戳
            String timeStamp= String.valueOf(System.currentTimeMillis() / 1000);
            scp.setTimeStamp(timeStamp);
            //Md5加密
            scp.setSign(MD5Utils.getMD5(timeStamp+md5key));
            //不按车次信息查询
            Calendar calendar=Calendar.getInstance();
            Date tTime=Objects.requireNonNull(DateUtils.parseDate(ticketTime, DateUtils.YMD_UNDERLINED));
            calendar.setTime(tTime);
            scp.setDepartDate(calendar);
            SearchS2SResponseType s2sst=stationContract.getStopStations(scp);
            //查询代售点
            TwoContractCheckPojo tccp= new TwoContractCheckPojo();
            ticketTime.split(" ");
            tccp.setPartnerOrderId(orderId);
            GetCheckResultResponse gcrr = twoContract.getCheckResult(tccp);
            if(gcrr.getRet()==0){
                vmTwo.setCheckTrain(gcrr.getCheckResultInfo().stream().sorted(Comparator.comparing(CheckResultInfo::getTicketDate)).collect(Collectors.toList()));
            }else {
                vmTwo.setCheckTrain(null);
            }
            if(oodr.getPayList().size()>0){
                vmTwo.setPartnerOrderId(oodr.getPayList().get(0).getPartnerOrderId());
                vmTwo.setPartnerName(oodr.getPayList().get(0).getPartnerName());
            }else if(oodr.getRefundList().size()>0){
                vmTwo.setPartnerOrderId(oodr.getRefundList().get(0).getPartnerOrderId());
                vmTwo.setPartnerName(oodr.getRefundList().get(0).getPartnerName());
            }
            vmTwo.setOrderId(orderId);
            vmTwo.setContactName(odi.getContactName());
            vmTwo.setContactMobile(odi.getContactMobile());
            vmTwo.setFromCity(from);
            vmTwo.setToCity(to);
            vmTwo.setTicketNum(String.valueOf(ticketNum));
            vmTwo.setSeatNo(seatNo);
            vmTwo.setTrainNumber(trainNumber);
            vmTwo.setTicketTime(ticketTime);
            vmTwo.setSearchS2S(s2sst.getTrains());
//            vmTwo.setSearchS2SNum(trainsByNum);
            vmTwo.setDate(new SimpleDateFormat("yyyy-MM-dd").format(tTime));
        }
        return vmTwo;
    }

    public VMS2S searchS2S(Map<String,String> map) {
        StationContractPojo scp = getStationContractPojo(map);
        String seatName=map.get("seat");
        SearchS2SResponseType s2ss=stationContract.getStopStations(scp);
        VMS2S vmS2S =new VMS2S();
        if(s2ss.getMessage()==null||s2ss.getMessage().equals("")){
            List<TrainInfo> trains=s2ss.getTrains();
            if(trains.size()>0){
                TrainInfo train=s2ss.getTrains().get(0);
                vmS2S.setFromCity(train.getFromStationName());
                vmS2S.setToCity(train.getToStationName());
                vmS2S.setTrainNumber(train.getTrainNo());
                List<SeatInfo> seatList=train.getSeats();
                if(seatName.equals("searchByNum")){
                    //自检推荐
                    for(SeatInfo si:seatList){
                        if(si.getSeatName().equals("硬卧上")){
                            Map<String,Object> seatMap=new HashMap <String,Object>();
                            seatMap.put("seatName",si.getSeatName());
                            seatMap.put("ticketLeft",si.getTicketLeft());
                            seatMap.put("price",si.getPrice());
                            vmS2S.setShangSeat(seatMap);
                        }else if(si.getSeatName().equals("硬卧中")){
                            Map<String,Object> seatMap=new HashMap <String,Object>();
                            seatMap.put("seatName",si.getSeatName());
                            seatMap.put("ticketLeft",si.getTicketLeft());
                            seatMap.put("price",si.getPrice());
                            vmS2S.setZhongSeat(seatMap);
                        }else if(si.getSeatName().equals("硬卧下")){
                            Map<String,Object> seatMap=new HashMap<String,Object>();
                            seatMap.put("seatName",si.getSeatName());
                            seatMap.put("ticketLeft",si.getTicketLeft());
                            seatMap.put("price",si.getPrice());
                            vmS2S.setXiaSeat(seatMap);
                        }
                    }
                }else {
                    //站站推荐
                    for(SeatInfo si:seatList){
                        if(si.getSeatName().equals(seatName)){
                            Map<String,Object> seatMap=new HashMap<String,Object>();
                            seatMap.put("seatName",si.getSeatName());
                            seatMap.put("ticketLeft",si.getTicketLeft());
                            seatMap.put("price",si.getPrice());
                            vmS2S.setS2sSeat(seatMap);
                        }
                    }
                }
            }
            vmS2S.setTicketTime(map.get("ticketTime"));
            return vmS2S;
        }else {
            return vmS2S;
        }
    }

    public VMTwo queryTrainsByDate(Map<String,String> map)  {
        //判断是前一天还是后一天
        String ticketTime=null;
        if(map.get("time").equals("before")){
            ticketTime=DateUtils.getSpecifiedDayBefore(map.get("ticketTime"));
        }else if(map.get("time").equals("after")){
            ticketTime=DateUtils.getSpecifiedDayAfter(map.get("ticketTime"));
        }
        StationContractPojo scp = getStationContractPojo(map);
        SearchS2SResponseType s2ss=stationContract.getStopStations(scp);
        VMTwo vmTwo=new VMTwo();
        if(s2ss.getMessage()==null||s2ss.getMessage().equals("")){
            vmTwo.setSearchS2S(s2ss.getTrains());
            vmTwo.setTicketTime(ticketTime);
        }
        return vmTwo;
    }

    private StationContractPojo getStationContractPojo(Map<String, String> map) {
        StationContractPojo scp = new StationContractPojo();
        scp.setFromCity(map.get("fromCity"));
        scp.setToCity(map.get("to"));
        scp.setUser(md5Uid);
        //转成unix时间戳
        String timeStamp= String.valueOf(System.currentTimeMillis() / 1000);
        scp.setTimeStamp(timeStamp);
        //Md5加密
        scp.setSign(MD5Utils.getMD5(timeStamp+md5key));
        scp.setTrainNo(map.get("trainNo"));
        Calendar calendar=Calendar.getInstance();
        Date tTime= Objects.requireNonNull(DateUtils.parseDate(map.get("ticketTime"), DateUtils.YMD_UNDERLINED));
        calendar.setTime(tTime);
        scp.setDepartDate(calendar);
        return scp;
    }

    public UpdateDeliveryOrderForSecPushResponse tryTwoPush(Map<String,String> map)  {
        TwoContractPojo tcp=new TwoContractPojo();
        tcp.setPartnerOrderId(map.get("partnerOrderId"));
        String[] tickettime = map.get("ticketDate").split(" ");
        String msg="";
        String seatName=map.get("seatName");
        tcp.setTicketInfo(getTlist(map));
        UpdateDeliveryOrderForSecPushResponse responseType = twoContract.twoPushUpdateOrder(tcp);
        if(responseType.getRetCode()==0){
            //推荐成功更改订单
            Map<String, Object> updatemap =new HashMap<>();
            updatemap.put("partnerOrderId",String.valueOf(map.get("partnerOrderId")));
            updatemap.put("processingRemark","推荐成功");
            updatemap.put("processingState","4");
            updatemap.put("opReason","12");
            updatemap.put("operator",map.get("operator"));
            updatemap.put("operatorNum",map.get("operatorNum"));
            extScmTwoTask.updateOrderStatusByOrderId(updatemap);

            Map<String,Object> centreMap= new HashMap();
            List centreList=new ArrayList<Map<String,String>>();
            centreMap.put("orderId",String.valueOf(map.get("orderId")));
            centreMap.put("partnerName",String.valueOf(map.get("partnerName")));
            Map<String, String> logMap=new HashMap<>();
            logMap.put("orderId",String.valueOf(map.get("orderId")));
            logMap.put("partnerOrderId",String.valueOf(map.get("partnerOrderId")));
            StringBuilder sb=new StringBuilder();
            sb.append(map.get("ticketDate"));
            if (!map.get("xiaSeat").equals("0")) {
                sb.append(seatName+"下铺位"+map.get("xiaSeat")+"张");
                for(int i=1; i<=Integer.valueOf(map.get("xiaSeat"));i++){
                    Map <String,String> xmap =new HashMap<>();
                    xmap.put("ticketSeat",seatName+"下");
                    xmap.put("ticketTime",tickettime[1]);
                    xmap.put("SectionNumber","1");
                    xmap.put("TrainNumber",map.get("trainNo"));
                    xmap.put("AcceptSeat",msg);
                    centreList.add(xmap);
                }
            }
            if (!map.get("zhongSeat").equals("0")) {
                sb.append(seatName+"中铺位"+map.get("zhongSeat")+"张");
                for(int i=1; i<=Integer.valueOf(map.get("zhongSeat"));i++) {
                    Map<String, String> zmap = new HashMap<>();
                    zmap.put("ticketSeat", seatName+"中");
                    zmap.put("ticketTime", tickettime[1]);
                    zmap.put("SectionNumber", "1");
                    zmap.put("TrainNumber", map.get("trainNo"));
                    zmap.put("AcceptSeat", msg);
                    centreList.add(zmap);
                }
            }
            if (!map.get("shangSeat").equals("0")) {
                sb.append(seatName+"上铺位"+map.get("shangSeat")+"张");
                for(int i=1; i<=Integer.valueOf(map.get("shangSeat"));i++) {
                    Map<String, String> smap = new HashMap<>();
                    smap.put("ticketSeat", seatName+"上");
                    smap.put("ticketTime", tickettime[1]);
                    smap.put("SectionNumber", "1");
                    smap.put("TrainNumber", map.get("trainNo"));
                    smap.put("AcceptSeat", msg);
                    centreList.add(smap);
                }
            }
            //            配送票二推改单
            centreMap.put("ChangeTicket",centreList);
            DeliveryRecommendChangeOrderResponseType drcor = orderContract.requestOrderCentreChangeOrder(centreMap);
            if(drcor.getRetCode()!=0)
                CLogger.error("配送票二推改单出错",drcor.getMessage());
            logMap.put("comment",sb.toString());//内容
            logMap.put("operator",map.get("operator"));
            logMap.put("reasonType","通知代售点出票");
            logMap.put("actionName","通知代售点出票");//日志操作类型
            orderContract.addOrderLog(logMap);
        }
        return responseType;
    }

    private  List<Map<String,String>> getTlist(Map<String,String> map){
        String[] tickettime = map.get("ticketDate").split(" ");
        String msg="";
        String seatName=map.get("seatName");
        List<Map<String,String>> tlist=new ArrayList();
        if(map.get("falg").equals("1")) {
            int num =Integer.parseInt(map.get("shangSeat"))+Integer.parseInt(map.get("zhongSeat"))+Integer.parseInt(map.get("xiaSeat"));
            if (!map.get("xiaSeat").equals("0")) {
                Map<String,String> xiaMap=new HashMap();
                xiaMap.put("ticketDate",tickettime[0]);
                xiaMap.put("ticketSeat",seatName+"下");
                xiaMap.put("ticketTime",tickettime[1]);
                xiaMap.put("ticketNum",map.get("xiaSeat"));
                tlist.add(xiaMap);
            }else if (!map.get("zhongSeat").equals("0")) {
                Map<String,String> zhongMap=new HashMap();
                zhongMap.put("ticketSeat",seatName+"中");
                zhongMap.put("ticketDate",tickettime[0]);
                zhongMap.put("ticketTime",tickettime[1]);
                zhongMap.put("ticketNum",map.get("zhongSeat"));
                tlist.add(zhongMap);
            }else if (!map.get("shangSeat").equals("0")) {
                Map<String,String> shangMap=new HashMap();
                shangMap.put("ticketSeat",seatName+"上");
                shangMap.put("ticketDate",tickettime[0]);
                shangMap.put("ticketTime",tickettime[1]);
                shangMap.put("ticketNum",map.get("shangSeat"));
                tlist.add(shangMap);
            }
            if(num<Integer.parseInt(map.get("ticketNum"))){
                msg="经客服电联，请至少出【"+tlist.get(0).get("ticketNum")+"】张【"+tlist.get(0).get("ticketSeat")+"】铺，其余可随意。";
            }else if(num==Integer.parseInt(map.get("ticketNum"))){
                StringBuilder setsb=new StringBuilder();
                setsb.append("经客服电联，请出");
                if (!map.get("xiaSeat").equals("0")) {
                    setsb.append("【"+map.get("xiaSeat")+"】张【"+seatName+"下】铺 ");
                }
                if (!map.get("zhongSeat").equals("0")) {
                    setsb.append("【"+map.get("zhongSeat")+"】张【"+seatName+"中】铺 ");
                }
                if (!map.get("shangSeat").equals("0")) {
                    setsb.append("【"+map.get("shangSeat")+"】张【"+seatName+"上】铺 ");
                }
//                setsb.append("其余可随意。");
                msg=setsb.toString();
            }
            //设置可接受坐席
            tlist.get(0).put("acceptSeat",msg);
        }else {
            for(int i = 1; i<=Integer.parseInt(map.get("ticketNum")); i++){
                Map<String,String> ticketMap=new HashMap();
                ticketMap.put("ticketSeat", map.get("seats"));
                ticketMap.put("ticketDate", tickettime[0]);
                ticketMap.put("ticketTime", tickettime[1]);
                tlist.add(ticketMap);
            }
        }
        return tlist;
    }


    public AddcheckResultResponse addTraincheck(Map<String,String> map) {

        TwoContractCheckPojo tccp= new TwoContractCheckPojo();
        tccp.setPartnerOrderId(map.get("partnerOrderId"));
        tccp.setTrainNo(map.get("trainNo"));
        tccp.setStartStation(map.get("fromCity"));
        tccp.setEndStation(map.get("to"));
        tccp.setSeatName("硬卧");//硬卧
        tccp.setTicketTime(map.get("ticketTime").split(" ")[1]);
        tccp.setTicketDate(map.get("ticketTime").split(" ")[0]);
        tccp.setExceLevel(1);
        return twoContract.addcheckResult(tccp);
    }

    public GetCheckResultResponse queryticketLeft(Map<String,Object> map) {
        TwoContractCheckPojo tccp= new TwoContractCheckPojo();
        tccp.setPartnerOrderId(map.get("partnerOrderId").toString());
        tccp.setCheckId(Long.valueOf(String.valueOf(map.get("checkId"))));

        GetCheckResultResponse gcrr = twoContract.getCheckResult(tccp);
        return gcrr;
    }

    public SetNoTicketResponse twoPushNoTicket(Map<String,String>map)  {
        SetNoTicketResponse responseType=twoContract.twoPushNoTicket(map.get("partnerOrderId"),map.get("cancelReason"));
        if(responseType.getRetCode()==0){

            //无票更改订单
            Map<String, Object> updatemap =new HashMap<>();
            updatemap.put("partnerOrderId",String.valueOf(map.get("partnerOrderId")));
            updatemap.put("processingRemark",map.get("cancelReason"));
            updatemap.put("processingState","5");
            updatemap.put("opReason","10");
            updatemap.put("operator",map.get("operator"));
            updatemap.put("operatorNum",map.get("operatorNum"));
            extScmTwoTask.updateOrderStatusByOrderId(updatemap);

            Map<String, String> logMap=new HashMap<>();
            logMap.put("orderId",String.valueOf(map.get("orderId")));
            logMap.put("partnerOrderId",String.valueOf(map.get("partnerOrderId")));
            logMap.put("comment",map.get("cancelReason"));//内容
            logMap.put("operator",map.get("operator"));
            logMap.put("reasonType","设置订单无票");
            logMap.put("actionName","设置订单无票");//日志操作类型
            orderContract.addOrderLog(logMap);
        }
        return responseType;
    }
}
