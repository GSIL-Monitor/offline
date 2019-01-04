package com.ctrip.train.kefu.system.job.config;


import com.ctrip.train.kefu.system.job.constants.PropertyFile;
import common.qconfig.QConfigHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author ying_zhou 2017/11/5 13:14
 */
@Service
public class CommonBusiness {
    /**
     * get prepared value in 'appsetting.properties' file stored in QConfig
     * @param key
     * @return
     */
    public String getAppSetting(String key) {
        Map<String, String> map = QConfigHelper.getQConfigMap(PropertyFile.APP_SETTING_PROPERTIES);
        return map.get(key);
    }
}
