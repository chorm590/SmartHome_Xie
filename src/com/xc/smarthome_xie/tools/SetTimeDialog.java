package com.xc.smarthome_xie.tools;

import com.xc.smarthome_xie.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

public class SetTimeDialog {
	
	private Context context;
	private OnSetTimeResult onResult;
	private int id;
	private int hour;
	private int min;
	
	public SetTimeDialog(Context context, int viewId, String currentTime){
		this.context = context;
		id = viewId;
		Parse.parseCurrentTime(currentTime);
	}
	
	public void setOnSetTimeResult(OnSetTimeResult o){
		onResult = o;
	}
	
	public SetTimeDialog showDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("«Î…Ë÷√ ±º‰£∫");
		View view = LayoutInflater.from(context).inflate(R.layout.set_time_layout, null);
		final NumberPicker npHour = (NumberPicker) view.findViewById(R.id.npHour);
		final NumberPicker npMin = (NumberPicker) view.findViewById(R.id.npMin);
		
		npHour.setMaxValue(23);
		npHour.setMinValue(0);
		npHour.setValue(Parse.hour);
		
		npMin.setMaxValue(59);
		npMin.setMinValue(0);
		npMin.setValue(Parse.min);
		
		builder.setView(view);
		builder.setNegativeButton("Cancel", null);
		builder.setPositiveButton("OK", new OnClickListener() {
			
			String h, m;
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				hour = npHour.getValue();
				min = npMin.getValue();
				
				if(onResult != null){
					if(hour<10)
						h = "0"+hour;
					else
						h = String.valueOf(hour);
					
					if(min < 10)
						m = "0"+min;
					else
						m = String.valueOf(min);
					
					onResult.onResult(id, h, m);
				}
			}
		});
		builder.create().show();
		return this;
	}
	
	public interface OnSetTimeResult {
		void onResult(int viewId, String hour, String min);
	}
	
}
