package com.xzjmt.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
/**
 * 
 * @author wangyb
 *
 */
public class WebHelper {
	public final static String XML_HTTP_REQUEST = "XMLHttpRequest";
	public final static String X_REQUESTED_WITH = "X-Requested-With";
	/**
	 * 判断请求是否为ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjaxRequest(HttpServletRequest request){
		String requestType = request.getHeader(X_REQUESTED_WITH);
		return XML_HTTP_REQUEST.equals(requestType);
	}
	/**
	 * Json对象返回，通知客户端，需要ajax登录
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public static void ajaxLogin(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		response.setDateHeader("expires", 0);
		JSONObject json = new JSONObject();
		json.put("needAjaxLogin", Boolean.TRUE);
		response.getWriter().write(json.toString());
		response.getWriter().flush();
	}
    
}