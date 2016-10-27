package com.zendaimoney.utils.helper;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.zendaimoney.crm.sysuser.service.SysUserService;
import com.zendaimoney.sys.parameter.entity.Parameter;
import com.zendaimoney.sys.parameter.entity.ParameterEntiry;
import com.zendaimoney.sys.parameter.service.ParameterService;

/**
 * 用于对系统参数parameter进行读取和缓存
 * 
 * @ author zhanghao
 * 
 * @ create date 2012-11-9 上午09:32:41
 * 
 * @ version 1.0
 */
@Component
public class SystemParameterHelper {
	private static ParameterService parameterService = SpringContextHelper.getBean("parameterService");
	
	private static LinkedHashMap<Long, Parameter> parameterMap = new LinkedHashMap<Long, Parameter>();
	
	static {
		init(loadParameterData());
	}
	

	  
	public static LinkedHashMap<String, String> findParameterByPrTypes(String prType){
		HashMap<Object, Object> condition = new LinkedHashMap<Object, Object>();
		LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
		condition.put("prType", prType);
		List<Parameter> list = findParameterByCondition(condition);
		for (Parameter parameter : list) {
			linkedHashMap.put(parameter.getPrValue(), parameter.getPrName());
		}
		return linkedHashMap;
	}
	
	

	/**
	 * 初始化数据,将参数进行缓存
	 * 
	 * @author zhanghao
	 * @date 2012-11-9,上午09:57:57
	 * @param loadParameterData
	 */
	protected static void init(List<Parameter> loadParameterData) {
		for (Parameter parameter : loadParameterData) {
			parameterMap.put(parameter.getId(), parameter);
		}
	}

	/**
	 * 加载参数数据,从数据库表里读取所有参数.
	 * 
	 * @author zhanghao
	 * @date 2012-11-9,上午09:57:22
	 * @return 参数列表集合
	 */
	protected static List<Parameter> loadParameterData() {
		return parameterService.getAllParameterOrderId();
	}

	/**
	 * 重新加载系统参数数据到缓存中
	 * 
	 * @author zhanghao
	 * @date 2012-11-9,上午10:03:48
	 */
	/*public static void reloadParameterData() {
		parameterMap.clear();
		init(loadParameterData());
		UcHelper.initUcStaffMap();
		CreditHelper.initCreditMap();
	}*/

	/**
	 * 刷新缓存数据
	 * 
	 * @author zhanghao
	 * @date 2012-11-9,上午10:06:30
	 * @param parameter 需要更新的参数对象
	 */
	public static void refreshParameterMap(Parameter parameter) {
		if (parameter != null) {
			parameterMap.put(parameter.getId(), parameter);
		}
	}
	
	/**
	 * 查询所有参数
	 * @author hezc
	 * @date 2012年11月30日17:00:02
	 * @param 
	 * @return 符合条件的参数
	 */
	public static List<Parameter> findAll(){
		HashMap<Object, Object> condition = new HashMap<Object, Object>();
		return findParameterByCondition(condition);
	}
	
	/**
	 * 去重参数类型名称查询所有参数
	 * @author hezc
	 * @date 2012年11月30日17:00:02
	 * @param 
	 * @return 符合条件的参数
	 */
	public static List<Parameter> findAllDisPrTypename(){
		List<Parameter> parameters = SystemParameterHelper.findAll();
		HashMap<String, Object> disList = new HashMap<String, Object>();
		for (Parameter parameter : parameters) {
			if(disList.containsKey(parameter.getPrTypename())){
				disList.put(parameter.getPrTypename(), parameter);
			}
		}
		List<Parameter> parameterList = new ArrayList<Parameter>();
		LinkedHashMap<String, Parameter> parameterMap = new LinkedHashMap<String, Parameter>();
		Iterator<Entry<String, Parameter>> parameterIterator = parameterMap.entrySet().iterator();
		while(parameterIterator.hasNext()){
			parameterList.add(parameterIterator.next().getValue());
		}
		
		return parameterList;
	}
	
	/**
	 * 根据参数类型名称查询参数
	 * @author zhanghao
	 * @date 2012-11-13,下午01:56:57
	 * @param prTypename 参数类型名称
	 * @return 符合条件的参数
	 */
	public static List<Parameter> findParameterByPrTypename(String prTypename){
		HashMap<Object, Object> condition = new HashMap<Object, Object>();
		condition.put("prTypename", prTypename);
		return findParameterByCondition(condition);
	}
	
