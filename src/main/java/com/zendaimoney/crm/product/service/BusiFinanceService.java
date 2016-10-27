package com.zendaimoney.crm.product.service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.zendaimoney.constant.LoggingSource;
import com.zendaimoney.constant.LoggingType;
import com.zendaimoney.crm.BaseService;
import com.zendaimoney.crm.customer.entity.Bankaccount;
import com.zendaimoney.crm.customer.entity.Customer;
import com.zendaimoney.crm.customer.entity.RecordStaff;
import com.zendaimoney.crm.customer.helper.CustomerHelper;
import com.zendaimoney.crm.customer.service.CustomerService;
import com.zendaimoney.crm.product.entity.BusiFinance;
import com.zendaimoney.crm.product.entity.BusiNofixFinance;
import com.zendaimoney.crm.product.entity.BusiRedeem;
import com.zendaimoney.crm.product.repository.BusiFinanceDao;
import com.zendaimoney.crm.product.util.Md5Algorithm;
import com.zendaimoney.crm.product.vo.FinanceMaturityRemind;
import com.zendaimoney.uc.rmi.vo.Staff;
import com.zendaimoney.utils.mapper.BeanMapper;

/**
 * 
 * @author zhanghao
 * @create 2012-11-26, 下午04:36:56
 * @version 1.0
 */
@Service
@Transactional
public class BusiFinanceService extends BaseService<BusiFinance> {
	
	private static Logger logger = LoggerFactory
	.getLogger(BusiFinanceService.class);
	
	@Autowired
	private BusiFinanceDao financeDao;
	/*
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private BusiProductService busiProductService;
	
	@Autowired
	private CrmManagerChangeService crmManagerChangeService;
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private RecordStaffService recordStaffService;
	
	@Autowired
	MemberDao memberDao;

	@Autowired
	private BusiNofixFinanceService busiNofixFinanceService;
	@Autowired
	private FinanceRedeemService financeRedeemService;
	
	@Autowired
	private BusiFinanceUcService busiFinanceUcService;
	
	*/
	/**
	 * 查找单笔理财业务
	 * 
	 * @author zhanghao
	 * @date 2013-01-16 17:09:13
	 * @param id业务id
	 * @return 理财业务对象
	 */
	public BusiFinance findOneFinance(Long id) {
		return financeDao.findOne(id);
	}
	
