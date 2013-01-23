package com.xzjmt.action.cms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xzjmt.action.BaseAction;
import com.xzjmt.common.dao.EntityView;
import com.xzjmt.common.page.PageContext;
import com.xzjmt.dao.MessageDAO;

@Controller
public class MessageAction extends BaseAction{
	@Autowired
	private MessageDAO messageDAO;
	
	@RequestMapping("/cms/message/list")
	public String list(Integer pageNum,Integer pageSize,Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		EntityView ev = new EntityView();
		ev.addOrder(Order.desc("id"));
		PageContext pageCtx = messageDAO.queryUsePage(ev, pageNum, pageSize);
 		model.addAttribute("pageCtx", pageCtx);
		
		return "cms/message/list";
	}
}
