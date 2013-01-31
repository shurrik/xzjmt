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
	
	public void deleteById(Integer id)
	{
		EntityView ev = new EntityView();
		ev.add(Restrictions.eq("itemId", id));
		itemDAO.delete(ev);
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
		if(ids.size()==0)
		{
			return null;
		}
		List<Item> items = this.getListByIds(ids);
		return this.getMap(items);
	}
	

	
	//出手
	public void toggleSold(Integer itemId)
	{
		updateSoldStatus(itemId,!this.hasSold(itemId));
	}
	
	public boolean updateSoldStatus(Integer itemId,Boolean sold)
	{
		int soldVal = sold==true?1:0;
		String sql = "UPDATE xz_item m SET m.sold=? WHERE m.user_id=?";
		int n = itemDAO.update(sql, new Object [] {soldVal, itemId});
		return n > 0;
	}
	
	
	public Boolean hasSold(Integer itemId)
	{
		EntityView ev = new EntityView();
		ev.add(Restrictions.eq("itemId", itemId));
		ev.add(Restrictions.eq("sold", true));
		return itemDAO.exist(ev);
	}	
	
	
}
