package com.zendaimoney.crm.product.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.crm.product.entity.BusiRedeemView;


/** 
* 项目名称：crm-webapp
* @ClassName: NoFixFinViewDao 
* @Description: 非固续投 
* @author Sam.J 
* @date 2016-1-21 下午05:17:27  
*/
public interface FinRedeemViewDao extends
		PagingAndSortingRepository<BusiRedeemView, Long>,
		JpaSpecificationExecutor<BusiRedeemView> {
	
	@Query("select count(*) from BusiRedeemView where id = ?")
	Long countBusi(Long id);
	
}
