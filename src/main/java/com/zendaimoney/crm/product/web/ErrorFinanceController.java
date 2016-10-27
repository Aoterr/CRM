package com.zendaimoney.crm.product.web;

import java.util.Date;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.orm.PageVo;

import com.zendaimoney.constant.Share;
import com.zendaimoney.crm.CrudUiController;
import com.zendaimoney.crm.customer.entity.RecordStaff;
import com.zendaimoney.crm.product.entity.BusiFinance;
import com.zendaimoney.crm.product.service.BusiFinanceService;
import com.zendaimoney.crm.product.service.RecordStaffService;
import com.zendaimoney.uc.rmi.vo.Staff;
import com.zendaimoney.utils.mapper.BeanMapper;

/**
 * 项目名称：crm-webapp
 * 
 * @ClassName: ErrorFinanceController
 * @Description: 异常订单处理类
 * @author Sam.J
 * @date 2015-5-18 下午02:05:40
 */

@Controller
@RequestMapping(value = "/product/errfindeal")
public class ErrorFinanceController extends CrudUiController<BusiFinance> {/*

	@Autowired
	private BusiFinanceService financeService;

	@Autowired
	private RecordStaffService recordStaffService;

	@RequestMapping(value = { "" })
	public String index(Model model) {
		return "/crm/product/errfindeal/list";
	}

	// list页面
	@ResponseBody
	@RequestMapping(value = "/list")
	public PageVo list(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "auto") String sort,
			@RequestParam(value = "order", defaultValue = "asc") String order,
			Model model, ServletRequest request) {
		Page<BusiFinance> errFinPage = financeService
				.findAllErrorFinance(
						builderCondition(request, null, Share.PRODUCT_REQUEST,
								"feManager"),
						buildPageRequest(page, rows, sort, order));
		return new PageVo<BusiFinance>(errFinPage);
	}

	*//**
	 * @Title: send
	 * @Description: 重发fortune质检通过
	 * @return ResultVo 返回类型
	 * @author Sam.J
	 * @date 2015-5-21 上午10:24:05
	 * @throws
	 *//*
	@ResponseBody
	@RequestMapping(value = { "/sendFortune" })
	public ResultVo send(@RequestParam(value = "id") Long id) {
		ResultVo resultVo = null;
		try {
			resultVo =financeService.sendFinanceToFortune(id, ParamConstant.REQUEST_STATE_CHENGGONG);
		} catch (Exception e) {
			e.printStackTrace();
			resultVo = errorResutlVo();
		}
		return resultVo;
	}

	*//**
	 * @Title: refund
	 * @Description: 退款接口
	 * @return ResultVo 返回类型
	 * @author Sam.J
	 * @date 2015-5-21 下午01:48:45
	 * @throws
	 *//*
	@ResponseBody
	@RequestMapping(value = { "/refund" })
	public ResultVo refund(@RequestParam(value = "id") Long id) {
		ResultVo resultVo = null;
		try {
			resultVo = financeService.refundApp(id);
		} catch (Exception e) {
			e.printStackTrace();
			resultVo = errorResutlVo();
		}
		return resultVo;
	}

	*//**
	 * @Title: errorResutlVo
	 * @Description: 系统出错返回方法
	 * @return ResultVo 返回类型
	 * @author Sam.J
	 * @date 2015-5-21 上午10:36:05
	 * @throws
	 *//*
	public ResultVo errorResutlVo() {
		return new ResultVo(false, "操作失败,请重新尝试或联系管理员!");
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
*/}
