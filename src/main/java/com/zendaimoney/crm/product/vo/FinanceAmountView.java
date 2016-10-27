package com.zendaimoney.crm.product.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Subselect;

/**
 * 获取产品总额
 * 
 * @author liye.Zhang
 * @time 2014-12-9 下午15:42:20
 */
@Entity
@Subselect("SELECT ROWNUM AS ID , T3.* FROM (SELECT T.FE_PRODUCT AS PRODUCT_ID,T.FE_ACTIVITY_ID AS  ACTIVITY_ID,SUM( FE_AMOUNT) AS TOTAL_AMOUNT  FROM  BUSI_FINANCE T WHERE T.FE_PAY_STATUS='3' AND T.FE_BUSI_STATE NOT IN('02000011','02000012') AND T.FE_PRODUCT IN (SELECT ID FROM BUSI_PRODUCT T2 WHERE T2.PT_PRODUCT_TYPE='3') GROUP BY  (T.FE_ACTIVITY_ID,T.FE_PRODUCT) ) T3")
public class FinanceAmountView implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	/**
	 * 理财产品
	 */
	private String productId;

	/**
	 * 活动
	 */
	private String activityId;
	/**
	 * 金额
	 */
	private BigDecimal totalAmount;

	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

}
