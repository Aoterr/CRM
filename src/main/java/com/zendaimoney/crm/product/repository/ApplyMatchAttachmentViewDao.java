/*
* Copyright (c) 2015 zendaimoney.com. All Rights Reserved.
*/
package com.zendaimoney.crm.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.crm.product.entity.ApplyMatchAttachmentView;

/**
 * ApplyMatchAttachmentViewDao.java
 *
 * Author: Yangying
 * Date: 2016年8月21日 上午11:34:58
 * Mail: yangy06@zendaimoney.com
 */
public interface ApplyMatchAttachmentViewDao extends
	PagingAndSortingRepository<ApplyMatchAttachmentView,Long>,
	JpaSpecificationExecutor<ApplyMatchAttachmentView>{

	@Query("select A from ApplyMatchAttachmentView A where lendNo in (?1)")
	List<ApplyMatchAttachmentView> findByLendNo(List<String> lendNos);
}
