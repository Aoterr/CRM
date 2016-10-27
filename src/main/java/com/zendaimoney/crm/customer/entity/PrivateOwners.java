package com.zendaimoney.crm.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springside.modules.orm.IdEntity;

/**
 * 私营业主职业信息
 * 
 * @author zhanghao
 * @version $Revision:1.0.0, $Date: 2013-6-27 上午10:06:55 $
 */
@Entity
@Table(name = "CRM_PRIVATE_OWNERS")
public class PrivateOwners extends IdEntity {

	private static final long serialVersionUID = -8112248754598737983L;
	/**
	 * 客户ID
	 */
	private Long customerid;
	/**
	 * 私营企业类型
	 */
	private String poCompanyType;
	/**
	 * 私营企业累类型（其他）
	 */
	private String poCompanyOther;
	/**
	 * 成立时间
	 */
	private Date poCompanyRegtime;
	/**
	 * 占股比列
	 */
	private Double poShareholdingRatio;
	/**
	 * 经营场所
	 */
	private String poRoom;
	/**
	 * 员工认输
	 */
	private Long poCompanySize;
	/**
	 * 企业净利润率
	 */
	private Double poNetIncome;
	/**
	 * 每月净利润率
	 */
	private Double poNetIncomeMonth;

	public Long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}

	@Column(name = "PO_COMPANY_TYPE", length = 2)
	public String getPoCompanyType() {
		return this.poCompanyType;
	}

	public void setPoCompanyType(String poCompanyType) {
		this.poCompanyType = poCompanyType;
	}

	@Column(name = "PO_COMPANY_OTHER", length = 60)
	public String getPoCompanyOther() {
		return this.poCompanyOther;
	}

	public void setPoCompanyOther(String poCompanyOther) {
		this.poCompanyOther = poCompanyOther;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "PO_COMPANY_REGTIME", length = 7)
	public Date getPoCompanyRegtime() {
		return this.poCompanyRegtime;
	}

	public void setPoCompanyRegtime(Date poCompanyRegtime) {
		this.poCompanyRegtime = poCompanyRegtime;
	}

	@Column(name = "PO_SHAREHOLDING_RATIO", precision = 7)
	public Double getPoShareholdingRatio() {
		return this.poShareholdingRatio;
	}

	public void setPoShareholdingRatio(Double poShareholdingRatio) {
		this.poShareholdingRatio = poShareholdingRatio;
	}

	@Column(name = "PO_ROOM", length = 2)
	public String getPoRoom() {
		return this.poRoom;
	}

	public void setPoRoom(String poRoom) {
		this.poRoom = poRoom;
	}

	@Column(name = "PO_COMPANY_SIZE", precision = 11, scale = 0)
	public Long getPoCompanySize() {
		return this.poCompanySize;
	}

	public void setPoCompanySize(Long poCompanySize) {
		this.poCompanySize = poCompanySize;
	}

	@Column(name = "PO_NET_INCOME", precision = 17, scale = 7)
	public Double getPoNetIncome() {
		return this.poNetIncome;
	}

	public void setPoNetIncome(Double poNetIncome) {
		this.poNetIncome = poNetIncome;
	}

	@Column(name = "PO_NET_INCOME_MONTH", precision = 17, scale = 7)
	public Double getPoNetIncomeMonth() {
		return this.poNetIncomeMonth;
	}

	public void setPoNetIncomeMonth(Double poNetIncomeMonth) {
		this.poNetIncomeMonth = poNetIncomeMonth;
	}

}