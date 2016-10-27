/** 
 * @(#)CustomerBirthDayRemindVoDao.java 1.0.0 2013-8-20 下午05:33:33  
 *  
 * Copyright © 2013 证大财富.  All rights reserved.  
 */

package com.zendaimoney.crm.remind.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.crm.remind.vo.CustomerBirthDayRemindVo;

/**
 * 
 * 
 * @author Yuan Changchun
 * @version $Revision:1.0.0, $Date: 2013-8-20 下午05:33:33 $
 */
public interface CustomerBirthDayRemindDao extends
		PagingAndSortingRepository<CustomerBirthDayRemindVo, Long>,
		JpaSpecificationExecutor<CustomerBirthDayRemindVo> {

}
