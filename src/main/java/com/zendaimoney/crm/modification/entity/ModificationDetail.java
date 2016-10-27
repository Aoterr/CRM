package com.zendaimoney.crm.modification.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springside.modules.orm.IdEntity;

@Entity
@Table(name = "CRM_MODIFICATION_DETAIL")
public class ModificationDetail extends IdEntity {
	private static final long serialVersionUID = 4765406696034342322L;
	/**
	 * 字段对象
	 */
	private Field field;
	/**
	 * 变更单对象
	 */
	private Modification modification;
	/**
	 * 变更之前的值
	 */
	private String mlFieldOldValue;
	/**
	 * 变更后的值
	 */
	private String mlFieldNewValue;
	/**
	 * 变更表中对应行的ID
	 */
	private Long sourceId;
	/**
	 * 备注
	 */
	private String mlMemo;

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public String getMlMemo() {
		return mlMemo;
	}

	public void setMlMemo(String mlMemo) {
		this.mlMemo = mlMemo;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FIELD_ID")
	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MODIFICATION_ID")
	public Modification getModification() {
		return modification;
	}

	public void setModification(Modification modification) {
		this.modification = modification;
	}

	public String getMlFieldOldValue() {
		return mlFieldOldValue;
	}

	public void setMlFieldOldValue(String mlFieldOldValue) {
		this.mlFieldOldValue = mlFieldOldValue;
	}

	public String getMlFieldNewValue() {
		return mlFieldNewValue;
	}

	public void setMlFieldNewValue(String mlFieldNewValue) {
		this.mlFieldNewValue = mlFieldNewValue;
	}

	@Override
	public String toString() {
		return "ModificationDetail [field=" + field + ", modification="
				+ modification + ", mlFieldOldValue=" + mlFieldOldValue
				+ ", mlFieldNewValue=" + mlFieldNewValue + ", sourceId="
				+ sourceId + ", id=" + id + "]";
	}

}