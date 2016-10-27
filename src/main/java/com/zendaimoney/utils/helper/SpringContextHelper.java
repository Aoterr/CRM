package com.zendaimoney.utils.helper;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.zendaimoney.uc.rmi.vo.Staff;

public class SpringContextHelper implements ApplicationContextAware {
	private static ApplicationContext _cxt = null; // Spring应用上下文环境

	public SpringContextHelper(){
		_log.info("创建SpringContextUtil实例");
	}
	/**
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境
	 * 
	 * @param applicationContext
	 * @throws BeansException
	 */
	public void setApplicationContext(ApplicationContext applicationContext)
		throws BeansException {	
		this._cxt = applicationContext;
		_log.info("注入 spring ApplicationContext");
	}
	/**
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		if(SpringContextHelper._cxt==null){
			_log.info("not give spring ApplicationContext");
		}
		return SpringContextHelper._cxt;
	}

	/**
	 * 获取对象
	 * 
	 * @param name
	 * @return Object 一个以所给名字注册的bean的实例
	 * @throws BeansException
	 */
	public static <T> T getBean(String name) throws BeansException {
		if(_cxt==null){
			_log.info("null");
		}
		return (T) _cxt.getBean(name);
	}

	/**
	 * 获取类型为requiredType的对象
	 * 如果bean不能被类型转换，相应的异常将会被抛出（BeanNotOfRequiredTypeException）
	 * 
	 * @param name
	 *            bean注册名
	 * @param requiredType
	 *            返回对象类型
	 * @return Object 返回requiredType类型对象
	 * @throws BeansException
	 */
	public static Object getBean(String name, Class requiredType)
			throws BeansException {
		return _cxt.getBean(name, requiredType);
	}

	/**
	 * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
	 * 
	 * @param name
	 * @return boolean
	 */
	public static boolean containsBean(String name) {
		return _cxt.containsBean(name);
	}

	/**
	 * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
	 * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
	 * 
	 * @param name
	 * @return boolean
	 * @throws NoSuchBeanDefinitionException
	 */
	public static boolean isSingleton(String name)
			throws NoSuchBeanDefinitionException {
		return _cxt.isSingleton(name);
	}

	/**
	 * @param name
	 * @return Class 注册对象的类型
	 * @throws NoSuchBeanDefinitionException
	 */
	public static Class getType(String name)
			throws NoSuchBeanDefinitionException {
		return _cxt.getType(name);
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		return (T) _cxt.getBeansOfType(clazz);
	}

	/**
	 * 如果给定的bean名字在bean定义中有别名，则返回这些别名
	 * 
	 * @param name
	 * @return
	 * @throws NoSuchBeanDefinitionException
	 */
	public static String[] getAliases(String name)
			throws NoSuchBeanDefinitionException {
		return _cxt.getAliases(name);
	}



	private static Log _log = LogFactory.getLog(SpringContextHelper.class);
	
	/** 
	* @Title: getBean2 
	* @Description: 根据class类读取对应的service或dao（加载时需注入）
	* @param <T>
	* @param clz
	* @return
	* @throws BeansException T(描述入参以及返回参数的含义)
	* @author Sam.J
	* @date 2015-11-10 下午03:43:52 
	* @throws 
	*/
	public static <T> T getBean2(Class<T> clz) throws BeansException {
        T result = (T) _cxt.getBean(clz);
        return result;
    }
}
