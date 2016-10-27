/** 
 * @(#)BusiAuditDao.java 1.0.0 2013-01-11 14:53:19  
 *  
 * Copyright © 2013 证大财富.  All rights reserved.  
 */

package com.zendaimoney.crm.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.crm.product.entity.BusiAudit;

/**
 * Class BusiAuditDao
 * 
 * @author zhanghao
 * @version $Revision:1.0.0, $Date: 2013-01-11 14:53:19 $
 */
public interface BusiAuditDao extends
		PagingAndSortingRepository<BusiAudit, Long>,
		JpaSpecificationExecutor<BusiAudit> {

	List<BusiAudit> findAllByAtBusiAndAtTypeOrderByAtInputTimeDesc(Long id,
			String productTypeCustomerModification);

}
