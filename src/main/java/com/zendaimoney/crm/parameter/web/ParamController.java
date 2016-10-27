package com.zendaimoney.crm.parameter.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.swing.JPopupMenu.Separator;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.orm.RecordStaffVo;

import com.google.common.base.Strings;
import com.zendaimoney.constant.LoggingSource;
import com.zendaimoney.constant.LoggingType;
import com.zendaimoney.constant.ParamConstant;
import com.zendaimoney.crm.CrudUiController;
import com.zendaimoney.crm.customer.entity.Bankaccount;
import com.zendaimoney.crm.customer.service.BankaccountService;
import com.zendaimoney.sys.area.service.AreaService;
import com.zendaimoney.sys.area.vo.AreaVo;
import com.zendaimoney.sys.parameter.entity.Parameter;
import com.zendaimoney.sys.parameter.entity.ParameterEntiry;
import com.zendaimoney.uc.rmi.vo.Department;
import com.zendaimoney.uc.rmi.vo.Staff;
import com.zendaimoney.utils.helper.AreaHelper;
import com.zendaimoney.utils.helper.SystemParameterHelper;
import com.zendaimoney.utils.helper.UcHelper;

/**
 * 
 * @author bianxj 获取参数集合helper
 */
@Controller
@RequestMapping(value = "/crm/param")
public class ParamController extends CrudUiController<Parameter> {
	@Autowired
	private AreaService areaService;
	@Autowired
	private BankaccountService bankService;

	// 取所有省份
	@RequestMapping("getProvinces")
	@ResponseBody
	public List getProvinces() {
		return AreaHelper.findAllAreaByFatherId(ParamConstant.CHINAID);
	}

	// 取所有国家
	@RequestMapping("getNations")
	@ResponseBody
	public List getNations() {
		return AreaHelper.findAllAreaByLevel("0");
	}

	/**
	 * 根据市县地区
	 * 
	 * @param pareaId
	 * @return
	 */
	@RequestMapping("getArea")
	@ResponseBody
	public List<AreaVo> getArea() {
		return AreaHelper.findAllArea();
	}

	/**
	 * 根据父Id获取下级地区
	 * 
	 * @param pareaId
	 * @return
	 */
	@RequestMapping("getSubAreas")
	@ResponseBody
	public List getSubAreas(@RequestParam(value = "pareaId") String pareaId) {
		if (pareaId.equals("null")) {
			return null;
		} else {
			return areaService.getAreaByParent(pareaId);
		}
	}

	/**
	 * 根据Id获取地区
	 * 
	 * @param pareaId
	 * @return
	 */
	@RequestMapping("getAreas")
	@ResponseBody
	public AreaVo getAreas(@RequestParam(value = "id") String id) {
		return areaService.getAreaById(id);
	}

	/**
	 * 根据参数类型获取对应所有的参数
	 * 
	 * @param prTypeName
	 * @return List<Parameter>
	 */
	@RequestMapping(value = "findAllByPrTypeName", method = RequestMethod.POST)
	@ResponseBody
	public List<Parameter> findAllByPrTypeName(
			@RequestParam(value = "prTypeName") String prTypeName) {
		return SystemParameterHelper.findParameterByPrTypenameAndPrState(
				prTypeName, "1");
	}

	/**
	 * 根据参数类型获取对应所有的参数（包括禁用型数据）
	 * 
	 * @param prTypeName
	 * @return List<Parameter>
	 */
	@RequestMapping(value = "findAllPrType", method = RequestMethod.POST)
	@ResponseBody
	public List<ParameterEntiry> findAllPrType(
			@RequestParam(value = "prType") String prType) {
		return SystemParameterHelper.queryParameterByPrTypeAndPrState(prType);
	}

	/**
	 * 根据参数类获取对应所有的参数
	 * 
	 * @param prType
	 * @return List<Parameter>
	 */
	@RequestMapping("findAllByPrType")
	@ResponseBody
	public List<ParameterEntiry> findAllByPrType(
			@RequestParam(value = "prType") String prType) {
		return SystemParameterHelper.queryParameterByPrTypeAndPrState(prType,
				"1");
	}

