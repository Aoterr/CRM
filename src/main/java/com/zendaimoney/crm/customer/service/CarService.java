package com.zendaimoney.crm.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.crm.BaseService;
import com.zendaimoney.crm.customer.entity.Car;
import com.zendaimoney.crm.customer.repository.CarDao;

@Component
@Transactional(readOnly = true)
public class CarService extends BaseService<Car> {
	@Autowired
	private CarDao carDao;

	public Car getCar(Long id) {
		return carDao.findOne(id);
	}

	// 添加房产信息
	@Transactional(readOnly = false)
	public void addCar(Long id, Car car) {
		if (car != null) {
			car.setCustomerid(id);
			saveCar(car);
		}

	}

	@Transactional(readOnly = false)
	public Car saveCar(Car entity) {
		if (entity.getId() == null) {
			return carDao.save(entity);
		} else {
			Car car = this.getCar(entity.getId());
			car.setCaType(entity.getCaType());
			car.setCaBuyDate(entity.getCaBuyDate());
			car.setCaPice(entity.getCaPice());
			car.setCaHaveLoan(entity.getCaHaveLoan());
			car.setCaLoanTerm(entity.getCaLoanTerm());
			car.setCaMonthRepayment(entity.getCaMonthRepayment());
			return carDao.save(car);
		}
	}

	public List<Car> findAllByCustomerid(Long id) {
		return carDao.findByCustomeridAll(id);
	}
}
