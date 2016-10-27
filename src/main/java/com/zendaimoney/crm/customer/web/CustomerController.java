package com.zendaimoney.crm.customer.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.orm.PageVo;
import org.springside.modules.orm.ResultVo;
import org.springside.modules.orm.SearchFilter;
import org.springside.modules.orm.SearchFilter.Operator;

import com.zendaimoney.constant.LoggingSource;
import com.zendaimoney.constant.LoggingType;
import com.zendaimoney.constant.ParamConstant;
import com.zendaimoney.constant.Share;
import com.zendaimoney.crm.CrudUiController;
import com.zendaimoney.crm.channel.entity.Channel;
import com.zendaimoney.crm.channel.service.ChannelService;
import com.zendaimoney.crm.contactperson.entiy.Contactperson;
import com.zendaimoney.crm.contactperson.service.ContactpersonService;
import com.zendaimoney.crm.customer.entity.Addr;
import com.zendaimoney.crm.customer.entity.Attribute;
import com.zendaimoney.crm.customer.entity.Bankaccount;
import com.zendaimoney.crm.customer.entity.Car;
import com.zendaimoney.crm.customer.entity.Customer;
import com.zendaimoney.crm.customer.entity.House;
import com.zendaimoney.crm.customer.entity.PrivateOwners;
import com.zendaimoney.crm.customer.entity.Tel;
import com.zendaimoney.crm.customer.entity.TelorAddrVo;
import com.zendaimoney.crm.customer.helper.CustomerHelper;
import com.zendaimoney.crm.customer.service.AddrService;
import com.zendaimoney.crm.customer.service.AttributeService;
import com.zendaimoney.crm.customer.service.BankaccountService;
import com.zendaimoney.crm.customer.service.CarService;
import com.zendaimoney.crm.customer.service.CustomerService;
import com.zendaimoney.crm.customer.service.HouseService;
import com.zendaimoney.crm.customer.service.PrivateOwnersService;
import com.zendaimoney.crm.customer.service.TelService;
import com.zendaimoney.crm.fund.service.BusiFundsService;
import com.zendaimoney.crm.index.vo.TreeVo;
import com.zendaimoney.crm.member.service.MemberService;
import com.zendaimoney.crm.modification.service.ModificationService;
import com.zendaimoney.crm.product.entity.BusiFinance;
import com.zendaimoney.crm.product.service.BusiFinanceService;
import com.zendaimoney.crm.sysuser.entity.SysUser;
import com.zendaimoney.sys.area.service.AreaService;
import com.zendaimoney.sys.parameter.entity.Parameter;
import com.zendaimoney.sys.sysparam.entity.SysParam;
import com.zendaimoney.sys.sysparam.service.SysParamService;
import com.zendaimoney.uc.rmi.vo.Department;
import com.zendaimoney.uc.rmi.vo.Staff;
import com.zendaimoney.utils.LoggingHelper;
import com.zendaimoney.utils.helper.AreaHelper;
import com.zendaimoney.utils.helper.SystemParameterHelper;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "/crm/customer")
public class CustomerController extends CrudUiController<Customer> {
	@Autowired
	private CustomerService customerService;

	@Autowired
	private TelService telService;

	@Autowired
	private AddrService addrService;

	@Autowired
	private BankaccountService bankaccountService;

	@Autowired
	private ContactpersonService contactpersonService;

	@Autowired
	private HouseService houseService;

	@Autowired
	private PrivateOwnersService privateOwnersService;

	@Autowired
	private BusiFinanceService busiFinanceService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private CarService carService;

	@Autowired
	private SysParamService sysParamService;

	@Autowired
	private AttributeService attributeService;

	@Autowired
	private ModificationService modificationService;

	/*
	 * 修改新增
	 */
	@Autowired
	private MemberService memberService;
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private BusiFundsService busiFundsService;

	@RequestMapping(value = { "" })
	public String index(Model model, ServletRequest request) {

		return "crm/customer/list";
	}

	@RequestMapping("list")
	@ResponseBody
	public PageVo list(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "auto") List<String> sort,
			@RequestParam(value = "order", defaultValue = "asc") String order,
			Model model, ServletRequest request) {
		Page<Customer> customers = customerService.getCustomer(
				buildSpecification(request, null, Share.CUSTOMER, "crGather"),
				buildPageRequest(page, rows, sort, order));
		// Page<Customer> customers =
		// customerService.getCustomer(buildSpecification(request,null,null,null),
		// buildPageRequest(page, rows, sort, order));
		return new PageVo(customers);
	}

	// 账务
	@RequestMapping(value = { "investInfoList" })
	public String investInfoList(@RequestParam(value = "customerid") Long id,
			Model model) {
		Customer customers = customerService.getCustomer(id);
		model.addAttribute("customers", customers);
		return "crm/customer/investInfoList";
	}

	@RequestMapping(value = { "query" })
	public String customer_query(Model model) {
		return "crm/customer/customer_query";
	}

	@RequestMapping(value = { "create_lc" })
	public String create_lc(Model model, ServletRequest request)
			throws UnsupportedEncodingException {
		List<Parameter> idTypeList = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.IDTYPEE,
						ParamConstant.PRSTATE);
		model.addAttribute("crIdtypes", idTypeList);
		List<Parameter> occupationList = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.OCCUPATIONE,
						ParamConstant.PRSTATE);
		model.addAttribute("crOccupations", occupationList);
		List<Parameter> headcountList = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.HEADCOUNTE,
						ParamConstant.PRSTATE);
		model.addAttribute("crCompanySizes", headcountList);
		List<Parameter> fileReceive = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.FILERECEIVEE,
						ParamConstant.PRSTATE);
		model.addAttribute("fileReceives", fileReceive);
		// 增加客户偏好 预加载 Sam.J 14.09.04
		List<Parameter> hobbyList = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.PARAMHOBBY,
						ParamConstant.PRSTATE);
		model.addAttribute("hobbies", hobbyList);
		return "crm/customer/create_lc";
	}
/*
	// APP ，理财申请
	@RequestMapping(value = { "app_create_lc" })
	public String app_create_lc(@RequestParam(value = "crName") String crName,
			@RequestParam(value = "crIdNum") String crIdNum,
			@RequestParam(value = "tlTelNum") String tlTelNum, Model model,
			ServletRequest request) throws UnsupportedEncodingException {
		List<Parameter> idTypeList = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.IDTYPEE,
						ParamConstant.PRSTATE);
		model.addAttribute("crIdtypes", idTypeList);
		List<Parameter> occupationList = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.OCCUPATIONE,
						ParamConstant.PRSTATE);
		model.addAttribute("crOccupations", occupationList);
		List<Parameter> headcountList = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.HEADCOUNTE,
						ParamConstant.PRSTATE);
		model.addAttribute("crCompanySizes", headcountList);
		List<Parameter> fileReceive = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.FILERECEIVEE,
						ParamConstant.PRSTATE);
		model.addAttribute("fileReceives", fileReceive);
		String name = URLDecoder.decode(crName, "UTF-8");
		model.addAttribute("crName", "null".equals(name) ? "" : name);
		model.addAttribute("crIdNum", "null".equals(crIdNum) ? "" : crIdNum);
		model.addAttribute("telNums", "null".equals(tlTelNum) ? "" : tlTelNum);
		// 增加客户偏好 预加载 Sam.J 14.09.04
		List<Parameter> hobbyList = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.PARAMHOBBY,
						ParamConstant.PRSTATE);
		model.addAttribute("hobbies", hobbyList);
		return "crm/customer/create_lc";
	}

	@RequestMapping(value = { "create_xd" })
	public String create_xd(Model model, ServletRequest request)
			throws UnsupportedEncodingException {
		List<Parameter> crEducationList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.EDUCATION);
		model.addAttribute("crEducationList", crEducationList);
		List<Parameter> crHouseList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.HOUSE);
		model.addAttribute("crHouseList", crHouseList);
		List<Parameter> crCopropertyList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.COPROPERTY);
		model.addAttribute("crCopropertyList", crCopropertyList);
		List<Parameter> maritalStatusList = SystemParameterHelper
				.findParameterByPrTypenameAndPrState(
						ParamConstant.MaritalStatus, ParamConstant.PRSTATE);
		model.addAttribute("crMaritalStatus", maritalStatusList);

		List<Parameter> jobNatureList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.JOBNATURE);
		model.addAttribute("jobNatureList", jobNatureList);
		List<Parameter> poCompanyTypeList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.PRIVATECOMPANYTYPE);
		model.addAttribute("poCompanyTypeList", poCompanyTypeList);
		return "crm/customer/create_xd";
	}

	@RequestMapping(value = { "create_zendai" })
	public String create_cpxd(Model model, ServletRequest request)
			throws UnsupportedEncodingException {
		List<Parameter> crPayWageList = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.PAYWAGE,
						ParamConstant.PRSTATE);
		model.addAttribute("crPayWageList", crPayWageList);
		return "crm/customer/create_zendai";
	}

	@RequestMapping(value = { "create_bz" })
	public String create_bz(Model model) {
		return "crm/customer/create_bz";
	}

	// 修改页面bz
	@RequestMapping(value = { "edit_bz" })
	public String edit_bz(@RequestParam(value = "id") Long id, Model model) {
		Customer customers = customerService.getCustomer(id);
		model.addAttribute("customers", customers);
		return "crm/customer/edit_bz";
	}
*/
	// 修改页面lc
	@RequestMapping(value = { "edit_lc" })
	public String edit_lc(@RequestParam(value = "id") Long id,
			@RequestParam(value = "tabs", defaultValue = "0") Long tabs,
			Model model) {
		List<Parameter> idTypeList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.IDTYPEE);
		model.addAttribute("crIdtypes", idTypeList);
		List<Parameter> occupationList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.OCCUPATIONE);
		model.addAttribute("crOccupations", occupationList);
		List<Parameter> headcountList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.HEADCOUNTE);
		model.addAttribute("crCompanySizes", headcountList);
		List<Parameter> fileReceive = SystemParameterHelper
				.findParameterByPrType(ParamConstant.FILERECEIVEE);
		model.addAttribute("fileReceives", fileReceive);

		Customer customers = customerService.getCustomer(id);
		List<Contactperson> cp = contactpersonService
				.findAllByCrmCustomer(customers);
		model.addAttribute("cp", cp.size() != 0 ? cp.get(0)
				: new Contactperson());
		model.addAttribute("customers", customers);
		model.addAttribute("urlnow", "edit_lc");
		model.addAttribute("tabs", tabs);
		// 增加客户偏好 预加载 Sam.J 14.09.04
		List<Parameter> hobbyList = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.PARAMHOBBY,
						ParamConstant.PRSTATE);
		model.addAttribute("hobbies", hobbyList);
		return "crm/customer/edit_lc";
	}
/*
	// 修改页面民信理财
	@RequestMapping(value = { "edit_mxlc" })
	public String edit_mxlc(@RequestParam(value = "id") Long id,
			@RequestParam(value = "tabs", defaultValue = "0") Long tabs,
			Model model) {
		List<Parameter> idTypeList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.IDTYPEE);
		model.addAttribute("crIdtypes", idTypeList);
		List<Parameter> occupationList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.OCCUPATIONE);
		model.addAttribute("crOccupations", occupationList);
		List<Parameter> headcountList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.HEADCOUNTE);
		model.addAttribute("crCompanySizes", headcountList);
		List<Parameter> fileReceive = SystemParameterHelper
				.findParameterByPrType(ParamConstant.FILERECEIVEE);
		model.addAttribute("fileReceives", fileReceive);

		Customer customers = customerService.getCustomer(id);
		List<Contactperson> cp = contactpersonService
				.findAllByCrmCustomer(customers);
		model.addAttribute("cp", cp.size() != 0 ? cp.get(0)
				: new Contactperson());
		model.addAttribute("customers", customers);
		model.addAttribute("customersId", id);
		model.addAttribute("urlnow", "edit_mxlc");
		model.addAttribute("tabs", tabs);

		return "crm/customer/edit_mxlc";
	}

	// 展示页面民信理财
	@RequestMapping(value = { "show_mxlc" })
	public String show_mxlc(@RequestParam(value = "id") Long id,
			@RequestParam(value = "tabs", defaultValue = "0") Long tabs,
			Model model) {
		model.addAttribute("crIdtypes", SystemParameterHelper
				.findParameterByPrType(ParamConstant.IDTYPEE));

		model.addAttribute("crOccupations", SystemParameterHelper
				.findParameterByPrType(ParamConstant.OCCUPATIONE));

		model.addAttribute("crCompanySizes", SystemParameterHelper
				.findParameterByPrType(ParamConstant.HEADCOUNTE));
		List<Parameter> fileReceive = SystemParameterHelper
				.findParameterByPrType(ParamConstant.FILERECEIVEE);
		model.addAttribute("fileReceives", fileReceive);

		// Customer customers = customerService.getCustomer(id);
		Customer customers = new Customer();
		customers.setId(id);
		model.addAttribute("customers", customers);

		model.addAttribute("getArea",
				JSONArray.fromObject(AreaHelper.findAllArea()));

		model.addAttribute("urlnow", "show_mxlc");
		model.addAttribute("tabs", tabs);

		return "crm/customer/show_mxlc";
	}

	*//**
	 * 跳转到民信理财产品申购页面
	 * 
	 * @author Jinghr
	 * @date 2013-9-10 16:00:35
	 * @return
	 *//*
	@RequestMapping(value = "/finance_apply")
	public String toFortuneProductApply(
			@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		Customer customers = customerService.getCustomer(id);
		model.addAttribute("customers", customers);
		return "/crm/product/minxin/finance/finance_apply";
	}

	*//**
	 * 民信信贷业务申请
	 * 
	 * @author Yuan Changchun
	 * @date 2013-7-26 下午03:24:51
	 * @return
	 *//*
	@RequestMapping(value = "/apply")
	public String apply(
			@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		Customer customers = customerService.getCustomer(id);
		model.addAttribute("customers", customers);
		return "/crm/product/minxin/credit/apply";
	}
*/
	// 展示页面lc
	@RequestMapping(value = { "show_lc" })
	public String show_lc(@RequestParam(value = "id") Long id, Model model) {
		List<Parameter> idTypeList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.IDTYPEE);
		model.addAttribute("crIdtypes", idTypeList);
		List<Parameter> occupationList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.OCCUPATIONE);
		model.addAttribute("crOccupations", occupationList);
		List<Parameter> headcountList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.HEADCOUNTE);
		model.addAttribute("crCompanySizes", headcountList);
		List<Parameter> fileReceive = SystemParameterHelper
				.findParameterByPrType(ParamConstant.FILERECEIVEE);
		model.addAttribute("fileReceives", fileReceive);
		Customer customers = customerService.getCustomer(id);
		model.addAttribute("customers", customers);
		model.addAttribute("urlnow", "show_lc");
		// 增加客户偏好 预加载 Sam.J 14.09.04
		List<Parameter> hobbyList = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.PARAMHOBBY,
						ParamConstant.PRSTATE);
		model.addAttribute("hobbies", hobbyList);
		return "crm/customer/show_lc";
	}
