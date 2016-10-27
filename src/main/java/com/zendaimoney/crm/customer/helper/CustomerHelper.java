package com.zendaimoney.crm.customer.helper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.zendaimoney.constant.ParamConstant;
import com.zendaimoney.crm.contactperson.entiy.Contactperson;
import com.zendaimoney.crm.contactperson.service.ContactpersonService;
import com.zendaimoney.crm.customer.entity.Addr;
import com.zendaimoney.crm.customer.entity.Bankaccount;
import com.zendaimoney.crm.customer.entity.Customer;
import com.zendaimoney.crm.customer.entity.Tel;
import com.zendaimoney.crm.customer.service.AddrService;
import com.zendaimoney.crm.customer.service.BankaccountService;
import com.zendaimoney.crm.customer.service.CustomerService;
import com.zendaimoney.crm.customer.service.TelService;
import com.zendaimoney.sys.area.service.AreaService;
import com.zendaimoney.sys.parameter.entity.Parameter;
import com.zendaimoney.utils.helper.SpringContextHelper;
import com.zendaimoney.utils.helper.SystemParameterHelper;

/**
 * 
 * @author bianxj
 * 
 */
public class CustomerHelper {
	private static final org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory
			.getLog(CustomerHelper.class);

	/** 电话类型 */
	public static final String telType = "telType";

	/** 地址类型 */
	public static final String addrType = "addrType";

	/** 客户对象不能为空的字段 */
	public static final String[] fields = new String[] { "crName", "crSex",
			"crNationality", "crIdtype", "crIdnum",/*
													 * "crIssueDate","crExpiryDate"
													 * , "crOccupation",
													 */"crFileAccept",
			"crSource" };

	/** 民信信貸客户对象不能为空的字段 */
	public static final String[] mxFields = new String[] { "crName", "crSex",
			"crIdtype", "crIdnum", "crEducation", "crMaritalStatus", "crHouse",
			"crLive", "crCompany", "crCoproperty", "crEntryDate",
			"crYearIncome", "crDept", "crDuty", "crGather", "crSource",
			"crGatherDate" };

	/** 民信理财客户对象不能为空的字段 */
	public static final String[] mxlcFields = new String[] { "crName", "crSex",
			"crIdtype", "crIdnum", "crGather", "crFileAccept" };

	/** 民信理财（紧急联系人）对象不能为空的字段 */
	public static final String[] mxlcContactPersonFields = new String[] {
			"cpName", "cpSex", "cpIdtype", "cpId" };

	/**
	 * 捷越信贷客户不能为空的字段(姓名,性别,婚姻状况,住宅类型,工作单位全称,所属部门,担任职务,进入该单位时间)
	 */
	public static final String[] jyCreditCustomerField = new String[] {
			"crName", "crSex", "crIdnum", "crMaritalStatus", "crHouse",
			"crCompany", "crDept", "crDuty", "crEntryDate" };

	private static AddrService addrService;

	private static TelService telService;

	private static ContactpersonService contactService;

	private static AreaService areaService;

	private static CustomerService customerService;

	private static BankaccountService bankaccountService;

	private static LinkedHashMap<String, String> blankBaseMap = new LinkedHashMap<String, String>();

	static {
		telService = SpringContextHelper.getBean("telService");
		addrService = SpringContextHelper.getBean("addrService");
		areaService = SpringContextHelper.getBean("areaService");
		contactService = SpringContextHelper.getBean("contactpersonService");
		customerService = SpringContextHelper.getBean("customerService");
		bankaccountService = SpringContextHelper.getBean("bankaccountService");
		initBlankMessage();
	}

