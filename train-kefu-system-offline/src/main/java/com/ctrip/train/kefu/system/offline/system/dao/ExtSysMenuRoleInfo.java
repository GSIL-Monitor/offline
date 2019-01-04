package com.ctrip.train.kefu.system.offline.system.dao;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import com.ctrip.train.kefu.system.offline.system.domain.RoleMenuResult;
import com.ctrip.train.kefu.system.offline.system.domain.StaffMenuResult;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainchat.dao.OfflineMenuRoleDao;
import dao.ctrip.ctrainchat.entity.OfflineMenu;
import dao.ctrip.ctrainchat.entity.OfflineRole;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class ExtSysMenuRoleInfo extends OfflineMenuRoleDao {

    protected DalQueryDao baseDao;
    public ExtSysMenuRoleInfo() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }

    public void removeByRoleId(long rId){
        DalHints hints=new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine("delete from offline_menu_role where roleId=?", Types.INTEGER,rId);
        hints.set(DalHintEnum.allowPartial);
        try {
            baseDao.getClient().update(builder.getSql(), builder.getParameters(),new DalHints());
        } catch (SQLException e) {
            CLogger.error("dao:removeByRoleId", e);
        }
    }

    /**
     * 查询找右侧菜单栏
     * @param map
     * @return
     */
    public List<OfflineMenu> searchStaffMenu(Map<String,String> map){
        DalHints hints=new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();

        builder.combine(" select m.* from offline_menu m where  m.menuid in (select distinct rm.menuid from offline_menu_role rm ");
        builder.combine(" left join offline_role r on(rm.roleid = r.roleid) left join offline_staff_role ur on(ur.roleid = r.roleid) ");
        builder.combine(" left join chat_staff_info u on (u.staff_Number = ur.staffNum) ");
        builder.combine(" where u.staff_Number =?) order by m.create_time ",Types.VARCHAR,map.get("staffNum")  );
        try {
            return  baseDao.query(builder.getSql(),builder.getParameters(),hints,OfflineMenu.class);
        } catch (SQLException e) {
            CLogger.error("dao:searchStaffMenu", e);
            return null;
        }
    }
}
