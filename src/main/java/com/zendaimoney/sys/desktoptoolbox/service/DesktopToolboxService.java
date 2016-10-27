package com.zendaimoney.sys.desktoptoolbox.service;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.zendaimoney.sys.desktoptoolbox.entity.DesktopToolbox;
import com.zendaimoney.sys.desktoptoolbox.vo.DesktopToolboxVo;
import com.zendaimoney.uc.rmi.vo.Function;
import com.zendaimoney.uc.rmi.vo.Staff;

@Component
@Transactional(readOnly = true)
public class DesktopToolboxService {
	@PersistenceContext
	private EntityManager em;

	
	public List<DesktopToolboxVo> getDesktopToolboxs(Staff staff){
		Query query = em.createQuery("from DesktopToolbox where staffId =:staffId");
		query.setParameter("staffId", staff.getId());
		List<DesktopToolbox> list = query.getResultList();
		List<DesktopToolboxVo> dtvo = Lists.newArrayList();
		for (DesktopToolbox desktopToolbox : list) {
			String[] deskToolboxs = desktopToolbox.getMenuCode()==null?new String[]{}:desktopToolbox.getMenuCode().split(",");
			for (String boxcode : deskToolboxs) {
				for (Function function : staff.getPageFunction()) {
					if(function.getFunctionCode().equalsIgnoreCase(boxcode)){
						DesktopToolboxVo  bv = new DesktopToolboxVo();
						bv.setBoxTitle(function.getFunctionName());
						bv.setMenuCode(function.getFunctionCode());
						bv.setMenuUrl(function.getUrl());
						dtvo.add(bv);
						continue;
					}
				}
				
			}
		}
//		Collections.reverse(dtvo);
		return dtvo;
	}
 
	public String[] getDesktopToolboxsString(Staff staff){
		Query query = em.createQuery("from DesktopToolbox where staffId =:staffId");
		query.setParameter("staffId", staff.getId());
		List<DesktopToolbox> list = query.getResultList();
		if(!list.isEmpty()){			
			return list.get(0).getMenuCode()==null?new String[]{}:list.get(0).getMenuCode().split(",");
		}
		return new String[]{};
	}
	
	
	public void saveDesktopToolboxsString(Staff staff,String mcs){
		Query query = em.createQuery("delete from DesktopToolbox where staffId =:staffId");
		query.setParameter("staffId", staff.getId());
		query.executeUpdate();
		
		DesktopToolbox dtb = new DesktopToolbox();
		dtb.setStaffId(staff.getId());
		dtb.setMenuCode(mcs);
		em.persist(dtb);
		em.flush();
		
		
	}
}
