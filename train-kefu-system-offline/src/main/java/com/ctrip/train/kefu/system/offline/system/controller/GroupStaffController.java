package com.ctrip.train.kefu.system.offline.system.controller;

import com.ctrip.train.kefu.system.offline.common.controller.BaseController;
import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import com.ctrip.train.kefu.system.offline.common.utils.PageInfo;
import com.ctrip.train.kefu.system.offline.system.domain.StaffGroupResult;
import com.ctrip.train.kefu.system.offline.system.vm.VmGroupRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Map;

public class GroupStaffController extends BaseController{

//    @RequestMapping(value ="/groupstaff", produces = "application/json", method = RequestMethod.GET)
//    public String groupStaff(Map<String, Object> model, @RequestParam long tid) {
//        StaffGroupResult result=sysGroupServiceImpl.getGroup(tid);
//        model.put("groupStaff",result);
//        return "system/group/groupStaff";
//    }
//
//    @RequestMapping(value = "/group/list", produces = "application/json")
//    @ResponseBody
//    public PageInfo searchGroup(int pageIndex, int pageSize, String productLine, String groupName) {
//        PageInfo ss=sysGroupServiceImpl.searchGroupForPage(pageIndex,pageSize,productLine,groupName);
//        return ss;
//    }
//
//    @ResponseBody
//    @RequestMapping(value ="/group/add", produces = "application/json", method = RequestMethod.POST)
//    public JsonResult addGroup(@RequestBody VmGroupRequest request){
//        if(sysGroupServiceImpl.addGroup(request)==1){
//            return JsonResult.ok();
//        }
//        return JsonResult.fail();
//    }
//    @ResponseBody
//    @RequestMapping(value ="/group/get", produces = "application/json", method = RequestMethod.GET)
//    public JsonResult getGroup(HttpServletRequest request, @RequestParam Long tid) {
//        StaffGroupResult result=sysGroupServiceImpl.getGroup(tid);
//        if(result!=null){
//            return JsonResult.ok().putData("response",result);
//        }
//        return JsonResult.fail("获取失败");
//    }
//    @ResponseBody
//    @RequestMapping(value ="/group/edit", produces = "application/json", method = RequestMethod.POST)
//    public JsonResult editGroup(@RequestBody VmGroupRequest request) throws SQLException {
//        if(sysGroupServiceImpl.editGroup(request)==1){
//            return JsonResult.ok();
//        }
//        return JsonResult.fail();
//    }
//
//    @ResponseBody
//    @RequestMapping(value ="group/remove", produces = "application/json", method = RequestMethod.GET)
//    public JsonResult editGroup(long tid) throws SQLException {
//        if(sysGroupServiceImpl.deleteGroup(tid)==1){
//            return JsonResult.ok();
//        }
//        return JsonResult.fail();
//    }
}
