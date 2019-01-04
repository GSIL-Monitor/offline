package com.ctrip.train.kefu.system.offline.order.service;

import com.ctrip.offlineBase.LoginState.EmpsInformationEntity;
import com.ctrip.soa.train.javaxproductservice.v1.SearchProductByUidResponseType;
import com.ctrip.train.cartel.adminservice.contract.GetOrderDetailResponseType;
import com.ctrip.train.kefu.system.offline.order.vm.ExcePriceEx;
import com.ctrip.train.kefu.system.offline.order.vm.VmMemberCardRights;
import dao.ctrip.ctrainpps.entity.ExcePrice;
import dao.ctrip.ctrainpps.entity.ScmSmallEnum;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by jian_ji on 2018/7/2.
 */
@Service
public interface ExcePriceService {

    /**
     * 新增 更新异常件
     * @param excePriceData
     * @throws Exception
     */
    Map<String, String> excepricedata(ExcePriceEx excePriceData,EmpsInformationEntity empInfo);

    /**
     * 获取异常件详情
     * @param exceid
     * @return
     * @throws Exception
     */
    ExcePrice getExcePriceData(long exceid);

    /**
     * 获取下拉选单配置
     * @param fkUpperTid
     * @param value
     * @param superId
     * @return
     * @throws Exception
     */
    List<ScmSmallEnum> getDropdownList(String fieldType,long fkUpperTid,Integer value, Integer superId );

    /**
     * 获取产品线名称
     * @param productLine
     * @return
     * @throws Exception
     */
    String getProductname(Integer productLine);

    /**
     * 获取智行专车订单详情
     * @param orderNumber
     * @return
     */
    GetOrderDetailResponseType getOrderDetail(Long orderNumber);

    /**
     * 异常件的个数
     * @param orderId
     * @return
     */
    int searchExcePriceCount(String orderId);

}
