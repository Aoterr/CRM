/** 
 * @(#)ModificationController.java 1.0.0 2013-01-29 16:16:01  
 *  
 * Copyright © 2013 证大财富.  All rights reserved.  
 */

package com.zendaimoney.crm.modification.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.orm.PageVo;
import org.springside.modules.orm.ResultVo;
import org.springside.modules.orm.SearchFilter;

import com.zendaimoney.constant.ParamConstant;
import com.zendaimoney.constant.Share;
import com.zendaimoney.crm.CrudUiController;
import com.zendaimoney.crm.modification.entity.Field;
import com.zendaimoney.crm.modification.entity.Modification;
import com.zendaimoney.crm.modification.entity.ModificationDetail;
import com.zendaimoney.crm.modification.service.FieldService;
import com.zendaimoney.crm.modification.service.ModificationService;
import com.zendaimoney.crm.product.entity.BusiAudit;
import com.zendaimoney.crm.sysuser.entity.SysUser;


/**
 * Class ModificationController 变更单控制器
 * 
 * @author zhanghao
 * @version $Revision:1.0.0, $Date: 2013-01-29 16:16:01 $
 */
@Controller
@RequestMapping(value = "/modification")
public class ModificationController extends CrudUiController<Modification> {
	@Autowired
	private ModificationService modificationService;

	@Autowired
	private FieldService fieldService;
	/*
	@RequestMapping(value = { "business" })
	public String business_index(
			@RequestParam(required = false) String mnState, Model model) {
		model.addAttribute("mnState", mnState);
		return "/crm//modification/business/list";
	}
*/
	private String getSort(String sort){
		if(StringUtils.isNotBlank(sort) && !sort.startsWith("M.")){
			if(sort.startsWith("cr"))
				sort = "C."+sort;
			else if(sort.startsWith("mn"))
				sort = "M."+sort;
			else if(sort.startsWith("fe"))
				sort = "BF."+sort; 
		}
		return sort;
	}
	
	@RequestMapping(value = { "customer" })
	public String customer_index(
			@RequestParam(required = false) String mnState, Model model) {
		model.addAttribute("mnState", mnState);
		return "/crm//modification/customer/list";
	}
/*
	// list页面
	@ResponseBody
	@RequestMapping(value = "/business/list")
	public PageVo<Modification> businessList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "auto") String sort,
			@RequestParam(value = "order", defaultValue = "asc") String order,
			Model model, ServletRequest request) {
		sort = getSort(sort);
		Page<Modification> viewPage = modificationService.findAllModification(
				null, buildPageRequest(page, rows, sort, order),
				ParamConstant.BIANGENGFINANCE);
		return new PageVo<Modification>(viewPage);
	}

	// list search页面
	@ResponseBody
	@RequestMapping(value = "/business/list/search")
	public PageVo<Modification> businessListSearch(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "auto") String sort,
			@RequestParam(value = "order", defaultValue = "asc") String order,
			Model model, ServletRequest request) {
		sort = getSort(sort);
		Page<Modification> viewPage = modificationService.findAllModification(
				builderCondition(request, null, Share.CUSTOMER, "mnInputId"),
				buildPageRequest(page, rows, sort, order),
				ParamConstant.BIANGENGFINANCE);
		return new PageVo<Modification>(viewPage);
	}

	// list页面
	@ResponseBody
	@RequestMapping(value = "/customer/list")
	public PageVo<Modification> customerList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "auto") String sort,
			@RequestParam(value = "order", defaultValue = "asc") String order,
			Model model, ServletRequest request) {
		sort = getSort(sort);
		Page<Modification> viewPage = modificationService.findAllModification(
				null, buildPageRequest(page, rows, sort, order),
				ParamConstant.BIANGENGCUSTOMER);
		return new PageVo<Modification>(viewPage);
	}
	*/

	// search list页面
	@ResponseBody
	@RequestMapping(value = "/customer/list/search")
	public PageVo<Modification> customerListSearch(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "auto") String sort,
			@RequestParam(value = "order", defaultValue = "asc") String order,
			Model model, ServletRequest request) {
		sort = getSort(sort);
		Page<Modification> viewPage = modificationService.findAllModification(
				builderCondition(request, null, Share.CUSTOMER, "mnInputId"),
				buildPageRequest(page, rows, sort, order),
				ParamConstant.BIANGENGCUSTOMER);
		return new PageVo<Modification>(viewPage);
	}
