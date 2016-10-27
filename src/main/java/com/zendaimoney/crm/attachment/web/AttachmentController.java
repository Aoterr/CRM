package com.zendaimoney.crm.attachment.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.orm.ResultVo;

import com.zendaimoney.constant.LoggingSource;
import com.zendaimoney.constant.LoggingType;
import com.zendaimoney.constant.ParamConstant;
import com.zendaimoney.crm.CrudUiController;
import com.zendaimoney.crm.attachment.entity.BusiAttachment;
import com.zendaimoney.crm.attachment.entity.BusiRequestAttach;
import com.zendaimoney.crm.attachment.service.BusiAttachmentService;
import com.zendaimoney.crm.attachment.service.BusiRequestAttachService;
import com.zendaimoney.crm.customer.service.CustomerService;
import com.zendaimoney.crm.file.service.BusiFileService;
import com.zendaimoney.crm.fund.service.BusiFundInfoService;
import com.zendaimoney.crm.modification.service.ModificationService;
import com.zendaimoney.crm.product.entity.BusiCredit;
import com.zendaimoney.crm.product.service.BusiCreditService;
import com.zendaimoney.crm.product.service.BusiFinanceService;
import com.zendaimoney.crm.sysuser.entity.SysUser;
import com.zendaimoney.uc.rmi.vo.Staff;
import com.zendaimoney.utils.LoggingHelper;

/**
 * 附件管理
 * 
 * @author zhanghao
 * @create 2012-11-15, 下午04:49:32
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/attachment")
public class AttachmentController extends CrudUiController<BusiCredit> {

	private static Logger logger = LoggerFactory
			.getLogger(AttachmentController.class);

	@Autowired
	private BusiAttachmentService attachmentService;

	@Autowired
	private BusiCreditService busiCreditService;

	@Autowired
	private BusiFinanceService busiFinanceService;

	@Autowired
	private BusiFundInfoService busiFundInfoService;
	
	@Autowired
	private BusiRequestAttachService requestAttachService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ModificationService modificationService;

	@Autowired
	BusiFileService busiFileService;

	@RequestMapping(value = { "" })
	public String index(Model model) {
		return "/crm/attachment/attachment_list";
	}

	// list页面
	@ResponseBody
	@RequestMapping(value = "/list")
	public String list(Model model) {
		return "/crm/attachment/attachment_list";
	}

	/*// 跳转到显示产品所有附件页面
	@RequestMapping(value = "/show/product/attachment")
	public String showProductAttachment(@RequestParam(value = "id") Long id,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "customerid") Long customerid,
			@RequestParam(value = "productName") Long productName, Model model,
			ServletRequest request) {
		model.addAttribute("id", id);
		model.addAttribute("type", type);
		model.addAttribute("customerid", customerid);
		model.addAttribute("productName", productName);
		return "/crm/attachment/attachment_list";
	}

	// 跳转到业务变更单所有附件页面
	@RequestMapping(value = "/show/modification/business/attachment")
	public String showModificationBusinessAttachment(
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "customerid") Long customerid,
			@RequestParam(value = "productName") Long productName,
			@RequestParam(value = "type") String type, Model model,
			ServletRequest request) {
		model.addAttribute("id", id);
		model.addAttribute("customerid", customerid);
		model.addAttribute("type", type);
		model.addAttribute("productName", productName);
		return "/crm/attachment/modification/business_list";
	}

	// 取业务变更单的所有附件
	@ResponseBody
	@RequestMapping(value = "/modification/business/attachment/list")
	public List<BusiAttachment> modificationBusinessAttachmentList(
			@RequestParam(value = "id") Long id) {
		List<BusiAttachment> busiAttachments = new ArrayList<BusiAttachment>();
		for (BusiRequestAttach busiRequestAttach : modificationService
				.findModificationOne(id).getBusiRequestAttachs()) {
			BusiAttachment busiAttachment = busiRequestAttach
					.getBusiAttachment();
			if (busiAttachment.getAtState().equals("1"))
				busiAttachments.add(busiAttachment);
		}
		Collections.sort(busiAttachments, new Comparator<BusiAttachment>() {

			@Override
			public int compare(BusiAttachment o1, BusiAttachment o2) {
				if (o1.getAtModifytime().before(o2.getAtModifytime())) {
					return 1;
				}
				return -1;
			}
		});
		return busiAttachments;
	}

	// 跳转到业务变更单附件新增界面
	@RequestMapping(value = "/modification/business/create")
	public String modificationBusinessAttachmentCreate(
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "customerid") Long customerid,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "productName") Long productName, Model model) {
		model.addAttribute("id", id);
		model.addAttribute("type", type);
		model.addAttribute("customerid", customerid);
		model.addAttribute("productName", productName);
		return "/crm/attachment/modification/business_create";
	}
