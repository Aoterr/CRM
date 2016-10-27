/*
* Copyright (c) 2015 zendaimoney.com. All Rights Reserved.
*/
package com.zendaimoney.crm.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.crm.product.entity.ProductListView;
import com.zendaimoney.crm.product.repository.ProductListViewDao;

/**
 * ProductListViewService.java
 *
 * Author: Yangying
 * Date: 2016年5月9日 下午5:05:56
 * Mail: yangy06@zendaimoney.com
 */
@Service
@Transactional(readOnly = true)
public class ProductListViewService {

	@Autowired
	private ProductListViewDao productListViewDao;
	
	public Page<ProductListView> getAllProductListView(Specification<ProductListView> spec,
			PageRequest pageRequest) {
		return productListViewDao.findAll(spec, pageRequest);
	}
}
