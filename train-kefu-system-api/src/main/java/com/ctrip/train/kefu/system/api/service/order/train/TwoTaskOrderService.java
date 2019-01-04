package com.ctrip.train.kefu.system.api.service.order.train;


import com.ctrip.train.kefu.system.api.contract.QueryTwoTaskOrderStateRequsetType;
import com.ctrip.train.kefu.system.api.contract.QueryTwoTaskOrderStateResponseType;
import com.ctrip.train.kefu.system.api.dao.order.ExtTwoTaskOrder;
import com.ctrip.train.kefu.system.api.infrastructure.constants.Result;
import dao.ctrip.ctrainpps.entity.ScmTwoTaskTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwoTaskOrderService {

    @Autowired
    private ExtTwoTaskOrder extTwoTaskOrder;

    /**
     * 查询二推订单状态
     */
    public QueryTwoTaskOrderStateResponseType QueryTwoTaskOrderState(QueryTwoTaskOrderStateRequsetType requset){
        QueryTwoTaskOrderStateResponseType response=new QueryTwoTaskOrderStateResponseType();
        if (requset==null){
            response.setCode(Result.FAIL);
            response.setMsg(Result.ParamIsEmpty("request"));
            return  response;
        }
        if (requset.getPartnerOrderId()==null||requset.getPartnerOrderId().isEmpty()){
            response.setCode(Result.FAIL);
            response.setMsg(Result.ParamIsEmpty("OrderID"));
            return  response;
        }
        ScmTwoTaskTable twoTask=extTwoTaskOrder.queryTwoTaskOrderState(requset.getPartnerOrderId());
        if(twoTask!=null){
            response.setCode(Result.SUCCESS);
            response.setProcessingState(twoTask.getProcessingState());
            return  response;
        }else {
            response.setCode(Result.FAIL);
            response.setMsg("订单不存在！");
            return  response;
        }
    }
}
