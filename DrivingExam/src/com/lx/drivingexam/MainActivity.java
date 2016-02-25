package com.lx.drivingexam;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new Handler().postDelayed(new Runnable() {
            
            @Override
            public void run() {
                    // TODO Auto-generated method stub
                Intent intent=new Intent(MainActivity.this,SlidingMenuZoomActivity.class);
                startActivityForResult(intent,300);
            }
		}, 2000);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==300){
			this.finish();
		}
	}

}
