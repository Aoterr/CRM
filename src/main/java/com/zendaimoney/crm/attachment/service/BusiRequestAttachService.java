package com.zendaimoney.crm.attachment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.crm.attachment.entity.BusiAttachment;
import com.zendaimoney.crm.attachment.entity.BusiRequestAttach;
import com.zendaimoney.crm.attachment.repository.BusiAttachmentDao;
import com.zendaimoney.crm.attachment.repository.BusiRequestAttachDao;
import com.zendaimoney.crm.file.entity.BusiFile;
import com.zendaimoney.crm.file.repository.BusiFileDao;

/**
 * 
 * @author zhanghao
 * @create 2012-11-26, 下午02:46:43
 * @version 1.0
 */
@Service
public class BusiRequestAttachService {
	@Autowired
	private BusiRequestAttachDao requestAttachDao;

	@Autowired
	private BusiAttachmentDao busiAttachmentDao;

	@Autowired
	private BusiFileDao busiFileDao;

	public BusiRequestAttach saveRequestAttach(BusiRequestAttach requestAttach) {
		return requestAttachDao.save(requestAttach);
	}

	public BusiRequestAttach findBusiRequestAttachById(Long id) {
		return requestAttachDao.findOne(id);
	}

	/**
	 * 保存业务申请附件
	 * 
	 * @author zhanghao
	 * @date 2012-11-27,下午03:04:40
	 * @param requestAttach
	 *            业务申请附件对象
	 * @param busiAttachment
	 *            附件对象
	 * @param files
	 *            文件对象的主键id
	 * @return 是否保存成功
	 */
	@Transactional()
	public boolean saveRequestAttach(BusiRequestAttach requestAttach,
			BusiAttachment busiAttachment, String[] files) {
		boolean flag = false;
		try {
			busiAttachment = busiAttachmentDao.save(busiAttachment);// 保存附件对象
			requestAttach.setRaAttachment(busiAttachment.getId());// 设置业务申请附件关系的附件id
			requestAttachDao.save(requestAttach);// 保存业务申请附件关系
			if (files != null) {
				for (String val : files) {
					BusiFile busiFile = busiFileDao.findOne(new Long(val));
					busiFile.setfAttachment(busiAttachment);
					busiFileDao.save(busiFile);
				}
			}
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 保存业务附件关系及更新附件文件关联信息
	 * 
	 * @author Yuan Changchun
	 * @date 2013-9-26 下午03:25:21
	 * @param busiRequestAttach
	 * @param busiAttachment
	 * @param busiFileId
	 */
	public boolean saveOneRequestAttach(BusiRequestAttach busiRequestAttach,
			BusiAttachment busiAttachment, Long busiFileId) {
		boolean flag = false;
		try {

			List<BusiAttachment> busiAttachmentList = busiAttachmentDao
					.findAllAttachmentByTypeAndName(
							busiRequestAttach.getRaBusi(),
							busiAttachment.getAtTypeOne(),
							busiAttachment.getAtType(),
							busiAttachment.getAtName());
			if (busiAttachmentList.size() != 0) {
				BusiAttachment attachment = busiAttachmentList.get(0);
				BusiFile busiFile = busiFileDao.findOne(busiFileId);
				busiFile.setfOrderNo(busiFileDao.findMaxFOrderNo(attachment
						.getId()) + 1);
				busiFile.setfAttachment(attachment);
				busiFileDao.save(busiFile);
			} else {
				busiAttachment = busiAttachmentDao.save(busiAttachment);// 保存附件对象
				busiRequestAttach.setRaAttachment(busiAttachment.getId());// 设置业务申请附件关系的附件id
				requestAttachDao.save(busiRequestAttach);// 保存业务申请附件关系
				BusiFile busiFile = busiFileDao.findOne(busiFileId);
				busiFile.setfAttachment(busiAttachment);
				busiFile.setfOrderNo(1);
				busiFileDao.save(busiFile);
			}

			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
