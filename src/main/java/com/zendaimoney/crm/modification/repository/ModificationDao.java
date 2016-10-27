/** 
 * @(#)ModificationDao.java 1.0.0 2013-01-29 16:15:21  
 *  
 * Copyright © 2013 证大财富.  All rights reserved.  
 */

package com.zendaimoney.crm.modification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zendaimoney.crm.modification.entity.Modification;

/**
 * Class ModificationDao
 * 
 * @author zhanghao
 * @version $Revision:1.0.0, $Date: 2013-01-29 16:15:21 $
 */
public interface ModificationDao extends
		PagingAndSortingRepository<Modification, Long>,
		JpaSpecificationExecutor<Modification> {
	@Query("select count(*) from Modification where mnSourceId =:mnSourceId and mnType =:mnType and mnState < 4")
	Long findUnSubmitCount(@Param("mnSourceId") Long id,
			@Param("mnType") String type);

	@Query("SELECT id FROM Modification WHERE mnSourceId =:mnSourceId")
	List<Long> findAllIdByMnSourceId(@Param("mnSourceId") Long mnSourceId);

	@Query("FROM Modification WHERE mnSourceId =:mnSourceId order by id desc")
	List<Modification> findAllByMnSourceId(@Param("mnSourceId") Long mnSourceId);
}
