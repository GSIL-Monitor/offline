package com.ctrip.train.kefu.system.offline.common.utils;

import com.ctrip.train.kefu.system.offline.order.vm.train.refund.VmRefundSxfRequest;
import com.ctrip.train.kefu.system.offline.order.vm.train.refund.VmRefundSxfResponse;
import common.qconfig.QConfigHelper;
import common.util.DateUtils;
import common.util.StringUtils;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

public class TrainTicketUtils {


    /**
     * 是否可以退票
     *
//     * @param isReturnTrue         是否直接返回true 比喻 停运的车次都可以退票，配送票除外，
//     * @param isDistributionTicket 是否配送票
     * @param isChangeTicket       是否改签票
     * @param ticketState          票状态
     * @param fromStationName      出发站
     * @param toStationName        达到站
     * @param ticketTime           车票时间
     * @return
     */
    public static Pair<Boolean, String> isEnableReturnTicket( boolean isChangeTicket,String ticketState, String fromStationName,
                                                              String toStationName, String ticketTime) {
        // Pair<Boolean, String> pair=new Pair<Boolean, String>(false, "默认不可退");

        //配送票不可退
        //停运车次且不是配送票的直接显示退票按钮
//        if (isDistributionTicket) {
//            return new Pair<Boolean, String>(false, "配送票不可退");
//        }

//        if (isReturnTrue) {
//            return new Pair<Boolean, String>(true, "停运非配送票可退");
//        }

        Date tickedTime = DateUtils.parseDate(ticketTime);//车票时间
        long second = tickedTime.getTime() - DateUtils.timeMillis();
        long minute = second / 60000;//距离发车时间还有多少分钟

        //改签票 ticketStatus //0改签中1改签成功2改签失败3退票中4退票成功5退票失败
        //出发站或者到达站是香港西九龙的改签票改签成功后无法退票
        if (isChangeTicket
                && (ticketState.trim().equals("1") || ticketState.trim().equals("5"))
                && (fromStationName.trim().equals("香港西九龙") || toStationName.trim().equals("香港西九龙"))) {
            return new Pair<Boolean, String>(false, "出发站或者到达站是香港西九龙的改签票改签成功后无法退票");
        } else {
            //未停运 出发站是香港西九龙的大于60分钟可退票
            //未停运 出发站不是香港西九龙的大于30分钟可退票
            if ((fromStationName.trim().equals("香港西九龙") && minute > 60)
                    || (!fromStationName.trim().equals("香港西九龙") && minute > 30)
                    ) {

                //原始票中 ticketStatus 0未退 1已退 2退票中 3退票失败 4改签中 5 改签成功 原票作废状态
                //原始票中，0：未退   3：退票失败  可以退票
                //改签票 ticketStatus //0改签中1改签成功2改签失败3退票中4退票成功5退票失败
                //改签票中，1：改签成功  5：退票失败  可以退票
                if ((!isChangeTicket && (ticketState.trim().equals("0") || ticketState.trim().equals("3")))
                        || (isChangeTicket && (ticketState.trim().equals("1") || ticketState.trim().equals("5")))) {
                    return new Pair<Boolean, String>(true, "");
                } else {
                    if (!isChangeTicket) {
                        return new Pair<Boolean, String>(false, "原票状态为未退和退票失败可以退票，其余状态不可退票");
                    } else {
                        return new Pair<Boolean, String>(false, "改签票状态为改签成功和退票失败可以退票，其余状态不可退票");
                    }
                }
            } else {
                if (fromStationName.trim().equals("香港西九龙")) {
                    return new Pair<Boolean, String>(false, "发车前30分内不可退票");
                } else {
                    return new Pair<Boolean, String>(false, "出发站为香港西九龙发车前60分内不可退票");
                }
            }
        }
    }

