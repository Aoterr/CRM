package com.zendaimoney.crm.product.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.orm.PageVo;
import org.springside.modules.orm.ResultVo;
import org.springside.modules.orm.SearchFilter;
import org.springside.modules.orm.SearchFilter.Operator;

import com.google.common.base.Strings;
import com.zendaimoney.constant.LoggingSource;
import com.zendaimoney.constant.LoggingType;
import com.zendaimoney.constant.ParamConstant;
import com.zendaimoney.constant.Share;
import com.zendaimoney.crm.CrudUiController;
import com.zendaimoney.crm.customer.entity.Bankaccount;
import com.zendaimoney.crm.customer.entity.Customer;
import com.zendaimoney.crm.customer.entity.RecordStaff;
import com.zendaimoney.crm.customer.service.BankaccountService;
import com.zendaimoney.crm.customer.service.CustomerService;
import com.zendaimoney.crm.product.entity.BusiCredit;
import com.zendaimoney.crm.product.entity.BusiFinance;
import com.zendaimoney.crm.product.entity.BusiProduct;
import com.zendaimoney.crm.product.entity.ProductView;
import com.zendaimoney.crm.product.service.BusiCreditService;
import com.zendaimoney.crm.product.service.BusiFinanceService;
import com.zendaimoney.crm.product.service.BusiProductService;
import com.zendaimoney.crm.product.service.ProductViewService;
import com.zendaimoney.crm.product.service.RecordStaffService;
import com.zendaimoney.sys.parameter.entity.Parameter;
import com.zendaimoney.uc.rmi.vo.DataPermission;
import com.zendaimoney.uc.rmi.vo.Staff;
import com.zendaimoney.utils.helper.SystemParameterHelper;
import com.zendaimoney.utils.mapper.BeanMapper;

