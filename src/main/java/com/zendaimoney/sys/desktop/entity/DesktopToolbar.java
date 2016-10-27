package com.zendaimoney.sys.desktop.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springside.modules.orm.IdEntity;

/**
 * 桌面工具栏
 * 
 * @author bianxj
 * 
 */
@Entity
@Table(name = "sys_DesktopToolbar")
public class DesktopToolbar extends IdEntity {

	

	/** 逻辑标示 */
	private String code;
	/** 标题 */
	private String title;

	/** 进入地址 */
	private String address;

	/** 显示顺序 */
	private Integer displaySequence;

	/** 显示开关 */
	private Integer display;//1开2关

	/** 进入地址 */
	private String type;

	public DesktopToolbar() {
	}

	public String getAddress() {
		return address;
	}

	 
	public void setAddress(String address) {
		this.address = address;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	 

	public Integer getDisplaySequence() {
		return displaySequence;
	}

	public void setDisplaySequence(Integer displaySequence) {
		this.displaySequence = displaySequence;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