    /**
     * 退票手续费计算
     * @param request
     * @return
     */
    public static VmRefundSxfResponse getRefundSxf(VmRefundSxfRequest request) {
        VmRefundSxfResponse response = new VmRefundSxfResponse();
        response.setKtPrice(BigDecimal.ZERO);
        response.setTotalPrice(BigDecimal.ZERO);
        response.setXsfPrice(BigDecimal.ZERO);
        response.setXsfRate(0);

        if (request == null) return response;

        //获取系统当前时间戳 调试的时候换成日志记录的时间
        //DateUtils.parseDate("2018-10-11 12:00:00").getTime();
        long nowTimeMillis = DateUtils.timeMillis();
        Date nowDate= DateUtils.currentTime();
        //西九龙
//        香港西九龙高铁跨境列车，退票手续费
//        发车时间前48小时内办理退票的，收取票价的50%手续费；
//        发车时间前48小时至第14天办理的，收取票价的30%手续费；
//        发车时间前15天及以上办理的，收取票价的5%手续费。
//        退票费按元计算，不足一元的部分舍去免收。
        if ("香港西九龙".equals(request.getToStationName())){
            if (DateUtils.addDays(DateUtils.currentTime(), 2).getTime()>request.getTicketDate().getTime()) {
                //发车时间前48小时内办理退票的，收取票价的50%手续费；
                response.setXsfPrice(request.getTotalPrice().multiply( new BigDecimal(0.5)));
                response.setXsfRate(50);
            } else if (DateUtils.addDays(DateUtils.currentTime(), 2).getTime()<request.getTicketDate().getTime()
                    && DateUtils.addDays(DateUtils.currentTime(), 15).getTime()>=request.getTicketDate().getTime()) {
                //两天之内一天外
                //发车时间前48小时至第14天办理的，收取票价的30%手续费；
                response.setXsfPrice(request.getTotalPrice().multiply( new BigDecimal(0.3)));
                response.setXsfRate(30);
            } else if (DateUtils.addDays(DateUtils.currentTime(), 15).getTime()<request.getTicketDate().getTime()){
                //发车时间前15天及以上办理的，收取票价的5%手续费。
                response.setXsfPrice(request.getTotalPrice().multiply( new BigDecimal(0.05)));
                response.setXsfRate(5);
            }

            //按元计算不足一元免去
            if (response.getXsfPrice().doubleValue()<1){
                response.setXsfPrice(BigDecimal.ZERO);
            }else {
                int temp=response.getXsfPrice().intValue();
                response.setXsfPrice(new BigDecimal(temp));
            }
            response.setKtPrice(request.getTotalPrice().subtract(response.getXsfPrice()));
            return response;
        }else if("九龙".equals(request.getToStationName())){
//            广九票普通列车，退票手续费（出发到达站为九龙的）
//            发车前2小时-2天办理的，收取票价的50%手续费；
//            发车前3-14天办理的，收取票价的30%手续费；
//            发车时间前15天以上办理的，不收手续费，另核收15元手续费，最终退款以广州东站实际退款金额为准。
            if (DateUtils.addHours(DateUtils.currentTime(), 2).getTime()<request.getTicketDate().getTime()
                    && DateUtils.addDays(DateUtils.currentTime(), 2).getTime()>=request.getTicketDate().getTime()) {
                //发车前2小时-2天办理的，收取票价的50%手续费
                response.setXsfPrice(request.getTotalPrice().multiply( new BigDecimal(0.5)));
                response.setXsfRate(50);
            } else if (DateUtils.addDays(DateUtils.currentTime(), 15).getTime()>request.getTicketDate().getTime()
                    && DateUtils.addDays(DateUtils.currentTime(), 3).getTime()<=request.getTicketDate().getTime()){
                //发车前3-14天办理的，收取票价的30%手续费；
                response.setXsfPrice(request.getTotalPrice().multiply( new BigDecimal(0.3)));
                response.setXsfRate(30);
            }else if (DateUtils.addDays(DateUtils.currentTime(), 15).getTime()<=request.getTicketDate().getTime()){
                // 发车时间前15天以上办理的，不收手续费，另核收15元手续费，最终退款以广州东站实际退款金额为准。
                response.setXsfPrice(new BigDecimal(15));
            }
            //四舍五入
            BigDecimal temp=response.getXsfPrice().setScale(2, BigDecimal.ROUND_HALF_UP);
            response.setXsfPrice(temp);
            response.setKtPrice(request.getTotalPrice().subtract(response.getXsfPrice()));
            return response;
        }
        /**
         * 境内车次
         */
        else {
            //票总额
            response.setTotalPrice(request.getTotalPrice());
            //是否不收取手续费 停运不收取手续费
            if (request.getNoHasSxf()) {
                return response;
            }
            if (request.getTicketType() == 0)//原票
            {
                if (DateUtils.addDays(DateUtils.currentTime(), 1).getTime()>request.getTicketDate().getTime()) {
                    //24小时以内，收取票面20%退票费。
                    //  response.xsfPrice += item.totalPrice * (decimal)0.2;
                    response.setXsfPrice(request.getTotalPrice().multiply( new BigDecimal(0.2)));
                    response.setXsfRate(20);
                } else if (DateUtils.addDays(DateUtils.currentTime(), 2).getTime()>request.getTicketDate().getTime()
                        && DateUtils.addDays(DateUtils.currentTime(), 1).getTime()<=request.getTicketDate().getTime()) {
                    //24小时～48小时，收取票面10%退票费。
                    response.setXsfPrice(request.getTotalPrice().multiply( new BigDecimal(0.1)));
                    response.setXsfRate(10);
                } else if (DateUtils.addDays(DateUtils.currentTime(), 15).getTime()>request.getTicketDate().getTime()
                        && DateUtils.addDays(DateUtils.currentTime(), 2).getTime()<=request.getTicketDate().getTime()){
                    // 48小时-15天（含），收取票面5%退票费。
                    //  response.xsfPrice += item.totalPrice * (decimal)0.05;
                    response.setXsfPrice(request.getTotalPrice().multiply( new BigDecimal(0.05)));
                    response.setXsfRate(5);
                }else if (DateUtils.addDays(DateUtils.currentTime(), 15).getTime()<=request.getTicketDate().getTime()){
                    //发车前15天（不含）以上退票的，不收取退票费。
                    response.setXsfPrice(BigDecimal.ZERO);
                }
            }
            //开车前48小时～15天期间内，改签或者变更到站至距离开车15天以上的其他列车，又在距开车15天前退票费，仍核收5%的退票费，。
            else if (request.getTicketType() == 1)//改签票
            {
                if (isSpringFestival()){
                    //改签至春运 改签或变更到站后的车票乘车日期在春运期间的，退票时一律按开车前不足24小时收退票
                    response.setXsfPrice(request.getTotalPrice().multiply( new BigDecimal(0.2)));
                    response.setXsfRate(20);
                }else {
                    if (DateUtils.addDays(DateUtils.currentTime(), 1).getTime()>request.getChangeTicketDate().getTime()) {
                        //24小时以内，收取票面20%退票费。
                        //  response.xsfPrice += item.totalPrice * (decimal)0.2;
                        response.setXsfPrice(request.getTotalPrice().multiply( new BigDecimal(0.2)));
                        response.setXsfRate(20);
                    } else if (DateUtils.addDays(DateUtils.currentTime(), 2).getTime()>request.getChangeTicketDate().getTime()
                            && DateUtils.addDays(DateUtils.currentTime(), 1).getTime()<=request.getChangeTicketDate().getTime()) {
                        //24小时～48小时，收取票面10%退票费。
                        //response.xsfPrice += item.totalPrice * (decimal)0.1;
                        response.setXsfPrice(request.getTotalPrice().multiply( new BigDecimal(0.1)));
                        response.setXsfRate(10);
                    } else if (DateUtils.addDays(DateUtils.currentTime(), 15).getTime()>request.getChangeTicketDate().getTime()
                            && DateUtils.addDays(DateUtils.currentTime(), 2).getTime()<=request.getChangeTicketDate().getTime()){
                        // 48小时-15天（含），收取票面5%退票费。
                        //  response.xsfPrice += item.totalPrice * (decimal)0.05;
                        response.setXsfPrice(request.getTotalPrice().multiply( new BigDecimal(0.05)));
                        response.setXsfRate(5);
                    }else if (DateUtils.addDays(DateUtils.currentTime(), 15).getTime()<=request.getChangeTicketDate().getTime()){
                        //发车前15天（不含）以上退票的，不收取退票费。
                        if (request.getChangeSuccessDate() != null && request.getTicketDate() != null) {
                            if (request.getChangeSuccessDate().getTime() >= DateUtils.addDays(request.getTicketDate(), -15).getTime()) {
                                response.setXsfPrice(request.getTotalPrice().multiply( new BigDecimal(0.05)));
                                response.setXsfRate(5);
                            }
                        }else {
                            response.setXsfPrice(BigDecimal.ZERO);
                        }
                    }
                }
            }
            if (response.getXsfPrice().doubleValue()>0){
                //尾数<2.5  0
                //2.5<尾数<7.5 5角
                //尾数>=7.5  1元
                //退票若产生手续费最低收取2元
                String xsf= response.getXsfPrice().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString();
                String[] xsfs = xsf.split("\\.");
                int dprice=0;
                if (xsfs.length>1){
                    dprice = Integer.valueOf(xsfs[1]);//小数部分
                }
                if (dprice < 25) {
                    response.setXsfPrice(new BigDecimal(xsfs[0]));
                } else if (25 <= dprice && dprice < 75) {
                    response.setXsfPrice(new BigDecimal(xsfs[0]+".5") );
                } else if (75 <= dprice && dprice < 100) {
                    response.setXsfPrice(new BigDecimal(Integer.valueOf(xsfs[0]) + 1));
                }
                //退票手续费最低按2元收取，最终退款以铁路局实退为准。
                if (response.getXsfPrice().intValue() > 0 && response.getXsfPrice().intValue() < 2) {
                    response.setXsfPrice(new BigDecimal(2));
                }
            }
            response.setKtPrice(request.getTotalPrice().subtract(response.getXsfPrice()));
            return response;
        }
    }

    /**
     * 是否是春运
     * @return
     */
    public static Boolean isSpringFestival(){
        String springFestival= QConfigHelper.getAppSetting("SpringFestival");//2018-12-17|2018-01-02
        Date nowDate= DateUtils.currentTime();
        if (StringUtils.isNotBlank(springFestival)){
            String[] springDates = springFestival.split("\\|");
            if (springDates!=null&&springDates.length>0){

                if (nowDate.after(DateUtils.parseDate(springDates[0]))&&nowDate.after(DateUtils.parseDate(springDates[1])))
                    return true;
            }
        }
        return false;
    }

}
