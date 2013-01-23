package com.xzjmt.common.util.enumeration;

public enum UserType {
	MEMBER(0, "会员"),
	ADMIN(1, "管理员");

	private int value;
	private String label;
	
	private UserType(int value, String label) {
		this.value = value;
		this.label = label;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}