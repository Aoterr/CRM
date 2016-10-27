package com.zendaimoney.crm.fund.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springside.modules.orm.IdEntity;
/**
 * 基金产品类
 * @author user
 *
 */
@Entity
@Table(name = "BUSI_PRODUCT_FUNDS")
public class BusiFunds  extends IdEntity implements Serializable {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 7483696123086704847L;

	private String fdFundName;//基金名称
	
	private String fdFundAlias;//基金简称
	
	private String fdFundCode;//基金代码
	
	private String fdFundType;//基金产品类型 1.固定收益类     2浮动收益类      3固定+浮动收益类
	
	private BigDecimal fdMinAmountFirst;//首次购买最低金额 单位 分
	
	private BigDecimal fdMinAmountFix;//定投最低金额 单位 元
	
	private Long fdMinShareCallback;//最低赎回份额 单位 份
	
	private Long fdMinShareHold;//最低持有份额 单位 份
	
	private String fdRiskLevel;//风险等级
	
	private String fdShowWay;//显示方式
	
	private String fdShowPosition;//显示位置
	
	private String fdFeeWay;//收费方式
	
	private Date fdStartDate;//认购开始时间
	
	private Date fdEndDate;//认购结束时间
	
	private BigDecimal fdRengouFee;//认购费
	
	private BigDecimal fdManageFee;//托管费
	
	private String fdValid;//是否有效标志位  1.有效  0无效
	
	private Date fdInputDate;//录入时间
	
	private Long fdInputId;//录入人
	
	private Date fdModifyTime;//修改时间
	
	private Long fdModifyId;//修改人
	
	private Double fdMinYield;//最低收益率
	
	private Double fdMaxYield;//最高收益率 固定收益产品取该值
	
	private Long fdAvailableNum;//可购买数量
	
	private String fdIsDel;//是否删除标注位 1.正常 0.删除
	
	private String fdStarttimeMemo;//起始时间说明
	
	private String fdEndtimeMemo;//到期时间（处理）说明
	
	private String fdMemo1;//备用注释字段1
	
	private String fdMemo2;//备用注释字段1
	
	private String fdMemo3;//备用注释字段1
	
	private String fdBuyFlag;//是否可购买标志位
	
	private BigDecimal fdSingleAmount;//单份购买金额 单位  分
	
	private Date fdInvestDate;//基金成立日  
	
	private Long fdManageAccountId;//托管账户id
	
	
	public Long getFdManageAccountId() {
		return fdManageAccountId;
	}

	public void setFdManageAccountId(Long fdManageAccountId) {
		this.fdManageAccountId = fdManageAccountId;
	}

	public Date getFdInvestDate() {
		return fdInvestDate;
	}

	public void setFdInvestDate(Date fdInvestDate) {
		this.fdInvestDate = fdInvestDate;
	}

	public String getFdFundName() {
		return fdFundName;
	}

	public void setFdFundName(String fdFundName) {
		this.fdFundName = fdFundName;
	}

	public String getFdFundAlias() {
		return fdFundAlias;
	}

	public void setFdFundAlias(String fdFundAlias) {
		this.fdFundAlias = fdFundAlias;
	}

	public String getFdFundCode() {
		return fdFundCode;
	}

	public void setFdFundCode(String fdFundCode) {
		this.fdFundCode = fdFundCode;
	}

	public String getFdFundType() {
		return fdFundType;
	}

	public void setFdFundType(String fdFundType) {
		this.fdFundType = fdFundType;
	}

	public BigDecimal getFdMinAmountFirst() {
		return fdMinAmountFirst;
	}

	public void setFdMinAmountFirst(BigDecimal fdMinAmountFirst) {
		this.fdMinAmountFirst = fdMinAmountFirst;
	}

	public BigDecimal getFdMinAmountFix() {
		return fdMinAmountFix;
	}

	public void setFdMinAmountFix(BigDecimal fdMinAmountFix) {
		this.fdMinAmountFix = fdMinAmountFix;
	}

	public Long getFdMinShareCallback() {
		return fdMinShareCallback;
	}

	public void setFdMinShareCallback(Long fdMinShareCallback) {
		this.fdMinShareCallback = fdMinShareCallback;
	}

	public Long getFdMinShareHold() {
		return fdMinShareHold;
	}

	public void setFdMinShareHold(Long fdMinShareHold) {
		this.fdMinShareHold = fdMinShareHold;
	}

	public String getFdRiskLevel() {
		return fdRiskLevel;
	}

