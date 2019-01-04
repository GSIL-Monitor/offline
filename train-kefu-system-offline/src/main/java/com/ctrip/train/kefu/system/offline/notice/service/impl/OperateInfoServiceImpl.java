package com.ctrip.train.kefu.system.offline.notice.service.impl;

import com.ctrip.train.kefu.system.offline.notice.dao.ExtOperateInfo;
import com.ctrip.train.kefu.system.offline.notice.enums.NoticeOperateTypeEnum;
import com.ctrip.train.kefu.system.offline.notice.service.OperateInfoService;
import common.log.CLogger;
import common.util.DateUtils;
import dao.ctrip.ctrainpps.entity.OperateInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperateInfoServiceImpl  implements OperateInfoService{

    @Autowired
    private ExtOperateInfo operateInfo;

    /**
     *根据通知id 获取通知操作记录
     */
    public List<OperateInfo> getOperateList(Long noticeid)  {
        OperateInfo model=new  OperateInfo();
        model.setTid(noticeid);
        try {
            List<OperateInfo> lst=operateInfo.queryLike(model);
            if (lst!=null) {
                lst = lst.stream()
                         .sorted(Comparator.comparing(OperateInfo::getOperateTime))
                         .collect(Collectors.toList());
            }
            return lst;
        }
        catch (Exception ex)
        {
            CLogger.warn("getOperateList",ex);
            return  null;
        }
    }

    /**
     *添加通知操作记录
     */
    public Integer insertOperate(Long noticeid,String content,String opuser,NoticeOperateTypeEnum type)  {
        OperateInfo model=new  OperateInfo();
        model.setTid(noticeid);
        model.setOperateComment(content);
        model.setOperateUser(opuser);
        model.setOperateTime(DateUtils.getCurFullTimestamp());
        model.setOperateType(type.getState());
        try {
            int result=operateInfo.insert(model);
            return result;
        }
        catch (Exception ex)
        {
            CLogger.warn("insertOperate",ex);
            return  0;
        }
    }
}
