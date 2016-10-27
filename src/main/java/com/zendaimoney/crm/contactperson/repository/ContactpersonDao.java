package com.zendaimoney.crm.contactperson.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zendaimoney.crm.contactperson.entiy.Contactperson;
import com.zendaimoney.crm.customer.entity.Customer;

public interface ContactpersonDao extends
		PagingAndSortingRepository<Contactperson, Long> {

	@Query("from Contactperson c where c.crmCustomer.id = ? and cpState=1")
	List<Contactperson> findByCustomerid(Long id);

	/**
	 * 取当前客户所有的有效联系人
	 * 
	 * @param id
	 *            客户id
	 * @return
	 */
	@Query("from Contactperson c where c.crmCustomer.id = ? and cpState = 1")
	List<Contactperson> findAllContactPersonByCustId(Long id);

	@Query("from Contactperson c where c.id.id = ?")
	Contactperson findByid(Long id);

	@Query("from Contactperson c where c.crmCustomer = ? order by c.id desc")
	List<Contactperson> findByCrmCustomer(Customer crmCustomer);

	@Query("from Contactperson c where c.crmCustomer = ? order by c.cpInputTime,c.id ")
	List<Contactperson> findContactperson(Customer crmCustomer);

	@Query("from Contactperson c where c.crmCustomer = ? and c.cpRelation = ? order by c.id desc")
	List<Contactperson> findAllByCrmCustomerAndCpRelation(Customer customer,
			String relation);

	@Query("from Contactperson c where c.crmCustomer.id = ? order by c.id desc")
	List<Contactperson> findByEmergencyContact(Long id);

	@Query("from Contactperson c where c.crmCustomer = ? and c.cpRelation in (3,4,5) order by c.id desc")
	List<Contactperson> findAllByCrmCustomerAndCpRelationWithZx(
			Customer customer);

	@Query("from Contactperson c where  c.cpWorkunit = :searchValue ")
	List<Contactperson> findAllByCompanyM(
			@Param("searchValue") String searchValue);

	@Query("from Contactperson c where  c.cpName = :searchValue ")
	List<Contactperson> findAllByNameM(@Param("searchValue") String searchValue);

	/**
	 * @Title: findAllByCrmCustomerAndCpContactpersonType
	 * @Description: 按照客户跟联系人类型查找
	 * @return List<Contactperson> 返回类型
	 * @author Sam.J
	 * @date 2015-5-22 下午02:41:41
	 * @throws
	 */
	@Query("from Contactperson c where c.crmCustomer = ? and c.cpContactpersonType = ? order by c.id desc")
	List<Contactperson> findAllByCrmCustomerAndCpContactpersonType(
			Customer customer, String cpContactpersonType);
}
