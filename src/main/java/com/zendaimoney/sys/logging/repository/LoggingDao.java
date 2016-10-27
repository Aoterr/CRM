package com.zendaimoney.sys.logging.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.sys.logging.entity.Logging;
public interface LoggingDao extends PagingAndSortingRepository<Logging, Long> , JpaSpecificationExecutor<Logging>{

	@Query("select t from sys_log t order by t.id")
	List<Logging> getAllLoggingOrderById();
}
