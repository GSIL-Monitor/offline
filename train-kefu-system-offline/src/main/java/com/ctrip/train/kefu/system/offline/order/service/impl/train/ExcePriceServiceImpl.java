package com.ctrip.train.kefu.system.offline.order.service.impl.train;

import com.ctrip.offlineBase.LoginState.EmpsInformationEntity;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.train.cartel.adminservice.contract.GetOrderDetailResponseType;
import com.ctrip.train.kefu.system.client.offline.car.ZhixingCarContract;
import com.ctrip.train.kefu.system.offline.order.dao.ExtChatConfigDao;
import com.ctrip.train.kefu.system.offline.order.dao.ExtExcePriceDao;
import com.ctrip.train.kefu.system.offline.order.dao.ExtScmSmallEnum;
import com.ctrip.train.kefu.system.offline.order.enums.train.ExecCheckStatusEnum;
import com.ctrip.train.kefu.system.offline.order.service.ExcePriceService;
import com.ctrip.train.kefu.system.offline.order.vm.ExcePriceEx;
import common.credis.CRedisHelper;
import common.log.CLogger;
import common.util.SendMailUtils;
import dao.ctrip.ctrainpps.entity.ExcePrice;
import dao.ctrip.ctrainpps.entity.ScmSmallEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * Created by jian_ji n 2018/7/2.
 */
@Service
public class ExcePriceServiceImpl implements ExcePriceService {

    @Autowired
    private ExtScmSmallEnum scmSmallEnumDao;

    @Autowired
    private ExtExcePriceDao excePriceDao;

    @Autowired
    private ExtChatConfigDao extChatConfigDao;

    @Autowired
    private ZhixingCarContract zhixingCarContract;

    /**P
     * 更新异常件信息
     * type : add   update
     * priceType 1 退款 3 赔款
     * @return
     * @throws Exception
     */
    public Map<String, String> excepricedata(ExcePriceEx excePriceData,EmpsInformationEntity empInfo)
    {
        Map<String, String> resultmodel = new HashMap<String, String>();
        if (excePriceData.getTotalPrice().compareTo(BigDecimal.ZERO)!=0)
        {
            if (!checkInfo(excePriceData,resultmodel))
            {
                return resultmodel;
            }
        }
        Integer reslut = 0;

        if (excePriceData.getExceID() == 0)
        {
            ExcePrice entity = new ExcePrice();
            entity.setExceID(excePriceData.getExceID());
            entity.setOrderNumber(excePriceData.getOrderNumber());
            entity.setResponsibility(excePriceData.getResponsibility());
            entity.setResponsQuestion(excePriceData.getResponsQuestion());
            entity.setResponsReason(excePriceData.getResponsReason());
            entity.setQuestionDesc(excePriceData.getQuestionDesc());
            entity.setBankAccountName(excePriceData.getBankAccountName());
            entity.setBankAddress(excePriceData.getBankAddress());
            entity.setBankName(excePriceData.getBankName());
            entity.setBankNo(excePriceData.getBankNo());

            entity.setDelivery(0);  //这个是必填栏位，赋值0 ，如果有问题可以拿掉
            entity.setDepartment(0);

            reslut = addexceprice(entity,excePriceData,empInfo);
        }else
        {
            reslut = updateexceprice(excePriceData);
        }

        if(reslut > 0){
            resultmodel.put("status","0");
        }else{
            resultmodel.put("status","1");
            resultmodel.put("msg","更新失败");
        }
        return resultmodel;
    }

    /**
     * 添加异常件
     * @throws Exception
     */
    public Integer addexceprice(ExcePrice entity,ExcePriceEx excePriceData,EmpsInformationEntity empInfo) {

        Integer reslut = 0;
        try {
            entity.setChannel(excePriceData.getChannel());
            entity.setOrderDate(excePriceData.getOrderDate());
            entity.setCreateDate(new Timestamp((new Date()).getTime()));
            entity.setInsertName(empInfo.getEmpName() + "(" + empInfo.getEid() + ")");
            entity.setProductLine(excePriceData.getProductLine());
            entity.setBackPrice(null);
            entity.setAccpetCompany(0);
            entity.setAccpetName(0);
            entity.setBackReason(0);
            entity.setIsDelete(1);
            entity.setAuditDate(new Timestamp((new Date()).getTime()));
            if (excePriceData.getRepaPrice() != null && !(excePriceData.getRepaPrice().compareTo(BigDecimal.ZERO) == 0)) {
                entity.setRepaPrice(excePriceData.getRepaPrice());
            }
            //退款
            if (excePriceData.getTotalPrice() != null && !(excePriceData.getTotalPrice().compareTo(BigDecimal.ZERO) == 0)) {
                entity.setOperatingType(1);
                entity.setTicketPrice(excePriceData.getTicketPrice());
                entity.setOtherPrice(excePriceData.getOtherPrice());
                entity.setTotalPrice(excePriceData.getTotalPrice());
                if (excePriceData.getTotalPrice().compareTo(new BigDecimal("1000.00")) == -1) {
                    entity.setIsAudit(ExecCheckStatusEnum.ExecCheckServicePending.getCode()); //专车先服务审核在财务审核
                } else {
                    entity.setIsAudit(ExecCheckStatusEnum.ExecCheckServiceManagerPending.getCode()); //大于1000 ，主管审核
                }
                if (excePriceData.getTicketPrice() != null && excePriceData.getTicketPrice().compareTo(BigDecimal.ZERO) == 1)  //退款金额走线上渠道 发邮件通知
                {
                    saveInsertorMailOnline(entity, empInfo.getEmail());
                }
                reslut = excePriceDao.insert(entity);
                if (reslut > 0) {
                    mailToServerChecker(entity, empInfo.getEmail());   //邮件通知服务审核人   需要确认一下是否使用
                }
            }
        }catch(Exception ex){
            CLogger.error("专车异常件addexceprice",ex);
        }
        return reslut;
    }

