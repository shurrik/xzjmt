package com.xzjmt.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xzjmt.util.HomeNavMenu;

@Controller
public class AboutAction extends BaseAction{

	@ModelAttribute("init")
	public void setTopNavKey(ModelMap model) {
		model.addAttribute(TOP_NAV_KEY, HomeNavMenu.ABOUT);
	}
	
	@RequestMapping("/about")
	public String about(Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return "about";
	}
}
