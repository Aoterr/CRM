/*
* Copyright (c) 2015 zendaimoney.com. All Rights Reserved.
*/
package com.zendaimoney.crm.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.crm.product.entity.BusiFinanceUc;

/**
 * BusiFinanceUcDao.java
 *
 * Author: Yangying
 * Date: 2016年9月12日 上午11:11:55
 * Mail: yangy06@zendaimoney.com
 */
public interface BusiFinanceUcDao extends
PagingAndSortingRepository<BusiFinanceUc, Long>,
JpaSpecificationExecutor<BusiFinanceUc>{

	/**
	 * 查询指定业务id的有效UC信息
	 * @param fuBusiId
	 * @return
	 */
	@Query("select FU from BusiFinanceUc FU where FU.fuBusiId = ? and FU.fuStatus = '1' ")
	List<BusiFinanceUc> findByBusiId(Long fuBusiId);
}
