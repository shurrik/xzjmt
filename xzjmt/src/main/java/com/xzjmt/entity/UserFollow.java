package com.xzjmt.entity;

import java.util.Date;

import com.xzjmt.common.entity.BaseEntity;

public class UserFollow extends BaseEntity implements java.io.Serializable{

	private Integer id;
	private Integer userId;
	private String email;
	private String nickName;
	private Integer followId;
	private String followEmail;
	private String followName;
	private Date createDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Integer getFollowId() {
		return followId;
	}
	public void setFollowId(Integer followId) {
		this.followId = followId;
	}
	public String getFollowEmail() {
		return followEmail;
	}
	public void setFollowEmail(String followEmail) {
		this.followEmail = followEmail;
	}
	public String getFollowName() {
		return followName;
	}
	public void setFollowName(String followName) {
		this.followName = followName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
