package io.barryliu.facebud.res;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MyView extends View {

	boolean gender;
	public int[] res;
	public Bitmap[] bms;
	private MyRes myRes;

	public MyView(Context context, boolean gender) {
		super(context);
		this.gender = gender;

		myRes = new MyRes();
		if (gender) {
			res = myRes.getBoyDefault();
		} else {
			res = myRes.getGirlDefault();
		}

		getDate(context);

		bms = new Bitmap[res.length];
		for (int i = 0; i < res.length; i++) {
			Bitmap bitmap = BitmapFactory
					.decodeResource(getResources(), res[i]);
			bms[i] = bitmap;
		}
	}

	private void getDate(Context context) {
		SharedPreferences preferences;
		if (gender) {
			preferences = context.getSharedPreferences("boy",
					Context.MODE_PRIVATE);
		} else {
			preferences = context.getSharedPreferences("girl",
					Context.MODE_PRIVATE);
		}

		res[0] = preferences.getInt("发型", res[0]);
		res[1] = preferences.getInt("脸型", res[1]);
		res[2] = preferences.getInt("眉毛", res[2]);
		res[3] = preferences.getInt("眼睛", res[3]);
		res[4] = preferences.getInt("嘴巴", res[4]);
		res[5] = preferences.getInt("特征", res[5]);
		res[6] = preferences.getInt("眼镜", res[6]);
		res[7] = preferences.getInt("衣服", res[7]);
		res[8] = preferences.getInt("帽子", res[8]);
		res[9] = preferences.getInt("爱好", res[9]);
		res[10] = preferences.getInt("背景", res[10]);
		res[11] = preferences.getInt("气泡", res[11]);

	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		int width = this.getWidth();
		int height = this.getHeight();
		
		bms[0] = Bitmap.createScaledBitmap(bms[0], 2*width/3, 2*width/3, false);
		bms[1] = Bitmap.createScaledBitmap(bms[1], 2*width/3, 2*width/3, false);
		bms[2] = Bitmap.createScaledBitmap(bms[2], width/3, width/3, false);
		bms[3] = Bitmap.createScaledBitmap(bms[3], 3*width/10, 3*width/10, false);
		bms[4] = Bitmap.createScaledBitmap(bms[4], width/5, width/5, false);
		bms[5] = Bitmap.createScaledBitmap(bms[5], width/2,width/2, false);
		bms[6] = Bitmap.createScaledBitmap(bms[6], width/3, width/3, false);
		bms[7] = Bitmap.createScaledBitmap(bms[7], 5*width/11, 5*width/11, false);
		bms[10] = Bitmap.createScaledBitmap(bms[10], width, height, false);
		
		canvas.drawBitmap(bms[10], 0,0, null);
		canvas.drawBitmap(bms[1],(width-bms[1].getWidth())/2, (height-bms[1].getHeight())/2, null);
		canvas.drawBitmap(bms[2],(width-bms[2].getWidth())/2, (height-bms[2].getHeight())/2, null);
		canvas.drawBitmap(bms[3],(width-bms[3].getWidth())/2, (height-bms[2].getHeight())/2+height/15, null);
		canvas.drawBitmap(bms[4],(width-bms[4].getWidth())/2, 11*height/20, null);
		canvas.drawBitmap(bms[5],(width-bms[5].getWidth())/2, (height-bms[5].getHeight())/2, null);
		canvas.drawBitmap(bms[6],(width-bms[6].getWidth())/2, (height-bms[6].getHeight())/2+height/20, null);
		canvas.drawBitmap(bms[7],(width-bms[7].getWidth())/2, height-bms[7].getHeight(),null);
		
		canvas.drawBitmap(bms[0],(width-bms[0].getWidth())/2, (height-bms[0].getHeight())/2, null);
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTextSize(22);
		
		super.onDraw(canvas);
	}
}
