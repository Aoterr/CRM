package com.zendaimoney.crm.product.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.crm.product.entity.ProductView;

/**
 * 
 * @author zhanghao
 * @create 2012-12-3, 下午04:06:01
 * @version 1.0
 */
public interface ProductViewDao extends
		PagingAndSortingRepository<ProductView, Long>,
		JpaSpecificationExecutor<ProductView> {
	@Query("select count(*) from ProductView where id = ?")
	Long countBusi(Long id);

//	@Modifying
//	@Query("update ProductView p set p.applyMatchStatus = ?2,p.applyMatchOperator=?3,p.applyMatchDate=?4 where  p.id in (?1)")
//	void batchUpdateApplyMatchById(List<Long> ids, String applyMatchStatus, Long operatorId, Date date, String financeCenterName);

	@Modifying
	@Query("update ProductView p set p.applyMatchStatus = ?2 where  p.lendNo in (?1)")
	void batchUpdateApplyMatchByLendNo(List<String> codes, String applyMatchStatus);
	
//	@Modifying
//	@Query("update ProductView p set p.applyMatchStatus = '2' where p.applyMatchStatus=? ")
//	int updateApplyMatchByFinanceCenter(String financeCenterName, String applyMatchStatus);

	@Modifying
	@Query("update ProductView p set p.applyMatchStatus = ?1 where  p.applyMatchStatus = ?2 and p.applyMatchDate = ?3")
	void updateApplyMatchByStatusAndDate(String  targerStatus,String sourceStatus,Date date);

	@Query("select PV from ProductView PV where lendNo in(?1)") 
	List<ProductView> findAllByLendNos(List<String> lendNoList);
}
