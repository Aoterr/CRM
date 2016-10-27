/** 
 * @(#)AuditController.java 1.0.0 2013-02-25 15:43:54  
 *  
 * Copyright © 2013 证大财富.  All rights reserved.  
 */

package com.zendaimoney.crm.product.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zendaimoney.crm.CrudUiController;
import com.zendaimoney.crm.product.entity.BusiAudit;
import com.zendaimoney.crm.product.service.BusiAuditService;


/**
 * Class AuditController
 * 
 * @author zhanghao
 * @version $Revision:1.0.0, $Date: 2013-02-25 15:43:54 $
 */
@Controller
@RequestMapping(value = "/audit")
public class AuditController extends CrudUiController<BusiAudit> {
	@Autowired
	private BusiAuditService auditService;

	// 获取审核日志
	@ResponseBody
	@RequestMapping(value = "/find/audit/log")
	public List<BusiAudit> findAllAuditByModificationId(
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "checkType") String checkType) {
		List<BusiAudit> busiAuditList = auditService.findAllByAtBusiAndAtType(
				id, type);
		List<BusiAudit> busiAuditList2 = new ArrayList<BusiAudit>();
		if (busiAuditList != null) {
			if (checkType != null && checkType != "") {
				if (checkType.equals("1")) { // 复检
					for (BusiAudit ba : busiAuditList) {
						if (Integer.valueOf(ba.getAtNextState()) > 6) {
							busiAuditList2.add(ba);
						}
					}
				} else {
					for (BusiAudit ba : busiAuditList) {
						if (Integer.valueOf(ba.getAtNextState()) <= 6) {
							busiAuditList2.add(ba);
						}
					}
				}
				return busiAuditList2;
			}
		}
		return busiAuditList;
	}

}
