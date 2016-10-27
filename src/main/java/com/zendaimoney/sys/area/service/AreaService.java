package com.zendaimoney.sys.area.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.sys.area.vo.AreaVo;

@Component
@Transactional(readOnly = true)
public class AreaService {
	@PersistenceContext
	private EntityManager em;

	/**
	 * 取所有国家
	 * 
	 * @return
	 */
	public List getAllNation() {
		String sql = "select t.* from CRM_AREA t where t.AA_LEVEL=:level";
		Query query = em.createNativeQuery(sql);
		query.setParameter("level", 0);
		List<Object[]> list = query.getResultList();
		List<AreaVo> avs = new ArrayList<AreaVo>();
		 for (Object[] object : list) {
			 AreaVo av = new AreaVo();
			 av.setId(object[0]==null?"":object[0].toString());
			 av.setName(object[1]==null?"":object[1].toString());
			 av.setCode(object[2]==null?"":object[2].toString());
			 av.setFatherId(object[3]==null?"":object[3].toString());
			 av.setLevel(object[4]==null?"":object[4].toString());
			 av.setMemo(object[5]==null?"":object[5].toString());
			 avs.add(av);
		}
		return avs;
	}

	/**
	 * 取所有下级
	 * 
	 * @param parent
	 * @return
	 */
	public List getAreaByParent(String parentId) {
		String sql = "select ID,AA_NAME,AA_CODE,AA_FATHER_ID,AA_LEVEL,AA_MEMO from crm_area where ID<>:areaName  and LEVEL =2  start with  ID=:areaName connect by prior id=aa_father_id";
		Query query = em.createNativeQuery(sql);
		query.setParameter("areaName", parentId);
		List<Object[]> list = query.getResultList();
		List<AreaVo> avs = new ArrayList<AreaVo>();
		 for (Object[] object : list) {
			 AreaVo av = new AreaVo();
			 av.setId(object[0]==null?"":object[0].toString());
			 av.setName(object[1]==null?"":object[1].toString());
			 av.setCode(object[2]==null?"":object[2].toString());
			 av.setFatherId(object[3]==null?"":object[3].toString());
			 av.setLevel(object[4]==null?"":object[4].toString());
			 av.setMemo(object[5]==null?"":object[5].toString());
			 avs.add(av);
		}
		return avs;
	}
	
	/**
	 * 取上级
	 * @param parent
	 * @return
	 */
	public AreaVo getAreaBySub(String subId) {
		String sql = "select ID,AA_NAME,AA_CODE,AA_FATHER_ID,AA_LEVEL,AA_MEMO from crm_area where aa_name<>:areaName start with  aa_name=:areaName connect by prior aa_father_id=id";
		Query query = em.createNativeQuery(sql);
		query.setParameter("areaName", subId);
		List list = query.getResultList();
		if(list!=null && list.size()>0){
			 Object[] object = (Object[]) list.get(0);
			 AreaVo av = new AreaVo();
			 av.setId(object[0]==null?"":object[0].toString());
			 av.setName(object[1]==null?"":object[1].toString());
			 av.setCode(object[2]==null?"":object[2].toString());
			 av.setFatherId(object[3]==null?"":object[3].toString());
			 av.setLevel(object[4]==null?"":object[4].toString());
			 av.setMemo(object[5]==null?"":object[5].toString());
			return av;
		}
		return null;
	}
	
	/**
	 * 根据id去地区
	 * @param id
	 * @return
	 */
	public AreaVo getAreaById(String id) {
		String sql = "select ID,AA_NAME,AA_CODE,AA_FATHER_ID,AA_LEVEL,AA_MEMO from crm_area where ID=:areaId";
		Query query = em.createNativeQuery(sql);
		query.setParameter("areaId", id);
		List list = query.getResultList();
		if(list!=null && list.size()>0){
			 Object[] object = (Object[]) list.get(0);
			 AreaVo av = new AreaVo();
			 av.setId(object[0]==null?"":object[0].toString());
			 av.setName(object[1]==null?"":object[1].toString());
			 av.setCode(object[2]==null?"":object[2].toString());
			 av.setFatherId(object[3]==null?"":object[3].toString());
			 av.setLevel(object[4]==null?"":object[4].toString());
			 av.setMemo(object[5]==null?"":object[5].toString());
			return av;
		}
		return null;
	}
	
	public List<AreaVo> getArea() {
		String sql = "select ID,AA_NAME,AA_CODE,AA_FATHER_ID,AA_LEVEL,AA_MEMO from crm_area where AA_LEVEL in (2,3)";
		Query query = em.createNativeQuery(sql);
		List<Object[]> list = query.getResultList();
		List<AreaVo> avs = new ArrayList<AreaVo>();
		 for (Object[] object : list) {
			 AreaVo av = new AreaVo();
			 av.setId(object[0]==null?"":object[0].toString());
			 av.setName(object[1]==null?"":object[1].toString());
			 av.setCode(object[2]==null?"":object[2].toString());
			 av.setFatherId(object[3]==null?"":object[3].toString());
			 av.setLevel(object[4]==null?"":object[4].toString());
			 av.setMemo(object[5]==null?"":object[5].toString());
			 avs.add(av);
		}
		return avs;
	}
	
	@SuppressWarnings("unchecked") 
	public List<AreaVo> getAllArea(){
		String sql = "SELECT ID,AA_NAME,AA_CODE,AA_FATHER_ID,AA_LEVEL,AA_MEMO FROM CRM_AREA";
		Query query = em.createNativeQuery(sql);
		List<Object[]> list = query.getResultList();
		List<AreaVo> avs = new ArrayList<AreaVo>();
		 for (Object[] object : list) {
			 AreaVo av = new AreaVo();
			 av.setId(object[0]==null?"":object[0].toString());
			 av.setName(object[1]==null?"":object[1].toString());
			 av.setCode(object[2]==null?"":object[2].toString());
			 av.setFatherId(object[3]==null?"":object[3].toString());
			 av.setLevel(object[4]==null?"":object[4].toString());
			 av.setMemo(object[5]==null?"":object[5].toString());
			 avs.add(av);
		}
		return avs;
	}

}
