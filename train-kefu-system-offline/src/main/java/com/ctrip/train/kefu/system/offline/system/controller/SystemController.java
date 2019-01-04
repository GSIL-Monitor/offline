package com.ctrip.train.kefu.system.offline.system.controller;

import com.ctrip.basebiz.offlineBase.LoginState.LoginStateBase;
import com.ctrip.train.kefu.system.offline.common.controller.BaseController;
import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SystemController extends BaseController{
    @RequestMapping("user/logout")
    @ResponseBody
    public JsonResult logout(HttpServletRequest request, HttpServletResponse response){
        try {
            LoginStateBase.LogOut(request,response);
        } catch (ServletException e) {
            return JsonResult.fail("注销失败");
        }
        return JsonResult.ok("注销成功");
    }
    @RequestMapping("/index")
    public String index() {
        return "system/index";
    }
}
