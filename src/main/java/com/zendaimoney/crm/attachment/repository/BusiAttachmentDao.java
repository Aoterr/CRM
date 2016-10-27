package com.zendaimoney.crm.attachment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zendaimoney.crm.attachment.entity.BusiAttachment;

public interface BusiAttachmentDao extends
		PagingAndSortingRepository<BusiAttachment, Long>,
		JpaSpecificationExecutor<BusiAttachment> {
	List<BusiAttachment> findByIdIn(List<Long> ids);

	@Query("from BusiAttachment where customerid=?")
	List<BusiAttachment> findAllAttachmentByCusId(Long customerid);

	@Query("SELECT BA FROM BusiAttachment BA, BusiRequestAttach BQA WHERE BA.id = BQA.raAttachment AND BQA.raBusi =:busi AND BA.atTypeOne =:atTypeOne AND BA.atType =:atType AND BA.atState='1' ")
	List<BusiAttachment> findAttachmentByBusiIdAndOneTypeAndSecondType(
			@Param("busi") Long busi, @Param("atTypeOne") String atTypeOne,
			@Param("atType") String atType);

	/**
	 * 根据业务ID,附件类型以及附件名称查找附件列表(根据时间的由近及远排序)
	 * 
	 * @author Yuan Changchun
	 * @date 2013-9-26 下午03:42:03
	 * @param raBusi
	 * @param atTypeOne
	 * @param atType
	 * @param atName
	 */
	@Query("SELECT BA FROM BusiAttachment BA, BusiRequestAttach BQA WHERE BA.id = BQA.raAttachment AND BQA.raBusi =:raBusi AND BA.atTypeOne =:atTypeOne AND BA.atType =:atType AND BA.atName=:atName AND BA.atState='1' ORDER BY BA.atCratetime DESC")
	List<BusiAttachment> findAllAttachmentByTypeAndName(
			@Param("raBusi") Long raBusi, @Param("atTypeOne") String atTypeOne,
			@Param("atType") String atType, @Param("atName") String atName);
}
