package dao.ctrip.ctrainpps.entity;

import com.ctrip.platform.dal.dao.DalPojo;
import com.ctrip.platform.dal.dao.annotation.Database;
import com.ctrip.platform.dal.dao.annotation.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.sql.Types;

/**
 * @author wql王全礼
 * @date 2018-08-16
 */
@Entity
@Database(name = "ctrainppsdb")
@Table(name = "notice_complain_info")
public class NoticeComplainInfo implements DalPojo {

    /**
     * 主键
     */
    @Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value = Types.BIGINT)
	private Long iD;

    /**
     * 录入日期
     */
	@Column(name = "EnterDate")
	@Type(value = Types.TIMESTAMP)
	private Timestamp enterDate;

    /**
     * 录入人
     */
	@Column(name = "EnterUser")
	@Type(value = Types.VARCHAR)
	private String enterUser;

    /**
     * 订单号
     */
	@Column(name = "OrderID")
	@Type(value = Types.VARCHAR)
	private String orderID;

    /**
     * 发送时间
     */
	@Column(name = "SendTime")
	@Type(value = Types.TIMESTAMP)
	private Timestamp sendTime;

    /**
     * 事件类别（二线通知：1；投诉：2；一线通知：3；领班通知：4） 重构后：通知1 投诉2 领班4
     */
	@Column(name = "EnvenType")
	@Type(value = Types.INTEGER)
	private Integer envenType;

    /**
     * 联系人
     */
	@Column(name = "ContactUser")
	@Type(value = Types.VARCHAR)
	private String contactUser;

    /**
     * 联系电话
     */
	@Column(name = "ContactPhone")
	@Type(value = Types.VARCHAR)
	private String contactPhone;

    /**
     * 紧急情况(一般：0；紧急：1；)
     */
	@Column(name = "EmergeState")
	@Type(value = Types.INTEGER)
	private Integer emergeState;

    /**
     * 通知类型 重构后：也表示投诉类型
     */
	@Column(name = "NoticeType")
	@Type(value = Types.INTEGER)
	private Integer noticeType;

    /**
     * 投诉来源 重构后：来源
     */
	@Column(name = "ComplainSource")
	@Type(value = Types.INTEGER)
	private Integer complainSource;

    /**
     * 投诉类别1 重构后：废除
     */
	@Column(name = "ComplainFirstType")
	@Type(value = Types.INTEGER)
	private Integer complainFirstType;

    /**
     * 投诉类别2 重构后：废除
     */
	@Column(name = "ComplainSecondType")
	@Type(value = Types.INTEGER)
	private Integer complainSecondType;

    /**
     * 概要
     */
	@Column(name = "Contents")
	@Type(value = Types.LONGVARCHAR)
	private String contents;

    /**
     * 通知状态,待处理-80； 处理中-81； 暂缓-82； 已解决-83； 无需处理-84； 已转二线-93;已转投诉-94 重构后不区分通知、投诉：80待处理 81处理中 82暂缓 83已解决 84无需处理 94已转投诉 100已转领班
     */
	@Column(name = "NoticeState")
	@Type(value = Types.INTEGER)
	private Integer noticeState;

    /**
     * 投诉状态(待处理95； 处理中96； 暂缓97； 已解决98； 无需处理99 ） 重构后：废除
     */
	@Column(name = "ComplainState")
	@Type(value = Types.INTEGER)
	private Integer complainState;

    /**
     * 处理人
     */
	@Column(name = "OpUser")
	@Type(value = Types.VARCHAR)
	private String opUser;

    /**
     * 处理时间
     */
	@Column(name = "OpTime")
	@Type(value = Types.TIMESTAMP)
	private Timestamp opTime;

    /**
     * 投诉处理时间 重构后：废除
     */
	@Column(name = "ComplainOpTime")
	@Type(value = Types.TIMESTAMP)
	private Timestamp complainOpTime;

    /**
     * 处理完成时间
     */
	@Column(name = "CompleteTime")
	@Type(value = Types.TIMESTAMP)
	private Timestamp completeTime;

    /**
     * 催次数
     */
	@Column(name = "OpCount")
	@Type(value = Types.INTEGER)
	private Integer opCount;

    /**
     * 处理结果
     */
	@Column(name = "Result")
	@Type(value = Types.VARCHAR)
	private String result;

    /**
     * 备注
     */
	@Column(name = "Remark")
	@Type(value = Types.VARCHAR)
	private String remark;

    /**
     * 数据来源（携程，铁友，其他） 重构后：表示渠道：携程、铁友、智行、去哪儿、其他
     */
	@Column(name = "DataSource")
	@Type(value = Types.INTEGER)
	private Integer dataSource;

    /**
     * 删除：1；未删除：0；
     */
	@Column(name = "IsDelete")
	@Type(value = Types.INTEGER)
	private Integer isDelete;

    /**
     * 暂缓次数
     */
	@Column(name = "DeferCount")
	@Type(value = Types.INTEGER)
	private Integer deferCount;

    /**
     * 通知二类 重构后：也表示投诉二级类型
     */
	@Column(name = "NoticeSecondType")
	@Type(value = Types.INTEGER)
	private Integer noticeSecondType;

    /**
     * 产品线:1:火车,2:汽车,3:欧铁,4:机场巴士  重构后：1火车、3欧铁、10国内机票、31去哪儿、32国际机票
     */
	@Column(name = "Product_Line")
	@Type(value = Types.VARCHAR)
	private String productLine;

    /**
     * 重要通知转投诉时间 重构后，废除 老通知的完成时间即新投诉的创建时间，没必要有转投诉时间
     */
	@Column(name = "ToComplainTime")
	@Type(value = Types.TIMESTAMP)
	private Timestamp toComplainTime;

    /**
     * 一线通知转二线时间 重构后：废除
     */
	@Column(name = "ToSecondNoticeTime")
	@Type(value = Types.TIMESTAMP)
	private Timestamp toSecondNoticeTime;

    /**
     * 转投诉操作人 重构后：废除，没用
     */
	@Column(name = "ToComplainOpUser")
	@Type(value = Types.VARCHAR)
	private String toComplainOpUser;

    /**
     * 合作方唯一code 重构后：废除
     */
	@Column(name = "PartnerUniqueCode")
	@Type(value = Types.VARCHAR)
	private String partnerUniqueCode;

    /**
     * 自定义扩展属性 重构后：废除
     */
	@Column(name = "ExtendProp")
	@Type(value = Types.VARCHAR)
	private String extendProp;

    /**
     * 预约回电时间
     */
	@Column(name = "AppointedProcessTime")
	@Type(value = Types.TIMESTAMP)
	private Timestamp appointedProcessTime;

    /**
     * 更新时间
     */
	@Column(name = "DatachangeLastTime")
	@Type(value = Types.TIMESTAMP)
	private Timestamp datachangeLastTime;

    /**
     * 订单类型
     */
	@Column(name = "orderType")
	@Type(value = Types.INTEGER)
	private Integer orderType;

    /**
     * 交班时间
     */
	@Column(name = "ChangeDutyTime")
	@Type(value = Types.TIMESTAMP)
	private Timestamp changeDutyTime;

    /**
     * 操作人姓名
     */
	@Column(name = "OpUserName")
	@Type(value = Types.VARCHAR)
	private String opUserName;

    /**
     * 操作人编号
     */
	@Column(name = "OpUserNum")
	@Type(value = Types.VARCHAR)
	private String opUserNum;

    /**
     * 第一次处理时间，值不变
     */
	@Column(name = "FirstDealTime")
	@Type(value = Types.TIMESTAMP)
	private Timestamp firstDealTime;

    /**
     * 第一次外呼时间
     */
	@Column(name = "FirstCallTime")
	@Type(value = Types.TIMESTAMP)
	private Timestamp firstCallTime;

    /**
     * 开始处理时间，重新分配值会变化
     */
	@Column(name = "StartDealTIme")
	@Type(value = Types.TIMESTAMP)
	private Timestamp startDealTIme;

    /**
     * 最后催时间
     */
	@Column(name = "LastTimeUrge")
	@Type(value = Types.TIMESTAMP)
	private Timestamp lastTimeUrge;

	public Long getID() {
		return iD;
	}

	public void setID(Long iD) {
		this.iD = iD;
	}

	public Timestamp getEnterDate() {
		return enterDate;
	}

	public void setEnterDate(Timestamp enterDate) {
		this.enterDate = enterDate;
	}

	public String getEnterUser() {
		return enterUser;
	}

	public void setEnterUser(String enterUser) {
		this.enterUser = enterUser;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getEnvenType() {
		return envenType;
	}

	public void setEnvenType(Integer envenType) {
		this.envenType = envenType;
	}

	public String getContactUser() {
		return contactUser;
	}

	public void setContactUser(String contactUser) {
		this.contactUser = contactUser;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Integer getEmergeState() {
		return emergeState;
	}

	public void setEmergeState(Integer emergeState) {
		this.emergeState = emergeState;
	}

	public Integer getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}

	public Integer getComplainSource() {
		return complainSource;
	}

	public void setComplainSource(Integer complainSource) {
		this.complainSource = complainSource;
	}

	public Integer getComplainFirstType() {
		return complainFirstType;
	}

	public void setComplainFirstType(Integer complainFirstType) {
		this.complainFirstType = complainFirstType;
	}

	public Integer getComplainSecondType() {
		return complainSecondType;
	}

	public void setComplainSecondType(Integer complainSecondType) {
		this.complainSecondType = complainSecondType;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Integer getNoticeState() {
		return noticeState;
	}

	public void setNoticeState(Integer noticeState) {
		this.noticeState = noticeState;
	}

	public Integer getComplainState() {
		return complainState;
	}

	public void setComplainState(Integer complainState) {
		this.complainState = complainState;
	}

	public String getOpUser() {
		return opUser;
	}

	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}

	public Timestamp getOpTime() {
		return opTime;
	}

	public void setOpTime(Timestamp opTime) {
		this.opTime = opTime;
	}

	public Timestamp getComplainOpTime() {
		return complainOpTime;
	}

	public void setComplainOpTime(Timestamp complainOpTime) {
		this.complainOpTime = complainOpTime;
	}

	public Timestamp getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Timestamp completeTime) {
		this.completeTime = completeTime;
	}

	public Integer getOpCount() {
		return opCount;
	}

	public void setOpCount(Integer opCount) {
		this.opCount = opCount;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getDataSource() {
		return dataSource;
	}

	public void setDataSource(Integer dataSource) {
		this.dataSource = dataSource;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getDeferCount() {
		return deferCount;
	}

	public void setDeferCount(Integer deferCount) {
		this.deferCount = deferCount;
	}

	public Integer getNoticeSecondType() {
		return noticeSecondType;
	}

	public void setNoticeSecondType(Integer noticeSecondType) {
		this.noticeSecondType = noticeSecondType;
	}

	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	public Timestamp getToComplainTime() {
		return toComplainTime;
	}

	public void setToComplainTime(Timestamp toComplainTime) {
		this.toComplainTime = toComplainTime;
	}

	public Timestamp getToSecondNoticeTime() {
		return toSecondNoticeTime;
	}

	public void setToSecondNoticeTime(Timestamp toSecondNoticeTime) {
		this.toSecondNoticeTime = toSecondNoticeTime;
	}

	public String getToComplainOpUser() {
		return toComplainOpUser;
	}

	public void setToComplainOpUser(String toComplainOpUser) {
		this.toComplainOpUser = toComplainOpUser;
	}

	public String getPartnerUniqueCode() {
		return partnerUniqueCode;
	}

	public void setPartnerUniqueCode(String partnerUniqueCode) {
		this.partnerUniqueCode = partnerUniqueCode;
	}

	public String getExtendProp() {
		return extendProp;
	}

	public void setExtendProp(String extendProp) {
		this.extendProp = extendProp;
	}

	public Timestamp getAppointedProcessTime() {
		return appointedProcessTime;
	}

	public void setAppointedProcessTime(Timestamp appointedProcessTime) {
		this.appointedProcessTime = appointedProcessTime;
	}

	public Timestamp getDatachangeLastTime() {
		return datachangeLastTime;
	}

	public void setDatachangeLastTime(Timestamp datachangeLastTime) {
		this.datachangeLastTime = datachangeLastTime;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Timestamp getChangeDutyTime() {
		return changeDutyTime;
	}

	public void setChangeDutyTime(Timestamp changeDutyTime) {
		this.changeDutyTime = changeDutyTime;
	}

	public String getOpUserName() {
		return opUserName;
	}

	public void setOpUserName(String opUserName) {
		this.opUserName = opUserName;
	}

	public String getOpUserNum() {
		return opUserNum;
	}

	public void setOpUserNum(String opUserNum) {
		this.opUserNum = opUserNum;
	}

	public Timestamp getFirstDealTime() {
		return firstDealTime;
	}

	public void setFirstDealTime(Timestamp firstDealTime) {
		this.firstDealTime = firstDealTime;
	}

	public Timestamp getFirstCallTime() {
		return firstCallTime;
	}

	public void setFirstCallTime(Timestamp firstCallTime) {
		this.firstCallTime = firstCallTime;
	}

	public Timestamp getStartDealTIme() {
		return startDealTIme;
	}

	public void setStartDealTIme(Timestamp startDealTIme) {
		this.startDealTIme = startDealTIme;
	}

	public Timestamp getLastTimeUrge() {
		return lastTimeUrge;
	}

	public void setLastTimeUrge(Timestamp lastTimeUrge) {
		this.lastTimeUrge = lastTimeUrge;
	}

}