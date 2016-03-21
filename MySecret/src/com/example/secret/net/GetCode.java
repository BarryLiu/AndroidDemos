package com.example.secret.net;

import org.json.JSONObject;

import com.example.secret.Config;

public class GetCode {

	public GetCode(String phone, SuccessCallback successCallback,
			final FailCallback failCallback) {

		new NetConnection(Config.SERVER_URL, HttpMethod.POST,
				new NetConnection.SuccessCallback() {

					@Override
					public void onSuccess(String result) {
						try {
							JSONObject jsonObject = new JSONObject(result);

							switch (jsonObject.getInt(Config.KEY_STATUS)) {
							case Config.RESULT_STATUS_SUCCESS:

								break;
							case Config.RESULT_STATUS_FAIL:

								break;
							case Config.RESULT_STATUS_INVAID_TOKEN:

								break;

							default:
								if (failCallback != null) {
									failCallback.onFail();
								}
								break;
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}, new NetConnection.FailCallback() {

					@Override
					public void onFail() {
						// TODO Auto-generated method stub

					}
				}, Config.KEY_ACTION, Config.ACTION_GET_CODE,
				Config.KEY_PHONE_NUM, phone);
	}

	public static interface SuccessCallback {
		void onSuccess(String result);
	}

	public static interface FailCallback {
		void onFail();
	}
}
