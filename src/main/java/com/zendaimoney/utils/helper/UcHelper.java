package com.zendaimoney.utils.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springside.modules.orm.RecordStaffVo;

import com.zendaimoney.uc.rmi.service.IStaffService;
import com.zendaimoney.uc.rmi.vo.DataPermission;
import com.zendaimoney.uc.rmi.vo.Department;
import com.zendaimoney.uc.rmi.vo.Staff;
import com.zendaimoney.uc.rmi.vo.StaffDepartmentLevel;
import com.zendaimoney.uc.rmi.vo.SuperiorAndUpperDepartments;

/**
 * 
 * @author bianxj rmi 客户端facade
 */
public class UcHelper {
//	private static ClientRmiServiceImpl clientRmiServiceImpl;
	
	private static IStaffService  staffService;
	
	
	private static ConcurrentHashMap<Long, String> staffMap=new ConcurrentHashMap<Long, String>(2048);   
		
	static {
//		clientRmiServiceImpl = SpringContextHelper.getBean("clientRmiServiceImpl");
		staffService= SpringContextHelper.getBean("staffService");
	}

	private static final org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog(UcHelper.class);

	public  static void initUcStaffMap () {		
		staffMap.clear();		
	}
	
	/**
	 * 用户名
	 * 
	 * @param staffId
	 * @return
	 */
	public static String getStaffName(Long staffId) {
		if (staffId==null)
			return null;
		String name =null;
		try {
			if(!staffMap.containsKey(staffId))
			{
				name =staffService.getNameByStaffId(staffId);
				staffMap.put(staffId, name);
			}
			else {
				name=staffMap.get(staffId);
			}
		} catch (Exception e) {
			name=staffMap.get(staffId);
		}
		return name;
		//return clientRmiServiceImpl.getNameByStaffId(staffId);
	}

	public static Staff getStaff(Long staffId) {
		return staffService.getStaffByStaffId(staffId);
	}
	
	public static Staff getStaffWithAuth(String sysName) {
		return staffService.getUserDetails(sysName);
	}

	/**
	 * 根据部门id返回部门
	 * 
	 * @param departmentId
	 * @return
	 */
	//UC2.0 改为调用1参方法
	public static Department getDepartName(Long departmentId) {
		return staffService.getDepartmentByDepartmentIdDepCode(departmentId,null);
		//return staffService.getDepartmentByDepartmentIdDepCode(departmentId);   //UC2.0
	}
	
	/**
	 * 返回部门下某一职级所有人
	 * 
	 * @param departmentId
	 * @return
	 */
	public static List<Staff> getStaffsByDepartmentIdAndLevelCode(String departmentCode,String levelCode) {
		//Long levelId = getLevelIdByLevelCode(levelCode);
		//return 	staffService.getStaffsByDepartmentIdLevelId("", departmentId,levelId);
		return 	staffService.getStaffsByDeptCodeLevelCode(departmentCode,levelCode, true);
	}

	/**
	 * 获取所有人员
	 * 
	 * @param staffId
	 * @return
	 */
	public static List<Staff> getAllStaff() {
		return staffService.getStaffs();
	}

	/**
	 * 根据部门code和职级code 获取所有人员
	 * 
	 * @param staffId
	 * @return
	 */
	public static List<org.springside.modules.orm.Staff> getStaffList(String levelCode) {
		List<org.springside.modules.orm.Staff> staffs = new ArrayList<org.springside.modules.orm.Staff>();
		Staff staff = AuthorityHelper.getStaff();
		List<DataPermission> datapermissions = staff.getPartDepartmentDataPermission();
		Long levelId = getLevelIdByLevelCode(levelCode);
		for (DataPermission dataPermission : datapermissions) {
			if (levelId.equals(dataPermission.getStaffLevelId())) {
				Long staffId = dataPermission.getSatffId();
				//String staffName = clientRmiServiceImpl.getNameByStaffId(staffId);
				String staffName = getStaffName(staffId);
				org.springside.modules.orm.Staff staffen = new org.springside.modules.orm.Staff();
				staffen.setId(staffId);
				staffen.setName(staffName);
				staffs.add(staffen);
			}
		}
		return staffs;
	}
	
