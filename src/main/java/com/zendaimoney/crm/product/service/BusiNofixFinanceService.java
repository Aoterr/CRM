/** 
 * @(#)BusiAuditDao.java 1.0.0 2013-01-11 14:53:19  
 *  
 * Copyright © 2013 证大财富.  All rights reserved.  
 */

package com.zendaimoney.crm.product.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.constant.LoggingSource;
import com.zendaimoney.constant.LoggingType;
import com.zendaimoney.crm.customer.repository.CustomerDao;
import com.zendaimoney.crm.product.entity.BusiFinance;
import com.zendaimoney.crm.product.entity.BusiNofixFinance;
import com.zendaimoney.crm.product.repository.BusiFinanceDao;
import com.zendaimoney.crm.product.repository.BusiNofixFinanceDao;
import com.zendaimoney.uc.rmi.vo.Staff;


/** 
* 项目名称：crm-webapp
* @ClassName: NoFixFinViewService 
* @Description: 非固续投
* @author Sam.J 
* @date 2015-7-29 下午05:16:53  
*/
@Service
@Transactional(readOnly = true)
public class BusiNofixFinanceService {/*
	
	private static Logger logger = LoggerFactory.getLogger(BusiNofixFinanceService.class);
	
	@Autowired
	private BusiNofixFinanceDao busiNofixFinanceDao;
	
	@Autowired
	private BusiFinanceDao financeDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private WhitelistDao whitelistDao;
	@Autowired
	private CrmCallTaskService crmCallTaskService;

	*//** 
	* @Title: findAllBusiNofixFinance 
	* @Description: 查询所有的续投信息
	* @return List<BusiNofixFinance>    返回类型 
	* @author Sam.J
	* @date 2015-8-5 下午02:09:18 
	* @throws 
	*//*
	public List<BusiNofixFinance> findAllBusiNofixFinance() {
		return (List<BusiNofixFinance>) busiNofixFinanceDao.findAll();
	}

	*//** 
	* @Title: findOne 
	* @Description: 根据id查询某一个续投信息
	* @return BusiNofixFinance    返回类型 
	* @author Sam.J
	* @date 2015-8-5 下午02:09:28 
	* @throws 
	*//*
	public BusiNofixFinance findOne(Long id) {
		return busiNofixFinanceDao.findOne(id);
	}
	
	*//** 
	* @Title: findByFxBusiNo 
	* @Description: 根据出借编号查询续投信息
	* @return List<BusiNofixFinance>    返回类型 
	* @author Sam.J
	* @date 2015-8-5 下午02:08:47 
	* @throws 
	*//*
	public List<BusiNofixFinance> findByFxBusiNo(String fxBusiNo) {
		return busiNofixFinanceDao.findByFxBusiNo(fxBusiNo);
	}
	
	
	*//** 
	* @Title: findByFxContractNo 
	* @Description: 根据合同编号查询续投信息
	* @return List<BusiNofixFinance>    返回类型 
	* @author Sam.J
	* @date 2015-8-5 下午02:08:47 
	* @throws 
	*//*
	public List<BusiNofixFinance> findByFxContractNo(String fxContractNo) {
		return busiNofixFinanceDao.findByFxContractNo(fxContractNo);
	}
	
	*//** 
	* @Title: delBusiNofixFinance 
	* @Description: 逻辑删除订单
	* @return ResultVo    返回类型 
	* @author Sam.J
	* @date 2015-8-13 下午03:13:10 
	* @throws 
	*//*
	@Transactional(readOnly = false)
	public ResultVo delBusiNofixFinance(Long id) {
		try {
			BusiNofixFinance bn=busiNofixFinanceDao.findOne(id);
			bn.setFxValid(ParamConstant.UNVALID); //设为逻辑删除
			Staff staff = AuthorityHelper.getStaff();
			bn.setFxModifyId(staff.getId());//修改人
			bn.setFxModifyTime(new Date()); //修改时间
			busiNofixFinanceDao.save(bn);
			//记录操作日志信息
			LoggingHelper.createLogging(LoggingType.删除, LoggingSource.非固续期, LoggingHelper.builderLogContent(bn.getClass().getSimpleName(),bn.getId()));
			return new ResultVo(true, "操作成功");
		} catch (Exception e) {
			logger.error("",e);
			return new ResultVo(false, "操作失败,请重新尝试或联系管理员!");
		}
	}
	
	*//** 
	* @Title: saveBusiNofixFinance 
	* @Description: 保存业务订单
	* @return ResultVo    返回类型 
	* @author Sam.J
	* @date 2015-8-6 上午11:17:29 
	* @throws 
	*//*
	@Transactional(readOnly = false)
	public ResultVo saveBusiNofixFinance(BusiNofixFinance bnfinance, String state) {
		try {
			Staff staff = AuthorityHelper.getStaff();
			dateCalculation(bnfinance);//日期处理
			
			List<BusiNofixFinance> allNofix = findLatestByFxBusiNo(bnfinance.getFxBusiNo());
			bnfinance.setFxConCount(allNofix.size()+1); //设置续期次数：已有有效续期+1
			
			bnfinance.setFxInputId(staff.getId()); //录入人
			bnfinance.setFxInputTime(new Date());  //录入时间
			bnfinance.setFxModifyId(staff.getId());//修改人
			bnfinance.setFxModifyTime(new Date()); //修改时间
			bnfinance.setFxValid(ParamConstant.VALID);//状态位标为正常
			bnfinance.setFxState(state); //业务状态位
			busiNofixFinanceDao.save(bnfinance);
			//记录审核日志
			AuditHelper.saveCreateAuditLog(bnfinance.getId(), ParamConstant.AUDIT_TYPE_NOFIX, state);
			//记录操作日志信息
			LoggingHelper.createLogging(LoggingType.新增, LoggingSource.非固续期, LoggingHelper.builderLogContent(bnfinance.getClass().getSimpleName(),bnfinance.getId()));
			return new ResultVo(true, "操作成功");
		} catch (Exception e) {
			logger.error("",e);
			return new ResultVo(false, "操作失败,请重新尝试或联系管理员!");
		}
	}
	
	*//** 
	* @Title: updateBusiNofixFinance 
	* @Description: 修改续期业务
	* @return ResultVo    返回类型 
	* @author Sam.J
	* @date 2015-8-10 下午04:27:39 
	* @throws 
	*//*
	@Transactional(readOnly = false)
	public ResultVo updateBusiNofixFinance(BusiNofixFinance bnfinance, String state) {
		try {
			Staff staff = AuthorityHelper.getStaff();
			BusiNofixFinance oldBnfinance=busiNofixFinanceDao.findOne(bnfinance.getId());
			oldBnfinance.setFxConTerm(bnfinance.getFxConTerm()); //修改后期数
			oldBnfinance.setFxConRate(bnfinance.getFxConRate()); //修改后利率
			String oldState = oldBnfinance.getFxState();//获取之前的状态
			oldBnfinance.setFxSignDate(bnfinance.getFxSignDate());//修改签约日期
			dateCalculation(oldBnfinance);//日期处理
			//bnfinance.setFxConCount(1); //设置续期次数为1   PS：目前只支持续期一次，如以后还要新续期  逻辑需要修改
			oldBnfinance.setFxModifyId(staff.getId());//修改人
			oldBnfinance.setFxModifyTime(new Date()); //修改时间
			oldBnfinance.setFxState(state); //业务状态位
			oldBnfinance.setFxContractNo(bnfinance.getFxContractNo());//合同编号
			busiNofixFinanceDao.save(oldBnfinance);
			//记录审核日志
			AuditHelper.saveAuditLog(bnfinance.getId(), ParamConstant.AUDIT_TYPE_NOFIX, "提交到质检","",oldState,state);
			//记录操作日志信息
			LoggingHelper.createLogging(LoggingType.修改, LoggingSource.非固续期, LoggingHelper.builderLogContent(bnfinance.getClass().getSimpleName(),bnfinance.getId()));
			return new ResultVo(true, "操作成功");
		} catch (Exception e) {
			logger.error("",e);
			return new ResultVo(false, "操作失败,请重新尝试或联系管理员!");
		}
	}
	
	
	*//** 
	* @Title: auditNofixFinance 
	* @Description: 质检业务
	* @return ResultVo    返回类型 
	* @author Sam.J
	* @date 2015-8-11 下午02:25:35 
	* @throws 
	*//*
	@Transactional(readOnly = false)
	public ResultVo auditNofixFinance(Long id,String auditState,String content) {
		try {
			ResultVo resultVo = null;
			BusiNofixFinance bnfinance=busiNofixFinanceDao.findOne(id);
			//提交
			if(auditState.equals(AuditHelper.getStateValue(ParamConstant.REQUEST_STATE_CHENGGONG))){
				resultVo = submitNofixFinance(bnfinance, content);
			}
			//退回
			else if(auditState.equals(AuditHelper.getStateValue(ParamConstant.REQUEST_STATE_HUITUI))){
				resultVo = backNofixFinance(bnfinance, content);
			}
			// 拒绝
			else if (auditState.equals(AuditHelper
					.getStateValue(ParamConstant.REQUEST_STATE_JUJUE))) {
				resultVo = refuseNofixFinance(bnfinance, content);
			}
			return resultVo;
		} catch (Exception e) {
			logger.error("",e);
			return new ResultVo(false, "操作失败,请重新尝试或联系管理员!");
		}
	}
	
	
	*//** 
	* @Title: reAuditNofixFinance 
	* @Description: 非固续期复检
	* @return ResultVo    返回类型 
	* @author Sam.J
	* @date 2015-8-21 上午11:44:21 
	* @throws 
	*//*
	@Transactional(readOnly = false)
	public ResultVo reAuditNofixFinance(Long id,String auditState,String content) {
		try {
			BusiNofixFinance bnfinance=busiNofixFinanceDao.findOne(id);
			String oldState=bnfinance.getFxState();
			//修改业务状态
			Staff staff = AuthorityHelper.getStaff();
			bnfinance.setFxModifyId(staff.getId());//修改人
			bnfinance.setFxModifyTime(new Date()); //修改时间
			bnfinance.setFxState(auditState);
			busiNofixFinanceDao.save(bnfinance);
			// 添加审批日志
			AuditHelper.saveAuditLog(id, ParamConstant.AUDIT_TYPE_NOFIX, content, "", oldState, auditState);
			//记录操作日志信息
			LoggingHelper.createLogging(LoggingType.审核, LoggingSource.非固续期, LoggingHelper.builderLogContent(bnfinance.getClass().getSimpleName(),bnfinance.getId()));
			return new ResultVo(true, "操作成功");
		} catch (Exception e) {
			logger.error("",e);
			return new ResultVo(false, "操作失败,请重新尝试或联系管理员!");
		}
	}
	
	
	*//** 
	* @Title: submitNofixFinance 
	* @Description: 质检通过
	* @return ResultVo    返回类型 
	* @author Sam.J
	* @date 2015-8-11 下午02:34:40 
	* @throws 
	*//*
	@Transactional(readOnly = false)
	public ResultVo submitNofixFinance(BusiNofixFinance bnFin, String content) {
		//判断这笔业务是否存在
		if (AuditHelper.isEmpty(bnFin))
			return new ResultVo(false, ParamConstant.ERROR_BUSI_NOT_EXIST);	
		//判断这笔业务当时是否处于可提交状态
		if (!AuditHelper.isSubmit(bnFin.getFxState()))
			return new ResultVo(false, ParamConstant.ERROR_NOT_EXECUTE_CURRENT_OPERATION);	
		// 判断原业务订单fortune状态是否满足条件
		if (!fortuneStateCheck(bnFin.getFxBusiId()))
			return new ResultVo(false, ParamConstant.ERROR_BUSI_STATE_ERROR);
		Staff staff = AuthorityHelper.getStaff();//获取当前登录人
		//发送参数赋值
		Map<String,Object> paramMap=new HashMap<String, Object>();
		paramMap.put("customerLendingNo", bnFin.getFxBusiNo());//出借编号
		paramMap.put("closedPeriodEndDate", bnFin.getFxOldEndDate());//原封闭期到期日
		paramMap.put("againStartDate", bnFin.getFxBeginDate());//续期起始日
		paramMap.put("againEndDate", bnFin.getFxEndDate());//续期到期日
		paramMap.put("againNumber", bnFin.getFxConCount());//续期次数
		paramMap.put("bountyRate", bnFin.getFxConRate());//奖励金比例
		paramMap.put("userId", staff.getId());//操作人
		long stime = System.currentTimeMillis();
		String result = FortuneHelper.invokeNofixFinance(paramMap); 
		//String result="0000";
		long etime = System.currentTimeMillis();
		logger.info("调用fortune续期提交接口结束，本次接口提交共花费时间:" + (etime - stime) + ",提交续期对象ID：" + bnFin.getId());
		logger.info("result:::" + result);
		// 判断发送消息到Fortune是否成功	
		if(!FortuneHelper.INVOKE_SUCCESS.equals(result))
			return new ResultVo(false,result);
		// 更新业务状态
		bnFin.setFxState(ParamConstant.FE_REQUEST_STATE_PASS);	
		bnFin.setFxModifyId(staff.getId());//修改人
		bnFin.setFxModifyTime(new Date()); //修改时间
		busiNofixFinanceDao.save(bnFin);
	    //修改原订单中的封闭日期
		BusiFinance bf=financeDao.findOne(bnFin.getFxBusiId()); //找到原业务订单
		bf.setFeClosedDate(bnFin.getFxEndDate()); //赋值封闭到期日
		bf.setFeModifyId(staff.getId());//修改人
		bf.setFeModifyDate(new Date());//修改日期
		financeDao.save(bf);
		crmCallTaskService.createCallTask(ParamConstant.CallTaskType_NofixBusi, bnFin);//创建回访任务					
		// 添加审批日志
		AuditHelper.saveSubmitAuditLog(bnFin.getId(), ParamConstant.AUDIT_TYPE_NOFIX, content);
		//记录操作日志信息
		LoggingHelper.createLogging(LoggingType.审核通过, LoggingSource.非固续期, LoggingHelper.builderLogContent(bnFin.getClass().getSimpleName(),bnFin.getId()));
		return new ResultVo(true, "操作成功");
	}
	
	
	*//** 
	* @Title: backNofixFinance 
	* @Description: 质检回退 
	* @return ResultVo    返回类型 
	* @author Sam.J
	* @date 2015-8-11 下午02:41:21 
	* @throws 
	*//*
	@Transactional(readOnly = false)
	public ResultVo backNofixFinance(BusiNofixFinance bnFin, String content) {
		//判断这笔业务是否存在
		if (AuditHelper.isEmpty(bnFin))
			return new ResultVo(false, ParamConstant.ERROR_BUSI_NOT_EXIST);
		//判断是否处于可退回状态
		if (!AuditHelper.isBack(bnFin.getFxState()))
			return new ResultVo(false, ParamConstant.ERROR_NOT_EXECUTE_CURRENT_OPERATION);
		//修改业务状态
		Staff staff = AuthorityHelper.getStaff();
		bnFin.setFxModifyId(staff.getId());//修改人
		bnFin.setFxModifyTime(new Date()); //修改时间
		bnFin.setFxState(ParamConstant.FE_REQUEST_STATE_BACK);
		busiNofixFinanceDao.save(bnFin);
		// 添加审批日志
		AuditHelper.saveBackAuditLog(bnFin.getId(), ParamConstant.AUDIT_TYPE_NOFIX, content);
		//记录操作日志信息
		LoggingHelper.createLogging(LoggingType.审核回退, LoggingSource.非固续期, LoggingHelper.builderLogContent(bnFin.getClass().getSimpleName(),bnFin.getId()));
		return new ResultVo(true, "操作成功");
	}
	
	
	*//** 
	* @Title: refuseNofixFinance 
	* @Description: 质检拒绝
	* @return ResultVo    返回类型 
	* @author Sam.J
	* @date 2015-8-11 下午02:47:48 
	* @throws 
	*//*
	private ResultVo refuseNofixFinance(BusiNofixFinance bnFin, String content) {
		// 判断这笔业务是否存在
		if (AuditHelper.isEmpty(bnFin))
			return new ResultVo(false, ParamConstant.ERROR_BUSI_NOT_EXIST);
		// 判断是否处于可拒绝状态
		if (!AuditHelper.isBack(bnFin.getFxState()))
			return new ResultVo(false,
					ParamConstant.ERROR_NOT_EXECUTE_CURRENT_OPERATION);
		//修改业务状态
		Staff staff = AuthorityHelper.getStaff();
		bnFin.setFxModifyId(staff.getId());//修改人
		bnFin.setFxModifyTime(new Date()); //修改时间
		bnFin.setFxState(ParamConstant.FE_REQUEST_STATE_REFUSE);
		busiNofixFinanceDao.save(bnFin);	
		// 添加审批日志
		AuditHelper.saveRefuseAuditLog(bnFin.getId(),
				ParamConstant.AUDIT_TYPE_FINANCE, content);
		return new ResultVo(true, "操作成功");
	}
	
	
	*//** 
	* @Title: revokeBusiNofixFinance 
	* @Description: 撤销续期
	* @return ResultVo    返回类型 
	* @author Sam.J
	* @date 2015-8-14 上午09:42:18 
	* @throws 
	*//*
	@Transactional(readOnly = false)
	public ResultVo revokeBusiNofixFinance(Long id) {
		try {		
			BusiNofixFinance bnFin=busiNofixFinanceDao.findOne(id);
			// 判断原业务订单fortune状态是否满足条件
			if (!fortuneStateCheck(bnFin.getFxBusiId())||!canRevoke(bnFin))
				return new ResultVo(false, ParamConstant.ERROR_BUSI_STATE_ERROR);
			Staff staff = AuthorityHelper.getStaff();//获取当前登录人
			Map<String,Object> paramMap=new HashMap<String, Object>();
			paramMap.put("customerLendingNo", bnFin.getFxBusiNo());//出借编号
			paramMap.put("closedPeriodEndDate", bnFin.getFxOldEndDate());//原封闭期到期日
			paramMap.put("againStartDate", bnFin.getFxBeginDate());//续期起始日
			paramMap.put("againEndDate", bnFin.getFxEndDate());//续期到期日
			paramMap.put("againNumber", bnFin.getFxConCount());//续期次数
			paramMap.put("bountyRate", bnFin.getFxConRate());//奖励金比例
			paramMap.put("userId", staff.getId());//操作人
			long stime = System.currentTimeMillis();
			String result = FortuneHelper.revokeNofixFinance(paramMap); 
			long etime = System.currentTimeMillis();
			logger.info("调用fortune续期撤销接口结束，本次接口提交共花费时间:" + (etime - stime) + ",提交续期撤销对象ID：" + bnFin.getId());
			logger.info("result:::" + result);
			// 判断发送消息到Fortune是否成功	
			if(!FortuneHelper.INVOKE_SUCCESS.equals(result))
				return new ResultVo(false,result);
			// 更新业务状态
			String oldState=bnFin.getFxState(); //原状态用于audit日志记录
			bnFin.setFxState(ParamConstant.FE_REQUEST_STATE_REVOKE);		
			bnFin.setFxModifyId(staff.getId());//修改人
			bnFin.setFxModifyTime(new Date()); //修改时间
			busiNofixFinanceDao.save(bnFin);
			 //修改原订单中的封闭日期
			BusiFinance bf=financeDao.findOne(bnFin.getFxBusiId()); //找到原业务订单
			bf.setFeClosedDate(bnFin.getFxOldEndDate()); //赋值封闭到期日
			bf.setFeModifyId(staff.getId());//修改人
			bf.setFeModifyDate(new Date());//修改日期
			financeDao.save(bf);
			// 添加审批日志
			AuditHelper.saveRevokeAuditLog(bnFin.getId(), ParamConstant.AUDIT_TYPE_NOFIX, "撤销",oldState);
			return new ResultVo(true, "操作成功");
		} catch (Exception e) {
			logger.error("",e);
			return new ResultVo(false, "操作失败,请重新尝试或联系管理员!");
		}
	}
	
	
	*//**
	 * 判断是否允许续期撤销
	 * @param bnFin
	 * @return
	 *//*
	private boolean canRevoke(BusiNofixFinance bnFin) {
		if(!(bnFin.getFxState().compareTo(ParamConstant.FE_REQUEST_STATE_REVOKE) > 0 
				|| ParamConstant.FE_REQUEST_STATE_PASS.equals(bnFin.getFxState())))
			return false;
		
		if(bnFin.getFxConCount() < findValidByFxBusiNo(bnFin.getFxBusiNo()).size())
			return false;
		
		if(new Date().getTime() >= bnFin.getFxEndDate().getTime())
			return false;
		
		return true;
	}

	*//** 
	* @Title: dateCalculation 
	* @Description: 各种时间处理。。巴拉巴拉巴拉。。
	* @return BusiNofixFinance    返回类型 
	* @author Sam.J
	* @date 2015-8-6 下午03:07:52 
	* @throws 
	*//*
	private BusiNofixFinance dateCalculation(BusiNofixFinance bn){
		BusiFinance bf=financeDao.findOne(bn.getFxBusiId()); //找到原业务订单
		Calendar cal = Calendar.getInstance();  
		List<BusiNofixFinance> allNofix = findLatestByFxBusiNo(bf.getFeLendNo());
		if(allNofix.size()>0)
			cal.setTime(allNofix.get(0).getFxBeginDate());//设定时间为投资起始日-原有续期起始日
		else
			cal.setTime(bf.getFeInvestDate()); //设定时间为投资起始日
        cal.add(Calendar.YEAR, 1);//投资起始日加1年，则为原来的投资结束日
        Date OldEndDate=cal.getTime(); 
        bn.setFxOldEndDate(OldEndDate); //订单设置原到期日期
        //如果签约日在原封闭日期前的，新封闭日期按照原封闭到期日算
        if(bn.getFxSignDate().getTime()<=OldEndDate.getTime()){
        	cal.setTime(OldEndDate);  //原投资结束日为新的投资开始日
        	cal.add(Calendar.MONTH, Integer.parseInt(bn.getFxConTerm()));//投资起始日加期数（单位位月），则为新的的投资结束日  PS：目前只有12个月一个选项，即续期一年
        	Date endDate=cal.getTime();
        	bn.setFxBeginDate(OldEndDate);
        	bn.setFxEndDate(endDate);  	
        }else{ //签约日在原封闭期后的，需要计算得出最新的封闭日
        	cal.setTime(bn.getFxSignDate()); //取签约日的年跟月
        	int signYear=cal.get(Calendar.YEAR);
        	int signMonth=cal.get(Calendar.MONTH);        	
        	cal.setTime(OldEndDate);  //取原到期日的 年跟月 
        	int oldYear=cal.get(Calendar.YEAR); 
        	int oldMonth=cal.get(Calendar.MONTH);
        	int monthDiff=(signYear-oldYear)*12+(signMonth-oldMonth); //计算月份差
        	cal.add(Calendar.MONTH, monthDiff); //原到期日加对应的月份，保持日期不变
        	Date beginDate=cal.getTime(); //得到新的封闭起始日
        	cal.add(Calendar.MONTH, Integer.parseInt(bn.getFxConTerm()));//投资起始日加期数（单位位月），则为新的的投资结束日  PS：目前只有12个月一个选项，即续期一年
        	Date endDate=cal.getTime();  //得到新的封闭结束日
        	bn.setFxBeginDate(beginDate);
        	bn.setFxEndDate(endDate);
        }
		return bn;
	}
	
	
	*//**
	 * @Title: fortuneStateCheck
	 * @Description: 判断发起各种业务时fortune状态是否满足
	 * @return boolean 返回类型
	 * @author Sam.J
	 * @date 2015-8-26 下午04:59:14
	 * @throws
	 *//*
	private boolean fortuneStateCheck(long busiid) {
		BusiFinance bf = financeDao.findOne(busiid); // 找到原业务订单
		String state = bf.getFeBusiState();// 得到fortune状态
		//判断订单当前状态是否在下列状态中
		if (AuditHelper.getFortuneStateValue("回款已撮合").equals(state)
				|| AuditHelper.getFortuneStateValue("投资生效").equals(state)
				|| AuditHelper.getFortuneStateValue("回款待撮合").equals(state)
				|| AuditHelper.getFortuneStateValue("全部已撮合").equals(state)) 
		{return true;}
        return false;
	}

	*//**
	 * 查询所有已生效有效续期——质检通过 复检通过 复检回退 复检质检提交
	 * @param fxBusiNo
	 * @return
	 *//*
	public List<BusiNofixFinance> findLatestByFxBusiNo(String fxBusiNo) {
		List<String> status = new ArrayList<String>();
		status.add(ParamConstant.FE_REQUEST_STATE_PASS);
		status.add(ParamConstant.FE_REQUEST_STATE_REPASS);
		status.add(ParamConstant.FE_REQUEST_STATE_REBACK);
		status.add(ParamConstant.FE_REQUEST_STATE_RECOMMIT);
		return busiNofixFinanceDao.findLatestByFxBusiNo(fxBusiNo,status);
	}
	
	*//**
	 * 查询所有有效续期——除撤销 删除外所有
	 * @param fxBusiNo
	 * @return
	 *//*
	public List<BusiNofixFinance> findValidByFxBusiNo(String fxBusiNo) {
		List<String> status = new ArrayList<String>();
		status.add(ParamConstant.FE_REQUEST_STATE_CREATE);
		status.add(ParamConstant.FE_REQUEST_STATE_BACK);
		status.add(ParamConstant.FE_REQUEST_STATE_AUDIT);
		status.add(ParamConstant.FE_REQUEST_STATE_PASS);
		status.add(ParamConstant.FE_REQUEST_STATE_REPASS);
		status.add(ParamConstant.FE_REQUEST_STATE_REBACK);
		status.add(ParamConstant.FE_REQUEST_STATE_RECOMMIT);
		return busiNofixFinanceDao.findLatestByFxBusiNo(fxBusiNo,status);
	}
	
	*//**
	 * 查询所有生效过的续期 质检通过及之后
	 * @param fxBusiNo
	 * @return
	 *//*
	public BusiNofixFinance findPassByFxBusiNo(String fxBusiNo) {
		List<String> status = new ArrayList<String>();
		status.add(ParamConstant.FE_REQUEST_STATE_PASS);
		status.add(ParamConstant.FE_REQUEST_STATE_REFUSE);
		status.add(ParamConstant.FE_REQUEST_STATE_REVOKE);
		status.add(ParamConstant.FE_REQUEST_STATE_REPASS);
		status.add(ParamConstant.FE_REQUEST_STATE_REBACK);
		status.add(ParamConstant.FE_REQUEST_STATE_RECOMMIT);
		List<BusiNofixFinance> list = busiNofixFinanceDao.findLatestByFxBusiNo(fxBusiNo,status);
		if(list.size()>0)
			return list.get(0);
		return null;
	}
*/}
