package com.zendaimoney.crm.fieldform.helper;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zendaimoney.constant.ParamConstant;
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
import com.zendaimoney.crm.customer.service.CarService;
import com.zendaimoney.crm.customer.service.CustomerService;
import com.zendaimoney.crm.customer.service.HouseService;
import com.zendaimoney.crm.customer.service.PrivateOwnersService;
import com.zendaimoney.crm.customer.service.TelService;
import com.zendaimoney.crm.modification.entity.Field;
import com.zendaimoney.crm.modification.entity.ModificationDetail;
import com.zendaimoney.crm.modification.service.FieldService;
import com.zendaimoney.crm.product.entity.BusiFinance;
import com.zendaimoney.utils.DateUtil;
import com.zendaimoney.utils.helper.AuthorityHelper;
import com.zendaimoney.utils.helper.SpringContextHelper;

/**
 * 变更单 采集数据表单 保存变更单
 * 
 * @author bianxj
 * 
 */

@SuppressWarnings("rawtypes")
public class formUtilHelper {
	private static final Long operId = null;
	private static FieldService fieldService;
	private static TelService telService;
	private static AddrService addrService;
	private static ContactpersonService contactpersonService;
	private static CustomerService customerService;
	private static HouseService houseService;
	private static PrivateOwnersService privateOwnersService;
	private static CarService carService;

	private static Logger logger = LoggerFactory
			.getLogger(formUtilHelper.class);

	static {
		fieldService = SpringContextHelper.getBean("fieldService");
		telService = SpringContextHelper.getBean("telService");
		addrService = SpringContextHelper.getBean("addrService");
		contactpersonService = SpringContextHelper
				.getBean("contactpersonService");
		customerService = SpringContextHelper.getBean("customerService");
		houseService = SpringContextHelper.getBean("houseService");
		privateOwnersService = SpringContextHelper
				.getBean("privateOwnersService");
		carService = SpringContextHelper.getBean("carService");
	}

