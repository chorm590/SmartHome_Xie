package com.xc.smarthome_xie;

import com.xc.smarthome_xie.tools.CusApplication;
import com.xc.smarthome_xie.tools.InstructionFactory;
import com.xc.smarthome_xie.tools.SetTimeDialog;
import com.xc.smarthome_xie.tools.SetTimeDialog.OnSetTimeResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ControlActivity extends Activity implements OnClickListener, OnSetTimeResult {
	
	private ToggleButton tb;
	private TextView tvStartTime, tvEndTime;
	private CheckBox cbRepeat, cbSetTime, cbDelay;
	private NumberPicker npMin, npDay, npHour;
	
	private int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control);
		init();
		setup();
	}
	
	private void init() {
		Intent intent = getIntent();
		position = intent.getIntExtra("position", 0);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(MainActivity.name[position]);
		getActionBar().setIcon(R.drawable.touming);
		tb = (ToggleButton) findViewById(R.id.tb);
		tvStartTime = (TextView) findViewById(R.id.tvStartTime);
		tvEndTime = (TextView) findViewById(R.id.tvEndTime);
		cbRepeat = (CheckBox) findViewById(R.id.cbRepeat);
		cbSetTime = (CheckBox) findViewById(R.id.cbSetTimeSwitch);
		cbDelay = (CheckBox) findViewById(R.id.cbDelaySwitch);
		npDay = (NumberPicker) findViewById(R.id.npDay);
		npHour = (NumberPicker) findViewById(R.id.npHour);
		npMin = (NumberPicker) findViewById(R.id.npMin);
	}

	private void setup() {
		tb.setOnClickListener(this);
		tvStartTime.setOnClickListener(this);
		tvEndTime.setOnClickListener(this);

		npMin.setMaxValue(59);
		npMin.setMinValue(0);
		npMin.setValue(0);
		
		npHour.setMaxValue(23);
		npHour.setMinValue(0);
		npHour.setValue(0);
		
		npDay.setMaxValue(7);
		npDay.setMinValue(0);
		npDay.setValue(0);
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			finishActivity();
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home){
			finishActivity();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void finishActivity(){
		Intent data = new Intent();
		data.putExtra("test", "yongfa.yang");
		setResult(Activity.RESULT_OK, data);
		
		finish();
	}
	
	public void ok(View view){
		sendData();
	}
	
	private void sendData(){
		InstructionFactory.which = position + 1;
		InstructionFactory.isSwitchOn = tb.isChecked();
		InstructionFactory.isSetTimeOn = cbSetTime.isChecked();
		InstructionFactory.setTimeStartAfterMins = tvStartTime.getText().toString();
		InstructionFactory.setTimeEndAfterMins = tvEndTime.getText().toString();
		InstructionFactory.isSetTimeRepeat = cbRepeat.isChecked();
		InstructionFactory.isDelayOn = cbDelay.isChecked();
		InstructionFactory.delayAfterMins = npMin.getValue();
		
		String ins = InstructionFactory.getSettingInstruction();
		Log.d("mylog", "fucking ins="+ins);
		CusApplication.getInstance().getConnection().write(ins);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.tb:{
				
			}break;
			case R.id.tvStartTime:{
				Log.d("mylog", "Set start time.");
				new SetTimeDialog(this, v.getId(), tvStartTime.getText().toString()).showDialog().setOnSetTimeResult(this);
			}break;
			case R.id.tvEndTime:{
				Log.d("mylog", "Set end time.");
				new SetTimeDialog(this, v.getId(), tvEndTime.getText().toString()).showDialog().setOnSetTimeResult(this);
			}break;
		} // switch  --  end.
	}//  onClick  --  end.

	@Override
	public void onResult(int viewId, String hour, String min) {
		switch(viewId){
			case R.id.tvStartTime:{
				tvStartTime.setText(hour+":"+min);
			}break;
			case R.id.tvEndTime:{
				tvEndTime.setText(hour+":"+min);
			}break;
		}
	}

}
