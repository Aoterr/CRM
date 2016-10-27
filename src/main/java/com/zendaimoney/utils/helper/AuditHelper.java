package com.zendaimoney.utils.helper;


import java.util.Date;

import com.zendaimoney.constant.ParamConstant;
import com.zendaimoney.crm.product.entity.BusiAudit;
import com.zendaimoney.crm.product.repository.BusiAuditDao;
import com.zendaimoney.sys.parameter.entity.Parameter;
import com.zendaimoney.uc.rmi.vo.Staff;

/**
 * Class AuditHelper 审核帮助类
 * 
 * @author zhanghao
 * @version $Revision:1.0.0, $Date: 2013-01-29 23:18:24 $
 */
public class AuditHelper {
	private static BusiAuditDao auditDao;
	static {
		auditDao = SpringContextHelper.getBean("busiAuditDao");
	}

	/**
	 * 是否可以编辑,用于控制直接通过浏览器输入地址访问修改界面
	 * 
	 * @author zhanghao
	 * @date 2013-01-09 11:28:11
	 * @param state
	 *            状态
	 */
	public static boolean isEdit(String state) {
		boolean flag = true;
		if (!(state.trim().equals(
				getStateValue(ParamConstant.REQUEST_STATE_HUITUI)) || state
				.trim().equals(
						getStateValue(ParamConstant.REQUEST_STATE_XINJIAN)))) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 是否可以退回,用于控制直接通过浏览器输入地址访问退回界面
	 * 
	 * @author zhanghao
	 * @date 2013-01-09 11:28:11
	 * @param state
	 *            状态
	 */
	public static boolean isBack(String state) {
		boolean flag = true;
		if (!state.trim().equals(
				getStateValue(ParamConstant.REQUEST_STATE_DAIZHIJIAN))) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 是否可以提交,用于控制直接通过浏览器输入地址访问提交界面
	 * 
	 * @author zhanghao
	 * @date 2013-01-09 11:28:11
	 * @param state
	 *            状态
	 */
	public static boolean isSubmit(String state) {
		boolean flag = true;
		if (!state.trim().equals(
				getStateValue(ParamConstant.REQUEST_STATE_DAIZHIJIAN))) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @author zhanghao
	 * @date 2013-01-23 10:52:48
	 * @param obj
	 *            对象
	 * @return 为空返回:true,不为空返回false
	 */
	public static boolean isEmpty(Object obj) {
		return obj == null ? true : false;
	}

	/**
	 * 保存产品申购审核记录
	 * 
	 * @author zhanghao
	 * @date 2013-01-11 15:01:59
	 * @param busi
	 *            业务id
	 * @param type
	 *            类型
	 * @param content
	 *            内容
	 * @param state
	 *            状态
	 * @param privState
	 *            审核前状态
	 * @param nextState
	 *            审核后状态
	 */
	public static void saveAuditLog(Long busi, String type, String content,
			String state, String privState, String nextState) {
		Date date = new Date();
		Staff staff = AuthorityHelper.getStaff();
		// 修改id获取方法，如果没有登录人，则判断位app日志，staff的id 值赋值为 99999999 Sam.J 14.11.18
		long staffid = staff == null ? ParamConstant.APP_AUDIT_STAFFID : staff
				.getId();
		BusiAudit audit = new BusiAudit(busi, type, content, staffid, date,
				state, privState, nextState);
		auditDao.save(audit);
	}

	/**
	 * 保存产品申购审核记录 为app 提供接口
	 * 
	 * @author zhanghao
	 * @date 2013-01-11 15:01:59
	 * @param busi
	 *            业务id
	 * @param type
	 *            类型
	 * @param content
	 *            内容
	 * @param state
	 *            状态
	 * @param privState
	 *            审核前状态
	 * @param nextState
	 *            审核后状态
	 */
	public static void saveAuditLogApp(Long staffId, Long busi, String type,
			String content, String state, String privState, String nextState) {
		Date date = new Date();
		BusiAudit audit = new BusiAudit(busi, type, content, staffId, date,
				state, privState, nextState);
		auditDao.save(audit);
	}

	/**
	 * 保存回退审核记录
	 * 
	 * @author zhanghao
	 * @date 2013-01-11 16:19:50
	 * @param busi
	 *            业务id
	 * @param type
	 *            类型
	 * @param content
	 *            内容
	 */
	public static void saveBackAuditLog(Long busi, String type, String content) {
		saveAuditLog(busi, type, content, "",
				getStateValue(ParamConstant.REQUEST_STATE_DAIZHIJIAN),
				getStateValue(ParamConstant.REQUEST_STATE_HUITUI));
	}

	/**
	 * 保存拒绝审核记录
	 * 
	 * @author Yuan Changchun
	 * @date 2013-11-21 下午04:17:01
	 * @param busi
	 * @param type
	 * @param content
	 */
	public static void saveRefuseAuditLog(Long busi, String type, String content) {
		saveAuditLog(busi, type, content, "",
				getStateValue(ParamConstant.REQUEST_STATE_DAIZHIJIAN),
				getStateValue(ParamConstant.REQUEST_STATE_JUJUE));
	}

	/**
	 * 保存提交审核记录
	 * 
	 * @author zhanghao
	 * @date 2013-01-11 16:20:33
	 * @param busi
	 *            业务id
	 * @param type
	 *            类型
	 * @param content
	 *            内容
	 */
	public static void saveSubmitAuditLog(Long busi, String type, String content) {
		saveAuditLog(busi, type, content, "",
				getStateValue(ParamConstant.REQUEST_STATE_DAIZHIJIAN),
				getStateValue(ParamConstant.REQUEST_STATE_CHENGGONG));
	}

	/**
	 * 保存新建审核日志
	 * 
	 * @author zhanghao
	 * @date 2013-02-28 22:32:33
	 * @param busi
	 *            业务id
	 * @param type
	 *            类型
	 * @param state
	 *            状态
	 */
	public static void saveCreateAuditLog(Long busi, String type, String state) {
		String content = state
				.equals(getStateValue(ParamConstant.REQUEST_STATE_XINJIAN)) ? "新建"
				: "质检";
		saveAuditLog(busi, type, content, "", "", state);
	}

	/**
	 * 保存新建审核日志 为app 提供接口
	 * 
	 * @author zhanghao
	 * @date 2013-02-28 22:32:33
	 * @param busi
	 *            业务id
	 * @param type
	 *            类型
	 * @param state
	 *            状态
	 */
	public static void saveCreateAuditLogApp(Long staffId, Long busi,
			String type, String state) {
		String content = state
				.equals(getStateValue(ParamConstant.REQUEST_STATE_XINJIAN)) ? "新建"
				: "质检";
		saveAuditLogApp(staffId, busi, type, "移动终端" + content, "", "", state);
	}

	/**
	 * 是否为新建状态
	 * 
	 * @author zhanghao
	 * @date 2013-03-07 10:29:20
	 * @param state
	 * @return
	 */
	public static boolean isNew(String state) {
		return state.equals(getStateValue(ParamConstant.REQUEST_STATE_XINJIAN));
	}

	/**
	 * 根据变更单类型获取审核日志的类型
	 * 
	 * @author zhanghao
	 * @date 2013-02-25 18:19:15
	 * @param mnType
	 *            变更单类型
	 * @return
	 */
	public static String getAuditType(String mnType) {
		String type = "";
		if (mnType.equals(ParamConstant.BIANGENGFINANCE)) {
			type = ParamConstant.AUDIT_TYPE_MODIFICATION_FINANCE;
		} else if (mnType.equals(ParamConstant.BIANGENGCUSTOMER)) {
			type = ParamConstant.AUDIT_TYPE_MODIFICATION_CUSTOMER;
		} else if (mnType.equals(ParamConstant.BIANGENGCREDIT)) {
			type = ParamConstant.AUDIT_TYPE_MODIFICATION_CREDIT;
		}
		return type;
	}

	/**
	 * 取得状态在参数表里的Value
	 * 
	 * @author zhanghao
	 * @date 2013-01-11 16:26:56
	 * @param val
	 * @return
	 */
	public static String getStateValue(String name) {
		return getParaValue("requestState", name);
	}

	/**
	 * 获取业务的业务状态Value
	 * 
	 * @author zhanghao
	 * @date 2013-6-25 上午11:58:43
	 * @param name
	 * @return
	 */
	public static String getFortuneStateValue(String name) {
		return getParaValue("FortuneState", name);
	}

	/**
	 * 获取参数value
	 * 
	 * @author zhanghao
	 * @date 2013-01-21 22:13:41
	 * @param type
	 *            类型
	 * @param name
	 *            名称
	 * @return 值
	 */
	public static String getParaValue(String type, String name) {
		Parameter parameter = SystemParameterHelper
				.findParameterByPrTypeAndPrName(type, name);
		return parameter == null ? "" : parameter.getPrValue();
	}

	/**
	 * @Title: saveAuditLogWithPaystatus
	 * @Description: 业务留痕，用于有支付状态的业务
	 * @param busi
	 * @param type
	 * @param content
	 * @param state
	 * @param privState
	 * @param nextState
	 * @param privPayState
	 *            操作前支付状态
	 * @param nextPayState
	 *            操作后支付状态
	 * @return void 返回类型
	 * @author Sam.J
	 * @date 2015-5-13 上午10:38:09
	 * @throws
	 */
	public static void saveAuditLogWithPaystatus(Long busi, String type,
			String content, String state, String privState, String nextState,
			String privPayState, String nextPayState) {
		Date date = new Date();
		Staff staff = AuthorityHelper.getStaff();
		// 修改id获取方法，如果没有登录人，则判断位app日志，staff的id 值赋值为 99999999 Sam.J 14.11.18
		long staffid = staff == null ? ParamConstant.APP_AUDIT_STAFFID : staff
				.getId();
		BusiAudit audit = new BusiAudit(busi, type, content, staffid, date,
				state, privState, nextState);
		audit.setAtPrivPaystate(privPayState); // 操作前支付状态
		audit.setAtNextPaystate(nextPayState);// 操作后支付状态
		auditDao.save(audit);
	}
	
	
	/** 
	* @Title: saveRevokeAuditLog 
	* @Description: 撤销保存操作日志 
	* @return void    返回类型 
	* @author Sam.J
	* @date 2015-8-18 上午10:05:04 
	* @throws 
	*/
	public static void saveRevokeAuditLog(Long busi, String type, String content,String oldStatus) {
		saveAuditLog(busi, type, content, "",
				oldStatus,
				getStateValue(ParamConstant.REQUEST_STATE_CEXIAO));
	}
}
