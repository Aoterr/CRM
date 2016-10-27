package com.zendaimoney.sys.parameter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zendaimoney.sys.parameter.entity.Parameter;

public interface ParameterDao extends PagingAndSortingRepository<Parameter, Long>, JpaSpecificationExecutor<Parameter>{

	List<Parameter> findAllByPrType(String prType);//通过参数类型查询参数列表
	
	@Query("select s from Parameter s order by id")
	List<Parameter> findAllOrderId();
	
	@Query("select s from Parameter s WHERE s.id = ?")
	Parameter getParameter(long id);
	
	@Query("select s from Parameter s WHERE s.id = (SELECT MAX(q.id) FROM Parameter q WHERE q.prTypename = s.prTypename)")
	List<Parameter> getAllParameterDisTypeName();
	
	@Query("select distinct s from Parameter s WHERE s.id = (SELECT MAX(q.id) FROM Parameter q WHERE q.prTypename = s.prTypename)")
	List<Parameter> getAllParameterTypeName();
	
	@Query("select s.prValue from Parameter s WHERE s.id = (SELECT MAX(q.id) FROM Parameter q WHERE q.prType = ?)")
	String findMaxPrValue(String prType);
	
	@Query("select s.id from Parameter s WHERE s.prName like ?")
	List<Long> findId(String prTypename);
	
	@Query("select count(*) from Parameter where prTypename= :id or id = :id")
	Long delCount(@Param("id")String id);
	
	@Query("select MAX(cast(s.prValue as int)) from Parameter s where s.prTypename= ? AND s.prType= ?")
	Integer MaxPrvalue(String prTypename,String prType);
	
	@Query("select s from Parameter s where s.prType= ? AND s.prTypename= ? AND s.prState = ?")
	List<Parameter> findParameterByPrTypeAndprTypename(String prType,String prTypename,String prState);
	
	@Query("select s from Parameter s where s.prType= ? AND s.prValue= ?")
    Parameter findByPrTypeAndPrValue(String prType,String prValue);

}
