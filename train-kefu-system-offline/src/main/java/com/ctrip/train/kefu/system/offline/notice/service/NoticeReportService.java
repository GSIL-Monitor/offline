package com.ctrip.train.kefu.system.offline.notice.service;
import com.ctrip.train.kefu.system.offline.notice.vm.notice.*;

import java.util.Date;
import java.util.List;

public interface NoticeReportService  {

    ResponseNoticeReport GetNoticeReport(RequestNoticeReport request);

    ResponseNoticeReport GetNoticeReportExport(RequestNoticeReport request);

    ResponseNoticeReport getNoticeReportExportTwo(RequestNoticeReport request);

    ResponseComplainReport getComplainReport(RequestComplainReport request);

    ResponseOnceSolveReport SearchOnceSolve(RequestOnceSolveReport request);
    /**
     * 通知一次性解决
     * @param request
     * @return
     */
    ResponseOnceSolveReport SearchOrderSolve(RequestOnceSolveReport request);
    /**
     * 客服一次性解决的单子
     * @param request
     * @return
     */
    ResponseOnceSolveReport SearchOnceSolveUser(RequestOnceSolveReport request);

    /**
     * 导出客服一次性解决的单子明细
     * @param request
     * @return
     */
    byte[] SearchOnceSolveUserDetail(RequestOnceSolveReport request);

    /**
     * 导出客服一次性解决的单子明细
     * @param
     * @return
     */
    byte[] SearchUrgeUserDetail(Date enterDate, String opUser);

    /**
     * 导出订单一次性解决的单子明细
     * @param request
     * @return
     */
    byte[] SearchOnceSolveOrderDetail(RequestOnceSolveReport request);
}
