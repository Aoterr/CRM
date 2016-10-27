
package com.zendaimoney.crm.index.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.zendaimoney.crm.index.vo.TreeVo;
import com.zendaimoney.uc.rmi.vo.Function;
/**
 * Bianxj
 */
@Component
@Transactional(readOnly = true)
public class IndexService {
	@Transactional(readOnly = true)
	public List<TreeVo> findMenus(Long parentId, Set<Function> subMenus,
			Function topTree) {
		TreeVo treeDataJson = new TreeVo();
		treeDataJson.setId(parentId + "");
		iterateList(treeDataJson, subMenus);
		List treeDatas = treeDataJson.getChildren();
		if (null == treeDatas) {
			treeDatas = new ArrayList();
		}
		return treeDatas;
	}
	@Transactional(readOnly = true)
	private void iterateList(TreeVo treeDataJson, Set<Function> menus) {
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
				childModel.setId(menu.getId() + "");
				childModel.setText(menu.getFunctionName());
				childModel
						.setSortIndex(menu.getOrd() == null ? Long.MAX_VALUE
								: Long.parseLong(menu.getOrd() + ""));
				Map attributes = new HashMap();
				attributes.put("url", menu.getUrl());
				childModel.setAttributes(attributes);
				childrenModel.add(childModel);
				iterator.remove();
				Collections.sort(childrenModel);
				treeDataJson.setChildren(childrenModel);
			}
		}
		if (childrenModel != null && !childrenModel.isEmpty()
				&& !menus.isEmpty()) {
			Iterator treeIt = childrenModel.iterator();
			while (treeIt.hasNext()) {
				TreeVo model = (TreeVo) treeIt.next();
				iterateList(model, menus);
			}
		}
	}

	 
}
