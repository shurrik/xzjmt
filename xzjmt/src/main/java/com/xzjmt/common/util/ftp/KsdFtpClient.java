package com.xzjmt.common.util.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KsdFtpClient {
	private static final Logger log = LoggerFactory.getLogger(KsdFtpClient.class);
	private FTPClient ftpClient;
	public static final int BINARY_FILE_TYPE = FTP.BINARY_FILE_TYPE;
	public static final int ASCII_FILE_TYPE = FTP.ASCII_FILE_TYPE;

	// path should not the path from root index
	// or some FTP server would go to root as '/'.
	public void connectServer(KsdFtpConfig ftpConfig) throws SocketException,
			IOException {
		String server = ftpConfig.getServer();
		int port = ftpConfig.getPort();
		String user = ftpConfig.getUsername();
		String password = ftpConfig.getPassword();
		String location = ftpConfig.getLocation();
		connectServer(server, port, user, password, location);
	}

	public void connectServer(String server, int port, String user,String password, String path) throws SocketException, IOException {
		ftpClient = new FTPClient();
		ftpClient.connect(server, port);
		log.debug("Connected to " + server + ".");
		log.debug(String.valueOf(ftpClient.getReplyCode()));
		boolean isLogin = ftpClient.login(user, password);
		ftpClient.enterLocalPassiveMode();
		// Path is the sub-path of the FTP path
		if (StringUtils.isNotBlank(path)) {
			ftpClient.changeWorkingDirectory(path);
		}
	}

	// FTP.BINARY_FILE_TYPE | FTP.ASCII_FILE_TYPE
	// Set transform type
	public void setFileType(int fileType) throws IOException {
		ftpClient.setFileType(fileType);
	}

	public void closeServer() throws IOException {
		if (ftpClient.isConnected()) {
			ftpClient.disconnect();
		}
	}

	// =======================================================================
	// == About directory =====
	// The following method using relative path better.
	// =======================================================================

	public boolean changeDirectory(String path) throws IOException {
		return ftpClient.changeWorkingDirectory(path);
	}

	public boolean createDirectory(String pathName) throws IOException {
		return ftpClient.makeDirectory(pathName);
	}

	public boolean removeDirectory(String path) throws IOException {
		return ftpClient.removeDirectory(path);
	}

	// delete all subDirectory and files.
	public boolean removeDirectory(String path, boolean isAll)
			throws IOException {

		if (!isAll) {
			return removeDirectory(path);
		}

		FTPFile[] ftpFileArr = ftpClient.listFiles(path);
		if (ftpFileArr == null || ftpFileArr.length == 0) {
			return removeDirectory(path);
		}
		//
		for (FTPFile ftpFile : ftpFileArr) {
			String name = ftpFile.getName();
			if (ftpFile.isDirectory()) {
				System.out.println("* [sD]Delete subPath [" + path + "/" + name
						+ "]");
				removeDirectory(path + "/" + name, true);
			} else if (ftpFile.isFile()) {
				System.out.println("* [sF]Delete file [" + path + "/" + name
						+ "]");
				deleteFile(path + "/" + name);
			} else if (ftpFile.isSymbolicLink()) {

			} else if (ftpFile.isUnknown()) {

			}
		}
		return ftpClient.removeDirectory(path);
	}

	// Check the path is exist; exist return true, else false.
	public boolean existDirectory(String path) throws IOException {
		boolean flag = false;
		FTPFile[] ftpFileArr = ftpClient.listFiles(path);
		for (FTPFile ftpFile : ftpFileArr) {
			if (ftpFile.isDirectory()
					&& ftpFile.getName().equalsIgnoreCase(path)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	// =======================================================================
	// == About file =====
	// Download and Upload file using
	// ftpUtil.setFileType(FtpUtil.BINARY_FILE_TYPE) better!
	// =======================================================================

	// #1. list & delete operation
	// Not contains directory
	public List<String> getFileList(String path) throws IOException {
		// listFiles return contains directory and file, it's FTPFile instance
		// listNames() contains directory, so using following to filer
		// directory.
		// String[] fileNameArr = ftpClient.listNames(path);
		FTPFile[] ftpFiles = ftpClient.listFiles(path);

		List<String> retList = new ArrayList<String>();
		if (ftpFiles == null || ftpFiles.length == 0) {
			return retList;
		}
		for (FTPFile ftpFile : ftpFiles) {
			if (ftpFile.isFile()) {
				retList.add(ftpFile.getName());
			}
		}
		return retList;
	}

	public boolean deleteFile(String pathName) throws IOException {
		return ftpClient.deleteFile(pathName);
	}

	// #2. upload to ftp server
	// InputStream <------> byte[] simple and See API

	public boolean uploadFile(String fileName)throws IOException {
		File file = new File(fileName);
		boolean flag = false;
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(fileName);
			flag = ftpClient.storeFile(file.getName(), stream);
		} catch (IOException e) {
			flag = false;
			return flag;
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
		return flag;
	}




	// #3. Down load

	public boolean download(String remoteFileName, String localFileName)
			throws IOException {
		boolean flag = false;
		File outfile = new File(localFileName);
		OutputStream oStream = null;
		try {
			oStream = new FileOutputStream(outfile);
			flag = ftpClient.retrieveFile(remoteFileName, oStream);
		} catch (IOException e) {
			flag = false;
			return flag;
		} finally {
			oStream.close();
		}
		return flag;
	}

	public InputStream downFile(String sourceFileName) throws IOException {
		return ftpClient.retrieveFileStream(sourceFileName);
	}
	
	/**
	 * 递归创建远程服务器目录
	 * 
	 * @param remote     远程服务器文件绝对路径
	 * @param ftpClient   FTPClient对象
	 * @throws IOException
	 */
	public void createDirecroty(String remote)
			throws IOException {
		String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
		if (!directory.equalsIgnoreCase("/")
				&& !ftpClient.changeWorkingDirectory(new String(directory
						.getBytes("GBK"), "iso-8859-1"))) {
			// 如果远程目录不存在，则递归创建远程服务器目录
			int start = 0;
			int end = 0;
			if (directory.startsWith("/")) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf("/", start);
			while (true) {
				String subDirectory = new String(remote.substring(start, end)
						.getBytes("GBK"), "iso-8859-1");
				if (!ftpClient.changeWorkingDirectory(subDirectory)) {
					if (ftpClient.makeDirectory(subDirectory)) {
						ftpClient.changeWorkingDirectory(subDirectory);
					} else {
						System.out.println("创建目录失败");
					}
				}

				start = end + 1;
				end = directory.indexOf("/", start);

				// 检查所有目录是否创建完毕
				if (end <= start) {
					break;
				}
			}
		}
	}
	public void uploadFile(String folder,File file) throws IOException{
		ftpClient.enterLocalPassiveMode();
		ftpClient.changeWorkingDirectory("/");
		createDirecroty(folder);
		FileInputStream fis = new FileInputStream(file);
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);  
		ftpClient.storeFile(file.getName(), fis);
		fis.close();
	}
	
	public  void upload(String folder,File file){
		FTPClient client = new FTPClient();
		FileInputStream fis = null;
		try {
			client.connect("192.168.0.202");
			client.login("zhouliyi", "xuefukaoyan");
			client.enterLocalPassiveMode();
			createDirecroty(folder);
			String filename = file.getName();
			fis = new FileInputStream(file);
			client.setFileType(FTP.BINARY_FILE_TYPE);  
			client.storeFile(filename, fis);
			client.logout();
			client.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				client.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public static void main(String[] args) throws SocketException, IOException{
		File f = null;
//		upload("/folder1/folder2/",f);
	}
}