package com.example.secret.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.example.secret.R;
import com.example.secret.net.GetCode;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		etPhone = (EditText) findViewById(R.id.etPhoneNum);
		findViewById(R.id.btnGetCode).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (TextUtils.isEmpty(etPhone.getText().toString())) {
					Toast.makeText(LoginActivity.this,
							R.string.phone_num_can_not_null, Toast.LENGTH_SHORT)
							.show();
					return;
				}
				final ProgressDialog pd = ProgressDialog.show(LoginActivity.this, getResources()
						.getString(R.string.connection), getResources()
						.getString(R.string.connection_to_server));

				new GetCode(etPhone.getText().toString(),
						new GetCode.SuccessCallback() {

							@Override
							public void onSuccess(String result) {
								Toast.makeText(LoginActivity.this,
										R.string.suc_to_get_code,
										Toast.LENGTH_SHORT).show();
								pd.dismiss();
							}
						}, new GetCode.FailCallback() {

							@Override
							public void onFail() {
								Toast.makeText(LoginActivity.this,
										R.string.fail_to_get_code,
										Toast.LENGTH_SHORT).show();
								pd.dismiss();
							}
						});
			}
		});
	}

	public EditText etPhone;

}
