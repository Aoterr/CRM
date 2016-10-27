package com.zendaimoney.crm.index.web;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.orm.ResultVo;

import com.zendaimoney.constant.DesktopConstant;
import com.zendaimoney.constant.ParamConstant;
import com.zendaimoney.crm.sysuser.entity.SysUser;
import com.zendaimoney.crm.sysuser.service.SysUserService;
import com.zendaimoney.sys.desktop.entity.DesktopToolbar;
import com.zendaimoney.sys.desktop.service.DesktopService;
import com.zendaimoney.sys.desktoptoolbox.service.DesktopToolboxService;
import com.zendaimoney.sys.desktoptoolbox.vo.DesktopToolboxVo;
import com.zendaimoney.sys.sysparam.entity.SysParam;
import com.zendaimoney.sys.sysparam.service.SysParamService;
import com.zendaimoney.utils.MD5;

/**
 * 登录控制类
 * @author YM10108
 *
 */
@Controller
@RequestMapping(value = "/index")
public class IndexController {

	@Autowired
	private SysParamService sysParamService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private DesktopService desktopService;
	@Autowired
	private DesktopToolboxService desktopToolboxService;

	@RequestMapping("login")
	public String login(Model model, ServletRequest request) {
		
		return "login";
	}

	@RequestMapping("doLogin")
	@ResponseBody
	public ResultVo doLogin(Model model, ServletRequest request,HttpSession httpSession) {
		String result="";
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		if(username==null||"".equals(username)){
			result="用户名不能为空";
			return new ResultVo(false,result);
		}
		if(password==null||"".equals(password)){
			result="用户密码不能为空";
			return new ResultVo(false,result);
		}
		SysUser sysusers = sysUserService.findBySysUserName(username);
		if(sysusers!=null){
			String pw=MD5.MD5Encode(password);
			String up=sysusers.getUserPassword();
			if(!up.equals(pw)){
				result="密码错误";
				return new ResultVo(false,result);
			}
		}
		sysusers.setLastLoginTime(new Date());
		sysUserService.saveSysUser(sysusers);
		httpSession.setAttribute(ParamConstant.SYS_USER, sysusers);
		return new ResultVo(true);
	}
	
	@RequestMapping("loginSuccess")
	public String loginSuccess(Model model, ServletRequest request,HttpSession httpSession) {
		SysUser sysuser=(SysUser)httpSession.getAttribute(ParamConstant.SYS_USER);
		model.addAttribute(ParamConstant.SYS_USER, sysuser);
		return "index";
	}
	
	@RequestMapping("logout")
	public String logout(Model model, ServletRequest request,HttpSession httpSession) {
		httpSession.removeAttribute(ParamConstant.SYS_USER);
		return "login";
	}

	@RequestMapping(value = { "" })
	public String index(Model model,HttpSession httpSession) {
		SysUser sysuser=(SysUser)httpSession.getAttribute(ParamConstant.SYS_USER);
		model.addAttribute(ParamConstant.SYS_USER, sysuser);
		SysParam sysParam = sysParamService.getSysParam();
		model.addAttribute("sysParam", sysParam);
		return "index";
	}

	@RequestMapping(value = { "left" })
	public String left(Model model,HttpSession httpSession) {
		SysUser sysuser=(SysUser)httpSession.getAttribute(ParamConstant.SYS_USER);
		model.addAttribute(ParamConstant.SYS_USER, sysuser);
		return "index_left";
	}

	@RequestMapping(value = { "right" })
	public String right(Model model,HttpSession httpSession) {
		List<DesktopToolbar> customerDesktopToolbar = desktopService.getDesktopToolbarByType(DesktopConstant.ON,DesktopConstant.BUS_REMIND);//客户类提醒
		//List<DesktopToolboxVo> customerDesktopToolbox = desktopToolboxService.getDesktopToolboxs(AuthorityHelper.getStaff());
		List<DesktopToolboxVo> customerDesktopToolbox =null;
		model.addAttribute("customerDesktopToolbox", customerDesktopToolbox);
		model.addAttribute("busDesktopToolbar", customerDesktopToolbar);
		
		return "index_right";
	}

}
