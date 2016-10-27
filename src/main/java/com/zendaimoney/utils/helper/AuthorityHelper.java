package com.zendaimoney.utils.helper;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.zendaimoney.constant.Share;
import com.zendaimoney.uc.rmi.vo.DataPermission;
import com.zendaimoney.uc.rmi.vo.Staff;
import com.zendaimoney.utils.CommonUtil;

/**
 * 
 * @author bianxj 获取数据权限
 */
public class AuthorityHelper {
	private static final org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory
			.getLog(AuthorityHelper.class);

	private static EntityManager em;

	public static EntityManager getEm() {
		if (null == em) {
			EntityManagerFactory em1 = SpringContextHelper
					.getBean("entityManagerFactory");
			em = em1.createEntityManager();
		}
		return em;
	}

	/**
	 * 判断用户是不是有这个权限 true 有权限
	 * 
	 * @param permission
	 * @return
	 */
	public static boolean hasAuthorized(String permission) {
		return SecurityHelper.hasPermision(permission);
	}

	/**
	 * 获取当前登陆人员
	 * 
	 * @return
	 */
	public static Staff getStaff() {
		return (Staff) SecurityHelper.getUserDetails();
	}

	/**
	 * 生成三级权限hql条件语句 asName 表 别名 如果没有就置空 departParam 部门 字段 gatherParam 采集人 字段
	 * shareType 业务类型 见Share.java
	 * 
	 * @return
	 */
	public static String createAuthorityHql(Integer shareType, String asName,String gatherParam,Long... staffIdsP) {
		Staff staff=null;
		if (staffIdsP==null||staffIdsP.length<1) {
			staff = getStaff();
		}
		else {
			staff=UcHelper.getStaffOnlyDataPermission(staffIdsP[0]);
		}
		List<DataPermission> departDatas = staff.getAllDepartmentDataPermission();
		List<DataPermission> staffDatas = staff.getPartDepartmentDataPermission();
		List<Long> departIds = new ArrayList<Long>();
		List<Long> staffIds = new ArrayList<Long>();
		List<Long> shareIds = getShareIds(shareType, staff.getId());
		StringBuffer authorityHql = new StringBuffer();
		for (DataPermission dataPermission : departDatas) {
			departIds.add(dataPermission.getDepartmentId());
		}
		for (DataPermission dataPermission : staffDatas) {
			staffIds.add(dataPermission.getSatffId());
		}

		/*
		 * if (!departIds.isEmpty()) {
		 * authorityHql.append((StringUtils.hasText(asName) ? asName + "." : "")
		 * + departParam + " in(" + CommonUtil.listToString(departIds) + ") ");
		 * }
		 * 
		 * if (!departIds.isEmpty() && !staffIds.isEmpty()) {
		 * authorityHql.append(" or "); }
		 */

		if (!staffIds.isEmpty()) {
			authorityHql.append(getSQLInParamsSplit((StringUtils.hasText(asName) ? asName + "." : "")+ gatherParam, 1000, staffIds));
			
		}
		if (!shareIds.isEmpty()) {
			authorityHql.append(" or "
					+ (StringUtils.hasText(asName) ? asName + "." : "")
					+ "id in( " + CommonUtil.listToString(shareIds) + " )) ");
		}
		if (authorityHql.length() == 0) {
			return " and 1=2 ";
		}

		return " and (" + authorityHql.toString() + ")";
	}
	
	public static <T> String getSQLInParamsSplit(String paramName, int length,
			List<T> paramsList) {
		if (length < 1 || paramName == null || paramsList == null
				|| paramsList.size() == 0)
			return null;
		List<List<T>> list = splitInParams(length, paramsList);
		StringBuilder sb = new StringBuilder();
		String temp = list.get(0).toString();
		sb.append(paramName).append(
				" IN (" + temp.subSequence(1, temp.length() - 1) + ") ");
		int size = list.size();
		for (int i = 1; i < size; i++) {
			temp = list.get(i).toString();
			sb.append(" OR " + paramName + " IN ("
					+ temp.subSequence(1, temp.length() - 1) + ") ");
		}
		return sb.toString();
	}
	
	public static <T> List<List<T>> splitInParams(int length, List<T> paramsList) {
		if (length < 1 || paramsList == null || paramsList.size() == 0)
			return null;
		int size = paramsList.size();
		List<List<T>> list = new ArrayList<List<T>>();
		//int d = (int) Math.ceil(size / (length + 0.0));
		int d =((size-1)/ length)+1;
		for (int i = 0; i < d; i++) {
			int fromIndex = length * i;
			int toIndex = Math.min(fromIndex + length, size);
			list.add(paramsList.subList(fromIndex, toIndex));
		}
		return list;
	}

