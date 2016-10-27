package com.zendaimoney.sys.desktoptoolbox.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zendaimoney.sys.desktoptoolbox.service.DesktopToolboxService;
import com.zendaimoney.sys.desktoptoolbox.vo.DesktopToolboxVo;
import com.zendaimoney.uc.rmi.vo.Function;
import com.zendaimoney.uc.rmi.vo.Staff;

/**
 * 桌面工具箱
 * 
 * @author bianxj
 * 
 */
@Controller
@RequestMapping(value = "/desktopToolbox")
public class DesktopToolboxController {/*
	@Autowired
	private DesktopToolboxService desktopToolboxService;

	@RequestMapping(value = { "manageTools" })
	public String manageTools(Model model) {
		//Staff staff = AuthorityHelper.getStaff();
		Map<String, Function> permission2 = staff.getMyPermission();
		List<Function> permissions = new ArrayList<Function>();
		Function tree = new Function();
		for (Function f : permission2.values()) {
			if (f.getFunctionName() == null) {
				continue;
			}
			if (f.getFunctionName().contains("crm")) {
				tree = f;
				model.addAttribute("treeTopId", tree.getId());
			}
		}
		return "/manageTools";
	}
	
	@RequestMapping(value = { "showSubMenus/{pid}" })
	@ResponseBody
	public List<TreeVo> showSubMenus(@PathVariable("pid") Long pid, Model model) {
		Staff staff = AuthorityHelper.getStaff();
		Map<String, Function> permission2 = staff.getMyPermission();

		Set<Function> subTrees = new HashSet<Function>();
		Function topTree = null;
		for (Function f : permission2.values()) {
			if (f.getId() == pid) {
				topTree = f;
			}
			getSubTrees(subTrees, f);
		}
		return findMenus(pid, subTrees, topTree);
	}
	
	@RequestMapping(value = { "saveShowSubMenus" })
	@ResponseBody
	public String saveShowSubMenus(@RequestParam("muneCodes") String muneCodes) {
		try {
			Staff staff = AuthorityHelper.getStaff();
			desktopToolboxService.saveDesktopToolboxsString(staff, muneCodes);
			return "true";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "false";

		}
	}

	public List<TreeVo> findMenus(Long parentId, Set<Function> subMenus, Function topTree) {
		TreeVo treeDataJson = new TreeVo();
		treeDataJson.setId(parentId + "");
		iterateList(treeDataJson, subMenus);
	//缺少crmtop
		List treeDatas = treeDataJson.getChildren();
		if (null == treeDatas) {
			treeDatas = new ArrayList();
		}
		return treeDatas;
	}

	private void iterateList(TreeVo treeDataJson, Set<Function> menus) {
		Staff staff = AuthorityHelper.getStaff();
		String[] menuCodes = desktopToolboxService.getDesktopToolboxsString(staff);
		String parentId = treeDataJson.getId();
		List childrenModel = null;
		Iterator iterator = menus.iterator();
		while (iterator.hasNext()) {
			Function menu = (Function) iterator.next();
			String groupParentId = String.valueOf(menu.getParent().getId());
			childrenModel = treeDataJson.getChildren();
			if (groupParentId.equals(parentId)) {
				childrenModel = treeDataJson.getChildren();
				if (childrenModel == null) {
					childrenModel = new ArrayList();
				}
				TreeVo childModel = new TreeVo();
				for (String menuCode : menuCodes) {
					if(menu.getFunctionCode().equals(menuCode)){
						childModel.setChecked(true);
						continue;
					}
				}
				childModel.setId(menu.getId() + "");
				childModel.setText(menu.getFunctionName());
				childModel.setSortIndex(menu.getOrd() == null ? Long.MAX_VALUE : Long.parseLong(menu.getOrd() + ""));
				Map attributes = new HashMap();
//				childModel.setCode(menu.getFunctionCode());
				attributes.put("code",menu.getFunctionCode());
				childModel.setAttributes(attributes);
				
				childrenModel.add(childModel);
				iterator.remove();
				Collections.sort(childrenModel);
				treeDataJson.setChildren(childrenModel);
			}
		}
		if (childrenModel != null && !childrenModel.isEmpty() && !menus.isEmpty()) {
			Iterator treeIt = childrenModel.iterator();
			while (treeIt.hasNext()) {
				TreeVo model = (TreeVo) treeIt.next();
				iterateList(model, menus);
			}
		}
	}

	private void getSubTrees(Set<Function> subTrees, Function fun) {
		Set<Function> childs = fun.getChildren();
		List<Function> permissionss = new ArrayList<Function>();
		for (Function f : childs) {
			if (!f.getType().equals("4")) {
				if (f.getParent().getId() == fun.getId()) {
					permissionss.add(f);
				}
			}
		}
		if (permissionss != null && permissionss.size() > 0) {
			subTrees.addAll(permissionss);
			for (Object object : permissionss) {
				Function fun2 = (Function) object;
				getSubTrees(subTrees, fun2);
			}
		}
	}
	

	
*/}
