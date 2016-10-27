package com.zendaimoney.crm.remind.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.zendaimoney.constant.ParamConstant;
import com.zendaimoney.constant.Share;
import com.zendaimoney.crm.BaseService;
import com.zendaimoney.crm.remind.entity.Remind;
import com.zendaimoney.crm.remind.repository.RemindDao;
import com.zendaimoney.utils.ConfigurationHelper;
import com.zendaimoney.utils.DateUtil;


/**
 * 
 * @author bianxj
 * 
 */
@Component
@Transactional(readOnly = true)
public class RemindService extends BaseService<Remind> {
	@Autowired
	private RemindDao remindDao;
	@PersistenceContext
	private EntityManager em;

	//@Autowired
	//public CustomerService customerService;

	@Autowired
	public CustomerBirthDayService customerBirthDayService;

	/**
	 * 根据登录用户ID获取桌面提醒相关数据
	 * 
	 * @author Yuanchangchun
	 * @date 2013-7-02下午01:58:23
	 * @param staffId
	 * @param remindTypes
	 * @return
	 */
	public List<Remind> getRemindByUserId(String... remindTypes) {
		List<Remind> list = new ArrayList<Remind>();
	/*	StringBuffer searchBaseSQL = new StringBuffer(
				"select A from BusiFinance A,Customer B where A.customerid = B.id ");
		searchBaseSQL
				.append(" and ((A.feDivestDate between ")
				.append(toDateYYYYMMDD(DateUtil.getYMDTime(new Date())))
				.append(" and ")
				.append(toDateYYYYMMDD(DateUtil.getCurrDateAfterDay(Integer.parseInt(new PropertiesLoader(
						"crm.properties")
						.getProperty("finance.continue.invest.remind.day")))));
		// 状态过滤
		searchBaseSQL.append(buildSearchSql(
				"A.feBusiState",
				new String[] { AuditHelper.getFortuneStateValue("回款已撮合"),
						AuditHelper.getFortuneStateValue("投资生效"),
						AuditHelper.getFortuneStateValue("回款待撮合"),
						AuditHelper.getFortuneStateValue("全部已撮合") }));
		// 产品过滤
		searchBaseSQL.append(buildSearchSql("A.feProduct", new String[] {
				ParamConstant.FORTUNE_PRODUCT_ZHENGDASUIYUE,
				ParamConstant.FORTUNE_PRODUCT_ZHENGDAJIXI,
				ParamConstant.FORTUNE_PRODUCT_ZHENGDAFUXIANG,
				ParamConstant.FORTUNE_PRODUCT_ZHENGDASHUANGXIN }));
		searchBaseSQL.append(") or ");
		// 月稳盈产品///////////////////////////////
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
		searchBaseSQL
				.append(buildSearchSql(
						"A.feProduct",
						new String[] { ParamConstant.FORTUNE_PRODUCT_ZHENGDAYUEWENYING }));
		searchBaseSQL.append(")");
		// ///////////////////////////////////////////////
		searchBaseSQL.append(")");
		// 添加数据权限    
		// 修改客户经理读取从 busi表变为customer表
		String authorityStr = null;
		if (remindTypes == null || remindTypes.length < 1
				|| remindTypes[0] != "app") {
			authorityStr = AuthorityHelper.createAuthorityHql(
					//Share.PRODUCT_REQUEST, "A", "feManager");
			         Share.PRODUCT_REQUEST, "B", "crGather");
		} else {
			authorityStr = AuthorityHelper.createAuthorityHql(
					//Share.PRODUCT_REQUEST, "A", "feManager", staffId);
					Share.PRODUCT_REQUEST, "B", "crGather", staffId);
		}
		searchBaseSQL.append(authorityStr);
		// 查询到期投资总记录数
		long total = getTotal(null, new StringBuffer(searchBaseSQL));
		List<Remind> list = new ArrayList<Remind>();
		if (total != 0) {
			Remind remind = new Remind();
			remind.setDailyRemindContent("投资到期提醒【" + total + "】");
			remind.setDailyRemindLink("finance/maturity/remind?randomNum="
					+ new Random().nextInt(10000));
			list.add(remind);
		}*/
		// -----------------------以下获取生日提醒总数----------------------------
		// 数据权限--app接口 需要调用uc获取数据权限，这里为减少调用app接口的次数，
		//authorityStr = authorityStr.replaceAll("B.crGather", "A.crGather");
		// 获取生日提醒总条数
		Remind birthDayRemind = getBirthDayRemind();
		if (birthDayRemind != null) {
			list.add(birthDayRemind);
		}
		// app只有两种提醒，到此退出
		if (remindTypes != null && remindTypes[0] == "app")
			return list;
		/*Remind busiCredit = getBusiCreditRemind();

		if (busiCredit != null) {
			list.add(busiCredit);
		}
		Remind busiFinance = getBusiFinanceRemind();
		if (busiFinance != null) {
			list.add(busiFinance);
		}
		Remind info = getCustomerChangeRemind();
		if (info != null) {
			list.add(info);
		}
		Remind product = getProductChangeRemind();
		if (product != null) {
			list.add(product);
		}*/
		//增加 非固到期提醒   add by Sam.J 15.08.24
		/*Remind nofixRemind = getNofixBusiRemind();
		if (nofixRemind != null) {
			list.add(nofixRemind);
		}*/
		return list;
	}

