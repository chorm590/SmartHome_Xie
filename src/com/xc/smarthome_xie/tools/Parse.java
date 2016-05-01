package com.xc.smarthome_xie.tools;

import com.xc.smarthome_xie.MainActivity;
import com.xc.smarthome_xie.entity.WatcherEntity;

import android.content.Intent;
import android.util.Log;

public class Parse {
	
	private static final String TAG = "Parse";
	public static int hour;
	public static int min;

	public static void parse(String string) {
		Log.d(TAG, "string="+string);
		String[] split = string.split(";");
		/*
		 
		  数据示例：
		  					23;0.79;68;
		  					温度；PM2.5；湿度；
		  
		 * */
		WatcherEntity entity = new WatcherEntity(split[0],split[1],split[2]);
		Intent intent = new Intent();
		intent.putExtra("entity", entity);
		intent.setAction(MainActivity.BROADCAST_ACTION);
		MainActivity.getInstance().sendBroadcast(intent);
	}

	public static void parseCurrentTime(String currentTime) {
		String[] sp = currentTime.split(":");
		hour = Integer.valueOf(sp[0]);
		min = Integer.valueOf(sp[1]);
	}

}
