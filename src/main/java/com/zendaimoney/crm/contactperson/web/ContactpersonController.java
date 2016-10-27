package com.zendaimoney.crm.contactperson.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.orm.ResultVo;

import com.zendaimoney.constant.ParamConstant;
import com.zendaimoney.crm.CrudUiController;
import com.zendaimoney.crm.contactperson.entiy.Contactperson;
import com.zendaimoney.crm.contactperson.service.ContactpersonService;
import com.zendaimoney.crm.customer.entity.Addr;
import com.zendaimoney.crm.customer.entity.Customer;
import com.zendaimoney.crm.customer.entity.Tel;
import com.zendaimoney.crm.customer.service.AddrService;
import com.zendaimoney.crm.customer.service.CustomerService;
import com.zendaimoney.crm.customer.service.TelService;
import com.zendaimoney.crm.sysuser.entity.SysUser;
import com.zendaimoney.uc.rmi.vo.Staff;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/crm/contactperson")
public class ContactpersonController extends CrudUiController<Contactperson> {
	@Autowired
	private ContactpersonService contactpersonService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private TelService telService;

	@Autowired
	private AddrService addrService;

	@RequestMapping(value = { "create" })
	public String create(@RequestParam(value = "customerid") Long customerid,
			@RequestParam(value = "urlnow") String urlnow, Model model) {
		model.addAttribute("customerid", customerid);
		model.addAttribute("customerName",
				customerService.getCustomer(customerid).getCrName());
		model.addAttribute("urlnow", urlnow);
		return "crm/contactperson/create";
	}
	/*
	@RequestMapping(value = { "create_Zendai" })
	public String create_Zendai(
			@RequestParam(value = "customerid") Long customerid,
			@RequestParam(value = "urlnow") String urlnow, Model model) {
		model.addAttribute("customerid", customerid);
		model.addAttribute("customerName",
				customerService.getCustomer(customerid).getCrName());
		model.addAttribute("urlnow", urlnow);
		return "crm/contactperson/create_Zendai";
	}
*/
	@ResponseBody
	@RequestMapping(value = { "cpList" })
	public List<Contactperson> cpList(
			@RequestParam(value = "customerid") Long customerid) {
		return contactpersonService.findALlByCustomerid(customerid);
	}
/*
	@RequestMapping(value = { "createMx" })
	public String createMx(@RequestParam(value = "customerid") Long customerid,
			@RequestParam(value = "urlnow") String urlnow, Model model) {
		model.addAttribute("customerid", customerid);
		model.addAttribute("customerName",
				customerService.getCustomer(customerid).getCrName());
		model.addAttribute("urlnow", urlnow);
		return "crm/contactperson/createMx";
	}*/

	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor dateEditor = new CustomDateEditor(fmt, true);
		binder.registerCustomEditor(Date.class, dateEditor);
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo saveContactpersonAndTelAndAddr(
			Contactperson contactperson,
			@RequestParam(value = "customerid") Long customerid,
			@RequestParam(value = "jsonString", required = false) String jsonString,HttpSession httpSession)
			throws Exception {
		SysUser sysuser=(SysUser)httpSession.getAttribute(ParamConstant.SYS_USER);
		try {
			contactpersonService.saveContactpersonAndTelAndAddr(contactperson,
					customerid, jsonString,sysuser.getId());
			return new ResultVo(true);
		} catch (HibernateOptimisticLockingFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultVo(false, ParamConstant.ERRORA);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultVo(false);
		}
	}