	public String toDateYYYYMMDD(String date) {
		return "TO_DATE('" + date + "','yyyy-MM-dd')";
	}

	/**
	 * 构建理财信息的业务状态查询SQL
	 * 
	 * @author Yuanchangchun
	 * @date 2013-7-02下午01:58:23
	 * @param busiState
	 * @return
	 */
	private static String buildSearchSql(String fieldName, String[] busiState) {
		int len = busiState.length, i = 0;
		StringBuffer busiStateSql = new StringBuffer(" and " + fieldName
				+ " in (");
		for (String state : busiState) {
			busiStateSql.append("'").append(state).append("'");
			if (i < len - 1)
				busiStateSql.append(",");
			i++;
		}
		busiStateSql.append(")");
		return busiStateSql.toString();
	}

	/**
	 * 添加数据权限
	 * 
	 * @author Yuan Changchun
	 * @date 2013-07-02 14:01:10
	 * @param baseSql
	 * @param shareType
	 * @param asName
	 * @param gatherParam
	 * @return
	 */
	/*public StringBuffer addDataAuthority(StringBuffer baseSql,
			Integer shareType, String asName, String gatherParam,
			Long... staffIds) {
		if (staffIds == null || staffIds.length < 1) {
			return baseSql.append(AuthorityHelper.createAuthorityHql(shareType,
					asName, gatherParam));
		} else {
			return baseSql.append(AuthorityHelper.createAuthorityHql(shareType,
					asName, gatherParam, staffIds));
		}
	}*/

	/**
	 * 获得生日提醒在我的桌面的显示提醒实例
	 * 
	 * @author Yuan Changchun
	 * @date 2013-07-02 14:01:10
	 * @param staffId
	 * @return
	 */
	private Remind getBirthDayRemind() {
		StringBuffer baseSql = new StringBuffer(
				"SELECT * from CustomerBirthDayRemindVo A WHERE birthRemainDays >=0 AND birthRemainDays <="
						+ ConfigurationHelper
								.getInteger("customerBirthdayRemindDays"));
		/*
		 * if(remindTypes==null||remindTypes.length<1||remindTypes[0]!="app")
		 * baseSql = addDataAuthority(baseSql, Share.CUSTOMER, "", "crGather");
		 * else { baseSql = addDataAuthority(baseSql, Share.CUSTOMER, "",
		 * "crGather",staffId);
		 * 
		 * }
		 */
		//baseSql.append(authorityStr);
		long total = customerBirthDayService.getTotal(null, baseSql);

		if (total == 0) {
			return null;
		}
		Remind remind = new Remind();
		remind.setDailyRemindContent("客户生日提醒【" + total + "】");
		remind.setDailyRemindLink("crm/remind/remindDetail?remindType=cus_birthday"
				+ "&randomNum=" + new Random().nextInt(10000));
		return remind;
	}

	/**
	 * 信贷回退通知提醒
	 * 
	 * @param staffId
	 * @return
	 */
	private Remind getBusiCreditRemind() {
		StringBuffer baseSql = new StringBuffer(
				"select * from BusiCredit c where c.cState=2 ");
		//baseSql = addDataAuthority(baseSql, Share.CUSTOMER, "", "cManager");
		long total = customerBirthDayService.getTotal(null, baseSql);
		if (total == 0) {
			return null;
		}
		Remind remind = new Remind();
		remind.setDailyRemindContent("信贷回退提醒【" + total + "】");
		remind.setDailyRemindLink("credit/mx/parameter?cState=2&randomNum="
				+ new Random().nextInt(10000));
		return remind;
	}

