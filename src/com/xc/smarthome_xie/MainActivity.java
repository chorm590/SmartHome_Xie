package com.xc.smarthome_xie;

import java.util.ArrayList;

import com.xc.smarthome_xie.adapter.MyAdapter;
import com.xc.smarthome_xie.entity.Entity;
import com.xc.smarthome_xie.entity.WatcherEntity;
import com.xc.smarthome_xie.net.ServerInfo;
import com.xc.smarthome_xie.tools.CusApplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements OnItemClickListener{

	private final String TAG = "MainActivity";
	
	public final byte REFRESH_DATA = 1;
	public static final byte ACTIVITY_REQUEST_CODE = 2;
	public static final String BROADCAST_ACTION = "samsung";
	
	private TextView tvLeftMain, tvLeftUnit, tvLeftSub;
	private TextView tvCenterMain, tvCenterUnit, tvCenterSub;
	private TextView tvRightMain, tvRightUnit, tvRightSub;
	
	private GridView mGridView;
	private MyAdapter adapter;
	
	private ArrayList<Entity> list;
	private static MainActivity instance;
	private DataBroadcastReceiver receiver;
	
	public static final String[] name = {
			"主卧灯",
			"副卧灯",
			"加湿器",
			"空调",
			"空气净化器"
	};
	
	private int[] res = {
			Color.BLACK,
			Color.CYAN,
			Color.BLUE,
			Color.GREEN,
			Color.RED
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		instance = this;
		init();
		Log.d(TAG, "init finished.");
		setup();
		Log.d(TAG, "setup finished.");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
//		refreshData();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
		CusApplication.getInstance().killMePlease();
	}

	private void init() {
		View view = findViewById(R.id.viewLeft);
		tvLeftMain = (TextView) view.findViewById(R.id.tvMainInfo);
		tvLeftUnit = (TextView) view.findViewById(R.id.tvUnit);
		tvLeftSub = (TextView) view.findViewById(R.id.tvSubInfo);
		
		view = findViewById(R.id.viewCenter);
		tvCenterMain = (TextView) view.findViewById(R.id.tvMainInfo);
		tvCenterUnit = (TextView) view.findViewById(R.id.tvUnit);
		tvCenterSub = (TextView) view.findViewById(R.id.tvSubInfo);
		
		view = findViewById(R.id.viewRight);
		tvRightMain = (TextView) view.findViewById(R.id.tvMainInfo);
		tvRightUnit = (TextView) view.findViewById(R.id.tvUnit);
		tvRightSub = (TextView) view.findViewById(R.id.tvSubInfo);
		
		mGridView = (GridView) findViewById(R.id.gvMain);
		list = new ArrayList<Entity>();
		adapter = new MyAdapter(this, R.layout.function_layout, list);
		mGridView.setAdapter(adapter);
		mGridView.setOnItemClickListener(this);
		
		// Register broadcast receiver.
		receiver = new DataBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(BROADCAST_ACTION);
		registerReceiver(receiver, filter);
	}
	
	private void setup(){
		tvLeftSub.setText(getResources().getString(R.string.tempreture));
		tvCenterSub.setText(getResources().getString(R.string.pm2_5));
		tvRightSub.setText(getResources().getString(R.string.nosie));
		tvLeftUnit.setText(getResources().getString(R.string.tempreture_unit));
		tvCenterUnit.setText(getResources().getString(R.string.pm2_5_unit));
		tvCenterUnit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
		tvRightUnit.setText(getResources().getString(R.string.nosie_uint));
		
		// GridView功能列表初始化。
		Entity entity = null;
		for(byte i = 0;i<name.length;i++){
			entity = new Entity();
			entity.setName(name[i]);
			entity.setRes(res[i]);
			list.add(entity);
		}
		adapter.notifyDataSetChanged();
	}
	
	private void refresh(WatcherEntity entity){
		tvLeftMain.setText(entity.getTempretrue());
		tvCenterMain.setText(entity.getPm2_5());
		tvRightMain.setText(entity.getNosie());
		float cen = Float.valueOf(entity.getPm2_5());
		if(cen >=10.0f){
			tvCenterMain.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
		}else if(cen >= 100.0f){
			tvCenterMain.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
		}else{
			tvCenterMain.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
		}
	}
	
	public void setIp(View viw){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View view = LayoutInflater.from(this).inflate(R.layout.set_ip, null);
		final EditText etIp = (EditText) view.findViewById(R.id.etSettingIP);
		final EditText etPort = (EditText) view.findViewById(R.id.etSettingPort);
		builder.setView(view);
		builder.setPositiveButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Log.d(TAG, "ip:"+etIp.getText().toString()+"  port:"+etPort.getText().toString());
				ServerInfo.getInstance().setIPAddress(etIp.getText().toString());
				ServerInfo.getInstance().setPort(Integer.valueOf(etPort.getText().toString()));
				// 初始化连接的方法里包含了关闭连接的方法。
				CusApplication.getInstance().initConnection();
			}
		});
		
		builder.setNegativeButton("Cancel", null);
		builder.create().show();
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent();
		intent.setClassName("com.xc.smarthome_xie", "com.xc.smarthome_xie.ControlActivity");
		intent.putExtra("position", position);
		startActivityForResult(intent, ACTIVITY_REQUEST_CODE);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
			Log.d("mylog", "test="+data.getStringExtra("test")+Activity.RESULT_CANCELED);
			
		} // if  --  end.
	}

	
	public static MainActivity getInstance(){
		return instance;
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch(msg.what){
				case REFRESH_DATA:{
					refresh((WatcherEntity) msg.obj);
				}break;
			}
		};
		
	};
	
	private class DataBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "onReceive:"+intent.getAction());
			WatcherEntity entity = intent.getParcelableExtra("entity");
			Message msg = Message.obtain();
			msg.obj = entity;
			msg.what = REFRESH_DATA;
			handler.sendMessage(msg);
			Log.d(TAG, "yyf:"+entity.getTempretrue()+"   "+entity.getPm2_5()+"   "+entity.getNosie());
		}
		
	} // inner class  --  end.

}
