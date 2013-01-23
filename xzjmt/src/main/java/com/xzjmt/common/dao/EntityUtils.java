package com.xzjmt.common.dao;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzjmt.common.entity.BaseEntity;
import com.xzjmt.common.entity.Entity;


public class EntityUtils {
	private static final Logger log = LoggerFactory.getLogger(EntityUtils.class); 
	public static BaseEntity removeValue(BaseEntity entity,Object value) {
		PropertyDescriptor[] thisPDS = PropertyUtils.getPropertyDescriptors(entity.getClass());
		for (PropertyDescriptor thisPD:thisPDS) {
			String propertyName = thisPD.getName();
			String propertyValue = null;
			try{
				propertyValue = BeanUtils.getProperty(entity, propertyName);
			}catch(Exception e){
				log.debug(e.getLocalizedMessage());
				continue;
			} 
			if(propertyValue == null){
				continue;
			}
			if(propertyValue instanceof String){
				//如果propertyValue中前后包含空格，去掉空格
				String trimValue = propertyValue.toString().trim();
				if(!propertyValue.equals(trimValue)){
					setNewValueForProp(entity, thisPD,trimValue);
				}
				if(trimValue.length()==0){
					setNewValueForProp(entity, thisPD,null);
				}
			}
			//如果propertyValue值等于value,则将propertyValue值改为NULL
			if(propertyValue.equals(value)){
				setNewValueForProp(entity, thisPD,null);
			}
		}
		return entity;
	}

	private static void setNewValueForProp(BaseEntity entity,
			PropertyDescriptor thisPD,Object value) {
		Method method = thisPD.getWriteMethod();
		if(method!=null){
			try {
				method.invoke(entity,new Object[]{value});
			} catch (Exception e) {
				log.debug(e.getLocalizedMessage());
			} 
		}
	}

	/**
	 * @functionDescription :将Bean转换为Map，使用map保存为<propertyName,propertyValue>
	 * @param entity
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public static Map<String, Object> bean2Map(Entity entity)
			throws IllegalAccessException, InvocationTargetException,NoSuchMethodException, InstantiationException,
			ClassNotFoundException {
		Map<String,Object> map = new HashMap<String,Object>();
		//entity 具有的所有属性
		PropertyDescriptor[] thisPD = PropertyUtils.getPropertyDescriptors(entity.getClass());
		//entity的父类BaseEntity所具有的属性
		PropertyDescriptor[] parentPD = PropertyUtils.getPropertyDescriptors(BaseEntity.class);
		//entity实现的所有接口中定义的属性
		PropertyDescriptor[] implPD = EntityUtils.getImplProperties(entity);
		
		for (int i = 0; i < thisPD.length; i++) {
			String propertyName = thisPD[i].getName();
			String propertyValue = null;
			try{
				propertyValue = BeanUtils.getProperty(entity, propertyName);
			}catch(NoSuchMethodException e){
				log.debug(e.getLocalizedMessage());
				continue;
			}
			//如果属性值为空，不作为查询条件，继续处理下一个属性
			if(StringUtils.isEmpty(propertyValue)){
				continue;
			}
	
			//如果是BaseEntity中的属性，不予处理,继续处理下一个属性
			boolean isBaseProperty = EntityUtils.hasProperty(propertyName, parentPD);
			if(isBaseProperty){
				continue;
			}
			//如果是实现的接口中定义的属性，不予处理，继续处理下一个属性
			boolean isImplProperty = EntityUtils.hasProperty(propertyName, implPD);
			if(isImplProperty){
				continue;
			}
			
			//如果带Set，即可能是一对多的情况，child中的条件不作为过滤条件
			String propertyType = thisPD[i].getPropertyType().getName();
			if("java.util.Set".equals(propertyType)){
				continue;
			}
			if("int".equals(propertyType)){
				map.put(propertyName, propertyValue);
				continue;
			}
			if("boolean".equals(propertyType)){
				map.put(propertyName, propertyValue);
				continue;
			}
			Object obj = ConstructorUtils.invokeConstructor(
					Class.forName(propertyType), propertyValue);
			map.put(propertyName, obj);
		}
		return map;
	}

	/**
	 * @functionDescription :获取entity对象实现的所有接口所具有的属性
	 * @author 王渊博
	 * @date 2009-7-28 下午03:08:15
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static PropertyDescriptor[] getImplProperties(Entity entity) {
		Class[] interfaces = entity.getClass().getInterfaces();
		List<PropertyDescriptor> implPDs = new ArrayList<PropertyDescriptor>();
		for(Class clazz:interfaces){
			PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(clazz);
			for(PropertyDescriptor pd:pds){
				implPDs.add(pd);
			}
		}
		return implPDs.toArray(new PropertyDescriptor[]{});
	}

	/**
	 * @functionDescription :判断propertyDes是否包含name为properName的属性
	 * @author 王渊博
	 * @date 2009-6-16 下午06:47:00
	 * @param properName
	 * @param propertyDes
	 * @return
	 */
	private static boolean hasProperty(String properName,
			PropertyDescriptor[] propertyDes) {
		boolean hasThisProprty = false;
		for (PropertyDescriptor pd:propertyDes) {
			if (pd.getName().equals(properName)) {
				hasThisProprty = true;
				break;
			}
		}
		return hasThisProprty;
	}
	/**
	 * @functionDescription :获取一个类的所有field，包含父类
	 * @param clazz
	 * @author 王渊博
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Set<Field> getFields(Class clazz){
		Set<Field> fields = new HashSet<Field>();
		Field[] thisFields = clazz.getDeclaredFields();
		for(Field field:thisFields){
			fields.add(field);
		}
		Class superClass = clazz.getSuperclass();
		if(superClass!=null){
			Set<Field> superFields = getFields(clazz.getSuperclass());
			fields.addAll(superFields);
		}
		return fields;
	}

	/**
	  * 生成随即密码
	  * @param pwd_len 生成的密码的总长度
	  * @return  密码的字符串
	  */
	 public static String genRandomPassword(int pwd_len) {
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数，
			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}
}
