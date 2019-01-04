package com.ctrip.train.kefu.system.api.dao.user;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import com.ctrip.train.kefu.system.api.domain.user.DmUserInfo;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainchat.dao.UserVendorInfoDao;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;

@Repository
public class ExtUserVendorInfo extends UserVendorInfoDao {
    protected DalQueryDao baseDao;
    public ExtUserVendorInfo() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }

    /**
     * 获取供应商待处理处理通知
     * @param userName,password
     * @return
     * @throws SQLException
     */
    public DmUserInfo getUserInfo(String userName, String password)  {
        DalHints hints = new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine(" SELECT basic.userName ,basic.password ,basic.userNum ,basic.email, basic.realName,");
        builder.combine(" basic.telephone ,basic.userType,vendor.vendorCode,vendor.vendorName");
        builder.combine(" FROM user_basic_info basic");
        builder.combine(" INNER JOIN user_vendor_info vendor ON basic.Id=vendor.Id");
        builder.combine(" where  1=1 ");
        builder.combine(userName != null, " AND basic.UserName=?  ", Types.VARCHAR, userName);
        builder.combine(password != null, " AND basic.`Password`=?  ", Types.VARCHAR, password);
        hints.set(DalHintEnum.allowPartial);
        try {
            return baseDao.queryForObjectNullable(builder.getSql(), builder.getParameters(), hints, DmUserInfo.class);
        } catch (Exception ex) {
            CLogger.error("getUserInfo", ex);
        }
        return null;
    }
}
