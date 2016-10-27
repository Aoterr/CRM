/*
* Copyright (c) 2015 zendaimoney.com. All Rights Reserved.
*/
package com.zendaimoney.crm.product.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.springside.modules.orm.IdEntity;

/**
 * BusiFinanceUc.java
 *
 * Author: Yangying
 * Date: 2016年9月12日 上午10:33:54
 * Mail: yangy06@zendaimoney.com
 */

@Entity
@Table(name = "BUSI_FINANCE_UC")
public class BusiFinanceUc extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = -9068160146857916781L;

	private Long fuBusiId;
	private Long fuManagerId;
	private String fuManagerName;
	private String fuFinanceCenter;
	private String fuGroupName;
	private String fuBigTeam;
	private String fuUpperArea;
	private String fuTeamLeder;
	private String fuStatus;
	private String fuMemo;
	private Date fuInputTime;
	private Long fuInputId;
	private Date fuModifyTime;
	private Long fuModifyId;
	
	public Long getFuBusiId() {
		return fuBusiId;
	}
	
	public void setFuBusiId(Long fuBusiId) {
		this.fuBusiId = fuBusiId;
	}
	
	public Long getFuManagerId() {
		return fuManagerId;
	}
	
	public void setFuManagerId(Long fuManagerId) {
		this.fuManagerId = fuManagerId;
	}
	
	public String getFuManagerName() {
		return fuManagerName;
	}
	
	public void setFuManagerName(String fuManagerName) {
		this.fuManagerName = fuManagerName;
	}
	
	public String getFuFinanceCenter() {
		return fuFinanceCenter;
	}
	
	public void setFuFinanceCenter(String fuFinanceCenter) {
		this.fuFinanceCenter = fuFinanceCenter;
	}
	
	public String getFuGroupName() {
		return fuGroupName;
	}
	
	public void setFuGroupName(String fuGroupName) {
		this.fuGroupName = fuGroupName;
	}
	
	public String getFuBigTeam() {
		return fuBigTeam;
	}
	
	public void setFuBigTeam(String fuBigTeam) {
		this.fuBigTeam = fuBigTeam;
	}
	
	public String getFuUpperArea() {
		return fuUpperArea;
	}
	
	public void setFuUpperArea(String fuUpperArea) {
		this.fuUpperArea = fuUpperArea;
	}
	
	public String getFuTeamLeder() {
		return fuTeamLeder;
	}
	
	public void setFuTeamLeder(String fuTeamLeder) {
		this.fuTeamLeder = fuTeamLeder;
	}
	
	public String getFuStatus() {
		return fuStatus;
	}
	
	public void setFuStatus(String fuStatus) {
		this.fuStatus = fuStatus;
	}
	
	public String getFuMemo() {
		return fuMemo;
	}
	
	public void setFuMemo(String fuMemo) {
		this.fuMemo = fuMemo;
	}
	
	public Date getFuInputTime() {
		return fuInputTime;
	}
	
	public void setFuInputTime(Date fuInputTime) {
		this.fuInputTime = fuInputTime;
	}
	
	public Long getFuInputId() {
		return fuInputId;
	}
	
	public void setFuInputId(Long fuInputId) {
		this.fuInputId = fuInputId;
	}
	
	public Date getFuModifyTime() {
		return fuModifyTime;
	}
	
	public void setFuModifyTime(Date fuModifyTime) {
		this.fuModifyTime = fuModifyTime;
	}
	
	public Long getFuModifyId() {
		return fuModifyId;
	}
	
	public void setFuModifyId(Long fuModifyId) {
		this.fuModifyId = fuModifyId;
	}
}
