package com.zendaimoney.crm.remind.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springside.modules.orm.IdEntity;

/**
 * 
 * @author bianxj
 * 
 */
@Entity
@Table(name = "crm_Remind")
public class Remind extends IdEntity {

	/** 日常提醒类别 */
	private String dailyRemindType;

	/** 日常提醒内容 */
	private String dailyRemindContent;

	/** 日常提醒链接 */
	private String dailyRemindLink;

	/** 日常提醒备注 */
	private String dailyRemindMemo;

	/** 提醒接受人 */
	private Long dailyRemindUserId;

	public Remind() {
	}

	public Remind(Long id, String dailyRemindType, String dailyRemindContent,
			String dailyRemindLink, String dailyRemindMemo,
			Long dailyRemindUserId) {
		super();
		setId(id);
		this.dailyRemindType = dailyRemindType;
		this.dailyRemindContent = dailyRemindContent;
		this.dailyRemindLink = dailyRemindLink;
		this.dailyRemindMemo = dailyRemindMemo;
		this.dailyRemindUserId = dailyRemindUserId;
	}

	public String getDailyRemindType() {
		return this.dailyRemindType;
	}

	public void setDailyRemindType(String dailyRemindType) {
		this.dailyRemindType = dailyRemindType;
	}

	public String getDailyRemindContent() {
		return this.dailyRemindContent;
	}

	public void setDailyRemindContent(String dailyRemindContent) {
		this.dailyRemindContent = dailyRemindContent;
	}

	public String getDailyRemindLink() {
		return this.dailyRemindLink;
	}

	public void setDailyRemindLink(String dailyRemindLink) {
		this.dailyRemindLink = dailyRemindLink;
	}

	public String getDailyRemindMemo() {
		return this.dailyRemindMemo;
	}

	public void setDailyRemindMemo(String dailyRemindMemo) {
		this.dailyRemindMemo = dailyRemindMemo;
	}

	public Long getDailyRemindUserId() {
		return dailyRemindUserId;
	}

	public void setDailyRemindUserId(Long dailyRemindUserId) {
		this.dailyRemindUserId = dailyRemindUserId;
	}

}