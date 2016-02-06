package io.barryliu.driverexam;

import android.app.Application;
import android.os.Bundle;

import com.ab.activity.AbActivity;

public class SlidingMenuZoomActivity extends AbActivity {

	private MyApplication myApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setAbContentView(R.layout.sliding_menu_content);
		myApp = (MyApplication)getApplication();
	}
}
