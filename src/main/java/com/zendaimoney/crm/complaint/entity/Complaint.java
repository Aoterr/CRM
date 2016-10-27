package com.zendaimoney.crm.complaint.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springside.modules.orm.IdEntity;

import com.zendaimoney.crm.customer.entity.Customer;

@Entity(name = "crm_complaint")
public class Complaint extends IdEntity {

	private static final long serialVersionUID = 6426011293509425559L;
	/**
	 * 客户
	 */
	private Customer customer;
	/**
	 * 投诉时间
	 */
	private Date ctComplaintTime; // ct_complaint_time TIMESTAMP
	/**
	 * 投诉内容
	 */
	private String ctComplaintContent;
	/**
	 * 投诉分类
	 */
	private String ctComplaintType; // ct_complaint_type CHAR(2)
	/**
	 * 紧急程度
	 */
	private String ctUrgency; // ct_urgency CHAR(2)
	/**
	 * 期望的结果
	 */
	private String ctCustomerHope; // ct_customer_hope VARCHAR2(500)
	/**
	 * 处理人
	 */
	private Long ctProcessId; // ct_process_id VARCHAR2(20)
	/**
	 * 处理时间
	 */
	private Date ctProcessTime; // ct_process_time TIMESTAMP
	/**
	 * 处理结果
	 */
	private String ctProcessResult; // ct_process_result VARCHAR2(500)
	/**
	 * 
	 */
	private String ctProcessMemo;
	/**
	 * 
	 */
	private String ctIsOvertime; // 是否超时处理 ct_is_overtime CHAR(1)
	/**
	 * 
	 */
	private String ctCustomerSatisfaction; // 客户满意度 ct_customer_satisfaction
											// CHAR(2)
	/**
	 * 
	 */
	private String ctComplainant; // 投诉人 ct_complainant VARCHAR(60)
	/**
	 * 
	 */
	private String ctContactMethods; // 投诉人联系方式 ct_contact_methods CHAR(2)
	/**
	 * 
	 */
	private String ctProcessState; // 处理标示 ct_process_state CHAR(2)
	/**
	 * 
	 */
	private String ctIsture; // 是否属实 ct_isture CHAR(1)
	/**
	 * 
	 */
	private String ctCustomerService; // 客服ID ct_customer_service VARCHAR2(20)
	/**
	 * 
	 */
	private Date ctInputDate; // 信息录入时间 ct_input_date TIMESTAMP
	/**
	 * 
	 */
	private Long ctInputId; // 信息录入人 ct_input_id VARCHAR2(20)
	/**
	 * 
	 */
	private Date ctModifyTime; // 最后修改时间 ct_modify_time TIMESTAMP
	/**
	 * 
	 */
	private Long ctModifyId; // 最后修改人 ct_modify_id VARCHAR2(20)
	/**
	 * 
	 */
	private String ctMemo; // 备注 ct_memo VARCHAR2(200)
	/**
	 * 
	 */
	private String ctComplaintTimeString;
	/**
	 * 
	 */
	private String ctDepartment;// 投诉部门
	/**
	 * 
	 */
	private Date ctTimeLimit;// 处理时限
	/**
	 * 
	 */
	private String ctContactNum;// 联系号码
	/**
	 * 
	 */
	private Long ctAuditId;
	/**
	 * 
	 */
	private Date ctAuditTime;
	/**
	 * 
	 */
	private String ctAuditResult;
	/**
	 * 
	 */
	private String ctAuditMemo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CUSTOMERID")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@javax.persistence.Transient
	public String getCtComplaintTimeString() {
		return ctComplaintTimeString;
	}

	public void setCtComplaintTimeString(String ctComplaintTimeString) {
		this.ctComplaintTimeString = ctComplaintTimeString;
	}

	/*public Date getCtComplaintTime() {
		String datetimestyle = "yyyy-MM-dd HH:mm:ss";
		if (ctComplaintTime != null) {
			ctComplaintTimeString = DateUtils.formatDate(ctComplaintTime,
					datetimestyle);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(datetimestyle);
			try {
				if (ctComplaintTimeString != null)
					this.ctComplaintTime = sdf.parse(ctComplaintTimeString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return ctComplaintTime;
	}*/

	// public int getCustomerid() {
	// return customerid;
	// }
	// public void setCustomerid(int customerid) {
	// this.customerid = customerid;
	// }
	public String getCtComplaintContent() {
		return ctComplaintContent;
	}

	public void setCtComplaintContent(String ctComplaintContent) {
		this.ctComplaintContent = ctComplaintContent;
	}

	public String getCtComplaintType() {
		return ctComplaintType;
	}

	public void setCtComplaintType(String ctComplaintType) {
		this.ctComplaintType = ctComplaintType;
	}

