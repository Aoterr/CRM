package com.zendaimoney.crm.product.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.crm.customer.entity.RecordStaff;

public interface RecordStaffDao extends
		PagingAndSortingRepository<RecordStaff, Long>,
		JpaSpecificationExecutor<RecordStaff> {

}
