/** 
 * @(#)BusiAuditDao.java 1.0.0 2013-01-11 14:53:19  
 *  
 * Copyright © 2013 证大财富.  All rights reserved.  
 */

package com.zendaimoney.crm.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.crm.product.entity.BusiCredit;

/**
 * Class BusiCreditDao
 * 
 * @author zhanghao
 * @version $Revision:1.0.0, $Date: 2013-01-11 14:53:19 $
 */
public interface BusiCreditDao extends
		PagingAndSortingRepository<BusiCredit, Long>,
		JpaSpecificationExecutor<BusiCredit> {

	public List<BusiCredit> findAllByIdIn(List<Long> ids);

	@Query("select count(*) from BusiCredit where customerId = ?")
	public Long countByCustomerId(Long customerId);

	@Query("SELECT MAX(cBorrowNo) FROM BusiCredit WHERE CUSTOMERID = ?")
	public String findCustMaxLendNo(Long custId);

}
