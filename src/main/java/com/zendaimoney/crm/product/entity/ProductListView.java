package com.zendaimoney.crm.product.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Subselect;

/**
 * 产品视图
 * 
 * @author lk
 * @create 2016-5-10, 下午04:01:09
 * @version 1.0
 */
@Entity
@Subselect("select p.id,p.ext_id, p.pt_product_name as name,p.product_type as type,p.pt_product_type as product_type,p.pt_min_term as min_term,p.pt_max_term as max_term,p.pt_min_amount as min_amount, p.pt_max_amount as max_amount,p.pt_min_yield as min_yield, p.pt_max_yield as max_yield,p.is_hot,p.order_nums,p.invest_valid_nums, p.pt_buy_flag as can_buy, p.pt_is_del as is_del, p.pt_valid as valid_state, p.pt_memo1 as memo1, p.pt_memo2 as memo2,p.pt_memo3 as memo3 from (select p1.id,p1.pt_product_name, p1.pt_product_type, pt_min_term, p1.pt_max_term,p1.pt_min_amount, p1.pt_max_amount, p1.pt_min_yield, p1.pt_max_yield, p1.pt_buy_flag,p1.pt_is_del, p1.pt_valid, p2.id as ext_id, p2.is_hot, p2.order_nums,p2.invest_valid_nums, p2.product_type, p1.pt_memo1,p1.pt_memo2,p1.pt_memo3 from busi_product p1 left join busi_product_ext p2 on p1.id = p2.product_id) p")
public class ProductListView implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Long id;
	private String name;//产品名称
	private String type;//类型 0固定类 1非固定类
	private String productType;//产品类型 1信贷 2线下理财 3线上理财
	private Long minTerm;//最低投资期限
	private Long maxTerm;//最高投资期限
	private BigDecimal minAmount;//最低投资金额
	private BigDecimal maxAmount;//最高投资金额
	private Double minYield;//最低收益
	private Double maxYield;//最高收益（固定类收益）
	private String isHot;//是否主打
	private Long orderNums;//申购数量
	private String canBuy;//是否可购买
	private String isDel;//是否删除
	private String validState;//状态 有效 无效

	private String memo1;//计划介绍
	private String memo2;//项目特点
	private String memo3;//还款方式
	
	private BigDecimal investValidNums;//投资生效数量
	
	private Long extId; //附属表主键
	
	@Id
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * @return the minTerm
	 */
	public Long getMinTerm() {
		return minTerm;
	}

	/**
	 * @param minTerm the minTerm to set
	 */
	public void setMinTerm(Long minTerm) {
		this.minTerm = minTerm;
	}

	/**
	 * @return the maxTerm
	 */
	public Long getMaxTerm() {
		return maxTerm;
	}

	/**
	 * @param maxTerm the maxTerm to set
	 */
	public void setMaxTerm(Long maxTerm) {
		this.maxTerm = maxTerm;
	}

	/**
	 * @return the minAmount
	 */
	public BigDecimal getMinAmount() {
		return minAmount;
	}

	/**
	 * @param minAmount the minAmount to set
	 */
	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	/**
	 * @return the maxAmount
	 */
	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	/**
	 * @param maxAmount the maxAmount to set
	 */
	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	/**
	 * @return the minYield
	 */
	public Double getMinYield() {
		return minYield;
	}

	/**
	 * @param minYield the minYield to set
	 */
	public void setMinYield(Double minYield) {
		this.minYield = minYield;
	}

	/**
	 * @return the maxYield
	 */
	public Double getMaxYield() {
		return maxYield;
	}

	/**
	 * @param maxYield the maxYield to set
	 */
	public void setMaxYield(Double maxYield) {
		this.maxYield = maxYield;
	}

	/**
	 * @return the isHot
	 */
	public String getIsHot() {
		return isHot;
	}

	/**
	 * @param isHot the isHot to set
	 */
	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}

	/**
	 * @return the orderNums
	 */
	public Long getOrderNums() {
		return orderNums;
	}

	/**
	 * @param orderNums the orderNums to set
	 */
	public void setOrderNums(Long orderNums) {
		this.orderNums = orderNums;
	}

	/**
	 * @return the canBuy
	 */
	public String getCanBuy() {
		return canBuy;
	}

	/**
	 * @param canBuy the canBuy to set
	 */
	public void setCanBuy(String canBuy) {
		this.canBuy = canBuy;
	}

	/**
	 * @return the isDel
	 */
	public String getIsDel() {
		return isDel;
	}

	/**
	 * @param isDel the isDel to set
	 */
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	/**
	 * @return the validState
	 */
	public String getValidState() {
		return validState;
	}

	/**
	 * @param validState the validState to set
	 */
	public void setValidState(String validState) {
		this.validState = validState;
	}

	/**
	 * @return the memo1
	 */
	public String getMemo1() {
		return memo1;
	}

	/**
	 * @param memo1 the memo1 to set
	 */
	public void setMemo1(String memo1) {
		this.memo1 = memo1;
	}

	/**
	 * @return the memo2
	 */
	public String getMemo2() {
		return memo2;
	}

	/**
	 * @param memo2 the memo2 to set
	 */
	public void setMemo2(String memo2) {
		this.memo2 = memo2;
	}

	/**
	 * @return the memo3
	 */
	public String getMemo3() {
		return memo3;
	}

	/**
	 * @param memo3 the memo3 to set
	 */
	public void setMemo3(String memo3) {
		this.memo3 = memo3;
	}

	/**
	 * @return the investValidNums
	 */
	public BigDecimal getInvestValidNums() {
		return investValidNums;
	}

	/**
	 * @param investValidNums the investValidNums to set
	 */
	public void setInvestValidNums(BigDecimal investValidNums) {
		this.investValidNums = investValidNums;
	}

	public Long getExtId() {
		return extId;
	}

	public void setExtId(Long extId) {
		this.extId = extId;
	}

}
