package com.zendaimoney.crm.product.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springside.modules.orm.IdEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zendaimoney.crm.attachment.entity.BusiRequestAttach;


@Entity
@Table(name = "BUSI_FINANCE")
public class BusiFinance extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = -1551107711619778507L;
	private Long customerid;// 客户编号
	private Date feRepaymentDate;// 债权还款日
	private String feProduct;// 理财产品
	private Double feAmount;// 出借金额
	private String feInvestWay;// 资金出借方式
	private Date feInvestDate;// 预期出借日期
	private Date feDivestDate;// 计划撤资日期
	private String feProtocolVersion;// 协议版本
	private String feCallback;// 回收方式
	private String feRiskCompensation;// 是否风险补偿 1:是 0：否
	private Double feTimeInvestAmount;// 定投金额
	private Date feTimeInvestStart;// 定投生效日期
	private Date feTimeInvestEnd;// 定投失效日期
	private String fePaymentWay;// 支付方式
	private Date feDeductDate;// 划扣日期
	private Long feDeductAccount;// 划扣账号ID
	private Long feReturnAccount;// 回款账号ID
	private String feContinueInvest;// 是否续投
	private String feContinueProduct;// 续投产品
	private Long feManager;// 客户经理
	private Date feRequestDate;// 申请日期
	private Date feInputTime;// 创建时间
	private Long feInputId;// 创建人
	private String feBusiState;// 业务状态
	private String feState;// 状态
	private String feMemo;// 备注
	private String feResult;// 申请处理结果
	private Date feModifyDate;// 最后修改时间
	private Long feModifyId;// 最后修改人
	private Double feManageFee;// 管理费折扣
	private String feContractNo;// 合同编号
	private String feLendNo;// 出借编号
	private String feRemittanceAccount;// 汇入行别

	// add by zhanghao on 2013-05-23针对续投添加字段fePreviousId
	private Long fePreviousId;// 上一笔投资id
	
	
	//申请匹配功能增加
