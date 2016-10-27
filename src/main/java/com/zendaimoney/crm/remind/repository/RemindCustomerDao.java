package com.zendaimoney.crm.remind.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.crm.remind.entity.RemindCustomer;

/**
 * 
 * @author Yuan Changchun
 * 
 */
public interface RemindCustomerDao extends
		PagingAndSortingRepository<RemindCustomer, Long>,
		JpaSpecificationExecutor<RemindCustomer> {

	@Query("select A.customerId from RemindCustomer A where A.remindId = ?")
	public List<Long> findByRemindId(Long remindId);

	@Modifying
	@Query("delete from RemindCustomer A where A.remindId = ?")
	public void deleteByRemindId(Long remindId);
}