/*
	// 修改页面xd
	@RequestMapping(value = { "edit_xd" })
	public String edit_xd(@RequestParam(value = "id") Long id, Model model) {
		List<Parameter> crEducationList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.EDUCATION);
		model.addAttribute("crEducationList", crEducationList);
		List<Parameter> crHouseList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.HOUSE);
		model.addAttribute("crHouseList", crHouseList);
		List<Parameter> crCopropertyList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.COPROPERTY);
		model.addAttribute("crCopropertyList", crCopropertyList);
		List<Parameter> maritalStatusList = SystemParameterHelper
				.findParameterByPrTypenameAndPrState(
						ParamConstant.MaritalStatus, ParamConstant.PRSTATE);
		model.addAttribute("crMaritalStatus", maritalStatusList);
		Customer customers = customerService.getCustomer(id);
		model.addAttribute("customers", customers);
		model.addAttribute("urlnow", "edit_xd");

		List<Parameter> jobNatureList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.JOBNATURE);
		model.addAttribute("jobNatureList", jobNatureList);
		List<Parameter> poCompanyTypeList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.PRIVATECOMPANYTYPE);
		model.addAttribute("poCompanyTypeList", poCompanyTypeList);
		return "crm/customer/edit_xd";
	}

	// 展示页面xd
	@RequestMapping(value = { "show_xd" })
	public String show_xd(@RequestParam(value = "id") Long id, Model model) {
		List<Parameter> crEducationList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.EDUCATION);
		model.addAttribute("crEducationList", crEducationList);
		List<Parameter> crHouseList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.HOUSE);
		model.addAttribute("crHouseList", crHouseList);
		List<Parameter> crCopropertyList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.COPROPERTY);
		model.addAttribute("crCopropertyList", crCopropertyList);
		List<Parameter> maritalStatusList = SystemParameterHelper
				.findParameterByPrTypenameAndPrState(
						ParamConstant.MaritalStatus, ParamConstant.PRSTATE);
		model.addAttribute("crMaritalStatus", maritalStatusList);
		Customer customers = customerService.getCustomer(id);
		model.addAttribute("customers", customers);
		model.addAttribute("urlnow", "edit_xd");

		List<Parameter> jobNatureList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.JOBNATURE);
		model.addAttribute("jobNatureList", jobNatureList);
		List<Parameter> poCompanyTypeList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.PRIVATECOMPANYTYPE);
		model.addAttribute("poCompanyTypeList", poCompanyTypeList);
		return "crm/customer/show_xd";
	}

	@RequestMapping(value = { "create_contactmx" })
	public String create_contactmx(@RequestParam(value = "id") Long id,
			Model model) {
		model.addAttribute("id", id);
		model.addAttribute("urlnow", "create_contactmx");
		return "crm/customer/create_contactmx";
	}

	// 编辑民信，jinghr,2013-7-12 11:16:55
	@RequestMapping(value = "editMx", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo editMx(Model model, HttpServletRequest request) {
		Customer customer = customerService.saveOrUpdateCus(assembleWithAlias(
				request, Customer.class, "customer", "_"));
		// 增加 对 appMember 表的修改 2014.06.18 Sam.J
		updateAppMember(customer);
		if (customer.getId() != null) {
			// 获取客户联系信息，并保存
			List<Tel> telList = TelList_CustomerOrContactperByType(request, "0");
			telService.saveOrUpTelListByCus_id(customer.getId(), telList);

			// 获取客户地址信息，并保存
			List<Addr> addrList = AddrList_CustomerOrContactperByType(request,
					"0");
			addrService.saveOrUpAddrListByCus_id(customer.getId(), addrList);

			return new ResultVo(true);
		} else {
			return new ResultVo(false);
		}

	}

	// 完成对民信数据录入，jinghr,2013-7-20 14:03:41
	@RequestMapping(value = { "completeMx" })
	@ResponseBody
	public ResultVo completeMx(@RequestParam(value = "id") Long id, Model model) {

		if (id != null && id != 0) {
			return new ResultVo(true, "", "/crm/customer/apply?id=" + id);
		} else {
			return new ResultVo(false, ParamConstant.ERRORA);
		}

	}

	// 保存民信，jinghr,2013-7-12 11:16:55
	@RequestMapping(value = "saveMx", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo saveMx(Model model, HttpServletRequest request) {
		try {
			Customer customer = assembleWithAlias(request, Customer.class,
					"customer", "_");

			// 身份证验证
			Customer cusIdNum = validCusIdnum(customer.getCrIdtype(),
					customer.getCrIdnum(), customer.getId());

			if (null != cusIdNum && customer.getCrIdnum() != null) {
				return new ResultVo(false, "该客户证件号已存在！");
			}

			customer = customerService.saveOrUpdateCus(customer);
			// 增加 对 appMember 表的修改 2014.06.18 Sam.J
			updateAppMember(customer);
			if (customer.getId() != null) {
				// 获取客户联系信息，并保存
				List<Tel> telList = TelList_CustomerOrContactperByType(request,
						"0");
				telService.saveOrUpTelListByCus_id(customer.getId(), telList);

				// 获取客户地址信息，并保存
				List<Addr> addrList = AddrList_CustomerOrContactperByType(
						request, "0");
				addrService
						.saveOrUpAddrListByCus_id(customer.getId(), addrList);
				return new ResultVo(true, customer.getId().toString());
			}
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
			return new ResultVo(false, "保存失败");
		}
		return new ResultVo(false, "客户地址保存失败 ，请返回到修改页面补充地址信息 ");

	}

	// 完成对民信数据录入，jinghr,2013-7-20 14:03:41
	@RequestMapping(value = { "completeZendai" })
	@ResponseBody
	public ResultVo completeZendai(@RequestParam(value = "id") Long id,
			Model model) {

		if (id != null && id != 0) {
			Customer customers = customerService.getCustomer(id);
			model.addAttribute("customers", customers);
			return new ResultVo(true, "",
					"/crm/customer/zendai_finance_apply?id=" + id);
		} else {
			return new ResultVo(false, ParamConstant.ERRORA);
		}

	}

	*//**
	 * 跳转到公司信贷产品申购页面
	 * 
	 * @author Jinghr
	 * @date 2013-9-10 16:00:35
	 * @return
	 *//*
	@RequestMapping(value = "/zendai_finance_apply")
	public String toZendaiFortuneProductApply(
			@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		Customer customers = customerService.getCustomer(id);
		model.addAttribute("customers", customers);
		return "crm/product/zendai/credit/apply";
	}

	@RequestMapping(value = { "create_contactmxzd" })
	public String create_contactmxzd(@RequestParam(value = "id") Long id,
			Model model) {
		model.addAttribute("id", id);
		model.addAttribute("urlnow", "create_contactmxzd");
		return "crm/customer/create_contactzd";
	}

	// 修改页面
	@RequestMapping(value = { "edit_Zendai" })
	public String edit_Zendai(@RequestParam(value = "id") Long id,
			@RequestParam(value = "tabs", defaultValue = "0") Long tabs,
			Model model) {
		model.addAttribute("customersId", id);
		model.addAttribute("urlnow", "edit_Zendai");
		model.addAttribute("tabs", tabs);
		List<Parameter> crPayWageList = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.PAYWAGE,
						ParamConstant.PRSTATE);
		model.addAttribute("crPayWageList", crPayWageList);

		return "crm/customer/edit_zendai";
	}

	// 显示页面
	@RequestMapping(value = { "show_zendai" })
	public String show_Zendai(@RequestParam(value = "id") Long id,
			@RequestParam(value = "tabs", defaultValue = "0") Long tabs,
			Model model) {
		model.addAttribute("customersId", id);
		model.addAttribute("urlnow", "show_zendai");
		model.addAttribute("tabs", tabs);
		List<Parameter> crPayWageList = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.PAYWAGE,
						ParamConstant.PRSTATE);
		model.addAttribute("crPayWageList", crPayWageList);
		return "crm/customer/show_zendai";
	}

	// 取得客户数据lc
	@RequestMapping(value = { "edit_zendaiById" })
	@ResponseBody
	public Map edit_zendaiById(Long id) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Map map = new HashMap();
		Customer customer = customerService.getCustomer(id);
		editMap(map, customer, "customer_");
		List<Tel> mobile = telService
				.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
						customer.getId(), "0", ParamConstant.MOBILE,
						ParamConstant.priorityHighNum);
		if (mobile.size() != 0) {
			editMap(map, mobile.get(0), "mobile0_");
		}
		;
		List<Tel> tel = telService
				.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
						customer.getId(), "0", ParamConstant.TEL,
						ParamConstant.priorityHighNum);
		if (tel.size() != 0) {
			editMap(map, tel.get(0), "tel0_");
		}
		;
		List<Tel> cptel0 = telService
				.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
						customer.getId(), "0", ParamConstant.CPTEL,
						ParamConstant.priorityHighNum);
		if (cptel0.size() != 0) {
			editMap(map, cptel0.get(0), "cptel0_");
		}
		;
		List<Addr> addr = addrService
				.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
						customer.getId(), "0", ParamConstant.ADDR,
						ParamConstant.priorityHighNum);
		if (addr.size() != 0) {
			editMap(map, addr.get(0), "addr0_");
		}
		;
		List<Addr> addrWork = addrService
				.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
						customer.getId(), "0", ParamConstant.CPADDR,
						ParamConstant.priorityHighNum);
		if (addrWork.size() != 0) {
			editMap(map, addrWork.get(0), "cpaddr0_");
		}
		;
		List<Addr> addrOrig = addrService
				.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
						customer.getId(), "0", ParamConstant.REG,
						ParamConstant.priorityHighNum);
		if (addrOrig.size() != 0) {
			editMap(map, addrOrig.get(0), "reg0_");
		}
		;

		List<House> house = houseService.findAllByCustomerid(customer.getId());
		if (house.size() != 0) {
			editMap(map, house.get(0), "house_");
		}
		;

		List<Car> car = carService.findAllByCustomerid(customer.getId());
		if (car.size() != 0) {
			editMap(map, car.get(0), "car_");
		}
		;

		List<PrivateOwners> po = privateOwnersService
				.findAllByCustomerid(customer.getId());
		if (po.size() != 0) {
			editMap(map, po.get(0), "po_");
		}
		;

		return map;
	}

	// 保存公司信贷，jinghr,2013-7-12 11:16:55
	@RequestMapping(value = "saveZendai", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo saveZendai(Model model, HttpServletRequest request) {
		try {
			Customer customer = assembleWithAlias(request, Customer.class,
					"customer", "_");

			// 身份证验证
			Customer cusIdNum = validCusIdnum(customer.getCrIdtype(),
					customer.getCrIdnum(), customer.getId());

			if (null != cusIdNum && customer.getCrIdnum() != null) {
				return new ResultVo(false, "该客户证件号已存在！");
			}

			customer = customerService.saveOrUpdateCus(customer);
			if (customer.getId() != null) {
				// 获取客户联系信息，并保存
				List<Tel> telList = TelList_CustomerOrContactperByType(request,
						"0");
				telService.saveOrUpTelListByCus_id(customer.getId(), telList);

				// 获取客户地址信息，并保存
				List<Addr> addrList = AddrList_CustomerOrContactperByType(
						request, "0");
				addrService
						.saveOrUpAddrListByCus_id(customer.getId(), addrList);

				// 获取客户房产信息
				House house = assembleWithAlias(request, House.class, "house",
						"_");
				customerService.addHouse(customer.getId(), house);

				// 获取私营业务信息
				PrivateOwners privateOwners = assembleWithAlias(request,
						PrivateOwners.class, "po", "_");
				customerService.addPrivateOwners(customer.getId(),
						privateOwners);

				// 获取车辆信息
				Car car = assembleWithAlias(request, Car.class, "car", "_");
				carService.addCar(customer.getId(), car);

				return new ResultVo(true, customer.getId().toString());
			}
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
			return new ResultVo(false, "保存失败");
		}
		return new ResultVo(false, "客户地址保存失败 ，请返回到修改页面补充地址信息 ");

	}

	// 保存民信理财
	@RequestMapping(value = "saveMxLc", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo saveMxlc(Model model, HttpServletRequest request)
			throws HibernateOptimisticLockingFailureException, Exception {
		Long customerId = null;
		try {
			Customer customer = assembleWithAlias(request, Customer.class,
					"customer", "_");
			// 身份证验证
			Customer cusIdNum = validCusIdnum(customer.getCrIdtype(),
					customer.getCrIdnum(), customer.getId());
			if (null != cusIdNum && customer.getCrIdnum() != null) {
				return new ResultVo(false, "该客户证件号已存在！");
			}

			// 客户基本信息
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
			String forever_crExpiryDate = request
					.getParameter("forever_crExpiryDate");
			if (forever_crExpiryDate != null)
				customer.setCrExpiryDate(formatDate.parse("2099-12-31"));
			List<Tel> crTels = new ArrayList<Tel>();
			crTels.add(formatTel(request, "mobile", "0", ParamConstant.MOBILE));
			crTels.add(formatTel(request, "tel", "0", ParamConstant.TEL));
			crTels.add(formatTel(request, "cz", "0", ParamConstant.CZ));
			List<Addr> crAddrs = new ArrayList<Addr>();
			crAddrs.add(formatAddr(request, "addr", "0", ParamConstant.ADDRCON));
			crAddrs.add(formatAddr(request, "zj", "0", ParamConstant.ADDZJ));

			// 紧急联系人信息
			Contactperson contactperson = assembleWithAlias(request,
					Contactperson.class, "cp", "_");
			List<Contactperson> contactpersons = new ArrayList<Contactperson>();
			Map<Contactperson, List<Tel>> telList = new HashMap<Contactperson, List<Tel>>();
			Map<Contactperson, List<Addr>> addrList = new HashMap<Contactperson, List<Addr>>();
			Tel cpmobile = formatTel(request, "cpmobile", "1",
					ParamConstant.MOBILE);
			Tel cptel = formatTel(request, "cptel", "1", ParamConstant.TEL);
			Addr cpaddr = formatAddr(request, "cpaddr", "1",
					ParamConstant.ADDRCON);

			if (contactperson != null) {
				List<Tel> cpTels = new ArrayList<Tel>();
				List<Addr> cpAddrs = new ArrayList<Addr>();
				cpTels.add(cpmobile);
				cpTels.add(cptel);
				cpAddrs.add(cpaddr);
				telList.put(contactperson, cpTels);
				addrList.put(contactperson, cpAddrs);
				contactpersons.add(cpModel(contactperson, customer));
			} else {
				if (cpmobile != null || cptel != null || cpaddr != null) {
					return new ResultVo(false, ParamConstant.ERRORB);
				}
			}
			if (customer.getId() != null) {
				String result = customerService.saveXdCustomer(customer,
						crTels, crAddrs, null, null, null, contactpersons,
						telList, addrList, "lc");
				LoggingHelper.createLogging(LoggingType.修改, LoggingSource.客户管理,
						result.toString());
			} else {
				Customer savedCus = customerService.saveCustomerMXLC(customer);
				customerId = savedCus.getId();
				String result = customerService.saveXdCustomer(savedCus,
						crTels, crAddrs, null, null, null, contactpersons,
						telList, addrList, "lc");
				LoggingHelper.createLogging(LoggingType.新增, LoggingSource.客户管理,
						result);
			}

			return new ResultVo(true, "", "/crm/customer/finance_apply?id="
					+ customerId);
		} catch (HibernateOptimisticLockingFailureException e) {
			e.printStackTrace();
			return new ResultVo(false, ParamConstant.ERRORA);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultVo(false);
		}
	}
*/
	// 保存理财
	@RequestMapping(value = "saveLc", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo savelc(Model model, HttpServletRequest request,HttpSession httpSession)
			throws HibernateOptimisticLockingFailureException, Exception {
		SysUser sysuser=(SysUser)httpSession.getAttribute(ParamConstant.SYS_USER);
		
		Long customerId = null;
		try {
			// 客户基本信息
			Customer customer = assembleWithAlias(request, Customer.class,
					"customer", "_");

			// 客户姓名处理 San.J 14.12.18
			customer.setCrName(CustomerHelper.dealCustName(customer.getCrName()));
			// 身份证验证
			Customer cusIdNum = validCusIdnum(customer.getCrIdtype(),
					customer.getCrIdnum(), customer.getId());
			if (null != cusIdNum && customer.getCrIdnum() != null) {
				return new ResultVo(false, "该客户证件号已存在！");
			}
			// 邮箱正则校验 lisf 2016/9/5
		    String check = "(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$";
		    Pattern pattern = Pattern.compile(check);
		    if(StringUtils.isNotEmpty(customer.getCrEmail())){
		    	Matcher crEmail = pattern.matcher(customer.getCrEmail());
		    	if (!crEmail.matches()) {
		    		return new ResultVo(false, "请输入有效的电子邮箱地址");
		    	} 		    	
		    }
			// 客户申请书编号验证
			if (customer.getCrApplicationNumber() != null) { // 判断填入的申请书编号是否为空
				List<Customer> listApplicationNumber = customerService
						.findAllByCrApplicationNumber(customer
								.getCrApplicationNumber());
				if (listApplicationNumber.size() > 0) {
					for (Customer c : listApplicationNumber) {
						if (!c.getId().equals(customer.getId())) { // 判断如果存在原申请书编号是否为该客户自己
							return new ResultVo(false, "该客户申请书编号已存在！");
						}
					}
				}
			}

			// 客户基本信息
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

			String forever_crExpiryDate = request
					.getParameter("forever_crExpiryDate");
			if (forever_crExpiryDate != null)
				customer.setCrExpiryDate(formatDate.parse("2099-12-31"));
			List<Tel> crTels = new ArrayList<Tel>();
			crTels.add(formatTel(request, "mobile", "0", ParamConstant.MOBILE));
			crTels.add(formatTel(request, "tel", "0", ParamConstant.TEL));
			crTels.add(formatTel(request, "cz", "0", ParamConstant.CZ));
			List<Addr> crAddrs = new ArrayList<Addr>();
			crAddrs.add(formatAddr(request, "addr", "0", ParamConstant.ADDRCON));
			crAddrs.add(formatAddr(request, "zj", "0", ParamConstant.ADDZJ));

			// 紧急联系人信息
			Contactperson contactperson = assembleWithAlias(request,
					Contactperson.class, "cp", "_");
			// 邮箱正则校验 lisf 2016/9/5
			if(StringUtils.isNotEmpty(contactperson.getCpEmail())){
				Matcher cpEmail = pattern.matcher(contactperson.getCpEmail());
				if (!cpEmail.matches()) {
					return new ResultVo(false, "请输入有效的电子邮箱地址");
				} 				
			}
			List<Contactperson> contactpersons = new ArrayList<Contactperson>();
			Map<Contactperson, List<Tel>> telList = new HashMap<Contactperson, List<Tel>>();
			Map<Contactperson, List<Addr>> addrList = new HashMap<Contactperson, List<Addr>>();
			Tel cpmobile = formatTel(request, "cpmobile", "1",
					ParamConstant.MOBILE);
			Tel cptel = formatTel(request, "cptel", "1", ParamConstant.TEL);
			Addr cpaddr = formatAddr(request, "cpaddr", "1",
					ParamConstant.ADDRCON);

			if (contactperson != null) {
				List<Tel> cpTels = new ArrayList<Tel>();
				List<Addr> cpAddrs = new ArrayList<Addr>();
				cpTels.add(cpmobile);
				cpTels.add(cptel);
				cpAddrs.add(cpaddr);
				telList.put(contactperson, cpTels);
				addrList.put(contactperson, cpAddrs);
				contactpersons.add(cpModel(contactperson, customer,sysuser));
			} else {
				if (cpmobile != null || cptel != null || cpaddr != null) {
					return new ResultVo(false, ParamConstant.ERRORB);
				}
			}
			if (customer.getId() != null) {
				String result = customerService.saveXdCustomer(customer,
						crTels, crAddrs, null, null, null, contactpersons,
						telList, addrList, "lc",sysuser.getId());
				// 增加 对 appMember 表的修改 2014.06.18 Sam.J
				updateAppMember(customer);
				// 客户偏好新增 Sam.J 2014.09.04
				List<Attribute> attList = formatHobby(request, customer.getId(),sysuser);
				saveOrUpdateHobby(attList, customer.getId());
				LoggingHelper.createLogging(LoggingType.修改, LoggingSource.客户管理,
						result,sysuser);
			} else {
				Customer savedCus = customerService.saveCustomerLC(customer,sysuser.getId());
				customerId = savedCus.getId();
				String result = customerService.saveXdCustomer(customer,
						crTels, crAddrs, null, null, null, contactpersons,
						telList, addrList, "lc",sysuser.getId());
				// 客户偏好新增 Sam.J 2014.09.04
				List<Attribute> attList = formatHobby(request, customerId,sysuser);
				saveOrUpdateHobby(attList, customerId);
				LoggingHelper.createLogging(LoggingType.新增, LoggingSource.客户管理,
						result,sysuser);
			}

			//return new ResultVo(true, "","/crm/customer/company_finance_apply?id=" + customerId);
			return new ResultVo(true, "保存成功");
		} catch (HibernateOptimisticLockingFailureException e) {
			e.printStackTrace();
			return new ResultVo(false, ParamConstant.ERRORA);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultVo(false);
		}
	}
/*
	// 跳转到理财产品申购页面公司
	@RequestMapping(value = "/company_finance_apply")
	public String toCompanyFortuneProductApply(
			@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		List<Parameter> deductCompanyList = SystemParameterHelper
				.findParameterByPrTypeAndPrState("deductCompany",ParamConstant.VALID);
		model.addAttribute("deductCompanyList", deductCompanyList);
		Customer customers = customerService.getCustomer(id);
		model.addAttribute("customers", customers);
		*//*************** add by Sam.J 2014/08/26 ***************//*
		Staff staff = AuthorityHelper.getStaff();
		if (UcHelper.getHouseStaff(staff.getId(), ParamConstant.HouseUserDep)) // 如为南京房产项目，跳转专有的页面
			return "/crm/product/purchase/finance_apply_forhouse";
		*//*************** add by Sam.J 2015/07/14 ***************//*
		if (UcHelper.getHouseStaff(staff.getId(), ParamConstant.AthjbUserDep)) // 如为爱特鸿金宝项目，跳转专有的页面
			return "/crm/product/purchase/finance_apply_forathjb";
		return "/crm/product/purchase/finance_apply";
	}

	// 保存信贷客户主体信息
	@RequestMapping(value = "savexd", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo savexd(Model model, HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		try {
			// 客户基本信息
			Customer customer = assembleWithAlias(request, Customer.class,
					"customer", "_");
			List<Tel> crTels = new ArrayList<Tel>();
			crTels.add(formatTel(request, "crMobile", "0", ParamConstant.MOBILE));
			crTels.add(formatTel(request, "crTel", "0", ParamConstant.TEL));
			crTels.add(formatTel(request, "crCpTel", "0", ParamConstant.CPTEL));
			List<Addr> crAddrs = new ArrayList<Addr>();
			crAddrs.add(formatAddr(request, "crRegAddr", "0", ParamConstant.REG));
			String reg_crAddr = request.getParameter("reg_crAddr");
			if (reg_crAddr == null) {
				crAddrs.add(formatAddr(request, "crAddr", "0",
						ParamConstant.ADDR));
			} else {
				Addr crRegAddr = formatAddr(request, "crRegAddr", "0",
						ParamConstant.ADDR);
				Addr crAddr = formatAddr(request, "crAddr", "0",
						ParamConstant.ADDR);
				if (crAddr != null) {
					if (crRegAddr != null) {
						crRegAddr.setId(crAddr.getId());
					}
				}
				crAddrs.add(crRegAddr);
			}
			crAddrs.add(formatAddr(request, "crCyAddr", "0",
					ParamConstant.CPADDR));
			House house = assembleWithAlias(request, House.class, "house", "_");
			PrivateOwners privateOwners = assembleWithAlias(request,
					PrivateOwners.class, "privateOwners", "_");
			// 联系人信息
			Contactperson cpp = assembleWithAlias(request, Contactperson.class,
					"cpp", "_");
			List<Contactperson> contactpersons = new ArrayList<Contactperson>();
			Map<Contactperson, List<Tel>> telList = new HashMap<Contactperson, List<Tel>>();
			Map<Contactperson, List<Addr>> addrList = new HashMap<Contactperson, List<Addr>>();
			Tel cppMobile = formatTel(request, "cppMobile", "1",
					ParamConstant.MOBILE);
			Tel cppTel = formatTel(request, "cppTel", "1", ParamConstant.CPTEL);
			Addr cppAddr = formatAddr(request, "cppAddr", "1",
					ParamConstant.CPADDR);
			if (cpp != null) {
				cpp.setCpRelation(ParamConstant.RELATIONPO);
				List<Tel> cpTels = new ArrayList<Tel>();
				List<Addr> cpAddrs = new ArrayList<Addr>();
				cpTels.add(cppMobile);
				cpTels.add(cppTel);
				cpAddrs.add(cppAddr);
				telList.put(cpp, cpTels);
				addrList.put(cpp, cpAddrs);
				contactpersons.add(cpModel(cpp, customer));
			} else {
				if (cppMobile != null || cppTel != null || cppAddr != null) {
					return new ResultVo(false, ParamConstant.ERRORB);
				}
			}
			Contactperson cpz = assembleWithAlias(request, Contactperson.class,
					"cpz", "_");
			Tel cpzMobile = formatTel(request, "cpzMobile", "1",
					ParamConstant.MOBILE);
			Addr cpzAddr = formatAddr(request, "cpzAddr", "1",
					ParamConstant.ADDR);
			Addr cpzpAddr = formatAddr(request, "cpzpAddr", "1",
					ParamConstant.CPADDR);
			if (cpz != null) {
				List<Tel> cpTels = new ArrayList<Tel>();
				List<Addr> cpAddrs = new ArrayList<Addr>();
				cpTels.add(cpzMobile);
				cpAddrs.add(cpzAddr);
				cpAddrs.add(cpzpAddr);
				telList.put(cpz, cpTels);
				addrList.put(cpz, cpAddrs);
				contactpersons.add(cpModel(cpz, customer));
			} else {
				if (cpzMobile != null || cpzAddr != null || cpzpAddr != null) {
					return new ResultVo(false, ParamConstant.ERRORB);
				}
			}
			Contactperson cpt = assembleWithAlias(request, Contactperson.class,
					"cpt", "_");
			Tel cptMobile = formatTel(request, "cptMobile", "1",
					ParamConstant.MOBILE);
			Tel cptTel = formatTel(request, "cptTel", "1", ParamConstant.TEL);
			if (cpt != null) {
				cpt.setCpRelation("1");
				List<Tel> cpTels = new ArrayList<Tel>();
				cpTels.add(cptMobile);
				cpTels.add(cptTel);
				telList.put(cpt, cpTels);
				contactpersons.add(cpModel(cpt, customer));
			} else {
				if (cptMobile != null || cptTel != null) {
					return new ResultVo(false, ParamConstant.ERRORB);
				}
			}

			if (customer.getId() != null) {
				String result = customerService.saveXdCustomer(customer,
						crTels, crAddrs, null, house, privateOwners,
						contactpersons, telList, addrList, "xd");
				// 增加 对 appMember 表的修改 2014.06.18 Sam.J
				updateAppMember(customer);
				LoggingHelper.createLogging(LoggingType.修改, LoggingSource.客户管理,
						result);
			} else {
				String result = customerService.saveXdCustomer(customer,
						crTels, crAddrs, null, house, privateOwners,
						contactpersons, telList, addrList, "xd");
				LoggingHelper.createLogging(LoggingType.新增, LoggingSource.客户管理,
						result);
			}
			return new ResultVo(true);
		} catch (HibernateOptimisticLockingFailureException e) {
			e.printStackTrace();
			return new ResultVo(false, ParamConstant.ERRORA);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultVo(false);
		}
	}

	// 展示页面mx
	@RequestMapping(value = { "show_mx" })
	public String show_mx(@RequestParam(value = "id") Long id,
			@RequestParam(value = "tabs", defaultValue = "0") Long tabs,
			Model model) {
		Customer customers = customerService.getCustomer(id);
		List<Addr> addrAll = addrService.findByCustomeridAndArPriority(id, "1");
		List<Tel> telAll = telService.findByCustomeridAndTlPriority(id, "1");
		Addr blank_addr = new Addr();
		Tel blank_tel = new Tel();
		model.addAttribute("naddr", blank_addr);
		model.addAttribute("haddr", blank_addr);
		model.addAttribute("daddr", blank_addr);
		model.addAttribute("hj", blank_tel);
		model.addAttribute("ntel", blank_tel);
		model.addAttribute("dtel", blank_tel);
		if (addrAll.size() > 0) {
			for (Addr addr : addrAll)
				if (null != addr.getArAddrType()) {
					if (addr.getArAddrType().equals(ParamConstant.ADDRCON)) // 联系地址
						model.addAttribute("naddr", switchAddr(addr));
					else if (addr.getArAddrType().equals(ParamConstant.REG)) // 户籍地址
						model.addAttribute("haddr", switchAddr(addr));
					else if (addr.getArAddrType().equals(ParamConstant.CPADDR)) // 单位地址
						model.addAttribute("daddr", switchAddr(addr));
				}
		}

		if (telAll.size() > 0) {
			for (Tel tel : telAll)
				if (null != tel.getTlTelType()) {
					if (tel.getTlTelType().equals(ParamConstant.TEL))
						model.addAttribute("ntel", tel);
					else if (tel.getTlTelType().equals(ParamConstant.CPTEL))
						model.addAttribute("dtel", tel);
					else if (tel.getTlTelType().equals(ParamConstant.MOBILE))
						model.addAttribute("mobl",
								tel.getTlTelNum() != null ? tel.getTlTelNum()
										: "");
					else if (tel.getTlTelType().equals(ParamConstant.HJ))
						model.addAttribute("hj", tel);
				}
		}
		model.addAttribute("customers", customers);
		model.addAttribute("urlnow", "show_mx");
		model.addAttribute("tabs", tabs);
		return "crm/customer/show_mx";
	}

	// 创建mx
	@RequestMapping(value = { "create_mx" })
	public String create_mx(Model model) {
		return "crm/customer/create_mx";
	}

	// 创建民信理财
	@RequestMapping(value = { "create_mxlc" })
	public String create_mxlc(Model model, ServletRequest request)
			throws UnsupportedEncodingException {
		List<Parameter> idTypeList = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.IDTYPEE,
						ParamConstant.PRSTATE);
		model.addAttribute("crIdtypes", idTypeList);
		List<Parameter> occupationList = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.OCCUPATIONE,
						ParamConstant.PRSTATE);
		model.addAttribute("crOccupations", occupationList);
		List<Parameter> headcountList = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.HEADCOUNTE,
						ParamConstant.PRSTATE);
		model.addAttribute("crCompanySizes", headcountList);
		List<Parameter> fileReceive = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.FILERECEIVEE,
						ParamConstant.PRSTATE);
		model.addAttribute("fileReceives", fileReceive);
		return "crm/customer/create_mxlc";
	}

	// 修改页面mx
	@RequestMapping(value = { "edit_mx" })
	public String edit_mx(@RequestParam(value = "id") Long id,
			@RequestParam(value = "tabs", defaultValue = "0") Long tabs,
			Model model) {
		// Customer customers = customerService.getCustomer(id);
		// model.addAttribute("customers", customers);
		model.addAttribute("customersId", id);
		model.addAttribute("urlnow", "edit_mx");
		model.addAttribute("tabs", tabs);
		return "crm/customer/edit_mx";
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor dateEditor = new CustomDateEditor(fmt, true);
		binder.registerCustomEditor(Date.class, dateEditor);
	}

	// 保存客户主体信息
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo save(Customer customer, HttpServletRequest request)
			throws Exception {
		try {
			// Date now = new Date();
			// customer.setCrModifyDate(now);
			customer.setCrCustomerNumber(customer.getId() + " ");
			HttpSession session = request.getSession(true);
			session.setAttribute("customer", customer);
			customerService.saveCustomer(customer, null);
			return new ResultVo(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultVo(false);
		}
	}

	@RequestMapping("getIdByFeLendNoExceptApp")
	@ResponseBody
	public Long getIdByFeLendNoExceptApp(
			@RequestParam(value = "feLendNo") String feLendNo) {
		Long busiId = busiFinanceService.getIdByFeLendNo(feLendNo);
		BusiFinance busi = busiFinanceService.findOneFinance(busiId);
		if (!ParamConstant.FE_DATA_ORIGIN_PC.equals(busi.getFeDataOrigin())) {
			busiId = (long) 0;
		}
		return busiId;
	}

	@RequestMapping("getIdByFeLendNo")
	@ResponseBody
	public Long getIdByFeLendNo(
			@RequestParam(value = "feLendNo") String feLendNo) {
		return busiFinanceService.getIdByFeLendNo(feLendNo);
	}
	*/

	// 联系人列表
	@RequestMapping("lxr")
	@ResponseBody
	public List<Contactperson> lxr(@RequestParam(value = "id") Long id) {
		return contactpersonService.findALlByCustomerid(id);
	}

	// 电话 列表
	@RequestMapping("tel")
	@ResponseBody
	public List<Tel> tel(@RequestParam(value = "id") Long id) {
		return telService.findALlByCustomerid(id);
	}

	// 地址列表
	@RequestMapping("addr")
	@ResponseBody
	public List<Addr> addr(@RequestParam(value = "id") Long id) {
		return addrService.findAllByCustomerid(id);
	}

	// 银行列表
	@RequestMapping("bank")
	@ResponseBody
	public List<Bankaccount> bank(@RequestParam(value = "id") Long id) {
		return bankaccountService.findALlByCustomerid(id);
	}

	// 银行列表 无有效验证
	@RequestMapping("bankN")
	@ResponseBody
	public List<Bankaccount> bankN(@RequestParam(value = "id") Long id) {
		return bankaccountService.findAllByCustomeridN(id);
	}

	// 保存电话信息
	@RequestMapping(value = "saveTel")
	@ResponseBody
	public List<Tel> saveTel(Tel tel,
			@RequestParam(value = "customerid") Long customerid,HttpSession httpSession)
			throws Exception {
		SysUser sysuser=(SysUser)httpSession.getAttribute(ParamConstant.SYS_USER);
		try {
			tel.setCustomerid(customerid);
			tel.setTlCustType("0");
			telService.saveTel(tel,sysuser.getId());
			return telService.getAllTel();
		} catch (Exception e) {
			e.printStackTrace();
			return telService.getAllTel();
		}
	}

	// 删除联系人
	@RequestMapping(value = "delLxr")
	@ResponseBody
	public List<Contactperson> delLxr(@RequestParam(value = "id") Long[] id) {
		try {
			contactpersonService.deleteContactperson(id);
			return contactpersonService.getAllContactperson();
		} catch (Exception e) {
			e.printStackTrace();
			return contactpersonService.getAllContactperson();
		}
	}

	// 删除电话
	@RequestMapping(value = "delTel")
	@ResponseBody
	public List<Tel> delTel(@RequestParam(value = "id") Long[] id) {
		try {
			telService.deleteTel(id);
			return telService.getAllTel();
		} catch (Exception e) {
			e.printStackTrace();
			return telService.getAllTel();
		}
	}

	// 保存地址信息
	@RequestMapping(value = "saveAddr", method = RequestMethod.POST)
	@ResponseBody
	public List<Addr> saveAddr(Addr addr,
			@RequestParam(value = "customerid") Long customerid,HttpSession httpSession)
			throws Exception {
		SysUser sysuser=(SysUser)httpSession.getAttribute(ParamConstant.SYS_USER);
		try {
			addr.setCustomerid(customerid);
			addr.setArCustType("0");
			// 判断是否已存在默认联系地址，如无即将该地址设为默认联系地址
			if (ParamConstant.ADDRCON.equals(addr.getArAddrType())) {
				boolean telPriorityH = addrService.highPriority(customerid,
						addr.getArAddrType());
				if (telPriorityH) {
					addr.setArPriority(ParamConstant.priorityHighNum);
				}
			}
			addrService.saveAddr(addr,sysuser.getId());
			return addrService.getAllAddr();
		} catch (Exception e) {
			e.printStackTrace();
			return addrService.getAllAddr();
		}
	}

	// 删除地址
	@RequestMapping(value = "delAddr")
	@ResponseBody
	public List<Addr> delAddr(@RequestParam(value = "id") Long[] id) {
		try {
			addrService.deleteAddr(id);
			return addrService.getAllAddr();
		} catch (Exception e) {
			e.printStackTrace();
			return addrService.getAllAddr();
		}
	}

	// 保存银行信息
	@RequestMapping(value = "saveBank", method = RequestMethod.POST)
	@ResponseBody
	public List<Bankaccount> saveAddr(Bankaccount bank,
			@RequestParam(value = "customerid") Long customerid,HttpSession httpSession)
			throws Exception {
		SysUser sysuser=(SysUser)httpSession.getAttribute(ParamConstant.SYS_USER);
		try {
			bank.setCrmCustomer(customerService.getCustomer(customerid));
			bank.setBaBankName(SystemParameterHelper
					.findParameterByPrTypeAndPrValue(ParamConstant.BANKE,
							bank.getBaBankCode()).getPrName());
			bankaccountService.saveBankaccount(bank,sysuser.getId());
			return bankaccountService.getAllBankaccount();
		} catch (Exception e) {
			e.printStackTrace();
			return bankaccountService.getAllBankaccount();
		}
	}

	// 删除银行
	@RequestMapping(value = "delBank")
	@ResponseBody
	public int delBank(@RequestParam(value = "id") Long[] id,
			HttpServletRequest request) {
		try {
			//
			List<Long> ids = new ArrayList<Long>();
			ids.add(Long.parseLong(ParamConstant.FIELD_FEDEDUCTACCOUNT));
			ids.add(Long.parseLong(ParamConstant.FIELD_FERETURNACCOUNT));
			Long flag = modificationService.findCountByModifyTypeAndValue(ids,
					id[0] + "");
			if (flag > 0)
				return 0;
			bankaccountService.deleteBankaccount(id);
			return bankaccountService.getAllBankaccount().size();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	

	// 取得客户数据lc
	@RequestMapping(value = { "edit_lcById" })
	@ResponseBody
	public Map edit_lcById(Long id) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Map map = new HashMap();
		Customer customer = customerService.getCustomer(id);
		editMap(map, customer, "customer_");
		List<Tel> mobile = telService
				.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
						customer.getId(), "0", ParamConstant.MOBILE,
						ParamConstant.priorityHighNum);
		if (mobile.size() != 0) {
			editMap(map, mobile.get(0), "mobile_");
			editMap(map, mobile.get(0), "mobile0_");
		}
		;
		List<Tel> tel = telService
				.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
						customer.getId(), "0", ParamConstant.TEL,
						ParamConstant.priorityHighNum);
		if (tel.size() != 0) {
			editMap(map, tel.get(0), "tel_");
			editMap(map, tel.get(0), "tel0_");
		}
		;
		List<Tel> hj = telService
				.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
						customer.getId(), "0", ParamConstant.HJ,
						ParamConstant.priorityHighNum);
		if (hj.size() != 0) {
			editMap(map, hj.get(0), "hj0_");
		}
		;
		List<Tel> cptel0 = telService
				.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
						customer.getId(), "0", ParamConstant.CPTEL,
						ParamConstant.priorityHighNum);
		if (cptel0.size() != 0) {
			editMap(map, cptel0.get(0), "cptel0_");
		}
		;
		// List<Tel> cz =
		// telService.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(customer.getId(),
		// "0", ParamConstant.CZ,ParamConstant.priorityHighNum);
		// if (cz.size() != 0) {
		// editMap(map, cz.get(0), "cz_");
		// }
		// ;
		List<Addr> addr = addrService
				.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
						customer.getId(), "0", ParamConstant.ADDRCON,
						ParamConstant.priorityHighNum);
		if (addr.size() != 0) {
			editMap(map, addr.get(0), "addr_");
			editMap(map, addr.get(0), "addrcon0_");
		}
		;
		List<Addr> addrWork = addrService
				.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
						customer.getId(), "0", ParamConstant.CPADDR,
						ParamConstant.priorityHighNum);
		if (addrWork.size() != 0) {
			editMap(map, addrWork.get(0), "cpaddr0_");
		}
		;
		List<Addr> addrOrig = addrService
				.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
						customer.getId(), "0", ParamConstant.REG,
						ParamConstant.priorityHighNum);
		if (addrOrig.size() != 0) {
			editMap(map, addrOrig.get(0), "reg0_");
		}
		;
		List<Addr> addrZj = addrService
				.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
						customer.getId(), "0", ParamConstant.ADDZJ,
						ParamConstant.priorityHighNum);
		if (addrZj.size() != 0) {
			editMap(map, addrZj.get(0), "zj_");
		}
		;
		List<Contactperson> cp = contactpersonService
				.findAllByCrmCustomer(customer);
		// 继续使用老逻辑展示页面，以免出现页面空白缺联系人情况
		// List<Contactperson> cp =
		// contactpersonService.getContactpersonByCustmerAndType(customer,ParamConstant.ContactpersonType_JINJI);
		if (cp.size() != 0) {
			editMap(map, cp.get(0), "cp_");
			List<Tel> cpmobile = telService
					.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
							cp.get(0).getId(), "1", ParamConstant.MOBILE,
							ParamConstant.priorityHighNum);
			if (cpmobile.size() != 0) {
				editMap(map, cpmobile.get(0), "cpmobile_");
			}
			;
			List<Tel> cptel = telService
					.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
							cp.get(0).getId(), "1", ParamConstant.TEL,
							ParamConstant.priorityHighNum);
			if (cptel.size() != 0) {
				editMap(map, cptel.get(0), "cptel_");
			}
			;
			// List<Tel> cpcz =
			// telService.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(cp.get(0).getId(),
			// "1", ParamConstant.CZ,ParamConstant.priorityHighNum);
			// if (cpcz.size() != 0) {
			// editMap(map, cpcz.get(0), "cpcz_");
			// }
			// ;
			List<Addr> cpaddr = addrService
					.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
							cp.get(0).getId(), "1", ParamConstant.ADDRCON,
							ParamConstant.priorityHighNum);
			if (cpaddr.size() != 0) {
				editMap(map, cpaddr.get(0), "cpaddr_");
			}
			;

		}
		;
		// 增加客户偏好
		List<Attribute> attList = attributeService.findALlByCustomeridAndHType(
				id, ParamConstant.PARAMHOBBY);
		String[] strs = fomatHobbyForPage(attList);
		map.put("customer_hobbies", strs);

		if (customer.getCrChannel() != null && customer.getCrChannel() != "") {
			Channel channel = channelService.getChannel(Long.valueOf(customer
					.getCrChannel()));
			map.put("top_channel",
					channel.getParentId() == 1 ? customer.getCrChannel()
							: String.valueOf(channel.getParentId()));
			map.put("mid_channel",
					channel.getParentId() == 1 ? null : String.valueOf(channel
							.getId()));
		}

		return map;
	}
