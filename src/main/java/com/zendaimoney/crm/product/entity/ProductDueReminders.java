/** 
 * @(#)ProductDueReminders.java 1.0.0 2013-7-24 上午10:07:58  
 *  
 * Copyright © 2013 证大财富.  All rights reserved.  
 */

package com.zendaimoney.crm.product.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Subselect;

/**
 * 
 * 信贷补件信息业务对象视图
 * 
 * @author Yuan Changchun
 * @version $Revision:1.0.0, $Date: 2013-7-24 上午10:07:58 $
 */
@Entity
@Subselect("select t1.id,t1.customerid,t2.cr_Name,t2.cr_Customer_Number,t1.c_Product,t1.c_Amount,t1.c_Term,t1.c_Manager from BUSI_CREDIT t1,CRM_CUSTOMER t2 where t1.customerid = t2.id")
public class ProductDueReminders implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7778787034428084658L;

	/**
	 * 信贷业务id
	 */
	private Long id;
	/**
	 * 客户id
	 */
	private Long customerid;
	/**
	 * 客户名字
	 */
	private String crName;
	/**
	 * 客户编号
	 */
	private String crCustomerNumber;
	/**
	 * 产品类型
	 */
	private String cProduct;
	/**
	 * 申请金额
	 */
	private Double cAmount;
	/**
	 * 申请期数
	 */
	private Integer cTerm;
	/**
	 * 客户经理ID
	 */
	private Long cManager;

	public ProductDueReminders() {

	}

	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}

	public String getCrName() {
		return crName;
	}

	public void setCrName(String crName) {
		this.crName = crName;
	}

	public String getCrCustomerNumber() {
		return crCustomerNumber;
	}

	public void setCrCustomerNumber(String crCustomerNumber) {
		this.crCustomerNumber = crCustomerNumber;
	}

	public String getcProduct() {
		return cProduct;
	}

	public void setcProduct(String cProduct) {
		this.cProduct = cProduct;
	}

	public Double getcAmount() {
		return cAmount;
	}

	public void setcAmount(Double cAmount) {
		this.cAmount = cAmount;
	}

	public Integer getcTerm() {
		return cTerm;
	}

	public void setcTerm(Integer cTerm) {
		this.cTerm = cTerm;
	}

	public Long getcManager() {
		return cManager;
	}

	public void setcManager(Long cManager) {
		this.cManager = cManager;
	}

}
