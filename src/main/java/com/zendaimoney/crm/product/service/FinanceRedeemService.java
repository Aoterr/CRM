/** 
 * @(#)BusiAuditDao.java 1.0.0 2013-01-11 14:53:19  
 *  
 * Copyright © 2013 证大财富.  All rights reserved.  
 */

package com.zendaimoney.crm.product.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.constant.LoggingSource;
import com.zendaimoney.constant.LoggingType;
import com.zendaimoney.crm.product.entity.BusiNofixFinance;
import com.zendaimoney.crm.product.entity.BusiRedeem;
import com.zendaimoney.crm.product.repository.FinanceRedeemDao;
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
public class FinanceRedeemService {/*
	
	private static Logger logger = LoggerFactory.getLogger(FinanceRedeemService.class);
	
	@Autowired
	private FinanceRedeemDao financeRedeemDao;
	
	@Autowired
	private CrmCallTaskService crmCallTaskService;
	
	
	*//** 
	* @Title: findByFxBusiNo 
	* @Description: 根据出借编号查询赎回信息
	* @param reBusiNo
	* @return List<BusiRedeem>(描述入参以及返回参数的含义)
	* @author Sam.J
	* @date 2016年1月25日 下午2:30:17 
	* @throws 
	*//*
    public List<BusiRedeem> findByFxBusiNo(String reBusiNo){
    	return financeRedeemDao.findByFxBusiNo(reBusiNo);
    }
    
    *//**
     * 根据出借编号查询最近的赎回信息
     * @param reBusiNo
     * @return
     *//*
    public BusiRedeem findLatestByFxBusiNo(String reBusiNo){
    	List<BusiRedeem> busiRedeems =  financeRedeemDao.findLatestByReBusiNo(reBusiNo);
    	if(busiRedeems.size()>0)
    		return busiRedeems.get(0);
    	return null;
    }
    
    *//** 
    * @Title: findOne 
    * @Description: 根据id查找赎回信息
    * @param id
    * @return BusiRedeem(描述入参以及返回参数的含义)
    * @author Sam.J
    * @date 2016年1月26日 下午5:24:40 
    * @throws 
    *//*
    public BusiRedeem findOne(Long id) {
		return financeRedeemDao.findOne(id);
	}
    
    
   
    *//** 
    * @Title: saveOne 
    * @Description: 保存
    * @param br
    * @return BusiRedeem(描述入参以及返回参数的含义)
    * @author Sam.J
    * @date 2016年1月27日 上午9:55:33 
    * @throws 
    *//*
	@Transactional(readOnly = false)
    public BusiRedeem saveOne(BusiRedeem br) {
		return financeRedeemDao.save(br);
	}
    
    
    *//** 
    * @Title: ByFxBusiId 
    * @Description: 根据理财id跟状态查赎回信息
    * @param id
    * @return BusiRedeem(描述入参以及返回参数的含义)
    * @author Sam.J
    * @date 2016年1月27日 上午9:36:38 
    * @throws 
    *//*
    public BusiRedeem findByFxBusiId(Long id) {
    	List<BusiRedeem> brList=financeRedeemDao.findByReBusiId(id);
    	for (BusiRedeem br:brList){
    		if(!ParamConstant.REDEEM_STATE_CANCLE.equals(br.getReState()))return br;
    	}
    	return null;
	}
	
	
	*//** 
	* @Title: invokeRedeemFin 
	* @Description: 通过赎回业务
	* @param br
	* @return ResultVo(描述入参以及返回参数的含义)
	* @author Sam.J
	* @date 2016年1月26日 上午11:46:15 
	* @throws 
	*//*
    @Transactional(readOnly = false)
	public  ResultVo  invokeRedeemFin(BusiRedeem br){
		logger.info("===调用fortune提交赎回申请请求====begin:"+br.getId());
		String returnSS=FortuneHelper.invokeRedeemFinance(ParamConstant.REDEEM_TYPE_INVOKE,br.getReBusiId()+"","");
//		String returnSS = FortuneHelper.INVOKE_SUCCESS;
		logger.info("===调用fortune提交赎回申请请求====end:"+br.getId()+"  返回："+returnSS);
		if(FortuneHelper.INVOKE_SUCCESS.equals(returnSS)){
			Staff staff = AuthorityHelper.getStaff();
			br.setReInputTime(new Date()); //录入时间
			br.setReInputId(staff!=null?staff.getId():null); //录入人
			br.setReState(ParamConstant.REDEEM_STATE_PASS);//通过状态
			br.setReValid(ParamConstant.VALID);//正常逻辑删除位
			br.setReType(ParamConstant.REDEEM_TYPE_NORMOL);
			financeRedeemDao.save(br);
			//记录审核日志
			AuditHelper.saveAuditLog(br.getId(), ParamConstant.AUDIT_TYPE_REDEEM, "质检通过","","",ParamConstant.REDEEM_STATE_PASS);
			//记录操作日志信息
			LoggingHelper.createLogging(LoggingType.审核通过, LoggingSource.非固赎回, LoggingHelper.builderLogContent(br.getClass().getSimpleName(),br.getId()));
			return new ResultVo(true, "申请成功");
		}
		return new ResultVo(false, "请求出错："+returnSS);
	}
    
    
    *//** 
    * @Title: revokeRedeemFin 
    * @Description: 赎回撤销 
    * @param br
    * @return ResultVo(描述入参以及返回参数的含义)
    * @author Sam.J
    * @date 2016年3月7日 下午3:14:21 
    * @throws 
    *//*
    @Transactional(readOnly = false)
   	public  ResultVo  revokeRedeemFin(BusiRedeem br){
   		logger.info("===调用fortune提交赎回撤销请求====begin:"+br.getId());
   		String returnSS=FortuneHelper.invokeRedeemFinance(ParamConstant.REDEEM_TYPE_REVOKE,br.getReBusiId()+"","");
   		logger.info("===调用fortune提交赎回撤销请求====end:"+br.getId()+"  返回："+returnSS);
   		if(FortuneHelper.INVOKE_SUCCESS.equals(returnSS)||FortuneHelper.REVOKE_SUCCESS.equals(returnSS)){
   			Staff staff = AuthorityHelper.getStaff();
   			br.setReModifyTime(new Date()); //录入时间
   			br.setReModifyId(staff.getId()); //录入人
   			String resultState="";
   			if(FortuneHelper.INVOKE_SUCCESS.equals(returnSS)){
   				br.setReState(ParamConstant.REDEEM_STATE_CANCLE);//通过状态
   				br.setReReturnDate(null);
				br.setReAmount(null); 
   				resultState=ParamConstant.REDEEM_STATE_CANCLE;
   			}else{
   				br.setReState(ParamConstant.REDEEM_STATE_WAITCANCLE);//待处理状态
   				resultState=ParamConstant.REDEEM_STATE_WAITCANCLE;
   				crmCallTaskService.revokeRedeemCallTask(br.getId());
   			} 			
   			financeRedeemDao.save(br);
   			//记录审核日志
   			AuditHelper.saveAuditLog(br.getId(), ParamConstant.AUDIT_TYPE_REDEEM, "撤销","",ParamConstant.REDEEM_STATE_PASS,resultState);
   			//记录操作日志信息
   			LoggingHelper.createLogging(LoggingType.删除, LoggingSource.非固赎回, LoggingHelper.builderLogContent(br.getClass().getSimpleName(),br.getId()));
   			return new ResultVo(true, "撤销请求成功");
   		}
   		return new ResultVo(false, "请求出错："+returnSS);
   	}
*/}
