/** 
 * @(#)FinanceMaturityRemind.java 1.0.0 2013-5-21 下午02:22:45  
 *  
 * Copyright © 2013 证大财富.  All rights reserved.  
 */

package com.zendaimoney.crm.product.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 理财到期提醒VO对象
 * 
 * @author zhanghao
 * @version $Revision:1.0.0, $Date: 2013-5-21 下午02:22:45 $
 */
public class FinanceMaturityRemind implements Serializable {

	private static final long serialVersionUID = 8913647290022025374L;

	/**
	 * 客户ID
	 */
	private Long custId;

	/**
	 * 客户姓名
	 */
	private String custName;

	/**
	 * 投资理财ID
	 */
	private Long financeId;

	/**
	 * 产品ID
	 */
	private Long productId;

	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 申购金额
	 */
	private Double amount;
	/**
	 * 出借编号
	 */
	private String lendNo;
	/**
	 * 客户经理ID
	 */
	private Long custManagerId;
	/**
	 * 客户经理
	 */
	private String custManagerName;
	/**
	 * 债权价值
	 */
	private Double debtRightsValue;
	/**
	 * 到期时间
	 */
	private Date maturityDate;
	/**
	 * 剩余天数
	 */
	private int remainDays;

	// ============= Sam.J edit 2014-10-21 增加合同编号属性 START=====================
	private String contractNo; // 合同编号

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	// ============= Sam.J edit 2014-10-21 增加合同编号属性 END=====================

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Long getFinanceId() {
		return financeId;
	}

	public void setFinanceId(Long financeId) {
		this.financeId = financeId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getLendNo() {
		return lendNo;
	}

	public void setLendNo(String lendNo) {
		this.lendNo = lendNo;
	}

	public Long getCustManagerId() {
		return custManagerId;
	}

	public void setCustManagerId(Long custManagerId) {
		this.custManagerId = custManagerId;
	}

	public String getCustManagerName() {
		return custManagerName;
	}

	public void setCustManagerName(String custManagerName) {
		this.custManagerName = custManagerName;
	}

	public Double getDebtRightsValue() {
		return debtRightsValue;
	}

	public void setDebtRightsValue(Double debtRightsValue) {
		this.debtRightsValue = debtRightsValue;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public int getRemainDays() {
		return remainDays;
	}

	public void setRemainDays(int remainDays) {
		this.remainDays = remainDays;
	}

	@Override
	public String toString() {
		return "FinanceMaturityRemind [custId=" + custId + ", custName="
				+ custName + ", financeId=" + financeId + ", productId="
				+ productId + ", productName=" + productName + ", amount="
				+ amount + ", lendNo=" + lendNo + ", custManagerId="
				+ custManagerId + ", custManagerName=" + custManagerName
				+ ", debtRightsValue=" + debtRightsValue + ", maturityDate="
				+ maturityDate + ", remainDays=" + remainDays + "]";
	}

}
