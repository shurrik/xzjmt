package com.xzjmt.common.util.ftp;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class KsdFtpConfig {
	/** ftp服务器地址 */
	private String server;
	
	/** ftp服务器端口 */
	private int port = 21;
	
	/** ftp服务器用户名 */
	private String username;
	
	/** ftp服务器密码 */
	private String password;
	
	/** ftp服务器显示风格 一般为unix 或者nt*/
	private String FTPStyle;
	
	/** 本地编码格式 */
	private String localEncoding = "UTF-8";
	
	/** 远程编码格式 */
	private String remoteEncoding = "UTF-8";
	
	/** 是否设置 passiveMode模式 */
	private boolean passiveMode;
	
	/** 是否设置以二进制传输文件 */
	private boolean binaryFileType = true;
	
	/** ftp服务器工作根目录 */
	private String rootPath;
	
	private String location;
	
	public void loadProperties() throws IOException{
		Resource res = new ClassPathResource("filesserver.properties");
		Properties p = new Properties();   
		p.load(res.getInputStream());   
		if(p.getProperty("filesserver.host")!=null){
			this.setServer(p.getProperty("filesserver.host"));
		}
		if(p.getProperty("filesserver.port")!=null){
			this.setPort(Integer.parseInt(p.getProperty("filesserver.port")));
		}
		if(p.getProperty("filesserver.username")!=null){
			this.setUsername(p.getProperty("filesserver.username"));
		}
		if(p.getProperty("filesserver.password")!=null){
			this.setPassword(p.getProperty("filesserver.password"));
		}
	}
	
	public void loadAdProperties() throws IOException{
		Resource res = new ClassPathResource("adserver.properties");
		Properties p = new Properties();   
		p.load(res.getInputStream());   
		if(p.getProperty("adserver.host")!=null){
			this.setServer(p.getProperty("adserver.host"));
		}
		if(p.getProperty("adserver.port")!=null){
			this.setPort(Integer.parseInt(p.getProperty("adserver.port")));
		}
		if(p.getProperty("adserver.username")!=null){
			this.setUsername(p.getProperty("adserver.username"));
		}
		if(p.getProperty("adserver.password")!=null){
			this.setPassword(p.getProperty("adserver.password"));
		}
	}
	
	public void loadStaticProperties()  throws IOException{
		Resource res = new ClassPathResource("staticserver.properties");
		Properties p = new Properties();   
		p.load(res.getInputStream());   
		if(p.getProperty("staticserver.host")!=null){
			this.setServer(p.getProperty("staticserver.host"));
		}
		if(p.getProperty("staticserver.port")!=null){
			this.setPort(Integer.parseInt(p.getProperty("staticserver.port")));
		}
		if(p.getProperty("staticserver.username")!=null){
			this.setUsername(p.getProperty("staticserver.username"));
		}
		if(p.getProperty("staticserver.password")!=null){
			this.setPassword(p.getProperty("staticserver.password"));
		}
		
	}
	
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getFTPStyle() {
		return FTPStyle;
	}
	public void setFTPStyle(String fTPStyle) {
		FTPStyle = fTPStyle;
	}
	public String getLocalEncoding() {
		return localEncoding;
	}
	public void setLocalEncoding(String localEncoding) {
		this.localEncoding = localEncoding.trim();
	}
	public String getRemoteEncoding() {
		return remoteEncoding;
	}
	public void setRemoteEncoding(String remoteEncoding) {
		this.remoteEncoding = remoteEncoding.trim();		
	}
	public boolean isPassiveMode() {
		return passiveMode;
	}
	public void setPassiveMode(boolean passiveMode) {
		this.passiveMode = passiveMode;
	}
	public boolean isBinaryFileType() {
		return binaryFileType;
	}
	public void setBinaryFileType(boolean binaryFileType) {
			this.binaryFileType = binaryFileType;
	}
	public String getRootPath() {
		return rootPath;
	}
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	
}