*/
	// 跳转到客户变更单所有附件页面 hezc
	@RequestMapping(value = "/show/customer/attachment/modification")
	public String showModificationCustomerAttachment(
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "customerid") Long customerid, Model model,
			ServletRequest request, @RequestParam(value = "type") String type) {
		model.addAttribute("id", id);
		model.addAttribute("type", type);
		model.addAttribute("customerid", customerid);
		// model.addAttribute("productName", productName);
		return "/crm/attachment/modification/customer_list";
	}

	// 跳转到显示产品所有附件页面 hezc
	@RequestMapping(value = "/show/customer/attachment")
	public String showCustomerAttachment(
			@RequestParam(value = "customerid") Long customerid, Model model) {
		model.addAttribute("customerid", customerid);
		return "/crm/attachment/customer_list";
	}

	// 取当前客户的所有附件 hezc
	@ResponseBody
	@RequestMapping(value = "/customer/attachment/list")
	public List<BusiAttachment> customerAttachmentList(
			@RequestParam(value = "id") Long id) {
		List<BusiAttachment> busiAttachments = new ArrayList<BusiAttachment>();
		for (BusiRequestAttach busiRequestAttach : customerService.getCustomer(
				id).getBusiRequestAttachs()) {
			BusiAttachment busiAttachment = busiRequestAttach
					.getBusiAttachment();
			if (busiAttachment.getAtState() != null
					&& "1".equals(busiAttachment.getAtState())) {
				busiAttachments.add(busiAttachment);
			}
		}
		Collections.sort(busiAttachments, new Comparator<BusiAttachment>() {

			@Override
			public int compare(BusiAttachment o1, BusiAttachment o2) {
				if (o1.getAtModifytime().before(o2.getAtModifytime())) {
					return 1;
				}
				return -1;
			}
		});
		return busiAttachments;
	}
	
	// 取当前客户变更单的所有附件 hezc
	@ResponseBody
	@RequestMapping(value = "/customer/attachment/modification/list")
	public List<BusiAttachment> modificationCustomerAttachmentList(
			@RequestParam(value = "id") Long id) {
		List<BusiAttachment> busiAttachments = new ArrayList<BusiAttachment>();
		for (BusiRequestAttach busiRequestAttach : modificationService
				.findModificationOne(id).getBusiRequestAttachs()) {
			BusiAttachment busiAttachment = busiRequestAttach
					.getBusiAttachment();
			if (busiAttachment.getAtState().equals("1")) {
				busiAttachments.add(busiRequestAttach.getBusiAttachment());
			}
		}
		Collections.sort(busiAttachments, new Comparator<BusiAttachment>() {

			@Override
			public int compare(BusiAttachment o1, BusiAttachment o2) {
				if (o1.getAtModifytime().before(o2.getAtModifytime())) {
					return 1;
				}
				return -1;
			}
		});
		return busiAttachments;
	}
