package com.ctrip.train.kefu.system.offline.system.controller;

import com.ctrip.train.kefu.system.offline.common.controller.BaseController;
import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import com.ctrip.train.kefu.system.offline.common.utils.PageInfo;
import com.ctrip.train.kefu.system.offline.notice.enums.ProductLineEnum;
import com.ctrip.train.kefu.system.offline.system.service.SysRoleService;
import com.ctrip.train.kefu.system.offline.system.vm.VmRoleRequest;
import com.ctrip.train.kefu.system.offline.system.vm.VmRoleResponse;
import com.ctrip.train.kefu.system.offline.system.vm.VmStaffResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class RoleController extends BaseController {

    @Autowired
    private SysRoleService sysRoleServiceImpl;

    @RequestMapping("/role")
    public String Role(Map<String, Object> model) {
//        ProductLineEnum[] productLineEnums=ProductLineEnum.values();
//        model.put("productLineEnums", Arrays.stream(productLineEnums).collect(Collectors.toList()));
        return "system/role/role";
    }

    @ResponseBody
    @RequestMapping(value = "/role/add", produces = "application/json", method = RequestMethod.POST)
    public JsonResult addRole(HttpServletRequest request, @RequestBody VmRoleRequest vmRoleRequest) throws SQLException {
        int ss=sysRoleServiceImpl.addRole(vmRoleRequest);
        if (ss!=0) {
            return JsonResult.ok();
        }
        return JsonResult.fail();
    }

    @ResponseBody
    @RequestMapping(value = "/role/list", produces = "application/json", method = RequestMethod.GET)
    public PageInfo searchRole(int pageIndex, int pageSize) {
        return sysRoleServiceImpl.searchRoleForPage(pageIndex,pageSize);
    }

    @ResponseBody
    @RequestMapping(value = "/role/get", produces = "application/json", method = RequestMethod.GET)
    public VmRoleResponse getRole(HttpServletRequest request, @RequestParam Long roleId) {
        return sysRoleServiceImpl.getRoleByRoleId(roleId);
    }

    @ResponseBody
    @RequestMapping(value = "/role/edit", produces = "application/json", method = RequestMethod.POST)
    public int editRole(HttpServletRequest request, @RequestBody VmRoleRequest vmRoleRequest) {
        return sysRoleServiceImpl.editRole(vmRoleRequest);
    }

    @RequestMapping("/role/roleMenu")
    @ResponseBody
    public JsonResult searchRoleMenu(Long roleId) {
         return JsonResult.ok().putData("response",sysRoleServiceImpl.searchRoleMenu(roleId));
    }


    @RequestMapping("/role/editRoleMenu")
    @ResponseBody
    public void editRoleMenu(@RequestBody Map<String,String> map) throws SQLException {

        sysRoleServiceImpl.updateRoleMenu(Long.valueOf(map.get("rolesId")),map.get("menuIds"));
    }

    @RequestMapping("/role/roleperm")
    @ResponseBody
    public JsonResult searchRolePerm(Long roleId) {
        return JsonResult.ok().putData("response",sysRoleServiceImpl.searchRolePerm(roleId));
    }


    @RequestMapping("/role/editRolePerm")
    @ResponseBody
    public void editRolePerm(@RequestBody Map<String,String> map) throws SQLException {
        sysRoleServiceImpl.updateRolePerm(Long.valueOf(map.get("rolesId")),map.get("permId"));
    }
}
