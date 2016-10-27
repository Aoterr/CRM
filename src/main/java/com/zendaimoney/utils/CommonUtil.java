package com.zendaimoney.utils;

import java.math.BigDecimal;
import java.util.List;

public class CommonUtil {
	public static String listToString(List<Long> stringList) {
		if (stringList == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (Long string : stringList) {
			if (flag) {
				result.append(",");
			} else {
				flag = true;
			}
			result.append(string);
		}
		return result.toString();
	}

	/**
	 * 格式化编号
	 * 
	 * @author zhanghao
	 * @date 2013-01-09 14:28:53
	 * @param oldNumber
	 * @return
	 */
	public static String formatNumber(Long oldNumber) {
		String number = "";
		if (oldNumber > 100) {
			number = String.valueOf(oldNumber);
		} else if (oldNumber > 10) {
			number = "0" + String.valueOf(oldNumber);
		} else {
			number = "00" + String.valueOf(oldNumber);
		}
		return number;
	}
	
	 /**
	   * 提供精确的小数位四舍五入处理
	   * @author Yuan Changchun
	   * @date 2013-06-14 16:18:20
	   * @param v 需要四舍五入的数字
	   * @param scale 小数点后保留几位
	   * @param round_mode 指定的舍入模式
	   * @return 四舍五入后的结果，以字符串格式返回
	   */

	public static String round(String v, int scale, int round_mode) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		if(!isDouble(v)){
			throw new IllegalArgumentException("非法的格式化金额!金额值:" + v);
		}
		BigDecimal b = new BigDecimal(v);
		return b.setScale(scale, round_mode).toString();
	}
	
	/**
     * 验证是否为小数
     * @param val
     * @return
     */
    public static  boolean  isDouble(String val){
        return val.matches("(\\d+)(.(\\d+))?");
    }

}
