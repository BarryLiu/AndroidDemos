package com.example.mytypetextview;

import android.app.Activity;
import android.os.Bundle;
/**
 * 抽象父类    用途：规范一下代码
 * @author Barry
 *
 */
public abstract class BaseActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		init();
	}

	private void init() {
		setContentView();
		findViews();
		getData();
		showContent();
	}
	
	public abstract void setContentView();
	public abstract void findViews();
	public abstract void getData();
	public abstract void showContent();
	
}
