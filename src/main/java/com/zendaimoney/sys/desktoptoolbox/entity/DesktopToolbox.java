package com.zendaimoney.sys.desktoptoolbox.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.springside.modules.orm.IdEntity;

/**
 * 桌面工具箱
 * 
 * @author bianxj
 * 
 */
@Entity
@Table(name = "sys_DesktopToolbox")
public class DesktopToolbox extends IdEntity {

	 /**
	 * 
	 */
	private static final long serialVersionUID = -3827240109964966369L;
	private Long staffId;
	 private String menuCode;
	 
 
	public Long getStaffId() {
		return staffId;
	}
	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	 
	 

}
