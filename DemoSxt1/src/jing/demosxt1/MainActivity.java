package jing.demosxt1;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends ActionBarActivity implements OnSeekBarChangeListener{

	private ProgressBar mProgressBar;
	private Button btnDownload;
	
	
	
	private int mProgress;// 代表seekbar 当前的progress 值
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		testProgressBar
//		setContentView(R.layout.progressbar);
//		initView();
//		setListener();
//		
		
		// testSeekBar
		this.setContentView(R.layout.seekbar);
		
		SeekBar seekBar = (SeekBar) this.findViewById(R.id.seekBar);
		seekBar.setOnSeekBarChangeListener(this);
		
	}
	
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		mProgress = progress;
		
		Log.i("main", "progress="+progress);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		Log.i("main", "开始拖动");
	}
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		Log.i("main", "结束拖动");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	// =============ProgressBar
	private void setListener() {
		btnDownload = (Button) this.findViewById(R.id.btnDounLoad);
		btnDownload.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(){
					public void run(){
						mProgressBar.setProgress(0);
						for (int i = 1; i < 100; i++) {
							mProgressBar.setProgress(i);
							SystemClock.sleep(20); //让当前线程休眠20 毫秒
						}
					}
				}.start();
			}
		});
	}
	
	
	private void initView() {
		mProgressBar = (ProgressBar) this.findViewById(R.id.pd2);
	}
 
}
