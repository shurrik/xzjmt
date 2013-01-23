package com.xzjmt.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xzjmt.common.dao.EntityView;
import com.xzjmt.common.page.PageContext;
import com.xzjmt.entity.Item;
import com.xzjmt.entity.User;
import com.xzjmt.manager.ItemMng;
import com.xzjmt.manager.UserFollowMng;
import com.xzjmt.manager.UserMng;

@Controller
public class UserAction extends BaseAction{
	
	@Autowired
	private UserMng userMng;
	@Autowired
	private ItemMng itemMng;
	@Autowired
	private UserFollowMng userFollowMng;
	
	@RequestMapping("/user/{id}")
	public String index(@PathVariable("id") Integer userId,Integer pageNum,Integer pageSize,Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User curUser = getCurrentUser();
		User user = userMng.findById(userId);
		EntityView ev = new EntityView();
		ev.add(Restrictions.eq("userId", user.getUserId()));
		PageContext<Item> pageCtx = itemMng.queryUsePage(ev, pageNum, 6);
		Boolean self = curUser!=null&&curUser.getUserId().equals(user.getUserId())?true:false;
		Boolean followed = userFollowMng.followed(curUser, user);
		model.addAttribute("pageCtx", pageCtx);
		model.addAttribute("user", user);	
		model.addAttribute("self",self);
		model.addAttribute("followed",followed);
		return "user/index";
	}

}
