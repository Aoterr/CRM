package com.zendaimoney.crm.fund.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.crm.fund.entity.FundsProductView;
import com.zendaimoney.crm.product.entity.ProductView;

/**
 * 
 * @author zhanghao
 * @create 2012-12-3, 下午04:06:01
 * @version 1.0
 */
public interface FundsProductViewDao extends
		PagingAndSortingRepository<FundsProductView, Long>,
		JpaSpecificationExecutor<FundsProductView> {
	@Query("select count(*) from FundsProductView where id = ?")
	Long countBusi(Long id);
}
