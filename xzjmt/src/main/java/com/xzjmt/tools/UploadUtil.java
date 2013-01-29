package com.xzjmt.tools;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadUtil {
	
	/**
     * <b>function:</b> 传入一个文件名，得到这个文件名称的后缀
     * @param fileName 文件名
     * @return 后缀名
     */
    public static String getSuffix(String fileName) {
    	 int index = fileName.lastIndexOf(".");
         if (index != -1) {
         	String suffix = fileName.substring(index);//后缀
         	return suffix; 
         } else {
         	return null;
         }
    }
    /**
     * <b>function:</b>和文件后缀一样，不同的是没有“.”
     * @param fileName 文件名称
     * @return
     */
    public static String getType(String fileName) {
   	 int index = fileName.lastIndexOf(".");
        if (index != -1) {
        	String suffix = fileName.substring(index + 1);//后缀
        	return suffix; 
        } else {
        	return null;
        }
    }
    /**
     * <b>function:</b> 传递一个文件名称和一个新名称，组合成一个新的带后缀文件名
     * 当传递的文件名没有后缀，会添加默认的后缀
     * @param fileName 文件名称
     * @param newName 新文件名称
     * @param nullSuffix 为没有后缀的文件所添加的后缀;eg:txt
     * @return String 文件名称
     */
    public static String getNewFileName(String fileName, String newName, String nullSuffix) {
        String suffix = getSuffix(fileName);
        if (suffix != null) {
        	newName += suffix; 
        } else {
        	newName = newName.concat(".").concat(nullSuffix);
        }
        return newName;   
    } 
    /**
     * <b>function:</b> 利用uuid产生一个随机的name
     * @param fileName 带后缀的文件名称
     * @return String 随机生成的name
     */
    public static String getRandomName(String fileName) {
    	String randomName = UUID.randomUUID().toString();
    	return getNewFileName(fileName, randomName, "txt");
    } 
    
    /**
     * <b>function:</b> 用当前日期、时间和1000以内的随机数组合成的文件名称
     * @param fileName 文件名称
     * @return 新文件名称
     */
    public static String getNumberName(String fileName) {	
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ssSSS");  
        Calendar calendar = Calendar.getInstance();  
        //以当前时间为文件名 格式如：2011-09-03-19-30-36047   
        String numberName = df.format(calendar.getTime());  		
		return getNewFileName(fileName, numberName, "txt");
    }
    /**
     * <b>function:</b>删除文件
     * @param file 要删除的文件
     * @return 是否删除成功
     */
    public static boolean removeFile(File file) {
    	boolean flag = false;
    	if (file != null && file.exists()) {
    		flag = file.delete();
    	}
    	return flag;
    }
    
    public static String upload(HttpServletRequest request){
    	//上传文件目标文件夹
        String folder = "upload";		 
	 	DiskFileItemFactory fac = new DiskFileItemFactory();  
     	ServletFileUpload upload = new ServletFileUpload(fac);  
        upload.setHeaderEncoding("utf-8"); 
        List<FileItem> fileList=null;
        try {  
        	//得到所有的表单域，它们目前都被当作FileItem
            fileList = upload.parseRequest(request);  
        } catch (FileUploadException e1) {  
            e1.printStackTrace();  
        }  
        //根路径  
        String root=request.getSession().getServletContext().getRealPath("");  
        //图片保存完整路径  
        String savePath = root+File.separator+folder+File.separator;  
        File f1 = new File(savePath);  
        if (!f1.exists()) {  
            f1.mkdirs();  
        }  
        //原文件名称  
        String name = "";  
        //新生成的文件名称 ，包含扩展名 
        String newName = "";  
        //扩展名  
        String extName = "";  
        boolean flag = true;  
        Iterator<FileItem> it = fileList.iterator();  
        JSONObject json = new JSONObject();
        while (it.hasNext()) {  
            FileItem item = it.next();  
            // 如果是文件类型字段  
            if (!item.isFormField() && item.getName().length() > 0) {  
                name = item.getName();  
                //扩展名
                extName = getSuffix(name);  
                // 定义允许上传的文件类型  
                List<String> fileTypes = new ArrayList<String>();  
                fileTypes.add(".jpg");  
                fileTypes.add(".jpeg");  
                fileTypes.add(".gif");  
                fileTypes.add(".png");  
                if(!fileTypes.contains(extName.toLowerCase())){  
                    flag = false;  
                }  
                if(flag) {  
                	newName = UploadUtil.getNumberName(name);
                    File newFile = new File(savePath + newName);  
                    try {
						item.write(newFile);
					} catch (Exception e) {
						e.printStackTrace();
					}  
                    Map<Object,Object> map = new HashMap<Object, Object>();
                    map.put("statusCode", "200");
                    map.put("successFileFullName", File.separator + folder + File.separator + newName);                    
            		json.putAll(map);
                }   
            }  
        } 
        return json.toString();
    }




 

}
