package com.xc.smarthome_xie.net;

import com.xc.smarthome_xie.entity.ServerInfoEntity;

import android.util.Log;

/**
 * ��ȡ��������IP��ַ���˿ںŵ���Ϣ��</br>
 * ������
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
		// �������鴫���IP��ַ�Ƿ�Ϸ���
		if(isIpLegal(ip)){
			info.setIp(ip);
			Log.d(TAG, "set ip:"+ip);
		}else {
			
		}
	}
	
	public void setPort(int port){
		// ��������˿ں��Ƿ�Ϸ���
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
	 * �������ģʽ��</br>
	 * ����ʽ����ģʽ��
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
