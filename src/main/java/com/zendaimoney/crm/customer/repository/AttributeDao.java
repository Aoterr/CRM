package com.zendaimoney.crm.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.crm.customer.entity.Attribute;

public interface AttributeDao extends
		PagingAndSortingRepository<Attribute, Long> {

	@Query("from Attribute a where a.customerid = ? and a.hType = ? ")
	List<Attribute> findALlByCustomeridAndHType(Long customerid, String hType);
}
