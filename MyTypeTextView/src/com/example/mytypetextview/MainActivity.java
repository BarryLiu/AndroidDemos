package com.example.mytypetextview;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * 
 * @author Barry
 *
 */
public class MainActivity extends BaseActivity {
	
	public static final String TEST_STRING="这个是我模仿网上别人的代码写的,主要是自己写的一个TextView子类,效果是 界面打出声音的时候有打字机打印数据的声音。 ";
	private TypeTextView mTypeTextView;
	 
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_main);
	}

	@Override
	public void findViews() {
		mTypeTextView = (TypeTextView) findViewById(R.id.ttvId);
 		mTypeTextView.setOnTypeViewLisenter(new TypeTextView.OnTypeTextViewLisenter() {
			@Override
			public void onTypeViewStart() {
				print(" onTypeViewStart");
			}
			@Override
			public void onTypeViewOver() {
				print(" onTypeViewOver");
			}
		});
		
		//点击按钮
		Button btnShow= (Button) findViewById(R.id.btnShow);
		 btnShow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showContent( );
			}
		}); 
	}

	@Override
	public void getData() {
		
	}

	@Override
	public void showContent() {
		mTypeTextView.start(TEST_STRING);
	}

	private void print( String printStr ){
		System.out.println( "TAG == TypeTextViewActivity, info == " + printStr );
	}
	public void btnShowClick(){
		showContent( );
	}
}
