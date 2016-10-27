/** 
 * @(#)FinanceMaturityRemind.java 1.0.0 2013-7-03 上午11:41:40  
 *  
 * Copyright © 2013 证大财富.  All rights reserved.  
 */
package com.zendaimoney.crm.customer.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 生日到期提醒VO对象
 * 
 * @author Yuan Changchun
 * @version $Revision:1.0.0, $Date: 2013-7-03 上午11:41:40 $
 */
public class CustomerBirthdayRemind implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6241685059330330222L;
	private Long id;// 客户ID
	private String crCustomerNumber; // 客户编号
	private String crName; // 中文名称
	private Date crBirthday; // 出生日期
	private Date crGatherDate; // 采集日期
	private Long crGather; // 信息采集人
	private Double remainDays;// 剩余天数

	public CustomerBirthdayRemind() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerBirthdayRemind(Long id, String crCustomerNumber,
			String crName, Date crBirthday, Date crGatherDate, Long crGather,
			Double remainDays) {
		this.id = id;
		this.crCustomerNumber = crCustomerNumber;
		this.crName = crName;
		this.crBirthday = crBirthday;
		this.crGatherDate = crGatherDate;
		this.crGather = crGather;
		this.remainDays = remainDays;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the crCustomerNumber
	 */
	public String getCrCustomerNumber() {
		return crCustomerNumber;
	}

	/**
	 * @param crCustomerNumber
	 *            the crCustomerNumber to set
	 */
	public void setCrCustomerNumber(String crCustomerNumber) {
		this.crCustomerNumber = crCustomerNumber;
	}

	/**
	 * @return the crName
	 */
	public String getCrName() {
		return crName;
	}

	/**
	 * @param crName
	 *            the crName to set
	 */
	public void setCrName(String crName) {
		this.crName = crName;
	}

	/**
	 * @return the crBirthday
	 */
	public Date getCrBirthday() {
		return crBirthday;
	}

	/**
	 * @param crBirthday
	 *            the crBirthday to set
	 */
	public void setCrBirthday(Date crBirthday) {
		this.crBirthday = crBirthday;
	}

	/**
	 * @return the crGatherDate
	 */
	public Date getCrGatherDate() {
		return crGatherDate;
	}

	/**
	 * @param crGatherDate
	 *            the crGatherDate to set
	 */
	public void setCrGatherDate(Date crGatherDate) {
		this.crGatherDate = crGatherDate;
	}

	/**
	 * @return the crGather
	 */
	public Long getCrGather() {
		return crGather;
	}

	/**
	 * @param crGather
	 *            the crGather to set
	 */
	public void setCrGather(Long crGather) {
		this.crGather = crGather;
	}

	/**
	 * @return the remainDays
	 */
	public Double getRemainDays() {
		return remainDays;
	}

	/**
	 * @param remainDays
	 *            the remainDays to set
	 */
	public void setRemainDays(Double remainDays) {
		this.remainDays = remainDays;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomerBirthdayRemind [id=" + id + ", crCustomerNumber="
				+ crCustomerNumber + ", crName=" + crName + ", crBirthday="
				+ crBirthday + ", crGatherDate=" + crGatherDate + ", crGather="
				+ crGather + ", remainDays=" + remainDays + "]";
	}

}
