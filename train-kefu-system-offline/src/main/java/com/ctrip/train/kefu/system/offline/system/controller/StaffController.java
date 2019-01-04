package com.ctrip.train.kefu.system.offline.system.controller;

import com.ctrip.train.kefu.system.offline.common.controller.BaseController;
import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import com.ctrip.train.kefu.system.offline.common.utils.PageInfo;
import com.ctrip.train.kefu.system.offline.notice.enums.ProductLineEnum;
import com.ctrip.train.kefu.system.offline.system.enums.StaffGroupEnum;
import com.ctrip.train.kefu.system.offline.system.service.SysPositionService;
import com.ctrip.train.kefu.system.offline.system.service.SysRoleService;
import com.ctrip.train.kefu.system.offline.system.service.SysStaffService;

import com.ctrip.train.kefu.system.offline.system.vm.VmStaffRequest;
import com.ctrip.train.kefu.system.offline.system.vm.VmStaffResponse;
import dao.ctrip.ctrainchat.entity.ChatStaffInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class StaffController extends BaseController{

    @Autowired
    private SysStaffService sysStaffServiceImpl;
    @Autowired
    private SysRoleService sysRoleServiceImpl;
    @Autowired
    private SysPositionService sysPositionServiceImpl;
    @RequestMapping("/staff")
    public String Staff(Map<String, Object> model) throws SQLException {
        ProductLineEnum[] productLineEnums=ProductLineEnum.values();
        StaffGroupEnum[] staffGroupEnums= StaffGroupEnum.values();

        model.put("productLineEnums", Arrays.stream(productLineEnums).collect(Collectors.toList()));
        model.put("staffGroupEnums", Arrays.stream(staffGroupEnums).collect(Collectors.toList()));
        model.put("rolesEnums",sysRoleServiceImpl.searchAllRole());
        model.put("postsEnums",sysPositionServiceImpl.selectAllPost());

        return "system/staff/staff";
    }

    @ResponseBody
    @RequestMapping(value = "/staff/list", produces = "application/json", method = RequestMethod.GET)
    public PageInfo searchRole(int pageIndex, int pageSize, String productLine,String staffName) {
        VmStaffRequest request= new VmStaffRequest();
        request.setProductLine(productLine);
        request.setStaffName(staffName);
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        return sysStaffServiceImpl.searchStaffForPage(request);
    }

    @ResponseBody
    @RequestMapping(value ="/staff/get", produces = "application/json", method = RequestMethod.GET)
    public JsonResult searchStaffById(Long  tId) {
        VmStaffResponse ss =sysStaffServiceImpl.queryStaffBytId(tId);
        if (ss!=null) {
            return JsonResult.ok().putData("response",ss);
        }
        return JsonResult.fail();
    }

    @ResponseBody
    @RequestMapping(value = "/staff/add", produces = "application/json", method = RequestMethod.POST)
    public JsonResult addStaff(HttpServletRequest request, @RequestBody VmStaffRequest vmStaffRequest) throws SQLException {

        int ss=sysStaffServiceImpl.addStaff(vmStaffRequest);
        if (ss!=0) {
            return JsonResult.ok();
        }
        return JsonResult.fail();
    }


    @RequestMapping(value = "/staff/edit", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult editStaff(HttpServletRequest request, @RequestBody VmStaffRequest vmStaffRequest) throws SQLException {
        int ss=sysStaffServiceImpl.editStaff(vmStaffRequest);
        if (ss!=0) {
            return JsonResult.ok();
        }
        return JsonResult.fail();
    }

    @ResponseBody
    @RequestMapping(value = "/staff/delete", produces = "application/json", method = RequestMethod.GET)
    public JsonResult editDelete(long tId) throws SQLException {
        int ss=sysStaffServiceImpl.deleteStaffByTid(tId,0);
        if (ss!=0) {
            return JsonResult.ok();
        }
        return JsonResult.fail();
    }
    @ResponseBody
    @RequestMapping(value = "/staff/query", produces = "application/json", method = RequestMethod.GET)
    public JsonResult queryStaffByStaffNum(String staffNum){
        VmStaffResponse staff=sysStaffServiceImpl.queryStaffByStaffNum(staffNum);
        if(staff!=null){
            return JsonResult.ok();
        }
        return JsonResult.fail();
    }

    @ResponseBody
    @RequestMapping(value = "/staff/queryByName", produces = "application/json", method = RequestMethod.GET)
    public JsonResult queryStaffByStaffName(String staffName){
        VmStaffResponse staff=sysStaffServiceImpl.queryStaffByStaffName(staffName);
        if(staff!=null){
            return JsonResult.ok().putData("response",staff);
        }
        return JsonResult.fail();
    }
}