	public String getCtUrgency() {
		return ctUrgency;
	}

	public void setCtUrgency(String ctUrgency) {
		this.ctUrgency = ctUrgency;
	}

	public String getCtCustomerHope() {
		return ctCustomerHope;
	}

	public void setCtCustomerHope(String ctCustomerHope) {
		this.ctCustomerHope = ctCustomerHope;
	}

	public Long getCtProcessId() {
		return ctProcessId;
	}

	public void setCtProcessId(Long ctProcessId) {
		this.ctProcessId = ctProcessId;
	}

	public Date getCtProcessTime() {
		return ctProcessTime;
	}

	public void setCtProcessTime(Date ctProcessTime) {
		this.ctProcessTime = ctProcessTime;
	}

	public String getCtProcessResult() {
		return ctProcessResult;
	}

	public void setCtProcessResult(String ctProcessResult) {
		this.ctProcessResult = ctProcessResult;
	}

	public String getCtIsOvertime() {
		return ctIsOvertime;
	}

	public void setCtIsOvertime(String ctIsOvertime) {
		this.ctIsOvertime = ctIsOvertime;
	}

	public String getCtCustomerSatisfaction() {
		return ctCustomerSatisfaction;
	}

	public void setCtCustomerSatisfaction(String ctCustomerSatisfaction) {
		this.ctCustomerSatisfaction = ctCustomerSatisfaction;
	}

	public String getCtComplainant() {
		return ctComplainant;
	}

	public void setCtComplainant(String ctComplainant) {
		this.ctComplainant = ctComplainant;
	}

	public String getCtContactMethods() {
		return ctContactMethods;
	}

	public void setCtContactMethods(String ctContactMethods) {
		this.ctContactMethods = ctContactMethods;
	}

	public String getCtProcessState() {
		return ctProcessState;
	}

	public void setCtProcessState(String ctProcessState) {
		this.ctProcessState = ctProcessState;
	}

	public String getCtIsture() {
		return ctIsture;
	}

	public void setCtIsture(String ctIsture) {
		this.ctIsture = ctIsture;
	}

	public String getCtCustomerService() {
		return ctCustomerService;
	}

	public void setCtCustomerService(String ctCustomerService) {
		this.ctCustomerService = ctCustomerService;
	}

	public Date getCtInputDate() {
		return ctInputDate;
	}

	public void setCtInputDate(Date ctInputDate) {
		this.ctInputDate = ctInputDate;
	}

	public Long getCtInputId() {
		return ctInputId;
	}

	public void setCtInputId(Long long1) {
		this.ctInputId = long1;
	}

	public Date getCtModifyTime() {
		return ctModifyTime;
	}

	public void setCtModifyTime(Date ctModifyTime) {
		this.ctModifyTime = ctModifyTime;
	}

	public Long getCtModifyId() {
		return ctModifyId;
	}

	public void setCtModifyId(Long long1) {
		this.ctModifyId = long1;
	}

	public String getCtMemo() {
		return ctMemo;
	}

	public void setCtMemo(String ctMemo) {
		this.ctMemo = ctMemo;
	}

	public String getCtDepartment() {
		return ctDepartment;
	}

	public void setCtDepartment(String ctDepartment) {
		this.ctDepartment = ctDepartment;
	}

	public Date getCtTimeLimit() {
		return ctTimeLimit;
	}

	public void setCtTimeLimit(Date ctTimeLimit) {
		this.ctTimeLimit = ctTimeLimit;
	}

	public String getCtContactNum() {
		return ctContactNum;
	}

	public void setCtContactNum(String ctContactNum) {
		this.ctContactNum = ctContactNum;
	}

	public void setCtComplaintTime(Date ctComplaintTime) {
		this.ctComplaintTime = ctComplaintTime;
	}

	public String getCtProcessMemo() {
		return ctProcessMemo;
	}

	public void setCtProcessMemo(String ctProcessMemo) {
		this.ctProcessMemo = ctProcessMemo;
	}

	public Long getCtAuditId() {
		return ctAuditId;
	}

	public void setCtAuditId(Long ctAuditId) {
		this.ctAuditId = ctAuditId;
	}

	public Date getCtAuditTime() {
		return ctAuditTime;
	}

	public void setCtAuditTime(Date ctAuditTime) {
		this.ctAuditTime = ctAuditTime;
	}

	public String getCtAuditResult() {
		return ctAuditResult;
	}

	public void setCtAuditResult(String ctAuditResult) {
		this.ctAuditResult = ctAuditResult;
	}

	public String getCtAuditMemo() {
		return ctAuditMemo;
	}

	public void setCtAuditMemo(String ctAuditMemo) {
		this.ctAuditMemo = ctAuditMemo;
	}

}
