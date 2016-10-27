package com.zendaimoney.crm.modification.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springside.modules.orm.IdEntity;

@Entity
@Table(name = "CRM_FIELD")
public class Field extends IdEntity {
	private static final long serialVersionUID = -2734368419608664376L;
	/**
	 * 所属对象（表）
	 */
	private String fdTable;
	/**
	 * 分类名（中文）
	 */
	private String fdClassCn;
	/**
	 * 分类名（英文）
	 */
	private String fdClassEn;
	/**
	 * 字段名（中文）
	 */
	private String fdFieldCn;
	/**
	 * 字段名（英文）
	 */
	private String fdFieldEn;
	/**
	 * 字段长度
	 */
	private Long fdFieldLength;
	/**
	 * 字段类型
	 */
	private String fdFieldType;
	/**
	 * 字段对应HTML的标签类型:select,text,checkbox等
	 */
	private String fdFieldOption;
	/**
	 * 链接地址
	 */
	private String fdUrl;
	/**
	 * 是否可以为空
	 */
	private String fdFieldIsnull;
	/**
	 * 预留字段
	 */
	private String fdReserve;
	/**
	 * （民信，理财）系统字段
	 */
	private Long fdSystem;

	/**
	 * 备注
	 */
	private String fdMemo;
	/**
	 * 备注2
	 */
	private String fdMemo2;

	public String getFdMemo2() {
		return fdMemo2;
	}

	public void setFdMemo2(String fdMemo2) {
		this.fdMemo2 = fdMemo2;
	}

	public Field() {
	}

	public Field(String fdClassCn, String fdClassEn) {
		setFdClassCn(fdClassCn);
		setFdClassEn(fdClassEn);
	}

	public String getFdTable() {
		return fdTable;
	}

	public void setFdTable(String fdTable) {
		this.fdTable = fdTable;
	}

	public String getFdClassCn() {
		return fdClassCn;
	}

	public void setFdClassCn(String fdClassCn) {
		this.fdClassCn = fdClassCn;
	}

	public String getFdClassEn() {
		return fdClassEn;
	}

	public void setFdClassEn(String fdClassEn) {
		this.fdClassEn = fdClassEn;
	}

	public String getFdFieldCn() {
		return fdFieldCn;
	}

	public void setFdFieldCn(String fdFieldCn) {
		this.fdFieldCn = fdFieldCn;
	}

	public String getFdFieldEn() {
		return fdFieldEn;
	}

	public void setFdFieldEn(String fdFieldEn) {
		this.fdFieldEn = fdFieldEn;
	}

	public Long getFdFieldLength() {
		return fdFieldLength;
	}

	public void setFdFieldLength(Long fdFieldLength) {
		this.fdFieldLength = fdFieldLength;
	}

	public String getFdFieldType() {
		return fdFieldType;
	}

	public void setFdFieldType(String fdFieldType) {
		this.fdFieldType = fdFieldType;
	}

	public String getFdFieldOption() {
		return fdFieldOption;
	}

	public void setFdFieldOption(String fdFieldOption) {
		this.fdFieldOption = fdFieldOption;
	}

	public String getFdUrl() {
		return fdUrl;
	}

	public void setFdUrl(String fdUrl) {
		this.fdUrl = fdUrl;
	}

	public String getFdFieldIsnull() {
		return fdFieldIsnull;
	}

	public void setFdFieldIsnull(String fdFieldIsnull) {
		this.fdFieldIsnull = fdFieldIsnull;
	}

	public String getFdReserve() {
		return fdReserve;
	}

	public void setFdReserve(String fdReserve) {
		this.fdReserve = fdReserve;
	}

	public Long getFdSystem() {
		return fdSystem;
	}

	public void setFdSystem(Long fdSystem) {
		this.fdSystem = fdSystem;
	}

	public String getFdMemo() {
		return fdMemo;
	}

	public void setFdMemo(String fdMemo) {
		this.fdMemo = fdMemo;
	}

	@Override
	public String toString() {
		return "Field [fdTable=" + fdTable + ", fdClassCn=" + fdClassCn
				+ ", fdClassEn=" + fdClassEn + ", fdFieldCn=" + fdFieldCn
				+ ", fdFieldEn=" + fdFieldEn + ", fdFieldLength="
				+ fdFieldLength + ", fdFieldType=" + fdFieldType
				+ ", fdFieldOption=" + fdFieldOption + ", fdUrl=" + fdUrl
				+ ", fdFieldIsnull=" + fdFieldIsnull + ", fdReserve="
				+ fdReserve + ", fdMemo=" + fdMemo + ", fdSystem=" + fdSystem
				+ ",id=" + id + "]";
	}

}