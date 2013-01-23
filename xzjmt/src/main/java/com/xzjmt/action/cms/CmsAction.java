package com.xzjmt.action.cms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xzjmt.action.BaseAction;

@Controller
public class CmsAction extends BaseAction{

	@RequestMapping("/cms")
	public String index(Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return "cms/index";
	}
}
