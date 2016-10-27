package com.zendaimoney.sys.sysparam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.sys.sysparam.entity.SysParam;
import com.zendaimoney.sys.sysparam.repository.SysParamDao;

@Component
@Transactional
public class SysParamService {
	@Autowired
	private SysParamDao sysParamDao;

	public SysParam getSysParam() {
		List<SysParam> list = (List<SysParam>) sysParamDao.findAll();
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return new SysParam();
	}

	public void saveSysParam(SysParam entity) {
		if (entity.getId() != null) {
			SysParam sysParam = sysParamDao.findOne(entity.getId());
			sysParam.setCompName(entity.getCompName());
			sysParam.setLogoPath(entity.getLogoPath());
			
			if(!"logo_mx".equals(entity.getLogoPath())){
				sysParam.setMailHost(entity.getMailHost());
				sysParam.setMailPassword(entity.getMailPassword());
				sysParam.setMailUsername(entity.getMailUsername());
				
				sysParam.setSmsServerIp(entity.getSmsServerIp());
				sysParam.setSmsServerName(entity.getSmsServerName());
				sysParam.setSmsServerPort(entity.getSmsServerPort());
				sysParam.setSmsServerPwd(entity.getSmsServerPwd());
			}
			
			sysParamDao.save(sysParam);
		} else {
			sysParamDao.save(entity);
		}
	}
}