	@RequestMapping(value = { "edit" })
	public String edit(
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "customerid", required = false) Long customerid,
			@RequestParam(value = "urlnow") String urlnow, Model model) {
		Contactperson contactpersons = contactpersonService
				.getContactperson(id);
		Customer customer = contactpersons.getCrmCustomer();
		model.addAttribute("contactpersons", contactpersons);
		model.addAttribute("customerName", customer.getCrName());
		model.addAttribute("customerid", customer.getId());
		model.addAttribute("urlnow", urlnow);
		return "crm/contactperson/edit";
	}
	/*
	@RequestMapping(value = { "show" })
	public String show(@RequestParam(value = "id") Long id,
			@RequestParam(value = "urlnow") String urlnow, Model model) {
		Contactperson contactpersons = contactpersonService
				.getContactperson(id);
		Customer customer = contactpersons.getCrmCustomer();
		model.addAttribute("contactpersons", contactpersons);
		model.addAttribute("customerName", customer.getCrName());
		model.addAttribute("customerid", customer.getId());
		model.addAttribute("urlnow", urlnow);
		return "crm/contactperson/show";
	}

	@RequestMapping(value = "saveMx", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo saveContactpersonAndTelAndAddrMx(
			Contactperson contactperson,
			@RequestParam(value = "customerid") Long customerid,
			@RequestParam(value = "jsonString", required = false) String jsonString)
			throws Exception {
		try {
			contactpersonService.saveContactpersonAndTelAndAddr(contactperson,
					customerid, jsonString);
			return new ResultVo(true);
		} catch (HibernateOptimisticLockingFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultVo(false, ParamConstant.ERRORA);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultVo(false);
		}
	}

	@RequestMapping(value = { "editMx" })
	public String editMx(
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "customerid", required = false) Long customerid,
			@RequestParam(value = "urlnow") String urlnow, Model model) {
		Contactperson contactpersons = contactpersonService
				.getContactperson(id);
		Customer customer = contactpersons.getCrmCustomer();
		model.addAttribute("contactpersons", contactpersons);
		model.addAttribute("customerName", customer.getCrName());
		model.addAttribute("customerid", customer.getId());
		model.addAttribute("urlnow", urlnow);
		return "crm/contactperson/editMx";
	}

	@RequestMapping(value = { "showMx" })
	public String showMx(@RequestParam(value = "id") Long id,
			@RequestParam(value = "urlnow") String urlnow, Model model) {
		Contactperson contactpersons = contactpersonService
				.getContactperson(id);
		Customer customer = contactpersons.getCrmCustomer();
		model.addAttribute("contactpersons", contactpersons);
		model.addAttribute("customerName", customer.getCrName());
		model.addAttribute("customerid", customer.getId());
		model.addAttribute("urlnow", urlnow);
		return "crm/contactperson/showMx";
	}
	*/

	@RequestMapping(value = { "contactpersonById" })
	@ResponseBody
	public Contactperson contactpersonById(@RequestParam(value = "id") Long id) {
		return contactpersonService.getContactperson(id);
	}

	@RequestMapping(value = { "tel" })
	@ResponseBody
	public List<Tel> tel(@RequestParam(value = "id") Long id) {
		return telService.findALlByCustomerid(id);
	}

	@RequestMapping(value = { "addr" })
	@ResponseBody
	public List<Addr> addr(@RequestParam(value = "id") Long id) {
		return addrService.findAllByCustomerid(id);
	}

	// 保存电话信息
	@RequestMapping(value = "saveTel")
	@ResponseBody
	public List<Tel> saveTel(Tel tel,
			@RequestParam(value = "contactpersonid") Long contactpersonid,HttpSession httpSession)
			throws Exception {
		SysUser sysuser=(SysUser)httpSession.getAttribute(ParamConstant.SYS_USER);
		try {
			tel.setCustomerid(contactpersonid);
			tel.setTlCustType("1");
			telService.saveTel(tel,sysuser.getId());
			return telService.getAllTel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return telService.getAllTel();
		}
	}
