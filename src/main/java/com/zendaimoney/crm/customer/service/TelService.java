package com.zendaimoney.crm.customer.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.constant.ParamConstant;
import com.zendaimoney.crm.customer.entity.Tel;
import com.zendaimoney.crm.customer.repository.TelDao;
import com.zendaimoney.uc.rmi.vo.Staff;

@Component
@Transactional(readOnly = true)
public class TelService {

	@Autowired
	private TelDao telDao;

	public Tel getTel(Long id) {
		return telDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public void saveOrUpTelListByCus_id(Long customerId, List<Tel> telList,Long sysuserId) {
		if (telList.isEmpty() || telList == null) {
			return;
		}
		//Staff staff = AuthorityHelper.getStaff();
		for (Tel entity : telList) {
			if (entity != null) {
				entity.setTlInputId(sysuserId);
				entity.setTlInputDate(new Date());
				entity.setTlModifyId(sysuserId);
				entity.setTlModifyTime(new Date());
				entity.setCustomerid(customerId);
				if (entity.getId() == null) {
					if (entity.getTlPriority() == null) {
						entity.setTlPriority(ParamConstant.priorityOrNum);
					}
					telDao.save(entity);
				} else {
					Tel tel = this.getTel(entity.getId());
					BeanUtils.copyProperties(entity, tel);
					if (tel.getTlPriority() == null) {
						tel.setTlPriority(ParamConstant.priorityHighNum);
					}
					telDao.save(tel);
				}
			}
		}
	}

	@Transactional(readOnly = false)
	public Tel saveTel(Tel entity,Long sysuserId) {
		Date now = new Date();
		//Staff staff = AuthorityHelper.getStaff();
		String tlPriority = null;
		if (entity.getTlPriority() != null) {
			tlPriority = entity.getTlPriority();
		}
		if (entity.getTlPriority() == null) {
			List<Tel> list =telDao.findByCustomeridAndTlCustTypeAndTlTelType
					(entity.getCustomerid(),"0",entity.getTlTelType());//  0是客户  1是联系人
			if(list.size()<1){
				entity.setTlPriority(ParamConstant.priorityHighNum);
			}else{
				entity.setTlPriority(ParamConstant.priorityOrNum);
			}
		} else if (entity.getTlPriority().equals(ParamConstant.priorityHighNum)) {
			List<Tel> tels = this
					.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
							entity.getCustomerid(), entity.getTlCustType(),
							entity.getTlTelType(),
							ParamConstant.priorityHighNum);
			for (Tel tel : tels) {
				tel.setTlPriority(ParamConstant.priorityOrNum);
				telDao.save(tel);
			}
		}
		if (entity.getId() == null) {
			if (entity.getTlPriority() == null) {
				List<Tel> list =telDao.findByCustomeridAndTlCustTypeAndTlTelType
						(entity.getCustomerid(),"0",entity.getTlTelType());//  0是客户  1是联系人
				if(list.size()<1){
					entity.setTlPriority(ParamConstant.priorityHighNum);
				}else{
					entity.setTlPriority("2");
				}
			}
			entity.setTlInputId(sysuserId);
			entity.setTlInputDate(now);
			entity.setTlModifyId(sysuserId);
			entity.setTlModifyTime(now);
			return telDao.save(entity);
		} else {
			Tel tel = this.getTel(entity.getId());
			tel.setTlTelType(entity.getTlTelType());
			tel.setTlAreaCode(entity.getTlAreaCode());
			tel.setTlTelNum(entity.getTlTelNum());
			tel.setTlExtCode(entity.getTlExtCode());
			if (tlPriority != null)
				tel.setTlPriority(tlPriority);
			tel.setTlValid(entity.getTlValid());
			tel.setTlModifyId(sysuserId);
			tel.setTlModifyTime(now);
			return telDao.save(tel);
		}
	}

	public List<Tel> findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
			Long customerid, String tlCustType, String tlTelType,
			String priorityhighnum) {
		// TODO Auto-generated method stub
		return telDao
				.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
						customerid, tlCustType, tlTelType, priorityhighnum);
	}

	@Transactional(readOnly = false)
	public void deleteTel(Long id) {
		telDao.delete(id);
	}

	@Transactional(readOnly = false)
	public void deleteTel(Long[] ids) {
		for (Long id : ids) {
			telDao.delete(id);
		}
	}

	public List<Tel> getAllTel() {
		return (List<Tel>) telDao.findAll();
	}

	public Page<Tel> getTel(PageRequest pageRequest) {
		return telDao.findAll(pageRequest);
	}

	public List<Tel> findByCustomeridAndTlCustTypeAndTlTelType(Long customerid,
			String string, String prValue) {
		// TODO Auto-generated method stub
		return telDao.findByCustomeridAndTlCustTypeAndTlTelType(customerid,
				string, prValue);
	}

	public List<Tel> findByCustomeridAndTelType(Long customerid, String prValue) {
		// TODO Auto-generated method stub
		return telDao.findByCustomeridAndTlCustTypeAndTlTelType(customerid,
				prValue);
	}

	public List<Tel> findByCustomeridAndTlCustTypeAndTlTelTypeWithoutValid(
			Long customerid, String string, String prValue) {
		// TODO Auto-generated method stub
		return telDao.findByCustomeridAndTlCustTypeAndTlTelTypeWithoutValid(
				customerid, string, prValue);
	}

	public List<Tel> findByCustomeridAndTlCustTypeAndTlTelTypeWithoutValid(
			Long customerid, String string) {
		// TODO Auto-generated method stub
		return telDao.findByCustomeridAndTlCustTypeAndTlTelTypeWithoutValid(
				customerid, string);
	}

	public List<Tel> findALlByCustomerid(Long id) {
		// TODO Auto-generated method stub
		return telDao.findALlByCustomerid(id);
	}

	public List<Tel> findByCustomeridAndTlPriority(Long customerid,
			String prValue) {
		// TODO Auto-generated method stub
		return telDao.findByCustomeridAndTlPriority(customerid, prValue);
	}

	public List<Tel> findALlByCustomeridDesc(Long customerid) {
		// TODO Auto-generated method stub
		return telDao.findALlByCustomeridDesc(customerid);
	}

	public List<Tel> findByCustomeridAndTlCustType(Long id, String string) {
		// TODO Auto-generated method stub
		return telDao.findByCustomeridAndTlCustType(id, string);
	}

	public List<Tel> findAllTelByCustomerid(Long id) {
		// TODO Auto-generated method stub
		List<Tel> list1 = telDao.findALlByCustomerid(id);
		List<Tel> list2 = telDao.findAllTelByCustomerid(id);
		list1.addAll(list2);
		return list1;
	}

	public List<Tel> findByCustomeridAndTlPriorityAndTlTelType(Long customerid,
			String priorityhighnum, String mobile) {
		// TODO Auto-generated method stub
		return telDao.findByCustomeridAndTlPriorityAndTlTelType(customerid,
				priorityhighnum, mobile);
	}

	public Tel findByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
			Long objectId, String string, String fdReserve,
			String priorityhighnum) {
		// TODO Auto-generated method stub
		return telDao.findByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
				objectId, string, fdReserve, priorityhighnum);
	}

	public Boolean highPriority(long id, String type) {
		List<Tel> telList = telDao.findByCustomeridAndPriorityAndType(id, "1",
				type);
		if (telList.size() > 0)
			return false;
		else
			return true;
	}
	
	public List<Long> findByMobile(String mobile){
		return telDao.findByMobile(mobile);		
	}

}
