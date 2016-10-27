package com.zendaimoney.crm.customer.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.zendaimoney.constant.LoggingSource;
import com.zendaimoney.constant.LoggingType;
import com.zendaimoney.constant.ParamConstant;
import com.zendaimoney.crm.BaseService;
import com.zendaimoney.crm.contactperson.entiy.Contactperson;
import com.zendaimoney.crm.contactperson.service.ContactpersonService;
import com.zendaimoney.crm.customer.entity.Addr;
import com.zendaimoney.crm.customer.entity.Bankaccount;
import com.zendaimoney.crm.customer.entity.Customer;
import com.zendaimoney.crm.customer.entity.House;
import com.zendaimoney.crm.customer.entity.PrivateOwners;
import com.zendaimoney.crm.customer.entity.Tel;
import com.zendaimoney.crm.customer.repository.CustomerDao;
import com.zendaimoney.crm.product.entity.BusiFinance;
import com.zendaimoney.crm.product.service.BusiFinanceService;
import com.zendaimoney.crm.product.service.BusiFinanceUcService;
import com.zendaimoney.sys.area.service.AreaService;
import com.zendaimoney.uc.rmi.vo.Staff;
import com.zendaimoney.utils.LoggingHelper;
import com.zendaimoney.utils.helper.SequenceHelper;
import com.zendaimoney.utils.helper.SpringContextHelper;

@Component
@Transactional(readOnly = true)
public class CustomerService extends BaseService<Customer> {

	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private BankaccountService bankaccountService;
	@Autowired
	private TelService telService;
	@Autowired
	private AddrService addrService;
	@Autowired
	private HouseService houseService;
	@Autowired
	private PrivateOwnersService privateOwnersService;
	@Autowired
	private ContactpersonService contactpersonService;
	/*
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private BusiFinanceService busiFinanceService;

	@Autowired
	private BusiCreditService_ busiCreditService_;

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BusiFinanceUcService busiFinanceUcService;
	 */
	public Customer getCustomer(Long id) {
		return customerDao.findOne(id);
	}
	
	// 根据id获取客户姓名
	public String getCustomerName(Long id) {
		return customerDao.getCustomerName(id);
	}

	private static synchronized String getCustomerNumber(Customer customer) {
		Long cityId = customer.getCrCityId();
		String city = cityId + "";
		String memo = ((AreaService) SpringContextHelper.getBean("areaService"))
				.getAreaById(city).getMemo();// 区号
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
		String now = formatter.format(new Date());

		SimpleDateFormat for2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Date date = new Date();// 取时间
		Timestamp t_today = Timestamp.valueOf(for2.format(date));
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime();
		Timestamp t_tomorrow = Timestamp.valueOf(for2.format(date));
		List<Customer> customers = ((CustomerDao) SpringContextHelper
				.getBean("customerDao")).findCustomerAllByInputDateAndCityId(
				t_today, t_tomorrow, cityId);
		int num;
		if (null == customers)
			num = 0;
		else
			num = customers.size() + 1;
		String numString = String.format("%04d", num);
		return memo + now + numString;
	}
	/*
	@Transactional(readOnly = false)
	public Boolean UpdateBlacklistToCustomer(long blacklist_Customerid) {
		if (blacklist_Customerid != 0) {
			Customer upCus = this.getCustomer(blacklist_Customerid);
			upCus.setCrCustomerType("1"); // 1为储备客户
			customerDao.save(upCus);
			return true;
		} else {
			return false;
		}
	}

	@Transactional(readOnly = false)
	public Boolean UpdateCustomerToBlacklist(long blacklist_Customerid) {
		Customer upCus = this.getCustomer(blacklist_Customerid);
		upCus.setCrCustomerType("6"); // 6为黑名单
		customerDao.save(upCus);
		return true;
	}

	@Transactional(readOnly = false)
	public Customer saveOrUpdateCus(Customer cus) {
		if (cus == null) {
			return new Customer();
		}
		Staff staff = AuthorityHelper.getStaff();
		cus.setCrModifyDate(new Date());
		cus.setCrModifyId(staff.getId());

		String idNum = cus.getCrIdnum();
		if (null != idNum && !"".equals(idNum))
			idNum = idNum.toUpperCase();
		cus.setCrIdnum(idNum);

		if (cus.getId() == null) {
			cus.setCrInputId(staff.getId());
			cus.setCrInputDate(new Date());
			cus.setCrCustomerNumber(" ");
			cus.setCrState(ParamConstant.CRSTATE);
			cus.setCrCustomerType(ParamConstant.CRCUSTOMERTYPE);
			cus.setCrCustomerLevel(ParamConstant.CRCUSTOMELEVEL);
			cus.setCrCategory(ParamConstant.CRCATEGORY);
			cus.setCrBusiCount(ParamConstant.CRBASICOUNT);
			Customer customer = customerDao.save(cus);
			customer.setCrCustomerNumber(SequenceHelper.getCustomerFlowNumber());
			return customerDao.save(customer);
		} else {
			Customer upCus = this.getCustomer(cus.getId());
			cus.setCrInputId(upCus.getCrInputId());
			cus.setCrInputDate(upCus.getCrInputDate());
			BeanUtils.copyProperties(cus, upCus);
			return customerDao.save(upCus);
		}
	}*/

