/**
 * 
 */
package com.xzjmt.action.directive;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.SimpleDate;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * @author <p>Innate Solitary 于 2011-12-17 上午11:04:25</p>
 *
 */
public class CalculateTimeDirective implements TemplateDirectiveModel {
	private static final long SEC_MS = 1000;
	private static final long MIN_MS = 60 * SEC_MS;
	private static final long HOUR_MS = 60 * MIN_MS;
	private static final long DAY_MS = 24 * HOUR_MS;
	private static final long WEEK_MS = 7 * DAY_MS;
	private static final long MONTH_MS = 30 * DAY_MS;
	private static final long YEAR_MS = 365 * DAY_MS;

	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		long timems = params.get("timems") == null ? Calendar.getInstance().getTimeInMillis() : ((SimpleDate)params.get("timems")).getAsDate().getTime();
		String label = "";
		timems = new Date().getTime() - timems;
		if(timems < MIN_MS) {
			label = "1分钟以内";
		} else if(timems >= MIN_MS && timems < HOUR_MS) {
			label = (timems / MIN_MS) + "分钟前";
		} else if(timems >= HOUR_MS && timems < DAY_MS) {
			label = (timems / HOUR_MS) + "小时前";
		} else if(timems >= DAY_MS && timems < WEEK_MS) {
			label = (timems / DAY_MS) + "天前";
		} else if(timems >= WEEK_MS && timems < MONTH_MS) {
			label = (timems / WEEK_MS) + "周前";
		} else if(timems >= MONTH_MS && timems < YEAR_MS) {
			label = (timems / MONTH_MS) + "月前";
		} else if(timems >= YEAR_MS) {
			label = (timems / YEAR_MS) + "年前";
		}
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>();
		paramWrap.put("timelabel", DEFAULT_WRAPPER.wrap(label));
		Map<String, TemplateModel> origMap = DirectiveUtils.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}

}
