package com.example.mytypetextview;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 自定义 控件
 * 
 * 
 * @author Barry
 * 
 */
public class TypeTextView extends TextView {
	private Context mContext;

	/**
	 * MediaPlayer class can be used to control playback of audio/video files
	 * and streams. An example on how to use the methods in this class can be
	 * found in android.widget.VideoView.
	 */
	private MediaPlayer mMediaPlayer;
	private String mShowTextString;
	private Timer mTimer;

	private OnTypeTextViewLisenter mOnTypeViewListener;
	// 默认打字间隔
	private static final int TYPE_TIME_DELAY = 80;
	// 当前打字间隔
	private int mTypeTimeDelay = TYPE_TIME_DELAY;

	public TypeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
	}

	public TypeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
	}

	public TypeTextView(Context context) {
		super(context);
		this.mContext = context;
	}

	public void setOnTypeViewLisenter(
			OnTypeTextViewLisenter onTypeTextViewLisenter) {
		this.mOnTypeViewListener = onTypeTextViewLisenter;
	}

	public void start(final String textString) {
		start(textString, TYPE_TIME_DELAY);
	}

	private void start(final String textString, final int typeTimeDelay) {
		if (TextUtils.isEmpty(textString) || typeTimeDelay <= 0)
			return;
		post(new Runnable() {

			@Override
			public void run() {
				mShowTextString = textString;
				mTypeTimeDelay = typeTimeDelay;
				setText("");
				startTypeTimer();
				if (mOnTypeViewListener != null) {
					mOnTypeViewListener.onTypeViewStart();
				}
			}

		});
	}

	public void stop() {
		stopTypeTimer();
		stopAudio();
	}

	private void stopAudio() {
		if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
			mMediaPlayer.stop();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}

	private void stopTypeTimer() {
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
	}

	private void startTypeTimer() {
		stopTypeTimer();
		mTimer = new Timer();
		mTimer.schedule(new TypeTimerTask(), mTypeTimeDelay);
	}

	private void startAudioPlayer() {
		stopAudio();
		playAudio(R.raw.type_in);
	}

	private void playAudio(int typeIn) {
		try {
			stopAudio();
			mMediaPlayer = MediaPlayer.create(mContext, typeIn);
			mMediaPlayer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class TypeTimerTask extends TimerTask {
		@Override
		public void run() {
			post(new Runnable() {

				@Override
				public void run() {
					if (getText().toString().length() < mShowTextString
							.length()) {
						setText(mShowTextString.substring(0, getText()
								.toString().length() + 1));
						startAudioPlayer();
						startTypeTimer();
					} else {
						stopTypeTimer();
						if (mOnTypeViewListener == null) {
							mOnTypeViewListener.OnTypeViewOver();
						}
					}
				}
			});
		}
	}

	/**
	 * 打印监听器
	 * 
	 * @author Barry
	 * 
	 */
	public interface OnTypeTextViewLisenter {
		/**
		 * 开始打印文字
		 */
		void onTypeViewStart();

		/**
		 * 结束打印文字
		 */
		void OnTypeViewOver();
	}
}