	public Customer setCustomer(Customer entity,Long sysuserId) {
		Customer oldCustomer = this.getCustomer(entity.getId());
		Customer customer = new Customer();
		BeanUtils.copyProperties(oldCustomer, customer);
		customer.setCrName(entity.getCrName());
		customer.setCrEname(entity.getCrEname());
		customer.setCrSex(entity.getCrSex());
		customer.setCrBirthday(entity.getCrBirthday());
		customer.setCrNationality(entity.getCrNationality());
		customer.setCrLanguage(entity.getCrLanguage());
		customer.setCrMother(entity.getCrMother());
		customer.setCrMaritalStatus(entity.getCrMaritalStatus());
		customer.setCrIdtype(entity.getCrIdtype());
		String idNum = customer.getCrIdnum();
		if (null != idNum && !"".equals(idNum))
			idNum = idNum.toUpperCase();
		customer.setCrIdnum(idNum);
		customer.setCrOccupation(entity.getCrOccupation());
		customer.setCrEducation(entity.getCrEducation());
		customer.setCrIndustry(entity.getCrIndustry());
		customer.setCrCompany(entity.getCrCompany());
		customer.setCrWorkYears(entity.getCrWorkYears());
		customer.setCrDuty(entity.getCrDuty());
		customer.setCrCompanySize(entity.getCrCompanySize());
		if (entity.getCrEmail() != null && entity.getCrEmail() != "") {
			customer.setCrEmail(entity.getCrEmail().toLowerCase());
		} else {
			customer.setCrEmail(entity.getCrEmail());
		}
		customer.setCrGather(entity.getCrGather());
		customer.setCrSource(entity.getCrSource());
		customer.setCrGatherDate(entity.getCrGatherDate());
		customer.setCrModifyDate(new Date());
		customer.setCrModifyId(sysuserId);
		customer.setCrMemo(entity.getCrMemo());
		customer.setCrIssueDate(entity.getCrIssueDate());
		customer.setCrExpiryDate(entity.getCrExpiryDate());
		customer.setCrFileAccept(entity.getCrFileAccept());
		customer.setCrDeptManager(entity.getCrDeptManager());
		customer.setCrSignDate(entity.getCrSignDate());
		customer.setCrMemo(entity.getCrMemo());
		customer.setOptlock(entity.getOptlock());
		customer.setCrChannel(entity.getCrChannel());// 2014-9-11 修改渠道
		customer.setCrApplicationNumber(entity.getCrApplicationNumber()); // 2015-01-07
																			// 客户申请书
																			// 编号
		customer.setCrRiskLevel(entity.getCrRiskLevel());//客户风险等级
		return customer;
	}

	@Transactional(readOnly = false)
	public synchronized Customer saveCustomerMXLC(Customer entity,Long sysuserId)
			throws InterruptedException {
		Date now = new Date();
	//	Staff staff = AuthorityHelper.getStaff();
		String idNum = entity.getCrIdnum();
		if (null != idNum && !"".equals(idNum))
			idNum = idNum.toUpperCase();
		entity.setCrIdnum(idNum);
		if (entity.getId() == null) {
			entity.setCrInputDate(now);
			entity.setCrInputId(sysuserId);
			entity.setCrModifyDate(now);
			entity.setCrModifyId(sysuserId);

			entity.setCrState(ParamConstant.CRSTATE);
			entity.setCrCustomerType(ParamConstant.CRCUSTOMERTYPE);
			entity.setCrCustomerLevel(ParamConstant.CRCUSTOMELEVEL);
			entity.setCrCategory(ParamConstant.CRCATEGORY);
			entity.setCrBusiCount(ParamConstant.CRBASICOUNT);
			entity.setCrCustomerNumber(" ");
			Customer customer = customerDao.save(entity);
			customer.setCrCustomerNumber(SequenceHelper.getCustomerFlowNumber());
			return customerDao.save(customer);
		} else {
			return customerDao.save(setCustomer(entity,sysuserId));
		}
	}

	@Transactional(readOnly = false)
	public synchronized Customer saveCustomerLC(Customer entity,Long sysuserId)
			throws InterruptedException {
		Date now = new Date();
	//	Staff staff = AuthorityHelper.getStaff();
		String idNum = entity.getCrIdnum();
		if (null != idNum && !"".equals(idNum))
			idNum = idNum.toUpperCase();
		entity.setCrIdnum(idNum);
		if (entity.getId() == null) {
			entity.setCrInputDate(now);
			entity.setCrInputId(sysuserId);
			entity.setCrModifyDate(now);
			entity.setCrModifyId(sysuserId);

			entity.setCrState(ParamConstant.CRSTATE);
			entity.setCrCustomerType(ParamConstant.CRCUSTOMERTYPE);
			entity.setCrCustomerLevel(ParamConstant.CRCUSTOMELEVEL);
			entity.setCrCategory(ParamConstant.CRCATEGORY);
			entity.setCrBusiCount(ParamConstant.CRBASICOUNT);
			entity.setCrCustomerNumber(getCustomerNumber(entity));
			Customer customer = customerDao.save(entity);
			return customerDao.save(customer);
		} else {
			return customerDao.save(setCustomer(entity,sysuserId));
		}
	}
	
