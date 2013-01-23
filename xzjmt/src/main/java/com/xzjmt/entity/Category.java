package com.xzjmt.entity;

import com.xzjmt.common.entity.BaseEntity;

public class Category extends BaseEntity implements java.io.Serializable{

	private Integer catId;
	private String catName;
	private Integer order;
	public Integer getCatId() {
		return catId;
	}
	public void setCatId(Integer catId) {
		this.catId = catId;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
}
