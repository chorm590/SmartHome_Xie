package com.xc.smarthome_xie.tools;

import java.util.Calendar;

import android.util.Log;

public class InstructionFactory {
	
	public static boolean isSwitchOn ;
	public static boolean isSetTimeOn ;
	public static String setTimeStartAfterMins ;
	public static String setTimeEndAfterMins ;
	public static boolean isSetTimeRepeat ;
	public static boolean isDelayOn ;
	public static int delayAfterMins ;
	
	public static String getSettingInstruction(){
		String vitamin = "";
		vitamin += (isSwitchOn?1:0); // The switch.
		vitamin += (isSetTimeOn?"1"+getTime():"0"+"00000000"); // set time mission.
		vitamin += (isSetTimeRepeat?"1":"0"); // is repeat.
		String dt = "";
		if(delayAfterMins<10)
			dt = "0"+delayAfterMins;
		else
			dt = String.valueOf(delayAfterMins);
		
		vitamin += (isDelayOn?1+dt:0+"00"); // delay mission.
		
		return vitamin;
	}

	private static int getSetStart(String tt) {
		String[] ss = tt.split(":");
		int hour = Integer.valueOf(ss[0]);
		int min = Integer.valueOf(ss[1]);
		Calendar c = Calendar.getInstance();
		int ch = c.get(Calendar.HOUR_OF_DAY);
		int cm = c.get(Calendar.MINUTE);
		
		
		if(hour>=ch){
			hour = (hour - ch);
		}else {
			hour = 24 - ch + hour;
		}
		
		if(min>=cm){
			min = min - cm;
		}else {
			min = 60 - cm + min;
		}
		
		return hour*60 + min;
	}

	private static String getTime() {
		int s = getSetStart(setTimeStartAfterMins);
		int e = getSetStart(setTimeEndAfterMins);
		Log.d("mylog", "getTime,s="+s+" e="+e);
		String s1 = "";
		String s2 = "";
		
		if(e<s){
			e += 24*60;
		}
		
		if(s<10){
			s1 = "000"+s;
		}else if(s<100){
			s1 = "00"+s;
		}else if(s<1000){
			s1 = "0"+s;
		}else{
			s1 = String.valueOf(s);
		}
		
		if(e<10){
			s2 = "000"+e;
		}else if(e<100){
			s2 = "00"+e;
		}else if(e<1000){
			s2 = "0"+e;
		}else{
			s2 = String.valueOf(e);
		}
		Log.d("mylog", "getTime  s1+s2"+(s1+s2));
		return s1+s2;
	}
}
