package com.zendaimoney.crm.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springside.modules.orm.IdEntity;

/**
 * 
 * 地址信息
 * 
 */
@Entity
@Table(name = "CRM_CAR")
public class Car extends IdEntity {
	private static final long serialVersionUID = -4486592159217251685L;
	/**
	 * 客户ID
	 */
	private Long customerid;
	/**
	 * 购车类型
	 */
	private String caType;
	/**
	 * 购买日期
	 */
	private Date caBuyDate;
	/**
	 * 购车价格
	 */
	private Double caPice;
	/**
	 * 车贷有无
	 */
	private String caHaveLoan;
	/**
	 * 贷款期限
	 */
	private Long caLoanTerm;
	/**
	 * 月供金额
	 */
	private Double caMonthRepayment;

	public Long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}

	@Column(name = "CA_TYPE", length = 16)
	public String getCaType() {
		return caType;
	}

	public void setCaType(String caType) {
		this.caType = caType;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CA_BUY_DATE", length = 7)
	public Date getCaBuyDate() {
		return caBuyDate;
	}

	public void setCaBuyDate(Date caBuyDate) {
		this.caBuyDate = caBuyDate;
	}

	public Double getCaPice() {
		return caPice;
	}

	public void setCaPice(Double caPice) {
		this.caPice = caPice;
	}

	public String getCaHaveLoan() {
		return caHaveLoan;
	}

	public void setCaHaveLoan(String caHaveLoan) {
		this.caHaveLoan = caHaveLoan;
	}

	public Long getCaLoanTerm() {
		return caLoanTerm;
	}

	public void setCaLoanTerm(Long caLoanTerm) {
		this.caLoanTerm = caLoanTerm;
	}

	public Double getCaMonthRepayment() {
		return caMonthRepayment;
	}

	public void setCaMonthRepayment(Double caMonthRepayment) {
		this.caMonthRepayment = caMonthRepayment;
	}

}
