package com.zone.bean;

public class FtpProperty {

	private final String server;
	private final String user;
	private final String pass;
	private final int port;

	public FtpProperty(String server, String user, String pass, String port) {
		this.server = server;
		this.user = user;
		this.pass = pass;
		if (port == null || port.equals("")) {
			this.port = 21;
		}else {
			this.port = Integer.parseInt(port);
		}
	}

	public String getServer() {
		return server;
	}

	public String getUser() {
		return user;
	}

	public String getPass() {
		return pass;
	}

	public int getPort() {
		return port;
	}
}
