package com.zendaimoney.crm.attachment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zendaimoney.crm.customer.entity.Customer;
import com.zendaimoney.crm.fund.entity.BusiFundInfo;
import com.zendaimoney.crm.modification.entity.Modification;
import com.zendaimoney.crm.product.entity.BusiCredit;
import com.zendaimoney.crm.product.entity.BusiFinance;
import org.springside.modules.orm.IdEntity;

@Entity
@Table(name = "BUSI_REQUEST_ATTACH")
public class BusiRequestAttach extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 3838028754615751976L;
	private Long raBusi;
	private Long raAttachment;
	private String raType;// 业务类型
	private BusiAttachment busiAttachment; // 附件对象
	private BusiCredit busiCredit;// 信贷业务对象
	private BusiFinance busiFinance;// 理财业务对象
	private BusiFundInfo busiFundInfo;//基金业务对象
	private Customer busiCustomer;// 客户对象
	private Modification modification;// 客户变更单对象

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RA_BUSI", insertable = false, updatable = false)
	public Modification getModification() {
		return modification;
	}

	public void setModification(Modification modification) {
		this.modification = modification;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RA_BUSI", insertable = false, updatable = false)
	public Customer getBusiCustomer() {
		return busiCustomer;
	}

	public void setBusiCustomer(Customer busiCustomer) {
		this.busiCustomer = busiCustomer;
	}

	public BusiRequestAttach() {
		super();
	}

	public BusiRequestAttach(Long raBusi, String raType) {
		this.raBusi = raBusi;
		this.raType = raType;
	}

	public BusiRequestAttach(Long raBusi, String raType, Long raAttachment) {
		this.raBusi = raBusi;
		this.raType = raType;
		this.raAttachment = raAttachment;
	}

	@Column(name = "RA_ATTACHMENT", precision = 11, scale = 0)
	public Long getRaAttachment() {
		return this.raAttachment;
	}

	public void setRaAttachment(Long raAttachment) {
		this.raAttachment = raAttachment;
	}

	@Column(name = "RA_TYPE", length = 2)
	public String getRaType() {
		return this.raType;
	}

	public void setRaType(String raType) {
		this.raType = raType;
	}

	@Column(name = "RA_BUSI", precision = 11, scale = 0)
	public Long getRaBusi() {
		return this.raBusi;
	}

	public void setRaBusi(Long raBusi) {
		this.raBusi = raBusi;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "RA_ATTACHMENT", insertable = false, updatable = false)
	public BusiAttachment getBusiAttachment() {
		return this.busiAttachment;
	}

	public void setBusiAttachment(BusiAttachment busiAttachment) {
		this.busiAttachment = busiAttachment;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RA_BUSI", insertable = false, updatable = false)
	public BusiCredit getBusiCredit() {
		return this.busiCredit;
	}

	public void setBusiCredit(BusiCredit busiCredit) {
		this.busiCredit = busiCredit;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RA_BUSI", insertable = false, updatable = false)
	public BusiFinance getBusiFinance() {
		return this.busiFinance;
	}

	public void setBusiFinance(BusiFinance busiFinance) {
		this.busiFinance = busiFinance;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RA_BUSI", insertable = false, updatable = false)
	public BusiFundInfo getBusiFundInfo() {
		return this.busiFundInfo;
	}

	public void setBusiFundInfo(BusiFundInfo busiFundInfo) {
		this.busiFundInfo = busiFundInfo;
	}
	
}