	public static String getValue(Object obj, Object fi)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Field field = (Field) fi;
		String fieldName = field.getFdFieldEn();
		String getMethod = "get" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		Object value = obj.getClass().getMethod(getMethod, new Class[] {})
				.invoke(obj, new Object[] {});
		String reValue = "";
		if (value instanceof String)
			reValue = value.toString();
		if (value instanceof Date)
			reValue = value.toString();
		if (value instanceof Double)
			reValue = String.valueOf(value);
		if (value instanceof Long)
			reValue = String.valueOf(value);
		if (value instanceof Integer)
			reValue = value.toString();
		return reValue;
	}

	public static String getTel(Object obj, Object fi)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Field field = (Field) fi;
		Field field1 = fieldService.findByFdClassEnAndFdFieldEnAndFdReserve(
				field.getFdClassEn(), "tlAreaCode", field.getFdReserve());
		Field field2 = fieldService.findByFdClassEnAndFdFieldEnAndFdReserve(
				field.getFdClassEn(), "tlExtCode", field.getFdReserve());
		if (!field.getFdTable().equals("tel") || field1 == null
				|| field2 == null) {
			return null;
		}
		String string = "<input name='" + field1.getFdFieldEn() + "' id='"
				+ field1.getFdFieldEn() + "' type='text' value='"
				+ getValue(obj, field1) + "'/>" + "-<input name='"
				+ field.getFdFieldEn() + "' id='" + field.getFdFieldEn()
				+ "' type='text' value='" + getValue(obj, field) + "'/>"
				+ "-<input name='" + field2.getFdFieldEn() + "' id='"
				+ field2.getFdFieldEn() + "' type='text' value='"
				+ getValue(obj, field2) + "'/>";
		return string;
	}

	public static Long getTelValue(Object obj, Object fi)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Field field = (Field) fi;
		List<Tel> tels = new ArrayList<Tel>();
		if (field.getFdTable().equalsIgnoreCase("tel")) {
			Object value = obj.getClass()
					.getMethod("getCustomerid", new Class[] {})
					.invoke(obj, new Object[] {});
			tels.addAll(telService.findALlByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
					new Long(value.toString()), field.getFdMemo(),
					field.getFdReserve(), ParamConstant.priorityHighNum));
		}
		return tels.size() > 0 ? tels.get(0) != null ? tels.get(0).getId()
				: null : null;
	}

	public static Long getAddrValue(Object obj, Object fi)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Field field = (Field) fi;
		List<Addr> addrs = new ArrayList<Addr>();
		if (field.getFdTable().equalsIgnoreCase("addr")) {
			Object value = obj.getClass()
					.getMethod("getCustomerid", new Class[] {})
					.invoke(obj, new Object[] {});
			addrs.add(addrService.findByCustomeridAndArCustTypeAndArAddrTypeAndArPriority(
					new Long(value.toString()), field.getFdMemo(),
					field.getFdReserve(), ParamConstant.priorityHighNum));
		}
		return addrs.size() > 0 ? addrs.get(0) != null ? addrs.get(0).getId()
				: null : null;
	}

	public static String getAddr(Object obj, Object fi)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Field field = (Field) fi;
		if (!field.getFdTable().equals("addr")) {
			return null;
		}
		Field arCity = fieldService.findByFdClassEnAndFdFieldEnAndFdReserve(
				field.getFdClassEn(), "arCity", field.getFdReserve());
		Field arCounty = fieldService.findByFdClassEnAndFdFieldEnAndFdReserve(
				field.getFdClassEn(), "arCounty", field.getFdReserve());
		Field arStreet = fieldService.findByFdClassEnAndFdFieldEnAndFdReserve(
				field.getFdClassEn(), "arStreet", field.getFdReserve());
		Field arAddrDetail = fieldService
				.findByFdClassEnAndFdFieldEnAndFdReserve(field.getFdClassEn(),
						"arAddrDetail", field.getFdReserve());
		Field arZipCode = fieldService.findByFdClassEnAndFdFieldEnAndFdReserve(
				field.getFdClassEn(), "arZipCode", field.getFdReserve());
		String string = "";
		if (field.getFdReserve().equalsIgnoreCase("5")) {
			string = "<div style='display:none'><input id='arProvince' type='hidden' name='arProvince' value='' class='easyui-combobox' style='width: 60px;'>省-"
					+ "<input id='arCity' type='hidden' name='arCity' value='' class='easyui-combobox' style='width: 100px;'>市-"
					+ "<input id='arCounty' type='hidden' name='arCounty' value='' class='easyui-combobox' style='width: 60px;'>县-"
					+ "<input id='arStreet' type='hidden' name='arStreet' value='' class='easyui-validatebox' maxLength='20'> 街/路</div>"
					+ "<input id='"
					+ arAddrDetail.getFdFieldEn()
					+ "' name='"
					+ arAddrDetail.getFdFieldEn()
					+ "' value='"
					+ getValue(obj, arAddrDetail)
					+ "' class='easyui-validatebox' style='width: 350px;' maxLength='66'>";
		} else {
			string = "<input id='"
					+ field.getFdFieldEn()
					+ "' name='"
					+ field.getFdFieldEn()
					+ "' value='"
					+ getValue(obj, field)
					+ "' class='easyui-combobox' style='width: 60px;'>省-"
					+ "<input id='"
					+ arCity.getFdFieldEn()
					+ "' name='"
					+ arCity.getFdFieldEn()
					+ "' value='"
					+ getValue(obj, arCity)
					+ "' class='easyui-combobox' style='width: 100px required='true';'>市-"
					+ "<input id='" + arCounty.getFdFieldEn() + "' name='"
					+ arCounty.getFdFieldEn() + "' value='"
					+ getValue(obj, arCounty)
					+ "' class='easyui-combobox' style='width: 60px;'>县-"
					+ "<input id='" + arStreet.getFdFieldEn() + "' name='"
					+ arStreet.getFdFieldEn() + "' value='"
					+ getValue(obj, arStreet).replaceAll("\\r|\n", "")
					+ "' class='easyui-validatebox' maxLength='20'> 街/路";

			if (arAddrDetail != null) {
				string += "<br>详细地址<input id='"
						+ arAddrDetail.getFdFieldEn()
						+ "' name='"
						+ arAddrDetail.getFdFieldEn()
						+ "' value='"
						+ getValue(obj, arAddrDetail).replaceAll("\\r|\n", "")
						+ "' class='easyui-validatebox' style='width: 350px;' maxLength='66'>";
			} else {
				string += "<br><input id='arAddrDetail' type='hidden' name='arAddrDetail' value='' class='easyui-validatebox' style='width: 350px;' maxLength='66'>";
			}
		}

		if (arZipCode != null) {
			string += "邮编:<input id='arZipCode' name='arZipCode' value='"
					+ getValue(obj, arZipCode)
					+ "' class='easyui-validatebox'>";
		} else {
			string += "<input id='arZipCode' type='hidden' name='arZipCode' value='' class='easyui-validatebox'>";
		}
		return string;
	}

	/**
	 * 房产信息中的地址 生成HTML
	 * 
	 * @param obj
	 * @param fi
	 * @return
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static String getHouseAddr(Object obj, Object fi)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Field field = (Field) fi;
		if ((!field.getFdTable().equals("house"))
				|| (field.getFdReserve() == null)
				|| (!field.getFdReserve().equalsIgnoreCase("house"))) {
			return null;
		}
		Field arCity = fieldService.findByFdClassEnAndFdFieldEnAndFdReserve(
				field.getFdClassEn(), "heCity", field.getFdReserve());
		Field arCounty = fieldService.findByFdClassEnAndFdFieldEnAndFdReserve(
				field.getFdClassEn(), "heCounty", field.getFdReserve());
		/*
		 * Field arStreet =
		 * fieldService.findByFdClassEnAndFdFieldEnAndFdReserve(
		 * field.getFdClassEn(),"arStreet",field.getFdReserve());
		 */
		Field arAddrDetail = fieldService
				.findByFdClassEnAndFdFieldEnAndFdReserve(field.getFdClassEn(),
						"heAddrDetail", field.getFdReserve());
		Field arZipCode = fieldService.findByFdClassEnAndFdFieldEnAndFdReserve(
				field.getFdClassEn(), "heZipCode", field.getFdReserve());
		String string = "";

		string = "<input id='"
				+ field.getFdFieldEn()
				+ "' name='"
				+ field.getFdFieldEn()
				+ "' value='"
				+ getValue(obj, field)
				+ "' class='easyui-combobox' style='width: 60px;'>省-"
				+ "<input id='"
				+ arCity.getFdFieldEn()
				+ "' name='"
				+ arCity.getFdFieldEn()
				+ "' value='"
				+ getValue(obj, arCity)
				+ "' class='easyui-combobox' style='width: 100px required='true';'>市-"
				+ "<input id='" + arCounty.getFdFieldEn() + "' name='"
				+ arCounty.getFdFieldEn() + "' value='"
				+ getValue(obj, arCounty)
				+ "' class='easyui-combobox' style='width: 60px;'>县";
		// +"<input id='"+arStreet.getFdFieldEn()+"' name='"+arStreet.getFdFieldEn()+"' value='"+
		// getValue(obj,arStreet).replaceAll("\\r|\n",
		// "")+"' class='easyui-validatebox' maxLength='20'> 街/路";

		if (arAddrDetail != null) {
			string += "<br>详细地址<input id='"
					+ arAddrDetail.getFdFieldEn()
					+ "' name='"
					+ arAddrDetail.getFdFieldEn()
					+ "' value='"
					+ getValue(obj, arAddrDetail).replaceAll("\\r|\n", "")
					+ "' class='easyui-validatebox' style='width: 350px;' maxLength='66'>";
		} else {
			string += "<br><input id='heAddrDetail' type='hidden' name='heAddrDetail' value='' class='easyui-validatebox' style='width: 350px;' maxLength='66'>";
		}

		if (arZipCode != null) {
			string += "邮编:<input id='heZipCode' name='heZipCode' value='"
					+ getValue(obj, arZipCode)
					+ "' class='easyui-validatebox'>";
		} else {
			string += "<input id='heZipCode' type='hidden' name='heZipCode' value='' class='easyui-validatebox'>";
		}
		return string;
	}

	/**
	 * 保存变更单
	 * 
	 * @param detail
	 * @return
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	/*
	 * public static boolean saveNewValue(ModificationDetail detail) throws
	 * IllegalArgumentException, SecurityException, IllegalAccessException,
	 * InvocationTargetException, NoSuchMethodException{ Class table =
	 * getTableBelong(detail.getField().getFdFieldEn()); Long operId =
	 * detail.getModification().getMnSourceId(); EntityManager em =
	 * AuthorityHelper.getEm(); CriteriaBuilder builder =
	 * em.getCriteriaBuilder(); CriteriaQuery criteriaQuery =
	 * builder.createQuery(table); Root root = criteriaQuery.from(table);
	 * 
	 * Object obj = em.createQuery( criteriaQuery.where(
	 * builder.equal(root.get("id"), operId))).getSingleResult();
	 * 
	 * Field field = (Field)detail.getField(); String fieldName =
	 * field.getFdFieldEn(); String setMethod = "set" + fieldName.substring(0,
	 * 1).toUpperCase() + fieldName.substring(1); String type =
	 * field.getFdFieldType(); obj.getClass().getMethod(setMethod,
	 * getParameType(type)).invoke(obj, new Object[]
	 * {getParameValue(type,detail.getMlFieldNewValue())});
	 * em.getTransaction().begin(); em.persist(obj);
	 * em.getTransaction().commit(); return true; }
	 */

	public static boolean saveNewValue(Set<ModificationDetail> details,Long sysuserId)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		EntityManager em = AuthorityHelper.getEm();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		try {
			em.getTransaction().begin();
			for (ModificationDetail detail : details) {
				Class table = getTableBelong(detail.getField().getFdFieldEn());
				Long operId = detail.getModification().getMnSourceId();
				Field field = (Field) detail.getField();
				if (table.getName().equals(Tel.class.getName())) {//通讯信息
					if (!field.getFdFieldEn().equals("tlPriority")) {
						if (field.getFdClassEn().equals(ParamConstant.TXXX)
								|| field.getFdClassEn().equals(
										ParamConstant.ZYXX)) {
							if (field.getFdReserve().equals(
									ParamConstant.MOBILE)) {
								Tel tel = telService
										.findByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
												operId, "0",
												field.getFdReserve(),
												ParamConstant.priorityHighNum);
								operId = null;
								tel.setTlTelNum(detail.getMlFieldNewValue());
								telService.saveTel(tel,sysuserId);
							} else {
								Tel tel = telService
										.findByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
												operId, "0",
												field.getFdReserve(),
												ParamConstant.priorityHighNum);
								operId = null;
								String[] stringNewList = detail
										.getMlFieldNewValue().split("!@#");
								int sizes = stringNewList.length;
								if (0 < sizes)
									tel.setTlAreaCode(stringNewList[0]);
								if (1 < sizes)
									tel.setTlTelNum(stringNewList[1]);
								if (2 < sizes)
									tel.setTlExtCode(stringNewList[2]);
								telService.saveTel(tel,sysuserId);
							}
						}
						if (field.getFdClassEn().equals(ParamConstant.JJLXRXX)) {//紧急联系人
							// List<Contactperson> contactpersons =
							// contactpersonService.findAllByCrmCustomer(customerService.getCustomer(operId));
							Contactperson contactpersons = contactpersonService
									.findByid(detail.getSourceId()); // detail.getSourceId()存放联系人id
							if (field.getFdReserve().equals(
									ParamConstant.MOBILE)) {
								Tel tel = telService
										.findByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
												contactpersons.getId(), "1",
												field.getFdReserve(),
												ParamConstant.priorityHighNum);
								operId = null;
								tel.setTlTelNum(detail.getMlFieldNewValue());
								telService.saveTel(tel,sysuserId);
							} else {
								Tel tel = telService
										.findByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority(
												contactpersons.getId(), "1",
												field.getFdReserve(),
												ParamConstant.priorityHighNum);
								operId = null;
								String[] stringNewList = detail
										.getMlFieldNewValue().split("!@#");
								int sizes = stringNewList.length;
								if (0 < sizes)
									tel.setTlAreaCode(stringNewList[0]);
								if (1 < sizes)
									tel.setTlTelNum(stringNewList[1]);
								if (2 < sizes)
									tel.setTlExtCode(stringNewList[2]);
								telService.saveTel(tel,sysuserId);
							}
						}
					} else {
						Tel tel = telService.getTel(new Long(detail
								.getMlFieldNewValue()));
						operId = null;
						tel.setTlPriority(ParamConstant.priorityHighNum);
						telService.saveTel(tel,sysuserId);
					}
				}
				if (table.getName().equals(Contactperson.class.getName())) {//联系人
					if (field.getFdClassEn().equals(ParamConstant.JJLXRXX)) {
						// List<Contactperson> contactpersons =
						// contactpersonService.findAllByCrmCustomer(customerService.getCustomer(operId));
						// operId = contactpersons.get(0).getId();
						operId = detail.getSourceId();
					}
				}
				if (table.getName().equals(Addr.class.getName())) {//地址
					if (!field.getFdFieldEn().equals("arPriority")) {
						if (field.getFdClassEn().equals(ParamConstant.JBXX)//基本信息
								|| field.getFdClassEn().equals(
										ParamConstant.TXXX)//通讯信息
								|| field.getFdClassEn().equals(
										ParamConstant.ZYXX)) {//职业信息
							Addr addr = addrService
									.findByCustomeridAndArCustTypeAndArAddrTypeAndArPriority(
											operId, "0", field.getFdReserve(),
											ParamConstant.priorityHighNum);
							operId = null;
							String[] stringNewList = detail
									.getMlFieldNewValue().split("!@#");
							int sizes = stringNewList.length;
							if (0 < sizes)
								addr.setArProvince(stringNewList[0]);
							if (1 < sizes)
								addr.setArCity(stringNewList[1]);
							if (2 < sizes)
								addr.setArCounty(stringNewList[2]);
							if (3 < sizes)
								addr.setArStreet(stringNewList[3]);
							if (4 < sizes)
								addr.setArAddrDetail(stringNewList[4]);
							if (5 < sizes)
								addr.setArZipCode(stringNewList[5]);
							addrService.saveAddr(addr,sysuserId);
						}
						if (field.getFdClassEn().equals(ParamConstant.JJLXRXX)) {//紧急联系人信息
							/*
							 * List<Contactperson> contactpersons =
							 * contactpersonService
							 * .findAllByCrmCustomer(customerService
							 * .getCustomer(operId));
							 */
							Addr addr = addrService
									.findByCustomeridAndArCustTypeAndArAddrTypeAndArPriority(
											detail.getSourceId(), "1",
											field.getFdReserve(),
											ParamConstant.priorityHighNum);
							operId = null;
							String[] stringNewList = detail
									.getMlFieldNewValue().split("!@#");
							int sizes = stringNewList.length;
							if (0 < sizes)
								addr.setArProvince(stringNewList[0]);
							if (1 < sizes)
								addr.setArCity(stringNewList[1]);
							if (2 < sizes)
								addr.setArCounty(stringNewList[2]);
							if (3 < sizes)
								addr.setArStreet(stringNewList[3]);
							if (4 < sizes)
								addr.setArAddrDetail(stringNewList[4]);
							if (5 < sizes)
								addr.setArZipCode(stringNewList[5]);
							addrService.saveAddr(addr,sysuserId);
						}
					} else {
						Addr addr = addrService.getAddr(new Long(detail
								.getMlFieldNewValue()));
						operId = null;
						addr.setArPriority(ParamConstant.priorityHighNum);
						addrService.saveAddr(addr,sysuserId);
					}
				}
				if (table.getName().equals(House.class.getName())) {//房产
					operId = houseService.findByCustomerid(operId).getId();
					// 房产地址特殊处理
					if (field.getFdFieldOption().equalsIgnoreCase("addr")) {
						House house = em.find(House.class, operId,
								LockModeType.OPTIMISTIC);
						if (house == null)
							continue;
						String[] stringNewList = detail.getMlFieldNewValue()
								.split("!@#");
						int sizes = stringNewList.length;
						if (0 < sizes)
							house.setHeProvince(stringNewList[0]);
						if (1 < sizes)
							house.setHeCity(stringNewList[1]);
						if (2 < sizes)
							house.setHeCounty(stringNewList[2]);
						if (3 < sizes)
							house.setHeAddrDetail(stringNewList[3]);
						if (4 < sizes)
							house.setHeZipCode(stringNewList[4]);
						houseService.saveHouse(house);
						continue;
					}
				} else if (table.getName()
						.equals(PrivateOwners.class.getName())) {//私营业主
					operId = privateOwnersService.findByCustomerid(operId)
							.getId();
				} else if (table.getName().equals(Car.class.getName())) {//汽车
					operId = carService.findAllByCustomerid(operId) != null ? carService
							.findAllByCustomerid(operId).get(0).getId()
							: null;
				}

				if (operId != null) {
					CriteriaQuery criteriaQuery = builder.createQuery(table);
					Root root = criteriaQuery.from(table);
					Object obj = em.createQuery(
							criteriaQuery.where(builder.equal(root.get("id"),
									operId))).getSingleResult();
					String fieldName = field.getFdFieldEn();
					String setMethod = "set"
							+ fieldName.substring(0, 1).toUpperCase()
							+ fieldName.substring(1);
					String type = field.getFdFieldType();
					obj.getClass()
							.getMethod(setMethod, getParameType(type))
							.invoke(obj,
									new Object[] { getParameValue(type,
											detail.getMlFieldNewValue()) });
					em.persist(obj);
					
				}
			}
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			logger.error("saveNewValue:" + e.getMessage(), e); // 修改先打印报错日志
																// 再进行回滚动作，以免对报错信息造成影响
			em.clear();
