package com.zendaimoney.crm.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.crm.customer.entity.PrivateOwners;
import com.zendaimoney.crm.customer.repository.PrivateOwnersDao;

@Component
@Transactional(readOnly = true)
public class PrivateOwnersService {
	@Autowired
	private PrivateOwnersDao privateOwnersDao;

	public PrivateOwners getPrivateOwners(Long id) {
		return privateOwnersDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public PrivateOwners savePrivateOwners(PrivateOwners entity) {
		if (entity.getId() == null) {
			return privateOwnersDao.save(entity);
		} else {
			PrivateOwners privateOwners = this.getPrivateOwners(entity.getId());
			privateOwners.setPoCompanyRegtime(entity.getPoCompanyRegtime());
			privateOwners.setPoCompanySize(entity.getPoCompanySize());
			privateOwners.setPoCompanyType(entity.getPoCompanyType());
			privateOwners.setPoCompanyOther(entity.getPoCompanyOther());
			privateOwners.setPoNetIncome(entity.getPoNetIncome());
			privateOwners.setPoNetIncomeMonth(entity.getPoNetIncomeMonth());
			privateOwners.setPoRoom(entity.getPoRoom());
			privateOwners.setPoShareholdingRatio(entity
					.getPoShareholdingRatio());
			return privateOwnersDao.save(privateOwners);
		}
	}

	@Transactional(readOnly = false)
	public void deletePrivateOwners(Long id) {
		privateOwnersDao.delete(id);
	}

	@Transactional(readOnly = false)
	public void deletePrivateOwners(Long[] ids) {
		for (Long id : ids) {
			privateOwnersDao.delete(id);
		}
	}

	public List<PrivateOwners> getAllPrivateOwners() {
		return (List<PrivateOwners>) privateOwnersDao.findAll();
	}

	public Page<PrivateOwners> getPrivateOwners(PageRequest pageRequest) {
		return privateOwnersDao.findAll(pageRequest);
	}

	public PrivateOwners findByCustomerid(Long id) {
		// TODO Auto-generated method stub
		return privateOwnersDao.findByCustomerid(id);
	}

	public List<PrivateOwners> findAllByCustomerid(Long id) {
		return privateOwnersDao.findByCustomeridAll(id);
	}
}