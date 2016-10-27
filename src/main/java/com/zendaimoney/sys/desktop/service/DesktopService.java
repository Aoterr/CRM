package com.zendaimoney.sys.desktop.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.constant.DesktopConstant;
import com.zendaimoney.crm.BaseService;
import com.zendaimoney.sys.desktop.entity.DesktopToolbar;
import com.zendaimoney.sys.desktop.repository.DesktopDao;
import com.zendaimoney.uc.rmi.vo.DataPermission;
import com.zendaimoney.uc.rmi.vo.Function;
import com.zendaimoney.uc.rmi.vo.Staff;
import com.zendaimoney.utils.DateUtil;

@Component
@Transactional(readOnly = true)
public class DesktopService extends BaseService<DesktopToolbar>{
	@Autowired
	private DesktopDao desktopDao;
 
	 
	public List<DesktopToolbar> getDesktopToolbarByType(Integer display,String type){
		return desktopDao.getDesktopToolbarByType(display, type);
	}	

	/**
	 * 获取用户功能权限代码列表
	 *  
	 * @author Yuan Changchun
	 * @date 2013-10-25 下午03:36:22
	 * @param functionsList
	 * @param resultlList
	 * @return
	 */
	private List<String> getAllFunctionCode(List<Function> functionsList, List<String> resultlList) {
		if (functionsList == null || functionsList.size() == 0) {
			return resultlList;
		}
		for (Function function : functionsList) {
			if (function != null && function.getFunctionCode() != null) {
				resultlList.add(function.getFunctionCode());
				if (function.getChildren().size() != 0) {
					getAllFunctionCode(new ArrayList<Function>(function.getChildren()), resultlList);
				}
			}
		}
		return resultlList;
	}
	
	/**
	 * 获取FunsionCharts中xml数据label标签的值列表
	 *  
	 * @author Yuan Changchun
	 * @date 2013-10-25 下午04:22:09
	 * @param dateStr
	 * @return
	 */
	public String getDateStrList() {
		List<Date> dateList = DateUtil.getRecentNumMonDateList(12);
		Collections.sort(dateList);
		List<String> resultList = new ArrayList<String>();
		for (Date date : dateList) {
			resultList.add(new SimpleDateFormat("yyyy-MM").format(date).substring(0, 7));
		}
		StringBuffer dateStr = new StringBuffer("");
		dateStr.append("<categories> ");
		
		for (String date : resultList) {
			dateStr.append("<category label='").append(date).append("' /> ");
		}
		dateStr.append("</categories> ");
		return dateStr.toString();
	}
	 
}