	@Transactional(readOnly = false)
	public Customer saveCustomer(Customer entity, String modelType,Long sysuserId) {
		Date now = new Date();
		//Staff staff = AuthorityHelper.getStaff();
		String idNum = entity.getCrIdnum();
		if (null != idNum && !"".equals(idNum))
			idNum = idNum.toUpperCase();
		entity.setCrIdnum(idNum);
		if (entity.getId() == null) {
			entity.setCrCustomerNumber(getCustomerNumber(entity));
			entity.setCrInputDate(now);
			entity.setCrInputId(sysuserId);
			entity.setCrModifyDate(now);
			entity.setCrModifyId(sysuserId);
			entity.setCrState(ParamConstant.CRSTATE);
			entity.setCrCustomerType(ParamConstant.CRCUSTOMERTYPE);
			entity.setCrCustomerLevel(ParamConstant.CRCUSTOMELEVEL);
			entity.setCrCategory(ParamConstant.CRCATEGORY);
			entity.setCrBusiCount(ParamConstant.CRBASICOUNT);
			return customerDao.save(entity);
		} else if (modelType.equals("lc")) {
			return customerDao.save(setCustomer(entity,sysuserId));
		}
		if (modelType.equals("xd")) {
			Customer oldCustomer = this.getCustomer(entity.getId());
			Customer customer = new Customer();
			BeanUtils.copyProperties(oldCustomer, customer);
			customer.setCrName(entity.getCrName());
			customer.setCrIdtype(entity.getCrIdtype());
			customer.setCrIdnum(entity.getCrIdnum());
			customer.setCrSex(entity.getCrSex());
			customer.setCrReg(entity.getCrReg());
			customer.setCrEducation(entity.getCrEducation());
			customer.setCrMaritalStatus(entity.getCrMaritalStatus());
			customer.setCrChildren(entity.getCrChildren());
			if (entity.getCrEmail() != null && entity.getCrEmail() != "") {
				customer.setCrEmail(entity.getCrEmail().toLowerCase());
			} else {
				customer.setCrEmail(entity.getCrEmail());
			}
			// customer.setCrHouseRent(entity.getCrHouseRent());
			customer.setCrCompany(entity.getCrCompany());
			customer.setCrCoproperty(entity.getCrCoproperty());
			customer.setCrJobNature(entity.getCrJobNature());
			customer.setCrIncome(entity.getCrIncome());
			customer.setCrDependents(entity.getCrDependents());
			customer.setCrDept(entity.getCrDept());
			customer.setCrDuty(entity.getCrDuty());
			customer.setCrIndustry(entity.getCrIndustry());
			customer.setCrBasicSalary(entity.getCrBasicSalary());
			customer.setCrOtherIncome(entity.getCrOtherIncome());
			customer.setCrCreditCount(entity.getCrCreditCount());
			customer.setCrCreditAmount(entity.getCrCreditAmount());
			customer.setCrCreditOverdraw(entity.getCrCreditOverdraw());
			customer.setCrGather(entity.getCrGather());

			customer.setCrSource(entity.getCrSource());
			customer.setCrGatherDate(entity.getCrGatherDate());
			customer.setCrModifyDate(now);
			customer.setCrModifyId(sysuserId);

			customer.setCrPayday(entity.getCrPayday());

			customer.setCrJobNatureOther(entity.getCrJobNatureOther());

			customer.setOptlock(entity.getOptlock());
			
			customer.setCrRiskLevel(entity.getCrRiskLevel());//客户风险等级
			return customerDao.save(customer);
		}
		return entity;
	}
/*
	@Transactional(readOnly = false)
	public void deleteCustomer(Long id) {
		List<Contactperson> contactpersonList = contactpersonService
				.findAllByCrmCustomer(this.getCustomer(id));
		for (Contactperson contactperson : contactpersonList) {
			contactpersonService.deleteContactperson(contactperson.getId());
		}
		List<Tel> telList = telService.findALlByCustomerid(id);
		for (Tel tel : telList) {
			telService.deleteTel(tel.getId());
		}
		List<Addr> addrList = addrService.findAllByCustomerid(id);
		for (Addr addr : addrList) {
			addrService.deleteAddr(addr.getId());
		}
		House houseList = houseService.findByCustomerid(id);
		houseService.deleteHouse(houseList.getId());
		PrivateOwners privateOwners = privateOwnersService.findByCustomerid(id);
		privateOwnersService.deletePrivateOwners(privateOwners.getId());
		List<Bankaccount> bankList = bankaccountService.findALlByCustomerid(id);
		for (Bankaccount bankaccount : bankList) {
			bankaccountService.deleteBankaccount(bankaccount.getId());
		}
		customerDao.delete(id);
	}

	@Transactional(readOnly = false)
	public void deleteCustomer(Long[] ids) {
		for (Long id : ids) {
			List<Contactperson> contactpersonList = contactpersonService
					.findAllByCrmCustomer(this.getCustomer(id));
			for (Contactperson contactperson : contactpersonList) {
				contactpersonService.deleteContactperson(contactperson.getId());
			}
			List<Tel> telList = telService.findALlByCustomerid(id);
			for (Tel tel : telList) {
				telService.deleteTel(tel.getId());
			}
			List<Addr> addrList = addrService.findAllByCustomerid(id);
			for (Addr addr : addrList) {
				addrService.deleteAddr(addr.getId());
			}
			House houseList = houseService.findByCustomerid(id);
			if (houseList != null)
				houseService.deleteHouse(houseList.getId());
			PrivateOwners privateOwners = privateOwnersService
					.findByCustomerid(id);
			if (privateOwners != null)
				privateOwnersService.deletePrivateOwners(privateOwners.getId());
			List<Bankaccount> bankList = bankaccountService
					.findALlByCustomerid(id);
			for (Bankaccount bankaccount : bankList) {
				bankaccountService.deleteBankaccount(bankaccount.getId());
			}
			customerDao.delete(id);
		}
	}

	public List<Customer> getAllCustomer() {
		return (List<Customer>) customerDao.findAll();
	}
	*/

