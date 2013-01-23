package com.xzjmt.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;


public class JsonResult {
	//保存成功
	public final static JsonResult SAVE_SUCCESS;
	//保存失败
	public final static JsonResult SAVE_FAILURE;
	//保存成功并关闭
	public final static JsonResult SAVE_SUCCESS_CLOSE;
	//删除成功
	public final static JsonResult DELETE_SUCCESS;
	//删除失败
	public final static JsonResult DELETE_FAILURE;
	//删除成功并关闭
	public final static JsonResult DELETE_SUCCESS_CLOSE;
	//修改成功并关闭
	public final static JsonResult MODIFY_SUCCESS_CLOSE;
	//修改失败并关闭
	public final static JsonResult MODIFY_FAILURE_CLOSE;
	//上传成功
	public final static JsonResult UPLOAD_SUCCESS_CLOSE;
	//session过期，请重新登录
	public final static JsonResult SESSION_TIMEOUT;
	//操作成功
	public final static JsonResult OPERATE_SUCCESS;
	public final static JsonResult OPERATE_SUCCESS_CLOSE;
	//操作失败
	public final static JsonResult OPERATE_FAILURE_CLOSE;
	//发布成功
	public final static JsonResult ISSUANCE_SUCCESS_CLOSE;
	
	// 审核
	public final static JsonResult CHECK_SUCCESS;
	public final static JsonResult CHECK_FAILURE;
	
	public final static JsonResult OPTION_FAILUTE;
	
	public final static JsonResult TAG_SAVE_SUCCESS;
	
	public final static JsonResult TAG_SAVE_FAILURE;
	
	public final static JsonResult CUSTOM_SUCCESS_CLOSE;
	
