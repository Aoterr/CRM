package com.zendaimoney.crm.file.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zendaimoney.crm.BaseService;
import com.zendaimoney.crm.file.entity.BusiFile;
import com.zendaimoney.crm.file.repository.BusiFileDao;

/**
 * 
 * @author zhanghao
 * @create 2012-11-22, 上午11:11:12
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class BusiFileService   extends BaseService<BusiFile>{

	@Autowired
	private BusiFileDao busiFileDao;

	@Transactional
	public Long saveFile(BusiFile entity) {
		return busiFileDao.save(entity).getId();
	}

	@Transactional
	public void deleteFile(Long id) {
		busiFileDao.delete(id);
	}

	@Transactional(readOnly = false)
	public void deleteFile(BusiFile file) {
		busiFileDao.delete(file);
	}

	@Transactional(readOnly = false)
	public BusiFile findBusiFileById(Long id) {
		return busiFileDao.findOne(id);
	}

	@Transactional
	public boolean updateFileListById(String files, Long attachmentId) {
		return busiFileDao.updateFileListById(files, attachmentId);
	}

	public List<BusiFile> findAllByAttachmentId(Long attachmentId) {
		// return busiFileDao.findAllByAttachmentId(attachmentId);
		return busiFileDao.findAllByAttachmentIdAndBusiFilesState(attachmentId);
	}

	/**
	 * 更新文件状态
	 * 
	 * @author Yuan Changchun
	 * @date 2013-10-8 下午01:45:45
	 * @param busiFile
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean updateFileFState(BusiFile busiFile, String fState) {
		busiFile.setfState("0");
		return busiFileDao.save(busiFile) == null ? false : true;
		// try {
		// busiFileDao.save(busiFile);
		// return true;
		// } catch (Exception e) {
		// e.printStackTrace();
		// return false;
		// }
	}
	
	
	/** 
	* @Title: 根据 出借号查找文件 
	* @Description: 根据 出借号查找文件 
	* @author wb_lyq
	* @date   2015-09-24  10:11:11
	* @param  feLendNo 文件出借编号使用英文,分割的字符串
	* 
	*/
	public List<BusiFile> findFileByFeLendNo(String feLendNo) {
		StringBuffer searchBaseSQL = new StringBuffer();
		searchBaseSQL
	.append("SELECT D FROM   BusiFile  D , BusiAttachment C, BusiRequestAttach  B   , BusiFinance A  where D.fAttachment.id=C.id  AND C.id= B.busiAttachment.id  AND B.busiFinance.id= A.id  AND   A.feValidState='1' AND  C.atState='1' AND  A.feLendNo IN (")
				.append(stateDeal(feLendNo)).append(")");
		return getContent(searchBaseSQL.toString());
	}
	/** 
	* @Title: 根据   一级二级文件类型  查找文件
	* @Description: 根据   一级二级文件类型  查找文件
	* @author wb_lyq
	* @date   2015-09-24  10:11:11
	* @param  atType  文件的二级类型
	* @param  atType  文件的二级类型使用英文,分割的字符串
	*/
	public List<BusiFile>findFileByAtType (String atType,String atTypeOne) {
		StringBuffer searchBaseSQL = new StringBuffer();
		// 根据条件查询
		searchBaseSQL
	.append("SELECT D FROM   BusiFile  D , BusiAttachment C, BusiRequestAttach  B   , BusiFinance A  where D.fAttachment.id=C.id  AND C.id= B.busiAttachment.id  AND B.busiFinance.id= A.id AND   A.feValidState='1' AND  C.atState='1' AND  C .atType  IN (")
				.append(stateDeal(atType)).append(")").append(" and C .atTypeOne =").append("'")
				.append(atTypeOne).append("'");
		System.out.println(searchBaseSQL.toString());
		return getContent(searchBaseSQL.toString());
	}
     //  
	/** 
	* @Title: 根据   一级二级文件类型和出借号查找文件
	* @Description: 根据   一级二级文件类型和出借号查找文件
	* @author wb_lyq
	* @date   2015-09-24  10:11:11
	* @param  feLendNo   文件出借编号使用英文,分割的字符串
	* @param  atType     文件的二级类型使用英文,分割的字符串
	* @param  atTypeOne  文件的一级类型
	*/
	public List<BusiFile> findFileByFeLendNoAndType(String feLendNo, String atType, String atTypeOne) {
		StringBuffer searchBaseSQL = new StringBuffer();
		searchBaseSQL.append("SELECT D FROM   BusiFile  D , BusiAttachment C, BusiRequestAttach  B   , BusiFinance A  where D.fAttachment.id=C.id  AND C.id= B.busiAttachment.id  AND B.busiFinance.id= A.id AND   A.feValidState='1' AND  C.atState='1' AND  C .atType  IN (")
					.append(stateDeal(atType)).append(")")
					.append(" and C .atTypeOne =").append("'").append(atTypeOne).append("'")
					.append(" and A .feLendNo IN ( ").append(stateDeal(feLendNo)).append(")");
			System.out.println(searchBaseSQL.toString());
		return getContent(searchBaseSQL.toString());
	}

	/**
	 * 更新文件展示的顺序
	 * 
	 * @author Yuan Changchun
	 * @date 2013-10-21 上午09:33:40
	 * @param keyList
	 * @param valueList
	 */
	public boolean updateFileOrder(List<Integer> keyList, List<Long> valueList) {
		try {
			for (int i = 0; i < valueList.size(); i++) {
				valueList.get(i);
				BusiFile busiFile = busiFileDao.findOne(valueList.get(i));
				busiFile.setfOrderNo(keyList.get(i));
				busiFileDao.save(busiFile);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	/** 
	* @Title: stateDeal 
	* @Description: 状态str处理
	* @return String    返回类型 
	*/
	private String stateDeal(String fState){
		String[] strState=fState.split(",");
		String dealState="";
		for(int i=0;i<strState.length;i++){
			dealState+="'"+strState[i]+"',";
		}
		dealState=dealState.substring(0,dealState.length()-1);
		return dealState;
	}
	
	/** 
	* @Title: 根据 出借号查找文件 
	* @Description: 根据 出借号查找文件 
	* @author CJ
	* @date   2015-12-10 10:11:11
	* @param  feLendNo 文件出借编号使用英文,分割的字符串
	* 
	*/
	public List<BusiFile> findFileByFeLendNoForFund(String feLendNo) {
		StringBuffer searchBaseSQL = new StringBuffer();
		searchBaseSQL
	.append("SELECT D FROM BusiFile D , BusiAttachment C, BusiRequestAttach  B , BusiFundInfo A  where D.fAttachment.id=C.id  AND C.id= B.busiAttachment.id  AND B.busiFundInfo.id= A.id  AND   A.fdValidState='1' AND  C.atState='1' AND  A.fdLendNo IN (")
				.append(stateDeal(feLendNo)).append(")");
		return getContent(searchBaseSQL.toString());
	}
	/** 
	* @Title: 根据   一级二级文件类型  查找文件
	* @Description: 根据   一级二级文件类型  查找文件
	* @author CJ
	* @date   2015-12-10  10:11:11
	* @param  atType  文件的二级类型
	* @param  atType  文件的二级类型使用英文,分割的字符串
	*/
	public List<BusiFile>findFileByAtTypeForFund (String atType,String atTypeOne) {
		StringBuffer searchBaseSQL = new StringBuffer();
		// 根据条件查询
		searchBaseSQL
	.append("SELECT D FROM   BusiFile  D , BusiAttachment C, BusiRequestAttach  B , BusiFundInfo A  where D.fAttachment.id=C.id  AND C.id= B.busiAttachment.id  AND B.busiFundInfo.id= A.id AND   A.fdValidState='1' AND  C.atState='1' AND  C .atType  IN (")
				.append(stateDeal(atType)).append(")").append(" and C .atTypeOne =").append("'")
				.append(atTypeOne).append("'");
		System.out.println(searchBaseSQL.toString());
		return getContent(searchBaseSQL.toString());
	}
     //  
	/** 
	* @Title: 根据   一级二级文件类型和出借号查找文件
	* @Description: 根据   一级二级文件类型和出借号查找文件
	* @author CJ
	* @date   2015-12-10  10:11:11
	* @param  feLendNo   文件出借编号使用英文,分割的字符串
	* @param  atType     文件的二级类型使用英文,分割的字符串
	* @param  atTypeOne  文件的一级类型
	*/
	public List<BusiFile> findFileByFeLendNoAndTypeForFund(String feLendNo, String atType, String atTypeOne) {
		StringBuffer searchBaseSQL = new StringBuffer();
		searchBaseSQL.append("SELECT D FROM   BusiFile  D , BusiAttachment C, BusiRequestAttach  B , BusiFundInfo A  where D.fAttachment.id=C.id  AND C.id= B.busiAttachment.id  AND B.busiFundInfo.id= A.id AND   A.fdValidState='1' AND  C.atState='1' AND  C .atType  IN (")
					.append(stateDeal(atType)).append(")")
					.append(" and C .atTypeOne =").append("'").append(atTypeOne).append("'")
					.append(" and A .fdLendNo IN ( ").append(stateDeal(feLendNo)).append(")");
			System.out.println(searchBaseSQL.toString());
		return getContent(searchBaseSQL.toString());
	}

}
