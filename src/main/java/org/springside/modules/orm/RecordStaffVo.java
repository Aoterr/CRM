package org.springside.modules.orm;


import java.util.Date;

public class RecordStaffVo{
	
	private static final long serialVersionUID = 2433461464054606867L;
	
	/** 员工Id */
    private Long staffId;
    /** 员工姓名 */
    private String staffName;
    /** 部门(团队)Id */
    private Long depId;
    /** 部门(团队)名称 */
    private String depName;
    /** 父级部门Id */
    private Long parentDepId;
    /** 父级部门名称 */
    private String parentDepName;
    /** 大团队Id*/
    private Long bigTeamId;
    /** 大团队名称 */
    private String bigTeamName;
    /** 大区Id*/
    private Long regionId;
    /** 大区名称 */
    private String regionName;
    /** 父级职员Id*/
    private Long parentStaffId;
    /** 父级职员姓名 */
    private String parentStaffName;

    
    /**
     * 业务ＩＤ
     */
    private Long busiId;
    
    /**
     * CRM业务状态
     */
    private String status;
    
    /**
     * forture返回的处理结果信息
     */
    private String memo;
    
    /** 开始时间 */
    private Date startDate;
    
    /** 修改时间 */
    private Date endDate;
    
    /**
     * 财富拓展id
     */
    private Long extDataId;

	public Long getExtDataId() {
		return extDataId;
	}

	public void setExtDataId(Long extDataId) {
		this.extDataId = extDataId;
	}

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Long getDepId() {
		return depId;
	}

	public void setDepId(Long depId) {
		this.depId = depId;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public Long getParentDepId() {
		return parentDepId;
	}

	public void setParentDepId(Long parentDepId) {
		this.parentDepId = parentDepId;
	}

	public String getParentDepName() {
		return parentDepName;
	}

	public void setParentDepName(String parentDepName) {
		this.parentDepName = parentDepName;
	}

	public Long getBigTeamId() {
		return bigTeamId;
	}

	public void setBigTeamId(Long bigTeamId) {
		this.bigTeamId = bigTeamId;
	}

	public String getBigTeamName() {
		return bigTeamName;
	}

	public void setBigTeamName(String bigTeamName) {
		this.bigTeamName = bigTeamName;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Long getParentStaffId() {
		return parentStaffId;
	}

	public void setParentStaffId(Long parentStaffId) {
		this.parentStaffId = parentStaffId;
	}

	public String getParentStaffName() {
		return parentStaffName;
	}

	public void setParentStaffName(String parentStaffName) {
		this.parentStaffName = parentStaffName;
	}

	public Long getBusiId() {
		return busiId;
	}

	public void setBusiId(Long busiId) {
		this.busiId = busiId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

    
    

}
