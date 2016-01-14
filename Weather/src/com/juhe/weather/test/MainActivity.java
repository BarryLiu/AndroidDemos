package com.juhe.weather.test;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ScrollView;

import com.juhe.weather.R;
import com.juhe.weather.swiperefresh.PullToRefreshBase;
import com.juhe.weather.swiperefresh.PullToRefreshBase.OnRefreshListener;
import com.juhe.weather.swiperefresh.PullToRefreshScrollView;

public class MainActivity extends Activity {

	PullToRefreshScrollView mPullRefreshScrollView;
	ScrollView mScrollView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);
//		mPullRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
		mPullRefreshScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				new GetDataTask().execute();
			}
		});

		mScrollView = mPullRefreshScrollView.getRefreshableView();
	}

	//测试用异步类
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			// Do some stuff here

			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshScrollView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}
}
