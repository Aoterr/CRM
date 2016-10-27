package com.zendaimoney.crm.product.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springside.modules.orm.IdEntity;

@Entity
@Table(name = "BUSI_NOFIX_FINANCE")
public class BusiNofixFinance extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = -2257058771062698841L;
	/**
	 * 客户id
	 */
	private Long customerid;
	/**
	 * 业务id
	 */
	private Long fxBusiId;
	/**
	 * 续投业务新封闭开始日期
	 */
	private Date fxBeginDate;
	/**
	 * 续投业务新封闭结束日期
	 */
	private Date fxEndDate;
	/**
	 * 出借编号
	 */
	private String fxBusiNo;
	/**
	 * 系统录入人
	 */
	private Long fxInputId;
	/**
	 * 录入时间
	 */
	private Date fxInputTime;
	/**
	 * 续投业务原封闭结束日期
	 */
	private Date fxOldEndDate;
	/**
	 * 续投业务签约日期
	 */
	private Date fxSignDate;
	/**
	 * 系统修改人
	 */
	private Long fxModifyId;
	/**
	 * 修改时间
	 */
	private Date fxModifyTime;
	/**
	 * 续期期数
	 */
	private String fxConTerm;
	/**
	 * 续期奖励金率
	 */
	private String fxConRate;
	/**
	 * 续期奖金
	 */
	private String fxConPrice;
	/**
	 * 续期次数
	 */
	private int fxConCount;
	/**
	 * 续期业务状态
	 */
	private String fxState;
	/**
	 * 逻辑删除位
	 */
	private String fxValid;
	
	/**
	 * 合同编号
	 */
	private String fxContractNo;
	
	public Long getCustomerid() {
		return customerid;
	}
	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}
	public Long getFxBusiId() {
		return fxBusiId;
	}
	public void setFxBusiId(Long fxBusiId) {
		this.fxBusiId = fxBusiId;
	}
	public Date getFxBeginDate() {
		return fxBeginDate;
	}
	public void setFxBeginDate(Date fxBeginDate) {
		this.fxBeginDate = fxBeginDate;
	}
	public Date getFxEndDate() {
		return fxEndDate;
	}
	public void setFxEndDate(Date fxEndDate) {
		this.fxEndDate = fxEndDate;
	}
	public String getFxBusiNo() {
		return fxBusiNo;
	}
	public void setFxBusiNo(String fxBusiNo) {
		this.fxBusiNo = fxBusiNo;
	}
	public Long getFxInputId() {
		return fxInputId;
	}
	public void setFxInputId(Long fxInputId) {
		this.fxInputId = fxInputId;
	}
	public Date getFxInputTime() {
		return fxInputTime;
	}
	public void setFxInputTime(Date fxInputTime) {
		this.fxInputTime = fxInputTime;
	}
	public Date getFxOldEndDate() {
		return fxOldEndDate;
	}
	public void setFxOldEndDate(Date fxOldEndDate) {
		this.fxOldEndDate = fxOldEndDate;
	}
	public Date getFxSignDate() {
		return fxSignDate;
	}
	public void setFxSignDate(Date fxSignDate) {
		this.fxSignDate = fxSignDate;
	}
	public Long getFxModifyId() {
		return fxModifyId;
	}
	public void setFxModifyId(Long fxModifyId) {
		this.fxModifyId = fxModifyId;
	}
	public Date getFxModifyTime() {
		return fxModifyTime;
	}
	public void setFxModifyTime(Date fxModifyTime) {
		this.fxModifyTime = fxModifyTime;
	}
	public String getFxConTerm() {
		return fxConTerm;
	}
	public void setFxConTerm(String feConTerm) {
		this.fxConTerm = feConTerm;
	}
	public String getFxConRate() {
		return fxConRate;
	}
	public void setFxConRate(String fxConRate) {
		this.fxConRate = fxConRate;
	}
	public String getFxConPrice() {
		return fxConPrice;
	}
	public void setFxConPrice(String fxConPrice) {
		this.fxConPrice = fxConPrice;
	}
	public int getFxConCount() {
		return fxConCount;
	}
	public void setFxConCount(int fxConCount) {
		this.fxConCount = fxConCount;
	}
	public String getFxState() {
		return fxState;
	}
	public void setFxState(String fxState) {
		this.fxState = fxState;
	}
	public String getFxValid() {
		return fxValid;
	}
	public void setFxValid(String fxValid) {
		this.fxValid = fxValid;
	}
	public String getFxContractNo() {
		return fxContractNo;
	}
	public void setFxContractNo(String fxContractNo) {
		this.fxContractNo = fxContractNo;
	}
	
}