package com.zendaimoney.sys.announcement.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.orm.PageVo;
import org.springside.modules.orm.ResultVo;

import com.google.common.collect.Lists;
import com.zendaimoney.constant.LoggingSource;
import com.zendaimoney.constant.LoggingType;
import com.zendaimoney.constant.ParamConstant;
import com.zendaimoney.crm.CrudUiController;
import com.zendaimoney.crm.sysuser.entity.SysUser;
import com.zendaimoney.sys.announcement.entity.Announcement;
import com.zendaimoney.sys.announcement.service.AnnouncementService;
import com.zendaimoney.utils.LoggingHelper;

@Controller
@RequestMapping(value = "/sys/announcement")
public class AnnouncementController extends CrudUiController<Announcement> {
	@Autowired
	private AnnouncementService announcementService;

	@RequestMapping(value = { "" })
	public String index(Model model) {
		return "sys/announcement/announcement";
	}
	 
	@RequestMapping(value = "list",method = RequestMethod.POST)
	@ResponseBody
	public PageVo list(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "auto") String sort,
			@RequestParam(value = "order", defaultValue = "asc") String order,Model model, ServletRequest request) {
//		Page<Announcement> announcements = announcementService.getAnnouncement(buildSpecification(request,null,null),buildPageRequest(page, rows, sort, order));
		Page<Announcement> CrmServices = announcementService.getAnnouncement(
				buildSpecification(request),
				buildPageRequest(page, rows, sort, order));
		return new PageVo(CrmServices);
	}
	
	@RequestMapping(value = { "create" })
	public String create(Model model) {
		return "sys/announcement/create";
	}
	
	@RequestMapping(value = "save",method = RequestMethod.POST)
	@ResponseBody
	public ResultVo save(Announcement announcement,HttpServletRequest request,HttpSession httpSession) throws HibernateOptimisticLockingFailureException,Exception{
		SysUser sysuser=(SysUser)httpSession.getAttribute(ParamConstant.SYS_USER);
		try {
			String result="";
			Announcement newAnnouncement = new Announcement();
			if(announcement.getAnnouncementType().equals("3"))//紧急消息内容同标题
				announcement.setContent(announcement.getTitle());
			if(announcement.getId()!=null){				
				Announcement entity = announcementService.getAnnouncement(announcement.getId());
				Announcement oldAnnouncement = new Announcement();
				BeanUtils.copyProperties(entity,oldAnnouncement);
				List<String> needList = Lists.newArrayList("title","beginDate","endDate","content","createDate","pubMan","status","announcementType","openLogin");
				newAnnouncement = announcementService.saveNewAnnouncement(announcement,sysuser);
				
				result = "公告  Id："+entity.getId()+"；"+LoggingHelper.loggingEquals(oldAnnouncement, newAnnouncement,needList);
				LoggingHelper.createLogging(LoggingType.修改, LoggingSource.系统,result,sysuser);
			}else{
				newAnnouncement = announcementService.saveNewAnnouncement(announcement,sysuser);
				LoggingHelper.createLogging(LoggingType.新增, LoggingSource.系统, "公告Id:"+newAnnouncement.getId(),sysuser);
			}
			return new ResultVo(true);
		} catch (HibernateOptimisticLockingFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultVo(false,"  此数据已有新内容，请刷新后编辑！");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultVo(false);
		}
	}
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CustomDateEditor dateEditor = new CustomDateEditor(fmt, true);
		binder.registerCustomEditor(Date.class, dateEditor);
	}
	


	@RequestMapping(value = { "edit" })
	public String edit(@RequestParam(value = "id") Long id, Model model) {
		model.addAttribute("announcementId", id);
		return "sys/announcement/edit";
	}

	@RequestMapping(value = { "show" })
	public String show(@RequestParam(value = "id") Long id, Model model) {
		Announcement announcement=announcementService.getAnnouncement(id);
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		model.addAttribute("content", announcement.getContent());
		model.addAttribute("pubMan", announcement.getPubMan());
		model.addAttribute("createDate",fmt.format(announcement.getCreateDate()));
		return "sys/announcement/show";
	}
	
	@RequestMapping("getAnnouncementById")
	@ResponseBody
	public Announcement getAnnouncementById(Long id) {
		return announcementService.getAnnouncement(id);
	}
	
	
//	@Resource
//	private AnnouncementService announcementService;
//
//	@Resource
//	SpringContextHelper springContextHelper;
//	@Resource
//	private DictionaryManager dictionaryManager;
//	private CmAnnouncement entity;
//	private Long[] id;
//	private Page<CmAnnouncement> page = new Page<CmAnnouncement>(10);
//	private String paperTypeSelect;
//
//	public CmAnnouncement getModel() {
//		return entity;
//	}
//
//	@Override
//	protected void prepareModel() throws Exception {
//		
//		if (!ArrayUtils.isEmpty(id) && null != id[0]) {
//
//			entity = announcementService.getAnnouncement(id[0]);
//		} else {
//			entity = new CmAnnouncement();
//		}
//	}
//
//	@Override
//	public String delete() throws Exception {
//		try {
//			announcementService.deleteAnnouncement(id);
//			addActionMessage("删除成功");
//		} catch (ServiceException e) {
//			logger.error(e.getMessage(), e);
//			addActionMessage("删除失败");
//		}
//		return RELOAD;
//	}
//
//	@Override
//	public String input() throws Exception {
//		return INPUT;
//	}
//
//	@Override
//	public String list() throws Exception {
//		List<PropertyFilter> filters = HibernateWebUtils
//				.buildPropertyFilters(Struts2Utils.getRequest());
//		// 设置默认排序方式
//		if (!page.isOrderBySetted()) {
//			page.setOrderBy("createDate");
//			page.setOrder(Page.DESC);
//		}
//
//		page = announcementService.searchAnnouncement(page, filters);
//		return SUCCESS;
//	}
//
//
//	@Override
//	public String save() throws Exception {
//		if (null == entity.getId()) {
//			entity.setCreateDate(new Date());
//		}
//
//
//		announcementService.saveAnnouncement(entity);
//		addActionMessage("保存成功");
//		return RELOAD;
//	}
//
//
//
//	public LinkedHashMap getAllFiled() {
//		return dictionaryManager.getEntryMapByField(
//new String[] {
// "paperType" }, new String[] { "paperTypeCode" },
//				new String[] { "paperTypeName" });
//	}
//
//	public Long[] getId() {
//		return id;
//	}
//
//	public void setId(Long[] id) {
//		this.id = id;
//	}
//
//	public Page<CmAnnouncement> getPage() {
//		return page;
//	}
//
//	public String getPaperTypeSelect() {
//		return paperTypeSelect;
//	}


}
