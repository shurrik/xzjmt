package com.xzjmt.common.util.upload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.xzjmt.common.exception.XzBizException;
import com.xzjmt.common.util.DateUtils;
import com.xzjmt.common.util.ftp.KsdFtpClient;
import com.xzjmt.common.util.ftp.KsdFtpConfig;

/**
 * @author <p>Innate Solitary 于 2012-2-24 下午3:11:18 修改（添加了xls, txt和视频文件格式）</p>
 * @formatter:off
 */
public final class KsdUpload {
	//允许上传的文件类型
	private final static String[] ALLOW_FILE_TYPE = { 
		".jpg", ".jpeg", ".gif",".png",".pdf",".swf",".ftl",".html",".js",".css", ".xls", ".xlsx", ".txt",
		".mpeg", ".mpg", ".mpe", ".3gp", ".avi", ".rmvb", ".rm", ".wmv", ".asf", ".asx", ".mov", 
		".mp4", ".m4v", ".dat", ".mkv", ".flv", ".vob",".doc",".docx", ".zip", ".rar"
	};
	public final static String FILES_DIR = "files";
	public final static String LECTURE_SERVER_NAME = "static";
	public final static String AD_SERVER_NAME = "ad";
	private Map<String,String> paramMap;
	private String root;
	private FileItem fileItem;
	//private String folder = FILES_DIR;
	private String folder = "";
	private String savePath;
	private String originalFileName;
	private String newFileName;
	private String relativeUrl;
	private String absolutePath;
	private List<Thumbnail> thumbnailList;

	private KsdUpload(){}
	@SuppressWarnings("unchecked")
	public static KsdUpload parseRequest(ServletContext servletContext,HttpServletRequest request) throws Exception{
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		List<FileItem> fileList = upload.parseRequest(request);
		
		KsdUpload ksdUpload = new KsdUpload();
		ksdUpload.parse(servletContext,request,fileList);
		return ksdUpload;
	}
	
	/**
	 * 直接上传文件到指定服务器，如上传到url为http://192.168.1.201:8080/files/123/123123123/123123123.swf
	 * @param serverName	服务器名字，如果是“ad”，那么就是广告服务器，否则是默认服务器
	 * @param absolutePath	本地即将上传到服务器的文件物理路径 E:\ksdcms\src\main\webapp\files\123\123123123\123123123.swf
	 * @param folder		即将在服务器上上传的文件目录结构 /123/123123123/
	 * @return
	 */
	public static int upload2Ftp(String serverName, String absolutePath, String folder) throws XzBizException{
		KsdUpload upload =  new KsdUpload();
		upload.setAbsolutePath(absolutePath);
		upload.setFolder(folder);
		int result = 0;
		try {
			upload.uploadToFtp(serverName);
		} catch (SocketException e) {
			result = 1;
			throw new XzBizException("服务器连接失败！");
		} catch (IOException e) {
			result = 1;
			throw new XzBizException("文件上传失败！");
		}
		return result;
	}