	/**
	 * 根据人员id和职级code 获取上级部门下非某一职级所有人员
	 * @param levelId 
	 * 
	 * @param staffId
	 * @return
	 */
	/*@Deprecated
	public static List<Staff> getStaffList(Long staffid,String levelCode) {
		Staff staff = staffService.getStaffByStaffId(staffid);
		Staff staffForIp = AuthorityHelper.getStaff();
		Set<StaffDepartmentLevel> staffDepartmentLevels = staff.getStaffDepartmentLevels();
		Long levelId = getLevelIdByLevelCode(levelCode);
		List<Staff> staffs = new ArrayList<Staff>();
		for (StaffDepartmentLevel staffDepartmentLevel : staffDepartmentLevels) {
			Department staffDepartment = staffDepartmentLevel.getDepartment();
			List<Staff> staffList  = staffService.getStaffsByDepartmentIdLevelId(getServerIpByStringServerIp(staffForIp.getServerIP()), staffDepartment.getId(),null);
			for (Staff staff2 : staffList) {
				Set<StaffDepartmentLevel> staffDepartmentLevelSet = staff2.getStaffDepartmentLevels();
				for (StaffDepartmentLevel staffDepartmentLevel2 : staffDepartmentLevelSet) {
					if(staffDepartmentLevel2.getDepartment().getId().equals(staffDepartmentLevel.getDepartment().getId())){
						if(!staffDepartmentLevel2.getStaffLevel().getId().equals(levelId)){
							staffs.add(staff2);
						}
					}
				}
			}
		}

		return staffs;
	}*/

	
	public static String getServerIpByStringServerIp(String StringServerIp) {
		String ip = "";
		if (StringServerIp != null && StringServerIp.length() > 7) {
			String serverIp = StringServerIp.substring(0, StringServerIp.lastIndexOf("/"));
			int lastIndexOf = serverIp.lastIndexOf(":");
			if(lastIndexOf<7){
				ip = StringServerIp.substring(7, serverIp.lastIndexOf("/"));
			}else{
				ip = StringServerIp.substring(7, lastIndexOf);
			}
		}
		return ip;
	}

	/**
	 * 根据职级code返回职级id
	 * 
	 * @param staffId
	 * @return
	 */
	public static Long getLevelIdByLevelCode(String levelCode) {
		return staffService.getStaffLevlelIdByLevelCode(levelCode);
	}

	/**
	 * 所有部门列表 树形
	 * 
	 * @param staffId
	 * @return
	 */
	public static Department getAllDepart() {
		return staffService.getDepartment();
	}
	
	/**
	 * 根据业务类型取得所有部门列表 树形
	 * 
	 * @param staffId
	 * @return
	 */
	public static Department getAllDeptList(String departmentTypeNo) {
		return staffService.getAllDeptList(departmentTypeNo);
	}
	
	 /**
	 * 获取staff下所有部门Id
	 *
	 * @param staffId
	 * @return
	 */
	//UC2.0 改为调用1参方法
	 /*public static List<Long> getAllStaffDepart() {
		 Staff staff = AuthorityHelper.getStaff();
		 List<Long> depIds = new ArrayList<Long>();
		 List<DataPermission> dataPermission = staff.getPartDepartmentDataPermission();
		 for (DataPermission dataPermission2 : dataPermission) {
			Long depId = dataPermission2.getDepartmentId();
			//Department department = staffService.getDepartmentByDepartmentIdDepCode(depId);  //UC2.0
			Department department = staffService.getDepartmentByDepartmentIdDepCode(depId, null);
			depIds.add(depId);
			getSubDepIds(depIds,department);
		}
		 return depIds;
	 }
*/
		private static void getSubDepIds(List<Long> depIds, Department department) {
			List<Department> childs = department.getChildren();
			if(childs != null && childs.size()>0){
				for (Department department2 : childs) {
					depIds.add(department2.getId());
					getSubDepIds(depIds,department2);
				}
			}
		}
	 
	/**
	 * 更改密码
	 * 
	 * @param staffId
	 * @return
	 */
	public static boolean changePassword(String loginName, String newPassword, String oldPassword) {
		return staffService.updateStaffPwdByLoginName(loginName, oldPassword, newPassword);
	}
	/**
	 * 用户名部門名稱
	 * 
	 * @param staffId
	 * @return
	 */
	public static List<String> getStaffDepartmentName(Long staffId) {
		List<Department> depts = staffService.getDepartmentByStaffProp(staffId);
		List<String> departmentList = new ArrayList<String>();
		if (depts.size() > 0) {
			departmentList.add(depts.get(0).getName());
			departmentList.add(depts.get(0).getParent().getName());
		}
		return departmentList;
	}
	
	/**
	 * 获取客户经理的直属主管
	 * @param staffId
	 * @return
	 */
	public static List<Staff> queryImmediateSupervisorByStaffId(Long staffId){
		return staffService.queryImmediateSupervisorByStaffId(staffId);
	}
	
	public static Staff getStaffOnlyDataPermission(Long staffId)
	{
		return staffService.queryPermissionStaffByIdOrLoginName(staffId);
//		return staffService.getStaffByStaffId(staffId);
	}
	
