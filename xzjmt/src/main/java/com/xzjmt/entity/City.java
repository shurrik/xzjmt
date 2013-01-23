package com.xzjmt.entity;

import com.xzjmt.common.entity.BaseEntity;

public class City extends BaseEntity implements java.io.Serializable{

	private Integer cityId;
	private String cityName;
	private String ipName;
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getIpName() {
		return ipName;
	}
	public void setIpName(String ipName) {
		this.ipName = ipName;
	}
}
