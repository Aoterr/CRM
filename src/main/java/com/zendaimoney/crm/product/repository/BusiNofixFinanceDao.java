package com.zendaimoney.crm.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zendaimoney.crm.product.entity.BusiNofixFinance;

/**
 * 项目名称：crm-webapp
 * 
 * @ClassName: NoFixFinViewDao
 * @Description: 非固续投
 * @author Sam.J
 * @date 2015-7-29 下午05:17:27
 */
public interface BusiNofixFinanceDao extends PagingAndSortingRepository<BusiNofixFinance, Long>,JpaSpecificationExecutor<BusiNofixFinance> {
	
	
	/** 
	* @Title: findByFxBusiNo 
	* @Description: 根据出借编号查询续投信息
	* @return List<BusiNofixFinance>    返回类型 
	* @author Sam.J
	* @date 2015-8-5 下午02:08:47 
	* @throws 
	*/
	@Query("select B from BusiNofixFinance B where fxBusiNo = ? and B.fxValid=1")
	List<BusiNofixFinance> findByFxBusiNo(String fxBusiNo);
	
	

	@Query("select B from BusiNofixFinance B where fxContractNo = ? and B.fxValid=1")
	List<BusiNofixFinance> findByFxContractNo(String fxContractNo);
	
	/**
	 * 查询有效续期,按续期次数倒序
	 * @param fxBusiNo
	 * @param status
	 * @return
	 */
	@Query("select B from BusiNofixFinance B where fxBusiNo = :fxBusiNo and B.fxValid=1 and B.fxState in (:status) order by B.fxConCount desc")
	List<BusiNofixFinance> findLatestByFxBusiNo(@Param("fxBusiNo")String fxBusiNo,@Param("status")List<String> status);
	
	
}
