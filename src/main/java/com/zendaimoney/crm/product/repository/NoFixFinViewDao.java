package com.zendaimoney.crm.product.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.crm.product.entity.NoFixFinView;


/** 
* 项目名称：crm-webapp
* @ClassName: NoFixFinViewDao 
* @Description: 非固续投 
* @author Sam.J 
* @date 2015-7-29 下午05:17:27  
*/
public interface NoFixFinViewDao extends
		PagingAndSortingRepository<NoFixFinView, Long>,
		JpaSpecificationExecutor<NoFixFinView> {
	@Query("select count(*) from NoFixFinView where id = ?")
	Long countBusi(Long id);
}
