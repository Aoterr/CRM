package com.zendaimoney.crm.product.entity;

import java.io.Serializable;
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
@Subselect("SELECT V.ID, V.CUSTOMERID,V.FX_BEGIN_DATE,V.FX_END_DATE,V.FX_BUSI_NO,V.FX_INPUT_TIME,V.FX_OLD_END_DATE,V.FX_STATE,V.FX_INPUT_ID,C.CR_NAME,C.CR_GATHER, V.FX_CON_COUNT ,F.FE_AMOUNT,V.FX_SIGN_DATE,V.FX_MODIFY_TIME,F.FE_PRODUCT FROM BUSI_NOFIX_FINANCE V,BUSI_FINANCE F, CRM_CUSTOMER C WHERE V.CUSTOMERID = C.ID AND V.FX_BUSI_ID=F.ID AND V.FX_VALID=1")
public class NoFixFinView implements Serializable {

	private static final long serialVersionUID = 86821485344668720L;
	
	/**
	 * 续投业务id
	 */
	private Long id;
	/**
	 * 客户id
	 */
	private Long customerid;
	/**
	 * 续投业务新封闭开始日期
	 */
	private Date fxBeginDate;
	/**
	 * 续投业务新封闭结束日期
	 */
	private Date fxEndDate;
	/**
	 * 续投业务原封闭结束日期
	 */
	private Date fxOldEndDate;
	/**
	 * 出借编号
	 */
	private String fxBusiNo;
	/**
	 * 状态
	 */
	private Long fxState;
	/**
	 * 系统录入人
	 */
	private Long fxInputId;
	/**
	 * 续期次数
	 */
	private Long fxConCount;
	/**
	 * 客户姓名
	 */
	private String crName;
	/**
	 * 客户经理
	 */
	private String crGather;
	/**
	 * 出借金额
	 */
	private Double feAmount;
	/**
	 * 续投业务签约日期
	 */
	private Date fxSignDate;
	/**
	 * 录入时间
	 */
	private Date fxInputTime;
	/**
	 * 修改时间
	 */
	private Date fxModifyTime;
	/**
	 * 理财产品
	 */
	private String feProduct;
	
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
	public Date getFxOldEndDate() {
		return fxOldEndDate;
	}
	public void setFxOldEndDate(Date fxOldEndDate) {
		this.fxOldEndDate = fxOldEndDate;
	}
	public String getFxBusiNo() {
		return fxBusiNo;
	}
	public void setFxBusiNo(String fxBusiNo) {
		this.fxBusiNo = fxBusiNo;
	}
	public Long getFxState() {
		return fxState;
	}
	public void setFxState(Long fxState) {
		this.fxState = fxState;
	}
	public Long getFxInputId() {
		return fxInputId;
	}
	public void setFxInputId(Long fxInputId) {
		this.fxInputId = fxInputId;
	}
	public Long getFxConCount() {
		return fxConCount;
	}
	public void setFxConCount(Long fxConCount) {
		this.fxConCount = fxConCount;
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
	public Date getFxSignDate() {
		return fxSignDate;
	}
	public void setFxSignDate(Date fxSignDate) {
		this.fxSignDate = fxSignDate;
	}
	public Date getFxInputTime() {
		return fxInputTime;
	}
	public void setFxInputTime(Date fxInputTime) {
		this.fxInputTime = fxInputTime;
	}
	public Date getFxModifyTime() {
		return fxModifyTime;
	}
	public void setFxModifyTime(Date fxModifyTime) {
		this.fxModifyTime = fxModifyTime;
	}
	public String getFeProduct() {
		return feProduct;
	}
	public void setFeProduct(String feProduct) {
		this.feProduct = feProduct;
	}
}
