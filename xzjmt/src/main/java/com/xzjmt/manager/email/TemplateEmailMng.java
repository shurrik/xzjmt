package com.xzjmt.manager.email;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import com.xzjmt.common.email.KsdMailSender;
import com.xzjmt.common.email.MailHeader;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * @author 王渊博
 *
 */

@Service
public class TemplateEmailMng {
	private static final Logger log = LoggerFactory.getLogger(TemplateEmailMng.class);
	@Autowired
	private KsdMailSender sender;
/*	@Autowired
	private SohuCloudMailMessenger messenger;*/
	@Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    
    /**
     * 将model和模板处理成静态html
     * @param model
     * @param templatePath
     * @return
     */
	private String generateHtml(Map<String, Object> model, String templatePath) {
		String htmlText = "";
		try {
			Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
			htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, model);
		} catch (IOException e) {
			log.error(e.getMessage());
		} catch (TemplateException e) {
			log.error(e.getMessage());
		}
		return htmlText;
	}
	
	public void sendSimpleMail(MailHeader header, String text) {
		sender.sendSimpleMail(header, text);
	}
	
	/**
	 * 发送邮件
	 * @param username
	 * @throws MessagingException
	 * @throws javax.mail.MessagingException 
	 */
	public void sendTemplateMail(MailHeader header, Map<String, Object> model, String templatePath) throws MessagingException, javax.mail.MessagingException {
		String body = header.getBody();
		if(StringUtils.isBlank(body)) {
			body = generateHtml(model, templatePath); // 使用模板生成html邮件内容
		}
		sender.sendTemplateMail(header, model, body);
    }
	
/*	*//**
	 * 使用搜狐ESP云邮件服务的Sdk方式发送
	 *//*
	public void sendCloudMailViaSdk(MailHeader header, Map<String, Object> model, String templatePath) throws BlankException {
		String body = header.getBody();
		if(StringUtils.isBlank(body)) {
			body = generateHtml(model, templatePath); // 使用模板生成html邮件内容
		}
		messenger.sendCloudMailViaSdk(header, model, body);
	}
	
	*//**
	 * 使用搜狐ESP云邮件服务的Http方式发送
	 *//*
	public void sendCloudMailViaHttp(MailHeader header, Map<String, Object> model, String templatePath) {
		// 设置信件message
		String body = header.getBody();
		if(StringUtils.isBlank(body)) {
			body = generateHtml(model, templatePath); // 使用模板生成html邮件内容
		}
		messenger.sendCloudMailViaHttp(header, model, body);
	}*/
	
}