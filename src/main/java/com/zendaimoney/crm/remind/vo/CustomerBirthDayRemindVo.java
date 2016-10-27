/** 
 * @(#)CreditView.java 1.0.0 2013-8-20 下午05:01:42  
 *  
 * Copyright © 2013 证大财富.  All rights reserved.  
 */

package com.zendaimoney.crm.remind.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Subselect;

/**
 * 生日提醒视图
 * 
 * @author Yuan Changchun
 * @version $Revision:1.0.0, $Date: 2013-8-20 下午05:01:42 $
 */
@Entity
@Subselect("SELECT T.*,TO_DATE(TO_CHAR(ADD_MONTHS(TO_DATE(TO_CHAR(T.CR_BIRTHDAY,'YYYYMMDD'),'YYYYMMDD'),(EXTRACT(YEAR FROM SYSDATE)-EXTRACT(YEAR FROM T.CR_BIRTHDAY))*12),'YYYYMMDD'),'YYYYMMDD')-TO_DATE(TO_CHAR(SYSDATE,'YYYYMMDD'),'YYYYMMDD') AS BIRTH_REMAIN_DAYS FROM CRM_CUSTOMER T WHERE T.CR_BIRTHDAY IS NOT NULL ")
public class CustomerBirthDayRemindVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1472134688677158491L;

	/**
	 * 客户ID
	 */
	private Long id;

	/**
	 * 客户编号
	 */
	private String crCustomerNumber;
	/**
	 * 中文名称
	 */
	private String crName;
	/**
	 * 英文名称
	 */
	private String crEname;
	/**
	 * 证件类型
	 */
	private String crIdtype;
	/**
	 * 证件号码
	 */
	private String crIdnum;
	/**
	 * 户口所在地
	 */
	private String crReg;
	/**
	 * 国籍
	 */
	private String crNationality;
	/**
	 * 使用语言
	 */
	private String crLanguage;
	/**
	 * 母亲姓名
	 */
	private String crMother;
	/**
	 * 性别 1男 ；0女
	 */
	private String crSex;
	/**
	 * 出生日期
	 */
	private Date crBirthday;
	/**
	 * 城市ID
	 */
	private Long crCityId;
	/**
	 * 最高学历
	 */
	private String crEducation;
	/**
	 * 主修专业
	 */
	private String crMajor;
	/**
	 * 职业
	 */
	private String crOccupation;
	/**
	 * 职务
	 */
	private String crDuty;
	/**
	 * 职称
	 */
	private String crTitle;
	/**
	 * 住宅类型
	 */
	private String crHouse;
	/**
	 * 与谁同住
	 */
	private String crLive;
	/**
	 * 入职日期
	 */
	private Date crEntryDate;
	/**
	 * 企业性质
	 */
	private String crCoproperty;
	/**
	 * 工作单位
	 */
	private String crCompany;
	/**
	 * 部门
	 */
	private String crDept;
	/**
	 * 单位网址
	 */
	private String crWebsite;
	/**
	 * 所属行业
	 */
	private String crIndustry;
	/**
	 * EMail
	 */
	private String crEmail;
	/**
	 * QQ
	 */
	private String crQq;
	/**
	 * MSN
	 */
	private String crMsn;
	/**
	 * 主要收入来源
	 */
	private String crIncomeSource;
	/**
	 * 月收入（元）
	 */
	private Double crIncome;
	/**
	 * 婚姻状况
	 */
	private String crMaritalStatus;
	/**
	 * 子女数目
	 */
	private BigDecimal crChildren;
	/**
	 * 健康状况
	 */
	private String crHealthStatus;
	/**
	 * 客户类型
	 */
	private String crCustomerType;
	/**
	 * 客户级别
	 */
	private String crCustomerLevel;
	/**
	 * 客户来源
	 */
	private String crSource;
	/**
	 * 采集日期
	 */
	private Date crGatherDate;
	/**
	 * 信息采集人
	 */
	private Long crGather;
	/**
	 * 信息录入人
	 */
	private Long crInputId;
	/**
	 * 信息录入时间
	 */
	private Date crInputDate;
	/**
	 * 最后修改人
	 */
	private Long crModifyId;
	/**
	 * 最后修改时间
	 */
	private Date crModifyDate;
	/**
	 * 客户共享
	 */
	private String crShare;
	/**
	 * 数据状态
	 */
	private String crState;
	/**
	 * 备注
	 */
	private String crMemo;
	/**
	 * 备注2
	 */
	private String crMemo2;
	/**
	 * 客户类别
	 */
	private String crCategory;
	/**
	 * 业务数
	 */
	private Long crBusiCount;
	/**
	 * 工作年限
	 */
	private Long crWorkYears;
	/**
	 * 公司规模
	 */
	private Long crCompanySize;
	/**
	 * 供养亲属
	 */
	private String crDependents;
	/**
	 * 工作性质
	 */
	private String crJobNature;
	/**
	 * 付薪日
	 */
	private String crPayday;
	/**
	 * 其他收入
	 */
	private Double crOtherIncome;
	/**
	 * 基础薪水
	 */
	private Double crBasicSalary;
	/**
	 * 信用卡数量
	 */
	private Long crCreditCount;
	/**
	 * 信用金额
	 */
	private Double crCreditAmount;
	/**
	 * 透支金额
	 */
	private Double crCreditOverdraw;
	/**
	 * 采集人所属部门
	 */
	private Long crDepartment;
	/**
	 * 工作性质 其他
	 */
	private String crJobNatureOther;
	/**
	 * 签发日期
	 */
	private Date crIssueDate;
	/**
	 * 失效日期
	 */
	private Date crExpiryDate;
	/**
	 * 文件接收方式
	 */
	private String crFileAccept;
	/**
	 * 部门经理
	 */
	private Long crDeptManager;
	/**
	 * 客户签署日期
	 */
	private Date crSignDate;

	/**
	 * 客户生日提醒剩余天数
	 */
	private Integer birthRemainDays;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the crCustomerNumber
	 */
	@Id
	public String getCrCustomerNumber() {
		return crCustomerNumber;
	}

	/**
	 * @param crCustomerNumber
	 *            the crCustomerNumber to set
	 */
	public void setCrCustomerNumber(String crCustomerNumber) {
		this.crCustomerNumber = crCustomerNumber;
	}

	/**
	 * @return the crName
	 */
	public String getCrName() {
		return crName;
	}

	/**
	 * @param crName
	 *            the crName to set
	 */
	public void setCrName(String crName) {
		this.crName = crName;
	}

	/**
	 * @return the crEname
	 */
	public String getCrEname() {
		return crEname;
	}

	/**
	 * @param crEname
	 *            the crEname to set
	 */
	public void setCrEname(String crEname) {
		this.crEname = crEname;
	}

	/**
	 * @return the crIdtype
	 */
	public String getCrIdtype() {
		return crIdtype;
	}

	/**
	 * @param crIdtype
	 *            the crIdtype to set
	 */
	public void setCrIdtype(String crIdtype) {
		this.crIdtype = crIdtype;
	}

	/**
	 * @return the crIdnum
	 */
	public String getCrIdnum() {
		return crIdnum;
	}

	/**
	 * @param crIdnum
	 *            the crIdnum to set
	 */
	public void setCrIdnum(String crIdnum) {
		this.crIdnum = crIdnum;
	}

	/**
	 * @return the crReg
	 */
	public String getCrReg() {
		return crReg;
	}

	/**
	 * @param crReg
	 *            the crReg to set
	 */
	public void setCrReg(String crReg) {
		this.crReg = crReg;
	}

	/**
	 * @return the crNationality
	 */
	public String getCrNationality() {
		return crNationality;
	}

	/**
	 * @param crNationality
	 *            the crNationality to set
	 */
	public void setCrNationality(String crNationality) {
		this.crNationality = crNationality;
	}

	/**
	 * @return the crLanguage
	 */
	public String getCrLanguage() {
		return crLanguage;
	}

	/**
	 * @param crLanguage
	 *            the crLanguage to set
	 */
	public void setCrLanguage(String crLanguage) {
		this.crLanguage = crLanguage;
	}

	/**
	 * @return the crMother
	 */
	public String getCrMother() {
		return crMother;
	}

	/**
	 * @param crMother
	 *            the crMother to set
	 */
	public void setCrMother(String crMother) {
		this.crMother = crMother;
	}

	/**
	 * @return the crSex
	 */
	public String getCrSex() {
		return crSex;
	}

	/**
	 * @param crSex
	 *            the crSex to set
	 */
	public void setCrSex(String crSex) {
		this.crSex = crSex;
	}

	/**
	 * @return the crBirthday
	 */
	@Temporal(TemporalType.DATE)
	public Date getCrBirthday() {
		return crBirthday;
	}

	/**
	 * @param crBirthday
	 *            the crBirthday to set
	 */
	public void setCrBirthday(Date crBirthday) {
		this.crBirthday = crBirthday;
	}

	/**
	 * @return the crCityId
	 */
	public Long getCrCityId() {
		return crCityId;
	}

	/**
	 * @param crCityId
	 *            the crCityId to set
	 */
	public void setCrCityId(Long crCityId) {
		this.crCityId = crCityId;
	}

	/**
	 * @return the crEducation
	 */
	public String getCrEducation() {
		return crEducation;
	}

	/**
	 * @param crEducation
	 *            the crEducation to set
	 */
	public void setCrEducation(String crEducation) {
		this.crEducation = crEducation;
	}

	/**
	 * @return the crMajor
	 */
	public String getCrMajor() {
		return crMajor;
	}

	/**
	 * @param crMajor
	 *            the crMajor to set
	 */
	public void setCrMajor(String crMajor) {
		this.crMajor = crMajor;
	}

	/**
	 * @return the crOccupation
	 */
	public String getCrOccupation() {
		return crOccupation;
	}

	/**
	 * @param crOccupation
	 *            the crOccupation to set
	 */
	public void setCrOccupation(String crOccupation) {
		this.crOccupation = crOccupation;
	}

	/**
	 * @return the crDuty
	 */
	public String getCrDuty() {
		return crDuty;
	}

	/**
	 * @param crDuty
	 *            the crDuty to set
	 */
	public void setCrDuty(String crDuty) {
		this.crDuty = crDuty;
	}

	/**
	 * @return the crTitle
	 */
	public String getCrTitle() {
		return crTitle;
	}

	/**
	 * @param crTitle
	 *            the crTitle to set
	 */
	public void setCrTitle(String crTitle) {
		this.crTitle = crTitle;
	}

	/**
	 * @return the crHouse
	 */
	public String getCrHouse() {
		return crHouse;
	}

	/**
	 * @param crHouse
	 *            the crHouse to set
	 */
	public void setCrHouse(String crHouse) {
		this.crHouse = crHouse;
	}

	/**
	 * @return the crLive
	 */
	public String getCrLive() {
		return crLive;
	}

	/**
	 * @param crLive
	 *            the crLive to set
	 */
	public void setCrLive(String crLive) {
		this.crLive = crLive;
	}

	/**
	 * @return the crEntryDate
	 */
	public Date getCrEntryDate() {
		return crEntryDate;
	}

	/**
	 * @param crEntryDate
	 *            the crEntryDate to set
	 */
	public void setCrEntryDate(Date crEntryDate) {
		this.crEntryDate = crEntryDate;
	}

	/**
	 * @return the crCoproperty
	 */
	public String getCrCoproperty() {
		return crCoproperty;
	}

	/**
	 * @param crCoproperty
	 *            the crCoproperty to set
	 */
	public void setCrCoproperty(String crCoproperty) {
		this.crCoproperty = crCoproperty;
	}

	/**
	 * @return the crCompany
	 */
	public String getCrCompany() {
		return crCompany;
	}

	/**
	 * @param crCompany
	 *            the crCompany to set
	 */
	public void setCrCompany(String crCompany) {
		this.crCompany = crCompany;
	}

	/**
	 * @return the crDept
	 */
	public String getCrDept() {
		return crDept;
	}

	/**
	 * @param crDept
	 *            the crDept to set
	 */
	public void setCrDept(String crDept) {
		this.crDept = crDept;
	}

	/**
	 * @return the crWebsite
	 */
	public String getCrWebsite() {
		return crWebsite;
	}

	/**
	 * @param crWebsite
	 *            the crWebsite to set
	 */
	public void setCrWebsite(String crWebsite) {
		this.crWebsite = crWebsite;
	}

	/**
	 * @return the crIndustry
	 */
	public String getCrIndustry() {
		return crIndustry;
	}

	/**
	 * @param crIndustry
	 *            the crIndustry to set
	 */
	public void setCrIndustry(String crIndustry) {
		this.crIndustry = crIndustry;
	}

	/**
	 * @return the crEmail
	 */
	public String getCrEmail() {
		return crEmail;
	}

	/**
	 * @param crEmail
	 *            the crEmail to set
	 */
	public void setCrEmail(String crEmail) {
		this.crEmail = crEmail;
	}

	/**
	 * @return the crQq
	 */
	public String getCrQq() {
		return crQq;
	}

	/**
	 * @param crQq
	 *            the crQq to set
	 */
	public void setCrQq(String crQq) {
		this.crQq = crQq;
	}

	/**
	 * @return the crMsn
	 */
	public String getCrMsn() {
		return crMsn;
	}

	/**
	 * @param crMsn
	 *            the crMsn to set
	 */
	public void setCrMsn(String crMsn) {
		this.crMsn = crMsn;
	}

	/**
	 * @return the crIncomeSource
	 */
	public String getCrIncomeSource() {
		return crIncomeSource;
	}

	/**
	 * @param crIncomeSource
	 *            the crIncomeSource to set
	 */
	public void setCrIncomeSource(String crIncomeSource) {
		this.crIncomeSource = crIncomeSource;
	}

	/**
	 * @return the crIncome
	 */
	public Double getCrIncome() {
		return crIncome;
	}

	/**
	 * @param crIncome
	 *            the crIncome to set
	 */
	public void setCrIncome(Double crIncome) {
		this.crIncome = crIncome;
	}

	/**
	 * @return the crMaritalStatus
	 */
	public String getCrMaritalStatus() {
		return crMaritalStatus;
	}

	/**
	 * @param crMaritalStatus
	 *            the crMaritalStatus to set
	 */
	public void setCrMaritalStatus(String crMaritalStatus) {
		this.crMaritalStatus = crMaritalStatus;
	}

	/**
	 * @return the crChildren
	 */
	public BigDecimal getCrChildren() {
		return crChildren;
	}

	/**
	 * @param crChildren
	 *            the crChildren to set
	 */
	public void setCrChildren(BigDecimal crChildren) {
		this.crChildren = crChildren;
	}

	/**
	 * @return the crHealthStatus
	 */
	public String getCrHealthStatus() {
		return crHealthStatus;
	}

	/**
	 * @param crHealthStatus
	 *            the crHealthStatus to set
	 */
	public void setCrHealthStatus(String crHealthStatus) {
		this.crHealthStatus = crHealthStatus;
	}

	/**
	 * @return the crCustomerType
	 */
	public String getCrCustomerType() {
		return crCustomerType;
	}

	/**
	 * @param crCustomerType
	 *            the crCustomerType to set
	 */
	public void setCrCustomerType(String crCustomerType) {
		this.crCustomerType = crCustomerType;
	}

	/**
	 * @return the crCustomerLevel
	 */
	public String getCrCustomerLevel() {
		return crCustomerLevel;
	}

	/**
	 * @param crCustomerLevel
	 *            the crCustomerLevel to set
	 */
	public void setCrCustomerLevel(String crCustomerLevel) {
		this.crCustomerLevel = crCustomerLevel;
	}

	/**
	 * @return the crSource
	 */
	public String getCrSource() {
		return crSource;
	}

	/**
	 * @param crSource
	 *            the crSource to set
	 */
	public void setCrSource(String crSource) {
		this.crSource = crSource;
	}

	/**
	 * @return the crGatherDate
	 */
	@Temporal(TemporalType.DATE)
	public Date getCrGatherDate() {
		return crGatherDate;
	}

	/**
	 * @param crGatherDate
	 *            the crGatherDate to set
	 */
	public void setCrGatherDate(Date crGatherDate) {
		this.crGatherDate = crGatherDate;
	}

	/**
	 * @return the crGather
	 */
	public Long getCrGather() {
		return crGather;
	}

	/**
	 * @param crGather
	 *            the crGather to set
	 */
	public void setCrGather(Long crGather) {
		this.crGather = crGather;
	}

	/**
	 * @return the crInputId
	 */
	public Long getCrInputId() {
		return crInputId;
	}

	/**
	 * @param crInputId
	 *            the crInputId to set
	 */
	public void setCrInputId(Long crInputId) {
		this.crInputId = crInputId;
	}

	/**
	 * @return the crInputDate
	 */
	public Date getCrInputDate() {
		return crInputDate;
	}

	/**
	 * @param crInputDate
	 *            the crInputDate to set
	 */
	public void setCrInputDate(Date crInputDate) {
		this.crInputDate = crInputDate;
	}

	/**
	 * @return the crModifyId
	 */
	public Long getCrModifyId() {
		return crModifyId;
	}

	/**
	 * @param crModifyId
	 *            the crModifyId to set
	 */
	public void setCrModifyId(Long crModifyId) {
		this.crModifyId = crModifyId;
	}

	/**
	 * @return the crModifyDate
	 */
	public Date getCrModifyDate() {
		return crModifyDate;
	}

	/**
	 * @param crModifyDate
	 *            the crModifyDate to set
	 */
	public void setCrModifyDate(Date crModifyDate) {
		this.crModifyDate = crModifyDate;
	}

	/**
	 * @return the crShare
	 */
	public String getCrShare() {
		return crShare;
	}

	/**
	 * @param crShare
	 *            the crShare to set
	 */
	public void setCrShare(String crShare) {
		this.crShare = crShare;
	}

	/**
	 * @return the crState
	 */
	public String getCrState() {
		return crState;
	}

	/**
	 * @param crState
	 *            the crState to set
	 */
	public void setCrState(String crState) {
		this.crState = crState;
	}

	/**
	 * @return the crMemo
	 */
	public String getCrMemo() {
		return crMemo;
	}

	/**
	 * @param crMemo
	 *            the crMemo to set
	 */
	public void setCrMemo(String crMemo) {
		this.crMemo = crMemo;
	}

	/**
	 * @return the crMemo2
	 */
	public String getCrMemo2() {
		return crMemo2;
	}

	/**
	 * @param crMemo2
	 *            the crMemo2 to set
	 */
	public void setCrMemo2(String crMemo2) {
		this.crMemo2 = crMemo2;
	}

	/**
	 * @return the crCategory
	 */
	public String getCrCategory() {
		return crCategory;
	}

	/**
	 * @param crCategory
	 *            the crCategory to set
	 */
	public void setCrCategory(String crCategory) {
		this.crCategory = crCategory;
	}

	/**
	 * @return the crBusiCount
	 */
	public Long getCrBusiCount() {
		return crBusiCount;
	}

	/**
	 * @param crBusiCount
	 *            the crBusiCount to set
	 */
	public void setCrBusiCount(Long crBusiCount) {
		this.crBusiCount = crBusiCount;
	}

	/**
	 * @return the crWorkYears
	 */
	public Long getCrWorkYears() {
		return crWorkYears;
	}

	/**
	 * @param crWorkYears
	 *            the crWorkYears to set
	 */
	public void setCrWorkYears(Long crWorkYears) {
		this.crWorkYears = crWorkYears;
	}

	/**
	 * @return the crCompanySize
	 */
	public Long getCrCompanySize() {
		return crCompanySize;
	}

	/**
	 * @param crCompanySize
	 *            the crCompanySize to set
	 */
	public void setCrCompanySize(Long crCompanySize) {
		this.crCompanySize = crCompanySize;
	}

	/**
	 * @return the crDependents
	 */
	public String getCrDependents() {
		return crDependents;
	}

	/**
	 * @param crDependents
	 *            the crDependents to set
	 */
	public void setCrDependents(String crDependents) {
		this.crDependents = crDependents;
	}

	/**
	 * @return the crJobNature
	 */
	public String getCrJobNature() {
		return crJobNature;
	}

	/**
	 * @param crJobNature
	 *            the crJobNature to set
	 */
	public void setCrJobNature(String crJobNature) {
		this.crJobNature = crJobNature;
	}

	/**
	 * @return the crPayday
	 */
	public String getCrPayday() {
		return crPayday;
	}

	/**
	 * @param crPayday
	 *            the crPayday to set
	 */
	public void setCrPayday(String crPayday) {
		this.crPayday = crPayday;
	}

	/**
	 * @return the crOtherIncome
	 */
	public Double getCrOtherIncome() {
		return crOtherIncome;
	}

	/**
	 * @param crOtherIncome
	 *            the crOtherIncome to set
	 */
	public void setCrOtherIncome(Double crOtherIncome) {
		this.crOtherIncome = crOtherIncome;
	}

	/**
	 * @return the crBasicSalary
	 */
	public Double getCrBasicSalary() {
		return crBasicSalary;
	}

	/**
	 * @param crBasicSalary
	 *            the crBasicSalary to set
	 */
	public void setCrBasicSalary(Double crBasicSalary) {
		this.crBasicSalary = crBasicSalary;
	}

	/**
	 * @return the crCreditCount
	 */
	public Long getCrCreditCount() {
		return crCreditCount;
	}

	/**
	 * @param crCreditCount
	 *            the crCreditCount to set
	 */
	public void setCrCreditCount(Long crCreditCount) {
		this.crCreditCount = crCreditCount;
	}

	/**
	 * @return the crCreditAmount
	 */
	public Double getCrCreditAmount() {
		return crCreditAmount;
	}

	/**
	 * @param crCreditAmount
	 *            the crCreditAmount to set
	 */
	public void setCrCreditAmount(Double crCreditAmount) {
		this.crCreditAmount = crCreditAmount;
	}

	/**
	 * @return the crCreditOverdraw
	 */
	public Double getCrCreditOverdraw() {
		return crCreditOverdraw;
	}

	/**
	 * @param crCreditOverdraw
	 *            the crCreditOverdraw to set
	 */
	public void setCrCreditOverdraw(Double crCreditOverdraw) {
		this.crCreditOverdraw = crCreditOverdraw;
	}

	/**
	 * @return the crDepartment
	 */
	public Long getCrDepartment() {
		return crDepartment;
	}

	/**
	 * @param crDepartment
	 *            the crDepartment to set
	 */
	public void setCrDepartment(Long crDepartment) {
		this.crDepartment = crDepartment;
	}

	/**
	 * @return the crJobNatureOther
	 */
	public String getCrJobNatureOther() {
		return crJobNatureOther;
	}

	/**
	 * @param crJobNatureOther
	 *            the crJobNatureOther to set
	 */
	public void setCrJobNatureOther(String crJobNatureOther) {
		this.crJobNatureOther = crJobNatureOther;
	}

	/**
	 * @return the crIssueDate
	 */
	public Date getCrIssueDate() {
		return crIssueDate;
	}

	/**
	 * @param crIssueDate
	 *            the crIssueDate to set
	 */
	public void setCrIssueDate(Date crIssueDate) {
		this.crIssueDate = crIssueDate;
	}

	/**
	 * @return the crExpiryDate
	 */
	public Date getCrExpiryDate() {
		return crExpiryDate;
	}

	/**
	 * @param crExpiryDate
	 *            the crExpiryDate to set
	 */
	public void setCrExpiryDate(Date crExpiryDate) {
		this.crExpiryDate = crExpiryDate;
	}

	/**
	 * @return the crFileAccept
	 */
	public String getCrFileAccept() {
		return crFileAccept;
	}

	/**
	 * @param crFileAccept
	 *            the crFileAccept to set
	 */
	public void setCrFileAccept(String crFileAccept) {
		this.crFileAccept = crFileAccept;
	}

	/**
	 * @return the crDeptManager
	 */
	public Long getCrDeptManager() {
		return crDeptManager;
	}

	/**
	 * @param crDeptManager
	 *            the crDeptManager to set
	 */
	public void setCrDeptManager(Long crDeptManager) {
		this.crDeptManager = crDeptManager;
	}

	/**
	 * @return the crSignDate
	 */
	public Date getCrSignDate() {
		return crSignDate;
	}

	/**
	 * @param crSignDate
	 *            the crSignDate to set
	 */
	public void setCrSignDate(Date crSignDate) {
		this.crSignDate = crSignDate;
	}

	/**
	 * @return the remainDays
	 */
	public Integer getBirthRemainDays() {
		return birthRemainDays;
	}

	/**
	 * @param remainDays
	 *            the remainDays to set
	 */
	public void setBirthRemainDays(Integer birthRemainDays) {
		this.birthRemainDays = birthRemainDays;
	}

	public CustomerBirthDayRemindVo() {
		super();
		// TODO Auto-generated constructor stub
	}

}
