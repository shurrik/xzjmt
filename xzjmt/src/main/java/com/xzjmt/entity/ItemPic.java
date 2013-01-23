package com.xzjmt.entity;

import java.util.Date;

import com.xzjmt.common.entity.BaseEntity;

public class ItemPic extends BaseEntity implements java.io.Serializable{

	private Integer id;
	private Integer itemId;
	private String picUrl;
	private String picUrlSmall;
	private Date createDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getPicUrlSmall() {
		return picUrlSmall;
	}
	public void setPicUrlSmall(String picUrlSmall) {
		this.picUrlSmall = picUrlSmall;
	}
}
