package com.zendaimoney.crm.sysuser.web;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.orm.PageVo;
import org.springside.modules.orm.ResultVo;
import org.springside.modules.orm.SearchFilter;
import org.springside.modules.orm.SearchFilter.Operator;

import com.zendaimoney.constant.ParamConstant;
import com.zendaimoney.crm.CrudUiController;
import com.zendaimoney.crm.sysuser.entity.SysUser;
import com.zendaimoney.crm.sysuser.service.SysUserService;
import com.zendaimoney.utils.MD5;

@Controller
@RequestMapping(value = "sysuser")
public class SysUserController extends CrudUiController<SysUser> {
	@Autowired
	private SysUserService sysUserService;	
	
	@RequestMapping(value = { "" })
	public String index(Model model, ServletRequest request) {
		return "crm/sysuser/list";
	}
	
	@RequestMapping("list")
	@ResponseBody
	public PageVo list(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "auto") List<String> sort,
			@RequestParam(value = "order", defaultValue = "asc") String order,
			Model model, ServletRequest request) {
		Page<SysUser> sysusers = sysUserService.getSysUser(
				buildSpecification(request, builderSearchFilter("state", "1"), 1, ""),
				buildPageRequest(page, rows, sort, order));
		return new PageVo(sysusers);
	}
	// 构建查询过滤
	public Map<String, SearchFilter> builderSearchFilter(String type,
			String value) {
		TreeMap<String, SearchFilter> searchTreeMap = new TreeMap<String, SearchFilter>();
		if (type.equals(ParamConstant.SYSUSER_STATE)) {
			searchTreeMap.put("EQ_state", new SearchFilter("state",
					Operator.EQ, value));
		}		
		return searchTreeMap;
	}
	
	@RequestMapping("create_sysuser")
	public String create_sysuser(Model model, ServletRequest request) {
		return "crm/sysuser/create_sysuser";
	}
	
	// 保存用户信息
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo save(Model model, HttpServletRequest request)
			throws HibernateOptimisticLockingFailureException, Exception {
		try {
			// 用户信息
			SysUser sysuser = assembleWithAlias(request, SysUser.class,
					"sysuser", "_");
			sysuser.setState(1);
			
			String pw=MD5.MD5Encode(sysuser.getUserPassword());
			sysuser.setUserPassword(pw);
			
			sysUserService.saveSysUser(sysuser);

			return new ResultVo(true);
		} catch (HibernateOptimisticLockingFailureException e) {
			e.printStackTrace();
			return new ResultVo(false, ParamConstant.ERRORA);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultVo(false);
		}
	}
	
	/**
	 * 重置用户密码(888888)
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value ="resetpwd")
	public ResultVo resetpwd(@RequestParam(value = "id") Long id) {
		ResultVo resultVo = null;
		try {
			SysUser sysuser = sysUserService.findOne(id);
			String pw=MD5.MD5Encode("888888");
			sysuser.setUserPassword(pw);
			sysUserService.saveSysUser(sysuser);
			resultVo=new ResultVo(true,"重置成功!");
		} catch (Exception e) {
			e.printStackTrace();
			resultVo = new ResultVo(false, "操作失败,请重新尝试或联系管理员!");
		}
		return resultVo;
	}
	
	/**
	 * 查看用户名是否被注册
	 * @param username
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value ="checkUserNameIsUsed")
	public ResultVo checkUserNameIsUsed(@RequestParam(value = "username") String username) {
		ResultVo resultVo = null;
		try {
			SysUser sysuser = sysUserService.findBySysUserName(username);
			if(sysuser!=null){
				resultVo = new ResultVo(false, "用户名已存在!");
			}else{
				resultVo=new ResultVo(true);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			resultVo = new ResultVo(false, "操作失败,请重新尝试或联系管理员!");
		}
		return resultVo;
	}
	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value ="del")
	public ResultVo del(@RequestParam(value = "id") Long id) {
		ResultVo resultVo = null;
		try {
			SysUser sysuser = sysUserService.findOne(id);
			sysuser.setState(ParamConstant.SYSUSER_STATE_DEL);
			sysUserService.saveSysUser(sysuser);
			resultVo=new ResultVo(true,"删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			resultVo = new ResultVo(false, "操作失败,请重新尝试或联系管理员!");
		}
		return resultVo;
	}
}
