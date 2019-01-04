package com.ctrip.train.kefu.system.job.worker.service.notice;

import com.ctrip.train.kefu.system.job.worker.dao.notice.ExtOperateInfo;
import com.ctrip.train.kefu.system.job.worker.dao.notice.ExtVendorNotice;
import common.log.CLogger;
import dao.ctrip.ctrainpps.entity.NoticeVendor;
import dao.ctrip.ctrainpps.entity.OperateInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendorNoticeService {

    @Autowired
    private ExtVendorNotice extVendorNotice;

    @Autowired
    private ExtOperateInfo extOperateInfo;


    /**
     * 更新通知操作数据
     */
    public void initNoticeOpData(){
        List<NoticeVendor>  noticeVendors=extVendorNotice.getVendorList();
        if (noticeVendors!= null&& noticeVendors.size()>0){
            List<Long> noticeIds=noticeVendors.stream().map(NoticeVendor::getNoticeId).collect(Collectors.toList());
            List<OperateInfo>  operateInfos= extOperateInfo.getOperateInfos(noticeIds);

            noticeVendors.forEach(p->{
                NoticeVendor vendor =new  NoticeVendor();
                List<OperateInfo> infos=  operateInfos.stream().filter(q->q.getTid().equals(p.getNoticeId())).collect(Collectors.toList());
                if (infos!=null&&infos.size()>0){
                    Optional<OperateInfo> operate= infos.stream().max(Comparator.comparing(OperateInfo::getOperateTime));
                    operate.ifPresent(operateInfo -> {
                        vendor.setID(p.getID());
                        vendor.setOpTime(operateInfo.getOperateTime());
                        vendor.setOpUser(operateInfo.getOperateUser());
                        if (operateInfo.getOperateSource()==2){
                            vendor.setOpUserType(2);
                        }else{
                            vendor.setOpUserType(1);
                        }

                    });
                }else {
                    vendor.setID(p.getID());
                    vendor.setOpTime(p.getSendTime());
                    vendor.setOpUser(p.getSendName());
                    vendor.setOpUserType(p.getSendType());
                }
                try {
                    extVendorNotice.update(vendor);
                }
                catch (Exception ex){
                    CLogger.error("initNoticeOpData",ex.getMessage());
                }
            });
        }
    }
}
