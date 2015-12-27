package com.jing.elts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;

import com.jing.R;
import com.jing.elts.biz.ExamBiz;
import com.jing.elts.biz.IExampBiz;

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
		//加载数据
		initData();
	}

	private void setListener() {
		setExitClickListener();
		
		
		
	}

	private void initData() {
		findViewById(R.id.btnLogin).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				new Thread(){
					@Override
					public void run() {
						mBiz =new ExamBiz(LoginActivity.this);
						
					}
				}.start();;
			}
		});;
	}

	private void setExitClickListener() {
		findViewById(R.id.btnExit).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				View layout = View.inflate(LoginActivity.this, R.layout.exit_prompt, null);
				
				AlertDialog.Builder builder
				=new AlertDialog.Builder(LoginActivity.this);
				builder.setTitle("退出").setView(layout).setPositiveButton("退出", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
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