//			em.getTransaction().rollback();
			// TODO: handle exception
			return false;
		}
	}

	/*
	 * public static boolean saveNewValue(Set<ModificationDetail> details,Long
	 * operId) throws IllegalArgumentException, SecurityException,
	 * IllegalAccessException, InvocationTargetException, NoSuchMethodException{
	 * EntityManager em = AuthorityHelper.getEm(); CriteriaBuilder builder =
	 * em.getCriteriaBuilder(); try { em.getTransaction().begin(); for
	 * (ModificationDetail detail : details) { Class table =
	 * getTableBelong(detail.getField().getFdFieldEn()); Field field =
	 * (Field)detail.getField(); if
	 * (table.getName().equals(Tel.class.getName())) {
	 * if(field.getFdClassEn().equals
	 * (ParamConstant.TXXX)||field.getFdClassEn().equals(ParamConstant.ZYXX)){
	 * Tel tel =
	 * telService.findByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority
	 * (operId,"0",field.getFdReserve(),ParamConstant.priorityHighNum); operId =
	 * tel.getId(); } if(field.getFdClassEn().equals(ParamConstant.JJLXRXX)){
	 * Tel tel =
	 * telService.findByCustomeridAndTlCustTypeAndTlTelTypeAndTlPriority
	 * (operId,"1",field.getFdReserve(),ParamConstant.priorityHighNum); operId =
	 * tel.getId(); } } if (table.getName().equals(Addr.class.getName())) {
	 * if(field
	 * .getFdClassEn().equals(ParamConstant.JBXX)||field.getFdClassEn().equals
	 * (ParamConstant.TXXX)||field.getFdClassEn().equals(ParamConstant.ZYXX)){
	 * Addr addr =
	 * addrService.findByCustomeridAndArCustTypeAndArAddrTypeAndArPriority
	 * (operId,"0",field.getFdReserve(),ParamConstant.priorityHighNum); operId =
	 * addr.getId(); } if(field.getFdClassEn().equals(ParamConstant.JJLXRXX)){
	 * Addr addr =
	 * addrService.findByCustomeridAndArCustTypeAndArAddrTypeAndArPriority
	 * (operId,"1",field.getFdReserve(),ParamConstant.priorityHighNum); operId =
	 * addr.getId(); } } CriteriaQuery criteriaQuery =
	 * builder.createQuery(table); Root root = criteriaQuery.from(table); Object
	 * obj = em.createQuery( criteriaQuery.where( builder.equal(root.get("id"),
	 * operId))).getSingleResult(); String fieldName = field.getFdFieldEn();
	 * String setMethod = "set" + fieldName.substring(0, 1).toUpperCase() +
	 * fieldName.substring(1); String type = field.getFdFieldType();
	 * obj.getClass().getMethod(setMethod, getParameType(type)).invoke(obj, new
	 * Object[] {getParameValue(type,detail.getMlFieldNewValue())});
	 * em.persist(obj); } em.getTransaction().commit(); return true; } catch
	 * (Exception e) { em.getTransaction().rollback(); // TODO: handle exception
	 * return false; } }
	 */

	public static Class getParameType(String fieldType) {
		Class clazz = null;
		if (fieldType.equalsIgnoreCase("String")) {
			clazz = String.class;
		}
		if (fieldType.equalsIgnoreCase("Date")) {
			clazz = Date.class;
		}
		if (fieldType.equalsIgnoreCase("Long")) {
			clazz = Long.class;
		}
		if (fieldType.equalsIgnoreCase("Double")) {
			clazz = Double.class;
		}
		if (fieldType.equalsIgnoreCase("BigDecimal")) {
			clazz = BigDecimal.class;
		}

		return clazz;
	}

	public static Object getParameValue(String fieldType, String fieldValue) {
		Object object = null;
		if (fieldType.equalsIgnoreCase("String")) {
			object = fieldValue;
		}
		if (fieldType.equalsIgnoreCase("Date")) {
			object = DateUtil.parse(fieldValue, "yyyy-MM-dd");
		}
		if (fieldType.equalsIgnoreCase("Long")) {
			object = Long.parseLong(fieldValue);
		}
		if (fieldType.equalsIgnoreCase("Double")) {
			object = Double.parseDouble(fieldValue);
		}
		if (fieldType.equalsIgnoreCase("BigDecimal")) {
			object = new BigDecimal(fieldValue);
		}
		return object;
	}

	static HashMap<String, Class> fieldMap = new HashMap<String, Class>();
	static {
		Class[] clazzs = new Class[] { BusiFinance.class, Customer.class,
				Bankaccount.class, Addr.class, Tel.class, Contactperson.class,
				House.class, PrivateOwners.class, Car.class };
		for (Class class1 : clazzs) {
			java.lang.reflect.Field[] fieldArray = class1.getDeclaredFields();
			for (java.lang.reflect.Field field : fieldArray) {
				fieldMap.put(field.getName(), class1);
			}
		}
	}

	public static Class getTableBelong(String fieldName) {
		Class returnClass = null;
		if (fieldMap.containsKey(fieldName))
			returnClass = fieldMap.get(fieldName);
		else
			try {
				throw new NoSuchFieldException("没有匹配的字段:" + fieldName);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		return returnClass;
	}

}
