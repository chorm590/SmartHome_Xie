package com.xc.smarthome_xie.net;

import java.io.IOException;
import java.net.Socket;

import com.xc.smarthome_xie.tools.CusApplication;

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
					CusApplication.getInstance().handler.sendEmptyMessage(CusApplication.CONNECT_SUCCESS);
				} catch (IOException e) {
					Log.d(TAG, "connect failed."+e.getMessage());
					CusApplication.getInstance().handler.sendEmptyMessage(CusApplication.CONNECT_FAILED);
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
			CusApplication.getInstance().handler.sendEmptyMessage(CusApplication.SINGLE_TOAST);
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
