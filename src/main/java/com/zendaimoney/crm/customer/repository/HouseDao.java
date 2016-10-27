package com.zendaimoney.crm.customer.repository;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.crm.customer.entity.House;

public interface HouseDao extends PagingAndSortingRepository<House, Long> {

	House findByCustomerid(Long id);

	@Query("from House c where c.customerid = ?")
	List<House> findByCustomeridAll(Long id);

	@Lock(LockModeType.READ)
	@Modifying
	@Query("delete House c where c.customerid = ?")
	public void deleteHouseByCustomerId(Long customerId);
}
