package com.zendaimoney.crm.customer.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zendaimoney.crm.customer.entity.Customer;

public interface CustomerDao extends
		PagingAndSortingRepository<Customer, Long>,
		JpaSpecificationExecutor<Customer> {
	@Query("select crName from Customer c where c.id=?")
	public String getCustomerName(Long id);

	/**
	 * 基本信息表中是否有该身份证
	 * 
	 * @param idNum
	 * @return
	 */
	@Query("select new Customer(id,crGather) from Customer c where c.crIdtype=1 and c.crIdnum=?")
	public List<Customer> findByIdNum(String idNum);

	/**
	 * 根据id判断是否是新老客户。cr_customer_type=3为老客户 cr_customer_type=2为客户。即是否有业务
	 * 
	 * @param id
	 * @return
	 */
	@Query("select id from Customer c where (c.crCustomerType=2 or c.crCustomerType=3) and c.id=?")
	public Long findBusinessByCustomerId(Long id);

	// @Query("from Customer c where c.crName like '%" + name + "'%")
	public List<Customer> findByCrNameContaining(String name);

	public List<Customer> findAllByCrIdtypeAndCrIdnum(String idType,
			String idNum);

	/**
	 * @Title:根据客户申请书编号查找客户
	 * @Description: TODO
	 * @param crApplicationNumber
	 * @return
	 * @throws
	 * @time:2015-1-7 上午10:34:57
	 * @author:Sam.J
	 */
	public List<Customer> findAllByCrApplicationNumber(
			String crApplicationNumber);

	@Query("from Customer c where c.id =?")
	public List<Customer> getCustomerList(Long id);

	@Query(" from Customer c where c.crName in (:ids)")
	public List<Customer> findAllCustomerById(@Param("ids") String ids);

	public List<Customer> findByIdIn(List<Long> ids);

	@Query(" from Customer c where c.crInputDate> ? and c.crInputDate< ? and c.crCityId = ?")
	public List<Customer> findCustomerAllByInputDateAndCityId(
			Timestamp t_today, Timestamp t_tomorrow, Long cityId);

	@Query("select count(*) from Customer c where c.crInputDate> ? and c.crInputDate< ? and c.crCityId is null")
	public Long findCustomerAllByInputDateMx(Timestamp t_today,
			Timestamp t_tomorrow);

	@Query(" from Customer c where c.crCompany = :searchValue  ")
	// or id in (select p.crmCustomer.id from Contactperson p where p.cpWorkunit
	// = :searchValue )
	public List<Customer> findAllByCrCompanyM(
			@Param("searchValue") String searchValue);

	@Query(" from Customer c where c.crName = :searchValue ")
	// or id in (select p.crmCustomer.id from Contactperson p where p.cpName =
	// :searchValue )
	public List<Customer> findAllByCrNameM(
			@Param("searchValue") String searchValue);

	@Query(" from Customer c where c.crHouse = ?")
	public List<Customer> findAllByCrHouseM(String searchValue);

	public List<Customer> findAllByCrIdnum(String searchValue);

	public List<Customer> findAllByCrCustomerNumber(String searchValue);

	public List<Customer> findAllByCrGather(Long crGatherId);

	// 根据客户ID查询客户编号
	@Query("select crCustomerNumber from Customer where id = ?")
	public String getCustomerNumber(Long customerId);

	@Lock(LockModeType.READ)
	@Modifying
	@Query("update Customer cr set cr.crCustomerNumber ="
			+ "(select concat(to_char(sysdate,'yyyyMMdd'),lpad(count(*)+1,6,'0')) from Customer cr1 "
			+ "where trunc(sysdate,'dd')=trunc(cr1.crInputDate ,'dd')) "
			+ "where id = :Id ")
	public int updateCustomerNumberById(@Param("Id") Long Id);

	@Query(" from Customer c where c.crName like ?")
	public List<Customer> findAllByCrName(String searchValue);

	@Query(" from Customer c where lower(c.crEmail) = ?")
	public List<Customer> findAllByCrEmail(String searchValue);

	@Query(" from Customer c where c.crInputDate> ? and c.crInputDate< ?")
	public List<Customer> findCustomerAllByInputDate(Timestamp t_today,
			Timestamp t_tomorrow);

	@Query("from Customer c where c.crIdtype=? and c.crIdnum=? and c.crName=?")
	public Customer findAllByCrIdtypeAndCrIdnumAndCrName(String idType,
			String idNum, String crName);

	@Query("from Customer c where c.crIdtype=? and upper(c.crIdnum)=?")
	public Customer findAllByCrIdTypeAndCrIdnum(String idType, String idNum);

	@Query("from Customer c where c.crState=1 and c.id=?")
	public Customer findAllById(Long id);

	//根据参数查询客户列表
//	public List<Customer> getCustomerList(Map<String, Object> requestMap);
	
	//根据客户编号查询客户
	@Query("from Customer c where c.crCustomerNumber = ? and c.crState=1")
	public List<Customer> findByCrCustomerNumber(String crCustomerNumber);
	
	@Query("from Customer c where c.crIdtype=? and upper(c.crIdnum)=?")
	public List<Customer> findAllCusByCrIdTypeAndCrIdnum(String idType, String idNum);
	
	@Query("from Customer c where  upper(c.crIdnum)=?")
	public List<Customer> findAllCusByCrIdnum(String idNum);
	
}
