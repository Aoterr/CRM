package com.zendaimoney.crm.product.service;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.zendaimoney.crm.customer.entity.Customer;
import com.zendaimoney.crm.customer.helper.CustomerHelper;
import com.zendaimoney.crm.customer.repository.CustomerDao;
import com.zendaimoney.crm.customer.service.CustomerService;
import com.zendaimoney.crm.product.entity.BusiCredit;
import com.zendaimoney.crm.product.repository.BusiCreditDao;
import com.zendaimoney.uc.rmi.vo.Staff;


/**
 * 信贷业务Service层
 * 
 * @author zhanghao
 * @create 2012-11-21, 上午10:06:49
 * @version 1.0
 */
@Component
@Transactional(readOnly = true)
public class BusiCreditService extends ProductViewService {/*

	private static Logger logger = LoggerFactory
			.getLogger(BusiCreditService.class);

	@Autowired
	private BusiCreditDao creditDao;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private CustomerService customerService;

	@PersistenceContext
	private EntityManager em;

	*//**
	 * 保存信贷业务
	 * 
	 * @author zhanghao
	 * @date 2013-01-11 16:41:19
	 * @param entity
	 *            业务对象
	 * @return
	 *//*
	@Transactional(readOnly = false)
	public boolean saveBusiCredit(BusiCredit credit) {
		boolean isNew = credit.getId() == null ? true : false;
		BusiCredit oldCredit = null;
		// List<String> needList = Lists.newArrayList("cProduct", "cAmount",
		// "cTerm", "cRepayMonth", "cLoanUse", "cRepaymentAccount",
		// "cLoanAccount", "cContact", "cManager", "cContractNo");
		if (!isNew)
			oldCredit = creditDao.findOne(credit.getId());
		BusiCredit newCredit = buildBusiCredit(credit, oldCredit);
		boolean flag = creditDao.save(newCredit) != null ? true : false;
		return flag;
	}

	*//**
	 * 获得单个业务对象
	 * 
	 * @author zhanghao
	 * @date 2013-01-11 16:43:13
	 * @param id
	 *            业务id
	 * @return 业务对象
	 *//*
	public BusiCredit findOneCredit(Long id) {
		return creditDao.findOne(id);
	}

	*//**
	 * 获得多个业务对象,并分页
	 * 
	 * @author zhanghao
	 * @date 2013-01-11 16:43:48
	 * @param spec
	 *            动态查询条件对象
	 * @param pageRequest
	 *            分页 对象
	 * @return
	 *//*
	public Page<BusiCredit> getBusiCredit(Specification<BusiCredit> spec,
			PageRequest pageRequest) {
		return creditDao.findAll(spec, pageRequest);
	}

	*//**
	 * 提交申请操作
	 * 
	 * @author zhanghao
	 * @date 2013-01-11 15:59:52
	 * @param id
	 *            业务id
	 * @param content
	 *            审核内容
	 * @return
	 *//*
	@Transactional(readOnly = false)
	public ResultVo submit(Long id, String content) {
		BusiCredit credit = creditDao.findOne(id);

		if (credit == null)
			return new ResultVo(false, ParamConstant.ERROR_BUSI_NOT_EXIST);

		if (!AuditHelper.isSubmit(credit.getcState()))
			return new ResultVo(false,
					ParamConstant.ERROR_NOT_EXECUTE_CURRENT_OPERATION);

		credit.setcState(AuditHelper
				.getStateValue(ParamConstant.REQUEST_STATE_CHENGGONG));
		creditDao.save(credit);
		// 添加审批日志
		AuditHelper.saveSubmitAuditLog(credit.getId(),
				ParamConstant.PRODUCT_TYPE_CREDIT, content);
		return new ResultVo(true);
	}

	*//**
	 * 退回申请操作
	 * 
	 * @author zhanghao
	 * @date 2013-01-11 16:00:53
	 * @param id
	 * @param content
	 * @return
	 *//*
	@Transactional(readOnly = false)
	public ResultVo back(Long id, String content) {
		BusiCredit credit = creditDao.findOne(id);

		if (credit == null)
			return new ResultVo(false, ParamConstant.ERROR_BUSI_NOT_EXIST);

		if (!AuditHelper.isBack(credit.getcState()))
			return new ResultVo(false,
					ParamConstant.ERROR_NOT_EXECUTE_CURRENT_OPERATION);

		credit.setcState(AuditHelper
				.getStateValue(ParamConstant.REQUEST_STATE_HUITUI));
		creditDao.save(credit);
		// 添加审批日志
		AuditHelper.saveBackAuditLog(credit.getId(),
				ParamConstant.PRODUCT_TYPE_CREDIT, content);
		return new ResultVo(true);
	}

	*//**
	 * 构建信贷业务对象
	 * 
	 * @author zhanghao
	 * @date 2013-01-11 16:13:13
	 * @param obj
	 *            业务对象
	 *//*
	public BusiCredit buildBusiCredit(BusiCredit newObject, BusiCredit oldObject) {
		Date date = new Date();
		Staff staff = AuthorityHelper.getStaff();
		// 新增
		if (newObject.getId() == null) {
			newObject.setcInputTime(date);
			newObject.setcInputId(staff.getId());
			newObject.setcState(AuditHelper
					.getStateValue(ParamConstant.REQUEST_STATE_DAIZHIJIAN));
			newObject.setcRequestDate(date);
			newObject.setcBorrowNo(buildCBorrowNo(newObject.getCustomerid()));
		}
		// 修改
		else {
			newObject.setcInputTime(oldObject.getcInputTime());
			newObject.setcInputId(oldObject.getcInputId());
			newObject.setcState(AuditHelper
					.getStateValue(ParamConstant.REQUEST_STATE_DAIZHIJIAN));
			newObject.setcRequestDate(oldObject.getcRequestDate());
			newObject.setcBorrowNo(oldObject.getcBorrowNo());
		}
		newObject.setcModifyId(staff.getId());
		newObject.setcModifyDate(date);

		return newObject;
	}

	*//**
	 * 构建借款编号
	 * 
	 * @author zhanghao
	 * @date 2013-01-06 15:55:50
	 * @param customerId
	 *            客户id
	 * @return 借款编号
	 *//*
	public synchronized String buildCBorrowNo(Long customerId) {
		String prefix = customerDao.getCustomerNumber(customerId);// 前缀,取客户编号
		String suffix = CommonUtil.formatNumber(creditDao
				.countByCustomerId(customerId) + 1);
		return prefix + suffix;
	}

	// ////////////////////////////////////民信//////////////////////////////////////////
	@Transactional(readOnly = false)
	public boolean saveBusiCredit(BusiCredit busiCredit, String state,
			String urgencyDegree, String btValidate) {
		busiCredit.setcState(state);
		int actionAll = 0;
		if (btValidate != null) {
			String[] actionArr = btValidate.split(",");
			for (String string : actionArr) {
				actionAll += Integer.valueOf(string);
			}
		}
		BusiCredit creditOld = null;
		if (busiCredit.getId() != null) {
			creditOld = this.creditDao.findOne(busiCredit.getId());
			creditOld = ProductUtil.copyFieldValue(creditOld, busiCredit);
			creditOld.setcModifyDate(new Date());
			creditOld.setcModifyId(AuthorityHelper.getStaff().getId());
			creditOld.setBtValidate(actionAll);
			return creditDao.save(creditOld) != null ? true : false;
		} else {
			busiCredit.setcBorrowNo(buildNewCBorrowNo(busiCredit
					.getCustomerid()));
			busiCredit.setcInputTime(new Date());
			busiCredit.setcRequestDate(new Date());
			busiCredit.setcInputId(AuthorityHelper.getStaff().getId());
			busiCredit.setBtValidate(actionAll);
			return creditDao.save(busiCredit) != null ? true : false;
		}
	}

	*//**
	 * 构建出借编号
	 * 
	 * @author Yuan Changchun
	 * @date 2013-07-12 10:50:20
	 * @param customerId
	 *            客户id
	 * @return 出借编号
	 *//*
	public synchronized String buildNewCBorrowNo(Long customerId) {
		String lendNo = "";
		lendNo += customerService.getCustNumber(customerId);
		lendNo += "-" + AuditHelper.getParaValue("BusinessType", "信贷");
		String maxLendNo = findCustMaxLendNo(customerId);
		if (!StringUtils.isBlank(maxLendNo)) {
			lendNo += "-"
					+ String.format("%03d", Long.parseLong(maxLendNo.substring(
							maxLendNo.length() - 3, maxLendNo.length())) + 1);
		} else {
			lendNo += "-001";
		}
		logger.info(lendNo + "," + lendNo.length());
		return lendNo;
	}

	*//**
	 * 查找客户当前的最大出借编号
	 * 
	 * @author Yuan Changchun
	 * @date 2013-07-12 11:00:20
	 * @param custId
	 * @return
	 *//*
	public String findCustMaxLendNo(Long custId) {
		return this.creditDao.findCustMaxLendNo(custId);
	}

	*//**
	 * 信贷审核
	 * 
	 * @author Yuan Changchun
	 * @date 2013-7-16 上午10:52:25
	 * @param id
	 * @param content
	 * @param auditResult
	 * @return
	 *//*
	@Transactional(readOnly = false)
	public ResultVo auditCredit(Long id, String content, String auditResult) {
		ResultVo resultVo = null;
		BusiCredit credit = creditDao.findOne(id);
		// 提交
		if (auditResult.equals(AuditHelper
				.getStateValue(ParamConstant.REQUEST_STATE_CHENGGONG))) {
			resultVo = submitCredit(credit, content);
		}
		// 退回
		else if (auditResult.equals(AuditHelper
				.getStateValue(ParamConstant.REQUEST_STATE_HUITUI))) {
			resultVo = backCredit(credit, content);
		}
		return resultVo;
	}

	private static ICreditService creditService;
	static {
		creditService = (ICreditService) SpringContextHelper
				.getBean("creditService");
	}

	*//**
	 * 提交质检申请
	 * 
	 * @author Yuan Changchun
	 * @date 2013-7-16 上午11:18:03
	 * @param credit
	 * @param content
	 * @return
	 *//*
	@Transactional(readOnly = false)
	private ResultVo submitCredit(BusiCredit credit, String content) {
		if (AuditHelper.isEmpty(credit)) {
			return new ResultVo(false, ParamConstant.ERROR_BUSI_NOT_EXIST);
		}
		if (!AuditHelper.isSubmit(credit.getcState())) {
			return new ResultVo(false,
					ParamConstant.ERROR_NOT_EXECUTE_CURRENT_OPERATION);
		}
		if (CustomerHelper.validateMXCustomerRequird(
				customerService.getCustomer(credit.getCustomerid())).size() > 0 ? true
				: false)
			return new ResultVo(false, ParamConstant.ERROR_CUSTOMER_NOT_FULL);
		// 信贷消息推送接口

		CreditApplyVo busiCreditVo = new CreditApplyVo();
		copyBudiCreditValue2Vo(credit, busiCreditVo);
		long stime = System.currentTimeMillis();
		com.zendaimoney.credit.vo.ResultVo result = CreditHelper
				.createLoan(busiCreditVo);
		long etime = System.currentTimeMillis();

		logger.info("本次接口提交共花费时间:" + (etime - stime) + ",提交信贷对象ID："
				+ credit.getId());

		logger.info("result:::" + result);
		if ("success".equals(result)) {
			// 更新投资申请的状态
			updateCState(
					credit.getId(),
					AuditHelper
							.getStateValue(ParamConstant.REQUEST_STATE_CHENGGONG));
			// 更新客户的状态和业务数量
			updateCustomer(credit.getCustomerid());
			AuditHelper.saveSubmitAuditLog(credit.getId(),
					ParamConstant.AUDIT_TYPE_CREDIT, content);
			return new ResultVo(true);
		} else {
			return new ResultVo(false, result);
		}
	}

	*//**
	 * 把credit中必须的值全部拷贝到vo对象中
	 * 
	 * @author Yuan Changchun
	 * @date 2013-7-18 下午03:57:52
	 * @param credit
	 * @param busiCreditVo
	 *//*
	private static void copyBudiCreditValue2Vo(BusiCredit credit,
			CreditApplyVo busiCreditVo) {
		busiCreditVo.setId(credit.getId());
		busiCreditVo.setBtUrgent(credit.getBtUrgent());
		busiCreditVo.setBtUrgentAmount(credit.getBtUrgentAmount());
		busiCreditVo.setBtValidate(credit.getBtValidate());
		busiCreditVo.setcAmount(credit.getcAmount());
		busiCreditVo.setcBorrowNo(credit.getcBorrowNo());
		busiCreditVo.setcBusiState(credit.getcBusiState());
		busiCreditVo.setcContact(credit.getcContact());
		busiCreditVo.setcContractNo(credit.getcContractNo());
		busiCreditVo.setcInputId(credit.getcInputId());
		busiCreditVo.setcInputTime(credit.getcInputTime());
		busiCreditVo.setcLoanAccount(credit.getcLoanAccount());
		busiCreditVo.setcLoanUse(credit.getcLoanUse());
		busiCreditVo.setcManager(credit.getcManager());
		busiCreditVo.setcMemo(credit.getcMemo());
		busiCreditVo.setcMinAmount(credit.getcMinAmount());
		busiCreditVo.setcModifyDate(credit.getcModifyDate());
		busiCreditVo.setcModifyId(credit.getcModifyId());
		busiCreditVo.setcProduct(credit.getcProduct());
		busiCreditVo.setcRepaymentAccount(credit.getcRepaymentAccount());
		busiCreditVo.setcRepayMonth(credit.getcRepayMonth());
		busiCreditVo.setcRequestDate(credit.getcRequestDate());
		busiCreditVo.setcResult(credit.getcResult());
		busiCreditVo.setcState(credit.getcState());
		busiCreditVo.setcTerm(credit.getcTerm());
		busiCreditVo.setCustomerid(credit.getCustomerid());
		busiCreditVo.setcWarranter(credit.getcWarranter());
	}

	*//**
	 * 更新客户信息
	 * 
	 * @author Yuan Changchun
	 * @date 2013-7-16 下午03:18:26
	 * @param customerid
	 *//*
	@Transactional(readOnly = false)
	private void updateCustomer(Long customerid) {
		Customer customer = customerService.getCustomer(customerid);
		// 业务数量加一,如果是储备客户的话,那么则将其更新为客户
		customer.setCrBusiCount(customer.getCrBusiCount() == null ? 1
				: (customer.getCrBusiCount() + 1));
		if (customer.getCrCustomerType().equals(
				AuditHelper.getParaValue("customerType", "储备客户"))) {
			customer.setCrCustomerType(AuditHelper.getParaValue("customerType",
					"客户"));
		}
		customerService.saveCustomer(customer);
	}

	*//**
	 * 更新信贷业务状态
	 * 
	 * @author Yuan Changchun
	 * @date 2013-7-16 下午03:18:35
	 * @param id
	 * @param cState
	 *//*
	@Transactional(readOnly = false)
	private void updateCState(Long id, String cState) {
		Query query = em
				.createQuery("update BusiCredit set cState = :cState where id = :id");
		query.setParameter("cState", cState);
		query.setParameter("id", id);
		query.executeUpdate();
	}

	*//**
	 * 退回质检申请
	 * 
	 * @author Yuan Changchun
	 * @date 2013-7-16 上午11:18:32
	 * @param credit
	 * @param content
	 * @return
	 *//*
	@Transactional(readOnly = false)
	private ResultVo backCredit(BusiCredit credit, String content) {
		// 判断这笔业务是否存在
		if (AuditHelper.isEmpty(credit))
			return new ResultVo(false, ParamConstant.ERROR_BUSI_NOT_EXIST);
		// 判断是否处于可退回状态
		if (!AuditHelper.isBack(credit.getcState()))
			return new ResultVo(false,
					ParamConstant.ERROR_NOT_EXECUTE_CURRENT_OPERATION);
		credit.setcState(AuditHelper
				.getStateValue(ParamConstant.REQUEST_STATE_HUITUI));
		creditDao.save(credit);
		// 添加审批日志
		AuditHelper.saveBackAuditLog(credit.getId(),
				ParamConstant.AUDIT_TYPE_CREDIT, content);
		return new ResultVo(true);
	}

*/}