//	private String feFinanceCenter;//理财中心名
	private Long feApplyMatchOperator;//经办人ID
	private String feApplyMatchStatus;//申请匹配状态
	private Date feApplyMatchDate;//申请匹配提交时间

	/**
	 * 划扣公司
	 * 
	 * @author Yuan Changchun
	 */
	private Integer feDeductCompany;

	/**
	 * 年化利率
	 * 
	 * @author Yuan Changchun
	 */
	private Double feInterestRate;

	/**
	 * 车贷期数
	 * 
	 * @author Yuan Changchun
	 */
	private Integer feTerm;

	/**
	 * 月还款额
	 * 
	 * @author Yuan Changchun
	 */
	private Double feMonthRepay;

	/**
	 * 推荐人
	 */
	private String feReferee;

	/**
	 * 推荐人工号
	 */
	private String feRefereeNo;

	/*--------增加4S 门面信息  add by  Sam.J  2014.06.23
	/**
	 * 4S合作门店
	 */
	private String feFoursName;

	/****** APP大拇指start *****/
	private String fePayStatus;// 付款状态
	private String feDataOrigin;// 数据来源
	private String feActivityId;// 活动ID
	private Date feReconciliationDate;// 对账日期
	private String feAppMemo;// APP大拇指专用备注信息
	
	//add by chenjiale thumb2.0 
	//fortune系统撤单标识，1：订单撤单 用来统计客户撤单次数，如果超过规定次数，将无法通过app申购
	private String feAppOrderCancelFlag; 

	public String getFeAppOrderCancelFlag() {
		return feAppOrderCancelFlag;
	}

	public void setFeAppOrderCancelFlag(String feAppOrderCancelFlag) {
		this.feAppOrderCancelFlag = feAppOrderCancelFlag;
	}

	public String getFeValidState() {
		return feValidState;
	}

	public void setFeValidState(String feValidState) {
		this.feValidState = feValidState;
	}

	private Date fePayTime;// 支付时间

	/****** APP大拇指end *****/

	/*--------增加业务单子逻辑删除标志位  add by  Sam.J  2014.12.9
	/**
	 * 逻辑删除位
	 */
	private String feValidState;

	/*--------增加是否允许划扣标志位  add by  Sam.J  2015.1.28
	/**
	 * 划扣标志位
	 */
	private String fePayauditFlag;

	/*--------增加债权id字段  add by  Sam.J  2015.4.8
	/**
	 * 债权id
	 */
	private String feLoanId;
	
	/*--------增加非固产品封闭到期日字段  add by  Sam.J  2015.8.14
	/**
	 * 非固产品封闭日
	 */
	private Date feClosedDate;

	public String getFePayauditFlag() {
		return fePayauditFlag;
	}

	public void setFePayauditFlag(String fePayauditFlag) {
		this.fePayauditFlag = fePayauditFlag;
	}

	public String getFeFoursName() {
		return feFoursName;
	}

	public void setFeFoursName(String feFoursName) {
		this.feFoursName = feFoursName;
	}

	private Set<BusiRequestAttach> busiRequestAttachs = new HashSet<BusiRequestAttach>();// 业务申请附件

	public BusiFinance() {
	}

	public BusiFinance(Long id) {
		this.id = id;
	}

	public Long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getFeRepaymentDate() {
		return feRepaymentDate;
	}

	public void setFeRepaymentDate(Date feRepaymentDate) {
		this.feRepaymentDate = feRepaymentDate;
	}

	public String getFeProduct() {
		return feProduct;
	}

	public void setFeProduct(String feProduct) {
		this.feProduct = feProduct;
	}

	public Double getFeAmount() {
		return feAmount;
	}

	public void setFeAmount(Double feAmount) {
		this.feAmount = feAmount;
	}

	public String getFeInvestWay() {
		return feInvestWay;
	}

	public void setFeInvestWay(String feInvestWay) {
		this.feInvestWay = feInvestWay;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getFeInvestDate() {
		return feInvestDate;
	}

	public void setFeInvestDate(Date feInvestDate) {
		this.feInvestDate = feInvestDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getFeDivestDate() {
		return feDivestDate;
	}

	public void setFeDivestDate(Date feDivestDate) {
		this.feDivestDate = feDivestDate;
	}

	public String getFeProtocolVersion() {
		return feProtocolVersion;
	}

	public void setFeProtocolVersion(String feProtocolVersion) {
		this.feProtocolVersion = feProtocolVersion;
	}

	public String getFeCallback() {
		return feCallback;
	}

	public void setFeCallback(String feCallback) {
		this.feCallback = feCallback;
	}

	public String getFeRiskCompensation() {
		return feRiskCompensation;
	}

	public void setFeRiskCompensation(String feRiskCompensation) {
		this.feRiskCompensation = feRiskCompensation;
	}

	public Double getFeTimeInvestAmount() {
		return feTimeInvestAmount;
	}

	public void setFeTimeInvestAmount(Double feTimeInvestAmount) {
		this.feTimeInvestAmount = feTimeInvestAmount;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getFeTimeInvestStart() {
		return feTimeInvestStart;
	}

	public void setFeTimeInvestStart(Date feTimeInvestStart) {
		this.feTimeInvestStart = feTimeInvestStart;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getFeTimeInvestEnd() {
		return feTimeInvestEnd;
	}

	public void setFeTimeInvestEnd(Date feTimeInvestEnd) {
		this.feTimeInvestEnd = feTimeInvestEnd;
	}

	public String getFePaymentWay() {
		return fePaymentWay;
	}

	public void setFePaymentWay(String fePaymentWay) {
		this.fePaymentWay = fePaymentWay;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getFeDeductDate() {
		return feDeductDate;
	}

	public void setFeDeductDate(Date feDeductDate) {
		this.feDeductDate = feDeductDate;
	}

	public Long getFeDeductAccount() {
		return feDeductAccount;
	}

	public void setFeDeductAccount(Long feDeductAccount) {
		this.feDeductAccount = feDeductAccount;
	}

	public Long getFeReturnAccount() {
		return feReturnAccount;
	}

	public void setFeReturnAccount(Long feReturnAccount) {
		this.feReturnAccount = feReturnAccount;
	}

	public String getFeContinueInvest() {
		return feContinueInvest;
	}

	public void setFeContinueInvest(String feContinueInvest) {
		this.feContinueInvest = feContinueInvest;
	}

	public String getFeContinueProduct() {
		return feContinueProduct;
	}

	public void setFeContinueProduct(String feContinueProduct) {
		this.feContinueProduct = feContinueProduct;
	}

	public Long getFeManager() {
		return feManager;
	}

	public void setFeManager(Long feManager) {
		this.feManager = feManager;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getFeRequestDate() {
		return feRequestDate;
	}

	public void setFeRequestDate(Date feRequestDate) {
		this.feRequestDate = feRequestDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getFeInputTime() {
		return feInputTime;
	}

	public void setFeInputTime(Date feInputTime) {
		this.feInputTime = feInputTime;
	}

	public Long getFeInputId() {
		return feInputId;
	}

	public void setFeInputId(Long feInputId) {
		this.feInputId = feInputId;
	}

	public String getFeBusiState() {
		return feBusiState;
	}

	public void setFeBusiState(String feBusiState) {
		this.feBusiState = feBusiState;
	}

	public String getFeState() {
		return feState;
	}

	public void setFeState(String feState) {
		this.feState = feState;
	}

	public String getFeMemo() {
		return feMemo;
	}

	public void setFeMemo(String feMemo) {
		this.feMemo = feMemo;
	}

	public String getFeResult() {
		return feResult;
	}

	public void setFeResult(String feResult) {
		this.feResult = feResult;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getFeModifyDate() {
		return feModifyDate;
	}

	public void setFeModifyDate(Date feModifyDate) {
		this.feModifyDate = feModifyDate;
	}

	public Long getFeModifyId() {
		return feModifyId;
	}

	public void setFeModifyId(Long feModifyId) {
		this.feModifyId = feModifyId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "busiFinance")
	@JsonIgnore
	public Set<BusiRequestAttach> getBusiRequestAttachs() {
		return this.busiRequestAttachs;
	}

	public void setBusiRequestAttachs(Set<BusiRequestAttach> busiRequestAttachs) {
		this.busiRequestAttachs = busiRequestAttachs;
	}

	public Double getFeManageFee() {
		return feManageFee;
	}

	public void setFeManageFee(Double feManageFee) {
		this.feManageFee = feManageFee;
	}

	public String getFeContractNo() {
		return feContractNo;
	}

	public void setFeContractNo(String feContractNo) {
		this.feContractNo = feContractNo;
	}

	public String getFeLendNo() {
		return feLendNo;
	}

	public void setFeLendNo(String feLendNo) {
		this.feLendNo = feLendNo;
	}

	public String getFeRemittanceAccount() {
		return feRemittanceAccount;
	}

	public void setFeRemittanceAccount(String feRemittanceAccount) {
		this.feRemittanceAccount = feRemittanceAccount;
	}

	public Long getFePreviousId() {
		return fePreviousId;
	}

	public void setFePreviousId(Long fePreviousId) {
		this.fePreviousId = fePreviousId;
	}

	/**
	 * @return the feDeductCompany
	 */
	public Integer getFeDeductCompany() {
		return feDeductCompany;
	}

	/**
	 * @param feDeductCompany
	 *            the feDeductCompany to set
	 */
	public void setFeDeductCompany(Integer feDeductCompany) {
		this.feDeductCompany = feDeductCompany;
	}

	/**
	 * @return the feInterestRate
	 */
	public Double getFeInterestRate() {
		return feInterestRate;
	}

	/**
	 * @param feInterestRate
	 *            the feInterestRate to set
	 */
	public void setFeInterestRate(Double feInterestRate) {
		this.feInterestRate = feInterestRate;
	}

	/**
	 * @return the feTerm
	 */
	public Integer getFeTerm() {
		return feTerm;
	}

	/**
	 * @param feTerm
	 *            the feTerm to set
	 */
	public void setFeTerm(Integer feTerm) {
		this.feTerm = feTerm;
	}

	/**
	 * @return the feMonthRepay
	 */
	public Double getFeMonthRepay() {
		return feMonthRepay;
	}

	/**
	 * @param feMonthRepay
	 *            the feMonthRepay to set
	 */
	public void setFeMonthRepay(Double feMonthRepay) {
		this.feMonthRepay = feMonthRepay;
	}

	/**
	 * @return the feReferee
	 */
	public String getFeReferee() {
		return feReferee;
	}

	/**
	 * @param feReferee
	 *            the feReferee to set
	 */
	public void setFeReferee(String feReferee) {
		this.feReferee = feReferee;
	}

	/**
	 * @return the feRefereeNo
	 */
	public String getFeRefereeNo() {
		return feRefereeNo;
	}

	/**
	 * @param feRefereeNo
	 *            the feRefereeNo to set
	 */
	public void setFeRefereeNo(String feRefereeNo) {
		this.feRefereeNo = feRefereeNo;
	}

	public String getFePayStatus() {
		return fePayStatus;
	}

	public void setFePayStatus(String fePayStatus) {
		this.fePayStatus = fePayStatus;
	}

	public String getFeDataOrigin() {
		return feDataOrigin;
	}

	public void setFeDataOrigin(String feDataOrigin) {
		this.feDataOrigin = feDataOrigin;
	}

	public String getFeActivityId() {
		return feActivityId;
	}

	public void setFeActivityId(String feActivityId) {
		this.feActivityId = feActivityId;
	}

	public Date getFeReconciliationDate() {
		return feReconciliationDate;
	}

	public void setFeReconciliationDate(Date feReconciliationDate) {
		this.feReconciliationDate = feReconciliationDate;
	}

	public String getFeAppMemo() {
		return feAppMemo;
	}

	public void setFeAppMemo(String feAppMemo) {
		this.feAppMemo = feAppMemo;
	}

	public Date getFePayTime() {
		return fePayTime;
	}

	public void setFePayTime(Date fePayTime) {
		this.fePayTime = fePayTime;
	}

	@Override
	public String toString() {
		return "BusiFinance [customerid=" + customerid + ", feRepaymentDate="
				+ feRepaymentDate + ", feProduct=" + feProduct + ", feAmount="
				+ feAmount + ", feInvestWay=" + feInvestWay + ", feInvestDate="
				+ feInvestDate + ", feDivestDate=" + feDivestDate
				+ ", feProtocolVersion=" + feProtocolVersion + ", feCallback="
				+ feCallback + ", feRiskCompensation=" + feRiskCompensation
				+ ", feTimeInvestAmount=" + feTimeInvestAmount
				+ ", feTimeInvestStart=" + feTimeInvestStart
				+ ", feTimeInvestEnd=" + feTimeInvestEnd + ", fePaymentWay="
				+ fePaymentWay + ", feDeductDate=" + feDeductDate
				+ ", feDeductAccount=" + feDeductAccount + ", feReturnAccount="
				+ feReturnAccount + ", feContinueInvest=" + feContinueInvest
				+ ", feContinueProduct=" + feContinueProduct + ", feManager="
				+ feManager + ", feRequestDate=" + feRequestDate
				+ ", feInputTime=" + feInputTime + ", feInputId=" + feInputId
				+ ", feBusiState=" + feBusiState + ", feState=" + feState
				+ ", feMemo=" + feMemo + ", feResult=" + feResult
				+ ", feModifyDate=" + feModifyDate + ", feModifyId="
				+ feModifyId + ", feManageFee=" + feManageFee
				+ ", feContractNo=" + feContractNo + ", feLendNo=" + feLendNo
				+ ", feRemittanceAccount=" + feRemittanceAccount
				+ ", fePreviousId=" + fePreviousId + ", busiRequestAttachs="
				+ busiRequestAttachs + ", id=" + id + "]";
	}

	public String getFeLoanId() {
		return feLoanId;
	}

	public void setFeLoanId(String feLoanId) {
		this.feLoanId = feLoanId;
	}

	public Date getFeClosedDate() {
		return feClosedDate;
	}

	public void setFeClosedDate(Date feClosedDate) {
		this.feClosedDate = feClosedDate;
	}

//	public String getFeFinanceCenter() {
//		return feFinanceCenter;
//	}
//
//	public void setFeFinanceCenter(String feFinanceCenter) {
//		this.feFinanceCenter = feFinanceCenter;
//	}

	public Long getFeApplyMatchOperator() {
		return feApplyMatchOperator;
	}

	public void setFeApplyMatchOperator(Long feApplyMatchOperator) {
		this.feApplyMatchOperator = feApplyMatchOperator;
	}

	public String getFeApplyMatchStatus() {
		return feApplyMatchStatus;
	}

	public void setFeApplyMatchStatus(String feApplyMatchStatus) {
		this.feApplyMatchStatus = feApplyMatchStatus;
	}

	public Date getFeApplyMatchDate() {
		return feApplyMatchDate;
	}

	public void setFeApplyMatchDate(Date feApplyMatchDate) {
		this.feApplyMatchDate = feApplyMatchDate;
	}
	
	

}