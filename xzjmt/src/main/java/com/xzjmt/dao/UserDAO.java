package com.xzjmt.dao;

import org.springframework.stereotype.Repository;

import com.xzjmt.common.dao.HibernateBaseDao;
import com.xzjmt.entity.User;

@Repository
public class UserDAO extends HibernateBaseDao<User, Integer>{

	@Override
	public Class<User> getEntityClass() {
		return User.class;
	}
}
