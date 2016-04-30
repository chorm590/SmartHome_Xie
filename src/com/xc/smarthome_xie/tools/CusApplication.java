package com.xc.smarthome_xie.tools;

import com.xc.smarthome_xie.net.ConnectToServer;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class CusApplication extends Application {
	
	private final String TAG = "CusApplication";

	private static CusApplication instance ;
	
	private ConnectToServer connection;
	
	public boolean isNetAvailable;
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		initConnection();
	}
	
	public void initConnection(){
		Log.d(TAG, "init ConnectionToServer");
		if(connection != null)
			connection.close();
		connection = new ConnectToServer();
		connection.connect();
	}
	
	public ConnectToServer getConnection(){
		return connection;
	}
	
	public void killMePlease(){
		ActivityManager acm = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		String pkgname = getPackageName();
		Log.d(TAG, "package name:" + pkgname);
		acm.killBackgroundProcesses(pkgname);
		
	}
	
	public static CusApplication getInstance(){
		return instance;
	}
	
	public void showToast(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	public Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			showToast((String) msg.obj);
		};
	};
	
}
