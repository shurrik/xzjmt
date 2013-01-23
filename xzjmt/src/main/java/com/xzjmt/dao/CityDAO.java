package com.xzjmt.dao;

import org.springframework.stereotype.Repository;

import com.xzjmt.common.dao.HibernateBaseDao;
import com.xzjmt.entity.City;

@Repository
public class CityDAO extends HibernateBaseDao<City, Integer>{
	@Override
	public Class<City> getEntityClass() {
		return City.class;
	}
}
