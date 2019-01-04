package com.ctrip.train.kefu.system.job.enums.notice;




import com.ctrip.train.kefu.system.job.enums.notice.interfaces.IProductLine;
import com.ctrip.train.kefu.system.job.constants.BizConstant;

import java.util.Arrays;

/**
 * 产品线枚举
 */
public enum ProductLineEnum implements IProductLine {
    Train {
        @Override
        public int getProductLineCode() { return 134; }
        @Override
        public String getProductLineName() { return "火车票"; }
        @Override
        public String getOrderDetailUrlTml() { return BizConstant.OLD_PAGE_DOMAIN+"/OrderPageNew/ShowOrderDetailNew.aspx?OrderID=%s"; }
        @Override
        public String getSearchOrderListUrlTml() { return BizConstant.OLD_PAGE_DOMAIN+"/OrderPageNew/SearchOrderNew.aspx?mobile=%s&source=offline"; }
        @Override
        public String getNoticeProcessUrlTml() { return null; }
        @Override
        public String getNoticeType() { return "NoticeType"; }
        @Override
        public String getLeaderNoticeType() { return "NoticeType"; }
        @Override
        public String getComplainType() { return "complain_type_new"; }
        @Override
        public String getResponseType() { return "responsy_type"; }
        @Override
        public String getNoticeSource() { return "notice_source"; }
        @Override
        public String getComplainSource() { return "complain_source"; }
    },
    SpecialCarTrain {
        @Override
        public int getProductLineCode() { return 137; }
        @Override
        public String getProductLineName() { return "国内专车（火车）"; }
        @Override
        public String getOrderDetailUrlTml() { return BizConstant.OLD_PAGE_DOMAIN+"IntelligenceCarPage/ShowCarOrderDetail.aspx?OrderID=%s"; }
        @Override
        public String getSearchOrderListUrlTml() { return BizConstant.OLD_PAGE_DOMAIN+"IntelligenceCarPage/SearchCarOrder.aspx"; }
        @Override
        public String getNoticeProcessUrlTml() { return null; }
        @Override
        public String getNoticeType() { return "NoticeType"; }
        @Override
        public String getLeaderNoticeType() { return "NoticeType"; }
        @Override
        public String getComplainType() { return "complain_type_new"; }
        @Override
        public String getResponseType() { return "responsy_type"; }
        @Override
        public String getNoticeSource() { return "notice_source"; }
        @Override
        public String getComplainSource() { return "complain_source"; }
    },
    SpecialCarFlight {
        @Override
        public int getProductLineCode() { return 138; }
        @Override
        public String getProductLineName() { return "国内专车（飞机）"; }
        @Override
        public String getOrderDetailUrlTml() { return BizConstant.OLD_PAGE_DOMAIN+"IntelligenceCarPage/ShowCarOrderDetail.aspx?OrderID=%s"; }
        @Override
        public String getSearchOrderListUrlTml() { return BizConstant.OLD_PAGE_DOMAIN+"IntelligenceCarPage/SearchCarOrder.aspx"; }
        @Override
        public String getNoticeProcessUrlTml() { return null; }
        @Override
        public String getNoticeType() { return "TYZXFlightNoticeType"; }
        @Override
        public String getLeaderNoticeType() { return "TYZXFlightNoticeType"; }
        @Override
        public String getComplainType() { return "TYZXFlightComplainType"; }
        @Override
        public String getResponseType() { return "zhixingFlight_Responsy_type"; }
        @Override
        public String getNoticeSource() { return "notice_source"; }
        @Override
        public String getComplainSource() { return "complain_source"; }
    },
    IntTrain {
        @Override
        public int getProductLineCode() { return 3; }
        @Override
        public String getProductLineName() { return "欧铁"; }
        @Override
        public String getOrderDetailUrlTml() { return BizConstant.OLD_PAGE_DOMAIN+"/OrderPageNew/ShowOuTieOrderDetail.aspx?OrderID=%s"; }
        @Override
        public String getSearchOrderListUrlTml() { return BizConstant.OLD_PAGE_DOMAIN+"/OrderPageNew/SearchOrderNew.aspx?mobile=%s&source=offline"; }
        @Override
        public String getNoticeProcessUrlTml() { return null; }
        @Override
        public String getNoticeType() { return "OutieNoticeType"; }
        @Override
        public String getLeaderNoticeType() { return "OutieNoticeType"; }
        @Override
        public String getComplainType() { return "outiecomplain_type_new"; }
        @Override
        public String getResponseType() { return "outieresponsy_type"; }
        @Override
        public String getNoticeSource() { return "notice_source"; }
        @Override
        public String getComplainSource() { return "complain_source"; }
    },
    Flight{
        @Override
        public int getProductLineCode() { return 135; }
        @Override
        public String getProductLineName() { return "国内机票"; }
        @Override
        public String getOrderDetailUrlTml() { return BizConstant.OLD_PAGE_DOMAIN+"/TieyouFlight/TieyouFlightOrderDetailNew.aspx?orderid=%s"; }
        @Override
        public String getSearchOrderListUrlTml() { return BizConstant.OLD_PAGE_DOMAIN+"/TieyouFlight/SearchTieyouFlightOrder.aspx?ani=%s"; }
        @Override
        public String getNoticeProcessUrlTml() { return null; }
        @Override
        public String getNoticeType() { return "TYZXFlightNoticeType"; }
        @Override
        public String getLeaderNoticeType() { return "TYZXFlightNoticeType"; }
        @Override
        public String getComplainType() { return "TYZXFlightComplainType"; }
        @Override
        public String getResponseType() { return "zhixingFlight_Responsy_type"; }
        @Override
        public String getNoticeSource() { return "notice_source"; }
        @Override
        public String getComplainSource() { return "complain_source"; }
    },
    IntFlight {
        @Override
        public int getProductLineCode() { return 31; }
        @Override
        public String getProductLineName() { return "国际机票"; }
        @Override
        public String getOrderDetailUrlTml() { return BizConstant.OLD_PAGE_DOMAIN+"/TieyouFlight/TieyouFlightOrderDetailNew.aspx?orderid=%s"; }
        @Override
        public String getSearchOrderListUrlTml() { return BizConstant.OLD_PAGE_DOMAIN+"/TieyouFlight/SearchTieyouFlightOrder.aspx?ani=%s"; }
        @Override
        public String getNoticeProcessUrlTml() { return null; }
        @Override
        public String getNoticeType() { return "TYZXFlightNoticeType"; }
        @Override
        public String getLeaderNoticeType() { return "TYZXFlightNoticeType"; }
        @Override
        public String getComplainType() { return "TYZXFlightComplainType"; }
        @Override
        public String getResponseType() { return "zhixingFlight_Responsy_type"; }
        @Override
        public String getNoticeSource() { return "notice_source"; }
        @Override
        public String getComplainSource() { return "complain_source"; }
    } ;


    public static ProductLineEnum convertByCode(int productLineCode){
        ProductLineEnum rs= Arrays.stream(ProductLineEnum.values()).filter(it->it.getProductLineCode()==productLineCode).findFirst().orElse(null);
        return rs;
    }
    public static ProductLineEnum convertByCode(String productLineCode){
        return convertByCode(Integer.parseInt(productLineCode));
    }
}
