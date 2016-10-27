package com.zendaimoney.utils;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.zendaimoney.constant.DesktopConstant;
import com.zendaimoney.constant.LoggingSource;
import com.zendaimoney.constant.LoggingType;
import com.zendaimoney.crm.sysuser.entity.SysUser;
import com.zendaimoney.sys.logging.entity.Logging;
import com.zendaimoney.sys.logging.repository.LoggingDao;
import com.zendaimoney.uc.rmi.vo.Staff;
import com.zendaimoney.utils.helper.SpringContextHelper;

/**
 * 
 * @author bxj
 * 
 */
public class LoggingHelper {
	private static LoggingDao loggingDao;

	/**
	 * 
	 * @param operType 操作类型，见枚举LoggingType
	 * @param operSource 操作来源，见枚举LoggingSource
	 * @param operContent 内容
	 * @param request
	 */
	public static void createLogging(LoggingType operType,LoggingSource operSource, String operContent,SysUser user) {
		loggingDao = SpringContextHelper.getBean("loggingDao");

		//Staff staff = AuthorityHelper.getStaff();
		//修改id获取方法，如果没有登录人，则判断位app日志，staff的id 值赋值为 99999999   Sam.J 14.11.18
		//long staffid=staff==null?DesktopConstant.APP_AUDIT_STAFFID:staff.getId();  //员工id
		//String staffName = staff == null ? "" : staff.getUsername();   //员工姓名
		//String loginIP = staff == null ? "" : staff.getLoginIP();    //操作系统的ip地址
		long staffid=user.getId();  //员工id
		String staffName = user.getUserName();   //员工姓名
		String loginIP ="";
		Logging log = new Logging();

		log.setLgOperatorid(staffid);
		log.setLgOperatorname(staffName);
		log.setLgLogtime(new Date());
		log.setLgLogtype(operType);
		log.setLgSource(operSource);
		log.setLgContent(operContent);
		log.setLgIp(loginIP);
		loggingDao.save(log);
	}
	/**
	 * 为app 提供接口
	 * @param operType 操作类型，见枚举LoggingType
	 * @param operSource 操作来源，见枚举LoggingSource
	 * @param operContent 内容
	 * @param request
	 */
	public static void createLoggingApp(Long staffId,LoggingType operType,LoggingSource operSource, String operContent) {
		loggingDao = SpringContextHelper.getBean("loggingDao");

		

		Logging log = new Logging();

		log.setLgOperatorid(staffId);
		log.setLgOperatorname("移动终端登陆");
		log.setLgLogtime(new Date());
		log.setLgLogtype(operType);
		log.setLgSource(operSource);
		log.setLgContent(operContent);
		log.setLgIp("移动终端登陆");
		loggingDao.save(log);
	}
	public static void createLogging(LoggingType operType,LoggingSource operSource, String operContent,Long operatorid) {
		loggingDao = SpringContextHelper.getBean("loggingDao");

		Logging log = new Logging();

		log.setLgOperatorid(operatorid);
//		log.setLgOperatorname(staff.getUsername());
		log.setLgLogtime(new Date());
		log.setLgLogtype(operType);
		log.setLgSource(operSource);
		log.setLgContent(operContent);
//		log.setLgIp(staff.getLoginIP());
		loggingDao.save(log);
	}
	
	public static void createLogging(Staff staff,LoggingType operType,LoggingSource operSource, String operContent) {
		loggingDao = SpringContextHelper.getBean("loggingDao");

		Logging log = new Logging();

		log.setLgOperatorid(staff.getId());
		log.setLgOperatorname(staff.getUsername());
		log.setLgLogtime(new Date());
		log.setLgLogtype(operType);
		log.setLgSource(operSource);
		log.setLgContent(operContent);
		log.setLgIp(staff.getLoginIP());
		loggingDao.save(log);
	}
	/**
	 * 比较实体对象更改
	 * @param oldObject
	 * @param newObject
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @author hezc 2013年1月8日16:31:49
	 */
	public static String loggingEquals(Object oldObject,Object newObject,List<String> needList) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String result = "";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");    

		Class newClassType = newObject.getClass();
		Class oldClassType = oldObject.getClass();
		Field newFields[] = newClassType.getDeclaredFields();
		Field oldFields[] = oldClassType.getDeclaredFields();
		for (int i = 0; i < newFields.length; i++) {
		   Field newField = newFields[i];
		   Field oldField = oldFields[i];
		   String newFieldName = newField.getName();
		   String type = newField.getGenericType().toString();    
		   for (String need : needList) {
			 if(newFieldName.equals(need)){
				String oldFieldName = oldField.getName();
				String newFirstLetter = newFieldName.substring(0, 1).toUpperCase(); // 获得和属性对应的getXXX()方法的名字
				String oldFirstLetter = oldFieldName.substring(0, 1).toUpperCase(); // 获得和属性对应的getXXX()方法的名字
				String newGetMethodName = "get" + newFirstLetter + newFieldName.substring(1); // 获得和属性对应的getXXX()方法的名字
				String oldGetMethodName = "get" + oldFirstLetter + oldFieldName.substring(1); // 获得和属性对应的getXXX()方法的名字
				Method newGetMethod = newClassType.getMethod(newGetMethodName, new Class[] {}); // 获得和属性对应的getXXX()方法
				Method oldGetMethod = oldClassType.getMethod(oldGetMethodName, new Class[] {}); // 获得和属性对应的getXXX()方法
				Object newValue = newGetMethod.invoke(newObject, new Object[] {});
				Object oldValue = oldGetMethod.invoke(oldObject, new Object[] {});
				if(oldValue==null||newValue==null){
					if(newValue!=oldValue){result += "属性["+newFieldName+ "] 由 “"+oldValue +"”-->“"+ newValue +"”；\r\n";}
				}else if(type.equals("class java.util.Date")){    
					newValue = format.format(newValue);
					oldValue = format.format(oldValue);
				}else if(oldValue.equals(newValue)){
				}else{
					result += "属性["+newFieldName+ "] 由 “"+oldValue +"”-->“"+ newValue +"”；\r\n";
				}
			 }
		   }
		}
		return result;
	}
	
	/**
	 * 反射获取某个类中的get方法
	 *  
	 * @author zhanghao
	 * @date 2013-01-17 14:04:38
	 * @param obj 类对象
	 * @param att 列名
	 * @return 属性值
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Object getter(Object obj,String att) {
		Method method = null;
		Object object = null;
			try {
				method = obj.getClass().getMethod("get" + att);
				object = method.invoke(obj); 
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		return object;
	}
	
	/**
	 * 构建日志内容
	 *  
	 * @author zhanghao
	 * @date 2013-02-27 00:03:50
	 * @param className 对象名称
	 * @param id 对象id
	 * @return 日志内容
	 */
	public static String builderLogContent(String className, Long id) {
		return "操作对象:" + className + ",对象ID:" + String.valueOf(id);
	}
	
	/**
	 * 构建日志内容
	 *  
	 * @author zhanghao
	 * @date 2013-02-27 00:04:21
	 * @param id 对象id
	 * @param oldObject 原始对象
	 * @param newObject 修改后的对象
	 * @param needList 修改的字段集合
	 * @return 日志内容
	 */
	public static String builderLogContent(Long id, Object oldObject,Object newObject,List<String> needList) {
		String logContent = "";
		try {
			logContent = builderLogContent(newObject.getClass().getSimpleName(), 
					id) + "," + LoggingHelper.loggingEquals(oldObject, newObject, needList);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return logContent;
	}

}
