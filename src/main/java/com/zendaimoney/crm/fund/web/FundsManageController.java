package com.zendaimoney.crm.fund.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
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

import com.google.common.collect.Lists;
import com.zendaimoney.constant.LoggingSource;
import com.zendaimoney.constant.LoggingType;
import com.zendaimoney.constant.ParamConstant;
import com.zendaimoney.constant.Share;
import com.zendaimoney.crm.CrudUiController;
import com.zendaimoney.crm.customer.service.CustomerService;
import com.zendaimoney.crm.fund.entity.BusiFunds;
import com.zendaimoney.crm.fund.repository.BusiFundsDao;
import com.zendaimoney.crm.fund.service.BusiFundsService;
import com.zendaimoney.crm.product.service.RecordStaffService;
import com.zendaimoney.crm.sysuser.entity.SysUser;
import com.zendaimoney.utils.LoggingHelper;



/**
 * 基金产品管理控制器
 * 
 * @author CJ
 * @create 2015-11-24, 下午03:18:16
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/fund/manage")
public class FundsManageController extends CrudUiController<BusiFunds> {

	@Autowired
	private BusiFundsService busiFundsService;
	
	@Autowired
	private BusiFundsDao busiFundsDao;

	@Autowired
	private RecordStaffService recordStaffService;

	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = { "" })
	public String index(Model model) {
		return "/crm/fund/funds_product_list";//基金产品列表页面
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
	//	Page<BusiFunds> productViewPage = busiFundsService.getAllProductValid(
		Map<String, SearchFilter> filtersMap = new HashMap<String, SearchFilter>();
		SearchFilter searchDataValid = new SearchFilter("fdIsDel",
				Operator.EQ, ParamConstant.VALID); // 只展示数据状态为1 即没删除作废的单子
		filtersMap.put("fdIsDel", searchDataValid);
		Page<BusiFunds> productViewPage = busiFundsService.getAllProduct(
						buildSpecification(request, filtersMap,
								Share.PRODUCT_REQUEST, null),
						buildPageRequest(page, rows, sort, order));
		return new PageVo(productViewPage);
	}
	//跳到创建页面
	@RequestMapping(value = { "create" })
	public String create(Model model) {
		return "crm/fund/funds_product_create";
	}
	//跳到编辑页面
	@RequestMapping(value = { "edit" })
	public String edit(@RequestParam(value = "id") Long id, Model model) {
		model.addAttribute("fundId", id);
		return "crm/fund/funds_product_edit";
	}

		
/**"
 * 保存基金产品
 * @param busiFunds
 * @param request
 * @return
 * @throws HibernateOptimisticLockingFailureException
 * @throws Exception
 */
	@RequestMapping(value = "/fund_save",method = RequestMethod.POST)
	@ResponseBody
	public ResultVo save(BusiFunds busiFunds,HttpServletRequest request,HttpSession httpSession) throws HibernateOptimisticLockingFailureException,Exception{
		SysUser sysuser=(SysUser)httpSession.getAttribute(ParamConstant.SYS_USER);
		try {
			String result="";
			BusiFunds newbusiFunds = new BusiFunds();
			if(busiFunds.getId()!=null){	//修改更新			
				BusiFunds entity = busiFundsService.findOneProduct(busiFunds.getId());//根据id查询基金产品
				BusiFunds oldBusiFunds = new BusiFunds();
				BeanUtils.copyProperties(entity,oldBusiFunds);
				List<String> needList = Lists.newArrayList("fdFundCode","fdFundName","fdFundType","fdTerm","fdInputDate","fdMinAmountFirst","fdValid","fdMinShareCallback","fdMinAmountFix");
				newbusiFunds = busiFundsService.saveFunds(busiFunds);
				
				result = "基金产品  Id："+entity.getId()+"；"+LoggingHelper.loggingEquals(oldBusiFunds, newbusiFunds,needList);
				LoggingHelper.createLogging(LoggingType.修改, LoggingSource.系统,result,sysuser);
			}else{//新增保存
				newbusiFunds = busiFundsService.saveFunds(busiFunds);
				LoggingHelper.createLogging(LoggingType.新增, LoggingSource.系统, "基金产品Id:"+newbusiFunds.getId(),sysuser);
			}
			return new ResultVo(true,"保存成功！");
		} catch (HibernateOptimisticLockingFailureException e) {
			e.printStackTrace();
			return new ResultVo(false,"  此数据已有新内容，请刷新后编辑！");
		}catch (Exception e) {
			e.printStackTrace();
			return new ResultVo(false,"保存失败！");
		}
	}
		
		//查找理财产品
		@RequestMapping("fundsById")
		@ResponseBody
		public BusiFunds getFundsProductById(Long id) {
			return busiFundsService.findOneProduct(id);
		}
		
		//时间格式转换
		@InitBinder
		protected void initBinder(HttpServletRequest request,
				ServletRequestDataBinder binder) throws Exception {
			DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			CustomDateEditor dateEditor = new CustomDateEditor(fmt, true);
			binder.registerCustomEditor(Date.class, dateEditor);
		}
}

