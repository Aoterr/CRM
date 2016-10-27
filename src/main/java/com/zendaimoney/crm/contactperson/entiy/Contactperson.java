package com.zendaimoney.crm.contactperson.entiy;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springside.modules.orm.IdEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zendaimoney.crm.customer.entity.Customer;

/**
 * 
 * 联系人信息
 * 
 */
@Entity
@Table(name = "CRM_CONTACTPERSON")
public class Contactperson extends IdEntity {
	private static final long serialVersionUID = 6679625142553041435L;
	/**
	 * 客户
	 */
	private Customer crmCustomer;
	/**
	 * 姓名
	 */
	private String cpName;
	/**
	 * 英文姓名
	 */
	private String cpEname;
	/**
	 * 性别
	 */
	private String cpSex;
	/**
	 * 出生日期
	 */
	private Date cpBirthday;
	/**
	 * 证件类型
	 */
	private String cpIdtype;
	/**
	 * 证件号码
	 */
	private String cpId;
	/**
	 * 与客户关系
	 */
	private String cpRelation;
	/**
	 * 职业
	 */
	private String cpOccupation;
	/**
	 * 工作单位
	 */
	private String cpWorkunit;
	/**
	 * 工作部门
	 */
	private String cpDepart;
	/**
	 * 职务
	 */
	private String cpPost;
	/**
	 * QQ
	 */
	private String cpQq;
	/**
	 * MSN
	 */
	private String cpMsn;
	/**
	 * EMail
	 */
	private String cpEmail;
	/**
	 * 联系人状态
	 */
	private String cpState;
	/**
	 * 采集日期
	 */
	private Date cpGatherDate;
	/**
	 * 采集人
	 */
	private Long cpGatherId;
	/**
	 * 信息录入人
	 */
	private Long cpInputId;
	/**
	 * 信息录入时间
	 */
	private Date cpInputTime;
	/**
	 * 最后修改人
	 */
	private Long cpModifyId;
	/**
	 * 最后修改时间
	 */
	private Date cpModifyTime;
	/**
	 * 备注
	 */
	private String cpMemo;

	private String cpContactpersonType; // 联系人类型

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CUSTOMERID")
	public Customer getCrmCustomer() {
		return this.crmCustomer;
	}

	public void setCrmCustomer(Customer crmCustomer) {
		this.crmCustomer = crmCustomer;
	}

