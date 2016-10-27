/** 
 * @(#)CustomerBirthDayRemindController.java 1.0.0 2013-8-20 下午06:02:29  
 *  
 * Copyright © 2013 证大财富.  All rights reserved.  
 */

package com.zendaimoney.crm.remind.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.orm.PageVo;
import org.springside.modules.orm.SearchFilter;
import org.springside.modules.orm.SearchFilter.Operator;

import com.zendaimoney.constant.DesktopConstant;
import com.zendaimoney.constant.Share;
import com.zendaimoney.crm.CrudUiController;
import com.zendaimoney.crm.remind.service.CustomerBirthDayService;
import com.zendaimoney.crm.remind.vo.CustomerBirthDayRemindVo;
import com.zendaimoney.utils.ConfigurationHelper;


/**
 * 
 * 
 * @author Yuan Changchun
 * @version $Revision:1.0.0, $Date: 2013-8-20 下午06:02:29 $
 */
@Controller
@RequestMapping(value = "/crm/remind")
public class CustomerBirthDayRemindController extends
		CrudUiController<CustomerBirthDayRemindVo> {

	@Autowired
	private CustomerBirthDayService customerBirthDayService;

	@RequestMapping(value = { "remindDetailList1" })
	@ResponseBody
	public PageVo<CustomerBirthDayRemindVo> remindDetailList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "auto") String sort,
			@RequestParam(value = "order", defaultValue = "asc") String order,
			Model model, ServletRequest request,
			@RequestParam(value = "remindType") String remindType,
			@RequestParam(value = "staffId") String staffId) {
		// 生日提醒
		if (remindType.equals(DesktopConstant.CUS_BIRTHDAY_REMIND)) {
			Map<String, SearchFilter> filtersMap = new HashMap<String, SearchFilter>();
			filtersMap.put("birthRemainDaysFloor", new SearchFilter(
					"birthRemainDays", Operator.GTE, 0));
			filtersMap.put(
					"birthRemainDaysTop",
					new SearchFilter("birthRemainDays", Operator.LTE,
							ConfigurationHelper
									.getInteger("customerBirthdayRemindDays")));
			Page<CustomerBirthDayRemindVo> pageView = customerBirthDayService
					.getAllCustomerBirthDayRemind(
							buildSpecification(request, filtersMap,
									Share.CUSTOMER, "crGather"),
							buildPageRequest(page, rows, sort, order));
			return new PageVo<CustomerBirthDayRemindVo>(pageView);
		}
		return null;
	}

}
