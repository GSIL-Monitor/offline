package com.ctrip.train.kefu.system.offline.system.controller;

import com.ctrip.train.kefu.system.offline.common.controller.BaseController;
import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import com.ctrip.train.kefu.system.offline.common.utils.PageInfo;
import com.ctrip.train.kefu.system.offline.system.service.SysPermissionService;
import com.ctrip.train.kefu.system.offline.system.vm.VmPermissionRequest;
import com.ctrip.train.kefu.system.offline.system.vm.VmPermissionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@Controller
public class PermissionController extends BaseController {
    @Autowired
    private SysPermissionService sysPermissionService;

    @RequestMapping("/permission")
    public String permission() {
        return "system/permission/permission";
    }

    @ResponseBody
    @RequestMapping(value = "/permission/list", produces = "application/json", method = RequestMethod.GET)
    public PageInfo searchPermission(int pageIndex, int pageSize) {
        VmPermissionRequest request= new VmPermissionRequest();
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        return sysPermissionService.searchPermissionForPage(request);
    }

    @ResponseBody
    @RequestMapping(value = "/permission/add", produces = "application/json", method = RequestMethod.POST)
    public JsonResult addPermission(HttpServletRequest request, @RequestBody VmPermissionRequest vmRequest) throws SQLException {

        int ss=sysPermissionService.addPermission(vmRequest);
        if (ss!=0) {
            return JsonResult.ok();
        }
        return JsonResult.fail();
    }

    @ResponseBody
    @RequestMapping(value ="/permission/get", produces = "application/json", method = RequestMethod.GET)
    public JsonResult searchPermissionById(Long  tId) throws SQLException {
        VmPermissionResponse ss =sysPermissionService.queryPermissionBytId(tId);
        if (ss!=null) {
            return JsonResult.ok().putData("response",ss);
        }
        return JsonResult.fail();
    }
    @RequestMapping(value = "/permission/edit", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult editPermission(HttpServletRequest request, @RequestBody VmPermissionRequest vmPermissionRequest) throws SQLException {
        int ss=sysPermissionService.editPermission(vmPermissionRequest);
        if (ss!=0) {
            return JsonResult.ok();
        }
        return JsonResult.fail();
    }
    @ResponseBody
    @RequestMapping(value ="/permission/remove", produces = "application/json", method = RequestMethod.GET)
    public JsonResult searchPermissionByStaffNum(Long  tId) throws SQLException {
        int ss =sysPermissionService.updatePerm(tId);
        if (ss==1) {
            return JsonResult.ok().putData("response",ss);
        }
        return JsonResult.fail();
    }
}
