package com.zendaimoney.utils.helper;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;

public class SequenceHelper {
	
	private static final Logger logger = Logger.getLogger(SequenceHelper.class);
	
	private static DataSource dataSource = SpringContextHelper.getBean("dataSource");
	
	private static final String create_sequence_sql = "CREATE SEQUENCE %S MINVALUE 1 MAXVALUE 9999999999999999999999999999 START WITH 1 INCREMENT BY 1 CACHE 20";
	
	private static final String drop_sequence_sql = "DROP SEQUENCE %S";
	
	public static final String customer_num_seq = "SEQ_CUSTOMER_NUM";
	
	/**
	 * 	获取序列的值
	 *  
	 * @param seq_name 序列名称
	 * @return
	 */
	public static Long getSequenceNextValue(String seq_name )
	{
		if(StringUtils.isBlank(seq_name)) {
			logger.warn("获取序列值失败,序列名不能为空!"); 
			return null;
		}
		OracleSequenceMaxValueIncrementer incrementer = new OracleSequenceMaxValueIncrementer(dataSource,seq_name);
		return incrementer.nextLongValue();
	}
	
	/**
	 * 获取客户流水号
	 *  
	 * @return
	 */
	public static String getCustomerFlowNumber(){
		return new SimpleDateFormat("yyyyMMdd").format(new Date()) + String.format("%06d", getSequenceNextValue(customer_num_seq));
	}
	
	
	/**
	 * 创建序列
	 *  
	 * @param sequenceName
	 */
	public static void createSequence(String sequenceName)
	{
		if(StringUtils.isBlank(sequenceName)) {
			logger.warn("创建序列失败,序列名不能为空!");
		}
		execute(String.format(create_sequence_sql, sequenceName));
	}
	
	/**
	 * 删除序列
	 *  
	 * @param sequenceName
	 */
	public static void dropSequence(String sequenceName)
	{
		if(StringUtils.isBlank(sequenceName)) {
			logger.warn("删除序列失败,序列名不能为空!");
		}
		execute(String.format(drop_sequence_sql, sequenceName));
	}
	
	/**
	 * 执行SQL语句
	 *  
	 */
	public static boolean execute(String sql)
	{
		boolean result = false;
		Connection connection = getConnection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql);
			result = statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warn("执行SQL出错:" + e.getMessage());
		}finally{
			try {
				closeConnection(connection, statement);
			} catch (SQLException e) {
				logger.warn("关闭连接出错:" + e.getMessage());
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 关闭连接
	 *  
	 */
	public static void closeConnection(Connection connection, Statement statement) throws SQLException
	{
		if(statement != null){
			statement.close();
		}
		if(connection != null){
			connection.close();
		}
	}
	
	/**
	 * 获得数据库连接对象
	 *  
	 * @return
	 */
	public static Connection getConnection(){
		Connection connectio = null;
		try {
			connectio =  dataSource.getConnection();
		} catch (SQLException e) {
			logger.warn("获取connection连接出错:" + e.getMessage());
			e.printStackTrace();
		}
		return connectio;
	}
	

}
