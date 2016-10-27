package com.zendaimoney.crm.sysuser.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.zendaimoney.crm.sysuser.entity.SysUser;

public interface SysUserDao extends
		PagingAndSortingRepository<SysUser, Long>,
		JpaSpecificationExecutor<SysUser> {	

	@Query(" from SysUser s where s.userName = ? and state=1")
	public SysUser findBySysUserName(String userName);
	
}
