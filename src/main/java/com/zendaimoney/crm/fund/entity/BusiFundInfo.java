package com.zendaimoney.crm.fund.entity;

import java.math.BigDecimal;
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
/**
 * 基金业务类
 * @author CJ
 *
 */
@Entity
@Table(name = "BUSI_FUND_INFO")
public class BusiFundInfo extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = -1551107711619778507L;
	private Long customerid;// 客户编号
	private String fdProduct;//基金 理财产品
	private BigDecimal fdAmount;// 认购金额
	private String fdInvestWay;// 资金出借方式
	private Date fdInvestDate;// 预期出借日期
	private Date fdDivestDate;// 计划撤资日期
	private String fdProtocolVersion;// 协议版本
	private String fdRiskCompensation;// 是否风险补偿 1:是 0：否
	private BigDecimal fdTimeInvestAmount;// 定投金额
	private Date fdTimeInvestStart;// 定投生效日期
	private Date fdTimeInvestEnd;// 定投失效日期
	private String fdPaymentWay;// 支付方式
	private Date fdDeductDate;// 划扣日期
	private Long fdDeductAccount;// 划扣账号ID
	private Long fdReturnAccount;// 回款账号ID
	private String fdContinueInvest;// 是否续投
	private String fdContinueProduct;// 续投产品
	private Long fdManager;// 客户经理
	private Date fdRequestDate;// 申请日期
	private Date fdInputTime;// 创建时间
	private Long fdInputId;// 创建人
	private String fdBusiState;// 业务状态
	private String fdState;// 状态
	private String fdMemo;// 备注
	private String fdResult;// 申请处理结果
	private Date fdModifyDate;// 最后修改时间
	private Long fdModifyId;// 最后修改人
	private Double fdManageFee;// 管理费折扣
	private String fdContractNo;// 合同编号
	private String fdLendNo;// 出借编号
	private String fdRemittanceAccount;// 汇入行别
	private Long fdPreviousId;// 上一笔投资id
	private Integer fdDeductCompany;// 划扣公司
	private Double fdInterestRate;//年化利率
	private String fdTerm;//存续期限 月
	private String fdPayStatus;// 付款状态
	private String fdDataOrigin;// 数据来源
	private Date fdPayTime;// 支付时间
	private String fdValidState;//是否有效  1有效
	private String fdPayauditFlag;//划扣标志位


