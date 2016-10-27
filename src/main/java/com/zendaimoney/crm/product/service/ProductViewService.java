/** 
 * @(#)BusiAuditDao.java 1.0.0 2013-01-11 14:53:19  
 *  
 * Copyright © 2013 证大财富.  All rights reserved.  
 */

package com.zendaimoney.crm.product.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.constant.ParamConstant;
import com.zendaimoney.crm.product.entity.BusiFinanceUc;
import com.zendaimoney.crm.product.entity.ProductView;
import com.zendaimoney.crm.product.repository.ProductViewDao;
import com.zendaimoney.crm.product.util.Logger;
import com.zendaimoney.crm.product.vo.ApplyMatchVo;
import com.zendaimoney.uc.rmi.vo.Staff;

/**
 * Class ProductViewService
 * 
 * @author zhanghao
 * @version $Revision:1.0.0, $Date: 2013-01-11 14:53:19 $
 */
@Service
@Transactional(readOnly = true)
public class ProductViewService {/*
	@Autowired
	private ProductViewDao productViewDao;
	@Autowired
	private BusiFinanceUcService busiFinanceUcService;

	public Page<ProductView> getAllProductView(Specification<ProductView> spec,
			PageRequest pageRequest) {
		return productViewDao.findAll(spec, pageRequest);
	}

	public List<ProductView> findAllProductView() {
		return (List<ProductView>) productViewDao.findAll();
	}

	public ProductView findOne(Long id) {
		return productViewDao.findOne(id);
	}

//	public String batchUpdateApplyMatchById(List<Long> ids, String status, String deptName){
//		try{
//			Staff staff = (Staff) SecurityHelper.getUserDetails();
//			Long operatorId = null;
//			if(staff!=null){
//				operatorId = staff.getId();
//			}
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//			String dateStr = dateFormat.format(new Date());
//			Date date = dateFormat.parse(dateStr);
//			productViewDao.batchUpdateApplyMatchById(ids, status,operatorId,date,deptName);
//			Logger.debug(this.getClass(),String.format("executing batchUpdateApplyMatch success where ids:%s",ids.toString()));
//			return "success";
//		}catch (Exception e){
//			e.printStackTrace();
//			Logger.debug(this.getClass(),"executing batchUpdateApplyMatch fail...");
//			return "fail";
//		}
//	}

	public String batchUpdateApplyMatchByLendNo(List<String> codes, String status){
		try{
			productViewDao.batchUpdateApplyMatchByLendNo(codes, status);
			Logger.debug(this.getClass(),String.format("executing batchUpdateApplyMatch success where codes:%s",codes.toString()));
			return "success";
		}catch (Exception e){
			e.printStackTrace();
			Logger.debug(this.getClass(),"executing batchUpdateApplyMatch fail...");
			return "fail";
		}
	}

	public List<ApplyMatchVo> listApplyMatch(Specification<ProductView> spec, PageRequest pageRequest,String busiType){
		Page<ProductView> page = productViewDao.findAll(spec, pageRequest);
		List<ApplyMatchVo> returnList = null;
		if(page!=null){
			List<ProductView> records = page.getContent();
			List<ApplyMatchVo> vos = null;
			if(records!=null&&records.size()>0){
				Logger.debug(this.getClass(),String.format("Get productViews success [size:%s]",records.size()));
				Map<String,ApplyMatchVo> tempRecWithDepName = new HashMap<String, ApplyMatchVo>();
				for(ProductView view : records){
					fetchDataToMap(view,tempRecWithDepName,busiType);		//从view中拿取数据拼装成一个map, 其中key为理财中心的名字,value为ApplyMatchVo
				}
				returnList = new ArrayList<ApplyMatchVo>();
				convertMapToList(returnList,tempRecWithDepName);
				Logger.debug(this.getClass(),String.format("GET ApplyMatchVo success [size:%s]",returnList.size()));
			}
		}
		return returnList;
	}

	private void fetchDataToMap(ProductView view , Map<String,ApplyMatchVo> map, String busiType){
		String depName = "未知理财中心";
		BusiFinanceUc busiFinanceUc = busiFinanceUcService.findByBusiId(view.getId());
		if(busiFinanceUc!=null)
			depName = busiFinanceUc.getFuFinanceCenter();
//		if(StringUtils.isBlank(depName)){//历史数据处理，新建单子时未写入理财中心
//			RecordStaffVo recordStaffVo = UcHelper.getRecordStaff(view.getManager());
//			if(recordStaffVo != null && !Strings.isNullOrEmpty(recordStaffVo.getParentDepName())){
//				depName = recordStaffVo.getParentDepName();
//			}
//		}
//		depName = (depName==null||"".equals(depName))?"未知理财中心":depName;
		String way = view.getFePaymentWay();
		String productName = view.getProductName();
		BigDecimal amount = view.getAmount();
		String status  = view.getApplyMatchStatus();
		String lendNo = view.getLendNo();
		Long applyMatchOperator = view.getApplyMatchOperator();
		Staff staff = null;
		String staffName = "";
		Long staffId = 0L;
		if(applyMatchOperator!=null){//经办人非空，获取id及姓名
			staff = UcHelper.getStaff(applyMatchOperator);
			staffName = staff.getName();
			staffId = staff.getId();
		}
		ApplyMatchVo vo = null;
		
		if(!map.containsKey(depName)){//理财中心depName第一笔单子
			vo = new ApplyMatchVo();
			vo.setFinancialCenterName(depName);

			if(staff!=null){
				vo.setSender(staffName);
				List<Long> staffids = new ArrayList<Long>();
				staffids.add(staffId);
				vo.setStaffId(staffids);
			}else{
				vo.setSender("");
			}
			Map<String,ApplyMatchVo.BusiType> types = new HashMap<String, ApplyMatchVo.BusiType>();
			ApplyMatchVo.BusiType type = new ApplyMatchVo.BusiType("", 0, BigDecimal.ZERO, "汇款出资");
			ApplyMatchVo.BusiType type2 = new ApplyMatchVo.BusiType("", 0, BigDecimal.ZERO, "划扣出资");
			ApplyMatchVo.BusiType type3 = new ApplyMatchVo.BusiType("", 0, BigDecimal.ZERO, "月稳盈");
			types.put("zxzz",type);		//自行转账
			types.put("wthk",type2);	//委托划扣
			types.put("zdywy",type3);	//月稳赢
			vo.setTypes(types);
			map.put(depName,vo);
		}
		else {//depName非首笔单子
			vo = map.get(depName);
			List<Long> staffids = vo.getStaffId();
			if(staffids == null){
				if(staff!=null){
					staffids = new ArrayList<Long>();
					staffids.add(staffId);
					vo.setStaffId(staffids);
					if(StringUtils.isBlank(vo.getSender()))
						vo.setSender(staffName);
					else
						vo.setSender(vo.getSender()+","+staffName);
				}else{
					vo.setSender("");
				}
			}
			else if(staff!=null && !staffids.contains(staffId)){//记录经办人id、姓名，去重
				staffids.add(staffId);
				vo.setStaffId(staffids);
				if(StringUtils.isBlank(vo.getSender()))
					vo.setSender(staffName);
				else
					vo.setSender(vo.getSender()+","+staffName);
			}
		}
		Map<String,ApplyMatchVo.BusiType> types = vo.getTypes();
		ApplyMatchVo.BusiType type = null;
		if("8".equals(productName)){
			type = types.get("zdywy");
		}else {
			if("5".equals(productName)){
				amount = view.getTimeInvestAmount();
			}
			if("1".equals(way)){
				type = types.get("zxzz");
			}else if("2".equals(way)){
				type = types.get("wthk");
			}
		}

		if(type!=null){
			if(ParamConstant.APPLY_MATCH_STATUS_WAITING.equals(status)&&!ParamConstant.APPLY_MATCH_STATUS_WAITING.equals(busiType)){
				lendNo = "<span style='color:red'>"+lendNo+"</span>";
			}
			type.setCount(type.getCount()+1);
			type.setFeLendNo(type.getFeLendNo()==null||"".equals(type.getFeLendNo())?lendNo+",":type.getFeLendNo()+lendNo+",");
			BigDecimal total = type.getTotalAmount();
			if(total!=null&&amount!=null){
				type.setTotalAmount(total.add(amount));
			}
		}
	}

	private void convertMapToList(List<ApplyMatchVo> list,Map<String,ApplyMatchVo> map){
		if(map!=null){
			for(String key : map.keySet()){
				list.add(map.get(key));
			}
		}
	}
	
	*//**
	 * 申请匹配提交，按理财中心和经办人更新状态
	 * @param codes
	 * @param status
	 * @return
	 *//*
//	public int updateApplyMatchByFinanceCenter(String financeCenter, String applyMatchState){
//		try{
//			int count = productViewDao.updateApplyMatchByFinanceCenter(financeCenter, applyMatchState);
//			Logger.debug(this.getClass(),String.format("executing batchUpdateApplyMatch success where codes:%s",financeCenter));
//			return count;
//		}catch (Exception e){
//			e.printStackTrace();
//			Logger.debug(this.getClass(),"executing batchUpdateApplyMatch fail...");
//			return 0;
//		}
//	}


	public void batchUpateApplyMatchBystatusAndDate() throws Exception{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = dateFormat.format(new Date());
		Date date = dateFormat.parse(dateStr);
		productViewDao.updateApplyMatchByStatusAndDate(ParamConstant.APPLY_MATCH_STATUS_NEW,ParamConstant.APPLY_MATCH_STATUS_WAITING,date);
	}

	//根据页面传来出借编号集合封装为ApplyMatchVo集合
	public List<ApplyMatchVo> listApplyMatch(List<String> lendNoList) {
		List<ProductView> records = productViewDao.findAllByLendNos(lendNoList);
		Map<String,ApplyMatchVo> tempRecWithDepName = new HashMap<String, ApplyMatchVo>();
		for(ProductView pView : records)
			fetchDataToMap(pView,tempRecWithDepName,ParamConstant.APPLY_MATCH_STATUS_WAITING);
		return new ArrayList<ApplyMatchVo>(tempRecWithDepName.values());
	}

*/}