	static{
		SAVE_SUCCESS = new JsonResult();
		SAVE_SUCCESS.put("statusCode", "200");
		SAVE_SUCCESS.put("message", "保存成功");
		SAVE_SUCCESS.put("navTabId", "main");
		SAVE_SUCCESS.put("forwardUrl", "");
		
		SAVE_FAILURE = new JsonResult();
		SAVE_FAILURE.put("statusCode", "300");
		SAVE_FAILURE.put("message", "保存失败");
		SAVE_FAILURE.put("navTabId", "main");
		SAVE_FAILURE.put("forwardUrl", "");
		
		DELETE_SUCCESS = new JsonResult();
		DELETE_SUCCESS.put("statusCode", "200");
		DELETE_SUCCESS.put("message", "删除成功");
		DELETE_SUCCESS.put("navTabId", "main");
		DELETE_SUCCESS.put("forwardUrl", "");
		
		DELETE_FAILURE = new JsonResult();
		DELETE_FAILURE.put("statusCode", "300");
		DELETE_FAILURE.put("message", "删除失败");
		DELETE_FAILURE.put("navTabId", "main");
		DELETE_FAILURE.put("forwardUrl", "");
		
		SAVE_SUCCESS_CLOSE = new JsonResult();
		SAVE_SUCCESS_CLOSE.put("statusCode", "200");
		SAVE_SUCCESS_CLOSE.put("message", "保存成功");
		SAVE_SUCCESS_CLOSE.put("navTabId", "main");
		SAVE_SUCCESS_CLOSE.put("callbackType", "closeCurrent");
		SAVE_SUCCESS_CLOSE.put("forwardUrl", "");

		DELETE_SUCCESS_CLOSE = new JsonResult();
		DELETE_SUCCESS_CLOSE.put("statusCode", "200");
		DELETE_SUCCESS_CLOSE.put("message", "删除成功");
		DELETE_SUCCESS_CLOSE.put("navTabId", "main");
		DELETE_SUCCESS_CLOSE.put("callbackType", "closeCurrent");
		DELETE_SUCCESS_CLOSE.put("forwardUrl", "");
		
		MODIFY_SUCCESS_CLOSE = new JsonResult();
		MODIFY_SUCCESS_CLOSE.put("statusCode", "200");
		MODIFY_SUCCESS_CLOSE.put("message", "修改成功");
		MODIFY_SUCCESS_CLOSE.put("navTabId", "main");
		MODIFY_SUCCESS_CLOSE.put("callbackType", "closeCurrent");
		MODIFY_SUCCESS_CLOSE.put("forwardUrl", "");
		
		MODIFY_FAILURE_CLOSE = new JsonResult();
		MODIFY_FAILURE_CLOSE.put("statusCode", "300");
		MODIFY_FAILURE_CLOSE.put("message", "修改失败");
		MODIFY_FAILURE_CLOSE.put("navTabId", "main");
		MODIFY_FAILURE_CLOSE.put("callbackType", "closeCurrent");
		MODIFY_FAILURE_CLOSE.put("forwardUrl", "");
		
		UPLOAD_SUCCESS_CLOSE = new JsonResult();
		UPLOAD_SUCCESS_CLOSE.put("statusCode", "200");
		//UPLOAD_SUCCESS_CLOSE.put("message", "successful");
		UPLOAD_SUCCESS_CLOSE.put("navTabId", "main");
		UPLOAD_SUCCESS_CLOSE.put("rel", "");
		UPLOAD_SUCCESS_CLOSE.put("callbackType", "closeCurrent");
		//UPLOAD_SUCCESS_CLOSE.put("forwardUrl", "");	
		
		SESSION_TIMEOUT = new JsonResult();
		SESSION_TIMEOUT.put("statusCode", "301");
		SESSION_TIMEOUT.put("message", "会话超时，请重新登录。");
		SESSION_TIMEOUT.put("navTabId", "main");
		SESSION_TIMEOUT.put("callbackType", "");
		SESSION_TIMEOUT.put("forwardUrl", "");
		
		OPERATE_SUCCESS = new JsonResult();
		OPERATE_SUCCESS.put("statusCode", "200");
		OPERATE_SUCCESS.put("message", "操作成功");
		OPERATE_SUCCESS.put("navTabId", "main");
		OPERATE_SUCCESS.put("callbackType", "");
		OPERATE_SUCCESS.put("forwardUrl", "");
		
		OPERATE_SUCCESS_CLOSE = new JsonResult();
		OPERATE_SUCCESS_CLOSE.put("statusCode", "200");
		OPERATE_SUCCESS_CLOSE.put("message", "操作成功");
		OPERATE_SUCCESS_CLOSE.put("navTabId", "main");
		OPERATE_SUCCESS_CLOSE.put("callbackType", "closeCurrent");
		OPERATE_SUCCESS_CLOSE.put("forwardUrl", "");
		
		OPERATE_FAILURE_CLOSE = new JsonResult();
		OPERATE_FAILURE_CLOSE.put("statusCode", "300");
		OPERATE_FAILURE_CLOSE.put("message", "操作失败");
		OPERATE_FAILURE_CLOSE.put("navTabId", "main");
		OPERATE_FAILURE_CLOSE.put("callbackType", "closeCurrent");
		OPERATE_FAILURE_CLOSE.put("forwardUrl", "");
		
		ISSUANCE_SUCCESS_CLOSE=new JsonResult();
		ISSUANCE_SUCCESS_CLOSE.put("statusCode", "200");
		ISSUANCE_SUCCESS_CLOSE.put("message", "发布成功");
		ISSUANCE_SUCCESS_CLOSE.put("navTabId", "main");
		ISSUANCE_SUCCESS_CLOSE.put("callbackType", "");
		ISSUANCE_SUCCESS_CLOSE.put("forwardUrl", "");
		
		CHECK_SUCCESS = new JsonResult();
		CHECK_SUCCESS.put("statusCode", "200");
		CHECK_SUCCESS.put("message", "审核成功");
		CHECK_SUCCESS.put("navTabId", "main");
		CHECK_SUCCESS.put("callbackType", "closeCurrent");
		CHECK_SUCCESS.put("forwardUrl", "");
		
		CHECK_FAILURE = new JsonResult();
		CHECK_FAILURE.put("statusCode", "200");
		CHECK_FAILURE.put("message", "审核失败");
		CHECK_FAILURE.put("navTabId", "main");
		CHECK_FAILURE.put("callbackType", "closeCurrent");
		CHECK_FAILURE.put("forwardUrl", "");
		
		OPTION_FAILUTE = new JsonResult();
		OPTION_FAILUTE.put("statusCode", "404");
		OPTION_FAILUTE.put("message", "有异常");
		
		TAG_SAVE_SUCCESS = new JsonResult();
		TAG_SAVE_SUCCESS.put("statusCode", "200");
		TAG_SAVE_SUCCESS.put("message", "标签保存/更新成功");
		TAG_SAVE_SUCCESS.put("navTabId", "main");

		TAG_SAVE_FAILURE = new JsonResult();
		TAG_SAVE_FAILURE.put("statusCode", "200");
		TAG_SAVE_FAILURE.put("message", "标签保存/更新失败");
		TAG_SAVE_FAILURE.put("navTabId", "main");
		
		CUSTOM_SUCCESS_CLOSE = new JsonResult();
		CUSTOM_SUCCESS_CLOSE.put("statusCode", "200");
		CUSTOM_SUCCESS_CLOSE.put("message", "修改成功");
		CUSTOM_SUCCESS_CLOSE.put("navTabId", "main"); // 指定要刷新的层的id，如果是main则会去刷新整个标签
		CUSTOM_SUCCESS_CLOSE.put("callbackType", "closeCurrent");
		CUSTOM_SUCCESS_CLOSE.put("treeId", ""); // 指定要刷新树的id
		CUSTOM_SUCCESS_CLOSE.put("treeNodeId", ""); // 需要刷新树的节点id
		
	}
	private Map<Object,Object> map = new HashMap<Object, Object>();
	public JsonResult put(Object key,Object value){
		this.map.put(key, value);
		return this;
	}
	public String toString() {
		JSONObject json = new JSONObject();
		json.putAll(map);
		return json.toString();
	}
	
