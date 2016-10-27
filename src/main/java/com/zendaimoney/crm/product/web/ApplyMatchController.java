package com.zendaimoney.crm.product.web;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.zendaimoney.crm.CrudUiController;
import com.zendaimoney.crm.product.entity.ApplyMatchAttachmentView;
import com.zendaimoney.crm.product.entity.BusiFinance;
import com.zendaimoney.crm.product.entity.ProductView;
import com.zendaimoney.crm.product.repository.ApplyMatchAttachmentViewDao;
import com.zendaimoney.crm.product.service.BusiFinanceService;
import com.zendaimoney.crm.product.service.ProductViewService;
import com.zendaimoney.crm.product.vo.ApplyMatchVo;
import com.zendaimoney.crm.product.vo.ApplyMatchVo.BusiType;
import com.zendaimoney.uc.rmi.vo.Staff;


/**
 *
 * 申请匹配控制器
 *
 * @author chendl
 * @version $Revision:1.0.0, $Date: 2013-7-17 上午10:53:17 $
 */
@Controller
@RequestMapping(value = "/product/applyMatch")
public class ApplyMatchController extends CrudUiController<ProductView> {/*

	private static Logger logger = LoggerFactory
			.getLogger(ApplyMatchController.class);
	
    @Autowired
    private ProductViewService productViewService;
    
    @Autowired
    private SysMailService sysMailService;
    
    @Autowired
    private BusiFinanceService busiFinanceService;
    @Autowired
    private ApplyMatchAttachmentViewDao applyMatchAttachmentViewDao;

    @ResponseBody
    @RequestMapping(value = { "/list" })
    public ResultVo listApplayMatch(ServletRequest request,@RequestParam(value = "applyMatchStatus")String applyMatchStatus) throws Exception{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = dateFormat.format(new Date());
		Date date = dateFormat.parse(dateStr);
        Map<String, SearchFilter> filtersMap = new HashMap<String, SearchFilter>();
        SearchFilter searchDataValid = new SearchFilter("applyMatchStatus", IN, applyMatchStatus);
		SearchFilter searchDataValid2 = new SearchFilter("applyMatchDate", EQ, date);
		filtersMap.put("applyMatchDate", searchDataValid2);
		filtersMap.put("applyMatchStatus", searchDataValid);
        List<ApplyMatchVo> content = productViewService.listApplyMatch(buildSpecification(request, filtersMap, Share.PRODUCT_REQUEST, "manager"), null,applyMatchStatus);
        return new ResultVo(true,content);
    }

    @RequestMapping(value = {"index"})
    public String applyMatchIndex(Model model) {
        return "/crm/product/applyMatch/list";
    }

    @ResponseBody
    @RequestMapping(value = { "/withDraw" })
    public ResultVo withDraw(@RequestParam(value = "codes")List<String> codes){
    	List<BusiFinance> bfList = busiFinanceService.findByFeLendNoAndApplyMatchStatus(codes);
    	if(bfList.size() > 0){
    		return new ResultVo(false,"数据已修改，请刷新页面后重新操作！");
		}
		String res = productViewService.batchUpdateApplyMatchByLendNo(codes,ParamConstant.APPLY_MATCH_STATUS_NEW);
		return "success".equals(res)? new ResultVo(true,""): new ResultVo(false,"操作失败,请重新尝试或联系管理员!");
    }
    
    *//**
     * 申请匹配提交并发送邮件
     * @param model
     * @param request
     * @return
     *//*
    @ResponseBody
    @RequestMapping(value = { "/submitApplyMatch" })
    public ResultVo submitApplyMatch(Model model, ServletRequest request,@RequestParam(value = "lendNos")String lendNos) {
    	ResultVo resultVo = null;
    	try{
    		String[] lendNoArray = lendNos.split(",");
    		List<String> lendNoList = Arrays.asList(lendNoArray);
    		List<BusiFinance> bfList = busiFinanceService.findByFeLendNoAndApplyMatchStatus(lendNoList);
     		if(bfList.size() > 0){
    			resultVo = new ResultVo(false,"数据已修改，请刷新页面后重新操作！");
    			return resultVo;
    		}
     		
     		//页面传回数据封装
     		List<ApplyMatchVo> list  = productViewService.listApplyMatch(lendNoList);    				    			
            //收件人列表
            List<String> receiverList = new ArrayList<String>();
            //抄送人列表
            List<String> ccList = new ArrayList<String>();
            //获取抄送人邮箱列表
            List<SysMail> ccMailList = sysMailService.findAllByEmailTypeAndUseScene(ParamConstant.MAIL_TYPE_CC,ParamConstant.MAIL_USE_SCENE_APPLY_MATCH);
    		for(int j=0; j < ccMailList.size(); j++){
    			ccList.add(ccMailList.get(j).getEmail());
    		}
    		//获取收件人邮箱列表
    		List<SysMail> receiverMailList = sysMailService.findAllByEmailTypeAndUseScene(ParamConstant.MAIL_TYPE_RECEIVER,ParamConstant.MAIL_USE_SCENE_APPLY_MATCH);
    		for(int j=0; j < receiverMailList.size(); j++){
    			receiverList.add(receiverMailList.get(j).getEmail());
    		}
            for(int i=0; i<list.size();i++){
            	ApplyMatchVo match = list.get(i);
            	String financCenter = match.getFinancialCenterName();//理财中心名称
            	List<Long> senderIds = match.getStaffId();//经办人ID
            	String sender = match.getSender();//经办人姓名
            	Map<String, BusiType> typesMap = match.getTypes();
            	
            	//收件人列表：邮箱配置+经办人的邮箱
            	List<String> resultReceiverList = new ArrayList<String>();
            	resultReceiverList.addAll(receiverList);
            	for(Long senderId:senderIds){            		
            		Staff operatorStaff = UcHelper.getStaff(senderId);
            		resultReceiverList.add(operatorStaff.geteMail());            		
            	}
            	if(resultReceiverList.size() > 0){//收件人不为空        
            		//收件人格式转换 “,”隔开
            		String receive = Arrays.toString(resultReceiverList.toArray()).replace("[", "").replace("]", "");
            		resultReceiverList.clear();
            		resultReceiverList.add(receive);
            		
            		//邮件主题
            		String subject = DateUtil.format(new Date(), "yyyy-MM-dd")+financCenter+"申请匹配信息";
            		
            		//抄送人列表
            		List<String> resultCcList = new ArrayList<String>();
            		resultCcList.addAll(ccList);
            		
            		//邮件内容
            		StringBuffer mailContext = new StringBuffer();
            		mailContext = formatContent(financCenter, mailContext, typesMap, sender);
            		
            		//获取理财中心出借编号列表，查询附件所需信息，生成文件并返回附件路径
            		List<String> financeCenterlendNos = selectLendNosByFinanceCenter(typesMap);
            		List<ApplyMatchAttachmentView> dataList = applyMatchAttachmentViewDao.findByLendNo(financeCenterlendNos);
            		String AttachmentPath = GenerateExcelUtil.generateExcelFromList(dataList);
            		
        			ResultVo result =MailService.mailSend(subject, resultReceiverList, resultCcList, mailContext.toString(),ParamConstant.TYTX_BODYFORMAT_HTML,AttachmentPath);
        			Map<String, Object> map = result.result;
        			if(map.containsKey("success")){//邮件发送成功
        				//更新数据状态
        				String updateResult = productViewService.batchUpdateApplyMatchByLendNo(financeCenterlendNos, ParamConstant.APPLY_MATCH_STATUS_SUCCESS);
        				if("success".equals(updateResult)){
        					resultVo = new ResultVo(true,"操作成功");
        				} else {
        					resultVo = new ResultVo(false,"更新状态失败，,请重新尝试或联系管理员!");
        				}
        				
        			} else {
        				resultVo =  new ResultVo(false, "邮件发送失败,请重新尝试或联系管理员!");
        			}
        			financeCenterlendNos.clear();
        			dataList.clear();
//        			记录日志会更新ApplyMatchAttachmentView报错，暂取消日志
//        			LoggingHelper.createLogging(LoggingType.申请, LoggingSource.申请匹配,
//        					"汇款出资出借编号：" + typesMap.get("zxzz").getFeLendNo() +"划扣出资出借编号："
//            		                         +typesMap.get("wthk").getFeLendNo()+"月稳盈出借编号："
//        							         +typesMap.get("zdywy").getFeLendNo()+ ",收件人："+receive);
        			logger.info("申请匹配信息——汇款出资出借编号："+ typesMap.get("zxzz").getFeLendNo() +"，划扣出资出借编号："
            		                         +typesMap.get("wthk").getFeLendNo()+"，月稳盈出借编号："
        							         +typesMap.get("zdywy").getFeLendNo()+ ",收件人："+receive);         			
            	} 
            }
            resultVo = new ResultVo(true,"操作成功");
    	}catch(Exception e) {
			e.printStackTrace();
			resultVo = new ResultVo(false, "操作失败,请重新尝试或联系管理员!");
        }
    	
 
        return resultVo;
    }
    

    //获取某理财中心所有出借编号，用于查询附件所需数据
	private List<String> selectLendNosByFinanceCenter(Map<String, BusiType> typesMap) {
		ArrayList<String> newlist = new ArrayList<String>();
		String zzLendNo = typesMap.get("zxzz").getFeLendNo();
		String hkLendNo = typesMap.get("wthk").getFeLendNo();
		String ywyLendNo = typesMap.get("zdywy").getFeLendNo();
		if(StringUtils.isNotBlank(zzLendNo)){
			List<String> zxzz =  Arrays.asList(zzLendNo.split(","));
			newlist.addAll(zxzz);
		}
		if(StringUtils.isNotBlank(hkLendNo)){
			List<String> wthk =  Arrays.asList(typesMap.get("wthk").getFeLendNo().split(","));
			newlist.addAll(wthk);
		}
		if(StringUtils.isNotBlank(ywyLendNo)){
			List<String> zdywy =  Arrays.asList(typesMap.get("zdywy").getFeLendNo().split(","));
			newlist.addAll(zdywy);
		}
		return newlist;
	}

	*//**
     * 格式化出借编号，去除最后一位
     * @param lendNo
     * @return
     *//*
    private String formatLendNo(String lendNo) {
    	if(Strings.isNullOrEmpty(lendNo)){
    		return "";
    	} else {
    		return lendNo.substring(0,lendNo.length()-1);
    	}
    	 
    }
    
    *//**
     * 生成 html格式邮件内容
     * @param financCenter
     * @param mailContext
     * @param typesMap
     * @param sender
     * @return
     *//*
    private StringBuffer formatContent(String financCenter, StringBuffer mailContext,Map<String, BusiType> typesMap, String sender){
    	//表格标题
    	String tableHeadHtml = "";
    	tableHeadHtml+="<table border=1><tr>";
    	tableHeadHtml+="<td><span>理财中心</span></td>";
    	tableHeadHtml+="<td><span>业务区分</span></td>";
    	tableHeadHtml+="<td><span>出借编号</span></td>";
    	tableHeadHtml+="<td><span>笔数</span></td>";
    	tableHeadHtml+="<td><span>出借金额</span></td>";
    	tableHeadHtml+="<td><span>经办人</span></td></tr>";
    	mailContext.append(tableHeadHtml);
			
    	//表格数据  
    	//汇款出资
    	String tableDataHtml="";
    	tableDataHtml+="<tr>";
		tableDataHtml+="<td rowspan='3'>"+financCenter+"</td>";
		tableDataHtml+="<td>"+typesMap.get("zxzz").getTitle()+"</td>";
		
		tableDataHtml+="<td>"+formatLendNo(typesMap.get("zxzz").getFeLendNo())+"</td>";
		tableDataHtml+="<td>"+typesMap.get("zxzz").getCount()+"</td>";
		tableDataHtml+="<td rowspan='2'>"+typesMap.get("zxzz").getTotalAmount().add(typesMap.get("wthk").getTotalAmount())+"</td>";
		tableDataHtml+="<td rowspan='3'>"+sender+"</td>";
		tableDataHtml+="</tr>";
		//划扣出资
		tableDataHtml+="<tr>";
		tableDataHtml+="<td>"+typesMap.get("wthk").getTitle()+"</td>";
		tableDataHtml+="<td>"+formatLendNo(typesMap.get("wthk").getFeLendNo())+"</td>";
		tableDataHtml+="<td>"+typesMap.get("wthk").getCount()+"</td>";
		tableDataHtml+="</tr>";
		//月稳盈
		tableDataHtml+="<tr>";
		tableDataHtml+="<td>"+typesMap.get("zdywy").getTitle()+"</td>";
		tableDataHtml+="<td>"+formatLendNo(typesMap.get("zdywy").getFeLendNo())+"</td>";
		tableDataHtml+="<td>"+typesMap.get("zdywy").getCount()+"</td>";
		tableDataHtml+="<td>"+typesMap.get("zdywy").getTotalAmount()+"</td>";
		tableDataHtml+="</tr>";
    	mailContext.append(tableDataHtml);
    	mailContext.append("</table>");
    	return mailContext;
    }
*/}
