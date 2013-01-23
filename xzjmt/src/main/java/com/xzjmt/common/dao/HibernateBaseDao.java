package com.xzjmt.common.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.xzjmt.common.entity.BaseEntity;
import com.xzjmt.common.exception.XzBizException;
import com.xzjmt.common.page.PageContext;
import com.xzjmt.common.page.PageTemplate;


public abstract class HibernateBaseDao<T extends BaseEntity, ID extends Serializable> {
	static final Log log = LogFactory.getLog(HibernateBaseDao.class);
	private PageTemplate<T> pageTemplate;
	private PlatformTransactionManager transactionManager;
	private TransactionTemplate transactionTemplate;
	

	/**
	 * @functionDescription 根据example进行分页查询，类似findByExample，但是带了分页功能
	 * @param example, entity instance，从这里可以获取到要查那个实体及查询条件
	 * @return PageContext，包含了当前分页得到的记录及分页所用到的pageBean
	 * @throws Exception
	 */
	public PageContext<T> queryUsePage(final T example, Integer pageNo,Integer pageSize) throws Exception {
		return this.queryUsePage(example, null, pageNo,pageSize);
	}
	
	/**
	 * @functionDescription 根据example进行分页查询，类似findByExample，但是带了分页功能
	 * @param example, entity instance，从这里可以获取到要查那个实体及查询条件
	 * @return PageContext，包含了当前分页得到的记录及分页所用到的pageBean
	 * @throws Exception
	 */
	public PageContext<T> queryUsePage(T example, EntityView ev, Integer pageNum,Integer pageSize)
			throws Exception {
		if(ev==null){
			ev = new EntityView();
		}
		pageNum = pageNum==null?1:pageNum;
		pageSize = pageSize==null?20:pageSize;
		return getPageTemplate().queryByEntity(example,ev,pageNum,pageSize);
	}
	/**
	 * @functionDescription :分页查询
	 * @param ev 过滤条件
	 * @param pageNo
	 * @return
	 * @throws Exception
	 */
	public PageContext queryUsePage(EntityView ev,Integer pageNum,Integer pageSize) throws Exception {
		pageNum = pageNum==null?1:pageNum;
		pageSize = pageSize==null?20:pageSize;
		return getPageTemplate().queryByEntity(this.getEntityClass(), ev, pageNum,pageSize);
	}
	
	/**	限制返回數量
	 * @param ev
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<T> findLimit(EntityView ev,Integer limit) throws Exception
	{
		return queryUsePage(ev,1,limit).getItemList();
	}
	/**
	 * @functionDescription :分页查询
	 * @param ev 过滤条件
	 * @param pageNo
	 * @return
	 * @throws Exception
	 */
	public PageContext queryUsePage(PageContext pageCtx, EntityView ev,Integer pageNum,Integer pageSize) throws Exception {
		pageNum = pageNum==null?1:pageNum;
		pageSize = pageSize==null?20:pageSize;
		return getPageTemplate().queryByEntity(pageCtx, this.getEntityClass(), ev, pageNum,pageSize);
	}

