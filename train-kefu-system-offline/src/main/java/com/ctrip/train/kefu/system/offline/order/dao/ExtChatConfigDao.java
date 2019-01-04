package com.ctrip.train.kefu.system.offline.order.dao;

import com.ctrip.platform.dal.dao.DalQueryDao;
import com.ctrip.platform.dal.exceptions.DalException;
import common.constants.DatabaseName;
import dao.ctrip.ctrainchat.dao.ChatConfigDao;
import dao.ctrip.ctrainchat.entity.ChatConfig;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class ExtChatConfigDao extends ChatConfigDao {

    protected DalQueryDao baseDao;
    public ExtChatConfigDao() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }

    /**
     * @param
     * @return 1 online  2 offline 3 rest
     * default returned value is 2
     */
    public String getConfig(String key) throws DalException {

        try
        {
            ChatConfig tempQuery=new ChatConfig();
            tempQuery.setConfigType(key);
            List<ChatConfig> cc = queryBy(tempQuery);
            if (cc != null && cc.size() > 0)
            {
                return cc.get(0).getConfigValue();
            }
        }
        catch (Exception ex)
        {
            throw new DalException("调用OnlineChatDao时，访问GetChatConfig时出错", ex);
        }
        return "";
    }
}
