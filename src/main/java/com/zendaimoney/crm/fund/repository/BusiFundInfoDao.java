package com.zendaimoney.crm.fund.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zendaimoney.crm.fund.entity.BusiFundInfo;
import com.zendaimoney.crm.product.entity.BusiFinance;
import com.zendaimoney.crm.product.vo.FinanceAmountView;

/**
 * 
 * @author zhanghao
 * @create 2012-11-26, 下午04:33:18
 * @version 1.0
 */
public interface BusiFundInfoDao extends
		PagingAndSortingRepository<BusiFundInfo, Long>,
		JpaSpecificationExecutor<BusiFundInfo> {

	@Query("select count(*) from BusiFundInfo where customerId = ?")
	Long countByCustomerId(Long customerId);

	BusiFundInfo findByFdLendNo(String fdLendNo);

	// 修改 增加状态标志位判断 只查状态为正常的业务单 Sam.J 14.12.16
	@Query("select count(*) from BusiFundInfo where fdContractNo =:fdContractNo and fdValidState='1' ")
	Long countFdContractNo(@Param("fdContractNo") String fdContractNo);

	@Query("select count(*) from BusiFundInfo where id <>:id and fdContractNo =:fdContractNo and fdValidState='1'")
	Long countFdContractNo1(@Param("fdContractNo") String fdContractNo,
			@Param("id") Long id);

	List<BusiFundInfo> findAllByCustomerid(Long customerId);

	// 增加按照客户id和 状态位查找方法
	List<BusiFundInfo> findAllByCustomeridAndFdValidState(Long customerId,
			String fdValidState);

	// 增加按照客户id和客户经理id再加 状态位查找方法
	List<BusiFundInfo> findAllByCustomeridAndFdManagerAndFdValidState(Long id,
			Long crGatherId, String fdValidState);

	@Query("select b from BusiFundInfo b where b.fdManager = :fdManager and b.customerid not in (select c.id from Customer c where c.crGather = :fdManager) and b.fdValidState='1'")
	List<BusiFundInfo> findAllByfdManagerWithNotCustomer(
			@Param("fdManager") Long crGatherId);

	List<BusiFundInfo> findAllByCustomeridAndFdManager(Long id, Long crGatherId);

	@Query("SELECT MAX(fdLendNo) FROM BusiFundInfo WHERE CUSTOMERID = :custId")
	String findCustMaxLendNo(@Param("custId") Long custId);

	@Query("select id from BusiFundInfo where fdLendNo =:fdLendNo")
	Long getIdByFdLendNo(@Param("fdLendNo") String fdLendNo);

	/**
	 * 新增时根据合同编号和理财产品判断合同编号是否重复
	 * 
	 * @param fdContractNo
	 * @param fdProduct
	 * @return
	 */
	@Query("select count(id) from BusiFundInfo BF where BF.fdContractNo = ? and BF.fdProduct = ? and BF.fdPreviousId is null and BF.fdValidState='1'")
	Long countByFdContractAndFdProduct(String fdContractNo, String fdProduct);

	/**
	 * 修改时根据合同编号和理财产品判断合同编号是否重复
	 * 
	 * @param fdContractNo
	 * @param fdProduct
	 * @return
	 */
	@Query("select count(id) from BusiFundInfo BF where BF.fdContractNo = ? and BF.fdProduct = ? and id <> ? and BF.fdPreviousId is null and BF.fdValidState='1'")
	Long countByFdContractAndFdProduct(String fdContractNo, String fdProduct,
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
	@Query("select BF from BusiFundInfo BF where BF.fdContractNo = ? and BF.fdProduct = ? and BF.fdPreviousId is null and BF.fdValidState='1'")
	List<BusiFundInfo> findByFdContractNoAndFdProduct(String fdContractNo,
			String fdProduct);
	
	
	/**
	 * 查询合同编号是否存在
	 * 
	 * @author zhujj
	 * @date 2014-7-14 下午02:27:26
	 * @param feContractNo
	 * @return
	 */
	@Query("select BF from BusiFundInfo BF where BF.fdContractNo = ?")
	List<BusiFundInfo> findByFdContractNo(String fdContractNo);

	/**
	 * 根据业务ID列表查找到期续投理财信息(续投)
	 * 
	 * @author Yuan Changchun
	 * @date 2013-11-1 下午04:15:41
	 * @param idList
	 * @return
	 */
	@Query("select BF from BusiFundInfo BF where BF.fdPreviousId in (:id) and BF.fdValidState='1'")
	List<BusiFundInfo> findByIdIn(@Param("id") List<Long> id);

	/**
	 * 查询客户已申请过理财数目总数
	 * 
	 * @author Yuan Changchun
	 * @date 2013-12-10 下午04:47:29
	 * @param customerid
	 * @return
	 */
	@Query("SELECT max(substr(BF.fdLendNo,-3,3)) FROM BusiFundInfo BF WHERE BF.customerid = :custId")
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
	@Query("SELECT BF FROM BusiFundInfo BF WHERE BF.fdContractNo in (:ids)")
	List<BusiFundInfo> findFinanceByContract(@Param("ids") List<String> ids);

	/**
	 * 根据客户ID查询当前客户已生效的理财业务信息
	 * 
	 * @param customerId
	 * @return
	 */
	@Query("SELECT f from BusiFundInfo f  where f.customerid = :customerId  and f.fdBusiState in('02000008','02000009','02000010','02000011','02000013','02000014') and f.fdInvestDate is not null")
	List<BusiFundInfo> getBusiFundInfoListByCustomerId(
			@Param("customerId") Long customerId);

	@Query("SELECT nvl(max(substr(BF.fdContractNo,-6,6)),0) FROM BusiFundInfo BF WHERE substr(bf.fdContractNo,1,3) = :contractNo")
	String findBusiFundInfoMaxFdContractNo(
			@Param("contractNo") String fdContractNo);

	BusiFundInfo findByFdLendNoAndId(String fdLendNo, Long id);

//	@Query("SELECT FA FROM FinanceAmountView FA WHERE FA.productId in (:ids)")
//	List<FinanceAmountView> findFinanceAmountByProduct(
//			@Param("ids") List<String> ids);

	/**
	 * 增加按照客户ID和 产品ID和 状态位查找方法
	 * 
	 * @param customerId
	 * @return
	 * @author liyez
	 */
	List<BusiFundInfo> findAllByCustomeridAndFdProductAndFdValidState(
			Long customerId, String fdProduct, String fdValidState);
	
	
	/** 
	* @Title: countFeContractNo1 
	* @Description: 按照产品搜索理财条数
	* @return Long    返回类型 
	* @author Sam.J
	* @date 2015-7-9 下午04:29:10 
	* @throws 
	*/
	@Query("select count(*) from BusiFundInfo where fdProduct=:fdProduct ")
	Long countByFdProduct(@Param("fdProduct") String fdProduct);
	

}
