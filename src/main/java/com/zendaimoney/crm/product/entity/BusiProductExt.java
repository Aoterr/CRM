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
 * 产品附属表
 * 
 * @author zhanghao
 * @create 2012-11-29, 上午10:03:55
 * @version 1.0
 */
@Entity
@Table(name = "BUSI_PRODUCT_EXT")
public class BusiProductExt extends IdEntity implements Serializable {
	private static final long serialVersionUID = 6085721746155672550L;
	private Long productId;// 产品ID
	private String productType;// 产品类型 固定期限、非固定期限
	private String isHot; //主打
	private String productSource; //产品来源
	private Long orderNums; //申购人数
	private Long investValidNums;
	private BigDecimal increase; //收益
	public BusiProductExt() {
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getIsHot() {
		return isHot;
	}
	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}
	public String getProductSource() {
		return productSource;
	}
	public void setProductSource(String productSource) {
		this.productSource = productSource;
	}
	public Long getOrderNums() {
		return orderNums;
	}
	public void setOrderNums(Long orderNums) {
		this.orderNums = orderNums;
	}
	public BigDecimal getIncrease() {
		return increase;
	}
	public void setIncrease(BigDecimal increase) {
		this.increase = increase;
	}
	public Long getInvestValidNums() {
		return investValidNums;
	}
	public void setInvestValidNums(Long investValidNums) {
		this.investValidNums = investValidNums;
	}

	 

}
