package com.example.secret;

import com.example.secret.activity.LoginActivity;
import com.example.secret.activity.TimeLineActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String token = Config.getCachedToken(this);
		if(token != null){
			Intent intent =new Intent(this,TimeLineActivity.class);
			intent.putExtra(Config.KEY_TOKEN, token);
			 
			startActivity(intent);
			
		}else{
			Intent intent = new Intent(this,LoginActivity.class);
			startActivity(intent);
		}
		
		finish();
	
	}

}
