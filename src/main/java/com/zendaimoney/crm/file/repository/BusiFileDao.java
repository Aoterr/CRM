package com.zendaimoney.crm.file.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.crm.file.entity.BusiFile;
import com.zendaimoney.crm.product.entity.BusiFinance;

/**
 * 处理文件的DAO
 * 
 * @author zhanghao
 * @create 2012-11-22, 上午11:09:00
 * @version 1.0
 */
public interface BusiFileDao extends
		PagingAndSortingRepository<BusiFile, Long>,
		JpaSpecificationExecutor<BusiFile> {

	/**
	 * 更新文件的附件指引
	 * 
	 * @author zhanghao
	 * @date 2012-11-27,下午01:53:55
	 * @param files
	 * @param attachmentId
	 * @return
	 */
	@Query("update BusiFile bf set bf.fAttachment.id = :attachmentId where id in (:files) ")
	public boolean updateFileListById(String files, Long attachmentId);

	@Query(" from BusiFile bf where bf.fAttachment.id = ?")
	public List<BusiFile> findAllByAttachmentId(Long attachmentId);

	/**
	 * 根据附件ID和文件状态查找文件列表
	 * 
	 * @author Yuan Changchun
	 * @date 2013-10-8 上午11:52:28
	 * @param attachmentId
	 * @return
	 */
	@Query(" from BusiFile bf where bf.fAttachment.id = ? and bf.fState='1' order by bf.fOrderNo ")
	public List<BusiFile> findAllByAttachmentIdAndBusiFilesState(
			Long attachmentId);

	/**
	 * 更新文件状态
	 * 
	 * @author Yuan Changchun
	 * @date 2013-10-8 下午01:47:47
	 * @param id
	 * @param fState
	 * @return
	 */
	@Query("update BusiFile bf set bf.fState = :fState where bf.id = :fileId ")
	public boolean updateFileFStateById(String fState, Long fileId);

	/**
	 * 根据附件ID查找附件文件顺序的最大值
	 * 
	 * @author Yuan Changchun
	 * @date 2013-10-17 下午02:49:03
	 * @param id
	 */
	@Query(" select max(bf.fOrderNo) from BusiFile bf where bf.fAttachment.id = ? ")
	public Integer findMaxFOrderNo(Long busiAttachmentId);
	
	//  根据 一级和二级文件类型  查找文件 id
	@Query(" select bf from BusiFile bf,BusiAttachment ba  where  ba.atType= :atType  and ba.atTypeOne= :atTypeOne   and   bf.fAttachment.id = ba.id")
	public List<BusiFile> findFileByAtTypeOneAndAtType(String atType ,String atTypeOne);
	

    //  根据 一级和二级文件类型 和 出借号  查找文件 id
	@Query("SELECT A FROM BusiFinance A, BusiRequestAttach  B , BusiAttachment C  ,BusiFile  D   where  A.id=B.busiFinance.id and B.busiAttachment.id =C.id  and C.id=  D.fAttachment.id   and   A.feLendNo = :feLendNo  and  C.atType = :atType  and C.atTypeOne= :atTypeOne ")
	public List<BusiFile> findFileByAtTypeOneAndAtTypeAndlendNo(String feLendNo,String atType ,String atTypeOne);

	//  根据出借号查找文件
	@Query("SELECT A FROM BusiFinance A, BusiRequestAttach  B , BusiAttachment C  ,BusiFile  D   where  A.id=B.busiFinance.id and B.busiAttachment.id =C.id  and C.id=  D.fAttachment.id   and   A.feLendNo = :feLendNo")
	public List<BusiFile> findFileByLendNo(String feLendNo);
	

	
	
	
}
