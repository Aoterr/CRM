package com.zendaimoney.sys.parameter.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.SearchFilter;

import com.zendaimoney.crm.BaseService;
import com.zendaimoney.sys.parameter.entity.Parameter;
import com.zendaimoney.sys.parameter.repository.ParameterDao;

@Component
@Transactional(readOnly = true)
public class ParameterService  extends BaseService<Parameter> {
	
	@Autowired
	private ParameterDao parameterDao;
	
	public Parameter getParameter(Long id) {
		return parameterDao.findOne(id);
	}
	
	@Transactional(readOnly = false)
	public void saveParameter(Parameter entity){
		parameterDao.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void deleteParameter(Long id) {
		parameterDao.delete(id);
	}
	@Transactional(readOnly = false)
	public boolean deleteParameter(Long[] ids) {
		boolean del = true;
		for (Long id : ids) {
			if(delCapable(id)){
				parameterDao.delete(id);
			}else{
				del = false;
			}
		} 
		return del;
	}
	
	public boolean delCapable(Long id){
		Long count = parameterDao.delCount(id.toString());
		if(count>1){
			return false;
		}else{
			return true;
		}
	}
	
	public List<Parameter> getAllParameter() {
		return (List<Parameter>) parameterDao.findAll();
	}
	
	public List<Parameter> getAllParameterOrderId() {
		return (List<Parameter>) parameterDao.findAllOrderId();
	}


	public Page<Parameter> getParameter(PageRequest pageRequest ) {
		return parameterDao.findAll(pageRequest);
	}

	public List<Parameter> getAllParameterDisTypeName() {
		// TODO Auto-generated method stub
		return parameterDao.getAllParameterDisTypeName() ;
	}
	
	/**
	 * author:Jinghr 2013-8-6 15:19:54
	 * 
	 * 返回格式化后的参数列表
	 * */
	public List<Parameter> getAllParameterTypeName(){
		  // 一级附件类型列表
		List<Parameter> atOneList = parameterDao.findParameterByPrTypeAndprTypename("attachmentType", "附件类型","1");
		List<Parameter> AllParameterList  = parameterDao.getAllParameterTypeName() ;
		for(int i=0;i<AllParameterList.size();i++){
			if(isNumber(AllParameterList.get(i).getPrTypename())
					&& AllParameterList.get(i).getPrType().equals("attachmentType")){
				int indexAtOne = 0; //一级菜单下标
				long prTypename = Long.parseLong(AllParameterList.get(i).getPrTypename());
			    while(prTypename != atOneList.get(indexAtOne).getId()){
						indexAtOne++;
						if(indexAtOne==atOneList.size()) 
							break;
			    }
			    if(indexAtOne != atOneList.size()){			   
			    	AllParameterList.get(i).setPrTypename(atOneList.get(indexAtOne).getPrName());
			    }
			}
		}
       
		return AllParameterList;
	}
	
	/*
	 * 参数保存修改
	 * jinghr,2013-9-17 10:46:54
	 * */
	@Transactional(readOnly = false)
	public void saveN(Parameter parameter,long updateId) {
		if(parameter.getId()==null){
			parameter.setPrTypename(parameterDao.getParameter(updateId).getPrTypename());
			if("cdInterestRate".equals(parameter.getPrType())){
				String param_input=parameter.getPrName();
				if(param_input.endsWith("%")){
					parameter.setPrValue(Float.parseFloat(param_input.substring(0,param_input.length()-1))/100+"");
				}else{
					parameter.setPrValue(param_input);
					parameter.setPrName(Float.parseFloat(param_input.substring(0,param_input.length()-1))*100+"%");
				}
				parameter.setPrValue(parameter.getPrName());
				parameter.setPrIsedit("1");
				parameter.setPrState("1");
				parameterDao.save(parameter); 
			}else{
				//String prValue=parameterDao.findMaxPrValue(parameter.getPrType());
				//int i= Integer.parseInt(prValue);
				Integer maxPrvalue = parameterDao.MaxPrvalue(parameter.getPrTypename(), parameter.getPrType());  //获取新增参数所在参数一类中最大值
				String maxPrvalueStr = maxPrvalue!=null?(maxPrvalue+1)+"":"1";
				parameter.setPrValue(maxPrvalueStr);
				parameter.setPrIsedit("1");
				parameter.setPrState("1");
				Parameter hasParam = parameterDao.save(parameter); //新增附件类型成功，默认新增改记录一条子数据（二级）
				if(hasParam.getPrType().equals("attachmentType") && hasParam.getPrTypename().equals("附件类型")){
					Parameter addHasParam = new Parameter();
					addHasParam.setPrTypename(hasParam.getId().toString());
					addHasParam.setPrType(hasParam.getPrType());				
					addHasParam.setPrValue("1");
					addHasParam.setPrName(hasParam.getPrName());
					addHasParam.setPrIsedit("1");
					addHasParam.setPrState("1");
					parameterDao.save(addHasParam);
				}
			}		
		}else{
			Parameter entity = this.getParameter(parameter.getId());
			entity.setPrTypename(parameter.getPrTypename());
			entity.setPrType(parameter.getPrType());
			entity.setPrName(parameter.getPrName());
			entity.setPrState(parameter.getPrState());
			this.saveParameter(entity);
		}
		
	}

	public Page<Parameter> getParameter(Specification<Parameter> spec,PageRequest pageRequest ) {
		return parameterDao.findAll(spec,pageRequest);
	}
	
	public Page<Parameter> findAllParameterlist(Map<String, SearchFilter> conditionMap, PageRequest pageRequest,Specification<Parameter> spec){
		StringBuffer baseSql = new StringBuffer();
		String likeValue = "";
		String searchSql = "";
		String likeName = "";
		for (Entry<String, SearchFilter> condition : conditionMap.entrySet()) {
			likeValue  = "%" + condition.getValue().value + "%" ;
			likeName = "" + condition.getValue().fieldName + "";
		}
		List<Long> idList = parameterDao.findId(likeValue);
		if("".equals(likeValue) || null==likeValue || idList.size()<1){			
			return parameterDao.findAll(spec,pageRequest);
		//客户查询
		}else if(likeName.equals("prName")){
			searchSql =
					baseSql.		
						append("select P from Parameter P").
						append(" where P.prName like '").
						append(likeValue).
						append("'").toString();
		}else{			
			baseSql.		
			append("select P from Parameter P").
			append(" where P.prTypename like '").
			append(likeValue).
			append("' or P.prTypename in  (");
			for(Long id : idList){
				baseSql.append("'"+ id +"',");	
			}
			 searchSql = baseSql.substring(0, baseSql.length()-1) + ")";
			
		}
		//查询总记录数
		long total = getTotal(null,new StringBuffer(searchSql));
		Page<Parameter> page = new PageImpl<Parameter>(getContents(searchSql, pageRequest), pageRequest, total);
		return page;
	}
	
	@SuppressWarnings("unchecked")
	public List<Parameter> getContents(String baseSql, PageRequest page) {
		
		Query query = em.createQuery(baseSql.toString());
		//分页参数
		query.setFirstResult(page.getPageNumber() * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		return query.getResultList();
	}
	
	/**
	 * 根据prType以及prValue查找参数
	 * @param prType
	 * @param prValue
	 * @return
	 */
    public Parameter findByPrTypeAndPrValue(String prType,String prValue){
		return parameterDao.findByPrTypeAndPrValue(prType, prValue);    	
    }
}




	 
	