	public static void initBlankMessage() {

		blankBaseMap.put("crName", "客户姓名(中文)不能为空!");
		blankBaseMap.put("crSex", "客户性别不能为空!");
		blankBaseMap.put("crNationality", "客户国籍不能为空!");
		blankBaseMap.put("crIdtype", "客户证件类型不能为空!");
		blankBaseMap.put("crIdnum", "客户证件号码不能为空!");

		blankBaseMap.put("crEducation", "客户学历不能为空!");
		blankBaseMap.put("crMaritalStatus", "客户婚姻状况不能为空!");
		blankBaseMap.put("crHouse", "客户房产状况不能为空!");
		blankBaseMap.put("crLive", "客户居住情况不能为空!");
		blankBaseMap.put("crCompany", "客户公司名称不能为空!");
		blankBaseMap.put("crCoproperty", "客户单位性质不能为空!");
		blankBaseMap.put("crEntryDate", "客户进入单位时间不能为空!");
		blankBaseMap.put("crYearIncome", "客户年收入不能为空!");
		blankBaseMap.put("crDept", "客户部门不能为空!");
		blankBaseMap.put("crDuty", "客户职务不能为空!");
		blankBaseMap.put("crTitle", "客户职称不能为空!");
		blankBaseMap.put("crGather", "客户经理不能为空!");
		blankBaseMap.put("crSource", "客户信息来源不能为空!");
		blankBaseMap.put("crGatherDate", "客户采集日期不能为空!");

		blankBaseMap.put("crIssueDate", "客户证件签发日期不能为空!");
		blankBaseMap.put("crExpiryDate", "客户证件失效日期不能为空!");
		blankBaseMap.put("crOccupation", "客户职业不能为空!");
		blankBaseMap.put("crFileAccept", "客户文件接收方式不能为空!");
		blankBaseMap.put("crEmail", "客户Emial不能为空!");

		blankBaseMap.put("issueAddress", "客户发证机关地址不能为空!");
		blankBaseMap.put("mobilePhone", "客户手机不能为空!");

		blankBaseMap.put("hujiTel", "客户户籍电话不能为空!");
		blankBaseMap.put("familyTel", "客户家庭电话不能为空!");
		blankBaseMap.put("workTel", "客户工作电话不能为空!");

		blankBaseMap.put("postalAddress", "客户通讯地址不能为空!");
		blankBaseMap.put("postalAddressZipCode", "客户通讯地址的邮编不能为空!");

		blankBaseMap.put("contactPerson", "客户紧急联系人不能为空!");
		blankBaseMap.put("contactPersonName", "客户紧急联系人姓名不能为空!");
		blankBaseMap.put("contactPersonSex", "客户紧急联系人性别不能为空!");
		blankBaseMap.put("cpContactpersonType", "客户联系人类型不能为空!");
		blankBaseMap.put("contactPersonRelation", "客户紧急联系人与客户关系不能为空!");
		blankBaseMap.put("contactPersonMobilePhone", "客户紧急联系人移动电话不能为空!");

		blankBaseMap.put("cpName", "(客户紧急联系人)姓名不能为空!");
		blankBaseMap.put("cpSex", "(客户紧急联系人)性别不能为空!");
		blankBaseMap.put("cpIdtype", "(客户紧急联系人)身份证类型不能为空!");
		blankBaseMap.put("cpId", "(客户紧急联系人)身份证号码不能为空!");

		blankBaseMap.put("contactPersonMx",
				"客户联系人至少录入 两条家庭联系人信息,两条工作联系人信息,一条紧急联系人信息!");
		blankBaseMap.put("contactPersonJY",
				"客户联系人至少录入 两条直系亲属联系人信息,两条工作联系人信息,两条紧急联系人信息!");

	}

	public static Customer getCustomer(Long id) {
		return customerService.getCustomer(id);
	}

	/**
	 * 根据客户id获取电话集合
	 * 
	 * @author zhanghao
	 * @date 2013-01-21 17:29:47
	 * @param id
	 *            客户id
	 * @return 电话集合
	 */
	public static List<Tel> getTelList(Long id) {
		return telService.findALlByCustomerid(id);
	}

	/**
	 * 根据客户id获取地址集合
	 * 
	 * @author zhanghao
	 * @date 2013-01-21 17:30:19
	 * @param id
	 *            客户id
	 * @return 地址集合
	 */
	public static List<Addr> getAddrList(Long id) {
		return addrService.findAllByCustomerid(id);
	}

	/**
	 * 获取区域的名称
	 * 
	 * @author zhanghao
	 * @date 2013-01-21 16:43:16
	 * @param id
	 *            区域id
	 * @return 名称
	 */
	public static String getAreaName(Long id) {
		return id != null ? areaService.getAreaById(String.valueOf(id))
				.getName() : "";
	}

	/**
	 * 获取区域的名称
	 * 
	 * @author zhanghao
	 * @date 2013-01-21 16:43:16
	 * @param id
	 *            区域id
	 * @return 名称
	 */
	public static String getAreaName(String id) {
		return id != null ? areaService.getAreaById(id).getName() : "";
	}

	/**
	 * 根据地址对象取邮编
	 * 
	 * @author zhanghao
	 * @date 2013-01-21 16:43:47
	 * @param addr
	 *            地址对象
	 * @return 邮编
	 */
	public static String getPostCode(Addr addr) {
		return addr != null ? addr.getArZipCode() : "";
	}

	/**
	 * 获取移动电话
	 * 
	 * @author zhanghao
	 * @date 2013-01-21 16:44:17
	 * @param telList
	 *            用户的电话对象集合
	 * @return 移动电话
	 */
	public static String getMobilePhone(List<Tel> telList) {
		return formatTel(getTel(telList, getParaValue(telType, "手机"),
				getParaValue("priority", "高")));
	}

	/**
	 * 获取联系电话
	 * 
	 * @author zhanghao
	 * @date 2013-01-21 16:45:14
	 * @param telList
	 *            用户的电话对象集合
	 * @return 联系电话
	 */
	public static String getTelephone(List<Tel> telList) {
		return formatTel(getTel(telList, getParaValue(telType, "家庭电话"),
				getParaValue("priority", "高")));
	}

	/**
	 * 获取传真号码
	 * 
	 * @author zhanghao
	 * @date 2013-01-21 16:45:42
	 * @param telList
	 *            用户的电话对象集合
	 * @return 传真号码
	 */
	public static String getFax(List<Tel> telList) {
		return formatTel(getTel(telList, getParaValue(telType, "传真"),
				getParaValue("priority", "高")));
	}

	/**
	 * 获取发证机关地址
	 * 
	 * @author zhanghao
	 * @date 2013-01-21 16:46:06
	 * @param addrList
	 *            用户的地址对象集合
	 * @return 发证机关地址
	 */
	public static String getIssueAddress(List<Addr> addrList) {
		return formatAddr(getAddr(addrList, getParaValue(addrType, "发证机关地址"),
				getParaValue("priority", "高")));
	}

	/**
	 * 获取通讯地址
	 * 
	 * @author zhanghao
	 * @date 2013-01-21 16:46:47
	 * @param addrList
	 *            用户的地址对象集合
	 * @return 通讯地址
	 */
	public static String getPostalAddress(List<Addr> addrList) {
		return formatAddr(getPostAddrObject(addrList));
	}

