package com.zendaimoney.utils.helper;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

 
/**
 * 
 * @author bianxj
 *提供取用户和用户权限的方法
 */
public class SecurityHelper {
	
	/**
	 * 得到登录用户
	 * 
	 * @return
	 */
	public static UserDetails getUserDetails(){
		 Authentication au = SecurityContextHolder.getContext().getAuthentication();
		 //增加是否为null判断，有app业务不走页面所以没有登录人信息 Sam.J 14.11.18
		 UserDetails userdetails=au==null?null:(UserDetails)au.getPrincipal();
		return userdetails;
	 }
	 
	/**
	 * 得到用户有的权限用逗号割开的
	 * 
	 * @return
	 */ 
	 
	 public static Map getPermissionNames(){
		 Map map=new HashMap();
		
		 Authentication au = SecurityContextHolder.getContext().getAuthentication();
		 Collection<? extends GrantedAuthority>  permissions = au.getAuthorities();
		 for( Iterator i =permissions.iterator();i.hasNext();){
			 GrantedAuthority permission=(GrantedAuthority) i.next();
				if(permission.getAuthority()!=null) {
					map.put(permission.getAuthority(), permission.getAuthority());
				}
		 }
		 return map;
	 }
	 
	 
	 /**
	 * 判断用户是不是有这个权限 true 有权限
	 * 
	 * @param permission
	 * @return
	 */
	 public static boolean hasPermision(String permission){
		 if(permission!=null&&permission.length()>1){
			 Map map = getPermissionNames();
				  if(map.get(permission)!=null)
					return true;
		 }
		 return false;
	 }
}
