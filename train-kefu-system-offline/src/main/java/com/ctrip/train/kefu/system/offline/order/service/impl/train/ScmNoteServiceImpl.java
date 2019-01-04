package com.ctrip.train.kefu.system.offline.order.service.impl.train;

import com.alibaba.fastjson.JSON;
import com.ctrip.train.kefu.system.offline.notice.dao.ExScmNoteInfo;
import com.ctrip.train.kefu.system.offline.order.service.ScmNoteService;
import com.ctrip.train.kefu.system.offline.order.vm.train.order.VmRemarks;

import common.log.CLogger;
import common.util.DateUtils;
import dao.ctrip.ctrainpps.entity.ScmNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Service
public class ScmNoteServiceImpl implements ScmNoteService {
    @Autowired
    private ExScmNoteInfo exScmNoteInfo;


    @Override
    public List<VmRemarks> searchRemarksList(String orderId) {
        List<VmRemarks> resultList=new ArrayList<>();

        List<ScmNote> list=exScmNoteInfo.searchScmNoteList(orderId);
        if (list!=null&&list.size()>0){
            for (ScmNote sn:list){
                VmRemarks temp=new VmRemarks();
                temp.setOperator(sn.getOperator());
                temp.setOrderId(sn.getOrderID());
                temp.setTid(sn.getTid());
                temp.setNode(sn.getNote());
                temp.setOperatorTime(sn.getOperateTime());
                resultList.add(temp);
            }
        }
        CLogger.info("OrderOperate", JSON.toJSONString(resultList));
        return resultList;
    }

    @Override
    public int addRemarks(String orderId, String remarks,String opUserName) {
        try {
            ScmNote sn=new ScmNote();
            sn.setOrderID(orderId);
            sn.setOperator(opUserName);
            sn.setOperateTime(DateUtils.getCurDateStr());
            sn.setNote(remarks);
            return exScmNoteInfo.insert(sn);
        } catch (SQLException e) {
            CLogger.error("getQueryOrderDetail", e);
            return 0;
        }
    }
}
