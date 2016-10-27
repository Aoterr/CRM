/*
* Copyright (c) 2015 zendaimoney.com. All Rights Reserved.
*/
package com.zendaimoney.crm.product.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.constant.ParamConstant;
import com.zendaimoney.crm.BaseService;
import com.zendaimoney.crm.product.entity.BusiFinance;
import com.zendaimoney.crm.product.entity.BusiFinanceUc;
import com.zendaimoney.crm.product.repository.BusiFinanceUcDao;
import com.zendaimoney.uc.rmi.vo.Staff;

/**
 * BusiFinanceUcService.java
 *
 * Author: Yangying
 * Date: 2016年9月12日 上午11:18:19
 * Mail: yangy06@zendaimoney.com
 */
@Service
@Transactional
public class BusiFinanceUcService extends BaseService<BusiFinanceUc> {/*

	@Autowired
	private BusiFinanceUcDao busiFinanceUcDao;
	
	*//**
	 * 查询指定业务的UC数据
	 * @param busiId
	 * @return
	 *//*
	public BusiFinanceUc findByBusiId(Long busiId){
		List<BusiFinanceUc> list = busiFinanceUcDao.findByBusiId(busiId);
		if(list.size() == 1)
			return list.get(0);
		return null;
	}

	*//**
	 * 查询业务对应的大团队/理财小组
	 * @param busiId
	 * @return
	 *//*
	public List<String> getFinanceCenterAndGroup(Long busiId) {
		String depName = null;
		String group = null;
		BusiFinanceUc busiFinanceUc = findByBusiId(busiId);
		if(busiFinanceUc!=null){
			depName = busiFinanceUc.getFuFinanceCenter();
			group = busiFinanceUc.getFuBigTeam();
			if(StringUtils.isBlank(group)){
				group = busiFinanceUc.getFuGroupName();
			}
			else if(StringUtils.isNotBlank(busiFinanceUc.getFuGroupName())){
				group = group + "/" + busiFinanceUc.getFuGroupName();
			}
		}
		List<String> result = new ArrayList<String>();
		result.add(group);
		result.add(depName);
		return result;
	}
	
	*//**
	 * 保存指定业务的UC数据
	 * @param finance
	 * @param reason  
	 * @throws Exception   
	 *//*
	public void saveOrUpdate(BusiFinance finance, String reason) throws Exception {
		if(finance==null){
			throw new Exception("业务不存在");
		}
		//登录人
		Long staffId = null;
		Staff staff = AuthorityHelper.getStaff();
		if(staff!=null)
			staffId = staff.getId();
		
		BusiFinanceUc busiFinanceUc = findByBusiId(finance.getId());
		//为空，直接插入
		if(busiFinanceUc==null){
			busiFinanceUc = new BusiFinanceUc();
			newBusiFinanceUc(finance, staffId, busiFinanceUc);
			busiFinanceUcDao.save(busiFinanceUc);
		}
		else{
			//发生业务转移，原记录状态失效，新增一条记录
			if("业务转移".equals(reason)){
				busiFinanceUc.setFuStatus(ParamConstant.UNVALID);
				busiFinanceUcDao.save(busiFinanceUc);//TODO 再插入一条？
				
				BusiFinanceUc newBusiFinanceUc = new BusiFinanceUc();
				newBusiFinanceUc(finance, staffId, newBusiFinanceUc);
				newBusiFinanceUc.setFuMemo(reason);
				busiFinanceUcDao.save(newBusiFinanceUc);
			}
			//编辑提交等场景，直接覆盖
			else{
				makeBusiFinance(finance, busiFinanceUc);
				busiFinanceUc.setFuModifyId(staffId);
				busiFinanceUc.setFuModifyTime(new Date());
				busiFinanceUcDao.save(busiFinanceUc);
			}
		}
	}

	*//**
	 * 新建理财中心关联记录
	 * @param finance
	 * @param staffId
	 * @param busiFinanceUc
	 * @return
	 *//*
	private BusiFinanceUc newBusiFinanceUc(BusiFinance finance, Long staffId, BusiFinanceUc busiFinanceUc) {
		busiFinanceUc.setFuBusiId(finance.getId());
		makeBusiFinance(finance, busiFinanceUc);
		busiFinanceUc.setFuInputId(staffId);
		busiFinanceUc.setFuInputTime(new Date());
		return busiFinanceUc;
	}

	*//**
	 * 组装理财中心关联数据
	 * @param finance
	 * @param busiFinanceUc
	 * @return
	 *//*
	private BusiFinanceUc makeBusiFinance(BusiFinance finance, BusiFinanceUc busiFinanceUc) {
		RecordStaffVo recordStaffVo = UcHelper.getRecordStaff(finance.getFeManager());
		
		busiFinanceUc.setFuManagerId(finance.getFeManager());
		busiFinanceUc.setFuManagerName(recordStaffVo.getStaffName());
		busiFinanceUc.setFuBigTeam(recordStaffVo.getBigTeamName());
		busiFinanceUc.setFuFinanceCenter(recordStaffVo.getParentDepName());
		busiFinanceUc.setFuGroupName(recordStaffVo.getDepName());
		busiFinanceUc.setFuStatus(ParamConstant.VALID);
		busiFinanceUc.setFuTeamLeder(recordStaffVo.getParentStaffName());
		busiFinanceUc.setFuUpperArea(recordStaffVo.getRegionName());
		return busiFinanceUc;
	}
*/}
