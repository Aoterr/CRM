package com.zendaimoney.crm.complaint.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zendaimoney.uc.rmi.vo.DepartmentType;

public class Department {
	private Long id;
	private String enName;
	private String name;
	private String depCode;
	private Long ctiyId;
	private String depSum;
	private String fullPath;
	private String status;
	private Date createDate;
	private Long createUserId;
	private Long editUserId;
	private Date editDate;
	private String memo;
	private DepartmentType depType;
	private List<Department> children = new ArrayList<Department>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setChildren(List<Department> children) {
		this.children = children;
	}

	public List<Department> getChildren() {
		return children;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getDepCode() {
		return depCode;
	}

	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	public Long getCtiyId() {
		return ctiyId;
	}

	public void setCtiyId(Long ctiyId) {
		this.ctiyId = ctiyId;
	}

	public String getDepSum() {
		return depSum;
	}

	public void setDepSum(String depSum) {
		this.depSum = depSum;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Long getEditUserId() {
		return editUserId;
	}

	public void setEditUserId(Long editUserId) {
		this.editUserId = editUserId;
	}

	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		text = name;
		return text;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public void setDepType(DepartmentType depType) {
		this.depType = depType;
	}

	public DepartmentType getDepType() {
		return depType;
	}

	private String text;
	private String state = "open";
	private boolean checked = false;
	private String iconCls;// 前面的小图标样式
}