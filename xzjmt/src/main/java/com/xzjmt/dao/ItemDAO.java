package com.xzjmt.dao;

import org.springframework.stereotype.Repository;

import com.xzjmt.common.dao.HibernateBaseDao;
import com.xzjmt.entity.Item;

@Repository
public class ItemDAO extends HibernateBaseDao<Item, Integer>{
	@Override
	public Class<Item> getEntityClass() {
		return Item.class;
	}
}
