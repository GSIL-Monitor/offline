package com.ctrip.train.kefu.system.api.dao.notice;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import common.util.DalUtils;
import common.util.StringUtils;
import dao.ctrip.ctrainpps.dao.NoticeComplainInfoDao;
import dao.ctrip.ctrainpps.entity.NoticeComplainInfo;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;


@Repository
public class ExtNotice extends NoticeComplainInfoDao {

    protected DalQueryDao baseDao;
    public ExtNotice() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_PPS_DB);
    }

    /**
     * 获取通知数据
     * @param notice
     * @return
     * @throws SQLException
     */
    public List<NoticeComplainInfo> getNotiList(NoticeComplainInfo notice) throws SQLException {
        DalHints hints=new DalHints();
        DalUtils.Builder builder=DalUtils.createBuilder();
        builder.combine("SELECT * FROM notice_complain_info where 1=1 ");
        builder.combine(StringUtils.isNotBlank(notice.getOrderID()),"and orderid = ?", Types.VARCHAR,notice.getOrderID());
        builder.combine(notice.getNoticeType()>0,"and NoticeType=?", Types.INTEGER,notice.getNoticeType());
        builder.combine(notice.getNoticeSecondType()>0,"and NoticeSecondType=?", Types.INTEGER,notice.getNoticeSecondType());
        builder.combine(notice.getComplainSource()>0,"and ComplainSource=?", Types.INTEGER,notice.getComplainSource());
        builder.combine(StringUtils.isNotBlank(notice.getEnterUser()),"and EnterUser=?",Types.VARCHAR,notice.getEnterUser());
        hints.set(DalHintEnum.allowPartial);
        return baseDao.query(builder.getSql(),builder.getParameters(),hints,NoticeComplainInfo.class);
    }
}
