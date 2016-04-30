package com.xc.smarthome_xie.entity;

/**
 * 储存包括服务器地址，端口等信息。
 * */
public class ServerInfoEntity {

	private String ip;
	private int port;
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
}
