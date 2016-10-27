package com.zendaimoney.crm.product.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.springside.modules.orm.IdEntity;

/**
 * 
 * @ClassName: BusiProductConsume
 * @Description: 消费类产品列表
 * @author liyez
 * @date 2015年1月20日 下午8:29:34
 * 
 */
@Entity
@Table(name = "BUSI_PRODUCT_CONSUME")
public class BusiProductConsume extends IdEntity implements Serializable {

	private static final long serialVersionUID = 1680860132242881466L;

	private String ptProductName;// 产品名称
	private String ptProductCode;// 产品代码
	private String ptProductType;// 产品类型

	private Date ptInputDate;// 输入时间
	private Long ptInputId;// 输入人
	private Date ptModifyTime;// 最后修改时间
	private Long ptModifyId;// 最后修改人

	private String ptDescription; // 产品描述
	private String ptMemo; // 备注

	private String ptValid;// 是否有效 1-有效，0-无效， 默认1
	private Long ptIsDel; // 删除标志位 1-正常，0-删除， 默认1

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

	public String getPtDescription() {
		return ptDescription;
	}

	public void setPtDescription(String ptDescription) {
		this.ptDescription = ptDescription;
	}

	public String getPtMemo() {
		return ptMemo;
	}

	public void setPtMemo(String ptMemo) {
		this.ptMemo = ptMemo;
	}

	public String getPtValid() {
		return ptValid;
	}

	public void setPtValid(String ptValid) {
		this.ptValid = ptValid;
	}

	public Long getPtIsDel() {
		return ptIsDel;
	}

	public void setPtIsDel(Long ptIsDel) {
		this.ptIsDel = ptIsDel;
	}

}
