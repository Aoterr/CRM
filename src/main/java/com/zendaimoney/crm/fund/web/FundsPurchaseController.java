package com.zendaimoney.crm.fund.web;

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
import com.zendaimoney.crm.fund.entity.BusiFundInfo;
import com.zendaimoney.crm.fund.entity.BusiFunds;
import com.zendaimoney.crm.fund.entity.FundsProductView;
import com.zendaimoney.crm.fund.service.BusiFundInfoService;
import com.zendaimoney.crm.fund.service.BusiFundsService;
import com.zendaimoney.crm.fund.service.FundsProductViewService;
import com.zendaimoney.crm.product.service.RecordStaffService;
import com.zendaimoney.uc.rmi.vo.DataPermission;
import com.zendaimoney.uc.rmi.vo.Staff;
import com.zendaimoney.utils.mapper.BeanMapper;

/**
 * 基金产品申购控制器
 * 
 * @author CJ
 * @create 2015-11-24, 下午03:18:16
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/fund/purchase")
public class FundsPurchaseController extends CrudUiController<FundsProductView> {/*

	@Autowired
	private BusiFundInfoService financeService;

	@Autowired
	private BusiFundsService busiFundsService;

	@Autowired
	private BankaccountService bankaccountService;

	@Autowired
	private FundsProductViewService productViewService;

	@Autowired
	private RecordStaffService recordStaffService;

	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = { "" })
	public String index(Model model, ServletRequest request) {
		return "/crm/fund/list";
	}

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
		// //只展示数据来源是 线下的业务单子
		SearchFilter searchDataValid = new SearchFilter("fdValidState",
				Operator.EQ, ParamConstant.VALID); // 只展示数据状态为1 即没删除作废的单子
		filtersMap.put("fdValidState", searchDataValid);
		Page<FundsProductView> productViewPage = productViewService
				.getAllProductView(
						buildSpecification(request, filtersMap,
								Share.PRODUCT_REQUEST, "manager"),
						buildPageRequest(page, rows, sort, order));
		return new PageVo(productViewPage);
	}


	// 跳转到理财产品申购页面
	@RequestMapping(value = "/fund_apply")
	public String toFortuneProductApply(Model model) {
		List<Parameter> deductCompanyList = SystemParameterHelper
				.findParameterByPrType("deductCompany");
		model.addAttribute("deductCompanyList", deductCompanyList);
		model.addAttribute("customers", new Customer());
		return "/crm/fund/fund_apply";
	}

	

	// 理财产品申请
	@ResponseBody
	@RequestMapping(value = "/fund/apply")
	public ResultVo saveFinanceApply(BusiFundInfo busiFinance,
			@RequestParam(value = "state") String state,
			@RequestParam(value = "returnAccount") String returnAccount) {
		ResultVo resultVo = null;
		
		// 增加校验   防止重复提交    
		List<Long> idList = new ArrayList<Long>();		
		if(busiFinance.getFdPreviousId()!=null){
			idList.add(busiFinance.getFdPreviousId());
			boolean flag=financeService.findByPreId(idList);
			if(!flag){
				return new ResultVo(false, "该产品已进行过到期续投操作");
			}
		}
			
		try {
			busiFinance.setFdPaymentWay(busiFinance.getFdPaymentWay().substring(0, 1));//分隔
			if (returnAccount != null && !"".equals(returnAccount)
					&& !"undefined".equals(returnAccount))
				busiFinance.setFdReturnAccount(Long.parseLong(returnAccount));
			resultVo = financeService.saveBusiFinance(busiFinance, state);
			// 增加报表记录 
			if (resultVo.result.containsKey("id")) // 加上是否为空判断，如为空不执行避免报错 
				AddRecordStaff(busiFinance.getFdManager(), state,resultVo.result.get("id").toString());
		} catch (Exception e) {
			e.printStackTrace();
			resultVo = errorResutlVo();
		}
		return resultVo;
	}

	// 理财产品更新
	@ResponseBody
	@RequestMapping(value = "/finance/update")
	public ResultVo updateFinanceApply(BusiFundInfo busiFinance,
			@RequestParam(value = "state") String state,
			@RequestParam(value = "returnAccount") String returnAccount) {
		ResultVo resultVo = null;
		try {
			busiFinance.setFdPaymentWay(busiFinance.getFdPaymentWay().substring(0, 1));
			// 为了南京房产项目，做改造 14.08.25
			if (returnAccount != null && !"".equals(returnAccount)
					&& !"undefined".equals(returnAccount))
				busiFinance.setFdReturnAccount(Long.parseLong(returnAccount));
			resultVo = financeService.updateBusiFinance(busiFinance, state);
			// 增加报表记录 
			if (resultVo.result.containsKey("id")&&state.equals(AuditHelper.getStateValue(ParamConstant.REQUEST_STATE_DAIZHIJIAN)))
				// 加上是否为空判断，如为空不执行避免报错 by
				//修改状态记录时的BUG 回退状态下修改 后只有待质检状态才记录   
				AddRecordStaff(busiFinance.getFdManager(), state,
						resultVo.result.get("id").toString());
		} catch (Exception e) {
			e.printStackTrace();
			resultVo = errorResutlVo();
		}
		return resultVo;
	}

	// 理财审核  质检
	@ResponseBody
	@RequestMapping(value = "/finance/audit")
	public ResultVo financeAudti(@RequestParam(value = "id") Long id,
			@RequestParam(value = "auditResult") String auditResult,
			@RequestParam(value = "content") String content) {
		ResultVo resultVo = null;
		try {
			resultVo = financeService.auditFinance(id, content, auditResult);
			// 增加报表记录
			// 将质检记录保存入库，用于查询质检人员
			 if(!"4".equals(auditResult)){
			long feManager = Long.parseLong(resultVo.result.get("manager")
					.toString());
			if (resultVo.result.containsKey("id")) // 加上是否为空判断，如为空不执行避免报错 by
				AddRecordStaff(feManager, auditResult, resultVo.result
						.get("id").toString());
			 }
		} catch (Exception e) {
			e.printStackTrace();
			resultVo = errorResutlVo();
		}
		return resultVo;
	}

	
	// 查找理财产品
	@ResponseBody
	@RequestMapping(value = "/find/finance/product")
	public List<BusiFunds> findFinanceProduct() {
	    return busiFundsService.findAllFinanceProduct();
    }
	

	// 查找理财产品(传入查询类型参数，依照参数查询结果)
	@ResponseBody
	@RequestMapping(value = "/find/finance/product_byType")
	public List<BusiFunds> findFinanceProductByType(
			@RequestParam(value = "type") String type) {
		List<String> types = new ArrayList<String>();
		String[] typesplit = type.split(",");
		for (String typeone : typesplit) {
			types.add(typeone);
		}
		return busiFundsService.findProductByTypes(types);
	}

	// 查找所有有效产品
	@ResponseBody
	@RequestMapping(value = "/find/all/product")
	public List<BusiFunds> findAllProductValid() {
	return busiFundsService.findAllBusiFundsValid();//查找有效且可以购买未删除的产品
	//	return busiFundsService.findAllBusiFundsValidBetweenTime();//查找有效且可以购买未删除在有效期内的产品
	}
	
	// 查找所有产品
		@ResponseBody
		@RequestMapping(value = "/find/all/productNew")
		public List<BusiFunds> findAllProduct() {
			return busiFundsService.findAllBusiFunds();
		}
		
	// 跳转到产品明细页面
	@RequestMapping(value = "/show")
	public String toProductShow(@RequestParam(value = "id") Long id,
			@RequestParam(value = "type") String type, Model model) {
			model.addAttribute("productId", id);
	
			List<Parameter> deductCompanyList = SystemParameterHelper
					.findParameterByPrType("deductCompany");
			model.addAttribute("deductCompanyList", deductCompanyList);
		//	if (financeService.findOneFinance(id).getFdPreviousId() != null) {
		//		return "/crm/fund/continue_invest_show";//续投
		//	} else {
				return "/crm/fund/fund_show";//新增
		//}
	}

	// 跳转到产品编辑页面
	@RequestMapping(value = "/edit")
	public String toProductEdit(@RequestParam(value = "id") Long id,
			@RequestParam(value = "type") String type, Model model) {
		// 用于控制审核安全,当一笔业务处于审核阶段,则不能跳转
		if (AuditHelper.isEdit(String.valueOf(productViewService.findOne(id)
				.getState()))) {
			model.addAttribute("productId", id);
			
				List<Parameter> deductCompanyList = SystemParameterHelper
						.findParameterByPrType("deductCompany");
				model.addAttribute("deductCompanyList", deductCompanyList);
		//		if (financeService.findOneFinance(id).getFdPreviousId() != null) {
		//			return "/crm/fund/continue_invest_edit";
		//		} else {
					return "/crm/fund/fund_edit";
		//		}
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
			
				List<Parameter> deductCompanyList = SystemParameterHelper
						.findParameterByPrType("deductCompany");
				model.addAttribute("deductCompanyList", deductCompanyList);
		//		if (financeService.findOneFinance(id).getFdPreviousId() != null) {
		//			return "/crm/fund/continue_invest_preliminary_review";
		//		} else {
					return "/crm/fund/fund_preliminary_review";
		//		}
		}
		return getIllegalityAddr();
	}


	// 根据id查找理财业务
	@ResponseBody
	@RequestMapping(value = "/get/finance/product")
	public BusiFundInfo findOneFinance(Long id) {
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
	public BusiFunds getProduct(@RequestParam(value = "id") Long id) {
		return busiFundsService.findOneProduct(id);
	}

	// 跳转到查询页面
	@RequestMapping(value = { "query" })
	public String complaint_query(Model model) {
		return "/crm/fund/purchase_query";
	}


	public ResultVo errorResutlVo() {
		return new ResultVo(false, "操作失败,请重新尝试或联系管理员!");
	}

	// 删除理财申请   物理删除
	@ResponseBody
	@RequestMapping(value = { "/finance/del" })
	public ResultVo del(@RequestParam(value = "id") Long id) {
		ResultVo resultVo = null;
		try {
			BusiFundInfo busiFinance = financeService.findOneFinance(id);
			LoggingHelper.createLogging(LoggingType.删除, LoggingSource.基金申请,
					"订单编号：" + busiFinance.getFdLendNo() + ",合同编号："
							+ busiFinance.getFdContractNo());
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

	
//查找客户经理
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
	 * @Title:删除理财订单
	 * @Description: 新方法，修改原来的物理删除为逻辑删除
	 * @param id
	 * @return
	 * @throws
	 * @time:2015-12-7 下午04:01:14
	 * @author:CJ
	 *//*
	@ResponseBody
	@RequestMapping(value = { "/finance/delBusi" })
	public ResultVo delBusi(@RequestParam(value = "id") Long id) {
		ResultVo resultVo = null;
		try {
			BusiFundInfo busiFinance = financeService.findOneFinance(id);
			if (busiFinance == null)
				return new ResultVo(false, "该笔订单不存在，请刷新页面再次查看!");
			if (!ParamConstant.FE_REQUEST_STATE_CREATE.equals(busiFinance
					.getFdState()))
				return new ResultVo(false, "该笔订单不是新建状态，无法删除!请刷新页面再次查看!");
			if (ParamConstant.FE_DATA_ORIGIN_APP.equals(busiFinance
					.getFdDataOrigin())
					&& !ParamConstant.FE_PAY_STAUTS_UNPAY.equals(busiFinance
							.getFdPayStatus()))
				return new ResultVo(false, "该笔订单付款状态不是未付款，无法删除!");
			LoggingHelper.createLogging(LoggingType.删除, LoggingSource.基金申请,
					"订单编号：" + busiFinance.getFdLendNo() + ",合同编号："
							+ busiFinance.getFdContractNo());
			busiFinance.setFdValidState(ParamConstant.UNVALID); // 修改状态标志位为 已删除
			busiFinance.setFdModifyDate(new Date()); // 删除时间
			Staff staff = AuthorityHelper.getStaff();
			busiFinance.setFdModifyId(staff.getId());// 删除人id
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
	 * @time:2015-12-2 下午02:14:20
	 * @author:CJ
	 *//*
	@ResponseBody
	@RequestMapping(value = { "/finance/checkForPayAuditFlag" })
	public ResultVo checkForPayAuditFlag(@RequestParam(value = "id") Long id) {
		ResultVo resultVo = null;
		try {
			BusiFundInfo busiFinance = financeService.findOneFinance(id);
			if (busiFinance == null)
				return new ResultVo(false, "该笔订单不存在，请刷新页面再次查看!");
			if (ParamConstant.FE_PAYAUDIT_PERMIT.equals(busiFinance.getFdPayauditFlag()))  //1
				return new ResultVo(false, "该笔订单已进行过允许划扣操作!请刷新页面再次查看!");
			resultVo = financeService.checkForPayAuditFlag(busiFinance);
		} catch (Exception e) {
			e.printStackTrace();
			resultVo = errorResutlVo();
		}
		return resultVo;
	}
	
	*//**
	 * @Title:付款允许操作
	 * @Description: TODO
	 * @param id
	 * @return
	 * @throws
	 * @time:2015-12-2 下午02:14:20
	 * @author:CJ
	 *//*
	@ResponseBody
	@RequestMapping(value = { "/finance/checkForPayFlag" })
	public ResultVo checkForPayFlag(@RequestParam(value = "id") Long id) {
		ResultVo resultVo = null;
		try {
			BusiFundInfo busiFinance = financeService.findOneFinance(id);
			if (busiFinance == null)
				return new ResultVo(false, "该笔订单不存在，请刷新页面再次查看!");
			if (ParamConstant.FD_PAY_STAUTS_PAYDONE.equals(busiFinance.getFdPayauditFlag()))  //1 已付款 支付状态
				return new ResultVo(false, "该笔订单已进行过允许付款操作!请刷新页面再次查看!");
			resultVo = financeService.checkForPayFlag(busiFinance);
		} catch (Exception e) {
			e.printStackTrace();
			resultVo = errorResutlVo();
		}
		return resultVo;
	}
	
	*//**
	 * 根据合同编号,客户编号和理财产品查询理财信息(非续投)
	 * 
	 * @author CJ
	 * @date 2015-7-14下午02:12:13
	 * @param feContractNo
	 * @param feProduct
	 * @param customerid
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/find/feContractNoAndFeProductBycustomerId")
	public ResultVo findByFeContractNoAndFeProductByCustomer(
			@RequestParam(value = "fdContractNo") String fdContractNo,
			@RequestParam(value = "fdProduct") String fdProduct,
			@RequestParam(value = "fdLendNo") String fdLendNo,
			@RequestParam(value = "customerid") String customerid) {		
			return financeService.findByFeContractNoAndFeProduct(fdContractNo,
					fdProduct, fdLendNo,customerid);
		
		
	}
*/}

