package com.zendaimoney.crm.customer.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zendaimoney.crm.attachment.entity.BusiRequestAttach;
import com.zendaimoney.crm.contactperson.entiy.Contactperson;
import org.springside.modules.orm.IdEntity;

@Entity
@Table(name = "CRM_CUSTOMER")
public class Customer extends IdEntity {
	private static final long serialVersionUID = 7423118625017090817L;
	private String crCustomerNumber; // 客户编号
	private String crName; // 中文名称
	private String crEname; // 英文名称
	private String crIdtype; // 证件类型
	private String crIdnum; // 证件号码
	private String crReg; // 户口所在地
	private String crNationality; // 国籍
	private String crLanguage; // 使用语言
	private String crMother; // 母亲姓名
	private String crSex; // 性别 1男 ；0女
	private Date crBirthday; // 出生日期
	private Long crCityId; // 城市ID
	private String crEducation; // 最高学历
	private String crMajor; // 主修专业
	private String crOccupation; // 职业
	private String crDuty; // 职务
	private String crTitle; // 职称
	private String crHouse; // 住宅类型
	private String crLive; // 与谁同住
	private Date crEntryDate; // 入职日期
	private String crCoproperty; // 企业性质
	private String crCompany; // 工作单位
	private String crDept; // 部门
	private String crWebsite; // 单位网址
	private String crIndustry; // 所属行业
	private String crEmail; // EMail
	private String crQq; // QQ
	private String crMsn; // MSN
	private String crIncomeSource; // 主要收入来源
	private Double crIncome; // 月收入（元）
	private String crMaritalStatus; // 婚姻状况
	private BigDecimal crChildren; // 子女数目
	private String crHealthStatus; // 健康状况
	private String crCustomerType; // 客户类型
	private String crCustomerLevel; // 客户级别
	private String crSource; // 客户来源
	private Date crGatherDate; // 采集日期
	private Long crGather; // 信息采集人
	private Long crInputId; // 信息录入人
	private Date crInputDate; // 信息录入时间
	private Long crModifyId; // 最后修改人
	private Date crModifyDate; // 最后修改时间
	private String crShare; // 客户共享
	private String crState; // 数据状态
	private String crMemo; // 备注
	private String crMemo2; // 备注2
	private String crCategory; // 客户类别
	private Long crBusiCount; // 业务数
	private Long crWorkYears;// 工作年限
	private Long crCompanySize;// 公司规模
	private String crDependents;// 供养亲属
	private String crJobNature;// 工作性质
	private String crPayday;// 付薪日
	private Double crOtherIncome;// 其他收入
	private Double crBasicSalary;// 基础薪水
	// private Double crHouseRent;//房租
	private Long crCreditCount;// 信用卡数量
	private Double crCreditAmount;// 信用金额
	private Double crCreditOverdraw;// 透支金额
	private Long crDepartment;// 采集人所属部门
	private String crJobNatureOther;// 工作性质 其他

	private Date crIssueDate; // 签发日期
	private Date crExpiryDate; // 失效日期
	private String crFileAccept;// 文件接收方式
	private Long crDeptManager; // 部门经理
	private Date crSignDate;// 客户签署日期
	private Double crYearIncome; // 年收入

	private String crCome;// 来本市年份
	private Double crMonthExpend; // 每月支出
	private String crPayWage;// 工资发放形式
	private Double crRental;// 月租金/还款

	/*
	 * private String crHaveCredit; //是否有信用卡 private String crHaveLoan;//是否有信贷
	 * private String crPriorityContactAddress;//优先联系地址
	 */
	private String crHope; // 客户意愿

	private String crChannel;// 客户渠道通路 2014-9-10

	/**
	 * 客户申请书编号
	 */
	private String crApplicationNumber;
	
	/**
	 * 风险等级
	 * @return
	 */
	private String crRiskLevel;

	public String getCrRiskLevel() {
		return crRiskLevel;
	}

