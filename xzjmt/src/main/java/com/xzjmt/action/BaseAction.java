package com.xzjmt.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.support.WebApplicationObjectSupport;

import com.xzjmt.common.web.DateTypeEditor;
import com.xzjmt.common.web.ObjectIdEditor;
import com.xzjmt.entity.User;
import com.xzjmt.shiro.XzSecurityUtils;

public class BaseAction extends WebApplicationObjectSupport{

	public static final String TOP_NAV_KEY = "_currTopNav";
	public static final String SUCC = "success";
	public static final String FAIL = "fail";
	
	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {

			binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
			
/*			DateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			CustomDateEditor timeeditor = new CustomDateEditor(tf, false);
			binder.registerCustomEditor(DateTime.class, timeeditor);*/
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			CustomDateEditor editor = new CustomDateEditor(df, true);
			binder.registerCustomEditor(Date.class, editor);

			ObjectIdEditor objectIdEditor = new ObjectIdEditor();
			binder.registerCustomEditor(ObjectId.class, objectIdEditor);
			
			binder.registerCustomEditor(Date.class, new DateTypeEditor());
			
	}
	
	// 获取当前用户信息
	public User getCurrentUser() {
		return XzSecurityUtils.getCurrentMember();
	}
}
