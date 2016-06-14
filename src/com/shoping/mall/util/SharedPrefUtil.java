package com.shoping.mall.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefUtil {
	public static String readStringFromSharedPref(Context context, String name) {
		SharedPreferences MyPreferences = context.getSharedPreferences(
				"icbc", 2);
		String str = MyPreferences.getString(name, "");
		return str;
	}
	
	
	public static int readIntFromSharedPref(Context ctx,String key,int defaultValue){
		SharedPreferences MyPreferences = ctx.getSharedPreferences(
				"icbc", 2);
		
		int value = MyPreferences.getInt(key, defaultValue);
		return value;
	}
	
	public static boolean readBooleanFromSharedPref(Context ctx,String key ,boolean defaultValue){
		SharedPreferences MyPreferences = ctx.getSharedPreferences(
				"icbc", 2);
		
		boolean value = MyPreferences.getBoolean(key, defaultValue);
		return value;
	}
	
	
	public static void writeStringSharedPref(Context ctx, String key, String value) {
		SharedPreferences.Editor editor = getIcbcSharePrefEditor(ctx);
		editor.putString(key, value);
		editor.commit();
	}
	
	public static void writeIntSharedPref(Context ctx,String key,int value){
				SharedPreferences.Editor editor = getIcbcSharePrefEditor(ctx);
				editor.putInt(key, value);
				editor.commit();
	}
	
	public static void writeBooleanSharedPref(Context ctx,String key,boolean value){
				SharedPreferences.Editor editor = getIcbcSharePrefEditor(ctx);
				editor.putBoolean(key, value);
				editor.commit();
	}
	
	public static Editor getIcbcSharePrefEditor(Context ctx){
		return ctx.getSharedPreferences(
				"icbc", 2).edit();
	}
	
	
}
