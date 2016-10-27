package com.zendaimoney.crm.product.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Subselect;

/**
 * 产品视图
 * 
 * @author zhanghao
 * @create 2012-12-3, 下午04:01:09
 * @version 1.0
 */
@Entity
@Subselect("SELECT C.CR_GATHER, V.ID, C.CR_NAME AS NAME, V.CUSTOMERID, V.PRODUCT_NAME, V.AMOUNT, V.REQUEST_DATE, V.INPUT_TIME, V.RESULT, MANAGER, V.TYPE, V.STATE, V.BUSI_STATE, V.TIME_INVEST_AMOUNT, v.invest_date, V.INPUT_ID, V.LEND_NO, V.MODIFY_DATE, V.PREVIOUS_ID, V.CONTRACT_NO, V.FE_DATA_ORIGIN, V.FE_VALID_STATE, V.FE_PAYAUDIT_FLAG, V.FE_PAYMENT_WAY, V.APPLY_MATCH_STATUS, V.APPLY_MATCH_OPERATOR, V.APPLY_MATCH_DATE FROM (SELECT ID, CUSTOMERID, FE_PRODUCT AS PRODUCT_NAME, FE_AMOUNT AS AMOUNT, FE_REQUEST_DATE AS REQUEST_DATE, FE_INPUT_TIME AS INPUT_TIME, FE_STATE AS STATE, FE_BUSI_STATE AS BUSI_STATE, FE_RESULT AS RESULT, FE_MANAGER AS MANAGER, FE_TIME_INVEST_AMOUNT AS TIME_INVEST_AMOUNT, FE_INPUT_ID AS INPUT_ID, FE_LEND_NO AS LEND_NO, FE_MODIFY_DATE AS MODIFY_DATE, FE_PREVIOUS_ID AS PREVIOUS_ID, FE_CONTRACT_NO AS CONTRACT_NO, FE_DATA_ORIGIN AS FE_DATA_ORIGIN, FE_VALID_STATE AS FE_VALID_STATE, FE_PAYAUDIT_FLAG AS FE_PAYAUDIT_FLAG, FE_INVEST_DATE AS INVEST_DATE, FE_PAYMENT_WAY AS FE_PAYMENT_WAY, 2 AS TYPE, FE_APPLY_MATCH_STATUS AS APPLY_MATCH_STATUS, FE_APPLY_MATCH_OPERATOR AS APPLY_MATCH_OPERATOR, FE_APPLY_MATCH_DATE AS APPLY_MATCH_DATE FROM BUSI_FINANCE) V, CRM_CUSTOMER C WHERE V.CUSTOMERID = C.ID")
public class ProductView implements Serializable {
	private static final long serialVersionUID = 3696076028049227159L;
	private Long id;
	private String name;
	private Long customerid;// 客户id
	private String productName;// 产品名称
	private BigDecimal amount;// 金额
	private Date requestDate;// 申请日期
	private Date  investDate;//  投资起始日 15-9-2
	private Date inputTime;// 输入时间
	private String result;// 结果
	private Long manager;// 客户经理
	private Long type;// 区分是理财产品还是信贷产品
	private Long state;// 处理状态
	private Long busiState;// 业务状态
	private BigDecimal timeInvestAmount;// 定投金额
	private Long inputId;// 创建人
	private String lendNo;// 出借/借款编号
	private Date modifyDate;// 修改时间
	private Long previousId;// 上笔投资ID
	private Long crGather;// 信息采集人
//	private String financeCenterName;//理财中心名
	private Long applyMatchOperator;//经办人ID
	private Date applyMatchDate;	//申请提交时间

	// ============= Sam.J edit 2014-10-10 增加合同编号属性 START=====================
	private String contractNo; // 合同编号

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	// ============= Sam.J edit 2014-10-10 增加合同编号属性 END=====================

	// ============= Sam.J edit 2014-11-25 增加数据来源属性 START=====================
	private String feDataOrigin;// 数据来源

	public String getFeDataOrigin() {
		return feDataOrigin;
	}

	public void setFeDataOrigin(String feDataOrigin) {
		this.feDataOrigin = feDataOrigin;
	}

	// ============= Sam.J edit 2014-11-25 增加数据来源 END=====================

	// =============增加业务单子逻辑删除标志位 add by Sam.J 2014.12.9 =====================
	/**
	 * 逻辑删除位
	 */
	private String feValidState;

	public String getFeValidState() {
		return feValidState;
	}

	public void setFeValidState(String feValidState) {
		this.feValidState = feValidState;
	}

	// ============= Sam.J edit 2014-12-9 增加业务单子逻辑删除标志位 END=====================

	/*--------增加是否允许划扣标志位和支付方式  add by  Sam.J  2015.1.28
	/**
	 * 划扣标志位
	 */
	private String fePayauditFlag;

	/**
	 * 支付方式
	 */
	private String fePaymentWay;

	//申请匹配状态
	private String applyMatchStatus;

	public String getFePaymentWay() {
		return fePaymentWay;
	}

	public void setFePaymentWay(String fePaymentWay) {
		this.fePaymentWay = fePaymentWay;
	}

	public String getFePayauditFlag() {
		return fePayauditFlag;
	}

	public void setFePayauditFlag(String fePayauditFlag) {
		this.fePayauditFlag = fePayauditFlag;
	}

	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
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

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Long getManager() {
		return manager;
	}

	public void setManager(Long manager) {
		this.manager = manager;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public Long getBusiState() {
		return busiState;
	}

	public void setBusiState(Long busiState) {
		this.busiState = busiState;
	}

	public BigDecimal getTimeInvestAmount() {
		return timeInvestAmount;
	}

	public void setTimeInvestAmount(BigDecimal timeInvestAmount) {
		this.timeInvestAmount = timeInvestAmount;
	}

	public Long getInputId() {
		return inputId;
	}

	public void setInputId(Long inputId) {
		this.inputId = inputId;
	}

	public String getLendNo() {
		return lendNo;
	}

	public void setLendNo(String lendNo) {
		this.lendNo = lendNo;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Long getPreviousId() {
		return previousId;
	}

	public void setPreviousId(Long previousId) {
		this.previousId = previousId;
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

	public Date getInvestDate() {
		return investDate;
	}

	public void setInvestDate(Date investDate) {
		this.investDate = investDate;
	}

	public String getApplyMatchStatus() {
		return applyMatchStatus;
	}

	public void setApplyMatchStatus(String applyMatchStatus) {
		this.applyMatchStatus = applyMatchStatus;
	}

//	public String getFinanceCenterName() {
//		return financeCenterName;
//	}
//
//	public void setFinanceCenterName(String financeCenterName) {
//		this.financeCenterName = financeCenterName;
//	}

	public Long getApplyMatchOperator() {
		return applyMatchOperator;
	}

	public void setApplyMatchOperator(Long applyMatchOperator) {
		this.applyMatchOperator = applyMatchOperator;
	}

	public Date getApplyMatchDate() {
		return applyMatchDate;
	}

	public void setApplyMatchDate(Date applyMatchDate) {
		this.applyMatchDate = applyMatchDate;
	}
}
