package com.ctrip.train.kefu.system.offline.notice.service;

import com.ctrip.train.kefu.system.offline.notice.enums.NoticeOperateTypeEnum;
import dao.ctrip.ctrainpps.entity.OperateInfo;

import java.util.List;

public interface OperateInfoService {

    /**
     *根据订单号获取通知列表
     */
    List<OperateInfo> getOperateList(Long noticeid);

    /**
     *添加通知操作记录
     */
    Integer insertOperate(Long noticeid,String content,String opuser,NoticeOperateTypeEnum type);
}
