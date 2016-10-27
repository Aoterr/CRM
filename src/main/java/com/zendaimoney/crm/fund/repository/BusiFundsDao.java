package com.zendaimoney.crm.fund.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zendaimoney.crm.fund.entity.BusiFunds;
import com.zendaimoney.crm.product.entity.BusiProduct;
import com.zendaimoney.crm.product.entity.BusiProductConsume;

/**
 * 
 * @author CJ
 * @create 2012-11-29, 下午02:05:02
 * @version 1.0
 */
public interface BusiFundsDao extends
		PagingAndSortingRepository<BusiFunds, Long>,
		JpaSpecificationExecutor<BusiFunds> {
	@Query("from BusiFunds fd where fd.fdFundType = ?")
	public List<BusiFunds> findAllProductByType(String type);

	/**
	 * 根据产品的id查找产品信息
	 * 
	 * @author CJ
	 * @date 2015-11-25下午03:31:35
	 * @param BusiFunds
	 * @return
	 */
	@Query("from BusiFunds fd where fd.id in (:ids) ")
	public List<BusiFunds> findAllProductById(@Param("ids")List<Long> ids);
	
	/**
	 * 根据产品的code查找产品信息
	 * 
	 * @author CJ
	 * @date 2015-11-25下午03:31:35
	 * @param fdProductCode
	 * @return
	 */
	@Query("from BusiFunds fd where fd.fdFundCode = ?")
	public BusiFunds findOneByFdProductCode(String fdFundCode);

	/**
	 * @Title:根据产品type搜索出可用的所有产品信息
	 * @Description: TODO
	 * @param type
	 * @return
	 * @throws
	 * @time:2015-11-25 下午03:19:22
	 * @author:CJ
	 */
	@Query("from BusiFunds fd where fd.fdFundType in (:type)  and fd.fdIsDel='1' and fd.fdValid='1'")
	public List<BusiFunds> findProductByTypeNotDel(
			@Param("type") List<String> type);

	/**
	 * @Title:根据产品type搜索出所有产品信息  可购买
	 * @Description: TODO
	 * @param type
	 * @return
	 * @throws
	 * @time:2015-11-25 下午03:19:22
	 * @author:CJ
	 */
	@Query("from BusiFunds fd where fd.fdFundType in (:type)  and fd.fdIsDel='1'")
	public List<BusiFunds> findProductByTypes(@Param("type") List<String> type);
	
	/**
	 * @Title:搜索出有效可购买未删除的所有产品信息  
	 * @Description: TODO
	 * @param 
	 * @return
	 * @throws
	 * @time:2015-11-25 下午03:19:22
	 * @author:CJ
	 */
	@Query("from BusiFunds fd where fd.fdBuyFlag='1'  and fd.fdIsDel='1' and fd.fdValid='1'")
	public List<BusiFunds> findAllValid();
	/**
	 * @Title:搜索出未删除的所有产品信息  在有效时间内
	 * @Description: TODO
	 * @param 
	 * @return
	 * @throws
	 * @time:2015-11-25 下午03:19:22
	 * @author:CJ
	 * @param pageable 
	 * @param spec 
	 */
	/*@Query("from BusiFunds fd where fd.fdBuyFlag='1'  and fd.fdIsDel='1' and fd.fdValid='1' and sysdate between fd.fdStartDate and fd.fdEndDate")
	public List<BusiFunds> findAllValidBetweenTime();*/
}
