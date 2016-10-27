/** 
 * @(#)BusiAuditDao.java 1.0.0 2013-01-11 14:53:19  
 *  
 * Copyright © 2013 证大财富.  All rights reserved.  
 */

package com.zendaimoney.crm.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.crm.product.entity.BusiRedeemView;
import com.zendaimoney.crm.product.repository.FinRedeemViewDao;


/** 
* 项目名称：crm-webapp
* @ClassName: NoFixFinViewService 
* @Description: 非固续投
* @author Sam.J 
* @date 2015-7-29 下午05:16:53  
*/
@Service
@Transactional(readOnly = true)
public class FinRedeemViewService {
	@Autowired
	private FinRedeemViewDao finRedeemViewDao;

	public Page<BusiRedeemView> getAllProductView(Specification<BusiRedeemView> spec,
			PageRequest pageRequest) {
		return finRedeemViewDao.findAll(spec, pageRequest);
    }
	
}