	public Page<Customer> getCustomer(PageRequest pageRequest) {
		return customerDao.findAll(pageRequest);
	}

	/**
	 * 
	 * @param customer
	 *            客户对象
	 * @param crTels
	 *            客户电话list
	 * @param crAddrs
	 *            客户地址list
	 * @param banks
	 *            银行list
	 * @param contactpersonList
	 *            联系人list
	 * @param cptelMap
	 *            联系人对应电话list的map
	 * @param cpaddrMap
	 *            联系人对应地址list 的map
	 * @param modelType
	 *            模板类型："lc","xd","bz"
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 */
	@Transactional(readOnly = false)
	public String saveXdCustomer(Customer customer, List<Tel> crTels,
			List<Addr> crAddrs, List<Bankaccount> banks, House house,
			PrivateOwners privateOwners, List<Contactperson> contactpersonList,
			Map<Contactperson, List<Tel>> cptelMap,
			Map<Contactperson, List<Addr>> cpaddrMap, String modelType,Long sysuserId)
			throws HibernateOptimisticLockingFailureException, Exception,
			SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub

		String result = "";// 日志操作

		Customer crEntity = new Customer();// 保存客户主体;
		// Customer crEntity = this.saveCustomer(customer,modelType);//保存客户主体
		String idNum = customer.getCrIdnum();
		if (null != idNum && !"".equals(idNum))
			idNum = idNum.toUpperCase();
		customer.setCrIdnum(idNum);
		if (customer.getId() != null) {
			Customer entity = this.getCustomer(customer.getId());
			entity.setCrIdnum(idNum);
			Customer oldCustomer = new Customer();
			BeanUtils.copyProperties(entity, oldCustomer);// 老数据
			List<String> needList = Lists.newArrayList("crName", "crEname",
					"crIdtype", "crIdnum", "crReg", "crNationality",
					"crLanguage", "crMother", "crSex", "crBirthday",
					"crCityId", "crEducation", "crMajor", "crOccupation",
					"crDuty", "crTitle", "crHouse", "crLive", "crEntryDate",
					"crCoproperty", "crCompany", "crDept", "crWebsite",
					"crIndustry", "crEmail", "crQq", "crMsn", "crIncomeSource",
					"crIncome", "crMaritalStatus", "crChildren",
					"crHealthStatus", "crCustomerType", "crCustomerLevel",
					"crSource", "crGatherDate", "crGather", "crShare",
					"crState", "crMemo", "crMemo2", "crCategory",
					"crBusiCount", "crWorkYears", "crCompanySize",
					"crDependents", "crJobNature", "crPayday", "crOtherIncome",
					"crBasicSalary", "crCreditCount", "crCreditAmount",
					"crCreditOverdraw", "crDepartment", "crJobNatureOther");
			crEntity = this.saveCustomer(customer, modelType,sysuserId);// 保存客户主体
			result += "客户 Id："
					+ entity.getId()
					+ "；"
					+ LoggingHelper.loggingEquals(oldCustomer, crEntity,
							needList);
			// 增加判断，当名字有变动时，才调用此方法
			if (!customer.getCrName().equals(oldCustomer.getCrName())) {
				bankaccountService.saveBankaccountName(entity.getId());
				result += "变更银行开户名" + oldCustomer.getCrName() + "->"
						+ crEntity.getCrName();

			}
		} else {
			if (null == customer.getCrCityId()
					|| "".equals(customer.getCrCityId())) {
				crEntity = this.saveCustomerMXLC(customer,sysuserId);// 保存民信理财主体
			} else {
				crEntity = this.saveCustomer(customer, modelType,sysuserId);// 保存客户主体

			}
			result += "客户 Id：" + crEntity.getId();
		}