	/**
	 * 到期续投时、根据出借编号查询借款信息,但数据权限根据客户表里的采集人来控制
	 * 实际只会查出一笔理财
	 * @author wb_lyq
	 * @date 2015-11-9 上午10:05:03
	 * @param feLnedNo 出借编号
	 * @return
	 *//*
	public List<BusiFinance> getAllByFeLendNo(String feLendNo){
		StringBuffer searchBaseSQL = new StringBuffer();
		// 根据条件查询
		searchBaseSQL
				.append("SELECT A FROM BusiFinance A, Customer C WHERE A.customerid = C.id  AND  A.feValidState='1' AND A.feLendNo = '")
				.append(feLendNo).append("'");	
		return getContent(searchBaseSQL.toString());
	}
	
	
	*//**
	 * 条件查询所有理财业务
	 *  
	 * @author zhanghao
	 * @date 2013-02-27 16:54:00
	 * @param spec 查询条件
	 * @return
	 *//*
	public List<BusiFinance> findAll(Specification<BusiFinance> spec) {
		return financeDao.findAll(spec);
	}
	
	*//**
	 * 到期续投时、根据出借编号查询借款信息,但数据权限根据客户表里的采集人来控制
	 *  
	 * @author zhanghao
	 * @date 2013-6-21 上午10:05:03
	 * @param feLnedNo 出借编号
	 * @return
	 *//*
	public List<BusiFinance> findAllByFeLendNo(String feLendNo){
		StringBuffer searchBaseSQL = new StringBuffer();
		// 根据条件查询
		searchBaseSQL
				.append("SELECT A FROM BusiFinance A, Customer C WHERE A.customerid = C.id AND A.feState > 3 AND A.feLendNo = '")
				.append(feLendNo).append("'");
		//状态过滤
		searchBaseSQL.append(buildSearchSql(
				"A.feBusiState",
				new String[] { AuditHelper.getFortuneStateValue("回款已撮合"),
						AuditHelper.getFortuneStateValue("投资生效"),
						AuditHelper.getFortuneStateValue("回款待撮合"),
						AuditHelper.getFortuneStateValue("全部已撮合") }));
		//产品过滤
		searchBaseSQL.append(buildSearchSql("A.feProduct", new String[] {
				ParamConstant.FORTUNE_PRODUCT_ZHENGDASUIYUE,
				ParamConstant.FORTUNE_PRODUCT_ZHENGDAJIXI,
				ParamConstant.FORTUNE_PRODUCT_ZHENGDAFUXIANG,
				ParamConstant.FORTUNE_PRODUCT_ZHENGDASHUANGXIN,
				//增加PLUS产品   Sam.J  15.10.30
				ParamConstant.FORTUNE_PRODUCT_ZHENGDASHUANGXINPLUS,
				ParamConstant.FORTUNE_PRODUCT_ZHENGDAYUEWENYINGPLUS
				ParamConstant.FORTUNE_PRODUCT_ZHENGDAYUEWENYING }));
		// 添加数据权限-大拇指订单跳过
		BusiFinance busiFinance = financeDao.findByFeLendNo(feLendNo);
		if(!ParamConstant.FE_DATA_ORIGIN_THUMB_APP.equals(busiFinance.getFeDataOrigin()))
			addDataAuthority(searchBaseSQL, Share.CUSTOMER, "C", "crGather");
		return getContent(searchBaseSQL.toString());
	}
	
	
	*//**
	 * 续投时、根据出借编号查询双鑫+  岁悦+
	 *  
	 * @author wb_lyq
	 * @date 2015-10-28 上午10:05:03
	 * @param feLnedNo 出借编号
	 * @return
	 *//*
	public List<BusiFinance> findCustomizedByFeLendNo(String feLendNo){
		StringBuffer searchBaseSQL = new StringBuffer();
		// 根据条件查询
		searchBaseSQL
				.append("SELECT A FROM BusiFinance A, Customer C WHERE A.customerid = C.id AND A.feState >= 4 AND A.feLendNo = '")
				.append(feLendNo).append("'");
		//产品过滤
		searchBaseSQL.append(buildSearchSql("A.feProduct", new String[] {
				ParamConstant.FORTUNE_PRODUCT_ZHENGDASHUANGXINPLUS,
				ParamConstant.FORTUNE_PRODUCT_ZHENGDAYUEWENYINGPLUS }));
		// 添加数据权限
		addDataAuthority(searchBaseSQL, Share.CUSTOMER, "C", "crGather");
		return getContent(searchBaseSQL.toString());
	}
	
	*//**
	 * 根据出借编号查询借款信息,但数据权限根据客户表里的采集人来控制
	 *  
	 * @author ndh
	 * @date 2013-9-5 上午10:05:03
	 * @param feLnedNo 出借编号
	 * @return
	 *//*
	public List<BusiFinance> findAllByFeLendNoEx(String feLendNo){
		StringBuffer searchBaseSQL = new StringBuffer();
		// 根据条件查询
		searchBaseSQL
				.append("SELECT A FROM BusiFinance A, Customer C WHERE A.customerid = C.id AND A.feState > 3 AND A.feLendNo = '")
				.append(feLendNo).append("'");
		// 添加数据权限
		addDataAuthority(searchBaseSQL, Share.CUSTOMER, "C", "crGather");
		return getContent(searchBaseSQL.toString());
	}

	
	*//**
	 * 构建理财信息的业务状态查询SQL
	 *  
	 * @author zhanghao
	 * @date 2013-6-25 下午01:54:45
	 * @param busiState
	 * @return
	 *//*
	private static String buildSearchSql(String fieldName, String[] busiState){
		int len = busiState.length, i = 0;
		StringBuffer busiStateSql = new StringBuffer(" and " + fieldName + " in (");
		for (String state : busiState) {
			busiStateSql.append("'").append(state).append("'");
			if (i < len - 1)
				busiStateSql.append(",");
			i++;
		}
		busiStateSql.append(")");
		return busiStateSql.toString();
	}
	
	*//**
	 * 保存单笔投资信息
	 *  
	 * @author zhanghao
	 * @date 2013-5-22 上午10:56:39
	 * @param finance 投资信息对象
	 *//*
	@Transactional(readOnly = false)
	public void saveFinance(BusiFinance finance) {
		financeDao.save(finance);
	}
	
	*//**
	 * 业务审核
	 *  
	 * @author zhanghao
	 * @date 2013-02-25 17:37:52
	 * @param id 变更单id
	 * @param content 审核备注
	 * @param auditResult 审核结果
	 * @return 
	 *//*
	public ResultVo auditFinance(Long id, String content, String auditResult)  {
		ResultVo resultVo = null;
		BusiFinance finance = financeDao.findOne(id);
		//提交
		if(auditResult.equals(AuditHelper.getStateValue(ParamConstant.REQUEST_STATE_CHENGGONG))){
			resultVo = submitFinance(finance, content);
		}
		//退回
		else if(auditResult.equals(AuditHelper.getStateValue(ParamConstant.REQUEST_STATE_HUITUI))){
			resultVo = backFinance(finance, content);
		}
		// 拒绝
		else if (auditResult.equals(AuditHelper
				.getStateValue(ParamConstant.REQUEST_STATE_JUJUE))) {
			resultVo = refuseFinance(finance, content);
		}
		//修改返回值，增加业务id值,manager值，用于报表记录  by Sam.J  14.06.20
		resultVo.result.put("id",finance.getId()); 
		resultVo.result.put("manager",finance.getFeManager());
		return resultVo;
	}
	
	*//**
	 * 拒绝申请
	 *  
	 * @author Yuan Changchun
	 * @date 2013-12-3 上午09:34:51
	 * @param finance
	 * @param content
	 * @return
	 *//*
	private ResultVo refuseFinance(BusiFinance finance, String content) {
		// 判断这笔业务是否存在
		if (AuditHelper.isEmpty(finance))
			return new ResultVo(false, ParamConstant.ERROR_BUSI_NOT_EXIST);
		// 判断是否处于可拒绝状态
		if (!AuditHelper.isBack(finance.getFeState()))
			return new ResultVo(false,
					ParamConstant.ERROR_NOT_EXECUTE_CURRENT_OPERATION);
		finance.setFeState(AuditHelper
				.getStateValue(ParamConstant.REQUEST_STATE_JUJUE));
		// 添加审批日志
		AuditHelper.saveRefuseAuditLog(finance.getId(),
				ParamConstant.AUDIT_TYPE_FINANCE, content);
		this.financeDao.save(finance);
		return new ResultVo(true);
	}
	

	*//**
	 * 提交申请
	 * 
	 * @author zhanghao
	 * @date 2013-01-11 16:30:24
	 * @param id业务id
	 * @param content内容
	 * @return
	 *//*
	@Transactional(readOnly = false)
	public ResultVo submitFinance(BusiFinance finance, String content) {
		//判断这笔业务是否存在
		if (AuditHelper.isEmpty(finance))
			return new ResultVo(false, ParamConstant.ERROR_BUSI_NOT_EXIST);
		
		//判断这笔业务当时是否处于可提交状态
		if (!AuditHelper.isSubmit(finance.getFeState()))
			return new ResultVo(false, ParamConstant.ERROR_NOT_EXECUTE_CURRENT_OPERATION);
		
		// 判断对象的必填信息
		if (CustomerHelper.validateCustomerRequird(customerService .getCustomer(finance.getCustomerid())).size() > 0 ? true : false)
			return new ResultVo(false, ParamConstant.ERROR_CUSTOMER_NOT_FULL);
		// 判断发送消息到Fortune是否成功
		long stime = System.currentTimeMillis();
		String result = FortuneHelper.invokeCreate(String.valueOf(finance.getId()), "");
		long etime = System.currentTimeMillis();
		logger.info("本次接口提交共花费时间:" + (etime - stime) + ",提交理财对象ID：" + finance.getId());
		logger.info("result:::" + result);
		if(!FortuneHelper.INVOKE_SUCCESS.equals(result))
			return new ResultVo(false,result);
		// 更新投资申请的状态
		updateFeState(finance.getId(),AuditHelper.getStateValue(ParamConstant.REQUEST_STATE_CHENGGONG));
		
		// 更新客户的状态和业务数量
		updateCustomer(finance.getCustomerid());
		// 添加审批日志
		AuditHelper.saveSubmitAuditLog(finance.getId(), ParamConstant.AUDIT_TYPE_FINANCE, content);
		return new ResultVo(true);
	}
	
	*//**
	 * 更新客户信息
	 *  
	 * @author zhanghao
	 * @date 2013-03-04 20:43:28
	 * @param id 客户id
	 * @return
	 *//*
	public boolean updateCustomer(Long id) {
		boolean flag = false;
		Customer customer = customerService.getCustomer(id);
		//业务数量加一,如果是储备客户的话,那么则将其更新为客户
		customer.setCrBusiCount(customer.getCrBusiCount() == null ? 1 : (customer.getCrBusiCount() + 1));
		if(customer.getCrCustomerType().equals(AuditHelper.getParaValue("customerType", "储备客户"))) {
			customer.setCrCustomerType(AuditHelper.getParaValue("customerType", "客户"));
		}
		customerService.saveCustomer(customer);
		return flag;
	}
	
	*//**
	 * 退回申请
	 * 
	 * @author zhanghao
	 * @date 2013-01-11 16:31:45
	 * @param id
	 *            业务id
	 * @param content
	 *            内容
	 * @return
	 *//*
	@Transactional(readOnly = false)
	public ResultVo backFinance(BusiFinance finance, String content) {
		//判断这笔业务是否存在
		if (AuditHelper.isEmpty(finance))
			return new ResultVo(false, ParamConstant.ERROR_BUSI_NOT_EXIST);
		//判断是否处于可退回状态
		if (!AuditHelper.isBack(finance.getFeState()))
			return new ResultVo(false, ParamConstant.ERROR_NOT_EXECUTE_CURRENT_OPERATION);

		finance.setFeState(AuditHelper.getStateValue(ParamConstant.REQUEST_STATE_HUITUI));
		financeDao.save(finance);
		// 添加审批日志
		AuditHelper.saveBackAuditLog(finance.getId(), ParamConstant.AUDIT_TYPE_FINANCE, content);
		return new ResultVo(true);
	}
	
	*//**
	 * 更新理财状态不使用数据库锁的概念
	 *  
	 * @author zhanghao
	 * @date 2013-02-28 22:27:52
	 * @param id 业务id
	 * @param feState 状态
	 *//*
	public void updateFeState(Long id, String feState) {
		Query query = em.createQuery("update BusiFinance set feState = :feState where id = :id");
		query.setParameter("feState", feState);
		query.setParameter("id", id);
		query.executeUpdate();
	}

	*//**
	 * 更新理财业务状态
	 * 
	 * @author zhanghao
	 * @date 2013-01-18 18:00:51
	 * @return
	 *//*
	@Transactional(readOnly = false)
	public boolean updateFinance(BusiFinance finance) {
		return financeDao.save(finance) != null ? true : false;
	}
	
	*//**
	 * 保存理财业务信息
	 *  
	 * @author zhanghao
	 * @date 2013-02-26 23:58:02
	 * @param finance 理财对象
	 * @param state 状态
	 * @return
	 * @throws Exception 
	 *//*
	public ResultVo saveBusiFinance(BusiFinance finance, String state) throws Exception {
		//如果不是新建的话,那么则需要验证客户的必填信息
		if(!AuditHelper.isNew(state)) {
			List<String> messageList = isSave(finance);
			if(messageList.size() > 0) 
				return new ResultVo(false,messageList); 
		}
		Date date = new Date();
		Staff staff = AuthorityHelper.getStaff();
		finance.setFeDataOrigin(ParamConstant.FE_DATA_ORIGIN_PC);// 线下下单  Sam.J 14.11.28
		//爱特鸿金宝增加合同编号生成   add by Sam.J  15.7.9
		if(ParamConstant.FORTUNE_PRODUCT_AITEHONGJINBAO.equals(finance.getFeProduct())&&finance.getFeContractNo()==null){
			String contractNumber=ProductUtil.contractNumberForATHJB();
			if(contractNumber==null){
				return new ResultVo(false,"构造合同编号出错!");
			}
			finance.setFeContractNo(contractNumber);
		}
		setFinanceValue(finance, date, staff.getId(), state, date, buildFeLendNo(finance.getCustomerid()), date, staff.getId());
		
		//申请匹配模块增加理财中心字段 2016/6/24
		finance.setFeApplyMatchStatus(ParamConstant.APPLY_MATCH_STATUS_NEW);//申请匹配状态
		financeDao.save(finance);
		
		//保存理财中心相关数据
		RecordStaffVo recordStaffVo = UcHelper.getRecordStaff(finance.getFeManager());
		if(recordStaffVo != null && !Strings.isNullOrEmpty(recordStaffVo.getParentDepName())){
			busiFinanceUcService.saveOrUpdate(finance,null);
		}
		//记录审核日志
		AuditHelper.saveCreateAuditLog(finance.getId(), ParamConstant.AUDIT_TYPE_FINANCE, state);
		//记录操作日志信息
		LoggingHelper.createLogging(LoggingType.申请, LoggingSource.产品申请, LoggingHelper.builderLogContent(finance.getClass().getSimpleName(),finance.getId()));
		
		//修改返回值，增加业务id值，用于报表记录  by Sam.J  14.06.20
		ResultVo  result=new ResultVo(true);
		result.result.put("id",finance.getId());
		return result;
	}
	
	
	*//**
	 * 保存理财业务信息FOR 大拇指app下单
	 *  
	 * @author wangwm
	 * @date 2014-11-11 16：42：14
	 * @param finance 理财对象
	 * @param state 状态
	 * @return
	 *//*
	public ResultVo saveBusiFinance4App(Map<String, Object> map) {
		BusiFinance finance = new BusiFinance();
		if(map.get("feProduct")!=null){// 产品
			finance.setFeProduct(String.valueOf(map.get("feProduct")));
		}
		if(map.get("feAmount") != null){//金额
			finance.setFeAmount(Double.valueOf((String)map.get("feAmount")));
		}
		if(map.get("feRequestDate") != null){
			finance.setFeRequestDate((Date)map.get("feRequestDate"));
		}
		if(map.get("customerId") != null){
			finance.setCustomerid(Long.valueOf((String) map.get("customerId")));
		}
		if(map.get("feManager") != null){
			finance.setFeManager(Long.valueOf((String) map.get("feManager")));
		}
		
		//2015.5.13 增加债权id
		if(map.get("loanId") !=null){
			finance.setFeLoanId(String.valueOf(map.get("loanId")));
		}
		
		//TODO 后期APP发起活动再做跟踪
		if(map.get("feActivityId") != null){//活动ID
			finance.setFeActivityId((String)map.get("feActivityId"));
		}
		
		if(ParamConstant.CRM_PRODUCT_YOUYIJIHUA.equals(finance.getFeProduct())){ //如果是优益计划，使用优益计划的合同编号
			finance.setFeContractNo(ProductUtil.contractNumber(ParamConstant.CON_NUM_TYPE_YXJH));
		}else{
			finance.setFeContractNo(ProductUtil.contractNumber(ParamConstant.CON_NUM_TYPE_APP));//合同编号(大拇指APP)
		}
		finance.setFePayStatus(ParamConstant.FE_PAY_STAUTS_UNPAY);//付款状态--未付款
		finance.setFeDataOrigin(ParamConstant.FE_DATA_ORIGIN_APP);//数据来源APP
		
		//fortune需要写入的值
		finance.setFePaymentWay(ParamConstant.FE_PAYMENT_WAY_SELF);//付款方式    1.自行转账
		if(ParamConstant.CRM_PRODUCT_YOUYIJIHUA.equals(finance.getFeProduct()))   //针对优益计划 
			finance.setFeManageFee(ParamConstant.FE_FEE_MANAGER_DISCOUNT_ZREO);//管理费折扣 默认为 0%    
		finance.setFeContinueProduct(ParamConstant.FE_CONTINUE_PRODUCT_ONE);// 进行债券转让
		finance.setFeRiskCompensation(ParamConstant.FE_RISK_COMPENSATION_TRUE);//是否风险补偿 1:是 0：否
		
		
		Date date = new Date();
		setFinanceValue(finance, date, 99999999L, ParamConstant.FE_REQUEST_STATE_CREATE, date, buildFeLendNo(finance.getCustomerid()), date, 99999999L);
		financeDao.save(finance);
		//记录审核日志
		//AuditHelper.saveCreateAuditLog(finance.getId(), ParamConstant.AUDIT_TYPE_FINANCE, ParamConstant.FE_REQUEST_STATE_CREATE);
		AuditHelper.saveAuditLogWithPaystatus(finance.getId(), ParamConstant.AUDIT_TYPE_FINANCE,"新建","","",
				ParamConstant.FE_REQUEST_STATE_CREATE,"",ParamConstant.FE_PAY_STAUTS_UNPAY);
		//记录操作日志信息
		LoggingHelper.createLogging(LoggingType.申请, LoggingSource.产品申请, LoggingHelper.builderLogContent(finance.getClass().getSimpleName(),finance.getId()));
		//增加报表 recordStaff 表的记录
		AddRecordStaff(finance.getFeManager(),ParamConstant.FE_REQUEST_STATE_CREATE,finance.getId()+"");
		ResultVo  resultvo=new ResultVo(true);
		resultvo.result.put(ParamConstant.FE_APP_SAVE_RESULT, finance);
		return resultvo;
	}
	
	
	*//**
	 * 保存理财业务信息   为app 提供接口
	 *  
	 * @author zhanghao
	 * @date 2013-02-26 23:58:02
	 * @param finance 理财对象
	 * @param state 状态
	 * @return
	 *//*
	public ResultVo saveBusiFinanceApp(BusiFinance finance, String state,Long staffId) {
		//如果不是新建的话,那么则需要验证客户的必填信息
		if(!AuditHelper.isNew(state)) {
			List<String> messageList = isSave(finance);
			if(messageList.size() > 0) 
				return new ResultVo("-1","失败!",messageList); 
		}
		Date date = new Date();
		setFinanceValue(finance, date,staffId, state, date, buildFeLendNo(finance.getCustomerid()), date, staffId);
		financeDao.save(finance);
		//记录审核日志
		AuditHelper.saveCreateAuditLogApp(staffId,finance.getId(), ParamConstant.AUDIT_TYPE_FINANCE, state);
		//记录操作日志信息
		LoggingHelper.createLoggingApp(staffId,LoggingType.申请, LoggingSource.产品申请, LoggingHelper.builderLogContent(finance.getClass().getSimpleName(),finance.getId()));
		return new ResultVo("0","成功!",finance.getId());
	}
	
	*//**
	 * 更新理财业务信息
	 *  
	 * @author zhanghao
	 * @date 2013-02-26 23:58:46
	 * @param finance 理财对象
	 * @param state 状态
	 * @return
	 * @throws Exception 
	 *//*
	public ResultVo updateBusiFinance(BusiFinance finance,String state) throws Exception {
		//如果不是新建的话,那么则需要验证客户的必填信息
		if(!AuditHelper.isNew(state)) {
			List<String> messageList = isSave(finance);
			if(messageList.size() > 0) 
				return new ResultVo(false,messageList); 
		}
		ResultVo resultVo = null;
		Date date = new Date();
		BusiFinance oldFinance = financeDao.findOne(finance.getId());
		String oldState = oldFinance.getFeState();//获取之前的状态
		if ("1".equals(state)) {
			state = oldState;
		}
		Staff staff = AuthorityHelper.getStaff();
		finance.setFeDataOrigin(ParamConstant.FE_DATA_ORIGIN_PC);// 线下下单  Sam.J 14.11.28
		setFinanceValue(finance, oldFinance.getFeInputTime(), oldFinance.getFeInputId(), state, oldFinance.getFeRequestDate(), oldFinance.getFeLendNo(), date, staff.getId());
		financeDao.save(finance);
		
		//保存理财中心相关数据
		RecordStaffVo recordStaffVo = UcHelper.getRecordStaff(finance.getFeManager());
		if(recordStaffVo != null && !Strings.isNullOrEmpty(recordStaffVo.getParentDepName())){
			busiFinanceUcService.saveOrUpdate(finance,null);
		}
		
		//如果是提交状态,记录审核日志
		if(state.equals(AuditHelper.getStateValue(ParamConstant.REQUEST_STATE_DAIZHIJIAN))){
			AuditHelper.saveAuditLog(finance.getId(), ParamConstant.AUDIT_TYPE_FINANCE, "提交到质检","",oldState,state);
		}  
		//记录操作日志信息
		LoggingHelper.createLogging(LoggingType.修改, LoggingSource.产品申请, LoggingHelper.builderLogContent(finance.getId(), oldFinance, finance, getNeedList()));
		//修改返回值，增加业务id值，用于报表记录  by Sam.J  14.06.20
		ResultVo  result=new ResultVo(true);
		result.result.put("id",finance.getId());
		return result;
	}
	
	*//**
	 * 更新理财业务信息4APP
	 *  
	 * @author wangwm
	 * @date 2014-11-13 10:21:46
	 * @param  map
	 * @return
	 * @throws ParseException 
	 *//*
	public ResultVo updateBusiFinance4APP(Map<String, Object> map) throws ParseException {
		//验签校验(密钥信息)
		ResultVo resultVo = null;
		Object object = map.get("sign");
		String md5SignStr = "";
		StringBuffer sb = new StringBuffer();
		sb.append(map.get("orderNo")).append("|").append(map.get("channelOrderNo")).append("|").
		   append(map.get("sernum")).append("|").append(map.get("merchantCode")).append("|").append(map.get("payAmt")).append("|").append(map.get("payTime")).
		   append("|").append(map.get("resultCode")).append("|").append(ConfigurationHelper.getString("crm.app.transaction.key"));//ParamConstant.FE_CRM_APP_KEY java配置文件  改成 分离配置
		logger.info("接收到的验证参数值:"+sb.toString());
		//TODO 上线前需要更换对应线上密钥--- ParamConstant.FE_CRM_APP_KEY ---密钥
		try {//根据验签规则生成验签信息
			md5SignStr = Md5Algorithm.getInstance().md5Digest(sb.toString().getBytes("iso8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		logger.info("[网关传回验签值]"+object+"---------"+"[CRM验签值]"+md5SignStr);
		if(md5SignStr.equals(object)){//验签规则通过则执行
			//----update  sam.J 
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			String sernum = (String) map.get("sernum");
			if(sernum == null||sernum.length()<= 16) return resultVo; //流水为空的话 异常处理  Sam.J  14.12.05
			String busiId = sernum.substring(16, sernum.length()); //去掉前16位，取后面的即为业务的id 
			Long busiIdLong = Long.parseLong(busiId);
			BusiFinance parmfinance = financeDao.findByFeLendNoAndId((String)map.get("channelOrderNo"),busiIdLong);//channelOrderNo --出借编号  busiIdLong 业务id
			if(parmfinance != null){
				if(parmfinance.getFeBusiState()!=null){ //判断是否有fortune状态，如果已有值，表明该记录已到fortune端  直接返回成功
					resultVo=new ResultVo(true);
					resultVo.result.put(ParamConstant.FE_APP_UPDATE_RESULT, parmfinance);
					return resultVo;
				}
				
				//判断回传金额与数据库中存在的金额是否一致
				if(Double.parseDouble((map.get("payAmt")+""))!=(parmfinance.getFeAmount()*100)){
					//测试和开发环境使用1分钱进行测试
					String plat =ConfigurationHelper.getString("crm.distributed.system.name");
					if(!( Double.parseDouble((map.get("payAmt")+""))==1d && ( plat.equals("crm_zd_dev") || plat.equals("crm_zd_test") ) )){
					logger.info("传入金额与本地金额不符     传入金额:"+map.get("payAmt")+"分， 本地金额:"+parmfinance.getFeAmount()*100+"分");
						return resultVo;
					}else{
						logger.info("测试环境     传入金额:"+map.get("payAmt")+"分， 本地金额:"+parmfinance.getFeAmount()*100+"分");
					}
				}
				
				parmfinance.setFeAppMemo(sernum);//2015-5-14 保存前端设备流水号 提交退款需要
				parmfinance.setFePayStatus(ParamConstant.FE_PAY_STAUTS_PAYDONE);//手动设置付款成功后为默认的付款已处理状态
				if(map.get("settleDate") != null){
					Date settleDate = sdf2.parse((String)map.get("settleDate"));
					parmfinance.setFeReconciliationDate(settleDate);//以付款网关接口传回来的时间为准
				}
				if(map.get("payTime") != null){
					Date fePayTime = sdf1.parse((String)map.get("payTime"));
					parmfinance.setFePayTime(fePayTime);//支付时间
				}

				
				//银行卡信息相关
				Bankaccount b = new Bankaccount();
				Customer c = customerService.getCustomer(Long.valueOf(((String)map.get("userNo")).toString().trim())); //TODO
				//c.setId(Long.valueOf((String)map.get("userNo")));//客户ID
				b.setCrmCustomer(c);
				b.setBaAccount((String)map.get("bankCardNo"));//银行卡号
				b.setBaBankCode((String)map.get("bankCode"));//银行代码
				b.setBaBankName((String)map.get("bankName"));//银行名称
				b.setBaAccountName((String)map.get("userName"));//客户姓名
				b.setBaValid("1"); //启用
				b.setBaAccountType("1");// 银行卡类型
				//新增或返回银行卡信息
				Map resultMap = CustomerHelper.saveOrGetBankByCustomer(b);
				if(resultMap != null){
					if(ParamConstant.FE_CRM_RESULT_SUCCESS.equals(resultMap.get("MSG"))){//成功则设置
						Bankaccount ba =(Bankaccount) resultMap.get("result");
						parmfinance.setFeDeductAccount(ba.getId());
						parmfinance.setFeReturnAccount(ba.getId());
					}
				}
				
				setFinanceValue(parmfinance, parmfinance.getFeInputTime(), 99999999L, ParamConstant.FE_REQUEST_STATE_AUDIT, parmfinance.getFeRequestDate(), parmfinance.getFeLendNo(), date, 99999999L);
				BusiFinance finance = financeDao.save(parmfinance);
				
				//如果是提交状态,记录审核日志
				//AuditHelper.saveAuditLog(finance.getId(), ParamConstant.AUDIT_TYPE_FINANCE, "提交到质检","",ParamConstant.FE_REQUEST_STATE_CREATE,ParamConstant.FE_REQUEST_STATE_PASS);
				AuditHelper.saveAuditLogWithPaystatus(finance.getId(), ParamConstant.AUDIT_TYPE_FINANCE, "提交到质检","",
						ParamConstant.FE_REQUEST_STATE_CREATE,ParamConstant.FE_REQUEST_STATE_AUDIT,ParamConstant.FE_PAY_STAUTS_UNPAY,ParamConstant.FE_PAY_STAUTS_PAYDONE);
				//增加报表 recordStaff 表的记录
				AddRecordStaff(finance.getFeManager(),ParamConstant.FE_REQUEST_STATE_AUDIT,finance.getId()+"");
				//记录操作日志信息
				LoggingHelper.createLogging(LoggingType.修改, LoggingSource.产品申请, LoggingHelper.builderLogContent(finance.getId(), parmfinance, finance, getNeedList()));
				resultVo=new ResultVo(true);
				resultVo.result.put(ParamConstant.FE_APP_UPDATE_RESULT, finance);
				return resultVo;
			}
		}
		return resultVo;
	}
	
	*//**
	 * 更新理财业务信息               为app提供接口
	 *  
	 * @author zhanghao
	 * @date 2013-02-26 23:58:46
	 * @param finance 理财对象
	 * @param state 状态
	 * @return
	 *//*
	public ResultVo updateBusiFinanceApp(BusiFinance finance,String state,Long staffId) {
		//如果不是新建的话,那么则需要验证客户的必填信息
		if(!AuditHelper.isNew(state)) {
			List<String> messageList = isSave(finance);
			if(messageList.size() > 0) 
				return new ResultVo("-1","失败!",messageList);
		}
		ResultVo resultVo = null;
		Date date = new Date();
		BusiFinance oldFinance = financeDao.findOne(finance.getId());
		String oldState = oldFinance.getFeState();//获取之前的状态
		if ("1".equals(state)) {
			state = oldState;
		}
		finance.setOptlock(oldFinance.getOptlock());
		finance.setFePreviousId(oldFinance.getFePreviousId());
		setFinanceValue(finance, oldFinance.getFeInputTime(), oldFinance.getFeInputId(), state, oldFinance.getFeRequestDate(), oldFinance.getFeLendNo(), date, staffId);
		financeDao.save(finance);
		//如果是提交状态,记录审核日志
		if(state.equals(AuditHelper.getStateValue(ParamConstant.REQUEST_STATE_DAIZHIJIAN))){
			AuditHelper.saveAuditLogApp(staffId,finance.getId(), ParamConstant.AUDIT_TYPE_FINANCE, "移动终端提交到质检","",oldState,state);
		}  
		//记录操作日志信息
		LoggingHelper.createLoggingApp(staffId,LoggingType.修改, LoggingSource.产品申请, LoggingHelper.builderLogContent(finance.getId(), oldFinance, finance, getNeedList()));
		resultVo = new ResultVo(true,finance.getId());
		 return new ResultVo("0","成功!",finance.getId());
	}
	*//**
	 * 删除投资信息
	 *  
	 * @author zhanghao
	 * @date 2013-04-16 11:05:42
	 * @param id 投资ID
	 * @return 是否删除成功
	 *//*
	public ResultVo delBusiFinance(Long id) {
		Query query = em.createQuery("delete from BusiFinance where id = :id");
		query.setParameter("id", id);
		query.executeUpdate();
		return new ResultVo(true,"删除成功!");
	}
	
	*//**
	 * 验证理财申请是否可以保存
	 *  
	 * @author zhanghao
	 * @date 2013-03-06 10:22:40
	 * @param id
	 * @return
	 *//*
	public List<String> isSave(BusiFinance finance) {
		Customer customer = customerService.getCustomer(finance.getCustomerid());
		List<String> customerBlankMessageList = CustomerHelper.validateCustomerRequird(customer);
		if(customerBlankMessageList.size() > 0) {
			if(customer.getCrCustomerType().equals(AuditHelper.getParaValue("customerType", "储备客户"))){
				customerBlankMessageList.add("1");
			}else{
				customerBlankMessageList.add("2");
			}
			customerBlankMessageList.add(String.valueOf(customer.getId()));
		}
		return customerBlankMessageList;
	}
	
	*//**
	 * 获取理财修改时的字段名
	 *  
	 * @author zhanghao
	 * @date 2013-02-26 23:59:15
	 * @return
	 *//*
	public List<String> getNeedList() {
		return Lists.newArrayList("feProduct", "feAmount","feInvestDate", "feDivestDate", "feProtocolVersion",
				"feRiskCompensation", "feTimeInvestAmount","feTimeInvestStart", "feTimeInvestEnd", "fePaymentWay",
				"feDeductAccount", "feReturnAccount", "feContinueProduct", "feManager", "feManageFee", "feContractNo");
	}
	
	*//**
	 * 给理财对象赋值
	 *  
	 * @author zhanghao
	 * @date 2013-02-26 23:59:54
	 * @param finance 理财对象
	 * @param feInputTime 输入时间
	 * @param feInputId 输入人
	 * @param feState 状态
	 * @param feRequestDate 申请日期
	 * @param feLendNo 出借编号
	 * @param feModifyDate 最后一次修改日期
	 * @param feModifyId 最后一次修改人
	 *//*
	public void setFinanceValue(BusiFinance finance,Date feInputTime, Long feInputId, 
			String feState, Date feRequestDate, String feLendNo, Date feModifyDate, Long feModifyId) {
		finance.setFeInputTime(feInputTime);
		finance.setFeInputId(feInputId);
		finance.setFeState(feState);
		finance.setFeRequestDate(feRequestDate);
		finance.setFeLendNo(feLendNo);
		finance.setFeModifyId(feModifyId);
		finance.setFeModifyDate(feModifyDate);
		//增加订单状态位标志  为正常状态   Sam.J 14.12.16
		finance.setFeValidState(ParamConstant.VALID); //设为正常启用状态

	}
	
	*//**
	 * 检查新增合同编号的唯一性
	 *  
	 * @author zhanghao
	 * @date 2013-03-01 10:36:05
	 * @param feContractNo 合同编号
	 * @return
	 *//*
	public boolean checkFeContractNoUniqueAdd(String feContractNo) {
		return financeDao.countFeContractNo(feContractNo) > 0 ? false : true;
	}
	
	*//**
	 * 检查新增合同编号的唯一性
	 *  
	 * @author zhanghao
	 * @date 2013-5-22 上午11:00:39
	 * @param feContractNo 合同编号
	 * @param id 理财业务对象ID
	 * @return
	 *//*
	public boolean checkFeContractNoUniqueEdit(String feContractNo,Long id) {
		return financeDao.countFeContractNo1(feContractNo, id) > 0 ? false : true;
	}
	
	*//**
	 * 返回所有符合条件的到期提醒数据
	 *  
	 * @author zhanghao
	 * @date 2013-5-21 下午05:21:28
	 * @param spec 查询条件
	 * @param pageRequest 分页对象
	 * @return
	 *//*
	public Page<FinanceMaturityRemind> findAllMaturityRemind(Map<String, SearchFilter> conditionMap,PageRequest pageRequest,Long...staffIds){
		StringBuffer searchBaseSQL = new StringBuffer(
				"select A from BusiFinance A,Customer B where A.customerid = B.id ");
		searchBaseSQL
				.append(" and ((A.feDivestDate between ")
				.append(toDateYYYYMMDD(DateUtil.getYMDTime(new Date())))
				.append(" and ")
				.append(toDateYYYYMMDD(DateUtil
						.getCurrDateAfterDay(Integer.parseInt(new PropertiesLoader(
								"crm.properties")
								.getProperty("finance.continue.invest.remind.day")))));

		//状态过滤
		searchBaseSQL.append(buildSearchSql(
				"A.feBusiState",
				new String[] { AuditHelper.getFortuneStateValue("回款已撮合"),
						AuditHelper.getFortuneStateValue("投资生效"),
						AuditHelper.getFortuneStateValue("回款待撮合"),
						AuditHelper.getFortuneStateValue("全部已撮合") }));
		//产品过滤
		searchBaseSQL.append(buildSearchSql("A.feProduct", new String[] {
				ParamConstant.FORTUNE_PRODUCT_ZHENGDASUIYUE,
				ParamConstant.FORTUNE_PRODUCT_ZHENGDAJIXI,
				ParamConstant.FORTUNE_PRODUCT_ZHENGDAFUXIANG,
				ParamConstant.FORTUNE_PRODUCT_ZHENGDASHUANGXIN }));
		searchBaseSQL.append(") or ");
		//月稳盈产品///////////////////////////////
		searchBaseSQL
		.append("(A.feDivestDate between ")
		.append(toDateYYYYMMDD(DateUtil.getYMDTime(new Date())))
		.append(" and ")
		.append(toDateYYYYMMDD(DateUtil.getCurrDateAfterDay(Integer.parseInt(new PropertiesLoader(
				"crm.properties")
				.getProperty("finance.continue.ywy.invest.remind.day")))));
		// 状态过滤
		searchBaseSQL.append(buildSearchSql(
				"A.feBusiState",
				new String[] { AuditHelper.getFortuneStateValue("回款已撮合"),
						AuditHelper.getFortuneStateValue("投资生效"),
						AuditHelper.getFortuneStateValue("回款待撮合"),
						AuditHelper.getFortuneStateValue("全部已撮合") }));
		// 产品过滤
		searchBaseSQL.append(buildSearchSql("A.feProduct", new String[] {
				ParamConstant.FORTUNE_PRODUCT_ZHENGDAYUEWENYING}));
		searchBaseSQL.append(")");
		/////////////////////////////////////////////////
		searchBaseSQL.append(")");
		//添加数据权限
		if(staffIds==null||staffIds.length<1){
			searchBaseSQL = addDataAuthority(searchBaseSQL, Share.PRODUCT_REQUEST, "A", "feManager");
		}else {
			searchBaseSQL = addDataAuthority(searchBaseSQL, Share.PRODUCT_REQUEST, "A", "feManager",staffIds);
		}
		
		//添加数据权限   修改数据读取客户经理归属  从订单中的客户经理改为 客户信息中的客户经理
		if(staffIds==null||staffIds.length<1){
			searchBaseSQL = addDataAuthority(searchBaseSQL, Share.PRODUCT_REQUEST, "B", "crGather");
		}else {
			searchBaseSQL = addDataAuthority(searchBaseSQL, Share.PRODUCT_REQUEST, "B", "crGather",staffIds);
		}
		
		//查询总记录数
		long total = getTotal(conditionMap,new StringBuffer(searchBaseSQL));
		
		//查询结果集
		List<BusiFinance> financeList = getContent(conditionMap,searchBaseSQL, pageRequest);
		
		List<FinanceMaturityRemind> remindList = new ArrayList<FinanceMaturityRemind>();
		for (BusiFinance finance : financeList) {
			remindList.add(formatMaturityRemind(finance));
		}
		return new PageImpl<FinanceMaturityRemind>(remindList, pageRequest, total);
	}
	
	public String toDateYYYYMMDD(String date){
		return "TO_DATE('" + date + "','yyyy-MM-dd')";
	}
	
	*//**
	 * 格式化到期提醒对象信息
	 *  
	 * @author zhanghao
	 * @date 2013-5-22 上午10:38:13
	 * @param finance 理财业务对象
	 * @return
	 *//*
	public FinanceMaturityRemind formatMaturityRemind(BusiFinance finance) {
		FinanceMaturityRemind remind = new FinanceMaturityRemind();
		remind.setCustId(finance.getCustomerid());
		remind.setCustName(customerService.getCustomer(finance.getCustomerid()).getCrName());
		remind.setFinanceId(finance.getId());
		remind.setProductId(Long.valueOf(finance.getFeProduct()));
		busiProductService.findOneProduct(Long.valueOf(finance.getFeProduct()));
		remind.setProductName(busiProductService.findOneProduct(Long.valueOf(finance.getFeProduct())).getPtProductName());
		remind.setAmount(finance.getFeAmount());
		remind.setLendNo(finance.getFeLendNo());
		remind.setCustManagerId(finance.getFeManager());
		remind.setCustManagerName("");
		//增加合同编号  Sam.J  14.10.21
		remind.setContractNo(finance.getFeContractNo());
		try {
			remind.setDebtRightsValue(Double.valueOf(CommonUtil.round(FortuneHelper.getFieldsValue(finance.getId(), "pv").get("pv").toString(), 2, 4)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//如果是月收或年丰产品，则提醒日期重新赋值
		if(ParamConstant.FORTUNE_PRODUCT_ZHENGDAYUESHOU.equals(finance.getFeProduct())||ParamConstant.FORTUNE_PRODUCT_ZHENGDANIANFENG.equals(finance.getFeProduct())){
			Date remindDate=finance.getFeClosedDate();
			if(remindDate==null){ //判断如果封闭期日期该值为空，则重新计算封闭到期日
				Calendar cal = Calendar.getInstance();  
		        cal.setTime(finance.getFeInvestDate()); //设定时间为投资起始日
		        cal.add(Calendar.YEAR, 1);//投资起始日加1年，则为原来的投资结束日
		        remindDate=cal.getTime(); 
			}
			remind.setMaturityDate(remindDate);
		}else{
			remind.setMaturityDate(finance.getFeDivestDate());
		}		
		remind.setRemainDays(DateUtil.getDays(DateUtil.parse(new Date(), "yyyy-MM-dd"), remind.getMaturityDate()));
		return remind;
	}
	
	*//**
	 * 根据客户ID查询投资信息
	 *  
	 * @author zhanghao
	 * @date 2013-5-22 上午10:52:24
	 * @param customerId  客户ID
	 * @return 符合条件的投资信息集合
	 *//*
	public List<BusiFinance> findAllByCustomerid(Long customerId) {
		return  financeDao.findAllByCustomeridAndFeValidState(customerId,ParamConstant.VALID); //修改查询方法为带删除与否标志位查询
	}

	public List<BusiFinance> findAllByfeManagerWithNotCustomer(Long crGatherId) {
		return financeDao.findAllByfeManagerWithNotCustomer(crGatherId);
	}
	
	*//**
	 * 根据客户ID和客户ID查询投资信息
	 *  
	 * @author zhanghao
	 * @date 2013-5-22 上午10:55:10
	 * @param id 客户ID
	 * @param crGatherId 客户经理ID
	 * @return 符合条件的投资信息集合
	 *//*
	public List<BusiFinance> findAllByCustomeridAndFeManager(Long id,Long crGatherId) {
		return financeDao.findAllByCustomeridAndFeManagerAndFeValidState(id,crGatherId,ParamConstant.VALID); //修改查询方法为带删除与否标志位查询
	}
	
	*//**
	 * 构建出借编号
	 *  
	 * @author zhanghao
	 * @date 2013-01-06 15:55:50
	 * @param customerId 客户id
	 * @return 出借编号
	 *//*
	public static synchronized  String buildFeLendNo(Long customerId){
		String lendNo = "";
		lendNo += ((CustomerService) SpringContextHelper
				.getBean("customerService")).getCustNumber(customerId);
		lendNo += "-" + AuditHelper.getParaValue("BusinessType", "理财"); 
		//客户业务量
		String count = ((BusiFinanceDao) SpringContextHelper
				.getBean("busiFinanceDao")).findCustomerFinanceMaxCount(customerId);
		Long customerFinanceTotalCount =  Long.valueOf(count == null ? "0" : count);
		//生成单个客户理财借款编号最后三位
		if (customerFinanceTotalCount == null || customerFinanceTotalCount == 0) {
			lendNo += "-001";
		}
		else
			lendNo += "-" + String.format("%03d", customerFinanceTotalCount + 1);
		return lendNo;
	}
	
	*//**
	 * 查找客户当前的最大出借编号
	 *  
	 * @author zhanghao
	 * @date 2013-5-28 下午04:39:40
	 * @param custId
	 * @return
	 *//*
	public String findCustMaxLendNo(Long custId){
		return financeDao.findCustMaxLendNo(custId);
	}
	
	
	public Long getIdByFeLendNo(String feLendNo){
		return financeDao.getIdByFeLendNo(feLendNo);
	}

	*//**
	 * 根据合同编号和理财产品查询理财信息(非续投)
	 *  
	 * @author Yuan Changchun
	 * @date 2013-11-1 下午02:19:40
	 * @param feContractNo
	 * @param feProduct
	 * @return
	 *//*
	public ResultVo findByFeContractNoAndFeProduct(String feContractNo, String feProduct, String feLendNo) {
		List<BusiFinance> busiFinances = financeDao.findByFeContractNoAndFeProduct(feContractNo, feProduct);
		
		if(busiFinances!=null)
		{
			for (BusiFinance busiFinance : busiFinances) {
				if((feLendNo == null || !busiFinance.getFeLendNo().equals(feLendNo)))
					return new ResultVo(false, "合同编号重复，保存失败");
			}
		}
		
		if (isEdit && busiFinances != null && busiFinances.size() > 1) {
			return new ResultVo(false, "合同编号重复，保存失败");
		}
		else if (!isEdit && busiFinances != null && busiFinances.size() > 0) {
			return new ResultVo(false, "合同编号重复，保存失败");
		}
		return new ResultVo(true, "操作成功");
	}
	
	*//**
	 * 根据合同编号和理财产品查询理财信息(非续投)
	 *  
	 * @author zhujj
	 * @date 2015-7-14 下午02:19:40
	 * @param feContractNo
	 * @param feProduct
	 * @param customerid
	 * @return
	 *//*
	public ResultVo findByFeContractNoAndFeProduct(String feContractNo, String feProduct, String feLendNo,String customerid) {
		List<BusiFinance> busiFinances1=financeDao.findByFeContractNo(feContractNo);
		for(BusiFinance busiFinance : busiFinances1){
			if(!ParamConstant.VALID.equals(busiFinance.getFeValidState()))continue; //如果订单的状态为逻辑删除，则不加入判断
			if(!customerid.equals(busiFinance.getCustomerid().toString())){
				return new ResultVo(false, "该合同编号已存在，保存失败");
			}
		}
		List<BusiFinance> busiFinances = financeDao.findByFeContractNoAndFeProduct(feContractNo, feProduct);		
		if(busiFinances!=null)
		{
			for (BusiFinance busiFinance : busiFinances) {
				if(!ParamConstant.VALID.equals(busiFinance.getFeValidState()))continue; //如果订单的状态为逻辑删除，则不加入判断
				if((feLendNo == null || !busiFinance.getFeLendNo().equals(feLendNo)))
					return new ResultVo(false, "合同编号重复，保存失败");
			}
		}
		
		if (isEdit && busiFinances != null && busiFinances.size() > 1) {
			return new ResultVo(false, "合同编号重复，保存失败");
		}
		else if (!isEdit && busiFinances != null && busiFinances.size() > 0) {
			return new ResultVo(false, "合同编号重复，保存失败");
		}
		return new ResultVo(true, "操作成功");
	}
		
	*//**
	 * 新增时根据合同编号和理财产品判断合同编号是否重复
	 * @param feContractNo
	 * @param feProduct
	 * @return
	 *//*
    public Long countByFeContractAndFeProduct(String feContractNo, String feProduct) {
    	return  financeDao.countByFeContractAndFeProduct(feContractNo, feProduct);
    }
    *//**
	 * 修改时根据合同编号和理财产品判断合同编号是否重复
	 * @param feContractNo
	 * @param feProduct
	 * @return
	 *//*
    public Long countByFeContractAndFeProduct(String feContractNo, String feProduct,Long id) {
    	return  financeDao.countByFeContractAndFeProduct(feContractNo, feProduct,id);
    }
	*//**
	 * 根据业务ID列表查找到期续投理财信息(续投)
	 *  
	 * @author Yuan Changchun
	 * @date 2013-11-1 下午04:14:23
	 * @param idList
	 * @return
	 *//*
	public ResultVo findByPreIds(List<Long> idList) {
		if (idList == null || idList.size() == 0) {
			return new ResultVo(true, "操作成功");
		}
		List<BusiFinance> busiFinances = financeDao.findByIdIn(idList);
		if (busiFinances != null && busiFinances.size() != 0){
            for(BusiFinance b:busiFinances){
            	if(b.getFeBusiState()==null){
            		return new ResultVo(false, "该产品已进行过到期续投操作");
            	}
            	if(!ParamConstant.FortuneState_12.equals(b.getFeBusiState())){
            		return new ResultVo(false, "该产品已进行过到期续投操作");
            	}          	
            }          
		}
		return new ResultVo(true, "操作成功");
	}
	
	*//**
	 * 根据业务ID列表查找到期续投理财信息(定制续投)
	 *  
	 * @author wb_lyq
	 * @date 2015-11-5 下午14:14:23
	 * @param idList
	 * @return
	 *//*
	public boolean findByPreId(List<Long> idList) {
		boolean  flag  =true;
		if (idList == null || idList.size() == 0) {
			return  flag;
		}
		List<BusiFinance> busiFinances = financeDao.findByIdIn(idList);
		if (busiFinances != null && busiFinances.size() != 0){
            for(BusiFinance b:busiFinances){
            	if(b.getFeBusiState()==null){
            		flag=false;
            	}
            	if(!ParamConstant.FortuneState_12.equals(b.getFeBusiState())){
            		flag=false;
            	}          	
            }          
		}
		return flag;
	}
	
	
	*//**
	 * 根据当客户id查询该客户申请的理财业务顺序最大数
	 *  
	 * @author Yuan Changchun
	 * @date 2013-12-10 下午04:45:17
	 * @param customerid
	 * @return
	 *//*
	public static Long findCustomerFinanceMaxCount(Long customerid) {
		String count = ((BusiFinanceDao) SpringContextHelper
				.getBean("busiFinanceDao")).findCustomerFinanceMaxCount(customerid);
		return Long.valueOf(count == null ? "0" : count);
	}
	
	
	*//**
	 * 根据客户ID查询当前客户已生效的理财业务信息
	 * @param customerId
	 * @return
	 *//*
	public List<BusiFinance> getBusiFinanceListByCustomerId(Long customerId){
		return financeDao.getBusiFinanceListByCustomerId(customerId);
	}
	
	*//**
	 * * 业务复审
	 *  
	 * @param id
	 * @param content
	 * @param auditResult
	 * @param atPrivState
	 * @return
	 *//*
	public ResultVo auditFinanceCheck(Long id, String content, String auditResult, String atPrivState)  {
		BusiFinance finance = financeDao.findOne(id);
		//更新业务状态
		updateBusiFinanceState(finance, auditResult);
		// 添加审批日志
		AuditHelper.saveAuditLog(id, ParamConstant.AUDIT_TYPE_FINANCE, content, "", atPrivState, auditResult);
		//修改返回值，增加业务id值,manager值，用于报表记录  by Sam.J  14.06.20
		ResultVo resultVo = new ResultVo(true);
		resultVo.result.put("id",finance.getId()); 
		resultVo.result.put("manager",finance.getFeManager());
		return resultVo;
	}
	
	*//**
	 * 修改业务状态
	 *  
	 * @param id
	 * @param mnState
	 * @return
	 *//*
	@Transactional(readOnly = false)
	public void updateBusiFinanceState(BusiFinance finance, String feState){
		finance.setFeState(feState);
		financeDao.save(finance);
	}
	
	public String findBusiFinanceMaxFeContractNo(String feContractNo) {
		return financeDao.findBusiFinanceMaxFeContractNo(feContractNo);
	}
	

	
	*//**
	 * 获取合同参数列表
	* @Title:getContractsList 
	* @Description:
	* @param map
	* @return   
	* @throws 
	* @time:2014年11月21日 上午10:45:56
	* @author:wangwm
	 *//*
	public Map<String, Object> getContractsList(Map<String, Object> map){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		return resultMap;
	}
	
	
	*//**
	 * 删除投资信息FOR APP
	 *  
	 * @author zhanghao
	 * @date 2013-04-16 11:05:42
	 * @param id 投资ID
	 * @return 是否删除成功
	 *//*
	public ResultVo delBusiFinanceForApp(Long id) {
		Query query = em.createQuery("delete from BusiFinance where id = :id and feState=1");
		query.setParameter("id", id);
		query.executeUpdate();
		BusiFinance busiFinance=financeDao.findOne(id);
		if(busiFinance==null)return new ResultVo(false,"订单不存在!");
		if(!ParamConstant.FE_REQUEST_STATE_CREATE.equals(busiFinance.getFeState()))return new ResultVo(false,"订单状态无法删除!");
		busiFinance.setFeValidState(ParamConstant.UNVALID); //修改状态标志位为   已删除
		financeDao.save(busiFinance);
		return new ResultVo(true,"删除成功!");
	}
	
	*//** 
	* @Title:根据出借编号查询理财信息(去除APP订单)
	* @Description: TODO 
	* @param feLendNo
	* @return   
	* @throws 
	* @time:2014-11-26 下午05:26:05
	* @author:Sam.J
	*//*
	public List<BusiFinance> feLendNoWithoutApp(String feLendNo) {
		StringBuffer searchBaseSQL = new StringBuffer();
		// 根据条件查询
		searchBaseSQL
				.append("SELECT A FROM BusiFinance A, Customer C WHERE A.customerid = C.id AND A.feState > 3 AND A.feDataOrigin='0' AND A.feLendNo = '")
				.append(feLendNo).append("'");
		// 添加数据权限
		addDataAuthority(searchBaseSQL, Share.CUSTOMER, "C", "crGather");
		return getContent(searchBaseSQL.toString());
	}
	
	*//** 
	* @Title: 报表系统日记记录
	* @Description: 当业务新发起后状态变化时，在recourdStaff表中插入记录
	* @param ${tags}    设定文件 
	* @return String    返回类型    noRecord:没有对应的APP记录   fail：出错失败   succes：更新成功
	* @throws 
	*//*
	private void AddRecordStaff(Long feManager,String state,String busiId){
		try{
			//报表系统日记记录
			RecordStaffVo record = UcHelper.getRecordStaff(feManager);
			record.setStatus(state);
			record.setBusiId(Long.parseLong(busiId));
			RecordStaff staffLogger = BeanMapper.map(record, RecordStaff.class);
			staffLogger.setStartDate(new Date());
			staffLogger.setEndDate(new Date());
			recordStaffService.saveRecordStaff(staffLogger);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	*//** 
	* @Title:saveBusiFinance4AppTemp 
	* @Description: TODO 
	* @param map
	* @return   
	* @throws 
	* @time:2014-12-31 下午04:44:29
	* @author:Sam.J
	*//*
	public ResultVo saveBusiFinance4AppTemp(Map<String, Object> map) {
		BusiFinance finance = new BusiFinance();
		String dataOrigin = "";
		if(map.containsKey("dataOrigin")&&map.get("dataOrigin")!=null){
			dataOrigin = map.get("dataOrigin").toString();
		}
		if(map.containsKey("feProduct")&&map.get("feProduct")!=null){// 产品
			
			finance.setFeProduct(String.valueOf(map.get("feProduct")));
			if(ParamConstant.CRM_PRODUCT_YOUYIJIHUA.equals(finance.getFeProduct())){ //如果是优益计划，则增加债权id的记录
				finance.setFeLoanId(map.get("loanId").toString());//赋值债权id
			}
		}
		if(map.containsKey("feAmount")&&map.get("feAmount") != null){//金额
			//证大月定投产品，单独设置定投金额字段
			if(ParamConstant.FORTUNE_PRODUCT_ZHENGDAYUEDINGTOU.equals(String.valueOf(map.get("feProduct")))){
				finance.setFeTimeInvestAmount(Double.valueOf((String)map.get("feAmount")));
				if(finance.getFeTimeInvestAmount()<=0){
					return new ResultVo(false);
				}
			} else {
				finance.setFeAmount(Double.valueOf((String)map.get("feAmount")));
				//2015.4.30 by liyez 金额控制：下单金额必须大于0元
				if(finance.getFeAmount()<=0)
					return new ResultVo(false);
			}
			

		}
		if(map.containsKey("feRequestDate")&&map.get("feRequestDate") != null){
			finance.setFeRequestDate((Date)map.get("feRequestDate"));
		}
		if(map.containsKey("customerId")&&map.get("customerId") != null){
			
			finance.setCustomerid(Long.valueOf((String) map.get("customerId")));
		}
		if(map.containsKey("feManager")&&map.get("feManager") != null){
			finance.setFeManager(Long.valueOf((String) map.get("feManager")));
		}
		if(map.containsKey("feActivityId")&&map.get("feActivityId") != null){//活动ID
			finance.setFeActivityId((String)map.get("feActivityId"));
		}
		if(map.containsKey("feAppMemo")&&map.get("feAppMemo") != null){//备注字段
			finance.setFeAppMemo((String)map.get("feAppMemo"));
		}
		//新增银行卡信息
		Bankaccount b=new Bankaccount();
		Member member = null;
		//add by chenjiale 2016/5/5 展业app start
		if(ParamConstant.FE_DATA_ORIGIN_THUMB_APP.equals(dataOrigin)){
			member = memberDao.findMember(Long.valueOf(map.get("memberId").toString()));
			if(member != null){
				finance.setFeDeductAccount(member.getBankaccountId());
				finance.setFeReturnAccount(member.getBankaccountId());
			}
			
			
		} else {
			Customer c = customerService.getCustomer(Long.valueOf((String)map.get("customerId"))); //TODO
			//c.setId(Long.valueOf((String)map.get("userNo")));//客户ID
			b.setCrmCustomer(c);
			b.setBaAccount((String)map.get("bankCardNo"));//银行卡号
			b.setBaBankCode((String)map.get("bankCode"));//银行代码
			b.setBaBankName((String)map.get("bankName"));//银行名称
			b.setBaBranchName((String)map.get("bankSubName"));//银行代码
			b.setBaAccountName((String)map.get("userName"));//客户姓名
			b.setBaValid("1"); //启用
			b.setBaAccountType("1");// 银行卡类型
			//新增或返回银行卡信息
			Map resultMap = CustomerHelper.saveOrGetBankByCustomer(b);
			b=(Bankaccount) resultMap.get("result");
			
			//设置划扣账号，回款账号跟划扣公司
			finance.setFeDeductAccount(b.getId());
			finance.setFeReturnAccount(b.getId());
			
		}
		finance.setFePaymentWay("2"); //支付方式为委托划扣
		if(ParamConstant.FE_DATA_ORIGIN_THUMB_APP.equals(dataOrigin)) {
			finance.setFeContractNo(ProductUtil.contractNumberForThumbApp());//合同编号(大拇指展业APP)
			finance.setFeDataOrigin(ParamConstant.FE_DATA_ORIGIN_THUMB_APP);//数据来源展业APP
			finance.setFeDeductCompany(ParamConstant.FIN_DEDUCT_TONGLIAN);
		} else {
			if(ParamConstant.CRM_PRODUCT_YOUYIJIHUA.equals(finance.getFeProduct())){ //如果是优益计划，使用优益计划的合同编号
				finance.setFeContractNo(ProductUtil.contractNumber(ParamConstant.CON_NUM_TYPE_YXJH));
				finance.setFeDeductCompany(ParamConstant.FIN_DEDUCT_YINLIAN); //划扣公司为银联
			}else{
				finance.setFeContractNo(ProductUtil.contractNumber(ParamConstant.CON_NUM_TYPE_APP));//合同编号(大拇指APP)
				finance.setFeDeductCompany(ParamConstant.FIN_DEDUCT_TONGLIAN); //划扣公司为通联
			}
			finance.setFeDataOrigin(ParamConstant.FE_DATA_ORIGIN_APP);//数据来源APP
		}
		
		finance.setFePayStatus(ParamConstant.FE_PAY_STAUTS_UNPAY);//付款状态--未付款
		
		
		//fortune需要写入的值
		//finance.setFePaymentWay(ParamConstant.FE_PAYMENT_WAY_SELF);//付款方式    1.自行转账
		finance.setFeManageFee(ParamConstant.FE_FEE_MANAGER_DISCOUNT_PERCENT);//管理费折扣 默认"1" 改为0.50 员工折扣率为 0% 需要走变更流程
		if(ParamConstant.CRM_PRODUCT_YOUYIJIHUA.equals(finance.getFeProduct()))finance.setFeManageFee(0d); //如果是优益计划，管理费折扣设为0
		finance.setFeContinueProduct(ParamConstant.FE_CONTINUE_PRODUCT_ONE);// 默认债权转让
		finance.setFeRiskCompensation(ParamConstant.FE_RISK_COMPENSATION_TRUE);//是否风险补偿 1:是 0：否
		
		finance.setFePayauditFlag(ParamConstant.FE_PAYAUDIT_CANCEL);//
		
		//add by yangYing 2016/5/7  续投不同点  start
		int type = map.containsKey("type")?(Integer) map.get("type"):1;
		if(type == 0){
			Long preId = (Long) (map.get("busiId"));
			BusiFinance preFinance = financeDao.findOne(preId);
			finance.setFeContractNo(preFinance.getFeContractNo());//合同编号沿用上笔投资
			finance.setFePreviousId(preId);
			finance.setFeContinueProduct(ParamConstant.FE_CONTINUE_PRODUCT_ONE);// 默认债权转让
			finance.setFePaymentWay("1"); //支付方式为自动转账
			finance.setFeDeductCompany(null);
		}
		finance.setFeProtocolVersion(ParamConstant.PROTOCOL_VERSION_3);
		//add by yangYing 2016/5/7  续投不同点  end
		
		Date date = new Date();
		Long manager = (member!=null && member.getMrManager()!=null)?member.getMrManager():99999999L;
		setFinanceValue(finance, date, manager, ParamConstant.FE_REQUEST_STATE_CREATE, date, buildFeLendNo(finance.getCustomerid()), date, manager);
		financeDao.save(finance);
		//记录审核日志
		AuditHelper.saveCreateAuditLog(finance.getId(), ParamConstant.AUDIT_TYPE_FINANCE, ParamConstant.FE_REQUEST_STATE_CREATE);
		//记录操作日志信息
		LoggingHelper.createLogging(LoggingType.申请, LoggingSource.产品申请, LoggingHelper.builderLogContent(finance.getClass().getSimpleName(),finance.getId()));
		//增加报表 recordStaff 表的记录
		AddRecordStaff(finance.getFeManager(),ParamConstant.FE_REQUEST_STATE_CREATE,finance.getId()+"");
		ResultVo  resultvo=new ResultVo(true);
		resultvo.result.put(ParamConstant.FE_APP_SAVE_RESULT, finance);
		return resultvo;
	}
	
	
	
	*//** 
	* @Title:划扣允许操作
	* @Description: TODO 
	* @param busiFinance
	* @return   
	* @throws 
	* @time:2015-1-28 下午02:04:37
	* @author:Sam.J
	*//*
	public ResultVo checkForPayAuditFlag(BusiFinance busiFinance){
		ResultVo resultVo = null;
		try {
			//先调fortune系统的划扣接口
			logger.info("====调用fortune划扣复核接口开始====入参："+busiFinance.getId());
			String keepFlag = "1";
			if(busiFinance.getFePreviousId()!=null)
				keepFlag = "0";//表示续投
			String result=FortuneHelper.checkForPayAuditFlag(busiFinance.getId(), ParamConstant.FE_PAYAUDIT_PERMIT,keepFlag);
			logger.info("====调用fortune划扣复核接口结束====返回："+result);
			if(FortuneHelper.INVOKE_SUCCESS.equals(result)){ //如若返回成功，则修改本地划扣允许状态
				if(!busiFinance.getFeDataOrigin().equals(ParamConstant.FE_DATA_ORIGIN_THUMB_APP)){
					busiFinance.setFePayauditFlag(ParamConstant.FE_PAYAUDIT_PERMIT);
					financeDao.save(busiFinance);
				}
				resultVo=new ResultVo(true,"确认成功!");
			}else{
				resultVo=new ResultVo(false,result);
			}
		} catch (Exception e) {
			logger.info("",e);
			resultVo = new ResultVo(false, "操作失败,请重新尝试或联系管理员!");
		}
		return resultVo;
	}
	
	//退款通知
		public BusiFinance updateStateFromRefund(Map<String, Object> map) throws ParseException {
				BusiFinance busifinance =financeDao.findByFeLendNo((String)map.get("oldChannelOrderNo"));
				if(null==busifinance){
					logger.info(" 退款通知接口的订单不存在");
					return null;
				}//支付状态  1.未支付  3.已支付  4.已提交退款 5.提交退款失败 6.退款成功 7.退款失败
				String atPrivPaystate = busifinance.getFePayStatus();
				String privState = busifinance.getFeState();
				String content ="";
				if(null !=map.get("refoudResult")){
					if("0000".equals(map.get("refoudResult"))){//退款成功
						busifinance.setFePayStatus("6");
						content=ParamConstant.CONSUME_AUDIT_REFUND_SUCCESS;
					}else if ("9999".equals(map.get("refoudResult"))){//退款失败
						busifinance.setFePayStatus("7");
						content=ParamConstant.CONSUME_AUDIT_REFUND_FAILURE;
					}
				}
				busifinance.setFeModifyDate(new Date());
				financeDao.save(busifinance);
				//记录留痕日志信息
				AuditHelper.saveAuditLogWithPaystatus(busifinance.getId(), ParamConstant.AUDIT_TYPE_FINANCE
, "退款", content, privState,busifinance.getFeState(),atPrivPaystate, busifinance.getFePayStatus());
				//记录操作日志信息
				LoggingHelper.createLogging(LoggingType.修改, LoggingSource.理财类产品, LoggingHelper.builderLogContent(busifinance.getClass().getSimpleName(), busifinance.getId()));
				return busifinance;
		}
	
		
		
		
		*//** 
		* @Title: findAllErrorFinance 
		* @Description: 查出所有的异常订单
		* @param conditionMap
		* @param pageRequest
		* @param staffIds
		* @return Page<BusiFinance>    返回类型 
		* @author Sam.J
		* @date 2015-5-19 下午01:43:46 
		* @throws 
		*//*
		public Page<BusiFinance> findAllErrorFinance(Map<String, SearchFilter> conditionMap,PageRequest pageRequest,Long...staffIds){
			StringBuffer searchBaseSQL = new StringBuffer(
					"select A from BusiFinance A,Customer B where A.customerid = B.id ");
			
		    //不同的业务错误情况  目前只针对APP订单的失败业务
			searchBaseSQL
			.append(" and (")
			.append(" A.feState='3' and A.fePayStatus='3'")//CRM系统接收订单成功，发往fortune失败
			.append(" or")
			.append(" A.feState='4' and A.fePayStatus='3' and A.feBusiState='02000012'")//订单作废，发送退款失败的
			.append(" or")
			.append(" A.fePayStatus='5' ")//退款请求接收失败的
			.append(")");
			//线上APP订单条件
			searchBaseSQL
			.append(" and A.feDataOrigin='"+ParamConstant.FE_DATA_ORIGIN_APP+"'");
			//未删除订单
			searchBaseSQL
			.append(" and A.feValidState='"+ParamConstant.VALID+"'");
			
			//添加数据权限
			if(staffIds==null||staffIds.length<1){
				searchBaseSQL = addDataAuthority(searchBaseSQL, Share.PRODUCT_REQUEST, "A", "feManager");
			}else {
				searchBaseSQL = addDataAuthority(searchBaseSQL, Share.PRODUCT_REQUEST, "A", "feManager",staffIds);
			}
			
			//查询总记录数
			long total = getTotal(conditionMap,new StringBuffer(searchBaseSQL));
			
			//查询结果集
			List<BusiFinance> financeList = getContent(conditionMap,searchBaseSQL, pageRequest);
			
			return new PageImpl<BusiFinance>(financeList, pageRequest, total);
		}
		
		
		*//** 
		* @Title: refundApp 
		* @Description: APP退款
		* @return resultVo    返回类型 
		* @author Sam.J
		* @date 2015-5-21 上午11:18:33 
		* @throws 
		*//*
		public  ResultVo  refundApp(Long id){
			ResultVo resultVo = null;
			BusiFinance finance = financeDao.findOne(id);
			//投资作废已完成付款的订单发起退款
			if((ParamConstant.FE_PAY_STAUTS_PAYDONE.equals(finance.getFePayStatus())||ParamConstant.FE_PAY_STAUTS_SUBREFFAIL.equals(finance.getFePayStatus()))&& null!=finance.getFePayTime()){
				RefundObj obj= new RefundObj(finance.getFeLendNo(), new Date(), finance.getFeAmount(), finance.getFeLendNo(), finance.getFePayTime(), finance.getFeAppMemo());
				Map<String, Object> resMap=RefundByHttp.doBusiFinanceRefund(obj);
				String res=(String) resMap.get("responseRechargeCode");
				//支付状态  1.未支付  3.已支付  4.已提交退款 5.提交退款失败 6.退款成功 7.退款失败
				if(RmiConstant.RMI_RESULT_SUCCESS.equals(res)){
					finance.setFePayStatus(ParamConstant.FE_PAY_STAUTS_SUBREFSUC);//4.已提交退款 
					String oldState=finance.getFeState(); //取得原CRM系统状态
					//如果是未质检到fortune的单子选择退款，则将其CRM状态标为5（质检拒绝）
					if(ParamConstant.FE_REQUEST_STATE_AUDIT.equals(oldState)){
						finance.setFeState(ParamConstant.FE_REQUEST_STATE_REFUSE);
					}
					resultVo=new ResultVo(true);
					AuditHelper.saveAuditLogWithPaystatus(finance.getId(), ParamConstant.AUDIT_TYPE_FINANCE, "提交退款请求成功","",
							oldState,finance.getFeState(),ParamConstant.FE_PAY_STAUTS_PAYDONE,finance.getFePayStatus());
				}else if(!"error".equals(res)){
					finance.setFePayStatus(ParamConstant.FE_PAY_STAUTS_SUBREFFAIL);//5.提交退款失败
					AuditHelper.saveAuditLogWithPaystatus(finance.getId(), ParamConstant.AUDIT_TYPE_FINANCE, "提交退款请求失败","",
							finance.getFeState(),finance.getFeState(),ParamConstant.FE_PAY_STAUTS_PAYDONE,finance.getFePayStatus());
					resultVo=new ResultVo(false,(String)resMap.get("responseDesc"));
				}else {
					resultVo=new ResultVo(false,"请求超时");
				}
				finance.setFeModifyDate(new Date());
				financeDao.save(finance);
			}else{
				resultVo=new ResultVo(false, ParamConstant.ERROR_NOT_EXECUTE_CURRENT_OPERATION);
			}	
			return resultVo;
		}
		
		
	
		*//** 
		* @Title: sendFinanceToFortune 
		* @Description: 发送请求到fortune 
		* @return ResultVo    返回类型 
		* @author Sam.J
		* @date 2015-6-4 下午04:26:34 
		* @throws 
		*//*
		public ResultVo sendFinanceToFortune(Long id, String content)  {		
			// 判断发送消息到Fortune是否成功
			long stime = System.currentTimeMillis();
			String result = FortuneHelper.invokeCreate(String.valueOf(id), "");
			long etime = System.currentTimeMillis();
			logger.info("本次接口提交共花费时间:" + (etime - stime) + ",提交理财对象ID：" + id);
			logger.info("result:::" + result);
			if(!FortuneHelper.INVOKE_SUCCESS.equals(result))
				return new ResultVo(false,result);
			BusiFinance finance = financeDao.findOne(id);
			// 更新投资申请的状态
			updateFeState(finance.getId(),AuditHelper.getStateValue(ParamConstant.REQUEST_STATE_CHENGGONG));
			
			// 更新客户的状态和业务数量
			updateCustomer(finance.getCustomerid());
			// 添加审批日志
			AuditHelper.saveSubmitAuditLog(finance.getId(), ParamConstant.AUDIT_TYPE_FINANCE, content);
			//增加报表 recordStaff 表的记录
			AddRecordStaff(finance.getFeManager(),ParamConstant.FE_REQUEST_STATE_PASS,finance.getId()+"");	
			return new ResultVo(true);
		}
		
		*//** 
		* @Title: countByProduct 
		* @Description:按照产品计算
		* @return long    返回类型 
		* @author Sam.J
		* @date 2015-7-9 下午04:35:52 
		* @throws 
		*//*
		public long countByProduct(String product){
			return financeDao.countByFeProduct(product);
		}
		

		*//** 
		* @Title: findAllByFeLendNo 
		* @Description: 根据出借编号查询非固产品信息
		* @return List<BusiFinance>    返回类型 
		* @author Sam.J
		* @date 2015-8-5 下午02:36:27 
		* @throws 
		*//*
		public List<BusiFinance> findAllByFeLendNoForNofix(String feLendNo){
			StringBuffer searchBaseSQL = new StringBuffer();
			// 根据条件查询
			searchBaseSQL
					.append("SELECT A FROM BusiFinance A, Customer C WHERE A.customerid = C.id AND A.feState > 3 AND A.feLendNo = '")
					.append(feLendNo).append("'");
			//状态过滤
			searchBaseSQL.append(buildSearchSql(
					"A.feBusiState",
					new String[] { AuditHelper.getFortuneStateValue("回款已撮合"),
							AuditHelper.getFortuneStateValue("投资生效"),
							AuditHelper.getFortuneStateValue("回款待撮合"),
							AuditHelper.getFortuneStateValue("定投已撮合"),
							AuditHelper.getFortuneStateValue("全部已撮合") }));
			//产品过滤
			searchBaseSQL.append(buildSearchSql("A.feProduct", new String[] {
					ParamConstant.FORTUNE_PRODUCT_ZHENGDAYUESHOU,
					ParamConstant.FORTUNE_PRODUCT_ZHENGDANIANFENG}));
			// 添加数据权限
			addDataAuthority(searchBaseSQL, Share.CUSTOMER, "C", "crGather");
			return getContent(searchBaseSQL.toString());
		}
	
		
		
		
	
	*//**
	 * @Title: findAllNofixRemind
	 * @Description: 非固到期提醒
	 * @return Page<FinanceMaturityRemind> 返回类型
	 * @author Sam.J
	 * @date 2015-8-14 下午04:56:26
	 * @throws
	 *//*
	public Page<FinanceMaturityRemind> findAllNofixRemind(
			Map<String, SearchFilter> conditionMap, PageRequest pageRequest,
			Long... staffIds) {
		StringBuffer searchBaseSQL = new StringBuffer(
				"select A from BusiFinance A,Customer B where A.customerid = B.id ");
		// 判断条件一  feClosedDate为空的话以feInvestDate投资生效日计算封闭日期  ///////////////////////////////
		searchBaseSQL
				.append(" and ((add_months(A.feInvestDate,12) between ")
				.append(toDateYYYYMMDD(DateUtil.getYMDTime(new Date())))
				.append(" and ")
				.append(toDateYYYYMMDD(DateUtil.getCurrDateAfterDay(Integer.parseInt(new PropertiesLoader(
						"crm.properties")
						.getProperty("finance.continue.nofix.remind.day")))));
		searchBaseSQL.append(" and A.feClosedDate is null) or ");
		// 判断条件二  feClosedDate不为空的话以feClosedDate作为封闭日期  ///////////////////////////////
		searchBaseSQL
				.append("(A.feClosedDate between ")
				.append(toDateYYYYMMDD(DateUtil.getYMDTime(new Date())))
				.append(" and ")
				.append(toDateYYYYMMDD(DateUtil.getCurrDateAfterDay(Integer.parseInt(new PropertiesLoader(
						"crm.properties")
						.getProperty("finance.continue.nofix.remind.day")))));
		searchBaseSQL.append(")");
		// ///////////////////////////////////////////////
		searchBaseSQL.append(")");
		// 状态过滤
		searchBaseSQL.append(buildSearchSql(
				"A.feBusiState",
				new String[] { AuditHelper.getFortuneStateValue("回款已撮合"),
						AuditHelper.getFortuneStateValue("投资生效"),
						AuditHelper.getFortuneStateValue("回款待撮合"),
						AuditHelper.getFortuneStateValue("全部已撮合") }));
		//产品过滤
		searchBaseSQL.append(buildSearchSql("A.feProduct", new String[] {
				ParamConstant.FORTUNE_PRODUCT_ZHENGDAYUESHOU,
				ParamConstant.FORTUNE_PRODUCT_ZHENGDANIANFENG}));
		// 添加数据权限  客户信息中的客户经理
		if (staffIds == null || staffIds.length < 1) {
			searchBaseSQL = addDataAuthority(searchBaseSQL,
					Share.PRODUCT_REQUEST, "B", "crGather");
		} else {
			searchBaseSQL = addDataAuthority(searchBaseSQL,
					Share.PRODUCT_REQUEST, "B", "crGather", staffIds);
		}

		// 查询总记录数
		long total = getTotal(conditionMap, new StringBuffer(searchBaseSQL));

		// 查询结果集
		List<BusiFinance> financeList = getContent(conditionMap, searchBaseSQL,
				pageRequest);

		List<FinanceMaturityRemind> remindList = new ArrayList<FinanceMaturityRemind>();
		for (BusiFinance finance : financeList) {
			remindList.add(formatMaturityRemind(finance));
		}
		return new PageImpl<FinanceMaturityRemind>(remindList, pageRequest,
				total);
	}
	
	
	*//**
	 * @Title: 
	 * @Description: webSerivice 查询理财接口使用      参数中放入 客户 id  出借编号   
	 * @return Page<BusiFinance> 返回类型
	 * @author wb_lyq
	 * @date 2015-11-09  下午04:56:26
	 * @throws
	 *//*
	public Page<BusiFinance> findFinanceByCustomerIdAndFelendNo(Map<String, SearchFilter> conditionMap, PageRequest pageRequest) {
		
		StringBuffer searchBaseSQL = new StringBuffer("select A from BusiFinance A,Customer B where A.customerid = B.id ");
		// 查询总记录数
		long total = getTotal(conditionMap, new StringBuffer(searchBaseSQL));
		// 查询结果集
		List<BusiFinance> financeList = getContent(conditionMap, searchBaseSQL,pageRequest);

		return new PageImpl<BusiFinance>(financeList, pageRequest,total);
	}
	
	*//** 
	* @Title: findForKHZY 
	* @Description: 客户转移查询方法
	* @return List<BusiFinance>    返回类型 
	* @author Sam.J
	* @date 2015-9-17 下午01:58:18 
	* @throws 
	*//*
	public List<BusiFinance> findForKHZY(Long custormerId,Long crGatherId,String flagDate,String fState,String searchType) {
		StringBuffer searchBaseSQL = new StringBuffer();
		// 根据条件查询
		searchBaseSQL
				.append("SELECT A FROM BusiFinance A  WHERE A.feValidState='1' ");
		if("1".equals(searchType)){
			searchBaseSQL.append("AND A.customerid='")
			             .append(custormerId+"' ")
			             .append("AND A.feManager='")
			             .append(crGatherId+"' ");
		}else if ("2".equals(searchType)){ //查询客户归属不在当前用户但业务归属在当前用户的投资信息
			searchBaseSQL.append(" AND A.customerid not in (select c.id from Customer c where c.crGather = '")
            .append(crGatherId+"')")
            .append("AND A.feManager='")
            .append(crGatherId+"' ");
		}			
		if(flagDate!=null&&!"".equals(flagDate)){
			searchBaseSQL.append(" AND (")
					     .append("A.feInvestDate > to_date('"+flagDate+"','yyyy/mm/dd')")
					     .append(" OR ")
					     .append("A.feTimeInvestStart > to_date('"+flagDate+"','yyyy/mm/dd')")
					     .append(")");
		}
		if(fState!=null&&!"".equals(fState)){
			searchBaseSQL.append(" AND ")
			             .append("A.feBusiState IN (")
			             .append(stateDeal(fState))
			             .append(")");
		}
		return getContent(searchBaseSQL.toString());
	}
	
	
	*//** 
	* @Title:  
	* @Description: 用于接口的查询 客户  id
	* @return List<BusiFinance>    返回类型 
	* @author wb_lyq
	* @date 2015-11-17 下午15:58:18 
	* @throws 
	*//*
	public List<BusiFinance> findBusiForWebser(Map<String ,String >  map) {
		StringBuffer searchBaseSQL = new StringBuffer();
		// 根据条件查询 
		//  customerNum  felendNo  contractNo
		searchBaseSQL.append("SELECT A FROM BusiFinance A ,Customer B  WHERE  A.customerid = B.id AND  A.feValidState='1' ");
		if(map.containsKey("felendNo")){
			searchBaseSQL.append("AND A.feLendNo='").append(map.get("felendNo")+"' ");
		}
		if(map.containsKey("customerNum")){
			searchBaseSQL.append("AND B.crCustomerNumber='").append(map.get("customerNum")+"' ");
		}	
		if(map.containsKey("contractNo")){
			searchBaseSQL.append("AND A.feContractNo='").append(map.get("contractNo")+"' ");
		}	
		return getContent(searchBaseSQL.toString());
	}
	
	
	*//** 
	* @Title: findAllByFeLendNoForRedeem 
	* @Description: 可赎回业务过滤判断 
	* @param feLendNo
	* @return List<BusiFinance>(描述入参以及返回参数的含义)
	* @author Sam.J
	* @date 2016年1月25日 下午4:42:12 
	* @throws 
	*//*
	public List<BusiFinance> findAllByFeLendNoForRedeem(String feLendNo){
		StringBuffer searchBaseSQL = new StringBuffer();
		// 根据条件查询
		searchBaseSQL
				.append("SELECT A FROM BusiFinance A, Customer C WHERE A.customerid = C.id AND A.feState > 3 AND A.feLendNo = '")
				.append(feLendNo).append("'");
		//状态过滤
		searchBaseSQL.append(buildSearchSql(
				"A.feBusiState",
				new String[] { AuditHelper.getFortuneStateValue("回款已撮合"),
						AuditHelper.getFortuneStateValue("投资生效"),
						AuditHelper.getFortuneStateValue("回款待撮合"),
						AuditHelper.getFortuneStateValue("定投已撮合"),
						AuditHelper.getFortuneStateValue("全部已撮合") }));
		//产品过滤
		searchBaseSQL.append(buildSearchSql("A.feProduct", new String[] {
				ParamConstant.FORTUNE_PRODUCT_ZHENGDAYUESHOU,
				ParamConstant.FORTUNE_PRODUCT_ZHENGDANIANFENG,
				ParamConstant.FORTUNE_PRODUCT_ZHENGDAYUEDINGTOU,
				ParamConstant.FORTUNE_PRODUCT_ENIANFENG}));
		// 添加数据权限-大拇指订单跳过
		BusiFinance busiFinance = financeDao.findByFeLendNo(feLendNo);
		if(!ParamConstant.FE_DATA_ORIGIN_THUMB_APP.equals(busiFinance.getFeDataOrigin()))
			addDataAuthority(searchBaseSQL, Share.CUSTOMER, "C", "crGather");
		return getContent(searchBaseSQL.toString());
	}
	
	*//** 
	* @Title: stateDeal 
	* @Description: 状态str处理
	* @return String    返回类型 
	* @author Sam.J
	* @date 2015-9-17 下午06:30:49 
	* @throws 
	*//*
	private String stateDeal(String fState){
		String[] strState=fState.split(",");
		String dealState="";
		for(int i=0;i<strState.length;i++){
			dealState+="'"+strState[i]+"',";
		}
		dealState=dealState.substring(0,dealState.length()-1);
		return dealState;
	}

	*//** 
	* @Title:划扣取消操作
	* @Description: TODO 
	* @param busiFinance
	* @return   
	* @throws 
	* @time:2016-1-14 下午02:04:37
	*//*
	@Transactional(readOnly = false)
	public ResultVo cancelForPayAuditFlag(BusiFinance busiFinance){
		ResultVo resultVo = null;
		try {
			//先调fortune系统的划扣接口
			logger.info("====调用fortune划扣复核接口开始====入参："+busiFinance.getId());
			String result=FortuneHelper.checkForPayAuditFlag(busiFinance.getId(), ParamConstant.FE_PAYAUDIT_CANCEL,"1");
			logger.info("====调用fortune划扣复核接口结束====返回："+result);
			if(FortuneHelper.INVOKE_SUCCESS.equals(result)){ //如若返回成功，则修改本地划扣允许状态
				if(!busiFinance.getFeDataOrigin().equals(ParamConstant.FE_DATA_ORIGIN_THUMB_APP)){
					busiFinance.setFePayauditFlag(ParamConstant.FE_PAYAUDIT_CANCEL);
					financeDao.save(busiFinance);
				}
				resultVo=new ResultVo(true,"确认成功!");
			}else{
				resultVo=new ResultVo(false,result);
			}
		} catch (Exception e) {
			logger.info("",e);
			resultVo = new ResultVo(false, e.getMessage()+"   请重新尝试或联系管理员!");
		}
		return resultVo;
	}
	
	*//** 
	* @Title: dealBusiChange 
	* @Description: 客户变更时，变更业务处理
	* @return String(描述入参以及返回参数的含义)
	* @author Sam.J
	* @date 2016年3月16日 上午10:33:24 
	* @throws 
	*//*
	@Transactional(readOnly = false)
	public String  dealBusiChange(String flagDate,Long custId,Long managerId){
		try {
			Date reDate = new SimpleDateFormat("yyyy/MM/dd").parse(flagDate);
			if(reDate.getYear()<=110){
				return "变更日期请至少填写2010年之后的年份";
			}
			List<BusiFinance> busiList=financeDao.findAllByCustomerid(custId);
			Staff staff = AuthorityHelper.getStaff();
			for(BusiFinance bf:busiList){
				if((bf.getFeInvestDate()!=null&&bf.getFeInvestDate().getTime()>=reDate.getTime())
						||(bf.getFeInvestDate()==null&&(bf.getFeTimeInvestStart()==null||bf.getFeTimeInvestStart().getTime()>=reDate.getTime()))){  //判断投资生效日是否在 判断日期后
					bf.setFeModifyDate(new Date());
					bf.setFeModifyId(staff.getId());
					Long oldManger=bf.getFeManager();
					bf.setFeManager(managerId); //修改客户经理id
					financeDao.save(bf);
					//保存理财中心相关数据
					RecordStaffVo recordStaffVo = UcHelper.getRecordStaff(bf.getFeManager());
					if(recordStaffVo != null && !Strings.isNullOrEmpty(recordStaffVo.getParentDepName())){
						busiFinanceUcService.saveOrUpdate(bf,"业务转移");
					}
					CrmManagerChange  cc=new CrmManagerChange();
					cc.setChType(ParamConstant.CHANGE_TYPE_BUSINESS);
					cc.setChInputTime(new Date());
					cc.setCustomerid(bf.getCustomerid());
					cc.setChBusiNo(bf.getFeLendNo());
					cc.setChOldManager(oldManger);
					cc.setChNewManager(managerId);
					cc.setChInputId(staff.getId());
					crmManagerChangeService.saveone(cc);
				}
			}
			return "success";
		} catch(ParseException e){
			logger.info("",e);
			return "日期格式有误";
		}catch (Exception e) {
			logger.info("",e);
			return "错误信息："+e.getMessage();
		}
	}
	
	*//**
	 * 根据出借编号和申请匹配状态查询记录
	 * @param feLendNo
	 * @return
	 *//*
	public List<BusiFinance> findByFeLendNoAndApplyMatchStatus(List<String> feLendNo){
		List<String> applyMatchStatus = new ArrayList<String>();
		applyMatchStatus.add(ParamConstant.APPLY_MATCH_STATUS_NEW);
		applyMatchStatus.add(ParamConstant.APPLY_MATCH_STATUS_SUCCESS);
		return financeDao.findByFeLendNoAndApplyMatchStatus(feLendNo, applyMatchStatus);
		
	}

	*//**
	 * 查询业务单最新续期或赎回状态
	 * @param lendingNo
	 * @return
	 *//*
	public String getnofixOrRedeemStatus(String lendingNo) {
		Date nofixDate = null;
		Date redeemDate = null;
		//获取质检通过及之后的最近续期记录
		BusiNofixFinance nofixFinance = busiNofixFinanceService.findPassByFxBusiNo(lendingNo);
		//获取最近的赎回信息
		BusiRedeem busiRedeem = financeRedeemService.findLatestByFxBusiNo(lendingNo);
		
		if(nofixFinance!=null)
			nofixDate = nofixFinance.getFxInputTime();
		if(busiRedeem!=null)
			redeemDate = busiRedeem.getReInputTime();
		
		//只存在赎回记录或都存在且赎回日期更近，取赎回状态
		if(nofixDate==null && redeemDate!=null || 
				nofixDate!=null && redeemDate!=null && nofixDate.getTime() < redeemDate.getTime()){
			
			String redeemState = busiRedeem.getReState();
			switch (Integer.parseInt(redeemState)) {
			case 1:
				return "赎回已受理";
			case 2:
				return "赎回已排期";
			case 3:
				return "赎回已撤销";
			case 4:
				return "撤销待处理";
			case 5:
				return "赎回已完成";
			}
		}
		
		//只存在续期或都存在且续期日期更近，取续期状态
		if(nofixDate!=null && redeemDate == null || 
				nofixDate!=null && redeemDate!=null && nofixDate.getTime() > redeemDate.getTime()){
			
			String nofixState = nofixFinance.getFxState();
			if(ParamConstant.FE_REQUEST_STATE_REFUSE.equals(nofixState) ||
					ParamConstant.FE_REQUEST_STATE_REVOKE.equals(nofixState))
				return "续期撤销";
			else 
				if(new Date().getTime() <= nofixFinance.getFxEndDate().getTime())
				return "续期中";
		}
		
		return null;
	}

*/}
