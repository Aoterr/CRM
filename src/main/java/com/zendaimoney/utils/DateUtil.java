package com.zendaimoney.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*
 * 时间日期计算
 * */
public class DateUtil {
	// 一天的毫秒数 60*60*1000*24
	public final static long DAY_MILLIS = 86400000;

	// 一小时的毫秒数 60*60*1000
	private final static long HOUR_MILLIS = 3600000;

	// 一分钟的毫秒数 60*1000
	private final static long MINUTE_MILLIS = 60000;	
	
	//随机数个数
	private final static int RANDOM_COUNT = 3;
	
	public static String format(Date date, String pattern) {
		if (null != date) {
			return org.apache.commons.lang3.time.DateFormatUtils.format(date, pattern);
		}
		return null;
	}
	
	public static Date parse(String date, String pattern) {
		if (null != date) {
			try {
				return org.apache.commons.lang3.time.DateUtils.parseDate(date, pattern);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static Date parse(Date date, String pattern){
		if (null != date) {
			
			try {
				return org.apache.commons.lang3.time.DateUtils.parseDate(format(date, "yyyy-MM-dd"), pattern);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static Date getDaysDate(Date date,Integer days) {
		date.setDate(date.getDate() + (days));
		return date;
	}
	
	
	/**
	 * 计算两个日期相差天数 bengin >=end 返回0  bengin < end返回相差天数 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int countDays(String begin,String end){
		  int days = 0;
		  
		  DateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
		  Calendar beginDate = Calendar.getInstance();
		  Calendar endDate = Calendar.getInstance();
		  
		  try{
			  beginDate.setTime(dateFormate.parse(begin));
			  endDate.setTime(dateFormate.parse(end));
		   
		   while(beginDate.before(endDate)){
		    days++;
		    beginDate.add(Calendar.DAY_OF_YEAR, 1);
		   }
		   
		  }catch(ParseException pe){
		   //System.out.println("日期格式必须为：yyyy-MM-dd；如：2010-4-4.");
		  }
		  
		  return days; 
		} 
	

	/**
	 * 计算两个日期之差的间隔天数,开始时间必须大于结束时间。
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getDays(Date start, Date end) {
		if (start.getTime() > end.getTime()) {
			throw new RuntimeException("开始时间必须大于结束时间!");
		}
		long temp = end.getTime() - start.getTime();
		return (int) (temp / DAY_MILLIS);
	}

	/**
	 * 计算两个日期相差的天、小时、分钟
	 * 
	 * @param start
	 * @param end
	 */
	public static String show(Date start, Date end) {
		long temp = end.getTime() - start.getTime();
		String leavingTime = temp / DAY_MILLIS + "天" + (temp % DAY_MILLIS)
				/ HOUR_MILLIS + "小时" + ((temp % DAY_MILLIS) % HOUR_MILLIS)
				/ MINUTE_MILLIS + "分";
		return leavingTime;
	}

	/**
	 * 计算当前24小时前的时间
	 * 
	 */
	public static Date getDateBeforeOneDay() {
		// 当前时间
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 24小时之前的时间
		currentTime.setDate(currentTime.getDate() - 1);
		String strDate = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(strDate, pos);
	}

	/**
	 * 计算当前时间
	 * 
	 */
	public static Date getCurrentDate() {
		// 当前时间
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(strDate, pos);
	}
	/**
	 * 定自义格式当前日期
	 * @param format
	 * @return
	 */
	public static String getCurrentDate(String format){
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(Calendar.getInstance().getTime());
	}

	/**
	 * 计算的7天后时间
	 * 
	 */
	public static Date getSevenDaysDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date.setDate(date.getDate() + 7);
		String strDate = formatter.format(date);
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(strDate, pos);
	}

	/**
	 * 计算N个月后的日期
	 * @param date
	 * @param par
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Date getAfterMonth(Date date,int par) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		date.setMonth(date.getMonth() + par);
		String strDate = formatter.format(date);
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(strDate, pos);
	}

	/**
	 * 获得yyyy/MM/dd日期格式
	 * 
	 */
	public static Date getDateyyyyMMdd() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String d = formatter.format(date);
		Date r = null;
		try {
			r = formatter.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return r;
	}
	
	/**
	 * 获得yyyy/MM/dd日期格式
	 * 
	 */
	public static Date setDateyyyyMMDD(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String d = formatter.format(date);
		Date r = null;
		try {
			r = formatter.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return r;
	}

   /**
    * 获取时间,年月日格式（yyyy-MM-dd）
    */
   //获取时间（yyyy-MM-dd）    
   public static String getYMDTime(Date date){   
	   String dateformat = "yyyy-MM-dd";  
	   SimpleDateFormat   dateFormat   =   new   SimpleDateFormat(dateformat);//可以方便地修改日期格式    
       return dateFormat.format(date);
   }
   
   /**
    * 获取时间,时分秒格式（HH:mm:ss）
    */
   //获取时间（HH:mm:ss）    
   public static String getHMSTime(Date date){   
	   String dateformat = "HH:mm:ss";  
	   SimpleDateFormat   dateFormat   =   new   SimpleDateFormat(dateformat);//可以方便地修改日期格式    
       return dateFormat.format(date);
   }
   
   
   /**
    * 生成交易流水号前17位
    */
   public static String getTransactionSerialNumber(String flowSeq){
	   String dateformat = "yyyyMMddHHmmssSSS";
	   SimpleDateFormat   dateFormat   =   new   SimpleDateFormat(dateformat);
	   if(flowSeq.length()<3){
		   for (int i = 0; i <= 3-flowSeq.length(); i++) {
			   flowSeq = "0".concat(flowSeq);
		}
	   }
	   return dateFormat.format(System.currentTimeMillis()).concat(flowSeq);
   }
   
   /**
    * 获得字符串格式的日期
    * @param date
    * @return
    */
   public static String getStrDate(Date date){
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
	   return sdf.format(date);
   }
   
   
   /**
    * 获取指定日期N天之后的日期
    *  
    * @author zhanghao
    * @date 2013-5-22 下午01:54:41
    * @param date 指定的日期
    * @param afterDays N天
    * @return
    */
   public static String getAfterDay(Date date,int afterDays){
       Calendar calendar = Calendar.getInstance();
       calendar.setTime(date);
       calendar.add(Calendar.DATE, afterDays);
       return format(calendar.getTime(), "yyyy-MM-dd");
   }
   
   /**
    * 获取当前日期N天之后的日期
    *  
    * @author zhanghao
    * @date 2013-5-22 下午01:57:28
    * @param afterDays N天
    * @return
    */
   public static String getCurrDateAfterDay(int afterDays){
	   return getAfterDay(new Date(), afterDays);
   }
   
   /**
    * 获取首页列表的时间列表
    *  
    * @author Yuan Changchun
    * @date 2013-10-25 下午03:20:37
    * @param monthNum
    * @return
    */
   public static List<Date> getRecentNumMonDateList(Integer monthNum) {
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		currentDate = calendar.getTime();
		List<Date> resultList = new ArrayList<Date>();
		for (int i = 0; i < monthNum; i++) {
			resultList.add(currentDate);
			currentDate = getPrevMonthDate(currentDate);
		}
		return resultList;
	}
	
	private static Date getPrevMonthDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		return calendar.getTime();
	}
}