	/**
	 * 生成三级权限 Specification clazz 表 departParam 部门 字段 gatherParam 采集人 字段
	 * shareType 业务类型 见Share.java
	 * 
	 * @return
	 *//*
	public static Predicate createAuthorityPredicate(Integer shareType,
			Class clazz, String gatherParam) {
		Staff staff = getStaff();
		return createAuthorityPredicate(staff, shareType, clazz, gatherParam);
	}

	*//**
	 * 生成三级权限 Specification clazz 表 departParam 部门 字段 gatherParam 采集人 字段
	 * shareType 业务类型 见Share.java
	 * 
	 * @return
	 *//*
	public static Predicate createAuthorityPredicate(Staff staff,
			Integer shareType, Class clazz, String gatherParam) {
		List<DataPermission> departDatas = staff
				.getAllDepartmentDataPermission();
		List<DataPermission> staffDatas = staff
				.getPartDepartmentDataPermission();
		List<Long> departIds = new ArrayList<Long>();
		List<Long> staffIds = new ArrayList<Long>();
		List<Long> shareIds = getShareIds(shareType, staff.getId());
		List<Predicate> predicates = Lists.newArrayList();

		CriteriaBuilder builder = getEm().getCriteriaBuilder();
		CriteriaQuery criteriaQuery = builder.createQuery(clazz);
		Root root = criteriaQuery.from(clazz);

		for (DataPermission dataPermission : departDatas) {
			departIds.add(dataPermission.getDepartmentId());
		}
		for (DataPermission dataPermission : staffDatas) {
			staffIds.add(dataPermission.getSatffId());
		}

		
		 * if (!departIds.isEmpty()) {
		 * predicates.add(root.get(departParam).in(departIds)); }
		 

		staffIds.add(1l);
		if (!staffIds.isEmpty()) {
			predicates.add(root.get(gatherParam).in(staffIds));
		}
		if (!shareIds.isEmpty()) {
			predicates.add(root.get(gatherParam).in(shareIds));
		}

		return builder.or(predicates.toArray(new Predicate[predicates.size()]));
	}
	*/
	
	public static Predicate createAuthorityPredicate(Root<?> root,
			CriteriaQuery<?> query, CriteriaBuilder cb,
			Integer shareType, String gatherParam) {
		Staff staff = getStaff();
		return createAuthorityPredicate(root, query, cb, staff, shareType, gatherParam);
	}
	public static Predicate createAuthorityPredicate(Root<?> root,
			CriteriaQuery<?> query, CriteriaBuilder cb,Staff staff,
			Integer shareType, String gatherParam) {
		List<DataPermission> departDatas = staff
				.getAllDepartmentDataPermission();
		List<DataPermission> staffDatas = staff
				.getPartDepartmentDataPermission();
		List<Long> departIds = new ArrayList<Long>();
		List<Long> staffIds = new ArrayList<Long>();
		List<Long> shareIds = getShareIds(shareType, staff.getId());
		List<Predicate> predicates = Lists.newArrayList();
 

		for (DataPermission dataPermission : departDatas) {
			departIds.add(dataPermission.getDepartmentId());
		}
		for (DataPermission dataPermission : staffDatas) {
			staffIds.add(dataPermission.getSatffId());
		}

		/*
		 * if (!departIds.isEmpty()) {
		 * predicates.add(root.get(departParam).in(departIds)); }
		 */
		if (!staffIds.isEmpty()) {
			predicates.add(cb.or(initPredicates(root, staffIds, gatherParam)));
		}
		if (!shareIds.isEmpty()) {
			predicates.add(cb.or(initPredicates(root, shareIds, gatherParam)));
		}
		
		

		return cb.or(predicates.toArray(new Predicate[predicates.size()]));
	}
	
	
	private static Predicate[] initPredicates(Root<?> root,List<Long> ids,String property){
		List<Predicate> predicates = Lists.newArrayList();
		int i = ids.size()%1000==0 ? ids.size()/1000 : ids.size()/1000+1;
		for (int j = 0; j < i; j++) {
			List<Long> idTemp = ids.subList(j*1000, j*1000+1000<ids.size()?j*1000+1000:ids.size());
			predicates.add(root.get(property).in(idTemp));
		}
		return predicates
				.toArray(new Predicate[predicates.size()]);
	}

	private static List<Long> getShareIds(Integer shareType, Long userId) {
		CriteriaBuilder builder = getEm().getCriteriaBuilder();
		CriteriaQuery<Share> criteriaQuery = builder.createQuery(Share.class);
		Root<Share> root = criteriaQuery.from(Share.class);

		List<Share> shares = getEm().createQuery(
				criteriaQuery.where(builder.and(
						builder.equal(root.get("shareType"), shareType),
						builder.equal(root.get("shareId"), userId))))
				.getResultList();
		List<Long> shareIds = Lists.newArrayList();
		for (Share share : shares) {
			shareIds.add(share.getBusId());
		}
		return shareIds;
	}

}