	/**
	 * 根据参数类型查询参数
	 * @author zhanghao
	 * @date 2012-11-13,下午02:00:05
	 * @param prType 参数类型
	 * @return 符合条件的参数
	 */
	public static List<Parameter> findParameterByPrType(String prType){
		HashMap<Object, Object> condition = new HashMap<Object, Object>();
		condition.put("prType", prType);
		return findParameterByCondition(condition);
	}
	
	/**
	 * 根据参数类型和状态查询参数
	 * @author zhanghao
	 * @date 2012-11-13,下午02:08:08
	 * @param prType 参数类型
	 * @param string 参数状态
	 * @return 符合条件的参数
	 */
	public static List<Parameter> findParameterByPrTypeAndPrState(String prType,String prState){//将prState类型由char改为string，因为domain写的是String，by hezc
		HashMap<Object, Object> condition = new HashMap<Object, Object>();
		condition.put("prType", prType);
		condition.put("prState", prState);
		return findParameterByCondition(condition);
	}
	
	/**
	 * 根据参数类型和状态查询参数
	 * @author jinghr
	 * @date 2013-8-19 15:39:56
	 * @param model 参数类型
	 * @param string[] 参数列表
	 * @return 符合条件的参数
	 */
	public static Model paramterList(Model model,String[] param){
		for(String pm : param){
			model.addAttribute(pm, JSONArray.fromObject(
					SystemParameterHelper.findParameterByPrTypeAndPrState(pm, "1")
					));
		}
		return model;
	}
	
	
	/**
	 * 根据参数类型名称和状态查询参数
	 * @author hezc
	 * @date 2012年11月30日16:54:31
	 * @param prTypeName prTypeName
	 * @param prState 参数状态
	 * @return 符合条件的参数
	 */
	public static List<Parameter> findParameterByPrTypenameAndPrState(String prTypeName,String prState) {
		HashMap<Object, Object> condition = new HashMap<Object, Object>();
		condition.put("prTypename", prTypeName);
		condition.put("prState", prState);
		return findParameterByCondition(condition);
	}
	/**
	 * 根据类型名称和参数名称查询参数
	 * @author hezc
	 * @date 2012年11月29日11:58:31
	 * @param prTypeName 类型名称
	 * @param prName 参数名称
	 * @return 符合条件的参数
	 */
	public static Parameter findParameterByPrTypenameAndPrName(String prTypeName,String prName){
		HashMap<Object, Object> condition = new HashMap<Object, Object>();
		condition.put("prTypename", prTypeName);
		condition.put("prName", prName);
		List<Parameter> list = findParameterByCondition(condition);
		return list.size() > 0 ? list.get(0) : null;
	}
	
	/**
	 * 根据参数类和参数名称查询参数
	 * @author hezc
	 * @date 2012年11月29日11:58:31
	 * @param prTypeName 参数类
	 * @param prName 参数名称
	 * @return 符合条件的参数
	 */
	public static Parameter findParameterByPrTypeAndPrName(String prType,String prName){
		HashMap<Object, Object> condition = new HashMap<Object, Object>();
		condition.put("prType", prType);
		condition.put("prName", prName);
		List<Parameter> list = findParameterByCondition(condition);
		return list.size() > 0 ? list.get(0) : null;
	}
	
	/**
	 * 根据类型名称和参数值查询参数
	 * @author hezc
	 * @date 2012年11月29日11:58:31
	 * @param prTypeName 类型名称
	 * @param prValue 参数值
	 * @return 符合条件的参数
	 */
	public static Parameter findParameterByPrTypenameAndPrValue(String prTypeName,String prValue){
		HashMap<Object, Object> condition = new HashMap<Object, Object>();
		condition.put("prTypename", prTypeName);
		condition.put("prValue", prValue);
		List<Parameter> list = findParameterByCondition(condition);
		return list.size() > 0 ? list.get(0) : null;
	}
	
