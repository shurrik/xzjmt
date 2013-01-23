package com.xzjmt.common.page;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.xzjmt.common.dao.EntityView;
import com.xzjmt.common.dao.PageBean;
import com.xzjmt.common.entity.BaseEntity;

public final class PageTemplate<T> {
	private HibernateTemplate hibernateTemplate;
//	private static final String FILE_PATH = "struts";
//	private static int PAGE_SIZE = 3;
//	static{
//		String sPageSize = ProResource.getParament(FILE_PATH, "pageSize");
//		try{
//			PAGE_SIZE = Integer.parseInt(sPageSize);
//		}catch(Exception e){
//			PAGE_SIZE = 20;
//		}
//	}
	/**
	 * @functionDescription :构造函数，需要注入hibernateTemplate
	 * @author 王渊博
	 * @date 2009-7-24 下午02:37:02
	 */
	public PageTemplate(){
	}
	/**
	 * @functionDescription :构造函数
	 * @author 王渊博
	 * @date 2009-7-24 下午02:37:27
	 * @param hibernateTemplate
	 */
	public PageTemplate(HibernateTemplate hibernateTemplate){
		this.hibernateTemplate = hibernateTemplate;
	}
	/**
	 * @functionDescription :执行分页查询
	 * @author 王渊博
	 * @date 2009-7-17 下午04:35:34
	 * @param pageNo
	 * @param pageSize
	 * @param pageQuery
	 * @return
	 */
	private PageContext<T> excuteQuery(final IPageQuery<T> pageQuery,final int pageNo) {
		final int pageSize = 20;
		return excuteQuery(pageQuery,pageNo,pageSize);
	}
	/**
	 * @functionDescription :执行分页查询
	 * @author 王渊博
	 * @date 2009-7-17 下午04:35:34
	 * @param pageNo
	 * @param pageSize
	 * @param pageQuery
	 * @return
	 */
	private PageContext<T> excuteQuery(final IPageQuery<T> pageQuery,final int pageNo,final int pageSize) {
		PageContext<T> pageContext = (PageContext<T>) this.hibernateTemplate.execute(
				(new HibernateCallback() {
					public Object doInHibernate(Session session) {
						return doQuery(pageQuery, pageNo, pageSize, session);
					}
				}));
		return pageContext;
	}
	/**
	 * @functionDescription :执行分页查询
	 * @author 王渊博
	 * @date 2009-7-17 下午04:35:34
	 * @param pageNo
	 * @param pageSize
	 * @param pageQuery
	 * @return
	 */
	private PageContext<T> excuteQuery(final PageContext pageCtx, final IPageQuery pageQuery,final int pageNo,final int pageSize) {
		PageContext pageContext = (PageContext) this.hibernateTemplate.execute(
				(new HibernateCallback() {
					public Object doInHibernate(Session session) {
						return doQuery(pageCtx, pageQuery, pageNo, pageSize, session);
					}
				}));
		return pageContext;
	}
	/**
	 * @functionDescription :使用session进行分页查询
	 * @author 王渊博
	 * @date 2009-7-22 下午02:13:02
	 * @param pageQuery
	 * @param pageNo
	 * @param session
	 * @return
	 */
	private PageContext<T> doQuery(final IPageQuery<T> pageQuery, final int pageNo,final int pageSize,
			Session session) {
		pageQuery.setSession(session);
		pageQuery.execute(pageNo, pageSize);
		PageContext<T> pageCtx = new PageContext<T>();
		//计算总记录数
		int nowPage = pageQuery.getTotalCount()==0?1:pageNo;
		List<T> pageRecords = pageQuery.getPageRecords();
		pageCtx.setItemlList(pageRecords);
		PageBean pageBean = pageCtx.getPageBean() == null ? new PageBean(nowPage,pageQuery.getTotalCount()) : pageCtx.getPageBean();
		pageBean.setPageSize(pageSize);
		pageBean.setCurrTotalCount(pageRecords.size());
		pageCtx.setPageBean(pageBean);
		return pageCtx;
	}
	/**
	 * @functionDescription :使用session进行分页查询
	 * @author 王渊博
	 * @date 2009-7-22 下午02:13:02
	 * @param pageQuery
	 * @param pageNo
	 * @param session
	 * @return
	 */
	private PageContext<T> doQuery(final PageContext<T> pCtx, final IPageQuery<T> pageQuery, final int pageNo,final int pageSize,
			Session session) {
		pageQuery.setSession(session);
		pageQuery.execute(pageNo, pageSize);
		PageContext<T> pageCtx = pCtx == null ? new PageContext<T>() : pCtx;
		//计算总记录数
		int nowPage = pageQuery.getTotalCount()==0?1:pageNo;
		List<T> pageRecords = pageQuery.getPageRecords();
		pageCtx.setItemlList(pageRecords);
		PageBean pageBean = pageCtx.getPageBean() == null ? new PageBean(nowPage,pageQuery.getTotalCount()) : pageCtx.getPageBean();
		pageBean.setPageSize(pageSize);
		pageBean.setCurrTotalCount(pageRecords.size());
		pageCtx.setPageBean(pageBean);
		return pageCtx;
	}
	/**
	 * @functionDescription :根据Criteria进行分页查询
	 * @param criteria
	 * @param pageNo
	 * @author 王渊博
	 * @date 2009-7-24 下午02:12:54
	 * @return
	 * @throws Exception
	 */
	public PageContext<T> queryByCriteria(Criteria criteria,final int pageNo)throws Exception{
		final IPageQuery<T> pageQuery = PageQueryFactory.getCriteriaPageQueryInstance(criteria);
		return excuteQuery(pageQuery,pageNo);
	}
	/**
	 * @functionDescription :根据Criteria进行分页查询，可以使用DetachedCriteria.forClass(Cat.class)
	 * 形式创建一个离线查询进行分页处理，这时和session无关
	 * @param criteria
	 * @param pageNo
	 * @author 王渊博
	 * @date 2009-7-24 下午02:12:54
	 * @return
	 * @throws Exception
	 */
	public PageContext<T> queryByCriteria(DetachedCriteria query,final int pageNo)throws Exception{
		final IPageQuery<T> pageQuery = PageQueryFactory.getCriteriaPageQueryInstance(query);
		return excuteQuery(pageQuery,pageNo);
	}
	