/*
	// 验证电话唯一最高优先级
	@RequestMapping(value = "telHighPriority")
	@ResponseBody
	public JSON telHighPriority(
			@RequestParam(value = "customerid") Long customerid,
			@RequestParam(value = "telType") String telType) {
		JSONObject json = new JSONObject();
		boolean telPriorityH = telService.highPriority(customerid, telType);
		if (telPriorityH) {
			json.put("data", "noHigh");
			return json;
		} else {
			json.put("data", "hasHigh");
			return json;
		}
	}

	// 验证地址唯一最高优先级
	@RequestMapping(value = "addrHighPriority")
	@ResponseBody
	public JSON addrHighPriority(
			@RequestParam(value = "customerid") Long customerid,
			@RequestParam(value = "arAddrType") String arAddrType) {
		JSONObject json = new JSONObject();
		boolean addrPriorityH = addrService
				.highPriority(customerid, arAddrType);
		if (addrPriorityH) {
			json.put("data", "noHigh");
			return json;
		} else {
			json.put("data", "hasHigh");
			return json;
		}
	}

	// 检验完整性contactIntegrity
	@RequestMapping(value = "contactIntegrity")
	@ResponseBody
	public JSON contactIntegrity(
			@RequestParam(value = "customerid") Long customerid) {
		JSONObject json = new JSONObject();
		if (customerid != 0 && customerid != null) {
			String integrity = contactpersonService.cpIntegrity(customerid);
			if (integrity.equals("ok")) {
				json.put("data", "666");
				return json; // 666代表信息完整性
			} else if (integrity.equals("error")) {
				json.put("data", "444");
				return json; // 44代表信息异常或是不完整
			} else {
				json.put("data", integrity);
				return json; // 返回完整性校验结果
			}
		}
		json.put("data", "111000");
		return json; // 111000 代表异常
	}

	// 删除电话
	@RequestMapping(value = "delTel")
	@ResponseBody
	public List<Tel> delTel(@RequestParam(value = "id") Long[] id) {
		try {
			telService.deleteTel(id);
			return telService.getAllTel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return telService.getAllTel();
		}
	}

	// 取得电话数据
	@RequestMapping(value = { "telById" })
	@ResponseBody
	public Tel telById(Long id) {
		return telService.getTel(id);
	}

	// 取得地址数据
	@RequestMapping(value = { "addrById" })
	@ResponseBody
	public Addr addrById(Long id) {
		return addrService.getAddr(id);
	}

	// 保存地址信息
	@RequestMapping(value = "saveAddr", method = RequestMethod.POST)
	@ResponseBody
	public List<Addr> saveAddr(Addr addr,
			@RequestParam(value = "contactpersonid") Long contactpersonid)
			throws Exception {
		try {
			addr.setCustomerid(contactpersonid);
			addr.setArCustType("1");
			addrService.saveAddr(addr);
			return addrService.getAllAddr();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return addrService.getAllAddr();
		}
	}

	// 删除地址
	@RequestMapping(value = "delAddr")
	@ResponseBody
	public List<Addr> delAddr(@RequestParam(value = "id") Long[] id) {
		try {
			addrService.deleteAddr(id);
			return addrService.getAllAddr();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return addrService.getAllAddr();
		}
	}

	// =====================================================捷越信贷
	@RequestMapping(value = { "createjyxd" })
	public String createjyxd(
			@RequestParam(value = "customerid") Long customerid,
			@RequestParam(value = "urlnow") String urlnow, Model model) {
		model.addAttribute("customerid", customerid);
		model.addAttribute("customerName",
				customerService.getCustomer(customerid).getCrName());
		model.addAttribute("urlnow", urlnow);
		return "crm/contactperson/createjyxd";
	}

	// 编辑联系人信息
	@RequestMapping(value = { "edit_jyxd" })
	public String editJyxd(
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "customerid", required = false) Long customerid,
			@RequestParam(value = "urlnow") String urlnow, Model model) {
		Contactperson contactpersons = contactpersonService
				.getContactperson(id);
		Customer customer = contactpersons.getCrmCustomer();
		model.addAttribute("contactpersons", contactpersons);
		model.addAttribute("customerName", customer.getCrName());
		model.addAttribute("customerid", customer.getId());
		model.addAttribute("urlnow", urlnow);
		return "crm/contactperson/edit_jyxd";
	}

	// 检验完整性contactIntegrity
	@RequestMapping(value = "contactIntegrityJyxd")
	@ResponseBody
	public JSON contactIntegrityJyxd(
			@RequestParam(value = "customerid") Long customerid) {
		JSONObject json = new JSONObject();
		if (customerid != null && customerid != 0) {
			String integrity = contactpersonService.cpIntegrityJyxd(customerid);
			if (integrity.equals("ok")) {
				json.put("data", "666");
				return json; // 666代表信息完整性
			} else if (integrity.equals("error")) {
				json.put("data", "444");
				return json; // 44代表信息异常或是不完整
			} else {
				json.put("data", integrity);
				return json; // 返回完整性校验结果
			}
		}
		json.put("data", "111000");
		return json; // 111000 代表异常
	}

	// 查看联系人信息
	@RequestMapping(value = { "show_jyxd" })
	public String show_jyxd(
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "customerid", required = false) Long customerid,
			@RequestParam(value = "urlnow") String urlnow, Model model) {
		Contactperson contactpersons = contactpersonService
				.getContactperson(id);
		Customer customer = contactpersons.getCrmCustomer();
		model.addAttribute("contactpersons", contactpersons);
		model.addAttribute("customerName", customer.getCrName());
		model.addAttribute("customerid", customer.getId());
		model.addAttribute("urlnow", urlnow);
		model.addAttribute("show", 1);// 表示查看
		return "crm/contactperson/edit_jyxd";
	}

	// =====================================================公司信贷
	@RequestMapping(value = { "createZendai" })
	public String createZendai(
			@RequestParam(value = "customerid") Long customerid,
			@RequestParam(value = "urlnow") String urlnow, Model model) {
		model.addAttribute("customerid", customerid);
		model.addAttribute("customerName",
				customerService.getCustomer(customerid).getCrName());
		model.addAttribute("urlnow", urlnow);
		return "crm/contactperson/createZendai";
	}

	@RequestMapping(value = "saveZendai", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo saveZendai(Contactperson contactperson,
			@RequestParam(value = "customerid") Long customerid,
			HttpServletRequest request) throws Exception {
		try {
			Contactperson saveCp = contactpersonService
					.saveOrUpdateContactperson(customerid, contactperson);
			if (saveCp.getId() != null) {
				List<Tel> telList = TelList_CustomerOrContactperByType(request,
						"1");
				telService.saveOrUpTelListByCus_id(saveCp.getId(), telList);

				return new ResultVo(true);
			}
		} catch (Exception e) {

			e.printStackTrace();
			return new ResultVo(false);
		}
		return new ResultVo(false);
	}

	@RequestMapping(value = { "showZendai" })
	public String Zendai(@RequestParam(value = "id") Long id,
			@RequestParam(value = "urlnow") String urlnow, Model model) {
		Contactperson contactpersons = contactpersonService
				.getContactperson(id);
		Customer customer = contactpersons.getCrmCustomer();
		List<Tel> moblie = telService.findByCustomeridAndTelType(id, "3");
		List<Tel> tel = telService.findByCustomeridAndTelType(id, "1");
		if (moblie.size() > 0)
			model.addAttribute("moblie", moblie.get(0));
		else
			model.addAttribute("moblie", new Tel());

		if (tel.size() > 0)
			model.addAttribute("tel", tel.get(0));
		else
			model.addAttribute("tel", new Tel());
		model.addAttribute("contactpersons", contactpersons);
		model.addAttribute("customerName", customer.getCrName());
		model.addAttribute("customerid", customer.getId());
		model.addAttribute("urlnow", urlnow);
		return "crm/contactperson/showZendai";
	}

	@RequestMapping(value = { "editZendai" })
	public String editZendai(
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "customerid", required = false) Long customerid,
			@RequestParam(value = "urlnow") String urlnow, Model model) {
		Contactperson contactpersons = contactpersonService
				.getContactperson(id);
		Customer customer = contactpersons.getCrmCustomer();
		List<Tel> moblie = telService.findByCustomeridAndTelType(id, "3");
		List<Tel> tel = telService.findByCustomeridAndTelType(id, "1");
		if (moblie.size() > 0)
			model.addAttribute("moblie", moblie.get(0));
		else
			model.addAttribute("moblie", new Tel());

		if (tel.size() > 0)
			model.addAttribute("tel", tel.get(0));
		else
			model.addAttribute("tel", new Tel());

		model.addAttribute("contactpersons", contactpersons);
		model.addAttribute("customerName", customer.getCrName());
		model.addAttribute("customerid", customer.getId());
		model.addAttribute("urlnow", urlnow);
		return "crm/contactperson/editZendai";
	}

	
	 * jinghr,2013-7-12 14:21:08 0，客户电话信息集合,1，联系人电话集合； 设计思路由内向外；
	 * 接口融合性严格，前台传进的参数对象集合需为： （mobile_，tel_，cptel_,cz_,hj）
	 * 
	 * 参数类型内部是固定的，影响该方法的可用性及维护性；
	 
	public List<Tel> TelList_CustomerOrContactperByType(
			HttpServletRequest request, String type) {
		List<Tel> telList = new ArrayList<Tel>();
		if (type.equals("0") || type.equals("1")) {
			telList.add(formatTel(request, "mobile" + type, type,
					ParamConstant.MOBILE)); // 添加手机
			telList.add(formatTel(request, "tel" + type, type,
					ParamConstant.TEL)); // 添加家庭电话
			telList.add(formatTel(request, "cptel" + type, type,
					ParamConstant.CPTEL)); // 添加工作电话
			telList.add(formatTel(request, "cz" + type, type, ParamConstant.CZ)); // 添加传真
			telList.add(formatTel(request, "hj" + type, type, ParamConstant.HJ)); // 添加户籍电话
			return telList;
		} else {
			return telList;
		}
	}

	*//**
	 * 格式化电话信息
	 * 
	 * @param
	 * @author Hezc 2012年11月28日15:22:18
	 * 
	 *//*
	private Tel formatTel(HttpServletRequest request, String name, String type,
			String telType) {
		Date now = new Date();
		//Staff staff = AuthorityHelper.getStaff();
		Tel tel = assembleWithAlias(request, Tel.class, name, "_");
		return tel != null ? telModel(tel, type, telType,
				ParamConstant.priorityHighNum, ParamConstant.VALID, now,
				 now) : null;

	}

	*//**
	 * 电话信息补全
	 * 
	 * @param tel
	 *            补全对象 type 电话 类型，0为客户，1为联系人，2为公司 瞎编的，以后再改 telType
	 *            电话类型，0为固话，1为手机 pr 优先级，先给个默认的0 vl 是否有效 1有效0无效 inDate 录入时间 inId
	 *            录入人 mdDate 修改时间 mdId 修改人
	 * @author Hezc 2012年11月28日15:22:18
	 * 
	 *//*
	private Tel telModel(Tel tel, String type, String telType, String pr,
			String vl, Date inDate, Date mdDate) {
		tel.setTlCustType(type);
		tel.setTlTelType(telType);
		tel.setTlValid(vl);
		if (tel.getId() != null) {
		} else {
			tel.setTlPriority(pr);
		}
		return tel;
	}

*/}
