package com.xzjmt.common.page;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;

import com.xzjmt.common.dao.EntityView;
import com.xzjmt.common.entity.BaseEntity;


/**
 * @classDescription : pageQuery工厂类,创建PageQuery对象
 * @author 王渊博
 * @date 2009-6-16 下午08:13:38
 *
 */
public class PageQueryFactory<T> {
	static final Log log = LogFactory.getLog(PageQueryFactory.class);

	/**
	 * @functionDescription :根据实体的实例返回分页查询对象
	 * @author 王渊博
	 * @date 2009-6-12 下午07:44:04
	 * @param entity 
	 * @return 返回分页查询实例
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings( { "static-access", "unchecked" })
	public static IPageQuery getEntityPageQueryInstance(BaseEntity entity)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, InstantiationException,
			ClassNotFoundException {
		return getEntityPageQueryInstance(entity, null);
	}
	
	/**
	 * @functionDescription :根据entity构造一个分页查询对象
	 * @param entity
	 * @param ev
	 * @return
	 */
	public static IPageQuery getEntityPageQueryInstance(BaseEntity entity, EntityView ev) {
////		Map<String, Object> propertiesMap = Collections.emptyMap();
//		try {
////			propertiesMap = EntityUtils.bean2Map(entity);
//		} catch (Exception e) {
//			log.debug(e);
//		}
//		if(ev == null){
//			ev = new EntityView();
//		}
////		//是否模糊查询
////		boolean isFuzzyQuery = entity.isFuzzyQuery();
////		//ev.add(propertiesMap,isFuzzyQuery);
		return new EntityPageQuery(entity,ev);
	}
	/**
	 * @functionDescription :根据实体类名、Map、EntityView构造一个分页查询实例
	 * @param entityClass
	 * @param ev
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static IPageQuery getEntityPageQueryInstance(Class entityClass,EntityView ev) {
		if(ev == null){
			ev = new EntityView();
		}
		return new EntityPageQuery(entityClass,ev);
	}

	
	/**
	 * @functionDescription :根据criteria构造一个分页查询对象
	 * @param criteria
	 * @param ev
	 * @return
	 */
	public static IPageQuery getCriteriaPageQueryInstance(Criteria criteria){
		return new CriteriaPageQuery(criteria);
	}
	/**
	 * @functionDescription :根据DetachedCriteria构造一个分页查询对象
	 * @param criteria
	 * @param ev
	 * @return
	 */
	public static IPageQuery getCriteriaPageQueryInstance(DetachedCriteria query){
		return new CriteriaPageQuery(query);
	}
	/**
	 * @functionDescription :根据hql构造一个分页查询对象
	 * @author 王渊博
	 * @date 2009-6-12 下午07:44:04
	 * @param entity 
	 * @return 返回Hql分页查询实例
	 */	
	public static IPageQuery getHqlPageQueryInstance(final String hql,final Object[] paramValues){
		return new HqlPageQuery(hql, paramValues);
	}
	
	/**
	 * @functionDescription :根据sql和参数返回IPageQuery的实例,pageRecords返回的是List<Map<columnName,columnValue>>结构的
	 * @param sql
	 * @param paramValues
	 * @return
	 */
	public static IPageQuery getSqlPageQueryInstance(String sql,Object[] paramValues){
		return getSqlPageQueryInstance(sql,paramValues,null,null);
	}
	/**
	 * @functionDescription :根据sql和参数返回IPageQuery的实例,pageRecords返回的是List<Bean>结构的,bean为clazz参数指定的
	 * @param sql
	 * @param paramValues
	 * @param clazz 指定getPageRecords()方法返回的list元素类型，为一个bean class
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static IPageQuery getSqlPageQueryInstance(String sql,Object[] paramValues,Class clazz){
		return getSqlPageQueryInstance(sql,paramValues,null,clazz);
	}
	/**
	 * @functionDescription :根据sql和参数返回IPageQuery的实例,pageRecords返回的是List<Bean>结构的,bean为clazz参数指定的
	 * @param sql
	 * @param paramValues
	 * @param rowProcessor 指定查询返回的rowProcessor
	 * @param clazz 指定getPageRecords()方法返回的list中包含的元素类型，在PageContext.getPageRecords()返回的值为List<BaseEntity>
	 * 那么这里clazz就将BaseEntity.class作为参数传入
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static IPageQuery getSqlPageQueryInstance(String sql,Object[] paramValues,RowProcessor rowProcessor,Class clazz){
		SqlPageQuery sqlPageQuery = new SqlPageQuery(sql,paramValues);
		sqlPageQuery.setRowProcessor(rowProcessor);
		sqlPageQuery.setType(clazz);
		return sqlPageQuery;
	}
}

