package com.zendaimoney.crm.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zendaimoney.crm.customer.entity.Addr;

public interface AddrDao extends PagingAndSortingRepository<Addr, Long> {

	@Query("from Addr a where a.customerid = ? and a.arCustType = ? and a.arAddrType = ? and a.arValid = '1' order by a.id desc")
	List<Addr> findByCustomeridAndArCustTypeAndArAddrType(Long customerid,
			String string, String prValue);

	List<Addr> findAllByCustomerid(Long id);

	@Query("from Addr a where a.customerid = ? and a.arValid = '1' order by a.id desc")
	List<Addr> findALlByCustomeridDesc(Long id);

	@Query("from Addr a where a.customerid = ? order by a.id desc")
	List<Addr> findAllAddrByIdIncludeInvalid(Long id);

	@Query("from Addr a where a.customerid = ? and a.arPriority = ? and a.arValid = '1' order by a.id desc")
	List<Addr> findByCustomeridAndArPriority(Long customerid, String arPriority);

	@Query("from  Addr a  where a.customerid  in (select c.id from Contactperson c where c.crmCustomer.id = ?) order by a.id desc")
	List<Addr> findAllAddrByCustomerid(Long id);

	@Query("from Addr a where a.customerid = :id and a.arAddrType = :type ")
	Addr findIssueAddress(@Param("id") Long id, @Param("type") Long type);

	@Query("from Addr a where a.customerid = ? and a.arCustType = ? and a.arAddrType = ? and a.arPriority = ? and a.arValid = '1' ")
	List<Addr> findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
			Long customerid, String arCustType, String arAddrType,
			String arPriority);

	@Query("from Addr a where a.customerid = ? and a.arPriority = ? and a.arAddrType = ? and a.arValid = '1' ")
	List<Addr> findByCustomeridPriorityType(Long customerid,
			String priorityhighnum, String mobile);

	Addr findByCustomeridAndArCustTypeAndArAddrTypeAndArPriority(Long objectId,
			String string, String fdReserve, String priorityhighnum);

	/**
	 * 根据客户id查询优先级高且有效的地址
	 * @param id
	 * @return
	 */
	@Query("from Addr a where a.customerid = ? and a.arValid = '1'  and a.arPriority = '1' order by a.id desc")
	List<Addr> findAddrByCustomerid(Long id);
}
