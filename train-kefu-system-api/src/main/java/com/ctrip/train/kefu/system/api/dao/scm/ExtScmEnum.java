package com.ctrip.train.kefu.system.api.dao.scm;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainpps.dao.ScmSmallEnumDao;
import dao.ctrip.ctrainpps.entity.ScmSmallEnum;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class ExtScmEnum extends ScmSmallEnumDao{
    protected DalQueryDao baseDao;
    public ExtScmEnum() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_PPS_DB);
    }

    /**
     * 获取枚举数据
     * */
    public List<ScmSmallEnum> GetEnumList(String fieldType) {
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("SELECT * FROM scm_small_enum ");
            builder.combine(" where is_deleted=0  ");
            builder.combine(fieldType != null, " and Field_Type=?", Types.VARCHAR, fieldType);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, ScmSmallEnum.class);
        } catch (Exception ex) {
            CLogger.error("dao:GetNoticeTypeEnum", ex);
            return null;
        }
    }


    /**
     * 获取枚举数据
     * */
    public List<ScmSmallEnum> GetEnumList(Long upperTid) {
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("SELECT * FROM scm_small_enum ");
            builder.combine(" where 1=1  and Is_Deleted='0' ");
            builder.combine(upperTid >0, " and FK_Upper_Tid=?", Types.BIGINT, upperTid);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, ScmSmallEnum.class);
        } catch (Exception ex) {
            CLogger.error("dao:GetNoticeTypeEnum", ex);
            return null;
        }
    }
}
