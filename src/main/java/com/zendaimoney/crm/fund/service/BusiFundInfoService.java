package com.zendaimoney.crm.fund.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
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
import org.springside.modules.orm.ResultVo;

import com.google.common.collect.Lists;
import com.zendaimoney.constant.LoggingSource;
import com.zendaimoney.constant.LoggingType;
import com.zendaimoney.constant.ParamConstant;
import com.zendaimoney.crm.BaseService;
import com.zendaimoney.crm.customer.entity.Customer;
import com.zendaimoney.crm.customer.entity.RecordStaff;
import com.zendaimoney.crm.customer.helper.CustomerHelper;
import com.zendaimoney.crm.customer.service.CustomerService;
import com.zendaimoney.crm.fund.entity.BusiFundInfo;
import com.zendaimoney.crm.fund.repository.BusiFundInfoDao;
import com.zendaimoney.crm.product.entity.BusiFinance;
import com.zendaimoney.crm.product.service.RecordStaffService;
import com.zendaimoney.crm.product.vo.FinanceMaturityRemind;
import com.zendaimoney.uc.rmi.vo.Staff;
import com.zendaimoney.utils.mapper.BeanMapper;

/**
 * 基金业务
 * @author user
 *
 */
@Service
@Transactional
public class BusiFundInfoService extends BaseService<BusiFundInfo> {/*
	
	private static Logger logger = LoggerFactory
	.getLogger(BusiFundInfoService.class);
	
	@Autowired
	private BusiFundInfoDao financeDao;

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private BusiFundsService busiFundsService;
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private RecordStaffService recordStaffService;

	*//**
	 * 查找单笔基金理财业务
	 * 
	 * @author CJ
	 * @date 2015-12-2 17:09:13
	 * @param id业务id
	 * @return 理财业务对象
	 *//*
	public BusiFundInfo findOneFinance(Long id) {
		return financeDao.findOne(id);
	}
	
	*//**
	 * 到期续投时、根据出借编号查询借款信息,但数据权限根据客户表里的采集人来控制
	 * 实际只会查出一笔理财
	 * @author wb_lyq
	 * @date 2015-11-9 上午10:05:03
	 * @param feLnedNo 出借编号
	 * @return
	 *//*
	public List<BusiFundInfo> getAllByFdLendNo(String fdLendNo){
		StringBuffer searchBaseSQL = new StringBuffer();
		// 根据条件查询
		searchBaseSQL
				.append("SELECT A FROM BusiFundInfo A, Customer C WHERE A.customerid = C.id  AND  A.fdValidState='1' AND A.fdLendNo = '")
				.append(fdLendNo).append("'");	
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
	public List<BusiFundInfo> findAll(Specification<BusiFundInfo> spec) {
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
	public List<BusiFundInfo> findAllByFdLendNo(String fdLendNo){
		StringBuffer searchBaseSQL = new StringBuffer();
		// 根据条件查询
		searchBaseSQL
				.append("SELECT A FROM BusiFundInfo A, Customer C WHERE A.customerid = C.id AND A.fdState > 3 AND A.fdLendNo = '")
				.append(fdLendNo).append("'");
		//状态过滤
		searchBaseSQL.append(buildSearchSql(
				"A.fdBusiState",
				new String[] { AuditHelper.getFortuneStateValue("回款已撮合"),
						AuditHelper.getFortuneStateValue("投资生效"),
						AuditHelper.getFortuneStateValue("回款待撮合"),
						AuditHelper.getFortuneStateValue("全部已撮合") }));
		//产品过滤
		searchBaseSQL.append(buildSearchSql("A.fdProduct", new String[] {
				ParamConstant.FORTUNE_PRODUCT_ZHENGDASUIYUE,
				ParamConstant.FORTUNE_PRODUCT_ZHENGDAJIXI,
				ParamConstant.FORTUNE_PRODUCT_ZHENGDAFUXIANG,
				ParamConstant.FORTUNE_PRODUCT_ZHENGDASHUANGXIN,
				//增加PLUS产品   Sam.J  15.10.30
				ParamConstant.FORTUNE_PRODUCT_ZHENGDASHUANGXINPLUS,
				ParamConstant.FORTUNE_PRODUCT_ZHENGDAYUEWENYINGPLUS
				ParamConstant.FORTUNE_PRODUCT_ZHENGDAYUEWENYING }));
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
	public List<BusiFundInfo> findAllByFdLendNoEx(String fdLendNo){
		StringBuffer searchBaseSQL = new StringBuffer();
		// 根据条件查询
		searchBaseSQL
				.append("SELECT A FROM BusiFundInfo A, Customer C WHERE A.customerid = C.id AND A.fdState > 3 AND A.fdLendNo = '")
				.append(fdLendNo).append("'");
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
	 * @author CJ
	 * @date 2015-12-2 上午10:56:39
	 * @param finance 投资信息对象
	 *//*
	@Transactional(readOnly = false)
	public void saveFinance(BusiFundInfo finance) {
		financeDao.save(finance);
	}
	
	*//**
	 * 业务审核
	 *  
	 * @author CJ
	 * @date 2015-12-2 17:37:52
	 * @param id 变更单id
	 * @param content 审核备注
	 * @param auditResult 审核结果
	 * @return 
	 *//*
	public ResultVo auditFinance(Long id, String content, String auditResult)  {
		ResultVo resultVo = null;
		BusiFundInfo finance = financeDao.findOne(id);
		//提交
		if(auditResult.equals(AuditHelper.getStateValue(ParamConstant.REQUEST_STATE_CHENGGONG))){//4
			resultVo = submitFinance(finance, content);
		}
		//退回
		else if(auditResult.equals(AuditHelper.getStateValue(ParamConstant.REQUEST_STATE_HUITUI))){//2
			resultVo = backFinance(finance, content);
		}
		// 拒绝
		else if (auditResult.equals(AuditHelper
				.getStateValue(ParamConstant.REQUEST_STATE_JUJUE))) {//5
			resultVo = refuseFinance(finance, content);
		}
		//修改返回值，增加业务id值,manager值，用于报表记录
		resultVo.result.put("id",finance.getId()); 
		resultVo.result.put("manager",finance.getFdManager());
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
	private ResultVo refuseFinance(BusiFundInfo finance, String content) {
		// 判断这笔业务是否存在
		if (AuditHelper.isEmpty(finance))
			return new ResultVo(false, ParamConstant.ERROR_BUSI_NOT_EXIST);
		// 判断是否处于可拒绝状态
		if (!AuditHelper.isBack(finance.getFdState()))
			return new ResultVo(false,
					ParamConstant.ERROR_NOT_EXECUTE_CURRENT_OPERATION);
		finance.setFdState(AuditHelper
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
	 * @author CJ
	 * @date 2015-12-2 16:30:24
	 * @param id业务id
	 * @param content内容
	 * @return
	 *//*
	@Transactional(readOnly = false)
	public ResultVo submitFinance(BusiFundInfo finance, String content) {
		//判断这笔业务是否存在
		if (AuditHelper.isEmpty(finance))
			return new ResultVo(false, ParamConstant.ERROR_BUSI_NOT_EXIST);
		
		//判断这笔业务当时是否处于可提交状态
		if (!AuditHelper.isSubmit(finance.getFdState()))
			return new ResultVo(false, ParamConstant.ERROR_NOT_EXECUTE_CURRENT_OPERATION);
		
		// 判断对象的必填信息
		if (CustomerHelper.validateCustomerRequird(customerService .getCustomer(finance.getCustomerid())).size() > 0 ? true : false)
			return new ResultVo(false, ParamConstant.ERROR_CUSTOMER_NOT_FULL);
		// 判断发送消息到Fortune是否成功
	//	long stime = System.currentTimeMillis();
	//	String result = FortuneHelper.invokeCreate(String.valueOf(finance.getId()), "");//
	//	long etime = System.currentTimeMillis();
	//	logger.info("本次接口提交共花费时间:" + (etime - stime) + ",提交理财对象ID：" + finance.getId());
	//	logger.info("result:::" + result);
		String result="0000";
		if(!FortuneHelper.INVOKE_SUCCESS.equals(result))
			return new ResultVo(false,result);
		// 更新投资申请的状态
		updateFdState(finance.getId(),AuditHelper.getStateValue(ParamConstant.REQUEST_STATE_CHENGGONG));//4
		
		// 更新客户的状态和业务数量
		updateCustomer(finance.getCustomerid());
		// 添加审批日志
		AuditHelper.saveSubmitAuditLog(finance.getId(), ParamConstant.AUDIT_TYPE_FINANCE, content);
		return new ResultVo(true);
	}
	
	*//**
	 * 更新客户信息
	 *  
	 * @author CJ
	 * @date 2015-12-02 10:43:28
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
	public ResultVo backFinance(BusiFundInfo finance, String content) {
		//判断这笔业务是否存在
		if (AuditHelper.isEmpty(finance))
			return new ResultVo(false, ParamConstant.ERROR_BUSI_NOT_EXIST);
		//判断是否处于可退回状态
		if (!AuditHelper.isBack(finance.getFdState()))
			return new ResultVo(false, ParamConstant.ERROR_NOT_EXECUTE_CURRENT_OPERATION);

		finance.setFdState(AuditHelper.getStateValue(ParamConstant.REQUEST_STATE_HUITUI));
		financeDao.save(finance);
		// 添加审批日志
		AuditHelper.saveBackAuditLog(finance.getId(), ParamConstant.AUDIT_TYPE_FINANCE, content);
		return new ResultVo(true);
	}
	
	*//**
	 * 更新理财状态不使用数据库锁的概念
	 *  
	 * @author CJ
	 * @date 2015-12-2 11:27:52
	 * @param id 业务id
	 * @param feState 状态
	 *//*
	public void updateFdState(Long id, String fdState) {
		Query query = em.createQuery("update BusiFundInfo set fdState = :fdState where id = :id");
		query.setParameter("fdState", fdState);
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
	public boolean updateFinance(BusiFundInfo finance) {
		return financeDao.save(finance) != null ? true : false;
	}
	
	*//**
	 * 保存理财业务信息
	 *  
	 * @author CJ
	 * @date 2015-11-30 9:58:02
	 * @param finance 理财对象
	 * @param state 状态
	 * @return
	 *//*
	public ResultVo saveBusiFinance(BusiFundInfo finance, String state) {
		//如果不是新建的话,那么则需要验证客户的必填信息
		if(!AuditHelper.isNew(state)) {
			List<String> messageList = isSave(finance);
			if(messageList.size() > 0) 
				return new ResultVo(false,messageList); 
		}
		Date date = new Date();
		Staff staff = AuthorityHelper.getStaff();
		finance.setFdDataOrigin(ParamConstant.FE_DATA_ORIGIN_PC);// 线下下单  Sam.J 14.11.28
//		finance.setFdPayStatus(ParamConstant.FD_PAY_STAUTS_UNPAY);//未付款0
		setFinanceValue(finance, date, staff.getId(), state, date, buildFeLendNo(finance.getCustomerid()), date, staff.getId());
		financeDao.save(finance);
		//记录审核日志
		AuditHelper.saveCreateAuditLog(finance.getId(), ParamConstant.AUDIT_TYPE_FINANCE, state);
		//记录操作日志信息
		LoggingHelper.createLogging(LoggingType.申请, LoggingSource.基金申请, LoggingHelper.builderLogContent(finance.getClass().getSimpleName(),finance.getId()));
		
		//修改返回值，增加业务id值，用于报表记录  
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
		BusiFundInfo finance = new BusiFundInfo();
		if(map.get("fdProduct")!=null){// 产品
			finance.setFdProduct(String.valueOf(map.get("fdProduct")));
		}
		if(map.get("fdAmount") != null){//金额
			finance.setFdAmount(BigDecimal.valueOf((Long) map.get("fdAmount")));
		}
		if(map.get("fdRequestDate") != null){
			finance.setFdRequestDate((Date)map.get("fdRequestDate"));
		}
		if(map.get("customerId") != null){
			finance.setCustomerid(Long.valueOf((String) map.get("customerId")));
		}
		if(map.get("fdManager") != null){
			finance.setFdManager(Long.valueOf((String) map.get("fdManager")));
		}
		
		
		finance.setFdContractNo(ProductUtil.contractNumber(ParamConstant.CON_NUM_TYPE_APP));//合同编号(大拇指APP)
		finance.setFdPayStatus(ParamConstant.FE_PAY_STAUTS_UNPAY);//付款状态--未付款
		finance.setFdDataOrigin(ParamConstant.FE_DATA_ORIGIN_APP);//数据来源APP
		
		//fortune需要写入的值
		finance.setFdPaymentWay(ParamConstant.FE_PAYMENT_WAY_SELF);//付款方式    1.自行转账
		if(ParamConstant.CRM_PRODUCT_YOUYIJIHUA.equals(finance.getFdProduct()))   //针对优益计划 
			finance.setFdManageFee(ParamConstant.FE_FEE_MANAGER_DISCOUNT_ZREO);//管理费折扣 默认为 0%    
		finance.setFdContinueProduct(ParamConstant.FE_CONTINUE_PRODUCT_ONE);// 进行债券转让
		finance.setFdRiskCompensation(ParamConstant.FE_RISK_COMPENSATION_TRUE);//是否风险补偿 1:是 0：否
		
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
		AddRecordStaff(finance.getFdManager(),ParamConstant.FE_REQUEST_STATE_CREATE,finance.getId()+"");
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
	public ResultVo saveBusiFinanceApp(BusiFundInfo finance, String state,Long staffId) {
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
	 * @author CJ
	 * @date 2015-12-2 14:58:46
	 * @param finance 理财对象
	 * @param state 状态
	 * @return
	 *//*
	public ResultVo updateBusiFinance(BusiFundInfo finance,String state) {
		//如果不是新建的话,那么则需要验证客户的必填信息
		if(!AuditHelper.isNew(state)) {
			List<String> messageList = isSave(finance);
			if(messageList.size() > 0) 
				return new ResultVo(false,messageList); 
		}
		ResultVo resultVo = null;
		Date date = new Date();
		BusiFundInfo oldFinance = financeDao.findOne(finance.getId());
		String oldState = oldFinance.getFdState();//获取之前的状态
		if ("1".equals(state)) {
			state = oldState;
		}
		Staff staff = AuthorityHelper.getStaff();
		finance.setFdDataOrigin(ParamConstant.FE_DATA_ORIGIN_PC);// 线下下单
//		finance.setFdPayStatus(ParamConstant.FD_PAY_STAUTS_UNPAY);//未付款0
		setFinanceValue(finance, oldFinance.getFdInputTime(), oldFinance.getFdInputId(), state, oldFinance.getFdRequestDate(), oldFinance.getFdLendNo(), date, staff.getId());
		financeDao.save(finance);
		//如果是提交状态,记录审核日志
		if(state.equals(AuditHelper.getStateValue(ParamConstant.REQUEST_STATE_DAIZHIJIAN))){
			AuditHelper.saveAuditLog(finance.getId(), ParamConstant.AUDIT_TYPE_FINANCE, "提交到质检","",oldState,state);
		}  
		//记录操作日志信息
		LoggingHelper.createLogging(LoggingType.修改, LoggingSource.产品申请, LoggingHelper.builderLogContent(finance.getId(), oldFinance, finance, getNeedList()));
		//修改返回值，增加业务id值，用于报表记录 
		ResultVo  result=new ResultVo(true);
		result.result.put("id",finance.getId());
		return result;
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
		Query query = em.createQuery("delete from BusiFundInfo where id = :id");
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
	public List<String> isSave(BusiFundInfo finance) {
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
	 * @author CJ
	 * @date 2015-12-2 23:59:15
	 * @return
	 *//*
	public List<String> getNeedList() {
		return Lists.newArrayList("fdProduct", "fdAmount","fdInvestDate", "fdDivestDate", "fdProtocolVersion",
				"fdRiskCompensation", "fdTimeInvestAmount","fdTimeInvestStart", "fdTimeInvestEnd", "fdPaymentWay",
				"fdDeductAccount", "fdReturnAccount", "fdContinueProduct", "fdManager", "fdManageFee", "fdContractNo");
	}
	
	*//**
	 * 给理财对象赋值   
	 *  
	 * @author CJ
	 * @date 2015-11-30 9:59:54
	 * @param finance 理财对象
	 * @param feInputTime 输入时间
	 * @param feInputId 输入人
	 * @param feState 状态
	 * @param feRequestDate 申请日期
	 * @param feLendNo 出借编号
	 * @param feModifyDate 最后一次修改日期
	 * @param feModifyId 最后一次修改人
	 *//*
	public void setFinanceValue(BusiFundInfo finance,Date fdInputTime, Long fdInputId, 
			String fdState, Date fdRequestDate, String fdLendNo, Date fdModifyDate, Long fdModifyId) {
		finance.setFdInputTime(fdInputTime);
		finance.setFdInputId(fdInputId);
		finance.setFdState(fdState);
		finance.setFdRequestDate(fdRequestDate);
		finance.setFdLendNo(fdLendNo);//订单编号
		finance.setFdModifyId(fdModifyId);
		finance.setFdModifyDate(fdModifyDate);
		//增加订单状态位标志  为正常状态 
		finance.setFdValidState(ParamConstant.VALID); //设为正常启用状态
	}
	
	*//**
	 * 检查新增合同编号的唯一性
	 *  
	 * @author CJ
	 * @date 2015-12-01 10:36:05
	 * @param feContractNo 合同编号
	 * @return
	 *//*
	public boolean checkFdContractNoUniqueAdd(String fdContractNo) {
		return financeDao.countFdContractNo(fdContractNo) > 0 ? false : true;
	}
	
	*//**
	 * 检查新增合同编号的唯一性
	 *  
	 * @author CJ
	 * @date 2015-12-2 上午11:00:39
	 * @param feContractNo 合同编号
	 * @param id 理财业务对象ID
	 * @return
	 *//*
	public boolean checkFeContractNoUniqueEdit(String fdContractNo,Long id) {
		return financeDao.countFdContractNo1(fdContractNo, id) > 0 ? false : true;
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
				"select A from BusiFundInfo A,Customer B where A.customerid = B.id ");
		searchBaseSQL
				.append(" and ((A.fdDivestDate between ")
				.append(toDateYYYYMMDD(DateUtil.getYMDTime(new Date())))
				.append(" and ")
				.append(toDateYYYYMMDD(DateUtil
						.getCurrDateAfterDay(Integer.parseInt(new PropertiesLoader(
								"crm.properties")
								.getProperty("finance.continue.invest.remind.day")))));

		//状态过滤
		searchBaseSQL.append(buildSearchSql(
				"A.fdBusiState",
				new String[] { AuditHelper.getFortuneStateValue("回款已撮合"),
						AuditHelper.getFortuneStateValue("投资生效"),
						AuditHelper.getFortuneStateValue("回款待撮合"),
						AuditHelper.getFortuneStateValue("全部已撮合") }));
		//产品过滤
		searchBaseSQL.append(buildSearchSql("A.fdProduct", new String[] {
				ParamConstant.FORTUNE_PRODUCT_ZHENGDASUIYUE,
				ParamConstant.FORTUNE_PRODUCT_ZHENGDAJIXI,
				ParamConstant.FORTUNE_PRODUCT_ZHENGDAFUXIANG,
				ParamConstant.FORTUNE_PRODUCT_ZHENGDASHUANGXIN }));
		searchBaseSQL.append(") or ");
		//月稳盈产品///////////////////////////////
		searchBaseSQL
		.append("(A.fdDivestDate between ")
		.append(toDateYYYYMMDD(DateUtil.getYMDTime(new Date())))
		.append(" and ")
		.append(toDateYYYYMMDD(DateUtil.getCurrDateAfterDay(Integer.parseInt(new PropertiesLoader(
				"crm.properties")
				.getProperty("finance.continue.ywy.invest.remind.day")))));
		// 状态过滤
		searchBaseSQL.append(buildSearchSql(
				"A.fdBusiState",
				new String[] { AuditHelper.getFortuneStateValue("回款已撮合"),
						AuditHelper.getFortuneStateValue("投资生效"),
						AuditHelper.getFortuneStateValue("回款待撮合"),
						AuditHelper.getFortuneStateValue("全部已撮合") }));
		// 产品过滤
		searchBaseSQL.append(buildSearchSql("A.fdProduct", new String[] {
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
		List<BusiFundInfo> financeList = getContent(conditionMap,searchBaseSQL, pageRequest);
		
		List<FinanceMaturityRemind> remindList = new ArrayList<FinanceMaturityRemind>();
		for (BusiFundInfo finance : financeList) {
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
	public FinanceMaturityRemind formatMaturityRemind(BusiFundInfo finance) {
		FinanceMaturityRemind remind = new FinanceMaturityRemind();
		remind.setCustId(finance.getCustomerid());
		remind.setCustName(customerService.getCustomer(finance.getCustomerid()).getCrName());
		remind.setFinanceId(finance.getId());
		remind.setProductId(Long.valueOf(finance.getFdProduct()));
		busiFundsService.findOneProduct(Long.valueOf(finance.getFdProduct()));
		remind.setProductName(busiFundsService.findOneProduct(Long.valueOf(finance.getFdProduct())).getFdFundName());
//		remind.setAmount(finance.getFdAmount());
		remind.setLendNo(finance.getFdLendNo());
		remind.setCustManagerId(finance.getFdManager());
		remind.setCustManagerName("");
		//增加合同编号  Sam.J  14.10.21
		remind.setContractNo(finance.getFdContractNo());
		try {
			remind.setDebtRightsValue(Double.valueOf(CommonUtil.round(FortuneHelper.getFieldsValue(finance.getId(), "pv").get("pv").toString(), 2, 4)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//如果是月收或年丰产品，则提醒日期重新赋值
		if(ParamConstant.FORTUNE_PRODUCT_ZHENGDAYUESHOU.equals(finance.getFdProduct())||ParamConstant.FORTUNE_PRODUCT_ZHENGDANIANFENG.equals(finance.getFdProduct())){
		
		}
		remind.setMaturityDate(finance.getFdDivestDate());
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
	public List<BusiFundInfo> findAllByCustomerid(Long customerId) {
		return  financeDao.findAllByCustomeridAndFdValidState(customerId,ParamConstant.VALID); //修改查询方法为带删除与否标志位查询
	}

	public List<BusiFundInfo> findAllByfdManagerWithNotCustomer(Long crGatherId) {
		return financeDao.findAllByfdManagerWithNotCustomer(crGatherId);
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
	public List<BusiFundInfo> findAllByCustomeridAndFeManager(Long id,Long crGatherId) {
		return financeDao.findAllByCustomeridAndFdManagerAndFdValidState(id,crGatherId,ParamConstant.VALID); //修改查询方法为带删除与否标志位查询
	}
	
	*//**
	 * 构建基金订单编号
	 *  
	 * @author CJ
	 * @date 2015-12-02 15:55:50
	 * @param customerId 客户id
	 * @return 出借编号
	 *//*
	public synchronized String buildFeLendNo(Long customerId){
		String lendNo = "";
		lendNo += customerService.getCustNumber(customerId);
		lendNo += "-" + AuditHelper.getParaValue("BusinessType", "基金"); 
//		String maxLendNo = findCustMaxLendNo(customerId);
//		if(!StringUtils.isBlank(maxLendNo)){
//			lendNo += "-" + String.format("%03d", Long.parseLong(maxLendNo.substring(maxLendNo.length() - 3, maxLendNo.length())) + 1);
//		}else{
//			lendNo += "-001"; 
//		}
//		System.out.println(lendNo + "," + lendNo.length());
		lendNo += produceTheLastThreeNum(findCustomerFinanceMaxCount(customerId));
		return lendNo;
	}
	
	*//**
	 * 生成单个客户理财借款编号最后三位
	 *  
	 * @author Yuan Changchun
	 * @date 2013-12-10 下午04:58:26
	 * @param findCustomerFinanceTotalCount
	 * @return
	 *//*
	private String produceTheLastThreeNum(Long customerFinanceTotalCount) {
		if (customerFinanceTotalCount == null || customerFinanceTotalCount == 0) {
			return "-001";
		}
		return "-" + String.format("%03d", customerFinanceTotalCount + 1);
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
	
	
	public Long getIdByFeLendNo(String fdLendNo){
		return financeDao.getIdByFdLendNo(fdLendNo);
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
	public ResultVo findByFdContractNoAndFdProduct(String fdContractNo, String fdProduct, String fdLendNo) {
		List<BusiFundInfo> busiFinances = financeDao.findByFdContractNoAndFdProduct(fdContractNo, fdProduct);
		
		if(busiFinances!=null)
		{
			for (BusiFundInfo busiFinance : busiFinances) {
				if((fdLendNo == null || !busiFinance.getFdLendNo().equals(fdLendNo)))
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
	public ResultVo findByFdContractNoAndFdProduct(String fdContractNo, String fdProduct, String fdLendNo,String customerid) {
		List<BusiFundInfo> busiFinances1=financeDao.findByFdContractNo(fdContractNo);
		for(BusiFundInfo busiFinance : busiFinances1){
			if(!ParamConstant.VALID.equals(busiFinance.getFdValidState()))continue; //如果订单的状态为逻辑删除，则不加入判断
			if(!customerid.equals(busiFinance.getCustomerid().toString())){
				return new ResultVo(false, "该合同编号已存在，保存失败");
			}
		}
		List<BusiFundInfo> busiFinances = financeDao.findByFdContractNoAndFdProduct(fdContractNo, fdProduct);		
		if(busiFinances!=null)
		{
			for (BusiFundInfo busiFinance : busiFinances) {
				if(!ParamConstant.VALID.equals(busiFinance.getFdValidState()))continue; //如果订单的状态为逻辑删除，则不加入判断
				if((fdLendNo == null || !busiFinance.getFdLendNo().equals(fdLendNo)))
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
    public Long countByFdContractAndFdProduct(String fdContractNo, String fdProduct) {
    	return  financeDao.countByFdContractAndFdProduct(fdContractNo, fdProduct);
    }
    *//**
	 * 修改时根据合同编号和理财产品判断合同编号是否重复
	 * @param feContractNo
	 * @param feProduct
	 * @return
	 *//*
    public Long countByFdContractAndFdProduct(String fdContractNo, String fdProduct,Long id) {
    	return  financeDao.countByFdContractAndFdProduct(fdContractNo, fdProduct,id);
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
		List<BusiFundInfo> busiFinances = financeDao.findByIdIn(idList);
		if (busiFinances != null && busiFinances.size() != 0){
            for(BusiFundInfo b:busiFinances){
            	if(b.getFdBusiState()==null){
            		return new ResultVo(false, "该产品已进行过到期续投操作");
            	}
            	if(!ParamConstant.FortuneState_12.equals(b.getFdBusiState())){
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
		List<BusiFundInfo> busiFinances = financeDao.findByIdIn(idList);
		if (busiFinances != null && busiFinances.size() != 0){
            for(BusiFundInfo b:busiFinances){
            	if(b.getFdBusiState()==null){
            		flag=false;
            	}
            	if(!ParamConstant.FortuneState_12.equals(b.getFdBusiState())){
            		flag=false;
            	}          	
            }          
		}
		return flag;
	}
	
	
	*//**
	 * 根据当客户id查询该客户申请的基金理财业务顺序最大数
	 *  
	 * @author CJ
	 * @date 2015-12-3 下午04:45:17
	 * @param customerid
	 * @return
	 *//*
	public Long findCustomerFinanceMaxCount(Long customerid) {
		String count = financeDao.findCustomerFinanceMaxCount(customerid);
		return Long.valueOf(count == null ? "0" : count);
	}
	
	
	*//**
	 * 根据客户ID查询当前客户已生效的理财业务信息
	 * @param customerId
	 * @return
	 *//*
	public List<BusiFundInfo> getBusiFinanceListByCustomerId(Long customerId){
		return financeDao.getBusiFundInfoListByCustomerId(customerId);
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
		BusiFundInfo finance = financeDao.findOne(id);
		//更新业务状态
		updateBusiFinanceState(finance, auditResult);
		// 添加审批日志
		AuditHelper.saveAuditLog(id, ParamConstant.AUDIT_TYPE_FINANCE, content, "", atPrivState, auditResult);
		//修改返回值，增加业务id值,manager值，用于报表记录  by Sam.J  14.06.20
		ResultVo resultVo = new ResultVo(true);
		resultVo.result.put("id",finance.getId()); 
		resultVo.result.put("manager",finance.getFdManager());
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
	public void updateBusiFinanceState(BusiFundInfo finance, String fdState){
		finance.setFdState(fdState);
		financeDao.save(finance);
	}
	
	public String findBusiFinanceMaxFdContractNo(String fdContractNo) {
		return financeDao.findBusiFundInfoMaxFdContractNo(fdContractNo);
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
		BusiFundInfo busiFinance=financeDao.findOne(id);
		if(busiFinance==null)return new ResultVo(false,"订单不存在!");
		if(!ParamConstant.FE_REQUEST_STATE_CREATE.equals(busiFinance.getFdState()))return new ResultVo(false,"订单状态无法删除!");
		busiFinance.setFdValidState(ParamConstant.UNVALID); //修改状态标志位为   已删除
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
	public List<BusiFundInfo> fdLendNoWithoutApp(String fdLendNo) {
		StringBuffer searchBaseSQL = new StringBuffer();
		// 根据条件查询
		searchBaseSQL
				.append("SELECT A FROM BusiFundInfo A, Customer C WHERE A.customerid = C.id AND A.fdState > 3 AND A.fdDataOrigin='0' AND A.fdLendNo = '")
				.append(fdLendNo).append("'");
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
	* @Title:划扣允许操作
	* @Description: TODO 
	* @param busiFinance
	* @return   
	* @throws 
	* @time:2015-12-4下午02:04:37
	* @author:CJ
	*//*
	public ResultVo checkForPayAuditFlag(BusiFundInfo busiFinance){
		ResultVo resultVo = null;
		try {
//			//先调fortune系统的划扣接口
//			logger.info("====调用fortune划扣复核接口开始====入参："+busiFinance.getId());
//			String result=FortuneHelper.checkForPayAuditFlag(busiFinance.getId(), ParamConstant.FE_PAYAUDIT_PERMIT);
//			logger.info("====调用fortune划扣复核接口结束====返回："+result);
			String result="0000";
			if(FortuneHelper.INVOKE_SUCCESS.equals(result)){ //如若返回成功，则修改本地划扣允许状态
				busiFinance.setFdPayauditFlag(ParamConstant.FE_PAYAUDIT_PERMIT);//1 已划扣
				financeDao.save(busiFinance);
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
	
	*//** 
	* @Title:付款允许操作
	* @Description: TODO 
	* @param busiFinance
	* @return   
	* @throws 
	* @time:2015-12-4下午02:04:37
	* @author:CJ
	*//*
	public ResultVo checkForPayFlag(BusiFundInfo busiFinance){
		ResultVo resultVo = null;
		try {
//			//先调fortune系统的划扣接口
//			logger.info("====调用fortune划扣复核接口开始====入参："+busiFinance.getId());
//			String result=FortuneHelper.checkForPayAuditFlag(busiFinance.getId(), ParamConstant.FE_PAYAUDIT_PERMIT);
//			logger.info("====调用fortune划扣复核接口结束====返回："+result);
			String result="0000";
			if(FortuneHelper.INVOKE_SUCCESS.equals(result)){ //如若返回成功，则修改本地付款允许状态
		//		busiFinance.setFdPayStatus(ParamConstant.FD_PAY_STAUTS_PAYDONE);//1 已付款
				busiFinance.setFdPayauditFlag(ParamConstant.FE_PAYAUDIT_PERMIT);//1 已划扣
				busiFinance.setFdPayTime(new Date());//付款时间
				if("2".equals(busiFinance.getFdPaymentWay())){//若支付方式 委托划扣
					busiFinance.setFdPayauditFlag(ParamConstant.FE_PAYAUDIT_PERMIT);//1 已划扣
				}
				financeDao.save(busiFinance);
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
		public BusiFundInfo updateStateFromRefund(Map<String, Object> map) throws ParseException {
			BusiFundInfo busifinance =financeDao.findByFdLendNo((String)map.get("oldChannelOrderNo"));
				if(null==busifinance){
					logger.info(" 退款通知接口的订单不存在");
					return null;
				}//支付状态  1.未支付  3.已支付  4.已提交退款 5.提交退款失败 6.退款成功 7.退款失败
				String atPrivPaystate = busifinance.getFdPayStatus();
				String privState = busifinance.getFdState();
				String content ="";
				if(null !=map.get("refoudResult")){
					if("0000".equals(map.get("refoudResult"))){//退款成功
						busifinance.setFdPayStatus("6");
						content=ParamConstant.CONSUME_AUDIT_REFUND_SUCCESS;
					}else if ("9999".equals(map.get("refoudResult"))){//退款失败
						busifinance.setFdPayStatus("7");
						content=ParamConstant.CONSUME_AUDIT_REFUND_FAILURE;
					}
				}
				busifinance.setFdModifyDate(new Date());
				financeDao.save(busifinance);
				//记录留痕日志信息
				AuditHelper.saveAuditLogWithPaystatus(busifinance.getId(), ParamConstant.AUDIT_TYPE_FINANCE
, "退款", content, privState,busifinance.getFdState(),atPrivPaystate, busifinance.getFdPayStatus());
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
		public Page<BusiFundInfo> findAllErrorFinance(Map<String, SearchFilter> conditionMap,PageRequest pageRequest,Long...staffIds){
			StringBuffer searchBaseSQL = new StringBuffer(
					"select A from BusiFundInfo A,Customer B where A.customerid = B.id ");
			
		    //不同的业务错误情况  目前只针对APP订单的失败业务
			searchBaseSQL
			.append(" and (")
			.append(" A.fdState='3' and A.fdPayStatus='3'")//CRM系统接收订单成功，发往fortune失败
			.append(" or")
			.append(" A.fdState='4' and A.fdPayStatus='3' and A.fdBusiState='02000012'")//订单作废，发送退款失败的
			.append(" or")
			.append(" A.fdPayStatus='5' ")//退款请求接收失败的
			.append(")");
			//线上APP订单条件
			searchBaseSQL
			.append(" and A.fdDataOrigin='"+ParamConstant.FE_DATA_ORIGIN_APP+"'");
			//未删除订单
			searchBaseSQL
			.append(" and A.fdValidState='"+ParamConstant.VALID+"'");
			
			//添加数据权限
			if(staffIds==null||staffIds.length<1){
				searchBaseSQL = addDataAuthority(searchBaseSQL, Share.PRODUCT_REQUEST, "A", "fdManager");
			}else {
				searchBaseSQL = addDataAuthority(searchBaseSQL, Share.PRODUCT_REQUEST, "A", "fdManager",staffIds);
			}
			
			//查询总记录数
			long total = getTotal(conditionMap,new StringBuffer(searchBaseSQL));
			
			//查询结果集
			List<BusiFundInfo> financeList = getContent(conditionMap,searchBaseSQL, pageRequest);
			
			return new PageImpl<BusiFundInfo>(financeList, pageRequest, total);
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
			BusiFundInfo finance = financeDao.findOne(id);
			// 更新投资申请的状态
			updateFdState(finance.getId(),AuditHelper.getStateValue(ParamConstant.REQUEST_STATE_CHENGGONG));
			
			// 更新客户的状态和业务数量
			updateCustomer(finance.getCustomerid());
			// 添加审批日志
			AuditHelper.saveSubmitAuditLog(finance.getId(), ParamConstant.AUDIT_TYPE_FINANCE, content);
			//增加报表 recordStaff 表的记录
			AddRecordStaff(finance.getFdManager(),ParamConstant.FE_REQUEST_STATE_PASS,finance.getId()+"");	
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
			return financeDao.countByFdProduct(product);
		}
		

		*//** 
		* @Title: findAllByFeLendNo 
		* @Description: 根据出借编号查询非固产品信息
		* @return List<BusiFinance>    返回类型 
		* @author Sam.J
		* @date 2015-8-5 下午02:36:27 
		* @throws 
		*//*
		public List<BusiFundInfo> findAllByFeLendNoForNofix(String feLendNo){
			StringBuffer searchBaseSQL = new StringBuffer();
			// 根据条件查询
			searchBaseSQL
					.append("SELECT A FROM BusiFundInfo A, Customer C WHERE A.customerid = C.id AND A.fdState > 3 AND A.fdLendNo = '")
					.append(feLendNo).append("'");
			//状态过滤
			searchBaseSQL.append(buildSearchSql(
					"A.fdBusiState",
					new String[] { AuditHelper.getFortuneStateValue("回款已撮合"),
							AuditHelper.getFortuneStateValue("投资生效"),
							AuditHelper.getFortuneStateValue("回款待撮合"),
							AuditHelper.getFortuneStateValue("全部已撮合") }));
			//产品过滤
			searchBaseSQL.append(buildSearchSql("A.fdProduct", new String[] {
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
				"select A from BusiFundInfo A,Customer B where A.customerid = B.id ");
		// 判断条件一  feClosedDate为空的话以feInvestDate投资生效日计算封闭日期  ///////////////////////////////
		searchBaseSQL
				.append(" and ((add_months(A.fdInvestDate,12) between ")
				.append(toDateYYYYMMDD(DateUtil.getYMDTime(new Date())))
				.append(" and ")
				.append(toDateYYYYMMDD(DateUtil.getCurrDateAfterDay(Integer.parseInt(new PropertiesLoader(
						"crm.properties")
						.getProperty("finance.continue.nofix.remind.day")))));
		searchBaseSQL.append(" and A.fdClosedDate is null) or ");
		// 判断条件二  feClosedDate不为空的话以feClosedDate作为封闭日期  ///////////////////////////////
		searchBaseSQL
				.append("(A.fdClosedDate between ")
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
				"A.fdBusiState",
				new String[] { AuditHelper.getFortuneStateValue("回款已撮合"),
						AuditHelper.getFortuneStateValue("投资生效"),
						AuditHelper.getFortuneStateValue("回款待撮合"),
						AuditHelper.getFortuneStateValue("全部已撮合") }));
		//产品过滤
		searchBaseSQL.append(buildSearchSql("A.fdProduct", new String[] {
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
		List<BusiFundInfo> financeList = getContent(conditionMap, searchBaseSQL,
				pageRequest);

		List<FinanceMaturityRemind> remindList = new ArrayList<FinanceMaturityRemind>();
		for (BusiFundInfo finance : financeList) {
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
	public Page<BusiFundInfo> findFinanceByCustomerIdAndFelendNo(Map<String, SearchFilter> conditionMap, PageRequest pageRequest) {
		
		StringBuffer searchBaseSQL = new StringBuffer("select A from BusiFundInfo A,Customer B where A.customerid = B.id ");
		// 查询总记录数
		long total = getTotal(conditionMap, new StringBuffer(searchBaseSQL));
		// 查询结果集
		List<BusiFundInfo> financeList = getContent(conditionMap, searchBaseSQL,pageRequest);

		return new PageImpl<BusiFundInfo>(financeList, pageRequest,total);
	}
	
	*//** 
	* @Title: findForKHZY 
	* @Description: 客户转移查询方法
	* @return List<BusiFinance>    返回类型 
	* @author Sam.J
	* @date 2015-9-17 下午01:58:18 
	* @throws 
	*//*
	public List<BusiFundInfo> findForKHZY(Long custormerId,Long crGatherId,String flagDate,String fState,String searchType) {
		StringBuffer searchBaseSQL = new StringBuffer();
		// 根据条件查询
		searchBaseSQL
				.append("SELECT A FROM BusiFundInfo A  WHERE A.fdValidState='1' ");
		if("1".equals(searchType)){
			searchBaseSQL.append("AND A.customerid='")
			             .append(custormerId+"' ")
			             .append("AND A.fdManager='")
			             .append(crGatherId+"' ");
		}else if ("2".equals(searchType)){ //查询客户归属不在当前用户但业务归属在当前用户的投资信息
			searchBaseSQL.append(" AND A.customerid not in (select c.id from Customer c where c.crGather = '")
            .append(crGatherId+"')")
            .append("AND A.fdManager='")
            .append(crGatherId+"' ");
		}			
		if(flagDate!=null&&!"".equals(flagDate)){
			searchBaseSQL.append(" AND (")
					     .append("A.fdInvestDate > to_date('"+flagDate+"','yyyy/mm/dd')")
					     .append(" OR ")
					     .append("A.fdTimeInvestStart > to_date('"+flagDate+"','yyyy/mm/dd')")
					     .append(")");
		}
		if(fState!=null&&!"".equals(fState)){
			searchBaseSQL.append(" AND ")
			             .append("A.fdBusiState IN (")
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
	public List<BusiFundInfo> getId(Map<String ,String >  map) {
		StringBuffer searchBaseSQL = new StringBuffer();
		// 根据条件查询 
		//  customerNum  felendNo  contractNo
		searchBaseSQL.append("SELECT A FROM BusiFundInfo A ,Customer B  WHERE  A.customerid = B.id AND  A.fdValidState='1' ");
		if(map.containsKey("fdlendNo")){
			searchBaseSQL.append("AND A.fdLendNo='").append(map.get("fdlendNo")+"' ");
		}
		if(map.containsKey("customerNum")){
			searchBaseSQL.append("AND B.crCustomerNumber='").append(map.get("customerNum")+"' ");
		}	
		if(map.containsKey("contractNo")){
			searchBaseSQL.append("AND A.fdContractNo='").append(map.get("contractNo")+"' ");
		}	
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
	 * 根据合同编号和理财产品查询理财信息(非续投)
	 *  
	 * @authorCJ
	 * @date 2015-12-1 下午02:19:40
	 * @param feContractNo
	 * @param feProduct
	 * @param customerid
	 * @return
	 *//*
	public ResultVo findByFeContractNoAndFeProduct(String fdContractNo, String fdProduct, String fdLendNo,String customerid) {
		List<BusiFundInfo> busiFinances1=financeDao.findByFdContractNo(fdContractNo);
		for(BusiFundInfo busiFinance : busiFinances1){
			if(!ParamConstant.VALID.equals(busiFinance.getFdValidState()))continue; //如果订单的状态为逻辑删除，则不加入判断
			if(!customerid.equals(busiFinance.getCustomerid().toString())){
				return new ResultVo(false, "该合同编号已存在，保存失败");
			}
		}
		List<BusiFundInfo> busiFinances = financeDao.findByFdContractNoAndFdProduct(fdContractNo, fdProduct);		
		if(busiFinances!=null)
		{
			for (BusiFundInfo busiFinance : busiFinances) {
				if(!ParamConstant.VALID.equals(busiFinance.getFdValidState()))continue; //如果订单的状态为逻辑删除，则不加入判断
				if((fdLendNo == null || !busiFinance.getFdLendNo().equals(fdLendNo)))
					return new ResultVo(false, "合同编号重复，保存失败");
			}
		}
		
		return new ResultVo(true, "操作成功");
	}
		
*/}
