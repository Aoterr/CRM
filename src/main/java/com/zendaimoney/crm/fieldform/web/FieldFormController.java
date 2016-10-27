package com.zendaimoney.crm.fieldform.web;

import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;
import com.zendaimoney.constant.ParamConstant;
import com.zendaimoney.crm.CrudUiController;
import com.zendaimoney.crm.contactperson.entiy.Contactperson;
import com.zendaimoney.crm.contactperson.service.ContactpersonService;
import com.zendaimoney.crm.customer.entity.Addr;
import com.zendaimoney.crm.customer.entity.Bankaccount;
import com.zendaimoney.crm.customer.entity.Car;
import com.zendaimoney.crm.customer.entity.Customer;
import com.zendaimoney.crm.customer.entity.House;
import com.zendaimoney.crm.customer.entity.PrivateOwners;
import com.zendaimoney.crm.customer.entity.Tel;
import com.zendaimoney.crm.customer.service.AddrService;
import com.zendaimoney.crm.customer.service.BankaccountService;
import com.zendaimoney.crm.customer.service.CarService;
import com.zendaimoney.crm.customer.service.CustomerService;
import com.zendaimoney.crm.customer.service.HouseService;
import com.zendaimoney.crm.customer.service.PrivateOwnersService;
import com.zendaimoney.crm.customer.service.TelService;
import com.zendaimoney.crm.fieldform.helper.formUtilHelper;
import com.zendaimoney.crm.modification.entity.Field;
import com.zendaimoney.crm.modification.service.FieldService;
import com.zendaimoney.crm.product.entity.BusiFinance;
import com.zendaimoney.crm.product.service.BusiFinanceService;

import com.zendaimoney.utils.helper.SpringContextHelper;
import com.zendaimoney.utils.helper.SystemParameterHelper;
import com.zendaimoney.utils.mapper.GenHtmlHelper;

/**
 * 变更单 采集数据表单
 * 
 * @author bianxj
 * 
 */
@Controller
@RequestMapping(value = "/crm/fieldform")
public class FieldFormController extends CrudUiController<Customer> {

	@Autowired
	private static CustomerService customerService;
	@Autowired
	private static ContactpersonService contactpersonservice;

	@Autowired
	private static FieldService fieldService;
	@Autowired
	private static BankaccountService bankaccountService;

	@Autowired
	private static TelService telService;
	@Autowired
	private static AddrService addrService;
	@Autowired
	private static BusiFinanceService financeService;
	@Autowired
	private static HouseService houseService;
	@Autowired
	private static PrivateOwnersService privateOwnersService;
	@Autowired
	private static CarService carService;

	static {
		contactpersonservice = SpringContextHelper
				.getBean("contactpersonService");
		customerService = SpringContextHelper.getBean("customerService");
		fieldService = SpringContextHelper.getBean("fieldService");
		bankaccountService = SpringContextHelper.getBean("bankaccountService");
		telService = SpringContextHelper.getBean("telService");
		addrService = SpringContextHelper.getBean("addrService");
		financeService = SpringContextHelper.getBean("busiFinanceService");
		houseService = SpringContextHelper.getBean("houseService");
		privateOwnersService = SpringContextHelper
				.getBean("privateOwnersService");
		carService = SpringContextHelper.getBean("carService");
	}

	public static String existFlag = null;

	@RequestMapping(value = { "" })
	public String index(Model model, ServletRequest request, Long objectId,
			Long fieldId, String objectClass) throws Exception {
		com.zendaimoney.crm.modification.entity.Field field = fieldService
				.getFieldById(fieldId);
		Class table = formUtilHelper.getTableBelong(field.getFdFieldEn());
		List fields = Lists.newArrayList();
		List items = Lists.newArrayList();

		if (objectId != null) {
			bankaccount(table, objectId, fields, items);
			customer(table, objectId, field, fields, items);
			contactperson(table, objectId, field, fields, items);
			tels(table, objectId, field, fields, items);
			addrs(table, objectId, field, fields, items);
			BusiFinance(table, objectId, field, fields, items);
			house(table, objectId, field, fields, items);
			privateOwner(table, objectId, field, fields, items);
			car(table, objectId, field, fields, items);
		} else {
			existFlag = "cp" + field.getFdReserve();
		}
		model.addAttribute("existFlag", existFlag);
		model.addAttribute("items", items);
		model.addAttribute("fields", fields);
		existFlag = null;
		return "crm/fieldform/genMultInput";

	}

	public static void BusiFinance(Class table, Long objectId, Field field,
			List fields, List items) {
		if (table.getName().equals(BusiFinance.class.getName())) {
			BusiFinance finance = financeService.findOneFinance(objectId);
			items.add(finance);
			fields.add(field);
		}
	}

	public static void customer(Class table, Long objectId, Field field,
			List fields, List items) {
		if (table.getName().equals(Customer.class.getName())) {
			Customer customer = customerService.getCustomer(objectId);
			items.add(customer);
			fields.add(field);
		}
	}

