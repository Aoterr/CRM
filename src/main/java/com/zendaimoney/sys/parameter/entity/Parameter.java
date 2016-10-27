package com.zendaimoney.sys.parameter.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * SysParameter entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_PARAMETER")
public class Parameter extends AbstractSysParameter{

	// Constructors

	/** default constructor */
	public Parameter() {
	}

	/** minimal constructor */
	public Parameter(Long id) {
		super(id);
	}

	/** full constructor */
	public Parameter(Long id, String prTypename, String prType,
			String prName, String prValue, String prIsedit, String prState) {
		super(id, prTypename, prType, prName, prValue, prIsedit, prState);
	}

}