	/**
	 * 根据参数类获取对应所有的参数
	 * 
	 * @param prType
	 * @return List<Parameter>
	 */
	@RequestMapping("findByPrTypeAndPrTypename")
	@ResponseBody
	public List<Parameter> findByPrTypeAndPrTypename(
			@RequestParam(value = "prType") String prType,
			@RequestParam(value = "prTypename") String prTypename,
			HttpServletRequest request) throws UnsupportedEncodingException {
		prTypename = new String(request.getParameter("prTypename").getBytes(
				"iso-8859-1"), "UTF-8");
		return SystemParameterHelper.findParameterByPrTypeAndprTypename(prType,
				prTypename, "1");
	}

	/**                                                                                                                                                                                                                                                                                                                                                                                          
	 * 根据参数类获取对应所有的参数
	 * 
	 * @param prType
	 * @return List<Parameter>
	 */                                                                                                                                                                                  
	@RequestMapping("getPrName")
	@ResponseBody
	public List<String> getPrName(
			@RequestParam(value = "prType") String prType,
			@RequestParam(value = "prTypename") String prTypename,
			HttpServletRequest request) throws UnsupportedEncodingException {
		prTypename = new String(request.getParameter("prTypename").getBytes(
				"iso-8859-1"), "UTF-8");
		List<Parameter> paramList = SystemParameterHelper
				.findParameterByPrTypeAndprTypename(prType, prTypename, "1");
		List<String> prNameList = new ArrayList<String>();
		for (Parameter param : paramList)
			prNameList.add(null != param.getPrName() ? param.getPrName() : "");
		return prNameList;
	}

	/**
	 * 根据参数类获取对应所有的参数
	 * 
	 * @param prType
	 * @return List<Parameter>
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("findParameterByPrTypeAndPrName")
	@ResponseBody
	public Parameter findParameterByPrTypeAndPrName(
			@RequestParam(value = "prType") String prType,
			@RequestParam(value = "prName") String prName,
			HttpServletRequest request) throws UnsupportedEncodingException {
		// prName = URLDecoder.decode(prName);//中文乱码
		prName = new String(request.getParameter("prName").getBytes(
				"iso-8859-1"), "UTF-8");
		return SystemParameterHelper.findParameterByPrTypeAndPrName(prType,
				prName);
	}

	/**
	 * 根据参数类获取对应所有的参数
	 * 
	 * @param prType
	 * @return List<Parameter>
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("findParameterByPrTypeAndPrValue")
	@ResponseBody
	public Parameter findParameterByPrTypeAndPrValue(
			@RequestParam(value = "prType") String prType,
			@RequestParam(value = "prValue") String prValue) {
		return SystemParameterHelper.findParameterByPrTypeAndPrValue(prType,
				prValue);
	}

	/**
	 * 根据参数类获取对应所有的参数
	 * 
	 * @param prTypeList
	 * @return List<List<Parameter>>
	 */

	@RequestMapping("findAllByPrTypeNameList")
	@ResponseBody
	public List<List<Parameter>> findAllByPrTypeNameList(
			@RequestParam(value = "prTypeNameList") List<String> prTypeNameList) {
		List<List<Parameter>> listMap = new ArrayList<List<Parameter>>();
		for (String prTypeName : prTypeNameList) {
			listMap.add(SystemParameterHelper
					.findParameterByPrTypenameAndPrState(prTypeName, "1"));
		}
		return listMap;
	}

	/**
	 * 根据参数类型获取对应所有的参数
	 * 
	 * @param prTypeNameList
	 * @return List<List<Parameter>>
	 */

	@RequestMapping("findAllByPrTypeList")
	@ResponseBody
	public List<List<Parameter>> findAllByPrTypeList(
			@RequestParam(value = "prTypeList") List<String> prTypeList) {
		List<List<Parameter>> listMap = new ArrayList<List<Parameter>>();
		for (String prType : prTypeList) {
			listMap.add(SystemParameterHelper.findParameterByPrTypeAndPrState(
					prType, "1"));
		}
		return listMap;
	}