/*	
	// 取当前客户基金单的所有附件 CJ
		@ResponseBody
		@RequestMapping(value = "/customer/attachment/fund/list")
		public List<BusiAttachment> fundCustomerAttachmentList(
				@RequestParam(value = "id") Long id) {
			List<BusiAttachment> busiAttachments = new ArrayList<BusiAttachment>();
			for (BusiRequestAttach busiRequestAttach : busiFundInfoService
					.findOneFinance(id).getBusiRequestAttachs()) {
				BusiAttachment busiAttachment = busiRequestAttach
						.getBusiAttachment();
				if (busiAttachment.getAtState().equals("1")) {
					busiAttachments.add(busiRequestAttach.getBusiAttachment());
				}
			}
			Collections.sort(busiAttachments, new Comparator<BusiAttachment>() {

				@Override
				public int compare(BusiAttachment o1, BusiAttachment o2) {
					if (o1.getAtModifytime().before(o2.getAtModifytime())) {
						return 1;
					}
					return -1;
				}
			});
			return busiAttachments;
		}
*/
	// 跳转到客户变更单附件界面 hezc
	@RequestMapping(value = "/customer/modification/create")
	public String modificationCustomerAttachmentCreate(
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "type") Long type,
			@RequestParam(value = "customerid") Long customerid, Model model) {
		model.addAttribute("id", id);
		model.addAttribute("type", type);
		model.addAttribute("customerid", customerid);
		return "/crm/attachment/modification/customer_create";
	}

	// 跳转到客户附件界面 hezc
	@RequestMapping(value = "/customer/create")
	public String customerAttachmentCreate(@RequestParam(value = "id") Long id,
			Model model) {
		model.addAttribute("id", id);
		return "/crm/attachment/customer_create";
	}
