package com.zendaimoney.crm.product.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.springside.modules.orm.IdEntity;
@Entity
@Table(name = "BUSI_REDEEM")
public class BusiRedeem extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = 8482387946262920942L;
	/**
	 * 客户id
	 */
	private Long customerid;
	
	/**
	 * 业务id
	 */
	private Long reBusiId;
	
	/**
	 * 录入时间
	 */
	private Date reInputTime;
	
	/**
	 * 录入人
	 */
	private Long reInputId;
	
	/**
	 * 修改时间
	 */
	private Date reModifyTime;
	
	/**
	 * 修改人
	 */
	private Long reModifyId;
	
	/**
	 * 赎回状态
	 */
	private String reState;
	
	/**
	 * 转让对价
	 */
	private Double reAmount;
	
	/**
	 * 转让日期
	 */
	private Date reReturnDate;
	
	/**
	 * 备注
	 */
	private String  reMemo;
	
	/**
	 * 逻辑删除位
	 */
	private String reValid;
	
	/**
	 * 保留字段
	 */
	private String reReserve;
	
	/**
	 * 赎回类型（预留字段，以免以后有呵呵哒的需求）
	 */
	private String reType;
	
	/**
	 * 出借编号
	 */
	private String reBusiNo;
	
	/**
	 * 封闭期到期日
	 */
	private Date reCloseDate;

	public Long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}

	public Long getReBusiId() {
		return reBusiId;
	}

	public void setReBusiId(Long reBusiId) {
		this.reBusiId = reBusiId;
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

	public Date getReModifyTime() {
		return reModifyTime;
	}

	public void setReModifyTime(Date reModifyTime) {
		this.reModifyTime = reModifyTime;
	}

	public Long getReModifyId() {
		return reModifyId;
	}

	public void setReModifyId(Long reModifyId) {
		this.reModifyId = reModifyId;
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

	public String getReMemo() {
		return reMemo;
	}

	public void setReMemo(String reMemo) {
		this.reMemo = reMemo;
	}

	public String getReValid() {
		return reValid;
	}

	public void setReValid(String reValid) {
		this.reValid = reValid;
	}

	public String getReReserve() {
		return reReserve;
	}

	public void setReReserve(String reReserve) {
		this.reReserve = reReserve;
	}

	public String getReType() {
		return reType;
	}

	public void setReType(String reType) {
		this.reType = reType;
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