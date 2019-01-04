package com.ctrip.train.kefu.system.api.dao.notice;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import com.ctrip.train.kefu.system.api.domain.notice.DmNoticeVendor;
import com.ctrip.train.kefu.system.api.domain.notice.DmNoticeVendorCondition;
import com.ctrip.train.kefu.system.api.infrastructure.helper.page.PageRequest;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainpps.dao.NoticeVendorDao;
import dao.ctrip.ctrainpps.entity.NoticeVendor;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class ExtNoitceVendor extends NoticeVendorDao {
    protected DalQueryDao baseDao;
    public ExtNoitceVendor() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_PPS_DB);
    }


    /**
     * 获取供应商待处理处理通知
     */
    public List<DmNoticeVendor> getVendorPendingList(String vendorCode) {
        DalHints hints = new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine(" SELECT  vendor.id,vendor.noticeId,notice.emergeState,notice.orderId,notice.OpUser,vendor.opCount,  ");
        builder.combine(" notice.noticeType,notice.noticeState,notice.opUserNum,notice.isDelete,notice.noticeSecondType,");
        builder.combine(" vendor.verdorCode,vendor.verdorName,vendor.contents ,vendor.sendType,vendor.sendCode,");
        builder.combine(" vendor.sendName,vendor.sendTime,vendor.appointedProcessTime ");
        builder.combine(" from  notice_vendor vendor  ");
        builder.combine(" Inner JOIN notice_complain_info notice ON vendor.NoticeId=notice.ID  ");
        builder.combine(" WHERE notice.NoticeState in (80,81,82,84,102)  ");
        builder.combine(vendorCode != null, " AND vendor.VerdorCode=?", vendorCode);
        builder.combine(" ORDER BY vendor.AppointedProcessTime Desc,notice.opCount Desc, notice.emergeState Desc ,vendor.SendTime ASC,notice.SendTime asc");
        hints.set(DalHintEnum.allowPartial);
        try {
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, DmNoticeVendor.class);
        }
        catch (Exception ex){
            CLogger.error("getVendorPendingList",ex);
        }

        return null;
    }


    /**
     * 获取供应商通知数据列表
     * 分页
     */
    public List<DmNoticeVendor> getVendorList(PageRequest<DmNoticeVendorCondition> request){
        DmNoticeVendorCondition condition=request.getCondition();
        DalHints hints = new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine(" SELECT  vendor.id,vendor.noticeId,notice.emergeState,notice.orderId,vendor.OpUser,vendor.OpTime,vendor.opCount, ");
        builder.combine(" notice.noticeType,notice.noticeState,notice.opUserNum,notice.isDelete,notice.noticeSecondType,");
        builder.combine(" notice.completeTime,vendor.verdorCode,vendor.verdorName,vendor.contents ,vendor.sendType,vendor.sendCode,");
        builder.combine(" vendor.sendName,vendor.sendTime,vendor.appointedProcessTime");
        builder.combine(" from  notice_vendor vendor  ");
        builder.combine(" Inner JOIN notice_complain_info notice ON vendor.NoticeId=notice.ID  ");
        builder.combine(" WHERE 1=1  ");
        builder.combine(condition.getVerdorCode() != null," AND vendor.VerdorCode=?", Types.VARCHAR ,condition.getVerdorCode());
        builder.combine(condition.getOrderId() != null, " AND notice.orderId=?", Types.VARCHAR ,condition.getOrderId() );
        builder.combine(condition.getSendName()!= null, " AND vendor.sendName like ?", Types.VARCHAR ,condition.getSendName()+"%");
        builder.combine(condition.getStartTime() != null, " AND vendor.sendtime>?", Types.VARCHAR ,condition.getStartTime());
        builder.combine(condition.getEndTime() != null, " AND vendor.sendtime<?",Types.VARCHAR , condition.getEndTime());
        builder.combine(condition.getSendType() != null && condition.getSendType() > 0,
                " AND vendor.sendType=?",Types.INTEGER , condition.getSendType());
        builder.combine(condition.getOpUserType()!= null, " AND vendor.opUserType=?",Types.INTEGER , condition.getOpUserType());
        if (condition.getNoticeStatus() != null) {
            builder.combine(condition.getNoticeStatus() == 1, " AND vendor.opCount>0 ");
            builder.combine(condition.getNoticeStatus() == 2, " AND notice.emergeState=1 ");
            builder.combine(condition.getNoticeStatus() == 3, " AND notice.emergeState= 0 And vendor.opCount is null ");
        }
        builder.combine(condition.getNoticeType()!= null&&condition.getNoticeType()>0, " AND notice.noticetype=?",Types.INTEGER , condition.getNoticeType());
        builder.combineIn(condition.getNoticeStates() != null, " AND notice.noticeState in (?)", Types.INTEGER, condition.getNoticeStates());
        builder.combine(" ORDER BY vendor.opCount Desc ,notice.emergeState Desc ,vendor.opTime Desc,notice.SendTime asc");
        builder.combinePageLimit(request.getPageIndex(), request.getPageSize());
        hints.set(DalHintEnum.allowPartial);

        try {
            return   baseDao.query(builder.getSql(), builder.getParameters(), hints, DmNoticeVendor.class);
        }
        catch (Exception ex){
            CLogger.error("getVendorList",ex);
        }

        return null;

    }

    /**
     * 获取供应商通知数据总数
     * 分页
     */
    public int getVendorCount(DmNoticeVendorCondition condition) {
        DalHints hints = new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine(" SELECT Count(1) ");
        builder.combine(" from  notice_vendor vendor  ");
        builder.combine(" Inner JOIN notice_complain_info notice ON vendor.NoticeId=notice.ID  ");
        builder.combine(" WHERE 1=1   ");
        builder.combine(condition.getVerdorCode() != null, " AND vendor.VerdorCode=?", Types.VARCHAR, condition.getVerdorCode());
        builder.combine(condition.getOrderId() != null, " AND notice.orderId=?", Types.VARCHAR, condition.getOrderId());
        builder.combine(condition.getSendName() != null, " AND vendor.sendName like ?", Types.VARCHAR, condition.getSendName() + "%");
        builder.combine(condition.getStartTime() != null, " AND vendor.sendtime>?", Types.VARCHAR, condition.getStartTime());
        builder.combine(condition.getEndTime() != null, " AND vendor.sendtime<?", Types.VARCHAR, condition.getEndTime());
        builder.combine(condition.getSendType() != null && condition.getSendType() > 0,
                " AND vendor.sendType=?", Types.INTEGER, condition.getSendType());
        builder.combine(condition.getOpUserType() != null, " AND vendor.opUserType=?", Types.INTEGER, condition.getOpUserType());
        if (condition.getNoticeStatus() != null) {
            builder.combine(condition.getNoticeStatus() == 1, " AND vendor.opCount>0 ");
            builder.combine(condition.getNoticeStatus() == 2, " AND notice.emergeState=1 ");
            builder.combine(condition.getNoticeStatus() == 3, " AND notice.emergeState= 0 And vendor.opCount is null ");
        }
        builder.combine(condition.getNoticeType() != null && condition.getNoticeType() > 0, " AND notice.noticetype=?", Types.INTEGER, condition.getNoticeType());
        builder.combineIn(condition.getNoticeStates() != null, " AND notice.noticeState in (?)", Types.INTEGER, condition.getNoticeStates());
        builder.combine(" ORDER BY vendor.opCount Desc ,notice.emergeState Desc ,vendor.opTime Desc,notice.SendTime asc");
        hints.set(DalHintEnum.allowPartial);

        try {
            return baseDao.queryForObjectNullable(builder.getSql(), builder.getParameters(), hints, Integer.class);
        } catch (Exception ex) {
            CLogger.error("getVendorCount", ex);
        }
        return 0;
    }

    /**
     * 根据主键获取供应商通知数据
     * 分页
     */
    public DmNoticeVendor getVendorNotice(Long noticeId)  {
        DalHints hints = new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine(" SELECT  vendor.id,vendor.noticeId,notice.emergeState,notice.orderId,vendor.OpUser,vendor.OpTime,vendor.opCount, ");
        builder.combine(" notice.noticeType,notice.noticeState,notice.opUserNum ,notice.isDelete,notice.noticeSecondType,");
        builder.combine(" vendor.verdorCode,vendor.verdorName,vendor.contents ,vendor.sendType,vendor.sendCode,");
        builder.combine(" vendor.sendName,vendor.sendTime,vendor.appointedProcessTime ");
        builder.combine(" from  notice_vendor vendor  ");
        builder.combine(" Inner JOIN notice_complain_info notice ON vendor.NoticeId=notice.ID  ");
        builder.combine(" WHERE 1=1 ");
        builder.combine(noticeId != null , " AND notice.id=?", noticeId);
        hints.set(DalHintEnum.allowPartial);
        try {
            return   baseDao.queryForObjectNullable(builder.getSql(), builder.getParameters(), hints, DmNoticeVendor.class);
        }
        catch (Exception ex){
            CLogger.error("getVendorNotice",ex);
        }
        return  null;

    }

    /**
     * 根据订单号获取供应商通知数据
     * 分页
     */
    public List<DmNoticeVendor> getVendorNotice(String orderId)  {
        DalHints hints = new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine(" SELECT  vendor.id,vendor.noticeId,notice.emergeState,notice.orderId,vendor.OpUser,vendor.OpTime,vendor.opCount,   ");
        builder.combine(" notice.noticeType,notice.noticeState,notice.opUserNum,notice.isDelete,notice.noticeSecondType,");
        builder.combine(" vendor.verdorCode,vendor.verdorName,vendor.contents ,vendor.sendType,vendor.sendCode,");
        builder.combine(" vendor.sendName,vendor.sendTime,vendor.appointedProcessTime ");
        builder.combine(" from  notice_vendor vendor  ");
        builder.combine(" Inner JOIN notice_complain_info notice ON vendor.NoticeId=notice.ID  ");
        builder.combine(" WHERE 1=1 ");
        builder.combine(orderId != null , " AND notice.orderId=?", Types.VARCHAR,orderId);
        hints.set(DalHintEnum.allowPartial);
        try {
            return   baseDao.query(builder.getSql(), builder.getParameters(), hints, DmNoticeVendor.class);
        }
        catch (Exception ex){
            CLogger.error("getVendorNotice",ex);
        }
        return  null;

    }


    /**
     * 催处理更新通知数据
     */
    public void reminderVendoNotice(Long noticeId){
        DalHints hints = new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine(" update notice_complain_info ");
        builder.combine(" SET OpCount=OpCount+1");
        builder.combine(" where 1=1 ");
        builder.combine(noticeId != null ," AND ID=?", noticeId);
        hints.set(DalHintEnum.allowPartial);
        try {
            baseDao.getClient().update(builder.getSql(), builder.getParameters(), hints);
        }
        catch (Exception ex){
            CLogger.error("reminderVendoNotice",ex);
        }
    }

    /**
     * 修改操作数据
     */
    public void updateOpdate(NoticeVendor vendor) throws SQLException {
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine("UPDATE notice_vendor ")
                .combine(" SET OpTime=now()")
                .combine(",OpUser=?", vendor.getOpUser())
                .combine(",OpUserType=?",vendor.getOpUserType())
                .combine(" WHERE noticeId= ?", vendor.getNoticeId());
        baseDao.getClient().update(builder.getSql(), builder.getParameters(), new DalHints());
    }

}
