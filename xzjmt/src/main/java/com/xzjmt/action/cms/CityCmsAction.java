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
import com.xzjmt.dao.CityDAO;
import com.xzjmt.entity.City;

@Controller
public class CityCmsAction extends BaseAction{

	@Autowired
	private CityDAO cityDAO;
	@RequestMapping("/cms/city/list")
	public String list(Integer pageNum,Integer pageSize,Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		EntityView ev = new EntityView();
		ev.addOrder(Order.desc("cityId"));
		PageContext pageCtx = cityDAO.queryUsePage(ev, pageNum, pageSize);
 		model.addAttribute("pageCtx", pageCtx);
		
		return "cms/city/list";
	}
	
	@RequestMapping(value="/cms/city/add")
	public String add(){
		return "cms/city/edit";
	}

	@RequestMapping(value="/cms/city/save")
	@ResponseBody
	public String save(City city){
		cityDAO.saveOrUpdate(city);
		return "success";
	}	
}
