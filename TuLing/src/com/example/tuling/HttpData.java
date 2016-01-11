package com.example.tuling;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

/**
 * AsyncTask,是android提供的轻量级的异步类, 可以直接继承AsyncTask,在类中实现异步操作,
 * 并提供接口反馈当前异步执行的程度(可以通过接口实现UI进度更新), 最后反馈执行的结果给UI主线程.
 * 
 * @author Barry
 * 
 */
public class HttpData extends AsyncTask<String, Void, String> {

	/**
	 * 生成该类的对象，并调用execute方法之后 首先执行的是onProExecute方法 其次执行doInBackgroup方法
	 * 
	 */

	private HttpClient mHttpClient;
	private HttpGet mHttpGet;
	private HttpResponse mHttpResponse;
	private HttpEntity mHttpEntity;
	private InputStream in;

	private String url;

	// 自己写的 用于传递
	private HttpGetDateLisenter lisenter;

	public HttpData(String url, HttpGetDateLisenter lisenter) {
		this.url = url;
		this.lisenter = lisenter;
	}

	/**
	 * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
	 * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
	 */
	@Override
	protected String doInBackground(String... params) {
		try {
			// 实例化客户端
			mHttpClient = new DefaultHttpClient();
			mHttpGet = new HttpGet(url);

			mHttpResponse = mHttpClient.execute(mHttpGet);
			mHttpEntity = mHttpResponse.getEntity();

			in = mHttpEntity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
		}

		return null;
	}

	// 该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	/**
	 * 这里的Intege参数对应AsyncTask中的第二个参数
	 * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
	 * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作
	 */

	@Override
	protected void onPostExecute(String result) {
		/*自己写的接口   当拿到外部接口的到的数据时调用 并让MainActivity实现  以便立即显示图灵机器人发过来的话*/
		lisenter.getDataUrl(result);
		super.onPostExecute(result);
	}
}
