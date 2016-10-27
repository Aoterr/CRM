package com.zendaimoney.crm.attachment.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.ResultVo;

import com.zendaimoney.crm.attachment.entity.BusiAttachment;
import com.zendaimoney.crm.attachment.repository.BusiAttachmentDao;
import com.zendaimoney.crm.attachment.repository.BusiRequestAttachDao;
import com.zendaimoney.crm.modification.repository.ModificationDao;

/**
 * 附件服务层
 * 
 * @author zhanghao
 * @create 2012-11-20, 下午04:15:30
 * @version 1.0
 */
@Component
@Transactional(readOnly = true)
public class BusiAttachmentService {

	@Autowired
	private BusiAttachmentDao attachmentDao;

	@Autowired
	private BusiRequestAttachDao requestAttachDao;

	@Autowired
	private ModificationDao modificationDao;

	/**
	 * 保存附件
	 * 
	 * @author zhanghao
	 * @date 2012-11-20,下午04:29:02
	 * @param entity
	 */
	@Transactional
	public BusiAttachment saveAttachment(BusiAttachment attachment) {
		return attachmentDao.save(attachment);
	}

	/**
	 * 保存附件信息,并且更新附件所属的文件的外键
	 * 
	 * @author zhanghao
	 * @date 2012-11-27,下午01:58:25
	 * @param attachment
	 * @param files
	 * @return
	 */
	@Transactional
	public boolean saveAttachmentAndUpdateFile(BusiAttachment attachment,
			String[] files) {
		boolean flag = false;
		try {
			saveAttachment(attachment);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 根据id查找单个附件
	 * 
	 * @author zhanghao
	 * @date 2012-11-27,下午01:58:54
	 * @param id
	 * @return
	 */
	public BusiAttachment findBusiAttachmentById(Long id) {
		return attachmentDao.findOne(id);
	}

	/**
	 * 查询所有的附件
	 * 
	 * @author zhanghao
	 * @date 2012-11-20,下午04:29:12
	 * @return
	 */
	public List<BusiAttachment> getAllBusiAttachment() {
		return (List<BusiAttachment>) attachmentDao.findAll();
	}

	/**
	 * 根据业务查询所有附件
	 * 
	 * @author zhanghao
	 * @date 2013-03-21 09:53:34
	 * @param type
	 *            业务类型
	 * @param id
	 *            业务id
	 * @return
	 */
	public List<BusiAttachment> findAllAttachment(String type, Long id) {
		List<Long> ids = findAllAttachmentIdByBusiId(type, id);
		List<BusiAttachment> list = null;
		List<BusiAttachment> resultList = new ArrayList<BusiAttachment>();
		if (ids.size() > 0) {
			list = attachmentDao.findByIdIn(ids);
		} else {
			list = new ArrayList<BusiAttachment>();
		}
		for (BusiAttachment busiAttachment : list) {
			if (busiAttachment.getAtState().equals("1")) {
				resultList.add(busiAttachment);
			}
		}
		return resultList;
	}

	/**
	 * 查询业务申请单附件和变更单附件
	 * 
	 * @author zhanghao
	 * @date 2013-6-9 下午02:11:47
	 * @param type
	 * @param id
	 * @return
	 */
	public List<BusiAttachment> findBusiAttaAndModifiAtta(String type, Long id) {
		// 查询当前业务的所有的附件ID
		List<Long> busiIds = requestAttachDao.findAllRequestAttach(id);
		// 查询当前业务所有变更单的附件ID
		List<Long> modificationIds = modificationDao.findAllIdByMnSourceId(id);
		if (modificationIds.size() > 0) {
			modificationIds = requestAttachDao
					.findAllRequestAttach(modificationIds);
		}

		List<Long> ids = new ArrayList<Long>();
		ids.addAll(busiIds);
		if (modificationIds.size() > 0) {
			ids.addAll(modificationIds);
		}
		return ids.size() > 0 ? attachmentDao.findByIdIn(ids)
				: new ArrayList<BusiAttachment>();

	}

	/**
	 * 根据业务ID查询所有附件的ID
	 * 
	 * @author zhanghao
	 * @date 2013-6-7 下午03:50:52
	 * @param type
	 *            附件类型
	 * @param id
	 *            业务ID
	 * @return
	 */
	public List<Long> findAllAttachmentIdByBusiId(String type, Long id) {
		return requestAttachDao.findAllRequestAttach(type, id);
	}

	/**
	 * 构建附件信息
	 * 
	 * @author zhanghao
	 * @date 2012-11-21,下午03:32:59
	 * @param attachment
	 *            附件对象
	 * @param isSave
	 *            判断是新增还是修改,true:新增,false:修改
	 */
	public void builderBusiAttachment(BusiAttachment attachment, boolean isSave,Long sysuserId) {
		Date date = new Date();
		//long operationId = AuthorityHelper.getStaff().getId();
		// 新增
		if (isSave) {
			attachment.setAtCratetime(date);
			attachment.setAtCreateid(sysuserId);
			attachment.setAtState("1");
		}
		attachment.setAtModifyid(sysuserId);
		attachment.setAtModifytime(date);
	}

	/**
	 * 根据客户信息和业务类型查找附件列表
	 * 
	 * @author Yuan Changchun
	 * @date 2013-7-15 下午04:46:57
	 * @param customerid
	 * @param busiType
	 * @return
	 */
	public List<BusiAttachment> findAllAttachmentByCusIdAndBusiType(
			Long customerid, String busiType) {
		if (busiType != null && busiType.equals("0")) {
			return this.attachmentDao.findAllAttachmentByCusId(customerid);
		}
		return null;
	}

	/**
	 * 更新单个附件基本信息
	 * 
	 * @author Yuan Changchun
	 * @date 2013-10-9 下午05:36:27
	 * @param attachmentId
	 * @param atName
	 * @param atMemo
	 * @return
	 */
	@Transactional(readOnly = false)
	public ResultVo updateOne(Long attachmentId, String atName, String atMemo,Long sysuserId) {
		ResultVo resultVo = new ResultVo(false);
		try {
			BusiAttachment busiAttachment = attachmentDao.findOne(attachmentId);
			busiAttachment.setAtName(atName);
			busiAttachment.setAtMemo(atMemo);
			busiAttachment.setAtModifytime(new Date());
			busiAttachment.setAtModifyid(sysuserId);
			attachmentDao.save(busiAttachment);
			resultVo = new ResultVo(true);
		} catch (Exception e) {
			e.printStackTrace();
			resultVo = new ResultVo(false, e.getMessage() + e.getCause());
		}
		return resultVo;
	}

}
