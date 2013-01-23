package com.xzjmt.manager;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xzjmt.common.dao.EntityView;
import com.xzjmt.common.util.DateUtils;
import com.xzjmt.dao.UserFollowDAO;
import com.xzjmt.entity.User;
import com.xzjmt.entity.UserFollow;

@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
public class UserFollowMng {

	@Autowired
	private UserFollowDAO userFollowDAO;
	
	
	public void toggleFollow(User user,User followUser)
	{
		
		if(this.followed(user,followUser))
		{
			cancelFollow(user,followUser);
		}
		else
		{
			this.addNew(user,followUser);
		}
	}
	
	public void cancelFollow(User user,User followUser)
	{
		EntityView ev = new EntityView();
		ev.add(Restrictions.eq("userId", user.getUserId()));
		ev.add(Restrictions.eq("followId", followUser.getUserId()));
		userFollowDAO.delete(ev);
	}
	
	public Integer addNew(User user,User followUser)
	{
		UserFollow uf = new UserFollow();
		uf.setUserId(user.getUserId());
		uf.setNickName(user.getNickName());
		uf.setEmail(user.getEmail());
		uf.setFollowId(followUser.getUserId());
		uf.setFollowEmail(followUser.getEmail());
		uf.setFollowName(followUser.getNickName());
		uf.setCreateDate(DateUtils.now());
		return userFollowDAO.addNew(uf);
	}
	
	public Boolean followed(User user,User followUser)
	{
		if(user!=null&&followUser!=null)
		{
			EntityView ev = new EntityView();
			ev.add(Restrictions.eq("userId", user.getUserId()));
			ev.add(Restrictions.eq("followId", followUser.getUserId()));
			return userFollowDAO.exist(ev);
		}
		else 
		{
			return false;
		}
	}
}
