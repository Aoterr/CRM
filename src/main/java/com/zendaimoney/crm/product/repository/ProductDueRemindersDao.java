/** 
 * @(#)ProductDueRemindersDao.java 1.0.0 2013-7-24 上午10:07:10  
 *  
 * Copyright © 2013 证大财富.  All rights reserved.  
 */

package com.zendaimoney.crm.product.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.crm.product.entity.ProductDueReminders;

/**
 * 民信信贷业务DAo
 * 
 * @author Yuan Changchun
 * @version $Revision:1.0.0, $Date: 2013-7-24 上午10:07:10 $
 */
public interface ProductDueRemindersDao extends
		PagingAndSortingRepository<ProductDueReminders, Long>,
		JpaSpecificationExecutor<ProductDueReminders> {

}
