/** 
 * @(#)ModificationService.java 1.0.0 2013-01-29 16:15:30  
 *  
 * Copyright © 2013 证大财富.  All rights reserved.  
 */

package com.zendaimoney.crm.modification.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONArray;

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
import org.springside.modules.orm.SearchFilter;

import com.google.common.collect.Lists;
import com.zendaimoney.constant.LoggingSource;
import com.zendaimoney.constant.LoggingType;
import com.zendaimoney.constant.ParamConstant;
import com.zendaimoney.constant.Share;
import com.zendaimoney.crm.BaseService;
import com.zendaimoney.crm.customer.entity.Customer;
import com.zendaimoney.crm.customer.repository.BankaccountDao;
import com.zendaimoney.crm.customer.repository.CustomerDao;
import com.zendaimoney.crm.customer.service.BankaccountService;
import com.zendaimoney.crm.fieldform.helper.formUtilHelper;
import com.zendaimoney.crm.member.entity.Member;
import com.zendaimoney.crm.member.repository.MemberDao;
import com.zendaimoney.crm.modification.entity.Modification;
import com.zendaimoney.crm.modification.entity.ModificationDetail;
import com.zendaimoney.crm.modification.repository.ModificationDao;
import com.zendaimoney.crm.modification.repository.ModificationDetailDao;
import com.zendaimoney.crm.product.entity.BusiAudit;
import com.zendaimoney.crm.product.repository.BusiAuditDao;
import com.zendaimoney.crm.product.repository.BusiCreditDao;
import com.zendaimoney.crm.product.repository.BusiFinanceDao;
import com.zendaimoney.crm.sysuser.entity.SysUser;
import com.zendaimoney.utils.LoggingHelper;
import com.zendaimoney.utils.helper.AuditHelper;


/**
 * Class ModificationService
 * 
 * @author zhanghao
 * @version $Revision:1.0.0, $Date: 2013-01-29 16:15:30 $
 */
@Service
@Transactional
public class ModificationService extends BaseService<Modification> {
	@Autowired
	private ModificationDetailDao detailDao;

	@Autowired
	private BusiCreditDao busiCreditDao;

	@Autowired
	private BusiFinanceDao busiFinanceDao;
	
	@Autowired
	private ModificationDao modificationDao;
	
	@Autowired
	private BusiAuditDao busiAuditDao;

	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private BankaccountService bankaccountService;
	
	@Autowired
	private MemberDao memberDao;
	
	private static Logger logger = LoggerFactory
			.getLogger(ModificationService.class);

	/**
	 * 判断当前客户属于（理财，民信）
	 * 
	 * @author jinghr
	 * @date 2013-8-15 10:54:36
	 * @param id
	 *            客户id
	 * @return 客户系统范围编码
	 */
	public int customerTypeCode(long customerId) {
		int finalCustomerTypeCode = 0;
		if (busiCreditDao.countByCustomerId(customerId) > 0) {
			finalCustomerTypeCode = finalCustomerTypeCode
					+ ParamConstant.CUSTOMERCREDIT;
		}
		if (busiFinanceDao.countByCustomerId(customerId) > 0) {
			finalCustomerTypeCode = finalCustomerTypeCode
					+ ParamConstant.CUSTOMERFINANCE;
		}
		return finalCustomerTypeCode;
	}

	/**
	 * 查找单个变更单信息
	 * 
	 * @author zhanghao
	 * @date 2013-01-29 17:51:49
	 * @param id
	 *            变更单id
	 * @return 变更单对象
	 */
	public Modification findModificationOne(Long id) {
		return modificationDao.findOne(id);
	}

	/**
	 * 保存变更单
	 * 
	 * @author zhanghao
	 * @date 2013-01-29 22:19:38
	 * @param modification
	 *            变更单对象
	 * @param modificationDetails
	 *            变更单明细
	 * @return 是否保存成功
	 */
	public ResultVo saveModification(Modification modification,
			String modificationDetails, String state,SysUser sysuser) {
		ResultVo resultVo = null;
		modification.setMnDate(new Date());
		modification.setMnInputId(sysuser.getId());
		modification.setMnState(state);
		modification.setModificationDetails(builderModificationDetail1(
				modification, modificationDetails));
		resultVo = modificationDao.save(modification) != null ? new ResultVo(
				true) : new ResultVo(false, "保存变更单出错");
		// 记录审核日志
		AuditHelper.saveCreateAuditLog(modification.getId(),
				AuditHelper.getAuditType(modification.getMnType()), state);
		// 记录操作日志信息
		LoggingHelper.createLogging(LoggingType.新增, LoggingSource.申请变更,
				LoggingHelper.builderLogContent(modification.getClass()
						.getSimpleName(), modification.getId()),sysuser);
		resultVo = new ResultVo(true);
		return resultVo;
	}

