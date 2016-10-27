package com.zendaimoney.crm.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springside.modules.orm.IdEntity;

@Entity
@Table(name = "CRM_BANKACCOUNT")
public class Bankaccount extends IdEntity {
	private static final long serialVersionUID = -2052685326722242942L;
	private Customer crmCustomer; // 客户ID
	private String baAccount; // 银行账号
	private String baAccountName; // 开户名
	private String baAccountType; // 账户类型
	private String baBankCode; // 银行代码
	private String baBankName; // 银行名称
	private String baBranchName; // 支行名称
	private Long baInputId; // 信息录入人
	private Date baInputDate; // 信息录入时间
	private Long baModifyId; // 修改人
	private Date baModifyTime; // 修改时间
	private String baValid; // 是否有效
	private String baMemo; // 备注
	
	private String baAppFlag;//是否为线上APP可用

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMERID", nullable = false)
	@JsonIgnore
	public Customer getCrmCustomer() {
		return this.crmCustomer;
	}

	public void setCrmCustomer(Customer crmCustomer) {
		this.crmCustomer = crmCustomer;
	}

	@Column(name = "BA_ACCOUNT", nullable = false, length = 30)
	public String getBaAccount() {
		return this.baAccount;
	}

	public void setBaAccount(String baAccount) {
		this.baAccount = baAccount;
	}

	@Column(name = "BA_ACCOUNT_NAME", nullable = false, length = 60)
	public String getBaAccountName() {
		return this.baAccountName;
	}

	public void setBaAccountName(String baAccountName) {
		this.baAccountName = baAccountName;
	}

	@Column(name = "BA_ACCOUNT_TYPE", nullable = false, length = 2)
	public String getBaAccountType() {
		return this.baAccountType;
	}

	public void setBaAccountType(String baAccountType) {
		this.baAccountType = baAccountType;
	}

	@Column(name = "BA_BANK_CODE", length = 20)
	public String getBaBankCode() {
		return this.baBankCode;
	}

	public void setBaBankCode(String baBankCode) {
		this.baBankCode = baBankCode;
	}

	@Column(name = "BA_BANK_NAME", length = 40)
	public String getBaBankName() {
		return this.baBankName;
	}

	public void setBaBankName(String baBankName) {
		this.baBankName = baBankName;
	}

	@Column(name = "BA_BRANCH_NAME", length = 80)
	public String getBaBranchName() {
		return this.baBranchName;
	}

	public void setBaBranchName(String baBranchName) {
		this.baBranchName = baBranchName;
	}

	@Column(name = "BA_INPUT_ID", scale = 0)
	public Long getBaInputId() {
		return this.baInputId;
	}

	public void setBaInputId(Long baInputId) {
		this.baInputId = baInputId;
	}

	@Column(name = "BA_INPUT_DATE")
	public Date getBaInputDate() {
		return this.baInputDate;
	}

	public void setBaInputDate(Date baInputDate) {
		this.baInputDate = baInputDate;
	}

	@Column(name = "BA_MODIFY_ID", scale = 0)
	public Long getBaModifyId() {
		return this.baModifyId;
	}

	public void setBaModifyId(Long baModifyId) {
		this.baModifyId = baModifyId;
	}

	@Column(name = "BA_MODIFY_TIME")
	public Date getBaModifyTime() {
		return this.baModifyTime;
	}

	public void setBaModifyTime(Date baModifyTime) {
		this.baModifyTime = baModifyTime;
	}

	@Column(name = "BA_VALID", nullable = false, length = 1)
	public String getBaValid() {
		return this.baValid;
	}

	public void setBaValid(String baValid) {
		this.baValid = baValid;
	}

	@Column(name = "BA_MEMO", length = 200)
	public String getBaMemo() {
		return this.baMemo;
	}

	public void setBaMemo(String baMemo) {
		this.baMemo = baMemo;
	}

	@Override
	public String toString() {
		return "Bankaccount [crmCustomer=" + crmCustomer + ", baAccount="
				+ baAccount + ", baAccountName=" + baAccountName
				+ ", baAccountType=" + baAccountType + ", baBankCode="
				+ baBankCode + ", baBankName=" + baBankName + ", baBranchName="
				+ baBranchName + ", baInputId=" + baInputId + ", baInputDate="
				+ baInputDate + ", baModifyId=" + baModifyId
				+ ", baModifyTime=" + baModifyTime + ", baValid=" + baValid
				+ ", baMemo=" + baMemo + ", id=" + id + "]";
	}

	public String getBaAppFlag() {
		return baAppFlag;
	}

	public void setBaAppFlag(String baAppFlag) {
		this.baAppFlag = baAppFlag;
	}

}