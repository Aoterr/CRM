package com.zendaimoney.crm.file.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springside.modules.orm.IdEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zendaimoney.crm.attachment.entity.BusiAttachment;

@Entity
@Table(name = "BUSI_FILE")
public class BusiFile extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = -1472577059570632046L;
	/**
	 * 文件名
	 */
	private String fName;
	/**
	 * 文件类型
	 */
	private String fType;
	/**
	 * 文件大小
	 */
	private Long fSize;
	/**
	 * 保存名称
	 */
	private String fSaveName;
	/**
	 * 保存路径
	 */
	private String fPath;
	/**
	 * 创建时间
	 */
	private Date fCratetime;
	/**
	 * 创建人
	 */
	private Long fCreateid;
	/**
	 * 最后修改时间
	 */
	private Date fModifytime;
	/**
	 * 最后修改人
	 */
	private Long fModifyid;
	/**
	 * 归档时间
	 */
	private Date fPigeonholeTime;
	/**
	 * 归档人
	 */
	private Long fPigeonholeId;
	/**
	 * 状态
	 */
	private String fState;
	/**
	 * 备注
	 */
	private String fMemo;
	/**
	 * 附件
	 */
	private BusiAttachment fAttachment;

	/**
	 * 附件顺序
	 */
	private Integer fOrderNo;

	public Integer getfOrderNo() {
		return fOrderNo;
	}

	public void setfOrderNo(Integer fOrderNo) {
		this.fOrderNo = fOrderNo;
	}

	public BusiFile() {
	}

	public BusiFile(Long id) {
		this.id = id;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getfType() {
		return fType;
	}

	public void setfType(String fType) {
		this.fType = fType;
	}

	public Long getfSize() {
		return fSize;
	}

	public void setfSize(Long fSize) {
		this.fSize = fSize;
	}

	public String getfSaveName() {
		return fSaveName;
	}

	public void setfSaveName(String fSaveName) {
		this.fSaveName = fSaveName;
	}

	public String getfPath() {
		return fPath;
	}

	public void setfPath(String fPath) {
		this.fPath = fPath;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getfCratetime() {
		return fCratetime;
	}

	public void setfCratetime(Date fCratetime) {
		this.fCratetime = fCratetime;
	}

	public Long getfCreateid() {
		return fCreateid;
	}

	public void setfCreateid(Long fCreateid) {
		this.fCreateid = fCreateid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getfModifytime() {
		return fModifytime;
	}

	public void setfModifytime(Date fModifytime) {
		this.fModifytime = fModifytime;
	}

	public Long getfModifyid() {
		return fModifyid;
	}

	public void setfModifyid(Long fModifyid) {
		this.fModifyid = fModifyid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getfPigeonholeTime() {
		return fPigeonholeTime;
	}

	public void setfPigeonholeTime(Date fPigeonholeTime) {
		this.fPigeonholeTime = fPigeonholeTime;
	}

	public Long getfPigeonholeId() {
		return fPigeonholeId;
	}

	public void setfPigeonholeId(Long fPigeonholeId) {
		this.fPigeonholeId = fPigeonholeId;
	}

	public String getfState() {
		return fState;
	}

	public void setfState(String fState) {
		this.fState = fState;
	}

	public String getfMemo() {
		return fMemo;
	}

	public void setfMemo(String fMemo) {
		this.fMemo = fMemo;
	}

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "F_ATTACHMENT")
	public BusiAttachment getfAttachment() {
		return fAttachment;
	}

	public void setfAttachment(BusiAttachment fAttachment) {
		this.fAttachment = fAttachment;
	}

}