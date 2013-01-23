package com.xzjmt.dao;

import org.springframework.stereotype.Repository;

import com.xzjmt.common.dao.HibernateBaseDao;
import com.xzjmt.entity.UserFollow;

@Repository
public class UserFollowDAO extends HibernateBaseDao<UserFollow, Integer>{
	@Override
	public Class<UserFollow> getEntityClass() {
		return UserFollow.class;
	}
}
