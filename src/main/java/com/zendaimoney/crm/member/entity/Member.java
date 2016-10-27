package com.zendaimoney.crm.member.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springside.modules.orm.IdEntity;

/**
 * 会员列表
 * 
 */
@Entity
@Table(name = "APP_MEMBER")
public class Member extends IdEntity {

	private static final long serialVersionUID = -6274532934869997540L;
	

	
	/**
	 * 会员姓名
	 */
	private String mrName;
	/**
	 * 身份证号码
	 */
	private String mrIdnum;
	/**
	 * 手机号码
	 */
	private String mrMobile;
	/**
	 * 地址
	 */
	private String mrAddr;
	/**
	 * 所在城市
	 */
	private String mrCity;
	/**
	 * 登录名
	 * 
	 */
	private String mrLogin;

	private String mrPassword;
	/**
	 * 客户经理ID
	 */
	private Long mrManager;
	/**
	 * 会员来源（注册、申请...）0：注册，1：申请
	 */
	private String mrSource;
	/**
	 * 状态 1:未分配客户经理 2: 已分配客户经理
	 */
	private String mrMemberState;

	/**
	 * 0-删除 1-正常
	 */
	private String mrState;

	/**
	 * 注册时间
	 */
	private Date mrRegdate;
	/**
	 * 最后修改时间
	 */
	private Date mrModifyTime;
	/**
	 * 备注
	 */
	private String mrMemo;
	/**
	 * 客户Id
	 */
	private Long customerid;
	/**
	 * 客户经理分配类型
	 */
	private String appAssignmentType;
	private String crIdtype; // 证件类型
	
	/**
	 * 客户来源 2：Thump2APP
	 */
	private String customerSource;
	
	/**
	 * 客户信息审核状态 0：未通过1：通过 
	 */
	private String mrInfoState;
	
	/**
	 * 交易密码
	 */
	private String mrTradePassword;
	
	/**
	 * 银行卡Id
	 */
	private Long bankaccountId;
	
	
	/**
	 * pos状态
	 */
	private String  posTicketState;
	
	/**
	 * 签名状态
	 */
	private String  signInfoState;
	
	private String postTicketPath;
	private String signInfoPath;
	
	private Long cardBindTimes;

	private String salt;
	
	
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getSignInfoState() {
		return signInfoState;
	}

	public void setSignInfoState(String signInfoState) {
		this.signInfoState = signInfoState;
	}

	public String getPostTicketPath() {
		return postTicketPath;
	}

	public void setPostTicketPath(String postTicketPath) {
		this.postTicketPath = postTicketPath;
	}

	public String getSignInfoPath() {
		return signInfoPath;
	}

	public void setSignInfoPath(String signInfoPath) {
		this.signInfoPath = signInfoPath;
	}

	public String getPosTicketState() {
		return posTicketState;
	}

	public void setPosTicketState(String posTicketState) {
		this.posTicketState = posTicketState;
	}

	 

	public Long getBankaccountId() {
		return bankaccountId;
	}

	public void setBankaccountId(Long bankaccountId) {
		this.bankaccountId = bankaccountId;
	}

	public String getMrTradePassword() {
		return mrTradePassword;
	}

	public void setMrTradePassword(String mrTradePassword) {
		this.mrTradePassword = mrTradePassword;
	}

	public String getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}

	public String getMrInfoState() {
		return mrInfoState;
	}

	public void setMrInfoState(String mrInfoState) {
		this.mrInfoState = mrInfoState;
	}

	public Long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}

	public String getAppAssignmentType() {
		return appAssignmentType;
	}

	public void setAppAssignmentType(String appAssignmentType) {
		this.appAssignmentType = appAssignmentType;
	}

	public String getMrName() {
		return mrName;
	}

	public void setMrName(String mrName) {
		this.mrName = mrName;
	}

	public String getMrIdnum() {
		return mrIdnum;
	}

	public void setMrIdnum(String mrIdnum) {
		this.mrIdnum = mrIdnum;
	}

	public String getMrMobile() {
		return mrMobile;
	}

	public void setMrMobile(String mrMobile) {
		this.mrMobile = mrMobile;
	}

	public String getMrAddr() {
		return mrAddr;
	}

	public void setMrAddr(String mrAddr) {
		this.mrAddr = mrAddr;
	}

	public String getMrCity() {
		return mrCity;
	}

	public void setMrCity(String mrCity) {
		this.mrCity = mrCity;
	}

	public Long getMrManager() {
		return mrManager;
	}

	public void setMrManager(Long mrManager) {
		this.mrManager = mrManager;
	}

	public String getMrSource() {
		return mrSource;
	}

	public void setMrSource(String mrSource) {
		this.mrSource = mrSource;
	}

	public String getMrMemberState() {
		return mrMemberState;
	}

	public void setMrMemberState(String mrMemberState) {
		this.mrMemberState = mrMemberState;
	}

	public Date getMrRegdate() {
		return mrRegdate;
	}

	public void setMrRegdate(Date mrRegdate) {
		this.mrRegdate = mrRegdate;
	}

	public Date getMrModifyTime() {
		return mrModifyTime;
	}

	public void setMrModifyTime(Date mrModifyTime) {
		this.mrModifyTime = mrModifyTime;
	}

	public String getMrMemo() {
		return mrMemo;
	}

	public void setMrMemo(String mrMemo) {
		this.mrMemo = mrMemo;
	}

	public String getMrLogin() {
		return mrLogin;
	}

	public void setMrLogin(String mrLogin) {
		this.mrLogin = mrLogin;
	}

	public String getMrPassword() {
		return mrPassword;
	}

	public void setMrPassword(String mrPassword) {
		this.mrPassword = mrPassword;
	}

	public String getMrState() {
		return mrState;
	}

	public void setMrState(String mrState) {
		this.mrState = mrState;
	}

	public String getCrIdtype() {
		return crIdtype;
	}

	public void setCrIdtype(String crIdtype) {
		this.crIdtype = crIdtype;
	}

	public Long getCardBindTimes() {
		return cardBindTimes;
	}

	public void setCardBindTimes(Long cardBindTimes) {
		this.cardBindTimes = cardBindTimes;
	}
}
