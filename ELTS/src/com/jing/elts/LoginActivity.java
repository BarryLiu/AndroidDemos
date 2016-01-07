package com.jing.elts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;

import com.jing.R;
import com.jing.elts.biz.ExamBiz;
import com.jing.elts.biz.IExampBiz;
import com.jing.elts.biz.IdOrPwdException;
import com.jing.elts.entity.User;

public class LoginActivity extends BaseActivity {
	EditText metId, metPwd;
	RadioButton mrbSaveAll, mrbSaveId, mrNotSave;
	IExampBiz mBiz;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		// 得到控件
		initView();
		// 设置监听器
		setListener();
		// 加载数据
		initData();
	}

	private void setListener() {
		setExitClickListener();
		setLoginClickListener();

	}

	private void setLoginClickListener() {
		findViewById(R.id.btnLogin).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				String strId = metId.getText().toString();
				if (TextUtils.isEmpty(strId)) {
					metId.setError("编号不能为空");
					return;
				}
				int id = Integer.parseInt(strId);
				String pwd = metPwd.getText().toString();
				if (TextUtils.isEmpty(pwd)) {
					metPwd.setError("密码不能为空");
					return;
				}
				try {
					User user = mBiz.login(id, pwd);
					saveLoginInfo(id,pwd);
					
					Intent intent =new Intent(LoginActivity.this,MainMenuActivity.class);
					intent.putExtra("biz", (ExamBiz)mBiz);
					
					startActivity(intent);
					
				} catch (IdOrPwdException e) {
					if (e.getMessage().equals("请先注册")) {
						metId.setError("请先注册");
					} else if (e.getMessage().equals("密码错误")) {
						metPwd.setError("密码错误");
					}

				}
			}
		});
	}

	private void initData() {
		SharedPreferences sp = this.getSharedPreferences("login", MODE_PRIVATE);
		int id = sp.getInt("id", -1);
		if (id != -1) {
			metId.setText("" + id);
		}
		String pwd = sp.getString("password", "");
		metPwd.setText(pwd);

		new Thread() {
			@Override
			public void run() {
				mBiz = new ExamBiz(LoginActivity.this);
			}
		}.start();
	}

	private void saveLoginInfo(int id, String pwd) {
		SharedPreferences spf = this.getSharedPreferences("login", MODE_PRIVATE);
		Editor edit = spf.edit();
		edit.clear();
		edit.commit();
		
		if(mrbSaveAll.isChecked()){
			edit.putString("id", id+"");
			edit.putString("password", pwd);
		}else if(mrbSaveId.isChecked()){
			edit.putString("id", id+"");
		}
		edit.commit();
	}

	private void setExitClickListener() {
		findViewById(R.id.btnExit).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				View layout = View.inflate(LoginActivity.this,
						R.layout.exit_prompt, null);

				AlertDialog.Builder builder = new AlertDialog.Builder(
						LoginActivity.this);
				builder.setTitle("退出")
						.setView(layout)
						.setPositiveButton("退出",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										finish();
									}
								}).setNegativeButton("取消", null);
				builder.create().show();

			}
		});
	}

	private void initView() {
		metId = findViewById_(R.id.etId);
		metPwd = findViewById_(R.id.etPwd);
		mrbSaveAll = findViewById_(R.id.rbSaveAll);
		mrbSaveId = findViewById_(R.id.rbSaveId);
		mrNotSave = findViewById_(R.id.rbNotSave);
	}

}
