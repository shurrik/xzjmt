package com.xzjmt.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xzjmt.common.dao.EntityView;
import com.xzjmt.common.page.PageContext;
import com.xzjmt.common.util.DateUtils;
import com.xzjmt.entity.Category;
import com.xzjmt.entity.City;
import com.xzjmt.entity.Collection;
import com.xzjmt.entity.Item;
import com.xzjmt.entity.ItemPic;
import com.xzjmt.entity.Message;
import com.xzjmt.entity.User;
import com.xzjmt.manager.CategoryMng;
import com.xzjmt.manager.CityMng;
import com.xzjmt.manager.CollectionMng;
import com.xzjmt.manager.ItemMng;
import com.xzjmt.manager.ItemPicMng;
import com.xzjmt.manager.MessageMng;
import com.xzjmt.manager.UserFollowMng;
import com.xzjmt.manager.UserMng;
import com.xzjmt.util.HomeNavMenu;

@Controller
public class SelfAction extends BaseAction{

	@Autowired
	private ItemMng itemMng;
	@Autowired
	private CityMng cityMng;
	@Autowired
	private CategoryMng categoryMng;
	@Autowired
	private ItemPicMng itemPicMng;
	@Autowired
	private UserMng userMng;
	@Autowired
	private MessageMng messageMng;
	@Autowired
	private UserFollowMng userFollowMng;
	@Autowired
	private CollectionMng collectionMng;
	
	@ModelAttribute("init")
	public void setTopNavKey(ModelMap model) {
		model.addAttribute(TOP_NAV_KEY, HomeNavMenu.SELF);
	}
	
	@RequestMapping("/self")
	public String index(Integer pageNum,Integer pageSize,Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = getCurrentUser();
		user = userMng.findById(user.getUserId());
		EntityView ev = new EntityView();
		ev.add(Restrictions.eq("userId", user.getUserId()));
		PageContext<Item> pageCtx = itemMng.queryUsePage(ev, pageNum, 6);
		model.addAttribute("pageCtx", pageCtx);
		model.addAttribute("user", user);
		return "self/index";
	}
	
	@RequestMapping("/self/item/new")
	public String newItem(Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = getCurrentUser();
		List<City> citys = cityMng.findAll();
		List<Category> categorys = categoryMng.findAll();
		model.addAttribute("citys", citys);
		model.addAttribute("categorys", categorys);
		model.addAttribute("user", user);
		return "self/item";
	}
	
	@RequestMapping("/self/item/list")
	public String listItem(Integer pageNum,Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		User user = getCurrentUser();
		EntityView ev = new EntityView();
		ev.add(Restrictions.eq("userId", user.getUserId()));
		PageContext<Item> pageCtx = itemMng.queryUsePage(ev, pageNum, 6);
		model.addAttribute("pageCtx", pageCtx);
		return "self/item_list";
	}
	
	
	@RequestMapping("/self/item/delete/{id}")
	public String deleteItem(@PathVariable("id") Integer itemId,Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		itemMng.deleteById(itemId);
		return "redirect:/self/item/list";
	}
	
	@RequestMapping("/self/item/add")
	public String addItem(Item item,Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String[] picUrls =  request.getParameterValues("picUrl");
		String[] picUrlSmalls =  request.getParameterValues("picUrlSmall");

		User user = getCurrentUser();
		item.setUserId(user.getUserId());
		item.setNickName(user.getNickName());
		item.setEmail(user.getEmail());
		item.setCreateDate(DateUtils.now());
		Integer itemId = itemMng.add(item);
		
		for(int i=0;i<picUrls.length;i++)
		{
			ItemPic itemPic = new ItemPic();
			itemPic.setItemId(itemId);
			itemPic.setCreateDate(DateUtils.now());
			itemPic.setPicUrl(picUrls[i]);
			itemPic.setPicUrlSmall(picUrlSmalls[i]);
			itemPicMng.add(itemPic);
		}
		return "redirect:/self";
	}	
	
