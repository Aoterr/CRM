package org.springside.modules.orm;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;
import com.zendaimoney.crm.BaseController;

public class SearchFilter implements Serializable{

	private static final long serialVersionUID = -8644697546868108718L;

	public enum Operator {
		EQ, LIKE, GT, LT, GTE, LTE, IN, ISNULL,  //加入in byb
		NOTIN  //加入notin 条件
	}

	public String fieldName;
	public Object value;
	public Operator operator;
	
	public boolean isOr;//是否是或 byb

	public SearchFilter(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}
	
	public SearchFilter(String fieldName, Operator operator, Object value,boolean isOr) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
		this.isOr = isOr;
	}

	/**
	 * searchParams中key的格式为OPERATOR_FIELDNAME
	 */
	public static Map<String, SearchFilter> parse(
			Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = Maps.newHashMap();

		for (Entry<String, Object> entry : searchParams.entrySet()) {
			// 过滤掉空值
			String key = entry.getKey();
			Object value = entry.getValue();
			if(value instanceof String[]){//增加回退提醒后，点击高级查询会有两个状态值传入。这里做处理
				String[] values=(String[])value;
				if(StringUtils.isBlank(values[1])){
					value=values[0];
				}else{
					value=values[1];
				}
			}
			if (StringUtils.isBlank((String) value)) {
				continue;
			}

			/**
			 * 剔除endfix byb
			 */
			if (key.contains(BaseController.ENDTIMEFIX)) {
				key = key.toString().replace(BaseController.ENDTIMEFIX, "");
			}

			// 拆分operator与filedAttribute
			String[] names = StringUtils.split(key, "_");
			if (names.length != 2) {
				throw new IllegalArgumentException(key
						+ " is not a valid search filter name");
			}
			String filedName = names[1];
			Operator operator = Operator.valueOf(names[0]);

			// 创建searchFilter
			SearchFilter filter = new SearchFilter(filedName, operator, value);
			filters.put(key, filter);
		}

		return filters;
	}
}
