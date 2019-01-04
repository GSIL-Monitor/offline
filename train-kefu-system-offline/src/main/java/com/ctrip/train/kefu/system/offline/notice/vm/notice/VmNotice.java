package com.ctrip.train.kefu.system.offline.notice.vm.notice;


import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class VmNotice {
    /**
     * 主键
     */

    private Long iD;

    /**
     * 录入日期
     */

    private Timestamp enterDate;

    /**
     * 录入人
     */

    private String enterUser;

    /**
     * 订单号
     */

    private String orderID;

    /**
     * 发送时间
     */

    private Timestamp sendTime;

    /**
     * 事件类别（二线通知：1；投诉：2；一线通知：3；领班通知：4） 重构后：通知1 投诉2 领班4
     */

    private Integer envenType;

    /**
     * 联系人
     */

    private String contactUser;

    /**
     * 联系电话
     */

    private String contactPhone;

    /**
     * 紧急情况(一般：0；紧急：1；)
     */

    private Integer emergeState;

    /**
     * 通知类型 重构后：也表示投诉类型
     */

    private Integer noticeType;

    /**
     * 投诉来源 重构后：来源
     */

    private Integer complainSource;

    /**
     * 投诉类别1 重构后：废除
     */

    private Integer complainFirstType;

    /**
     * 投诉类别2 重构后：废除
     */

    private Integer complainSecondType;

    /**
     * 概要
     */

    private String contents;

    /**
     * 通知状态,待处理-80； 处理中-81； 暂缓-82； 已解决-83； 无需处理-84； 已转二线-93;已转投诉-94 重构后不区分通知、投诉：80待处理 81处理中 82暂缓 83已解决 84无需处理 94已转投诉 100已转领班
     */

    private Integer noticeState;

    /**
     * 投诉状态(待处理95； 处理中96； 暂缓97； 已解决98； 无需处理99 ） 重构后：废除
     */

    private Integer complainState;

    /**
     * 处理人
     */

    private String opUser;

    /**
     * 处理时间
     */

    private Timestamp opTime;

    /**
     * 投诉处理时间 重构后：废除
     */

    private Timestamp complainOpTime;

    /**
     * 处理完成时间
     */

    private Timestamp completeTime;

    /**
     * 催次数
     */

    private Integer opCount;

    /**
     * 处理结果
     */

    private String result;

    /**
     * 备注
     */

    private String remark;

    /**
     * 数据来源（携程，铁友，其他） 重构后：表示渠道：携程、铁友、智行、去哪儿、其他
     */

    private Integer dataSource;

    /**
     * 删除：1；未删除：0；
     */

    private Integer isDelete;

    /**
     * 暂缓次数
     */

    private Integer deferCount;

    /**
     * 通知二类 重构后：也表示投诉二级类型
     */

    private Integer noticeSecondType;

    /**
     * 产品线:1:火车,2:汽车,3:欧铁,4:机场巴士  重构后：1火车、3欧铁、10国内机票、31去哪儿、32国际机票
     */

    private String productLine;

    /**
     * 重要通知转投诉时间 重构后，废除 老通知的完成时间即新投诉的创建时间，没必要有转投诉时间
     */

    private Timestamp toComplainTime;

    /**
     * 一线通知转二线时间 重构后：废除
     */

    private Timestamp toSecondNoticeTime;

    /**
     * 转投诉操作人 重构后：废除，没用
     */

    private String toComplainOpUser;

    /**
     * 合作方唯一code 重构后：废除
     */

    private String partnerUniqueCode;

    /**
     * 自定义扩展属性 重构后：废除
     */

    private String extendProp;

    /**
     * 预约回电时间
     */

    private Timestamp appointedProcessTime;

    /**
     * 更新时间
     */

    private Timestamp datachangeLastTime;

    /**
     * 订单类型
     */

    private Integer orderType;

    /**
     * 交班时间
     */

    private Timestamp changeDutyTime;

    /**
     * 操作人姓名
     */

    private String opUserName;

    /**
     * 操作人编号
     */

    private String opUserNum;

    /**
     * 第一次处理时间，值不变
     */

    private Timestamp firstDealTime;

    /**
     * 第一次外呼时间
     */

    private Timestamp firstCallTime;

    /**
     * 开始处理时间，重新分配值会变化
     */

    private Timestamp startDealTIme;

    /**
     * 最后催时间
     */
    private Timestamp lastTimeUrge;
}
