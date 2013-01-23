package com.xzjmt.common.page;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.dialect.MySQL5Dialect;

public class SqlPageQuery extends AbstractPageQuery{
	private String sql;
	private Object[] paramValues;
	private String countSql;
	private RowProcessor rowProcessor;
	@SuppressWarnings("unchecked")
	private Class type;
	
	static final Log log = LogFactory.getLog(SqlPageQuery.class);
	
	public SqlPageQuery(String sql,Object[] paramValues) {
		this.sql = sql;
		this.paramValues = paramValues;
		this.countSql = "select count(1) from (" + sql +") _a";
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public void execute(int nowPage, int pageSize) {
		
		/* 由于使用Hibernate的SqlQuery无法获取ResulteSetMetaData，所以放弃这种方案
		SQLQuery query = session.createSQLQuery(sql);
		SQLQuery countQuery = session.createSQLQuery(countSql);
		for(int i=0; paramValues!=null && i<paramValues.length; i++){
			query.setParameter(i, paramValues[i]);
			countQuery.setParameter(i, paramValues[i]);
		}
		totalCount = Integer.parseInt(countQuery.uniqueResult().toString());
		query.setFirstResult((nowPage-1)*pageSize);
		query.setMaxResults(pageSize);
		List records = query.list();
		*/
		
		Connection conn = session.connection();
		//conn.getMetaData().getDatabaseProductName();
		
		//直接使用JDBC直接处理分页查询,在这里使用Oracle方言，以后可以扩展支持多种方言
		//Oracle10gDialect dialect = new Oracle10gDialect();
		MySQL5Dialect dialect = new MySQL5Dialect();
		String pageSql = dialect.getLimitString(sql, true);
		
		Object[] params = null;
		int length = 2;
		if(paramValues!=null&&paramValues.length>0){
			length = paramValues.length + 2;
			params = new Object[length];
			for(int i=0;i<paramValues.length;i++){
				params[i] = paramValues[i];
			}
		}else{
			params = new Object[length];
		}
		if(nowPage<=0){
			nowPage=1;//否则算出来起始行为负数，mysql会出错
		}
		
		params[length-1] = pageSize;//pageSize
		params[length-2] = (nowPage-1)*pageSize;//start
		
		QueryRunner run = new QueryRunner();
		
		try {
			log.debug(countSql);
			Object[] s = (Object[]) run.query(conn,countSql,new ArrayHandler(),paramValues);
			this.totalCount = ((Long)s[0]).intValue();
			
			//如果没有指定要转换成的Bean类型，则默认转为List<Map>
			if(this.rowProcessor == null){
				//this.rowProcessor = new VCellRowProcessor();
			}
			ResultSetHandler handler;
			if(this.type == null){
				handler = new MapListHandler(rowProcessor); 
			}else{
				handler = new BeanListHandler(type,rowProcessor);
			}
			log.debug(pageSql);
			pageRecords = (List) run.query(conn,pageSql,handler,params);
			
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	/**
	 * @functionDescription :设置行处理器，使用它可以对ResultSet进行处理
	 * @author 王渊博
	 * @param rowProcessor
	 */
	public void setRowProcessor(RowProcessor rowProcessor) {
		this.rowProcessor = rowProcessor;
	}
	/**
	 * @functionDescription :将一条记录转换为Bean,设置将要转换成的Bean的class
	 * @author 王渊博
	 * @param type
	 */
	public void setType(Class type) {
		this.type = type;
	}

}