	public void setFdRiskLevel(String fdRiskLevel) {
		this.fdRiskLevel = fdRiskLevel;
	}

	public String getFdShowWay() {
		return fdShowWay;
	}

	public void setFdShowWay(String fdShowWay) {
		this.fdShowWay = fdShowWay;
	}

	public String getFdShowPosition() {
		return fdShowPosition;
	}

	public void setFdShowPosition(String fdShowPosition) {
		this.fdShowPosition = fdShowPosition;
	}

	public String getFdFeeWay() {
		return fdFeeWay;
	}

	public void setFdFeeWay(String fdFeeWay) {
		this.fdFeeWay = fdFeeWay;
	}

	public Date getFdStartDate() {
		return fdStartDate;
	}

	public void setFdStartDate(Date fdStartDate) {
		this.fdStartDate = fdStartDate;
	}

	public Date getFdEndDate() {
		return fdEndDate;
	}

	public void setFdEndDate(Date fdEndDate) {
		this.fdEndDate = fdEndDate;
	}

	public BigDecimal getFdRengouFee() {
		return fdRengouFee;
	}

	public void setFdRengouFee(BigDecimal fdRengouFee) {
		this.fdRengouFee = fdRengouFee;
	}

	public BigDecimal getFdManageFee() {
		return fdManageFee;
	}

	public void setFdManageFee(BigDecimal fdManageFee) {
		this.fdManageFee = fdManageFee;
	}

	public String getFdValid() {
		return fdValid;
	}

	public void setFdValid(String fdValid) {
		this.fdValid = fdValid;
	}

	public Date getFdInputDate() {
		return fdInputDate;
	}

	public void setFdInputDate(Date fdInputDate) {
		this.fdInputDate = fdInputDate;
	}

	public Long getFdInputId() {
		return fdInputId;
	}

	public void setFdInputId(Long fdInputId) {
		this.fdInputId = fdInputId;
	}

	public Date getFdModifyTime() {
		return fdModifyTime;
	}

	public void setFdModifyTime(Date fdModifyTime) {
		this.fdModifyTime = fdModifyTime;
	}

	public Long getFdModifyId() {
		return fdModifyId;
	}

	public void setFdModifyId(Long fdModifyId) {
		this.fdModifyId = fdModifyId;
	}

	public Double getFdMinYield() {
		return fdMinYield;
	}

	public void setFdMinYield(Double fdMinYield) {
		this.fdMinYield = fdMinYield;
	}

	public Double getFdMaxYield() {
		return fdMaxYield;
	}

	public void setFdMaxYield(Double fdMaxYield) {
		this.fdMaxYield = fdMaxYield;
	}

	public Long getFdAvailableNum() {
		return fdAvailableNum;
	}

	public void setFdAvailableNum(Long fdAvailableNum) {
		this.fdAvailableNum = fdAvailableNum;
	}

	public String getFdIsDel() {
		return fdIsDel;
	}

	public void setFdIsDel(String fdIsDel) {
		this.fdIsDel = fdIsDel;
	}

	public String getFdStarttimeMemo() {
		return fdStarttimeMemo;
	}

	public void setFdStarttimeMemo(String fdStarttimeMemo) {
		this.fdStarttimeMemo = fdStarttimeMemo;
	}

	public String getFdEndtimeMemo() {
		return fdEndtimeMemo;
	}

	public void setFdEndtimeMemo(String fdEndtimeMemo) {
		this.fdEndtimeMemo = fdEndtimeMemo;
	}

	public String getFdMemo1() {
		return fdMemo1;
	}

	public void setFdMemo1(String fdMemo1) {
		this.fdMemo1 = fdMemo1;
	}

	public String getFdMemo2() {
		return fdMemo2;
	}

	public void setFdMemo2(String fdMemo2) {
		this.fdMemo2 = fdMemo2;
	}

	public String getFdMemo3() {
		return fdMemo3;
	}

	public void setFdMemo3(String fdMemo3) {
		this.fdMemo3 = fdMemo3;
	}

	public String getFdBuyFlag() {
		return fdBuyFlag;
	}

	public void setFdBuyFlag(String fdBuyFlag) {
		this.fdBuyFlag = fdBuyFlag;
	}

	public BigDecimal getFdSingleAmount() {
		return fdSingleAmount;
	}

	public void setFdSingleAmount(BigDecimal fdSingleAmount) {
		this.fdSingleAmount = fdSingleAmount;
	}
}
