package com.xc.smarthome_xie.net;

import com.xc.smarthome_xie.entity.ServerInfoEntity;

import android.util.Log;

/**
 * 获取服务器的IP地址、端口号等信息。</br>
 * 单例。
 * */
public class ServerInfo {
	
	private String TAG = "ServerInfo";

	private static ServerInfo instance ;
	private ServerInfoEntity info;
	
	private ServerInfo(){
		Log.d(TAG, "Instancing...");
		info = new ServerInfoEntity();
		info.setIp("192.168.11.254");
		info.setPort(8080);
	}
	
	public String getIPAddress(){
		Log.d(TAG, "ip:"+info.getIp());
		return info.getIp();
	}
	
	public int getPort(){
		Log.d(TAG, "port:"+info.getPort());
		return info.getPort();
	}
	
	public void setIPAddress(String ip){
		// 在这里检查传入的IP地址是否合法。
		if(isIpLegal(ip)){
			info.setIp(ip);
			Log.d(TAG, "set ip:"+ip);
		}else {
			
		}
	}
	
	public void setPort(int port){
		// 在这里检查端口号是否合法。
		if(isPortLegal(port)){
			info.setPort(port);
			Log.d(TAG, "set port:"+port);
		}else {
			
		}
		
	}
	
	private boolean isIpLegal(String ip){
		
		return true;
	}
	
	private boolean isPortLegal(int port){
		
		return true;
	}
	
	/**
	 * 单例设计模式。</br>
	 * 懒汉式单例模式。
	 * */
	public static ServerInfo getInstance(){
		if(instance == null){
			synchronized (ServerInfo.class) {
				if(instance == null){
					instance = new ServerInfo();
				}
			}
		}
		
		return instance;
	}
	
}
