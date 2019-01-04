package dao.ctrip.ctrainchat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ctrip.platform.dal.dao.annotation.Database;
import com.ctrip.platform.dal.dao.annotation.Type;

import java.sql.Types;
import java.sql.Timestamp;

import com.ctrip.platform.dal.dao.DalPojo;

/**
 * @author lsy刘书亚
 * @date 2018-11-20
 */
@Entity
@Database(name = "CtrainChat")
@Table(name = "ivr_config")
public class IvrConfig implements DalPojo {

    /**
     * 主键
     */
    @Id
    @Column(name = "Tid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(value = Types.INTEGER)
    private Integer tid;

    /**
     * 0:无异常 1：语音播报 2：TTS播报
     */
    @Column(name = "SMS_Type")
    @Type(value = Types.INTEGER)
    private Integer smsType;

    /**
     * 类型为0时为空，为1时播报Id，为2时TTS播报内容
     */
    @Column(name = "SMS_Content")
    @Type(value = Types.VARCHAR)
    private String smsContent;

    /**
     * 操作人
     */
    @Column(name = "Operator")
    @Type(value = Types.VARCHAR)
    private String operator;

    /**
     * 渠道
     */
    @Column(name = "PartnerName")
    @Type(value = Types.VARCHAR)
    private String partnerName;

    /**
     * 最后更新时间
     */
    @Column(name = "DataChange_LastTime", insertable = false, updatable = false)
    @Type(value = Types.TIMESTAMP)
    private Timestamp datachangeLasttime;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getSmsType() {
        return smsType;
    }

    public void setSmsType(Integer smsType) {
        this.smsType = smsType;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public Timestamp getDatachangeLasttime() {
        return datachangeLasttime;
    }

    public void setDatachangeLasttime(Timestamp datachangeLasttime) {
        this.datachangeLasttime = datachangeLasttime;
    }

}
