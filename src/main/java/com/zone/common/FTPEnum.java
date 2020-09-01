package com.zone.common;

public enum FTPEnum {
	FTP(PropertiesHandle.getResource("ftpServer"),
			PropertiesHandle.getResource("ftpUserName"),
			PropertiesHandle.getResource("ftpPassword"),
			PropertiesHandle.getResource("ftpFilePath"),
			Integer.parseInt(PropertiesHandle.getResource("ftpPort")),
			PropertiesHandle.getResourceAndSetDefault("fielEncoding", "utf-8"));

	private final String server;
	private final String ftpUser;
	private final String ftpPass;
	private final String ftpFilePath;
	private final int ftpPort;
	private final String encoding;

	FTPEnum(String server, String ftpUser, String ftpPass, String ftpFilePath, int ftpPort, String encoding) {
		this.server = server;
		this.ftpUser = ftpUser;
		this.ftpPass = ftpPass;
		this.ftpFilePath = ftpFilePath;
		this.ftpPort = ftpPort;
		this.encoding = encoding;
	}

	public int getFtpPort() {
		return ftpPort;
	}

	public String getServer() {
		return server;
	}

	public String getFtpUser() {
		return ftpUser;
	}

	public String getFtpPass() {
		return ftpPass;
	}

	public String getFtpFilePath() {
		return ftpFilePath;
	}
}
