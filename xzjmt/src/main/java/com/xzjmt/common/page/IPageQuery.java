package com.xzjmt.common.page;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public interface IPageQuery<T> {
	/**
	 * @functionDescription : 设置hibernate session
	 * @author 王渊博
	 * @param session
	 */
	public void setSession(Session session);
	
	/**
	 * @functionDescription :执行分页查询
	 * @author 王渊博
	 * @param nowPage
	 * @param pageSize
	 */
	public void execute(final int nowPage, final int pageSize);
	
	/**
	 * @functionDescription :返回总记录数
	 * @author 王渊博
	 * @return
	 */
	public int getTotalCount();
	
	/**
	 * @functionDescription :获取当前页的记录
	 * @author 王渊博
	 * @return
	 * @throws HibernateException
	 */
	public List<T> getPageRecords() throws HibernateException;

}