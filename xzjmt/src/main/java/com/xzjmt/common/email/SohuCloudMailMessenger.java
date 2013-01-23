package com.xzjmt.common.email;

import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sohu.sendcloud.Message;
import com.sohu.sendcloud.SendCloud;
import com.sohu.sendcloud.constat.AppFilter;
import com.sohu.sendcloud.exception.BlankException;
import com.xzjmt.common.page.PerformanceTest;
import com.xzjmt.common.util.DateUtils;

public class SohuCloudMailMessenger {

	private static final Logger log = LoggerFactory.getLogger(SohuCloudMailMessenger.class);
	private static final String MAIL_PERSONAL = "闲置姐妹淘";
	private static final String MAIL_FROM = "xzjmtme@126.com";
	
	private String apiuser;
	private String apikey;
	private boolean secure = false;
	private String server;
	private String port;

	public String getApiuser() {
		return apiuser;
	}

	public void setApiuser(String apiuser) {
		this.apiuser = apiuser;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public boolean isSecure() {
		return secure;
	}

	public void setSecure(boolean secure) {
		this.secure = secure;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	/**
	 * 根据分类设置来构造smtpapi头
	 */
	public static JSONObject createSmtpApiHeader(MailCategory category) {
		if(category == null) {
			return new JSONObject();
		}
		String [] filterSetting = new String[3];
		filterSetting[0] = String.format("%s: {settings: {enable: '%s'}}", AppFilter.ADDUNSUBSCRIBE, category.isScriptionTracking() ? 1 : 0);
		filterSetting[1] = String.format("%s: {settings: {enable: '%s'}}", AppFilter.ADDHIDEIMAGE, category.isOpenTracking() ? 1 : 0);
		filterSetting[2] = String.format("%s: {settings: {enable: '%s'}}", AppFilter.PROCESSURLREPLACE, category.isClickTracking() ? 1 : 0);
		String xSmtpApi = String.format("{category:['%s'], filters: {%s}}", category.getLabel(), StringUtils.join(filterSetting, ","));
		JSONObject smtpApiJson = JSONObject.fromObject(xSmtpApi);
		return smtpApiJson;
	}
	
	/**
	 * 使用搜狐ESP云邮件服务的Sdk方式发送
	 */
	public void sendCloudMailViaSdk(MailHeader header, Map<String, Object> model, String htmlText) throws BlankException {
		PerformanceTest test = new PerformanceTest();
		String body = header.getBody();
		if(StringUtils.isBlank(body)) {
			body = htmlText; // 使用模板生成html邮件内容
		}
		Message message = new Message(MAIL_FROM, MAIL_PERSONAL, header.getSubject(), body);
		List<String> recipients = Arrays.asList(header.getTo());
		message.addMultiRecipient(recipients);
		MailCategory category = header.getCategory();
		if(category != null) {
			message.addCategory(category.getLabel());
		}
		message.addFilterSetting(AppFilter.ADDUNSUBSCRIBE, "enable", category.isScriptionTracking() ? "1" : "0");
		message.addFilterSetting(AppFilter.ADDHIDEIMAGE, "enable", category.isOpenTracking() ? "1" : "0");
		message.addFilterSetting(AppFilter.PROCESSURLREPLACE, "enable", category.isClickTracking() ? "1" : "0");
		SendCloud sendCloud = new SendCloud(this.getApiuser(), this.getApikey(), this.isSecure(), message);
		try {
			sendCloud.send();
			log.info("{0} 向 {1} 的邮件发送成功。耗时{2}ms.", new Object [] {DateUtils.currentDatetime(), recipients, test.getTime()});
		} catch (Exception e) {
			log.info("{0} 向 {1} 的邮件发送失败！ >> " + e.getMessage(), DateUtils.currentDatetime(), recipients);
			e.printStackTrace();
		}
	}
	
	/**
	 * 使用搜狐ESP云邮件服务的Http方式发送
	 */
	public void sendCloudMailViaHttp(MailHeader header, Map<String, Object> model, String htmlText) {
		PerformanceTest test = new PerformanceTest();
		TrustManager easyTrustManager = new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}
			public void checkServerTrusted(X509Certificate[] chain, String authType)
					throws CertificateException {
			}
			public void checkClientTrusted(X509Certificate[] chain, String authType)
					throws CertificateException {
			}
		};
		
		String protocol = "http".concat(this.isSecure() ? "s" : "");
		String url = protocol + "://sendcloud.sohu.com/webapi/mail.send.json";
		HttpPost httpPost = new HttpPost(url);
		
		try {
			HttpClient httpClient = new DefaultHttpClient();
			if(this.isSecure()) {
				SSLContext sslContext = SSLContext.getInstance("ssl");
				sslContext.init(null, new TrustManager[] {easyTrustManager}, new SecureRandom());
				SSLSocketFactory sf = new SSLSocketFactory(sslContext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				Scheme scheme = new Scheme("https", 443, sf);
				httpClient.getConnectionManager().getSchemeRegistry().register(scheme);
			}
			
			Charset utf8 = Charset.forName("UTF-8");
			MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, utf8);
			entity.addPart("api_user", new StringBody(this.getApiuser(), utf8));
			entity.addPart("api_key", new StringBody(this.getApikey(), utf8));
			String recipient = StringUtils.join(header.getTo(), ';');
			entity.addPart("to", new StringBody(recipient, utf8));
			entity.addPart("from", new StringBody(MAIL_FROM, utf8));
			entity.addPart("fromname", new StringBody(MAIL_PERSONAL, utf8));
			entity.addPart("subject", new StringBody(header.getSubject(), utf8));
			
			// 添加smtpapi头
			MailCategory category = header.getCategory();
			JSONObject smtpApiJson = SohuCloudMailMessenger.createSmtpApiHeader(category);
			entity.addPart("x-smtpapi", new StringBody(smtpApiJson.toString(), utf8));
			
			// 设置信件message
			String body = header.getBody();
			if(StringUtils.isBlank(body)) {
				body = htmlText; // 使用模板生成html邮件内容
			}
			entity.addPart("html", new StringBody(body, utf8));
			
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String content = EntityUtils.toString(response.getEntity());
				log.info(content);
			} else {
				log.warn("网络连接失败，请稍后再试。");
			}
			EntityUtils.consume(entity);
			log.info("{0} 邮件发送成功。耗时{1}ms.", DateUtils.currentDatetime(), test.getTime());
		} catch (Exception e) {
			log.info("{0} 邮件发送失败！ >> " + e.getMessage(), DateUtils.currentDatetime());
			e.printStackTrace();
		} finally {
			httpPost.releaseConnection();
		}
	}
	
}