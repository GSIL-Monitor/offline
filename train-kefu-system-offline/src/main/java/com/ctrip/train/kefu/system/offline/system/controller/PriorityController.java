package com.ctrip.train.kefu.system.offline.system.controller;

import com.ctrip.train.kefu.system.offline.common.controller.BaseController;
import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import com.ctrip.train.kefu.system.offline.common.utils.PageInfo;
import com.ctrip.train.kefu.system.offline.notice.enums.ProductLineEnum;
import com.ctrip.train.kefu.system.offline.system.service.SysPriorityService;
import com.ctrip.train.kefu.system.offline.system.service.SysStaffService;
import com.ctrip.train.kefu.system.offline.system.vm.SmallEnumResponse;
import com.ctrip.train.kefu.system.offline.system.vm.VmPriorityRequest;
import common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 客服优先处理通知设置  多产线支持
 */
@Controller
public class PriorityController extends BaseController {

    @Autowired
    private SysPriorityService sysPriorityServiceImpl;


    @RequestMapping("/priority")
    public String priority() {
        return "system/priority/priority";
    }
    @ResponseBody
    @RequestMapping(value = "/priority/list", produces = "application/json", method = RequestMethod.GET)
    public PageInfo priorityList(int pageIndex, int pageSize,String staffName) throws SQLException {
        VmPriorityRequest request=new VmPriorityRequest();
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        request.setStaffName(StringUtils.isBlank(staffName)?null:staffName);
        return sysPriorityServiceImpl.searchPriorityForPage(request);
    }

    @RequestMapping(value = "/prioritypre", produces = "application/json", method = RequestMethod.GET)
    public String addPriorityPre(Map<String, Object> model ,HttpServletRequest request) throws SQLException {

        ProductLineEnum[] productLineEnums=ProductLineEnum.values();
        model.put("productLineEnums", Arrays.stream(productLineEnums).collect(Collectors.toList()));
        return "system/priority/addPriority";
    }

    @RequestMapping("/priority/add")
    @ResponseBody
    public JsonResult addPriority(@RequestBody VmPriorityRequest request) throws SQLException {
        return sysPriorityServiceImpl.addPriority(request);
    }

    @ResponseBody
    @RequestMapping(value ="/priority/get", produces = "application/json", method = RequestMethod.GET)
    public JsonResult searchPriorityById(Long  tId) throws SQLException {
        List<Map<String, Object>> sList =sysPriorityServiceImpl.queryPriorityBytId(tId);
        if (sList!=null&&sList.size()>0) {
            return JsonResult.ok().putData("response",sList);
        }
        return JsonResult.fail();
    }

    @RequestMapping(value = "/priority/edit", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult editPriority(HttpServletRequest request, @RequestBody VmPriorityRequest vmPriorityRequest) throws SQLException {
        int ss=sysPriorityServiceImpl.editPriority(vmPriorityRequest);
        if (ss!=0) {
            return JsonResult.ok();
        }
        return JsonResult.fail();
    }

    @RequestMapping(value = "/priority/getFirstType", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult editGetFirstType(int productLine,String staffNum) throws SQLException {
        SmallEnumResponse response=sysPriorityServiceImpl.searchSmallEnumsAll(productLine);
        if (response!=null) {
            return JsonResult.ok().putData("response",response);
        }
        return JsonResult.fail();
    }

    @ResponseBody
    @RequestMapping(value = "/priority/editstatus", produces = "application/json", method = RequestMethod.GET)
    public JsonResult removePriority(Long  tId) throws SQLException {
        return sysPriorityServiceImpl.updatePriorityStatus(tId);
    }


}
