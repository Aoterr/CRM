package com.zendaimoney.crm.sysuser.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.crm.BaseService;
import com.zendaimoney.crm.sysuser.dao.SysUserDao;
import com.zendaimoney.crm.sysuser.entity.SysUser;


@Service
@Transactional
public class SysUserService extends BaseService<SysUser> {

	@Autowired
	private SysUserDao sysUserDao;

	/**
	 * 保存用户
	 * @param sysuser
	 * @return
	 */
	public SysUser findOne(Long id) {
		return sysUserDao.findOne(id);
	}
	
	/**
	 * 保存用户
	 * @param sysuser
	 * @return
	 */
	public SysUser saveSysUser(SysUser sysuser) {
		return sysUserDao.save(sysuser);
	}
	/**
	 * 根据用户名查找用户
	 * @param userName
	 * @return
	 */
	public SysUser findBySysUserName(String userName) {
		return sysUserDao.findBySysUserName(userName);
	}
	/**
	 * 获取所有用户
	 * @return
	 */
	public List<SysUser> getAllSysUser() {
		return (List<SysUser>) sysUserDao.findAll();
	}
	/**
	 * 获取分页用户
	 * @param pageRequest
	 * @return
	 */
	public Page<SysUser> getSysUser(PageRequest pageRequest) {
		return sysUserDao.findAll(pageRequest);
	}
	/**
	 * 根据查询条件，获取分页用户
	 * @param buildSpecification
	 * @param buildPageRequest
	 * @return
	 */
	public Page<SysUser> getSysUser(Specification<SysUser> buildSpecification,PageRequest buildPageRequest) {
		return sysUserDao.findAll(buildSpecification, buildPageRequest);
	}
	/**
	 * 根据ID删除用户
	 * @param id
	 */
	public void delSysUser(Long id) {
		sysUserDao.delete(id); 
	}
	
}