	@Column(name = "CP_NAME", nullable = false, length = 60)
	public String getCpName() {
		return this.cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	@Column(name = "CP_ENAME", length = 60)
	public String getCpEname() {
		return this.cpEname;
	}

	public void setCpEname(String cpEname) {
		this.cpEname = cpEname;
	}

	@Column(name = "CP_SEX", length = 1)
	public String getCpSex() {
		return this.cpSex;
	}

	public void setCpSex(String cpSex) {
		this.cpSex = cpSex;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CP_BIRTHDAY", length = 7)
	public Date getCpBirthday() {
		return this.cpBirthday;
	}

	public void setCpBirthday(Date cpBirthday) {
		this.cpBirthday = cpBirthday;
	}

	@Column(name = "CP_IDTYPE", length = 2)
	public String getCpIdtype() {
		return this.cpIdtype;
	}

	public void setCpIdtype(String cpIdtype) {
		this.cpIdtype = cpIdtype;
	}

	@Column(name = "CP_ID", length = 40)
	public String getCpId() {
		return this.cpId;
	}

	public void setCpId(String cpId) {
		this.cpId = cpId;
	}

	@Column(name = "CP_RELATION", length = 2)
	public String getCpRelation() {
		return this.cpRelation;
	}

	public void setCpRelation(String cpRelation) {
		this.cpRelation = cpRelation;
	}

	@Column(name = "CP_OCCUPATION", length = 2)
	public String getCpOccupation() {
		return this.cpOccupation;
	}

	public void setCpOccupation(String cpOccupation) {
		this.cpOccupation = cpOccupation;
	}

	@Column(name = "CP_WORKUNIT", length = 100)
	public String getCpWorkunit() {
		return this.cpWorkunit;
	}

	public void setCpWorkunit(String cpWorkunit) {
		this.cpWorkunit = cpWorkunit;
	}

	@Column(name = "CP_DEPART", length = 40)
	public String getCpDepart() {
		return this.cpDepart;
	}

	public void setCpDepart(String cpDepart) {
		this.cpDepart = cpDepart;
	}

	@Column(name = "CP_POST", length = 2)
	public String getCpPost() {
		return this.cpPost;
	}

	public void setCpPost(String cpPost) {
		this.cpPost = cpPost;
	}

	@Column(name = "CP_QQ", length = 20)
	public String getCpQq() {
		return this.cpQq;
	}

	public void setCpQq(String cpQq) {
		this.cpQq = cpQq;
	}

	@Column(name = "CP_MSN", length = 40)
	public String getCpMsn() {
		return this.cpMsn;
	}

	public void setCpMsn(String cpMsn) {
		this.cpMsn = cpMsn;
	}

	@Column(name = "CP_EMAIL", length = 60)
	public String getCpEmail() {
		return this.cpEmail;
	}

	public void setCpEmail(String cpEmail) {
		this.cpEmail = cpEmail;
	}

	@Column(name = "CP_STATE", length = 2)
	public String getCpState() {
		return this.cpState;
	}

	public void setCpState(String cpState) {
		this.cpState = cpState;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CP_GATHER_DATE", length = 7)
	public Date getCpGatherDate() {
		return this.cpGatherDate;
	}

	public void setCpGatherDate(Date cpGatherDate) {
		this.cpGatherDate = cpGatherDate;
	}

	@Column(name = "CP_GATHER_ID", scale = 0)
	public Long getCpGatherId() {
		return this.cpGatherId;
	}

	public void setCpGatherId(Long cpGatherId) {
		this.cpGatherId = cpGatherId;
	}

	@Column(name = "CP_INPUT_ID", scale = 0)
	public Long getCpInputId() {
		return this.cpInputId;
	}

	public void setCpInputId(Long cpInputId) {
		this.cpInputId = cpInputId;
	}

	@Column(name = "CP_INPUT_TIME")
	public Date getCpInputTime() {
		return this.cpInputTime;
	}

	public void setCpInputTime(Date cpInputTime) {
		this.cpInputTime = cpInputTime;
	}

	@Column(name = "CP_MODIFY_ID", scale = 0)
	public Long getCpModifyId() {
		return this.cpModifyId;
	}

	public void setCpModifyId(Long cpModifyId) {
		this.cpModifyId = cpModifyId;
	}

	@Column(name = "CP_MODIFY_TIME")
	public Date getCpModifyTime() {
		return this.cpModifyTime;
	}

	public void setCpModifyTime(Date cpModifyTime) {
		this.cpModifyTime = cpModifyTime;
	}

	@Column(name = "CP_MEMO", length = 200)
	public String getCpMemo() {
		return this.cpMemo;
	}

	public void setCpMemo(String cpMemo) {
		this.cpMemo = cpMemo;
	}

	@Column(name = "CP_CONTACTPERSON_TYPE", length = 2)
	public String getCpContactpersonType() {
		return cpContactpersonType;
	}

	public void setCpContactpersonType(String cpContactpersonType) {
		this.cpContactpersonType = cpContactpersonType;
	}

	@Override
	public String toString() {
		return "Contactperson [crmCustomer=" + crmCustomer + ", cpName="
				+ cpName + ", cpEname=" + cpEname + ", cpSex=" + cpSex
				+ ", cpBirthday=" + cpBirthday + ", cpIdtype=" + cpIdtype
				+ ", cpId=" + cpId + ", cpRelation=" + cpRelation
				+ ", cpOccupation=" + cpOccupation + ", cpWorkunit="
				+ cpWorkunit + ", cpDepart=" + cpDepart + ", cpPost=" + cpPost
				+ ", cpQq=" + cpQq + ", cpMsn=" + cpMsn + ", cpEmail="
				+ cpEmail + ", cpState=" + cpState + ", cpGatherDate="
				+ cpGatherDate + ", cpGatherId=" + cpGatherId + ", cpInputId="
				+ cpInputId + ", cpInputTime=" + cpInputTime + ", cpModifyId="
				+ cpModifyId + ", cpModifyTime=" + cpModifyTime + ", cpMemo="
				+ cpMemo + ", cpContactpersonType=" + cpContactpersonType
				+ ",id=" + id + "]";
	}

}