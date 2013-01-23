package com.xzjmt.common.email;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class KsdMailSender extends JavaMailSenderImpl {
	
	private static final Logger log = LoggerFactory.getLogger(KsdMailSender.class);
	private static final String MAIL_PERSONAL = "闲置姐妹淘";
	
	
	/**
	 * 发送纯文本邮件
	 */
	public void sendSimpleMail(MailHeader header, String text) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom(getUsername());
		msg.setTo(header.getTo());
		msg.setSubject(header.getSubject());
		String htmlText = header.getBody();
		if(StringUtils.isBlank(htmlText)) {
			htmlText = text;
		}
		msg.setText(text);
		msg.setSentDate(new Date());
		try {
			send(msg);
			log.info("邮件发送成功");
		} catch (Exception e) {
			log.info("邮件发送失败！ >> " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 发送html邮件
	 */
	public void sendTemplateMail(MailHeader header, Map<String, Object> model, String htmlText) throws MessagingException {
		String encoding = "UTF-8";
		MimeMessage msg = super.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, false, encoding);// 由于是html邮件，不是mulitpart类型
		try {
			helper.setFrom(getUsername(), MAIL_PERSONAL);
		} catch (UnsupportedEncodingException e1) {
			log.info("不支持的编码：" + encoding);
		}
		helper.setTo(header.getTo());
		helper.setSubject(header.getSubject());
		String body = header.getBody();
		if(StringUtils.isBlank(body)) {
			body = htmlText; // 使用模板生成html邮件内容
		}
		helper.setText(body, true);
		try {
			send(msg);
			log.info("邮件发送成功！");
		} catch (Exception e) {
			log.info("邮件发送失败！ >> " + e.getMessage());
			e.printStackTrace();
		} 
    }

}