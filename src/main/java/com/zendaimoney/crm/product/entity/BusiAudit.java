package com.zendaimoney.crm.product.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springside.modules.orm.IdEntity;

@Entity
@Table(name = "BUSI_AUDIT")
public class BusiAudit extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = -8161414823749661452L;
	private Long atBusi;// 业务id
	private String atType;// 业务类型
	private String atContent;// 审核内容
	private Long atInputId;// 操作人
	private Date atInputTime;// 操作时间
	private String atState;// 状态
	private String atPrivState;// 操作前状态
	private String atNextState;// 操作后状态

	// 为优益计划增加支付状态 记录 add by Sam.J 15.5.13
	private String atPrivPaystate;// 操作前支付状态
	private String atNextPaystate;// 操作后支付状态

	public BusiAudit() {
	}

	public BusiAudit(Long atBusi, String atType, String atContent,
			Long atInputId, Date atInputTime, String atState,
			String atPrivState, String atNextState) {
		this.atBusi = atBusi;
		this.atType = atType;
		this.atContent = atContent;
		this.atInputId = atInputId;
		this.atInputTime = atInputTime;
		this.atState = atState;
		this.atPrivState = atPrivState;
		this.atNextState = atNextState;
	}

	public Long getAtBusi() {
		return atBusi;
	}

	public void setAtBusi(Long atBusi) {
		this.atBusi = atBusi;
	}

	public String getAtType() {
		return atType;
	}

	public void setAtType(String atType) {
		this.atType = atType;
	}

	public String getAtContent() {
		return atContent;
	}

	public void setAtContent(String atContent) {
		this.atContent = atContent;
	}

	public Long getAtInputId() {
		return atInputId;
	}

	public void setAtInputId(Long atInputId) {
		this.atInputId = atInputId;
	}

	public Date getAtInputTime() {
		return atInputTime;
	}

	public void setAtInputTime(Date atInputTime) {
		this.atInputTime = atInputTime;
	}

	public String getAtState() {
		return atState;
	}

	public void setAtState(String atState) {
		this.atState = atState;
	}

	public String getAtPrivState() {
		return atPrivState;
	}

	public void setAtPrivState(String atPrivState) {
		this.atPrivState = atPrivState;
	}

	public String getAtNextState() {
		return atNextState;
	}

	public void setAtNextState(String atNextState) {
		this.atNextState = atNextState;
	}

	public String getAtPrivPaystate() {
		return atPrivPaystate;
	}

	public void setAtPrivPaystate(String atPrivPaystate) {
		this.atPrivPaystate = atPrivPaystate;
	}

	public String getAtNextPaystate() {
		return atNextPaystate;
	}

	public void setAtNextPaystate(String atNextPaystate) {
		this.atNextPaystate = atNextPaystate;
	}

}