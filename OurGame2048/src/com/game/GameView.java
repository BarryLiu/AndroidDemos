package com.game;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;

public class GameView extends LinearLayout{
	
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGameView();
	}
	public GameView(Context context) {
		super(context);
		initGameView();
	}

	private void initGameView (){
		this.setOrientation(LinearLayout.VERTICAL);
		this.setBackgroundColor(0xffbbada0);
		
		setOnTouchListener(new View.OnTouchListener() {
			private float startX,startY,offsetX,offsetY;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					offsetX=event.getX()-startX;
					offsetY=event.getY()-startY;
					
					if(Math.abs(offsetX)>Math.abs(offsetY)){ //左右偏移的长度比上下偏移的长度大 用户是左右滑动的 
						if(offsetX<-5){ // 向左
							System.out.println("left");
							swipeLeft();
						}else if(offsetX>5){
							System.out.println("right");
							swipeRight();
						}	
					}else{
						if(offsetY<-5){ // 向左
							System.out.println("up");
							swipeUp();
							
						}else if(offsetY>5){
							System.out.println("down");
							swipeDown();
						}
					}
					 
					break;
				}
				return true;
			}
		});
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		int cardWidth=(Math.min(w, h)-10)/4; //得到每张卡片的宽度
		
		
		addCards(cardWidth,cardWidth);
		
		startGame();
	}
	private void addCards(int cardWidth,int cardHeight) {
		Card c;
		LinearLayout line;
		LinearLayout.LayoutParams lineLp;
		for (int y = 0; y < 4; y++) {
			line = new LinearLayout(getContext());
			lineLp = new LinearLayout.LayoutParams(-1, cardHeight);
			this.addView(line,lineLp);

			for (int x = 0; x < 4; x++) {
				c=new Card(getContext());
				line.addView(c,cardWidth,cardHeight);
				
				cardMap[x][y]=c;
			}
		}
	}
	private MainActivity aty = null;
	public void startGame(){
		 aty = MainActivity.getMainActivity();
		aty.clearScore();
		aty.showBestScore(aty.getBestScore());
	
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				cardMap[x][y].setNum(0);
			}
		}
		addRandomNum();
		addRandomNum();
 	}
	private void addRandomNum(){
		emptyPoints.clear();
		
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if(cardMap[x][y].getNum()<=0){
					emptyPoints.add(new Point(x,y));
				}
			}
		}
		if(emptyPoints.size()>0){
			Point p =emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
			cardMap[p.x][p.y].setNum(Math.random()>0.1?2:4);
			
			MainActivity.getMainActivity().getAnimLayer().createScaleTo1(cardMap[p.x][p.y]);
		}
	}
	private void swipeLeft(){
		 boolean merge=false;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
			
				for (int x1 = x+1; x1 < 4; x1++) {
					if(cardMap[x1][y].getNum()>0){
						if(cardMap[x][y].getNum()<=0){
							cardMap[x][y].setNum(cardMap[x1][y].getNum());
							cardMap[x1][y].setNum(0);
							x--;
							merge=true;
						}else if(cardMap[x][y].equals(cardMap[x1][y])){
							cardMap[x][y].setNum(cardMap[x][y].getNum()*2);
							cardMap[x1][y].setNum(0);
							
							MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
							merge=true;
						}
						break;
					}
				}
			}
		}
		if(merge){
			addRandomNum();
			checkComplete();
			checkPass();
		}
	}
	private void swipeRight(){
		 boolean merge=false;
		 
		for (int y = 0; y < 4; y++) {
			for (int x =3 ; x >=0 ; x--) {
				for (int x1 = x-1; x1 >= 0; x1--) {
					if(cardMap[x1][y].getNum()>0){
						if(cardMap[x][y].getNum()<=0){
							cardMap[x][y].setNum(cardMap[x1][y].getNum());
							cardMap[x1][y].setNum(0);
							x++;
							merge=true;
						}else if(cardMap[x][y].equals(cardMap[x1][y])){
							cardMap[x][y].setNum(cardMap[x][y].getNum()*2);
							cardMap[x1][y].setNum(0);
							MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
							merge=true;
						}
						break;
					}
				}
			}
		}
		if(merge){
			addRandomNum();
			checkComplete();
			checkPass();
		}
	}
	private void swipeUp(){
		 boolean merge=false;
		
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				for (int y1 = y+1; y1 < 4; y1++) {
					if(cardMap[x][y1].getNum()>0){
						if(cardMap[x][y].getNum()<=0){
							cardMap[x][y].setNum(cardMap[x][y1].getNum());
							cardMap[x][y1].setNum(0);
							y--;
							merge=true;
						}else if(cardMap[x][y].equals(cardMap[x][y1])){
							cardMap[x][y].setNum(cardMap[x][y].getNum()*2);
							cardMap[x][y1].setNum(0);
							MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
							merge=true;
						}
						break;
					}
				}
			}
		}
		if(merge){
			addRandomNum();
			checkComplete();
			checkPass();
		}
	}
	private void swipeDown(){
		 boolean merge=false;
		
		for (int x = 0; x < 4; x++) {
			for (int y = 3; y >= 0; y--) {
				for (int y1 = y-1; y1 >= 0; y1--) {
					if(cardMap[x][y1].getNum()>0){
						if(cardMap[x][y].getNum()<=0){
							cardMap[x][y].setNum(cardMap[x][y1].getNum());
							cardMap[x][y1].setNum(0);
							y++;
							merge=true;
						}else if(cardMap[x][y].equals(cardMap[x][y1])){
							cardMap[x][y].setNum(cardMap[x][y].getNum()*2);
							cardMap[x][y1].setNum(0);
							MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
							merge=true;
						}
						break;
					}
				}
			}
		}
		if(merge){
			addRandomNum();
			checkComplete();
			checkPass();
		}
	}
	
	private void checkComplete(){
		boolean complete = true;
		ALL:
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if(cardMap[x][y].getNum()==0 || 
						(x>0&& cardMap[x][y].equals(cardMap[x-1][y]))||
						(x<3&& cardMap[x][y].equals(cardMap[x+1][y]))||
						(y>0&&cardMap[x][y].equals(cardMap[x][y-1]))||
						(y<3&&cardMap[x][y].equals(cardMap[x][y+1]))){
					
					complete =false;
					break ALL ;
				}
			}
		}
		if(complete){
			new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束").setPositiveButton("重来",  new  DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					startGame();
				}
			}).show();
		}
		
		
	}
	private void checkPass(){
		boolean complete = false;
		ALL:
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if(cardMap[x][y].getNum()==2048||aty.getScore()>=10000){
					
					complete =true;
					break ALL ;
				}
			}
		}
		if(complete){
			new AlertDialog.Builder(getContext()).setTitle("恭喜").setMessage("通关成功！").setPositiveButton("重来",  new  DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					startGame();
				}
			}).show();
		}
	}
	private Card[][] cardMap =new Card[4][4];
	private List<Point> emptyPoints =new ArrayList<Point>();
	
	public void hintGame(){
		new AlertDialog.Builder(getContext()).setTitle("关于").setMessage(R.string.hint).setPositiveButton("确定",  new  DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		}).show();
	}
}

