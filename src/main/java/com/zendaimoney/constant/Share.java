package com.zendaimoney.constant;


import javax.persistence.Entity;

import org.springside.modules.orm.IdEntity;


@Entity(name = "CRM_SHARE")
public class Share extends IdEntity {
 
	// Fields
	public static final Integer CUSTOMER = 1;// 客户
	public static final Integer CRMSERVICE = 2;// 客户服务
	public static final Integer PRODUCT_REQUEST = 3;//产品申请
	public static final Integer CONTACTRECORD = 4;//联系记录
	public static final Integer COMPLAINT = 5;//客户投诉
	public static final Integer CRMVISIT = 6;//新客户回访
	public static final Integer REDEMVISIT = 7;//赎回客户回访
	public static final Integer DISTRIBUTED = 8;//任务派发
	public static final Integer HRSTAFF = 9;//优惠券

	private Long busId;// 业务Id
	private Long shareId;// 共享者
	private Integer shareType;// 业务类型

	public Long getShareId() {
		return shareId;
	}

	public Integer getShareType() {
		return shareType;
	}

	public void setShareType(Integer shareType) {
		this.shareType = shareType;
	}

	public Long getBusId() {
		return busId;
	}

	public void setBusId(Long busId) {
		this.busId = busId;
	}

	public void setShareId(Long shareId) {
		this.shareId = shareId;
	}

}