/** 
 * @(#)FinanceController.java 1.0.0 2013-02-27 16:43:54  
 *  
 * Copyright © 2013 证大财富.  All rights reserved.  
 */

package com.zendaimoney.crm.product.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.zendaimoney.crm.CrudUiController;
import com.zendaimoney.crm.product.entity.BusiFinance;
import com.zendaimoney.crm.product.service.BusiFinanceService;
import com.zendaimoney.crm.product.vo.FinanceMaturityRemind;

/**
 * Class FinanceController 理财业务控制器
 * 
 * @author zhanghao
 * @version $Revision:1.0.0, $Date: 2013-02-27 16:43:54 $
 */
@Controller
@RequestMapping(value = "/finance")
public class FinanceController extends CrudUiController<BusiFinance> {/*

	@Autowired
	private BusiFinanceService financeService;

	*//**
	 * 进入到到期提醒页面
	 * 
	 * @author zhanghao
	 * @date 2013-5-21 下午05:18:07
	 * @return
	 *//*
	@RequestMapping("maturity/remind")
	public String financeMaturityRemind() {
		return "/crm/finance/maturity_list";
	}

	*//**
	 * 查询到期提醒页面数据
	 * 
	 * @author zhanghao
	 * @date 2013-5-21 下午05:18:25
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/maturity/remind/list")
	public PageVo<FinanceMaturityRemind> list(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "auto") List<String> sort,
			@RequestParam(value = "order", defaultValue = "asc") String order,
			Model model, ServletRequest request) {
		Page<FinanceMaturityRemind> remindPage = financeService
				.findAllMaturityRemind(
						builderCondition(request, null, Share.PRODUCT_REQUEST,
								"feManager"),
						buildPageRequest(page, rows, sort, order));
		return new PageVo<FinanceMaturityRemind>(remindPage);
	}

	// 跳转到理财续投页面
	@RequestMapping(value = "/continue/invest")
	public String toContinueInvest() {
		return "/crm/finance/continue_invest_add";
	}
	
	
	// 跳转到理财定制续投页面
	@RequestMapping(value = "/customized/invest")
	public String toCustomizedInvest() {
		return "/crm/finance/customized_invest_add";
	}

	// 理财续投保存
	@ResponseBody
	@RequestMapping(value = "/continue/invest/save")
	public ResultVo continueInvestSave(BusiFinance busiFinance,
			@RequestParam(value = "state") String state) {
		ResultVo resultVo = null;
		try {
			resultVo = financeService.saveBusiFinance(busiFinance, state);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultVo;
	}

	// 根据出借编号查询理财信息(非续投)
	@ResponseBody
	@RequestMapping(value = "/find/lendNo")
	public List<BusiFinance> findAllByFeLendNo(
			@RequestParam(value = "feLendNo") String feLendNo,
			ServletRequest request) {
		// return financeService.findAll(buildSpecification(request,
		// listToMap(builderSearchFilter(feLendNo))));
		return financeService.findAllByFeLendNoEx(feLendNo);
	}

	// 根据出借编号查询理财信息(续投)
	@ResponseBody
	@RequestMapping(value = "/find/lendNo1")
	public List<BusiFinance> findAllByFeLendNo1(
			@RequestParam(value = "feLendNo") String feLendNo,
			ServletRequest request) {
		return financeService.findAllByFeLendNo(feLendNo);
	}

	*//**
	 * 根据合同编号和理财产品查询理财信息(非续投)
	 * 
	 * @author Yuan Changchun
	 * @date 2013-11-1 下午02:12:13
	 * @param feContractNo
	 * @param feProduct
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/find/feContractNoAndFeProduct")
	public ResultVo findByFeContractNoAndFeProduct(
			@RequestParam(value = "feContractNo") String feContractNo,
			@RequestParam(value = "feProduct") String feProduct,
			@RequestParam(value = "feLendNo") String feLendNo) {
			return financeService.findByFeContractNoAndFeProduct(feContractNo,
					feProduct, feLendNo);		
		
	}
	
	*//**
	 * 根据合同编号,客户编号和理财产品查询理财信息(非续投)
	 * 
	 * @author zhujj
	 * @date 2015-7-14下午02:12:13
	 * @param feContractNo
	 * @param feProduct
	 * @param customerid
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/find/feContractNoAndFeProductBycustomerId")
	public ResultVo findByFeContractNoAndFeProductByCustomer(
			@RequestParam(value = "feContractNo") String feContractNo,
			@RequestParam(value = "feProduct") String feProduct,
			@RequestParam(value = "feLendNo") String feLendNo,
			@RequestParam(value = "customerid") String customerid) {		
			return financeService.findByFeContractNoAndFeProduct(feContractNo,
					feProduct, feLendNo,customerid);
		
		
	}

	*//**
	 * 根据出借编号查找到期续投理财信息(续投)
	 * 
	 * @author Yuan Changchun
	 * @date 2013-11-1 下午04:12:14
	 * @param preFeLendNo
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/find/preFeLendNo")
	public ResultVo findByPreFeLendNo(
			@RequestParam(value = "preFeLendNo") String preFeLendNo) {
		List<BusiFinance> allByFeLendNoList = financeService
				.findAllByFeLendNo(preFeLendNo);
		List<Long> idList = new ArrayList<Long>();
		for (BusiFinance busiFinance : allByFeLendNoList) {
			idList.add(busiFinance.getId());
		}
		if (idList.size() <= 0)
			return new ResultVo(false, "该笔投资不满足续投条件或者该笔投资不存在!");
		// String idListStr = idList.toString();
		// String idsStr = "(" + idListStr.substring(1, idListStr.length() - 2)
		// + ")";

		
		 * //新增3天内不能续投 by Sam.J 2014.07.08 for (BusiFinance busiFinance :
		 * allByFeLendNoList) { try { if("8".equals(busiFinance.getFeProduct()))
		 * return new ResultVo(false,preFeLendNo+":月稳盈产品不允许到期续投!"); Date
		 * today=new Date(); Date busiday=busiFinance.getFeDivestDate(); long
		 * timeDis=(busiday.getTime()-today.getTime())/86400000; //24 * 60 * 60
		 * * 1000=86400000 if(timeDis<3&&timeDis>=0) return new
		 * ResultVo(false,preFeLendNo+":不允许在到期日前三个工作日续投!"); } catch (Exception
		 * e) { e.printStackTrace(); } }
		 
		return financeService.findByPreIds(idList);
	}
	

	*//**
	 * 根据出借编号查找到期续投理财信息(续投)
	 * 
	 * @author wb_lyq
	 * @date 2015-10-28 下午04:12:14
	 * @param preFeLendNo
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/find/customized/preFeLendNo")
	public ResultVo findCustomizedByPreFeLendNo(
			@RequestParam(value = "preFeLendNo") String preFeLendNo) {
		List<BusiFinance> allByFeLendNoList = financeService
				.findCustomizedByFeLendNo(preFeLendNo);
		List<Long> idList = new ArrayList<Long>();
		for (BusiFinance busiFinance : allByFeLendNoList) {
			if(busiFinance.getFeProduct().equals("9")||busiFinance.getFeProduct().equals("10")){
				idList.add(busiFinance.getId());
			}
		}
		if (idList.size() <= 0)
			return new ResultVo(false, "只可以查询（证大岁悦+/证大双鑫+）产品,该笔投资不满足续投条件或者该笔投资不存在!");
		return financeService.findByPreIds(idList);
	}
	

	@ResponseBody
	@RequestMapping(value = "/validInvestProduct")
	public ResultVo validInvestProduct(@RequestParam(value = "id") Long id) {
		BusiFinance finance = financeService.findOneFinance(id);
		if ((finance.getFeProduct().equals("1")
				|| finance.getFeProduct().equals("2") || finance.getFeProduct()
				.equals("6")||finance.getFeProduct().equals("9") ||finance.getFeProduct().equals("10") )
				&& (finance.getFeDivestDate().getTime() + ConfigurationHelper
						.getInteger("finance.continue.devest.day")
						* DateUtil.DAY_MILLIS) < new Date().getTime()) {
			return new ResultVo(false,
					ConfigurationHelper
							.getStringUTF8("finance.continue.devest.msg"));
		}
		return new ResultVo(true);
	}

	// 添加数据权限
	public Specification<BusiFinance> buildSpecification(
			ServletRequest request, Map<String, SearchFilter> filterMap) {
		return buildSpecification(request, filterMap, Share.PRODUCT_REQUEST,
				"feManager");
	}

	// 构建查询条件
	public List<SearchFilter> builderSearchFilter(String feLendNo) {
		return Lists.newArrayList(new SearchFilter("feLendNo", Operator.EQ,
				feLendNo), new SearchFilter("feState", Operator.GT, 3));
	}

	// list转换成map
	public Map<String, SearchFilter> listToMap(List<SearchFilter> filterList) {
		TreeMap<String, SearchFilter> filterMap = new TreeMap<String, SearchFilter>();
		for (SearchFilter filter : filterList) {
			filterMap.put(filter.operator.toString() + "" + filter.fieldName,
					filter);
		}
		return filterMap;
	}
	
	*//** 
	* @Title: financeMaturityNofixRemind 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return String    返回类型 
	* @author Sam.J
	* @date 2015-8-19 上午11:24:13 
	* @throws 
	*//*
	@RequestMapping("maturity/nofixremind")
	public String financeMaturityNofixRemind() {
		return "/crm/finance/nofix_maturity_list";
	}
	
	@ResponseBody
	@RequestMapping(value = "/maturity/nofix_remind/list")
	public PageVo<FinanceMaturityRemind> nofixlist(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "auto") List<String> sort,
			@RequestParam(value = "order", defaultValue = "asc") String order,
			Model model, ServletRequest request) {
		Page<FinanceMaturityRemind> remindPage = financeService
		.findAllNofixRemind(
						builderCondition(request, null, Share.PRODUCT_REQUEST,
								"feManager"),
						buildPageRequest(page, rows, sort, order));
		return new PageVo<FinanceMaturityRemind>(remindPage);
	}
*/}
