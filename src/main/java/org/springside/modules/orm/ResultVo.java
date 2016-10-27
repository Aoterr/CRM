package org.springside.modules.orm;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 *	返回结果值对象
 * 
 */
public class ResultVo {
	
	public Map<String, Object> result = new HashMap<String, Object>();
	
	public ResultVo(boolean isSuccess,Object msg) {
		result.put("error", isSuccess);
		result.put("msg", msg);
	}
	public ResultVo(String status,String respDesc,Object id) {
		result.put("status", status);
		result.put("respDesc", respDesc);
		result.put("id", id);
	}
	public ResultVo(boolean isSuccess) {
		if(isSuccess)renderResultSuccess();
		else renderResultError();
	}
	
	public ResultVo(boolean isSuccess,String alertMsg) {
		if(isSuccess)renderResultSuccess(alertMsg);
		else renderResultError(alertMsg);
	}
	
	public ResultVo(boolean isSuccess,String alertMsg,String redirectUrl) {
		if(isSuccess)renderResultSuccess(alertMsg,redirectUrl);
	}

	public void renderResultSuccess() {
		result.put("success", true);
		result.put("reload", true);
	}
	
	
	public void renderResultError() {
		result.put("error", true);
		result.put("errMsg", "操作过程出现错误！");
	}

	public void renderResultError(String errMsg) {
		result.put("error", true);
		result.put("errMsg", errMsg);
	}

	

	public void renderResultSuccess(String sucMsg) {
		result.put("success", true);
		result.put("reload", false);
		result.put("sucMsg", sucMsg);
	}


	public void renderResultSuccess(String sucMsg, String url) {
		result.put("success", true);
		result.put("reload", "redirect");
		result.put("url", url);
		result.put("sucMsg", sucMsg);
	}
}
