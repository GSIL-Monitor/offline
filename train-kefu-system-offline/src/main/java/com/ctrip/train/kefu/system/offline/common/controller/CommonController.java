package com.ctrip.train.kefu.system.offline.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/common")
public class CommonController {


    /**
     * 错误页面
     */
    @RequestMapping("/error/{code}")
    public ModelAndView handlerError(@PathVariable int code, Map<String, Object> model) {
        model.put("code", code);
        model.put("msg",getHintMessage(code));
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addAllObjects(model);
        modelAndView.setViewName("error");
        return modelAndView;
    }

    private String getHintMessage(int code) {
        switch (code) {
            case 400:
                return "页面参数错误";
            case 401:
                return "没有页面访问权限";
            case 404:
                return "没有找到该页面";
            case 500:
                return "服务器内部错误";
        }
        return "出错了";
    }
}
