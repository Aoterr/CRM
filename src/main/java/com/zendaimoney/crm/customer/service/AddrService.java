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
import com.zendaimoney.crm.BaseService;
import com.zendaimoney.crm.customer.entity.Addr;
import com.zendaimoney.crm.customer.repository.AddrDao;
import com.zendaimoney.uc.rmi.vo.Staff;

@Component
@Transactional(readOnly = true)
public class AddrService extends BaseService<Addr> {
	@Autowired
	private AddrDao addrDao;

	public Addr getAddr(Long id) {
		return addrDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public void saveOrUpAddrListByCus_id(Long customerId, List<Addr> addrList,Long sysuserId) {
		if (addrList.isEmpty() || addrList == null) {
			return;
		}
		//Staff staff = AuthorityHelper.getStaff();
		for (Addr entity : addrList) {
			if (entity != null) {
				entity.setCustomerid(customerId);
				entity.setArModifyId(sysuserId);
				entity.setArModifyTime(new Date());
				entity.setArProvince(isCodeNumber(entity.getArProvince()));
				entity.setArCity(isCodeNumber(entity.getArCity()));
				entity.setArCounty(isCodeNumber(entity.getArCounty()));

				if (entity.getId() == null) {
					entity.setArInputId(sysuserId);
					entity.setArInputDate(new Date());
					if (entity.getArPriority() == null) {
						entity.setArPriority(ParamConstant.priorityOrNum);
					}
					addrDao.save(entity);
				} else {
					Addr addr = this.getAddr(entity.getId());
					entity.setArInputId(addr.getArInputId());
					entity.setArInputDate(addr.getArInputDate());
					BeanUtils.copyProperties(entity, addr);
					if (addr.getArPriority() == null) {
						addr.setArPriority(ParamConstant.priorityHighNum);
					}
					addrDao.save(addr);
				}
			}
		}
	}

	@Transactional(readOnly = false)
	public Addr saveAddr(Addr entity,Long sysuserId) {
		Date now = new Date();
		//Staff staff = AuthorityHelper.getStaff();
		String arPriority = null;
		if (entity.getArPriority() != null) {
			arPriority = entity.getArPriority();
		}
		if (entity.getArPriority() == null) {
			entity.setArPriority(ParamConstant.priorityOrNum);
		} else if (entity.getArPriority().equals(ParamConstant.priorityHighNum)) {
			List<Addr> tels = this
					.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
							entity.getCustomerid(), entity.getArCustType(),
							entity.getArAddrType(),
							ParamConstant.priorityHighNum);
			for (Addr addr : tels) {
				addr.setArPriority(ParamConstant.priorityOrNum);
				addrDao.save(addr);
			}
		}
		if (entity.getId() == null) {
			if (entity.getArPriority() == null) {
				entity.setArPriority("2");
			}
			entity.setArProvince(isCodeNumber(entity.getArProvince()));
			entity.setArCity(isCodeNumber(entity.getArCity()));
			entity.setArCounty(isCodeNumber(entity.getArCounty()));
			entity.setArInputId(sysuserId);
			entity.setArInputDate(now);
			entity.setArModifyId(sysuserId);
			entity.setArModifyTime(now);
			return addrDao.save(entity);
		} else {
			Addr addr = this.getAddr(entity.getId());
			addr.setArProvince(isCodeNumber(entity.getArProvince()));
			addr.setArCity(isCodeNumber(entity.getArCity()));
			addr.setArCounty(isCodeNumber(entity.getArCounty()));
			addr.setArAddrType(entity.getArAddrType());
			addr.setArProvince(entity.getArProvince());
			addr.setArCity(entity.getArCity());
			addr.setArCounty(entity.getArCounty());
			addr.setArStreet(entity.getArStreet());
			addr.setArAddrDetail(entity.getArAddrDetail());
			addr.setArZipCode(entity.getArZipCode());
			if (arPriority != null)
				addr.setArPriority(arPriority);
			addr.setArValid(entity.getArValid());
			addr.setArAddrDetail(entity.getArAddrDetail());
			addr.setArZipCode(entity.getArZipCode());
			addr.setArModifyId(sysuserId);
			addr.setArModifyTime(now);
			return addrDao.save(addr);
		}
	}

	public List<Addr> findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
			Long customerid, String arCustType, String arAddrType,
			String priorityhighnum) {
		// TODO Auto-generated method stub
		return addrDao
				.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(
						customerid, arCustType, arAddrType, priorityhighnum);
	}

	@Transactional(readOnly = false)
	public void deleteAddr(Long id) {
		addrDao.delete(id);
	}

	@Transactional(readOnly = false)
	public void deleteAddr(Long[] ids) {
		for (Long id : ids) {
			addrDao.delete(id);
		}
	}

	public List<Addr> getAllAddr() {
		return (List<Addr>) addrDao.findAll();
	}

	public Page<Addr> getAddr(PageRequest pageRequest) {
		return addrDao.findAll(pageRequest);
	}

	public List<Addr> findByCustomeridAndTlCustTypeAndTlTelType(
			Long customerid, String string, String prValue) {
		// TODO Auto-generated method stub
		return addrDao.findByCustomeridAndArCustTypeAndArAddrType(customerid,
				string, prValue);
	}

	public List<Addr> findAllByCustomerid(Long id) {
		// TODO Auto-generated method stub
		return addrDao.findAllByCustomerid(id);
	}

	public List<Addr> findByCustomeridAndArPriority(Long customerid,
			String prValue) {
		// TODO Auto-generated method stub
		return addrDao.findByCustomeridAndArPriority(customerid, prValue);
	}

	public List<Addr> findALlByCustomeridDesc(Long customerid) {
		// TODO Auto-generated method stub
		return addrDao.findALlByCustomeridDesc(customerid);
	}

	public List<Addr> findAllAddrByCustomerid(Long id) {
		// TODO Auto-generated method stub
		List<Addr> list1 = addrDao.findAllByCustomerid(id);
		List<Addr> list2 = addrDao.findAllAddrByCustomerid(id);
		list1.addAll(list2);
		return list1;
	}

	public Addr findByCustomeridAndArCustTypeAndArAddrTypeAndArPriority(
			Long objectId, String string, String fdReserve,
			String priorityhighnum) {
		// TODO Auto-generated method stub
		return addrDao.findByCustomeridAndArCustTypeAndArAddrTypeAndArPriority(
				objectId, string, fdReserve, priorityhighnum);
	}

	public Boolean highPriority(long id, String type) {
		List<Addr> addrList = addrDao.findByCustomeridPriorityType(id, "1",
				type);
		if (addrList.size() > 0)
			return false;
		else
			return true;
	}
}
