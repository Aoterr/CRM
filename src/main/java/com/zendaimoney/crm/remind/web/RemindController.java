package com.zendaimoney.crm.remind.web;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.orm.PageVo;

import com.zendaimoney.constant.DesktopConstant;
import com.zendaimoney.crm.CrudUiController;
import com.zendaimoney.crm.customer.vo.CustomerBirthdayRemind;
import com.zendaimoney.crm.remind.entity.Remind;
import com.zendaimoney.crm.remind.service.RemindCustomerService;
import com.zendaimoney.crm.remind.service.RemindService;
import com.zendaimoney.utils.StringToDateConverter;


/**
 * 
 * @author bianxj
 */
@Controller
@RequestMapping(value = "/crm/remind")
public class RemindController extends CrudUiController<Remind> {

	@Autowired
	private RemindService remindService;

	@RequestMapping(value = { "remind" })
	public String remind(Model model) {
		List<Remind> remindlist = remindService.getRemindByUserId(
				DesktopConstant.CUS_BIRTHDAY_REMIND,
				DesktopConstant.RIGHT_OVERDUE_REMIND);
		model.addAttribute("remindlist", remindlist);
		return "crm/remind/remind";
	}

	@RequestMapping(value = { "remindDetail" })
	public String remindDetail(Model model,
			@RequestParam(value = "remindType") String remindType) {
		model.addAttribute("remindType", remindType);

		return "/crm/remind/remindDetail";
	}

	@RequestMapping(value = { "remindDetailList" })
	@ResponseBody
	public PageVo<CustomerBirthdayRemind> remindDetailList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "auto") String sort,
			@RequestParam(value = "order", defaultValue = "asc") String order,
			Model model, ServletRequest request,
			@RequestParam(value = "remindType") String remindType,
			@RequestParam(value = "staffId") String staffId) {

		return null;
	}

	@RequestMapping(value = { "" })
	public String index(Model model) {
		return "crm/desktop/index";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		/*
		 * 注册自定义的属性编辑器 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		 * CustomDateEditor dateEditor = new CustomDateEditor(df, true);
		 * 表示如果命令对象有Date类型的属性，将使用该属性编辑器进行类型转换
		 */
		binder.registerCustomEditor(Date.class, new StringToDateConverter());
	}

	@RequestMapping(value = { "query" })
	public String toQueryPage() {
		return "/crm/remind/customer_query";
	}

}