	/* (non-Javadoc)
	 * @see com.javalines.hibernate.dao.base.IBaseDAO#addNew(com.javalines.hibernate.pojo.base.BasePOJO)
	 */
	@SuppressWarnings("unchecked")
	public ID addNew(T editData){
		ID id = null;
		try {
		    id = (ID) getHibernateTemplate().save(this.getEntityClass().getName(),editData);
		}catch(RuntimeException e) {
		    log.error(e);
		    throw e;
		}
		log.debug("addNew successful!");
		return id;
	}
	/* (non-Javadoc)
	 * @see com.javalines.hibernate.dao.base.IBaseDAO#update(com.javalines.hibernate.pojo.recruitment.plan.TbRmApplymanage)
	 */
	public boolean update(T editData){
		getHibernateTemplate().update(this.getEntityClass().getName(),editData);
		log.debug("update successful!");
		return true;
	}
	public boolean saveOrUpdate(T editData){
		getHibernateTemplate().saveOrUpdate(this.getEntityClass().getName(),editData);
		log.debug("sava or update successful!");
		return true;
	}
	/**
	 * @functionDescription :根据sql更新，执行update，返回首次更新影响的记录条数
	 * @param sql
	 * @param paramValues
	 * @return
	 */
	public int update(final String sql, final Object[] paramValues) {
		Integer updateCount = (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						SQLQuery query = session.createSQLQuery(sql);
						if (paramValues != null) {
							for (int i = 0; i < paramValues.length; i++) {
								query.setParameter(i, paramValues[i]);
							}
						}
						return query.executeUpdate();
					}
				});
		return updateCount;
	}
	/**
	 * @functionDescription :根据sql更新，执行update，返回首次更新影响的记录条数
	 * @param sql
	 * @return
	 */
	public int update(final String sql){
		return update(sql,null);
	}
	
	public int[] batch(final String sql,final Object[][] paramValues){
		int[] rows = (int[]) getHibernateTemplate().execute(new HibernateCallback(){
			public int[] doInHibernate(Session session)
					throws HibernateException, SQLException {
				QueryRunner run = new QueryRunner();
				return run.batch(session.connection(), sql, paramValues);
			}
		});
		return rows;
	}
	/* (non-Javadoc)
	 * @see com.javalines.hibernate.dao.base.IBaseDAO#delete(java.lang.String[])
	 */
	public boolean delete(ID[] ids){
		List<T> list = new ArrayList<T>();
		//TODO:需要优化
		for (int i = 0; i < ids.length; i++) {
			T obj = this.findById(ids[i]);
			if (obj != null) {
				list.add(obj);
			}
		}
		this.deleteAll(list);
		return true;
	}
	/**
	 * @functionDescription :删除一个集合
	 * @param list
	 */
	public boolean deleteAll(Collection<T> list){
		try {
			getHibernateTemplate().deleteAll(list);
			log.debug("delete successful!");
		} catch (RuntimeException re) {
			XzBizException cause = getCause(re);
			if(cause!=null){
				log.info(cause.getMessage());
				throw cause;
			}
			log.error(re);
			throw re;
		}
		return true;
	}
	/**
	 * @functionDescription : 异常的根本原因，如果是VCellException，则返回VcellException,否则，返回null
	 * @param re
	 * @return
	 */
	private XzBizException getCause(Throwable re){
		if (re == null) {
			return null;
		}
		if (re instanceof XzBizException) {
			return (XzBizException) re;
		} else if (re.getCause() instanceof XzBizException) {
			return (XzBizException) re.getCause();
		} else {
			return getCause(re.getCause());
		}
	}
	/**
	 * @functionDescription :根据EntityView删除，删除满足EntityView条件的所有记录
	 * @param ev
	 * @return
	 */
	public boolean delete(EntityView ev){
		List<T> list = this.findByEntityView(ev);
		return this.deleteAll(list);
	}
	
	public boolean merge(T editData){
		getHibernateTemplate().merge(this.getEntityClass().toString(),editData);
		log.debug("merge successful!");
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.javalines.hibernate.dao.base.IBaseDAO#getInfo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public T findById(ID id){
		T entity = null;
		try{
			entity = (T) getHibernateTemplate().get(this.getEntityClass(), id);
		}catch(RuntimeException re){
			log.error("findById failed!", re);
		}
		return entity;
	}
	/**
	 * @functionDescription :根据属性名称和属性值获取唯一对象
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 */
	public T findUniq(String propertyName, Object propertyValue) {
		EntityView ev = new EntityView();
		ev.add(Restrictions.eq(propertyName, propertyValue));
		return this.findUniq(ev);
	}
	/**
	 * @functionDescription :根据EntityView获取唯一对象
	 * @param ev
	 * @return
	 */
	public T findUniq(EntityView ev){
		List<T> list = this.findByEntityView(ev);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * @functionDescription :根据sql查询
	 * @param sql
	 * @param paramValues
	 * @param rsh ResultSet 处理类
	 * @author 王渊博
	 * @date 2009-10-19 下午01:07:39 
	 * @return
	 */
	private Object find(final String sql, final Object[] paramValues,
			final ResultSetHandler rsh) {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				QueryRunner run = new QueryRunner();
				Connection conn = session.connection();
				if(paramValues==null){
					return run.query(conn,sql, rsh);
				}else{
					return findWithMutableParam(conn, sql, paramValues, rsh);
				}
			}
		});
	}
	@SuppressWarnings("unchecked")
	private Object findWithMutableParam(Connection conn,final String sql, final Object[] paramValues,
			final ResultSetHandler rsh) throws SQLException{
		QueryRunner run = new QueryRunner();
		String newSql = sql;
		List paramValueList = new ArrayList();
		for(int i=0;i<paramValues.length;i++){
			Object param = paramValues[i];
			if(param instanceof Collection || param instanceof Object[]){
				Collection params = param instanceof Collection?(Collection)param:Arrays.asList(param);
				int index = searchStrPosition(newSql,"?",i);
				String questionMarks = generateQuestionMark(params.size());
				newSql = newSql.substring(0,index)+questionMarks+newSql.substring(index+1);
				for(Object obj:params){
					paramValueList.add(obj);
				}
			}else{
				paramValueList.add(param);
			}
			
		}
		Object[] p = paramValueList.toArray();
		return run.query(conn, newSql, rsh,p);
	}
	private static int searchStrPosition(final String s1,final String s2,final int nth){
		int fromIndex = 0;
		for(int i=0;i<s1.length();i++){
			int index = s1.indexOf(s2, fromIndex+1);
			if(i==-1){
				return -1;
			}else if(i==nth){
				return index;
			}else{
				fromIndex = index;
				continue;
			}
		}
		return -1;
	}
	private static String generateQuestionMark(int nums){
		String s = "";
		for(int i=0;i<nums;i++){
			s+="?,";
		}
		if(s.length()>0){
			return StringUtils.removeEnd(s, ",");
		}
		return s;
	}
	public static void main(String[] args){
		String sql = "select * from t where name=? and id in(?)";
		int index = searchStrPosition(sql,"?",1);
		String questionMarks = generateQuestionMark(3);
		sql = sql.substring(0,index)+questionMarks+sql.substring(index+1);
		System.out.println(sql);
	}

	/**
	 * @functionDescription :根据sql查询，返回结果根据rsh处理
	 * @param sql
	 * @param paramValues
	 * @param rsh
	 * @return
	 */
	public List<?> findBySql(final String sql,final Object[] paramValues,
			final ResultSetHandler rsh){
		return (List<?>) find(sql,paramValues,rsh);
	}
	/**
	 * @functionDescription :根据sql查询，返回结果为List中嵌套Map,使用VCellRowProcessor
	 * @param sql
	 * @param paramValues
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findBySql(final String sql,final Object[] paramValues){
		return (List<Map>) find(sql,paramValues,new MapListHandler(new RowProcessor()));
	}

	/**
	 * @functionDescription :根据sql查询，返回结果为List中嵌套Map,使用VCellRowProcessor
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findBySql(final String sql){
		return (List<Map>)findBySql(sql, null, new MapListHandler(new RowProcessor()));
	}
	/**
	 * @functionDescription :根据属性名和属性值查询
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 */
	public List<T> findByProperty(String propertyName,Object propertyValue){
		EntityView ev = new EntityView();
		ev.add(Restrictions.eq(propertyName, propertyValue));
		return findByEntityView(ev);
	}
	
	/**
	 * @functionDescription :根据EntityView进行查询
	 * @param ev 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByEntityView(EntityView ev){
		DetachedCriteria query = DetachedCriteria.forClass(this.getEntityClass());
		ev.setEntityClass(this.getEntityClass());
		for(Criterion criterion:ev.getCriterionList()){
			query.add(criterion);
		}
		for(Order order:ev.getOrderList()){
			query.addOrder(order);
		}
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<T> list = getHibernateTemplate().findByCriteria(query);
		return list;
	}
	
	/**
	 * @functionDescription :离线查询
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(DetachedCriteria query){
		return getHibernateTemplate().findByCriteria(query);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleEntity){
		return getHibernateTemplate().findByExample(exampleEntity);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll(){
		String hql = "from "+this.getEntityClass().getName();
		return getHibernateTemplate().find(hql);
	}

	/* (non-Javadoc)
	 * @see com.javalines.hibernate.dao.base.IBaseDAO#delete(com.javalines.hibernate.pojo.base.BasePOJO)
	 */
	public boolean delete(T entity){
		List<T> list = new ArrayList<T>();
		list.add(entity);
		this.deleteAll(list);
		log.debug("delete successful!");
		return true;
	}
	/**
	 * @functionDescription :根据id判断数据库中是否存在该实体
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean exist(ID id){
		DetachedCriteria query = DetachedCriteria.forClass(this.getEntityClass());
		query.add(Restrictions.idEq(id));
		List list = getHibernateTemplate().findByCriteria(query);
		return list.size()>0;
	}
	
	/**
	 * @functionDescription :根据sql判断满足条件的记录是否存在
	 * @author 王渊博
	 * @date 2009-7-27 上午10:11:48
	 */
	public boolean exist(final String sql){
		return this.exist(sql, null);
	}
	/**
	 * @functionDescription :根据sql判断满足条件的记录是否存在
	 * @author 王渊博
	 * @date 2009-7-27 上午10:11:48
	 */
	public boolean exist(final String sql,final Object[] paramValues){
		Boolean retValue = (Boolean) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
			throws HibernateException, SQLException {
				QueryRunner run = new QueryRunner();
				List list = (List) run.query(session.connection(), sql,new ArrayListHandler(), paramValues);
				return list.size()>0;
			}
		});
		return retValue;
	}
	/**
	 * @functionDescription :根据EntityView判断满足条件的记录是否存在
	 * @author 王渊博
	 * @date 2009-7-27 上午10:11:48
	 */
	public boolean exist(final EntityView ev){
		return this.count(ev)>0;
	}
	/**
	 * @functionDescription :根据EntityView查询满足条件的记录条数
	 * @param ev
	 * @return 返回根据EntityView查询满足的记录条数
	 */
	public int count(final EntityView ev) {
		Integer totalCount = (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria = session
								.createCriteria(getEntityClass());
						for (Criterion criterion : ev.getCriterionList()) {
							criteria.add(criterion);
						}
						criteria.setProjection(Projections.rowCount());
						Object totalCount = criteria.uniqueResult();
						if(totalCount instanceof Long) {
							return ((Long)totalCount).intValue();
						} else {
							return totalCount;
						}
					}
				});
		return totalCount;
	}
	
	private HibernateTemplate hibernateTemplate; 
	
	/**
	 * @functionDescription 返回实体class,子类请覆盖
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public abstract Class<T> getEntityClass();
	
	/**
	 * @functionDescription :获取分页查询模版
	 * @author 王渊博
	 * @date 2009-7-21 上午11:54:31
	 * @return
	 */
	public PageTemplate<T> getPageTemplate() {
		return pageTemplate == null?new PageTemplate<T>(getHibernateTemplate()):pageTemplate;
	}
	


	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	@Autowired
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
}
