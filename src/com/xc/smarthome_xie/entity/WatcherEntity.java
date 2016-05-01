package com.xc.smarthome_xie.entity;

import java.text.DecimalFormat;

import android.os.Parcel;
import android.os.Parcelable;

public class WatcherEntity implements Parcelable {

	private String tempretrue;
	private String pm2_5;
	private String humidy;
	
	private String[] list = new String[3];
	
	public WatcherEntity(String tempretrue, String humidy, String pm2_5) {
		setTempretrue(tempretrue);
		setNosie(humidy);
		setPm2_5(pm2_5);
	}
	
	public WatcherEntity(){}
	
	public WatcherEntity(Parcel in){
		in.readStringArray(list);
		setTempretrue(list[0]);
		setNosie(list[1]);
		setPm2_5(list[2]);
	}
	
	public String getTempretrue() {
		return tempretrue;
	}
	public void setTempretrue(String tempretrue) {
		this.tempretrue = tempretrue;
	}
	public String getPm2_5() {
		return pm2_5;
	}
	public void setPm2_5(String pm2_5) {
		DecimalFormat df = new DecimalFormat("0.0");
		this.pm2_5 = String.valueOf(df.format(Double.valueOf(pm2_5)));
	}
	public String getNosie() {
		return humidy;
	}
	public void setNosie(String nosie) {
		this.humidy = nosie;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		list[0] = tempretrue;
		list[1] = pm2_5;
		list[2] = humidy;
		dest.writeStringArray(list);
	}
	
	public static final Parcelable.Creator<WatcherEntity> CREATOR = new Creator<WatcherEntity>() {

		@Override
		public WatcherEntity createFromParcel(Parcel source) {
			return new WatcherEntity(source);
		}

		@Override
		public WatcherEntity[] newArray(int size) {
			return new WatcherEntity[size];
		}
	};

}
