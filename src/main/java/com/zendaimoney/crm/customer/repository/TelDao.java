package com.zendaimoney.crm.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zendaimoney.crm.customer.entity.Tel;

public interface TelDao extends PagingAndSortingRepository<Tel, Long> {
	/**
	 * 根据客户id获取所有的电话号码
	 * 
	 * @param customerId
	 * @return
	 */
	@Query("select  tlTelNum  from Tel c where c.customerid=? and c.tlTelType=3")
	List<String> findTelNumByCustomerId(Long customerId);

	@Query("from Tel t where t.customerid = ? and t.tlCustType = ? and t.tlTelType = ? and t.tlValid = '1' order by t.id desc")
	List<Tel> findByCustomeridAndTlCustTypeAndTlTelType(Long customerid,
			String string, String prValue);

	@Query("from Tel t where t.customerid = ? and t.tlTelType = ? and t.tlValid = '1' order by t.id desc")
	List<Tel> findByCustomeridAndTlCustTypeAndTlTelType(Long customerid,
			String prValue);

	@Query("from Tel t where t.customerid = ? and t.tlCustType = ? and t.tlTelType = ? order by t.id desc")
	List<Tel> findByCustomeridAndTlCustTypeAndTlTelTypeWithoutValid(
			Long customerid, String string, String prValue);

	@Query("from Tel t where t.customerid = ? and t.tlCustType = ? and t.tlValid = '1'  order by t.id desc")
	List<Tel> findByCustomeridAndTlCustTypeAndTlTelTypeWithoutValid(
			Long customerid, String string);

	List<Tel> findALlByCustomerid(Long id);

	@Query("from Tel t where t.customerid = ? and t.tlValid = '1' ")
	List<Tel> findALlByCustomeridAll(long customerid);

	@Query("from Tel t where t.customerid = ?")
	List<Tel> findAllTelByIdIncludeInvalid(long customerid);

	@Query("from Tel t where t.customerid = ? and t.tlTelType <>'4' and t.tlValid = '1' order by t.id desc")
	List<Tel> findALlByCustomeridDesc(Long id);

	@Query("from Tel t where t.customerid = ? and t.tlTelType <>'4' and t.tlPriority = ? and t.tlValid = '1' order by t.id desc")
	List<Tel> findByCustomeridAndTlPriority(Long customerid, String tlPriority);

	List<Tel> findByCustomeridAndTlCustType(Long id, String string);

	@Query("from Tel t where t.customerid  in (select c.id from Contactperson c where c.crmCustomer.id = ?) order by t.id desc")
	List<Tel> findAllTelByCustomerid(Long id);

	@Query("from Tel t where t.customerid = ? and t.tlCustType = ? and t.tlTelType = ? and t.tlPriority = ? ")
	List<Tel> findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
			Long customerid, String tlCustType, String tlTelType,
			String tlPriority);

	@Query("from Tel t where t.customerid = ? and t.tlPriority = ? and t.tlTelType = ?")
	List<Tel> findByCustomeridAndTlPriorityAndTlTelType(Long customerid,
			String priorityhighnum, String mobile);

	@Query("from Tel t where t.customerid = ? and t.tlPriority = ? and t.tlTelType = ? and t.tlValid = '1' ")
	List<Tel> findByCustomeridAndPriorityAndType(Long customerid,
			String priorityhighnum, String mobile);

	Tel findByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(Long objectId,
			String string, String fdReserve, String priorityhighnum);

	@Query("SELECT T FROM Tel T WHERE T.customerid NOT IN(:idList) AND (NVL2(T.tlAreaCode, T.tlAreaCode || '-', T.tlAreaCode) || T.tlTelNum || NVL2(T.tlExtCode, '-' || T.tlExtCode, T.tlExtCode)) = :telNumber")
	List<Tel> findAllByTelAndCustomerIdNotInList(
			@Param("telNumber") String telNumber,
			@Param("idList") List<Long> idList);

	@Query("select T from Tel T where T.tlTelNum = ? and T.tlCustType = ? and T.tlTelType = ? and t.tlPriority = ? and T.tlValid = '1'")
	List<Tel> findBytlTelNum(String modify, String tlCustType,
			String tlTelType, String tlPriority);

	/**
	 * 查询客户有效手机号
	 * @param customerId
	 * @return
	 */
	@Query("select c from Tel c where c.customerid=? and c.tlTelType='3' and c.tlValid = '1'  and c.tlPriority = '1'")
	List<Tel> findMobileByCustomerId(Long customerId);
	
	/**
	 * 查询客户有效座机号
	 * @param customerId
	 * @return
	 */
	@Query("select c from Tel c where c.customerid=? and c.tlTelType<>'3' and c.tlValid = '1'  and c.tlPriority = '1'")
	List<Tel> findPhoneByCustomerId(Long customerId);
	
	/**
	 * 根据有效手机号码查询总记录
	 * @param tlTelNum
	 * @return
	 */
	@Query("select c.customerid from Tel c where c.tlTelNum=? and c.tlTelType='3' and c.tlValid = '1'  and c.tlPriority = '1' and c.tlCustType ='0' ")
	List<Long> findByMobile(String mobile);
}
