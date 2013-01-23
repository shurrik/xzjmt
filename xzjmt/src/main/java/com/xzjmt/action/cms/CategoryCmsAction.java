package com.xzjmt.action.cms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xzjmt.action.BaseAction;
import com.xzjmt.common.dao.EntityView;
import com.xzjmt.common.page.PageContext;
import com.xzjmt.dao.CategoryDAO;
import com.xzjmt.entity.Category;

@Controller
public class CategoryCmsAction extends BaseAction{

	@Autowired
	private CategoryDAO categoryDAO;
	
	@RequestMapping("/cms/category/list")
	public String list(Integer pageNum,Integer pageSize,Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		EntityView ev = new EntityView();
		ev.addOrder(Order.desc("catId"));
		PageContext pageCtx = categoryDAO.queryUsePage(ev, pageNum, pageSize);
 		model.addAttribute("pageCtx", pageCtx);
		
		return "cms/category/list";
	}
	
	@RequestMapping(value="/cms/category/add")
	public String add(){
		return "cms/category/edit";
	}

	@RequestMapping(value="/cms/category/save")
	@ResponseBody
	public String save(Category category){
		categoryDAO.saveOrUpdate(category);
		return "success";
	}
}
