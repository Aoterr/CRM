package com.zendaimoney.utils.mapper;

import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;
/**
 * 
 * @author bianxj
 * 
 */
public class GenHtmlHelper {
	
	/**
	 * 返回多选列表选中的值
	 * @param selectMap
	 * @param selectName
	 * @return
	 */
	public static  String getTextStr(String selectName){
		StringBuffer sb = new StringBuffer();
		sb.append("<input name=");
		sb.append(selectName);
		sb.append(">");
		sb.append("</input>");

		return sb.toString();
		}

	/**
	 * 返回下拉列表的值
	 * @param selectMap
	 * @param selectName
	 * @return
	 */
	public static  String getSelectStr(LinkedHashMap selectMap,String selectName,String displayName){
	 if(!"".equals(displayName)){
		 selectMap.put("", displayName);
	 }
	  StringBuffer str=new StringBuffer();
	  String id=selectName;
	  String[] strs =StringUtils.split(selectName,".");
	
	  if(strs.length==2){
		  id=strs[1];
	  }
	  str.append("<select name='"+selectName+"' id='"+id+"'>");
	 for(Iterator i=selectMap.keySet().iterator();i.hasNext();){
		  String value=String.valueOf(i.next());
		  String name=String.valueOf(selectMap.get(value));
		  String option="";
		  if(!"".equals(value)){
			   option="<option value='"+value+"'>"; 
		  }else{
			  option="<option value='"+value+"' selected >";
		  }
		 
		  String optioned="</option>";
		  str.append(option+name+optioned);
	  }
	  str.append("</select>");
	  return str.toString();
	}

	/**
	 * 返回下拉列表的已选择的值
	 * @param selectMap
	 * @param selectName
	 * @param selectValue
	 * @return
	 */
	public static String getSelectStrAndCheck(LinkedHashMap selectMap,String selectName,String selectValue,String displayName){
			 
		     String id=selectName;
			 String[] strs =StringUtils.split(selectName,".");
			
			 if(strs.length==2){
				  id=strs[1];
			  }
			 if(!"".equals(displayName)){
				 selectMap.put("", displayName);
			 }
			 
		 	if(selectValue==null){
		 		selectValue="";
		 	}
		 	
			StringBuffer str=new StringBuffer();
		 	str.append("<select name='"+selectName+"' id='"+id+"'>");
		 
		 	
		  for(Iterator i=selectMap.keySet().iterator();i.hasNext();){
			  String value=String.valueOf(i.next());
			  String name=String.valueOf(selectMap.get(value));
			  String option="";
			 
			 if(selectValue.equals(value)){
			    option="<option value='"+value+"' selected >";
			 }else{
			    option="<option value='"+value+"'>"; 
			 }
			  
			  String optioned="</option>";
			  str.append(option+name+optioned);
		  }
		  str.append("</select>");
		  return str.toString();
		}

	/**
	 * 返回多选列表的值
	 * @param selectMap
	 * @param selectName
	 * @param selectValue逗号分割
	 * @return
	 */
	public static  String getCheckStrAndCheck(LinkedHashMap selectMap,String selectName,String selectValue){
		String id=selectName;
		String[] strs =StringUtils.split(selectName,".");
		if(strs.length==2){
			  id=strs[1];
		  }
		StringBuffer str=new StringBuffer();
	  for(Iterator i=selectMap.keySet().iterator();i.hasNext();){
		  String value=String.valueOf(i.next());
		  String name=String.valueOf(selectMap.get(value));
			if (selectValue != null && selectValue.indexOf(value) != -1) {//包括这个字段,以后改这样写有bug
			  str.append("<input name='"+selectName+"' type=checkbox value='"+value+"' checked "+" id='"+id+"'> <span class='gray'>"+value+ "</span>");
			 }else{
				 str.append("<input name='"+selectName+"' type=checkbox value='"+value+"' id='"+id+"' ><span class='gray'>"+value+ "</span>");
			 }
		  
	  }
	  return str.toString();
	}

	/**
	 * 返回多选列表选中的值
	 * @param selectMap
	 * @param selectName
	 * @return
	 */
	public static  String getCheckStr(LinkedHashMap selectMap,String selectName){
	  StringBuffer str=new StringBuffer();
	  String id=selectName;
		String[] strs =StringUtils.split(selectName,".");
		if(strs.length==2){
			  id=strs[1];
		  }
	 int len=0;
	  for(Iterator i=selectMap.keySet().iterator();i.hasNext();){
		  String value=String.valueOf(i.next());
		  String name=String.valueOf(selectMap.get(value));
		  if(len==0){
			  str.append("<input name='"+selectName+"' type=checkbox value='"+value+"' checked "+" id='"+id+"'><span class='gray'>"+name+ "</span>");
		  }else{
			  str.append("<input name='"+selectName+"' type=checkbox value='"+value+"' id='"+id+"' ><span class='gray'>"+name+ "</span>");
		  }
		  len=len+1;
	  }
	  return str.toString();
	}
	

}
