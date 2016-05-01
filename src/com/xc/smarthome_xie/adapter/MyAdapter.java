package com.xc.smarthome_xie.adapter;

import java.util.List;

import com.xc.smarthome_xie.R;
import com.xc.smarthome_xie.entity.Entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<Entity> {

	private int resource;
	
	public MyAdapter(Context context, int resource, List<Entity> objects) {
		super(context, resource, objects);
		this.resource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(resource, null);
			holder.iv = (ImageView) convertView.findViewById(R.id.iv);
			holder.tv = (TextView) convertView.findViewById(R.id.tv);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tv.setText(getItem(position).getName());
		holder.iv.setImageResource(getItem(position).getRes());
		
		return convertView;
	}
	
	class ViewHolder {
		ImageView iv;
		TextView tv;
	}
}
