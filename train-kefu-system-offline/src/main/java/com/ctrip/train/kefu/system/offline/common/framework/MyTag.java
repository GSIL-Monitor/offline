package com.ctrip.train.kefu.system.offline.common.framework;

import com.ctrip.train.kefu.system.offline.common.component.StaffInfoComponent;
import com.ctrip.train.kefu.system.offline.common.domain.OfflineStaffInfo;
import com.ctrip.train.kefu.system.offline.system.service.SysMenuService;
import common.log.CLogger;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyTag implements TemplateDirectiveModel {

    private static final String METHOD_KEY = "method";

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysMenuService SysMenuServiceImpl;

    @Autowired
    private StaffInfoComponent staffInfoComponent;

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);

        if (staffInfoComponent.getEmpEntity()!=null){
            //当前登录人
            OfflineStaffInfo  staffInfo=staffInfoComponent.getEmpEntity();
            CLogger.info("MyTag:threadLocalVariableManager",staffInfo.getStaffBasicInfo().getUserNo());
            if (map.containsKey(METHOD_KEY)) {
                String method = map.get(METHOD_KEY).toString();
                switch (method) {
                    case "availableMenus":
                        // 获取所有可用的菜单资源
                        environment.setVariable("availableMenus", builder.build().wrap(SysMenuServiceImpl.availableMenus()));
                        break;
                    case "menus":
                        Map<String, String> params = new HashMap<>(2);
                        params.put("type", "menu");
                        params.put("staffNum", staffInfo.getStaffBasicInfo().getUserNo());
                        environment.setVariable("menus", builder.build().wrap(sysMenuService.searchStaffMenus(params)));
                        break;
                    default:
                        break;
                }
            }
            templateDirectiveBody.render(environment.getOut());
        }
        else {
            CLogger.error("MyTag:getStaffNumError","error");
        }
    }
}
