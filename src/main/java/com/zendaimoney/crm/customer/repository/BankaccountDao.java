package com.zendaimoney.crm.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.crm.customer.entity.Bankaccount;

public interface BankaccountDao extends
		PagingAndSortingRepository<Bankaccount, Long> {

	// List<Bankaccount> findALlByCustomerid(Long id);
	@Query("from Bankaccount ba where ba.crmCustomer.id = ? and ba.baValid = 1 order by ba.id desc")
	List<Bankaccount> findByCrmCustomer(Long id);

	@Query("from Bankaccount ba where ba.crmCustomer.id = ?")
	List<Bankaccount> findByCrmCustomerN(Long id);
	
	@Query("from Bankaccount ba where ba.id = ? and ba.baValid = 1")
	Bankaccount findByIdAndBaValid(Long id);
	//根据客户id和银行id查找
	@Query("from Bankaccount ba where ba.crmCustomer.id = ? and ba.baValid = 1 and ba.id = ? ")
	List<Bankaccount> findByCrmCustomerAndBankId(Long id,Long bankId);
	
	@Query("from Bankaccount ba where ba.id = ? and ba.baValid = 1 and ba.baAppFlag =1")
	Bankaccount findByIdAndAppFlag(Long id);
}