	/**
	 * 根据 去重 参数类型获取对应所有的参数
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("findAllParameter")
	@ResponseBody
	public List<Parameter> findAllParameter() {
		return SystemParameterHelper.findAllDisPrTypename();
	}

	/**
	 * 取出所有的参数操作类型枚举
	 * 
	 * @author hezc
	 * @date 2012年11月30日16:47:32
	 * @return 返回json
	 */
	@RequestMapping("findAllLoggingType")
	@ResponseBody
	public JSONArray findAllLoggingType() {
		LoggingType[] loggingTypes = LoggingType.values();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (LoggingType loggingType : loggingTypes) {
			Map<String, String> loggingTypeList = new HashMap<String, String>();
			loggingTypeList.put("key", loggingType.name());
			loggingTypeList.put("value", loggingType.name());
			list.add(loggingTypeList);
		}
		JSONArray json = JSONArray.fromObject(list);
		return json;
	}

	/**
	 * 取出所有的参数来源枚举
	 * 
	 * @author hezc
	 * @date 2012年11月30日16:47:32
	 * @return 返回json
	 */
	@RequestMapping("findAllLoggingSource")
	@ResponseBody
	public JSONArray findAllLoggingSource() {
		LoggingSource[] loggingSources = LoggingSource.values();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (LoggingSource loggingSource : loggingSources) {
			Map<String, String> loggingSourceList = new HashMap<String, String>();
			loggingSourceList.put("key", loggingSource.name());
			loggingSourceList.put("value", loggingSource.name());
			list.add(loggingSourceList);
		}
		JSONArray json = JSONArray.fromObject(list);
		return json;
	}

	/**
	 * 取出所有部门
	 * 
	 * @return
	 */
	@RequestMapping("findAllDepart")
	@ResponseBody
	public List<com.zendaimoney.crm.complaint.entity.Department> findAllDepart() {
		List<com.zendaimoney.crm.complaint.entity.Department> departments = new ArrayList<com.zendaimoney.crm.complaint.entity.Department>();
		DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
		com.zendaimoney.crm.complaint.entity.Department department = new com.zendaimoney.crm.complaint.entity.Department();
		dozerBeanMapper.map(UcHelper.getAllDepart(), department);
		departments.add(department);
		return departments;
	}

	/**
	 * 根据业务类型取出所有部门
	 * 
	 * @return
	 */
	@RequestMapping("getAllDeptList")
	@ResponseBody
	public List<com.zendaimoney.crm.complaint.entity.Department> getAllDeptList(
			@RequestParam(value = "departmentTypeNo") String departmentTypeNo) {
		List<com.zendaimoney.crm.complaint.entity.Department> departments = new ArrayList<com.zendaimoney.crm.complaint.entity.Department>();
		com.zendaimoney.crm.complaint.entity.Department department = new com.zendaimoney.crm.complaint.entity.Department();
		DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
		try {
			dozerBeanMapper.map(UcHelper.getAllDeptList(departmentTypeNo),
					department);
			departments.add(department);
		} catch (Exception e) {
			e.printStackTrace();
			return departments;
		}
		return departments;
	}

	/**
	 * 用户名
	 * 
	 * @return
	 */
	@RequestMapping("getStaffName")
	@ResponseBody
	public List<String> getStaffName(
			@RequestParam(value = "staffId") Long staffId) {
		List<String> nameList = new ArrayList<String>();
		nameList.add(UcHelper.getStaffName(staffId));
		return nameList;
	}

	/**
	 * 用户名部門名稱
	 * 
	 * @return
	 */
	@RequestMapping("getStaffDepartmentName")
	@ResponseBody
	public List<String> getStaffDepartmentName(
			@RequestParam(value = "staffId") Long staffId) {
		String depName = null;
		String group = null;
		RecordStaffVo recordStaffVo = UcHelper.getRecordStaff(staffId);
		if(recordStaffVo != null && !Strings.isNullOrEmpty(recordStaffVo.getParentDepName())){
			depName = recordStaffVo.getParentDepName();
			group = recordStaffVo.getDepName();
		}
		List<String> result = new ArrayList<String>();
		result.add(group);
		result.add(depName);
		return result;
//		return UcHelper.getStaffDepartmentName(staffId);
	}

	/**
	 * 获取员工理财中心
	 * @param staffId
	 * @return
	 */
	@RequestMapping("getStaffFinanceCenter")
	@ResponseBody
	public List<String> getStaffFinanceCenter(
			@RequestParam(value = "staffId") Long staffId){
		RecordStaffVo recordStaffVo = UcHelper.getRecordStaff(staffId);
		String depName = "";
		if(recordStaffVo != null && !Strings.isNullOrEmpty(recordStaffVo.getParentDepName())){
			depName = recordStaffVo.getParentDepName();
		}
		List<String> dep = new ArrayList<String>();
		dep.add(depName);
		return dep;
	}
	
