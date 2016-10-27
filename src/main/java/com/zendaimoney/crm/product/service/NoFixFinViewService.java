/** 
 * @(#)BusiAuditDao.java 1.0.0 2013-01-11 14:53:19  
 *  
 * Copyright © 2013 证大财富.  All rights reserved.  
 */

package com.zendaimoney.crm.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.crm.product.entity.NoFixFinView;
import com.zendaimoney.crm.product.repository.NoFixFinViewDao;


/** 
* 项目名称：crm-webapp
* @ClassName: NoFixFinViewService 
* @Description: 非固续投
* @author Sam.J 
* @date 2015-7-29 下午05:16:53  
*/
@Service
@Transactional(readOnly = true)
public class NoFixFinViewService {
	@Autowired
	private NoFixFinViewDao noFixFinViewDao;

	public Page<NoFixFinView> getAllProductView(Specification<NoFixFinView> spec,
			PageRequest pageRequest) {
		return noFixFinViewDao.findAll(spec, pageRequest);
	}

	public List<NoFixFinView> findAllProductView() {
		return (List<NoFixFinView>) noFixFinViewDao.findAll();
	}

	public NoFixFinView findOne(Long id) {
		return noFixFinViewDao.findOne(id);
	}

}