        /**
         * 更新异常件
         * excePriceData 数据源
         * priceType 1 退款 2 赔款
         */
    public Integer updateexceprice(ExcePriceEx excePriceData){

        Integer updateNum = 0;
        try {

            ExcePrice tempExce = excePriceDao.queryByPk(excePriceData.getExceID());
            tempExce.setResponsibility(excePriceData.getResponsibility());
            tempExce.setResponsQuestion(excePriceData.getResponsQuestion());
            tempExce.setResponsReason(excePriceData.getResponsReason());
            tempExce.setQuestionDesc(excePriceData.getQuestionDesc());
            tempExce.setBankAccountName(excePriceData.getBankAccountName());
            tempExce.setBankAddress(excePriceData.getBankAddress());
            tempExce.setBankName(excePriceData.getBankName());
            tempExce.setBankNo(excePriceData.getBankNo());

            tempExce.setAuditDate(new Timestamp((new Date()).getTime()));
            tempExce.setRepaPrice(excePriceData.getRepaPrice());
            //退款

            tempExce.setOperatingType(1);
            tempExce.setTicketPrice(excePriceData.getTicketPrice());
            tempExce.setOtherPrice(excePriceData.getOtherPrice());
            tempExce.setTotalPrice(excePriceData.getTotalPrice());
            if (excePriceData.getTotalPrice().compareTo(new BigDecimal("1000.00")) == -1) {
                tempExce.setIsAudit(ExecCheckStatusEnum.ExecCheckServicePending.getCode()); //专车先服务审核在财务审核
            } else {
                tempExce.setIsAudit(ExecCheckStatusEnum.ExecCheckServiceManagerPending.getCode()); //大于1000 ，主管审核
            }

            DalHints hints = new DalHints();
            hints.updateNullField();  //更新null字段;
            updateNum = excePriceDao.update(hints.updateNullField(),tempExce);
        }catch (Exception ex){
            CLogger.error("专车异常件updateexceprice",ex);
        }
        return updateNum;
    }

    /**
     * 获取智行专车订单详情
     * @param orderNumber
     * @return
     */
    public GetOrderDetailResponseType getOrderDetail(Long orderNumber){
        GetOrderDetailResponseType  response = new GetOrderDetailResponseType();
        GetOrderDetailResponseType responsetemp = zhixingCarContract.getOrderDetail(orderNumber);
        if(responsetemp!=null){
             response=responsetemp;
        }
        return response;
    }

    @Override
    public int searchExcePriceCount(String orderId) {
        return excePriceDao.searchExcePriceCount(orderId);
    }

    /**
     * 校验银行卡等信息
     * @param
     * @return
     */
    protected boolean checkInfo(ExcePriceEx excePriceData, Map<String, String> resultmodel )
    {
        GetOrderDetailResponseType responset = zhixingCarContract.getOrderDetail(Long.parseLong(excePriceData.getOrderNumber()));
        if(responset!=null){
            if(excePriceData.getTicketPrice() != null && responset.getPayPrice() != null && excePriceData.getTicketPrice().compareTo(responset.getPayPrice()) == 1){
                resultmodel.put("status","1");
                resultmodel.put("msg","退款金额不能大于订单金额");
                return false;
            }
        }

        String rs = checkBankAccount(excePriceData.getBankNo());
        if (rs.equals("OK"))
        {
            return true;
        }
        else
        {
            resultmodel.put("status","1");
            resultmodel.put("msg",rs);
            return false;
        }
    }

    public String getProductname(Integer productLine){
       String Productname = "";
        switch (productLine) {
            case 40:
                Productname = "接送火车";
                break;
            case 41:
                Productname = "接送飞机";
                break;
            case 42:
                Productname = "专车";
                break;
        }
        return Productname;
    }


