package com.xzjmt.common.web;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

/**
 * 日期编辑器
 * 
 * 根据日期字符串长度判断是长日期还是短日期。只支持yyyy-MM-dd，yyyy-MM-dd HH:mm, yyyy-MM-dd HH:mm:ss三种格式。
 * 
 * @author wangyb
 * 
 */
public class DateTypeEditor extends PropertyEditorSupport {
	/**
	 * 日期格式
	 */
	private static final String DATE_PATTERN_LONG = "yyyy-MM-dd HH:mm:ss";
	private static final String DATE_PATTERN_MID = "yyyy-MM-dd HH:mm";
	private static final String DATE_PATTERN_SHORT = "yyyy-MM-dd";
	
	public static final DateFormat DF_LONG = new SimpleDateFormat(DATE_PATTERN_LONG);
	public static final DateFormat DF_MID = new SimpleDateFormat(DATE_PATTERN_MID);
	public static final DateFormat DF_SHORT = new SimpleDateFormat(DATE_PATTERN_SHORT);
	

	public void setAsText(String text) throws IllegalArgumentException {
		text = text.trim();
		if (!StringUtils.hasText(text)) {
			setValue(null);
			return;
		}
		try {
			if (text.length() <= DATE_PATTERN_SHORT.length()) {
				
				setValue(new java.sql.Date(DF_SHORT.parse(text).getTime()));
				
			} else if(text.length() <= DATE_PATTERN_MID.length()){
				
				setValue(new java.sql.Timestamp(DF_MID.parse(text).getTime()));
				
			}else{
				
				setValue(new java.sql.Timestamp(DF_LONG.parse(text).getTime()));
			}
		} catch (ParseException ex) {
			IllegalArgumentException iae = new IllegalArgumentException(
					"Could not parse date: " + ex.getMessage());
			iae.initCause(ex);
			throw iae;
		}
	}

	/**
	 * Format the Date as String, using the specified DateFormat.
	 */
	public String getAsText() {
		Date value = (Date) getValue();
		return (value != null ? DF_LONG.format(value) : "");
	}
}