	/**
	 * 更新变更单
	 * 
	 * @author zhanghao
	 * @date 2013-01-29 22:26:34
	 * @param modification
	 *            变更单对象
	 * @param modificationDetails
	 *            变更单明细
	 * @return 是否更新成功
	 */
	public ResultVo updateModification(Modification modification,
			String modificationDetails, String state,SysUser sysuser) {
		Modification modificationOld = this.findModificationOne(modification
				.getId());// 获取数据库里未变更前的变更单信息
		String oldState = modificationOld.getMnState();
		ResultVo resultVo = null;
		if ("1".equals(state)) {
			state = oldState;
		}
		modification.setMnState(state);
		modification.setMnInputId(modificationOld.getMnInputId());
		modification.setMnDate(modificationOld.getMnDate());
		modification.setModificationDetails(builderModificationDetail1(
				modification, modificationDetails));
		detailDao.delete(modificationOld.getModificationDetails());// 先清空之前的所有明细，在保存新的数据
		resultVo = modificationDao.save(modification) != null ? new ResultVo(
				true) : new ResultVo(true, "修改变更单出错");
		// 如果是提交状态,记录审核日志
		if (state.equals(AuditHelper
				.getStateValue(ParamConstant.REQUEST_STATE_DAIZHIJIAN))) {
			AuditHelper.saveAuditLog(modification.getId(),
					AuditHelper.getAuditType(modification.getMnType()),
					"提交到质检", "", oldState, state);
		}
		// 记录操作日志信息
		LoggingHelper.createLogging(LoggingType.修改, LoggingSource.申请变更,
				LoggingHelper.builderLogContent(modification.getId(),
						modificationOld, modification, Lists.newArrayList("")),sysuser);
		resultVo = new ResultVo(true);
		return resultVo;
	}

