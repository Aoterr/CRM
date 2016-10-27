package com.zendaimoney.crm.modification.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zendaimoney.crm.attachment.entity.BusiRequestAttach;
import org.springside.modules.orm.IdEntity;

@Entity
@Table(name = "CRM_MODIFICATION")
public class Modification extends IdEntity {
	private static final long serialVersionUID = -8619987096445255417L;
	/**
	 * 对象ID,数据库的主键ID,不同的变更单类型对应不同的表
	 */
	private Long mnSourceId;
	/**
	 * 变更单类型,3.客户信息变更、2.理财申请变更、1.信贷申请变更
	 */
	private String mnType;
	/**
	 * 变更时间
	 */
	private Date mnDate;
	/**
	 * 操作类型,修改、新增、
	 */
	private String mnOperateType;
	/**
	 * 操作人
	 */
	private Long mnInputId;
	/**
	 * 状态
	 */
	private String mnState;
	/**
	 * 备注
	 */
	private String mnMemo;
	/**
	 * 变更项
	 */
	private Set<ModificationDetail> modificationDetails = new HashSet<ModificationDetail>();
	private Set<BusiRequestAttach> busiRequestAttachs = new HashSet<BusiRequestAttach>();

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "modification")
	public Set<BusiRequestAttach> getBusiRequestAttachs() {
		return this.busiRequestAttachs;
	}

	public void setBusiRequestAttachs(Set<BusiRequestAttach> busiRequestAttachs) {
		this.busiRequestAttachs = busiRequestAttachs;
	}

	public Long getMnSourceId() {
		return mnSourceId;
	}

	public void setMnSourceId(Long mnSourceId) {
		this.mnSourceId = mnSourceId;
	}

	public String getMnType() {
		return mnType;
	}

	public void setMnType(String mnType) {
		this.mnType = mnType;
	}

	public Date getMnDate() {
		return mnDate;
	}

	public void setMnDate(Date mnDate) {
		this.mnDate = mnDate;
	}

	public String getMnOperateType() {
		return mnOperateType;
	}

	public void setMnOperateType(String mnOperateType) {
		this.mnOperateType = mnOperateType;
	}

	public Long getMnInputId() {
		return mnInputId;
	}

	public void setMnInputId(Long mnInputId) {
		this.mnInputId = mnInputId;
	}

	public String getMnState() {
		return mnState;
	}

	public void setMnState(String mnState) {
		this.mnState = mnState;
	}

	public String getMnMemo() {
		return mnMemo;
	}

	public void setMnMemo(String mnMemo) {
		this.mnMemo = mnMemo;
	}

	@JsonIgnore
	@OrderBy("id ASC")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "modification")
	public Set<ModificationDetail> getModificationDetails() {
		return modificationDetails;
	}

	public void setModificationDetails(
			Set<ModificationDetail> modificationDetails) {
		this.modificationDetails = modificationDetails;
	}

	@Override
	public String toString() {
		return "Modification [mnSourceId=" + mnSourceId + ", mnType=" + mnType
				+ ", mnDate=" + mnDate + ", mnOperateType=" + mnOperateType
				+ ", mnInputId=" + mnInputId + ", mnState=" + mnState
				+ ", mnMemo=" + mnMemo + ", modificationDetails="
				+ modificationDetails + ", busiRequestAttachs="
				+ busiRequestAttachs + ", id=" + id + "]";
	}

}