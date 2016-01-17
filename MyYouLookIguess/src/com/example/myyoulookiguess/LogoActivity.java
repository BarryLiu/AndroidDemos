package com.example.myyoulookiguess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LogoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
	}
	
	public void toMain(View v){
		
		Intent intent =new Intent(LogoActivity.this,MainActivity.class);
		
		startActivity(intent);
		
		finish();
	}
}
