package com.zendaimoney.crm.file.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springside.modules.orm.ResultVo;

import com.zendaimoney.constant.ParamConstant;
import com.zendaimoney.crm.BaseController;
import com.zendaimoney.crm.file.entity.BusiFile;
import com.zendaimoney.crm.file.service.BusiFileService;
import com.zendaimoney.crm.sysuser.entity.SysUser;
import com.zendaimoney.utils.ConfigurationHelper;
import com.zendaimoney.utils.DateUtil;
import com.zendaimoney.utils.ImageUtils;
import com.zendaimoney.utils.helper.FileHelper;


/**
 * 文件控制器,用于处理文件相关的相关操作
 * 
 * @author zhanghao
 * @create 2012-11-22, 上午11:06:16
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/file")
public class BusiFileController extends BaseController<BusiFile> {

	@Autowired
	private BusiFileService busiFileService;

	private static final String TRANSFROM = "_small.jpg";

	@SuppressWarnings("static-access")
	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResultVo upload(
			@RequestParam(value = "fileName", required = false) String fileName,
			@RequestParam(value = "Filedata") MultipartFile file,HttpSession httpSession) {
		SysUser sysuser=(SysUser)httpSession.getAttribute(ParamConstant.SYS_USER);
		Long sysuserId=sysuser.getId();
		ResultVo resultVo = new ResultVo(false);
		ImageUtils imageUtil = new ImageUtils();
		try {
			if (!file.isEmpty()) {
				BusiFile busiFile = builderBusiFile(file,sysuserId);
				if (FileHelper.upload(file.getInputStream(),
						busiFile.getfPath(), busiFile.getfSaveName())) {

					if (imgeScheme(busiFile.getfSaveName())) {
						imageUtil.transform(
								busiFile.getfPath() + busiFile.getfSaveName(),
								busiFile.getfPath(), busiFile.getfSaveName(),
								200, 200);
					} else if (imgeBmp(busiFile.getfSaveName())) {
						imageUtil.transformBMP(
								busiFile.getfPath() + busiFile.getfSaveName(),
								busiFile.getfPath(), busiFile.getfSaveName(),
								200, 200);
					}
					/*
					 * else if (imgeTiff(busiFile.getfSaveName())){
					 * imageUtil.transformTiff
					 * (busiFile.getfPath()+busiFile.getfSaveName(),
					 * busiFile.getfPath(), busiFile.getfSaveName(), 200, 100);
					 * }
					 */
					busiFileService.saveFile(busiFile);
					resultVo = new ResultVo(true, busiFile.getId().toString());
				}

			}
		} catch (Exception e) {
			resultVo = new ResultVo(false, e.getCause().getMessage());
		}
		return resultVo;
	}

	/**
	 * 删除附件
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResultVo delete(@PathVariable(value = "id") String id) {
		ResultVo resultVo = new ResultVo(false);
		try {
			BusiFile busiFile = busiFileService.findBusiFileById(Long
					.parseLong(id));
			if (busiFile != null) {
				// 从数据库中删除
				busiFileService.deleteFile(busiFile);
				// 从硬盘删除
				FileHelper.delFile(busiFile.getfPath()
						+ busiFile.getfSaveName());

				resultVo = new ResultVo(true);
			}
		} catch (Exception e) {
			resultVo = new ResultVo(false, e.getCause().getMessage());
		}
		return resultVo;
	}

	/**
	 * 下载处理方法
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/download/{id}")
	public void download(@PathVariable(value = "id") String id,
			HttpServletResponse response) throws IOException {
		try {
			// 从数据库中读取文件并设置返回的属性
			BusiFile busiFile = busiFileService.findBusiFileById(Long
					.parseLong(id));
			if (busiFile != null) {
				FileHelper.download(response,
						busiFile.getfPath() + busiFile.getfSaveName(),
						busiFile.getfName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** 
	* @Title: 下载文件压缩包的方法
	* @Description: 下载理财附件文件压缩包的方法
	* @author wb_lyq
	* @date   2015-09-23 10:10:10
	* @throws IOException 
	* @param atType      二级的文件类型，使用,分割的字符串
	* @param forLendNos  出借编号，使用,分割的字符串
	* @param atTypeOne   一级的文件类型
	*/
	@ResponseBody
	@RequestMapping(value = "/downloads")
	public ResultVo downloads(@RequestParam(value = "atType") String atType,@RequestParam(value = "atTypeOne") String atTypeOne,
			@RequestParam(value = "forLendNos") String forLendNos,HttpServletResponse response) throws IOException {
		ResultVo  resultVo=new  ResultVo(false, "下载失败,系统出错,请联系管理员!");
		if(StringUtils.isEmpty(forLendNos)){
			return new ResultVo(false,"请输入选择条件，出借编号必填!");
		}
		String ids = new String();
		List<BusiFile> list = new ArrayList<BusiFile>();
		//   需要对文件的出借编号进行标点符号的过滤和去除空格换行等  只保留 数字字母和中间的英文-
		String regex="[^0-9a-zA-Z&&[^-,]]";   //   正则表达式  如  2001100100100-020-022
		forLendNos=forLendNos.replaceAll(regex, "");
		// 根据 文件的类型 和出借号
		if (StringUtils.isNotEmpty(atTypeOne) && StringUtils.isNotEmpty(atType)&& StringUtils.isNotEmpty(forLendNos)) {
			list.addAll(busiFileService.findFileByFeLendNoAndType(forLendNos,atType, atTypeOne));
		}
		// 根据文件类型
		if (StringUtils.isNotEmpty(atTypeOne) && StringUtils.isNotEmpty(atType)&&StringUtils.isEmpty(forLendNos)) {
			    list.addAll(busiFileService.findFileByAtType(atType, atTypeOne));
		}

		// 只填写出借号
		if (StringUtils.isNotEmpty(forLendNos)&&StringUtils.isEmpty(atTypeOne)&&StringUtils.isEmpty(atType)) {
				list.addAll(busiFileService.findFileByFeLendNo(forLendNos));
		}
		if(list.size()<1){
			return new ResultVo(false,"没有找到所需要的文件存在!");
		}
		for (BusiFile busiFile : list) {
			if(busiFile!=null){
				ids += busiFile.getId();
				//System.out.println(ids);
				ids += ",";
				}
			}
		try {
			// 压缩文件***
			String id1[] = ids.split(",");
			String paths[] = new String[id1.length];
			String saveNames[] = new String[id1.length];
			String realNames[] = new String[id1.length];
			List<String>  nameList=new ArrayList<String>();
			for (int i = 0; i < id1.length; i++) {
				BusiFile busiFile = busiFileService.findBusiFileById(Long.parseLong(id1[i]));
				paths[i] = busiFile.getfPath();
				System.out.println(paths[i]);
				saveNames[i] = busiFile.getfSaveName();
				if(nameList.contains(busiFile.getfName())){
                	String [] name=busiFile.getfName().split("\\.");
					String changeName=name[0]+"("+busiFile.getId()+")."+name[1];
					System.out.println(changeName);
					realNames[i] =changeName;
				  }else{
					realNames[i] = busiFile.getfName();
				}
			    nameList.add(realNames[i]);	 
			}
			String mess = FileHelper.ZipFile(paths, saveNames, realNames,ConfigurationHelper.getString("crm.zip.outencoding"));
			// 下载压缩文件***
			String filePath = null;
			String fileName = null;
			if (!"false".equals(mess)) {
				String[] info = mess.split(",");
				filePath = info[0];
				fileName = info[1];
				FileHelper.download(response, filePath + fileName, fileName);
			}else{
				return new ResultVo(false,"下载失败,系统出错,请联系管理员!");
			}
			// 下载成功后删除压缩文件***
			boolean flag = FileHelper.delFile(filePath + fileName);
			//System.out.println("下载成功后删除文件是否成功：-----------" + flag);

		} catch (Exception e) {
			e.printStackTrace();
			return resultVo;
		}
		return new  ResultVo(true, "下载成功!");
	}

	/**
	 * 构建业务文件对象
	 * 
	 * @author zhanghao
	 * @date 2012-11-27,上午11:09:13
	 * @param file
	 *            提交的上传文件
	 * @return 业务文件对象
	 * @throws UnsupportedEncodingException
	 */
	protected BusiFile builderBusiFile(MultipartFile file,Long sysuserId)
			throws UnsupportedEncodingException {
		Date date = DateUtil.getCurrentDate();
		Long operaterId = sysuserId;
		BusiFile busiFile = new BusiFile();
		busiFile.setfName(URLDecoder.decode(file.getOriginalFilename(), "utf8"));// 文件原始名称
		busiFile.setfType("1");// 文件类型
		busiFile.setfSize(file.getSize());// 文件大小
		busiFile.setfSaveName(FileHelper.builderNewFileName(file
				.getOriginalFilename()));
		;// 组成上传的新文件名
		busiFile.setfPath(FileHelper.builderUploadPath(date,sysuserId));// 上传路径
		busiFile.setfCratetime(date);// 创建时间
		busiFile.setfCreateid(operaterId);// 创建人
		busiFile.setfModifytime(date);// 最后修改时间
		busiFile.setfModifyid(operaterId);// 最后修改人
		busiFile.setfPigeonholeTime(date);// 归档时间
		busiFile.setfPigeonholeId(operaterId);// 归档人
		busiFile.setfMemo("");// 备注
		busiFile.setfState("1");// 状态
		return busiFile;
	}

	// 根据附件id取附件里面的文件
	@ResponseBody
	@RequestMapping(value = "/get/files")
	public List<BusiFile> getAttachmentFiles(
			@RequestParam(value = "id") Long id, Model model) {
		return busiFileService.findAllByAttachmentId(id);
	}

	/**
	 * 判断图像格式
	 * 
	 * @author Jinghr
	 * @date 2013-9-25 16:18:30
	 */
	public boolean imgeScheme(String filename) {
		String str = filename.substring(filename.lastIndexOf(".") + 1);
		Pattern pat = Pattern
				.compile("[Gg][Ii][Ff]|[Jj][Pp][Gg]|[Jj][Pp][Ee][Gg]|[Pp][Nn][Gg]");
		Matcher matcher = pat.matcher(str);
		if (matcher.matches())
			return true;
		else
			return false;
	}

	/**
	 * 判断图像格式
	 * 
	 * @author Jinghr
	 * @date 2013-9-25 16:18:30
	 */
	public boolean imgeTiff(String filename) {
		String str = filename.substring(filename.lastIndexOf(".") + 1);
		Pattern pat = Pattern.compile("^tif[f]$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pat.matcher(str);
		if (matcher.matches())
			return true;
		else
			return false;
	}

	/**
	 * 判断图像格式
	 * 
	 * @author Jinghr
	 * @date 2013-9-25 16:18:30
	 */
	public boolean imgeBmp(String filename) {
		String str = filename.substring(filename.lastIndexOf(".") + 1);
		Pattern pat = Pattern.compile("^bmp$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pat.matcher(str);
		if (matcher.matches())
			return true;
		else
			return false;
	}

	/**
	 * 删除附件文件
	 * 
	 * @author Yuan Changchun
	 * @date 2013-9-29 下午04:47:45
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete/one", method = RequestMethod.POST)
	public ResultVo deleteOne(@RequestParam(value = "id") Long id) {
		ResultVo resultVo = new ResultVo(false);
		try {
			BusiFile busiFile = busiFileService.findBusiFileById(id);
			if (busiFile != null) {
				// 从数据库中删除
				// busiFileService.deleteFile(busiFile);
				// 从硬盘删除
				// FileHelper.delFile(busiFile.getfPath() +
				// busiFile.getfSaveName());
				busiFileService.updateFileFState(busiFile, "0");
				resultVo = new ResultVo(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultVo = new ResultVo(false, e.getCause().getMessage());
		}
		return resultVo;
	}
	
	
	/** 
	* @Title: 下载文件压缩包的方法
	* @Description: 下载理财附件文件压缩包的方法
	* @author CJ
	* @date   2015-12-10 10:10:10
	* @throws IOException 
	* @param atType      二级的文件类型，使用,分割的字符串
	* @param forLendNos  出借编号，使用,分割的字符串
	* @param atTypeOne   一级的文件类型
	*/
	@ResponseBody
	@RequestMapping(value = "/downloads/fund")
	public ResultVo downloadsForFund(@RequestParam(value = "atType") String atType,@RequestParam(value = "atTypeOne") String atTypeOne,
			@RequestParam(value = "forLendNos") String forLendNos,HttpServletResponse response) throws IOException {
		ResultVo  resultVo=new  ResultVo(false, "下载失败,系统出错,请联系管理员!");
		if(StringUtils.isEmpty(forLendNos)){
			return new ResultVo(false,"请输入选择条件，订单编号必填!");
		}
		String ids = new String();
		List<BusiFile> list = new ArrayList<BusiFile>();
		//   需要对文件的出借编号进行标点符号的过滤和去除空格换行等  只保留 数字字母和中间的英文-
		String regex="[^0-9a-zA-Z&&[^-,]]";   //   正则表达式  如  2001100100100-020-022
		forLendNos=forLendNos.replaceAll(regex, "");
		// 根据 文件的类型 和出借号
		if (StringUtils.isNotEmpty(atTypeOne) && StringUtils.isNotEmpty(atType)&& StringUtils.isNotEmpty(forLendNos)) {
			list.addAll(busiFileService.findFileByFeLendNoAndTypeForFund(forLendNos,atType, atTypeOne));
		}
		// 根据文件类型
		if (StringUtils.isNotEmpty(atTypeOne) && StringUtils.isNotEmpty(atType)&&StringUtils.isEmpty(forLendNos)) {
			    list.addAll(busiFileService.findFileByAtTypeForFund(atType, atTypeOne));
		}

		// 只填写出借号
		if (StringUtils.isNotEmpty(forLendNos)&&StringUtils.isEmpty(atTypeOne)&&StringUtils.isEmpty(atType)) {
				list.addAll(busiFileService.findFileByFeLendNoForFund(forLendNos));
		}
		if(list.size()<1){
			return new ResultVo(false,"没有找到所需要的文件存在!");
		}
		for (BusiFile busiFile : list) {
			if(busiFile!=null){
				ids += busiFile.getId();
				//System.out.println(ids);
				ids += ",";
				}
			}
		try {
			// 压缩文件***
			String id1[] = ids.split(",");
			String paths[] = new String[id1.length];
			String saveNames[] = new String[id1.length];
			String realNames[] = new String[id1.length];
			List<String>  nameList=new ArrayList<String>();
			for (int i = 0; i < id1.length; i++) {
				BusiFile busiFile = busiFileService.findBusiFileById(Long.parseLong(id1[i]));
				paths[i] = busiFile.getfPath();
				System.out.println(paths[i]);
				saveNames[i] = busiFile.getfSaveName();
				if(nameList.contains(busiFile.getfName())){
                	String [] name=busiFile.getfName().split("\\.");
					String changeName=name[0]+"("+busiFile.getId()+")."+name[1];
					System.out.println(changeName);
					realNames[i] =changeName;
				  }else{
					realNames[i] = busiFile.getfName();
				}
			    nameList.add(realNames[i]);	 
			}
			String mess = FileHelper.ZipFile(paths, saveNames, realNames,ConfigurationHelper.getString("crm.zip.outencoding"));
			// 下载压缩文件***
			String filePath = null;
			String fileName = null;
			if (!"false".equals(mess)) {
				String[] info = mess.split(",");
				filePath = info[0];
				fileName = info[1];
				FileHelper.download(response, filePath + fileName, fileName);
			}else{
				return new ResultVo(false,"下载失败,系统出错,请联系管理员!");
			}
			// 下载成功后删除压缩文件***
			boolean flag = FileHelper.delFile(filePath + fileName);
			//System.out.println("下载成功后删除文件是否成功：-----------" + flag);

		} catch (Exception e) {
			e.printStackTrace();
			return resultVo;
		}
		return new  ResultVo(true, "下载成功!");
	}

}
