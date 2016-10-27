package com.zendaimoney.utils;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.ConvertUtils;

/**
 *
 * @author bianxj
 */
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {
    static { 
    	
    	ConvertUtils.register(new BeanUtilsDateConvert(), java.util.Date.class);
        ConvertUtils.register(new BeanUtilsDateConvert(), java.sql.Date.class);
    }
    public static void copyProperties(Object dest, Object orig) {
        try {
        	org.apache.commons.beanutils.BeanUtils.copyProperties(dest, orig);
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }
    public static void setProperty(Object bean, String name, Object value){
    	try {
        	org.apache.commons.beanutils.BeanUtils.setProperty(bean, name, value);
        	 System.out.println(ConvertUtils.lookup(java.util.Date.class));
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }
}
