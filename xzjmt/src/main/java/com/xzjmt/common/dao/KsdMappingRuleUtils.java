package com.xzjmt.common.dao;

public class KsdMappingRuleUtils {

	/**
	 * @functionDescription :将形如 course_plan_id的字符串处理为coursePlanId，这是myeclipse 反向生成属性名的规则
	 * @param columnName
	 * @return
	 */
	public static String columnNameToPropertyName(String columnName){
		if(columnName == null) {
			return "";
		}
		String[] words = columnName.split("_");
		if(words.length == 1){
			return columnName;
		}
		String newKey = words[0].toLowerCase();//将第一个单词处理为小写
		for(int i=1;i<words.length;i++){
			String word = words[i];
			//将每个word中第一个字母处理为大写，其余字母处理为小写
			String newWord = word.substring(0,1).toUpperCase()+word.substring(1).toLowerCase();
			newKey+=newWord;
		}
		return newKey;
	}

}