	/**
	 * 理财回退通知提醒
	 * 
	 * @param staffId
	 * @return
	 */
	private Remind getBusiFinanceRemind() {
		StringBuffer baseSql = new StringBuffer(
				"select * from BusiFinance c where c.feState=2 ");
		//baseSql = addDataAuthority(baseSql, Share.CUSTOMER, "", "feManager");
		long total = customerBirthDayService.getTotal(null, baseSql);
		if (total == 0) {
			return null;
		}
		Remind remind = new Remind();
		remind.setDailyRemindContent("理财回退提醒【" + total + "】");
		remind.setDailyRemindLink("finance/mx/parameter?feState=2&randomNum="
				+ new Random().nextInt(10000));
		return remind;
	}

	/**
	 * 客户信息变更回退通知提醒
	 * 
	 * @param staffId
	 * @return
	 */
	private Remind getCustomerChangeRemind() {
		StringBuffer baseSql = new StringBuffer(
				"select M from Modification M, Customer C where 1 = 1");
		baseSql.append(
				"and M.mnSourceId = C.id and M.mnState=2 and M.mnType = ")
				.append(ParamConstant.BIANGENGCUSTOMER);
		// 添加数据权限
		//baseSql = addDataAuthority(baseSql, Share.CUSTOMER, "C", "crGather");
		long total = customerBirthDayService.getTotal(null, baseSql);
		if (total == 0) {
			return null;
		}
		Remind remind = new Remind();
		remind.setDailyRemindContent("信息变更回退提醒【" + total + "】");
		remind.setDailyRemindLink("modification/customer?mnState=2&randomNum="
				+ new Random().nextInt(10000));
		return remind;
	}

	/**
	 * 申请变更回退提醒
	 * 
	 * @param staffId
	 * @return
	 */
	private Remind getProductChangeRemind() {
		StringBuffer baseSql = new StringBuffer();
		baseSql.append(
				"select M from Modification M, BusiFinance BF, Customer C where 1 = 1")
				.append("and M.mnSourceId = BF.id and BF.customerid = C.id  and M.mnState=2 and M.mnType = ")
				.append(ParamConstant.BIANGENGFINANCE);
		// 添加数据权限
		//baseSql = addDataAuthority(baseSql, Share.CUSTOMER, "C", "crGather");
		long total = customerBirthDayService.getTotal(null, baseSql);
		if (total == 0) {
			return null;
		}
		Remind remind = new Remind();
		remind.setDailyRemindContent("申请变更回退提醒【" + total + "】");
		remind.setDailyRemindLink("modification/business?mnState=2&randomNum="
				+ new Random().nextInt(10000));
		return remind;
	}
	
	
	/**
	 * @Title: getNofixBusiRemind
	 * @Description: 非固类封闭期到期提醒
	 * @return Remind 返回类型
	 * @author Sam.J
	 * @date 2015-8-24 上午11:25:27
	 * @throws
	 */
	/*private Remind getNofixBusiRemind() {
		StringBuffer searchBaseSQL = new StringBuffer(
				"select A from BusiFinance A,Customer B where A.customerid = B.id ");
		// 判断条件一 feClosedDate为空的话以feInvestDate投资生效日计算封闭日期
		// ///////////////////////////////
		searchBaseSQL
				.append(" and ((add_months(A.feInvestDate,12) between ")
				.append(toDateYYYYMMDD(DateUtil.getYMDTime(new Date())))
				.append(" and ")
				.append(toDateYYYYMMDD(DateUtil.getCurrDateAfterDay(Integer.parseInt(new PropertiesLoader(
						"crm.properties")
						.getProperty("finance.continue.nofix.remind.day")))));
		searchBaseSQL.append(" and A.feClosedDate is null) or ");
		// 判断条件二 feClosedDate不为空的话以feClosedDate作为封闭日期
		// ///////////////////////////////
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
		// 产品过滤
		searchBaseSQL.append(buildSearchSql("A.feProduct", new String[] {
				ParamConstant.FORTUNE_PRODUCT_ZHENGDAYUESHOU,
				ParamConstant.FORTUNE_PRODUCT_ZHENGDANIANFENG }));
		// 添加数据权限 客户信息中的客户经理
		searchBaseSQL = addDataAuthority(searchBaseSQL,
				Share.PRODUCT_REQUEST, "B", "crGather");
		// 查询总记录数
		long total = getTotal(null, new StringBuffer(searchBaseSQL));
		if (total == 0) {
			return null;
		}
		Remind remind = new Remind();
		remind.setDailyRemindContent("非固到期提醒【" + total + "】");
		remind.setDailyRemindLink("finance/maturity/nofixremind?randomNum="
				+ new Random().nextInt(10000));
		return remind;
	}*/
}
