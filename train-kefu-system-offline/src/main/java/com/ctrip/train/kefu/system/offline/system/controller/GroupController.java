package com.ctrip.train.kefu.system.offline.system.controller;

import com.ctrip.train.kefu.system.offline.common.controller.BaseController;
import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import com.ctrip.train.kefu.system.offline.common.utils.PageInfo;
import com.ctrip.train.kefu.system.offline.notice.enums.ProductLineEnum;
import com.ctrip.train.kefu.system.offline.system.domain.StaffGroupResult;
import com.ctrip.train.kefu.system.offline.system.service.SysGroupService;
import com.ctrip.train.kefu.system.offline.system.service.SysGroupStaffService;
import com.ctrip.train.kefu.system.offline.system.service.impl.SysGroupStaffServiceImpl;
import com.ctrip.train.kefu.system.offline.system.vm.VmGroupRequest;
import com.ctrip.train.kefu.system.offline.system.vm.VmGroupStaffRRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class GroupController extends BaseController {

    @Autowired
    private SysGroupService sysGroupServiceImpl;

    @Autowired
    private SysGroupStaffService sysGroupStaffServiceImpl;

    @RequestMapping("/group")
    public String Staff(Map<String, Object> model) {
        ProductLineEnum[] productLineEnums=ProductLineEnum.values();
        model.put("productLineEnums", Arrays.stream(productLineEnums).collect(Collectors.toList()));
        return "system/group/group";
    }
    @ResponseBody
    @RequestMapping(value ="/groupstaff", produces = "application/json", method = RequestMethod.GET)
    public PageInfo groupStaff(@RequestParam int pageIndex,@RequestParam int pageSize,@RequestParam long groupId) {
        return sysGroupStaffServiceImpl.searchGroupStaffForPage(pageIndex,pageSize,groupId);
    }

    @RequestMapping(value ="/groupstaffpre", produces = "application/json", method = RequestMethod.GET)
    public String groupStaffPre(Map<String, Object> model ,long tid) {
        model.put("group",sysGroupServiceImpl.getGroup(tid));
        return "system/group/groupStaff";
    }
    @RequestMapping(value = "/group/list", produces = "application/json")
    @ResponseBody
    public PageInfo searchGroup(int pageIndex, int pageSize, String productLine, String groupName) {
        PageInfo ss=sysGroupServiceImpl.searchGroupForPage(pageIndex,pageSize,productLine,groupName);
        return ss;
    }

    @ResponseBody
    @RequestMapping(value ="/group/add", produces = "application/json", method = RequestMethod.POST)
    public JsonResult addGroup(@RequestBody VmGroupRequest request){
        if(sysGroupServiceImpl.addGroup(request)==1){
            return JsonResult.ok();
        }
        return JsonResult.fail();
    }
    @ResponseBody
    @RequestMapping(value ="/group/get", produces = "application/json", method = RequestMethod.GET)
    public JsonResult getGroup(HttpServletRequest request, @RequestParam Long tid) {
        StaffGroupResult result=sysGroupServiceImpl.getGroup(tid);
        if(result!=null){
            return JsonResult.ok().putData("response",result);
        }
        return JsonResult.fail("获取失败");
    }
    @ResponseBody
    @RequestMapping(value ="/group/edit", produces = "application/json", method = RequestMethod.POST)
    public JsonResult editGroup(@RequestBody VmGroupRequest request) throws SQLException {
        if(sysGroupServiceImpl.editGroup(request)==1){
            return JsonResult.ok();
        }
        return JsonResult.fail();
    }

    @ResponseBody
    @RequestMapping(value ="group/remove", produces = "application/json", method = RequestMethod.GET)
    public JsonResult editGroup(long tid) throws SQLException {
        if(sysGroupServiceImpl.deleteGroup(tid)==1){
            return JsonResult.ok();
        }
        return JsonResult.fail();
    }

    @ResponseBody
    @RequestMapping(value ="/groupstaff/editpre", produces = "application/json", method = RequestMethod.GET)
    public PageInfo groupStaffEditPre(int pageIndex, int pageSize,long groupId) {
        return sysGroupStaffServiceImpl.editGroupStaffPre(groupId);
    }

    @ResponseBody
    @RequestMapping(value ="/groupstaff/edit", produces = "application/json", method = RequestMethod.POST)
    public JsonResult groupStaffEdit(@RequestBody VmGroupStaffRRequest request) throws SQLException {
        if(sysGroupStaffServiceImpl.editGroupStaff(request)==1)
            return JsonResult.ok();
        return JsonResult.fail();
    }
}