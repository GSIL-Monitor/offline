package com.ctrip.train.kefu.system.offline.notice.service;

import com.ctrip.train.kefu.system.offline.notice.vm.VmNoticeDetail;
import com.ctrip.train.kefu.system.offline.notice.vm.notice.ResponseUrge;
import com.ctrip.train.kefu.system.offline.notice.vm.notice.RpsImportantNotice;
import com.ctrip.train.kefu.system.offline.notice.vm.notice.VmNotice;
import com.ctrip.train.kefu.system.offline.notice.vm.notice.VmSendNoticeInfo;
import com.ctrip.train.kefu.system.offline.notice.vm.request.VmImportantNotice;

import java.util.List;

public interface NoticeService {

    /**
     * 根据订单号获取通知数据
     */
    List<VmNoticeDetail> getNoticeList(String orderid,Integer type);

    /**
     *备注通知
     */
    boolean remarksNotice(Long noticeId);

    /**
     *通知催处理
     */
    ResponseUrge reminderNotice(Long noticeId);

    /**
     * 重要通知
     */
    List<RpsImportantNotice> importantNotice(String orderId);

    /**
     * 发送信息初始页信息
     * @param orderId
     * @return
     */
    VmSendNoticeInfo getSendNoticeInfo(String orderId,String contactPhone ,String contactUser);

    /**
     * 发送重要通知
     */
    int sendImportantNotice(VmImportantNotice request);

    /**
     * 查看119通知
     */
    int search119Notice(String orderId);

    /**
     * 重要通知个数
     */
    int searchImportantNoticeCount(String orderId);


}
