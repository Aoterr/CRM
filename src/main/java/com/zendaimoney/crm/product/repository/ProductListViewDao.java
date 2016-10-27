/*
* Copyright (c) 2015 zendaimoney.com. All Rights Reserved.
*/
package com.zendaimoney.crm.product.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.crm.product.entity.ProductListView;

/**
 * ProductListViewDao.java
 *
 * Author: Yangying
 * Date: 2016年5月9日 下午5:07:07
 * Mail: yangy06@zendaimoney.com
 */
public interface ProductListViewDao extends
PagingAndSortingRepository<ProductListView, Long>,
JpaSpecificationExecutor<ProductListView>{

}
