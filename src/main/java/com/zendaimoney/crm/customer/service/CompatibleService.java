package com.zendaimoney.crm.customer.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.zendaimoney.crm.BaseService;
import com.zendaimoney.crm.customer.entity.Compatible;
import com.zendaimoney.crm.customer.repository.CompatibleDao;

public class CompatibleService extends BaseService<Compatible> {

	@Autowired
	private CompatibleDao compatibaleDao;

}