	/**
	 * 根据staffId获取员工的主管（相同部门下的主管）和各个上级部门的信息
	 */
	public static RecordStaffVo getRecordStaff(Long staffId){
		SuperiorAndUpperDepartments  ucStaffDatas = staffService.getSuperiorAndUpperDepartment(staffId);
		if(null == ucStaffDatas){
			throw new RuntimeException("员工工号查找对象，返回为空!");
		}
		RecordStaffVo loggerRecord  = new RecordStaffVo();
		//存放当前员工信息
		Staff staff = ucStaffDatas.getStaff();
		if(null != staff){
			loggerRecord.setStaffId(staff.getId());
			loggerRecord.setStaffName(staff.getName());
		}
		  //上级部门
		List<Department> departList = ucStaffDatas.getUpperDepartment();
		 //获取当前团队对象
		loggerRecord = getAllLoggerInfo(departList, loggerRecord);
		 //获取当前主管
		List<Staff> staffs = ucStaffDatas.getSuperiors();
		if(staffs.size() > 0){
			Staff manager = staffs.get(0);
			loggerRecord.setParentStaffId(manager.getId());
			loggerRecord.setParentStaffName(manager.getName());
		}
         return loggerRecord;
		
	}

	
	/**
	 * UC业务逻辑由CRM这边处理，呵呵
	 * 
	 * 1,大团队；2团队==其它；3大区；4，父级部门==理财网点 --；
	 * @return
	 */
	public static RecordStaffVo getAllLoggerInfo(List<Department> departList, RecordStaffVo loggerRecord){
		 Long topId = null; //top部门ID
		 Long parentId = null; //投资理财部门ID
		 Map<Long, Department> depMap = new HashMap<Long,Department>();   //部门ID，部门
		 
		 //遍历查找部门
		 for(Department department : departList){
			 depMap.put(department.getId(), department);
			 //投资理财管理部门
			 if("05".equals(department.getDepType().getTypeNo())){
				 parentId = department.getId();
			 }
			 
			 Long depFuId = department.getParent().getId();
             //获取顶级部门
			 if(department.getId() == depFuId){
				 topId = department.getId();
			 }
		 }
		 
		 if(null != topId){	 
			 //获取所有投资理财部下面的子集合
			 while(parentId != topId){
				 Department dep = depMap.get(parentId);
				 parentId = dep.getParent().getId();
				 depMap.remove(dep.getId());
			 }
		 }
		 
		 for(Department department : depMap.values()){
			 String typeNo = department.getDepType().getTypeNo();
			//大团队
			 if("07".equals(typeNo)){
				 loggerRecord.setBigTeamId(department.getId());
				 loggerRecord.setBigTeamName(department.getName());
			 }
			 //小组
			 if("08".equals(typeNo)){
				 loggerRecord.setDepId(department.getId());
				 loggerRecord.setDepName(department.getName());
			 }
			 //大区
			 if("06".equals(typeNo)){     
				 loggerRecord.setRegionId(department.getId());
				 loggerRecord.setRegionName(department.getName());
			 }
			 //理财网点
			 if("01".equals(typeNo)){
				 loggerRecord.setParentDepId(department.getId());
				 loggerRecord.setParentDepName(department.getName());
			 }
		}
		
		return loggerRecord;
	} 
	
	public static void main(String[] args){
		UcHelper.getRecordStaff(200000065L);
	}
	
	
	/** 
	* @Title:员工id 与部门名称判断是否存在 
	* @Description: 南京房产项目追加方法，传入员工id和部门的名称，做循环判断在其上级部门里是否存在
	* @param staffId
	* @param depname
	* @return   
	* @throws 
	* @time:2014-8-27 下午02:17:44
	* @author:Sam.J
	*/
	public static boolean getHouseStaff(Long staffId,String depname){
		SuperiorAndUpperDepartments  ucStaffDatas = staffService.getSuperiorAndUpperDepartment(staffId);
		if(null == ucStaffDatas){
			throw new RuntimeException("员工工号查找对象，返回为空!");
		}
		  //上级部门
		List<Department> departList = ucStaffDatas.getUpperDepartment();
		Long topId = null; //top部门ID
		 Long parentId = null; //投资理财部门ID
		 Map<Long, Department> depMap = new HashMap<Long,Department>();   //部门ID，部门
		 
		 //遍历查找部门
		 for(Department department : departList){
			 depMap.put(department.getId(), department);
			 
			 if("投资理财管理部".equals(department.getName())){
				 parentId = department.getId();
			 }
			 
			 Long depFuId = department.getParent().getId();
            //获取顶级部门
			 if(department.getId() == depFuId){
				 topId = department.getId();
			 }
		 }
		 
		 if(null != topId){	 
			 //获取所有投资理财部下面的子集合
			 while(parentId != topId){
				 Department dep = depMap.get(parentId);
				 parentId = dep.getParent().getId();
				 depMap.remove(dep.getId());
			 }
		 }
		for(Department department : depMap.values()){
			 if(depname.equals(department.getName()))return true;
		}
		return false;
	}
	
	
	/**
	 * 获取本部门的指定岗位的人员
	 * @param staffcode
	 * @param levelCode
	 * @param systemName
	 * @return
	 */
	public static List<Staff> getCurrentDepStaffsByStaffCodeLevelCodeSystemName(String staffcode,String levelCode,String systemName){
		return 	staffService.getCurrentDepStaffsByStaffCodeLevelCodeSystemName(staffcode, levelCode, systemName);
	}
	
	//add by chenjiale thmub2 2016/5/11 start
	/**
	 * 根据员工工号获取员工信息（只获取简单的信息，不包含权限数据）
	 * @param staffcode
	 * @return
	 */
	public static Staff getStaffByStaffCode(String staffCode){
		return 	staffService.getStaffByStaffCode(staffCode);
	}

}