	public void setCrRiskLevel(String crRiskLevel) {
		this.crRiskLevel = crRiskLevel;
	}

	public String getCrChannel() {
		return crChannel;
	}

	public String getCrApplicationNumber() {
		return crApplicationNumber;
	}

	public void setCrApplicationNumber(String crApplicationNumber) {
		this.crApplicationNumber = crApplicationNumber;
	}

	public void setCrChannel(String crChannel) {
		this.crChannel = crChannel;
	}

	public String getCrHope() {
		return crHope;
	}

	public void setCrHope(String crHope) {
		this.crHope = crHope;
	}

	public Double getCrRental() {
		return crRental;
	}

	public void setCrRental(Double crRental) {
		this.crRental = crRental;
	}

	public String getCrPayWage() {
		return crPayWage;
	}

	public void setCrPayWage(String crPayWage) {
		this.crPayWage = crPayWage;
	}

	public Double getCrMonthExpend() {
		return crMonthExpend;
	}

	public void setCrMonthExpend(Double crMonthExpend) {
		this.crMonthExpend = crMonthExpend;
	}

	public String getCrCome() {
		return crCome;
	}

	public void setCrCome(String crCome) {
		this.crCome = crCome;
	}

	private Set<Contactperson> crmContactpersons = new HashSet<Contactperson>(0);
	// private Set<Tel> crmTels = new HashSet<Tel>(0);
	// private Set<Addr> crmAddrs = new HashSet<Addr>(0);
	// private Set<BusiFinance> busiFinances = new HashSet<BusiFinance>(0);
	// private Set<BusiCredit> busiCredits = new HashSet<BusiCredit>(0);
	// private Set<Company> crmCompanies = new HashSet<Company>(0);
	// private Set<Extended> crmExtendeds = new HashSet<Extended>(0);
	private Set<Bankaccount> crmBankaccounts = new HashSet<Bankaccount>(0);