	/**
	 * 分页查询所有变更单信息
	 * 
	 * @author zhanghao
	 * @date 2013-01-29 16:39:19
	 * @param spec
	 *            查询条件
	 * @param pageable
	 *            分页对象
	 * @return 分页变更单对象集合
	 *//*
	public Page<Modification> getAllModification(
			Specification<Modification> spec, PageRequest pageable) {
		return modificationDao.findAll(spec, pageable);
	}

	*/
	/**
	 * 分页查询所有变更单信息
	 * 
	 * @author zhanghao
	 * @date 2013-03-14 18:03:51
	 * @param conditionMap
	 *            查询条件
	 * @param pageRequest
	 *            分页对象
	 * @param MnType
	 *            变更单类型
	 * @return
	 */
	public Page<Modification> findAllModification(
			Map<String, SearchFilter> conditionMap, PageRequest pageRequest,
			String MnType) {
		StringBuffer baseSql = new StringBuffer();
		// 理财查询
		if (MnType.equals(ParamConstant.BIANGENGFINANCE)) {
			baseSql.append(
					"select M from Modification M, BusiFinance BF, Customer C where 1 = 1")
					.append("and M.mnSourceId = BF.id and BF.customerid = C.id and M.mnType = ")
					.append(ParamConstant.BIANGENGFINANCE);
			// 添加数据权限
			//baseSql = addDataAuthority(baseSql, Share.CUSTOMER, "C", "crGather");
		}
		// 客户查询
		else if (MnType.equals(ParamConstant.BIANGENGCUSTOMER)) {
			baseSql.append(
					"select M from Modification M, Customer C where 1 = 1")
					.append("and M.mnSourceId = C.id and M.mnType = ")
					.append(ParamConstant.BIANGENGCUSTOMER);
			// 添加数据权限
			//baseSql = addDataAuthority(baseSql, Share.CUSTOMER, "C", "crGather");
		}
		// 新增加，高级搜索部分逻辑处理 Sam.J add 2014.10.14
		try {
			if (conditionMap != null) {
				Iterator it = conditionMap.entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, SearchFilter> condition = (Entry<String, SearchFilter>) it
							.next();
					if (condition.getValue().fieldName.equals("M.mnDate")) {
						String s = (String) condition.getValue().value;
						Date t = new SimpleDateFormat("yyyy/MM/dd").parse(s);
						condition.getValue().value = t;
					} else if (condition.getValue().fieldName
							.equals("M.mnInputId")) {
						condition.getValue().value = Long
								.parseLong((String) condition.getValue().value);
					} else if (condition.getValue().fieldName
							.equals("M.mnState")) {
						String s = (String) condition.getValue().value;
						String newStr = s.replace(",", "','");
						baseSql.append("and M.mnState in ('" + newStr + "')");
						it.remove();
					} else if (condition.getValue().fieldName
							.equals("M.DepStaff")) {
						String s = (String) condition.getValue().value;
						baseSql.append("and M.mnInputId in (" + s + ")");
						conditionMap.entrySet().remove(condition);
					} else if (condition.getValue().fieldName
							.equals("MODIFI.searchType")) {
						String s = (String) condition.getValue().value;
						baseSql.append("and M.id in (select MD.modification from ModificationDetail MD where MD.field="
								+ s + ")");
						it.remove();
					}
				}
			}
		} catch (Exception e) {
			logger.info("", e);
		}
		// 查询总记录数
		long total = getTotalNew(conditionMap, new StringBuffer(baseSql)); // 修改查询总条数方法为新方法
																			// Sam.J
		Page<Modification> page = new PageImpl<Modification>(getContentNew(
				conditionMap, baseSql, pageRequest), pageRequest, total); // 修改查询内容方法为新方法
																			// Sam.J
		return page;
	}

	/**
	 * 构建变更单明细对象集合
	 * 
	 * @author zhanghao
	 * @date 2013-03-01 10:54:20
	 * @param modification
	 *            变更单对象
	 * @param modificationDetails
	 *            明细集合Json字符串
	 * @return 变更单明细对象集合
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public Set<ModificationDetail> builderModificationDetail1(
			Modification modification, String modificationDetails) {
		Set<ModificationDetail> modificationDetailsSet = new HashSet<ModificationDetail>();
		JSONArray jsonArray = JSONArray.fromObject(modificationDetails);
		List<ModificationDetail> modificationDetailList = (List<ModificationDetail>) jsonArray
				.toCollection(jsonArray, ModificationDetail.class);
		for (ModificationDetail modificationDetail : modificationDetailList) {
			modificationDetail.setModification(modification);
			modificationDetailsSet.add(modificationDetail);
		}
		return modificationDetailsSet;
	}

	/**
	 * 审核变更单
	 * 
	 * @author zhanghao
	 * @date 2013-02-25 17:37:52
	 * @param id
	 *            变更单id
	 * @param content
	 *            审核备注
	 * @param auditResult
	 *            审核结果
	 * @return
	 */
	public ResultVo auditModification(Long id, String content,
			String auditResult,Long sysuserId) {
		ResultVo resultVo = null;
		Modification modification = modificationDao.findOne(id);
		// 提交
		if (auditResult.equals(AuditHelper
				.getStateValue(ParamConstant.REQUEST_STATE_CHENGGONG))) {
			resultVo = modificationSubmit(modification, content,sysuserId);
			
		}
		// 退回
		else if (auditResult.equals(AuditHelper
				.getStateValue(ParamConstant.REQUEST_STATE_HUITUI))) {
			resultVo = modificationBack(modification, content);
		}
		// 拒绝
		else if (auditResult.equals(AuditHelper
				.getStateValue(ParamConstant.REQUEST_STATE_JUJUE))) {
			resultVo = refuseModification(modification, content);
		}
		return resultVo;
	}

	/**
	 * 拒绝申请
	 * 
	 * @author Yuan Changchun
	 * @date 2013-12-3 上午09:38:28
	 * @param modification
	 * @param content
	 * @return
	 */
	private ResultVo refuseModification(Modification modification,
			String content) {
		// 判断这笔业务是否存在
		if (AuditHelper.isEmpty(modification))
			return new ResultVo(false, ParamConstant.ERROR_BUSI_NOT_EXIST);
		// 判断是否处于可拒绝状态
		if (!AuditHelper.isBack(modification.getMnState()))
			return new ResultVo(false,
					ParamConstant.ERROR_NOT_EXECUTE_CURRENT_OPERATION);
		modification.setMnState(AuditHelper
				.getStateValue(ParamConstant.REQUEST_STATE_JUJUE));
		// 添加审批日志
		// 如果为信贷信息变更
		if (modification.getMnType().equals("3")) {
			AuditHelper.saveRefuseAuditLog(modification.getId(),
					ParamConstant.AUDIT_TYPE_MODIFICATION_CUSTOMER, content);
		} else if (modification.getMnType().equals("2")) {
			AuditHelper.saveRefuseAuditLog(modification.getId(),
					ParamConstant.AUDIT_TYPE_MODIFICATION_FINANCE, content);
		} else if (modification.getMnType().equals("1")) {
			AuditHelper.saveRefuseAuditLog(modification.getId(),
					ParamConstant.AUDIT_TYPE_MODIFICATION_CREDIT, content);
		}
		this.modificationDao.save(modification);
		return new ResultVo(true);
	}

	/**
	 * 提交变更单 申请
	 * 
	 * @author zhanghao
	 * @date 2013-01-29 23:24:26
	 * @param id
	 *            申请单id
	 * @param memo
	 *            备注
	 * @return 是否提交成功
	 */
	public ResultVo modificationSubmit(Modification modification, String content,Long sysuserId) {
		ResultVo resultVo = null;
		// 判断这笔业务是否存在
		if (AuditHelper.isEmpty(modification))
			return new ResultVo(false, ParamConstant.ERROR_BUSI_NOT_EXIST);

		// 判断是否处于可提交状态
		if (!AuditHelper.isSubmit(modification.getMnState()))
			return new ResultVo(false,
					ParamConstant.ERROR_NOT_EXECUTE_CURRENT_OPERATION);

		// 理财提交
	/*	if (modification.getMnType().equals(ParamConstant.BIANGENGFINANCE)) {
			resultVo = modificationFinanceSubmit(
					modification,
					content,
					AuditHelper
							.getStateValue(ParamConstant.REQUEST_STATE_CHENGGONG));
		}*/
		// 客户提交
		else if (modification.getMnType()
				.equals(ParamConstant.BIANGENGCUSTOMER)) {
			resultVo = modificationCustomerSubmit(
					modification,
					content,
					AuditHelper.getStateValue(ParamConstant.REQUEST_STATE_CHENGGONG),sysuserId);
		}
		// 添加变更单提交审批日志
		if (resultVo.result.get("success") != null
				&& (Boolean) resultVo.result.get("success")) {
			AuditHelper
					.saveSubmitAuditLog(modification.getId(),
							AuditHelper.getAuditType(modification.getMnType()),
							content);
		}
		return resultVo;
	}

	/**
	 * 退回变更单 申请
	 * 
	 * @author zhanghao
	 * @date 2013-01-29 23:24:26
	 * @param id
	 *            申请单id
	 * @param memo
	 *            备注
	 * @return 是否提交成功
	 */
	public ResultVo modificationBack(Modification modification, String content) {
		ResultVo resultVo = null;
		// 判断这笔业务是否存在
		if (AuditHelper.isEmpty(modification))
			return new ResultVo(false, ParamConstant.ERROR_BUSI_NOT_EXIST);

		// 判断是否处于可退回状态
		if (!AuditHelper.isBack(modification.getMnState()))
			return new ResultVo(false,
					ParamConstant.ERROR_NOT_EXECUTE_CURRENT_OPERATION);

		updateModificationState(modification,
				AuditHelper.getStateValue(ParamConstant.REQUEST_STATE_HUITUI));
		// 添加变更单退回审批日志
		AuditHelper.saveBackAuditLog(modification.getId(),
				AuditHelper.getAuditType(modification.getMnType()), content);
		resultVo = new ResultVo(true);
		return resultVo;
	}

	
	/**
	 * 理财变更单提交
	 * 
	 * @author zhanghao
	 * @date 2013-01-30 10:53:44
	 * @param modification
	 *            变更单对象
	 * @param memo
	 *            备注
	 * @return 是否提交成功
	 */
	/*public ResultVo modificationFinanceSubmit(Modification modification,
			String memo, String mnState) {
		ResultVo resultVo = null;
		boolean flag = false;
		try {
			// 提交到fortune系统是否成功
			String result = FortuneHelper.invokeModify(
					String.valueOf(modification.getId()), memo);
			flag = result.equals(FortuneHelper.INVOKE_SUCCESS);
			resultVo = flag ? new ResultVo(true, "提交到理财系统成功!") : new ResultVo(
					false, result);
			logger.info("fortune接口返回: " + result);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultVo(false, "理财系统连接不上,请稍后再试!");
		}

		// 提交到Fortune成功,在更新数据库状态
		if (flag) {
			updateModificationState(modification, mnState);
		}
		return resultVo;
	}*/

	/**
	 * 客户变更单提交
	 * 
	 * @author zhanghao
	 * @date 2013-02-01 15:02:17
	 * @param modification
	 *            变更单对象
	 * @param memo
	 *            对象
	 * @return
	 */
	public ResultVo modificationCustomerSubmit(Modification modification,
			String memo, String mnState,Long sysuserId) {
		ResultVo resultVo = null;
		Set<ModificationDetail> modificationDetailsSet = modification
				.getModificationDetails();
		try {
			boolean flag = formUtilHelper.saveNewValue(modificationDetailsSet,sysuserId);
			if (flag) {
				resultVo = new ResultVo(true);
				updateModificationState(modification, mnState);
				
				//质检通过后，若修改了客户中文名，则修改其银行开户名   --By CJ
				Set<ModificationDetail>  mdSet=modification.getModificationDetails();
				for(ModificationDetail detail:mdSet){
					if(detail.getField().getId()==237 && "crName".equals(detail.getField().getFdFieldEn())){//判断变更的项是否为中文名，是则修改该客户的相关银行开户名
					Long cusId=modification.getMnSourceId();//获取变更单对应的客户id
					bankaccountService.saveBankaccountNameNew(cusId);//更新开户名  有效状态下的用户名
					Customer customer=customerDao.findOne(cusId);
					Member member=memberDao.findMemberByCustomerId(cusId);//查找客户ID关联的会员
					if(member!=null){
					 member.setMrName(customer.getCrName());//更改会员名
					 memberDao.save(member);
					}
					}
				}
			} else {
				resultVo = new ResultVo(false, "修改客户出错!");
			}
			return resultVo;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultVo(false, "客户变更提交抛出异常!");
		}
	}

	/**
	 * 修改变更单状态
	 * 
	 * @author zhanghao
	 * @date 2013-02-25 17:22:08
	 * @param modification
	 * @param mnState
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean updateModificationState(Modification modification,
			String mnState) {
		boolean flag = false;
		modification.setMnState(mnState);
		modificationDao.save(modification);
		flag = true;
		return flag;
	}

	/**
	 * 删除变更单
	 * 
	 * @author zhanghao
	 * @date 2013-04-16 11:05:42
	 * @param id
	 *            变更单ID
	 * @return 是否删除成功
	 *//*
	public ResultVo delModification(Long id) {
		modificationDao.delete(id);
		// Query query =
		// em.createQuery("delete from Modification where id = :id");
		// query.setParameter("id", id);
		// query.executeUpdate();
		return new ResultVo(true, "删除成功!");
	}*/

	public List<BusiAudit> findAllAuditByModificationId(Long id) {
		return busiAuditDao.findAllByAtBusiAndAtTypeOrderByAtInputTimeDesc(id,
				ParamConstant.PRODUCT_TYPE_CUSTOMER_Modification);
	}

	/**
	 * 根据类型和对象id获取未提交的业务数量
	 * 
	 * @author zhanghao
	 * @date 2013-02-27 20:26:43
	 * @param id
	 * @param type
	 * @return
	 *//*
	public Long getUnSubmitCount(Long id, String type) {
		return modificationDao.findUnSubmitCount(id, type);
	}

	*/
	/**
	 * (质检)复审变更单
	 * 
	 * @param id
	 *            变更单id
	 * @param content
	 *            审核备注
	 * @param auditResult
	 *            审核结果
	 * @param atPrivState
	 *            审核前结果
	 * @return
	 */
	public ResultVo auditModificationCheck(Long id, String content,
			String auditResult, String atPrivState) {
		Modification modification = modificationDao.findOne(id);
		// 更新业务状态
		updateModificationState(modification, auditResult);
		// 添加审批日志
		AuditHelper.saveAuditLog(id,
				ParamConstant.AUDIT_TYPE_MODIFICATION_FINANCE, content, "",
				atPrivState, auditResult);
		return new ResultVo(true);
	}
	
	/**
	 * @Title:计算变更数量
	 * @Description: 通过变更的类型跟变更的值去找对应的变更记录条数
	 * @param ids
	 * @param mlValue
	 * @return
	 * @throws
	 * @time:2014-11-12 下午12:55:45
	 * @author:Sam.J
	 */
	public Long findCountByModifyTypeAndValue(List<Long> ids, String mlValue) {
		return detailDao.findCountByModifyTypeAndValue(ids, mlValue);
	}

}