	/**
	 * @functionDescription :根据实体进行分页查询，类似Spring模版中的findByExample()
	 * @author 王渊博
	 * @date 2009-7-17 下午04:21:47
	 */
	public PageContext<T> queryByEntity(final BaseEntity example,final int pageNo)throws Exception{
		final IPageQuery<T> pageQuery = PageQueryFactory.getEntityPageQueryInstance(example, null);
		return excuteQuery(pageQuery,pageNo);

	}
	/**
	 * @functionDescription :可以根据指定条件进行分页查询
	 * @param example
	 * @param ev
	 * @param pageNo
	 * @return
	 * @throws Exception
	 */
	public PageContext<T> queryByEntity(final BaseEntity example,EntityView ev,final int pageNo)throws Exception{
		final IPageQuery<T> pageQuery = PageQueryFactory.getEntityPageQueryInstance(example, ev);
//		final IPageQuery pageQuery = PageQueryFactory.getCriteriaPageQueryInstance(example, ev);
		return excuteQuery(pageQuery,pageNo);
		
	}
	public PageContext<T> queryByEntity(final BaseEntity example,EntityView ev,final int pageNo,final int pageSize)throws Exception{
		final IPageQuery<T> pageQuery = PageQueryFactory.getEntityPageQueryInstance(example, ev);
		return excuteQuery(pageQuery,pageNo,pageSize);
		
	}	
	/**
	 * @functionDescription :可以根据指定条件进行分页查询
	 * @param example
	 * @param ev
	 * @param pageNo
	 * @return
	 * @throws Exception
	 */
	public PageContext<T> queryByEntity(final Class entityClass,EntityView ev,final int pageNo,final int pageSize)throws Exception{
		final IPageQuery<T> pageQuery = PageQueryFactory.getEntityPageQueryInstance(entityClass, ev);
//		final IPageQuery pageQuery = PageQueryFactory.getCriteriaPageQueryInstance(example, ev);
		return excuteQuery(pageQuery,pageNo,pageSize);
		
	}
	/**
	 * @functionDescription :可以根据指定条件进行分页查询
	 * @param example
	 * @param ev
	 * @param pageNo
	 * @return
	 * @throws Exception
	 */
	public PageContext<T> queryByEntity(final PageContext<T> pageCtx, final Class entityClass,EntityView ev,final int pageNo,final int pageSize)throws Exception{
		final IPageQuery pageQuery = PageQueryFactory.getEntityPageQueryInstance(entityClass, ev);
//		final IPageQuery pageQuery = PageQueryFactory.getCriteriaPageQueryInstance(example, ev);
		return excuteQuery(pageCtx, pageQuery,pageNo,pageSize);
		
	}
	/**
	 * @functionDescription :根据hql语句进行分页查询
	 * @author 王渊博
	 * @date 2009-7-17 下午04:21:39
	 * @param hql
	 * @param paramValues
	 * @param pageNo
	 * @return
	 */
	public PageContext<T> queryByHql(String hql,Object[] paramValues,final int pageNo,final int pageSize){
		IPageQuery pageQuery = PageQueryFactory.getHqlPageQueryInstance(hql, paramValues);
		return excuteQuery(pageQuery,pageNo,pageSize);
	}
	/**
	 * @functionDescription :根据hql语句进行分页查询
	 * @author 王渊博
	 * @date 2009-7-17 下午04:21:39
	 * @param hql
	 * @param pageNo
	 * @return
	 */
	public PageContext<T> queryByHql(String hql,final int pageNo,final int pageSize){
		IPageQuery pageQuery = PageQueryFactory.getHqlPageQueryInstance(hql,null);
		return excuteQuery(pageQuery,pageNo,pageSize);
	}
	/**
	 * @functionDescription :根据sql语句进行分页查询
	 * @author 王渊博
	 * @date 2009-7-17 下午04:21:30
	 * @param sql
	 * @param paramValues
	 * @param pageNo
	 * @return
	 */
	public PageContext<T> queryBySql(String sql,Object[] paramValues,final int pageNo,final int pageSize){
		IPageQuery pageQuery = PageQueryFactory.getSqlPageQueryInstance(sql, paramValues);
		return excuteQuery(pageQuery,pageNo,pageSize);
	}
	/**
	 * @functionDescription :根据sql语句进行分页查询
	 * @author 王渊博
	 * @date 2009-7-17 下午04:21:30
	 * @param sql
	 * @param pageNo
	 * @return
	 */
	public PageContext<T> queryBySql(String sql,final int pageNo,final int pageSize){
		IPageQuery pageQuery = PageQueryFactory.getSqlPageQueryInstance(sql, null);
		return excuteQuery(pageQuery,pageNo,pageSize);
	}
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
