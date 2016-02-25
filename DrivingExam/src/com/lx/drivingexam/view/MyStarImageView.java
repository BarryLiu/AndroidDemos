package com.lx.drivingexam.view;


import com.lx.drivingexam.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MyStarImageView extends View {
	Paint paint=new Paint();
	float left;
	int sum=0;
	Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.star_on);
	Bitmap bitmap1=BitmapFactory.decodeResource(getResources(), R.drawable.star);
	public MyStarImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public MyStarImageView(Context context,AttributeSet attrs) {
		super(context,attrs);
		// TODO Auto-generated constructor stub
		TypedArray typeArray = context.obtainStyledAttributes(attrs,R.styleable.MyStarImageView);
		//获取属性对应的属性值
		sum = typeArray.getInt(R.styleable.MyStarImageView_difficulty, 0);
		typeArray.recycle();
	}
	public void changeData(int sum){
		this.sum=sum;
		this.invalidate();
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		left=bitmap.getWidth()+2;
		for (int i = 0; i < sum; i++) {
			canvas.drawBitmap(bitmap, left*i,0, paint);
		}
		for (int i = 0; i < 5-sum; i++) {
			canvas.drawBitmap(bitmap1, left*(sum+i),0, paint);
		}
	}
}
