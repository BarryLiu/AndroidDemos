package com.example.secret;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class Config {

	public static final String KEY_TOKEN = "token";
	public static final String APP_ID = "com.example.secret";
	

	public static String getCachedToken(Context context ) {
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_TOKEN, null);
	}
	public static void cacheToken(Context context , String token){
		Editor edit = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
		edit.putString(KEY_TOKEN, token);
		edit.commit();
	}

}