	/**
	 * 返回文件保存的绝对路径
	 * @param serverName 服务器名 "ad":表示上传到ad服务器；为空表示上传到默认的ftp服务器
	 * @return
	 * @throws Exception
	 */
	public String  writeFile(String serverName) throws Exception{
		File f1 = new File(savePath);
		if (!f1.exists()) {
			f1.mkdirs();
		}
		this.absolutePath = savePath + getNewFileName() + getExtName();
		
		File f = new File(absolutePath);
		fileItem.write(f);
		relativeUrl = folder + "/" + getNewFileName() + getExtName();
		this.writeThumbnail(absolutePath);
		//上传到ftp
		this.uploadToFtp(serverName);
		return absolutePath;
	}
	
	
	/**
	 * 返回文件保存的绝对路径
	 * @param serverName 服务器名 "ad":表示上传到ad服务器；为空表示上传到默认的ftp服务器
	 * @return
	 * @throws Exception
	 */
	public String  writeFileToStatic(String serverName,String staticBasePath) throws Exception{
		File f1 = new File(savePath);
		if (!f1.exists()) {
			f1.mkdirs();
		}
		this.absolutePath = savePath + getNewFileName() + getExtName();
		
		File f = new File(absolutePath);
		fileItem.write(f);
		String fileDir = "/lectures/" + DateFormatUtils.format(new Date(), "yyyyMMdd") + "/" + String.valueOf(System.currentTimeMillis());
		relativeUrl = fileDir + "/" + getNewFileName() + getExtName();
		this.writeThumbnail(absolutePath);
		fileDir = staticBasePath+fileDir;
		uploadToFtp(serverName, fileDir);
		return absolutePath;
	}
	public String writeFileWithName(String serverName, String filename) throws Exception{
		File f1 = new File(savePath);
		if (!f1.exists()) {
			f1.mkdirs();
		}
		this.absolutePath = savePath + filename;
		
		File f = new File(absolutePath);
		fileItem.write(f);
		relativeUrl = folder + "/" + filename;
		this.writeThumbnail(absolutePath);
		//上传到ftp
		this.uploadToFtp(serverName);
		return absolutePath;
	}
	/**
	 * 需要给图片加入水印
	 * @param waterRemark
	 * @param serverName 服务器名 "ad":表示上传到ad服务器；为空表示上传到默认的ftp服务器
	 * @return
	 * @throws Exception
	 */
	public String  writeFile(String waterRemark, String serverName) throws Exception{
		File f1 = new File(savePath);
		if (!f1.exists()) {
			f1.mkdirs();
		}
		this.absolutePath = savePath + getNewFileName() + getExtName();
		
		File f = new File(absolutePath);
		fileItem.write(f);
		relativeUrl = folder + "/" + getNewFileName() + getExtName();
	    this.writeThumbnail(absolutePath,waterRemark);
		//上传到ftp
		this.uploadToFtp(serverName);
		return absolutePath;
	}
	/**
	 * 上传文件到ftp服务器
	 * @param serverName  服务器名 "ad":表示上传到ad服务器；为空表示上传到默认的ftp服务器
	 * @throws IOException 
	 * @throws SocketException 
	 * @throws ParseException 
	 */
	public void uploadToFtp(String serverName) throws SocketException, IOException{
		KsdFtpConfig config = new KsdFtpConfig();
		//加载配置文件
		if(StringUtils.isNotBlank(serverName) && AD_SERVER_NAME.equalsIgnoreCase(serverName)){
			config.loadAdProperties();
		}
		else if (StringUtils.isNotBlank(serverName) && KsdUpload.LECTURE_SERVER_NAME.equalsIgnoreCase(serverName)) {
			config.loadStaticProperties();
		}else{
			config.loadProperties();
		}
		KsdFtpClient ftpClient = new KsdFtpClient();
		ftpClient.connectServer(config);
		//上传到ftp目录
		File f = new File(absolutePath);
		String ftpfolder = folder;
		if(!ftpfolder.endsWith("/")){
			ftpfolder = ftpfolder+"/";
		}
		//增加当前月份一级
		//ftpfolder = ftpfolder+DateUtils.currentMonth()+"/";
		ftpClient.uploadFile(ftpfolder, f);
		if(this.getThumbnailList() != null && this.getThumbnailList().size() > 0){
			for(Thumbnail thum:this.getThumbnailList()){
				File file = new File(thum.getAbsolutePath());
				ftpClient.uploadFile(ftpfolder, file);
			}
		}
		ftpClient.closeServer();
	}
	
	/**
	 * 上传文件到ftp服务器
	 * @param serverName  服务器名 "ad":表示上传到ad服务器；为空表示上传到默认的ftp服务器
	 * @throws IOException 
	 * @throws SocketException 
	 */
	public void uploadToFtp(String serverName,String ftpFolder) throws SocketException, IOException{
		if (StringUtils.isNotEmpty(ftpFolder)) {
			this.folder = ftpFolder;
		}
		uploadToFtp(serverName);
	}
	
