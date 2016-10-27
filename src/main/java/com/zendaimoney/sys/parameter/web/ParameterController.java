package com.zendaimoney.sys.parameter.web;

import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.orm.PageVo;
import org.springside.modules.orm.ResultVo;

import com.zendaimoney.crm.CrudUiController;
import com.zendaimoney.sys.parameter.entity.Parameter;
import com.zendaimoney.sys.parameter.service.ParameterService;
import com.zendaimoney.utils.helper.AreaHelper;
import com.zendaimoney.utils.helper.SystemParameterHelper;

/**
 * 
 * @author hezc
 * 系统设置
 */
@Controller
@RequestMapping(value = "/sys/parameter")
public class ParameterController extends CrudUiController<Parameter>{
	
	@Autowired
	private ParameterService parameterService;

	@RequestMapping(value = { "" })
	public String index(Model model) {
		return "sys/parameter/list";
	}
	 
	@RequestMapping("parameterById")
	@ResponseBody
	public Parameter getParameterById(Long id) {
		return parameterService.getParameter(id);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public PageVo list(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "auto") String sort,
			@RequestParam(value = "order", defaultValue = "asc") String order,Model model, ServletRequest request) {
		Page<Parameter> Parameters = parameterService.getParameter(buildSpecification(request,null),buildPageRequest(page, rows, sort, order));
		return new PageVo(Parameters);
	}
	
	@RequestMapping("searchList")
	@ResponseBody
	public PageVo searchList(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "auto") String sort,
			@RequestParam(value = "order", defaultValue = "asc") String order,Model model, ServletRequest request) {
		Page<Parameter> Parameters = parameterService.findAllParameterlist(builderCondition(request, null,null,null), buildPageRequest(page, rows, sort, order),buildSpecification(request,null));
		return new PageVo(Parameters);
	}
	
	//查询界面
	@RequestMapping(value = { "query" })
	public String parameter_query(Model model) {
		return "sys/parameter/query";
	}

	//保存修改
	@RequestMapping(value = "save",method = RequestMethod.POST)
	@ResponseBody
	public ResultVo save(Parameter parameter,@RequestParam(value = "updateId") long updateId) throws Exception{
		try{
		parameterService.saveN(parameter,updateId);
		return new ResultVo(true);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultVo(false);
		}
	}
	//取得参数类型列表
	@RequestMapping(value = "findAllPrTypename")
	@ResponseBody
	public List<Parameter> findAllPrTypename() {
		List<Parameter> parameters = parameterService.getAllParameterDisTypeName();
		return parameters;
	}
	
	//取得格式化后的参数类型列表
	@RequestMapping(value = "formatAllPrTypename")
	@ResponseBody
	public List<Parameter> formatAllPrTypename() {
		List<Parameter> parameters = parameterService.getAllParameterTypeName();
		return parameters;
	}
	
	
	//取得参数类型列表
	/*@RequestMapping(value = "loadParameterData")
	@ResponseBody
	public String loadParameterData() {
		try{
			SystemParameterHelper.reloadParameterData();
			AreaHelper.reloadAreaData();
			return "true";
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "false";
		}
	}*/
	
	
	//删除
	@RequestMapping(value = "delete",method = RequestMethod.POST)
	@ResponseBody
	public ResultVo delete(@RequestParam(value = "id") Long[] id) {
		try {
			if(parameterService.deleteParameter(id)){
				return new ResultVo(true);
			}else{
				return new ResultVo(false,"请先删除所有关联的二级参数值");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultVo(false);
		}
	}
}
