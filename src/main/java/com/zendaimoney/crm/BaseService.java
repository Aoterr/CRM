/** 
 * @(#)BaseService.java 1.0.0 2013-03-14 12:30:21  
 *  
 * Copyright © 2013 证大财富.  All rights reserved.  
 */

package com.zendaimoney.crm;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Order;
import org.springside.modules.orm.SearchFilter;
import org.springside.modules.orm.SearchFilter.Operator;

/**
 * Class BaseService
 * 
 * @author zhanghao
 * @version $Revision:1.0.0, $Date: 2013-03-14 12:30:21 $
 * @param <E>
 */
public class BaseService<E> {
	@PersistenceContext
	protected EntityManager em;
	
	/**
	 * 查询内容
	 *  
	 * @author zhanghao
	 * @date 2013-03-14 18:09:32
	 * @param baseSql sql语句
	 * @param page 分页对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<E> getContent(Map<String, SearchFilter> searchCondition,StringBuffer baseSql, PageRequest page) {
		//添加查询SQL语句
		addConditionSql(baseSql, searchCondition);
		//添加排序
		baseSql.append(builderOrderSQL(page));
		
		Query query = em.createQuery(baseSql.toString());
		//设置查询参数值
		setQueryParameter(query, searchCondition);
		//分页参数
		query.setFirstResult(page.getPageNumber() * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		return query.getResultList();
	}
	
	/**
	 * 根据SQL语句进行查询
	 *  
	 * @author zhanghao
	 * @date 2013-6-21 上午10:04:09
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<E> getContent(String sql){
		if(StringUtils.isBlank(sql)) return null;
		Query query = em.createQuery(sql.toString());
		return query.getResultList();
	}
	
	/**
	 *  构建排序SQL
	 *  
	 * @author zhanghao
	 * @date 2013-4-19 上午11:48:15
	 * @param pageRequest
	 * @return
	 */
	public String builderOrderSQL(PageRequest pageRequest){
		StringBuffer orderSQL = new StringBuffer();
		if (pageRequest.getSort() == null) {
			return "";
		}
		Iterator<Order> orders = pageRequest.getSort().iterator();
		if(orders != null){
			orderSQL.append(" order by ");
			while(orders.hasNext()) {
				Order order = orders.next();
				orderSQL.append(order.getProperty()).append(" ").append(order.getDirection().name()).append(",");//实现多个排序条件
			}
		}
		return orderSQL.toString().substring(0,orderSQL.toString().length()-1);
		
	}
	
	
	/**
	 * 查询总记录
	 *  
	 * @author zhanghao
	 * @date 2013-03-15 15:56:23
	 * @param searchCondition 查询条件MAP
	 * @param baseSql 基础SQL
	 * @return
	 */
	public long getTotal(Map<String, SearchFilter> searchCondition, StringBuffer baseSql){
		//添加查询SQL语句
		addConditionSql(baseSql, searchCondition);
		String totalSql = "select count(*) " + baseSql.substring(baseSql.indexOf("from"), baseSql.length());
		Query query = em.createQuery(totalSql);
		//设置查询参数值
		setQueryParameter(query, searchCondition);
		return((Long)query.getSingleResult()).longValue();
	}
	
	/**
	 * 构建查询SQL
	 *  
	 * @author zhanghao
	 * @date 2013-03-15 15:34:17
	 * @param searchCondition 查询条件MAP
	 * @return 条件SQL
	 */
	public StringBuffer builderConditionSql(Map<String, SearchFilter> searchCondition) {
		StringBuffer conditionSql = new StringBuffer();
		if (searchCondition != null) {
			for (Entry<String, SearchFilter> condition : searchCondition
					.entrySet()) {
				conditionSql
						.append(" and ")
						.append(condition.getValue().fieldName)
						.append(formatOperator(condition.getValue().operator))
						.append(":")
						.append(formatFieldName(condition.getValue().fieldName));
			}
		}
		return conditionSql;
	}
	
	/**
	 * 添加查询条件
	 *  
	 * @author zhanghao
	 * @date 2013-03-15 15:56:06
	 * @param baseSql  基础SQL
	 * @param searchCondition 查询条件MAP
	 * @return 加上查询条件的SQL
	 */
	public StringBuffer addConditionSql(StringBuffer baseSql, Map<String, SearchFilter> searchCondition){
		return baseSql.append(builderConditionSql(searchCondition));
	}
	
	/**
	 * 设置查询条件的Value
	 *  
	 * @author zhanghao
	 * @date 2013-03-15 15:34:46
	 * @param query 查询对象
	 * @param searchCondition 查询条件MAP
	 */
	public void setQueryParameter(Query query, Map<String, SearchFilter> searchCondition) {
		if(searchCondition != null){
			for (Entry<String, SearchFilter> condition : searchCondition.entrySet()) {
				query.setParameter(
						formatFieldName(condition.getValue().fieldName),
						condition.getValue().operator.equals(Operator.LIKE) ? ("%" + condition.getValue().value +"%") : condition.getValue().value);
			}
		}
	}
	
