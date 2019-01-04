package com.ctrip.train.kefu.system.api.dao.order;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainchat.dao.ChatConfigDao;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

@Repository
public class ChatConfigEx extends ChatConfigDao {
    protected DalQueryDao baseDao;

    public ChatConfigEx() throws SQLException {
        super();
        baseDao = new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }

    /**
     * 获取配置
     */
    public String getConfigValue(String configType) {
        try {
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("select Config_Value");
            builder.combine("from chat_config");
            builder.combine("where 1=1");
            builder.combine("and Config_Type=?", Types.VARCHAR, configType);
            builder.combine("limit 1");
            String strSmsContent = baseDao.queryForObjectNullable(builder.getSql(), builder.getParameters(), hints, String.class);
            return strSmsContent;
        } catch (SQLException ex) {
            CLogger.error("ChatConfigEx", ex);
            return null;
        }
    }

    /**
     * 获取IVR转接方式(VDN或ICR)
     */
    public String ivrTransferInfoType() {
        String transferType = "VDN";
        String configValue = getConfigValue("IVR_TransferInfo_Type");
        if (configValue != null && !configValue.isEmpty()) {
            transferType = configValue;
        }
        return transferType;
    }

    /**
     * IVR转接人工信息
     */
    public Map<String, String> getTransferParam(String configType) {
        String configValue = getConfigValue(configType);
        Map<String, String> transferDic = new HashMap<>();
        if (configValue != null && !configValue.isEmpty()) {
            {
                String[] configValues = configValue.split(";");
                for (int i = 0; i < configValues.length; i++) {
                    String key = configValues[i].split("\\|")[0];
                    String value = configValues[i].split("\\|")[1];
                    transferDic.put(key, value);
                }
            }
        }
        return transferDic;
    }

    /**
     * IVR电话组互溢等待时长
     */
    public int ivrTransferInfoWaitSec() {
        int temp = 20;
        String configValue = getConfigValue("IVR_TransferInfo_WaitSec");
        if (configValue != null && !configValue.isEmpty()) {
            temp = Integer.parseInt(configValue);
        }
        return temp;
    }
}
