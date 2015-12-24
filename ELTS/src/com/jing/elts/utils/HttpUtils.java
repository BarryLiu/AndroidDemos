package com.jing.elts.utils;

import java.io.InputStream;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class HttpUtils {
	public enum RequestMethod{
		GET,POST;
	}
	static HttpClient mClient;
	public static final HttpEntity getEntity(String url,ArrayList<BasicNameValuePair> params,RequestMethod method) throws Exception{
		mClient=new DefaultHttpClient();
		HttpUriRequest request=null;
		if(method==RequestMethod.GET&&params!=null){
			StringBuilder sb=new StringBuilder(url);
			sb.append("?");
			for(BasicNameValuePair param:params){
				sb.append(param.getName()).append("=")
				  .append(param.getValue())
				  .append("&");
			}
			sb.deleteCharAt(sb.length()-1);
		}
		switch (method) {
		case GET:
			request=new HttpGet(url);
			break;
		case POST:
			request=new HttpPost(url);
			if(params!=null){
				UrlEncodedFormEntity entity=new UrlEncodedFormEntity(params);
				((HttpPost)request).setEntity(entity);
			}
			break;
		}
		HttpResponse response=mClient.execute(request);
		if(response.getStatusLine().getStatusCode()==200){
			return response.getEntity();
		}
		return null;
	}
	public static InputStream getInputStream(String url,ArrayList<BasicNameValuePair> params,RequestMethod method) throws Exception{
		return getEntity(url, params, method).getContent();
	}
	public static void closeClient(){
		mClient.getConnectionManager().shutdown();
	}
}
