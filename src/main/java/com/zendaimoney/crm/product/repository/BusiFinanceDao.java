package com.zendaimoney.crm.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zendaimoney.crm.product.entity.BusiFinance;
import com.zendaimoney.crm.product.vo.FinanceAmountView;

/**
 * 
 * @author zhanghao
 * @create 2012-11-26, 下午04:33:18
 * @version 1.0
 */
public interface BusiFinanceDao extends
		PagingAndSortingRepository<BusiFinance, Long>,
		JpaSpecificationExecutor<BusiFinance> {

	@Query("select count(*) from BusiFinance where customerId = ?")
	Long countByCustomerId(Long customerId);

	BusiFinance findByFeLendNo(String feLendNo);
	
	@Query("select BF from BusiFinance BF where BF.feLendNo in (:feLendNo) and BF.feApplyMatchStatus in (:applyMatchStatus))")
	List<BusiFinance> findByFeLendNoAndApplyMatchStatus(@Param("feLendNo")List<String> feLendNo,@Param("applyMatchStatus") List<String> applyMatchStatus);
	
	// 修改 增加状态标志位判断 只查状态为正常的业务单 Sam.J 14.12.16
	@Query("select count(*) from BusiFinance where feContractNo =:feContractNo and feValidState='1' ")
	Long countFeContractNo(@Param("feContractNo") String feContractNo);

	@Query("select count(*) from BusiFinance where id <>:id and feContractNo =:feContractNo and feValidState='1'")
	Long countFeContractNo1(@Param("feContractNo") String feContractNo,
			@Param("id") Long id);

	List<BusiFinance> findAllByCustomerid(Long customerId);

	// 增加按照客户id和 状态位查找方法
	List<BusiFinance> findAllByCustomeridAndFeValidState(Long customerId,
			String feValidState);

	// 增加按照客户id和客户经理id再加 状态位查找方法
	List<BusiFinance> findAllByCustomeridAndFeManagerAndFeValidState(Long id,
			Long crGatherId, String feValidState);

	@Query("select b from BusiFinance b where b.feManager = :feManager and b.customerid not in (select c.id from Customer c where c.crGather = :feManager) and b.feValidState='1'")
	List<BusiFinance> findAllByfeManagerWithNotCustomer(
			@Param("feManager") Long crGatherId);

	List<BusiFinance> findAllByCustomeridAndFeManager(Long id, Long crGatherId);

	@Query("SELECT MAX(feLendNo) FROM BusiFinance WHERE CUSTOMERID = :custId")
	String findCustMaxLendNo(@Param("custId") Long custId);

	@Query("select id from BusiFinance where feLendNo =:feLendNo")
	Long getIdByFeLendNo(@Param("feLendNo") String feLendNo);

	/**
	 * 新增时根据合同编号和理财产品判断合同编号是否重复
	 * 
	 * @param feContractNo
	 * @param feProduct
	 * @return
	 */
	@Query("select count(id) from BusiFinance BF where BF.feContractNo = ? and BF.feProduct = ? and BF.fePreviousId is null and BF.feValidState='1'")
	Long countByFeContractAndFeProduct(String feContractNo, String feProduct);

	/**
	 * 修改时根据合同编号和理财产品判断合同编号是否重复
	 * 
	 * @param feContractNo
	 * @param feProduct
	 * @return
	 */
	@Query("select count(id) from BusiFinance BF where BF.feContractNo = ? and BF.feProduct = ? and id <> ? and BF.fePreviousId is null and BF.feValidState='1'")
	Long countByFeContractAndFeProduct(String feContractNo, String feProduct,
			Long id);

	/**
	 * 根据合同编号和理财产品查询理财信息(非续投)
	 * 
	 * @author Yuan Changchun
	 * @date 2013-11-1 下午02:27:26
	 * @param feContractNo
	 * @param feProduct
	 * @return
	 */
	@Query("select BF from BusiFinance BF where BF.feContractNo = ? and BF.feProduct = ? and BF.fePreviousId is null and BF.feValidState='1'")
	List<BusiFinance> findByFeContractNoAndFeProduct(String feContractNo,
			String feProduct);
	
	
	/**
	 * 查询合同编号是否存在
	 * 
	 * @author zhujj
	 * @date 2014-7-14 下午02:27:26
	 * @param feContractNo
	 * @return
	 */
	@Query("select BF from BusiFinance BF where BF.feContractNo = ?")
	List<BusiFinance> findByFeContractNo(String feContractNo);

	/**
	 * 根据业务ID列表查找到期续投理财信息(续投)
	 * 
	 * @author Yuan Changchun
	 * @date 2013-11-1 下午04:15:41
	 * @param idList
	 * @return
	 */
	@Query("select BF from BusiFinance BF where BF.fePreviousId in (:id) and BF.feValidState='1'")
	List<BusiFinance> findByIdIn(@Param("id") List<Long> id);

	/**
	 * 查询客户已申请过理财数目总数
	 * 
	 * @author Yuan Changchun
	 * @date 2013-12-10 下午04:47:29
	 * @param customerid
	 * @return
	 */
	@Query("SELECT max(substr(BF.feLendNo,-3,3)) FROM BusiFinance BF WHERE BF.customerid = :custId")
	String findCustomerFinanceMaxCount(@Param("custId") Long custId);

	/**
	 * @Title:批量根据业务合同编号查询业务
	 * @Description:
	 * @param ids
	 * @return
	 * @throws
	 * @time:2014-8-22 下午03:42:57
	 * @author:Sam.J
	 */
	@Query("SELECT BF FROM BusiFinance BF WHERE BF.feContractNo in (:ids)")
	List<BusiFinance> findFinanceByContract(@Param("ids") List<String> ids);

	/**
	 * 根据客户ID查询当前客户已生效的理财业务信息
	 * 
	 * @param customerId
	 * @return
	 */
	@Query("SELECT f from BusiFinance f  where f.customerid = :customerId  and f.feBusiState in('02000008','02000009','02000010','02000011','02000013','02000014') and f.feInvestDate is not null")
	List<BusiFinance> getBusiFinanceListByCustomerId(
			@Param("customerId") Long customerId);

	@Query("SELECT nvl(max(substr(BF.feContractNo,-6,6)),0) FROM BusiFinance BF WHERE substr(bf.feContractNo,1,3) = :contractNo")
	String findBusiFinanceMaxFeContractNo(
			@Param("contractNo") String feContractNo);

	BusiFinance findByFeLendNoAndId(String feLendNo, Long id);

	@Query("SELECT FA FROM FinanceAmountView FA WHERE FA.activityId in (:ids)")
	List<FinanceAmountView> findFinanceAmountByActivity(
			@Param("ids") List<String> ids);

	@Query("SELECT FA FROM FinanceAmountView FA WHERE FA.productId in (:ids)")
	List<FinanceAmountView> findFinanceAmountByProduct(
			@Param("ids") List<String> ids);

	/**
	 * 增加按照客户ID和 产品ID和 状态位查找方法
	 * 
	 * @param customerId
	 * @return
	 * @author liyez
	 */
	List<BusiFinance> findAllByCustomeridAndFeProductAndFeValidState(
			Long customerId, String feProduct, String feValidState);
	
	
	/** 
	* @Title: countFeContractNo1 
	* @Description: 按照产品搜索理财条数
	* @return Long    返回类型 
	* @author Sam.J
	* @date 2015-7-9 下午04:29:10 
	* @throws 
	*/
	@Query("select count(*) from BusiFinance where feProduct=:feProduct ")
	Long countByFeProduct(@Param("feProduct") String feProduct);
	
	
	//add by chenjial 2016/5/4 thumb2 start
	/** 
	* @Title: countFeContractNo1 
	* @Description: 查大拇指APP2.0的客户投资作废次数
	* @return Long    返回类型 
	* @author chenjl
	* @date 2016-5-9 下午04:29:10 
	* @throws 
	*/
	@Query("select count(*) from BusiFinance where customerId = ? and feBusiState = '02000012' and feDataOrigin='2' and feAppOrderCancelFlag='1' ")
	Long countByCustomerIdAndBusiState(Long customerId);
	
	/**
	 * 根据客户ID查询理财业务
	 * 
	 * @author chenjl
	 * @date 2016-6-1 下午02:27:26
	 * @param feContractNo
	 * @return
	 */
	@Query("select BF from BusiFinance BF where BF.customerid = ? and BF.feBusiState = '02000012' and BF.feDataOrigin='2' and BF.feAppOrderCancelFlag='1' ")
	List<BusiFinance> findByCustomerId(Long customerid);
	

}
