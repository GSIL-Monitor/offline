package com.ctrip.train.kefu.system.offline.two.service;

import com.ctrip.train.kefu.system.offline.two.vm.VMS2S;
import com.ctrip.train.kefu.system.offline.two.vm.VMTwo;
import com.ctrip.train.ticketagent.service.client.AddcheckResultResponse;
import com.ctrip.train.ticketagent.service.client.GetCheckResultResponse;
import com.ctrip.train.ticketagent.service.client.SetNoTicketResponse;
import com.ctrip.train.ticketagent.service.client.UpdateDeliveryOrderForSecPushResponse;
import dao.ctrip.ctrainpps.entity.ScmTwoTripTable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public interface TwoTripService {
    /**
     * 根据订单号获取scmtwotrip
     * @param orderId
     * @return
     * @throws
     */
    List<ScmTwoTripTable> getScmTwoTripList(String orderId) throws SQLException;
    /**
     * 根据订单号查询订单详情
     * OfflineOrderDetail
     */
    VMTwo getOfflineOrderDetailByOrderId(String orderId)  ;

    /**
     * 推荐时再次查询车票余数
     * @param map
     * @return
     * @throws Exception
     */
    VMS2S searchS2S(Map<String,String> map)  ;
    /**
     *
     * @param map
     * @return
     * @throws Exception
     */
    VMTwo queryTrainsByDate(Map<String,String> map)   ;

    /**
     *
     * 确定推荐，根据座位席下单
     * @param map
     * @return
     * @throws Exception
     */
    UpdateDeliveryOrderForSecPushResponse tryTwoPush(Map<String,String> map)  ;

    /**
     * 增加实时查询余票任务
     * @param map
     * @return
     * @throws Exception
     */
    AddcheckResultResponse addTraincheck(Map<String,String> map)  ;

    /**
     * 查询余票信息
     * @param map
     * @return
     * @throws Exception
     */
    GetCheckResultResponse queryticketLeft(Map<String,Object> map)  ;

    /**
     * 取消推荐 调用无票接口
     */
    SetNoTicketResponse twoPushNoTicket(Map<String,String> map)  ;

}