	@RequestMapping("/self/profile")
	public String profile(Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		User user = getCurrentUser();
		user = userMng.findById(user.getUserId());
		List<City> citys = cityMng.findAll();
		model.addAttribute("citys", citys);
		model.addAttribute("userId", user.getUserId());
		model.addAttribute("user", user);
		return "self/profile";
	}
	
	@RequestMapping("/self/save")
	public String save(Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		Integer userId = Integer.parseInt(request.getParameter("userId"));
		User user = userMng.findById(userId);
		String mobile = request.getParameter("mobile");
		String qq = request.getParameter("qq");
		String intro = request.getParameter("intro");
		String cid = request.getParameter("cityId");
		if(StringUtils.isBlank(cid)&&!cid.equals(""))
		{
			Integer cityId = Integer.parseInt(cid);
			user.setCityId(cityId);
		}

		user.setMobile(mobile);
		user.setQq(qq);
		user.setIntro(intro);
		userMng.saveOrUpdate(user);
		
		return "redirect:/self";
	}
	
	@RequestMapping("/self/message/write/{id}")
	public String write(@PathVariable("id") Integer receiver,Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		User recUser = userMng.findById(receiver);
		model.addAttribute("recUser", recUser);
		return "self/message/write";
	}
	
	@RequestMapping("/self/message/send")
	public String send(Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Integer receiver = Integer.parseInt(request.getParameter("receiver"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		User sendUser = getCurrentUser();
		User receUser = userMng.findById(receiver);
		messageMng.sendMessage(title, content, sendUser, receUser);
		return "redirect:/self";
	}	
	
	@RequestMapping("/self/message/list")
	public String listMessage(String type,Integer pageNum,Integer pageSize,Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		User user = getCurrentUser();
		EntityView ev = new EntityView();
		Boolean sendbox = false;
		if(StringUtils.isNotBlank(type)&&type.equals("send"))
		{
			sendbox = true;
			ev.add(Restrictions.eq("sender", user.getUserId()));
		}
		else
		{
			ev.add(Restrictions.eq("receiver", user.getUserId()));
		}
		
		PageContext<Message> pageCtx = messageMng.queryUsePage(ev, pageNum, 10);
		model.addAttribute("pageCtx", pageCtx);
		model.addAttribute("sendbox", sendbox);
		return "self/message/list";
	}
	
	@RequestMapping("/self/message/{id}")
	public String messageDetail(@PathVariable("id") Integer id,Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Message message = messageMng.findById(id);
		User user = getCurrentUser();
		if(!message.getSender().equals(user.getUserId()))
		{
			model.addAttribute("replyAble", true);
		}
		model.addAttribute("message", message);
		return "self/message/detail";
	}
	
	@RequestMapping("/self/message/reply/{id}")
	public String replyMessage(@PathVariable("id") Integer id,Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Message message = messageMng.findById(id);
		model.addAttribute("message", message);
		return "self/message/reply";
	}
	
	@RequestMapping("/self/follow/{id}")
	@ResponseBody
	public String follow(@PathVariable("id") Integer followId,Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User followUser = userMng.findById(followId);
		User user = getCurrentUser();
		userFollowMng.toggleFollow(user, followUser);
		return SUCC;
	}
	
	@RequestMapping("/self/collect/{id}")
	@ResponseBody
	public String collect(@PathVariable("id") Integer itemId,Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = getCurrentUser();
		collectionMng.toggleCollect(user, itemId);
		return SUCC;
	}
	
	
	@RequestMapping("/self/collection")
	public String collect(Integer pageNum,Integer pageSize,Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = getCurrentUser();
		user = userMng.findById(user.getUserId());
		PageContext pageCtx = collectionMng.findByUserId(user.getUserId(), pageNum, pageSize);
		List<Integer> ids = new ArrayList();
		List<Collection> collections = pageCtx.getItemList();
		for(Collection collection:collections)
		{
			ids.add(collection.getItemId());
		}
		Map<String,Item> itemMap = itemMng.getMapByIds(ids);
		model.addAttribute("pageCtx", pageCtx);
		model.addAttribute("itemMap", itemMap);
		model.addAttribute("user", user);		
		return "self/collection";
	}
}
