package com.xzjmt.dao;

import org.springframework.stereotype.Repository;

import com.xzjmt.common.dao.HibernateBaseDao;
import com.xzjmt.entity.Collection;

@Repository
public class CollectionDAO extends HibernateBaseDao<Collection, Integer>{
	@Override
	public Class<Collection> getEntityClass() {
		return Collection.class;
	}
}