/**
 * 产品申购控制器
 * 
 * @author zhanghao
 * @create 2012-11-14, 下午03:18:16
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/product/purchase")
public class ProductPurchaseController extends CrudUiController<ProductView> {

	@Autowired
	private BusiProductService busiProductService;
	/*

	@Autowired
	private BusiCreditService busiCreditService;

	@Autowired
	private BusiFinanceService financeService;
	@Autowired
	private BankaccountService bankaccountService;

	@Autowired
	private ProductViewService productViewService;

	@Autowired
	private RecordStaffService recordStaffService;

	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = { "" })
	public String index(Model model) {
		return "/crm/product/purchase/list";
	}*/

	// 查找所有产品
	@ResponseBody
	@RequestMapping(value = "/find/all/product")
	public List<BusiProduct> findAllProduct() {
		return busiProductService.findAllBusiProduct();
	}
	/*
	// list页面
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
	@RequestMapping(value = "/list")
	public PageVo list(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "auto") String sort,
			@RequestParam(value = "order", defaultValue = "asc") String order,
			Model model, ServletRequest request) {
		Map<String, SearchFilter> filtersMap = new HashMap<String, SearchFilter>();
		// SearchFilter searchDataOrigin=new
		// SearchFilter("feDataOrigin",Operator.EQ,ParamConstant.FE_DATA_ORIGIN_PC);
		// //只展示数据来源是 线下的业务单子
		SearchFilter searchDataValid = new SearchFilter("feValidState",
				Operator.EQ, ParamConstant.VALID); // 只展示数据状态为1 即没删除作废的单子
		// filtersMap.put("feDataOrigin", searchDataOrigin); //加入搜索条件
		filtersMap.put("feValidState", searchDataValid);
		Page<ProductView> productViewPage = productViewService
				.getAllProductView(
						buildSpecification(request, filtersMap,
								Share.PRODUCT_REQUEST, "manager"),
						buildPageRequest(page, rows, sort, order));
		return new PageVo(productViewPage);
	}

	// 跳转到信贷产品申购页面
	@RequestMapping(value = "/credit_apply")
	public String toCreditProductApply() {
		return "/crm/product/purchase/credit_apply";
	}

	// 跳转到理财产品申购页面
	@RequestMapping(value = "/finance_apply")
	public String toFortuneProductApply(Model model) {
		List<Parameter> deductCompanyList = SystemParameterHelper
				.findParameterByPrType("deductCompany");
		List<Parameter> deductCompanyList=SystemParameterHelper.findParameterByPrTypeAndPrState("deductCompany", "1");
		model.addAttribute("deductCompanyList", deductCompanyList);
		model.addAttribute("customers", new Customer());
		return "/crm/product/purchase/finance_apply";
	}

	// 信贷产品申请
	@ResponseBody
	@RequestMapping(value = "/credit/apply")
	public ResultVo saveCreditApply(BusiCredit busiCredit) {
		ResultVo resultVo = null;
		try {
			boolean flag = busiCreditService.saveBusiCredit(busiCredit);
			resultVo = new ResultVo(flag);
		} catch (Exception e) {
			e.printStackTrace();
			resultVo = errorResutlVo();
		}
		return resultVo;
	}

	// 理财产品申请
	@ResponseBody
	@RequestMapping(value = "/finance/apply")
	public ResultVo saveFinanceApply(BusiFinance busiFinance,
			@RequestParam(value = "state") String state,
			@RequestParam(value = "returnAccount") String returnAccount) {
		ResultVo resultVo = null;
		
		// 增加校验   防止重复提交    by  wb_lyq  2015-11-05  
		List<Long> idList = new ArrayList<Long>();		
		if(busiFinance.getFePreviousId()!=null){
			idList.add(busiFinance.getFePreviousId());
			boolean flag=financeService.findByPreId(idList);
			if(!flag){
				return new ResultVo(false, "该产品已进行过到期续投操作");
			}
		}
			
		try {
			busiFinance.setFePaymentWay(busiFinance.getFePaymentWay()
					.substring(0, 1));
			if ("8".equals(busiFinance.getFeProduct())
					&& (busiFinance.getFeAmount() < 50000
							|| busiFinance.getFeAmount() > 3000000 || busiFinance
							.getFeAmount() % 50000 != 0))
				return new ResultVo(false, "月稳盈起投金额5万元，上限300万元，并且金额必须是5万的倍数");
			// 为了南京房产项目，做改造 14.08.25
			if (returnAccount != null && !"".equals(returnAccount)
					&& !"undefined".equals(returnAccount))
				busiFinance.setFeReturnAccount(Long.parseLong(returnAccount));
			resultVo = financeService.saveBusiFinance(busiFinance, state);
			// 增加报表记录 by Sam.J 14.06.20
			if (resultVo.result.containsKey("id")) // 加上是否为空判断，如为空不执行避免报错 by
													// Sam.J 14.06.20
				AddRecordStaff(busiFinance.getFeManager(), state,
						resultVo.result.get("id").toString());
		} catch (Exception e) {
			e.printStackTrace();
			resultVo = errorResutlVo();
		}
		return resultVo;
	}

	// 理财产品更新
	@ResponseBody
	@RequestMapping(value = "/finance/update")
	public ResultVo updateFinanceApply(BusiFinance busiFinance,
			@RequestParam(value = "state") String state,
			@RequestParam(value = "returnAccount") String returnAccount) {
		ResultVo resultVo = null;
		try {
			busiFinance.setFePaymentWay(busiFinance.getFePaymentWay()
					.substring(0, 1));
			// 为了南京房产项目，做改造 14.08.25
			if (returnAccount != null && !"".equals(returnAccount)
					&& !"undefined".equals(returnAccount))
				busiFinance.setFeReturnAccount(Long.parseLong(returnAccount));
			resultVo = financeService.updateBusiFinance(busiFinance, state);
			// 增加报表记录 by Sam.J 14.06.20
			if (resultVo.result.containsKey("id")&&state.equals(AuditHelper.getStateValue(ParamConstant.REQUEST_STATE_DAIZHIJIAN))) // 加上是否为空判断，如为空不执行避免报错 by
													//修改状态记录时的BUG 回退状态下修改 后只有待质检状态才记录   Sam.J 15.07.15
				AddRecordStaff(busiFinance.getFeManager(), state,
						resultVo.result.get("id").toString());
		} catch (Exception e) {
			e.printStackTrace();
			resultVo = errorResutlVo();
		}
		return resultVo;
	}

	// 理财审核
	@ResponseBody
	@RequestMapping(value = "/finance/audit")
	public ResultVo financeAudti(@RequestParam(value = "id") Long id,
			@RequestParam(value = "auditResult") String auditResult,
			@RequestParam(value = "content") String content) {
		ResultVo resultVo = null;
		try {
			resultVo = financeService.auditFinance(id, content, auditResult);
			// 增加报表记录 by Sam.J 14.06.20
			// 将质检记录保存入库，用于查询质检人员
			// if(!"4".equals(auditResult)){
			long feManager = Long.parseLong(resultVo.result.get("manager")
					.toString());
			if (resultVo.result.containsKey("id")) // 加上是否为空判断，如为空不执行避免报错 by
													// Sam.J 14.06.20
				AddRecordStaff(feManager, auditResult, resultVo.result
						.get("id").toString());
			// }
		} catch (Exception e) {
			e.printStackTrace();
			resultVo = errorResutlVo();
		}
		return resultVo;
	}

	// 理财复核
	@ResponseBody
	@RequestMapping(value = "/check/finance/audit")
	public ResultVo checkFinanceAudti(@RequestParam(value = "id") Long id,
			@RequestParam(value = "auditResult") String auditResult,
			@RequestParam(value = "stateId") String stateId,
			@RequestParam(value = "content") String content) {
		ResultVo resultVo = null;
		try {
			resultVo = financeService.auditFinanceCheck(id, content,
					auditResult, stateId);
			// 增加报表记录 by Sam.J 14.06.20
			long feManager = Long.parseLong(resultVo.result.get("manager")
					.toString());
			if (resultVo.result.containsKey("id")) // 加上是否为空判断，如为空不执行避免报错 by
													// Sam.J 14.06.20
				AddRecordStaff(feManager, auditResult, resultVo.result
						.get("id").toString());
		} catch (Exception e) {
			e.printStackTrace();
			resultVo = errorResutlVo();
		}
		return resultVo;
	}

	// 理财质检复核
	@ResponseBody
	@RequestMapping(value = "/show/finance/audit")
	public ResultVo showFinanceAudti(@RequestParam(value = "id") Long id,
			@RequestParam(value = "auditResult") String auditResult,
			@RequestParam(value = "stateId") String stateId,
			@RequestParam(value = "content") String content) {
		ResultVo resultVo = null;
		try {
			resultVo = financeService.auditFinanceCheck(id, content,
					auditResult, stateId);
			// 增加报表记录 by Sam.J 14.06.20
			long feManager = Long.parseLong(resultVo.result.get("manager")
					.toString());
			if (resultVo.result.containsKey("id")) // 加上是否为空判断，如为空不执行避免报错 by
													// Sam.J 14.06.20
				AddRecordStaff(feManager, auditResult, resultVo.result
						.get("id").toString());
		} catch (Exception e) {
			e.printStackTrace();
			resultVo = errorResutlVo();
		}
		return resultVo;
	}

	// 提交申请
	@ResponseBody
	@RequestMapping(value = "/submit")
	public ResultVo submit(@RequestParam(value = "id") Long id,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "content") String content) {
		ResultVo resultVo = null;
		try {
			resultVo = isCredit(type) ? busiCreditService.submit(id, content)
					: null;
		} catch (Exception e) {
			e.printStackTrace();
			resultVo = errorResutlVo();
		}
		return resultVo;
	}

	// 退回申请
	@ResponseBody
	@RequestMapping(value = "/back")
	public ResultVo back(@RequestParam(value = "id") Long id,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "content") String content) {
		ResultVo resultVo = null;
		try {
			resultVo = isCredit(type) ? busiCreditService.back(id, content)
					: null;
		} catch (Exception e) {
			e.printStackTrace();
			resultVo = errorResutlVo();
		}
		return resultVo;
	}

	// 查找信贷产品
	@ResponseBody
	@RequestMapping(value = "/find/credit/product")
	public List<BusiProduct> findCreditProduct() {
		return busiProductService.findAllCreditProduct();
	}
 
	// 查找理财产品
	@ResponseBody
	@RequestMapping(value = "/find/finance/product")
	public List<BusiProduct> findFinanceProduct() {
	    return busiProductService.findAllFinanceProduct();
    }
	
	*//**
	 * 查找双鑫+  岁悦+ 
	 * 
	 * @author wb_lyq
	 * @date 2015-10-28 16:50:42
	 * @param type
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/find/customized/product")
	public List<BusiProduct> findCustomizedProduct() {
		String ids ="9,10";
		List<Long> types = new ArrayList<Long>();
		String[] typesplit = ids.split(",");
		for(int i=0;i<typesplit.length;i++){
			types.add(Long.parseLong(typesplit[i]));
		}
		return busiProductService.findCustomizedProduct(types);
	}

	// 查找理财产品(传入查询类型参数，依照参数查询结果)
	@ResponseBody
	@RequestMapping(value = "/find/finance/product_byType")
	public List<BusiProduct> findFinanceProductByType(
			@RequestParam(value = "type") String type) {
		List<String> types = new ArrayList<String>();
		String[] typesplit = type.split(",");
		for (String typeone : typesplit) {
			types.add(typeone);
		}
		return busiProductService.findProductByTypes(types);
	}
	
	// 跳转到产品明细页面
	@RequestMapping(value = "/show")
	public String toProductShow(@RequestParam(value = "id") Long id,
			@RequestParam(value = "type") String type, Model model) {
		model.addAttribute("productId", id);
		if (isCredit(type)) {
			return "/crm/product/purchase/credit_show";
		} else if (ParamConstant.PAGE_SHOW_APP.equals(type)) {
			List<Parameter> deductCompanyList = SystemParameterHelper
					.findParameterByPrType("deductCompany");
			model.addAttribute("deductCompanyList", deductCompanyList);
			return "/crm/product/purchase/finance_show_forapp";
		} else {
			List<Parameter> deductCompanyList = SystemParameterHelper
					.findParameterByPrType("deductCompany");
			model.addAttribute("deductCompanyList", deductCompanyList);
			if (financeService.findOneFinance(id).getFePreviousId() != null) {
				return "/crm/finance/continue_invest_show";
			} else {
				return "/crm/product/purchase/finance_show";
			}
		}
	}

	// 跳转到产品编辑页面
	@RequestMapping(value = "/edit")
	public String toProductEdit(@RequestParam(value = "id") Long id,
			@RequestParam(value = "type") String type, Model model) {
		// 用于控制审核安全,当一笔业务处于审核阶段,则不能跳转
		if (AuditHelper.isEdit(String.valueOf(productViewService.findOne(id)
				.getState()))) {
			model.addAttribute("productId", id);
			if (isCredit(type)) {
				return "/crm/product/purchase/credit_edit";
			} else {
				List<Parameter> deductCompanyList = SystemParameterHelper
						.findParameterByPrType("deductCompany");
				List<Parameter> deductCompanyList=SystemParameterHelper.findParameterByPrTypeAndPrState("deductCompany", "1");
				model.addAttribute("deductCompanyList", deductCompanyList);
				if (financeService.findOneFinance(id).getFePreviousId() != null) {
					return "/crm/finance/continue_invest_edit";
				} else {
					return "/crm/product/purchase/finance_edit";
				}
			}
		}
		return getIllegalityAddr();
	}

	// 跳转到产品预审页面
	@RequestMapping(value = "/preliminary/review")
	public String toPreliminaryReview(@RequestParam(value = "id") Long id,
			@RequestParam(value = "type") String type, Model model) {
		// 用于控制审核安全,当一笔业务处于审核阶段,则不能跳转
		if (AuditHelper.isSubmit(String.valueOf(productViewService.findOne(id)
				.getState()))) {
			model.addAttribute("productId", id);
			if (isCredit(type)) {
				return "/crm/product/purchase/credit_preliminary_review";
			} else {
				List<Parameter> deductCompanyList = SystemParameterHelper
						.findParameterByPrType("deductCompany");
				model.addAttribute("deductCompanyList", deductCompanyList);
				if (financeService.findOneFinance(id).getFePreviousId() != null) {
					return "/crm/finance/continue_invest_preliminary_review";
				} else {
					return "/crm/product/purchase/finance_preliminary_review";
				}
			}
		}
		return getIllegalityAddr();
	}

	// 跳转到产品复审页面
	@RequestMapping(value = "/check/preliminary/review")
	public String checkToPreliminaryReview(@RequestParam(value = "id") Long id,
			@RequestParam(value = "type") String type, Model model) {
		model.addAttribute("productId", id);
		model.addAttribute("stateId", productViewService.findOne(id).getState());
		if (isCredit(type)) {
			return "/crm/product/purchase/credit_preliminary_review";
		} else {
			List<Parameter> deductCompanyList = SystemParameterHelper
					.findParameterByPrType("deductCompany");
			model.addAttribute("deductCompanyList", deductCompanyList);
			// 续投
			if (financeService.findOneFinance(id).getFePreviousId() != null) {
				return "/crm/finance/continue_invest_preliminary_review_check";
			} else {
				return "/crm/product/purchase/finance_preliminary_review_check";
			}
		}
	}

	// 跳转到产品复审查看页面
	@RequestMapping(value = "/show/preliminary/review")
	public String showToPreliminaryReview(@RequestParam(value = "id") Long id,
			@RequestParam(value = "type") String type, Model model) {
		model.addAttribute("productId", id);
		model.addAttribute("stateId", productViewService.findOne(id).getState());
		if (isCredit(type)) {
			return "/crm/product/purchase/credit_preliminary_review";
		} else {
			List<Parameter> deductCompanyList = SystemParameterHelper
					.findParameterByPrType("deductCompany");
			model.addAttribute("deductCompanyList", deductCompanyList);
			// 续投
			if (financeService.findOneFinance(id).getFePreviousId() != null) {
				return "/crm/finance/continue_invest_preliminary_review_show";
			} else {
				return "/crm/product/purchase/finance_preliminary_review_show";
			}
		}
	}

	// 根据id查找信贷业务
	@ResponseBody
	@RequestMapping(value = "/get/credit/product")
	public BusiCredit findOneCredit(Long id) {
		return busiCreditService.findOneCredit(id);
	}

	// 根据id查找理财业务
	@ResponseBody
	@RequestMapping(value = "/get/finance/product")
	public BusiFinance findOneFinance(Long id) {
		return financeService.findOneFinance(id);
	}

	// 根据客户id查找客户的所有银行信息
	@ResponseBody
	@RequestMapping(value = "/find/customer/bank")
	public List<Bankaccount> findBankAccountByCustomerId(
			@RequestParam(value = "id") Long id) {
		List<Bankaccount> bankaccounts = bankaccountService
				.findALlByCustomerid(id);
		return bankaccounts;
	}

	// 根据银行id查询单个银行信息
	@ResponseBody
	@RequestMapping(value = "/get/bank")
	public Bankaccount getBankAccountById(@RequestParam(value = "id") Long id) {
		return bankaccountService.getBankaccount(id);
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor dateEditor = new CustomDateEditor(fmt, true);
		binder.registerCustomEditor(Date.class, dateEditor);
	}

	// 查根据产品id查找单个产品
	@ResponseBody
	@RequestMapping(value = "/get/product")
	public BusiProduct getProduct(@RequestParam(value = "id") Long id) {
		return busiProductService.findOneProduct(id);
	}

	// 跳转到查询页面
	@RequestMapping(value = { "query" })
	public String complaint_query(Model model) {
		return "/crm/product/purchase/purchase_query";
	}

	// 检查合同编号是否唯一
	@ResponseBody
	@RequestMapping(value = "/check/feContractNo/unique")
	public boolean checkFeContractNoUnique(
			@RequestParam(value = "feContractNo") String feContractNo,
			@RequestParam(value = "type") String type) {
		return type.equals("add") ? financeService
				.checkFeContractNoUniqueAdd(feContractNo) : financeService
				.checkFeContractNoUniqueEdit(feContractNo, Long.valueOf(type));
	}

	public ResultVo errorResutlVo() {
		return new ResultVo(false, "操作失败,请重新尝试或联系管理员!");
	}

	// 删除理财申请
	@ResponseBody
	@RequestMapping(value = { "/finance/del" })
	public ResultVo del(@RequestParam(value = "id") Long id) {
		ResultVo resultVo = null;
		try {
			BusiFinance busiFinance = financeService.findOneFinance(id);
			LoggingHelper.createLogging(LoggingType.删除, LoggingSource.产品申请,
					"出借编号：" + busiFinance.getFeLendNo() + ",合同编号："
							+ busiFinance.getFeContractNo());
			resultVo = financeService.delBusiFinance(id);
		} catch (Exception e) {
			e.printStackTrace();
			resultVo = errorResutlVo();
		}
		return resultVo;
	}

	*//**
	 * 获取非法请求的地址
	 * 
	 * @author zhanghao
	 * @date 2013-03-05 10:20:18
	 * @return
	 *//*
	public String getIllegalityAddr() {
		return "/error/illegality";
	}

	*//**
	 * 判断是否为credit业务
	 * 
	 * @author zhanghao
	 * @date 2013-01-11 16:50:42
	 * @param type
	 * @return
	 *//*
	protected boolean isCredit(String type) {
		return type.equals(ParamConstant.PRODUCT_TYPE_CREDIT);
	}

	*//**
	 * 从到期提醒到理财续投界面的跳转
	 * 
	 * @param feLendNo
	 *            出借编号
	 * @param mark
	 *            用来标识从到期提醒页面转到理财续投界面
	 * @param model
	 * @return
	 *//*
	@RequestMapping(value = "/maturitylist/tocontinueinvestadd")
	public String maturityToContinueInvestAdd(
			@RequestParam(value = "feLendNo") String feLendNo,
			@RequestParam(value = "mark") String mark, Model model) {
		model.addAttribute("feLendNo", feLendNo);
		model.addAttribute("mark", mark);
		return "/crm/finance/continue_invest_add";
	}

	@ResponseBody
	@RequestMapping(value = { "/getgatherids" })
	public List<Long> getGrtherIdsByStaffId() {
		ArrayList<Long> ids = new ArrayList<Long>();
		List<DataPermission> permissions = AuthorityHelper.getStaff()
				.getPartDepartmentDataPermission();
		for (DataPermission dataPermission : permissions) {
			ids.add(dataPermission.getSatffId());
		}
		return ids;
	}

	----------------------2014.6.20 add-----------------
	*//**
	 * @Title: 报表系统日记记录
	 * @Description: 当业务新发起后状态变化时，在recourdStaff表中插入记录
	 * @param $
	 *            {tags} 设定文件
	 * @return String 返回类型 noRecord:没有对应的APP记录 fail：出错失败 succes：更新成功
	 * @throws
	 *//*
	private void AddRecordStaff(Long feManager, String state, String busiId) {
		try {
			// 报表系统日记记录
			RecordStaffVo record = UcHelper.getRecordStaff(feManager);
			record.setStatus(state);
			record.setBusiId(Long.parseLong(busiId));
			RecordStaff staffLogger = BeanMapper.map(record, RecordStaff.class);
			staffLogger.setStartDate(new Date());
			staffLogger.setEndDate(new Date());
			// 增加操作人id记录入库 方便报表系统追溯 Sam.J 14.11.5
			Staff staff = AuthorityHelper.getStaff();
			staffLogger.setInputId(staff.getId());
			recordStaffService.saveRecordStaff(staffLogger);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	----------------------2014.8.21 add-----------------

	*//**
	 * @Title:跳转到理财产品申购页面(为房产项目专供模板)
	 * @Description:
	 * @param model
	 * @return
	 * @throws
	 * @time:2014-8-21 下午03:05:50
	 * @author:Sam.J
	 *//*
	@RequestMapping(value = "/finance_apply_forhouse")
	public String toFortuneProductApplyForHouse(Model model) {
		List<Parameter> deductCompanyList = SystemParameterHelper
				.findParameterByPrType("deductCompany");
		model.addAttribute("deductCompanyList", deductCompanyList);
		model.addAttribute("customers", new Customer());
		return "/crm/product/purchase/finance_apply_forhouse";
	}

	*//**
	 * @Title:为第三方理财模板做产品过滤（目前用于南京房产项目）
	 * @Description:
	 * @return
	 * @throws
	 * @time:2014-8-21 下午03:05:26
	 * @author:Sam.J
	 *//*
	@ResponseBody
	@RequestMapping(value = "/find/finance/product_forhouse")
	public List<BusiProduct> findFinanceProductForHouse() {
		List<BusiProduct> list = busiProductService.findAllFinanceProduct();
		for (int i = list.size() - 1; i >= 0; i--) { // 只能做季喜
			BusiProduct b = list.get(i);
			if (!ParamConstant.FORTUNE_PRODUCT_ZHENGDAJIXI.equals(b.getId()
					.toString())) {
				list.remove(b);
			}
		}
		return list;
	}

	*//**
	 * @Title:查询出南京地产公司2个公司账号
	 * @Description:
	 * @return
	 * @throws
	 * @time:2014-8-22 上午10:12:46
	 * @author:Sam.J
	 *//*
	@ResponseBody
	@RequestMapping(value = "/find/house/bank")
	public List<Bankaccount> findBankAccountByHouse() {
		List<Bankaccount> bankaccounts = bankaccountService
				.findALlByCustomerid(Long
						.parseLong(ParamConstant.HouseCustomerid));
		return bankaccounts;
	}

	*//**
	 * @Title:根据id查出账号信息
	 * @Description:
	 * @return
	 * @throws
	 * @time:2014-8-22 上午10:12:46
	 * @author:Sam.J
	 *//*
	@ResponseBody
	@RequestMapping(value = "/find/bank_byid")
	public Bankaccount findBankAccountById(
			@RequestParam(value = "bankid") Long bankid) {
		Bankaccount bankaccount = bankaccountService.getBankaccount(bankid);
		return bankaccount;
	}

	*//**
	 * @Title:保存回款账户为公司账户
	 * @Description: TODO
	 * @param bankid
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @throws
	 * @time:2014-8-25 下午04:10:26
	 * @author:Sam.J
	 *//*
	@ResponseBody
	@RequestMapping(value = "/save/house/bank")
	public ResultVo financeSaveBankForHouse(
			@RequestParam(value = "bankid") Long bankid,
			@RequestParam(value = "customerid") Long customerid)
			throws Exception {
		ResultVo resultVo = null;
		try {
			List<Bankaccount> list = bankaccountService
					.findALlByCustomerid(customerid);
			Bankaccount b = bankaccountService.getBankaccount(bankid);
			for (Bankaccount temp : list) {
				if (b.getBaAccount().equals(temp.getBaAccount())) { // 判断是否已经存在，如已存在，直接返回
					return new ResultVo(true, temp.getId());
				}
			}
			Bankaccount bank = new Bankaccount();
			bank.setBaAccount(b.getBaAccount()); // 账号
			bank.setBaAccountName(b.getBaAccountName()); // 户名
			bank.setBaAccountType(b.getBaAccountType()); // 账户类别
			bank.setBaBankCode(b.getBaBankCode()); // 银行代码
			bank.setBaBankName(b.getBaBankName()); // 银行名字
			bank.setBaBranchName(b.getBaBranchName()); // 分支姓名
			bank.setBaMemo(b.getBaMemo()); // 备注
			bank.setBaValid(b.getBaValid()); // 是否可用
			bank.setCrmCustomer(customerService.getCustomer(customerid)); // 客户
			bank = bankaccountService.saveBankaccount(bank);
			resultVo = new ResultVo(true, bank.getId());
		} catch (Exception e) {
			resultVo = new ResultVo(false, "后台保存回款账户信息出错，请联系管理员！");
			e.printStackTrace();
		}
		return resultVo;
	}

	*//**
	 * @Title:跳转房产项目编辑页面
	 * @Description: TODO
	 * @param id
	 * @param type
	 * @param model
	 * @return
	 * @throws
	 * @time:2014-8-25 下午04:09:51
	 * @author:Sam.J
	 *//*
	@RequestMapping(value = "/edit_forhouse")
	public String toProductEditForHouse(@RequestParam(value = "id") Long id,
			@RequestParam(value = "type") String type, Model model) {
		// 用于控制审核安全,当一笔业务处于审核阶段,则不能跳转
		if (AuditHelper.isEdit(String.valueOf(productViewService.findOne(id)
				.getState()))) {
			model.addAttribute("productId", id);
			List<Parameter> deductCompanyList = SystemParameterHelper
					.findParameterByPrType("deductCompany");
			model.addAttribute("deductCompanyList", deductCompanyList);
			return "/crm/product/purchase/finance_edit_forhouse";
		}
		return getIllegalityAddr();
	}

	*//**
	 * 根据出借编号查询理财信息(非续投)
	 * 
	 * @author Yuan Changchun
	 * @date 2013-9-5 下午05:00:10
	 * @param feLendNo
	 * @param request
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/find/feLendNoWithoutApp")
	public List<BusiFinance> feLendNoWithoutApp(
			@RequestParam(value = "feLendNo") String feLendNo,
			ServletRequest request) {
		return financeService.feLendNoWithoutApp(feLendNo);
	}

	*//**
	 * @Title:删除理财订单
	 * @Description: 新方法，修改原来的物理删除为逻辑删除
	 * @param id
	 * @return
	 * @throws
	 * @time:2014-12-15 下午04:01:14
	 * @author:Sam.J
	 *//*
	@ResponseBody
	@RequestMapping(value = { "/finance/delBusi" })
	public ResultVo delBusi(@RequestParam(value = "id") Long id) {
		ResultVo resultVo = null;
		try {
			BusiFinance busiFinance = financeService.findOneFinance(id);
			if (busiFinance == null)
				return new ResultVo(false, "该笔订单不存在，请刷新页面再次查看!");
			if (!ParamConstant.FE_REQUEST_STATE_CREATE.equals(busiFinance
					.getFeState()))
				return new ResultVo(false, "该笔订单不是新建状态，无法删除!请刷新页面再次查看!");
			if (ParamConstant.FE_DATA_ORIGIN_APP.equals(busiFinance
					.getFeDataOrigin())
					&& !ParamConstant.FE_PAY_STAUTS_UNPAY.equals(busiFinance
							.getFePayStatus()))
				return new ResultVo(false, "该笔订单付款状态不是未付款，无法删除!");
			LoggingHelper.createLogging(LoggingType.删除, LoggingSource.产品申请,
					"出借编号：" + busiFinance.getFeLendNo() + ",合同编号："
							+ busiFinance.getFeContractNo());
			busiFinance.setFeValidState(ParamConstant.UNVALID); // 修改状态标志位为 已删除
			busiFinance.setFeModifyDate(new Date()); // 删除时间
			Staff staff = AuthorityHelper.getStaff();
			busiFinance.setFeModifyId(staff.getId());// 删除人id
			financeService.updateFinance(busiFinance);
			resultVo = new ResultVo(true, "删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			resultVo = errorResutlVo();
		}
		return resultVo;
	}

	*//**
	 * @Title:划扣允许操作
	 * @Description: TODO
	 * @param id
	 * @return
	 * @throws
	 * @time:2015-1-28 下午02:14:20
	 * @author:Sam.J
	 *//*
	@ResponseBody
	@RequestMapping(value = { "/finance/checkForPayAuditFlag" })
	public ResultVo checkForPayAuditFlag(@RequestParam(value = "id") Long id) {
		ResultVo resultVo = null;
		try {
			BusiFinance busiFinance = financeService.findOneFinance(id);
			if (busiFinance == null)
				return new ResultVo(false, "该笔订单不存在，请刷新页面再次查看!");
			if (ParamConstant.FE_PAYAUDIT_PERMIT.equals(busiFinance
					.getFePayauditFlag()))
				return new ResultVo(false, "该笔订单已进行过允许划扣操作!请刷新页面再次查看!");
			resultVo = financeService.checkForPayAuditFlag(busiFinance);
		} catch (Exception e) {
			e.printStackTrace();
			resultVo = errorResutlVo();
		}
		return resultVo;
	}
	
	----------------------2015.7.8 add-----------------

	*//**
	 * @Title:跳转到理财产品申购页面(为爱特鸿金宝专供模板)
	 * @Description:
	 * @param model
	 * @return
	 * @throws
	 * @time:2015-7-8 下午03:05:50
	 * @author:Sam.J
	 *//*
	@RequestMapping(value = "/finance_apply_forathjb")
	public String toFortuneProductApplyForAthjb(Model model) {
		model.addAttribute("customers", new Customer());
		return "/crm/product/purchase/finance_apply_forathjb";
	}
	
	*//**
	 * @Title:跳转房产项目编辑页面
	 * @Description: TODO
	 * @param id
	 * @param type
	 * @param model
	 * @return
	 * @throws
	 * @time:2014-8-25 下午04:09:51
	 * @author:Sam.J
	 *//*
	@RequestMapping(value = "/edit_forathjb")
	public String toProductEditForAthjb(@RequestParam(value = "id") Long id,
			@RequestParam(value = "type") String type, Model model) {
		// 用于控制审核安全,当一笔业务处于审核阶段,则不能跳转
		if (AuditHelper.isEdit(String.valueOf(productViewService.findOne(id)
				.getState()))) {
			model.addAttribute("productId", id);
			return "/crm/product/purchase/finance_edit_forathjb";
		}
		return getIllegalityAddr();
	}
	
	
	*//** 
	* @Title: findFinanceProductNoPlus 
	* @Description: 查找理财产品(不包含Plus产品)
	* @return List<BusiProduct>(描述入参以及返回参数的含义)
	* @author Sam.J
	* @date 2015-10-30 下午02:25:11 
	* @throws 
	*//*
	@ResponseBody
	@RequestMapping(value = "/find/finance/product_noPlus")
	public List<BusiProduct> findFinanceProductNoPlus(@RequestParam(value = "ifIncludeFX") String ifIncludeFX) {
	    List<BusiProduct> list = busiProductService.findAllFinanceProductNoPlus();
	    if(ifIncludeFX.equals("noFx")){
	    	for(BusiProduct b:list){
	    		if(b.getId().toString().equals(ParamConstant.FORTUNE_PRODUCT_ZHENGDAFUXIANG)){
	    			list.remove(b);
	    			break;
	    		}
	    	}
	    }
	    else if(ifIncludeFX.equals("onlyFx")){
	    	List<BusiProduct> listOnly = new ArrayList<BusiProduct>();
	    	for(BusiProduct b:list){
	    		if(b.getId().toString().equals(ParamConstant.FORTUNE_PRODUCT_ZHENGDAFUXIANG)){
	    			listOnly.add(b);
	    			break;
	    		}
	    	}
	    	return listOnly;
	    }
	    return list;
    }
	
	*//** 
	* @Title: findFinanceProductNoPlus 
	* @Description: 查找理财产品(Plus产品)
	* @return List<BusiProduct>(描述入参以及返回参数的含义)
	* @author Sam.J
	* @date 2015-10-30 下午02:25:11 
	* @throws 
	*//*
	@ResponseBody
	@RequestMapping(value = "/find/finance/product_Plus")
	public List<BusiProduct> findFinanceProductPlus() {
	    return busiProductService.findAllFinanceProductPlus();
    }
	*//**
	 * @Title:划扣取消操作
	 * @Description: TODO
	 * @param id
	 * @return
	 * @throws
	 * @time:2016-1-14 下午02:14:20
	 *//*
	@ResponseBody
	@RequestMapping(value = { "/finance/cancelForPayAuditFlag" })
	public ResultVo cancelForPayAuditFlag(@RequestParam(value = "id") Long id) {
		ResultVo resultVo = null;
		try {
			BusiFinance busiFinance = financeService.findOneFinance(id);
			if (busiFinance == null)
				return new ResultVo(false, "该笔订单不存在，请刷新页面再次查看!");
			if (ParamConstant.FE_PAYAUDIT_CANCEL.equals(busiFinance
					.getFePayauditFlag()))
				return new ResultVo(false, "该笔订单已进行过取消划扣操作!请刷新页面再次查看!");
			resultVo = financeService.cancelForPayAuditFlag(busiFinance);
		} catch (Exception e) {
			e.printStackTrace();
			resultVo = errorResutlVo();
		}
		return resultVo;
	}

	*//**
	 * 新增申请匹配数据
	 * @param ids
	 * @return
     *//*
	@ResponseBody
	@RequestMapping(value={"/addApplyMatch"})
	public ResultVo addApplyMatch(@RequestParam(value = "ids") List<Long> ids){
		Staff staff = AuthorityHelper.getStaff();//获取当前登录用户
		Long operatorId = null;
		if(staff!=null){
			operatorId = staff.getId();
		}
		ResultVo resultVo = null;
		try{
			for(int i=0;i<ids.size();i++){
				Long id = ids.get(i);
				BusiFinance busiFinance = financeService.findOneFinance(id);
				if(busiFinance != null ){
					busiFinance.setFeApplyMatchStatus(ParamConstant.APPLY_MATCH_STATUS_WAITING);
					busiFinance.setFeApplyMatchOperator(operatorId);//申请匹配操作人
					busiFinance.setFeApplyMatchDate(DateUtil.parse(new Date(), "yyyy-MM-dd"));//申请匹配提交日期
					busiFinance.setFeModifyDate(new Date());
					financeService.updateFinance(busiFinance);

					//String res = productViewService.batchUpdateApplyMatchById(id, ParamConstant.APPLY_MATCH_STATUS_WAITING,depName);
				}
			}
			resultVo = new ResultVo(true,"提交成功");
		} catch(Exception e) {
			e.printStackTrace();
			resultVo = errorResutlVo();
		}
		return resultVo;
	}

*/}
