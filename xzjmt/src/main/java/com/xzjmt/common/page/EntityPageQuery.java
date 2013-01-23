package com.xzjmt.common.page;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Example.PropertySelector;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.type.Type;

import com.xzjmt.common.dao.EntityView;
import com.xzjmt.common.entity.BaseEntity;


public class EntityPageQuery extends AbstractPageQuery {
	@SuppressWarnings("unchecked")
	private Class entityClass;
	private EntityView entityView;
	private String entityAlias = "a";
	public EntityPageQuery(BaseEntity entity,EntityView ev){
		//EntityUtils.removeValue(entity, SysConstant.HEADER_OPTION.getKey());
		ev = ev == null ? new EntityView() : ev;
		Example example = Example.create(entity);
		//模糊查询 like '%xxxxx%'
		example.enableLike(MatchMode.ANYWHERE);
		example.setPropertySelector(new PropertySelector() {  
		    private static final long serialVersionUID = 1L;
		    //@Override
		    public boolean include(Object propertyValue, String propertyName, Type type) {
		        if (propertyValue == null) return false;
		        if (propertyValue instanceof Number)
		            if (((Number)propertyValue).longValue() == 0) return false;
		        if (propertyValue instanceof String)
		            if (((String)propertyValue).length() == 0) return false;
		        return true;
		    }  
		});
		ev.add(example);
		this.entityClass = entity.getClass();
		this.entityView = ev;
		this.entityAlias = entity.getAlias();
		this.entityView.setEntityClass(entityClass);
	}
	@SuppressWarnings("unchecked")
	public EntityPageQuery(Class entityClass,EntityView ev){
		this.entityClass = entityClass;
		this.entityView = ev;
		this.entityView.setEntityClass(entityClass);
	}
	@SuppressWarnings("unchecked")
	public void execute(int nowPage, int pageSize) {
		PerformanceTest test = new PerformanceTest();
		Criteria criteria = this.session.createCriteria(this.entityClass);
		for(Criterion criterion:entityView.getCriterionList()){
			criteria = criteria.add(criterion);
		}
		//计算总记录数
		criteria.setProjection(Projections.rowCount());
		Object totalObj = criteria.uniqueResult();
		if (totalObj instanceof Long) {
			totalCount = ((Long) totalObj).intValue();
		}else if(totalObj instanceof Integer){
			totalCount = (Integer) totalObj;
		}
		test.printTime("count");
		
		//查询满足条件的记录，返回结果为id的集合，相当于select id from ;
		ProjectionList list = Projections.projectionList();
		list.add(Property.forName("id"));
		criteria.setProjection(list);
		for(Order order:entityView.getOrderList()){
			criteria = criteria.addOrder(order);
		}
		criteria.setFirstResult((nowPage-1)*pageSize);
		criteria.setMaxResults(pageSize);
		List idList = criteria.list();
		
		test.printTime("select id");
		
		//根据Id查询出记录
		if(idList!=null && idList.size()>0){
			StringBuffer sbHql = new StringBuffer();
			sbHql.append("from ").append(entityClass.getName()).append(" ").append(entityAlias);
			sbHql.append(" where 1=1 and id in (:idList)");
			if(entityView.getOrderList().size() > 0){
				sbHql.append(" order by ");
				for(Order order:entityView.getOrderList()){
					sbHql.append(order.toString()).append(",");
				}
				sbHql.deleteCharAt(sbHql.length()-1);
			}
			Query query = this.session.createQuery(sbHql.toString());
			query.setParameterList("idList", idList);
			pageRecords = query.list();
		}
		test.printTime("select record");
		test.printTotalTime("总计");
	}
}
