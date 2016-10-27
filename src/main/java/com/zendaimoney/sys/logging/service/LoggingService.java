package com.zendaimoney.sys.logging.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.sys.logging.entity.Logging;
import com.zendaimoney.sys.logging.repository.LoggingDao;

@Component
@Transactional(readOnly = true)
public class LoggingService {
	@Autowired
	private LoggingDao loggingDao;

	public Logging getLogging(Long id) {
		return loggingDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public void saveLogging(Logging entity) {
		loggingDao.save(entity);
	}

	@Transactional(readOnly = false)
	public void deleteLogging(Long id) {
		loggingDao.delete(id);
	}
	@Transactional(readOnly = false)
	public void deleteLogging(Long[] ids) {
		for (Long id : ids) {
			loggingDao.delete(id);
		} 
	}

	public List<Logging> getAllLogging() {
		return (List<Logging>) loggingDao.findAll();
	}

	public Page<Logging> getLogging(Specification<Logging> spec,PageRequest pageRequest ) {
		return loggingDao.findAll(spec,pageRequest);
	}

	public List<Logging> getAllLoggingOrderById() {
		return (List<Logging>) loggingDao.getAllLoggingOrderById();
	} 
	 
}
