package com.ctrip.train.kefu.system.job.worker.workers.notice;

import com.ctrip.train.kefu.system.job.config.CommonBusiness;
import com.ctrip.train.kefu.system.job.constants.PropertyFile;
import com.ctrip.train.kefu.system.job.enums.notice.EventTypeEnum;
import com.ctrip.train.kefu.system.job.enums.notice.ProductLineEnum;
import com.ctrip.train.kefu.system.job.worker.BaseWorker;
import com.ctrip.train.kefu.system.job.worker.service.notice.NoticeService;
import common.log.CLogger;
import common.util.DateUtils;
import common.util.SendMailUtils;
import dao.ctrip.ctrainpps.entity.NoticeComplainInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qunar.tc.qschedule.config.QSchedule;
import qunar.tc.schedule.Parameter;

import java.util.*;

@Component
public class NoticeTimeoutEmailWorker extends BaseWorker{
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private CommonBusiness commonBusiness;

    /**
     * 超时未处理
     */
    @QSchedule(value = "com.ctrip.train.offline.NoticeTimeoutEmailWorker")
    @Override
    public void doWorker(Parameter parameter) {

        //邮件数据
        StringBuilder airMailContent =new StringBuilder();
        StringBuilder intFlightContent =new StringBuilder();
        StringBuilder trainMailContent =new StringBuilder();
        //半小时未分配的通知
        List<NoticeComplainInfo> noticeComplains =noticeService.searchNoticeAssigned();
        if (noticeComplains!=null&&noticeComplains.size()>0){
            Date time=DateUtils.addHours( new Date(),-1);
            for (NoticeComplainInfo notice:noticeComplains){
//                紧急半个小时 一般一个小时
                if (notice.getEmergeState()==1||(notice.getEmergeState()==0&&notice.getSendTime().before(time))){
                    String content= ProductLineEnum.convertByCode(notice.getProductLine()).getProductLineName()+"  "+
                            "发送时间:"+DateUtils.format(notice.getSendTime(),DateUtils.YMDHMS_UNDERLINED)+"  "+
                            EventTypeEnum.convertEventType(notice.getEnvenType()).getName()+
                            " 订单号为："+notice.getOrderID()+"; 通知单号为："+notice.getID()
                            +" 超时未分配,请及时处理！！！ <br />";
                    int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                    //机票通知机票 专车接送 通知工作时间：6：00-24：00  投诉工作时间：6：00-24：00
                    if (notice.getProductLine().equals("135")||notice.getProductLine().equals("138")){
                        airMailContent.append(content);
                    }else if (notice.getProductLine().equals("134")||notice.getProductLine().equals("137")||"3".equals(notice.getProductLine())){
                            //通知工作时间：9：00-23：00  2、投诉工作时间：9：00-18：00
                            if (notice.getEnvenType()==EventTypeEnum.Notice.getValue()&&hour>=9&&hour<=23){
                                trainMailContent.append(content);
                            }else if (notice.getEnvenType()==EventTypeEnum.Complain.getValue()&&hour>=9&&hour<=18) {
                                trainMailContent.append(content);
                            }
                    }else if (notice.getProductLine().equals("31")){
                            intFlightContent.append(content);
                    }
                }
            }
            //发送邮件
            Map<String,StringBuilder> mailMap=new HashMap<>();
            if (!airMailContent.toString().equals("")&&airMailContent.length()!=0)
                mailMap.put("airMailContent",airMailContent);
            if (!trainMailContent.toString().equals("")&&trainMailContent.length()!=0)
                mailMap.put("trainMailContent",trainMailContent);
            if (!trainMailContent.toString().equals("")&&trainMailContent.length()!=0)
                mailMap.put("intFlightContent",intFlightContent);
            if (!mailMap.isEmpty())
                SendEmail(mailMap);
        }
    }
    /**
     * 发送邮件
     * @return
     */
    public int SendEmail(Map<String,StringBuilder>  mailMap){
        String sender=commonBusiness.getAppSetting(PropertyFile.OFFLINE_MAIL_SENDER);
        String ccMail=commonBusiness.getAppSetting(PropertyFile.OFFLINE_MAIL_CC);
        String senderName=commonBusiness.getAppSetting(PropertyFile.OFFLINE_MAIL_SENDERNAME);
        List<String> cc=new ArrayList<>();
        cc.add(ccMail);
        int month= DateUtils.getCurrentMonth();
        String title="";
        try {
            for (Map.Entry<String, StringBuilder> entry : mailMap.entrySet()) {
                List<String> receipts=new ArrayList<>();
                if(entry.getKey().equals("trainMailContent")){
                    title =String.format("火车票通知超时未处理",month);
                    String [] recipient=commonBusiness.getAppSetting(PropertyFile.OFFLINE_TRAIN_RECEIPTS).split(",");
                    for(String s:recipient){
                        receipts.add(s);
                    }
                }else if(entry.getKey().equals("airMailContent")){
                    title =String.format("国内机票通知超时未处理",month);
                    String [] recipient=commonBusiness.getAppSetting(PropertyFile.OFFLINE_AIR_RECEIPTS).split(",");
                    for(String s:recipient){
                        receipts.add(s);
                    }
                }
                else if(entry.getKey().equals("airMailContent")){
                    title =String.format("国际机票通知超时未处理",month);
                    String [] recipient=commonBusiness.getAppSetting(PropertyFile.OFFLINE_AIR_RECEIPTS).split(",");
                    for(String s:recipient){
                        receipts.add(s);
                    }
                }
                SendMailUtils.sendNormalEmail(receipts,cc,sender,senderName,title,entry.getValue().toString());
            }
            return 1;
        } catch (Exception e) {
            CLogger.error("ApportionNoticeWorker邮件发送失败",e);
            return 0;
        }
    }
}
