package com.zendaimoney.sys.announcement.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.crm.sysuser.entity.SysUser;
import com.zendaimoney.sys.announcement.dao.AnnouncementDao;
import com.zendaimoney.sys.announcement.entity.Announcement;
import com.zendaimoney.uc.rmi.vo.Staff;
import com.zendaimoney.utils.ConfigurationHelper;

@Component
@Transactional(readOnly = true)
public class AnnouncementService {
	@Autowired
	private AnnouncementDao announcementDao;
	@PersistenceContext
	private EntityManager em;

	public Announcement getAnnouncement(Long id) {
		return announcementDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public void saveAnnouncement(Announcement entity) {
		announcementDao.save(entity);
	}

	@Transactional(readOnly = false)
	public void deleteAnnouncement(Long id) {
		announcementDao.delete(id);
	}

	@Transactional(readOnly = false)
	public void deleteAnnouncement(Long[] ids) {
		for (Long id : ids) {
			announcementDao.delete(id);
		}
	}

	public List<Announcement> getAllAnnouncement() {
		return (List<Announcement>) announcementDao.findAll();
	}

	public Page<Announcement> getAnnouncement(PageRequest pageRequest) {
		return announcementDao.findAll(pageRequest);
	}

	public List<Announcement> searchAnnouncementBydisplay() {
		 CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
	        CriteriaQuery<Announcement> criteriaQuery = criteriaBuilder
	                .createQuery(Announcement.class);
	        Root<Announcement> announcementRoot = criteriaQuery.from(Announcement.class);
	        criteriaQuery.select(announcementRoot);
	        Path<Date> beginDate = announcementRoot.get("beginDate");
			Path<Date> endDate = announcementRoot.get("endDate");
			criteriaQuery.where(criteriaBuilder.equal(announcementRoot.get("status"), Announcement.EXIST),
					criteriaBuilder.lessThanOrEqualTo(beginDate, new Date()),
					criteriaBuilder.greaterThanOrEqualTo(endDate, new Date())).orderBy(
							criteriaBuilder.desc(announcementRoot.get("pubDate")));
	        TypedQuery<Announcement> tq = em.createQuery(criteriaQuery);
	        tq.setMaxResults(ConfigurationHelper.getInteger("announcmentshowindex"));
	        return tq.getResultList();

	        
	        
		/*return announcementDao.findAll(new Specification<Announcement>() {

			@Override
			public Predicate toPredicate(Root<Announcement> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Path<Date> beginDate = root.get("beginDate");
				Path<Date> endDate = root.get("endDate");
				query.where(cb.equal(root.get("status"), Announcement.EXIST),
						cb.lessThanOrEqualTo(beginDate, new Date()),
						cb.greaterThanOrEqualTo(endDate, new Date())).orderBy(
						cb.desc(root.get("pubDate")));
				return null;
			}

		});*/
	}

	public Page<Announcement> getAnnouncement(Specification<Announcement> spec,
			PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return announcementDao.findAll(spec, pageRequest);
	}

	@Transactional(readOnly = false)
	public Announcement saveNewAnnouncement(Announcement announcement,SysUser sysuser) {
		// TODO Auto-generated method stub
		Date now = new Date();
		if (announcement.getId() == null) {
			announcement.setCreateDate(now);
			announcement.setCreator(sysuser.getId());
			return announcementDao.save(announcement);
		} else {
			Announcement announcements = this.getAnnouncement(announcement
					.getId());
			Announcement newAnnouncement = new Announcement();
			BeanUtils.copyProperties(announcements, newAnnouncement);
			newAnnouncement.setTitle(announcement.getTitle());
			newAnnouncement.setBeginDate(announcement.getBeginDate());
			newAnnouncement.setEndDate(announcement.getEndDate());
			newAnnouncement.setContent(announcement.getContent());
			newAnnouncement.setPubMan(announcement.getPubMan());
			newAnnouncement.setStatus(announcement.getStatus());
			newAnnouncement.setAnnouncementType(announcement
					.getAnnouncementType());
			newAnnouncement.setOpenLogin(announcement.getOpenLogin());
			newAnnouncement.setOptlock(announcement.getOptlock());
			newAnnouncement.setModifyDate(now);
			newAnnouncement.setModifyPerson(sysuser.getId());
			return announcementDao.save(newAnnouncement);
		}
	}


}
