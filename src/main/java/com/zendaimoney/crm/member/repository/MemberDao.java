package com.zendaimoney.crm.member.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zendaimoney.crm.member.entity.Member;

public interface MemberDao extends PagingAndSortingRepository<Member, Long>,
		JpaSpecificationExecutor<Member> {
	/**
	 * 根据身份证号码获取会员信息
	 * 
	 * @param IdCard
	 *            身份证号码
	 * @return 会员信息对象
	 */
	@Query("from Member m where upper(m.mrIdnum)=?")
	Member findMemoberByIdCard(String mrIdnum);

	/**
	 * 根据手机号码获取会员信息
	 * 
	 * @param mobile
	 *            手机号码
	 * @return 会员信息对象
	 */
	@Query("from Member m where m.mrMobile=? ")
	Member findMemberByMobile(String mrMobile);

	/**
	 * 根据手机号码和原始密码获取会员信息
	 * 
	 * @param mobile
	 *            手机号码
	 * @return 会员信息对象
	 */
	@Query("from Member m where m.mrMobile=? and m.mrPassword=?")
	Member findMemberByMobileAndPwd(String mrMobile, String pwd);

	@Query("from Member m where m.id = ?")
	Member findMember(Long id);

	@Query(" from Member c where c.mrName like ?")
	public List<Member> findAllByMrName(String searchValue);

	/**
	 * 根据客户经理id统计会员
	 * 
	 * @param stffId
	 * @return
	 */
	@Query(nativeQuery = true, value = "select sum(countCustomer) from( (select count(*) as countCustomer from APP_MEMBER m where m.mr_manager=:stffId and m.customerid is null) union all (select count(*) as countCustomer from CRM_CUSTOMER c where c.cr_gather=:stffId))")
	public BigDecimal getCustomerCount(@Param("stffId") Long stffId);

	/**
	 * 获取客户信息
	 * 
	 * @param staffId
	 *            客户经理的ID
	 * @param endNo结束数据
	 * @param startNo
	 *            开始数据
	 * @return
	 */
	@Query(nativeQuery = true, value = "SELECT * FROM(SELECT A.*, ROWNUM RN FROM (select id,cr_name  name,cr_idnum idnum,(select t.tl_tel_num from crm_tel t where t.customerid=c.id and t.tl_tel_type='3' and t.tl_valid='1' and rownum=1) as mobile,cr_gather manager,1 as type,(case when c.cr_customer_type='2' then '客户' when c.cr_customer_type='3' then '老客户' when c.cr_customer_type='1' then '储备客户' end ) as state,c.cr_input_date as time from crm_customer c where c.cr_state=1 and  c.cr_gather=:staffId  union all select id,m.mr_name  name,m.mr_idnum idnum, m.mr_mobile as mobile,m.mr_manager manager,2 as type,(case when mr_member_state='2' then '已分配' end  ) as state,m.mr_regdate as time from app_member m where m.customerid is null and m.mr_state=1 and m.mr_manager=:staffId) A WHERE ROWNUM <:endNo order by time desc)WHERE RN >=:startNo")
	public List<Object[]> appCustomerInfo(@Param("staffId") Long staffId,
			@Param("endNo") int endNo, @Param("startNo") int startNo);

	/**
	 * 手机注册的证件和customer表注册的证件是否一致
	 * 
	 * @param name
	 *            姓名 idCardCode :身份证 mobile：手机 type：证件类型。1是身份证
	 * @param feProduct
	 * @return
	 */
	@Query("select count(c.id) from Customer c,Tel t WHERE c.id=t.customerid AND c.crName=? AND upper(c.crIdnum)=? AND t.tlTelNum=? AND c.crIdtype=? ")
	Long idCardCodeIsSame(String name, String idCardCode, String mobile,
			String type);

	// -----------2014.06.18 修改 by Sam.J 增加依照客户id搜索客户内容-------//
	/**
	 * 根据客户id获取客户信息
	 * 
	 * @param customerid
	 *            手机号码
	 * @return 会员信息对象
	 */
	@Query("from Member m where m.customerid=? ")
	Member findMemberByCustomerId(Long customerid);

	// -----------2014.12.01 修改 by Sam.J 判断身份证是否被绑定-------//
	/**
	 * 根据身份证号码获取会员信息
	 * 
	 * @param IdCard
	 *            身份证号码
	 * @return 会员信息对象
	 */
	@Query("from Member m where upper(m.mrIdnum)=? and customerid is not null")
	Member findMemoberByIdCardNew(String mrIdnum);
	
	
	// -----------2016.5.04 修改 by chenjiale thumb2-------//
	/**
	 * 根据手机号码和交易密码获取会员信息
	 * 
	 * @param id
	 *            客户编号
	 * @param mrTradePassword
	 *            交易密码
	 * @return 会员信息对象
	 */
	@Query("from Member m where m.id=? and m.mrTradePassword=?")
	Member findMemberByIdAndTradePwd(Long id, String mrTradePassword);
	
	/**
	 * 根据客户ID和原始密码获取会员信息
	 * 
	 * @param id 客户ID
	 * @param pwd  密码
	 * @return 会员信息对象
	 */
	@Query("from Member m where m.id=? and m.mrPassword=?")
	Member findMemberByIdAndPwd(Long id, String mrPassword);
	
	/**
	 * 根据客户手机号和身份证号码查询客户信息
	 * 
	 * @param id 客户ID
	 * @param pwd  密码
	 * @return 会员信息对象
	 */
	@Query("from Member m where m.mrMobile=? and upper(m.mrIdnum)=?")
	Member findMemberByMobileAndIdnum(String mrMobile, String mrIdnum);

}


