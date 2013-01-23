package com.xzjmt.common.page;

/**
 * @classDescription :分页查询抽象类
 * @author 王渊博
 * @date 2009-8-12 下午03:50:07
 */

import java.util.Collections;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public abstract class AbstractPageQuery implements IPageQuery {
	protected Session session;
	@SuppressWarnings("unchecked")
	protected List pageRecords = Collections.EMPTY_LIST;
	protected int totalCount;

	public abstract void execute(int nowPage, int pageSize);

	@SuppressWarnings("unchecked")
	public List getPageRecords() throws HibernateException {
		return pageRecords;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}
