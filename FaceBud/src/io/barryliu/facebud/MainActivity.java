package io.barryliu.facebud;

import io.barryliu.facebud.res.MyRes;
import io.barryliu.facebud.res.MyView;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	public static boolean isBoy = true;
	private RelativeLayout rl;
	private MyView myView;
	private HorizontalScrollView hsv;
	private RadioButton rb0;
	private ViewPager vp;
	private TextView line;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Bundle bundle = getIntent().getExtras();
		isBoy = (Boolean) bundle.get(MyRes.TAG_ISBOY);
		
		rl = (RelativeLayout) findViewById(R.id.rl);
		myView = new MyView(MainActivity.this, isBoy);
		myView.setDrawingCacheEnabled(true);
		rl.addView(myView);
		
		initRadioButton();
		hsv = (HorizontalScrollView) findViewById(R.id.hsv);
		rb0 = (RadioButton) findViewById(R.id.rb0);
		vp = (ViewPager) findViewById(R.id.vp);
		line = (TextView) findViewById(R.id.line);
		
		
		
	}
	private void initRadioButton() {
		// TODO Auto-generated method stub
		
	}


}