		if (crTels != null) {
			for (Tel tel : crTels) {// 保存客户电话
				if (tel != null) {
					tel.setCustomerid(crEntity.getId());
					// telService.saveTel(tel);
					if (tel.getId() != null) {
						Tel entity = telService.getTel(tel.getId());
						Tel oldTel = new Tel();
						BeanUtils.copyProperties(entity, oldTel);// 老数据
						List<String> needList = Lists.newArrayList("tlTelType",
								"tlAreaCode", "tlTelNum", "tlExtCode",
								"tlPriority", "tlValid");
						Tel newTel = telService.saveTel(tel,sysuserId);
						result += "客户电话 Id："
								+ entity.getId()
								+ "；"
								+ LoggingHelper.loggingEquals(oldTel, newTel,
										needList);
					} else {
						Tel newTel = telService.saveTel(tel,sysuserId);
						result += "客户电话 Id：" + newTel.getId();
					}
				}
			}
		}
		if (crAddrs != null) {
			for (Addr addr : crAddrs) {// 保存客户地址
				if (addr != null) {
					addr.setCustomerid(crEntity.getId());
					addr.setArProvince(isCodeNumber(addr.getArProvince()));
					addr.setArCity(isCodeNumber(addr.getArCity()));
					addr.setArCounty(isCodeNumber(addr.getArCounty()));
					// addrService.saveAddr(addr);
					if (addr.getId() != null) {
						Addr entity = addrService.getAddr(addr.getId());
						Addr oldAddr = new Addr();
						BeanUtils.copyProperties(entity, oldAddr);// 老数据
						List<String> needList = Lists.newArrayList(
								"arAddrType", "arProvince", "arCity",
								"arCounty", "arAddrDetail", "arZipCode",
								"arPriority", "arValid");
						Addr newAddr = addrService.saveAddr(addr,sysuserId);
						result += "客户地址 Id："
								+ entity.getId()
								+ "；"
								+ LoggingHelper.loggingEquals(oldAddr, newAddr,
										needList);
					} else {
						Addr newAddr = addrService.saveAddr(addr,sysuserId);
						result += "客户地址 Id：" + newAddr.getId();
					}
				}
			}
		}
		if (house != null) {
			house.setCustomerid(crEntity.getId());
			// houseService.saveHouse(house);
			if (house.getId() != null) {
				House entity = houseService.getHouse(house.getId());
				House oldHouse = new House();
				BeanUtils.copyProperties(entity, oldHouse);// 老数据
				List<String> needList = Lists.newArrayList("heType",
						"heBuyDate", "heBuyprice", "heBuildingArea",
						"heUnitPrice", "heProvince", "heCity", "heCounty",
						"heAddrDetail", "heZipCode", "heRatio", "heLoanTerm",
						"heMonthRepayment", "heLoanBalance");
				House newHouse = houseService.saveHouse(house);
				result += "房产信息 Id："
						+ entity.getId()
						+ "；"
						+ LoggingHelper.loggingEquals(oldHouse, newHouse,
								needList);
			} else {
				House newHouse = houseService.saveHouse(house);
				result += "房产信息 Id：" + newHouse.getId();
			}
		}
		if (privateOwners != null) {
			privateOwners.setCustomerid(crEntity.getId());
			// privateOwnersService.savePrivateOwners(privateOwners);
			if (privateOwners.getId() != null) {
				PrivateOwners entity = privateOwnersService
						.getPrivateOwners(house.getId());
				PrivateOwners oldPrivateOwners = new PrivateOwners();
				BeanUtils.copyProperties(entity, oldPrivateOwners);// 老数据
				List<String> needList = Lists.newArrayList("poCompanyType",
						"poCompanyOther", "poCompanyRegtime",
						"poShareholdingRatio", "poRoom", "poCompanySize",
						"poNetIncome", "poNetIncomeMonth");
				PrivateOwners newPrivateOwners = privateOwnersService
						.savePrivateOwners(privateOwners);
				result += "私企 Id："
						+ entity.getId()
						+ "；"
						+ LoggingHelper.loggingEquals(oldPrivateOwners,
								newPrivateOwners, needList);
			} else {
				PrivateOwners newPrivateOwners = privateOwnersService
						.savePrivateOwners(privateOwners);
				result += "私企 Id：" + newPrivateOwners.getId();
			}
		}
		if (banks != null) {
			for (Bankaccount bank : banks) {// 保存客户银行
				if (bank != null) {
					bank.setCrmCustomer(crEntity);
					// bankaccountService.saveBankaccount(bank);
					if (bank.getId() != null) {
						Bankaccount entity = bankaccountService
								.getBankaccount(bank.getId());
						Bankaccount oldBankaccount = new Bankaccount();
						BeanUtils.copyProperties(entity, oldBankaccount);// 老数据
						List<String> needList = Lists.newArrayList("baAccount",
								"baAccountName", "baAccountType", "baBankCode",
								"baBankName", "baBranchName", "baMemo");
						Bankaccount newBankaccount = bankaccountService
								.saveBankaccount(bank,sysuserId);
						result += "客户银行 Id："
								+ entity.getId()
								+ "；"
								+ LoggingHelper.loggingEquals(oldBankaccount,
										newBankaccount, needList);
					} else {
						Bankaccount newBankaccount = bankaccountService
								.saveBankaccount(bank,sysuserId);
						result += "客户银行 Id：" + newBankaccount.getId();
					}
				}
			}
		}
		if (contactpersonList != null) {
			for (int i = 0; i < contactpersonList.size(); i++) {
				Contactperson contactperson = contactpersonList.get(i);
				if (contactperson != null) {
					List<Tel> tels = cptelMap.get(contactperson);
					List<Addr> addrs = cpaddrMap.get(contactperson);
					contactperson.setCrmCustomer(crEntity);
					// Contactperson cpEntity =
					// contactpersonService.saveContactperson(contactperson);//保存联系人
					Contactperson cpEntity = new Contactperson();
					if (contactperson.getId() != null) {
						Contactperson entity = contactpersonService
								.getContactperson(contactperson.getId());
						Contactperson oldContactperson = new Contactperson();
						BeanUtils.copyProperties(entity, oldContactperson);// 老数据
						List<String> needList = Lists.newArrayList("baAccount",
								"baAccountName", "baAccountType", "baBankCode",
								"baBankName", "baBranchName", "baMemo");
						contactperson.setCpState("1"); // 有效性
						contactperson.setCpContactpersonType("2"); // 为紧急联系人
						Contactperson newContactperson = contactpersonService
								.saveContactperson(contactperson);// 保存联系人
						result += "联系人 Id："
								+ entity.getId()
								+ "；"
								+ LoggingHelper.loggingEquals(oldContactperson,
										newContactperson, needList);
						cpEntity = newContactperson;
					} else {
						contactperson.setCpState("1"); // 有效性
						contactperson.setCpContactpersonType("2"); // 为紧急联系人
						Contactperson newContactperson = contactpersonService
								.saveContactperson(contactperson);// 保存联系人
						result += "联系人 Id：" + newContactperson.getId();
						cpEntity = newContactperson;
					}

					if (tels != null) {
						for (Tel tel : tels) {
							if (tel != null) {
								tel.setCustomerid(cpEntity.getId());
								// telService.saveTel(tel);//保存联系人电话
								if (tel.getId() != null) {
									Tel entity = telService.getTel(tel.getId());
									Tel oldTel = new Tel();
									BeanUtils.copyProperties(entity, oldTel);// 老数据
									List<String> needList = Lists.newArrayList(
											"tlTelType", "tlAreaCode",
											"tlTelNum", "tlExtCode",
											"tlPriority", "tlValid");
									Tel newTel = telService.saveTel(tel,sysuserId);
									result += "联系人电话 Id："
											+ entity.getId()
											+ "；"
											+ LoggingHelper.loggingEquals(
													oldTel, newTel, needList);
								} else {
									Tel newTel = telService.saveTel(tel,sysuserId);
									result += "联系人电话 Id：" + newTel.getId();
								}
							}
						}
					}
					if (addrs != null) {
						for (Addr addr : addrs) {
							if (addr != null) {
								addr.setCustomerid(cpEntity.getId());
								addr.setArProvince(isCodeNumber(addr
										.getArProvince()));
								addr.setArCity(isCodeNumber(addr.getArCity()));
								addr.setArCounty(isCodeNumber(addr
										.getArCounty()));
								// addrService.saveAddr(addr);//保存联系人地址
								if (addr.getId() != null) {
									Addr entity = addrService.getAddr(addr
											.getId());
									Addr oldAddr = new Addr();
									BeanUtils.copyProperties(entity, oldAddr);// 老数据
									List<String> needList = Lists.newArrayList(
											"arAddrType", "arProvince",
											"arCity", "arCounty",
											"arAddrDetail", "arZipCode",
											"arPriority", "arValid");
									Addr newAddr = addrService.saveAddr(addr,sysuserId);
									result += "联系人地址 Id："
											+ entity.getId()
											+ "；"
											+ LoggingHelper.loggingEquals(
													oldAddr, newAddr, needList);
								} else {
									Addr newAddr = addrService.saveAddr(addr,sysuserId);
									result += "联系人地址 Id：" + newAddr.getId();
								}
							}
						}
					}
				}
			}
		}
		return result;
	}

	public List<Customer> findAllCustomerByName(String searchVal) {
		return customerDao.findByCrNameContaining(searchVal);
	}

	public List<Customer> findAllByCrIdtypeAndCrIdnum(String idType,
			String idNum) {
		// TODO Auto-generated method stub
		return customerDao.findAllByCrIdtypeAndCrIdnum(idType, idNum);
	}

	public List<Customer> getCustomerList(Long id) {
		// TODO Auto-generated method stub
		return customerDao.getCustomerList(id);
	}

	public Page<Customer> getCustomer(
			Specification<Customer> buildSpecification,
			PageRequest buildPageRequest) {
		// TODO Auto-generated method stub
		return customerDao.findAll(buildSpecification, buildPageRequest);
	}

	public List<Customer> getCustomerList(
			Specification<Customer> buildSpecification) {
		// TODO Auto-generated method stub
		return customerDao.findAll(buildSpecification);
	}

	public List<Customer> getCustomer(Specification<Customer> buildSpecification) {
		return customerDao.findAll(buildSpecification);
	}

	public List<Customer> findAllCustomerById(String ids) {
		return customerDao.findAllCustomerById(ids);
	}

	public List<Customer> findAllCustomerById(List<Long> ids) {
		return customerDao.findByIdIn(ids);
	}

	@Transactional(readOnly = false)
	public void saveCustomer(Customer customer) {
		customerDao.save(customer);
	}

	/**
	 * 根据类型和内容查找客户
	 * 
	 * @param searchType
	 * @param searchValue
	 * @return
	 */
	/*public List<Customer> customerBysearchTypeAndsearchValue(String searchType,
			String searchValue) {
		if (searchType.equals(ParamConstant.crName)) {
			return customerDao.findAllByCrName(searchValue);
		}
		if (searchType.equals(ParamConstant.crIdnum)) {
			return customerDao.findAllByCrIdnum(searchValue);
		}
		if (searchType.equals(ParamConstant.crCustomerNumber)) {
			return customerDao.findAllByCrCustomerNumber(searchValue);
		}
		if(searchType.equals(ParamConstant.crMOBILE)){
			List<Customer> customerList = new ArrayList<Customer>();
			List<Long> customerIds = telService.findByMobile(searchValue);
			if(customerIds.size() > 0){
				return customerDao.findByIdIn(customerIds);							
			}else{
				return customerList;
			}
		}
		return null;
	}*/
