/** 
 * @(#)BusiAuditDao.java 1.0.0 2013-01-11 14:53:19  
 *  
 * Copyright © 2013 证大财富.  All rights reserved.  
 */

package com.zendaimoney.crm.fund.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.crm.fund.entity.FundsProductView;
import com.zendaimoney.crm.fund.repository.FundsProductViewDao;


/**
 * Class FundsProductViewService
 * 基金业务视图
 * @author CJ
 * @version $Revision:1.0.0, $Date: 2013-01-11 14:53:19 $
 */
@Service
@Transactional(readOnly = true)
public class FundsProductViewService {
	@Autowired
	private FundsProductViewDao productViewDao;

	public Page<FundsProductView> getAllProductView(Specification<FundsProductView> spec,
			PageRequest pageRequest) {
		return productViewDao.findAll(spec, pageRequest);
	}

	public List<FundsProductView> findAllProductView() {
		return (List<FundsProductView>) productViewDao.findAll();
	}

	public FundsProductView findOne(Long id) {
		return productViewDao.findOne(id);
	}

}
