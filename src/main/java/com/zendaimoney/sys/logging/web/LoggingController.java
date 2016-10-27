package com.zendaimoney.sys.logging.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.orm.PageVo;
import org.springside.modules.orm.ResultVo;

import com.zendaimoney.crm.CrudUiController;
import com.zendaimoney.sys.logging.entity.Logging;
import com.zendaimoney.sys.logging.service.LoggingService;
import com.zendaimoney.utils.StringToDateConverter;


/**
 * 
 * @author bianxj 日志管理
 */
@Controller
@RequestMapping(value = "/sys/logging")
public class LoggingController extends CrudUiController<Logging> {

	@Autowired
	private LoggingService loggingService;

	@RequestMapping(value = { "" })
	public String index(Model model) {
		return "sys/logging/logging";
	}
	
 	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
	/*注册自定义的属性编辑器
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	CustomDateEditor dateEditor = new CustomDateEditor(df, true);
	 表示如果命令对象有Date类型的属性，将使用该属性编辑器进行类型转换*/	
 		binder.registerCustomEditor(Date.class, new StringToDateConverter());
	} 
 
	
	@RequestMapping("list")
	@ResponseBody
	public PageVo list(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "auto") String sort,
			@RequestParam(value = "order", defaultValue = "asc") String order,
			Model model, ServletRequest request) {
		Page<Logging> Loggings = loggingService.getLogging(buildSpecification(request,null),buildPageRequest(page, rows, sort, order));
		return new PageVo(Loggings);
	}
	
	@RequestMapping(value = { "query" })
	public String logging_query(Model model) {
		return "sys/logging/logging_query";
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo delete(@RequestParam(value = "id") Long[] id) {
		try {
			loggingService.deleteLogging(id);
			return new ResultVo(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultVo(false);
		}

	}

	@RequestMapping(value = { "logExcel" })
	public void logExcel(HttpServletResponse response) {
		try {

			File fileWrite = new File("logExcel"+new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date())+".xls");
			fileWrite.createNewFile();
			OutputStream os = new FileOutputStream(fileWrite);			// 第一步，创建一个webbook，对应一个Excel文件
			HSSFWorkbook wb = new HSSFWorkbook();						// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet("系统日志");				// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			HSSFRow row = sheet.createRow((int) 0);						// 第四步，创建单元格，并设置值表头 设置表头居中
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);				// 创建一个居中格式
			HSSFCell cell = null;
			String[] title = {"序号","日志时间","IP","用户","来源","类型","描述信息"};
			for (int i = 0;i<title.length;i++) {
				cellset(cell,i,title[i],style,row);
			}
			List<Logging> list = loggingService.getAllLoggingOrderById();// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow((int) i + 1);
				Logging stu = (Logging) list.get(i);// 第四步，创建单元格，并设置值
				row.createCell((short) 0).setCellValue(stu.getId());
				cell = row.createCell((short) 1);
				cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(stu.getLgLogtime()));
				row.createCell((short) 2).setCellValue(stu.getLgIp());
				row.createCell((short) 3).setCellValue(stu.getLgOperatorname());
				row.createCell((short) 4).setCellValue(stu.getLgSource().toString());
				row.createCell((short) 5).setCellValue(stu.getLgLogtype().toString());
				row.createCell((short) 6).setCellValue(stu.getLgContent());

			}
			wb.write(os);
			os.close();
			downloadFile(response, fileWrite);// 第六步，将文件存到指定位置
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * excel 列名
	 * 
	 * @param cell表头单元格  row表头居中格式
	 * @throws IOException
	 */
	private void cellset(HSSFCell cell,int i,String name,HSSFCellStyle style,HSSFRow row){
		cell = row.createCell((short) i);
		cell.setCellValue(name);
		cell.setCellStyle(style);
	}
	
	
	public static void main(String[] args) {
		Date date = new Date("Tue Nov 20 16:52:03 CST 2012");
		System.out.println(date);
	}


}
