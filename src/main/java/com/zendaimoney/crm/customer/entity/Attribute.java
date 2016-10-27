package com.zendaimoney.crm.customer.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springside.modules.orm.IdEntity;

@Entity
@Table(name = "CRM_MULTIPLE_ATTRIBUTE")
public class Attribute extends IdEntity {

	private static final long serialVersionUID = 6580436985653424236L;

	/**
	 * 客户id
	 */
	private Long customerid;

	/**
	 * 类型
	 */
	private String hType;

	/**
	 * 参数值
	 */
	private String hValue;

	/**
	 * 录入时间
	 */
	private Date hInputTime;

	/**
	 * 录入人
	 */
	private Long hInputId;

	/**
	 * 修改时间
	 */
	private Date hModifyTime;

	/**
	 * 修改人
	 */
	private Long hModifyId;

	/**
	 * 备注
	 */
	private String hMemo;

	public Long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}

	public String gethType() {
		return hType;
	}

	public void sethType(String hType) {
		this.hType = hType;
	}

	public String gethValue() {
		return hValue;
	}

	public void sethValue(String hValue) {
		this.hValue = hValue;
	}

	public Date gethInputTime() {
		return hInputTime;
	}

	public void sethInputTime(Date hInputTime) {
		this.hInputTime = hInputTime;
	}

	public Long gethInputId() {
		return hInputId;
	}

	public void sethInputId(Long hInputId) {
		this.hInputId = hInputId;
	}

	public Date gethModifyTime() {
		return hModifyTime;
	}

	public void sethModifyTime(Date hModifyTime) {
		this.hModifyTime = hModifyTime;
	}

	public Long gethModifyId() {
		return hModifyId;
	}

	public void sethModifyId(Long hModifyId) {
		this.hModifyId = hModifyId;
	}

	public String gethMemo() {
		return hMemo;
	}

	public void sethMemo(String hMemo) {
		this.hMemo = hMemo;
	}

}