	/**
	 * 获取通讯地址对象
	 * 
	 * @author zhanghao
	 * @date 2013-01-21 22:08:18
	 * @param addrList
	 *            用户的地址对象集合
	 * @return 通讯地址对象
	 */
	public static Addr getPostAddrObject(List<Addr> addrList) {
		return getAddr(addrList, getParaValue(addrType, "联系地址"),
				getParaValue("priority", "高"));
	}

	/**
	 * 获取参数value
	 * 
	 * @author zhanghao
	 * @date 2013-01-21 22:13:41
	 * @param type
	 *            类型
	 * @param name
	 *            名称
	 * @return 值
	 */
	public static String getParaValue(String type, String name) {
		Parameter parameter = SystemParameterHelper
				.findParameterByPrTypeAndPrName(type, name);
		return parameter == null ? "" : parameter.getPrValue();
	}

	/**
	 * 获取紧急联系人
	 * 
	 * @author zhanghao
	 * @date 2013-01-24 16:33:18
	 * @param id
	 *            客户id
	 * @return 联系人
	 */
	public static Contactperson getEmergencyContact(Long id) {
		return contactService.findLastContactPerson(id);
	}

	/**
	 * 验证客户必填信息是否完整
	 * 
	 * @author zhanghao
	 * @date 2013-01-23 16:37:44
	 * @param obj
	 *            客户id
	 * @return 不能为空字段信息集合
	 */
	public static List<String> validateCustomerRequird(Customer obj) {
		List<String> messageList = new ArrayList<String>();
		// 发证机关所在地非空验证
		// if (isBlankIssueAddress(obj.getId())) {
		// messageList.add(blankBaseMap.get("issueAddress"));
		// }
		// 移动电话非空验证
		if (isBlankMobilePhone(obj.getId())) {
			messageList.add(blankBaseMap.get("mobilePhone"));
		}
		// 通讯地址非空验证
		validatePostalAddress(obj.getId(), messageList);

		// 紧急联系人非空验证
		validateContactPerson(obj.getId(), messageList);

		// 验证其他字段不能非空
		for (String fieldName : CustomerHelper.validateCustomerRequird(obj,
				fields)) {
			messageList.add(blankBaseMap.get(fieldName));
		}
		// Email不能为空
		if (!StringUtils.isBlank(obj.getCrFileAccept())) {
			if ((obj.getCrFileAccept()
					.equals(getParaValue("fileReceive", "电邮")) || obj
					.getCrFileAccept().equals(
							getParaValue("fileReceive", "两者都选")))) {
				if (StringUtils.isBlank(obj.getCrEmail()))
					messageList.add(blankBaseMap.get("crEmail"));
			}
		}
		return messageList;
	}

	/**
	 * 验证客户必填信息是否完整
	 * 
	 * @author zhanghao
	 * @date 2013-03-06 15:29:41
	 * @param id
	 *            客户id
	 * @return 不能为空字段信息集合
	 */
	public static List<String> validateCustomerRequird(Long id) {
		return validateCustomerRequird(customerService.getCustomer(id));
	}

	/**
	 * 验证通讯地址是否合法
	 * 
	 * @author zhanghao
	 * @date 2013-03-06 14:28:01
	 * @param id
	 *            客户id
	 * @param messageList
	 *            不能为空的字段消息集合
	 */
	public static void validatePostalAddress(Long id, List<String> messageList) {
		Addr addr = getPostalAddress(id);
		if (addr == null) {
			messageList.add(blankBaseMap.get("postalAddress"));
		} else {
			if (StringUtils.isBlank(addr.getArZipCode())) {
				messageList.add(blankBaseMap.get("postalAddressZipCode"));
			}
		}
	}

	/**
	 * 验证联系人对象是否合法
	 * 
	 * @author zhanghao
	 * @date 2013-03-06 14:35:04
	 * @param id
	 *            客户id
	 * @param messageList
	 *            不能为空的字段消息集合
	 */
	public static void validateContactPerson(Long id, List<String> messageList) {
		Contactperson person = contactService.findLastContactPerson(id);
		if (person == null) {
			messageList.add(blankBaseMap.get("contactPerson"));
		} else {
			if (StringUtils.isBlank(person.getCpName())) {
				messageList.add(blankBaseMap.get("contactPersonName"));
			}
			if (StringUtils.isBlank(person.getCpSex())) {
				messageList.add(blankBaseMap.get("contactPersonSex"));
			}
			if (StringUtils.isBlank(person.getCpRelation())) {
				messageList.add(blankBaseMap.get("contactPersonRelation"));
			}
			if (getTel(person.getId(), ParamConstant.CUSTOMER_TYPE_LINKMAN,
					getParaValue(telType, "手机")) == null) {
				messageList.add(blankBaseMap.get("contactPersonMobilePhone"));
			}
		}
	}


