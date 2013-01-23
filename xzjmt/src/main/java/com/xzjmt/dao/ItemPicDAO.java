package com.xzjmt.dao;

import org.springframework.stereotype.Repository;

import com.xzjmt.common.dao.HibernateBaseDao;
import com.xzjmt.entity.ItemPic;

@Repository
public class ItemPicDAO extends HibernateBaseDao<ItemPic, Integer>{
	@Override
	public Class<ItemPic> getEntityClass() {
		return ItemPic.class;
	}
}
