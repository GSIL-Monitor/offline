package com.ctrip.train.kefu.system.offline.order.service.impl;

import com.ctrip.train.kefu.system.offline.notice.dao.ExtAirNoticeExcetionInfo;
import com.ctrip.train.kefu.system.offline.order.service.AirNoticeExService;
import com.ctrip.train.kefu.system.offline.order.vm.RequestAirOrderEx;
import com.ctrip.train.kefu.system.offline.order.vm.ResponseAirOrderEx;
import com.ctrip.train.kefu.system.offline.order.vm.VmAirOrderEx;
import common.util.DateUtils;
import dao.ctrip.ctrainchat.entity.AirNoticeExcetion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AirNoticeExServiceImpl implements AirNoticeExService {
    @Autowired
    private ExtAirNoticeExcetionInfo extAirNoticeExcetionInfo;
    @Override
    public ResponseAirOrderEx searchAirNoticeEx(RequestAirOrderEx request) {

        int count=extAirNoticeExcetionInfo.SearchAirNoticeExCount(request);
        if(count!=0){
            List<AirNoticeExcetion> reports=extAirNoticeExcetionInfo.SearchAirNoticeEx(request);
            ResponseAirOrderEx rane = new ResponseAirOrderEx();
//            ModelMapper mapper =new ModelMapper();
//            List<VmAirOrderEx> vmList= mapper.map(reports,new TypeToken<List<AirNoticeExcetion>>() {}.getType());
            List<VmAirOrderEx> vmList=new ArrayList<>();
            for (AirNoticeExcetion ane:reports){
                VmAirOrderEx vm= new VmAirOrderEx();
                vm.setOrderId(ane.getOrderId());
                vm.setEnterUser(ane.getEnterUser());
//                vm.setSupplier(SupplierEnums.convertSupplier(ane.getSupplier()).getName());
                vm.setSupplier(ane.getSupplier());
                vm.setSendOrderTime(DateUtils.format(ane.getSendOrderTime(),DateUtils.YMDHMS_UNDERLINED));
//                vm.setExOrderType(EXOrderTypeEnums.convertEXOrderType(ane.getExOrderType()).getName());
                vm.setExOrderType(ane.getExOrderType().toString());
                vm.setLatestTicketingTime(DateUtils.format(ane.getLatestTicketingTime(),DateUtils.YMDHMS_UNDERLINED));
                vm.setProcessingRemark(ane.getProcessingRemark());
                vm.setProductLine(ane.getProductLine());
                vm.setTakeoffTime(DateUtils.format(ane.getTakeoffTime(),DateUtils.YMDHMS_UNDERLINED));
                vm.setID(ane.getID().toString());
                vmList.add(vm);
            }
            rane.setList(vmList);
            rane.setPageIndex(request.getPageIndex());
            rane.setPageSize(request.getPageSize());
            rane.setTotalRecord(count);
            return rane;
        }
        return null;
    }
}
