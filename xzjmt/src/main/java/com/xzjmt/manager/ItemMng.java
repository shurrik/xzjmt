package com.xzjmt.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xzjmt.common.dao.EntityView;
import com.xzjmt.common.page.PageContext;
import com.xzjmt.dao.ItemDAO;
import com.xzjmt.entity.Item;

@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
public class ItemMng {

	@Autowired
	private ItemDAO itemDAO;
	
	public Integer add(Item item)
	{
		return itemDAO.addNew(item);
	}
	
	public PageContext<Item> queryUsePage(EntityView ev, Integer pageNum, Integer pageSize) throws Exception
	{
		return itemDAO.queryUsePage(ev, pageNum, pageSize);
	}
	
	public Item findById(Integer id)
	{
		return itemDAO.findById(id);
	}
	
	public List<Item> findByUserId(Integer userId)
	{
		return itemDAO.findByProperty("userId", userId);
	}
	
	public List<Item> getListByIds(List<Integer> ids)
	{
		EntityView ev = new EntityView();
		ev.add(Restrictions.in("itemId", ids));
		return itemDAO.findByEntityView(ev);
	}
	
	public Map<String,Item> getMap(List<Item> items)
	{
		Map map = new HashMap();
		for(Item item:items)
		{
			map.put(item.getItemId().toString(), item);
		}
		return map;
	}
	
	public Map<String,Item> getMapByIds(List<Integer> ids)
	{
		List<Item> items = this.getListByIds(ids);
		return this.getMap(items);
	}
}