	private Set<BusiRequestAttach> busiRequestAttachs = new HashSet<BusiRequestAttach>();

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "busiCustomer")
	public Set<BusiRequestAttach> getBusiRequestAttachs() {
		return this.busiRequestAttachs;
	}

	public void setBusiRequestAttachs(Set<BusiRequestAttach> busiRequestAttachs) {
		this.busiRequestAttachs = busiRequestAttachs;
	}

	public Customer(Long id) {
		this.id = id;
	}

	public Customer(Long id, Long crGather) {
		this.id = id;
		this.crGather = crGather;
	}

	public Customer() {
	}

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "crmCustomer")
	public Set<Contactperson> getCrmContactpersons() {
		return this.crmContactpersons;
	}

	public void setCrmContactpersons(Set<Contactperson> crmContactpersons) {
		this.crmContactpersons = crmContactpersons;
	}

	// @JsonIgnore
	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =
	// "crmContactperson")
	// public Set<Tel> getCrmTels() {
	// return this.crmTels;
	// }
	//
	// public void setCrmTels(Set<Tel> crmTels) {
	// this.crmTels = crmTels;
	// }
	//
	// @JsonIgnore
	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =
	// "crmContactperson")
	// public Set<Addr> getCrmAddrs() {
	// return this.crmAddrs;
	// }
	//
	// public void setCrmAddrs(Set<Addr> crmAddrs) {
	// this.crmAddrs = crmAddrs;
	// }

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "crmCustomer")
	public Set<Bankaccount> getCrmBankaccounts() {
		return this.crmBankaccounts;
	}

	public void setCrmBankaccounts(Set<Bankaccount> crmBankaccounts) {
		this.crmBankaccounts = crmBankaccounts;
	}

	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =
	// "crmCustomer")
	// public Set<BusiFinance> getBusiFinances() {
	// return this.busiFinances;
	// }
	//
	// public void setBusiFinances(Set<BusiFinance> busiFinances) {
	// this.busiFinances = busiFinances;
	// }
	//
	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =
	// "crmCustomer")
	// public Set<BusiCredit> getBusiCredits() {
	// return this.busiCredits;
	// }
	//
	// public void setBusiCredits(Set<BusiCredit> busiCredits) {
	// this.busiCredits = busiCredits;
	// }
	//
	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =
	// "crmCustomer")
	// public Set<Company> getCrmCompanies() {
	// return this.crmCompanies;
	// }
	//
	// public void setCrmCompanies(SetCompany> crmCompanies) {
	// this.crmCompanies = crmCompanies;
	// }
	//
	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =
	// "crmCustomer")
	// public Set<Extended> getCrmExtendeds() {
	// return this.crmExtendeds;
	// }
	//
	// public void setCrmExtendeds(Set<Extended> crmExtendeds) {
	// this.crmExtendeds = crmExtendeds;
	// }

	@Column(name = "CR_CUSTOMER_NUMBER", nullable = false, length = 40)
	public String getCrCustomerNumber() {
		return this.crCustomerNumber;
	}

	public Date getCrSignDate() {
		return crSignDate;
	}

	public void setCrSignDate(Date crSignDate) {
		this.crSignDate = crSignDate;
	}

	@Temporal(TemporalType.DATE)
	public Date getCrIssueDate() {
		return crIssueDate;
	}

	public void setCrIssueDate(Date crIssueDate) {
		this.crIssueDate = crIssueDate;
	}

	@Temporal(TemporalType.DATE)
	public Date getCrExpiryDate() {
		return crExpiryDate;
	}

	public void setCrExpiryDate(Date crExpiryDate) {
		this.crExpiryDate = crExpiryDate;
	}

	public String getCrFileAccept() {
		return crFileAccept;
	}

	public void setCrFileAccept(String crFileAccept) {
		this.crFileAccept = crFileAccept;
	}

	public Long getCrDeptManager() {
		return crDeptManager;
	}

	public void setCrDeptManager(Long crDeptManager) {
		this.crDeptManager = crDeptManager;
	}

	public Long getCrDepartment() {
		return crDepartment;
	}

	public void setCrDepartment(Long crDepartment) {
		this.crDepartment = crDepartment;
	}

	public String getCrJobNatureOther() {
		return crJobNatureOther;
	}

	public void setCrJobNatureOther(String crJobNatureOther) {
		this.crJobNatureOther = crJobNatureOther;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCrDependents() {
		return crDependents;
	}

	public void setCrDependents(String crDependents) {
		this.crDependents = crDependents;
	}

	public String getCrJobNature() {
		return crJobNature;
	}

	public void setCrJobNature(String crJobNature) {
		this.crJobNature = crJobNature;
	}

	public String getCrPayday() {
		return crPayday;
	}

	public void setCrPayday(String crPayday) {
		this.crPayday = crPayday;
	}

	public Double getCrOtherIncome() {
		return crOtherIncome;
	}

	public void setCrOtherIncome(Double crOtherIncome) {
		this.crOtherIncome = crOtherIncome;
	}

	public Double getCrBasicSalary() {
		return crBasicSalary;
	}

	public void setCrBasicSalary(Double crBasicSalary) {
		this.crBasicSalary = crBasicSalary;
	}

	// public Double getCrHouseRent() {
	// return crHouseRent;
	// }
	//
	// public void setCrHouseRent(Double crHouseRent) {
	// this.crHouseRent = crHouseRent;
	// }

	public Long getCrCreditCount() {
		return crCreditCount;
	}

	public void setCrCreditCount(Long crCreditCount) {
		this.crCreditCount = crCreditCount;
	}

	public Double getCrCreditAmount() {
		return crCreditAmount;
	}

	public void setCrCreditAmount(Double crCreditAmount) {
		this.crCreditAmount = crCreditAmount;
	}

	public Double getCrCreditOverdraw() {
		return crCreditOverdraw;
	}

	public void setCrCreditOverdraw(Double crCreditOverdraw) {
		this.crCreditOverdraw = crCreditOverdraw;
	}

	public Long getCrWorkYears() {
		return crWorkYears;
	}

	public void setCrWorkYears(Long crWorkYears) {
		this.crWorkYears = crWorkYears;
	}

	public Long getCrCompanySize() {
		return crCompanySize;
	}

	public void setCrCompanySize(Long crCompanySize) {
		this.crCompanySize = crCompanySize;
	}

	public void setCrCustomerNumber(String crCustomerNumber) {
		this.crCustomerNumber = crCustomerNumber;
	}

	@Column(name = "CR_NAME", nullable = false, length = 100)
	public String getCrName() {
		return this.crName;
	}

	public void setCrName(String crName) {
		this.crName = crName;
	}

	@Column(name = "CR_ENAME", length = 60)
	public String getCrEname() {
		return this.crEname;
	}

	public void setCrEname(String crEname) {
		this.crEname = crEname;
	}

	@Column(name = "CR_IDTYPE", length = 2)
	public String getCrIdtype() {
		return this.crIdtype;
	}

	public void setCrIdtype(String crIdtype) {
		this.crIdtype = crIdtype;
	}

	@Column(name = "CR_IDNUM", length = 30)
	public String getCrIdnum() {
		return this.crIdnum;
	}

	public void setCrIdnum(String crIdnum) {
		this.crIdnum = crIdnum;
	}

	@Column(name = "CR_REG", length = 60)
	public String getCrReg() {
		return this.crReg;
	}

	public void setCrReg(String crReg) {
		this.crReg = crReg;
	}

	@Column(name = "CR_NATIONALITY", length = 2)
	public String getCrNationality() {
		return this.crNationality;
	}

	public void setCrNationality(String crNationality) {
		this.crNationality = crNationality;
	}

	@Column(name = "CR_LANGUAGE", length = 2)
	public String getCrLanguage() {
		return this.crLanguage;
	}

	public void setCrLanguage(String crLanguage) {
		this.crLanguage = crLanguage;
	}

	@Column(name = "CR_MOTHER", length = 60)
	public String getCrMother() {
		return this.crMother;
	}

	public void setCrMother(String crMother) {
		this.crMother = crMother;
	}

	@Column(name = "CR_SEX", length = 1)
	public String getCrSex() {
		return this.crSex;
	}

	public void setCrSex(String crSex) {
		this.crSex = crSex;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CR_BIRTHDAY", length = 7)
	public Date getCrBirthday() {
		return this.crBirthday;
	}

	public void setCrBirthday(Date crBirthday) {
		this.crBirthday = crBirthday;
	}

	@Column(name = "CR_CITY_ID", scale = 0)
	public Long getCrCityId() {
		return this.crCityId;
	}

	public void setCrCityId(Long crCityId) {
		this.crCityId = crCityId;
	}

	@Column(name = "CR_EDUCATION", length = 2)
	public String getCrEducation() {
		return this.crEducation;
	}

	public void setCrEducation(String crEducation) {
		this.crEducation = crEducation;
	}

	@Column(name = "CR_MAJOR", length = 40)
	public String getCrMajor() {
		return this.crMajor;
	}

	public void setCrMajor(String crMajor) {
		this.crMajor = crMajor;
	}

	@Column(name = "CR_OCCUPATION", length = 2)
	public String getCrOccupation() {
		return this.crOccupation;
	}

	public void setCrOccupation(String crOccupation) {
		this.crOccupation = crOccupation;
	}

	@Column(name = "CR_DUTY", length = 2)
	public String getCrDuty() {
		return this.crDuty;
	}

	public void setCrDuty(String crDuty) {
		this.crDuty = crDuty;
	}

	@Column(name = "CR_TITLE", length = 2)
	public String getCrTitle() {
		return this.crTitle;
	}

	public void setCrTitle(String crTitle) {
		this.crTitle = crTitle;
	}

	@Column(name = "CR_HOUSE", length = 2)
	public String getCrHouse() {
		return this.crHouse;
	}

	public void setCrHouse(String crHouse) {
		this.crHouse = crHouse;
	}

	@Column(name = "CR_LIVE", length = 2)
	public String getCrLive() {
		return this.crLive;
	}

	public void setCrLive(String crLive) {
		this.crLive = crLive;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CR_ENTRY_DATE", length = 7)
	public Date getCrEntryDate() {
		return this.crEntryDate;
	}

	public void setCrEntryDate(Date crEntryDate) {
		this.crEntryDate = crEntryDate;
	}

	@Column(name = "CR_COPROPERTY", length = 2)
	public String getCrCoproperty() {
		return this.crCoproperty;
	}

	public void setCrCoproperty(String crCoproperty) {
		this.crCoproperty = crCoproperty;
	}

	@Column(name = "CR_COMPANY", length = 100)
	public String getCrCompany() {
		return this.crCompany;
	}

	public void setCrCompany(String crCompany) {
		this.crCompany = crCompany;
	}

	@Column(name = "CR_DEPT", length = 40)
	public String getCrDept() {
		return this.crDept;
	}

	public void setCrDept(String crDept) {
		this.crDept = crDept;
	}

	@Column(name = "CR_WEBSITE", length = 60)
	public String getCrWebsite() {
		return this.crWebsite;
	}

	public void setCrWebsite(String crWebsite) {
		this.crWebsite = crWebsite;
	}

	@Column(name = "CR_INDUSTRY", length = 2)
	public String getCrIndustry() {
		return this.crIndustry;
	}

	public void setCrIndustry(String crIndustry) {
		this.crIndustry = crIndustry;
	}

	@Column(name = "CR_EMAIL", length = 60)
	public String getCrEmail() {
		return this.crEmail;
	}

	public void setCrEmail(String crEmail) {
		this.crEmail = crEmail;
	}

	@Column(name = "CR_QQ", length = 20)
	public String getCrQq() {
		return this.crQq;
	}

	public void setCrQq(String crQq) {
		this.crQq = crQq;
	}

	@Column(name = "CR_MSN", length = 40)
	public String getCrMsn() {
		return this.crMsn;
	}

	public void setCrMsn(String crMsn) {
		this.crMsn = crMsn;
	}

	@Column(name = "CR_INCOME_SOURCE", length = 2)
	public String getCrIncomeSource() {
		return this.crIncomeSource;
	}

	public void setCrIncomeSource(String crIncomeSource) {
		this.crIncomeSource = crIncomeSource;
	}

	@Column(name = "CR_INCOME", precision = 17, scale = 7)
	public Double getCrIncome() {
		return this.crIncome;
	}

	public void setCrIncome(Double crIncome) {
		this.crIncome = crIncome;
	}

	@Column(name = "CR_MARITAL_STATUS", length = 2)
	public String getCrMaritalStatus() {
		return this.crMaritalStatus;
	}

	public void setCrMaritalStatus(String crMaritalStatus) {
		this.crMaritalStatus = crMaritalStatus;
	}

	@Column(name = "CR_CHILDREN", precision = 22, scale = 0)
	public BigDecimal getCrChildren() {
		return this.crChildren;
	}

	public void setCrChildren(BigDecimal crChildren) {
		this.crChildren = crChildren;
	}

	@Column(name = "CR_HEALTH_STATUS", length = 2)
	public String getCrHealthStatus() {
		return this.crHealthStatus;
	}

	public void setCrHealthStatus(String crHealthStatus) {
		this.crHealthStatus = crHealthStatus;
	}

	@Column(name = "CR_CUSTOMER_TYPE", length = 2)
	public String getCrCustomerType() {
		return this.crCustomerType;
	}

	public void setCrCustomerType(String crCustomerType) {
		this.crCustomerType = crCustomerType;
	}

	@Column(name = "CR_CUSTOMER_LEVEL", length = 2)
	public String getCrCustomerLevel() {
		return this.crCustomerLevel;
	}

	public void setCrCustomerLevel(String crCustomerLevel) {
		this.crCustomerLevel = crCustomerLevel;
	}

	@Column(name = "CR_SOURCE", length = 2)
	public String getCrSource() {
		return this.crSource;
	}

	public void setCrSource(String crSource) {
		this.crSource = crSource;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CR_GATHER_DATE", length = 7)
	public Date getCrGatherDate() {
		return this.crGatherDate;
	}

	public void setCrGatherDate(Date crGatherDate) {
		this.crGatherDate = crGatherDate;
	}

	@Column(name = "CR_GATHER", scale = 0)
	public Long getCrGather() {
		return this.crGather;
	}

	public void setCrGather(Long crGather) {
		this.crGather = crGather;
	}

	@Column(name = "CR_INPUT_ID", scale = 0)
	public Long getCrInputId() {
		return this.crInputId;
	}

	public void setCrInputId(Long crInputId) {
		this.crInputId = crInputId;
	}

	@Column(name = "CR_INPUT_DATE")
	public Date getCrInputDate() {
		return this.crInputDate;
	}

	public void setCrInputDate(Date crInputDate) {
		this.crInputDate = crInputDate;
	}

	@Column(name = "CR_MODIFY_ID", scale = 0)
	public Long getCrModifyId() {
		return this.crModifyId;
	}

	public void setCrModifyId(Long crModifyId) {
		this.crModifyId = crModifyId;
	}

	@Column(name = "CR_MODIFY_DATE")
	public Date getCrModifyDate() {
		return this.crModifyDate;
	}

	public void setCrModifyDate(Date crModifyDate) {
		this.crModifyDate = crModifyDate;
	}

	@Column(name = "CR_SHARE", length = 1)
	public String getCrShare() {
		return this.crShare;
	}

	public void setCrShare(String crShare) {
		this.crShare = crShare;
	}

	@Column(name = "CR_STATE", length = 2)
	public String getCrState() {
		return this.crState;
	}

	public void setCrState(String crState) {
		this.crState = crState;
	}

	@Column(name = "CR_MEMO", length = 200)
	public String getCrMemo() {
		return this.crMemo;
	}

	public void setCrMemo(String crMemo) {
		this.crMemo = crMemo;
	}

	@Column(name = "CR_MEMO2", length = 200)
	public String getCrMemo2() {
		return this.crMemo2;
	}

	public void setCrMemo2(String crMemo2) {
		this.crMemo2 = crMemo2;
	}

	@Column(name = "CR_CATEGORY", length = 2)
	public String getCrCategory() {
		return this.crCategory;
	}

	public void setCrCategory(String crCategory) {
		this.crCategory = crCategory;
	}

	@Column(name = "CR_BUSI_COUNT", precision = 11, scale = 0)
	public Long getCrBusiCount() {
		return this.crBusiCount;
	}

	public void setCrBusiCount(Long crBusiCount) {
		this.crBusiCount = crBusiCount;
	}

	@Column(name = "CR_YEAR_INCOME", length = 2)
	public Double getCrYearIncome() {
		return crYearIncome;
	}

	public void setCrYearIncome(Double crYearIncome) {
		this.crYearIncome = crYearIncome;
	}

	/*
	 * public String getCrHaveCredit() { return crHaveCredit; }
	 * 
	 * public void setCrHaveCredit(String crHaveCredit) { this.crHaveCredit =
	 * crHaveCredit; }
	 * 
	 * public String getCrHaveLoan() { return crHaveLoan; }
	 * 
	 * public void setCrHaveLoan(String crHaveLoan) { this.crHaveLoan =
	 * crHaveLoan; }
	 * 
	 * public String getCrPriorityContactAddress() { return
	 * crPriorityContactAddress; }
	 * 
	 * public void setCrPriorityContactAddress(String crPriorityContactAddress)
	 * { this.crPriorityContactAddress = crPriorityContactAddress; }
	 */

}