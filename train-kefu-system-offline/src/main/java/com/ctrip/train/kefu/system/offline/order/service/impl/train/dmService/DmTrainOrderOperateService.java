package com.ctrip.train.kefu.system.offline.order.service.impl.train.dmService;

import com.alibaba.fastjson.JSON;
import com.ctrip.soa.framework.soa.recommendservice.v1.IsCanInsertRecommendResponseType;
import com.ctrip.soa.train.trainordercentreservice.v1.OrderDetailResponseType;
import com.ctrip.train.kefu.system.client.offline.train.IvrContract;
import com.ctrip.train.kefu.system.client.offline.train.LivechatServiceContract;
import com.ctrip.train.kefu.system.offline.notice.service.NoticeService;
import com.ctrip.train.kefu.system.offline.order.dao.ExtIvrCdrsubDao;
import com.ctrip.train.kefu.system.offline.order.domain.train.DmTrainOrderDetail;
import com.ctrip.train.kefu.system.offline.order.domain.train.order.DmTrainOrderOperate;
import com.ctrip.train.kefu.system.offline.order.manager.ButtonManager;
import com.ctrip.train.kefu.system.offline.order.service.ScmNoteService;
import com.ctrip.train.kefu.system.offline.order.vm.train.order.VmRemarks;
import com.ctrip.train.kefu.system.offline.order.vm.train.order.VmTrainButton;
import com.ctrip.train.kefu.system.offline.order.vm.train.order.VmTrainOrderOperate;
import common.log.CLogger;
import common.util.StringUtils;
import dao.ctrip.ctrainchat.entity.IvrCdrsub;
import dao.ctrip.ctrainchat.entity.OfflinePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tempuri.livechatservice.GetChatListResponseType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DmTrainOrderOperateService {

    @Autowired
    private ButtonManager buttonManager;

    @Autowired
    private ScmNoteService scmNoteServiceImpl;

    @Autowired
    private ExtIvrCdrsubDao extIvrCdrsubDao;

    @Autowired
    private LivechatServiceContract livechatServiceContract;
    @Autowired
    private NoticeService noticeServiceImpl;

    /**
     * 获取订单DoMain_操作信息
     */
    public DmTrainOrderOperate getDmOrderOperate(OrderDetailResponseType orderDetailResponseType) {
        DmTrainOrderOperate dm = new DmTrainOrderOperate();
        Map<String, String> dic = new HashMap<>();
        dic.put("orderNumber", orderDetailResponseType.getOrderMaster().getPartnerOrderId());
        try {

        } catch (Exception ex) {
            CLogger.error("获取订单DoMain_操作信息", ex, dic);
        }
        return dm;
    }

    /**
     * 获取页面实体信息_操作信息
     */
    public VmTrainOrderOperate getVmOrderOperate(DmTrainOrderDetail dmTrainOrderDetail, List<OfflinePermission> permissions) {
        String partnerOrderId=dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerOrderId();
        VmTrainOrderOperate vm = new VmTrainOrderOperate();
        Map<String, String> dic = new HashMap<>();
        dic.put("orderNumber", partnerOrderId);
        try {
            List<VmRemarks> vmRemarks=scmNoteServiceImpl.searchRemarksList(partnerOrderId);
//            searchNoticeByOrderIdCount
            //按钮初始化信息 权限判断 跳转链接等 TODO
            List<String> list = new ArrayList<>();
            VmTrainButton vtb= buttonManager.trainButtonInfo(dmTrainOrderDetail,list);
            vtb.setIvrPhoneNumber(getIvrPhoneCount(dmTrainOrderDetail));
            vtb.setChatCount(getChatCount(dmTrainOrderDetail));
            vtb.setImportantNoticeCount(noticeServiceImpl.searchImportantNoticeCount(partnerOrderId));
            vm.setOperates(vmRemarks);
            vm.setButtonsFlag(vtb);
            CLogger.info("OrderOperate", JSON.toJSONString(vm));
            return vm;
        } catch (Exception ex) {
            CLogger.error("获取页面实体信息_操作信息", ex, dic);
            return null;
        }
    }

    /**
     * 获取电话咨询次数
     * @param dmTrainOrderDetail
     * @return
     */
    private int getIvrPhoneCount(DmTrainOrderDetail dmTrainOrderDetail){
        int phoneCount = 0;
        try {
            if(dmTrainOrderDetail!=null && dmTrainOrderDetail.getDmTrainOrderBasicInfo() !=null && dmTrainOrderDetail.getDmTrainOrderBasicInfo().getContactMobile() !=null) {
                List<IvrCdrsub> ivrCdrsubList = extIvrCdrsubDao.GetIvrListByPhone(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getContactMobile());
                if (ivrCdrsubList != null && ivrCdrsubList.size() > 0) {
                    phoneCount = ivrCdrsubList.stream().map(p -> p.getMainCID()).distinct().collect(Collectors.toList()).size();
                }
            }
        }catch (Exception ex){
            CLogger.info("getIvrPhoneCount",ex);
        }
        return phoneCount;
    }

    /**
     * 获取在线咨询次数
     * @param dmTrainOrderDetail
     * @return
     */
    private int getChatCount(DmTrainOrderDetail dmTrainOrderDetail){
        int chatCount = 0;
        if (dmTrainOrderDetail != null && dmTrainOrderDetail.getDmTrainOrderBasicInfo() != null && dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerOrderId() != null) {
            GetChatListResponseType response = livechatServiceContract.getChatList(dmTrainOrderDetail.getDmTrainOrderBasicInfo().getPartnerOrderId());
            if (response != null && response.getChatList() != null) {
                chatCount = response.getChatList().size();
            }
        }
        return chatCount;
    }
}
