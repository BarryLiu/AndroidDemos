package com.example.tuling;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity implements HttpGetDateLisenter {
	private HttpData httpData;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		httpData =(HttpData) new HttpData("", this).execute();

	
	}

	@Override
	public void getDataUrl(String data) {
		System.out.println(data);
	}

	 
}
