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
@Table(name = "BUSI_CREDIT")
public class BusiCredit extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 318378975890447373L;
	private Long customerid;// 客户id
	private String cProduct;// 借款类型
	private Double cAmount;// 申请金额
	private Integer cTerm;// 申请期限
	private Double cRepayMonth;// 月还款能力
	private String cLoanUse;// 借款用途
	private Long cRepaymentAccount;// 还款账户ID
	private Long cLoanAccount;// 借款账户ID
	private String cContact;// 知晓借款的联系人
	private Long cManager;// 客户经理
	private Date cRequestDate;// 申请时间
	private Date cInputTime;// 创建时间
	private Long cInputId;// 创建人
	private String cBusiState;// 业务状态
	private String cState;// 状态
	private String cMemo;// 备注
	private String cResult;// 申请处理结果
	private Date cModifyDate;// 最后修改时间
	private Long cModifyId;// 最后修改人
	// private Long cBusinessId;// 业务编号
	private String cContractNo;// 合同编号
	private String cBorrowNo;// 借款编号
	private Double cMinAmount;// 申请额度（最低）
	private String btUrgent;// 是否加急
	private Double btUrgentAmount;// 加急费
	private Integer btValidate;// 资料验证
	private String cWarranter;// 担保人
	private Set<BusiRequestAttach> busiRequestAttachs = new HashSet<BusiRequestAttach>();

	public BusiCredit() {
	}

	public BusiCredit(Long id) {
		this.id = id;
	}

	public Long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
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

	public Double getcRepayMonth() {
		return cRepayMonth;
	}

	public void setcRepayMonth(Double cRepayMonth) {
		this.cRepayMonth = cRepayMonth;
	}

	public String getcLoanUse() {
		return cLoanUse;
	}

	public void setcLoanUse(String cLoanUse) {
		this.cLoanUse = cLoanUse;
	}

	public Long getcRepaymentAccount() {
		return cRepaymentAccount;
	}

	public void setcRepaymentAccount(Long cRepaymentAccount) {
		this.cRepaymentAccount = cRepaymentAccount;
	}

	public Long getcLoanAccount() {
		return cLoanAccount;
	}

	public void setcLoanAccount(Long cLoanAccount) {
		this.cLoanAccount = cLoanAccount;
	}

	public String getcContact() {
		return cContact;
	}

	public void setcContact(String cContact) {
		this.cContact = cContact;
	}

	public Long getcManager() {
		return cManager;
	}

	public void setcManager(Long cManager) {
		this.cManager = cManager;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getcRequestDate() {
		return cRequestDate;
	}

	public void setcRequestDate(Date cRequestDate) {
		this.cRequestDate = cRequestDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getcInputTime() {
		return cInputTime;
	}

	public void setcInputTime(Date cInputTime) {
		this.cInputTime = cInputTime;
	}

	public Long getcInputId() {
		return cInputId;
	}

	public void setcInputId(Long cInputId) {
		this.cInputId = cInputId;
	}

	public String getcBusiState() {
		return cBusiState;
	}

	public void setcBusiState(String cBusiState) {
		this.cBusiState = cBusiState;
	}

	public String getcState() {
		return cState;
	}

	public void setcState(String cState) {
		this.cState = cState;
	}

	public String getcMemo() {
		return cMemo;
	}

	public void setcMemo(String cMemo) {
		this.cMemo = cMemo;
	}

	public String getcResult() {
		return cResult;
	}

	public void setcResult(String cResult) {
		this.cResult = cResult;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getcModifyDate() {
		return cModifyDate;
	}

	public void setcModifyDate(Date cModifyDate) {
		this.cModifyDate = cModifyDate;
	}

	public Long getcModifyId() {
		return cModifyId;
	}

	public void setcModifyId(Long cModifyId) {
		this.cModifyId = cModifyId;
	}

	// public Long getcBusinessId() {
	// return cBusinessId;
	// }
	//
	// public void setcBusinessId(Long cBusinessId) {
	// this.cBusinessId = cBusinessId;
	// }

	public String getcContractNo() {
		return cContractNo;
	}

	public void setcContractNo(String cContractNo) {
		this.cContractNo = cContractNo;
	}

	public String getcBorrowNo() {
		return cBorrowNo;
	}

	public void setcBorrowNo(String cBorrowNo) {
		this.cBorrowNo = cBorrowNo;
	}

	public Double getcMinAmount() {
		return cMinAmount;
	}

	public void setcMinAmount(Double cMinAmount) {
		this.cMinAmount = cMinAmount;
	}

	public String getBtUrgent() {
		return btUrgent;
	}

	public void setBtUrgent(String btUrgent) {
		this.btUrgent = btUrgent;
	}

	public Double getBtUrgentAmount() {
		return btUrgentAmount;
	}

	public void setBtUrgentAmount(Double btUrgentAmount) {
		this.btUrgentAmount = btUrgentAmount;
	}

	public Integer getBtValidate() {
		return btValidate;
	}

	public void setBtValidate(Integer btValidate) {
		this.btValidate = btValidate;
	}

	public String getcWarranter() {
		return cWarranter;
	}

	public void setcWarranter(String cWarranter) {
		this.cWarranter = cWarranter;
	}

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "busiCredit")
	public Set<BusiRequestAttach> getBusiRequestAttachs() {
		return this.busiRequestAttachs;
	}

	public void setBusiRequestAttachs(Set<BusiRequestAttach> busiRequestAttachs) {
		this.busiRequestAttachs = busiRequestAttachs;
	}

}