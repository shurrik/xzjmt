package com.xzjmt.tools;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

public class EncodingPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		if (bean instanceof AnnotationMethodHandlerAdapter) {
			HttpMessageConverter<?>[] convs = ((AnnotationMethodHandlerAdapter) bean)
					.getMessageConverters();
			for(int i=0;i<convs.length;i++){
				if(convs[i] instanceof StringHttpMessageConverter){
					//处理SpringMVC ResponseBody返回处理的字符集问题
					convs[i] = new com.xzjmt.tools.StringHttpMessageConverter();
				}
			}

			((AnnotationMethodHandlerAdapter) bean).setMessageConverters(convs);
			convs = ((AnnotationMethodHandlerAdapter) bean).getMessageConverters();
			return bean;
		}

		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

}
