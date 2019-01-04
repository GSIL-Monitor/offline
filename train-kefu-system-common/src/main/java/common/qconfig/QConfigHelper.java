package common.qconfig;

import common.constants.PropertyFile;
import qunar.tc.qconfig.client.MapConfig;

import java.util.Map;

/**
 * @author ying_zhou 2017/10/25 9:18
 */
public class QConfigHelper {
    /**
     * get remote config map from QConfig
     * @param configName
     * @return
     */
    public static Map<String, String> getQConfigMap(String configName) {
        MapConfig config = MapConfig.get(configName);
        return config.asMap();
    }


    /**
     * get prepared value in 'appsetting.properties' file stored in QConfig
     * @param key
     * @return
     */
    public static String getAppSetting(String key) {
        Map<String, String> map = getQConfigMap(PropertyFile.APP_SETTING_PROPERTIES);
        return map.get(key);
    }
}
