package com.ctrip.train.kefu.system.offline.notice.service.impl;

import com.ctrip.train.kefu.system.offline.notice.dao.ExtNoticeComplainInfo;
import com.ctrip.train.kefu.system.offline.notice.enums.EmergencyStateEnum;
import com.ctrip.train.kefu.system.offline.notice.enums.EventTypeEnum;
import com.ctrip.train.kefu.system.offline.notice.enums.ProductLineEnum;
import com.ctrip.train.kefu.system.offline.notice.service.NoticeEnumService;
import com.ctrip.train.kefu.system.offline.notice.vm.notice.VmSendNoticeInfo;
import dao.ctrip.ctrainpps.entity.ScmSmallEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class NoticeEnumServiceImpl implements NoticeEnumService {

    @Autowired
    private ExtNoticeComplainInfo noticeService;

    /**
     *获取通知枚举数据
     */
    public List<ScmSmallEnum> GetNoticeTypeEnum(String produline){
        String type=GetNoticeType(produline);
        return  noticeService.GetNoticeTypeEnum(type);
    }

    /**
     * 获取枚举二级分类
     */
    public  List<ScmSmallEnum> GetSecondLevelEnum(Long id){
        return noticeService.GetSecondNoticeTypeEnum(id);
    }

    @Override
    public List<ScmSmallEnum> searchFirstNoticeTypes(int envenType, int productLine) {

        ScmSmallEnum req = new ScmSmallEnum();
        ProductLineEnum productLineEnum=ProductLineEnum.convertByCode(productLine);
        EventTypeEnum eventType=EventTypeEnum.convertEventType(envenType);
        String fieldType=productLineEnum.getNoticeType();
        switch (eventType){
            case Notice:
                fieldType = productLineEnum.getNoticeType();
                break;
            case Complain:
                fieldType = productLineEnum.getComplainType();
                break;
            case LeaderNotice:
                fieldType = productLineEnum.getLeaderNoticeType();
                break;
        }
        req.setFieldType(fieldType);
        req.setIsDeleted("0");
        req.setFkUpperTid((long) 0);

        return noticeService.searchScmSmallEnum(req);

    }

    @Override
    public List<ScmSmallEnum> searchSecondNoticeTypes(int firstId) {

        ScmSmallEnum req = new ScmSmallEnum();
        req.setIsDeleted("0");
        req.setFkUpperTid((long) firstId);
        return noticeService.searchScmSmallEnum(req);

    }


    protected  String GetNoticeType(String productLine){
        if (productLine.equals("32") || productLine.equals("136")) {
            return "QunarNoticeType";
        }
        if (productLine.equals("2")) {
            return "BusNoticeType";
        }
        if (productLine.equals("102"))
            return "TripBusNoticeType";
        if (productLine.equals("3"))
            return "OutieNoticeType";
        if (productLine.equals("4"))
            return "AirBusNoticeType";
        if (productLine.equals("5"))
            return "ChuanPiaoNoticeType";
        if (productLine.equals("6"))
            return "PinCheNoticeType";
        if (productLine.equals("10") || productLine.equals("31")|| productLine.equals("135")) {
            return "TYZXFlightNoticeType";
        }
        return "NoticeType";
    }
}
