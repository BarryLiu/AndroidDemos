package com.lx.drivingexam;

import com.ab.activity.AbActivity;
import com.ab.view.titlebar.AbTitleBar;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.widget.ImageView;

public class NodataActivity extends AbActivity {
	MyApplication myApp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_nodata);
		myApp=(MyApplication)getApplication();
		addTitleBar();
		ImageView iv_no_data=(ImageView)this.findViewById(R.id.iv_no_data);
		if(myApp.PRACTISE_TYPE==3)
			iv_no_data.setImageResource(R.drawable.no_data3);
		else
			iv_no_data.setImageResource(R.drawable.no_data4);
	}
	/***
	 * 添加头部性息 TitleBar
	 */
	private void addTitleBar(){
		AbTitleBar mAbTitleBar = this.getTitleBar();
		String titleText1;
		if (myApp.PRACTISE_TYPE==3)
			titleText1="我的收藏";
		else
			titleText1="我的错题";
		mAbTitleBar.setTitleText(titleText1);
		mAbTitleBar.setLogo(R.drawable.button_selector_back);
        mAbTitleBar.setTitleBarBackground(R.drawable.top_bg);
        mAbTitleBar.setTitleTextMargin(10, 0, 0, 0);
        mAbTitleBar.setLogoLine(R.drawable.line);
        mAbTitleBar.setTitleBarGravity(Gravity.CENTER, Gravity.CENTER);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nodata, menu);
		return true;
	}

}
