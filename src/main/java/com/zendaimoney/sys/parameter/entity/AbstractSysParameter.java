package com.zendaimoney.sys.parameter.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import org.springside.modules.orm.IdEntity;

/**
 * AbstractSysParameter entity provides the base persistence definition of the
 * SysParameter entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractSysParameter extends IdEntity  {

	// Fields

	private String prTypename;
	private String prType;
	private String prName;
	private String prValue;
	private String prIsedit;
	private String prState;

	// Constructors

	/** default constructor */
	public AbstractSysParameter() {
	}

	/** minimal constructor */
	public AbstractSysParameter(Long id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractSysParameter(Long id, String prTypename, String prType,
			String prName, String prValue, String prIsedit, String prState) {
		this.id = id;
		this.prTypename = prTypename;
		this.prType = prType;
		this.prName = prName;
		this.prValue = prValue;
		this.prIsedit = prIsedit;
		this.prState = prState;
	}

	// Property accessors


	@Column(name = "PR_TYPENAME", length = 40)
	public String getPrTypename() {
		return this.prTypename;
	}

	public void setPrTypename(String prTypename) {
		this.prTypename = prTypename;
	}

	@Column(name = "PR_TYPE", length = 20)
	public String getPrType() {
		return this.prType;
	}

	public void setPrType(String prType) {
		this.prType = prType;
	}

	@Column(name = "PR_NAME", length = 40)
	public String getPrName() {
		return this.prName;
	}

	public void setPrName(String prName) {
		this.prName = prName;
	}

	@Column(name = "PR_VALUE", length = 60)
	public String getPrValue() {
		return this.prValue;
	}

	public void setPrValue(String prValue) {
		this.prValue = prValue;
	}

	@Column(name = "PR_ISEDIT", length = 1)
	public String getPrIsedit() {
		return this.prIsedit;
	}

	public void setPrIsedit(String prIsedit) {
		this.prIsedit = prIsedit;
	}

	@Column(name = "PR_STATE", length = 1)
	public String getPrState() {
		return this.prState;
	}

	public void setPrState(String prState) {
		this.prState = prState;
	}

}