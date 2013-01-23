package com.xzjmt.common.page;


import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;

public class CriteriaPageQuery extends AbstractPageQuery{
	private Criteria criteria;
	private DetachedCriteria detachedCriteria;
	public CriteriaPageQuery(Criteria criteria){
		this.criteria = criteria;
	}
	public CriteriaPageQuery(DetachedCriteria detachedCriteria){
		this.detachedCriteria = detachedCriteria;
	}
	@SuppressWarnings("unchecked")
	public void execute(int nowPage, int pageSize) {
		// 将离线查询变为在线查询
		if (detachedCriteria != null) {
			criteria = detachedCriteria.getExecutableCriteria(session);
		}

		Projection projection = null;
		if (criteria instanceof CriteriaImpl) {
			projection = ((CriteriaImpl) criteria).getProjection();
		}
		criteria.setProjection(Projections.rowCount());
		totalCount = (Integer) criteria.uniqueResult();

		criteria.setProjection(projection);
		criteria.setFirstResult((nowPage - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		pageRecords = criteria.list();
	}
}
