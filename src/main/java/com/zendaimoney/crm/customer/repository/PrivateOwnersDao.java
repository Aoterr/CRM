package com.zendaimoney.crm.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.crm.customer.entity.PrivateOwners;

public interface PrivateOwnersDao extends
		PagingAndSortingRepository<PrivateOwners, Long> {

	PrivateOwners findByCustomerid(Long id);

	@Query("from PrivateOwners c where c.customerid = ?")
	List<PrivateOwners> findByCustomeridAll(Long id);

}
