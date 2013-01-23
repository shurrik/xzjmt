package com.xzjmt.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xzjmt.common.dao.EntityView;
import com.xzjmt.common.page.PageContext;
import com.xzjmt.entity.City;
import com.xzjmt.entity.Item;
import com.xzjmt.entity.ItemPic;
import com.xzjmt.entity.User;
import com.xzjmt.manager.CityMng;
import com.xzjmt.manager.CollectionMng;
import com.xzjmt.manager.ItemMng;
import com.xzjmt.manager.ItemPicMng;
import com.xzjmt.manager.UserFollowMng;
import com.xzjmt.manager.UserMng;
import com.xzjmt.util.HomeNavMenu;

@Controller
public class ItemAction extends BaseAction{

	@Autowired 
	private ItemMng itemMng;
	@Autowired
	private ItemPicMng itemPicMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private UserFollowMng userFollowMng;
	@Autowired
	private CityMng cityMng;
	@Autowired
	private CollectionMng collectionMng;
	
	@ModelAttribute("init")
	public void setTopNavKey(ModelMap model) {
		model.addAttribute(TOP_NAV_KEY, HomeNavMenu.ITEM);
	}
	
	@RequestMapping("/item")
	public String item(Integer pageNum,Integer pageSize,Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		City city = null;
		User curUser = getCurrentUser();
		if(curUser!=null)
		{
			curUser = userMng.findById(curUser.getUserId());
		}
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
		EntityView ev = new EntityView();
		ev.add(Restrictions.eq("cityId", city.getCityId()));
		ev.addOrder(org.hibernate.criterion.Order.desc("createDate"));
		PageContext<Item> pageCtx = itemMng.queryUsePage(ev, pageNum, 8);
		model.addAttribute("pageCtx", pageCtx);
		model.addAttribute("city", city);
		return "item/list";
	}
	
	@RequestMapping("/item/city/{id}")
	public String item(@PathVariable("id") Integer cityId,Integer pageNum,Integer pageSize,Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		City city = cityMng.findById(cityId);
		EntityView ev = new EntityView();
		ev.addOrder(org.hibernate.criterion.Order.desc("createDate"));
		ev.add(Restrictions.eq("cityId", cityId));
		PageContext<Item> pageCtx = itemMng.queryUsePage(ev, pageNum, 8);
		model.addAttribute("pageCtx", pageCtx);
		model.addAttribute("city", city);
		return "item/list";
	}
	
	@RequestMapping("/item/{id}")
	public String itemDetail(@PathVariable("id") Integer itemId,Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User curUser = getCurrentUser();
		Item item = itemMng.findById(itemId);
		List<ItemPic> itemPics = itemPicMng.getByItemId(itemId);
		User user = userMng.findById(item.getUserId());
		Boolean followed = userFollowMng.followed(curUser, user);
		Boolean self = curUser!=null&&curUser.getUserId().equals(user.getUserId())?true:false;
		Boolean collected = collectionMng.collected(curUser, itemId);
		model.addAttribute("item", item);
		model.addAttribute("itemPics", itemPics);
		model.addAttribute("user", user);
		model.addAttribute("followed",followed);
		model.addAttribute("self",self);
		model.addAttribute("collected",collected);		
		return "item/detail";
	}

}
