package com.zendaimoney.crm.product.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Subselect;


@Entity
@Subselect("SELECT R.RE_CLOSE_DATE,R.RE_BUSI_NO,F.FE_AMOUNT,F.FE_TIME_INVEST_AMOUNT,R.ID,R.CUSTOMERID,R.RE_INPUT_TIME, R.RE_INPUT_ID,R.RE_STATE, R.Re_Amount,R.RE_RETURN_DATE,C.CR_NAME, F.FE_PRODUCT,C.CR_GATHER  FROM BUSI_REDEEM R, BUSI_FINANCE F, CRM_CUSTOMER C  WHERE R.CUSTOMERID = C.ID  AND R.RE_BUSI_ID = F.ID  AND R.RE_VALID = 1")
public class BusiRedeemView implements Serializable {
	
	private static final long serialVersionUID = 7591627583656732770L;

	/**
	 * 赎回业务id
	 */
	private Long id;
	
	/**
	 * 客户id
	 */
	private Long customerid;
	
	/**
	 * 录入时间
	 */
	private Date reInputTime;
	
	/**
	 * 录入人
	 */
	private Long reInputId;
	
	/**
	 * 赎回状态
	 */
	private String reState;
	
	/**
	 * 转让日债权价值
	 */
	private Double reAmount;
	
	/**
	 *转让日期 
	 */
	private Date reReturnDate;
	
	/**
	 * 客户姓名
	 */
	private String crName;
	
	/**
	 * 理财产品
	 */
	private String feProduct;
	
	/**
	 * 客户经理
	 */
	private String crGather;
	
	/**
	 * 投资金额
	 */
	private Double feAmount;
	
	/**
	 * 定投金额
	 */
	private Double feTimeInvestAmount;// 定投金额
	
	/**
	 * 出借编号
	 */
	private String  reBusiNo;
	
	/**
	 * 封闭期到期日
	 */
	private Date reCloseDate;
	
	public String getFeProduct() {
		return feProduct;
	}
	public void setFeProduct(String feProduct) {
		this.feProduct = feProduct;
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
	public Date getReInputTime() {
		return reInputTime;
	}
	public void setReInputTime(Date reInputTime) {
		this.reInputTime = reInputTime;
	}
	public Long getReInputId() {
		return reInputId;
	}
	public void setReInputId(Long reInputId) {
		this.reInputId = reInputId;
	}
	public String getReState() {
		return reState;
	}
	public void setReState(String reState) {
		this.reState = reState;
	}
	public Double getReAmount() {
		return reAmount;
	}
	public void setReAmount(Double reAmount) {
		this.reAmount = reAmount;
	}
	public Date getReReturnDate() {
		return reReturnDate;
	}
	public void setReReturnDate(Date reReturnDate) {
		this.reReturnDate = reReturnDate;
	}
	public String getCrName() {
		return crName;
	}
	public void setCrName(String crName) {
		this.crName = crName;
	}
	public String getCrGather() {
		return crGather;
	}
	public void setCrGather(String crGather) {
		this.crGather = crGather;
	}
	public Double getFeAmount() {
		return feAmount;
	}
	public void setFeAmount(Double feAmount) {
		this.feAmount = feAmount;
	}
	public Double getFeTimeInvestAmount() {
		return feTimeInvestAmount;
	}
	public void setFeTimeInvestAmount(Double feTimeInvestAmount) {
		this.feTimeInvestAmount = feTimeInvestAmount;
	}
	public String getReBusiNo() {
		return reBusiNo;
	}
	public void setReBusiNo(String reBusiNo) {
		this.reBusiNo = reBusiNo;
	}
	public Date getReCloseDate() {
		return reCloseDate;
	}
	public void setReCloseDate(Date reCloseDate) {
		this.reCloseDate = reCloseDate;
	}
}
