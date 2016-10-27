package com.zendaimoney.crm.complaint.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.crm.complaint.entity.Complaint;

public interface ComplaintDao extends
		PagingAndSortingRepository<Complaint, Long>,
		JpaSpecificationExecutor<Complaint> {

}
