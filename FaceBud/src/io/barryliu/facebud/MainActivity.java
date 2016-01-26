package io.barryliu.facebud;

import io.barryliu.facebud.res.MyRes;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Bundle bundle = getIntent().getExtras();
		Boolean isBoy = (Boolean) bundle.get(MyRes.TAG_ISBOY);
		
		
		
	}


}
