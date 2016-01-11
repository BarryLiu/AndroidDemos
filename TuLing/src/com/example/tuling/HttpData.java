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

public class HttpData extends AsyncTask<String, Void, String>{

	private HttpClient mHttpClient;
	private HttpGet mHttpGet;
	private HttpResponse mHttpResponse;
	private HttpEntity mHttpEntity;
	private InputStream in;
	
	private String url;
	
	//自己写的 用于传递
	private HttpGetDateLisenter lisenter;
	public HttpData(String url,HttpGetDateLisenter lisenter) {
		this.url= url;
		this.lisenter = lisenter;
	}
	
	/* 获取数据 */
	@Override
	protected String doInBackground(String... params) {
		
		try {
			//实例化客户端
			mHttpClient=new DefaultHttpClient();
			mHttpGet =new HttpGet(url);
			
			mHttpResponse = mHttpClient.execute(mHttpGet);
			mHttpEntity = mHttpResponse.getEntity();
			
			in = mHttpEntity.getContent();
			BufferedReader br= new BufferedReader(new InputStreamReader(in));
			
			String line = null ;
			StringBuffer sb=new StringBuffer();
			while((line = br.readLine())!=null){
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
		}
		
		
		return null;
	}
	
	@Override
	protected void onPostExecute(String result) {
		lisenter.getDataUrl(result);
		super.onPostExecute(result);
	}
}
