package com.zendaimoney.sys.sysparam.entity;

import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springside.modules.orm.IdEntity;


@Entity(name = "Sys_SYS_Param")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysParam extends IdEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4678174067292391252L;
	private String compName;//公司名字
	private String logoPath;//顶部logo
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	
	//短信配置
	private String smsServerName;//短信客户端用户名
	private String smsServerPwd;//短信客户端密码
	private String smsServerIp;//短信客户端服务地址
	private Integer smsServerPort;//短信客户端端口
	
	
	
	//邮件配置
	private String mailHost;//邮件服务器域名或者Ip
	private String mailUsername;//发件人邮箱的用户名
	private String mailPassword;//发件人邮箱的密码
	
	public String getSmsServerName() {
		return smsServerName;
	}
	public void setSmsServerName(String smsServerName) {
		this.smsServerName = smsServerName;
	}
	public String getSmsServerPwd() {
		return smsServerPwd;
	}
	public void setSmsServerPwd(String smsServerPwd) {
		this.smsServerPwd = smsServerPwd;
	}
	public String getSmsServerIp() {
		return smsServerIp;
	}
	public void setSmsServerIp(String smsServerIp) {
		this.smsServerIp = smsServerIp;
	}
	public Integer getSmsServerPort() {
		return smsServerPort;
	}
	public void setSmsServerPort(Integer smsServerPort) {
		this.smsServerPort = smsServerPort;
	}
	public String getMailHost() {
		return mailHost;
	}
	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}
	public String getMailUsername() {
		return mailUsername;
	}
	public void setMailUsername(String mailUsername) {
		this.mailUsername = mailUsername;
	}
	public String getMailPassword() {
		return mailPassword;
	}
	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}
}