	public static JsonResult getFailureJsonResult(String msg){
		JsonResult failure = new JsonResult();
		failure.put("statusCode", "300");
		failure.put("navTabId", "main");
		failure.put("forwardUrl", "");
		failure.put("message", msg);
		return failure;
	}
	
	public JsonResult getFailureResult(String msg){
		JsonResult failure = new JsonResult();
		failure.put("statusCode", "300");
		failure.put("navTabId", "main");
		failure.put("forwardUrl", "");
		failure.put("message", msg);
		return failure;
	}
	
	public static JsonResult getSuccessJsonResult(String msg){
		JsonResult success = new JsonResult();
		success.put("statusCode", "200");
		success.put("navTabId", "main");
		success.put("forwardUrl", "");
		success.put("message", msg);
		return success;
	}
	
	public JsonResult getSuccessResult(String msg){
		JsonResult success = new JsonResult();
		success.put("statusCode", "200");
		success.put("navTabId", "main");
		success.put("forwardUrl", "");
		success.put("message", msg);
		return success;
	}
	
	public static JsonResult getJsonResult(String treeId, String treeNodeId, String statusCode, String message, String callbackType, String navTabId) {
		JsonResult json = new JsonResult ();
		json.put("treeId", treeId);
		json.put("treeNodeId", treeNodeId);
		json.put("statusCode", StringUtils.isEmpty(statusCode) ? "200" : statusCode);
		json.put("message", message);
		if(StringUtils.isNotEmpty(callbackType)) {
			json.put("callbackType", callbackType);
		}
		if(StringUtils.isNotEmpty(navTabId)) {
			json.put("navTabId", navTabId);
		}
		return json;
	}
}
