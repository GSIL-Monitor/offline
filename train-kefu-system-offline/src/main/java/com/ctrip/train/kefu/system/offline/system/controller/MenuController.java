package com.ctrip.train.kefu.system.offline.system.controller;

import com.ctrip.train.kefu.system.offline.common.controller.BaseController;
import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import com.ctrip.train.kefu.system.offline.common.utils.PageInfo;
import com.ctrip.train.kefu.system.offline.system.service.SysMenuService;
import com.ctrip.train.kefu.system.offline.system.vm.VmMenuRequest;
import com.ctrip.train.kefu.system.offline.system.vm.VmMenuResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Map;

@Controller
public class MenuController extends BaseController {
    @Autowired SysMenuService sysMenuServiceImpl;


    @RequestMapping("/menu")
    public String mean(Map<String, Object> model) {
        model.put("availableMenus",sysMenuServiceImpl.availableMenus());
        return "system/menu/menu";
    }

    @ResponseBody
    @RequestMapping(value = "/menu/list", produces = "application/json", method = RequestMethod.GET)
    public PageInfo searchMenu(int pageIndex, int pageSize) {
        VmMenuRequest request= new VmMenuRequest();
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        return sysMenuServiceImpl.searchMenuForPage(request);
    }

    @ResponseBody
    @RequestMapping(value = "/menu/add", produces = "application/json", method = RequestMethod.POST)
    public JsonResult addMenu(HttpServletRequest request, @RequestBody VmMenuRequest vmRequest) throws SQLException {
        int ss=sysMenuServiceImpl.addMenu(vmRequest);
        if (ss!=0) {
            return JsonResult.ok();
        }
        return JsonResult.fail();
    }

    @ResponseBody
    @RequestMapping(value ="/menu/get", produces = "application/json", method = RequestMethod.GET)
    public JsonResult searchMenuById(Long  tId) throws SQLException {
        VmMenuResponse ss =sysMenuServiceImpl.queryMenuBytId(tId);
        if (ss!=null) {
            return JsonResult.ok().putData("response",ss);
        }
        return JsonResult.fail();
    }

    @RequestMapping(value = "/menu/edit", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult editMenu(HttpServletRequest request, @RequestBody VmMenuRequest vmMenuRequest) throws SQLException {
        int ss=sysMenuServiceImpl.editMenu(vmMenuRequest);
        if (ss!=0) {
            return JsonResult.ok();
        }
        return JsonResult.fail();
    }
    @ResponseBody
    @RequestMapping(value ="/menu/remove", produces = "application/json", method = RequestMethod.GET)
    public JsonResult searchRemoveId(Long  tId) throws SQLException {
        int ss =sysMenuServiceImpl.deleteMenu(tId);
        if (ss==1) {
            return JsonResult.ok().putData("response",ss);
        }
        return JsonResult.fail();
    }

}