/*
	// 跳转到业务新增 变更单页面
	@RequestMapping(value = "/business/create")
	public String toBusinessModificationCreate() {
		return "/crm/modification/business/create";
	}
	
	// 跳转到业务新增高级 变更单页面
	@RequestMapping(value = "/business/create_super")
	public String toBusinessModificationCreateSuper() {
		return "/crm/modification/business/create_super";
	}

	// 跳转到业务编辑 变更单页面
	@RequestMapping(value = "/business/edit")
	public String toBusinessModificationEdit(
			@RequestParam(value = "id") Long id, Model model) {
		model.addAttribute("id", id);
		return "/crm/modification/business/edit";
	}
	// 跳转到业务编辑 高级 变更单页面
		@RequestMapping(value = "/business/edit_super")
		public String toBusinessModificationEditSuper(
				@RequestParam(value = "id") Long id, Model model) {
			model.addAttribute("id", id);
			return "/crm/modification/business/edit_super";
		}
	// 跳转到业务显示 变更单页面
	@RequestMapping(value = "/business/show")
	public String toBusinessModificationShow(
			@RequestParam(value = "id") Long id, Model model) {
		model.addAttribute("id", id);
		return "/crm/modification/business/show";
	}

	// 跳转到业务预审 变更单页面
	@RequestMapping(value = "/business/preliminary/review")
	public String toBusinessModificationPreliminaryReview(
			@RequestParam(value = "id") Long id, Model model) {
		model.addAttribute("id", id);
		return "/crm/modification/business/preliminary_review";
	}

	// 跳转到业务复审 变更单页面
	@RequestMapping(value = "/check/business/preliminary/review")
	public String checkToBusinessModificationPreliminaryReview(
			@RequestParam(value = "id") Long id, Model model) {
		model.addAttribute("id", id);
		model.addAttribute("stateId",
				modificationService.findModificationOne(id).getMnState());
		return "/crm/modification/business/preliminary_review_check";
	}

	// 跳转到业务复审查看页面
	@RequestMapping(value = "/show/business/preliminary/review")
	public String showToBusinessModificationPreliminaryReview(
			@RequestParam(value = "id") Long id, Model model) {
		model.addAttribute("id", id);
		model.addAttribute("stateId",
				modificationService.findModificationOne(id).getMnState());
		return "/crm/modification/business/preliminary_review_show";
	}
*/
	// 跳转到客户新增 变更单页面
	@RequestMapping(value = "/customer/create")
	public String toCustomerModificationCreate() {
		return "/crm/modification/customer/create";
	}
	
	// 跳转到客户新增 高级变更单页面
		@RequestMapping(value = "/customer/create_super")
		public String toCustomerModificationCreateSuper() {
			return "/crm/modification/customer/create_super";
		}
		
	// 跳转到客户编辑 变更单页面
	@RequestMapping(value = "/customer/edit")
	public String toCustomerModificationEdit(
			@RequestParam(value = "id") Long id, Model model) {
		model.addAttribute("id", id);
		return "/crm/modification/customer/edit";
	}
	
	// 跳转到客户编辑 高级 变更单页面
		@RequestMapping(value = "/customer/edit_super")
		public String toCustomerModificationEditSuper(
				@RequestParam(value = "id") Long id, Model model) {
			model.addAttribute("id", id);
			return "/crm/modification/customer/edit_super";
		}
		
	// 跳转到客户显示 变更单页面
	@RequestMapping(value = "/customer/show")
	public String toCustomerModificationShow(
			@RequestParam(value = "id") Long id, Model model) {
		model.addAttribute("id", id);
		return "/crm/modification/customer/show";
	}

	// 跳转到业务预审 变更单页面
	@RequestMapping(value = "/customer/preliminary/review")
	public String toCustomerModificationPreliminaryReview(
			@RequestParam(value = "id") Long id, Model model) {
		model.addAttribute("id", id);
		return "/crm/modification/customer/preliminary_review";
	}

	// 返回客户类型代码（理财，信贷）
	@ResponseBody
	@RequestMapping(value = "/modification/customerTypeCode")
	public int customerTypeCode(
			@RequestParam(value = "customerId") Long customerId) {
		return modificationService.customerTypeCode(customerId);
	}

	// 获取单个变更单对象
	@ResponseBody
	@RequestMapping(value = "/modification/find/one")
	public Modification findModificationOne(@RequestParam(value = "id") Long id) {
		return modificationService.findModificationOne(id);
	}

	// 审核客户变更单信息
	@ResponseBody
	@RequestMapping(value = "/modification/audit")
	public ResultVo auditModification(Modification modification,
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "auditResult") String auditResult,
			@RequestParam(value = "content") String content,HttpSession httpSession) {
		SysUser sysuser=(SysUser)httpSession.getAttribute(ParamConstant.SYS_USER);
		ResultVo resultVo = null;
		resultVo = modificationService.auditModification(id, content,
				auditResult,sysuser.getId());
		return resultVo;
	}

	// 复核审核申请变更单信息
	@ResponseBody
	@RequestMapping(value = "/check/modification/audit")
	public ResultVo checkAuditModification(Modification modification,
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "auditResult") String auditResult,
			@RequestParam(value = "stateId") String stateId,
			@RequestParam(value = "content") String content) {
		ResultVo resultVo = null;
		resultVo = modificationService.auditModificationCheck(id, content,
				auditResult, stateId);
		return resultVo;
	}

	// 质检复核申请变更单信息
	@ResponseBody
	@RequestMapping(value = "/show/modification/audit")
	public ResultVo showAuditModification(Modification modification,
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "auditResult") String auditResult,
			@RequestParam(value = "stateId") String stateId,
			@RequestParam(value = "content") String content) {
		ResultVo resultVo = null;
		resultVo = modificationService.auditModificationCheck(id, content,
				auditResult, stateId);
		return resultVo;
	}

	// 修改变更单信息
	@ResponseBody
	@RequestMapping(value = "/modification/update")
	public ResultVo updateModification(
			Modification modification,
			@RequestParam(value = "state") String state,
			@RequestParam(value = "modificationDetailList") String modificationDetailList,HttpSession httpSession) {
		SysUser sysuser=(SysUser)httpSession.getAttribute(ParamConstant.SYS_USER);
		return modificationService.updateModification(modification,
				modificationDetailList, state,sysuser);
	}

	// 保存变更单信息
	@ResponseBody
	@RequestMapping(value = "/modification/save")
	public ResultVo saveModification(
			Modification modification,
			@RequestParam(value = "state") String state,
			@RequestParam(value = "modificationDetailList") String modificationDetailList,HttpSession httpSession) {
		SysUser sysuser=(SysUser)httpSession.getAttribute(ParamConstant.SYS_USER);
		return modificationService.saveModification(modification,
				modificationDetailList, state,sysuser);
	}
