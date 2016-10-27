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

import com.zendaimoney.crm.modification.entity.ModificationDetail;

/**
 * Class ModificationDao
 * 
 * @author zhanghao
 * @version $Revision:1.0.0, $Date: 2013-01-29 16:15:21 $
 */
public interface ModificationDetailDao extends
		PagingAndSortingRepository<ModificationDetail, Long>,
		JpaSpecificationExecutor<ModificationDetail> {
	// @Query("delete from ModificationDetail md where md.modification.id =:modificationId")
	// void deleteAllModificationDetail(@Param("modificationId") Long
	// modificationId);

	@Query("select count(*) from ModificationDetail m where m.field.id in (:ids) and m.mlFieldOldValue =:mlValue or m.mlFieldNewValue =:mlValue")
	Long findCountByModifyTypeAndValue(@Param("ids") List<Long> ids,
			@Param("mlValue") String mlValue);
}