	/**
	 * 根据部门code和职级code 获取客户经理
	 * 
	 * @return
	 */
	@RequestMapping("getStaffList")
	@ResponseBody
	public List<org.springside.modules.orm.Staff> getStaffList() {
		DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
		List<org.springside.modules.orm.Staff> staffs = UcHelper
				.getStaffList(ParamConstant.KEHUJINGLI);
		return staffs;
	}

	/**
	 * 取单个用户
	 * 
	 * @param id
	 *            用户id
	 * @return 用户对象
	 */
	/*@ResponseBody
	@RequestMapping("getStaff")
	public List<com.zendaimoney.crm.ui.vo.Staff> getStaff(
			@RequestParam(value = "id") Long id) {
		DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
		Staff staff = UcHelper.getStaff(id);
		List<com.zendaimoney.crm.ui.vo.Staff> staffvos = new ArrayList<com.zendaimoney.crm.ui.vo.Staff>();

		com.zendaimoney.crm.ui.vo.Staff staffvo = new com.zendaimoney.crm.ui.vo.Staff();
		dozerBeanMapper.map(staff, staffvo);
		staffvos.add(staffvo);
		return staffvos;
	}*/

	/**
	 * 根据ID获取用户(只包含ID或者对象)
	 * 
	 * @author Yuan Changchun
	 * @date 2013-9-4 下午05:14:36
	 * @param id
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping("getStaffNameAndId")
	public List<com.zendaimoney.crm.ui.vo.Staff> getStaffNameAndId(
			@RequestParam(value = "id") Long id) {
		DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
		String staffName = UcHelper.getStaffName(id);
		List<com.zendaimoney.crm.ui.vo.Staff> staffvos = new ArrayList<com.zendaimoney.crm.ui.vo.Staff>();
		com.zendaimoney.crm.ui.vo.Staff staffvo = new com.zendaimoney.crm.ui.vo.Staff();
		Staff staff = new Staff();
		staff.setName(staffName);
		staff.setId(id);
		dozerBeanMapper.map(staff, staffvo);
		staffvos.add(staffvo);
		return staffvos;
	}*/

	/**
	 * （根据部门code和职级code 获取部门经理 --这个注释有误） 根据客户经理获取部门主管
	 * 
	 * @return
	 */
	/*@RequestMapping("getStaffList2")
	@ResponseBody
	public List<com.zendaimoney.crm.ui.vo.Staff> getStaffList2(
			@RequestParam(value = "id") Long id) {
		DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
		// List<Staff> staffs =
		// UcHelper.getStaffList(id,ParamConstant.KEHUJINGLI);
		List<Staff> staffs = UcHelper.queryImmediateSupervisorByStaffId(id);
		List<com.zendaimoney.crm.ui.vo.Staff> staffvos = new ArrayList<com.zendaimoney.crm.ui.vo.Staff>();

		for (Staff staff : staffs) {
			com.zendaimoney.crm.ui.vo.Staff staffvo = new com.zendaimoney.crm.ui.vo.Staff();
			dozerBeanMapper.map(staff, staffvo);
			staffvos.add(staffvo);
		}

		return staffvos;
	}*/

	@RequestMapping("queryImmediateSupervisorByStaffId")
	@ResponseBody
	public List<com.zendaimoney.uc.rmi.vo.Staff> queryImmediateSupervisorByStaffId(
			@RequestParam(value = "id") Long id) {
		return UcHelper.queryImmediateSupervisorByStaffId(id);
	}

	/**
	 * 取居间人账户信息
	 * 
	 * @author zhanghao
	 * @date 2013-01-25 16:20:36
	 * @return
	 */
	@ResponseBody
	@RequestMapping("get/remittance/account")
	public List<Bankaccount> getRemittanceAccountList() {
		List<Bankaccount> accList = bankService
				.findALlByCustomerid(ParamConstant.intermediary);
		for (Bankaccount bankaccount : accList) {
			bankaccount.setBaBankName(bankaccount.getBaBankName() + "     "
					+ bankaccount.getBaBranchName());
		}
		return accList;
	}
	
