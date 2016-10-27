package com.zendaimoney.crm.complaint.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.constant.ParamConstant;
import com.zendaimoney.crm.complaint.entity.Complaint;
import com.zendaimoney.crm.complaint.repository.ComplaintDao;
import com.zendaimoney.uc.rmi.vo.Staff;

@Component
@Transactional(readOnly = true)
public class ComplaintService {
	@Autowired
	private ComplaintDao complaintDao;

	public Complaint getComplaint(Long id) {
		return complaintDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public void saveComplaint(Complaint entity) {
		complaintDao.save(entity);
	}

	@Transactional(readOnly = false)
	public void deleteComplaint(Long id) {
		complaintDao.delete(id);
	}

	@Transactional(readOnly = false)
	public void deleteComplaint(Long[] ids) {
		for (Long id : ids) {
			complaintDao.delete(id);
		}
	}

	public List<Complaint> getAllComplaint() {
		return (List<Complaint>) complaintDao.findAll();
	}

	public Page<Complaint> getComplaint(PageRequest pageRequest) {
		return complaintDao.findAll(pageRequest);
	}

	/**
	 * 恢复保存
	 * 
	 * @param complaint
	 * @return
	 */
	@Transactional(readOnly = false)
	public Complaint savehuifu(Complaint complaint,Long sysuserId) {
		Complaint oldComplaint = getComplaint(complaint.getId());
		Complaint entity = new Complaint();
		BeanUtils.copyProperties(oldComplaint, entity);

		entity.setOptlock(complaint.getOptlock());

		entity.setCtIsture(complaint.getCtIsture());
		entity.setCtCustomerSatisfaction(complaint.getCtCustomerSatisfaction());
		entity.setCtProcessState(ParamConstant.complaintStateYCL);
		entity.setCtProcessResult(complaint.getCtProcessResult());
		entity.setCtProcessMemo(complaint.getCtProcessMemo());
		entity.setCtProcessTime(complaint.getCtProcessTime());
		// if(entity.getCtTimeLimit().getDate()>=complaint.getCtProcessTime().getDate()){
		// entity.setCtIsOvertime("0");
		// }else{
		// entity.setCtIsOvertime("1");
		// }
		// Date now = new Date();
		//Staff staff = AuthorityHelper.getStaff();
		entity.setCtProcessId(sysuserId);
		// TODO Auto-generated method stub
		return complaintDao.save(entity);
	}

	public Page<Complaint> getComplaint(Specification<Complaint> spec,
			PageRequest pageRequest) {
		return complaintDao.findAll(spec, pageRequest);
	}

	@Transactional(readOnly = false)
	public Complaint saveNComplaint(Complaint entity,Long sysuserId) {
		// TODO Auto-generated method stub
		Date now = new Date();
		//Staff staff = AuthorityHelper.getStaff();
		if (entity.getId() == null) {// 新建
			entity.setCtInputDate(now);
			entity.setCtInputId(sysuserId);
			entity.setCtModifyTime(now);
			entity.setCtModifyId(sysuserId);
			return complaintDao.save(entity);
		} else {// 修改
			Complaint oldComplaint = getComplaint(entity.getId());
			Complaint complaint = new Complaint();
			BeanUtils.copyProperties(oldComplaint, complaint);
			complaint.setCustomer(entity.getCustomer());
			complaint.setCtComplaintType(entity.getCtComplaintType());
			complaint.setCtTimeLimit(entity.getCtTimeLimit());
			complaint.setCtDepartment(entity.getCtDepartment());
			complaint.setCtComplaintTimeString(entity
					.getCtComplaintTimeString());
			complaint.setCtContactMethods(entity.getCtContactMethods());
			complaint.setCtContactNum(entity.getCtContactNum());
			complaint.setCtComplaintContent(entity.getCtComplaintContent());
			complaint.setCtProcessState(entity.getCtProcessState());
			complaint.setCtModifyTime(now);
			complaint.setCtModifyId(sysuserId);
			complaint.setOptlock(entity.getOptlock());
			return complaintDao.save(complaint);

		}
	}

	@Transactional(readOnly = false)
	public Complaint saveaudit(Complaint complaint,Long sysuserId) {
		// TODO Auto-generated method stub
		//Staff staff = AuthorityHelper.getStaff();
		Complaint oldComplaint = getComplaint(complaint.getId());
		Complaint entity = new Complaint();
		BeanUtils.copyProperties(oldComplaint, entity);

		entity.setOptlock(complaint.getOptlock());

		entity.setCtAuditId(sysuserId);
		entity.setCtAuditTime(complaint.getCtAuditTime());
		entity.setCtAuditResult(complaint.getCtAuditResult());
		entity.setCtAuditMemo(complaint.getCtAuditMemo());
		entity.setCtDepartment(complaint.getCtDepartment());
		if (complaint.getCtAuditResult().equals(ParamConstant.complaintAuditTY)) {
			entity.setCtProcessState(ParamConstant.complaintStateCLZ);
		}
		;
		if (complaint.getCtAuditResult().equals(ParamConstant.complaintAuditJJ)) {
			entity.setCtProcessState(ParamConstant.complaintStateYCL);
		}
		;
		if (complaint.getCtAuditResult().equals(ParamConstant.complaintAuditHT)) {
			entity.setCtProcessState(ParamConstant.complaintStateDCL);
		}
		;
		return complaintDao.save(entity);
	}
}
