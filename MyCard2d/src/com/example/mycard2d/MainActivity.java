package com.example.mycard2d;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {

	private ImageView imageA;
	private ImageView imageB;

	private ScaleAnimation sato0 = new ScaleAnimation(1, 0,1,1,
			Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT,
			0.5f );
	
	private ScaleAnimation sato1 = new ScaleAnimation(0, 1,1,1,
			Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT,
			0.5f );
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
		this.findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(imageA.getVisibility()==View.VISIBLE){
					imageA.startAnimation(sato0);
				}else{
					imageB.startAnimation(sato0);
				}
			}
		});
	}
	private void showImageA(){
		imageA.setVisibility(View.VISIBLE);
		imageB.setVisibility(View.INVISIBLE);
	}
	private void showImageB(){
		imageA.setVisibility(View.INVISIBLE);
		imageB.setVisibility(View.VISIBLE);
	}
	private void initView(){
		imageA = (ImageView) this.findViewById(R.id.ivA);
		imageB = (ImageView) this.findViewById(R.id.ivB);
		showImageA();
		sato0.setDuration(500);
		sato1.setDuration(500);
		
		sato0.setAnimationListener(new Animation.AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				if(imageA.getVisibility()==View.VISIBLE){
					imageA.setAnimation(null);
					showImageB();
					imageB.startAnimation(sato1);
				}else{
					imageB.setAnimation(null);
					showImageA();
					imageA.startAnimation(sato1);
				}
			}
		});
	}
	
}