	/**
	 * 取托管人账户信息
	 * 
	 * @author CJ
	 * @date 2015-12-16 16:20:36
	 * @return
	 */
	@ResponseBody
	@RequestMapping("get/remittance/account/edit")
	public List<Bankaccount> getRemittanceAccountListEdit() {
		List<Bankaccount> accList = bankService
				.findALlByCustomerid(ParamConstant.FUND_MANAGER);
		for (Bankaccount bankaccount : accList) {
			bankaccount.setBaBankName(bankaccount.getBaBankName() + "     "
					+ bankaccount.getBaBranchName());
		}
		return accList;
	}
	
	/**
	 * 取基金托管人账户信息
	 * 
	 * @author CJ
	 * @date 2015-12-11 16:20:36
	 * @return
	 */
	@ResponseBody
	@RequestMapping("get/remittance/account/fund")
	public List<Bankaccount> getRemittanceAccountListForFund(@RequestParam(value = "bankId") Long bankId) {
		//根据客户id和银行id查询
		if(bankId!=null){
		List<Bankaccount> accList = bankService.findAllByCusIdAndBankId(ParamConstant.FUND_MANAGER,bankId);
		for (Bankaccount bankaccount : accList) {
			bankaccount.setBaBankName(bankaccount.getBaBankName() + "     "
					+ bankaccount.getBaBranchName());
		
		}
		return accList;
		}
		return null;
	}
	/**
	 * 根据部门id返回部门名称
	 * 
	 * @return
	 * 
	 * @return
	 */
	@RequestMapping("getDeptName")
	@ResponseBody
	public List<String> getDeptName(@RequestParam(value = "id") Long id) {
		Department dep = UcHelper.getDepartName(id);
		List<String> depnames = new ArrayList<String>();
		if (dep != null) {
			depnames.add(dep.getName());
		}
		return depnames;
	}

	/**
	 * 根据客户id返回理财信息
	 * 
	 * @return
	 * 
	 * @return
	 */
	/*@RequestMapping("getLcM")
	@ResponseBody
	public List<InvestInfoVO> getLcM(@RequestParam(value = "id") Long id) {
		List<InvestInfoVO> lcMs = FortuneHelper.getInvestByCustomerID(id);
		return lcMs;
	}*/

	/**
	 * 根据客户id返回借款信息
	 * 
	 * @return
	 * 
	 * @return
	 */
	/*@RequestMapping("getCreditInfo")
	@ResponseBody
	public List<SimpleLoanVo> getCreditInfo(@RequestParam(value = "id") Long id) {
		List<SimpleLoanVo> crediLoanVos = CreditHelper
				.findAllSimpleLoanByCustId(id);
		return crediLoanVos;
	}*/

	/**
	 * 根据地区ID获取地区名称
	 * 
	 * @return
	 */
	@RequestMapping("getAreaName")
	@ResponseBody
	public List<String> getAreaName(@RequestParam(value = "id") Long id) {
		List<String> areaNameList = new ArrayList<String>();
		areaNameList.add(AreaHelper.formatArea(id.toString()));
		return areaNameList;
	}

	/**
	 * 添加默认值到list,Jinghr,2013-6-20 areaType: '1':省份；‘2’:城市；‘3’：‘县级’
	 */
	public List addDefaultToList(int areaType, List list) {
		AreaVo av = new AreaVo();
		switch (areaType) {
		case 1:
			av.setId("");
			av.setName("省/直辖市");
			list.add(av);
			return list;
		case 2:
			av.setId("");
			av.setName("市");
			list.add(av);
			return list;
		case 3:
			av.setId("");
			av.setName("区/县");
			list.add(av);
			return list;
		default:
			return list;
		}
	}

