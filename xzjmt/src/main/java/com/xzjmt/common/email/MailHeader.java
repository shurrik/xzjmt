package com.xzjmt.common.email;

public class MailHeader {
	
	private String[] to;
	private String subject;
	/**
	 * 邮件正文
	 * 
	 * <p>如果使用邮件模版作为正文，请忽略该属性。
	 */
	private String body;
	private MailCategory category;

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public MailCategory getCategory() {
		return category;
	}

	public void setCategory(MailCategory category) {
		this.category = category;
	}
	
}
