package com.ctrip.train.kefu.system.offline.order.controller.train;

import com.ctrip.train.kefu.system.offline.common.controller.BaseController;
import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import com.ctrip.train.kefu.system.offline.order.enums.train.ExecCheckStatusEnum;
import com.ctrip.train.kefu.system.offline.order.service.ExcePriceService;
import com.ctrip.train.kefu.system.offline.order.service.InvoiceService;
import com.ctrip.train.kefu.system.offline.order.service.TrainOrderService;
import com.ctrip.train.kefu.system.offline.order.vm.*;
import common.file.ImageUpload;
import common.log.CLogger;
import dao.ctrip.ctrainpps.entity.ExcePrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/train/order/")
public class TrainInvoiceController extends BaseController {

    @Autowired ExcePriceService excePriceService;

    @Autowired
    TrainOrderService trainOrderService;

    @Autowired
    InvoiceService invoiceService;

    @GetMapping("/detail")
    public  String detail (Map<String, Object> model){
        return "/order/train/detail";
    }

    @GetMapping("/exceprice")
    public  String exceprice (Map<String, Object> model, String orderid, String type, Integer exceid, String insertName, Integer productLine){
        ExcePrice excePricedetail = new ExcePrice();
        String orderdate = (new SimpleDateFormat("yyyy-MM-dd")).format( new Date());
        if(type!= null && type.equals("update")) {
            excePricedetail = excePriceService.getExcePriceData(exceid);
            if(excePricedetail!= null && excePricedetail.getOrderDate()!= null){
                orderdate = (new SimpleDateFormat("yyyy-MM-dd")).format(excePricedetail.getOrderDate());
            }
        }
        model.put("insertName", (insertName != null && insertName.trim().length() > 0) ?  insertName : getEmpName());  //录入人名称
        model.put("orderid", orderid);
        model.put("excepricedata", excePricedetail);
        model.put("responsibilitylist", excePriceService.getDropdownList("carresponsy_type", 0, 0,0));
        model.put("orderDate",orderdate);
        model.put("type",type);
        model.put("productLine",productLine);
        model.put("productname",excePriceService.getProductname(productLine));
        model.put("zhixingCarResponse",excePriceService.getOrderDetail( Long.parseLong(orderid)));
        String isSave = "";
        if (excePricedetail!= null && excePricedetail.getIsAudit() != null) {
            isSave = (excePricedetail.getIsAudit().equals(ExecCheckStatusEnum.ExecCheckSuccess.getCode()) || excePricedetail.getIsAudit().equals(ExecCheckStatusEnum.ExecCheckFail.getCode())) ? "1" : "0";
        }
        model.put("isSave", isSave);
        return "/order/train/exceprice/addexceprice";
    }

    /**
     * 点击保存按钮
     * @throws Exception
     */
    @PostMapping("/exceprice/addupdate")
    @ResponseBody
    public JsonResult excepricedata(@RequestBody ExcePriceEx excePrice){
        Map<String,String> resultmodel =  excePriceService.excepricedata(excePrice,getEmpInfo());
        if(resultmodel.get("status").equals("0")){
            if(excePrice.getExceID() == 0){
                return JsonResult.ok().putData("msg", "新增异常件成功");
            }else{
                return JsonResult.ok().putData("msg", "更新异常件成功");
            }
        }else{
            return JsonResult.fail().putData("msg", resultmodel.get("msg"));
        }
    }

    /**
     * 责任方原因下拉选单
     * @throws Exception
     */
    @PostMapping("/exceprice/getResponsibility")
    @ResponseBody
    public JsonResult getResponsibility(Integer fkUpperTid){
        return JsonResult.ok().putData("response", excePriceService.getDropdownList("carresponsy_type", fkUpperTid, 0, 0));
    }
    /**
     * 实现文件上传
     * */
    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("fileName") MultipartFile file){
      ImageUpload.upload(file);
        return  ImageUpload.upload(file).getUrl();
    }

    /**
     * 12306订单详情页面
     * @param
     * @return
     */
    @GetMapping("/12306info")
    public String orderDetail (Map<String, Object> model,String orderNumber,String username12306,String eOrderNumber){
        model.put("orderNumber", orderNumber);
        model.put("username12306", username12306);
        model.put("eOrderNumber", eOrderNumber);
        return "order/train/traininfo";
    }

    /**
     * 请求12306数据
     * @param
     * @return
     */
    @PostMapping("/12306info/detail")
    @ResponseBody
    public JsonResult get12306Info(String orderNumber,String username12306,String eOrderNumber){
        QueryOrderEx orderList =  trainOrderService.getQueryOrderDetail( orderNumber, username12306, eOrderNumber);
        if(orderList != null && orderList.getRetCode() == 1){
            return JsonResult.ok().putData("response",orderList);
        }else{
            assert orderList != null;
            return JsonResult.fail().putData("status",orderList.getRetCode());
        }
    }


    /**
     * 电子发票
     * @param
     * @return
     */
    @GetMapping("/einvoice/detail")
    public String eInvoice (Map<String, Object> model,String orderNumber, String mobile,String superVipOrderNumber,String productId,String uid,String source){
        List<VmInvoiceInfo> vmInvoiceInfolist = new ArrayList<VmInvoiceInfo>();
        List<VmInvoiceOrderInfo> vm = invoiceService.getOrderList(mobile,superVipOrderNumber,vmInvoiceInfolist,source);
        model.put("orderInfoList", vm);
        model.put("productId", productId);
        model.put("superuid", uid);
        model.put("superVipOrderNumber", superVipOrderNumber);
        model.put("invoiceInfoList", vmInvoiceInfolist);
        model.put("eid", getEmpInfo().getEid());
        model.put("delivery",invoiceService.getDelivery(orderNumber));
        model.put("mobile",mobile);
        return "order/train/einvoice";
    }

    /**
     * 开电子发票
     * @param
     * @return
     */
    @PostMapping("/einvoice/createinvoice")
    @ResponseBody
    public JsonResult createInvoice (@RequestBody VmInvoiceDetail vmInvoiceDetail){
        try {
            List<String> result = invoiceService.createInvoice(vmInvoiceDetail, getEmpInfo().getEid());
            if(result.size() == 0){
                return JsonResult.fail().putData("msg","开具电子发票失败");
            }
            return JsonResult.ok().putData("msg",result);
        }catch(Exception ex){
            CLogger.error("createInvoice",ex);
        }
        return JsonResult.fail().putData("msg","开具电子发票失败");
    }

    /**
     * 判断是否有异常件通知
     * @return
     */
    @RequestMapping("/searchExcePriceCount")
    @ResponseBody
    public JsonResult searchExcePriceCount(String orderId){
        int result = excePriceService.searchExcePriceCount(orderId);
        if(result>0||!hasPermission("exPrice:add")){
            return JsonResult.ok();
        }else{
            return JsonResult.fail();
        }
    }
}
