package com.lx.drivingexam;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.view.slidingmenu.SlidingMenu;
import com.ab.view.slidingmenu.SlidingMenu.CanvasTransformer;
import com.ab.view.titlebar.AbTitleBar;
import com.lx.drivingexam.fragment.MyFragment;
import com.lx.drivingexam.fragment.MyFragment.MenuChangeListener;
import com.lx.drivingexam.fragment.TypeFragment;

public class SlidingMenuZoomActivity extends AbActivity implements MenuChangeListener{
	public SlidingMenu menu;
	private AbTitleBar mAbTitleBar;
	private MyApplication myApp=null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.sliding_menu_content);
		myApp=(MyApplication)getApplication();
		addTitleBar();//添加头部性息
		addMenu();//添加侧滑栏Menu
	}
	/***
	 * 添加头部性息 TitleBar
	 */
	private void addTitleBar(){
		mAbTitleBar = this.getTitleBar();
		
		mAbTitleBar.setLogo(R.drawable.button_selector_menu);
        mAbTitleBar.setTitleBarBackground(R.color.green_dark2);
        mAbTitleBar.setTitleTextMargin(10, 0, 0, 0);
//      mAbTitleBar.setLogoLine(R.drawable.line);
        mAbTitleBar.setTitleBarGravity(Gravity.CENTER, Gravity.CENTER);
        mAbTitleBar.getLogoView().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (menu.isMenuShowing()) {
 					menu.showContent();
 				} else {
 					menu.showMenu();
 				}
			}
		});
	
	}
	@Override
	public void changeTitleText(){
		String kemu="驾考秘典-科目一";
		if(myApp.KEMU==3){
			kemu="驾考秘典-科目四";
		}
		mAbTitleBar.setTitleText(kemu);
	}
	/***添加侧滑栏Menu*/
	private void addMenu(){
		//SlidingMenu的配
		menu = new SlidingMenu(this);
		changeTitleText();
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		//menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		WindowManager wm = this.getWindowManager();
	    int width = wm.getDefaultDisplay().getWidth();
		menu.setFadeDegree(0.35f);
		menu.setBehindWidth(7*width/10);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		//menu视图的Fragment添加
		menu.setMenu(R.layout.sliding_menu_menu);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new MyFragment())
		.commit();
		
		//动画配置
		menu.setBehindCanvasTransformer(new CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				//将画布默认的黑背景替换掉
				canvas.drawColor(SlidingMenuZoomActivity.this.getResources().getColor(R.color.green_dark2));
				float scale = (float) (percentOpen*0.25 + 0.75);
				canvas.scale(scale, scale, canvas.getWidth()/2, canvas.getHeight()/2);
			}
		});
		 //主视图的Fragment添加
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, new TypeFragment())
		.commit();
	}
	@Override
	public void onBackPressed() {
		if (menu.isMenuShowing()) {
			menu.showContent();
		} else {
			super.onBackPressed();
		}
	}
	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	            finish();
	            System.exit(0);
	        }
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
