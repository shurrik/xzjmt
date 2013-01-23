package com.xzjmt.common.email;

import com.sohu.sendcloud.exception.BlankException;

public class TestMailSending {

	public static void main(String[] args) throws BlankException {
		StringBuffer sb = new StringBuffer();
//		sb.append("13571887253@139.com;18629435853@wo.com.cn;13379286932@189.cn;");
//		sb.append("feikouis@163.com;feikouis@126.com;feikouis@yeah.net;");
		sb.append("feikouis@qq.com;kouis@foxmail.com;");
//		sb.append("kouis@sina.com;kouis@sina.cn;feikouis@sina.com.cn;");
//		sb.append("kouis@sohu.com;kouis@sogou.com;");
//		sb.append("feikouis@yahoo.com.cn;feikouis@tom.com;");
//		sb.append("feikouis@gmail.com;kouis@live.cn;feikouis@hotmail.com;");
//		sb.append("feikouis@chinaren.com;feikouis@eyou.com;kouis@21cn.com;");
		
		String htmlText = "<html xmlns='http://www.w3.org/1999/xhtml'><head>" +
					"<meta http-equiv='Content-Type' content='text/html; charset=utf-8' /></head>" +
					"<body><b>这是测试邮件</b>" +
					"<p>アリババは、仕入れたい商材の調達、問屋を探せる卸サイトです。OEMや小ロットなど" +
					"の仕入れの条件は、メーカー・問屋に直接交渉。中国から仕入れのためコストダウン</p>" +
					"<p>트친여러분은 어떤 모습으로 웨딩촬영을 하고 싶으세요? 화제가 되고" +
					" 있는 '역대 최고의 웨딩 사진'은 어떤 모습일지 만나보세요화제가 " +
					"되고 있는 '역대 최고의 웨딩 사진'은 어떤 모습일지 만나보세요별빛이 쏟아</p>" +
					"<p><a href='http://www.baidu.com'>百度一下</a></p><p style='text-align:right'>2012年12月21日</p></body></html>";
		String [] address = sb.toString().split(";");
		MailHeader header = new MailHeader();
		header.setTo(address);
		header.setCategory(MailCategory.ONLY_TEST);
		header.setSubject("来自玛雅的问候");
		header.setBody(htmlText);
		SohuCloudMailMessenger messenger = new SohuCloudMailMessenger();
		messenger.setApiuser("wangyuanbo");
		messenger.setApikey("kaoshidian007");
		messenger.sendCloudMailViaHttp(header, null, null);
	}
	
}