    protected String checkBankAccount(String account)
    {
        if (isNullOrEmpty(account))
        {
            return "OK";
        }

        if (isNullOrEmpty(account) || account.length() > 19 || account.length() < 16)    //长度限制
        {
            return "开户帐号长度错误";
        }
        //字符为数字
        char[] ch = account.toCharArray();
        for(char c : ch)
        {
            if (c < '0' || c > '9')
            {
                return "开户帐号不能为非数字或全角字符";
            }
        }
        return luhn(ch) ? "OK" : "开户账号格式校验错误";  //有些不符
    }

    /// <summary>
    /// Luhn算法校验银行卡格式
    /// </summary>
    /// <param name="account"></param>
    /// <returns></returns>
    protected Boolean luhn(char[] account)
    {
        final int CardS = 2;
        final int CardL = 10;
        int luhnSum = 0;
        for (int i = account.length - CardS, j = 0; i >= 0; j++, i--)
        {
            int k = account[i] - '0';
            if (j % CardS == 0)
            {
                k *= CardS;
                k = k / CardL + k % CardL;
            }
            luhnSum += k;
        }
        char bit = (luhnSum % CardL == 0) ? '0' : (char)((CardL - luhnSum % CardL) + '0');
        return account[account.length - 1] == bit;
    }

    public List<ScmSmallEnum> getDropdownList(String fieldType,long fkUpperTid,Integer value, Integer superId ){
        ScmSmallEnum model = new ScmSmallEnum();
        model.setFieldType(fieldType);
        model.setFkUpperTid(fkUpperTid);
        try {
            List<ScmSmallEnum> lst=scmSmallEnumDao.queryLike(model);
            if (lst!=null) {
                lst = lst.stream()
                        .sorted(Comparator.comparing(ScmSmallEnum::getTid))
                        .collect(Collectors.toList());
            }
            return lst;
        }
        catch (Exception ex)
        {
            CLogger.warn("GetDropdownList",ex);
            return  null;
        }
    }

    public ExcePrice getExcePriceData(long exceid)
    {
        ExcePrice respose = new ExcePrice();
        try {
            ExcePrice model = new ExcePrice();
            model.setExceID(exceid);
            respose = excePriceDao.queryByPk(model);
            if (respose == null) {
                respose = new ExcePrice();
            }
        }catch(Exception ex){
            CLogger.error("专车异常件getExcePriceData",ex);
        }
        return respose;
    }

    /**
     * 邮件通知服务审核人
     * @param entity
     */
    private void mailToServerChecker(ExcePrice entity,String email) {
        try {
            final int RedisDays = 10;
            String ExAutoRefund_InsertMailKey = "ExAutoRefund_InsertMailKey" + entity.getOrderNumber();
            CRedisHelper.set(ExAutoRefund_InsertMailKey, email, RedisDays, TimeUnit.DAYS);  // 缓存10天 入录人邮箱，当审核失败时 邮件通知

            String content = String.format("发起人:%s, 订单号: %s, <p>%s</p> 进入异常件自动退款流程", entity.getInsertName(), entity.getOrderNumber(),
                    "http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/SearchExcePage.aspx?orderNumber=" + entity.getOrderNumber() + "&orderCheckStatus=3");
            SendMailUtils.sendNormalEmail(getEXRecipients(entity.getProductLine(), entity.getTotalPrice()), null, "TrainOffline@ctrip.com", "TrainOffline", "专车:异常件服务待审核", content);

        }catch(Exception ex){
            CLogger.error("专车异常件mailToServerChecker",ex);
        }
    }

    /**
     * 线上退款时也需要保存申请人邮箱
     * @param entity
     */
    private void saveInsertorMailOnline(ExcePrice entity,String email)
    {
        String ExAutoRefund_InsertMailKey = "ExAutoRefund_InsertMailKey" + entity.getOrderNumber();
        CRedisHelper.set(ExAutoRefund_InsertMailKey, email, 10,TimeUnit.DAYS);  // 缓存10天 入录人邮箱，当审核失败时 邮件通知
    }

    public List<String> getEXRecipients(String productLine, BigDecimal totalAmount)
    {
        final int GenAmountType = 1;   //普通审核
        final int BossAmountType = 2;   //主管审核
        List<String> configValuesList = new ArrayList<String>();
        try {
            int amountType = GenAmountType;
            if (totalAmount.compareTo(new BigDecimal("1000.00")) == -1) {
                amountType = GenAmountType;
            } else {
                amountType = BossAmountType;
                productLine = "40";
            }


            configValuesList.add("jian_ji@Ctrip.com");
            String configKey = String.format("Offline_SCPEXAutoMail_%s_%s", productLine, amountType);
            String configValue = extChatConfigDao.getConfig(configKey);
            if (!isNullOrEmpty(configValue)) {
                configValuesList = Arrays.asList(configValue.split("\\;"));
            }
        }catch(Exception ex){
            CLogger.error("专车异常件getEXRecipients",ex);
        }
        return configValuesList;
    }


    private  boolean isNullOrEmpty(String param) {
        return param == null || param.trim().length() == 0;
    }
}
