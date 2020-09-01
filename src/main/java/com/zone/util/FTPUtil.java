package com.zone.util;

import com.zone.bean.FileProperty;
import com.zone.bean.FtpProperty;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FTPUtil {

	private static final Logger logger = LoggerFactory.getLogger(FTPUtil.class);

	/**
	 * 建立连接
	 */
	public static FTPClient ftpConnect(FtpProperty ftp) throws NullPointerException, IOException {
		int status;
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect(ftp.getServer(), ftp.getPort());
		ftpClient.login(ftp.getUser(), ftp.getPass());
		status = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(status)) {
			throw new NullPointerException("ftp连接失败, status=" + status);
		}
		return ftpClient;
	}

	/**
	 * 断开连接
	 */
	public static void disConnect(FTPClient ftpClient) {
		if (ftpClient == null) {
			logger.error("ftp登出失败，ftp未建立连接");
			return;
		}
		try {
			ftpClient.logout();
		} catch (IOException e) {
			logger.error("ftp登出失败", e);
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					logger.error("ftp登出失败", e);
				}
			}
		}
	}

	/**
	 * 从FTP服务器下载文件
	 */
	public static void downloadFile(FtpProperty ftp, FileProperty fileProper) {
		String filePathAndName = fileProper.getFtpPath() + File.separator + fileProper.getFileName();
		FTPClient ftpClient = null;
		FileOutputStream fos = null;
		try {
			ftpClient = ftpConnect(ftp);
			ftpClient.setControlEncoding(fileProper.getEncoding());
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);//设置为二进制文件
			File file=new File(fileProper.getLocalPathAndName());
			ftpClient.changeWorkingDirectory(fileProper.getFtpPath());
			ftpClient.enterLocalPassiveMode();
			fos=new FileOutputStream(file);
			boolean result = ftpClient.retrieveFile(
					new String(fileProper.getFileName().getBytes(StandardCharsets.UTF_8),
							StandardCharsets.ISO_8859_1),fos);
			if(result) {
				logger.info("文件{}下载成功!", filePathAndName);
			}else {
				logger.error("文件{}下载失败!", filePathAndName);
			}
		}catch (Exception e) {
			logger.error("", e);
		}finally {
			StreamUtil.closeOutPutStream(fos);
			disConnect(ftpClient);
		}
	}


	public static void upload(FtpProperty ftp, FileProperty fileConfig) {
		FTPClient ftpClient = null;
		try {
			ftpClient = ftpConnect(ftp);
			logger.info("上传路径下文件:{}至{}目录{}", fileConfig.getLocalPathAndName(), ftp.getServer(), fileConfig.getFtpPath());
			upload(ftpClient, fileConfig);
		}catch (Exception e) {
			logger.error("上传文件:{}至:{}失败",  fileConfig.getLocalPathAndName(), e);
		}finally {
			disConnect(ftpClient);
		}
	}

	// 如果上传失败，确认文件夹是否具有读写权限
	public static void upload(FTPClient ftpClient, FileProperty fileConfig) {
		InputStream in = null;
		try {
			if(fileConfig.getFtpPath() !=null && !fileConfig.getFtpPath().equals("")) {
				if (!ftpClient.changeWorkingDirectory(fileConfig.getFtpPath())) {
					logger.error("切换目录到{}失败", fileConfig.getFtpPath());
					return;
				}
			}
			ftpClient.setControlEncoding(fileConfig.getEncoding());
			//ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			in = new FileInputStream(fileConfig.getLocalPathAndName());
			if (!ftpClient.storeFile(
					new String(fileConfig.getFileName().getBytes(StandardCharsets.UTF_8),
					StandardCharsets.ISO_8859_1), in)) {
				logger.error("上传文件{}失败", fileConfig.getFileName());
			}
		} catch (Exception e) {
			logger.error("上传文件{}失败", fileConfig.getFileName(), e);
		} finally {
			StreamUtil.closeInPutStream(in);
		}
	}


}
