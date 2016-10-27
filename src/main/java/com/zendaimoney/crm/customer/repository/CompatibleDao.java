package com.zendaimoney.crm.customer.repository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zendaimoney.crm.customer.entity.Compatible;

public interface CompatibleDao extends
		PagingAndSortingRepository<Compatible, Long> {
	// 查询是否绑定
	@Query("select count(ID) from Compatible c where c.CoStatus=1 and c.CoBinding=1 and c.CoThirdid=?")
	public int findbindBycustomerId(Long id);

	// 解绑
	@Lock(LockModeType.READ)
	@Modifying
	@Query("update Compatible c set c.CoBinding=2,c.CoStatus=2 where c.CoThirdid=:Id")
	public int rebindBycustomerId(@Param("Id") Long id);

}
