package com.zendaimoney.sys.desktop.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zendaimoney.constant.DesktopConstant;
import com.zendaimoney.crm.CrudUiController;
import com.zendaimoney.sys.desktop.entity.DesktopToolbar;
import com.zendaimoney.sys.desktop.service.DesktopService;
import com.zendaimoney.sys.logging.service.LoggingService;
import com.zendaimoney.utils.StringToDateConverter;


/**
 * 
 * @author bianxj
 */
@Controller
@RequestMapping(value = "/sys/desktop")
public class DesktopController extends CrudUiController<DesktopToolbar> {

	@Autowired
	private LoggingService loggingService;

	@Autowired
	private DesktopService desktopService;

	@RequestMapping(value = { "" })
	public String index(Model model) {
		return "sys/desktop/index";
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

	/*@ResponseBody
	@RequestMapping(value = "/businessReport")
	public List<String> businessReport() {
		List<String> resultlList = new ArrayList<String>();
		String reportData = desktopService.getIndexBusinessReportData();
		if (null == reportData || "".equals(reportData)) {
			return resultlList;
		}
		String dateStrList = desktopService.getDateStrList();
		StringBuffer resultXMLStr = new StringBuffer();
		resultXMLStr
				.append("<?xml version='1.0'?> <chart caption='业务进件统计' xAxisName='月份' yAxisName='进件数(单位:件)'")
				.append(" showValues='0' numberSuffix='' borderThickness='0' anchorRadius='5' baseFontSize='12' ")
				.append("chartLeftMargin='15' chartRightMargin='30' labelDisplay='Rotate' slantLabels='1' formatNumberScale='0'> ")
				.append(dateStrList)
				.append(reportData).append("</chart>");
		resultlList.add(resultXMLStr.toString());
		return resultlList;
	}*/

}