/*
	// 删除理财申请
	@ResponseBody
	@RequestMapping(value = { "/modification/del" })
	public ResultVo del(@RequestParam(value = "id") Long id) {
		ResultVo resultVo = null;
		try {
			resultVo = modificationService.delModification(id);
		} catch (Exception e) {
			e.printStackTrace();
			resultVo = new ResultVo(false, "操作失败,请重新尝试或联系管理员!");
		}
		return resultVo;
	}

	// 构建查询过路过滤
	public Map<String, SearchFilter> builderSearchFilter(String type) {
		TreeMap<String, SearchFilter> searchTreeMap = new TreeMap<String, SearchFilter>();
		searchTreeMap.put("EQ_mnType", new SearchFilter("mnType", Operator.EQ,
				type));
		return searchTreeMap;
	}
*/
	// 信息分类
	@ResponseBody
	@RequestMapping(value = "/modification/getBianType")
	public List<Field> getBianType() {
		List<String> fdTables = new ArrayList<String>();
		fdTables.add("customer");
		fdTables.add("tel");
		fdTables.add("addr");
		fdTables.add("house");
		fdTables.add("privateOwners");
		fdTables.add("car");
		return fieldService.findAllByFdTabledis(fdTables);
	}
	

	// 信息分类  增加关键信息
	@ResponseBody
	@RequestMapping(value = "/modification/getBianTypeNew")
	public List<Field> getBianTypeNew() {
		List<String> fdTables = new ArrayList<String>();
		fdTables.add("customer");
		fdTables.add("tel");
		fdTables.add("addr");
		fdTables.add("house");
		fdTables.add("privateOwners");
		fdTables.add("car");
		fdTables.add("keyInfo");//关键信息
		return fieldService.findAllByFdTabledis(fdTables);
	}

	// 变更项
	@ResponseBody
	@RequestMapping(value = "/modification/getBianVal")
	public List<Field> getBianVal(
			@RequestParam(value = "fdClassEn") String fdClassEn,
			@RequestParam(value = "customerTypeCode") Long customerTypeCode) {
		List<Field> fields = fieldService.findAllFieldItemByfdClassEn(
				fdClassEn, customerTypeCode);
		String[] list = { "tlAreaCode", "tlExtCode", "arCity", "arCounty",
				"arStreet", "arAddrDetail", "arZipCode" };
		List<Field> disfields = new ArrayList<Field>();
		for (String string : list) {
			for (Field field : fields) {
				if (field.getFdFieldEn().equalsIgnoreCase(string)) {
					disfields.add(field);
				}
			}
		}
		for (Field field : disfields) {
			fields.remove(field);
		}
		return fields;
	}
	
	// 变更项  关键信息
	@ResponseBody
	@RequestMapping(value = "/modification/getBianValNew")
	public List<Field> getBianValNew(
			@RequestParam(value = "fdClassEn") String fdClassEn) {
		List<Field> fields = fieldService.findAllFieldItemByfdClassEn(fdClassEn);//根据分类英文名查找所有字段
		//排除不要的字段
		String[] list = { "tlAreaCode", "tlExtCode", "arCity", "arCounty",
				"arStreet", "arAddrDetail", "arZipCode" };
		List<Field> disfields = new ArrayList<Field>();
		for (String string : list) {
			for (Field field : fields) {
				if (field.getFdFieldEn().equalsIgnoreCase(string)) {
					disfields.add(field);
				}
			}
		}
		for (Field field : disfields) {
			fields.remove(field);
		}
		return fields;
	}		

	// 变更项---电话 区号，号码，分机号
	@ResponseBody
	@RequestMapping(value = "/modification/getTelBianVal")
	public List<Field> getTelBianVal(@RequestParam(value = "field") Long fieldid) {
		Field field = fieldService.getFieldById(fieldid);
		Field field1 = fieldService.findByFdFieldEnAndFdReserve("tlAreaCode",
				field.getFdReserve());
		Field field2 = fieldService.findByFdFieldEnAndFdReserve("tlExtCode",
				field.getFdReserve());
		List<Field> list = new ArrayList<Field>();
		list.add(field1);
		list.add(field2);
		return list;
	}

	// 变更项---地区
	@ResponseBody
	@RequestMapping(value = "/modification/getAddrBianVal")
	public List<Field> getAddrBianVal(
			@RequestParam(value = "field") Long fieldid) {
		Field field = fieldService.getFieldById(fieldid);
		Field arCity = fieldService.findByFdFieldEnAndFdReserve("arCity",
				field.getFdReserve());
		Field arCounty = fieldService.findByFdFieldEnAndFdReserve("arCounty",
				field.getFdReserve());
		Field arStreet = fieldService.findByFdFieldEnAndFdReserve("arStreet",
				field.getFdReserve());
		Field arAddrDetail = fieldService.findByFdFieldEnAndFdReserve(
				"arAddrDetail", field.getFdReserve());
		Field arZipCode = fieldService.findByFdFieldEnAndFdReserve("arZipCode",
				field.getFdReserve());
		List<Field> list = new ArrayList<Field>();
		list.add(arCity);
		list.add(arCounty);
		list.add(arStreet);
		list.add(arAddrDetail);
		list.add(arZipCode);
		return list;
	}

	// 查找字段的分类
	@ResponseBody
	@RequestMapping(value = "/find/field/class")
	public List<Field> findFieldClass(
			@RequestParam(value = "fdTable") String fdTable) {
		return fieldService.findAllFieldClass(fdTable);
	}

	// 根据类别查找变更项
	@ResponseBody
	@RequestMapping(value = "/find/field/item/by/class")
	public List<Field> findFieldItemByClass(
			@RequestParam(value = "fdClassEn") String fdClassEn) {
		return fieldService.findAllFieldItemByfdClassEn(fdClassEn);
	}
