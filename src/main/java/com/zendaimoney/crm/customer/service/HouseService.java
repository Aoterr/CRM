package com.zendaimoney.crm.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.crm.customer.entity.House;
import com.zendaimoney.crm.customer.repository.HouseDao;

@Component
@Transactional(readOnly = true)
public class HouseService {
	@Autowired
	private HouseDao houseDao;

	public House getHouse(Long id) {
		return houseDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public House saveHouse(House entity) {
		if (entity.getId() == null) {
			return houseDao.save(entity);
		} else {
			House house = this.getHouse(entity.getId());
			house.setHeAddrDetail(entity.getHeAddrDetail());
			house.setHeBuildingArea(entity.getHeBuildingArea());
			house.setHeBuyDate(entity.getHeBuyDate());
			house.setHeCity(entity.getHeCity());
			house.setHeBuyprice(entity.getHeBuyprice());
			house.setHeCounty(entity.getHeCounty());
			house.setHeLoanBalance(entity.getHeLoanBalance());
			house.setHeLoanTerm(entity.getHeLoanTerm());
			house.setHeMonthRepayment(entity.getHeMonthRepayment());
			house.setHeProvince(entity.getHeProvince());
			house.setHeRatio(entity.getHeRatio());
			house.setHeType(entity.getHeType());
			house.setHeUnitPrice(entity.getHeUnitPrice());
			house.setHeZipCode(entity.getHeZipCode());

			house.setHeHouseRent(entity.getHeHouseRent());
			return houseDao.save(house);
		}
	}

	@Transactional(readOnly = false)
	public void deleteHouse(Long id) {
		houseDao.delete(id);
	}

	@Transactional(readOnly = false)
	public void deleteHouse(Long[] ids) {
		for (Long id : ids) {
			houseDao.delete(id);
		}
	}

	public List<House> getAllHouse() {
		return (List<House>) houseDao.findAll();
	}

	public Page<House> getHouse(PageRequest pageRequest) {
		return houseDao.findAll(pageRequest);
	}

	public House findByCustomerid(Long id) {
		// TODO Auto-generated method stub
		return houseDao.findByCustomerid(id);
	}

	public List<House> findAllByCustomerid(Long id) {
		return houseDao.findByCustomeridAll(id);
	}

	// 根据客户id删除房产信息
	@Transactional(readOnly = false)
	public void deleteHouseByCustomerId(Long customerId) {
		houseDao.deleteHouseByCustomerId(customerId);
	}
}
