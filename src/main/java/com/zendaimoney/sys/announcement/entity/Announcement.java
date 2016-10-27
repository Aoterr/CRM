package com.zendaimoney.sys.announcement.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springside.modules.orm.IdEntity;
import com.zendaimoney.utils.ConfigurationHelper;


/**
 * 
 * @author bianxj
 * 
 */
@Entity
@Table(name = "sys_Announcement")
public class Announcement extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6773301277082950922L;
	public static final String EXIST = "1";
	public static final String NOEXIST = "0";

	public static final String PUBLIC = "1";
	public static final String NEWS = "2";
	public static final String emergency = "3";
	public static final String message = "4";

	private String title;
	private String content;
	private Date beginDate;//开始时间
	private Date endDate;//结束时间
	private Date createDate;//创建时间
	private Long creator;//创建人
	
	private String pubMan;//公告来源

	private String status;

	private String announcementType;
	private String openLogin;

	private Date pubDate;
	public boolean hasNew;

	private Long modifyPerson;//修改人
	private Date modifyDate;//修改时间
	
	@Transient
	public boolean isHasNew() {
		Date now = new Date();

		Long time = 0l;

		time = now.getTime() - pubDate.getTime();

		if (time < 3600 * 24 * 1000 * ConfigurationHelper.getInteger("announcmentshownewdays"))
			return true;

		return false;
	}

	@Transient
	public String getOpenLoginStr() {
		if (openLogin != null && openLogin.trim().equals(EXIST))
			return "是";
		return "";
	}

	@Transient
	public String getAnnouncementTypeStr() {
		if (announcementType.trim().equals(PUBLIC))
			return "公司通知";
		if (announcementType.trim().equals(NEWS))
			return "新闻动态";
		if (announcementType.trim().equals(emergency))
			return "紧急消息";
		if (announcementType.trim().equals(message))
			return "系统消息";
		return "";
	}

	@Transient
	public String getStatusStr() {
		if (status.trim().equals(EXIST))
			return "启用";
		if (status.trim().equals(NOEXIST))
			return "禁用";
		return "";
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Date getPubDate() {
		if (createDate.after(beginDate) && createDate.before(endDate))
			return createDate;

		return beginDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getAnnouncementType() {
		return announcementType;
	}

	public void setAnnouncementType(String announcementType) {
		this.announcementType = announcementType;
	}

	public String getOpenLogin() {
		return openLogin;
	}

	public void setOpenLogin(String openLogin) {
		this.openLogin = openLogin;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Lob
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPubMan() {
		return pubMan;
	}

	public void setPubMan(String pubMan) {
		this.pubMan = pubMan;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getModifyPerson() {
		return modifyPerson;
	}

	public void setModifyPerson(Long modifyPerson) {
		this.modifyPerson = modifyPerson;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

}
