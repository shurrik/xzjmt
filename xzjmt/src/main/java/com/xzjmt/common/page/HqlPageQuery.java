package com.xzjmt.common.page;

import org.hibernate.Query;

public class HqlPageQuery extends AbstractPageQuery {
	private String hql;
	private Object[] paramValues;
	private String counthql;
	private Query query;
	private Query countQuery;
	public HqlPageQuery(String hql,Object[] paramValues) {
		super();
		this.hql = hql;
		this.paramValues = paramValues;
		this.counthql = "select count(id) " + hql;
	}

	@SuppressWarnings("unchecked")
	public void execute(final int nowPage, final int pageSize){
		query = session.createQuery(hql);
		countQuery = session.createQuery(counthql);
		for (int i = 0; paramValues != null && i < paramValues.length; i++) {
			Object tempVal = paramValues[i];
			query.setParameter(i, tempVal);
			countQuery.setParameter(i, tempVal);
		}
		totalCount = Integer.parseInt(countQuery.uniqueResult().toString());
		query.setFirstResult((nowPage-1)*pageSize);
		query.setMaxResults(pageSize);
		pageRecords = query.list();
	}
}
