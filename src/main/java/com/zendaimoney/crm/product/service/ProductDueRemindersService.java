/** 
 * @(#)ProductDueRemindersService.java 1.0.0 2013-7-24 上午10:06:05  
 *  
 * Copyright © 2013 证大财富.  All rights reserved.  
 */

package com.zendaimoney.crm.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.crm.BaseService;
import com.zendaimoney.crm.product.entity.ProductDueReminders;
import com.zendaimoney.crm.product.repository.ProductDueRemindersDao;

/**
 * 
 * 民信信贷业务补件提醒服务类
 * 
 * @author Yuan Changchun
 * @version $Revision:1.0.0, $Date: 2013-7-24 上午10:06:05 $
 */
@Service
@Transactional(readOnly = true)
public class ProductDueRemindersService extends
		BaseService<ProductDueReminders> {

	@Autowired
	private ProductDueRemindersDao productDueRemindersDao;

	/**
	 * 获取所有补件对象并分页
	 * 
	 * @author Yuan Changchun
	 * @date 2013-7-24 下午12:03:53
	 * @param spec
	 * @param pageRequest
	 * @return
	 */
	public Page<ProductDueReminders> getAllProductDueReminders(
			Specification<ProductDueReminders> spec, PageRequest pageRequest) {
		return productDueRemindersDao.findAll(spec, pageRequest);
	}

	/**
	 * 获取所有的补件对象
	 * 
	 * @author Yuan Changchun
	 * @date 2013-7-24 下午12:04:11
	 * @return
	 */
	public List<ProductDueReminders> findAllProductDueReminders() {
		return (List<ProductDueReminders>) this.productDueRemindersDao
				.findAll();
	}
}
