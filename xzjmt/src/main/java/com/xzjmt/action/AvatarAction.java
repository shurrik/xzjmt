package com.xzjmt.action;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xzjmt.common.util.BaseConfig;
import com.xzjmt.common.util.DateUtils;
import com.xzjmt.common.util.ftp.KsdFtpClient;
import com.xzjmt.common.util.ftp.KsdFtpConfig;
import com.xzjmt.common.util.image.ImageHelper;
import com.xzjmt.entity.User;
import com.xzjmt.manager.UserMng;

@Controller
public class AvatarAction extends BaseAction{

	@Autowired
	private BaseConfig baseConfig; 
	@Autowired
	private UserMng userMng;
	
	
	@RequestMapping("/self/avatar/generate")
	@ResponseBody
	public String upload(Model model,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = getCurrentUser();
		ServletContext servletContext = getServletContext();
		String root = servletContext.getRealPath("");
		int x = Integer.parseInt(request.getParameter("x"));
		int y = Integer.parseInt(request.getParameter("y"));
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		String srcPath = request.getParameter("srcPath");
		
		

		//String srcPath = root+"/resources/img/default_avatar.jpg";
		srcPath = baseConfig.getSTATICPCROOT()+"/"+srcPath;
		String subPath = root+"/img/avatar/"+DateUtils.currentMonth()+"/"+DateUtils.now().getTime()+".jpg";
		
		File f1 = new File(root+"/img/avatar/201301/");
		if (!f1.exists()) {
			f1.mkdirs();
		}
		
		String extName = srcPath.substring(srcPath.lastIndexOf("."));
		String format = extName.replace(".", "");
		
		ImageHelper imageHelper = new ImageHelper(x,y,width,height);
		imageHelper.setSrcpath(srcPath);  
		imageHelper.setSubpath(subPath);
		imageHelper.cut(format) ;
		String avatar = this.uploadToFtp(subPath,"/img/avatar");
		userMng.updateAvatar(user.getUserId(), avatar);
		return SUCC;
	}
	
	/** 上传文件到ftp服务器
	 * @param filePath
	 * @param folder
	 * @throws SocketException
	 * @throws IOException
	 */
	public String uploadToFtp(String filePath,String folder) throws SocketException, IOException{
		KsdFtpConfig config = new KsdFtpConfig();
		config.loadProperties();
		KsdFtpClient ftpClient = new KsdFtpClient();
		ftpClient.connectServer(config);
		//上传到ftp目录
		File f = new File(filePath);
		String ftpfolder = folder;
		if(!ftpfolder.endsWith("/")){
			ftpfolder = ftpfolder+"/";
		}
		//增加当前月份一级
		ftpfolder = ftpfolder+DateUtils.currentMonth()+"/";
		ftpClient.uploadFile(ftpfolder, f);
/*		if(this.getThumbnailList() != null && this.getThumbnailList().size() > 0){
			for(Thumbnail thum:this.getThumbnailList()){
				File file = new File(thum.getAbsolutePath());
				ftpClient.uploadFile(ftpfolder, file);
			}
		}*/
		ftpClient.closeServer();
		return ftpfolder+getFileName(filePath);
	}
	
	public String getFileName(String filePath)
	{
		String fileName = filePath.substring(filePath.lastIndexOf("/")).replace("/", "");
		return fileName;
	}
	
}
