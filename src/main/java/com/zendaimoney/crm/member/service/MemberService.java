package com.zendaimoney.crm.member.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.crm.BaseService;
import com.zendaimoney.crm.customer.entity.Customer;
import com.zendaimoney.crm.customer.repository.CustomerDao;
import com.zendaimoney.crm.customer.repository.TelDao;
import com.zendaimoney.crm.member.entity.Member;
import com.zendaimoney.crm.member.repository.MemberDao;

/**
 * 
 * @ClassName: MemberService
 * @Description: TODO
 * @author liyez
 * @date 2015年1月23日 下午5:50:05
 * 
 */
@Service
@Transactional
public class MemberService extends BaseService<Member> {
	private static Logger logger = LoggerFactory.getLogger(MemberService.class);
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private CustomerDao customerDao	;
	@Autowired
	private TelDao telDao;
	
	/**
	 * Sam.J,2014.06.18 更新app表中的 客户经理选项
	 * 
	 */
	public String updateMember(Customer customer) {
		String result = "";
		try {
			if (customer.getId() == null || "".equals(customer.getId())) {
				result = "customer id error";
				logger.info("修改APP表中客户经理结果：" + result);
				return result;
			}
			Member member = findMemberByCustomerId(customer.getId());
			if (member == null) {
				result = "no this customer";
			} else {
				// 更新数据
				member.setMrManager(customer.getCrGather());
				member.setMrModifyTime(new Date());
				memberDao.save(member);
				result = "succes";
			}
		} catch (Exception e) {
			result = "fail";
		}
		logger.info("修改APP表中客户经理结果：" + result);
		return result;
	}
	
	// -----------2014.06.18 修改 by Sam.J 增加依照客户id搜索客户内容-------//

