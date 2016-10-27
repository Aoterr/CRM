/*
* Copyright (c) 2015 zendaimoney.com. All Rights Reserved.
*/
package com.zendaimoney.crm.product.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Subselect;

import com.zendaimoney.sys.parameter.entity.Parameter;
import com.zendaimoney.utils.helper.SystemParameterHelper;


/**
 * ApplyMatchAttachmentView.java
 *
 * Author: Yangying
 * Date: 2016年8月20日 上午9:49:11
 * Mail: yangy06@zendaimoney.com
 */
@Entity
@Subselect("SELECT C.CR_GATHER,V.ID,V.LEND_NO,V.previous_Lend_No,C.CR_NAME AS NAME,C.Cr_Sex  as sex,c.cr_idnum as id_num,P.PRODUCT_NAME,V.AMOUNT,V.TIME_INVEST_AMOUNT,v.fe_manage_fee,c.cr_source as customer_source,v.refree_Name,v.refree_no, V.CONTRACT_NO,V.state,V.APPLY_MATCH_DATE as apply_match_time,V.FE_PAYMENT_WAY as pay_ment_way,v.deduct_company,v.deduct_Bank,v.MANAGER,v.deduct_Account,v.deduct_Name,v.customer_Manager,v.finance_Center_Name,v.group_name,v.upper_Team,v.upper_Area,v.team_Leder  FROM (select f1.id  as id, CUSTOMERID, PRODUCT_ID, AMOUNT, MANAGER, TIME_INVEST_AMOUNT, LEND_NO, PREVIOUS_ID, CONTRACT_NO, FE_PAYMENT_WAY, state, FINANCE_CENTER, APPLY_MATCH_OPERATOR, APPLY_MATCH_DATE, fe_manage_fee, refree_Name, refree_no, deduct_company, f2.previous_lend_no, b.ba_account as deduct_Account, b.ba_account_name as deduct_Name, b.ba_bank_name as deduct_Bank, u.fu_manager_name as customer_Manager,u.fu_finance_center as finance_Center_Name,u.fu_group_name as group_name,u.fu_big_team as upper_Team,u.fu_upper_area as upper_Area,u.fu_team_leder as team_Leder from (SELECT ID,CUSTOMERID,FE_PRODUCT AS PRODUCT_ID,FE_AMOUNT AS AMOUNT,FE_MANAGER AS MANAGER,FE_TIME_INVEST_AMOUNT   AS TIME_INVEST_AMOUNT,FE_LEND_NO AS LEND_NO,FE_PREVIOUS_ID   AS PREVIOUS_ID,FE_CONTRACT_NO   AS CONTRACT_NO,FE_PAYMENT_WAY   AS FE_PAYMENT_WAY,FE_state  AS state,FE_FINANCE_CENTER AS FINANCE_CENTER,FE_APPLY_MATCH_OPERATOR AS APPLY_MATCH_OPERATOR,FE_APPLY_MATCH_DATE     AS APPLY_MATCH_DATE,fe_manage_fee    as fe_manage_fee,fe_deduct_account as deduct_account,fe_referee as refree_Name,fe_referee_no    as refree_no, fe_deduct_company as deduct_company FROM BUSI_FINANCE) f1 left join (select id, FE_LEND_NO as previous_Lend_No from busi_finance) f2 on f1.previous_id = f2.id left join (select id, BA_ACCOUNT, BA_ACCOUNT_NAME, BA_BANK_NAME from crm_bankaccount) b on b.id = f1.deduct_account left join busi_finance_uc u on u.fu_busi_id = f1.id and u.fu_status = '1') V,(select id, pt_product_name as PRODUCT_NAME from busi_product b) p,CRM_CUSTOMER C WHERE V.CUSTOMERID = C.ID and v.product_id = p.id")
public class ApplyMatchAttachmentView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	// 借款编号
	private String lendNo;
	// 上笔投资
	private String previousLendNo;
	//客户姓名
	private String name; 
	//性别
	private String sex; 
	//身份证号
	private String idNum;
	//理财模式
	private String productName;
	// 金额
	private BigDecimal amount;
	// 定投金额
	private BigDecimal timeInvestAmount;
	//管理费折扣率	
	private Double feManageFee;
	// 客户来源
	private String customerSource; 
	//划扣银行
	private String deductBank;
	//划扣账户名
	private String deductName;
	//划扣账号
	private String deductAccount;
	//推荐人
	private String refreeName;
	//推荐人工号
	private String refreeNo;
	//客户经理(姓名) -uc
	private String customerManager;
	//团队主管-uc
	private String teamLeder;
	//组-uc
	private String groupName;
	//大团队-uc
	private String upperTeam;
	//理财中心名 
	private String financeCenterName;
	//大区-uc
	private String upperArea;
	// 合同编号
	private String contractNo; 
	//CRM处理状态
	private String state;
	//申请匹配处理时间
	private Date applyMatchTime;
	//支付方式
	private String payMentWay;
	//划扣公司
	private String deductCompany;

	//客户经理(工号)
	private Long manager;
	public String getCustomerManager() {
		return customerManager;
	}
	
	public String getTeamLeder() {
		return teamLeder;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public String getUpperTeam() {
		return upperTeam;
	}
	
	public String getUpperArea() {
		return upperArea;
	}
	//--------
	@Id
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLendNo() {
		return lendNo;
	}
	
	public void setLendNo(String lendNo) {
		this.lendNo = lendNo;
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex.equals("1")?"男":sex.equals("0")?"女":"";
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getIdNum() {
		return idNum;
	}
	
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public BigDecimal getTimeInvestAmount() {
		return timeInvestAmount;
	}
	
	public void setTimeInvestAmount(BigDecimal timeInvestAmount) {
		this.timeInvestAmount = timeInvestAmount;
	}
	
	public Double getFeManageFee() {
		return feManageFee;
	}
	
	public void setFeManageFee(Double feManageFee) {
		this.feManageFee = feManageFee;
	}
	
	public String getCustomerSource() {
		if(StringUtils.isBlank(customerSource)){
			return "CRM";
		}
		else{
			Parameter parameter = SystemParameterHelper.findParameterByPrTypenameAndPrValue("客户来源",customerSource);
			if(parameter!=null)
				return parameter.getPrName();
		}
		return null;
	}
	
	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}
	
	public String getDeductBank() {
		return deductBank;
	}
	
	public void setDeductBank(String deductBank) {
		this.deductBank = deductBank;
	}
	
	public String getDeductName() {
		return deductName;
	}
	
	public void setDeductName(String deductName) {
		this.deductName = deductName;
	}
	
	public String getDeductAccount() {
		return deductAccount;
	}
	
	public void setDeductAccount(String deductAccount) {
		this.deductAccount = deductAccount;
	}
	
	public String getRefreeName() {
		return refreeName;
	}
	
	public void setRefreeName(String refreeName) {
		this.refreeName = refreeName;
	}
	
	public String getRefreeNo() {
		return refreeNo;
	}
	
	public void setRefreeNo(String refreeNo) {
		this.refreeNo = refreeNo;
	}
	
	public String getFinanceCenterName() {
		return financeCenterName;
	}
	 
	 
	public void setFinanceCenterName(String financeCenterName) {
		this.financeCenterName = financeCenterName;
	}
	 
	public String getContractNo() {
		return contractNo;
	}
	 
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	 
	public Date getApplyMatchTime() {
		return applyMatchTime;
	}
	
	public void setApplyMatchTime(Date applyMatchTime) {
		this.applyMatchTime = applyMatchTime;
	}
	
	public String getPayMentWay() {
		if(!StringUtils.isBlank(payMentWay)){
			Parameter parameter = SystemParameterHelper.findParameterByPrTypenameAndPrValue("理财支付方式",payMentWay);
			if(parameter!=null)
				return parameter.getPrName();
		}
		return null;
	}
	
	public void setPayMentWay(String payMentWay) {
		this.payMentWay = payMentWay;
	}
	
	public String getDeductCompany() {
		if(payMentWay.equals("2") && !StringUtils.isBlank(deductCompany)){
			Parameter parameter = SystemParameterHelper.findParameterByPrTypenameAndPrValue("划扣公司",deductCompany);
			if(parameter!=null)
				return parameter.getPrName();
		}
		return null;
	}
	
	public void setDeductCompany(String deductCompany) {
		this.deductCompany = deductCompany;
	}

	public Long getManager() {
		return manager;
	}

	public void setManager(Long manager) {
		this.manager = manager;
	}

	public void setCustomerManager(String customerManager) {
		this.customerManager = customerManager;
	}

	public void setTeamLeder(String teamLeder) {
		this.teamLeder = teamLeder;
	}


	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	public void setUpperTeam(String upperTeam) {
		this.upperTeam = upperTeam;
	}

	public void setUpperArea(String upperArea) {
		this.upperArea = upperArea;
	}

	public String getPreviousLendNo() {
		return previousLendNo;
	}
	public void setPreviousLendNo(String previousLendNo) {
		this.previousLendNo = previousLendNo;
	}
	public String getState() {
		if(!StringUtils.isBlank(state)){
			Parameter parameter = SystemParameterHelper.findParameterByPrTypenameAndPrValue("申请状态",state);
			if(parameter!=null)
				return parameter.getPrName();
		}
		return null;
	}
	public void setState(String state) {
		this.state = state;
	}

}
