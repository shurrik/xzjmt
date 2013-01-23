package com.xzjmt.action.cms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xzjmt.action.BaseAction;
import com.xzjmt.common.dao.EntityView;
import com.xzjmt.common.page.PageContext;
import com.xzjmt.dao.UserDAO;

@Controller
public class UserCmsAction extends BaseAction{

	@Autowired
	private UserDAO userDAO;
	@RequestMapping("/cms/user/list")
	public String list(Integer pageNum,Integer pageSize,Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		EntityView ev = new EntityView();
		PageContext pageCtx = userDAO.queryUsePage(ev, pageNum, pageSize);
 		model.addAttribute("pageCtx", pageCtx);
		
		return "cms/user/list";
	}
}