/*
	// 跳转到客户变更单附件界面 hezc
	@RequestMapping(value = "/show/customerBiangeng/attachment")
	public String showCustomerBiangengAttachment(
			@RequestParam(value = "customerid") Long customerid, Model model) {
		model.addAttribute("customerid", customerid);
		return "/crm/attachment/customer_list";
	}

	// 取当前产品的所有附件
	@ResponseBody
	@RequestMapping(value = "/product/attachment/list")
	public List<BusiAttachment> productAttachmentList(
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "type") String type) {
		List<BusiAttachment> busiAttachments = attachmentService
				.findBusiAttaAndModifiAtta(type, id);
		List<BusiAttachment> busiAttachmentList = new ArrayList<BusiAttachment>();
		for (BusiAttachment busiAttachment : busiAttachments) {
			if (busiAttachment.getAtState().equals("1")) {
				busiAttachmentList.add(busiAttachment);
				// logger.info(busiAttachment.getAtName());
			}
		}
//		 if(type.equals(ParamConstant.PRODUCT_TYPE_CREDIT)){
//			 for (BusiRequestAttach busiRequestAttach :
//				 busiCreditService.findOneCredit(id).getBusiRequestAttachs()) {
//				 busiAttachments.add(busiRequestAttach.getBusiAttachment());
//			 }
//		 }else if(type.equals(ParamConstant.PRODUCT_TYPE_FUND)){//7
//			 for (BusiRequestAttach busiRequestAttach :
//				 busiFundInfoService.findOneFinance(id).getBusiRequestAttachs()) {
//				 busiAttachments.add(busiRequestAttach.getBusiAttachment());
//			 }
//		  }else{
//			  for (BusiRequestAttach busiRequestAttach :
//				  busiFinanceService.findOneFinance(id).getBusiRequestAttachs()) {
//				  busiAttachments.add(busiRequestAttach.getBusiAttachment());
//			  }
//		 }
		Collections.sort(busiAttachmentList, new Comparator<BusiAttachment>() {

			@Override
			public int compare(BusiAttachment o1, BusiAttachment o2) {
				if (o1.getAtModifytime().before(o2.getAtModifytime())) {
					return 1;
				}
				return -1;
			}
		});
		return busiAttachmentList;
	}

	// 跳转到新增附件界面
	@RequestMapping(value = "/create")
	public String create(@RequestParam(value = "id") Long id,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "customerid") Long customerid,
			@RequestParam(value = "productName") Long productName, Model model) {
		model.addAttribute("id", id);
		model.addAttribute("type", type);
		model.addAttribute("customerid", customerid);
		model.addAttribute("productName", productName);
		return "/crm/attachment/attachment_create";
	}

	// 保存新增的附件
	@ResponseBody
	@RequestMapping(value = "save")
	public ResultVo save(@RequestParam(value = "id") Long id,
			@RequestParam(value = "type") String type,
			BusiAttachment busiAttachment, ServletRequest request)
			throws Exception {
		try {
			// 这步操作是保存一些后台记录的信息,如操作人,操作时间等
			attachmentService.builderBusiAttachment(busiAttachment, true);
			// 这里的files是上传文件动态添加的属性,在handlers.js文件里的uploadSuccess里
			requestAttachService.saveRequestAttach(new BusiRequestAttach(id,
					type), busiAttachment, request.getParameterValues("files"));
			return new ResultVo(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultVo(false);
		}
	}

	*/
	/**
	 * 保存新增的附件
	 * 
	 * @author Yuan Changchun
	 * @date 2013-9-26 下午02:49:20
	 * @param busiId
	 *            业务ID(有可能是理财)
	 * @param type
	 * @param busiAttachment
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save/one")
	public ResultVo saveOne(@RequestParam(value = "busiId") Long busiId,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "customerid") Long customerid,
			String atTypeOne, String atType, String atName, String atMemo,
			@RequestParam(value = "busiFileId") Long busiFileId,
			ServletRequest request,HttpSession httpSession) throws Exception {
		SysUser sysuser=(SysUser)httpSession.getAttribute(ParamConstant.SYS_USER);
		atName = new String(request.getParameter("atName").getBytes(
				"iso-8859-1"), "UTF-8");
		atMemo = new String(request.getParameter("atMemo").getBytes(
				"iso-8859-1"), "UTF-8");
		try {
			BusiAttachment busiAttachment = new BusiAttachment();
			busiAttachment.setCustomerid(customerid);
			busiAttachment.setAtType(atType);
			busiAttachment.setAtTypeOne(atTypeOne);
			busiAttachment.setAtName(atName);
			busiAttachment.setAtMemo(atMemo);
			// 这步操作是保存一些后台记录的信息,如操作人,操作时间等
			attachmentService.builderBusiAttachment(busiAttachment, true,sysuser.getId());
			// 这里的files是上传文件动态添加的属性,在handlers1.js文件里的uploadSuccess里
			requestAttachService.saveOneRequestAttach(new BusiRequestAttach(
					busiId, type), busiAttachment, busiFileId);
			return new ResultVo(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultVo(false);
		}
	}
/*
	// 编辑附件
	@RequestMapping(value = "/edit")
	public String edit(@RequestParam(value = "id") Long id, Model model) {
		BusiAttachment busiAttachment = attachmentService
				.findBusiAttachmentById(id);
		model.addAttribute("busiAttachment", busiAttachment);
		return "/crm/attachment/attachment_edit";
	}

	*/
	/**
	 * 编辑客户附件
	 * 
	 * @author Yuan Changchun
	 * @date 2013-9-29 下午01:52:41
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit2")
	public String edit2(@RequestParam(value = "id") Long id, Model model) {
		BusiAttachment busiAttachment = attachmentService
				.findBusiAttachmentById(id);
		model.addAttribute("attachmentId", id);
		model.addAttribute("busiAttachment", busiAttachment);
		model.addAttribute("id", busiAttachment.getCustomerid());
		return "/crm/attachment/customer_attachment_edit";
	}

	/**
	 * 编辑业务附件
	 * 
	 * @author Yuan Changchun
	 * @date 2013-9-29 下午01:52:41
	 * @param id
	 * @param model
	 * @return
	 *//*
	@RequestMapping(value = "/edit3")
	public String edit3(@RequestParam(value = "id") Long id, Model model,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "busiId") Long busiId) {
		BusiAttachment busiAttachment = attachmentService
				.findBusiAttachmentById(id);
		model.addAttribute("attachmentId", id);
		model.addAttribute("type", type);
		model.addAttribute("busiAttachment", busiAttachment);
		model.addAttribute("customerid", busiAttachment.getCustomerid());
		model.addAttribute("id", busiId);
		return "/crm/attachment/busi_attachment_edit";
	}
	*//**
	 * 编辑基金业务附件
	 * 
	 * @author CJ
	 * @date 2015-12-4 下午01:52:41
	 * @param id
	 * @param model
	 * @return
	 *//*
	@RequestMapping(value = "/edit4")
	public String edit4(@RequestParam(value = "id") Long id, Model model,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "busiId") Long busiId) {
		BusiAttachment busiAttachment = attachmentService
				.findBusiAttachmentById(id);
		model.addAttribute("attachmentId", id);
		model.addAttribute("type", type);
		model.addAttribute("busiAttachment", busiAttachment);
		model.addAttribute("customerid", busiAttachment.getCustomerid());
		model.addAttribute("id", busiId);
		return "/crm/attachment/fund_attachment_edit";
	}
	*/
	// 显示附件详细信息
	@RequestMapping(value = "/detail")
	public String detail(@RequestParam(value = "id") Long id, Model model) {
		model.addAttribute("id", id);
		return "/crm/attachment/attachment_detail";
	}

	// 取单个附件信息
	@ResponseBody
	@RequestMapping(value = "/get")
	public BusiAttachment getAttachment(@RequestParam(value = "id") Long id,
			Model model) {
		return attachmentService.findBusiAttachmentById(id);
	}

		
	 //附件弹出框list页面和数据
	@RequestMapping(value = { "/attachment/list" })
	public String attachmentlist(Model model, ServletRequest request,
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "type") String type) {
		model.addAttribute("id", id);
		model.addAttribute("type", type);
		return "crm/attachment/attachment_detail_redonly";
	}

	@ResponseBody
	@RequestMapping(value = { "/get/attachment" })
	public List<BusiAttachment> getAttachment(Model model,
			ServletRequest request, @RequestParam(value = "id") Long id,
			@RequestParam(value = "type") String type) {
		return attachmentService.findAllAttachment(type, id);
	}

	@ResponseBody
	@RequestMapping(value = { "/get/allattachment" })
	public List<BusiAttachment> getAllAttachmentByCusIdAndBusiType(Model model,
			ServletRequest request,
			@RequestParam(value = "customerid") Long customerid,
			@RequestParam(value = "busiType") String busiType) {
		return attachmentService.findAllAttachmentByCusIdAndBusiType(
				customerid, busiType);
	}

	/**
	 * 删除附件(更新附件的状态信息为0)
	 * 
	 * @author Yuan Changchun
	 * @date 2013-9-12 下午05:22:43
	 * @param model
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/delete" })
	public ResultVo deleteAttachment(Model model,
			@RequestParam(value = "id") Long id,HttpSession httpSession) {
		SysUser sysuser=(SysUser)httpSession.getAttribute(ParamConstant.SYS_USER);
		try {
			// 根据id查询附件
			BusiAttachment busiAttachment = attachmentService
					.findBusiAttachmentById(id);
			// 更改附件状态为不可用
			busiAttachment.setAtState("0");
			//增加删除操作操作记录
			busiAttachment.setAtModifytime(new Date());
			//Staff staff = AuthorityHelper.getStaff();
			busiAttachment.setAtModifyid(sysuser.getId());
			// 保存附件更改信息
			attachmentService.saveAttachment(busiAttachment);
			//记录操作日志信息
			LoggingHelper.createLogging(LoggingType.删除, LoggingSource.附件, LoggingHelper.builderLogContent(busiAttachment.getClass().getSimpleName(),busiAttachment.getId()),sysuser);
			return new ResultVo(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultVo(false);
		}
	}

	/**
	 * 跳转到客户附件文件增加页面
	 * 
	 * @author Yuan Changchun
	 * @date 2013-10-9 下午05:32:20
	 * @param model
	 * @param request
	 * @param id
	 * @param busiId
	 * @return
	 */
	@RequestMapping(value = { "/customer/toCusAttachmentAdd" })
	public String createTest(Model model, ServletRequest request,
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "busiId") Long busiId) {
		model.addAttribute("id", id);
		model.addAttribute("busiId", busiId);
		return "crm/attachment/customer_attachment_file_add";
	}

	/**
	 * 跳转到业务附件文件增加页面
	 * 
	 * @author Yuan Changchun
	 * @date 2013-10-9 下午05:32:20
	 * @param model
	 * @param request
	 * @param id
	 * @param busiId
	 * @return
	 *//*
	@RequestMapping(value = { "/customer/toBusiAttachmentAdd" })
	public String createTest1(Model model, ServletRequest request,
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "busiId") Long busiId,
			@RequestParam(value = "customerid") Long customerid,
			@RequestParam(value = "type") String type) {
		model.addAttribute("id", id);
		model.addAttribute("busiId", busiId);
		model.addAttribute("type", type);
		model.addAttribute("customerid", customerid);
		return "crm/attachment/busi_attachment_file_add";
	}
	*//**
	 * 跳转到基金业务附件文件增加页面
	 * 
	 * @author CJ
	 * @date 2015-12-3 下午05:32:20
	 * @param model
	 * @param request
	 * @param id
	 * @param busiId
	 * @return
	 *//*
	@RequestMapping(value = { "/customer/toFundAttachmentAdd" })
	public String createTest2(Model model, ServletRequest request,
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "busiId") Long busiId,
			@RequestParam(value = "customerid") Long customerid,
			@RequestParam(value = "type") String type) {
		model.addAttribute("id", id);
		model.addAttribute("busiId", busiId);
		model.addAttribute("type", type);
		model.addAttribute("customerid", customerid);
		return "crm/attachment/fund_attachment_file_add";
	}
	/**
	 * 更新单个附件的附件基本信息(目前只提供更新附件名和附件备注)
	 * 
	 * @author Yuan Changchun
	 * @date 2013-10-9 下午05:32:47
	 * @param model
	 * @param attachmentId
	 * @param atName
	 * @param atMemo
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/update/one" })
	public ResultVo updateOne(Model model,
			@RequestParam(value = "attachmentId") Long attachmentId,
			ServletRequest request,
			@RequestParam(value = "fileOrderList") String fileOrderList,HttpSession httpSession)
			throws Exception {
		SysUser sysuser=(SysUser)httpSession.getAttribute(ParamConstant.SYS_USER);
		String atName = new String(request.getParameter("atName").getBytes(
				"iso-8859-1"), "UTF-8");
		String atMemo = new String(request.getParameter("atMemo").getBytes(
				"iso-8859-1"), "UTF-8");
		String[] orderList = fileOrderList.split(";");
		List<Long> valueList = new ArrayList<Long>();
		List<Integer> keyList = new ArrayList<Integer>();
		for (String string : orderList) {
			String[] split = string.split(",");
			keyList.add(Integer.valueOf(split[1]));
			valueList.add(Long.valueOf(split[0]));
		}
		Collections.sort(keyList);
		busiFileService.updateFileOrder(keyList, valueList);
		return attachmentService.updateOne(attachmentId, atName, atMemo,sysuser.getId());
		// return new ResultVo(true, "更新成功");
	}
}
