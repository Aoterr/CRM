package com.zendaimoney.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

/** 
 * 
 * @author bianxj 
 */  
public class BeanUtilsDateConvert implements Converter {  
  
	@Override
    public Object convert(Class type, Object value) {  
        String p =value.toString();  
        if(p== null || p.trim().length()==0){  
            return null;  
        }  
        try{  
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            return df.parse(p.trim());  
        }  
        catch(Exception e){  
            try {  
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
                return df.parse(p.trim());  
            } catch (ParseException ex) {  
                return null;  
            }  
        }  
          
    }  
  
}  