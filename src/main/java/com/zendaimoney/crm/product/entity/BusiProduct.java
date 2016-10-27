package com.zendaimoney.crm.product.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springside.modules.orm.IdEntity;

/**
 * 产品
 * 
 * @author zhanghao
 * @create 2012-11-29, 上午10:03:55
 * @version 1.0
 */
@Entity
@Table(name = "BUSI_PRODUCT")
public class BusiProduct extends IdEntity implements Serializable {
	private static final long serialVersionUID = 6085721746155672550L;
	private String ptProductName;// 产品名称
	private String ptProductCode;// 产品代码
	private String ptProductType;// 产品类型
	private Long ptMinTerm;// 产品最小期数
	private Long ptMaxTerm;// 产品最大期数
	private BigDecimal ptMinAmount;// 最小金额
	private BigDecimal ptMaxAmount;// 最大金额
	private String ptValid;// 是否有效
	private Date ptInputDate;// 输入时间
	private Long ptInputId;// 输入人
	private Date ptModifyTime;// 最后修改时间
	private Long ptModifyId;// 最后修改人

	// ---add by Sam.J 14.11.13--//
	private Double ptMinYield; // 最低收益率

	private Double ptMaxYield; // 最高收益率（固定收益产品取最高收益率）

	private Long ptAvailableNum; // 可购买数量

	private String ptBuyFlag; // 是否可购买标志位

	private String ptIsDel; // 是否删除标志位

	private String ptStarttimeMemo; // 起息时间说明

	private String ptEndtimeMemo; // 到期时间说明

	private String ptMemo1; // 备用字段1

	private String ptMemo2; // 备用字段2

	private String ptMemo3; // 备用字段3

	// ---add end--//
	
	//add by chenjiale 2016/5/4 thumb2.0 start
//	private Long id;// 产品ID
	//add by chenjiale 2016/5/4 thumb2.0 end

	public BusiProduct() {
	}

//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}

	public String getPtProductName() {
		return ptProductName;
	}

	public void setPtProductName(String ptProductName) {
		this.ptProductName = ptProductName;
	}

	public String getPtProductCode() {
		return ptProductCode;
	}

	public void setPtProductCode(String ptProductCode) {
		this.ptProductCode = ptProductCode;
	}

	public String getPtProductType() {
		return ptProductType;
	}

	public void setPtProductType(String ptProductType) {
		this.ptProductType = ptProductType;
	}

	public Long getPtMinTerm() {
		return ptMinTerm;
	}

	public void setPtMinTerm(Long ptMinTerm) {
		this.ptMinTerm = ptMinTerm;
	}

	public Long getPtMaxTerm() {
		return ptMaxTerm;
	}

	public void setPtMaxTerm(Long ptMaxTerm) {
		this.ptMaxTerm = ptMaxTerm;
	}

	public BigDecimal getPtMinAmount() {
		return ptMinAmount;
	}

	public void setPtMinAmount(BigDecimal ptMinAmount) {
		this.ptMinAmount = ptMinAmount;
	}

	public BigDecimal getPtMaxAmount() {
		return ptMaxAmount;
	}

	public void setPtMaxAmount(BigDecimal ptMaxAmount) {
		this.ptMaxAmount = ptMaxAmount;
	}

	public String getPtValid() {
		return ptValid;
	}

	public void setPtValid(String ptValid) {
		this.ptValid = ptValid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getPtInputDate() {
		return ptInputDate;
	}

	public void setPtInputDate(Date ptInputDate) {
		this.ptInputDate = ptInputDate;
	}

	public Long getPtInputId() {
		return ptInputId;
	}

	public void setPtInputId(Long ptInputId) {
		this.ptInputId = ptInputId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getPtModifyTime() {
		return ptModifyTime;
	}

	public void setPtModifyTime(Date ptModifyTime) {
		this.ptModifyTime = ptModifyTime;
	}

	public Long getPtModifyId() {
		return ptModifyId;
	}

	public void setPtModifyId(Long ptModifyId) {
		this.ptModifyId = ptModifyId;
	}

	public Double getPtMinYield() {
		return ptMinYield;
	}

	public void setPtMinYield(Double ptMinYield) {
		this.ptMinYield = ptMinYield;
	}

	public Double getPtMaxYield() {
		return ptMaxYield;
	}

	public void setPtMaxYield(Double ptMaxYield) {
		this.ptMaxYield = ptMaxYield;
	}

	public Long getPtAvailableNum() {
		return ptAvailableNum;
	}

	public void setPtAvailableNum(Long ptAvailableNum) {
		this.ptAvailableNum = ptAvailableNum;
	}

	public String getPtIsDel() {
		return ptIsDel;
	}

	public void setPtIsDel(String ptIsDel) {
		this.ptIsDel = ptIsDel;
	}

	public String getPtMemo1() {
		return ptMemo1;
	}

	public void setPtMemo1(String ptMemo1) {
		this.ptMemo1 = ptMemo1;
	}

	public String getPtMemo2() {
		return ptMemo2;
	}

	public void setPtMemo2(String ptMemo2) {
		this.ptMemo2 = ptMemo2;
	}

	public String getPtMemo3() {
		return ptMemo3;
	}

	public void setPtMemo3(String ptMemo3) {
		this.ptMemo3 = ptMemo3;
	}

	public String getPtStarttimeMemo() {
		return ptStarttimeMemo;
	}

	public void setPtStarttimeMemo(String ptStarttimeMemo) {
		this.ptStarttimeMemo = ptStarttimeMemo;
	}

	public String getPtEndtimeMemo() {
		return ptEndtimeMemo;
	}

	public void setPtEndtimeMemo(String ptEndtimeMemo) {
		this.ptEndtimeMemo = ptEndtimeMemo;
	}

	public String getPtBuyFlag() {
		return ptBuyFlag;
	}

	public void setPtBuyFlag(String ptBuyFlag) {
		this.ptBuyFlag = ptBuyFlag;
	}

}
