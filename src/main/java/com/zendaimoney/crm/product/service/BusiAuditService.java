/** 
 * @(#)BusiAuditService.java 1.0.0 2013-02-25 15:39:27  
 *  
 * Copyright © 2013 证大财富.  All rights reserved.  
 */

package com.zendaimoney.crm.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.crm.product.entity.BusiAudit;
import com.zendaimoney.crm.product.repository.BusiAuditDao;

/**
 * Class BusiAuditService
 * 
 * @author zhanghao
 * @version $Revision:1.0.0, $Date: 2013-02-25 15:39:27 $
 */
@Service
@Transactional(readOnly = true)
public class BusiAuditService {
	@Autowired
	private BusiAuditDao auditDao;

	public List<BusiAudit> findAllByAtBusiAndAtType(Long id, String type) {
		return auditDao
				.findAllByAtBusiAndAtTypeOrderByAtInputTimeDesc(id, type);
	}
}
