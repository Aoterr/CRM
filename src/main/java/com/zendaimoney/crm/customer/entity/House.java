package com.zendaimoney.crm.customer.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springside.modules.orm.IdEntity;

/**
 * 房产信息
 * 
 * @author zhanghao
 * @version $Revision:1.0.0, $Date: 2013-6-27 上午10:06:55 $
 */
@Entity
@Table(name = "CRM_HOUSE")
public class House extends IdEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 客户ID
	 */
	private Long customerid;
	/**
	 * 房屋类型
	 */
	private String heType;
	/**
	 * 购买日期
	 */
	private Date heBuyDate;
	/**
	 * 购买价格
	 */
	private Double heBuyprice;
	/**
	 * 建筑面积
	 */
	private Double heBuildingArea;
	/**
	 * 单价
	 */
	private Double heUnitPrice;
	/**
	 * 省
	 */
	private String heProvince;
	/**
	 * 市
	 */
	private String heCity;
	/**
	 * 县
	 */
	private String heCounty;
	/**
	 * 详细地址
	 */
	private String heAddrDetail;
	/**
	 * 邮政编码
	 */
	private String heZipCode;
	/**
	 * 产权比例
	 */
	private Double heRatio;
	/**
	 * 贷款年限
	 */
	private BigDecimal heLoanTerm;
	/**
	 * 月还款金额
	 */
	private Double heMonthRepayment;
	/**
	 * 贷款余额
	 */
	private Double heLoanBalance;
	/**
	 * 租金（月）
	 */
	private Double heHouseRent;
	/**
	 * 是否有房贷
	 */
	private String heHaveLoan;

	@Column(name = "HE_TYPE", length = 2)
	public String getHeType() {
		return this.heType;
	}

	public Long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}

	public void setHeType(String heType) {
		this.heType = heType;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "HE_BUY_DATE", length = 7)
	public Date getHeBuyDate() {
		return this.heBuyDate;
	}

	public void setHeBuyDate(Date heBuyDate) {
		this.heBuyDate = heBuyDate;
	}

	@Column(name = "HE_BUYPRICE", precision = 17, scale = 7)
	public Double getHeBuyprice() {
		return this.heBuyprice;
	}

	public void setHeBuyprice(Double heBuyprice) {
		this.heBuyprice = heBuyprice;
	}

	@Column(name = "HE_BUILDING_AREA", precision = 7)
	public Double getHeBuildingArea() {
		return this.heBuildingArea;
	}

	public void setHeBuildingArea(Double heBuildingArea) {
		this.heBuildingArea = heBuildingArea;
	}

	@Column(name = "HE_UNIT_PRICE", precision = 17, scale = 7)
	public Double getHeUnitPrice() {
		return this.heUnitPrice;
	}

	public void setHeUnitPrice(Double heUnitPrice) {
		this.heUnitPrice = heUnitPrice;
	}

	@Column(name = "HE_PROVINCE", length = 16)
	public String getHeProvince() {
		return this.heProvince;
	}

	public void setHeProvince(String heProvince) {
		this.heProvince = heProvince;
	}

	@Column(name = "HE_CITY", length = 60)
	public String getHeCity() {
		return this.heCity;
	}

	public void setHeCity(String heCity) {
		this.heCity = heCity;
	}

	@Column(name = "HE_COUNTY", length = 60)
	public String getHeCounty() {
		return this.heCounty;
	}

	public void setHeCounty(String heCounty) {
		this.heCounty = heCounty;
	}

	@Column(name = "HE_ADDR_DETAIL", length = 600)
	public String getHeAddrDetail() {
		return this.heAddrDetail;
	}

	public void setHeAddrDetail(String heAddrDetail) {
		this.heAddrDetail = heAddrDetail;
	}

	@Column(name = "HE_ZIP_CODE", precision = 6, scale = 0)
	public String getHeZipCode() {
		return this.heZipCode;
	}

	public void setHeZipCode(String heZipCode) {
		this.heZipCode = heZipCode;
	}

	@Column(name = "HE_RATIO", precision = 7)
	public Double getHeRatio() {
		return this.heRatio;
	}

	public void setHeRatio(Double heRatio) {
		this.heRatio = heRatio;
	}

	@Column(name = "HE_LOAN_TERM", precision = 22, scale = 0)
	public BigDecimal getHeLoanTerm() {
		return this.heLoanTerm;
	}

	public void setHeLoanTerm(BigDecimal heLoanTerm) {
		this.heLoanTerm = heLoanTerm;
	}

	@Column(name = "HE_MONTH_REPAYMENT", precision = 17, scale = 7)
	public Double getHeMonthRepayment() {
		return this.heMonthRepayment;
	}

	public void setHeMonthRepayment(Double heMonthRepayment) {
		this.heMonthRepayment = heMonthRepayment;
	}

	@Column(name = "HE_LOAN_BALANCE", precision = 17, scale = 7)
	public Double getHeLoanBalance() {
		return this.heLoanBalance;
	}

	public void setHeLoanBalance(Double heLoanBalance) {
		this.heLoanBalance = heLoanBalance;
	}

	public Double getHeHouseRent() {
		return heHouseRent;
	}

	public void setHeHouseRent(Double heHouseRent) {
		this.heHouseRent = heHouseRent;
	}

	public String getHeHaveLoan() {
		return heHaveLoan;
	}

	public void setHeHaveLoan(String heHaveLoan) {
		this.heHaveLoan = heHaveLoan;
	}

}