	/**
	 * 验证民信理财联系人对象是否合法
	 * 
	 * @author jinghr
	 * @date 2013-7-30 15:45:09
	 * @param id
	 *            客户id
	 * @param messageList
	 *            不能为空的字段消息集合
	 */
/*	public static void validateContactPersonMXLC(Long id,
			List<String> messageList) {
		List<Contactperson> personList = contactService
				.findAllContactPersonMx(id);
		if (personList.size() > 0) {
			for (Contactperson person : personList) {
				// 验证紧急联系人必填字段
				for (String fieldName : CustomerHelper
						.validateContactpersonRequird(person,
								mxlcContactPersonFields)) {
					messageList.add(person.getCpName()
							+ blankBaseMap.get(fieldName));
				}
				// 验证紧急联系人移动电话
				boolean telExist = getTel(person.getId(),
						ParamConstant.CUSTOMER_TYPE_LINKMAN,
						getParaValue(telType, "手机")) == null ? false : true;
				if (!telExist)
					messageList.add(person.getCpName() + ",(客户联系人)移动电话不能为空！");
			}
		} else {
			messageList.add(blankBaseMap.get("contactPerson"));
		}
	}*/

	/**
	 * 紧急联系人是否为空
	 * 
	 * @author zhanghao
	 * @date 2013-01-24 17:26:51
	 * @param id
	 *            客户id
	 * @return 是否为空
	 */
	public static boolean isBlankContactPerson(Long id) {
		boolean flag = true;
		Contactperson person = contactService.findLastContactPerson(id);
		if (person == null) {
			flag = false;
		}
		if (flag && StringUtils.isBlank(person.getCpName())) {
			flag = false;
		}
		if (flag && StringUtils.isBlank(person.getCpSex())) {
			flag = false;
		}
		if (flag && StringUtils.isBlank(person.getCpRelation())) {
			flag = false;
		}
		if (flag) {
			flag = getTel(person.getId(), ParamConstant.CUSTOMER_TYPE_LINKMAN,
					getParaValue(telType, "手机")) == null ? false : true;
		}
		return flag;
	}

	/**
	 * 客户发证机关所在地是否为空
	 * 
	 * @author zhanghao
	 * @date 2013-01-24 11:05:00
	 * @param id
	 *            客户id
	 * @return
	 */
	public static boolean isBlankIssueAddress(Long id) {
		// return getIssueAddress(id) == null ? true : false;
		Addr addr = getIssueAddress(id);
		if (addr == null || addr.getArAddrDetail() == null
				|| addr.getArAddrDetail().trim().equals(""))
			return true;
		else {
			return false;
		}
	}

	/**
	 * 客户移动电话是否为空
	 * 
	 * @author zhanghao
	 * @date 2013-01-24 11:05:17
	 * @param id
	 *            客户id
	 * @return
	 */
	public static boolean isBlankMobilePhone(Long id) {
		return getMobilePhone(id) == null ? true : false;
	}

	/**
	 * 客户移动电话是否为空
	 * 
	 * @author jingHr
	 * @date 2013-7-30 15:24:36
	 * @param id
	 *            客户id
	 * @return
	 */
	public static boolean isBlankMobile(Long id) {
		/*
		 * String telNum = getMobilePhone(id).getTlTelNum(); return telNum ==
		 * null || telNum.equals("")? true : false;
		 */
		return null == getMobilePhone(id) ? true : false;
	}

	/**
	 * 客户户籍电话是否为空
	 * 
	 * @author jingHr
	 * @date 2013-7-30 15:24:36
	 * @param id
	 *            客户id
	 * @return
	 */
	public static boolean isBlankHujiTel(Long id) {
		/*
		 * String telNum = getHujiTel(id).getTlTelNum(); return telNum == null
		 * || telNum.equals("")? true : false;
		 */
		return null == getHujiTel(id) ? true : false;
	}

	/**
	 * 客户家庭电话是否为空
	 * 
	 * @author jingHr
	 * @date 2013-7-30 15:24:36
	 * @param id
	 *            客户id
	 * @return
	 */
	public static boolean isBlankFamilyTel(Long id) {
		/*
		 * String telNum = getFamilyTel(id).getTlTelNum(); return telNum == null
		 * || telNum.equals("")? true : false;
		 */
		return null == getFamilyTel(id) ? true : false;
	}

	/**
	 * 客户工作电话是否为空
	 * 
	 * @author jingHr
	 * @date 2013-7-30 15:24:36
	 * @param id
	 *            客户id
	 * @return
	 */
	public static boolean isBlankWorkTel(Long id) {
		/*
		 * String telNum = getWorkTel(id).getTlTelNum(); return telNum == null
		 * || telNum.equals("")? true : false;
		 */
		return null == getWorkTel(id) ? true : false;
	}

