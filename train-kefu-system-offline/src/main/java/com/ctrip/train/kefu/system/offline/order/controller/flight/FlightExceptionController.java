package com.ctrip.train.kefu.system.offline.order.controller.flight;

import com.ctrip.framework.foundation.Foundation;
import com.ctrip.train.kefu.system.offline.common.controller.BaseController;
import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import com.ctrip.train.kefu.system.offline.notice.enums.ChannelSourceEnum;
import com.ctrip.train.kefu.system.offline.order.constants.AirUrlConstants;
import com.ctrip.train.kefu.system.offline.order.enums.train.EXOrderTypeEnums;
import com.ctrip.train.kefu.system.offline.notice.enums.ProductLineEnum;
import com.ctrip.train.kefu.system.offline.order.enums.train.SupplierEnums;
import com.ctrip.train.kefu.system.offline.order.service.AirNoticeExService;
import com.ctrip.train.kefu.system.offline.order.vm.RequestAirOrderEx;
import com.ctrip.train.kefu.system.offline.order.vm.ResponseAirOrderEx;
import common.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/airOrder")
public class FlightExceptionController extends BaseController {
    @Autowired
    private AirNoticeExService airNoticeExServiceImpl;

    @RequestMapping("/airOrderList")
    public String airTicketsException(Map<String,Object> model){
        String startDate= DateUtils.getCurDateStr();
        String endDate= DateUtils.getCurDateStr();
        ProductLineEnum[] productLineEnums=ProductLineEnum.values();
        ChannelSourceEnum[] channelSourceEnums=ChannelSourceEnum.values();

        SupplierEnums[] supplierEnums= SupplierEnums.values();
        EXOrderTypeEnums[] eXOrderTypeEnums= EXOrderTypeEnums.values();
        model.put("startDate",startDate);
        model.put("endDate",endDate);
        model.put("productLineEnums", Arrays.stream(productLineEnums).filter(o->o.getProductLineCode()==135||o.getProductLineCode()==31).collect(Collectors.toList()));
        model.put("supplierEnums", Arrays.stream(supplierEnums).collect(Collectors.toList()));
        model.put("eXOrderTypeEnums", Arrays.stream(eXOrderTypeEnums).collect(Collectors.toList()));
        model.put("channelSourceEnums", Arrays.stream(channelSourceEnums).collect(Collectors.toList()));
        return "airtickets/airticketsex";
    }
    /**
     * 异常单查询
     */
    @RequestMapping("/searchAirNotice")
    @ResponseBody
    public JsonResult searchAirNotice(int pageIndex,int pageSize,String startDate,String endDate,String supplier,String eOrderType,
                                        Integer productLine,String orderId){
        RequestAirOrderEx request =new RequestAirOrderEx();
        request.setEndDate(endDate!=null&&!endDate.equals("")?endDate:null);
        request.setStartDate(startDate!=null&&!startDate.equals("")?startDate:null);
        request.setOrderId(orderId!=null&&!orderId.equals("")?orderId:null);
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        request.seteXOrderType(eOrderType!=null&&!eOrderType.equals("")?eOrderType:null);
        request.setProductLine(productLine!=null&&!productLine.equals("")?productLine:null);
        request.setSupplier(supplier!=null&&!supplier.equals("")?supplier:null);
        ResponseAirOrderEx rane=airNoticeExServiceImpl.searchAirNoticeEx(request);

        if (rane!=null&&rane.getList()!=null&&rane.getList().size()>0) {
            return JsonResult.ok().putData("response",rane);
        }
        return JsonResult.fail().putData("response",rane);
    }

    /**
     * 机票异常单
     * @param model
     * @return
     */
    @RequestMapping("/airnoticehome")
    public String airnoticehome(Map<String,Object> model){
        if(Foundation.server().getEnv().isFAT()){
            model.put("TieyouFlightPrd", AirUrlConstants.TieyouFlightPrd);
            model.put("NoticePagePrd", AirUrlConstants.NoticePagePrd);
            model.put("NoticeDealPrd", AirUrlConstants.NoticeDealFat);
            model.put("NoticeListPrd", AirUrlConstants.NoticeListFat);
            model.put("AirOrderListPrd", AirUrlConstants.AirOrderListPrd);
        }else{
            model.put("TieyouFlightPrd", AirUrlConstants.TieyouFlightPrd);
            model.put("NoticePagePrd", AirUrlConstants.NoticePagePrd);
            model.put("NoticeDealPrd", AirUrlConstants.NoticeDealPrd);
            model.put("NoticeListPrd", AirUrlConstants.NoticeListPrd);
            model.put("AirOrderListPrd", AirUrlConstants.AirOrderListPrd);
        }
        return "airtickets/airnoticeex";
    }
}
