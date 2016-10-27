package com.zendaimoney.crm.fund.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Subselect;

/**
 * 产品视图
 * 
 * @author CJ
 * @create 2012-12-3, 下午04:01:09
 * @version 1.0
 */
@Entity
@Subselect("SELECT C.CR_GATHER,V.ID,C.CR_NAME AS NAME,V.CUSTOMERID,V.PRODUCT_NAME,V.AMOUNT,V.REQUEST_DATE,V.INPUT_TIME,V.RESULT,MANAGER,V.TYPE,V.STATE,V.BUSI_STATE,V.TIME_INVEST_AMOUNT,v.invest_date,V.INPUT_ID,V.LEND_NO,V.MODIFY_DATE,V.PREVIOUS_ID,V.CONTRACT_NO,V.FD_DATA_ORIGIN,V.FD_VALID_STATE,V.FD_PAYAUDIT_FLAG, v.FD_TERM, V.FD_PAY_STATUS,V.FD_PAYMENT_WAY FROM (SELECT ID,CUSTOMERID,FD_PRODUCT AS PRODUCT_NAME,FD_AMOUNT AS AMOUNT,FD_REQUEST_DATE AS REQUEST_DATE,FD_INPUT_TIME AS INPUT_TIME,FD_STATE AS STATE,FD_BUSI_STATE AS BUSI_STATE,FD_RESULT AS RESULT,FD_MANAGER AS MANAGER,FD_TIME_INVEST_AMOUNT AS TIME_INVEST_AMOUNT,FD_INPUT_ID AS INPUT_ID,FD_LEND_NO AS LEND_NO,FD_MODIFY_DATE AS MODIFY_DATE,FD_PREVIOUS_ID AS PREVIOUS_ID,FD_CONTRACT_NO AS CONTRACT_NO,FD_DATA_ORIGIN AS FD_DATA_ORIGIN,FD_VALID_STATE AS FD_VALID_STATE,FD_PAYAUDIT_FLAG AS FD_PAYAUDIT_FLAG,FD_INVEST_DATE  AS INVEST_DATE,FD_PAYMENT_WAY AS FD_PAYMENT_WAY, FD_PAY_STATUS AS  FD_PAY_STATUS, FD_TERM  AS FD_TERM,7 AS TYPE FROM BUSI_FUND_INFO) V,CRM_CUSTOMER C WHERE V.CUSTOMERID = C.ID")
public class FundsProductView implements Serializable {
	private static final long serialVersionUID = 3696076028049227159L;
	private Long id;
	private String name;//客户姓名
	private Long customerid;// 客户id
	private String productName;// 产品名称
	private BigDecimal amount;// 金额
	private Date requestDate;// 申请日期
	private Date  investDate;//  投资起始日 
	private Date inputTime;// 输入时间
	private String result;// 结果
	private Long manager;// 客户经理
	private Long type;// 基金类型
	private Long state;// 处理状态
	private Long busiState;// 业务状态
	private BigDecimal timeInvestAmount;// 定投金额
	private Long inputId;// 创建人
	private String lendNo;// 出借/借款编号
	private Date modifyDate;// 修改时间
	private Long previousId;// 上笔投资ID
	private Long crGather;// 信息采集人
	private String contractNo; // 合同编号
	private String fdTerm;//存续期限  月
	private String fdValidState;//逻辑删除位
	private String fdPayauditFlag;//划扣标志位
	private String fdPaymentWay;// 支付方式
	private String fdDataOrigin;// 数据来源
	private String fdPayStatus;//支付状态

	
	public String getFdPayStatus() {
		return fdPayStatus;
	}

	public void setFdPayStatus(String fdPayStatus) {
		this.fdPayStatus = fdPayStatus;
	}

	public String getFdTerm() {
		return fdTerm;
	}

	public void setFdTerm(String fdTerm) {
		this.fdTerm = fdTerm;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getFdDataOrigin() {
		return fdDataOrigin;
	}

	public void setFdDataOrigin(String fdDataOrigin) {
		this.fdDataOrigin = fdDataOrigin;
	}

	public String getFdValidState() {
		return fdValidState;
	}

	public void setFdValidState(String fdValidState) {
		this.fdValidState = fdValidState;
	}

	public String getFdPaymentWay() {
		return fdPaymentWay;
	}

	public void setFdPaymentWay(String fdPaymentWay) {
		this.fdPaymentWay = fdPaymentWay;
	}

	public String getFdPayauditFlag() {
		return fdPayauditFlag;
	}

	public void setFdPayauditFlag(String fdPayauditFlag) {
		this.fdPayauditFlag = fdPayauditFlag;
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

}