	/**
	 * 客户通讯地址是否为空
	 * 
	 * @author zhanghao
	 * @date 2013-01-24 11:05:36
	 * @param id
	 * @return
	 */
	public static boolean isBlankPostalAddress(Long id) {
		boolean flag = true;
		Addr addr = getPostalAddress(id);
		if (flag && addr == null) {
			flag = false;
		}
		if (flag && StringUtils.isBlank(addr.getArZipCode())) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 客户通讯地址是否为空
	 * 
	 * @author zhanghao
	 * @date 2013-01-24 11:05:36
	 * @param id
	 * @return
	 */
	public static boolean isBlankNowAddress(Long id) {
		boolean flag = true;
		Addr addr = getPostalAddress(id);
		if (addr != null) {
			return false;
		}
		return flag;
	}

	/**
	 * 客户户籍地址是否为空
	 * 
	 * @author Jinghr
	 * @date 2013-7-31 14:13:30
	 * @param id
	 * @return
	 */
	public static boolean isBlankHujiAddress(Long id) {
		boolean flag = true;
		Addr addr = getHujiAddress(id);
		if (addr != null) {
			return false;
		}
		return flag;
	}

	/**
	 * 客户工作地址是否为空
	 * 
	 * @author Jinghr
	 * @date 2013-7-31 14:13:35
	 * @param id
	 * @return
	 */
	public static boolean isBlankWorksAddress(Long id) {
		boolean flag = true;
		Addr addr = getWorksAddress(id);
		if (addr != null) {
			return false;
		}
		return flag;
	}

	/**
	 * 根据客户id获取户籍地址
	 * 
	 * @author Jinghr
	 * @date 2013-7-31 14:10:21
	 * @param id
	 *            客户id
	 * @return户籍地址
	 */
	public static Addr getHujiAddress(Long id) {
		return getAddr(id, ParamConstant.CUSTOMER_TYPE_MY, "3");
	}

	/**
	 * 根据客户id获取单位地址
	 * 
	 * @author Jinghr
	 * @date 2013-7-31 14:10:34
	 * @param id
	 *            客户id
	 * @return 单位地址
	 */
	public static Addr getWorksAddress(Long id) {
		return getAddr(id, ParamConstant.CUSTOMER_TYPE_MY, "2");
	}

	/**
	 * 根据客户id获取发证机关地址
	 * 
	 * @author zhanghao
	 * @date 2013-01-24 17:06:22
	 * @param id
	 *            客户id
	 * @return 发证机关地址
	 */
	public static Addr getIssueAddress(Long id) {
		return getAddr(id, ParamConstant.CUSTOMER_TYPE_MY,
				getParaValue(addrType, "发证机关地址"));
	}

	/**
	 * 根据联系人id获取民信家庭地址
	 * 
	 * @author Jinghr
	 * @date 2013-7-30 17:22:45
	 * @param id
	 *            客户id
	 * @return 家庭地址
	 */
	public static boolean getFamilyAddress(Long id) {

		List<Addr> detail = getAddrMx(id, "1", "1");// 1 =
													// getParaValue(addrType,
													// "家庭地址")
		/*
		 * if(detail!=null) for(Addr addr : detail){
		 * if(addr.getArAddrDetail()!=null &&
		 * !addr.getArAddrDetail().equals("")) return false; } return true;
		 */
		return null == detail ? true : false;
	}

	/**
	 * 根据联系人id获取民信单位地址
	 * 
	 * @author Jinghr
	 * @date 2013-7-30 17:22:49
	 * @param id
	 *            客户id
	 * @return 发证单位地址
	 */
	public static boolean getWorkAddress(Long id) {
		List<Addr> detail = getAddrMx(id, "1", "2"); // 2 =
														// getParaValue(addrType,
														// "单位地址")
		/*
		 * if(detail!=null) for(Addr addr : detail){
		 * if(addr.getArAddrDetail()!=null &&
		 * !addr.getArAddrDetail().equals("")) return false; } return true;
		 */
		return null == detail ? true : false;
	}

	/**
	 * 根据客户id获取联系地址
	 * 
	 * @author zhanghao
	 * @date 2013-01-24 17:06:52
	 * @param id
	 *            客户id
	 * @return 联系地址
	 */
	public static Addr getPostalAddress(Long id) {
		return getAddr(id, ParamConstant.CUSTOMER_TYPE_MY,
				getParaValue(addrType, "联系地址"));
	}

	/**
	 * 根据客户id获取移动电话
	 * 
	 * @author zhanghao
	 * @date 2013-01-24 17:08:05
	 * @param id
	 *            客户id
	 * @return 移动电话
	 */
	public static Tel getMobilePhone(Long id) {
		return getTel(id, ParamConstant.CUSTOMER_TYPE_MY, "3"); // 3 =
																// getParaValue(telType,
																// "手机")
	}

	/**
	 * 根据客户id获取户籍电话
	 * 
	 * @author Jinghr
	 * @date 2013-7-30 15:16:34
	 * @param id
	 *            客户id
	 * @return 户籍电话
	 */
	public static Tel getHujiTel(Long id) {
		Tel tel = getTel(id, ParamConstant.CUSTOMER_TYPE_MY, "5");
		return tel; // 5 = getParaValue(telType, "户籍联系电话")
	}

	/**
	 * 根据客户id获取家庭电话
	 * 
	 * @author Jinghr
	 * @date 2013-7-30 15:16:34
	 * @param id
	 *            客户id
	 * @return 家庭电话
	 */
	public static Tel getFamilyTel(Long id) {
		return getTel(id, ParamConstant.CUSTOMER_TYPE_MY, "1");// 1 =
																// getParaValue(telType,
																// "家庭电话")
	}

	/**
	 * 根据客户id获取单位电话
	 * 
	 * @author Jinghr
	 * @date 2013-7-30 15:16:34
	 * @param id
	 *            客户id
	 * @return 家庭电话
	 */
	public static Tel getWorkTel(Long id) {
		return getTel(id, ParamConstant.CUSTOMER_TYPE_MY, "2"); // getParaValue(telType,
																// "工作电话")
	}

	/**
	 * 根据地址类型查询客户优先级最高的地址信息
	 * 
	 * @author zhanghao
	 * @date 2013-01-24 17:08:56
	 * @param id
	 *            客户id
	 * @param cType
	 *            客户类型 0客户自己,1联系人
	 * @param type
	 *            地址类型
	 * @return 地址对象
	 */
	public static Addr getAddr(Long id, String cType, String type) {
		List<Addr> addrList = addrService
				.findALlByCustomeridAndArCustTypeAndArTelTypeAndArPriority(id,
						cType, type, getParaValue("priority", "高"));
		return addrList.size() > 0 ? addrList.get(0) : null;
	}

	/**
	 * 根据地址类型查询民信客户地址信息
	 * 
	 * @author jinghr
	 * @date 2013-7-30 17:26:58
	 * @param id
	 *            客户id
	 * @param cType
	 *            客户类型 0客户自己,1联系人
	 * @param type
	 *            地址类型
	 * @return 地址对象
	 */
	public static List<Addr> getAddrMx(Long id, String cType, String type) {
		List<Addr> addrList = addrService
				.findByCustomeridAndTlCustTypeAndTlTelType(id, cType, type);
		return addrList.size() > 0 ? addrList : null;
	}

	/**
	 * 根据电话类型查询客户优先级最高的地址信息
	 * 
	 * @author zhanghao
	 * @date 2013-01-24 17:13:31
	 * @param id
	 *            客户id
	 * @param cType
	 *            客户类型 0客户自己,1联系人
	 * @param type
	 *            电话类型
	 * @return 电话对象
	 */
	public static Tel getTel(Long id, String cType, String type) {
		List<Tel> telList = telService
				.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(id,
						cType, type, getParaValue("priority", "高"));
		return telList.size() > 0 ? telList.get(0) : null;
	}

	public static boolean getTelList(Long id, String cType) {
		List<Tel> telList = telService
				.findByCustomeridAndTlCustTypeAndTlTelTypeWithoutValid(id,
						cType);
		if (telList.size() > 0) {
			for (Tel tel : telList)
				if (tel.getTlTelNum() != null && !tel.getTlTelNum().equals(""))
					return false;
		}
		return true;
	}

	/**
	 * 根据地址类型过滤地址对象
	 * 
	 * @author zhanghao
	 * @date 2013-01-21 16:49:18
	 * @param addrList
	 *            地址集合
	 * @param type
	 *            地址类型
	 * @param priority
	 *            优先级
	 * @return 地址对象
	 */
	public static Addr getAddr(List<Addr> addrList, String type, String priority) {
		Addr addr = null;
		for (Addr addr1 : addrList) {
			if (addr1.getArAddrType().equals(type)
					&& addr1.getArPriority().equals(priority)) {
				addr = addr1;
			}
		}
		return addr;
	}

	/**
	 * 根据电话类型过滤电话对象
	 * 
	 * @author zhanghao
	 * @date 2013-01-21 16:49:39
	 * @param telList
	 *            电话对象集合
	 * @param type
	 *            电话类型
	 * @param priority
	 *            优先级
	 * @return 电话对象
	 */
	public static Tel getTel(List<Tel> telList, String type, String priority) {
		Tel tel = null;
		for (Tel tel1 : telList) {
			if (tel1.getTlTelType().equals(type)
					&& tel1.getTlPriority().equals(priority)) {
				tel = tel1;
			}
		}
		return tel;
	}

	/**
	 * 格式化地址（省+市+县+街道+详细地址）
	 * 
	 * @author zhanghao
	 * @date 2013-01-21 16:48:12
	 * @param addr
	 *            地址对象
	 * @return 地址
	 */
	public static String formatAddr(Addr addr) {
		String addrVal = "";
		if (addr != null) {
			addrVal += getAreaName(addr.getArProvince());
			if (!StringUtils.isBlank(addrVal))
				addrVal = filterMunicipality(addrVal);// 过滤直辖市的省份
			addrVal += getAreaName(addr.getArCity());
			addrVal += getAreaName(addr.getArCounty());
			addrVal += addr.getArStreet() != null ? addr.getArStreet() : "";
			addrVal += addr.getArAddrDetail() != null ? addr.getArAddrDetail()
					: "";
		}
		return addrVal;
	}

	/**
	 * 过滤掉直辖市的省份
	 * 
	 * @author zhanghao
	 * @date 2013-03-07 11:22:55
	 * @param municipality
	 * @return
	 */
	public static String filterMunicipality(String municipality) {
		String[] municipalitys = new String[] { "北京市", "上海市", "重庆市", "天津市" };
		for (String value : municipalitys) {
			if (municipality.equals(value)) {
				return "";
			}
		}
		return municipality;
	}

	/**
	 * 格式化电话（区号+电话+分机号）
	 * 
	 * @author zhanghao
	 * @date 2013-01-21 16:48:49
	 * @param tel
	 *            电话对象
	 * @return 电话号码
	 */
	public static String formatTel(Tel tel) {
		String telVal = "";
		if (tel != null) {
			telVal += StringUtils.isNotBlank(tel.getTlAreaCode()) ? (tel
					.getTlAreaCode() + "-") : "";
			telVal += tel.getTlTelNum();
			telVal += StringUtils.isNotBlank(tel.getTlExtCode()) ? ("-" + tel
					.getTlExtCode()) : "";
		}
		return telVal;
	}

	/**
	 * 格式化证件类型
	 * 
	 * @author zhanghao
	 * @date 2013-01-21 17:33:52
	 * @param val
	 *            证件类型参数
	 * @return 证件类型
	 */
	public static String formatIdtype(String val) {
		if (StringUtils.isBlank(val))
			return null;
		Parameter parameter = SystemParameterHelper
				.findParameterByPrTypeAndPrValue("idtype", val);
		return parameter == null ? null : parameter.getPrName();
	}

	/**
	 * 格式化关系
	 * 
	 * @author zhanghao
	 * @date 2013-01-24 18:11:53
	 * @param val
	 *            关系类型参数
	 * @return 关系
	 */
	public static String formatRelation(String val) {
		if (StringUtils.isBlank(val))
			return null;
		Parameter parameter = SystemParameterHelper
				.findParameterByPrTypeAndPrValue("relation", val);
		return parameter == null ? null : parameter.getPrName();
	}

	/**
	 * 验证客户信息必填项
	 * 
	 * @param propertys
	 *            字段集合
	 * @return 空值字段集合
	 */
	public static List<String> validateCustomerRequird(Customer customer,
			String... propertys) {
		List<String> nullPro = Lists.newArrayList();
		for (String property : propertys) {
			String methodName = "get" + property.substring(0, 1).toUpperCase()
					+ property.substring(1);
			try {
				Method method = Customer.class.getMethod(methodName,
						new Class[] {});
				Object value = method.invoke(customer, new Object[] {});
				if (null == value)
					nullPro.add(property);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				logger.error("没有get方法！");
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return nullPro;
	}

	/**
	 * 验证联系人信息必填项
	 * 
	 * @param propertys
	 *            字段集合
	 * @return 空值字段集合
	 */
	public static List<String> validateContactpersonRequird(Contactperson cp,
			String... propertys) {
		List<String> nullPro = Lists.newArrayList();
		for (String property : propertys) {
			String methodName = "get" + property.substring(0, 1).toUpperCase()
					+ property.substring(1);
			try {
				Method method = Contactperson.class.getMethod(methodName,
						new Class[] {});
				Object value = method.invoke(cp, new Object[] {});
				if (null == value)
					nullPro.add(property);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				logger.error("没有get方法！");
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return nullPro;
	}

	/**
	 * 民信客户基本信息验证
	 * 
	 * @author Yuan Changchun
	 * @date 2013-7-18 下午04:11:48
	 * @param customer
	 * @return
	 */
/*	public static List<String> validateMXCustomerRequird(Customer obj) {
		ArrayList<String> messageList = new ArrayList<String>();
		// 验证客户必填字段
		for (String fieldName : CustomerHelper.validateCustomerRequird(obj,
				mxFields)) {
			messageList.add(blankBaseMap.get(fieldName));
		}
		// 客户联系电话
		if (isBlankMobile(obj.getId())) {
			messageList.add(blankBaseMap.get("mobilePhone"));
		}
		
		 * //客户户籍电话 if(isBlankHujiTel(obj.getId())){
		 * messageList.add(blankBaseMap.get("hujiTel")); } //客户家庭电话
		 * if(isBlankFamilyTel(obj.getId())){
		 * messageList.add(blankBaseMap.get("familyTel")); }
		 
		// 客户工作电话
		if (isBlankWorkTel(obj.getId())) {
			messageList.add(blankBaseMap.get("workTel"));
		}
		// 客户户籍住址
		if (isBlankHujiAddress(obj.getId())) {
			messageList.add("客户户籍地址不能为空");
		}
		// 客户现住地址
		if (isBlankNowAddress(obj.getId())) {
			messageList.add(blankBaseMap.get("postalAddress"));
		}
		// 客户单位地址
		if (isBlankWorksAddress(obj.getId())) {
			messageList.add("客户单位地址不能为空！");
		}
		// 紧急联系人验证
		validateContactPersonMX(obj.getId(), messageList);

		return messageList;
	}*/

	/**
	 * 民信客户理财基本信息验证
	 * 
	 * @author Jinghr
	 * @date 2013-8-6 17:05:00
	 * @param customer
	 * @return list
	 */
/*	public static List<String> validateMXLCCustomerRequird(Customer obj) {
		ArrayList<String> messageList = new ArrayList<String>();
		// 验证客户必填字段
		for (String fieldName : CustomerHelper.validateCustomerRequird(obj,
				mxlcFields)) {
			messageList.add(blankBaseMap.get(fieldName));
		}

		// 验证客户邮箱
		if (obj.getCrFileAccept() != null) {
			if (obj.getCrFileAccept().equals("1")
					|| obj.getCrFileAccept().equals("3")) {
				if (null == obj.getCrEmail() || "".equals(obj.getCrEmail())) {
					messageList.add("客户邮箱不能为空！");
				}
			}
		}
		// 验证客户邮编
		validatePostalAddress(obj.getId(), messageList);

		// 客户联系电话
		if (isBlankMobile(obj.getId())) {
			messageList.add(blankBaseMap.get("mobilePhone"));
		}
		// 紧急联系人验证
		validateContactPersonMXLC(obj.getId(), messageList);

		return messageList;
	}*/

/*	*//**
	 * 捷越信贷客户信息验证
	 * 
	 * @author Yuan Changchun
	 * @date 2013-11-15 上午09:07:08
	 * @param customer
	 * @return
	 *//*
	public static List<String> validateJYCreditCustomerRequird(Customer customer) {
		ArrayList<String> messageList = new ArrayList<String>();
		// 验证客户必填字段
		for (String fieldName : CustomerHelper.validateCustomerRequird(
				customer, jyCreditCustomerField)) {
			messageList.add(blankBaseMap.get(fieldName));
		}
		// 客户联系电话
		if (isBlankMobile(customer.getId())) {
			messageList.add(blankBaseMap.get("mobilePhone"));
		}
		// 客户工作电话
		if (isBlankWorkTel(customer.getId())) {
			messageList.add(blankBaseMap.get("workTel"));
		}
		// 客户户籍住址
		if (isBlankHujiAddress(customer.getId())) {
			messageList.add("客户户籍地址不能为空");
		}
		// 客户现住地址
		if (isBlankNowAddress(customer.getId())) {
			messageList.add(blankBaseMap.get("postalAddress"));
		}
		// 客户单位地址
		if (isBlankWorksAddress(customer.getId())) {
			messageList.add("客户单位地址不能为空！");
		}
		// 紧急联系人验证
		validateContactPersonJY(customer.getId(), messageList);
		return messageList;
	}*/

	/**
	 * 验证捷越信贷联系人是否合法
	 * 
	 * @author Yuan Changchun
	 * @date 2013-11-22 上午09:29:26
	 * @param id
	 * @param messageList
	 */
	/*private static void validateContactPersonJY(Long id,
			ArrayList<String> messageList) {
		List<Contactperson> personList = contactService
				.findAllContactPersonMx(id);
		if (personList == null) {
			messageList.add(blankBaseMap.get("contactPerson"));
		} else if (personList.size() < 6) { // 最低录入联系人数目信息
			messageList.add(blankBaseMap.get("contactPersonJY"));
		} else {
			int familyNum = 0, workNum = 0, importantNum = 0, leastCompleteNum = 2, leastComplete = 2;
			for (Contactperson person : personList) {
				if (person.getCpContactpersonType() != null) {
					if (person.getCpContactpersonType().equals("2"))
						importantNum++;
					if (person.getCpContactpersonType().equals("3"))
						familyNum++;
					if (person.getCpContactpersonType().equals("4"))
						workNum++;
				}
				if (StringUtils.isBlank(person.getCpName())) {
					messageList.add("客户联系人名称不能为空！");
				}
				if (StringUtils.isBlank(person.getCpContactpersonType())) {
					messageList.add(person.getCpName() + "(客户联系人)类型不能为空！");
				}
				if (StringUtils.isBlank(person.getCpRelation())) {
					messageList.add(person.getCpName() + "(客户联系人)与客户关系不能为空！");
				}
				// if(StringUtils.isBlank(person.getCpWorkunit())) {
				// messageList.add(person.getCpName()+"(客户联系人)单位名称不能为空！");
				// }
				// if(StringUtils.isBlank(person.getCpPost())) {
				// messageList.add(person.getCpName()+"(客户联系人)职务不能为空！");
				// }
				if (getTelList(person.getId(), "1")) { // ParamConstant.CUSTOMER_TYPE_LINKMAN
					messageList.add(person.getCpName() + "(客户联系人)联系电话不能为空！");
				}
				// if(getFamilyAddress(person.getId()) &&
				// getWorkAddress(person.getId())) {
				// messageList.add(person.getCpName()+"(客户联系人)家庭或单位地址不能为空！");
				// }
			}
			if (familyNum < leastCompleteNum) {
				messageList.add("至少还需录入" + (leastCompleteNum - familyNum)
						+ "条家庭联系人信息");
			}
			if (workNum < leastCompleteNum) {
				messageList.add("至少还需录入" + (leastCompleteNum - workNum)
						+ "条工作联系人信息");
			}
			if (importantNum < leastComplete) {
				messageList.add("至少还需录入" + (leastComplete - importantNum)
						+ "条紧急联系人信息");
			}
		}
	}*/

	/**
	 * @Title:新增或返回银行卡信息
	 * @Description: 根据传进的银行卡信息判断库中是否已存在，不存在就加一条新数据，返回带id的值
	 * @param 传进的Bankaccount类
	 * @return Map中参数值： MSG--成功失败信息 （成功：success,出错:error）
	 *         result--查询出来的Bankaccount
	 * @throws
	 * @time:2014-11-13 下午12:01:47
	 * @author:Sam.J
	 */
	/*public static Map saveOrGetBankByCustomer(Bankaccount b) {
		Map<String, Object> m_result = new HashMap<String, Object>();
		try {
			List<Bankaccount> list = bankaccountService.findALlByCustomerid(b
					.getCrmCustomer().getId()); // 通过传来的customerid
												// 查出所有的该客户的已有银行卡
			boolean flag = false; // 判断位，用来判断是否已经存在该银行卡
			for (Bankaccount ba : list) {
				if (ba.getBaAccount().equals(b.getBaAccount())
						&& ba.getBaAccountName().equals(b.getBaAccountName())) {
					flag = true; // 如果账户名和账号号码匹配原有的数据 flag设true 将传来的b用库中的数据代替
					b = ba;
					break;
				}
			}
			if (!flag) {
				bankaccountService.saveBankaccount(b);
			}
			m_result.put("MSG", "success");
		} catch (Exception e) {
			logger.info("", e);
			m_result.put("MSG", "error");
		}
		m_result.put("result", b);
		return m_result;
	}*/

	/**
	 * @Title:客户名字处理
	 * @Description: 去掉开头跟结尾的空格
	 * @return
	 * @throws
	 * @time:2014-12-18 下午03:00:23
	 * @author:Sam.J
	 */
	public static String dealCustName(String name) {
		while (name.startsWith(" ") || name.startsWith("　")) { // 去掉开头的空格
			name = name.substring(1);
		}
		while (name.endsWith(" ") || name.endsWith("　")) {// 去掉结尾的空格
			name = name.substring(0, name.length() - 1);
		}
		return name;
	}

}