package com.xzjmt.manager;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xzjmt.common.dao.EntityView;
import com.xzjmt.common.page.PageContext;
import com.xzjmt.common.util.DateUtils;
import com.xzjmt.dao.CollectionDAO;
import com.xzjmt.entity.Collection;
import com.xzjmt.entity.User;


@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
public class CollectionMng {
	@Autowired
	private CollectionDAO collectionDAO;
	
	public void toggleCollect(User user,Integer itemId)
	{
		
		if(this.collected(user,itemId))
		{
			cancelCollect(user,itemId);
		}
		else
		{
			this.collect(user,itemId);
		}
	}
	
	public Integer collect(User user,Integer itemId)
	{
		Collection c = new Collection();
		c.setItemId(itemId);
		c.setUserId(user.getUserId());
		c.setNickName(user.getNickName());
		c.setEmail(user.getEmail());
		c.setCreateDate(DateUtils.now());
		return collectionDAO.addNew(c);
	}
	
	public void cancelCollect(User user,Integer itemId)
	{
		EntityView ev = new EntityView();
		ev.add(Restrictions.eq("userId", user.getUserId()));
		ev.add(Restrictions.eq("itemId", itemId));
		collectionDAO.delete(ev);
	}
	
	public Boolean collected(User user,Integer itemId)
	{
		if(user!=null&&itemId!=null)
		{
			EntityView ev = new EntityView();
			ev.add(Restrictions.eq("userId", user.getUserId()));
			ev.add(Restrictions.eq("itemId", itemId));
			return collectionDAO.exist(ev);
		}
		else 
		{
			return false;
		}
	}
	
	public PageContext findByUserId(Integer userId,Integer pageNum,Integer pageSize) throws Exception
	{
		EntityView ev = new EntityView();
		ev.add(Restrictions.eq("userId", userId));
		return collectionDAO.queryUsePage(ev, pageNum, pageSize);
	}
}
