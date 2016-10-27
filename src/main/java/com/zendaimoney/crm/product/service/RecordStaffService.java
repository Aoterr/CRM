package com.zendaimoney.crm.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.crm.customer.entity.RecordStaff;
import com.zendaimoney.crm.product.repository.RecordStaffDao;

@Service
@Transactional
public class RecordStaffService {

	@Autowired
	private RecordStaffDao recordStaffDao;

	@Transactional(readOnly = false)
	public void saveRecordStaff(RecordStaff entity) {
		recordStaffDao.save(entity);
	}
}
