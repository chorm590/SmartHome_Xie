package com.xc.smarthome_xie.net;

import java.io.IOException;
import java.net.Socket;

import com.xc.smarthome_xie.tools.CusApplication;

import android.os.Message;
import android.util.Log;

public class ConnectToServer {
	
	private String TAG = "ConnectToServer";
	
	private Socket socket;
	private Write writer;
	
	public void connect() {

		new Thread() {
			
			public void run() {
				
				try {
					Log.d(TAG, "Begin to connect.");
					socket = new Socket(ServerInfo.getInstance().getIPAddress(), ServerInfo.getInstance().getPort());
					Log.d(TAG, "Connect success!");
					// 设置当前网络状态为可用状态。
					CusApplication.getInstance().isNetAvailable = true;
					initReader();
					initWriter();
					Message msg = Message.obtain();
					msg.obj = "成功连接家庭设备";
					CusApplication.getInstance().handler.sendMessage(msg);
				} catch (IOException e) {
					Log.d(TAG, "connect failed."+e.getMessage());
					Message msg = Message.obtain();
					msg.obj = "无法连接家庭设备，请检查您的网络状况.";
					CusApplication.getInstance().handler.sendMessage(msg);
				}
			};
		}.start();
	}
	
	private void initWriter(){
		writer = new Write();
		writer.setSocket(socket);
	}
	
	public boolean isConnectionReady(){
		return writer != null || socket != null;
	}
	
	public void write(String instruction){
		if(!isConnectionReady()){
			Message msg = Message.obtain();
			msg.obj = "当前网络不可用";
			CusApplication.getInstance().handler.sendMessage(msg);
			return;
		}
		writer.setInstruction(instruction);
		new Thread(writer).start();
	}
	
	private void initReader(){
		new Read(socket).start();
	}
	
	public void close(){
		try {
			if(socket != null)
				socket.close();
			Log.d(TAG, "client socket close happlily");
		} catch (IOException e) {
			Log.e(TAG, e.toString());
		}
	}
}
