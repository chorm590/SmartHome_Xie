package com.xc.smarthome_xie.net;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.xc.smarthome_xie.tools.Parse;

import android.util.Log;

public class Read extends Thread {
	
	private final String TAG = "Read";

	private Socket socket;
	
	private InputStreamReader isr;
	
	public Read(Socket s){
		socket = s;
	}
	
	@Override
	public void run() {
		try {
			isr = new InputStreamReader(socket.getInputStream(), "gbk");
			int readLen = 0;
			int count = 0;
			int huangmenji = 0;
			StringBuilder sb = new StringBuilder();
			
			char[] ch = new char[256];
			while(huangmenji < 256){
				huangmenji++;
				count = 0;
				while(true){
					count++;
					Log.d(TAG, "watting for read");
					readLen = isr.read(ch);
					Log.d(TAG, "readded. readLen="+readLen);
					if(readLen == -1){
						break;
					}
					count++;
					sb.append(ch, 0, readLen);
					if(readLen != 256){
						break;
					}
				}
				
				if(count == 1){
					Log.i(TAG, "Socket maybe closed.");
					break;
				}
				
				//read finished.
				Log.d(TAG, "received data:"+sb.toString());
				Parse.parse(sb.toString());
				// clear stringbuilder.
				sb.delete(0, sb.length());
			}
		} catch (IOException e) {
			Log.e(TAG, "Catch an error,"+e.getMessage()+".  read thread ending.");
			return;
		}
	}
}