	/**
	 * 查找所有的二级附件类型
	 * 
	 * @author Yuan Changchun
	 * @date 2013-10-9 上午10:21:08
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("getAllAttachmentType")
	@ResponseBody
	public List<Parameter> findAllAttachmentType(Model model,
			ServletRequest request) {
		List<Parameter> parameterListByPrType = SystemParameterHelper
				.findParameterByPrType("attachmentType");
		List<Parameter> removList = new ArrayList<Parameter>();
		for (Parameter parameter : parameterListByPrType) {
			if (parameter.getPrTypename().equals("附件类型")) {
				removList.add(parameter);
			}
		}
		parameterListByPrType.removeAll(removList);
		return parameterListByPrType;
	}

	/**
	 * 根据参数类型获取对应所有的启用参数并且获取当前页面禁用的值
	 * 
	 * @param prType
	 * @return List<Parameter>
	 */
	@RequestMapping("findAllByPrTypeAndPrValue")
	@ResponseBody
	public List<ParameterEntiry> findAllByPrTypeAndPrValue(
			@RequestParam(value = "prType") String prType, String prValue) {
		List<ParameterEntiry> list = SystemParameterHelper
				.queryParameterByPrTypeAndPrState(prType, ParamConstant.PRSTATE);
		if (StringUtils.isNotBlank(prValue)) {
			Parameter parameter = SystemParameterHelper
					.findParameterByPrTypeAndPrValue(prType, prValue);
			String prState = (parameter == null ? null : parameter.getPrState());
			if ("0".equals(prState)) {
				ParameterEntiry entity = new ParameterEntiry();
				BeanUtils.copyProperties(parameter, entity);
				list.add(entity);
			}
		}
		return list;
	}

	/**
	 * 根据参数类获取对应所有的参数
	 * 
	 * @param prType
	 * @return List<Parameter>
	 */
	@RequestMapping("findAllByPrTypePro")
	@ResponseBody
	public List<ParameterEntiry> findAllByPrTypePro(
			@RequestParam(value = "prType") String prType) {
		List<ParameterEntiry> peList = SystemParameterHelper
				.queryParameterByPrTypeAndPrState(prType, "1");
		List<ParameterEntiry> peLista = new ArrayList<ParameterEntiry>();
		for (ParameterEntiry pe : peList) {
			ParameterEntiry parameterEntiry = new ParameterEntiry();
			if (pe.getPrName().equals(ParamConstant.REQUEST_STATE_CHENGGONG)) {
				parameterEntiry.setPrName(pe.getPrName());
				parameterEntiry.setPrValue("4,7,8,9");
				parameterEntiry.setPrState(pe.getPrState());
			} else {
				parameterEntiry = pe;
			}
			peLista.add(parameterEntiry);
		}
		return peLista;
	}

	/**
	 * @Title:查找出所有的理财网点
	 * @Description: 去除掉互联网来源，以供web版客户经理使用，避免选择出错
	 * @return
	 * @throws
	 * @time:2015-1-4 上午10:39:16
	 * @author:Sam.J
	 */
	@RequestMapping("findOrganFortuneExceptHLW")
	@ResponseBody
	public List<ParameterEntiry> findOrganFortuneExceptHLW() {
		List<ParameterEntiry> listback = SystemParameterHelper
				.queryParameterByPrTypeAndPrState("OrganFortune", "1");
		for (int i = listback.size() - 1; i >= 0; i--) {
			ParameterEntiry p = listback.get(i);
			if ("互联网".equals(p.getPrName())) {
				listback.remove(i);
				break;
			}
		}
		return listback;
	}
	
	/**
	 * 根据参数类获取对应所有的参数
	 * 
	 * @param prType
	 * @return List<Parameter>
	 */
	@RequestMapping("findAllByPrTypeFilter")
	@ResponseBody
	public List<ParameterEntiry> findAllByPrTypeFilter(
			@RequestParam(value = "prType") String prType) {
		List<ParameterEntiry> peList = SystemParameterHelper
				.queryParameterByPrTypeAndPrState(prType, "1");
		List<ParameterEntiry> peLista = new ArrayList<ParameterEntiry>();
		for (ParameterEntiry pe : peList) {
			ParameterEntiry parameterEntiry = new ParameterEntiry();
			if (pe.getPrName().equals(ParamConstant.REQUEST_STATE_CHENGGONG)) {//4
				parameterEntiry.setPrName(pe.getPrName());
				parameterEntiry.setPrValue("4");
				parameterEntiry.setPrState(pe.getPrState());
			}else if(pe.getPrName().equals("撤销")||pe.getPrName().equals("复检通过")||pe.getPrName().equals("复检回退")||pe.getPrName().equals("质检复检提交")){
				peLista.remove(pe);//过滤复检相关状态
			}
			else {
				parameterEntiry = pe;
			}
			peLista.add(parameterEntiry);
		}
		return peLista;
	}
}

