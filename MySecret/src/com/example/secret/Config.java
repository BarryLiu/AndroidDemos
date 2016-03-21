package com.example.secret;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class Config {

	//Todo
	public static final String SERVER_URL = "http://demo.eoeschool.com/api/";
	
	public static final String KEY_TOKEN = "token";
	public static final String KEY_ACTION = "action";
	public static final String KEY_PHONE_NUM = "phone";
	public static final String KEY_STATUS = "status";
	
	public static final int RESULT_STATUS_SUCCESS= 1;
	public static final int RESULT_STATUS_FAIL = 0;
	public static final int RESULT_STATUS_INVAID_TOKEN = 2;
	
	public static final String APP_ID = "com.example.secret";
	public static final String CHARSET = "utf-8";
	
	public static final String ACTION_GET_CODE = "send_pass";


	public static String getCachedToken(Context context ) {
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_TOKEN, null);
	}
	public static void cacheToken(Context context , String token){
		Editor edit = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
		edit.putString(KEY_TOKEN, token);
		edit.commit();
	}

}