//
	@ResponseBody
	@RequestMapping(value = "/find/field/item/by/table")
	public List<Field> findFieldItemByTable(
			@RequestParam(value = "fdTable") String fdTable) {
		return fieldService.findAllFieldItemByFdTable(fdTable);
	}
	//排除合同编号
	@ResponseBody
	@RequestMapping(value = "/find/field/item/by/tableNew")
	public List<Field> findFieldItemByTableNew(
			@RequestParam(value = "fdTable") String fdTable) {
		return fieldService.findAllFieldItemByFdTableNew(fdTable);
	}
	
	//排除理财产品
	@ResponseBody
	@RequestMapping(value = "/find/field/item/by/tableFx")
	public List<Field> findFieldItemByTableFx(
			@RequestParam(value = "fdTable") String fdTable) {
		return fieldService.findAllFieldItemByFdTableFx(fdTable);
	}
	//排除合同编号 排除理财产品
	@ResponseBody
	@RequestMapping(value = "/find/field/item/by/tableNewFx")
	public List<Field> findFieldItemByTableNewFx(
			@RequestParam(value = "fdTable") String fdTable) {
		return fieldService.findAllFieldItemByFdTableNewFx(fdTable);
	}

	// 获取单个变更单对象
	@ResponseBody
	@RequestMapping(value = "/ModificationDetail/find/all")
	public List<ModificationDetail> findAllModificationDetail(
			@RequestParam(value = "id") Long id) {
		Modification modification = modificationService.findModificationOne(id);
		List<ModificationDetail> modificationDetails = new ArrayList<ModificationDetail>();
		Set<ModificationDetail> modificationDetailset = modification
				.getModificationDetails();
		for (ModificationDetail modificationDetail : modificationDetailset) {
			modificationDetails.add(modificationDetail);
		}

		return modificationDetails;
	}

	// 获取单个变更单审核日志
	@ResponseBody
	@RequestMapping(value = "/ModificationDetail/findAllAuditByModificationId")
	public List<BusiAudit> findAllAuditByModificationId(
			@RequestParam(value = "id") Long id) {
		return modificationService.findAllAuditByModificationId(id);
	}
	/*
	@ResponseBody
	@RequestMapping(value = "/get/unsubmit/count")
	public boolean getUnSubmitCount(@RequestParam(value = "id") Long id,
			@RequestParam(value = "type") String type) {
		return modificationService.getUnSubmitCount(id, type) > 0 ? true
				: false;
	}

	*/
	/**
	 * @Title:变更高级查询页面
	 * @Description: 客户变更
	 * @param model
	 * @return
	 * @throws
	 * @time:2014-10-13 上午11:29:54
	 * @author:Sam.J
	 */
	@RequestMapping(value = { "query_forcustomer" })
	public String modification_customer_query(Model model) {
		return "/crm/modification/customer/modification_query";
	}

	/**
	 * @Title:变更高级查询页面
	 * @Description: 业务变更
	 * @param model
	 * @return
	 * @throws
	 * @time:2014-10-13 上午11:29:54
	 * @author:Sam.J
	 *//*
	@RequestMapping(value = { "query_forbusiness" })
	public String modification_business_query(Model model) {
		return "/crm/modification/business/modification_query";
	}

	*/
	/**
	 * @Title:变更高级搜索条件
	 * @Description: 变更类型，现在只取通讯地址，手机号码，EMAIL三种
	 * @param fdClassEn
	 * @return
	 * @throws
	 * @time:2014-11-4 下午02:31:33
	 * @author:Sam.J
	 */
	@ResponseBody
	@RequestMapping(value = "/querySearch/getModifiSearchValue")
	public List<Field> getModifiSearchValue(
			@RequestParam(value = "fdClassEn") String fdClassEn) {
		List<Field> backList = new ArrayList<Field>(); // 返回列表
		if (ParamConstant.MODIFITYPE_TONGXUNXINXI.equals(fdClassEn)) { // 客户变更的读取类型的搜索方法
			List<Field> fieldList = fieldService
					.findAllFieldItemByfdClassEn(fdClassEn);
			String[] needList = { ParamConstant.MODIFI_TONGXINDIZHI,
					ParamConstant.MODIFI_EMAIL, ParamConstant.MODIFI_SHOUJI };
			for (String s : needList) {
				for (Field f : fieldList) {
					if (s.equals(f.getFdFieldCn())) {
						backList.add(f);
					}
				}
			}
		} else if (ParamConstant.MODIFITYPE_TOUZIXINXI.equals(fdClassEn)) {
			List<Field> fieldList = fieldService
					.findAllFieldItemByFdTable(fdClassEn);
			String[] needList = { ParamConstant.MODIFI_LICAICHANPING,
					ParamConstant.MODIFI_CHUJIEJINE,
					ParamConstant.MODIFI_ZHIFUFANGSHI,
					ParamConstant.MODIFI_HUAKOUZHANGHAO,
					ParamConstant.MODIFI_HUIKUANZHANGHAO,
					ParamConstant.MODIFI_GUANLIZHEKOU, };
			for (String s : needList) {
				for (Field f : fieldList) {
					if (s.equals(f.getFdFieldEn())) {
						backList.add(f);
					}
				}
			}
		}
		return backList;
	}
}
