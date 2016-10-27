package com.zendaimoney.crm.remind.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.crm.remind.entity.Remind;

public interface RemindDao extends PagingAndSortingRepository<Remind, Long>,
		JpaSpecificationExecutor<Remind> {

	public List<Remind> findByDailyRemindType(String type);

	@Modifying
	@Query("delete from Remind where dailyRemindType=?")
	public void deleteByDailyRemindType(String type);

}
