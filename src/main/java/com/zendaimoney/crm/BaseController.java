
package com.zendaimoney.crm;

import java.lang.reflect.ParameterizedType;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springside.modules.orm.DynamicSpecifications;
import org.springside.modules.orm.SearchFilter;

import com.zendaimoney.utils.BeanUtils;
import com.zendaimoney.utils.DateUtil;
import com.zendaimoney.utils.Servlets;

/**
 * 
 *	Controller基类
 */
public class BaseController<E>{
	
	/**
	 * 创建动态查询条件组合.
	 * filtersMap
	 */
	public Specification<E> buildSpecification(ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Map<String, SearchFilter> filters = SearchFilter.parse(initEndDate(searchParams));
		Specification<E> spec = DynamicSpecifications.bySearchFilter(filters.values(),null,getEntityClass(),null,null);
		return spec;
	}
	/**
	 * filtersMap 创建动态查询条件组合.
	 * filtersMap
	 */
	public Specification<E> buildSpecification(ServletRequest request,Map<String, SearchFilter> filtersMap) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Map<String, SearchFilter> filters = SearchFilter.parse(initEndDate(searchParams));
		Specification<E> spec = DynamicSpecifications.bySearchFilter(filters.values(),null==filtersMap?null:filtersMap.values(),getEntityClass(),null,null);
		return spec;
	}
	/**
	 * filtersMap，数据权限 创建动态查询条件组合.
	 * @param request
	 * @param filtersMap filter过滤器
	 * @param shareType 共享类型 见 Share.java
	 * @param gatherParam 采集人字段
	 */
	public  Specification<E> buildSpecification(ServletRequest request,Map<String, SearchFilter> filtersMap,Integer shareType, String gatherParam) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Map<String, SearchFilter> filters = SearchFilter.parse(initEndDate(searchParams));
		Specification<E> spec = DynamicSpecifications.bySearchFilter(filters.values(),null==filtersMap?null:filtersMap.values(),getEntityClass(),shareType,  gatherParam);
		return spec;
	}
	
	/**
	 * 创建分页请求
	 */
	public PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType,String order) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("".equals(sortType)) {
			sort = null;
		} 
		else {
			sort = new Sort(Direction.fromString(order), sortType);
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
	
	/**
	 * 创建分页请求(可以组合排序),Jinghr,2013-6-20 
	 */
	public PageRequest buildPageRequest(int pageNumber, int pagzSize, List<String> sortType,String order) {
		Sort sort = null;
		if (sortType.size()==0 || "auto".equals(sortType.get(0))) {
			sort = new Sort(Direction.DESC, "id");
		} else  {
			//sortType.add("id");
			sort = new Sort(Direction.fromString(order), sortType);
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}		
	
	public Map<String, SearchFilter> searchConditionParamMap(ServletRequest request){
		return SearchFilter.parse(initEndDate(Servlets.getParametersStartingWith(request, "search_"))); //增加截止日期完善+1天方法
	}
	
	public static final String ENDTIMEFIX = "endDate-";
	public static final String ISORFIX = "isor";
	
	/**
	 * 把end时间+1天 
	 * @param request
	 * @param dateParamName
	 * @return
	 */
	
	private Map<String, Object> initEndDate(Map<String, Object> searchParams) {
		 Set<Map.Entry<String, Object>> set = searchParams.entrySet();
	        for (Iterator<Map.Entry<String, Object>> it = set.iterator(); it.hasNext();) {
	            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
	            if(entry.getKey().contains(ENDTIMEFIX)){
	            	String endDate = entry.getValue().toString();
	            	if(org.springframework.util.StringUtils.hasText(endDate))searchParams.put(entry.getKey(), DateUtil.format(DateUtil.getDaysDate(DateUtil.parse(endDate,"yyyy/MM/dd"), 1), "yyyy/MM/dd"));
	            }
	        }
		 return searchParams;
	}

	
	/**
	 * 根据别名装配参数 默认 “.”
	 */
	public static <E> E assembleWithAlias(ServletRequest request,Class<E> clazz,String alias) {
		Map<String, Map> parames = getParametersContainingWith(request,".");
		Map properties = parames.get(alias);
		if(properties != null && properties.size()>0){
			E object = null;
			try {
				object = clazz.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BeanUtils.copyProperties(object, parames.get(alias));
			return object;
		}else{
			return null;
		}
	}
	
	/**
	 * 根据别名装配参数
	 */
	public static <E> E assembleWithAlias(ServletRequest request,Class<E> clazz,String alias,String fix) {
		Map<String, Map> parames = getParametersContainingWith(request,fix);
		Map properties = parames.get(alias);
		if(properties != null && properties.size()>0){
			E object = doNewEntity(clazz);;
			BeanUtils.copyProperties(object, parames.get(alias));
			return object;
		}else{
			return null;
		}
	}
	
	
	/**
	 * 取得包含相同标记的Request Parameters 
	 *  
	 */
	private static Map<String, Map> getParametersContainingWith(ServletRequest request, String fix) {
		Enumeration paramNames = request.getParameterNames();
		Map<String, Map> params = new TreeMap<String, Map>();
		if (fix == null) {
			fix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(fix) || paramName.contains(fix)) {
				String alias = paramName.substring(0,paramName.indexOf(fix));//别名
				String unprefixed = paramName.substring(paramName.indexOf(fix)+1);//属性名
				String[] values = request.getParameterValues(paramName);
				
				Map proMap = params.get(alias);
				if(proMap == null)proMap = new HashMap();
				
				if (szIsBlank(values) ) {
				} else if (values.length > 1) {
					proMap.put(unprefixed, values);
					params.put(alias, proMap);
				} else {
					proMap.put(unprefixed, values[0]);
					params.put(alias, proMap);
				}
			}
		}
		
		return params;
	}
	
	/**
	 * 根据别名装配参数
	 */
	public static <E> E assembleWithAlias(Map requestParameterMap,Class<E> clazz,String alias,String fix) {
		Map<String, Map> parames = getParametersContainingWith(requestParameterMap,fix);
		Map properties = parames.get(alias);
		if(properties != null && properties.size()>0){
			E object = doNewEntity(clazz);
			BeanUtils.copyProperties(object, parames.get(alias));
			return object;
		}else{
			return null;
		}
	}

	/**
	 * 从map取得包含相同标记的Request Parameters 
	 *  
	 */
	 private static Map<String, Map> getParametersContainingWith(Map requestParameterMap, String fix) {
		Map<String, Map> params = new TreeMap<String, Map>();
		if (fix == null) {
			fix = "";
		}
		 Set  key = requestParameterMap.keySet();
	        for (Iterator it = key.iterator(); it.hasNext();) {
	            String paramName = (String) it.next();
	            if ("".equals(fix) || paramName.contains(fix)) {
					String alias = paramName.substring(0,paramName.indexOf(fix));//别名
					String unprefixed = paramName.substring(paramName.indexOf(fix)+1);//属性名
					String[] values = new String []{};
					Object value = requestParameterMap.get(paramName);
					
					if(value instanceof String )values = new String []{value.toString()};
					if(value instanceof String[] )values = (String[]) value;
					
					Map proMap = params.get(alias);
					if(proMap == null)proMap = new HashMap();
					
					if (szIsBlank(values) ) {
					} else if (values.length > 1) {
						proMap.put(unprefixed, values);
						params.put(alias, proMap);
					} else {
						proMap.put(unprefixed, values[0]);
						params.put(alias, proMap);
					}
				}
	        }
		return params;
	} 
	
	public static boolean szIsBlank(Object[] a) {
		if (a == null || a.length == 0 ) return true;
		for (int i = 0; i < a.length; i++) {
			if (!StringUtils.isBlank(a[i].toString()) ) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * new一个实体对象
	 * 
	 * @return 类型为E的一个实体对象
	 */
	public static <E> E doNewEntity(Class<E> clazz) {
		E object = null;
		try {
			object = clazz.newInstance();
		} catch (Exception e) {
		}
		return object;
	}
	
	/**
	 * new一个实体对象
	 * 
	 * @return 类型为E的一个实体对象
	 */
	public E doNewEntity() {
		E object = null;
		try {
			object = getEntityClass().newInstance();
		} catch (Exception e) {
		}
		return object;
	}
	
	/**
	 * 获取实体类的名称 不包含全路径(包路径)
	 * 
	 * @return 实体类的名称
	 */
	@SuppressWarnings("unchecked")
	public Class<E> getEntityClass() {
		return (Class<E>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}	 
}