	public static void contactperson(Class table, Long objectId, Field field,
			List fields, List items) {
		if (table.getName().equals(Contactperson.class.getName())) {
			if (field.getFdClassEn().equals(ParamConstant.JJLXRXX)) {
				Contactperson contactpersons = contactpersonservice
						.findByid(objectId);
				if (null != contactpersons) {
					items.add(contactpersons);
					fields.add(field);
				} else {
					existFlag = "cp" + field.getFdReserve();
				}
			}
		}
	}

	public static void bankaccount(Class table, Long objectId, List fields,
			List items) {
		if (table.getName().equals(Bankaccount.class.getName())) {
			fields = getFieldsByClass(Bankaccount.class);
			items = bankaccountService.findALlByCustomerid(objectId);
		}
	}

	public static void tels(Class table, Long objectId, Field field,
			List fields, List items) {
		if (table.getName().equals(Tel.class.getName())) {
			tel(table, objectId, field, fields, items);
			cpTel(table, objectId, field, fields, items);
		}
	}

	public static void tel(Class table, Long objectId, Field field,
			List fields, List items) {
		if (field.getFdClassEn().equals(ParamConstant.TXXX)
				|| field.getFdClassEn().equals(ParamConstant.ZYXX)) {
			if (!field.getFdFieldEn().equals("tlPriority")) {
				Tel tel = telService
						.findByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
								objectId, "0", field.getFdReserve(),
								ParamConstant.priorityHighNum);
				if (tel == null) {
					existFlag = "crtel" + field.getFdReserve();
				} else {
					items.add(tel);// 客户的电话
					fields.add(field);
				}
			} else {
				List<Tel> tels = telService
						.findByCustomeridAndTlCustTypeAndTlTelType(objectId,
								"0", field.getFdReserve());
				if (tels.size() == 0) {
					existFlag = "crtel" + field.getFdReserve();
				} else {
					items.add(tels.get(0));// 客户的电话
					fields.add(field);
				}
			}
		}
	}

	public static void cpTel(Class table, Long objectId, Field field,
			List fields, List items) {
		if (field.getFdClassEn().equals(ParamConstant.JJLXRXX)) {
			Contactperson contactpersons = contactpersonservice
					.findByid(objectId);
			if (!field.getFdFieldEn().equals("tlPriority")) {
				Tel tel = new Tel();
				if (contactpersons != null) {
					tel = telService
							.findByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
									objectId, "1", field.getFdReserve(),
									ParamConstant.priorityHighNum);
				} else {
					tel = null;
				}

				if (tel == null) {
					existFlag = "cptel" + field.getFdReserve();
				} else {
					items.add(tel);// 联系人电话
					fields.add(field);
				}
			} else {
				List<Tel> tels = telService
						.findByCustomeridAndTlCustTypeAndTlTelType(objectId,
								"1", field.getFdReserve());
				if (tels.size() == 0) {
					existFlag = "cptel" + field.getFdReserve();
				} else {
					items.add(tels.get(0));// 客户的电话
					fields.add(field);
				}
			}
		}
	}

	public static void addrs(Class table, Long objectId, Field field,
			List fields, List items) {
		if (table.getName().equals(Addr.class.getName())) {
			addr(table, objectId, field, fields, items);
			cpAddr(table, objectId, field, fields, items);
		}
	}

	public static void addr(Class table, Long objectId, Field field,
			List fields, List items) {

		if (field.getFdClassEn().equals(ParamConstant.JBXX)
				|| field.getFdClassEn().equals(ParamConstant.TXXX)
				|| field.getFdClassEn().equals(ParamConstant.ZYXX)) {
			if (!field.getFdFieldEn().equals("arPriority")) {
				Addr addr = addrService
						.findByCustomeridAndArCustTypeAndArAddrTypeAndArPriority(
								objectId, "0", field.getFdReserve(),
								ParamConstant.priorityHighNum);
				if (addr == null) {
					existFlag = "craddr" + field.getFdReserve();
				} else {
					items.add(addr);// 客户的地址
					fields.add(field);
				}
			} else {
				List<Addr> addrs = addrService
						.findByCustomeridAndTlCustTypeAndTlTelType(objectId,
								"0", field.getFdReserve());
				if (addrs.size() == 0) {
					existFlag = "craddr" + field.getFdReserve();
				} else {
					items.add(addrs.get(0));// 客户的地址
					fields.add(field);
				}
			}
		}
	}

	public static void cpAddr(Class table, Long objectId, Field field,
			List fields, List items) {
		if (field.getFdClassEn().equals(ParamConstant.JJLXRXX)) {
			Contactperson contactpersons = contactpersonservice
					.findByid(objectId);
			if (!field.getFdFieldEn().equals("arPriority")) {
				Addr addr = new Addr();
				if (contactpersons != null) {
					addr = addrService
							.findByCustomeridAndArCustTypeAndArAddrTypeAndArPriority(
									objectId, "1", field.getFdReserve(),
									ParamConstant.priorityHighNum);
				} else {
					addr = null;
				}
				if (addr == null) {
					existFlag = "cpaddr" + field.getFdReserve();
				} else {
					items.add(addr);// 联系人地址
					fields.add(field);
				}
			} else {
				List<Addr> addrs = addrService
						.findByCustomeridAndTlCustTypeAndTlTelType(objectId,
								"1", field.getFdReserve());
				if (addrs.size() == 0) {
					existFlag = "craddr" + field.getFdReserve();
				} else {
					items.add(addrs.get(0));// 客户的地址
					fields.add(field);
				}
			}
		}
	}

	// 获取房产信息（变更单）
	public static void house(Class table, Long objectId, Field field,
			List fields, List items) {
		if (table.getName().equals(House.class.getName())) {
			if (field.getFdClassEn().equals(ParamConstant.FCXX)) {
				House house = houseService.findByCustomerid(objectId);
				if (null != house) {
					items.add(house);
					fields.add(field);
				} else {
					existFlag = "cp" + field.getFdReserve();
				}
			}
		}
	}

	// 私营业务信息
	public static void privateOwner(Class table, Long objectId, Field field,
			List fields, List items) {
		if (table.getName().equals(PrivateOwners.class.getName())) {
			if (field.getFdClassEn().equals(ParamConstant.SYYZXX)) {
				PrivateOwners privateOwners = privateOwnersService
						.findByCustomerid(objectId);
				if (null != privateOwners) {
					items.add(privateOwners);
					fields.add(field);
				} else {
					existFlag = "cp" + field.getFdReserve();
				}
			}
		}
	}

	// 车辆信息
	public static void car(Class table, Long objectId, Field field,
			List fields, List items) {
		if (table.getName().equals(Car.class.getName())) {
			if (field.getFdClassEn().equals(ParamConstant.CLXX)) {
				List<Car> carList = carService.findAllByCustomerid(objectId);
				if (null != carList) {
					items.add(carList.get(0));
					fields.add(field);
				} else {
					existFlag = "cp" + field.getFdReserve();
				}
			}
		}
	}

	private <T> List<T> initAvaData(List<T> datas,
			List<com.zendaimoney.crm.modification.entity.Field> fields)
			throws Exception {
		List<T> returnList = Lists.newArrayList();
		for (T t : datas) {
			T newt = (T) t.getClass().newInstance();
			for (com.zendaimoney.crm.modification.entity.Field field : fields) {
				String fieldName = field.getFdFieldEn();
				String getMethod = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				Object value = t.getClass()
						.getMethod(getMethod, new Class[] {})
						.invoke(t, new Object[] {});

				String setMethod = "set"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				t.getClass().getMethod(setMethod, new Class[] {})
						.invoke(newt, new Object[] {});
			}
			returnList.add(newt);
		}

		return returnList;
	}

	private static List<com.zendaimoney.crm.modification.entity.Field> getFieldsByClass(
			Class clazz) {
		List<com.zendaimoney.crm.modification.entity.Field> fields = fieldService
				.getAllFields();
		List<com.zendaimoney.crm.modification.entity.Field> allF = Lists
				.newArrayList();
		for (com.zendaimoney.crm.modification.entity.Field field : fields) {
			try {
				java.lang.reflect.Field f = clazz.getDeclaredField(field
						.getFdFieldEn());
				allF.add(field);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return allF;
	}

	/*
	 * private String genSimpInput(com.zendaimoney.crm.modification.entity.Field
	 * field,Customer customer){ String returnStr = ""; if
	 * (field.getFdFieldOption().equals(TEXT)) return "crm/fieldform/" if
	 * (field.getFdFieldOption().equals(SELECT)) returnStr = genSelectInput(
	 * fieldName, fieldCnName, fieldUrl); if
	 * (field.getFdFieldOption().equals(CHECKBOX)) returnStr = genCheckboxInput(
	 * fieldName, fieldCnName, fieldUrl);
	 * 
	 * return returnStr; }
	 */

	// checkbox类型文本
	private String genCheckboxInput(String fieldName, String fieldCnName,
			String fieldUrl) {
		return fieldCnName
				+ GenHtmlHelper.getCheckStr(
						SystemParameterHelper.findParameterByPrTypes(fieldUrl),
						fieldName);
	}

	// select类型文本
	private String genSelectInput(String fieldName, String fieldCnName,
			String fieldUrl) {
		return fieldCnName
				+ GenHtmlHelper.getSelectStr(
						SystemParameterHelper.findParameterByPrTypes(fieldUrl),
						fieldName, "请选择");
	}

	// text类型文本
	private String genTextInput(String fieldName, String fieldCnName) {
		return fieldCnName + GenHtmlHelper.getTextStr(fieldName);
	}

}
