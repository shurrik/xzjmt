package com.xzjmt.dao;

import org.springframework.stereotype.Repository;

import com.xzjmt.common.dao.HibernateBaseDao;
import com.xzjmt.entity.Message;

@Repository
public class MessageDAO extends HibernateBaseDao<Message, Integer>{
	@Override
	public Class<Message> getEntityClass() {
		return Message.class;
	}
}
