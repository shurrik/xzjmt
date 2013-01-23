package com.xzjmt.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xzjmt.common.email.MailCategory;
import com.xzjmt.common.email.MailHeader;
import com.xzjmt.common.exception.AccountActivationException;
import com.xzjmt.common.util.BaseConfig;
import com.xzjmt.common.util.Md5Utils;
import com.xzjmt.common.util.RequestUtils;
import com.xzjmt.entity.User;
import com.xzjmt.manager.UserMng;
import com.xzjmt.manager.email.TemplateEmailMng;

@Controller
public class RegAction extends BaseAction{
	private static final Logger log = LoggerFactory.getLogger(BaseAction.class);
	
	@Autowired
	private UserMng userMng;
	@Autowired
	private TemplateEmailMng emailMng;
	@Autowired
	private BaseConfig baseConfig;
	
	@RequestMapping(value = "/reg", method = RequestMethod.GET)
	public String register(HttpServletRequest request) {
		return "reg/reg";
	}
	
	@RequestMapping(value = "/reg/email", method = RequestMethod.POST)
	public String email(@RequestParam String [] to, Model model, HttpServletRequest request) {
		
		String email = to [0];
		if(StringUtils.isBlank(email)) {
			model.addAttribute("step1_err_msg", "邮箱地址不能为空");
			return "reg/email";
		}
		email = email.trim().toLowerCase();
		boolean exist = userMng.checkExists("email", email);
		if(exist) {
			model.addAttribute("step1_email", email);
			model.addAttribute("step1_err_msg", "邮箱已被注册。请重新换一个再试");
			return "reg/email";
		}
		
		Date sendDate = new Date();
		String activationCode = Md5Utils.generateActiveCode(email);
		userMng.createUserWhenReg(email, activationCode);
		MailHeader header = new MailHeader();
		header.setTo(to);
		header.setSubject("[账号激活] 完成最后一步，您的账号就激活成功了！");
		header.setCategory(MailCategory.ACCOUNT_ACTIVATION);
		Map<String, Object> regMap = new HashMap<String, Object>();
		regMap.put("email", email);
	    regMap.put("username", email.substring(0, email.indexOf("@")));
	    regMap.put("regtime", sendDate);
		String baseUrl = RequestUtils.getBaseUrl(request);
		regMap.put("baseurl", baseUrl);
		regMap.put("acturl", baseUrl + "reg/activate?code=" + activationCode);
		regMap.put("ksdroot", baseConfig.getWWWROOT());
		
		
		try {
			//emailMng.sendCloudMailViaSdk(header, regMap, "/WEB-INF/freemarker/email/account_activation_m2.ftl");
			emailMng.sendTemplateMail(header, regMap, "/WEB-INF/freemarker/email/account_activation.ftl");
		} catch (Exception e) {
			log.warn("注册：to: {0} 验证邮件发送失败！", email);
		}
		model.addAttribute("ksd_confirmed_email", email);
		return "reg/email";
	}
	
	
	@RequestMapping("/reg/activate")
	public String activateMail(HttpServletRequest request, @RequestParam(required=false) String uid, Model model) {
		String code = RequestUtils.getQueryParam(request, "code");
		code  = StringUtils.trimToEmpty(code);
		String message ="激活码不存在或已失效";
		User user = userMng.findOne("activeCode", code);
		if (user != null){
			try {
				//memberMng.validateActivationKey(code, mongo);
				String email = user.getEmail();
				model.addAttribute("userId", user.getUserId());
				model.addAttribute("email", email);
				message = null;
			} catch (AccountActivationException e) {
				message = e.getMessage();
			}
		}
		model.addAttribute("reg_active_msg", message);
		return "reg/set_pwd1";
	}	
	
	/**
	 * 注册2 of 4: 邮箱验证后激活确认
	 */
	@RequestMapping(value = "/reg/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, @RequestParam Integer userId, Model model) {
		
		String nickName = request.getParameter("nickName");
		String passwd = request.getParameter("passwd");
		userMng.save(userId, passwd, nickName);
		return "redirect:/self";
	}	
}
