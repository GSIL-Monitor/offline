package com.ctrip.train.kefu.system.offline.system.controller;


import com.ctrip.train.kefu.system.offline.common.controller.BaseController;
import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import com.ctrip.train.kefu.system.offline.common.utils.PageInfo;
import com.ctrip.train.kefu.system.offline.system.service.SysPositionService;

import com.ctrip.train.kefu.system.offline.system.vm.VmPostRequest;
import com.ctrip.train.kefu.system.offline.system.vm.VmPostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * 职位
 */
@Controller
public class PositionController extends BaseController {
    @Autowired
    private SysPositionService  sysPositionServiceImpl;
    @RequestMapping("/position")
    public String position() {
        return "system/position/position";
    }

    @ResponseBody
    @RequestMapping(value = "/position/list", produces = "application/json", method = RequestMethod.GET)
    public PageInfo searchPermission(int pageIndex, int pageSize) {
        VmPostRequest request= new VmPostRequest();
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        return sysPositionServiceImpl.searchPostForPage(request);
    }

    @ResponseBody
    @RequestMapping(value = "/position/add", produces = "application/json", method = RequestMethod.POST)
    public JsonResult addPost(HttpServletRequest request, @RequestBody VmPostRequest vmRequest) throws SQLException {
        int ss=sysPositionServiceImpl.addPost(vmRequest);
        if (ss!=0) {
            return JsonResult.ok();
        }
        return JsonResult.fail();
    }

    @ResponseBody
    @RequestMapping(value ="/position/get", produces = "application/json", method = RequestMethod.GET)
    public JsonResult searchPostById(Long  tId) throws SQLException {
        VmPostResponse ss =sysPositionServiceImpl.queryPostBytId(tId);
        if (ss!=null) {
            return JsonResult.ok().putData("response",ss);
        }
        return JsonResult.fail();
    }

    @RequestMapping(value = "/position/edit", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult editPost(HttpServletRequest request, @RequestBody VmPostRequest vmPostRequest) throws SQLException {
        int ss=sysPositionServiceImpl.editPost(vmPostRequest);
        if (ss!=0) {
            return JsonResult.ok();
        }
        return JsonResult.fail();
    }
    @ResponseBody
    @RequestMapping(value ="/position/remove", produces = "application/json", method = RequestMethod.GET)
    public JsonResult searchRemoveId(Long  tId) throws SQLException {
        int ss =sysPositionServiceImpl.updatePost(tId);
        if (ss==1) {
            return JsonResult.ok().putData("response",ss);
        }
        return JsonResult.fail();
    }
}
