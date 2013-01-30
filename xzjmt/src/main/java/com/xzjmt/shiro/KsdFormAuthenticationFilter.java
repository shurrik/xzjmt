package com.xzjmt.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzjmt.util.WebHelper;

public class KsdFormAuthenticationFilter extends FormAuthenticationFilter {
	private static final Logger logger = LoggerFactory.getLogger(KsdFormAuthenticationFilter.class);

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		//如果非ajax请求，不予处理
		if(!WebHelper.isAjaxRequest((HttpServletRequest) request)){
			return super.onAccessDenied(request, response);
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("It's a ajax request.");
		}
		saveRequest(request);
		WebHelper.ajaxLogin((HttpServletRequest)request, (HttpServletResponse) response);
		return false;
	}
	
}