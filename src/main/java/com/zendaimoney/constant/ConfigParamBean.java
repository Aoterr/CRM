package com.zendaimoney.constant;

public class ConfigParamBean {
	
	/**
	 * 验签KEY
	 */
	private String rechargeMd5Key;
	
	/**
	 * 验签开关
	 */
	private String ceValidateSwitch;
	
	/**
	 * 回访任务 开始时间
	 */
	private String callTaskBeginTime;
	
	/**
 * 话务系统默认理财规划师
 */
	private String crManager;
/**
 * 话务系统默认分公司所属地区
 */
	private String crCityId;
	
	/**
	 * 非固续期签约日判断一---- 前置天数
	 */
	private String nofixSigncheckDaybefore;
	
	/**
	 * 非固续期签约日判断二---- 后置天数
	 */
	private String nofixSigncheckDayafter;
	
	/**
	 * 赎回展示日期控制天数
	 */
	private String redeemShowDate;
	
	public String getCrManager() {
	return crManager;
}

public void setCrManager(String crManager) {
	this.crManager = crManager;
}

public String getCrCityId() {
	return crCityId;
}

public void setCrCityId(String crCityId) {
	this.crCityId = crCityId;
}

	public String getRechargeMd5Key() {
		return rechargeMd5Key;
	}

	public void setRechargeMd5Key(String rechargeMd5Key) {
		this.rechargeMd5Key = rechargeMd5Key;
	}

	public String getCeValidateSwitch() {
		return ceValidateSwitch;
	}

	public void setCeValidateSwitch(String ceValidateSwitch) {
		this.ceValidateSwitch = ceValidateSwitch;
	}

	public String getCallTaskBeginTime() {
		return callTaskBeginTime;
	}

	public void setCallTaskBeginTime(String callTaskBeginTime) {
		this.callTaskBeginTime = callTaskBeginTime;
	}

	public String getNofixSigncheckDaybefore() {
		return nofixSigncheckDaybefore;
	}

	public void setNofixSigncheckDaybefore(String nofixSigncheckDaybefore) {
		this.nofixSigncheckDaybefore = nofixSigncheckDaybefore;
	}

	public String getNofixSigncheckDayafter() {
		return nofixSigncheckDayafter;
	}

	public void setNofixSigncheckDayafter(String nofixSigncheckDayafter) {
		this.nofixSigncheckDayafter = nofixSigncheckDayafter;
	}

	public String getRedeemShowDate() {
		return redeemShowDate;
	}

	public void setRedeemShowDate(String redeemShowDate) {
		this.redeemShowDate = redeemShowDate;
	}

}
