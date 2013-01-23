package com.xzjmt.dao;

import org.springframework.stereotype.Repository;

import com.xzjmt.common.dao.HibernateBaseDao;
import com.xzjmt.entity.Category;

@Repository
public class CategoryDAO extends HibernateBaseDao<Category, Integer>{

	@Override
	public Class<Category> getEntityClass() {
		return Category.class;
	}
}
