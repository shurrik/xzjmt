package com.xzjmt.common.page;

/**
 * @classDescription :页面上下文对象
 * @author 王渊博
 * @date 2009-6-15 上午08:53:53
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xzjmt.common.dao.PageBean;


public class PageContext<T> {
	private Map<String,Object> ctx = new HashMap<String, Object>();
	private String sPageBean = "pageBean";
	private String sItemList = "itemList";
	public PageBean getPageBean() {
		return (PageBean) ctx.get(sPageBean);
	}
	public void setPageBean(PageBean pageBean) {
		ctx.put(sPageBean, pageBean);
	}
	/**
	 * @functionDescription :获取当前页面记录
	 * @author 王渊博
	 * @date 2009-6-12 下午08:50:00
	 * @param itemList
	 */
	@SuppressWarnings("unchecked")
	public List<T> getItemList() {
		return (List<T>) ctx.get(sItemList);
	}
	/**
	 * @functionDescription :设置当前页面记录
	 * @author 王渊博
	 * @date 2009-6-15 上午08:51:40
	 * @param itemList
	 */
	public void setItemlList(List<T> itemList) {
		ctx.put(sItemList, itemList);
	}


	/**
	 * @functionDescription :ctx中put自定义值
	 * @param key
	 * @param value
	 */
	public void put(String key,Object value){
		ctx.put(key, value);
	}
	/**
	 * @functionDescription :获取ctx中自定义值
	 * @author 王渊博
	 * @date 2009-6-15 上午08:53:04
	 * @param key
	 * @return
	 */
	public Object get(String key){
		return ctx.get(key);
	}
	
}

