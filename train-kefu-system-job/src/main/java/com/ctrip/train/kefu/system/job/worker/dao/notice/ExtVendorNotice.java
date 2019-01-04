package com.ctrip.train.kefu.system.job.worker.dao.notice;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainpps.dao.NoticeVendorDao;
import dao.ctrip.ctrainpps.entity.NoticeVendor;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class ExtVendorNotice extends NoticeVendorDao {
    private DalQueryDao baseDao;
    public ExtVendorNotice() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_PPS_DB);
    }
    /**
     * 获取供应商待处理处理通知
     * 初始化之前通知操作数据
     * 每次获取50条数
     */
    public List<NoticeVendor> getVendorList() {
        DalHints hints = new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine(" SELECT  vendor.* ");
        builder.combine(" from  notice_vendor vendor  ");
        builder.combine(" Inner JOIN notice_complain_info notice ON vendor.NoticeId=notice.ID  ");
        builder.combine(" WHERE notice.NoticeState in (80,81,82,83,84,102)  and vendor.opuser is null ");
        builder.combine(" ORDER BY vendor.SendTime ASC");
        builder.combine(" limit 50");
        hints.set(DalHintEnum.allowPartial);
        try {
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, NoticeVendor.class);
        }
        catch (Exception ex){
            CLogger.error("getVendorPendingList",ex);
        }

        return null;
    }
}
