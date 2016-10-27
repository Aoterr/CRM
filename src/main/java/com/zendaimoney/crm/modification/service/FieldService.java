package com.zendaimoney.crm.modification.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.crm.modification.entity.Field;
import com.zendaimoney.crm.modification.repository.FieldDao;

@Service
@Transactional(readOnly = true)
public class FieldService {

	@Autowired
	private FieldDao fieldDao;

	@Transactional
	public Long saveField(Field entity) {
		return fieldDao.save(entity).getId();
	}

	public Field getFieldById(Long id) {
		return fieldDao.findOne(id);
	}

	public List<Field> getAllFields() {
		return (List<Field>) fieldDao.findAll();
	}

	public List<Field> findAllByFdTabledis(List<String> fdTables) {
		// TODO Auto-generated method stub
		return fieldDao.findAllByFdTabledis(fdTables);
	}

	public List<Field> findAllByFdTable(String fdTable) {
		// TODO Auto-generated method stub
		return fieldDao.findAllByFdTable(fdTable);
	}

	public Field findByFdFieldEnAndFdReserve(String string, String fdFieldEn) {
		// TODO Auto-generated method stub
		return fieldDao.findByFdFieldEnAndFdReserve(string, fdFieldEn);
	}

	/**
	 * 查找指定表的所有分类
	 * 
	 * @author zhanghao
	 * @date 2013-02-05 15:09:10
	 * @param table
	 *            表名
	 * @return 指定表的分类
	 */
	public List<Field> findAllFieldClass(String table) {
		return fieldDao.findAllFieldClass(table);
	}

	/**
	 * 根据类别查找变更项
	 * 
	 * @author zhanghao
	 * @date 2013-02-22 17:10:16
	 * @param fdClassEn
	 *            类别
	 * @return 变更项集合
	 */
	public List<Field> findAllFieldItemByfdClassEn(String fdClassEn) {
		return fieldDao.findAllFieldItemByfdClassEn(fdClassEn);
	}

	public List<Field> findAllFieldItemByfdClassEn(String fdClassEn,
			Long customerTypeCode) {
		return fieldDao
				.findAllFieldItemByfdClassEn(fdClassEn, customerTypeCode);
	}

	public List<Field> findAllFieldItemByFdTable(String fdTable) {
		return fieldDao.findAllFieldItemByFdTable(fdTable);
	}
	//排除理财字段中的合同编号
	public List<Field> findAllFieldItemByFdTableNew(String fdTable) {
		return fieldDao.findAllFieldItemByFdTableNew(fdTable);
	}
	
	//排除理财产品
	public List<Field> findAllFieldItemByFdTableFx(String fdTable) {
		return fieldDao.findAllFieldItemByFdTableFx(fdTable);
	}
	//排除理财字段中的合同编号  理财产品
	public List<Field> findAllFieldItemByFdTableNewFx(String fdTable) {
		return fieldDao.findAllFieldItemByFdTableNewFx(fdTable);
	}

	public List<Field> findAllByFdClassCn(String fdClassCn) {
		// TODO Auto-generated method stub
		return fieldDao.findAllByFdClassCn(fdClassCn);
	}

	public Field findByFdClassEnAndFdFieldEnAndFdReserve(String fdClassEn,
			String string, String fdReserve) {
		// TODO Auto-generated method stub
		return fieldDao.findByFdClassEnAndFdFieldEnAndFdReserve(fdClassEn,
				string, fdReserve);
	}


}
