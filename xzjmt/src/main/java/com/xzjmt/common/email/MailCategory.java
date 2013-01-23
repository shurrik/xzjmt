package com.xzjmt.common.email;

/**
 * 邮件分类
 * @author Administrator
 *
 */
public enum MailCategory {
	
	/**
	 * 测试
	 */
	ONLY_TEST("邮件发送测试"),
	/**
	 * 注册激活
	 */
	ACCOUNT_ACTIVATION("注册激活", false, true, false),
	/**
	 * 找回密码
	 */
	GETBACK_PASSWD("找回密码", false, true, false),
	/**
	 * 问题已解答
	 */
	ANSWERED_NOTIFICATION("问题解答通知", false, true, true),
	/**
	 * 订单确认
	 */
	ORDER_CONFIRMATION("订单确认", false, true, true);
	
	/** 邮件分类标签 */
	private String label;
	
	/** 是否启用订阅追踪 */
	private boolean scriptionTracking = false;
	
	/** 是否启用邮件打开追踪 */
	private boolean openTracking = true;
	
	/** 是否启用邮件链接点击追踪 */
	private boolean clickTracking = true;

	private MailCategory(String label) {
		this.label = label;
	}
	
	private MailCategory(String label, boolean scriptionTracking,
			boolean openTracking, boolean clickTracking) {
		this.label = label;
		this.scriptionTracking = scriptionTracking;
		this.openTracking = openTracking;
		this.clickTracking = clickTracking;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isScriptionTracking() {
		return scriptionTracking;
	}

	public void setScriptionTracking(boolean scriptionTracking) {
		this.scriptionTracking = scriptionTracking;
	}

	public boolean isOpenTracking() {
		return openTracking;
	}

	public void setOpenTracking(boolean openTracking) {
		this.openTracking = openTracking;
	}

	public boolean isClickTracking() {
		return clickTracking;
	}

	public void setClickTracking(boolean clickTracking) {
		this.clickTracking = clickTracking;
	}
	
}
