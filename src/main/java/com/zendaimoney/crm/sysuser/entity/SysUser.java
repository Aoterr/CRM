package com.zendaimoney.crm.sysuser.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springside.modules.orm.IdEntity;

@Entity
@Table(name = "SYS_USER")
public class SysUser extends IdEntity {

	private static final long serialVersionUID = -7978278754666055359L;

	private String userName;// 用户名

	private String userPassword;// 密码

	private Date lastLoginTime;// 最后登录时间
	
	private int userLevel;//用户级别 1普通用户 2系统管理员
	
	private int state;//1正常 2冻结 3 删除
	
	private String remark;// remark; //备注

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}	

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public int getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}