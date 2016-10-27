package com.zendaimoney.crm.attachment.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springside.modules.orm.IdEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zendaimoney.crm.file.entity.BusiFile;


/**
 * 附件
 * 
 */
@Entity
@Table(name = "BUSI_ATTACHMENT")
public class BusiAttachment extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = -1100010784620028436L;
	/**
	 * 客户id
	 */
	private Long customerid;
	/**
	 * 附件名称
	 */
	private String atName;

	/**
	 * 附件一级类型
	 */
	private String atTypeOne;

	/**
	 * 附件二级类型
	 */
	private String atType;
	/**
	 * 创建时间
	 */
	private Date atCratetime;
	/**
	 * 创建人
	 */
	private Long atCreateid;
	/**
	 * 最后更新时间
	 */
	private Date atModifytime;
	/**
	 * 最后更新人
	 */
	private Long atModifyid;
	/**
	 * 状态
	 */
	private String atState;
	/**
	 * 备注
	 */
	private String atMemo;
	/**
	 * 文件
	 */
	private Set<BusiFile> busiFiles = new HashSet<BusiFile>();

	private Set<BusiRequestAttach> busiRequestAttachs = new HashSet<BusiRequestAttach>(
			0);

	public BusiAttachment() {
	}

	public BusiAttachment(Long id) {
		this.id = id;
	}

	public Long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}

	public String getAtName() {
		return atName;
	}

	public void setAtName(String atName) {
		this.atName = atName;
	}

	public String getAtTypeOne() {
		return atTypeOne;
	}

	public void setAtTypeOne(String atTypeOne) {
		this.atTypeOne = atTypeOne;
	}

	public String getAtType() {
		return atType;
	}

	public void setAtType(String atType) {
		this.atType = atType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getAtCratetime() {
		return atCratetime;
	}

	public void setAtCratetime(Date atCratetime) {
		this.atCratetime = atCratetime;
	}

	public Long getAtCreateid() {
		return atCreateid;
	}

	public void setAtCreateid(Long atCreateid) {
		this.atCreateid = atCreateid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getAtModifytime() {
		return atModifytime;
	}

	public void setAtModifytime(Date atModifytime) {
		this.atModifytime = atModifytime;
	}

	public Long getAtModifyid() {
		return atModifyid;
	}

	public void setAtModifyid(Long atModifyid) {
		this.atModifyid = atModifyid;
	}

	public String getAtState() {
		return atState;
	}

	public void setAtState(String atState) {
		this.atState = atState;
	}

	public String getAtMemo() {
		return atMemo;
	}

	public void setAtMemo(String atMemo) {
		this.atMemo = atMemo;
	}

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "fAttachment")
	public Set<BusiFile> getBusiFiles() {
		return busiFiles;
	}

	public void setBusiFiles(Set<BusiFile> busiFiles) {
		this.busiFiles = busiFiles;
	}

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "busiAttachment")
	public Set<BusiRequestAttach> getBusiRequestAttachs() {
		return this.busiRequestAttachs;
	}

	public void setBusiRequestAttachs(Set<BusiRequestAttach> busiRequestAttachs) {
		this.busiRequestAttachs = busiRequestAttachs;
	}

	@Override
	public String toString() {
		return "BusiAttachment [customerid=" + customerid + ", atName="
				+ atName + ", atTypeOne=" + atTypeOne + ", atType=" + atType
				+ ", atCratetime=" + atCratetime + ", atCreateid=" + atCreateid
				+ ", atModifytime=" + atModifytime + ", atModifyid="
				+ atModifyid + ", atState=" + atState + ", atMemo=" + atMemo
				+ ", busiFiles=" + busiFiles + ", busiRequestAttachs="
				+ busiRequestAttachs + ", id=" + id + "]";
	}

}