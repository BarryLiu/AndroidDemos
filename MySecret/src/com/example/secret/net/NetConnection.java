package com.example.secret.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.example.secret.Config;

import android.os.AsyncTask;

public class NetConnection {
	public NetConnection(final String url, final HttpMethod method,
			final SuccessCallback successCallback, final FailCallback failCallback,
			final String... kvs) {

		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... arg0) {
				StringBuffer paramsStr = new StringBuffer();
				for (int i = 0; i < kvs.length; i += 2) {
					paramsStr.append(kvs[i]).append(kvs[i + 1]).append("$");
				}

				try {
					URLConnection uc = null;

					switch (method) {
					case POST:
						uc = new URL(url).openConnection();
						uc.setDoOutput(true);
						BufferedWriter bw = new BufferedWriter(
								new OutputStreamWriter(uc.getOutputStream(),
										Config.CHARSET));
						bw.write(paramsStr.toString());
						break;
					case GET:
						uc = new URL(url + "?" + paramsStr.toString())
								.openConnection();
						break;
					default:
						break;
					}
					// 读取数据
					BufferedReader br = new BufferedReader(
							new InputStreamReader(uc.getInputStream(),
									Config.CHARSET));
					String line;
					StringBuffer result = new StringBuffer();

					System.out.println("request URL:" + uc.getURL());
					System.out.println("request Data:" + paramsStr.toString());

					while ((line = br.readLine()) != null) {
						result.append(line);
					}
					return result.toString();

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);

				if (result != null) {
					if (successCallback != null) {
						successCallback.onSuccess(result);
					}
				} else {
					if (failCallback != null) {
						failCallback.onFail();
					}
				}
			}
		};

	}

	 

	public static interface SuccessCallback {
		void onSuccess(String result);
	}

	public static interface FailCallback {
		void onFail();
	}

}
