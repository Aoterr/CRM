package com.zendaimoney.crm.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.crm.customer.entity.Car;

public interface CarDao extends PagingAndSortingRepository<Car, Long> {

	@Query("from Car c where c.customerid = ?")
	List<Car> findByCustomeridAll(Long id);
}
