package com.xzjmt.action.directive;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.xzjmt.entity.City;
import com.xzjmt.manager.CityMng;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class CityNameDirective implements TemplateDirectiveModel{

	
	@Autowired
	private CityMng cityMng;
	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Map<String, TemplateModel> paramsWrap = new HashMap<String, TemplateModel>();
		Integer cityId = DirectiveUtils.getInt("cityId", params)!=null?DirectiveUtils.getInt("cityId", params):0;
		City city = cityMng.findById(cityId);
		String cityName = city!=null?city.getCityName():"";
		paramsWrap.put("cityName", DEFAULT_WRAPPER.wrap(cityName));
		Map<String, TemplateModel> origMap = DirectiveUtils.addParamsToVariable(env, paramsWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramsWrap, origMap);
	}
}
