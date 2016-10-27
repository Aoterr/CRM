package com.zendaimoney.crm.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.crm.BaseService;
import com.zendaimoney.crm.customer.entity.Attribute;
import com.zendaimoney.crm.customer.repository.AttributeDao;

@Component
@Transactional(readOnly = true)
public class AttributeService extends BaseService<Attribute> {

	@Autowired
	private AttributeDao attributeDao;

	/**
	 * @Title:findALlByCustomeridAndHType
	 * @Description: 根据客户id跟type搜索所有的值
	 * @param customerid
	 * @param hType
	 * @return
	 * @throws
	 * @time:2014-9-4 下午02:37:11
	 * @author:Sam.J
	 */
	public List<Attribute> findALlByCustomeridAndHType(Long customerid,
			String hType) {
		return attributeDao.findALlByCustomeridAndHType(customerid, hType);
	};

	/**
	 * @Title:deletAttribute
	 * @Description: 删除对象
	 * @param a
	 * @throws
	 * @time:2014-9-4 下午03:34:50
	 * @author:Sam.J
	 */
	public void deletAttribute(Attribute a) {
		attributeDao.delete(a);
	};

	/**
	 * @Title:saveAttribute
	 * @Description: 保存对象
	 * @param a
	 * @throws
	 * @time:2014-9-4 下午03:35:25
	 * @author:Sam.J
	 */
	public void saveAttribute(Attribute a) {
		attributeDao.save(a);
	};
}
