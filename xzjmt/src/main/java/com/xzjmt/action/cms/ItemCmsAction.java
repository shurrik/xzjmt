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
import com.xzjmt.dao.ItemDAO;
import com.xzjmt.manager.ItemMng;

@Controller
public class ItemCmsAction extends BaseAction{
	@Autowired
	private ItemMng itemMng;
	@Autowired
	private ItemDAO itemDAO;
	@RequestMapping("/cms/item/list")
	public String list(Integer pageNum,Integer pageSize,Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		EntityView ev = new EntityView();
		PageContext pageCtx = itemDAO.queryUsePage(ev, pageNum, pageSize);
 		model.addAttribute("pageCtx", pageCtx);
		
		return "cms/item/list";
	}
}
