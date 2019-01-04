package dao.ctrip.ctrainchat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.ctrip.platform.dal.dao.annotation.Database;
import com.ctrip.platform.dal.dao.annotation.Sensitive;
import com.ctrip.platform.dal.dao.annotation.Type;
import java.sql.Types;
import java.sql.Timestamp;

import com.ctrip.platform.dal.dao.DalPojo;

/**
 * @author jj季健(IT)
 * @date 2018-09-04
 */
@Entity
@Database(name = "CtrainChat")
@Table(name = "chat_config")
public class ChatConfig implements DalPojo {

    /**
     * 自增长列
     */
    @Id
	@Column(name = "Tid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value = Types.BIGINT)
	private Long tid;

    /**
     * 产品线,1:火车,2:汽车
     */
	@Column(name = "Product_Line")
	@Type(value = Types.VARCHAR)
	private String productLine;

    /**
     * 下单渠道,0:铁友,1:携程
     */
	@Column(name = "Order_Channel")
	@Type(value = Types.VARCHAR)
	private String orderChannel;

    /**
     * 终端,0:PC,1:APP
     */
	@Column(name = "Terminal")
	@Type(value = Types.VARCHAR)
	private String terminal;

    /**
     * 配置类型,0:读取订单中心开关,1:登录地址,2:订单详情,3:小结开启标记(1:开启，0:关闭),4:插入自动数据开关,9:咨询监控邮件发送者,10:咨询监控邮件接收者
     */
	@Column(name = "Config_Type")
	@Type(value = Types.VARCHAR)
	private String configType;

    /**
     * 内容
     */
	@Column(name = "Config_Value")
	@Type(value = Types.VARCHAR)
	private String configValue;

	@Column(name = "DataChange_LastTime", insertable = false, updatable = false)
	@Type(value = Types.TIMESTAMP)
	private Timestamp datachangeLasttime;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	public String getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getConfigType() {
		return configType;
	}

	public void setConfigType(String configType) {
		this.configType = configType;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public Timestamp getDatachangeLasttime() {
		return datachangeLasttime;
	}

	public void setDatachangeLasttime(Timestamp datachangeLasttime) {
		this.datachangeLasttime = datachangeLasttime;
	}

}