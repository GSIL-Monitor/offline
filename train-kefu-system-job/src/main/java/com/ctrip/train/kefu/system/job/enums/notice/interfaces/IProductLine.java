package com.ctrip.train.kefu.system.job.enums.notice.interfaces;

/**
 * @see
 * @author ying_zhou 2017/11/6 14:10
 */
public interface IProductLine {

    int getProductLineCode() ;

    String getProductLineName() ;

    /**
     * @return the url template to join a url linked to the order's detail page
     */
    String getOrderDetailUrlTml();

    String getSearchOrderListUrlTml();
    /**
     * @return the url template to join a url linked to the notice's processing page
     */
    String getNoticeProcessUrlTml();

    String getNoticeType();
    String getLeaderNoticeType();
    String getComplainType();

    /**
     * return the enumerations' field type of notice source
     * @return
     */
    String getNoticeSource();
    /**
     * return the enumerations' field type of complain source
     * @return
     */
    String getComplainSource();
    /**
     * responsibility type to add exception price
     * @return
     */
    String getResponseType();
}
