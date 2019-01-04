package com.ctrip.train.kefu.system.offline.notice.service;

import dao.ctrip.ctrainpps.entity.ScmSmallEnum;
import java.util.List;


public interface NoticeEnumService {

    /**
     *获取通知枚举数据
     */
    List<ScmSmallEnum> GetNoticeTypeEnum(String productLine);

    /**
     * 获取枚举二级分类
     */
    List<ScmSmallEnum> GetSecondLevelEnum(Long id);

    /**
     * 获取通知一级分类
     */
    List<ScmSmallEnum> searchFirstNoticeTypes(int envenType,int productLine);

    /**
     * 获取通知一级分类
     */
    List<ScmSmallEnum> searchSecondNoticeTypes(int firstId);
}
