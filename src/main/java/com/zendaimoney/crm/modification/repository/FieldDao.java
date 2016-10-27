package com.zendaimoney.crm.modification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zendaimoney.crm.modification.entity.Field;

public interface FieldDao extends PagingAndSortingRepository<Field, Long>,
		JpaSpecificationExecutor<Field> {

	@Query("select s from Field s WHERE s.id = (SELECT MAX(q.id) FROM Field q WHERE q.fdClassCn = s.fdClassCn) and s.fdTable in (:strings)")
	List<Field> findAllByFdTabledis(@Param("strings") List<String> fdTables);

	List<Field> findAllByFdTable(String fdTable);

	Field findByFdFieldEnAndFdReserve(String string, String fdFieldEn);

	@Query("select distinct new Field(f.fdClassCn,f.fdClassEn) from Field f where f.fdTable =:table")
	List<Field> findAllFieldClass(@Param("table") String table);

	@Query("from Field f where f.fdClassEn =:fdClassEn and :customerTypeCode = bitand(:customerTypeCode,f.fdSystem)")
	List<Field> findAllFieldItemByfdClassEn(
			@Param("fdClassEn") String fdClassEn,
			@Param("customerTypeCode") Long customerTypeCode);

	@Query("from Field f where f.fdClassEn =:fdClassEn")
	List<Field> findAllFieldItemByfdClassEn(@Param("fdClassEn") String fdClassEn);

	@Query("from Field f where f.fdTable =:fdTable")
	List<Field> findAllFieldItemByFdTable(@Param("fdTable") String fdTable);
	
	//排除合同编号
	@Query("from Field f where f.fdTable =:fdTable and f.id<>236")
	List<Field> findAllFieldItemByFdTableNew(@Param("fdTable") String fdTable);
	
	//排除理财产品
	@Query("from Field f where f.fdTable =:fdTable and f.id<>93")
	List<Field> findAllFieldItemByFdTableFx(@Param("fdTable") String fdTable);
	
	//排除合同编号 理财产品
	@Query("from Field f where f.fdTable =:fdTable and f.id<>236 and f.id<>93")
	List<Field> findAllFieldItemByFdTableNewFx(@Param("fdTable") String fdTable);
	
	List<Field> findAllByFdClassCn(String fdClassCn);

	Field findByFdClassEnAndFdFieldEnAndFdReserve(String fdClassEn,
			String string, String fdReserve);

}
