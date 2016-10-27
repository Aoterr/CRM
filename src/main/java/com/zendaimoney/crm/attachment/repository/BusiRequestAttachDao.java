package com.zendaimoney.crm.attachment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zendaimoney.crm.attachment.entity.BusiRequestAttach;

public interface BusiRequestAttachDao extends
		PagingAndSortingRepository<BusiRequestAttach, Long>,
		JpaSpecificationExecutor<BusiRequestAttach> {
	@Query("select raAttachment from BusiRequestAttach where raType=:type and raBusi=:id")
	List<Long> findAllRequestAttach(@Param("type") String type,
			@Param("id") Long id);

	@Query("select raAttachment from BusiRequestAttach where raBusi in(:id)")
	List<Long> findAllRequestAttach(@Param("id") List<Long> id);

	@Query("select raAttachment from BusiRequestAttach where raBusi=:id)")
	List<Long> findAllRequestAttach(@Param("id") Long id);

	@Query("select raAttachment from BusiRequestAttach where raBusi=:busiId)")
	List<Long> findAllRequestAttachByBusiId(@Param("busiId") Long busiId);

}
