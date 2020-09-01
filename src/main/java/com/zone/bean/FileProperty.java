package com.zone.bean;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FileProperty {

	private String ftpPath;
	private String localPath;
	private String encoding;
	private String fileName;
	private String localPathAndName;
	private SimpleDateFormat format;
	private String type;

	public void setLocalPathAndName(Calendar calendar) {
		this.fileName = fileName + format.format(calendar.getTime()) +"."+ type;
		this.localPathAndName = localPath + fileName;
	}

	public void setFtpPath(String ftpPath) {
		this.ftpPath = ftpPath;
	}

	public void setLocalPath(String localPath) {
		if (!localPath.endsWith("/") || !localPath.endsWith("\\")) {
			localPath += File.separator;
		}
		this.localPath = localPath;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFormat(String format) {
		this.format = new SimpleDateFormat(format);
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFileName() {
		return fileName;
	}

	public String getLocalPathAndName() {
		return localPathAndName;
	}

	public String getEncoding() {
		return encoding;
	}

	public String getFtpPath() {
		return ftpPath;
	}

	@Override
	public String toString() {
		return "FileProperty{" +
				"ftpPath='" + ftpPath + '\'' +
				", localPath='" + localPath + '\'' +
				", encoding='" + encoding + '\'' +
				", fileName='" + fileName + '\'' +
				", localPathAndName='" + localPathAndName + '\'' +
				", format=" + format +
				", type='" + type + '\'' +
				'}';
	}
}
