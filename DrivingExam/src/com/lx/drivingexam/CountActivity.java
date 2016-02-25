package com.lx.drivingexam;

import com.ab.activity.AbActivity;
import com.ab.view.titlebar.AbTitleBar;

import android.os.Bundle;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class CountActivity extends AbActivity {
	MyApplication myApp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_count);
		myApp=(MyApplication)getApplication();
		ImageView iv_count=(ImageView)this.findViewById(R.id.iv_count);
		Intent intent=getIntent();
		int mock0=intent.getIntExtra("mock0", 0);
		int mock1=intent.getIntExtra("mock1", 0);
		int mock2=intent.getIntExtra("mock2", 0);
		addTitleBar();
		int count=mock1*(100/myApp.listSize);
		if(count>90){
			iv_count.setImageResource(R.drawable.result_good1);
		}else{
			iv_count.setImageResource(R.drawable.result_bad1);
		}
		TextView tv_count=(TextView)findViewById(R.id.tv_count);
		tv_count.setText("得分 : "+count+"分\n正确 : "+mock1+"题\n错误 : "+mock2+"题\n未做 : "+mock0+"题");
		
	}
	/***
	 * 添加头部性息 TitleBar
	 */
	private void addTitleBar(){
		AbTitleBar mAbTitleBar = this.getTitleBar();
		mAbTitleBar.setTitleText("考试统计");
		mAbTitleBar.setLogo(R.drawable.button_selector_back);
        mAbTitleBar.setTitleBarBackground(R.drawable.top_bg);
        mAbTitleBar.setTitleTextMargin(10, 0, 0, 0);
        mAbTitleBar.setLogoLine(R.drawable.line);
        mAbTitleBar.setTitleBarGravity(Gravity.CENTER, Gravity.CENTER);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.count, menu);
		return true;
	}

}