	/**
	 * 格式化查询条件占位符名称,（比如C.crName := crName）
	 *  
	 * @author zhanghao
	 * @date 2013-03-15 15:35:20
	 * @param fieldName
	 * @return
	 */
	public String formatFieldName(String fieldName) {
		if(fieldName.lastIndexOf(".") == -1){
			return fieldName;
		}else{
			return fieldName.substring(fieldName.lastIndexOf(".") + 1, fieldName.length());
		}
	}
	
	public String formatOperator(Operator operator){
		String val = "";
		switch (operator) {
		case EQ:
			val = " =";
			break;
		case LIKE:
			val = " like";
			break;
		case GT:
			val = " >";
			break;
		case LT:
			val = " <";
			break;
		case GTE:
			val = " >=";
			break;
		case LTE:
			val = " <=";
			break;
		case IN:
			val = " in";
			break;
		}
		return val;
	}
	
	
	/** 
	 * author:Jinghr 2013-8-6 15:19:24
	 * 判断字符串是否是数字
	 * */
	public boolean isNumber(String src){
		 Pattern pat = Pattern.compile("\\d+");
		 Matcher matcher = pat.matcher(src);
		 if (matcher.matches())
			 return true;
		 else
			 return false;
	}
	
	/** 
	 * author:Jinghr 2013-9-25 9:23:45
	 * 判断字符串是否是数字
	 * */
	public String isCodeNumber(String src){
		if(null == src)
			return null;
		 Pattern pat = Pattern.compile("\\d+");
		 Matcher matcher = pat.matcher(src);
		 if (matcher.matches())
			 return src;
		 else
			 return null;
	}
	
	
	/** 
	* @Title:计算总条数修正版方法 
	* @Description: 修改原方法的赋值方式 
	* @param searchCondition
	* @param baseSql
	* @return   
	* @throws 
	* @time:2014-10-13 下午04:20:57
	* @author:Sam.J
	*/
	public long getTotalNew(Map<String, SearchFilter> searchCondition, StringBuffer baseSql){
		//添加查询SQL语句
		baseSql.append(builderConditionSqlNew(searchCondition));
		String totalSql = "select count(*) " + baseSql.substring(baseSql.indexOf("from"), baseSql.length());
		Query query = em.createQuery(totalSql);
		//设置查询参数值
		setQueryParameterNew(query, searchCondition);
		return((Long)query.getSingleResult()).longValue();
	}
	
	
	/** 
	* @Title:SQL构造方法 （新）
	* @Description: 直接在sql里赋值参数名用key（带操作占位符） 避免同一参数名不同操作造成赋值影响。如 >,<
	* @param searchCondition
	* @return   
	* @throws 
	* @time:2014-10-13 下午04:22:23
	* @author:Sam.J
	*/
	public StringBuffer builderConditionSqlNew(Map<String, SearchFilter> searchCondition) {
		StringBuffer conditionSql = new StringBuffer();
		if (searchCondition != null) {
			for (Entry<String, SearchFilter> condition : searchCondition
					.entrySet()) {
				conditionSql
						.append(" and ")
						.append(condition.getValue().fieldName)
						.append(formatOperator(condition.getValue().operator))
						.append(":")
						.append(formatFieldNameNew(condition.getKey()));
			}
		}
		return conditionSql;
	}
	
	
	/** 
	* @Title:给查询条件赋值 
	* @Description: 赋值的参数名取key值，对应新的构造sql方法 
	* @param query
	* @param searchCondition   
	* @throws 
	* @time:2014-10-13 下午04:50:18
	* @author:Sam.J
	*/
	public void setQueryParameterNew(Query query, Map<String, SearchFilter> searchCondition) {
		if(searchCondition != null){
			for (Entry<String, SearchFilter> condition : searchCondition.entrySet()) {
				query.setParameter(
						formatFieldNameNew(condition.getKey()),
						condition.getValue().operator.equals(Operator.LIKE) ? ("%" + condition.getValue().value +"%") : condition.getValue().value);
			}
		}
	}
	
	
	/** 
	* @Title:格式化参数名(新)
	* @Description: 去掉"."  避免对sql语句造成影响 
	* @param fieldName
	* @return   
	* @throws 
	* @time:2014-10-13 下午05:13:10
	* @author:Sam.J
	*/
	public String formatFieldNameNew(String fieldName) {
		return fieldName.replace(".","");
	}
	
	
	/** 
	* @Title:查询新方法 
	* @Description: 对应新的构造sql方法跟赋值方法 
	* @param searchCondition
	* @param baseSql
	* @param page
	* @return   
	* @throws 
	* @time:2014-10-13 下午05:13:35
	* @author:Sam.J
	*/
	@SuppressWarnings("unchecked")
	public List<E> getContentNew(Map<String, SearchFilter> searchCondition,StringBuffer baseSql, PageRequest page) {
		//添加查询SQL语句
		baseSql.append(builderConditionSqlNew(searchCondition));
		//添加排序
		baseSql.append(builderOrderSQL(page));
		
		Query query = em.createQuery(baseSql.toString());
		//设置查询参数值
		setQueryParameterNew(query, searchCondition);
		//分页参数
		query.setFirstResult(page.getPageNumber() * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		return query.getResultList();
	}
}
