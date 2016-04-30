package com.xc.smarthome_xie.net;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import android.util.Log;

/**
 * ����ָ����ࡣ</br>
 * �������裺</br>
 * 1.����ʵ����</br>
 * 2.����ǰ��δָ��socket������Ӧ�õ���setSocket()��</br>
 * 3.����setSendData()�趨Ҫ���͵�ָ�����ͣ�</br>
 * 4.ͨ��Thread����start������̡߳�</br>
 * */
public class Write implements Runnable {
	
	private final String TAG = "Write";
	
	private Socket socket;
	
	private BufferedWriter bw;
	private OutputStreamWriter osw;
	
	private String str;
	
	public void setSocket(Socket s){
		socket = s;
	}
	
	public void setInstruction(String instruction){
		str = instruction;
	}
	
	@Override
	public void run() {
		Log.d(TAG, "run");
		try {
			osw = new OutputStreamWriter(socket.getOutputStream(), "gbk");
			bw = new BufferedWriter(osw);
			bw.write(str);
			bw.flush();
			Log.d(TAG, "writed. data="+str);
		} catch (IOException e) {
			Log.e(TAG, "connection error!!! "+e.getMessage());
		}
	}
	
}