	/**
	 * 根据类型和参数值查询参数
	 * @author hezc
	 * @date 2012年11月29日11:58:31
	 * @param prType 类型名称
	 * @param prValue 参数值
	 * @return 符合条件的参数
	 */
	public static Parameter findParameterByPrTypeAndPrValue(String prType,String prValue){
		HashMap<Object, Object> condition = new HashMap<Object, Object>();
		condition.put("prType", prType);
		condition.put("prValue", prValue);
		List<Parameter> list = findParameterByCondition(condition);
		return list.size() > 0 ? list.get(0) : null;
	}
	
	/**
	 * 根据参数类型和是否可编辑查询参数
	 * @author zhanghao
	 * @date 2012-11-13,下午02:49:29
	 * @param prType
	 * @param prIsedit
	 * @return
	 */
	public static List<Parameter> findParameterByPrTypeAndPrIsedit(String prType,Character prIsedit){
		HashMap<Object, Object> condition = new HashMap<Object, Object>();
		condition.put("prType", prType);
		condition.put("prIsedit", prIsedit);
		return findParameterByCondition(condition);
	}
	
	/**
	 * 根据参数类型和是否可编辑查询参数
	 * @author Jinghr
	 * @date 2013-8-5 10:26:53
	 * @param prType
	 * @param prTypename
	 * @return
	 */
	public static List<Parameter> findParameterByPrTypeAndprTypename(String prType,String prTypename,String prState){
		HashMap<Object, Object> condition = new HashMap<Object, Object>();
		condition.put("prType", prType);
		condition.put("prTypename", prTypename);
		condition.put("prState", prState);
		return findParameterByCondition(condition);
	}
		
	
	/**
	 * 根据条件查询参数
	 * @author zhanghao
	 * @date 2012-11-13,下午01:53:15
	 * @param condition 条件,键值对形式传入
	 * @return 符合条件的参数
	 */
	public static List<Parameter> findParameterByCondition(HashMap<Object, Object> condition){
		LinkedHashMap<Long, Parameter> parameters = new LinkedHashMap<Long, Parameter>();
		parameters.putAll(parameterMap);
		Iterator<Entry<Long, Parameter>> iterator = parameterMap.entrySet().iterator();
		Parameter parameter;
		while(iterator.hasNext()){
			parameter = iterator.next().getValue();
			if(condition.containsKey("id") && parameter.getId() != condition.get("id")){
				parameters.remove(parameter.getId());
			}
			if(condition.containsKey("prTypename") && null != parameter.getPrTypename() && !parameter.getPrTypename().equals(condition.get("prTypename"))){
				parameters.remove(parameter.getId());
			}
			if(condition.containsKey("prType") && null != parameter.getPrType() && !parameter.getPrType().equals(condition.get("prType"))){
				parameters.remove(parameter.getId());
			}
			if(condition.containsKey("prName") && null != parameter.getPrName() && !parameter.getPrName().equals(condition.get("prName"))){
				parameters.remove(parameter.getId());
			}
			if(condition.containsKey("prValue") && null != parameter.getPrValue() && !parameter.getPrValue().equals(condition.get("prValue"))){
				parameters.remove(parameter.getId());
			}
			if(condition.containsKey("prIsedit") && null != parameter.getPrIsedit() && !parameter.getPrIsedit().equals(condition.get("prIsedit"))){
				parameters.remove(parameter.getId());
			}
			if(condition.containsKey("prState") && null != parameter.getPrState() && !parameter.getPrState().equals(condition.get("prState"))){
				parameters.remove(parameter.getId());
			}
		}
		return convertMapToList(parameters);
	}
	
	/**
	 * Map转List
	 * @author zhanghao
	 * @date 2012-11-13,下午01:54:14
	 * @param parameterMap
	 * @return
	 */
	protected static List<Parameter> convertMapToList(LinkedHashMap<Long, Parameter> parameterMap){
		List<Parameter> parameterList = new ArrayList<Parameter>();
		Iterator<Entry<Long, Parameter>> parameterIterator = parameterMap.entrySet().iterator();
		while(parameterIterator.hasNext()){
			Parameter vo=new Parameter();
			Parameter parameter=parameterIterator.next().getValue();
			vo.setPrName(parameter.getPrName());
			vo.setPrValue(parameter.getPrValue());
			vo.setPrTypename(parameter.getPrTypename());
			vo.setId(parameter.getId());
			vo.setPrState(parameter.getPrState());
			parameterList.add(vo);
		}
		return parameterList;
	}

