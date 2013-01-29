package com.xzjmt.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xzjmt.entity.City;
import com.xzjmt.entity.User;
import com.xzjmt.manager.CityMng;
import com.xzjmt.manager.UserMng;
import com.xzjmt.shiro.InvalidAccountException;
import com.xzjmt.shiro.KsdSecurityManager;

@Controller
public class HomeAction extends BaseAction{
	private static final Logger log = LoggerFactory.getLogger(HomeAction.class);
	@Autowired
	private ShiroFilterFactoryBean shiroFilter;
	@Autowired
	private CityMng cityMng;
	@Autowired
	private UserMng userMng;

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
/*		String ip = request.getParameter("ip");
		//IPSeeker ipSeeker = new IPSeeker();
		if(StringUtils.isNotBlank(ip))
		{
			String c = ipSeeker.getCountry(ip);
			System.out.println(c);
			City city = cityMng.getCity(request);
			System.out.println(city);
		}*/
		
		City city = null;
		User curUser = getCurrentUser();
		if(curUser!=null&&curUser.getCityId()!=null)
		{
			curUser = userMng.findById(curUser.getUserId());
			city = cityMng.findById(curUser.getCityId());
		}
		else
		{
			city = cityMng.getCity(request);
			if(city==null)
			{
				city = cityMng.getDefaultCity();
			}
		}

		model.addAttribute("cityName", city.getCityName());
		return "home";
	}
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public String authenticate(@RequestParam String username,@RequestParam String password, String returnUrl, HttpServletRequest request,HttpServletResponse response,ModelMap model) throws IOException {
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		token.setRememberMe(true);
		Subject currentUser = SecurityUtils.getSubject();
		String inputForward = "login";
		try{
			currentUser.login(token);
			User user = (User)currentUser.getPrincipal();
/*			MemberMongo mongo = null;
			if(member != null) {
				mongo = memberMongoDao.findOne("email", StringUtils.trimToEmpty(username));
				member.setMongo(mongo);
			}*/
			currentUser.getSession().setAttribute("user", user);
/*			if(mongo != null) {
				mongo.setLastLoginTime(new Date());
				mongo.setLoginCount(mongo.getLoginCount()==null?1:mongo.getLoginCount() + 1);
				memberMongoDao.save(mongo);
			}*/
		}catch(InvalidAccountException e) {
			model.addAttribute("msg", "用户名不能为空");
			return inputForward;
		}catch(UnknownAccountException e){
			model.addAttribute("msg", "此帐号不存在！");
			return inputForward;
		}catch(IncorrectCredentialsException e){
			model.addAttribute("msg", "用户名或密码不正确。请重新输入");
			return inputForward;
		}catch(LockedAccountException e) {
			model.addAttribute("msg", "您的帐号已被锁定，请联系管理员。");
			return inputForward;
		}
		
		if(StringUtils.isNotEmpty(returnUrl)) {
			String path = request.getContextPath();
			int pos = returnUrl.indexOf(path) + path.length() + 1;
			return "redirect:/" + returnUrl.substring(pos);
		}
		//return "redirect:/";
		String sessionReturn = (String)currentUser.getSession().getAttribute("returnUrl");
		if(sessionReturn != null){   
			return "redirect:" + sessionReturn;
		} else {
			return "redirect:/";
		}
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		//memberMng.onUserLogout();  // 退出后清除sessionId等
		SecurityUtils.getSubject().logout();
		KsdSecurityManager securityManager = (KsdSecurityManager) shiroFilter.getSecurityManager();
		String logoutUrl = securityManager.getLogoutUrl();
		String service = request.getParameter("returnurl");
		if(StringUtils.isNotBlank(service)) {
			logoutUrl += "?service=".concat(service);
		}
		//return "redirect:" + logoutUrl;
		return "redirect:/";
	}
	
	@RequestMapping(value = "/shirologin", method = RequestMethod.GET)
	public String shiroLogin(HttpServletRequest request) {
		String retUrl = request.getHeader("Referer");  
		
		System.out.println(retUrl);
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request) {
		
		return "login";
	}
	
	@RequestMapping(value = "/changecity", method = RequestMethod.GET)
	public String changeCity(HttpServletRequest request,ModelMap model) {
		
		List<City> citys = cityMng.findAll();
		model.addAttribute("citys", citys);
		return "changecity";
	}	
	
	@RequestMapping(value = "/ajaxauth", method = RequestMethod.POST)
	@ResponseBody
	public String ajaxAuth(@RequestParam String username,@RequestParam String password, String returnUrl, HttpServletRequest request,HttpServletResponse response,ModelMap model) throws Exception {
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		token.setRememberMe(true);
		Subject currentUser = SecurityUtils.getSubject();
		try{
			currentUser.login(token);
			User user = (User)currentUser.getPrincipal();
			currentUser.getSession().setAttribute("user", user);
		}catch(InvalidAccountException e) {
			return "用户名不能为空";
		}catch(UnknownAccountException e){
			return "此帐号不存在";
		}catch(IncorrectCredentialsException e){
			return "用户名或密码不正确。请重新输入";
		}catch(LockedAccountException e) {
			return "您的帐号已被锁定，请联系管理员";
		}
		return SUCC;
	}
	
}