	public void writeThumbnail(String srcFile) throws IOException{
		//是否需要生成缩略图
		if(this.isNeedThumbnail()){
			List<Thumbnail> list = this.getThumbnailList();
			for(Thumbnail thumnail:list){
				Thumbnails.of(srcFile)
				.forceSize(thumnail.getWidth(), thumnail.getHeight()).outputQuality(0.9f)
				//equivalent to calling: .size(thumnail.getWidth(), thumnail.getHeight()).keepAspectRatio(false)
				.toFile(thumnail.getAbsolutePath());
			}
		}
	}
	/**
	 * 缩略图并且有水印
	 * @param srcFile
	 * @param waterRemark
	 * @throws IOException
	 */
	public void writeThumbnail(String srcFile,String waterRemark) throws IOException{
		//是否需要生成缩略图
		if(this.isNeedThumbnail()){
			List<Thumbnail> list = this.getThumbnailList();
			for(Thumbnail thumnail:list){
				Thumbnails.of(srcFile)
				.size(thumnail.getWidth(), thumnail.getHeight()).outputQuality(0.9f)
				.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(waterRemark)), 0.5f)
				.toFile(thumnail.getAbsolutePath());
			}
		}
	}
	/**
	 * 返回uploadify上传的参数
	 * @return
	 */
	public Map<String,String> getParamMap(){
		return this.paramMap;
	}
	/**
	 * 返回文件扩展名
	 * @return
	 */
	public String getExtName(){
		if (originalFileName.lastIndexOf(".") >= 0) {
			return originalFileName.substring(originalFileName.lastIndexOf("."));
		}
		return "";
	}
	/**
	 * 是否需要生成缩略图
	 * @return
	 */
	public boolean isNeedThumbnail(){
		return !this.getThumbnailList().isEmpty();
	}
	
	private String buildNewFileName(){
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ssSSS");
//		Calendar calendar = Calendar.getInstance();
//		// 以当前时间为文件名 格式如：2011-09-03-19-30-36047
//		String newName = df.format(calendar.getTime());
//		return newName;
		return String.valueOf(System.currentTimeMillis());
	}
	/**
	 * 是否为合法的文件类型，根据扩展名判断
	 * @return
	 */
	private boolean isLegalType(){
		String extName = getExtName();
		// 定义允许上传的文件类型
		return !Arrays.asList(ALLOW_FILE_TYPE).contains(extName.toLowerCase());
	}
	
	/**
	 * 获取上传时表单提交的参数
	 * 
	 * @param fileList
	 * @return
	 * @throws Exception 
	 */
	private void parse(ServletContext servletContext,HttpServletRequest request,List<FileItem> fileList) throws Exception {
		this.root = servletContext.getRealPath("");
		Map<String, String> map = new HashMap<String, String>();
		for (FileItem item : fileList) {
			if (item.isFormField()) {
				map.put(item.getFieldName(), item.getString());
			}else if(StringUtils.isNotEmpty(item.getName())){
				this.fileItem = item;
				this.originalFileName = item.getName();
				if(isLegalType()){
					throw new Exception("文件格式非法,不允许上传！");
				}
			}
		}
		
		//是否需要重命名文件
		if("false".equalsIgnoreCase(map.get("isRename"))){
			this.newFileName = this.originalFileName.substring(0, originalFileName.lastIndexOf("."));
		}else{
			this.newFileName = buildNewFileName();
		}
		String path = map.get("folder");
		 if(StringUtils.isEmpty(path)){
			path = request.getParameter("folder");
		}
		folder = path.substring(1,path.length())+"/"+DateUtils.currentMonth();
		this.paramMap = map;
		Enumeration enu = request.getParameterNames();
		while(enu.hasMoreElements()){
			String paramName = (String)enu.nextElement();
			this.paramMap.put(paramName, request.getParameter(paramName));
		}
		savePath = root + File.separator + folder + File.separator;
		thumbnailList = parseThumbnail(this,paramMap);
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public String getNewFileName() {
		return newFileName;
	}
	public String getRelativeUrl() {
		return relativeUrl;
	}
	private static List<Thumbnail> parseThumbnail(KsdUpload upload,Map<String,String> paramMap) throws Exception{
		List<Thumbnail> list = new ArrayList<Thumbnail>();
		Iterator<String> ite = paramMap.keySet().iterator();
		while(ite.hasNext()){
			String paramName = ite.next();
			if(!paramName.startsWith("thumbnail")||!paramName.endsWith(".width")){
				continue;
			}
			String prefix = paramName.substring(0, paramName.lastIndexOf("."));
			String sIndex = paramName.substring(paramName.indexOf("[")+1, paramName.indexOf("]"));
			int sWidth = Integer.parseInt(paramMap.get(prefix+".width"));
			int sHeight = Integer.parseInt(paramMap.get(prefix+".height"));
			Thumbnail thumbnail = new Thumbnail();
			thumbnail.setIndex(Integer.parseInt(sIndex));
			//如果宽度或者高度为0时 则根据等比例进行缩放
			if (sWidth == 0 || sHeight == 0 ) {
				int value = calcRatioScale(upload.fileItem, sWidth, sHeight);
				if (sWidth == 0) {
					sWidth = value;
				}else{
					sHeight = value;
				}
			}
			thumbnail.setWidth(sWidth);
			thumbnail.setHeight(sHeight);
			//缩略图保存绝对路径
			String absoPath = null;
			if (upload.getExtName().equals(".png") || upload.getExtName().equals(".gif")) {
				absoPath = upload.getSavePath()+upload.getNewFileName()+"_"+String.valueOf(thumbnail.getWidth())+"_"+String.valueOf(thumbnail.getHeight())+".jpg";
			}else{
				absoPath = upload.getSavePath()+upload.getNewFileName()+"_"+String.valueOf(thumbnail.getWidth())+"_"+String.valueOf(thumbnail.getHeight())+upload.getExtName();
			}
			thumbnail.setAbsolutePath(absoPath);
			//缩略图访问相对路径地址
			String relUrl = null;
			if (upload.getExtName().equals(".png") || upload.getExtName().equals(".gif")) {
				relUrl = upload.getFolder() + "/" + upload.getNewFileName()+"_"+String.valueOf(thumbnail.getWidth())+"_"+String.valueOf(thumbnail.getHeight())+".jpg";
			}else{
				relUrl = upload.getFolder() + "/" + upload.getNewFileName()+"_"+String.valueOf(thumbnail.getWidth())+"_"+String.valueOf(thumbnail.getHeight())+upload.getExtName();
			}
			thumbnail.setRelativeUrl(relUrl);
			list.add(thumbnail);
			ite.remove();
		}
		Collections.sort(list, new Comparator<Thumbnail>() {
			public int compare(Thumbnail o1, Thumbnail o2) {
				if (o1.getIndex() > o2.getIndex()) {
					return 1;
				} else if (o1.getIndex() == o2.getIndex()) {
					return 0;
				} else {
					return -1;
				}
			}
		});
		return list;
	}
	public List<Thumbnail> getThumbnailList() {
		return thumbnailList;
	}
	public String getSavePath() {
		return savePath;
	}
	
	
	/*public static void main(String args[]) throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		map.put("thumbnail[0].width", String.valueOf("300"));
		map.put("thumbnail[0].height", String.valueOf("400"));
		
		map.put("thumbnail[1].width", String.valueOf("100"));
		map.put("thumbnail[1].height", String.valueOf("200"));
		
		map.put("thumbnail[2].width", String.valueOf("120"));
		map.put("thumbnail[2].height", String.valueOf("160"));
		map.put("zzzzz", String.valueOf("160"));
		//parseThumbnail(map);
	}*/
	public String getFolder() {
		return folder;
	}
	public String getAbsolutePath() {
		return absolutePath;
	}
	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	
	//计算图片的比例
	public static int calcRatioScale(String imgPath,int width,int heigth) throws Exception{
		int value = 0;
		BufferedImage image = ImageIO.read(new FileInputStream(imgPath));
		int imgWidth = image.getWidth();
		int imgHeight = image.getHeight();
		//System.out.println(imgWidth +"*" + imgHeight);
		if (width == 0) {
			value = imgWidth * heigth /imgHeight ;
		}else if(heigth == 0){
			value = imgHeight * width /imgWidth ;
		}
		return value;
	}
	
	//计算图片的比例
	public static int calcRatioScale(FileItem fileItem,int width,int heigth) throws Exception{
		int value = 0;
		BufferedImage image = ImageIO.read(fileItem.getInputStream());
		int imgWidth = image.getWidth();
		int imgHeight = image.getHeight();
		//System.out.println(imgWidth +"*" + imgHeight);
		if (width == 0) {
			value = imgWidth * heigth /imgHeight ;
		}else if(heigth == 0){
			value = imgHeight * width /imgWidth ;
		}
		return value;
	}
		
	public static void main(String[] args) throws Exception {
		String srcFile = "D:\\workspace\\work\\picture\\2.png";
		for(int i=0; i<1;i++){
			
			try {
				Thumbnails.of(srcFile).scale(1f).outputQuality(1f).toFile("d:\\test\\"+i+"");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
