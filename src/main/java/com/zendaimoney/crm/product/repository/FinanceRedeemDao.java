package com.zendaimoney.crm.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.crm.product.entity.BusiNofixFinance;
import com.zendaimoney.crm.product.entity.BusiRedeem;

/**
 * 项目名称：crm-webapp
 * 
 * @ClassName: FinanceRedeemDao
 * @Description: 非固赎回
 * @author Sam.J
 * @date 2016-1-21 下午05:17:27
 */
public interface FinanceRedeemDao extends PagingAndSortingRepository<BusiRedeem, Long>,JpaSpecificationExecutor<BusiRedeem> {
	
	/** 
	* @Title: findByFxBusiNo 
	* @Description: 根据出借编号查询赎回信息
	* @param reBusiNo
	* @return List<BusiRedeem>(描述入参以及返回参数的含义)
	* @author Sam.J
	* @date 2016年1月25日 下午2:30:17 
	* @throws 
	*/
	@Query("select B from BusiRedeem B where B.reBusiNo = ? and B.reValid=1")
	List<BusiRedeem> findByFxBusiNo(String reBusiNo);
	
	
	/** 
	* @Title: findByFxBusiId 
	* @Description: 根据业务id查赎回信息
	* @param reBusiId
	* @return List<BusiRedeem>(描述入参以及返回参数的含义)
	* @author Sam.J
	* @date 2016年1月27日 上午9:35:27 
	* @throws 
	*/
	@Query("select B from BusiRedeem B where B.reBusiId = ? and B.reValid=1")
	List<BusiRedeem> findByReBusiId(Long reBusiId);
	
	/**
	 * 根据出借编号查询赎回信息，按录入时间倒序
	 * @param reBusiNo
	 * @return
	 */
	@Query("select B from BusiRedeem B where B.reBusiNo = ? and B.reValid=1 order by B.reInputTime desc")
	List<BusiRedeem> findLatestByReBusiNo(String reBusiNo);
}
