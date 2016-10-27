package com.zendaimoney.sys.announcement.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zendaimoney.sys.announcement.entity.Announcement;

public interface AnnouncementDao extends PagingAndSortingRepository<Announcement, Long>,JpaSpecificationExecutor<Announcement>{
//	public void delete(final Long[] ids) {
//		Assert.notNull(ids, "id不能为空");
//		//List<SsUser> entitys = new ArrayList<SsUser>();
//		for (int i = 0; i < ids.length; i++) {
//			this.delete(ids[i]);
//
//		}
//	}
	
	/**
	 * 获取系统消息
	 * 
	 * @param type
	 *            消息类型
	 * @param endNo结束数据
	 * @param startNo
	 *            开始数据
	 * @return
	 */
	@Query(nativeQuery = true, value = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (select sa.title,sa.content,to_char(sa.create_date,'yyyy-MM-dd HH24:MI:SS') as create_date  from SYS_ANNOUNCEMENT sa where sa.status = '1' and sa.ANNOUNCEMENT_TYPE= :type) A WHERE ROWNUM < :endNo order by a.create_date desc) WHERE RN >= :startNo")
	public List<Object[]> findAnnouncement(@Param("endNo") int endNo, @Param("startNo") int startNo, @Param("type") String type);



}
