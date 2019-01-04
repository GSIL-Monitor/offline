package com.ctrip.train.kefu.system.offline.notice.dao;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import com.ctrip.train.kefu.system.offline.order.vm.RequestAirOrderEx;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainchat.dao.AirNoticeExcetionDao;
import dao.ctrip.ctrainchat.entity.AirNoticeExcetion;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class ExtAirNoticeExcetionInfo extends AirNoticeExcetionDao {

    protected DalQueryDao baseDao;
    public ExtAirNoticeExcetionInfo() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }

    /**
     * 机票异常单处理
     */
    public List<AirNoticeExcetion> SearchAirNoticeEx(RequestAirOrderEx request){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("SELECT *  From air_notice_excetion where 1=1");
            builder.combine(request.getStartDate() != null, " and date_format(sendOrderTime,'%y-%m-%d') >=date_format(?,'%y-%m-%d')", Types.DATE, request.getStartDate());
            builder.combine(request.getEndDate() != null, " and date_format(sendOrderTime,'%y-%m-%d')<=date_format(?,'%y-%m-%d')", Types.DATE, request.getEndDate());
            builder.combine( request.getProductLine()!= null," and ProductLine=?", Types.INTEGER,request.getProductLine());
            builder.combine( request.getOrderId()!= null," and orderId=?", Types.VARCHAR,request.getOrderId());
            builder.combine( request.getSupplier()!= null," and supplier=?", Types.VARCHAR,request.getSupplier());
            builder.combine( request.geteXOrderType()!= null," and exOrderType=?", Types.VARCHAR,request.geteXOrderType());
            builder.combine( " order by sendOrderTime Desc ");
            builder.combinePageLimit(request.getPageIndex() > 0 && request.getPageSize() > 0, request.getPageIndex(), request.getPageSize());
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, AirNoticeExcetion.class);
        } catch (Exception ex) {
            CLogger.error("dao:SearchAirNoticeEx", ex);
            return null;
        }
    }
    /**
     * 机票异常单处理
     */
    public int SearchAirNoticeExCount(RequestAirOrderEx request){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("SELECT count(0)  From air_notice_excetion where 1=1");
            builder.combine(request.getStartDate() != null, " and date_format(sendOrderTime,'%y-%m-%d') >=date_format(?,'%y-%m-%d')", Types.DATE, request.getStartDate());
            builder.combine(request.getEndDate() != null, " and date_format(sendOrderTime,'%y-%m-%d')<=date_format(?,'%y-%m-%d')", Types.DATE, request.getEndDate());
            builder.combine( request.getProductLine()!= null," and ProductLine=?", Types.INTEGER,request.getProductLine());
            builder.combine( request.getOrderId()!= null," and orderId=?", Types.VARCHAR,request.getOrderId());
            builder.combine( request.getSupplier()!= null," and supplier=?", Types.VARCHAR,request.getSupplier());
            builder.combine( request.geteXOrderType()!= null," and exOrderType=?", Types.VARCHAR,request.geteXOrderType());
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.queryForObject(builder.getSql(), builder.getParameters(), hints, Integer.class);
        } catch (Exception ex) {
            CLogger.error("dao:GetNoticeList", ex);
            return 0;
        }
    }

}
