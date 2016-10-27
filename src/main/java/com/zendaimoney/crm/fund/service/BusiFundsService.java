package com.zendaimoney.crm.fund.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.crm.fund.entity.BusiFunds;
import com.zendaimoney.crm.fund.repository.BusiFundsDao;
import com.zendaimoney.sys.announcement.entity.Announcement;
import com.zendaimoney.uc.rmi.vo.Staff;


/**
 * 基金产品  服务类
 * @author CJ
 * @create 2015-11-24, 下午02:08:23
 * @version 1.0
 */
@Component
@Transactional(readOnly = true)
public class BusiFundsService {
	@Autowired
	private BusiFundsDao busiFundsDao;
    //查找所有
	public List<BusiFunds> findAllBusiFunds() {
		return (List<BusiFunds>) busiFundsDao.findAll();
	}
	
	//查找有效且可以购买未删除的产品
	public List<BusiFunds> findAllBusiFundsValid() {
		return (List<BusiFunds>) busiFundsDao.findAllValid();
	}
	
	/*//查找有效且可以购买未删除的产品
		public List<BusiFunds> findAllBusiFundsValidBetweenTime() {
			return (List<BusiFunds>) busiFundsDao.findAllValidBetweenTime();
		}*/
	/**
	 * 查找所有的理财产品
	 * 
	 * @author CJ
	 * @date 2015-11-25,下午02:12:45
	 * @return
	 */
	public List<BusiFunds> findAllFinanceProduct() {
		return (List<BusiFunds>) busiFundsDao.findAllProductByType("2");
	}

	/**
	 * 查找单个产品
	 * 
	 * @author CJ
	 * @date 2015-11-25 10:12:46
	 * @param id
	 *            产品id
	 * @return 产品对象
	 */
	public BusiFunds findOneProduct(Long id) {
		return busiFundsDao.findOne(id);
	}
/**
 * 根据基金代码查找单个基金产品
 * @param fdProductCode
 * @return
 */
	public BusiFunds findOneProductByCode(String fdProductCode) {
		return busiFundsDao.findOneByFdProductCode(fdProductCode);
	}

	/**
	 * 按照types查找产品
	 * 
	 * @author CJ
	 * @date 2015-11-25,下午02:12:45
	 * @return
	 */
	public List<BusiFunds> findProductByTypes(List<String> type) {
		return (List<BusiFunds>) busiFundsDao.findProductByTypes(type);
	}

//查找满足条件的基金产品分页
	public Page<BusiFunds> getAllProduct(
			Specification<BusiFunds> spec,
			PageRequest pageable) {
		return busiFundsDao.findAll(spec, pageable);
	}
	
	//保存基金到数据库
	@Transactional(readOnly = false)
	public BusiFunds saveFunds(BusiFunds busiFunds) {
		//Staff staff = AuthorityHelper.getStaff();//获取当前登录人
		if (busiFunds.getId() == null) {//先查询是否存在该基金  不存在就新建
			busiFunds.setFdInputDate(new Date());
			//busiFunds.setFdInputId(staff.getId());
			busiFunds.setFdIsDel("1");//是否删除标志位  1正常
			return busiFundsDao.save(busiFunds);
		} else {//存在就修改更新
			BusiFunds oldbusiFunds = this.findOneProduct(busiFunds.getId());//根据id查询基金产品
			BusiFunds newbusiFunds = new BusiFunds();
			BeanUtils.copyProperties(oldbusiFunds, newbusiFunds);
			//需要修改字段
			newbusiFunds.setFdAvailableNum(busiFunds.getFdAvailableNum());//可购买份额
			newbusiFunds.setFdBuyFlag(busiFunds.getFdBuyFlag());//可购买标识
			newbusiFunds.setFdEndDate(busiFunds.getFdEndDate());//认购结束时间
			newbusiFunds.setFdFeeWay(busiFunds.getFdFeeWay());//收费方式
			newbusiFunds.setFdFundAlias(busiFunds.getFdFundAlias());//基金别称
			newbusiFunds.setFdFundCode(busiFunds.getFdFundCode());//基金代码
			newbusiFunds.setFdFundName(busiFunds.getFdFundName());//基金名称
			newbusiFunds.setFdFundType(busiFunds.getFdFundType());//基金类型
			newbusiFunds.setFdInvestDate(busiFunds.getFdInvestDate());//基金成立日
			newbusiFunds.setFdManageFee(busiFunds.getFdManageFee());//管理费
			newbusiFunds.setFdMinAmountFirst(busiFunds.getFdMinAmountFirst());//首次购买最低金额
			newbusiFunds.setFdMinShareHold(busiFunds.getFdMinShareHold());//最低持有份额
			//newbusiFunds.setFdModifyId(staff.getId());//修改人
			newbusiFunds.setFdModifyTime(new Date());//修改日期
			newbusiFunds.setFdRengouFee(busiFunds.getFdRengouFee());//认购费
			newbusiFunds.setFdRiskLevel(busiFunds.getFdRiskLevel());//风险等级
			newbusiFunds.setFdSingleAmount(busiFunds.getFdSingleAmount());//单份购买金额
			newbusiFunds.setFdStartDate(busiFunds.getFdStartDate());//认购开始日期
			newbusiFunds.setFdValid(busiFunds.getFdValid());//是否有效
			
		//	newbusiFunds.setFdMaxYield(busiFunds.getFdMaxYield());
		//	newbusiFunds.setFdEndtimeMemo(busiFunds.getFdEndtimeMemo());
		//	newbusiFunds.setFdIsDel(busiFunds.getFdIsDel());//是否删除 1正常
		//	newbusiFunds.setFdMemo1(busiFunds.getFdMemo1());
		//	newbusiFunds.setFdMemo2(busiFunds.getFdMemo2());
		//	newbusiFunds.setFdMemo3(busiFunds.getFdMemo3());
		//	newbusiFunds.setFdMinAmountFix(busiFunds.getFdMinAmountFix());
		//	newbusiFunds.setFdMinShareCallback(busiFunds.getFdMinShareCallback());
		//	newbusiFunds.setFdMinYield(busiFunds.getFdMinYield());
		//	newbusiFunds.setFdShowPosition(busiFunds.getFdShowPosition());
		//	newbusiFunds.setFdShowWay(busiFunds.getFdShowWay());
		//	newbusiFunds.setFdStarttimeMemo(busiFunds.getFdStarttimeMemo());
	//		newbusiFunds.setFdManageAccountId(busiFunds.getFdManageAccountId());//基金托管账户银行账号id
	//		newbusiFunds.setOptlock(busiFunds.getOptlock());//乐观锁  Version
			return busiFundsDao.save(newbusiFunds);
		}
	}
}