/*
	// 取得客户数据lc
	@RequestMapping(value = { "edit_mxlcById" })
	@ResponseBody
	public Map edit_mxlcById(Long id) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Map map = new HashMap();
		Customer customer = customerService.getCustomer(id);
		editMap(map, customer, "customer_");
		List<Tel> mobile = telService
				.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
						customer.getId(), "0", ParamConstant.MOBILE,
						ParamConstant.priorityHighNum);
		if (mobile.size() != 0) {
			editMap(map, mobile.get(0), "mobile_");
			editMap(map, mobile.get(0), "mobile0_");
		}
		;
		List<Tel> tel = telService
				.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
						customer.getId(), "0", ParamConstant.TEL,
						ParamConstant.priorityHighNum);
		if (tel.size() != 0) {
			editMap(map, tel.get(0), "tel_");
			editMap(map, tel.get(0), "tel0_");
		}
		;
		List<Tel> hj = telService
				.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
						customer.getId(), "0", ParamConstant.HJ,
						ParamConstant.priorityHighNum);
		if (hj.size() != 0) {
			editMap(map, hj.get(0), "hj0_");
		}
		;
		List<Tel> cptel0 = telService
				.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
						customer.getId(), "0", ParamConstant.CPTEL,
						ParamConstant.priorityHighNum);
		if (cptel0.size() != 0) {
			editMap(map, cptel0.get(0), "cptel0_");
		}
		;
		// List<Tel> cz =
		// telService.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(customer.getId(),
		// "0", ParamConstant.CZ,ParamConstant.priorityHighNum);
		// if (cz.size() != 0) {
		// editMap(map, cz.get(0), "cz_");
		// }
		// ;
		List<Addr> addr = addrService
				.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
						customer.getId(), "0", ParamConstant.ADDRCON,
						ParamConstant.priorityHighNum);
		if (addr.size() != 0) {
			editMap(map, addr.get(0), "addr_");
			editMap(map, addr.get(0), "addrcon0_");
		}
		;
		List<Addr> addrWork = addrService
				.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
						customer.getId(), "0", ParamConstant.CPADDR,
						ParamConstant.priorityHighNum);
		if (addrWork.size() != 0) {
			editMap(map, addrWork.get(0), "cpaddr0_");
		}
		;
		List<Addr> addrOrig = addrService
				.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
						customer.getId(), "0", ParamConstant.REG,
						ParamConstant.priorityHighNum);
		if (addrOrig.size() != 0) {
			editMap(map, addrOrig.get(0), "reg0_");
		}
		;
		List<Addr> addrZj = addrService
				.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
						customer.getId(), "0", ParamConstant.ADDZJ,
						ParamConstant.priorityHighNum);
		if (addrZj.size() != 0) {
			editMap(map, addrZj.get(0), "zj_");
		}
		;
		List<Contactperson> cp = contactpersonService
				.findContactperson(customer);
		if (cp.size() != 0) {
			editMap(map, cp.get(0), "cp_");
			List<Tel> cpmobile = telService
					.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
							cp.get(0).getId(), "1", ParamConstant.MOBILE,
							ParamConstant.priorityHighNum);
			if (cpmobile.size() != 0) {
				editMap(map, cpmobile.get(0), "cpmobile_");
			}
			;
			List<Tel> cptel = telService
					.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
							cp.get(0).getId(), "1", ParamConstant.TEL,
							ParamConstant.priorityHighNum);
			if (cptel.size() != 0) {
				editMap(map, cptel.get(0), "cptel_");
			}
			;
			// List<Tel> cpcz =
			// telService.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(cp.get(0).getId(),
			// "1", ParamConstant.CZ,ParamConstant.priorityHighNum);
			// if (cpcz.size() != 0) {
			// editMap(map, cpcz.get(0), "cpcz_");
			// }
			// ;
			List<Addr> cpaddr = addrService
					.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
							cp.get(0).getId(), "1", ParamConstant.ADDRCON,
							ParamConstant.priorityHighNum);
			if (cpaddr.size() != 0) {
				editMap(map, cpaddr.get(0), "cpaddr_");
			}
			;

		}
		;
		return map;
	}

	// 取得客户数据xd
	@RequestMapping(value = { "edit_xdById" })
	@ResponseBody
	public Map edit_xdById(Long id) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Map map = new HashMap();
		Customer customer = customerService.getCustomer(id);
		editMap(map, customer, "customer_");
		House house = houseService.findByCustomerid(customer.getId());
		if (house != null) {
			editMap(map, house, "house_");
		}
		PrivateOwners privateOwners = privateOwnersService
				.findByCustomerid(customer.getId());
		if (privateOwners != null) {
			editMap(map, privateOwners, "privateOwners_");
		}
		List<Tel> crMobile = telService
				.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
						customer.getId(), "0", ParamConstant.MOBILE,
						ParamConstant.priorityHighNum);
		if (crMobile.size() != 0) {
			editMap(map, crMobile.get(0), "crMobile_");
		}
		;
		List<Tel> crTel = telService
				.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
						customer.getId(), "0", ParamConstant.TEL,
						ParamConstant.priorityHighNum);
		if (crTel.size() != 0) {
			editMap(map, crTel.get(0), "crTel_");
		}
		;
		List<Tel> crCpTel = telService
				.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
						customer.getId(), "0", ParamConstant.CPTEL,
						ParamConstant.priorityHighNum);
		if (crCpTel.size() != 0) {
			editMap(map, crCpTel.get(0), "crCpTel_");
		}
		;

		List<Addr> crRegAddr = addrService
				.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
						customer.getId(), "0", ParamConstant.REG,
						ParamConstant.priorityHighNum);
		if (crRegAddr.size() != 0) {
			editMap(map, crRegAddr.get(0), "crRegAddr_");
		}
		;
		List<Addr> crAddr = addrService
				.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
						customer.getId(), "0", ParamConstant.ADDR,
						ParamConstant.priorityHighNum);
		if (crAddr.size() != 0) {
			editMap(map, crAddr.get(0), "crAddr_");
		}
		;
		List<Addr> crCyAddr = addrService
				.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
						customer.getId(), "0", ParamConstant.CPADDR,
						ParamConstant.priorityHighNum);
		if (crCyAddr.size() != 0) {
			editMap(map, crCyAddr.get(0), "crCyAddr_");
		}
		;

		List<Contactperson> cpp = contactpersonService
				.findAllByCrmCustomerAndCpRelation(customer,
						ParamConstant.RELATIONPO);
		if (cpp.size() != 0) {
			editMap(map, cpp.get(0), "cpp_");
			List<Tel> cppMobile = telService
					.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
							cpp.get(0).getId(), "1", ParamConstant.MOBILE,
							ParamConstant.priorityHighNum);
			if (cppMobile.size() != 0) {
				editMap(map, cppMobile.get(0), "cppMobile_");
			}
			;
			List<Tel> cppTel = telService
					.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
							cpp.get(0).getId(), "1", ParamConstant.CPTEL,
							ParamConstant.priorityHighNum);
			if (cppTel.size() != 0) {
				editMap(map, cppTel.get(0), "cppTel_");
			}
			;
			List<Addr> cppAddr = addrService
					.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
							cpp.get(0).getId(), "1", ParamConstant.CPADDR,
							ParamConstant.priorityHighNum);
			if (cppAddr.size() != 0) {
				editMap(map, cppAddr.get(0), "cppAddr_");
			}
			;

		}
		;

		List<Contactperson> cpz = contactpersonService
				.findAllByCrmCustomerAndCpRelationWithZx(customer);
		if (cpz.size() != 0) {
			editMap(map, cpz.get(0), "cpz_");
			List<Tel> cpzMobile = telService
					.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
							cpz.get(0).getId(), "1", ParamConstant.MOBILE,
							ParamConstant.priorityHighNum);
			if (cpzMobile.size() != 0) {
				editMap(map, cpzMobile.get(0), "cpzMobile_");
			}
			;
			List<Addr> cpzAddr = addrService
					.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
							cpz.get(0).getId(), "1", ParamConstant.ADDR,
							ParamConstant.priorityHighNum);
			if (cpzAddr.size() != 0) {
				editMap(map, cpzAddr.get(0), "cpzAddr_");
			}
			;
			List<Addr> cpzpAddr = addrService
					.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
							cpz.get(0).getId(), "1", ParamConstant.CPADDR,
							ParamConstant.priorityHighNum);
			if (cpzpAddr.size() != 0) {
				editMap(map, cpzpAddr.get(0), "cpzpAddr_");
			}
			;
		}
		;

		List<Contactperson> cpt = contactpersonService
				.findAllByCrmCustomerAndCpRelation(customer,
						ParamConstant.RELATIONTS);
		if (cpt.size() != 0) {
			editMap(map, cpt.get(0), "cpt_");
			List<Tel> cptMobile = telService
					.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
							cpt.get(0).getId(), "1", ParamConstant.MOBILE,
							ParamConstant.priorityHighNum);
			if (cptMobile.size() != 0) {
				editMap(map, cptMobile.get(0), "cptMobile_");
			}
			;
			List<Tel> cptTel = telService
					.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
							cpt.get(0).getId(), "1", ParamConstant.TEL,
							ParamConstant.priorityHighNum);
			if (cptTel.size() != 0) {
				editMap(map, cptTel.get(0), "cptTel_");
			}
			;

		}
		;

		return map;
	}

	// @ModelAttribute("customer")
	// public Customer addAccount(@RequestParam(value="customer.id") Long id) {
	// return customerService.getCustomer(id);
	// }
	//
	// @RequestMapping(value = "/helloWorld")
	// public String helloWorld(@ModelAttribute("customer") Customer
	// customer,HttpServletRequest request) {
	// customer = assembleWithAlias(request,Customer.class,"customer");
	// return "helloWorld";
	//
	// }
	// 删除
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo delete(@RequestParam(value = "id") Long[] id) {
		try {
			customerService.deleteCustomer(id);
			return new ResultVo(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultVo(false);
		}
	}
*/
	// 取得系统数据
	@RequestMapping(value = { "sysParam" })
	@ResponseBody
	public SysParam getSystem() {
		SysParam sp = sysParamService.getSysParam();
		return sp;
	}

	// 取得电话数据
	@RequestMapping(value = { "telById" })
	@ResponseBody
	public Tel telById(Long id) {
		return telService.getTel(id);
	}

	// 取得地址数据
	@RequestMapping(value = { "addrById" })
	@ResponseBody
	public Addr addrById(Long id) {
		return addrService.getAddr(id);
	}

	// 取得地址数据
	@RequestMapping(value = { "addrByCustomerId" })
	@ResponseBody
	public List<Addr> addrByCustomerId(
			@RequestParam(value = "customerid") Long customerid) {
		List<Addr> addrs = addrService.findByCustomeridAndArPriority(
				customerid,
				SystemParameterHelper.findParameterByPrTypenameAndPrName(
						ParamConstant.priority, ParamConstant.priorityHigh)
						.getPrValue());
		if (addrs.size() == 0) {
			addrs = addrService.findALlByCustomeridDesc(customerid);
		}
		return addrs;
	}

	// 取得电话数据
	@RequestMapping(value = { "telByCustomerId" })
	@ResponseBody
	public List<Tel> telByCustomerId(
			@RequestParam(value = "customerid") Long customerid) {
		List<Tel> tels = telService.findByCustomeridAndTlPriority(
				customerid,
				SystemParameterHelper.findParameterByPrTypenameAndPrName(
						ParamConstant.priority, ParamConstant.priorityHigh)
						.getPrValue());
		if (tels.size() == 0) {
			tels = telService.findALlByCustomeridDesc(customerid);
		}
		return tels;
	}

	// 取得电话数据
	@RequestMapping(value = { "findMoblie" })
	@ResponseBody
	public List<Tel> findMoblie(
			@RequestParam(value = "customerid") Long customerid) {
		List<Tel> tels = telService
				.findByCustomeridAndTlPriorityAndTlTelType(customerid,
						ParamConstant.priorityHighNum, ParamConstant.MOBILE);
		return tels;
	}

	// 取得银行数据
	@RequestMapping(value = { "bankById" })
	@ResponseBody
	public Bankaccount bankById(Long id) {
		return bankaccountService.getBankaccount(id);
	}

	// 根据姓名查找客户
	@ResponseBody
	@RequestMapping(value = "/find/customer")
	public List<Customer> findAllCustomerByName(HttpServletRequest request)
			throws UnsupportedEncodingException {
		return customerService.getCustomer(buildSpecification(request,
				builderSearchFilter(new String(request
						.getParameter("searchVal").getBytes("iso-8859-1"),
						"UTF-8")), Share.CUSTOMER, "crGather"));
	}

	// 构建查询过滤
	public Map<String, SearchFilter> builderSearchFilter(String name) {
		TreeMap<String, SearchFilter> searchTreeMap = new TreeMap<String, SearchFilter>();
		searchTreeMap.put("LIKE_crName", new SearchFilter("crName",
				Operator.LIKE, name));
		return searchTreeMap;
	}