	/**
	 * Sam.J,2014.06.18 依照客户id搜索客户内容
	 * 
	 */
	public Member findMemberByCustomerId(Long id) {
		Member member = memberDao.findMemberByCustomerId(id);
		return member;
	}
	/*

	*//**
	 * 查询手机号是否绑定身份证
	 * 
	 * @param mobile
	 *            手机号码
	 * @param idCardNo
	 *            身份证
	 * @return
	 *//*
	public String queryBusinessByMobileAndIdCardNo(String mobile,
			String idCardNo) {

		try {

			if (StringUtils.isBlank(mobile)) {
				return "手机号码为空!";
			}
			mobile = mobile.trim();
			Member member = memberDao.findMemberByMobile(mobile);
			if (member == null) {
				return "此手机没有注册会员!";
			}
			Long customerId = member.getCustomerid();
			// 存在客户id
			if (customerId != null) {
				// 根据id判断是否是新老客户。cr_customer_type=3为老客户
				// cr_customer_type=2为客户。即是否有业务
				Long cId = customerDao.findBusinessByCustomerId(customerId);
				if (cId == null) {
					return "4";// 手机号码一致,无业务!
				} else {
					return "3";// 手机号码一致,有业务!
				}
			} else {// 不存在客户id

				// 获取身份证
				String idNum = member.getMrIdnum();
				if (StringUtils.isBlank(idNum)) {
					return "6";// 会员没有身份证号码!
				}
				// 根据身份证查询客户信息表是否有此身份证，有返回客户id
				List<Customer> customerList = customerDao.findByIdNum(idNum);
				Long cId = null;
				Customer customer = null;
				if (customerList != null && customerList.size() > 0) {
					customer = customerList.get(0);
					cId = customer.getId();

				}
				if (cId == null) {
					return "5";// 身份证在客户基本信息表中不存在!
				}

				// 手机号码是否一致
				String tel = member.getMrMobile();
				List<String> list = telDao.findTelNumByCustomerId(cId);
				if (mobileIsEql(list, tel)) {
					// 分配客户经理关联客户id
					Long gather = customer.getCrGather();
					member.setAppAssignmentType("自动分配");
					member.setMrManager(gather);
					member.setCustomerid(cId);
					member.setMrMemberState("2");// '已分配','2'
					member.setMrModifyTime(new Date());
					memberDao.save(member);

					cId = customerDao.findBusinessByCustomerId(cId);
					if (cId == null) {
						return "4";// 手机号码一致,无业务!
					} else {
						return "3";// 手机号码一致,有业务!
					}

				} else {
					// 手机号码不一致，判断是否有业务
					cId = customerDao.findBusinessByCustomerId(cId);
					if (cId == null) {
						return "2";// 手机号码不一致,无业务!
					} else {
						return "1";// 手机号码不一致,有业务!
					}
				}
			}

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return null;
	}

	// 判断手机号码是否一致
	private boolean mobileIsEql(List<String> list, String mobile) {
		if (StringUtils.isBlank(mobile)) {
			return false;
		}
		for (String telNum : list) {

			if (mobile.equals(telNum)) {
				return true;
			}
		}

		return false;
	}

	public Page<Member> findAllMember(Map<String, SearchFilter> conditionMap,
			PageRequest pageRequest) {
		StringBuffer baseSql = new StringBuffer();
		// 客户查询
		baseSql.append("select M from Member M where M.mrState = '1'");

		// 查询总记录数
		long total = getTotal(conditionMap, new StringBuffer(baseSql));
		Page<Member> page = new PageImpl<Member>(getContent(conditionMap,
				baseSql, pageRequest), pageRequest, total);
		return page;
	}

	public Page<Member> getMember(Specification<Member> buildSpecification,
			PageRequest buildPageRequest) {
		// TODO Auto-generated method stub
		return memberDao.findAll(buildSpecification, buildPageRequest);
	}

	*//**
	 * Jinghr 2013年10月15日15:57:02 格式化处理列表数据
	 * 
	 * @param conditionMap
	 * @param pageRequest
	 * @return
	 *//*
	public Page<MemberEn> findAllMemberB(ServletRequest request,
			Map<String, SearchFilter> conditionMap, PageRequest pageRequest) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "search_");
		StringBuffer baseSql = new StringBuffer();
		// 客户查询
		baseSql.append("select M from Member M where M.mrState = '1' ");
		if (!searchParams.isEmpty()
				&& null != searchParams.get("LIKE_mrMobile")) {
			baseSql.append(" and M.mrMobile like '"
					+ searchParams.get("LIKE_mrMobile") + "%' ");
		}
		if (!searchParams.isEmpty() && null != searchParams.get("LIKE_mrName")) {
			String name = CustomerHelper.dealCustName(searchParams.get(
					"LIKE_mrName").toString());
			baseSql.append(" and M.mrName like '" + name + "%' ");
		}
		baseSql.append(" order by M.mrRegdate desc , M.id desc");

		// 查询总记录数
		long total = getTotal(conditionMap, new StringBuffer(baseSql));
		List<Member> memberList = getContent(conditionMap, baseSql, pageRequest);

		Page<MemberEn> page = new PageImpl<MemberEn>(formateMember(memberList),
				pageRequest, total);
		return page;
	}

	*//**
	 * Jinghr ,2013年10月14日16:28:22 处理Member数据，增添前台所需字段（手机是否一致，是否有业务，操作“异常”值）
	 * 
	 * @param List
	 *            <MemberEn>
	 * @return List<MemberEn>
	 *//*
	public List<MemberEn> formateMember(List<Member> memberList) {

		List<MemberEn> CopyMemberList = BeanMapper.mapList(memberList,
				MemberEn.class);

		for (int i = 0; i < CopyMemberList.size(); i++) {
			CopyMemberList.get(i)
					.setBusiCode(
							queryBusinessByMobileAndIdCardNo(CopyMemberList
									.get(i).getMrMobile(), CopyMemberList
									.get(i).getMrIdnum()));
		}

		return CopyMemberList;
	}

	*//**
	 * Jinghr,2013年10月15日14:25:47 解除绑定，擦除会员身份证和客户id
	 * 
	 *//*
	public Member removeFix(Long id) {
		Member member = memberDao.findOne(id);
		member.setCustomerid(null);
		member.setMrIdnum(null);
		member.setMrModifyTime(new Date());
		return memberDao.save(member);
	}

	*//**
	 * Jinghr,2013年10月15日14:25:47 业务异常处理，根据身份证插入已存在客户id
	 * 
	 *//*
	public Member delMemberException(Long id) {
		Member member = memberDao.findOne(id);
		member.setCustomerid(getCustomerId(member.getMrIdnum()));

		member.setMrModifyTime(new Date());
		return memberDao.save(member);
	}

	*//**
	 * Jinghr,2013年10月15日14:25:47 分配处理
	 * 
	 *//*
	public Member assignMember(Long id, Long mrManagerId) {
		Member member = memberDao.findOne(id);
		if (ParamConstant.MEMBER_ASSIGN_YUANGONGZIGOU.equals(mrManagerId)) { // 判断前台传下来的值是否为员工自购
																				// 如为员工自购，则取配置文件中配置的staffid
			String staffid = ConfigurationHelper
					.getString("crm.staffid.yuangongzigou");
			if (!"".equals(staffid)) {
				mrManagerId = Long.parseLong(staffid);
			}
		}
		member.setMrManager(mrManagerId);
		member.setMrMemberState("2"); // 2,指状态已分配
		member.setAppAssignmentType("手动分配");
		member.setMrModifyTime(new Date());
		return memberDao.save(member);
	}

	*//**
	 * 分配更新理财用户客户经理
	 * 
	 * @param @param member
	 * @return Customer 返回类型
	 * @throws
	 * @author liyez
	 * @date 2015年1月23日 下午5:50:12
	 *//*
	public Customer assignCustomer(Member member) {
		Customer customer = customerDao.findOne(member.getCustomerid());
		if (null != customer && null == customer.getCrGather()) {
			Staff staff = AuthorityHelper.getStaff();
			customer.setCrGather(member.getMrManager());// 客户经理
			customer.setCrModifyDate(new Date());
			customer.setCrModifyId(staff.getId());
			customer.setCrDeptManager(MemberHelper.getStaffLeaderId(member
					.getMrManager()));// 直属主管

			return customerDao.save(customer);
		}
		return null;
	}

	*//**
	 * Jinghr,2013年10月16日13:40:21 删除
	 * 
	 *//*
	public Member deleteApp(Long id) {
		Member member = memberDao.findOne(id);
		member.setMrState("0"); // 0 删除标志
		member.setMrModifyTime(new Date());
		return memberDao.save(member);
	}

	// 根据身份证查询客户信息表是否有此身份证，有返回客户id
	public Long getCustomerId(String idNum) {
		List<Customer> customerList = customerDao.findByIdNum(idNum);
		Long cId = null;
		if (customerList != null && customerList.size() > 0) {
			cId = customerList.get(0).getId();
		}
		return cId;
	}

	*//**
	 * 审核通过 Jinghr .2013-11-12 11:45:19
	 *//*
	public Member verifyPass(Member mb, String product) {
		// 更新数据
		Member member = memberDao.findOne(mb.getId());
		member.setMrMemberState("1"); // 1 为为分配
		member.setMrCity(mb.getMrCity());
		member.setMrMemo(mb.getMrMemo());
		member.setMrModifyTime(new Date());

		if (dealBusiApply(mb, product)) // 业务处理成功，更新数据
			memberDao.save(member);

		return member;
	}

	*//**
	 * 审核拒绝 Jinghr .2013-11-12 2013-11-12 13:32:48
	 *//*
	public Member verifyRefuse(Member mb) {
		// 更新数据
		Member member = memberDao.findOne(mb.getId());
		member.setMrMemberState("3"); // 3 为 拒绝
		member.setMrMemo(mb.getMrMemo());
		member.setMrModifyTime(new Date());
		return memberDao.save(member);
	}

	*//**
	 * 业务申请处理 一，单笔业务，直接更新数据 二，多笔业务，处理最后一条数据记录 三，没有，更新一条记录 jinghr,2013-11-12
	 * 12:49:42
	 *//*
	public boolean dealBusiApply(Member mb, String product) {
		AppBusiness appBusiness = appBusinessDao.lastAppBusiness(mb.getId());

		if (null == appBusiness) {
			AppBusiness newAppbusi = new AppBusiness();
			newAppbusi.setBsProduct(product);
			newAppbusi.setMemberId(mb.getId());
			newAppbusi.setBsState("1"); // 正常
			newAppbusi.setBsInputTime(new Date());
			newAppbusi.setBsModifyTime(new Date());
			appBusinessDao.save(newAppbusi);
		} else {
			appBusiness.setBsProduct(product);
			appBusiness.setBsModifyTime(new Date());
			appBusinessDao.save(appBusiness);
		}

		return true;
	}
	

	*//**
	 * @title 密码重置
	 * @data 2014-11-5
	 *//*
	public Member resetPassword(Long id, String password,String customerSource) {
		Member member = memberDao.findOne(id);
		if(ParamConstant.FE_DATA_ORIGIN_THUMB_APP.equals(customerSource)){
			if(member.getSalt() == null ){
				SimpleDateFormat sdf = new SimpleDateFormat(ConfigurationHelper.getString("salt"));
				String timeStamp = sdf.format(new Date());
				member.setSalt(timeStamp);
			}
			String salt = member.getSalt();
			String pwd = MD5.MD5Encode(password).toUpperCase();//模拟前台app重置密码，需要经过两层md5加密
			member.setMrPassword(MD5.MD5Encode(pwd.concat(salt)).toUpperCase());
		} else {
			member.setMrPassword(AppUtils.encryptToMD5(password));
		}
		
		member.setMrModifyTime(new Date());
		return memberDao.save(member);
	}

	*//**
	 * @title 获取会员
	 * @param mobile
	 * @data 2014-11-5
	 *//*
	public Member findMemberByMobile(String mobile) {
		return memberDao.findMemberByMobile(mobile);
	}

	*//**
	 * @title 获取会员
	 * @param mobile
	 * @data 2014-11-5
	 *//*
	public Member addMember(String mobile, String password) {
		Member member = new Member();
		member.setMrPassword(AppUtils.encryptToMD5(password));
		member.setMrMobile(mobile);
		member.setMrLogin(mobile);
		member.setMrMemberState("0");// 0：待审核
		member.setMrSource("0");// 0-注册;1-申请
		member.setMrState("1");// 1-正常；0-删除
		member.setMrRegdate(new Date());
		member.setMrModifyTime(new Date());
		return memberDao.save(member);
	}

	*//**
	 * @Title: 会员绑定理财账户
	 * @Description:
	 * @param @param map
	 * @return Map<String,Object> 返回类型
	 * @throws
	 * @author CJ
	 * @date 2015年5月28日 上午10:24:45
	 *//*
	public Map<String, Object> bind(String name, String idCardCode,
			String mobile){
		Map<String, Object> m_result = new HashMap<String, Object>();
		Map<String, Object> addCustomer = new HashMap<String, Object>();
		CustomerVo customerVo = null;
		Customer customer = null;
		String MSG = "";
		try {
			m_result.put("result", "-1");
			logger.debug("bind start");
			mobile = mobile.trim();
			if (StringUtils.isBlank(mobile)) {
				MSG += "证件绑定失败,手机号码为空!";
			}
			name = URLDecoder.decode(name, "utf-8");  //转换格式
			if (StringUtils.isBlank(name)) {
				MSG += "证件绑定失败,会员姓名为空!";
			}
			idCardCode = idCardCode.trim();
			idCardCode = idCardCode.toUpperCase();
			if (StringUtils.isBlank(idCardCode)) {
				MSG += "证件绑定失败,证件为空!";
			} else {
				Member member = memberDao.findMemoberByIdCardNew(idCardCode);
				if (member != null) {
					m_result.put("result", "1");
					MSG += "证件绑定失败,证件已被绑定!";
				} else {
					List<ParameterEntiry> parameterEntiryList = SystemParameterHelper
							.queryParameterByPrTypeAndPrState("source", "1");
					member = memberDao.findMemberByMobile(mobile);// 有用户
					if (member != null) {
						if (member.getCustomerid() != null) { // 有绑定有理财
							customer = customerDao.findOne(member
									.getCustomerid());
							customerVo = getCustomerVo(customer, mobile,
									parameterEntiryList);
							m_result.put("customer", customerVo);
							m_result.put("result", "1");
							MSG = "已经有绑定";
						} else { // 没有绑定
							// 根据名字和身份证验证
							customer = ((CustomerDao) SpringContextHelper
									.getBean("customerDao"))
									.findAllByCrIdtypeAndCrIdnumAndCrName(
											ParamConstant.IDTYPESF, idCardCode,
											name);
							if (customer != null) { // 有理财用户
								saveMember(member, customer);
								customerVo = getCustomerVo(customer, mobile,
										parameterEntiryList);
								m_result.put("customer", customerVo);
								m_result.put("result", "0");
								MSG = "有用户没有绑定,操作成功;";
							} else { // 没有理财账户没有绑定
								// 根据身份证验证
								customer = ((CustomerDao) SpringContextHelper
										.getBean("customerDao"))
										.findAllByCrIdTypeAndCrIdnum(
												ParamConstant.IDTYPESF,
												idCardCode);
								if (customer != null) {
									if (!name.equals(customer.getCrName())) {
										// customer身份证号存在 名字不一致
										m_result.put("result", "2");
										MSG = "用户名与身份证号码不一致!";
									}
								} else {
									customerVo = new CustomerVo();
									customerVo.setName(name.trim());// 去除名字空格
									customerVo.setIdnum(idCardCode);
									customerVo.setMobilePhone(mobile);
									addCustomer = newAddCustomerFromCRM(customerVo);// 新增一条客户记录
									customer = customerDao.findOne(Long
											.valueOf(addCustomer.get(
													"customerId").toString()));
									saveMember(member, customer);
									customerVo = getCustomerVo(customer,
											mobile, parameterEntiryList);
									m_result.put("customer", customerVo);
									m_result.put("result", "0");
									MSG = "创建用户绑定,操作成功;";
								}
							}
						}
					} else {
						MSG = "会员不存在;";
					}
				}
			}
		} catch (Exception ex) {
			MSG = "系统出错：" + ex.getMessage();
			logger.error(ex.getMessage(), ex);
		} finally {
			logger.debug("bind end");
		}
		m_result.put("MSG", MSG);
		return m_result;
	}

	*//**
	 * @Title:从后台新增客户
	 * @Description:
	 * @param
	 * @return String 返回类型
	 * @throws
	 * @author CJ
	 * @date 2015年5月28日 上午10:24:45
	 *//*
	private Map<String, Object> newAddCustomerFromCRM(CustomerVo c) {
		Map<String, Object> m_result = new HashMap<String, Object>();
		Customer cu = new Customer();
		Staff staff = AuthorityHelper.getStaff();// 获取当前登录人
		cu.setCrCustomerNumber(newGetCustomerNumber()); // 客户编号
		if (c.getManagerCode() != null && !"".equals(c.getManagerCode())) {
			staff = UcHelper.getStaffWithAuth(c.getManagerCode());
			List<Staff> staffList = getStaffList2(staff.getId());
			cu.setCrGather(staff.getId()); // 客户经理id
			cu.setCrDeptManager(staffList.get(0).getId());// 直属主管
		}
		cu.setCrInputId(staff.getId()); // 录入人id
		if (c.getArea() == null || "".equals(c.getArea())) { // 默认大拇指app添加地区为“互联网”
			cu.setCrCityId(ParamConstant.APP_AREA); // 地区号
		}
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = formatDate.parse(c.getIdnum().substring(6, 10) + "-"
					+ c.getIdnum().substring(10, 12) + "-"
					+ c.getIdnum().substring(12, 14));
			cu.setCrBirthday(date);// 出生日期
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (Integer.parseInt(c.getIdnum().substring(14, 17)) % 2 == 0) {
			cu.setCrSex("0");// 性别
		} else if (Integer.parseInt(c.getIdnum().substring(14, 17)) % 2 != 0) {
			cu.setCrSex("1");// 性别
		}
		cu.setCrInputDate(new Date()); // 信息录入时间
		cu.setCrGatherDate(new Date()); // 采集日期
		cu.setCrName(c.getName().trim()); // 客户姓名
		cu.setCrIdtype(ParamConstant.IDTYPESF); // 证件类型
		cu.setCrIdnum(c.getIdnum()); // 证件号码
		cu.setCrState(ParamConstant.CRSTATE); // 客户状态 正常
		cu.setCrCustomerType(ParamConstant.CRCUSTOMERTYPE); // 客户类型(默认储备用户)
		cu.setCrCustomerLevel(ParamConstant.CRCUSTOMELEVEL); // 客户级别
		cu.setCrCategory(ParamConstant.CRCATEGORY);
		cu.setCrBusiCount(ParamConstant.CRBASICOUNT);
		// cu.setCrHope(c.getCrHope().toString()==null?"":c.getCrHope().toString());
		// //投资意愿
		cu.setCrChannel(ParamConstant.CRCHANNEL);// 渠道通路 其他 "26"
		// cu.setCrShare("1");
		cu.setCrSource(ParamConstant.SOURCE); // 后台来源 客户来源
		cu.setCrMemo(ParamConstant.CRM_NEWCUSTOMER); // 备注 后台新建用户
		cu.setCrMemo2(c.getCrMemo2()); // 备注
		Customer customer = customerDao.save(cu);
		Tel tel = new Tel(); // 添加通信方式附表，保存手机号码
		tel.setCustomerid(customer.getId()); // 客户id
		tel.setTlTelType(ParamConstant.MOBILE); // tel类型为手机
		tel.setTlCustType(ParamConstant.TelCustTypeKH); // 通信人类型为客户
		tel.setTlTelNum(c.getMobilePhone()); // 手机号码
		tel.setTlPriority(ParamConstant.priorityHighNum); // 优先级高，web系统取高的手机号码为客户的默认号码
		tel.setTlValid(ParamConstant.VALID); // 状态正常
		tel.setTlMemo(ParamConstant.TELAPPMEMO); // 备注
		tel.setTlInputDate(new Date()); // 录入日期
		tel.setTlInputId(c.getCustomerManagerId()); // 录入人，app默认为当前客户经理
		telDao.save(tel);
		m_result.put("customerId", customer.getId());
		return m_result;
	}

	*//**
	 * @Title: 获得员工列表
	 * @Description:
	 * @param
	 * @return String 返回类型
	 * @throws
	 * @author CJ
	 * @date 2015年5月28日 上午10:24:45
	 *//*
	private List<Staff> getStaffList2(Long id) {
		DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
		List<Staff> staffs = UcHelper.queryImmediateSupervisorByStaffId(id);
		List<Staff> staffvos = new ArrayList<Staff>();

		for (Staff staff : staffs) {
			Staff staffvo = new Staff();
			dozerBeanMapper.map(staff, staffvo);
			staffvos.add(staffvo);
		}
		return staffvos;
	}

	*//**
	 * @Title: 获取客户编号
	 * @Description:
	 * @param
	 * @return String 返回类型
	 * @throws
	 * @author CJ
	 * @date 2015年5月28日 上午10:24:45
	 *//*
	private String newGetCustomerNumber() {
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
				.getBean("customerDao")).findCustomerAllByInputDate(t_today,
				t_tomorrow);
		int num;
		if (null == customers)
			num = 0;
		else
			num = customers.size() + 1;
		String numString = String.format("%04d", num);
		return ParamConstant.APP_AREA + now + numString;
	}

	*//**
	 * @Title: 保存客户到会员表中
	 * @Description:
	 * @param
	 * @return 返回类型
	 * @throws
	 * @author CJ
	 * @date 2015年5月28日 上午10:24:45
	 *//*
	private void saveMember(Member member, Customer customer) {
		try {
			member.setMrName(customer.getCrName());
			member.setCrIdtype(customer.getCrIdtype());
			member.setMrIdnum(customer.getCrIdnum());
			member.setCustomerid(customer.getId());
			member.setMrManager(customer.getCrGather());
			if (customer.getCrGather() != null) {
				member.setAppAssignmentType(ParamConstant.MR_ASSIGNMENT_TYPE_AUTO);
				member.setMrMemberState(ParamConstant.MR_MEMBER_STATE_ASSIGN);
			}
			else {
				member.setAppAssignmentType(ParamConstant.MR_ASSIGNMENT_TYPE_INITALIZE);
				member.setMrMemberState(ParamConstant.MR_MEMBER_STATE_NOASSIGN);
			}
			member.setMrModifyTime(new Date());
			memberDao.save(member);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	*//**
	 * @Title: 返回客户信息
	 * @Description:
	 * @param
	 * @return CustomerVo 返回类型
	 * @throws
	 * @author CJ
	 * @date 2015年5月28日 上午10:24:45
	 *//*
	private CustomerVo getCustomerVo(Customer customer, String mobile,
			List<ParameterEntiry> parameterEntiryList) {
		CustomerVo customerVo = null;
		try {
			customerVo = new CustomerVo();
			customerVo.setId(customer.getId());
			customerVo.setCustomerNumber(customer.getCrCustomerNumber());
			customerVo.setName(customer.getCrName());
			customerVo.setIdtype(customer.getCrIdtype());
			customerVo.setIdnum(customer.getCrIdnum());
			customerVo.setMobilePhone(mobile);
			//获得客户来源的参数名称
			customerVo.setSource(prName(customer.getCrSource(),
					parameterEntiryList));
			customerVo.setCustomerManagerId(customer.getCrGather());
			customerVo.setCustomerManager(UcHelper.getStaffName(customer
					.getCrGather()));
			customerVo.setCrCustomerType(customer.getCrCustomerType());// 添加客户类型
			if (customer.getCrGather() != null) {
				Staff staff = UcHelper.getStaff(customer.getCrGather());
				if (staff != null) {
					customerVo.setManagerMobile(staff.getMobile());
					customerVo.setManagerCode(staff.getSystemName());
				}
				RecordStaffVo recordStaffVo = UcHelper.getRecordStaff(customer
						.getCrGather());
				if (recordStaffVo != null) {
					customerVo.setDepNameId(recordStaffVo.getDepId());
					customerVo.setDepName(recordStaffVo.getDepName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerVo;
	}

	*//**
	 * @Title: 得到客户来源
	 * @Description:
	 * @param
	 * @return String 返回类型
	 * @throws
	 * @author CJ
	 * @date 2015年5月28日 上午10:24:45
	 *//*
	private String prName(String crSource,
			List<ParameterEntiry> parameterEntiryList) {
		String prName = "";
		if (crSource != null && !"".equals(crSource)) {
			for (ParameterEntiry parameterEntiry : parameterEntiryList) {
				if (parameterEntiry.getPrValue().equals(crSource)) {
					prName = parameterEntiry.getPrName();
					break;
				}
			}
		}
		return prName;
	}
	
	//add by chenjiale thumb2 2016/5/10 start
	*//**
	 * @title 获取会员
	 * @param memberId
	 * @data 2014-11-5
	 *//*
	public Member findMember(Long memberId) {
		return memberDao.findMember(memberId);
	}
	
	*//**
	 * Thumb2审核结果处理
	 *//*
	public Member audit(Long id,String content, String result,String type) {
		// 更新数据
		Member member = memberDao.findOne(id);
		member.setMrMemberState("1"); // 1 为分配
		if("0".equals(type)){
			member.setPosTicketState(result);
		} else if("1".equals(type)){
			member.setSignInfoState(result);
		}
		
		member.setMrModifyTime(new Date());
		if(ParamConstant.APP_AUDIT_PASS.equals(member.getPosTicketState()) && ParamConstant.APP_AUDIT_PASS.equals(member.getSignInfoState())){
			member.setMrInfoState(ParamConstant.APP_AUDIT_PASS);
		}
		memberDao.save(member);
		AppAudit aa = new AppAudit();
		if("0".equals(type)){
			aa.setAtType("0");
		} else if("1".equals(type)){
			aa.setAtType("1");
		}
		Staff staff = AuthorityHelper.getStaff();
		aa.setAtContent(content);
		aa.setMemberId(id);
		aa.setAtState(result);
		aa.setAtInputTime(new Date());
		aa.setAtInputId(staff.getId());
		appAuditDao.save(aa);

		return member;
	}
	
	*//**
	 * @title 获取审核信息
	 * @param memberId
	 * @data 2014-11-5
	 *//*
	public List<AppAudit> getAuditLog(Long memberId, String type) {
		return appAuditDao.findAllAppAuditByMemberId(memberId,type);
	}
	*/
	//add by chenjiale thumb2 2016/5/10 end
}
