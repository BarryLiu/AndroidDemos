package io.barryliu.facebud;

import io.barryliu.facebud.itemui.FacePageAdapter;
import io.barryliu.facebud.lisenter.MyItemLisenter;
import io.barryliu.facebud.res.MyRes;
import io.barryliu.facebud.res.MyView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity  implements MyItemLisenter{

	public static boolean isBoy = true;
	private RelativeLayout rl;
	private MyView myView;
	private HorizontalScrollView hsv;
	private RadioButton rb0;
	private ViewPager vp;
	private TextView line;
	private int fromX;
	private RadioGroup rg;
	
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
		vp.setAdapter(new FacePageAdapter(getSupportFragmentManager(), this));
		vp.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				int total = (int)((arg0+arg1)*rb0.getWidth());
				int green = (vp.getWidth()-rb0.getWidth())/2;
				int scoll = total - green;
				hsv.scrollTo(scoll, 0);
				
				tvMoveTo(arg0,arg1);
			}
			
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	private void tvMoveTo(int index, float f) {
		RadioButton button = (RadioButton) rg.getChildAt(index);
		
		int location [] = new int[2];
		button.getLocationInWindow(location);
		
		TranslateAnimation animation  = new TranslateAnimation(fromX, location[0] + f*button.getWidth(),0,0);
		animation.setDuration(50);
		
		animation.setFillAfter(true);
		
		fromX = (int)(location[0]+f*button.getWidth());
		
		line.startAnimation(animation);
		
	}
	private void initRadioButton() {
		rg = (RadioGroup) findViewById(R.id.rg);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb0:
					vp.setCurrentItem(0);
					break;
				case R.id.rb1:
					vp.setCurrentItem(1);
					break;
				case R.id.rb2:
					vp.setCurrentItem(2);
					break;
				case R.id.rb3:
					vp.setCurrentItem(3);
					break;
				case R.id.rb4:
					vp.setCurrentItem(4);
					break;
				case R.id.rb5:
					vp.setCurrentItem(5);
					break;
				case R.id.rb6:
					vp.setCurrentItem(6);
					break;
				case R.id.rb7:
					vp.setCurrentItem(7);
					break;
				case R.id.rb8:
					vp.setCurrentItem(8);
					break;
				case R.id.rb9:
					vp.setCurrentItem(9);
					break;
				case R.id.rb10:
					vp.setCurrentItem(10);
					break;
				case R.id.rb11:
					vp.setCurrentItem(11);
					break;
				default:
					break;
				}
			}
		});
	}
	@Override
	public void myItemClick(int res, int index) {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), res);
		myView.bms[index] = bitmap;
		myView.res[index] = res;
		myView.invalidate();
	}

	
}
