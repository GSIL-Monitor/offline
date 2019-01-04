package com.ctrip.train.kefu.system.offline.order.vm.flight.change;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VmFlightChangeDetail {
    /**
     * 1 = 单程 2 = 往返 4= 多程
     */
    private int tripType;
    /**
     * 航程
     */
    private String fromPortName;

    private String toPortName;

    private String arriveCityCode;

    private String departCityCode;

    /**
     * 航班
     */
    private String flightNo;

    /**
     * 起飞时间
     */
    private String takeoffTime;

    /**
     * 舱等
        0=Y=经济舱
        1=S=超级经济舱
        2=C=公务舱
        3=F=头等舱
        9=CF=公务舱+头等舱
     */
    private int classGrade;

    /**
     * 退改政策
     */
    private String rescheduleRefundRemark;

    private String airlineCode;

    private String cabinCode;

    private String departDate;

    private String routeIndex;

    private Boolean rebookAble;
    private Integer segmentNo;
    private Integer sequence;
    private Integer rebookMinDaysBefore;
    private Integer rebookMinDaysAfter;
    private String rebookDateAfter;
    private String rebookDateBefore;
    private String freeDateAfter;
    private String freeDateBefore;

    /**
     * 多程或往返拆单时使用此字段，表示行程编号
     1=第一程（去程）
     2=第二程（返程）
     3=多程的第三程
     */
    private Integer splitOrderSequence;

    /**
     * 航段名称
     */
    private String segmentName;
}