/*
	public List<RecordShow> contactReord(String searchVal) {
		List<RecordShow> recordShow = new ArrayList<RecordShow>();
		if (null != searchVal && !searchVal.equals("")) {
			List<Customer> customerList = customerDao
					.findAllByCrName(searchVal);
			List<Member> memberList = memberDao.findAllByMrName(searchVal);

			for (Customer customer : customerList) {
				recordShow.add(new RecordShow(customer.getId(), customer
						.getCrCustomerNumber(), customer.getCrName(), customer
						.getCrSex(), customer.getCrIdnum(), customer
						.getCrBirthday(), customer.getCrCompany(), customer
						.getCrReg(), "1"));
			}

			for (Member member : memberList) {
				recordShow.add(new RecordShow(member.getId(), "", member
						.getMrName(), "", member.getMrIdnum(), null, "", "",
						"2"));
			}
		}
		return recordShow;
	}

	public List<Customer> findAllByCrGather(Long crGatherId) {
		// TODO Auto-generated method stub
		return customerDao.findAllByCrGather(crGatherId);
	}

	@Transactional(readOnly = false)
	public void updateGather(List<CustomerTreeVo> jsonObjectList,
			Long guishustaffid, Long staffid) throws Exception {
		// TODO Auto-generated method stub
		String content = "";
		for (CustomerTreeVo customerTreeVo : jsonObjectList) {
			if (customerTreeVo.getType().equals("1")) {
				Customer customer = this.getCustomer(customerTreeVo.getId());
				customer.setCrGather(guishustaffid);
				this.saveCustomer(customer);
				// 增加对 member 会员表中的客户经理信息的修改
				memberService.updateMember(customer);
				content += "客户id：" + customer.getId();
			}
			if (customerTreeVo.getType().equals("2")) {
				BusiFinance busiFinance = busiFinanceService
						.findOneFinance(customerTreeVo.getId());
				busiFinance.setFeManager(guishustaffid);
				busiFinanceService.saveFinance(busiFinance);
				//保存理财中心相关数据
				RecordStaffVo recordStaffVo = UcHelper.getRecordStaff(busiFinance.getFeManager());
				if(recordStaffVo != null && !Strings.isNullOrEmpty(recordStaffVo.getParentDepName())){
					busiFinanceUcService.saveOrUpdate(busiFinance,"业务转移");
				}
				content += "出借编号：" + busiFinance.getFeLendNo();
			}
			// 由于要调用credit信贷的RMI接口，但是信贷接口存在问题，跟CRM不交互，暂时屏蔽这段代码 2014.08.04 By
			// Sam.J
			
			 * if (customerTreeVo.getType().equals("3")) { BusiCredit_
			 * busiCredit_ =
			 * busiCreditService_.findOneCredit(customerTreeVo.getId());
			 * busiCredit_.setcManager(guishustaffid);
			 * busiCreditService_.saveCredit(busiCredit_); content += "借款编号：" +
			 * busiCredit_.getcBorrowNo(); }
			 
		}
		content += "客户经理由" + staffid + "->" + guishustaffid;
		LoggingHelper
				.createLogging(LoggingType.修改, LoggingSource.客户经理, content);
	}

	*//**
	 * 根据客户ID获取客户编号
	 * 
	 * @author zhanghao
	 * @date 2013-5-22 下午03:35:04
	 * @param id
	 * @return
	 *//*
	public String getCustNumber(Long id) {
		return customerDao.getCustomerNumber(id);
	}

	*//**
	 * 获取客户出账金额
	 * 
	 * @param crmId
	 *            CRM投资ID
	 * @param oldCzAmount
	 *            出账金额字段名称
	 * @return
	 *//*
	public Double getOldCzAmountByBusiFinanceId(Long crmId, String oldCzAmount) {
		return Double.valueOf(CommonUtil.round(
				FortuneHelper.getFieldsValue(crmId, oldCzAmount)
						.get(oldCzAmount).toString(), 2, 4));
	}

	/**
	 * 根据客户名字查找客户
	 * 
	 * @author Yuan Changchun
	 * @date 2013-7-23 下午02:19:57
	 * @param crName
	 * @return
	 */
	public List<Customer> findAllCustomerByCrName(String crName) {
		crName = "%" + crName + "%";
		return this.customerDao.findAllByCrName(crName);
	}

	// ====================================================捷越信贷
	// 添加房产信息
	@Transactional(readOnly = false)
	public void addHouse(Long id, House house) {
		if (house != null) {
			house.setCustomerid(id);
			House newHouse = houseService.saveHouse(house);
		}

	}

	// 添加私营业主信息
	@Transactional(readOnly = false)
	public void addPrivateOwners(Long id, PrivateOwners privateOwners) {
		if (privateOwners != null) {
			privateOwners.setCustomerid(id);
			PrivateOwners newPrivateOwners = privateOwnersService
					.savePrivateOwners(privateOwners);
		}
	}

	// 根据客户id删除房产信息
	@Transactional(readOnly = false)
	public void deleteHouseByCustomerId(Long customerId) {
		houseService.deleteHouseByCustomerId(customerId);
	}

	// 根据E-mail查询客户信息
	public List<Customer> findAllByCrEmail(String searchVal) {
		return customerDao.findAllByCrEmail(searchVal);
	}


	/**
	 * @Title:根据客户申请书编号搜索客户
	 * @Description: TODO
	 * @param crApplicationNumber
	 * @return
	 * @throws
	 * @time:2015-1-7 上午11:03:57
	 * @author:Sam.J
	 */
	public List<Customer> findAllByCrApplicationNumber(
			String crApplicationNumber) {
		return customerDao.findAllByCrApplicationNumber(crApplicationNumber);
	}
	

