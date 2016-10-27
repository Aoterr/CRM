package com.zendaimoney.sys.logging.entity;

import java.util.Date;

import javax.persistence.Entity;

import org.springside.modules.orm.IdEntity;

import com.zendaimoney.constant.LoggingSource;
import com.zendaimoney.constant.LoggingType;

@Entity(name = "sys_log")
public class Logging extends IdEntity {

	
	
	private Long lgOperatorid;//操作用户id
	
	private String lgOperatorname;//操作用户
	
	private Date lgLogtime;//operDate;//操作时间
	
	private String lgIp;//operIp;//操作者ip
	
	private LoggingType lgLogtype;//operType;//操作类型
	
	private String lgContent;//operContent;//内容
	
	private LoggingSource lgSource;//operSource;//操作来源
	
	private String lgMemo;//remark; //备注



	public Long getLgOperatorid() {
		return lgOperatorid;
	}

	public void setLgOperatorid(Long lgOperatorid) {
		this.lgOperatorid = lgOperatorid;
	}

	public String getLgOperatorname() {
		return lgOperatorname;
	}

	public void setLgOperatorname(String lgOperatorname) {
		this.lgOperatorname = lgOperatorname;
	}

	public Date getLgLogtime() {
		return lgLogtime;
	}

	public void setLgLogtime(Date lgLogtime) {
		this.lgLogtime = lgLogtime;
	}

	public String getLgIp() {
		return lgIp;
	}

	public void setLgIp(String lgIp) {
		this.lgIp = lgIp;
	}

	public LoggingType getLgLogtype() {
		return lgLogtype;
	}

	public void setLgLogtype(LoggingType lgLogtype) {
		this.lgLogtype = lgLogtype;
	}

	public String getLgContent() {
		return lgContent;
	}

	public void setLgContent(String lgContent) {
		this.lgContent = lgContent;
	}

	public LoggingSource getLgSource() {
		return lgSource;
	}

	public void setLgSource(LoggingSource lgSource) {
		this.lgSource = lgSource;
	}

	public String getLgMemo() {
		return lgMemo;
	}

	public void setLgMemo(String lgMemo) {
		this.lgMemo = lgMemo;
	}
	
	
	


}