	public static String formatValue(String prType, String value){
		Parameter parameter = findParameterByPrTypeAndPrValue(prType, value);
		return parameter == null ? "" : parameter.getPrName();
	}
	
	/**
	 * 根据条件查询参数
	 * @author zhanghao
	 * @date 2012-11-13,下午01:53:15
	 * @param condition 条件,键值对形式传入
	 * @return 符合条件的参数
	 */
	protected static List<ParameterEntiry> queryParameterByCondition(HashMap<Object, Object> condition){
		LinkedHashMap<Long, Parameter> parameters = new LinkedHashMap<Long, Parameter>();
		parameters.putAll(parameterMap);
		Iterator<Entry<Long, Parameter>> iterator = parameterMap.entrySet().iterator();
		Parameter parameter;
		while(iterator.hasNext()){
			parameter = iterator.next().getValue();
			if(condition.containsKey("id") && parameter.getId() != condition.get("id")){
				parameters.remove(parameter.getId());
			}
			if(condition.containsKey("prTypename") && null != parameter.getPrTypename() && !parameter.getPrTypename().equals(condition.get("prTypename"))){
				parameters.remove(parameter.getId());
			}
			if(condition.containsKey("prType") && null != parameter.getPrType() && !parameter.getPrType().equals(condition.get("prType"))){
				parameters.remove(parameter.getId());
			}
			if(condition.containsKey("prName") && null != parameter.getPrName() && !parameter.getPrName().equals(condition.get("prName"))){
				parameters.remove(parameter.getId());
			}
			if(condition.containsKey("prValue") && null != parameter.getPrValue() && !parameter.getPrValue().equals(condition.get("prValue"))){
				parameters.remove(parameter.getId());
			}
			if(condition.containsKey("prIsedit") && null != parameter.getPrIsedit() && !parameter.getPrIsedit().equals(condition.get("prIsedit"))){
				parameters.remove(parameter.getId());
			}
			if(condition.containsKey("prState") && null != parameter.getPrState() && !parameter.getPrState().equals(condition.get("prState"))){
				parameters.remove(parameter.getId());
			}
		}
		return convertMap2List(parameters);
	}
	/**
	 * Map转List
	 * @author zhanghao
	 * @date 2012-11-13,下午01:54:14
	 * @param parameterMap
	 * @return
	 */
	protected static List<ParameterEntiry> convertMap2List(LinkedHashMap<Long, Parameter> parameterMap){
		List<ParameterEntiry> parameterList = new ArrayList<ParameterEntiry>();
		Iterator<Entry<Long, Parameter>> parameterIterator = parameterMap.entrySet().iterator();
		while(parameterIterator.hasNext()){
			ParameterEntiry vo=new ParameterEntiry();
			Parameter parameter=parameterIterator.next().getValue();
			vo.setPrName(parameter.getPrName());
			vo.setPrValue(parameter.getPrValue());
			vo.setPrState(parameter.getPrState());
			parameterList.add(vo);
		}
		return parameterList;
	}
	/**
	 * 根据参数类型和状态查询参数
	 * @author zhanghao
	 * @date 2012-11-13,下午02:08:08
	 * @param prType 参数类型
	 * @param string 参数状态
	 * @return 符合条件的参数
	 */
	public static List<ParameterEntiry> queryParameterByPrTypeAndPrState(String prType,String prState){//将prState类型由char改为string，因为domain写的是String，by hezc
		HashMap<Object, Object> condition = new HashMap<Object, Object>();
		condition.put("prType", prType);
		condition.put("prState", prState);
		return queryParameterByCondition(condition);
	}
	
