package com.ctrip.train.kefu.system.offline.common.framework;

import com.ctrip.train.kefu.system.offline.common.component.StaffInfoComponent;
import com.ctrip.train.kefu.system.offline.common.domain.OfflineStaffInfo;
import com.ctrip.train.kefu.system.offline.system.service.impl.SysMenuServiceImpl;
import common.log.CLogger;
import dao.ctrip.ctrainchat.entity.OfflinePermission;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MyPerm implements TemplateDirectiveModel {

    private static final String METHOD_KEY = "method";
    private static final String PERM_CODE = "permissionCode";

    @Autowired
    private StaffInfoComponent staffInfoComponent;

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
        if (staffInfoComponent.getEmpEntity()!=null){
            //当前登录人
            OfflineStaffInfo staffInfo=staffInfoComponent.getEmpEntity();
            List<OfflinePermission> permissions =staffInfo.getStaffPermission();
            CLogger.info("MyTag:threadLocalVariableManager",staffInfo.getStaffBasicInfo().getUserNo());
            if (map.containsKey(METHOD_KEY)) {
                String method = map.get(METHOD_KEY).toString();
                switch (method) {
                    case "hasButtonPermission":
                        environment.setVariable("hasPermissionFlag", builder.build().wrap(
                                permissions.stream().anyMatch(p->p.getPermCode().equals(map.get(PERM_CODE).toString())) ));

                        break;
                    case "test":
                        break;
                    default:
                        break;
                }
            }
            templateDirectiveBody.render(environment.getOut());
        }



    }


}
