package com.ctrip.train.kefu.system.offline.system.dao;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import com.ctrip.train.kefu.system.offline.system.domain.MenusNodeResult;
import com.ctrip.train.kefu.system.offline.system.domain.MenusResult;
import com.ctrip.train.kefu.system.offline.system.vm.VmMenuRequest;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainchat.dao.OfflineMenuDao;
import dao.ctrip.ctrainchat.entity.OfflineMenu;
import org.springframework.stereotype.Repository;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ExtSysOfflineMenu extends OfflineMenuDao {

    protected DalQueryDao baseDao;
    public ExtSysOfflineMenu() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }

    public List<MenusNodeResult> searchMenu(VmMenuRequest request){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" SELECT m.*,pm.menuname as parentName  from offline_menu m LEFT JOIN offline_menu pm ON m.parent_id=pm.menuId ");
            builder.combine(" ORDER by    m.parent_id ASC,     m.menuname DESC ");
            builder.combinePageLimit(request.getPageIndex() > 0 && request.getPageSize() > 0, request.getPageIndex(), request.getPageSize());
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, MenusNodeResult.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchMenu", ex);
            return null;
        }
    }
    public int searchMenuCount(VmMenuRequest request){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" SELECT count(1)  From offline_menu where 1=1 ");
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.queryForObject(builder.getSql(), builder.getParameters(), hints, Integer.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchMenuCount", ex);
            return 0;
        }
    }

    public List<MenusResult> searchAvailableMenu(){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" SELECT m.menuId,m.menuName,node.menuId AS nodeId,node.menuName AS nodeName,node.parent_Id as parentId ");
            builder.combine(" FROM offline_menu m LEFT JOIN offline_menu node ON ");
            builder.combine(" (node.parent_id = m.menuId AND node.available = 1  AND node.type = 'menu' ) " );
            builder.combine(" WHERE m.available = 1 AND m.type = 'menu' AND (m.url IS  NULL OR m.url = '')" );
            builder.combine(" ORDER BY m.sort ASC, node.sort ASC ");
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, MenusResult.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchAvailableMenu", ex);
            return null;
        }
    }
    public long queryMaxId(){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" SELECT Max(menuId) From offline_menu");
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.queryForObject(builder.getSql(), builder.getParameters(), hints, Long.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchMenuCount", ex);
            return 0;
        }
    }
}
