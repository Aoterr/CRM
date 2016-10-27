package com.zendaimoney.sys.sysparam.web;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.orm.ResultVo;

import com.zendaimoney.crm.CrudUiController;
import com.zendaimoney.sys.sysparam.entity.SysParam;
import com.zendaimoney.sys.sysparam.service.SysParamService;
import com.zendaimoney.utils.DesUtil;

@Controller
@RequestMapping(value = "sys/sysParam")
public class SysParamController extends CrudUiController<SysParam> {

	@Autowired
	private SysParamService sysParamService;
	
	private static Logger logger = LoggerFactory
	.getLogger(SysParamController.class);

	@RequestMapping(value = { "" })
	public String get(Model model) throws Exception{
		SysParam sp = sysParamService.getSysParam();
		logger.info(sp.getCompName()+","+sp.getId());
		//短信密码解密
		String smsPwd=sp.getSmsServerPwd();
		if(StringUtils.isNotBlank(smsPwd))
		sp.setSmsServerPwd(DesUtil.decrypt(smsPwd));
		//邮件密码解密
		String mailPwd=sp.getMailPassword();
		if(StringUtils.isNotBlank(mailPwd))
		sp.setMailPassword(DesUtil.decrypt(mailPwd));
		
		model.addAttribute("parameterTable", sp);
		return "sys/sysParam/sysParam";
	}

	@RequestMapping("set")
	@ResponseBody
	public ResultVo set(SysParam entity) throws Exception{
		logger.info(entity.getCompName()+","+entity.getId());
		
		//短信密码加密
		String smsPwd=entity.getSmsServerPwd();
		if(StringUtils.isNotBlank(smsPwd))
		entity.setSmsServerPwd(DesUtil.encrypt(smsPwd));
		//邮件密码加密
		String mailPwd=entity.getMailPassword();
		if(StringUtils.isNotBlank(mailPwd))
		entity.setMailPassword(DesUtil.encrypt(mailPwd));
		
		sysParamService.saveSysParam(entity);
		return new ResultVo(true);
	}
}
