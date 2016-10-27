package com.zendaimoney.crm.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springside.modules.orm.IdEntity;
/**
 * 
 * 地址信息
 * 
 */
@Entity
@Table(name = "CRM_ADDR")
public class Addr extends IdEntity {
	private static final long serialVersionUID = -4486592159217251685L;
	/**
	 * 客户ID
	 */
	private Long customerid;
	/**
	 * 客户类型0:客户,1:联系人
	 */
	private String arCustType;
	/**
	 * 地址类型
	 */
	private String arAddrType;
	/**
	 * 省
	 */
	private String arProvince;
	/**
	 * 市
	 */
	private String arCity;
	/**
	 * 县/区
	 */
	private String arCounty;
	/**
	 * 详细地址
	 */
	private String arAddrDetail;
	/**
	 * 邮编
	 */
	private String arZipCode;
	/**
	 * 优先级
	 */
	private String arPriority;
	/**
	 * 是否有效
	 */
	private String arValid;
	/**
	 * 信息录入人
	 */
	private Long arInputId;
	/**
	 * 信息录入时间
	 */
	private Date arInputDate;
	/**
	 * 修改人
	 */
	private Long arModifyId;
	/**
	 * 修改时间
	 */
	private Date arModifyTime;
	/**
	 * 备注
	 */
	private String arMemo;
	/**
	 * 街道
	 */
	private String arStreet;

	@Column(name = "AR_CUST_TYPE", length = 2)
	public String getArCustType() {
		return this.arCustType;
	}

	public Long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}

	public void setArCustType(String arCustType) {
		this.arCustType = arCustType;
	}

	@Column(name = "AR_ADDR_TYPE", length = 2)
	public String getArAddrType() {
		return this.arAddrType;
	}

	public void setArAddrType(String arAddrType) {
		this.arAddrType = arAddrType;
	}

	public String getArStreet() {
		return arStreet;
	}

	public void setArStreet(String arStreet) {
		this.arStreet = arStreet;
	}

	@Column(name = "AR_PROVINCE", length = 16)
	public String getArProvince() {
		return this.arProvince;
	}

	public void setArProvince(String arProvince) {
		this.arProvince = arProvince;
	}

	@Column(name = "AR_CITY", length = 60)
	public String getArCity() {
		return this.arCity;
	}

	public void setArCity(String arCity) {
		this.arCity = arCity;
	}

	@Column(name = "AR_COUNTY", length = 60)
	public String getArCounty() {
		return this.arCounty;
	}

	public void setArCounty(String arCounty) {
		this.arCounty = arCounty;
	}

	@Column(name = "AR_ADDR_DETAIL", length = 600)
	public String getArAddrDetail() {
		return this.arAddrDetail;
	}

	public void setArAddrDetail(String arAddrDetail) {
		this.arAddrDetail = arAddrDetail;
	}

	@Column(name = "AR_ZIP_CODE", precision = 6, scale = 0)
	public String getArZipCode() {
		return this.arZipCode;
	}

	public void setArZipCode(String arZipCode) {
		this.arZipCode = arZipCode;
	}

	@Column(name = "AR_PRIORITY", length = 2)
	public String getArPriority() {
		return this.arPriority;
	}

	public void setArPriority(String arPriority) {
		this.arPriority = arPriority;
	}

	@Column(name = "AR_VALID", length = 1)
	public String getArValid() {
		return this.arValid;
	}

	public void setArValid(String arValid) {
		this.arValid = arValid;
	}

	@Column(name = "AR_INPUT_ID", scale = 0)
	public Long getArInputId() {
		return this.arInputId;
	}

	public void setArInputId(Long arInputId) {
		this.arInputId = arInputId;
	}

	@Column(name = "AR_INPUT_DATE")
	public Date getArInputDate() {
		return this.arInputDate;
	}

	public void setArInputDate(Date arInputDate) {
		this.arInputDate = arInputDate;
	}

	@Column(name = "AR_MODIFY_ID", scale = 0)
	public Long getArModifyId() {
		return this.arModifyId;
	}

	public void setArModifyId(Long arModifyId) {
		this.arModifyId = arModifyId;
	}

	@Column(name = "AR_MODIFY_TIME")
	public Date getArModifyTime() {
		return this.arModifyTime;
	}

	public void setArModifyTime(Date arModifyTime) {
		this.arModifyTime = arModifyTime;
	}

	@Column(name = "AR_MEMO", length = 200)
	public String getArMemo() {
		return this.arMemo;
	}

	public void setArMemo(String arMemo) {
		this.arMemo = arMemo;
	}

	@Override
	public String toString() {
		return "Addr [customerid=" + customerid + ", arCustType=" + arCustType
				+ ", arAddrType=" + arAddrType + ", arProvince=" + arProvince
				+ ", arCity=" + arCity + ", arCounty=" + arCounty
				+ ", arAddrDetail=" + arAddrDetail + ", arZipCode=" + arZipCode
				+ ", arPriority=" + arPriority + ", arValid=" + arValid
				+ ", arInputId=" + arInputId + ", arInputDate=" + arInputDate
				+ ", arModifyId=" + arModifyId + ", arModifyTime="
				+ arModifyTime + ", arMemo=" + arMemo + ", arStreet="
				+ arStreet + ", id=" + id + "]";
	}

}