/**
 * @Title:根据查询参数查询客户列表  有效
 * @param map  参数个数不定
 * @return
 *//*
	public List<Customer> getCustomerListForHW(Map<String,Object> map) {  
		StringBuffer searchBaseSQL = new StringBuffer();
		if("".equals(map.get("crMobile"))){//入口参数中没有手机号
		// 根据条件查询
		searchBaseSQL
				.append("SELECT A FROM Customer A WHERE A.crState='1' ");//单表查询
		
		}else{//入口参数中有手机号  连表查询
			String  crMobile=map.get("crMobile")+"";
			String  areaCode="";
			//判断是否为固话
			if(crMobile.startsWith("0")){
				//判断是否为10个3位区号的地域
				if(crMobile.startsWith("01")||crMobile.startsWith("02")){
					areaCode=crMobile.substring(0, 3);
					crMobile=crMobile.substring(3,crMobile.length());
				}else{
					areaCode=crMobile.substring(0, 4);
					crMobile=crMobile.substring(4,crMobile.length());
				}
			}
			searchBaseSQL
			.append("SELECT A FROM Customer A, Tel T  WHERE A.crState='1' AND A.id=T.customerid  ");
			
			searchBaseSQL.append(" AND T.tlTelNum='")
			.append(crMobile+"' ");
			//区号字段不为空的话则加入区号做判断
			if(!"".equals(areaCode)){
				searchBaseSQL.append("  AND T.tlAreaCode='"+areaCode+"' ");
			}
		}
		//客户编号
		if(map.containsKey("crCustomerNumber")&&!"".equals(map.get("crCustomerNumber"))){
				searchBaseSQL.append(" AND A.crCustomerNumber='")
				             .append(map.get("crCustomerNumber")+"' ");
		}
		//客户姓名
		if(map.containsKey("crName")&&!"".equals(map.get("crName"))){
			searchBaseSQL.append("AND A.crName='")
			.append(map.get("crName")+"' ");
		}
		//客户证件号码
		if(map.containsKey("crIdnum")&&!"".equals(map.get("crIdnum"))){
			searchBaseSQL.append("AND A.crIdnum='")
			.append(map.get("crIdnum")+"' ");
		}
		return getContent(searchBaseSQL.toString());
	}
	
	*//** 
	* @Title: findCustmerByBusiLeno 
	* @Description: 根据出借编号找到投资客户
	* @return Customer(描述入参以及返回参数的含义)
	* @author Sam.J
	* @date 2015年12月8日 下午3:55:54 
	* @throws 
	*//*
	public  Customer  findCustmerByBusiLeno(String feLendNo){
		Map<String, String> paramMap=new HashMap<String, String>();
		paramMap.put("felendNo", feLendNo);
		List<BusiFinance> bf=busiFinanceService.findBusiForWebser(paramMap);
		if(bf.size()==0) return null;
		Long custId=bf.get(0).getCustomerid();
		Customer c=customerDao.findOne(custId);
		return c;
	}
	
*/}
