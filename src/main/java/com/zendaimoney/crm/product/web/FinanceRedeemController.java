package com.zendaimoney.crm.product.web;

import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zendaimoney.crm.CrudUiController;
import com.zendaimoney.crm.product.entity.BusiFinance;
import com.zendaimoney.crm.product.entity.BusiNofixFinance;
import com.zendaimoney.crm.product.entity.BusiRedeem;
import com.zendaimoney.crm.product.entity.BusiRedeemView;
import com.zendaimoney.crm.product.service.BusiFinanceService;
import com.zendaimoney.crm.product.service.BusiNofixFinanceService;
import com.zendaimoney.crm.product.service.FinRedeemViewService;
import com.zendaimoney.crm.product.service.FinanceRedeemService;

/**
 * 项目名称：crm-webapp
 * 
 * @ClassName: FinanceRedeemController
 * @Description: 非固赎回control类
 * @author Sam.J
 * @date 2016-1-20 上午10:06:26
 */
@Controller
@RequestMapping(value = "/product/redeem")
public class FinanceRedeemController extends CrudUiController<BusiRedeemView>{/*
	
	private static Logger logger = LoggerFactory.getLogger(FinanceRedeemController.class);

	@Autowired
	private FinanceRedeemService financeRedeemService;
	
	@Autowired
	private FinRedeemViewService  finRedeemViewService;
	
	@Autowired
	private BusiFinanceService financeService;
	
	@Autowired
	private BusiNofixFinanceService busiNofixFinanceService;
	
	private static ConfigParamBean configParamBean;
	
	@Autowired
	private WarnService warnService;
	
	private String warnRevoke="";
	
	private String warnPaiqi="";
	
	*//** 
	* @Title: index 
	* @Description: 跳转列表页面
	* @param model
	* @return String(描述入参以及返回参数的含义)
	* @author Sam.J
	* @date 2016年1月20日 上午11:47:43 
	* @throws 
	*//*
	@RequestMapping(value = { "" })
	public String index(Model model) {
		if("".equals(warnRevoke)||"".equals(warnPaiqi)){
			initWarn();
		}
		model.addAttribute("warnRevoke", warnRevoke);
		model.addAttribute("warnPaiqi", warnPaiqi);
		return "/crm/product/redeem/list";
	}
	
	
	@RequestMapping(value = "/show")
	public String toShow(@RequestParam(value = "id") Long id, Model model) {		
		model.addAttribute("id", id);
		return "/crm/product/redeem/show";
	}
	
	*//** 
	* @Title: list 
	* @Description: 取list数据
	* @return PageVo(描述入参以及返回参数的含义)
	* @author Sam.J
	* @date 2016年1月21日 上午11:37:16 
	* @throws 
	*//*
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
	@RequestMapping(value = "/list")
	public PageVo list(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "auto") String sort,
			@RequestParam(value = "order", defaultValue = "asc") String order,
			Model model, ServletRequest request) {
		Map<String, SearchFilter> filtersMap = new HashMap<String, SearchFilter>();
		Page<BusiRedeemView> productViewPage = finRedeemViewService
				.getAllProductView(
						buildSpecification(request, filtersMap,
								Share.PRODUCT_REQUEST, "crGather"),
						buildPageRequest(page, rows, sort, order));
		List<BusiRedeemView>  viewList=productViewPage.getContent();
		for(BusiRedeemView  v:viewList){
			showDateDeal(v);
		}
		return new PageVo(productViewPage);
	}
	
	
	*//** 
	* @Title: toApply 
	* @Description: 赎回申请页面 asv bg
	* @return String(描述入参以及返回参数的含义)
	* @author Sam.J
	* @date 2016年1月21日 下午3:33:46 
	* @throws 
	*//*
	@RequestMapping(value = "/apply")
	public String toApply(Model model) {
		if("".equals(warnPaiqi)){
			initWarn();
		}
		model.addAttribute("warnPaiqi", warnPaiqi);
		return "/crm/product/redeem/apply";
	}
	
	*//** 
	* @Title: findByPreFeLendNo 
	* @Description: 根据出借编号判断该业务是否满足赎回条件
	* @param feBusiNo
	* @return ResultVo(描述入参以及返回参数的含义)
	* @author Sam.J
	* @date 2016年1月25日 下午2:09:52 
	* @throws 
	*//*
	@ResponseBody
	@RequestMapping(value = "/find/preFeLendNo")
	public ResultVo findByPreFeLendNo(@RequestParam(value = "feBusiNo") String feBusiNo) {
		List<BusiRedeem>  reList=financeRedeemService.findByFxBusiNo(feBusiNo);
		for(BusiRedeem bn:reList){
			//是否为撤销状态  如为撤销，则跳过
			if(ParamConstant.REDEEM_STATE_CANCLE.equals(bn.getReState())){continue;} 
			return new ResultVo(false, "已存在该笔订单的赎回信息!");
		}
		List<BusiFinance> listFin2=financeService.findAllByFeLendNoForRedeem(feBusiNo);
		if(listFin2.size()==0){
			return new ResultVo(false, "该笔订单不存在或不满足赎回条件!");
		}
		for(BusiFinance f:listFin2){
			if(!checkCloseDate(f))return new ResultVo(false, "该笔订单未过封闭期，无法提出赎回请求!");
		}
		List<BusiNofixFinance> listFin=busiNofixFinanceService.findByFxBusiNo(feBusiNo);
		for(BusiNofixFinance bn:listFin){
			if(ParamConstant.FE_REQUEST_STATE_CREATE.equals(bn.getFxState())||ParamConstant.FE_REQUEST_STATE_AUDIT.equals(bn.getFxState())||ParamConstant.FE_REQUEST_STATE_BACK.equals(bn.getFxState())){
				return new ResultVo(false, "已存在该笔订单未处理完的续期业务!");} 
		}
		return new ResultVo(true, "操作成功");
	}
	
	*//** 
	* @Title: findByFeLendNo 
	* @Description: 根据出借编号查找出订单信息 
	* @param fxBusiNo
	* @return List<BusiFinance>(描述入参以及返回参数的含义)
	* @author Sam.J
	* @date 2016年1月26日 上午10:03:41 
	* @throws 
	*//*
	@ResponseBody
	@RequestMapping(value = "/find/FeLendNo")
	public List<BusiFinance> findByFeLendNo(@RequestParam(value = "feBusiNo") String feBusiNo) {
		List<BusiFinance> listFin2=financeService.findAllByFeLendNoForRedeem(feBusiNo);
		return listFin2;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/revoke")
	public ResultVo revoke(@RequestParam(value = "id") Long id) {		
		BusiRedeem br=financeRedeemService.findOne(id);
		if(!ParamConstant.REDEEM_STATE_PASS.equals(br.getReState())&&!ParamConstant.REDEEM_STATE_DEAL.equals(br.getReState()))return new ResultVo(false, "该订单当前状态不允许撤销！");
		if(br.getReReturnDate()!=null && br.getReReturnDate().compareTo(new Date())<=0)return new ResultVo(false, "转让日及之后不允许撤销！");
		return financeRedeemService.revokeRedeemFin(br);
	}
	
	*//** 
	* @Title: investSave 
	* @Description: 创建赎回业务 
	* @param br
	* @return ResultVo(描述入参以及返回参数的含义)
	* @author Sam.J
	* @date 2016年1月26日 下午6:41:31 
	* @throws 
	*//*
	@ResponseBody
	@RequestMapping(value = "/save")
	public ResultVo investSave(BusiRedeem  br) {
		try {
			ResultVo reFlag=findByPreFeLendNo(br.getReBusiNo());
			if(reFlag.result.containsKey("error")) return reFlag;
			List<BusiFinance> listFin2=financeService.findAllByFeLendNoForRedeem(br.getReBusiNo());
			br.setReCloseDate(getCloseDate(listFin2.get(0))); //给封闭到期日赋值
			return financeRedeemService.invokeRedeemFin(br);
		} catch (Exception e) {
			logger.error("",e);
			return new ResultVo(false, "系统异常，请联系系统管理员！");
		}
	}
	
	*//** 
	* @Title: updateWarn 
	* @Description: 修改提示语 
	* @param br
	* @return ResultVo(描述入参以及返回参数的含义)
	* @author Sam.J
	* @date 2016年2月3日 下午3:19:43 
	* @throws 
	*//*
	@ResponseBody
	@RequestMapping(value = "/updateWarn")
	public ResultVo updateWarn(String warnRevoke,String warnPaiqi) {
		try {
			String  rwarnRevoke=new String(warnRevoke.getBytes("iso-8859-1"));  
			rwarnRevoke=URLDecoder.decode(rwarnRevoke,"utf-8");
			String  rwarnPaiqi=new String(warnPaiqi.getBytes("iso-8859-1"));  
			rwarnPaiqi=URLDecoder.decode(rwarnPaiqi,"utf-8");
			List<SysWarn> listWarn=warnService.findByWtype("1");
			for(SysWarn s:listWarn){
				if("赎回撤销提醒".equals(s.getwTitle())){
					this.warnRevoke=rwarnRevoke;
					s.setwContent(rwarnRevoke);
				}
				if("赎回排期提醒".equals(s.getwTitle())){
					this.warnPaiqi=rwarnPaiqi;
					s.setwContent(rwarnPaiqi);
				}
				warnService.save(s);
			}
			return new ResultVo(true, "修改成功");
		} catch (Exception e) {
			logger.error("",e);
			return new ResultVo(false, "系统异常，请联系系统管理员！");
		}
	}
	

	*//** 
	* @Title: complaint_query 
	* @Description: 高级查询页面 
	* @return String    返回类型 
	* @author Sam.J
	* @date 2016年2月19日 下午5:24:01 
	* @throws 
	*//*
	@RequestMapping(value = { "query" })
	public String complaint_query(Model model) {
		return "/crm/product/redeem/purchase_query";
	}
	
	*//** 
	* @Title: checkCloseDate 
	* @Description: 判断封闭期是否在封闭期外
	* @param bf
	* @return boolean(描述入参以及返回参数的含义)
	* @author Sam.J
	* @date 2016年1月25日 下午4:51:21 
	* @throws 
	*//*
	private boolean checkCloseDate(BusiFinance bf){
		Date closeDate=getCloseDate(bf);
		if(closeDate==null) return false;
		Date  sysDate=new Date();
		 Calendar   calendar   =  Calendar.getInstance(); 
	     calendar.setTime(closeDate); 
	     calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
	     closeDate=calendar.getTime();
		if(sysDate.getTime()>=closeDate.getTime()) return true; //判断封闭期如果在当前日期前（包括当前日），则返回false
		return false;
	}
	
	
	*//** 
	* @Title: getCloseDate 
	* @Description: 计算封闭到期日 
	* @param bf
	* @return Date(描述入参以及返回参数的含义)
	* @author Sam.J
	* @date 2016年1月26日 下午3:52:51 
	* @throws 
	*//*
	private Date getCloseDate(BusiFinance bf){
		Date closeDate=null;
		Calendar cal = Calendar.getInstance(); 
		if(bf.getFeClosedDate()!=null){
			closeDate=bf.getFeClosedDate();
		}else if(bf.getFeInvestDate()!=null){
			cal.setTime(bf.getFeInvestDate()); //设定时间为投资起始日
		    cal.add(Calendar.YEAR, 1);//投资起始日加1年，则为原来的投资结束日
		    closeDate=cal.getTime(); 
		}else if(bf.getFeTimeInvestStart()!=null){
			cal.setTime(bf.getFeTimeInvestStart()); //设定时间为定投投资起始日
		    cal.add(Calendar.YEAR, 1);//投资起始日加1年，则为原来的投资结束日
		    closeDate=cal.getTime();
		}else{
			return null;
		}
		return closeDate;
	}
	
	
	*//** 
	* @Title: findBusiRedeemOne 
	* @Description:
	* @param id
	* @return BusiNofixFinance(描述入参以及返回参数的含义)
	* @author Sam.J
	* @date 2016年1月26日 下午5:22:08 
	* @throws 
	*//*
	@ResponseBody
	@RequestMapping(value = "/find/one")
	public BusiRedeem findBusiRedeemOne(@RequestParam(value = "id") Long id) {
		return financeRedeemService.findOne(id);
	}
	
	
	*//** 
	* @Title: initWarn 
	* @Description: 初始化提示语
	* @author Sam.J
	* @date 2016年1月28日 下午2:40:30 
	* @throws 
	*//*
	private  void  initWarn(){
		List<SysWarn> listWarn=warnService.findByWtype("1");
		for(SysWarn s:listWarn){
			if("赎回撤销提醒".equals(s.getwTitle()))this.warnRevoke=s.getwContent();
			if("赎回排期提醒".equals(s.getwTitle()))this.warnPaiqi=s.getwContent();
		}
	}
	
	
	*//** 
	* @Title: showDateDeal 
	* @Description: 判断是否需要展示转让日期给客户经理查看
	* @param bv void(描述入参以及返回参数的含义)
	* @author Sam.J
	* @date 2016年2月3日 下午1:50:42 
	* @throws 
	*//*
	private void   showDateDeal(BusiRedeemView bv){
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(new Date());
		if (configParamBean == null)
            configParamBean = (ConfigParamBean) SpringContextHelper.getBean("configParamBean");
		String   showDate=configParamBean.getRedeemShowDate();
		cal.add(cal.DATE,Integer.parseInt(showDate));
		if(bv.getReReturnDate()!=null){
			if(cal.getTime().getTime()<bv.getReReturnDate().getTime()){ //判断日期是否在允许范围内
				bv.setReReturnDate(null);
				bv.setReAmount(null);
			}
		}
	}
*/}
