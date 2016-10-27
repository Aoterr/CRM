/** 
 * @(#)CustomerBirthDayRemindService.java 1.0.0 2013-8-20 下午05:35:18  
 *  
 * Copyright © 2013 证大财富.  All rights reserved.  
 */

package com.zendaimoney.crm.remind.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.constant.Share;
import com.zendaimoney.crm.BaseService;
import com.zendaimoney.crm.remind.repository.CustomerBirthDayRemindDao;
import com.zendaimoney.crm.remind.vo.CustomerBirthDayRemindVo;

import com.zendaimoney.utils.ConfigurationHelper;


/**
 * 
 * 
 * @author Yuan Changchun
 * @version $Revision:1.0.0, $Date: 2013-8-20 下午05:35:18 $
 */
@Component
@Transactional(readOnly = true)
public class CustomerBirthDayService extends
		BaseService<CustomerBirthDayRemindVo> {

	@Autowired
	private CustomerBirthDayRemindDao customerBirthDayRemindDao;

	public Page<CustomerBirthDayRemindVo> getAllCustomerBirthDayRemind(
			Specification<CustomerBirthDayRemindVo> spec, PageRequest pageable) {
		return this.customerBirthDayRemindDao.findAll(spec, pageable);
	}

	public Page<CustomerBirthDayRemindVo> getPageCustomer4App(Long staffId,
			Integer pageSize, Integer pageNo) {
		StringBuffer baseSql = new StringBuffer(
				" from CustomerBirthDayRemindVo WHERE birthRemainDays >=0 AND birthRemainDays <="
						+ ConfigurationHelper
								.getInteger("customerBirthdayRemindDays"));
		//baseSql = addDataAuthority(baseSql, Share.CUSTOMER, "", "crGather",staffId);
		baseSql.append(" order by birthRemainDays");
		// long total = customerBirthDayService.getTotal(null, baseSql);
		long total = getTotal(null, baseSql);
		List<CustomerBirthDayRemindVo> content = getContent(null, baseSql,
				new PageRequest(pageNo - 1, pageSize, null));
		return new PageImpl<CustomerBirthDayRemindVo>(content, new PageRequest(
				pageNo, pageSize, null), total);
	}
}
