package com.zendaimoney.crm.remind.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.crm.remind.repository.RemindCustomerDao;

/**
 * 
 * @author Yuan Changchun
 * 
 */
@Component
@Transactional(readOnly = true)
public class RemindCustomerService {

	@Autowired
	private RemindCustomerDao remindCustomerDao;
	@PersistenceContext
	private EntityManager em;

	public List<Long> getCustomerIdsByRemindId(Long remindId) {
		return this.remindCustomerDao.findByRemindId(remindId);
	}
}