/*
	// 根据类型和内容查找客户
	@ResponseBody
	@RequestMapping(value = "/find/customerByTypeAndValue")
	public List<Customer> customerByTypeAndValue(HttpServletRequest request)
			throws UnsupportedEncodingException {
		String searchType = new String(request.getParameter("searchType")
				.getBytes("iso-8859-1"), "UTF-8");
		String searchValue = new String(request.getParameter("searchValue")
				.getBytes("iso-8859-1"), "UTF-8");

		return customerService.customerBysearchTypeAndsearchValue(searchType,
				searchValue);

	}

	*//**
	 * @Title: customerByTypeAndValueNew
	 * @Description: 根据类型和内容查找客户
	 * @param searchType
	 * @param searchValue
	 * @return List<Customer> 返回类型
	 * @throws
	 * @author liyez
	 * @date 2015年3月12日 下午9:34:32
	 *//*
	@ResponseBody
	@RequestMapping(value = "/find/customerByTypeAndValueNew")
	public List<Customer> customerByTypeAndValueNew(
			@RequestParam(value = "searchType") String searchType,
			@RequestParam(value = "searchValue") String searchValue,
			HttpServletRequest request) {
		return customerService.customerBysearchTypeAndsearchValue(searchType,
				searchValue);

	}

	// 根据类型和内容查找客户
	@ResponseBody
	@RequestMapping(value = "/find/contactReordByValue")
	public List<RecordShow> contactReordByValue(HttpServletRequest request)
			throws UnsupportedEncodingException {
		String searchVal = new String(request.getParameter("searchVal")
				.getBytes("iso-8859-1"), "UTF-8");
		return customerService.contactReord(searchVal);

	}
	*/

	// 根据类型和内容查找客户
	@ResponseBody
	@RequestMapping(value = "/find/customerBysearchTypeAndsearchValue")
	public List<Customer> customerBysearchTypeAndsearchValue(
			HttpServletRequest request) throws UnsupportedEncodingException {
		String searchType = new String(request.getParameter("searchType")
				.getBytes("iso-8859-1"), "UTF-8");
		String searchValue = new String(request.getParameter("searchValue")
				.getBytes("iso-8859-1"), "UTF-8");//测试环境编码
		
	//String searchValue = request.getParameter("searchValue");//开发环境防止中文乱码
		
		// return
		// customerService.customerBysearchTypeAndsearchValue(searchType,searchValue);
		List<Customer> customers = customerService
				.getCustomer(buildSpecification(request,
						builderSearchFilter(searchType, searchValue),
						Share.CUSTOMER, "crGather"));
		List<Customer> customerList = new ArrayList<Customer>();
		for (Customer customer : customers) {
			if (customer.getCrCustomerType() != null) {
				if (customer.getCrCustomerType().equals(
						ParamConstant.CRCUSTOMERTYPECUSTOMER)
						|| customer.getCrCustomerType().equals(
								ParamConstant.OLDCRCUSTOMERTYPECUSTOMER)) {
					customerList.add(customer);
				}
			}
		}
		return customerList;
	}

	// 构建查询过滤
	public Map<String, SearchFilter> builderSearchFilter(String type,
			String value) {
		TreeMap<String, SearchFilter> searchTreeMap = new TreeMap<String, SearchFilter>();
		if (type.equals(ParamConstant.crName)) {
			searchTreeMap.put("EQ_crName", new SearchFilter("crName",
					Operator.EQ, value));
		}
		if (type.equals(ParamConstant.crIdnum)) {
			searchTreeMap.put("EQ_crIdnum", new SearchFilter("crIdnum",
					Operator.EQ, value));
		}
		if (type.equals(ParamConstant.crCustomerNumber)) {
			searchTreeMap.put("EQ_crCustomerNumber", new SearchFilter(
					"crCustomerNumber", Operator.EQ, value));
		}
		return searchTreeMap;
	}

	/**
	 * 将多对象数据格式到map中
	 * 
	 * @author Hezc
	 * @param map
	 * @param obj
	 *            对象
	 * @param word
	 *            格式前缀
	 * @return
	 */
	private Map editMap(Map map, Object obj, String word) {
		Map customerMap = ConvertObjToMap(obj);
		Set<Map.Entry<String, Customer>> set = customerMap.entrySet();
		for (Iterator<Map.Entry<String, Customer>> it = set.iterator(); it
				.hasNext();) {
			Map.Entry<String, Customer> entry = (Map.Entry<String, Customer>) it
					.next();
			if (!entry.getKey().equalsIgnoreCase("busiRequestAttachs")) {
				map.put(word + entry.getKey(), entry.getValue());
			}
		}
		return map;
	}

	/**
	 * 对象转map
	 * 
	 * @author Hezc
	 * @param obj
	 *            对象
	 * @return Map
	 */
	private Map ConvertObjToMap(Object obj) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		if (obj == null)
			return null;
		Field[] fields = obj.getClass().getDeclaredFields();// getGenericSuperclass
		Field[] superFields = obj.getClass().getSuperclass()
				.getDeclaredFields();
		try {
			for (int i = 0; i < fields.length; i++) {
				try {
					Field f = obj.getClass().getDeclaredField(
							fields[i].getName());
					f.setAccessible(true);
					Object o = f.get(obj);
					reMap.put(fields[i].getName(), o);
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			for (int i = 0; i < superFields.length; i++) {
				try {
					Field f = obj.getClass().getSuperclass()
							.getDeclaredField(superFields[i].getName());
					f.setAccessible(true);
					Object o = f.get(obj);
					reMap.put(superFields[i].getName(), o);
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return reMap;
	}

	// 根据客户查找客户的联系人
	@ResponseBody
	@RequestMapping(value = "/find/customer/person")
	public List<TreeVo> findAllContactpersonByCustomer(
			@RequestParam(value = "id") Long id, HttpServletRequest request) {
		TreeVo treeVo;
		List<TreeVo> treeVoList = new ArrayList<TreeVo>();
		List<Contactperson> conList = contactpersonService
				.findALlByCustomerid(id);
		for (Contactperson contactperson : conList) {
			treeVo = new TreeVo();
			treeVo.setId(String.valueOf(contactperson.getId()));
			treeVo.setText(contactperson.getCpName());
			treeVoList.add(treeVo);
		}
		return treeVoList;
	}

	
	/**
	 * 格式化电话信息
	 * 
	 * @param
	 * @author Hezc 2012年11月28日15:22:18
	 * 
	 */
	private Tel formatTel(HttpServletRequest request, String name, String type,
			String telType) {
		Date now = new Date();
		//Staff staff = AuthorityHelper.getStaff();
		Tel tel = assembleWithAlias(request, Tel.class, name, "_");
		return tel != null ? telModel(tel, type, telType,
				ParamConstant.priorityHighNum, ParamConstant.VALID, now,
				1L, now, 1L) : null;

	}

	/**
	 * 格式化地址信息
	 * 
	 * @param
	 * @author Hezc 2012年11月28日15:22:18
	 * 
	 */
	private Addr formatAddr(HttpServletRequest request, String name,
			String type, String addrType) {
		Date now = new Date();
	//	Staff staff = AuthorityHelper.getStaff();
		Addr addr = assembleWithAlias(request, Addr.class, name, "_");
		if (addr == null) {
			if (name.equalsIgnoreCase("zj")) {
				addr = new Addr();
				addr.setArCustType(type);
				addr.setArAddrType(addrType);
				addr.setArValid(ParamConstant.VALID);
				if (addr.getId() != null) {
				} else {
					addr.setArPriority(ParamConstant.priorityHighNum);
				}
			}
		}
		return addr != null ? addrModel(addr, type, addrType,
				ParamConstant.priorityHighNum, ParamConstant.VALID, now,
				1L, now, 1L) : null;

	}

	/**
	 * 联系人信息补全
	 * 
	 * @param contactperson
	 *            补全对象
	 * @author Hezc 2012年11月28日15:22:18
	 * 
	 */
	private Contactperson cpModel(Contactperson contactperson, Customer customer,SysUser sysuser) {
		Date now = new Date();
		contactperson.setCpInputId(sysuser.getId());
		contactperson.setCpInputTime(now);
		contactperson.setCpModifyId(sysuser.getId());
		contactperson.setCpModifyTime(now);
		return contactperson;
	}

	/**
	 * 地址信息补全
	 * 
	 * @param addr
	 *            补全对象 type 类型，0为客户，1为联系人，2为公司 瞎编的，以后再改 addrType 地址类型 pr
	 *            优先级，先给个默认的0 vl 是否有效 1有效0无效 inDate 录入时间 inId 录入人 mdDate 修改时间
	 *            mdId 修改人
	 * @author Hezc 2012年11月28日15:22:18
	 * 
	 */
	private Addr addrModel(Addr addr, String type, String addrType, String pr,
			String vl, Date inDate, Long inId, Date mdDate, Long mdId) {
		addr.setArCustType(type);
		addr.setArAddrType(addrType);
		// addr.setArPriority(pr);
		addr.setArValid(vl);
		if (addr.getId() != null) {
		} else {
			addr.setArPriority(pr);
		}
		return addr;
	}

	/**
	 * 电话信息补全
	 * 
	 * @param tel
	 *            补全对象 type 电话 类型，0为客户，1为联系人，2为公司 瞎编的，以后再改 telType
	 *            电话类型，0为固话，1为手机 pr 优先级，先给个默认的0 vl 是否有效 1有效0无效 inDate 录入时间 inId
	 *            录入人 mdDate 修改时间 mdId 修改人
	 * @author Hezc 2012年11月28日15:22:18
	 * 
	 */
	private Tel telModel(Tel tel, String type, String telType, String pr,
			String vl, Date inDate, Long inId, Date mdDate, Long mdId) {
		tel.setTlCustType(type);
		tel.setTlTelType(telType);
		tel.setTlValid(vl);
		if (tel.getId() != null) {
		} else {
			tel.setTlPriority(pr);
		}
		return tel;
	}
	
	// 取得客户数据
	@RequestMapping(value = { "customerById" })
	@ResponseBody
	public Customer customerById(Long id) {
		return customerService.getCustomer(id);
	}

	// 取得客户姓名
	@RequestMapping(value = { "customerNameById" })
	@ResponseBody
	public List<String> customerNameById(Long id) {
		List<String> name = new ArrayList<String>();
		String crName = customerService.getCustomerName(id);
		name.add(crName == null ? "" : crName);
		return name;
	}

	// 取得客户数据list
	@RequestMapping(value = { "customerListById" })
	@ResponseBody
	public List<Customer> customerListById(Long id) {
		return customerService.getCustomerList(id);
	}

	// 取得客户数据
	@RequestMapping(value = { "customerByidTypeAndidNum" })
	@ResponseBody
	public Customer customerByidTypeAndidNum(
			@RequestParam(value = "idType") String idType,
			@RequestParam(value = "idNum") String idNum,
			@RequestParam(value = "customerId") Long customerId,
			HttpServletRequest request) throws UnsupportedEncodingException {
		idNum = new String(
				request.getParameter("idNum").getBytes("iso-8859-1"), "UTF-8");
		List<Customer> customerList = customerService
				.findAllByCrIdtypeAndCrIdnum(idType, idNum);
		if (customerList.size() < 1) {
			return null;
		} else if (customerList.size() == 1) {
			if (customerId != null) {
				if (customerList.get(0).getId().equals(customerId)) {
					return null;
				} else {
					return customerList.get(0);
				}
			} else {
				return customerList.get(0);
			}
		} else {
			return customerList.get(0);
		}
	}
	

	// 身份证验证后台
	public Customer validCusIdnum(String idType, String idNum, Long customerId)
			throws UnsupportedEncodingException {

		if (null != idNum && !"".equals(idNum))
			idNum = idNum.toUpperCase();

		List<Customer> customerList = customerService
				.findAllByCrIdtypeAndCrIdnum(idType, idNum);
		if (customerList.size() < 1) {
			return null;
		} else if (customerList.size() == 1) {
			if (customerId != null) {
				if (customerList.get(0).getId().equals(customerId)) {
					return null;
				} else {
					return customerList.get(0);
				}
			} else {
				return customerList.get(0);
			}
		} else {
			return customerList.get(0);
		}
	}

	// 取得lxr数据
	@RequestMapping(value = { "lxrBycustomerid" })
	@ResponseBody
	public List<Contactperson> lxrBycustomerid(Long id) {
		return contactpersonService.findALlByCustomerid(id);
	}

	// 取得tel数据
	@RequestMapping(value = { "telAllBycustomerid" })
	@ResponseBody
	public List<Tel> telAllBycustomerid(Long id) {
		return telService.findAllTelByCustomerid(id);
	}

	// 取得tel数据 手机3 家庭1
	@RequestMapping(value = { "telBycustomerid" })
	@ResponseBody
	public List<Tel> moblieBycustomerid(Long id, String telType) {
		return telService.findByCustomeridAndTelType(id, telType);
	}

	// 取得addr数据
	@RequestMapping(value = { "addrAllBycustomerid" })
	@ResponseBody
	public List<Addr> addrAllBycustomerid(Long id) {
		return addrService.findAllAddrByCustomerid(id);
	}

	// 取得bank数据
	@RequestMapping(value = { "bankBycustomerid" })
	@ResponseBody
	public List<Bankaccount> bankBycustomerid(Long id) {
		return bankaccountService.findALlByCustomerid(id);
	}

	// 保存银行信息
	@ResponseBody
	@RequestMapping(value = "finance/save/bank", method = RequestMethod.POST)
	public ResultVo financeSaveBank(Bankaccount bank,
			@RequestParam(value = "customerid") Long customerid,HttpSession httpSession)
			throws Exception {
		SysUser sysuser=(SysUser)httpSession.getAttribute(ParamConstant.SYS_USER);
		ResultVo resultVo = null;
		try {
			bank.setCrmCustomer(customerService.getCustomer(customerid));
			bank.setBaBankName(SystemParameterHelper
					.findParameterByPrTypeAndPrValue(ParamConstant.BANKE,
							bank.getBaBankCode()).getPrName());
			bankaccountService.saveBankaccount(bank,sysuser.getId());
			resultVo = new ResultVo(true, "添加银行成功!");
		} catch (Exception e) {
			resultVo = new ResultVo(false, "添加银行出错,请联系系统管理员或重新操作!");
			e.printStackTrace();
		}
		return resultVo;
	}
/*	
	// 保存银行信息  基金产品
		@ResponseBody
		@RequestMapping(value = "fund/save/bank", method = RequestMethod.POST)
		public ResultVo fundSaveBank(Bankaccount bank,
				@RequestParam(value = "customerid") Long customerid,HttpServletRequest request)
				throws Exception {
			ResultVo resultVo = null;
			try {
				bank.setCrmCustomer(customerService.getCustomer(customerid));
				bank.setBaBankName(SystemParameterHelper
						.findParameterByPrTypeAndPrValue(ParamConstant.BANKE,
								bank.getBaBankCode()).getPrName());
				bankaccountService.saveBankaccount(bank);
				resultVo = new ResultVo(true, "添加银行成功!");
			} catch (Exception e) {
				resultVo = new ResultVo(false, "添加银行出错,请联系系统管理员或重新操作!");
				e.printStackTrace();
			}
			return resultVo;
		}
	
		
		
	// 取得客户的通讯地址
	@RequestMapping(value = { "get/postal/address" })
	public String getPostalAddress(Long id, HttpServletResponse response) {
		String address = CustomerHelper.formatAddr(CustomerHelper
				.getPostalAddress(id));
		PrintWriter printWrite = null;
		try {
			printWrite = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		printWrite.write(address);
		printWrite.flush();
		printWrite.close();
		return null;
	}

	// 补全理财必填信息
	@RequestMapping(value = { "complement_lc" })
	public String complement_lc(@RequestParam(value = "id") Long id, Model model) {
		List<Parameter> idTypeList = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.IDTYPEE,
						ParamConstant.PRSTATE);
		model.addAttribute("crIdtypes", idTypeList);
		List<Parameter> occupationList = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.OCCUPATIONE,
						ParamConstant.PRSTATE);
		model.addAttribute("crOccupations", occupationList);
		List<Parameter> headcountList = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.HEADCOUNTE,
						ParamConstant.PRSTATE);
		model.addAttribute("crCompanySizes", headcountList);
		List<Parameter> fileReceive = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.FILERECEIVEE,
						ParamConstant.PRSTATE);
		model.addAttribute("fileReceives", fileReceive);

		Customer customers = customerService.getCustomer(id);
		model.addAttribute("customers", customers);
		model.addAttribute("urlnow", "edit_lc");
		// 增加客户偏好 预加载 Sam.J 14.09.04
		List<Parameter> hobbyList = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.PARAMHOBBY,
						ParamConstant.PRSTATE);
		model.addAttribute("hobbies", hobbyList);
		return "crm/product/purchase/complement_lc";
	}
*/
	@RequestMapping(value = { "fushuxinxi" })
	public String fushuxinxi(Model model, ServletRequest request,
			@RequestParam(value = "customerId") Long customerId) {
		Customer customer = customerService.getCustomer(customerId);
		model.addAttribute("customers", customer);
		return "crm/customer/fushuxinxi";
	}
/*
	@RequestMapping(value = { "zendaifushuxinxi" })
	public String zendaifushuxinxi(Model model, ServletRequest request,
			@RequestParam(value = "customerId") Long customerId) {
		Customer customer = customerService.getCustomer(customerId);
		model.addAttribute("customers", customer);
		return "crm/customer/zendaifushuxinxi";
	}

	// 民信附属信息
	@RequestMapping(value = { "mxfushuxinxi" })
	public String mxfushuxinxi(Model model, ServletRequest request,
			@RequestParam(value = "customerId") Long customerId) {
		Customer customer = customerService.getCustomer(customerId);
		model.addAttribute("customers", customer);
		return "crm/customer/mxfushuxinxi";
	}

	// 民信附属信息
	@RequestMapping(value = { "mxlcfushuxinxi" })
	public String mxlcfushuxinxi(Model model, ServletRequest request,
			@RequestParam(value = "customerId") Long customerId) {
		Customer customer = customerService.getCustomer(customerId);
		model.addAttribute("customers", customer);
		return "crm/customer/mxlcfushuxinxi";
	}

	@RequestMapping(value = { "xjLxr" })
	public String xjLxr(Model model, ServletRequest request,
			@RequestParam(value = "customerId") Long customerId) {
		Customer customer = customerService.getCustomer(customerId);
		model.addAttribute("customers", customer);
		return "crm/customer/xjLxr";
	}

	@RequestMapping(value = { "zendaixjLxr" })
	public String zendaixjLxr(Model model, ServletRequest request,
			@RequestParam(value = "customerId") Long customerId) {
		Customer customer = customerService.getCustomer(customerId);
		model.addAttribute("customers", customer);
		return "crm/customer/zendaixjLxr";
	}

	// 民信信贷
	@RequestMapping(value = { "mxxjLxr" })
	public String mxxjLxr(Model model, ServletRequest request,
			@RequestParam(value = "customerId") Long customerId) {
		Customer customer = customerService.getCustomer(customerId);
		model.addAttribute("customers", customer);
		model.addAttribute("customerid", customerId);
		model.addAttribute("customerName",
				customerService.getCustomer(customerId).getCrName());
		return "crm/customer/mxxjLxr";
	}

	// 民信理财
	@RequestMapping(value = { "mxlcxjLxr" })
	public String mxlcxjLxr(Model model, ServletRequest request,
			@RequestParam(value = "customerId") Long customerId) {
		Customer customer = customerService.getCustomer(customerId);
		model.addAttribute("customers", customer);
		model.addAttribute("customerid", customerId);
		model.addAttribute("customerName",
				customerService.getCustomer(customerId).getCrName());
		return "crm/customer/mxlcxjLxr";
	}
*/
	// 取得tel数据
	@RequestMapping(value = { "findByCustomeridAndTlCustTypeAndTlTelType" })
	@ResponseBody
	public List<TelorAddrVo> findByCustomeridAndTlCustTypeAndTlTelType(
			@RequestParam(value = "obId") Long obId,
			@RequestParam(value = "cusType") String cusType,
			@RequestParam(value = "fdReserve") String fdReserve) {
		List<Tel> tels = telService.findByCustomeridAndTlCustTypeAndTlTelType(
				obId, cusType, fdReserve);
		List<TelorAddrVo> TelorAddrVos = new ArrayList<TelorAddrVo>();
		for (Tel tel : tels) {
			TelorAddrVo telorAddrVo = new TelorAddrVo();
			telorAddrVo.setObId(tel.getId());
			String string = tel.getTlAreaCode() != null ? tel.getTlAreaCode()
					+ "-" : "";
			string += tel.getTlTelNum() != null ? tel.getTlTelNum() : "";
			string += tel.getTlExtCode() != null ? "-" + tel.getTlExtCode()
					: "";
			telorAddrVo.setObString(string);
			TelorAddrVos.add(telorAddrVo);
		}
		return TelorAddrVos;
	}

	@RequestMapping(value = { "findByCustomeridAndArCustTypeAndArTelType" })
	@ResponseBody
	public List<TelorAddrVo> findByCustomeridAndArCustTypeAndArTelType(
			@RequestParam(value = "obId") Long obId,
			@RequestParam(value = "cusType") String cusType,
			@RequestParam(value = "fdReserve") String fdReserve) {
		List<Addr> addrs = addrService
				.findByCustomeridAndTlCustTypeAndTlTelType(obId, cusType,
						fdReserve);
		List<TelorAddrVo> TelorAddrVos = new ArrayList<TelorAddrVo>();
		for (Addr addr : addrs) {
			TelorAddrVo telorAddrVo = new TelorAddrVo();
			telorAddrVo.setObId(addr.getId());
			String string = addr.getArProvince() != null ? areaService
					.getAreaById(addr.getArProvince()) != null ? areaService
					.getAreaById(addr.getArProvince()).getName() != null ? areaService
					.getAreaById(addr.getArProvince()).getName() : ""
					: ""
					: "";
			string += addr.getArCity() != null ? areaService.getAreaById(addr
					.getArCity()) != null ? areaService.getAreaById(
					addr.getArCity()).getName() != null ? areaService
					.getAreaById(addr.getArCity()).getName() : "" : "" : "";
			string += addr.getArCounty() != null ? areaService.getAreaById(addr
					.getArCounty()) != null ? areaService.getAreaById(
					addr.getArCounty()).getName() != null ? areaService
					.getAreaById(addr.getArCounty()).getName() : "" : "" : "";
			string += addr.getArStreet() != null ? addr.getArStreet() : "";
			string += addr.getArAddrDetail() != null ? addr.getArAddrDetail()
					: "";
			telorAddrVo.setObString(string);
			TelorAddrVos.add(telorAddrVo);
		}
		return TelorAddrVos;
	}

	@RequestMapping(value = { "getTelById" })
	@ResponseBody
	public List<String> getTelById(@RequestParam(value = "obId") Long obId) {
		List<String> slist = new ArrayList<String>();
		Tel tel = telService.getTel(obId);
		String string = tel.getTlAreaCode() != null ? tel.getTlAreaCode() + "-"
				: "";
		string += tel.getTlTelNum() != null ? tel.getTlTelNum() : "";
		string += tel.getTlExtCode() != null ? "-" + tel.getTlExtCode() : "";
		slist.add(string);
		return slist;
	}

	@RequestMapping(value = { "getAddrById" })
	@ResponseBody
	public List<String> getAddrById(@RequestParam(value = "obId") Long obId) {
		List<String> slist = new ArrayList<String>();
		Addr addr = addrService.getAddr(obId);
		String string = addr.getArProvince() != null ? areaService
				.getAreaById(addr.getArProvince()) != null ? areaService
				.getAreaById(addr.getArProvince()).getName() != null ? areaService
				.getAreaById(addr.getArProvince()).getName() : ""
				: ""
				: "";
		string += addr.getArCity() != null ? areaService.getAreaById(addr
				.getArCity()) != null ? areaService.getAreaById(
				addr.getArCity()).getName() != null ? areaService.getAreaById(
				addr.getArCity()).getName() : "" : "" : "";
		string += addr.getArCounty() != null ? areaService.getAreaById(addr
				.getArCounty()) != null ? areaService.getAreaById(
				addr.getArCounty()).getName() != null ? areaService
				.getAreaById(addr.getArCounty()).getName() : "" : "" : "";
		string += addr.getArStreet() != null ? addr.getArStreet() : "";
		string += addr.getArAddrDetail() != null ? addr.getArAddrDetail() : "";
		slist.add(string);
		return slist;
	}

/*	@RequestMapping(value = { "getOldCzAmountByFinId" })
	@ResponseBody
	public Double getOldCzAmountByByBusiFinanceId(
			@RequestParam(value = "finId") Long finId) {
		return customerService.getOldCzAmountByBusiFinanceId(finId,
				"deadlinProductYield");
	}*/

	 //获取地区名称,Jinghr 
	public String getArea(String key) {
		return key != null ? areaService.getAreaById(key).getName() : "";
	}

	
	 /** jinghr,2013-7-12 14:21:08 0，客户电话信息集合,1，联系人电话集合； 设计思路由内向外；
	 * 接口融合性严格，前台传进的参数对象集合需为： （mobile_，tel_，cptel_,cz_,hj）
	 * 
	 * 参数类型内部是固定的，影响该方法的可用性及维护性；
	 */
	public List<Tel> TelList_CustomerOrContactperByType(
			HttpServletRequest request, String type) {
		List<Tel> telList = new ArrayList<Tel>();
		if (type.equals("0") || type.equals("1")) {
			telList.add(formatTel(request, "mobile" + type, type,
					ParamConstant.MOBILE)); // 添加手机
			telList.add(formatTel(request, "tel" + type, type,
					ParamConstant.TEL)); // 添加家庭电话
			telList.add(formatTel(request, "cptel" + type, type,
					ParamConstant.CPTEL)); // 添加工作电话
			telList.add(formatTel(request, "cz" + type, type, ParamConstant.CZ)); // 添加传真
			telList.add(formatTel(request, "hj" + type, type, ParamConstant.HJ)); // 添加户籍电话
			return telList;
		} else {
			return telList;
		}
	}

	
	 /** jinghr,2013-7-12 14:21:08 0，客户地址信息集合,1，联系人地址集合； 设计思路由内向外； 接口融合性严格，前台参数需为：
	 * （mobile_，tel_，）
	 * 
	 * 参数类型内部是固定的，影响该方法的可用性及维护性；
	 */
	public List<Addr> AddrList_CustomerOrContactperByType(
			HttpServletRequest request, String type) {
		List<Addr> addrList = new ArrayList<Addr>();
		if (type.equals("0") || type.equals("1")) {
			addrList.add(formatAddr(request, "addr" + type, type,
					ParamConstant.ADDR)); // 添加家庭地址
			addrList.add(formatAddr(request, "cpaddr" + type, type,
					ParamConstant.CPADDR)); // 添加单位地址
			addrList.add(formatAddr(request, "reg" + type, type,
					ParamConstant.REG)); // 添加籍贯地址
			addrList.add(formatAddr(request, "addrcon" + type, type,
					ParamConstant.ADDRCON)); // 添加联系地址
			addrList.add(formatAddr(request, "addzj" + type, type,
					ParamConstant.ADDZJ)); // 添加发证机关地址
			return addrList;
		} else {
			return addrList;
		}
	}

	
	 // 联系地址转换 key - value 获取对应名称,Jinghr
	 
	public Addr switchAddr(Addr addr) {
		addr.setArProvince(getArea(addr.getArProvince()));
		addr.setArCity(getArea(addr.getArCity()));
		addr.setArCounty(getArea(addr.getArCounty()));
		return addr;
	}

	@RequestMapping(value = { "find/customeridbycrname" })
	@ResponseBody
	public List<Long> findAllCustomerByName(String crName) {
		List<Long> resultList = new ArrayList<Long>();
		List<Customer> list = this.customerService
				.findAllCustomerByCrName(crName);
		if (null != list && list.size() != 0) {
			for (Customer customer : list) {
				resultList.add(customer.getId());
			}
		}
		return resultList;
	}

	/**
	 * 取单个用户
	 * 
	 * @param searchType
	 *            用户某属性，searchValue属性值
	 * @return 用户对象
	 * @throws UnsupportedEncodingException
	 */
	/*@ResponseBody
	@RequestMapping("getStaffByDepartment")
	public List<Long> getStaffByDepartment(
			@RequestParam(value = "departmentId") Long departmentId) {
		Department department = UcHelper.getDepartName(departmentId);
		List<Staff> staffList = UcHelper.getStaffsByDepartmentIdAndLevelCode(
				department.getDepCode(), ParamConstant.KEHUJINGLI);
		List<Long> staffIdList = new ArrayList<Long>();
		for (Staff staff : staffList) {
			staffIdList.add(staff.getId());
		}
		return staffIdList;
	}*/

	// ===========================捷越新增信贷，查看，修改====================================================================
	// 创建捷越信贷页面
	@RequestMapping(value = { "create_jyxd" })
	public String create_jyxd(Model model) {
		List<Parameter> crPayWageList = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.PAYWAGE,
						ParamConstant.PRSTATE);
		model.addAttribute("crPayWageList", crPayWageList);
		List<Parameter> poCompanyTypeList = SystemParameterHelper
				.findParameterByPrTypeAndPrState(
						ParamConstant.PRIVATECOMPANYTYPE, ParamConstant.PRSTATE);
		model.addAttribute("poCompanyTypeList", poCompanyTypeList);
		List<Parameter> poRoomList = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.POROOM,
						ParamConstant.PRSTATE);
		model.addAttribute("poRoomList", poRoomList);
		List<Parameter> crLiveList = SystemParameterHelper
				.findParameterByPrTypeAndPrState(ParamConstant.CRLIVE,
						ParamConstant.PRSTATE);
		model.addAttribute("crLiveList", crLiveList);
		return "crm/customer/create_jyxd";
	}

	// 修改页面
	@RequestMapping(value = { "edit_jyxd" })
	public String edit_jyxd(@RequestParam(value = "id") Long id,
			@RequestParam(value = "tabs", defaultValue = "0") Long tabs,
			Model model) {
		model.addAttribute("customersId", id);
		model.addAttribute("urlnow", "edit_jyxd");
		model.addAttribute("tabs", tabs);
		List<Parameter> crPayWageList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.PAYWAGE);
		model.addAttribute("crPayWageList", crPayWageList);
		List<Parameter> poCompanyTypeList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.PRIVATECOMPANYTYPE);
		model.addAttribute("poCompanyTypeList", poCompanyTypeList);
		List<Parameter> poRoomList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.POROOM);
		model.addAttribute("poRoomList", poRoomList);
		List<Parameter> crLiveList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.CRLIVE);
		model.addAttribute("crLiveList", crLiveList);
		return "crm/customer/edit_jyxd";
	}

	// 显示页面
	@RequestMapping(value = { "show_jyxd" })
	public String show_jyxd(@RequestParam(value = "id") Long id,
			@RequestParam(value = "tabs", defaultValue = "0") Long tabs,
			Model model) {
		model.addAttribute("customersId", id);
		model.addAttribute("urlnow", "show_jyxd");
		model.addAttribute("tabs", tabs);
		List<Parameter> crPayWageList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.PAYWAGE);
		model.addAttribute("crPayWageList", crPayWageList);
		List<Parameter> poCompanyTypeList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.PRIVATECOMPANYTYPE);
		model.addAttribute("poCompanyTypeList", poCompanyTypeList);
		List<Parameter> poRoomList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.POROOM);
		model.addAttribute("poRoomList", poRoomList);
		List<Parameter> crLiveList = SystemParameterHelper
				.findParameterByPrType(ParamConstant.CRLIVE);
		model.addAttribute("crLiveList", crLiveList);
		return "crm/customer/edit_jyxd";
	}
	/*
	// 保存捷越信息
	@RequestMapping(value = "saveJyxd", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo saveJyxd(Model model, HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		try {
			Customer customer = assembleWithAlias(request, Customer.class,
					"customer", "_");

			// 身份证验证
			Customer cusIdNum = validCusIdnum(customer.getCrIdtype(),
					customer.getCrIdnum(), customer.getId());

			if (null != cusIdNum && customer.getCrIdnum() != null) {
				return new ResultVo(false, "该客户证件号已存在！");
			}

			customer = customerService.saveOrUpdateCus(customer);
			// 增加 对 appMember 表的修改 2014.06.18 Sam.J
			updateAppMember(customer);
			Long id = customer.getId();
			if (id != null) {
				// 获取客户联系信息，并保存
				List<Tel> telList = TelList_CustomerOrContactperByType(request,
						"0");
				telService.saveOrUpTelListByCus_id(id, telList);

				// 获取客户地址信息，并保存
				List<Addr> addrList = AddrList_CustomerOrContactperByType(
						request, "0");
				addrService
						.saveOrUpAddrListByCus_id(customer.getId(), addrList);
				String ch = customer.getCrHouse();
				Integer crHouse = ch == null ? 0 : new Integer(ch);
				House house = assembleWithAlias(request, House.class, "house",
						"_");
				if (crHouse > 0 && crHouse < 4) {
					// 添加房产信息
					customerService.addHouse(id, house);
				} else {

					// 删除房产信息
					customerService.deleteHouseByCustomerId(id);
				}
				PrivateOwners privateOwners = assembleWithAlias(request,
						PrivateOwners.class, "privateOwners", "_");

				// 添加私营业主
				customerService.addPrivateOwners(id, privateOwners);
				return new ResultVo(true, customer.getId().toString());
			}
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
			return new ResultVo(false, "保存失败");
		}
		return new ResultVo(false, "客户地址保存失败 ，请返回到修改页面补充地址信息 ");
	}

	// 取得客户数据捷越信贷
	@RequestMapping(value = { "edit_jyxdById" })
	@ResponseBody
	public Map edit_jyxdById(Long id) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Map map = new HashMap();
		Customer customer = customerService.getCustomer(id);
		editMap(map, customer, "customer_");
		House house = houseService.findByCustomerid(customer.getId());
		if (house != null) {
			editMap(map, house, "house_");
		}
		PrivateOwners privateOwners = privateOwnersService
				.findByCustomerid(customer.getId());
		if (privateOwners != null) {
			editMap(map, privateOwners, "privateOwners_");
		}
		List<Tel> mobile = telService
				.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
						customer.getId(), "0", ParamConstant.MOBILE,
						ParamConstant.priorityHighNum);
		if (mobile.size() != 0) {
			editMap(map, mobile.get(0), "mobile_");
			editMap(map, mobile.get(0), "mobile0_");
		}
		;
		List<Tel> tel = telService
				.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
						customer.getId(), "0", ParamConstant.TEL,
						ParamConstant.priorityHighNum);
		if (tel.size() != 0) {
			editMap(map, tel.get(0), "tel_");
			editMap(map, tel.get(0), "tel0_");
		}
		;
		List<Tel> hj = telService
				.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
						customer.getId(), "0", ParamConstant.HJ,
						ParamConstant.priorityHighNum);
		if (hj.size() != 0) {
			editMap(map, hj.get(0), "hj0_");
		}
		;
		List<Tel> cptel0 = telService
				.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
						customer.getId(), "0", ParamConstant.CPTEL,
						ParamConstant.priorityHighNum);
		if (cptel0.size() != 0) {
			editMap(map, cptel0.get(0), "cptel0_");
		}
		;
		// List<Tel> cz =
		// telService.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(customer.getId(),
		// "0", ParamConstant.CZ,ParamConstant.priorityHighNum);
		// if (cz.size() != 0) {
		// editMap(map, cz.get(0), "cz_");
		// }
		// ;
		List<Addr> addr = addrService
				.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
						customer.getId(), "0", ParamConstant.ADDRCON,
						ParamConstant.priorityHighNum);
		if (addr.size() != 0) {
			editMap(map, addr.get(0), "addr_");
			editMap(map, addr.get(0), "addrcon0_");
		}
		;
		List<Addr> addrWork = addrService
				.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
						customer.getId(), "0", ParamConstant.CPADDR,
						ParamConstant.priorityHighNum);
		if (addrWork.size() != 0) {
			editMap(map, addrWork.get(0), "cpaddr0_");
		}
		;
		List<Addr> addrOrig = addrService
				.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
						customer.getId(), "0", ParamConstant.REG,
						ParamConstant.priorityHighNum);
		if (addrOrig.size() != 0) {
			editMap(map, addrOrig.get(0), "reg0_");
		}
		;
		List<Addr> addrZj = addrService
				.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
						customer.getId(), "0", ParamConstant.ADDZJ,
						ParamConstant.priorityHighNum);
		if (addrZj.size() != 0) {
			editMap(map, addrZj.get(0), "zj_");
		}
		;
		List<Contactperson> cp = contactpersonService
				.findAllByCrmCustomer(customer);
		if (cp.size() != 0) {
			editMap(map, cp.get(0), "cp_");
			List<Tel> cpmobile = telService
					.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
							cp.get(0).getId(), "1", ParamConstant.MOBILE,
							ParamConstant.priorityHighNum);
			if (cpmobile.size() != 0) {
				editMap(map, cpmobile.get(0), "cpmobile_");
			}
			;
			List<Tel> cptel = telService
					.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
							cp.get(0).getId(), "1", ParamConstant.TEL,
							ParamConstant.priorityHighNum);
			if (cptel.size() != 0) {
				editMap(map, cptel.get(0), "cptel_");
			}
			;
			// List<Tel> cpcz =
			// telService.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(cp.get(0).getId(),
			// "1", ParamConstant.CZ,ParamConstant.priorityHighNum);
			// if (cpcz.size() != 0) {
			// editMap(map, cpcz.get(0), "cpcz_");
			// }
			// ;
			List<Addr> cpaddr = addrService
					.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
							cp.get(0).getId(), "1", ParamConstant.ADDRCON,
							ParamConstant.priorityHighNum);
			if (cpaddr.size() != 0) {
				editMap(map, cpaddr.get(0), "cpaddr_");
			}
			;

		}
		;
		return map;
	}

	// 进入联系人页面
	@RequestMapping(value = { "create_contactjyxd" })
	public String create_contactjyxd(@RequestParam(value = "id") Long id,
			Model model) {
		model.addAttribute("id", id);
		model.addAttribute("urlnow", "create_contactjyxd");
		return "crm/customer/create_contactjyxd";
	}

	// 完成对捷越数据录入
	@RequestMapping(value = { "completeJyxd" })
	@ResponseBody
	public ResultVo completeJyxd(@RequestParam(value = "id") Long id,
			Model model) {

		if (id != null && id != 0) {
			return new ResultVo(true, "", "/crm/customer/jy_apply?id=" + id);
		} else {
			return new ResultVo(false, ParamConstant.ERRORA);
		}

	}

	*//**
	 * 捷越信贷业务申请
	 * 
	 * @author Yuan Changchun
	 * @date 2013-7-26 下午03:24:51
	 * @return
	 *//*
	@RequestMapping(value = "/jy_apply")
	public String applyJyxd(
			@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		Customer customers = customerService.getCustomer(id);
		model.addAttribute("customers", customers);
		return "/crm/product/jieyue/credit/apply";
	}

	// 捷越附属信息查看
	@RequestMapping(value = { "jyfushuxinxi" })
	public String jyfushuxinxi(Model model, ServletRequest request,
			@RequestParam(value = "customerId") Long customerId) {
		Customer customer = customerService.getCustomer(customerId);
		model.addAttribute("customers", customer);
		return "crm/customer/jyfushuxinxi";
	}

	// 捷越附属信息创建
	@RequestMapping(value = { "jyxjLxr" })
	public String jyxjLxr(Model model, ServletRequest request,
			@RequestParam(value = "customerId") Long customerId) {
		Customer customer = customerService.getCustomer(customerId);
		model.addAttribute("customers", customer);
		model.addAttribute("customerid", customerId);
		model.addAttribute("customerName",
				customerService.getCustomer(customerId).getCrName());
		return "crm/customer/jyxjLxr";
	}

	*/
	/**
	 * @Title: 新增对app数据同步
	 * @Description: 当修改原crm内容时，增加同步对app表中内容进行修改
	 * @param $
	 *            {tags} 设定文件
	 * @return String 返回类型 noRecord:没有对应的APP记录 fail：出错失败 succes：更新成功
	 * @throws
	 */
	private void updateAppMember(Customer customer) {
		memberService.updateMember(customer);
	}
	
	// 根据E-mail查询客户信息
	@RequestMapping(value = { "customerByCrEmail" })
	@ResponseBody
	public Customer customerByCrEmail(
			@RequestParam(value = "crEmail") String crEmail,
			@RequestParam(value = "customerId") Long customerId,
			HttpServletRequest request) throws UnsupportedEncodingException {
		if (crEmail != null && crEmail != "") {
			crEmail = crEmail.toLowerCase();
		}
		List<Customer> customerList = customerService.findAllByCrEmail(crEmail);
		if (customerList.size() < 1) {
			return null;
		} else if (customerList.size() == 1) {
			if (customerId != null) {
				if (customerList.get(0).getId().equals(customerId)) {
					return null;
				} else {
					return customerList.get(0);
				}
			} else {
				return customerList.get(0);
			}
		} else {
			return customerList.get(0);
		}
	}
	/**
	 * @Title:格式化爱好
	 * @Description: 客户偏好通过request直接赋值返回list
	 * @param request
	 * @return
	 * @throws
	 * @time:2014-9-4 下午01:54:55
	 * @author:Sam.J
	 */
	private List<Attribute> formatHobby(HttpServletRequest request,
			Long customerid,SysUser sysuser) {
		String[] hobbies = request.getParameterValues(ParamConstant.PAGEHOBBY);
		List<Attribute> list = new ArrayList<Attribute>();
		//Staff staff = AuthorityHelper.getStaff();
		if (hobbies != null) {
			for (String hobby : hobbies) {
				Attribute a = new Attribute();
				a.sethType(ParamConstant.PARAMHOBBY);
				a.setCustomerid(customerid);
				a.sethValue(hobby);
				a.sethInputId(sysuser.getId());
				a.sethInputTime(new Date());
				a.sethModifyId(sysuser.getId());
				a.sethModifyTime(new Date());
				list.add(a);
			}
		}
		return list;
	}

	/**
	 * @Title:新增或修改偏好值
	 * @Description:
	 * @param newlist
	 * @param customerid
	 * @throws
	 * @time:2014-9-4 下午03:38:01
	 * @author:Sam.J
	 */
	private void saveOrUpdateHobby(List<Attribute> newlist, Long customerid) {
		List<Attribute> oldList = attributeService.findALlByCustomeridAndHType(
				customerid, ParamConstant.PARAMHOBBY);
		// 先除去原数据中被修改取消的
		if (oldList.size() > 0) {
			for (Attribute o : oldList) {
				boolean flag = false; // 标志判断位
				if ("".equals(o.gethValue()) || o.gethValue() == null)
					continue;
				for (Attribute n : newlist) {
					if (o.gethValue().equals(n.gethValue())) { // 如新操作中有和原数据同值的，说明不用删除，标志位改为true
						flag = true;
					}
				}
				if (flag)
					continue; // 标志位如为true 跳出此次循环
				attributeService.deletAttribute(o); // 删除原数据
			}
		}
		// 新增原来库中不存在的
		if (newlist.size() > 0) {
			for (Attribute n : newlist) {
				boolean flag = false; // 标志判断位
				if ("".equals(n.gethValue()) || n.gethValue() == null)
					continue;
				for (Attribute o : oldList) {
					if (n.gethValue().equals(o.gethValue())) { // 如新操作中有和原数据同值的，说明不用新增，标志位改为true
						flag = true;
					}
				}
				if (flag)
					continue; // 标志位如为true 跳出此次循环
				attributeService.saveAttribute(n); // 新增数据
			}
		}
	}

	/**
	 * @Title:将爱好数据转换成数组，方便页面展示
	 * @Description:
	 * @param newlist
	 * @return
	 * @throws
	 * @time:2014-9-5 下午01:34:09
	 * @author:Sam.J
	 */
	private String[] fomatHobbyForPage(List<Attribute> newlist) {
		String[] strs = new String[newlist.size()];
		for (int i = 0; i < newlist.size(); i++) {
			strs[i] = newlist.get(i).gethValue();
		}
		return strs;
	}
	
	
	// 取得客户的手机号 
		@RequestMapping(value = { "get/phone" })
		public String getPhone(Long id, HttpServletResponse response) {
			List<Tel> mobile = telService
					.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
							id, "0", ParamConstant.MOBILE,
							ParamConstant.priorityHighNum);
			if(mobile.size()>0&&mobile!=null){
			PrintWriter printWrite = null;
			try {
				printWrite = response.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
			}
			printWrite.write(mobile.get(0).getTlTelNum()==null?"":mobile.get(0).getTlTelNum());
			printWrite.flush();
			printWrite.close();
			}
			return null;
		}
		
}
