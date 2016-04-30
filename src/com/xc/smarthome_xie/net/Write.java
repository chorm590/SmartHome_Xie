package com.xc.smarthome_xie.net;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import android.util.Log;

/**
 * 发送指令的类。</br>
 * 操作步骤：</br>
 * 1.创建实例；</br>
 * 2.若当前尚未指定socket对象，则应该调用setSocket()；</br>
 * 3.调用setSendData()设定要发送的指令类型；</br>
 * 4.通过Thread对象start这个子线程。</br>
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
