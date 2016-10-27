package com.zendaimoney.crm.customer.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springside.modules.orm.IdEntity;

@Entity
@Table(name = "CRM_COMPATIBLE")
public class Compatible extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8867102166795612570L;

	/**
	 * 客户id
	 */
	private Long Customerid;

	/**
	 * 第三方客户id（E贷）
	 */
	private String Thirdid;

	/**
	 * 平台类型
	 */
	private String Platform;

	/**
	 * 绑定状态 1：绑定中 2：已解绑
	 */
	private String Binding;

	/**
	 * 客户状态 1：生效中 2：已删除
	 */
	private String Status;

	/**
	 * 录入时间
	 */
	private Date CreateTime;

	/**
	 * 录入人
	 */
	private Long CreateId;

	/**
	 * 修改时间
	 */
	private Date ModifyTime;

	/**
	 * 修改人
	 */
	private Long ModifyId;

	/**
	 * 备注
	 */
	private String Remark;

	public Long getCustomerid() {
		return Customerid;
	}

	public void setCustomerid(Long customerid) {
		Customerid = customerid;
	}

	public String getThirdid() {
		return Thirdid;
	}

	public void setThirdid(String thirdid) {
		Thirdid = thirdid;
	}

	public String getPlatform() {
		return Platform;
	}

	public void setPlatform(String platform) {
		Platform = platform;
	}

	public String getBinding() {
		return Binding;
	}

	public void setBinding(String binding) {
		Binding = binding;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public Date getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}

	public Long getCreateId() {
		return CreateId;
	}

	public void setCreateId(Long createId) {
		CreateId = createId;
	}

	public Date getModifyTime() {
		return ModifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		ModifyTime = modifyTime;
	}

	public Long getModifyId() {
		return ModifyId;
	}

	public void setModifyId(Long modifyId) {
		ModifyId = modifyId;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

}
