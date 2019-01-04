package com.ctrip.train.kefu.system.offline.notice.controller;

import com.ctrip.train.kefu.system.client.offline.flight.FlightOrderContract;
import com.ctrip.train.kefu.system.offline.common.aspect.annotation.Auth;
import com.ctrip.train.kefu.system.offline.common.aspect.annotation.Module;
import com.ctrip.train.kefu.system.offline.common.component.StaffInfoComponent;
import com.ctrip.train.kefu.system.offline.common.controller.BaseController;
import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import com.ctrip.train.kefu.system.offline.notice.enums.*;
import com.ctrip.train.kefu.system.offline.notice.service.*;
import com.ctrip.train.kefu.system.offline.notice.vm.VmNoticeDetail;
import com.ctrip.train.kefu.system.offline.notice.vm.VmNoticeVendor;
import com.ctrip.train.kefu.system.offline.notice.vm.notice.ResponseUrge;
import com.ctrip.train.kefu.system.offline.notice.vm.notice.RpsImportantNotice;
import com.ctrip.train.kefu.system.offline.notice.vm.notice.VmNotice;
import com.ctrip.train.kefu.system.offline.notice.vm.notice.VmOperateInfo;
import com.ctrip.train.kefu.system.offline.notice.vm.request.VmImportantNotice;
import common.file.ImageRespose;
import common.file.ImageUpload;
import dao.ctrip.ctrainpps.entity.ScmSmallEnum;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/notice/")
public class NoticeController extends BaseController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private OperateInfoService operateInfoService;

    @Autowired
    private NoticeVendorSevice noticeVendorSevice;

    @Autowired
    private NoticeEnumService noticeEnumService;

    @Autowired
    private AgentNoticeService agentNoticeService;


    /**
     * 订单通知详情
     */
    @RequestMapping("/detail")
    public String getOrderNotice(Map<String, Object> model, String orderid,Integer type) {
        //获取通知数据
        List<VmNoticeDetail> vm = noticeService.getNoticeList(orderid, type);
        if (vm != null && vm.size() > 0) {
            //模型隐射
            vm.forEach(p -> p.setOperateInfos(operateInfoService.getOperateList(p.getID())));
        }
        model.put("notices", vm);
        return "notice/detail";
    }

    /**
     *通知操作记录
     */
    @RequestMapping("/ops")
    @ResponseBody
    public JsonResult getOperateInfoLst(Long noticeid) {
        //获取操作信息
        List operateList = operateInfoService.getOperateList(noticeid);
        return JsonResult.ok().putData("operateList", operateList);
    }

    /**
     * 通知催处理/ 通知备注
     */
    @RequestMapping("/operate")
    @ResponseBody
    public JsonResult operate(Long noticeid,String content,String type)  {
        //备注
        if (type!=null&&type.equals("remarks")){
            boolean  result =noticeService.remarksNotice(noticeid);
            if (result) {
                //添加备注记录
                operateInfoService.insertOperate(noticeid, content, getOpUser(),
                        NoticeOperateTypeEnum.Save);
                return JsonResult.ok();
            }
            return JsonResult.fail();
        }
        //催处理
        ResponseUrge result=noticeService.reminderNotice(noticeid);
        if (result.getStatus()==0) {
            //添加催记录
            operateInfoService.insertOperate(noticeid, "一线客服催：" + content, getOpUser(),
                    NoticeOperateTypeEnum.Urge);
            return JsonResult.ok(result.getMsg());
        }
        return JsonResult.fail(result.getMsg());
    }


    /**
     * 发送供应商通知页面
     */
    @RequestMapping("/send-vendor")
    public String vendor(Map<String, Object> model, Long noticeId) {
        model.put("vendorInfo", Arrays.stream(VendorEnum.values()).collect(Collectors.toList()));
        model.put("emergencyStates", Arrays.stream(EmergencyStateEnum.values()).collect(Collectors.toList()));
        model.put("vendor",noticeVendorSevice.getNoitceVendor(noticeId));
        return "notice/vendor";
    }

    /**
     * 保存供应商通知数据
     */
    @PostMapping("/vendor/send")
    @ResponseBody
    public JsonResult saveVendor(@RequestBody VmNoticeVendor  vendor) throws SQLException {
        if (noticeVendorSevice.exisNoitceVendor(vendor.getNoticeId())){
            return JsonResult.fail("相同通知不可重复转供应商");
        }
        boolean result=noticeVendorSevice.sendVendor(vendor,getEid(),getEmpName());
        if(result){
            return  JsonResult.ok("发送成功");
        }
        else{
            return JsonResult.fail("报数数据");
        }
    }


    /**
     * 获取通知类型的二级分类
     */
    @GetMapping("/getSecondType")
    @ResponseBody
    public JsonResult getSecondNoticeType(Long firstLevel){
        List<ScmSmallEnum> noticeTypes=noticeEnumService.GetSecondLevelEnum(firstLevel);
        JsonResult rs;
        if(noticeTypes!=null&&noticeTypes.size()>0){
            rs=JsonResult.ok().putData("noticeTypes",noticeTypes);
        }
        else{
            rs=JsonResult.fail("没有找到二级分类");
        }
        return rs;
    }


    /**
     * 上传图片
     * */
    @RequestMapping("fileUpload")
    @ResponseBody
    public JsonResult fileUpload(MultipartFile file){
        ImageRespose respose= ImageUpload.upload(file);
        return JsonResult.ok().putData("result",respose);
    }

    /**
     * 重要通知
     * @param model
     * @param orderId
     * @return
     */
    @RequestMapping("/importantNotice")
    public String importantNotice(Map<String,Object> model ,String orderId,String contactPhone,
                                  String contactUser,String partnerName,int productLine,int orderType){
        List<RpsImportantNotice> notices=noticeService.importantNotice(orderId);
        if (notices!=null&&notices.size()>0) {
            notices.forEach(p -> {
                ModelMapper modelMapper = new ModelMapper();
                p.setOperates(modelMapper.map(operateInfoService.getOperateList(p.getID()),new TypeToken<List<VmOperateInfo>>() {}.getType()));
            });
        }
        //通知列表
        model.put("notices",notices);
        //发送通知
        model.put("sendNoticePre",noticeService.getSendNoticeInfo(orderId,contactPhone,contactUser));
        //pageFlag为页面标记 1 列表页面 添加页面 判断是否有添加通知权限 否 转列表页 是否发送过通知 否 转列表页
        model.put("pageFlag",(notices!=null&&notices.size()>0)?1:0);
        model.put("productLine",productLine);
        model.put("orderType",orderType);
        model.put("channelSource", ChannelSourceEnum.convertDatasource(partnerName).getValue());
        model.put("PermissionFlag",hasPermission("notice:addImportantNotice")?1:0);
        return "notice/deal/send-list";
    }

    /**
     * 查询通知投诉一级分类
     * @param evenType
     * @param productLine
     * @return
     */
    @RequestMapping("/firstNoticeTypes")
    @ResponseBody
    public JsonResult searchFirstNoticeTypes(int evenType,int productLine){
        List<ScmSmallEnum>  temp=noticeEnumService.searchFirstNoticeTypes(evenType,productLine);
        if(temp!=null&&temp.size()>0){
            return JsonResult.ok().putData("response",temp);
        }else{
            return JsonResult.fail("未查到一级分类");
        }
    }

    /**
     * 查询通知投诉二级分类
     * @param firstId
     * @return
     */
    @RequestMapping("/secondNoticeTypes")
    @ResponseBody
    public JsonResult searchSecondNoticeTypes(int firstId){
        List<ScmSmallEnum>  temp=noticeEnumService.searchSecondNoticeTypes(firstId);
        if(temp!=null&&temp.size()>0){
            return JsonResult.ok().putData("response",temp);
        }else{
            return JsonResult.fail("未查到二级分类");
        }
    }

    /**
     * 发送重要通知
     * @param request
     * @return
     */
    @RequestMapping("/commitImportantNotice")
    public @ResponseBody JsonResult sendNotice(@RequestBody VmImportantNotice request){
        //权限控制
        if (hasPermission("notice:addImportantNotice")){
            request.setEnterUser(getOpUser());
            if(noticeService.sendImportantNotice(request)==1){
                return JsonResult.ok("发送成功！");
            }
            return JsonResult.fail("发送失败！");
        } else {
            return JsonResult.fail("发送失败,您无此权限！");
        }
    }

    /**
     * 判断是否有119通知及添加权限
     * @return
     */
    @RequestMapping("/search119Count")
    @ResponseBody
    public JsonResult search119Notice(String orderId){
        int result = noticeService.search119Notice(orderId);
        if(result>0||!hasPermission("notice:119")){
            return JsonResult.ok();
        }else{
            //无通知跳转添加
            return JsonResult.fail();
        }
    }
    /**
     * 判断是否有代售点通知及添加权限
     * @return
     */
    @RequestMapping("/searchAgentNoticeCount")
    @ResponseBody
    public JsonResult searchAgentNotice(String orderId){
        int result = agentNoticeService.searchAgentNotice(orderId);
        //无添加权限或者result>0 列表页面
        if(result>0||!hasPermission("agentNotice:add")){
            return JsonResult.ok();
        }else{
            //无通知跳转添加
            return JsonResult.fail();
        }
    }
}
