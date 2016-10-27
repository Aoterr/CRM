package com.zendaimoney.crm.complaint.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

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

import com.google.common.collect.Lists;
import com.zendaimoney.constant.LoggingSource;
import com.zendaimoney.constant.LoggingType;
import com.zendaimoney.crm.CrudUiController;
import com.zendaimoney.crm.complaint.entity.Complaint;
import com.zendaimoney.crm.complaint.service.ComplaintService;
import com.zendaimoney.crm.customer.entity.Customer;
import com.zendaimoney.crm.customer.service.CustomerService;
import com.zendaimoney.sys.parameter.service.ParameterService;

@Controller
@RequestMapping(value = "/crm/complaint")
public class ComplaintController extends CrudUiController<Complaint> {/*
	@Autowired
	private ComplaintService complaintService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ParameterService parameterService;

	@RequestMapping(value = { "" })
	public String index(Model model) {
		return "crm/complaint/list";
	}

	// list页面
	@RequestMapping("list")
	@ResponseBody
	public PageVo list(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "auto") String sort,
			@RequestParam(value = "order", defaultValue = "asc") String order,
			Model model, ServletRequest request) {
		Page<Complaint> complaint = null;
		if (AuthorityHelper.hasAuthorized(ParamConstant.ServicesAdd)) {// 新建
			complaint = complaintService.getComplaint(
					buildSpecification(request, null, Share.COMPLAINT,
							"ctInputId"),
					buildPageRequest(page, rows, sort, order));
		}
		if (AuthorityHelper.hasAuthorized(ParamConstant.ComplaintAudit)) {// 审核
			complaint = complaintService.getComplaint(
					buildSpecification(request, null, null, null),
					buildPageRequest(page, rows, sort, order));
		}
		if (AuthorityHelper.hasAuthorized(ParamConstant.ComplaintHandling)) {// 处理
			Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
			List<Long> list = UcHelper.getAllStaffDepart();
			SearchFilter searchFilter = new SearchFilter("ctDepartment",
					Operator.IN, list.toArray());
			filters.put("ctProcessState", searchFilter);
			complaint = complaintService.getComplaint(
					buildSpecification(request, filters, null, null),
					buildPageRequest(page, rows, sort, order));
		}

		for (Complaint complaint2 : complaint) {
			Customer customer = new Customer();
			if (complaint2.getCustomer() != null) {
				Customer customerOld = complaint2.getCustomer();
				BeanUtils.copyProperties(customerOld, customer);
				complaint2.setCustomer(customer);
			}
		}
		return new PageVo(complaint);
	}

	@RequestMapping(value = { "query" })
	public String complaint_query(Model model) {
		return "crm/complaint/complaint_query";
	}

	// 新建页面
	@RequestMapping(value = { "create" })
	public String create(Model model) {
		return "crm/complaint/create";
	}

	// 显示页面
	@RequestMapping(value = { "show" })
	public String show(@RequestParam(value = "id") Long id, Model model) {
		Complaint complaints = complaintService.getComplaint(id);
		model.addAttribute("complaints", complaints);
		return "crm/complaint/show";
	}

	// 编辑页面
	@RequestMapping(value = { "edit" })
	public String edit(@RequestParam(value = "id") Long id, Model model) {
		Complaint complaints = complaintService.getComplaint(id);
		model.addAttribute("complaints", complaints);
		return "crm/complaint/edit";
	}

	// 处理页面
	@RequestMapping(value = { "edit_chuli" })
	public String edit_chuli(@RequestParam(value = "id") Long id, Model model) {
		Complaint complaints = complaintService.getComplaint(id);
		model.addAttribute("complaints", complaints);
		return "crm/complaint/edit_chuli";
	}

	// 审核页面
	@RequestMapping(value = { "shenhe" })
	public String shenhe(@RequestParam(value = "id") Long id, Model model) {
		Complaint complaints = complaintService.getComplaint(id);
		model.addAttribute("complaints", complaints);
		return "crm/complaint/shenhe";
	}

	// 获取单个数据
	@RequestMapping("complaintById")
	@ResponseBody
	public Complaint getComplaintById(Long id) {
		Complaint i = complaintService.getComplaint(id);
		return i;
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor dateEditor = new CustomDateEditor(fmt, true);
		binder.registerCustomEditor(Date.class, dateEditor);
	}

	// 保存
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo save(@RequestParam(value = "customerid") Long customerid,
			Complaint complaint, HttpServletRequest request)
			throws HibernateOptimisticLockingFailureException, Exception {
		try {

			String result = "";
			Complaint newComplaint = new Complaint();
			Customer customer = customerService.getCustomer(customerid);
			complaint.setCustomer(customer);

			if (complaint.getId() != null) {
				Complaint entity = complaintService.getComplaint(complaint
						.getId());
				Complaint oldComplaint = new Complaint();
				BeanUtils.copyProperties(entity, oldComplaint);// 老数据

				List<String> needList = Lists.newArrayList("customer",
						"ctComplaintTime", "ctComplaintContent",
						"ctComplaintType", "ctUrgency", "ctContactNum",
						"ctDepartment", "ctTimeLimit", "ctContactMethods");

				newComplaint = complaintService.saveNComplaint(complaint);

				result = "客户投诉 Id："
						+ entity.getId()
						+ "；"
						+ LoggingHelper.loggingEquals(oldComplaint,
								newComplaint, needList);
				LoggingHelper.createLogging(LoggingType.修改, LoggingSource.服务管理,
						result);
			} else {
				newComplaint = complaintService.saveNComplaint(complaint);
				LoggingHelper.createLogging(LoggingType.新增, LoggingSource.服务管理,
						"新建客户投诉Id:" + newComplaint.getId() + "成功");
			}

			return new ResultVo(true);
		} catch (HibernateOptimisticLockingFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultVo(false, ParamConstant.ERRORA);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultVo(false);
		}

	}

	// 审核
	@RequestMapping(value = "audit", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo audit(Complaint complaint, HttpServletRequest request)
			throws HibernateOptimisticLockingFailureException, Exception {
		try {
			// complaintService.saveaudit(complaint);
			String result = "";
			Complaint newComplaint = new Complaint();

			Complaint entity = complaintService.getComplaint(complaint.getId());
			Complaint oldComplaint = new Complaint();
			BeanUtils.copyProperties(entity, oldComplaint);// 老数据

			List<String> needList = Lists.newArrayList("ctAuditTime",
					"ctAuditResult", "ctAuditMemo", "ctProcessState");

			newComplaint = complaintService.saveaudit(complaint);

			result = "客户投诉 Id："
					+ entity.getId()
					+ "；"
					+ LoggingHelper.loggingEquals(oldComplaint, newComplaint,
							needList);
			LoggingHelper.createLogging(LoggingType.审核, LoggingSource.服务管理,
					result);
			return new ResultVo(true);
		} catch (HibernateOptimisticLockingFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultVo(false, ParamConstant.ERRORA);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultVo(false);
		}
	}

	// 保存回复
	@RequestMapping(value = "savehuifu", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo savehuifu(Complaint complaint, HttpServletRequest request)
			throws HibernateOptimisticLockingFailureException, Exception {
		try {
			// complaintService.savehuifu(complaint);
			String result = "";
			Complaint newComplaint = new Complaint();

			Complaint entity = complaintService.getComplaint(complaint.getId());
			Complaint oldComplaint = new Complaint();
			BeanUtils.copyProperties(entity, oldComplaint);// 老数据

			List<String> needList = Lists.newArrayList("ctProcessTime",
					"ctProcessResult", "ctProcessMemo",
					"ctCustomerSatisfaction", "ctProcessState");

			newComplaint = complaintService.savehuifu(complaint);

			result = "客户投诉 Id："
					+ entity.getId()
					+ "；"
					+ LoggingHelper.loggingEquals(oldComplaint, newComplaint,
							needList);
			LoggingHelper.createLogging(LoggingType.处理, LoggingSource.服务管理,
					result);
			return new ResultVo(true);
		} catch (HibernateOptimisticLockingFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultVo(false, ParamConstant.ERRORA);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultVo(false);
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo delete(@RequestParam(value = "id") Long[] id,
			HttpServletRequest request) {
		try {
			complaintService.deleteComplaint(id);
			String result = "";
			for (Long long1 : id) {
				result += "'" + long1 + "'";
			}
			LoggingHelper.createLogging(LoggingType.删除, LoggingSource.服务管理,
					"删除客户投诉Id:" + result + "成功");
			return new ResultVo(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultVo(false);
		}
	}
*/}