	/**
	 * 根据参数类型和状态查询参数
	 * @author zhanghao
	 * @date 2012-11-13,下午02:08:08
	 * @param prType 参数类型
	 * @param string 参数状态
	 * @return 符合条件的参数
	 */
	public static List<ParameterEntiry> queryParameterByPrTypeAndPrState(String prType){//将prState类型由char改为string，因为domain写的是String，by hezc
		HashMap<Object, Object> condition = new HashMap<Object, Object>();
		condition.put("prType", prType);
		return queryParameterByCondition(condition);
	}
	
	
	/**
	 * Map转List
	 * @author CJ
	 * @date 2015-11-20,下午01:54:14
	 * @param parameterMap
	 * @return
	 */
	protected static List<Parameter> convertMapToListNew(LinkedHashMap<Long, Parameter> parameterMap){
		List<Parameter> parameterList = new ArrayList<Parameter>();
		Iterator<Entry<Long, Parameter>> parameterIterator = parameterMap.entrySet().iterator();
		while(parameterIterator.hasNext()){
			Parameter vo=new Parameter();
			Parameter parameter=parameterIterator.next().getValue();
			vo.setPrType(parameter.getPrType());//类型
			vo.setPrIsedit(parameter.getPrIsedit());//是否可编辑
			vo.setPrName(parameter.getPrName());
			vo.setPrValue(parameter.getPrValue());
			vo.setPrTypename(parameter.getPrTypename());
			vo.setId(parameter.getId());
			vo.setPrState(parameter.getPrState());
			parameterList.add(vo);
		}
		return parameterList;
	}
	
	/**
	 * 根据条件查询参数
	 * @author CJ
	 * @date 2015-11-20,下午01:53:15
	 * @param condition 条件,键值对形式传入
	 * @return 符合条件的参数
	 */
	public static List<Parameter> findParameterByConditionNew(HashMap<Object, Object> condition){
		LinkedHashMap<Long, Parameter> parameters = new LinkedHashMap<Long, Parameter>();
		parameters.putAll(parameterMap);
		Iterator<Entry<Long, Parameter>> iterator = parameterMap.entrySet().iterator();
		Parameter parameter;
		while(iterator.hasNext()){
			parameter = iterator.next().getValue();
			if(condition.containsKey("id") && parameter.getId() != condition.get("id")){
				parameters.remove(parameter.getId());
			}
			if(condition.containsKey("prTypename") && null != parameter.getPrTypename() && !parameter.getPrTypename().equals(condition.get("prTypename"))){
				parameters.remove(parameter.getId());
			}
			if(condition.containsKey("prType") && null != parameter.getPrType() && !parameter.getPrType().equals(condition.get("prType"))){
				parameters.remove(parameter.getId());
			}
			if(condition.containsKey("prName") && null != parameter.getPrName() && !parameter.getPrName().equals(condition.get("prName"))){
				parameters.remove(parameter.getId());
			}
			if(condition.containsKey("prValue") && null != parameter.getPrValue() && !parameter.getPrValue().equals(condition.get("prValue"))){
				parameters.remove(parameter.getId());
			}
			if(condition.containsKey("prIsedit") && null != parameter.getPrIsedit() && !parameter.getPrIsedit().equals(condition.get("prIsedit"))){
				parameters.remove(parameter.getId());
			}
			if(condition.containsKey("prState") && null != parameter.getPrState() && !parameter.getPrState().equals(condition.get("prState"))){
				parameters.remove(parameter.getId());
			}
		}
		return convertMapToListNew(parameters);
	}
	/**
	 * 根据多条件查询参数
	 * @author CJ
	 * @date 2015-11-12 10:26:53
	 * @param prType
	 * @param prTypename
	 * @return
	 */
	public static List<Parameter> findParameterByMultiplyCondition(Map<String,Object> map){
		HashMap<Object, Object> condition = new HashMap<Object, Object>();
		if(map.containsKey("prType")&&!"".equals(map.get("prType"))){
			condition.put("prType", map.get("prType"));
		}
		if(map.containsKey("prTypename")&&!"".equals(map.get("prTypename"))){
			condition.put("prTypename",map.get("prTypename"));
		}
//		if(map.containsKey("prState")&&!"".equals(map.get("prState"))){
//			condition.put("prState", map.get("prState"));
//		}
//		if(map.containsKey("prName")&&!"".equals(map.get("prName"))){
//			condition.put("prName", map.get("prName"));
//		}
//		if(map.containsKey("prValue")&&!"".equals(map.get("prValue"))){
//			condition.put("prValue", map.get("prValue"));
//		}
//		if(map.containsKey("id")&&!"".equals(map.get("id").toString())){
//	//	if(map.containsKey("id")&&Long.valueOf(map.get("id").toString())!=0){
//			condition.put("id", map.get("id"));
//		}
//		if(map.containsKey("prIsedit")&&!"".equals(map.get("prIsedit"))){
//			condition.put("prIsedit", map.get("prIsedit"));
//		}
		
		return findParameterByConditionNew(condition);
	}
}
