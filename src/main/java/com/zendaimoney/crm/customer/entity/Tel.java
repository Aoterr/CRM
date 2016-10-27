package com.zendaimoney.crm.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springside.modules.orm.IdEntity;

/**
 * 
 * 电话信息
 * 
 */
@Entity
@Table(name = "CRM_TEL")
public class Tel extends IdEntity {
	private static final long serialVersionUID = 2433461464054606867L;
	/**
	 * 客户ID
	 */
	private Long customerid;
	/**
	 * 客户类型0:客户,1:联系人
	 */
	private String tlCustType;
	/**
	 * 电话类型
	 */
	private String tlTelType;
	/**
	 * 区号
	 */
	private String tlAreaCode;
	/**
	 * 电话号码
	 */
	private String tlTelNum;
	/**
	 * 分机
	 */
	private String tlExtCode;
	/**
	 * 优先级
	 */
	private String tlPriority;
	/**
	 * 是否有效
	 */
	private String tlValid;
	/**
	 * 信息录入人
	 */
	private Long tlInputId;
	/**
	 * 信息录入时间
	 */
	private Date tlInputDate;
	/**
	 * 修改人
	 */
	private Long tlModifyId;
	/**
	 * 修改时间
	 */
	private Date tlModifyTime;
	/**
	 * 备注
	 */
	private String tlMemo;

	@Column(name = "TL_CUST_TYPE", length = 2)
	public String getTlCustType() {
		return this.tlCustType;
	}

	public Long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}

	public void setTlCustType(String tlCustType) {
		this.tlCustType = tlCustType;
	}

	@Column(name = "TL_TEL_TYPE", length = 2)
	public String getTlTelType() {
		return this.tlTelType;
	}

	public void setTlTelType(String tlTelType) {
		this.tlTelType = tlTelType;
	}

	@Column(name = "TL_AREA_CODE", length = 4)
	public String getTlAreaCode() {
		return this.tlAreaCode;
	}

	public void setTlAreaCode(String tlAreaCode) {
		this.tlAreaCode = tlAreaCode;
	}

	@Column(name = "TL_TEL_NUM", length = 20)
	public String getTlTelNum() {
		return this.tlTelNum;
	}

	public void setTlTelNum(String tlTelNum) {
		this.tlTelNum = tlTelNum;
	}

	@Column(name = "TL_EXT_CODE", length = 6)
	public String getTlExtCode() {
		return this.tlExtCode;
	}

	public void setTlExtCode(String tlExtCode) {
		this.tlExtCode = tlExtCode;
	}

	@Column(name = "TL_PRIORITY", length = 2)
	public String getTlPriority() {
		return this.tlPriority;
	}

	public void setTlPriority(String tlPriority) {
		this.tlPriority = tlPriority;
	}

	@Column(name = "TL_VALID", length = 1)
	public String getTlValid() {
		return this.tlValid;
	}

	public void setTlValid(String tlValid) {
		this.tlValid = tlValid;
	}

	@Column(name = "TL_INPUT_ID", scale = 0)
	public Long getTlInputId() {
		return this.tlInputId;
	}

	public void setTlInputId(Long tlInputId) {
		this.tlInputId = tlInputId;
	}

	@Column(name = "TL_INPUT_DATE")
	public Date getTlInputDate() {
		return this.tlInputDate;
	}

	public void setTlInputDate(Date tlInputDate) {
		this.tlInputDate = tlInputDate;
	}

	@Column(name = "TL_MODIFY_ID", scale = 0)
	public Long getTlModifyId() {
		return this.tlModifyId;
	}

	public void setTlModifyId(Long tlModifyId) {
		this.tlModifyId = tlModifyId;
	}

	@Column(name = "TL_MODIFY_TIME")
	public Date getTlModifyTime() {
		return this.tlModifyTime;
	}

	public void setTlModifyTime(Date tlModifyTime) {
		this.tlModifyTime = tlModifyTime;
	}

	@Column(name = "TL_MEMO", length = 200)
	public String getTlMemo() {
		return this.tlMemo;
	}

	public void setTlMemo(String tlMemo) {
		this.tlMemo = tlMemo;
	}

	@Override
	public String toString() {
		return "Tel [customerid=" + customerid + ", tlCustType=" + tlCustType
				+ ", tlTelType=" + tlTelType + ", tlAreaCode=" + tlAreaCode
				+ ", tlTelNum=" + tlTelNum + ", tlExtCode=" + tlExtCode
				+ ", tlPriority=" + tlPriority + ", tlValid=" + tlValid
				+ ", tlInputId=" + tlInputId + ", tlInputDate=" + tlInputDate
				+ ", tlModifyId=" + tlModifyId + ", tlModifyTime="
				+ tlModifyTime + ", tlMemo=" + tlMemo + ", id=" + id + "]";
	}

}