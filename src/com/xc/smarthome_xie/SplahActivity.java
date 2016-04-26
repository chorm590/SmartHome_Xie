package com.xc.smarthome_xie;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

@SuppressLint("HandlerLeak")
public class SplahActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splah);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		handler.sendEmptyMessageDelayed(0, 1500);
	}
	
	private Handler handler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			Intent intent = new Intent();
			intent.setClassName("com.xc.smarthome_xie", "com.xc.smarthome_xie.MainActivity");
			startActivity(intent);
			finish();
		};
		
	};

}
