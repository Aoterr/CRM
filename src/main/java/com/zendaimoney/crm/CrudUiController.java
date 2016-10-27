
package com.zendaimoney.crm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springside.modules.orm.SearchFilter;



/**
 * 
 * @author bianxj
 *	UI Controller基类
 */
public class CrudUiController<T> extends BaseController<T>{
	
	/**
	 * 下载文件
	 * 
	 * @param fileName
	 *            文件名
	 * @throws IOException
	 */
	public void downloadFile(HttpServletResponse response, File file) throws IOException {
		response.reset();
		response.setHeader("Content-disposition", "attachment;filename=" + file.getName());
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setContentLength((int) file.length());

		OutputStream outStream = response.getOutputStream();
		FileInputStream inputStream = new FileInputStream(file);

		byte[] buffer = new byte[1024];
		int count = -1; // 每次读取字节数
		while ((count = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, count);
		}
		outStream.flush();
		outStream.close();
		inputStream.close();
		// 下载完文件之后,删除这个文件用于节省硬盘空间
		file.delete();
	}
	
	public Map<String, SearchFilter> builderCondition(ServletRequest request,Map<String, SearchFilter> filtersMap,Integer shareType, String gatherParam) {
		Map<String, SearchFilter> builderCondition = searchConditionParamMap(request);
		return builderCondition;
	}

}
