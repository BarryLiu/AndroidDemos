package com.game;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.MonthDisplayHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private LinearLayout root;
	private GameView gameView;
	private Button btnNewGame;
	private Button btnHintGame;

	public MainActivity(){
		mainActivity=this;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		root = (LinearLayout) this.findViewById(R.id.container);
		root.setBackgroundColor(0xfffaf8ef);
		
		tvScore=(TextView) this.findViewById(R.id.tvScore);
		tvBestScore = (TextView) this.findViewById(R.id.tvBestScore);
		
		gameView = (GameView) this.findViewById(R.id.gameView);
		
		btnNewGame = (Button) this.findViewById(R.id.btnNewGame);
		btnNewGame.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gameView.startGame();
			}
		});
		
		animLayer = (AnimLayer) this.findViewById(R.id.animLayer);
		
		btnHintGame = (Button) this.findViewById(R.id.btnHintGame);
		btnHintGame.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gameView.hintGame();
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void clearScore(){
		this.score=0;
	}
	public void showScore(){
		tvScore.setText(score+"");
	}
	public void addScore(int s){
		this.score+=s;
		showScore();
		
		int maxScore=Math.max(score, getBestScore());
		saveBestScore(maxScore);
		showBestScore(maxScore);
	}
	private void saveBestScore(int maxScore) {
		Editor e = getPreferences(MODE_PRIVATE).edit();
		e.putInt(SP_KEY_BEST_SCORE, maxScore);
		e.commit();
	}
	public int getScore() {
		return score;
	}
	private int score=0;
	private TextView tvScore,tvBestScore;
	
	private AnimLayer animLayer = null;
	
	private static MainActivity mainActivity =null;
	 
	public static MainActivity getMainActivity(){
		return mainActivity; 
	}
	public int getBestScore() {
		return getPreferences(MODE_PRIVATE).getInt(SP_KEY_BEST_SCORE, 0);
	}
	
	public static final String SP_KEY_BEST_SCORE ="bestScore";

	public void showBestScore(int s) {
		tvBestScore.setText(s+"");
	}
	public AnimLayer getAnimLayer() {
		return animLayer;
	}
	
	
}
