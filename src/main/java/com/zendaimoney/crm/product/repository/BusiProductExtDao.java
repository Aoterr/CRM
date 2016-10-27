package com.zendaimoney.crm.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zendaimoney.crm.product.entity.BusiProduct;
import com.zendaimoney.crm.product.entity.BusiProductConsume;
import com.zendaimoney.crm.product.entity.BusiProductExt;

/**
 * 
 * @author chenjl
 * @create 2016/5/4, 下午02:05:02
 * @version 1.0
 */
public interface BusiProductExtDao extends
		PagingAndSortingRepository<BusiProductExt, Long>,
		JpaSpecificationExecutor<BusiProductExt> {

	/**
	 * 根据产品的id查找产品附属表信息
	 * 
	 * @author chenjl
	 * @param BusiProductExt
	 * @return
	 */
	@Query("from BusiProductExt pt where pt.productId = ? ")
	public List<BusiProductExt> findAllProductExtByProductId(Long productId);

	@Query("from BusiProductExt pt where pt.productId = ? ")
	public BusiProductExt findOneByProductId(Long productId);

	@Query("from BusiProductExt pt where pt.isHot = 0 ")
	public List<BusiProductExt> findHot();
	
}
