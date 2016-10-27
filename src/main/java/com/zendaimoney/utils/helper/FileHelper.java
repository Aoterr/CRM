package com.zendaimoney.utils.helper;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zendaimoney.utils.Identities;
import com.zendaimoney.utils.PropertiesLoader;


/**
 * 处理文件的帮助类
 * 
 * @author zhanghao
 * @create 2012-11-23, 上午11:55:14
 * @version 1.0
 */
public class FileHelper {
	
	private static Logger logger = LoggerFactory
	.getLogger(FileHelper.class);
	
	/**
	 * 上传文件
	 * @author zhanghao
	 * @date 2012-11-23,下午03:35:25
	 * @param fileInputStream 需要上传文件的输入流
	 * @param uploadPath 上传路径
	 * @param uploadFileName 上传的文件名(即重命名的文件名)
	 * @return 是否上传成功
	 */
	public static boolean upload(InputStream fileInputStream, String uploadPath, String uploadFileName){
		boolean flag = false;
		File file = new File(uploadPath + uploadFileName);//构建上传文件
		mkDir(uploadPath);//判断目录是否存在,创建一个目录，并修改目录权限为777
		//if(!file.getParentFile().exists()) file.getParentFile().mkdirs();//判断目录是否存在,不存在则创建
		try {
			InputStream inputStream = null;
			OutputStream outputStream = null;
			try {
				inputStream = new BufferedInputStream(fileInputStream,1024);
				outputStream = new BufferedOutputStream(new FileOutputStream(file),1024);
				byte[] buffer = new byte[1024];
				while (inputStream.read(buffer) > 0) {
					outputStream.write(buffer);
				}
				flag = true;
			} finally {
				if (null != inputStream) { inputStream.close();}
				if (null != outputStream) { outputStream.close();}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 下载文件
	 * 
	 * @author zhanghao
	 * @date 2012-11-23,下午12:36:51
	 * @param response
	 * @param filePath 文件路径,这里传的路径是包含文件名的路径.
	 * @param fileName 文件名称
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static boolean download(HttpServletResponse response, String filePath, String fileName) throws UnsupportedEncodingException, IOException,FileNotFoundException {
		boolean flag = false;
		OutputStream outStream = null;
		InputStream inputStream = null;
		try {
			File file = new File(filePath);
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "utf-8") + "\"");
			response.setContentType("application/octet-stream; charset=uft-8");
			response.setContentLength((int) file.length());

			outStream = response.getOutputStream();
			inputStream = new FileInputStream(file);

			byte[] buffer = new byte[1024];
			int count = -1; // 每次读取字节数
			while ((count = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, count);
			}
			flag = true;
		}catch(Exception e){  
			logger.error(e.getMessage());
		} 
		finally {
			if (outStream != null)
				outStream.close();
				outStream.flush();
			if (inputStream != null)
				inputStream.close();
		}
		return flag;
	}
	
	/** 
	* @Title: ZipFile 
	* @Description: 文件打包
	* @param paths  路径
	* @param saveNames  文件名称
	* @param realNames  输出名称
	* @param encodStr  文件输出流所需编码字符集
	* @return String  返回导出结果
	* @author Sam.J
	* @date 2015-9-25 上午10:05:19 
	* @throws 
	*/
	public  static String  ZipFile(String[] paths, String saveNames[],String [] realNames,String encodStr){
		
		String mess=null;
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		String time=sdf.format(date);//创建当前时间  
        String zipFileName=time+".zip";// 压缩后文件的名字   20150911170609.zip
        String zipFilePath=paths[paths.length-1];//压缩文件的文件夹   /upload/2015/08/28/20885011/
        System.out.println(zipFilePath+zipFileName);
        
        File zipFile=new File(zipFilePath+File.separator+zipFileName);
		FileInputStream inputStream = null;
		BufferedInputStream bis=null;
		BufferedOutputStream bos=null;
		ZipOutputStream zipOut =null;
	
		try {
			bos=new BufferedOutputStream(new FileOutputStream(zipFile));
		    zipOut = new ZipOutputStream(bos);
		    zipOut.setEncoding(encodStr); //对输出流进行编码
			for(int i=0;i<paths.length;i++){
				File file=new File(paths[i]+File.separator+saveNames[i]);
				if(file.exists()){
					inputStream = new FileInputStream(file);
					bis=new BufferedInputStream(inputStream);
					zipOut.putNextEntry(new ZipEntry(realNames[i]));
					byte[] input = new byte[1024];
					int temp;
	                while ((temp = bis.read(input)) != -1) {
	                    zipOut.write(input,0,temp);
	                }
				}
				
			}
		}catch(Exception e){  
			logger.error(e.getMessage());
			return "false";
		} finally{
			try {
				if(zipOut!=null){
					zipOut.close();
					zipOut.flush();
				}
				if(bos!=null){
					bos.close();
					bos.flush();
				}
				if(bis!=null){
					bis.close();
				}
				if(inputStream!=null){
					inputStream.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				return "false";
			}
			
		}
		mess=zipFilePath+","+zipFileName;
		return mess;
		
		
//		if(!zipFile.exists()){    
//	    try {  
//	    	zipFile.mkdirs();
//	    	zipFile.setWritable(true);
//	    	boolean flag=zipFile.createNewFile();   
//	    	System.out.println(zipFile.canWrite());
//			System.out.println("文件是否存在："+zipFile.exists()+"是否创建成功："+flag);
//	    } catch (IOException e) {    
//	        e.printStackTrace();    
//			return "false";
//	    }    
//	} 
		
   }

	
	
	/**
	 * 构建文件上传的路径
	 * @author zhanghao
	 * @date 2012-11-23,下午03:28:01
	 * @param date 当前系统时间
	 * @return 上传的路径
	 */
	public static String builderUploadPath(Date date,Long sysuserId){
		//从配置文件里取得文件上传的根路径:如,d:\\upload
		String rootPath = new PropertiesLoader("crm.properties").getProperty("upload.root.path");
		//这里是组成文件上传的目录,组合方式:当前年/月/日/当前用户的id
		return rootPath + new SimpleDateFormat("yyyy/MM/dd/").format(date) + sysuserId+ "/";
	}
	
	
	/**
	 * 构建新的文件名,文件名生成规则,UUID+原文件名后缀。
	 * @author zhanghao
	 * @date 2012-11-23,下午02:35:23
	 * @param fileName 原文件名
	 * @return 新生成的文件名
	 */
	public static String builderNewFileName(String fileName){
		return Identities.uuid() + getExtension(fileName);
	}
	
	
	
	/**
     * 返回文件的后缀名
     * @author zhanghao
	 * @date 2012-11-23,下午02:35:56
     * @param fileName 文件名
     * @return 后缀名
     */
    public static String getExtension(String fileName){
        if (fileName == null || "".equals(fileName)){
            return null;
        }
        else{
            int index = fileName.lastIndexOf(".");
            if (index == -1){
                return null;
            }
            return fileName.substring(index);
        }
    }
	
	/**
	 * 删除指定文件下的文件
	 * @author zhanghao
	 * @date 2012-11-23,下午02:06:07
	 * @param filePath 指定文件的路径
	 * @return true:删除成功 false:删除失败
	 */
	public static boolean delFile(String filePath) {
		boolean flag = false;
		File file = new File(filePath);
		if (!file.exists()) {
			return flag;
		}
		flag = (new File(filePath)).delete();
		return flag;
	}
	
	/**
	 * 根据目录名创建目录，目录权限为777
	 */
	public static void mkDir(String pathName){
		File file=new File(pathName);
		if (file.exists()) {  
		    return;                                                      
		}                                                                      
		if (file.mkdir()) {
			file.setExecutable(true,false);
			file.setReadable(true, false);
			file.setWritable(true, false);
		    return;                                                       
		}                                                                      
		File canonFile = null;                                           
	    try {                                                            
	          canonFile =file.getCanonicalFile();                              
	      } catch (IOException e) {                                        
	          return;                                                
	      }                                                                
	    File parent = canonFile.getParentFile();   
 	    mkDir(parent.getPath());     
	    mkDir(canonFile.getPath());
	 }                         
	


}
