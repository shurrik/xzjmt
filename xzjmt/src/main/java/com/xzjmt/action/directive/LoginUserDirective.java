package com.xzjmt.action.directive;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.xzjmt.entity.User;
import com.xzjmt.manager.UserMng;
import com.xzjmt.shiro.XzSecurityUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 判断会员是否登录指令
 * 指令名         @loginMember
 * 参  数：       无
 * 说  明：       判断输出的member是否为null
 * @author Administrator
 *
 */
public class LoginUserDirective implements TemplateDirectiveModel{

	@Autowired
	private UserMng userMng;
	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Map<String, TemplateModel> paramsWrap = new HashMap<String, TemplateModel>();
		User user = XzSecurityUtils.getCurrentMember();
		if(user!=null)
		{
			user = userMng.findById(user.getUserId());
		}
		paramsWrap.put("loginUsr", DEFAULT_WRAPPER.wrap(user));
		Map<String, TemplateModel> origMap = DirectiveUtils.addParamsToVariable(env, paramsWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramsWrap, origMap);
	}

}
