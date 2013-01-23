package com.xzjmt.action;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationObjectSupport;

import com.xzjmt.common.util.BaseConfig;
import com.xzjmt.common.util.ftp.KsdFtpClient;
import com.xzjmt.common.util.ftp.KsdFtpConfig;
import com.xzjmt.common.util.upload.KsdUpload;
import com.xzjmt.common.util.upload.Thumbnail;

@Controller
public class UploadAction extends WebApplicationObjectSupport {
	private static final Logger log = LoggerFactory.getLogger(UploadAction.class);
	@Autowired
	private BaseConfig baseConfig;
	
	@RequestMapping("/upload")
	public @ResponseBody
	String upload(ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		KsdUpload upload = KsdUpload.parseRequest(getServletContext(),request);
		String absolutePath  = null;
		//String uploadTypeVal = upload.getParamMap().get("uploadType");
		//String serverName = upload.getParamMap().get("serverName");
		absolutePath = upload.writeFile("");
		JsonResult result = JsonResult.UPLOAD_SUCCESS_CLOSE;
		result.put("thumbnail", JSONArray.fromObject(upload.getThumbnailList()).toString());

		result.put("relativeUrl", upload.getRelativeUrl());
		result.put("absolutePath", absolutePath);
		result.put("originalFileName", upload.getOriginalFileName());
		result.put("newFileName", upload.getNewFileName());
		return result.toString();
	}
	
}


