package com.zendaimoney.crm.remind.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springside.modules.orm.IdEntity;

/**
 * 
 * @author Yuan Changchun
 * 
 */
@Entity
@Table(name = "crm_remind_customer")
public class RemindCustomer extends IdEntity {

	/** 日常提醒ID */
	private Long remindId;

	/** 关联的客户信息ID */
	private Long customerId;

	public RemindCustomer() {
	}

	public RemindCustomer(Long remindId, Long customerId) {
		super();
		setId(id);
		this.remindId = remindId;
		this.customerId = customerId;
	}

	public RemindCustomer(Long id, Long remindId, Long customerId) {
		super();
		setId(id);
		this.remindId = remindId;
		this.customerId = customerId;
	}

	/**
	 * @return the remindId
	 */
	public Long getRemindId() {
		return remindId;
	}

	/**
	 * @param remindId
	 *            the remindId to set
	 */
	public void setRemindId(Long remindId) {
		this.remindId = remindId;
	}

	/**
	 * @return the customerId
	 */
	public Long getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

}
