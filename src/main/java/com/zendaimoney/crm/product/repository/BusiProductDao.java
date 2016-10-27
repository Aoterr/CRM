package com.zendaimoney.crm.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zendaimoney.crm.product.entity.BusiProduct;
import com.zendaimoney.crm.product.entity.BusiProductConsume;

/**
 * 
 * @author zhanghao
 * @create 2012-11-29, 下午02:05:02
 * @version 1.0
 */
public interface BusiProductDao extends
		PagingAndSortingRepository<BusiProduct, Long>,
		JpaSpecificationExecutor<BusiProduct> {
	@Query("from BusiProduct pt where pt.ptProductType = ?")
	public List<BusiProduct> findAllProductByType(String type);

	/**
	 * 根据产品的id查找产品信息
	 * 
	 * @author wb_lyq
	 * @date 2015-10-28 下午03:31:35
	 * @param BusiProduct
	 * @return
	 */
	@Query("from BusiProduct pt where pt.id in (:ids) ")
	public List<BusiProduct> findAllProductById(@Param("ids")List<Long> ids);
	
	/**
	 * 根据产品的code查找产品信息
	 * 
	 * @author Yuan Changchun
	 * @date 2014-3-31 下午03:31:35
	 * @param ptProductCode
	 * @return
	 */
	@Query("from BusiProduct pt where pt.ptProductCode = ?")
	public BusiProduct findOneByPtProductCode(String ptProductCode);

	/**
	 * @Title:根据产品type搜索出可用的所有产品信息
	 * @Description: TODO
	 * @param type
	 * @return
	 * @throws
	 * @time:2014-11-14 下午03:19:22
	 * @author:Sam.J
	 */
	@Query("from BusiProduct pt where pt.ptProductType in (:type)  and pt.ptIsDel='1' and pt.ptValid='1'")
	public List<BusiProduct> findProductByTypeNotDel(
			@Param("type") List<String> type);

	/**
	 * @Title:根据产品type搜索出所有产品信息
	 * @Description: TODO
	 * @param type
	 * @return
	 * @throws
	 * @time:2014-12-11 下午03:19:22
	 * @author:Sam.J
	 */
	@Query("from BusiProduct pt where pt.ptProductType in (:type)  and pt.ptIsDel='1'")
	public List<BusiProduct> findProductByTypes(@Param("type") List<String> type);

	/**
	 * @Title: 根据产品type搜索出所有消费产品
	 * @Description: TODO
	 * @param @param type
	 * @return List<BusiProductConsume> 返回类型
	 * @throws
	 * @author liyez
	 * @date 2015年1月20日 下午8:58:52
	 */
	@Query("from BusiProductConsume pt where pt.ptProductType in (:type)  and pt.ptIsDel='1' and pt.ptValid='1'")
	public List<BusiProductConsume> findBusiProductConsumeByType(
			@Param("type") List<String> type);
	
	//add by chenjiale 2016/5/7
	/**
	 * @Title: 根据产品type搜索出所有产品
	 * @Description: TODO
	 * @param @param type
	 * @return List<Object[]> 返回类型
	 * @throws
	 * @author liyez
	 * @date 2015年1月20日 下午8:58:52
	 */
	@Query(nativeQuery = true, value = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (select bp.id,bp.pt_product_name,bp.pt_min_amount,bp.pt_min_yield,bp.pt_max_yield,bp.pt_min_term,bp.PT_MEMO1,bp.PT_MEMO2,bp.PT_MEMO3,bpe.product_type,bpe.is_hot,nvl(bpe.invest_valid_nums,0),nvl(bpe.order_nums,0),bp.PT_MAX_AMOUNT from busi_product bp left join busi_product_ext bpe on bp.id=bpe.product_id where bp.pt_product_type=2 and  bpe.product_type= :type and bp.pt_buy_flag='1' and bp.pt_valid='1' ) A WHERE ROWNUM < :endNo order by a.id) WHERE RN >= :startNo ")
	public List<Object[]> findBusiProductByType(@Param("endNo") int endNo, @Param("startNo") int startNo, @Param("type") String type);

	/**
	 * @Title: 查询主打产品
	 * @Description: TODO
	 * @param @param type
	 * @return List<Object[]> 返回类型
	 * @throws
	 * @author liyez
	 * @date 2015年1月20日 下午8:58:52
	 */
	@Query(nativeQuery = true, value = "select bp.id,bp.pt_product_name,bp.pt_min_amount,bp.pt_min_yield,bp.pt_max_yield,bp.pt_min_term,bp.PT_MEMO1,bp.PT_MEMO2,bp.PT_MEMO3,bpe.product_type,bpe.is_hot,nvl(bpe.invest_valid_nums,0),nvl(bpe.order_nums,0),bp.PT_MAX_AMOUNT from busi_product bp left join busi_product_ext bpe on bp.id=bpe.product_id where bp.pt_product_type=2  and bpe.is_hot='0' and bp.pt_buy_flag='1' and bp.pt_valid='1' ")
	public List<Object[]> findBusiProductHot();
	
	/**
	 * @Title: 通过产品ID查询主打产品
	 * @Description: TODO
	 * @param @param type
	 * @return List<Object[]> 返回类型
	 * @throws
	 * @author liyez
	 * @date 2015年1月20日 下午8:58:52
	 */
	@Query(nativeQuery = true, value = "select bp.id,bp.pt_product_name,bp.pt_min_amount,bp.pt_min_yield,bp.pt_max_yield,bp.pt_min_term,bp.PT_MEMO1,bp.PT_MEMO2,bp.PT_MEMO3,bpe.product_type,bpe.is_hot,nvl(bpe.invest_valid_nums,0),nvl(bpe.order_nums,0),bp.PT_MAX_AMOUNT from busi_product bp left join busi_product_ext bpe on bp.id=bpe.product_id where bp.pt_product_type=2  and bpe.is_hot='0' and bp.pt_buy_flag='1' and bp.pt_valid='1' and bp.id= :id ")
	public List<Object[]> findBusiProductHotById(@Param("id") String id);
	
	/**
	 * @Title: 查询所有可购买产品
	 * @Description: TODO
	 * @param @param type
	 * @return List<Object[]> 返回类型
	 * @throws
	 * @author liyez
	 * @date 2015年1月20日 下午8:58:52
	 */
	@Query(nativeQuery = true, value = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (select bp.id,bp.pt_product_name,bp.pt_min_amount,bp.pt_min_yield,bp.pt_max_yield,bp.pt_min_term,bp.PT_MEMO1,bp.PT_MEMO2,bp.PT_MEMO3,bpe.product_type,bpe.is_hot,nvl(bpe.invest_valid_nums,0),nvl(bpe.order_nums,0),bp.PT_MAX_AMOUNT from busi_product bp left join busi_product_ext bpe on bp.id=bpe.product_id where bp.pt_product_type=2 and bp.pt_buy_flag='1' and bp.pt_valid='1' ) A WHERE ROWNUM < :endNo order by a.id) WHERE RN >= :startNo ")
	public List<Object[]> findAllBusiProduct(@Param("endNo") int endNo, @Param("startNo") int startNo);
	
	/**
	 * @Title: 根据产品产品代码查询所有可购买产品
	 * @Description: TODO
	 * @param @param type
	 * @return List<Object[]> 返回类型
	 * @throws
	 * @author liyez
	 * @date 2015年1月20日 下午8:58:52
	 */
	@Query(nativeQuery = true, value = "select bp.id,bp.pt_product_name,bp.pt_min_amount,bp.pt_min_yield,bp.pt_max_yield,bp.pt_min_term,bp.PT_MEMO1,bp.PT_MEMO2,bp.PT_MEMO3,bpe.product_type,bpe.is_hot,nvl(bpe.invest_valid_nums,0),nvl(bpe.order_nums,0),bp.PT_MAX_AMOUNT from busi_product bp left join busi_product_ext bpe on bp.id=bpe.product_id where bp.pt_product_type=2  and bp.pt_buy_flag='1' and bp.pt_valid='1' and bp.id= :id ")
	public List<Object[]> findBusiProductById(@Param("id") String id);
	
	/**
	 * @Title: 根据类型和产品代码查询
	 * @Description: TODO
	 * @param @param type
	 * @return List<Object[]> 返回类型
	 * @throws
	 * @author liyez
	 * @date 2015年1月20日 下午8:58:52
	 */
	@Query(nativeQuery = true, value = "select bp.id,bp.pt_product_name,bp.pt_min_amount,bp.pt_min_yield,bp.pt_max_yield,bp.pt_min_term,bp.PT_MEMO1,bp.PT_MEMO2,bp.PT_MEMO3,bpe.product_type,bpe.is_hot,nvl(bpe.invest_valid_nums,0),nvl(bpe.order_nums,0),bp.PT_MAX_AMOUNT from busi_product bp left join busi_product_ext bpe on bp.id=bpe.product_id where bp.pt_product_type=2  and bp.pt_buy_flag='1' and bp.pt_valid='1' and bp.id= :id and bpe.product_type= :type ")
	public List<Object[]> findBusiProductByIdAndType(@Param("id") String id, @Param("type") String type);
	
	/**
	 * @Title:根据产品ID查询产品信息
	 * @Description: TODO
	 * @param id
	 * @return
	 * @throws
	 * @time:2014-11-14 下午03:19:22
	 * @author:Sam.J
	 */
	@Query("from BusiProduct pt where pt.id = :id  and pt.ptIsDel='1' and pt.ptValid='1' and pt.ptBuyFlag='1' ")
	public BusiProduct findProductByIdAndValid(@Param("id") Long id);

}
