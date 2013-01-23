package com.xzjmt.common.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.BasicRowProcessor;

public class RowProcessor extends BasicRowProcessor {
	
	public RowProcessor(){
		super();
	}
	
	/**
	 * @functionDescription :由于数据库中字段名可能带有下划线，去掉其中的下划线
	 * @author 王渊博
	 * @date 2009-7-20 下午03:55:59
	 */
	@Override
	public Map<String, Object> toMap(ResultSet rs) throws SQLException {
		Map<String, Object> map =  super.toMap(rs);
		//父类里将其处理为key对大小写不敏感，这里借用父类的大小写不敏感map
		//map, key对大小写不敏感,先将所有key取出，去掉'_'之后再put进去
		List<String> keyList = new ArrayList<String>();
		for(Iterator<String> ite = map.keySet().iterator();ite.hasNext();){
			String colName = (String) ite.next();
			keyList.add(colName);
		}
		for(String key:keyList){
			Object value = map.get(key);
			if(value instanceof Date){
				Timestamp v1 = rs.getTimestamp(key);
				if(v1.compareTo((Date)value)!=0){
					value=v1;
				}
			}
			String newKey = KsdMappingRuleUtils.columnNameToPropertyName(key);
			//新key和旧key不一致，则删除旧key,降低数据量,ajax通过json调用时，数据量较大
			if(!newKey.equals(key)){
				map.remove(key);
			}
			map.put(newKey, value);
		}
		return map;
	}
	
	public Object toBean(ResultSet rs, Class type) throws SQLException {
		Map<String, Object> map = this.toMap(rs);
		Object bean = null;
		
		// 如果字段中有枚举行，需要把字段的值转换成枚举
		Field[] fileds = type.getDeclaredFields();
		for (Field field : fileds) {
			if(map.get(field.getName()) == null) {
				continue;
			}
			
	        if(field.getType().isEnum()) {
	        	try {
	                Object enumValue = field.getType().getMethod("valueOf", String.class).invoke(field.getType(), map.get(field.getName()).toString());
	                map.put(field.getName(), enumValue);
                } catch (IllegalAccessException e) {
	                throw new SQLException(e);
                } catch (IllegalArgumentException e) {
                	throw new SQLException(e);
                } catch (InvocationTargetException e) {
                	throw new SQLException(e);
                } catch (NoSuchMethodException e) {
                	throw new SQLException(e);
                } catch (SecurityException e) {
                	throw new SQLException(e);
                }
	        }
        }
		
		
		try {
			bean = type.newInstance();
			org.apache.commons.beanutils.BeanUtils.populate(bean, map);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public List toBeanList(ResultSet rs, Class type) throws SQLException {
		return super.toBeanList(rs, type);
	}
	
	public Object[] toArray(ResultSet rs) throws SQLException {
		return super.toArray(rs);
	}
	
}
