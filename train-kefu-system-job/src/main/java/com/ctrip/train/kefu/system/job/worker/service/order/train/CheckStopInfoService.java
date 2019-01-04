package com.ctrip.train.kefu.system.job.worker.service.order.train;

import com.alibaba.fastjson.JSON;
import com.ctrip.soa.train.traindata.phenixdataapiservice.v1.SearchS2SResponseType;
import com.ctrip.soa.train.traindata.phenixdataapiservice.v1.TrainInfo;
import com.ctrip.train.kefu.system.client.offline.train.StationContract;
import com.ctrip.train.kefu.system.client.pojo.train.StationContractPojo;
import com.ctrip.train.kefu.system.job.worker.dao.order.ExtStoprunningTrainInfo;
import common.log.CLogger;
import common.util.DateUtils;
import common.util.MD5Utils;
import common.util.StringUtils;
import dao.ctrip.ctrainchat.entity.StoprunningTrainInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class CheckStopInfoService {

    @Autowired
    private ExtStoprunningTrainInfo extStoprunningTrainInfo;

    @Autowired
    private StationContract stationContract;

    final String md5Uid = "train.offline";
    final String md5key = "c32cbc04443d5f44dec759fbb5e6043d";

    /**
     * 停运站站信息校验
     * @return
     */
    public void checkStopInfo(){
        try {
            final int TempTimes = 1000;
            final int TempStatus= 2;
            final  int Temps2s=30;
            int result = 0;
            List<StoprunningTrainInfo> stopdetaillist = extStoprunningTrainInfo.getstopdetaillist();
            if (stopdetaillist != null && stopdetaillist.size() > 0) {   //
                for (int i = 0; i < stopdetaillist.size(); i++) {
                    StoprunningTrainInfo tempStop = new StoprunningTrainInfo();
                    tempStop.setTid(stopdetaillist.get(i).getTid());
                    StationContractPojo scp = new StationContractPojo();
                    scp.setFromCity(stopdetaillist.get(i).getFromStation());
                    String timeStamp = String.valueOf(System.currentTimeMillis() / TempTimes);
                    scp.setTimeStamp(timeStamp);
                    scp.setSign(MD5Utils.getMD5(timeStamp + md5key));
                    scp.setToCity(stopdetaillist.get(i).getToStation());
                    //scp.setTrainNo(stopdetaillist.get(i).getTrainNo());
                    scp.setUser(md5Uid);

                    Calendar calendar = Calendar.getInstance();
                    Date tTime = stopdetaillist.get(i).getDepartDate();
                    calendar.setTime(tTime);
                    scp.setDepartDate(calendar);

                    String tempTrainNo = stopdetaillist.get(i).getTrainNo();
                    SearchS2SResponseType s2ss = stationContract.getStopStations2(scp);
                    if (s2ss != null && s2ss.getTrains() != null && s2ss.getTrains().size() > 0) {
                        if (DateUtils.addMinutes(s2ss.getTicketLeftTime().getTime(), Temps2s)
                                .after(DateUtils.getDateFromTimestamp(DateUtils.getCurFullTimestamp())))
                        {
                            Optional<TrainInfo> trainInfo = s2ss.getTrains().stream().filter(t -> t.getTrainNo().equals(tempTrainNo)).findFirst();
                            if (!trainInfo.isPresent()) {
                                //列表中没有该车次--拒绝
                                tempStop.setStatusSms(TempStatus);
                                tempStop.setRemarkReject("拒绝--列表中没有该车次");
                                //tempStop.setOperator("system");
                            } else {
                                if (StringUtils.isEmpty(trainInfo.get().getNote())) {
                                    //在售--拒绝
                                    tempStop.setStatusSms(TempStatus);
                                    tempStop.setRemarkReject("拒绝--已在售");
                                    //tempStop.setOperator("system");
                                } else {
                                    //停运 --update checktime
                                    tempStop.setCheckTime(new Timestamp((new Date()).getTime()));
                                }
                            }
                            result = extStoprunningTrainInfo.update(tempStop);
                            if (result == 0) {
                                Map<String, String> loginfo = new HashMap<String, String>();
                                loginfo.put("Tid", String.valueOf(stopdetaillist.get(i).getTid()));
                                CLogger.info("CheckStopInfo", JSON.toJSONString(stopdetaillist.get(i)), loginfo);
                            }
                        }
//                        if (DateUtils.addMinutes(s2ss.getTicketLeftTime().getTime(), Temps2s)
//                                .before(DateUtils.getDateFromTimestamp(DateUtils.getCurFullTimestamp()))) {
//                            tempStop.setStatusSms(TempStatus);
//                            tempStop.setRemarkReject("系统检测到列车在售,自动忽略停运状态");
//                            tempStop.setOperator("system");
//                        }
                    }
                }
            }
        } catch (Exception ex) {
            CLogger.error("checkStopInfo", ex);
        }
    }

}