//@Temporal(TemporalType.TIMESTAMP)
	private Set<BusiRequestAttach> busiRequestAttachs = new HashSet<BusiRequestAttach>();// 业务申请附件

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "busiFinance")
	@JsonIgnore
	public Set<BusiRequestAttach> getBusiRequestAttachs() {
		return this.busiRequestAttachs;
	}

	public void setBusiRequestAttachs(Set<BusiRequestAttach> busiRequestAttachs) {
		this.busiRequestAttachs = busiRequestAttachs;
	}

	public Long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}

	public String getFdProduct() {
		return fdProduct;
	}

	public void setFdProduct(String fdProduct) {
		this.fdProduct = fdProduct;
	}

	public BigDecimal getFdAmount() {
		return fdAmount;
	}

	public void setFdAmount(BigDecimal fdAmount) {
		this.fdAmount = fdAmount;
	}

	public String getFdInvestWay() {
		return fdInvestWay;
	}

	public void setFdInvestWay(String fdInvestWay) {
		this.fdInvestWay = fdInvestWay;
	}

	public Date getFdInvestDate() {
		return fdInvestDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public void setFdInvestDate(Date fdInvestDate) {
		this.fdInvestDate = fdInvestDate;
	}

	public Date getFdDivestDate() {
		return fdDivestDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public void setFdDivestDate(Date fdDivestDate) {
		this.fdDivestDate = fdDivestDate;
	}

	public String getFdProtocolVersion() {
		return fdProtocolVersion;
	}

	public void setFdProtocolVersion(String fdProtocolVersion) {
		this.fdProtocolVersion = fdProtocolVersion;
	}

	public String getFdRiskCompensation() {
		return fdRiskCompensation;
	}

	public void setFdRiskCompensation(String fdRiskCompensation) {
		this.fdRiskCompensation = fdRiskCompensation;
	}

	public BigDecimal getFdTimeInvestAmount() {
		return fdTimeInvestAmount;
	}

	public void setFdTimeInvestAmount(BigDecimal fdTimeInvestAmount) {
		this.fdTimeInvestAmount = fdTimeInvestAmount;
	}

	public Date getFdTimeInvestStart() {
		return fdTimeInvestStart;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public void setFdTimeInvestStart(Date fdTimeInvestStart) {
		this.fdTimeInvestStart = fdTimeInvestStart;
	}

	public Date getFdTimeInvestEnd() {
		return fdTimeInvestEnd;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public void setFdTimeInvestEnd(Date fdTimeInvestEnd) {
		this.fdTimeInvestEnd = fdTimeInvestEnd;
	}

	public String getFdPaymentWay() {
		return fdPaymentWay;
	}

	public void setFdPaymentWay(String fdPaymentWay) {
		this.fdPaymentWay = fdPaymentWay;
	}

	public Date getFdDeductDate() {
		return fdDeductDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public void setFdDeductDate(Date fdDeductDate) {
		this.fdDeductDate = fdDeductDate;
	}

	public Long getFdDeductAccount() {
		return fdDeductAccount;
	}

	public void setFdDeductAccount(Long fdDeductAccount) {
		this.fdDeductAccount = fdDeductAccount;
	}

	public Long getFdReturnAccount() {
		return fdReturnAccount;
	}

	public void setFdReturnAccount(Long fdReturnAccount) {
		this.fdReturnAccount = fdReturnAccount;
	}

	public String getFdContinueInvest() {
		return fdContinueInvest;
	}

	public void setFdContinueInvest(String fdContinueInvest) {
		this.fdContinueInvest = fdContinueInvest;
	}

	public String getFdContinueProduct() {
		return fdContinueProduct;
	}

	public void setFdContinueProduct(String fdContinueProduct) {
		this.fdContinueProduct = fdContinueProduct;
	}

	public Long getFdManager() {
		return fdManager;
	}

	public void setFdManager(Long fdManager) {
		this.fdManager = fdManager;
	}

	public Date getFdRequestDate() {
		return fdRequestDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public void setFdRequestDate(Date fdRequestDate) {
		this.fdRequestDate = fdRequestDate;
	}

	public Date getFdInputTime() {
		return fdInputTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public void setFdInputTime(Date fdInputTime) {
		this.fdInputTime = fdInputTime;
	}

	public Long getFdInputId() {
		return fdInputId;
	}

	public void setFdInputId(Long fdInputId) {
		this.fdInputId = fdInputId;
	}

	public String getFdBusiState() {
		return fdBusiState;
	}

	public void setFdBusiState(String fdBusiState) {
		this.fdBusiState = fdBusiState;
	}

	public String getFdState() {
		return fdState;
	}

	public void setFdState(String fdState) {
		this.fdState = fdState;
	}

	public String getFdMemo() {
		return fdMemo;
	}

	public void setFdMemo(String fdMemo) {
		this.fdMemo = fdMemo;
	}

	public String getFdResult() {
		return fdResult;
	}

	public void setFdResult(String fdResult) {
		this.fdResult = fdResult;
	}

	public Date getFdModifyDate() {
		return fdModifyDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public void setFdModifyDate(Date fdModifyDate) {
		this.fdModifyDate = fdModifyDate;
	}

	public Long getFdModifyId() {
		return fdModifyId;
	}

	public void setFdModifyId(Long fdModifyId) {
		this.fdModifyId = fdModifyId;
	}

	public Double getFdManageFee() {
		return fdManageFee;
	}

	public void setFdManageFee(Double fdManageFee) {
		this.fdManageFee = fdManageFee;
	}

	public String getFdContractNo() {
		return fdContractNo;
	}

	public void setFdContractNo(String fdContractNo) {
		this.fdContractNo = fdContractNo;
	}

	public String getFdLendNo() {
		return fdLendNo;
	}

	public void setFdLendNo(String fdLendNo) {
		this.fdLendNo = fdLendNo;
	}

	public String getFdRemittanceAccount() {
		return fdRemittanceAccount;
	}

	public void setFdRemittanceAccount(String fdRemittanceAccount) {
		this.fdRemittanceAccount = fdRemittanceAccount;
	}

	public Long getFdPreviousId() {
		return fdPreviousId;
	}

	public void setFdPreviousId(Long fdPreviousId) {
		this.fdPreviousId = fdPreviousId;
	}

	public Integer getFdDeductCompany() {
		return fdDeductCompany;
	}

	public void setFdDeductCompany(Integer fdDeductCompany) {
		this.fdDeductCompany = fdDeductCompany;
	}

	public Double getFdInterestRate() {
		return fdInterestRate;
	}

	public void setFdInterestRate(Double fdInterestRate) {
		this.fdInterestRate = fdInterestRate;
	}

	public String getFdTerm() {
		return fdTerm;
	}

	public void setFdTerm(String fdTerm) {
		this.fdTerm = fdTerm;
	}

	public String getFdPayStatus() {
		return fdPayStatus;
	}

	public void setFdPayStatus(String fdPayStatus) {
		this.fdPayStatus = fdPayStatus;
	}

	public String getFdDataOrigin() {
		return fdDataOrigin;
	}

	public void setFdDataOrigin(String fdDataOrigin) {
		this.fdDataOrigin = fdDataOrigin;
	}

	public Date getFdPayTime() {
		return fdPayTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public void setFdPayTime(Date fdPayTime) {
		this.fdPayTime = fdPayTime;
	}

	public String getFdValidState() {
		return fdValidState;
	}

	public void setFdValidState(String fdValidState) {
		this.fdValidState = fdValidState;
	}

	public String getFdPayauditFlag() {
		return fdPayauditFlag;
	}

	public void setFdPayauditFlag(String fdPayauditFlag) {
		this.fdPayauditFlag = fdPayauditFlag;
	}

	@Override
	public String toString() {
		return "BusiFundInfo [customerid=" + customerid + ", fdProduct=" + fdProduct + ", fdAmount="
				+ fdAmount + ", fdInvestWay=" + fdInvestWay + ", fdInvestDate="
				+ fdInvestDate + ", fdDivestDate=" + fdDivestDate
				+ ", fdProtocolVersion=" + fdProtocolVersion + ", fdRiskCompensation=" + fdRiskCompensation
				+ ", fdTimeInvestAmount=" + fdTimeInvestAmount
				+ ", fdTimeInvestStart=" + fdTimeInvestStart
				+ ", fdTimeInvestEnd=" + fdTimeInvestEnd + ", fdPaymentWay="
				+ fdPaymentWay + ", fdDeductDate=" + fdDeductDate
				+ ", fdDeductAccount=" + fdDeductAccount + ", fdReturnAccount="
				+ fdReturnAccount + ", fdContinueInvest=" + fdContinueInvest
				+ ", fdContinueProduct=" + fdContinueProduct + ", fdManager="
				+ fdManager + ", fdRequestDate=" + fdRequestDate
				+ ", fdInputTime=" + fdInputTime + ", fdInputId=" + fdInputId
				+ ", fdBusiState=" + fdBusiState + ", fdState=" + fdState
				+ ", fdMemo=" + fdMemo + ", fdResult=" + fdResult
				+ ", fdModifyDate=" + fdModifyDate + ", fdModifyId="
				+ fdModifyId + ", fdManageFee=" + fdManageFee
				+ ", fdContractNo=" + fdContractNo + ", fdLendNo=" + fdLendNo
				+ ", fdRemittanceAccount=" + fdRemittanceAccount
				+ ", fdPreviousId=" + fdPreviousId + ", busiRequestAttachs="
				+ busiRequestAttachs + ", id=" + id + "]";
	}
}