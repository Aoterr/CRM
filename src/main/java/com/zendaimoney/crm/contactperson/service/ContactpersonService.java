package com.zendaimoney.crm.contactperson.service;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.constant.ParamConstant;
import com.zendaimoney.crm.contactperson.entiy.Contactperson;
import com.zendaimoney.crm.contactperson.repository.ContactpersonDao;
import com.zendaimoney.crm.customer.entity.Addr;
import com.zendaimoney.crm.customer.entity.Customer;
import com.zendaimoney.crm.customer.entity.Tel;
import com.zendaimoney.crm.customer.service.AddrService;
import com.zendaimoney.crm.customer.service.CustomerService;
import com.zendaimoney.crm.customer.service.TelService;
import com.zendaimoney.uc.rmi.vo.Staff;

@Component
@Transactional(readOnly = true)
public class ContactpersonService {
	@Autowired
	private ContactpersonDao contactpersonDao;
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private TelService telService;

	@Autowired
	private AddrService addrService;

	public Contactperson getContactperson(Long id) {
		return contactpersonDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public Contactperson saveContactperson(Contactperson entity) {
		Date now = new Date();
		//Staff staff = AuthorityHelper.getStaff();
		if (entity.getId() == null) {
			//entity.setCpInputId(staff.getId());
			entity.setCpInputTime(now);
			//entity.setCpModifyId(staff.getId());
			entity.setCpModifyTime(now);
			entity.setCpState("1"); // 有效性
			return contactpersonDao.save(entity);
		} else {
			Contactperson oldContactperson = this.getContactperson(entity
					.getId());
			Contactperson contactperson = new Contactperson();
			BeanUtils.copyProperties(oldContactperson, contactperson);
			contactperson.setCpName(entity.getCpName());
			contactperson.setCpEname(entity.getCpEname());
			contactperson.setCpSex(entity.getCpSex());
			contactperson.setCpBirthday(entity.getCpBirthday());
			contactperson.setCpRelation(entity.getCpRelation());
			contactperson.setCpIdtype(entity.getCpIdtype());
			contactperson.setCpId(entity.getCpId());
			contactperson.setCpWorkunit(entity.getCpWorkunit());
			contactperson.setCpOccupation(entity.getCpOccupation());
			contactperson.setCpPost(entity.getCpPost());
			contactperson.setCpDepart(entity.getCpDepart());
			contactperson.setCpQq(entity.getCpQq());
			contactperson.setCpMsn(entity.getCpMsn());
			contactperson.setCpEmail(entity.getCpEmail());
			contactperson.setCpMemo(entity.getCpMemo());
			contactperson.setCpContactpersonType(entity
					.getCpContactpersonType());
			contactperson.setCrmCustomer(entity.getCrmCustomer());
			//contactperson.setCpModifyId(staff.getId());
			contactperson.setCpModifyTime(now);
			contactperson.setCpState("1"); // 有效性
			contactperson.setOptlock(entity.getOptlock());
			return contactpersonDao.save(contactperson);
		}
	}

	@Transactional(readOnly = false)
	public Contactperson saveOrUpdateContactperson(Long customerid,
			Contactperson entity) {
		Customer customer = customerService.getCustomer(customerid);
		entity.setCrmCustomer(customer);
		Date now = new Date();
		//Staff staff = AuthorityHelper.getStaff();
		if (entity.getId() == null) {
			//entity.setCpInputId(staff.getId());
			entity.setCpInputTime(now);
			//entity.setCpModifyId(staff.getId());
			entity.setCpModifyTime(now);
			entity.setCpState("1"); // 有效性
			return contactpersonDao.save(entity);
		} else {
			Contactperson oldContactperson = this.getContactperson(entity
					.getId());
			Contactperson contactperson = new Contactperson();
			BeanUtils.copyProperties(oldContactperson, contactperson);
			contactperson.setCpName(entity.getCpName());
			contactperson.setCpEname(entity.getCpEname());
			contactperson.setCpSex(entity.getCpSex());
			contactperson.setCpBirthday(entity.getCpBirthday());
			contactperson.setCpRelation(entity.getCpRelation());
			contactperson.setCpIdtype(entity.getCpIdtype());
			contactperson.setCpId(entity.getCpId());
			contactperson.setCpWorkunit(entity.getCpWorkunit());
			contactperson.setCpOccupation(entity.getCpOccupation());
			contactperson.setCpPost(entity.getCpPost());
			contactperson.setCpDepart(entity.getCpDepart());
			contactperson.setCpQq(entity.getCpQq());
			contactperson.setCpMsn(entity.getCpMsn());
			contactperson.setCpEmail(entity.getCpEmail());
			contactperson.setCpMemo(entity.getCpMemo());
			contactperson.setCpContactpersonType(entity
					.getCpContactpersonType());
			contactperson.setCrmCustomer(entity.getCrmCustomer());
			//contactperson.setCpModifyId(staff.getId());
			contactperson.setCpModifyTime(now);
			contactperson.setCpState("1"); // 有效性
			contactperson.setOptlock(entity.getOptlock());
			return contactpersonDao.save(contactperson);
		}
	}

	@Transactional(readOnly = false)
	public void deleteContactperson(Long id) {
		List<Tel> cpTelList = telService.findALlByCustomerid(id);
		for (Tel tel : cpTelList) {
			telService.deleteTel(tel.getId());
		}
		List<Addr> cpAddrList = addrService.findAllByCustomerid(id);
		for (Addr addr : cpAddrList) {
			addrService.deleteAddr(addr.getId());
		}
		contactpersonDao.delete(id);
	}

	@Transactional(readOnly = false)
	public void deleteContactperson(Long[] ids) {
		for (Long id : ids) {
			List<Tel> cpTelList = telService.findALlByCustomerid(id);
			for (Tel tel : cpTelList) {
				telService.deleteTel(tel.getId());
			}
			List<Addr> cpAddrList = addrService.findAllByCustomerid(id);
			for (Addr addr : cpAddrList) {
				addrService.deleteAddr(addr.getId());
			}
			contactpersonDao.delete(id);
		}
	}

	public List<Contactperson> getAllContactperson() {
		return (List<Contactperson>) contactpersonDao.findAll();
	}

	public Page<Contactperson> getContactperson(PageRequest pageRequest) {
		return contactpersonDao.findAll(pageRequest);
	}

	public List<Contactperson> findALlByCustomerid(Long id) {
		return contactpersonDao.findByCustomerid(id);
	}

	public Contactperson findByid(Long id) {
		return contactpersonDao.findByid(id);
	}

	public List<Contactperson> findAllByCrmCustomer(Customer crmCustomer) {
		// TODO Auto-generated method stub
		return contactpersonDao.findByCrmCustomer(crmCustomer);
	}

	public List<Contactperson> findContactperson(Customer crmCustomer) {
		return contactpersonDao.findContactperson(crmCustomer);
	}

	@Transactional(readOnly = false)
	public void saveContactpersonAndTelAndAddr(Contactperson contactperson,
			Long customerid, String jsonString,Long sysuserId) throws Exception {
		// TODO Auto-generated method stub
		Customer customer = customerService.getCustomer(customerid);
		contactperson.setCrmCustomer(customer);
		// ------增加判断 该用户是否之前存在紧急联系人，如不存在，则将该联系人设置为紧急联系人 add by Sam.J 15.5.22
		List<Contactperson> cpList = contactpersonDao
				.findAllByCrmCustomerAndCpContactpersonType(customer,
						ParamConstant.ContactpersonType_JINJI);
		if (cpList.size() == 0) {
			contactperson
					.setCpContactpersonType(ParamConstant.ContactpersonType_JINJI);
		}
		// ------end
		Contactperson entity = this.saveContactperson(contactperson);
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Object insertTels = jsonObject.get("insertTel");
		JSONArray insertTelsjsonArray = JSONArray.fromObject(insertTels);
		List<Tel> insertTelList = insertTelsjsonArray.toList(
				insertTelsjsonArray, Tel.class);
		if (insertTelList.size() != 0) {
			if (insertTelList.get(0) != null) {
				for (Tel tel : insertTelList) {
					tel.setTlCustType("1");
					tel.setCustomerid(entity.getId());
					telService.saveTel(tel,sysuserId);
				}
			}
		}
		Object insertAddrs = jsonObject.get("insertAddr");
		JSONArray insertAddrsjsonArray = JSONArray.fromObject(insertAddrs);
		List<Addr> insertAddrsList = insertAddrsjsonArray.toList(
				insertAddrsjsonArray, Addr.class);
		if (insertAddrsList.size() != 0) {
			if (insertAddrsList.get(0) != null) {
				for (Addr addr : insertAddrsList) {
					addr.setArCustType("1");
					addr.setCustomerid(entity.getId());
					addrService.saveAddr(addr,sysuserId);
				}
			}
		}
	}

	public List<Contactperson> findAllByCrmCustomerAndCpRelation(
			Customer customer, String relation) {
		// TODO Auto-generated method stub
		return contactpersonDao.findAllByCrmCustomerAndCpRelation(customer,
				relation);
	}

	/**
	 * 取客户最新的联系人(紧急联系人)
	 * 
	 * @author zhanghao
	 * @date 2013-01-24 16:31:31
	 * @param id
	 *            客户id
	 * @return 联系人
	 */
	public Contactperson findLastContactPerson(Long id) {
		List<Contactperson> personList = contactpersonDao
				.findByEmergencyContact(id);
		return personList.size() > 0 ? personList.get(0) : null;
	}

	/**
	 * 取民信客户所有的联系人(紧急联系人)
	 * 
	 * @author Jinghr
	 * @date 2013-7-30 16:06:08
	 * @param id
	 *            客户id
	 * @return 联系人
	 *//*
	public List<Contactperson> findAllContactPersonMx(Long id) {
		List<Contactperson> personList = contactpersonDao
				.findByEmergencyContact(id);
		return personList.size() > 0 ? personList : null;
	}

	*//**
	 * Jinghr,2013-7-23 9:43:51 定义一个变量unCompleteOder完成顺序（family+work+import）返回值
	 * 
	 * *//*
	public String cpIntegrity(Long id) {
		List<Contactperson> personList = contactpersonDao
				.findByEmergencyContact(id);
		// 家庭联系人，工作联系人，紧急联系人，至少录入的数量
		if (personList.size() > 0) {
			int familyNum = 0, workNum = 0, importantNum = 0, leastCompleteNum = 2, atleastCompleteNum = 1;
			for (Contactperson cp : personList) {
				if (null != cp.getCpContactpersonType()
						&& !"".equals(cp.getCpContactpersonType())) {
					if (cp.getCpContactpersonType().equals("2"))
						importantNum++;
					if (cp.getCpContactpersonType().equals("3"))
						familyNum++;
					if (cp.getCpContactpersonType().equals("4"))
						workNum++;
				}
			}
			if ((familyNum >= leastCompleteNum)
					&& (workNum >= leastCompleteNum)
					&& (importantNum >= atleastCompleteNum))
				return "ok";

			StringBuffer unCompleteOrder = new StringBuffer("");

			if (familyNum < leastCompleteNum)
				unCompleteOrder.append((leastCompleteNum - familyNum));
			else
				unCompleteOrder.append("0");

			if (workNum < leastCompleteNum)
				unCompleteOrder.append((leastCompleteNum - workNum));
			else
				unCompleteOrder.append("0");

			if (importantNum < atleastCompleteNum)
				unCompleteOrder.append((atleastCompleteNum - importantNum));
			else
				unCompleteOrder.append("0");

			return unCompleteOrder.toString();
		} else {
			return "error";
		}
	}

	public List<Contactperson> findAllByCrmCustomerAndCpRelationWithZx(
			Customer customer) {
		// TODO Auto-generated method stub
		return contactpersonDao
				.findAllByCrmCustomerAndCpRelationWithZx(customer);
	}

	// =======================================捷越信贷
	*//**
	 * Jinghr,2013-7-23 9:43:51 定义一个变量unCompleteOder完成顺序（family+work+import）返回值
	 * 
	 * *//*
	public String cpIntegrityJyxd(Long id) {
		List<Contactperson> personList = contactpersonDao
				.findByEmergencyContact(id);
		// 家庭联系人，工作联系人，紧急联系人，至少录入的数量
		if (personList.size() > 0) {
			int familyNum = 0, workNum = 0, importantNum = 0, leastCompleteNum = 2, atleastCompleteNum = 2;
			for (Contactperson cp : personList) {
				if (null != cp.getCpContactpersonType()
						&& !"".equals(cp.getCpContactpersonType())) {
					if (cp.getCpContactpersonType().equals("2"))
						importantNum++;
					if (cp.getCpContactpersonType().equals("3"))
						familyNum++;
					if (cp.getCpContactpersonType().equals("4"))
						workNum++;
				}
			}
			if ((familyNum >= leastCompleteNum)
					&& (workNum >= atleastCompleteNum)
					&& (importantNum >= atleastCompleteNum))
				return "ok";

			StringBuffer unCompleteOrder = new StringBuffer("");

			if (familyNum < leastCompleteNum)
				unCompleteOrder.append((leastCompleteNum - familyNum));
			else
				unCompleteOrder.append("0");

			if (workNum < atleastCompleteNum)
				unCompleteOrder.append((atleastCompleteNum - workNum));
			else
				unCompleteOrder.append("0");

			if (importantNum < atleastCompleteNum)
				unCompleteOrder.append((atleastCompleteNum - importantNum));
			else
				unCompleteOrder.append("0");

			return unCompleteOrder.toString();
		} else {
			return "error";
		}
	}

	*//**
	 * @Title: getContactpersonByCustmerAndType
	 * @Description: 按照客户跟类型查询结果
	 * @return List<Contactperson> 返回类型
	 * @author Sam.J
	 * @date 2015-5-22 下午02:56:57
	 * @throws
	 *//*
	public List<Contactperson> getContactpersonByCustmerAndType(
			Customer customer, String cpContactpersonType) {
		return contactpersonDao.findAllByCrmCustomerAndCpContactpersonType(
				customer, cpContactpersonType);
	}
*/}
