package com.jing.elts;

import com.jing.R;
import com.jing.R.layout;
import com.jing.R.menu;
import com.jing.elts.biz.ExamBiz;
import com.jing.elts.biz.IExampBiz;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainMenuActivity extends BaseActivity {
	private TextView tvWelCome;
	IExampBiz mBiz;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		mBiz = (IExampBiz) getIntent().getSerializableExtra("biz");

		initView();
		setListener();
	}

	private void setListener() {
		setExitClickListener();

		setBeginExamClickListener();
	}

	private void setBeginExamClickListener() {
		findViewById(R.id.btnExam).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 创建一个显示一个加载进度的标题
				final ProgressDialog dialog = new ProgressDialog(
						MainMenuActivity.this);
				dialog.setTitle("加载考题");
				dialog.setMessage("加载中...稍后");
				dialog.show();

				new Thread() {
					@Override
					public void run() {
						mBiz.loadQuestions();// 下载考题
						dialog.dismiss();// 关闭对话框
						Intent intent = new Intent(MainMenuActivity.this,
								ExamActivity.class);
						intent.putExtra("biz", (ExamBiz) mBiz);

						startActivity(intent);
					}
				}.start();
			}
		});
	}

	private void setExitClickListener() {
		findViewById(R.id.btnExit).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				View layout = View.inflate(MainMenuActivity.this,
						R.layout.exit_prompt, null);

				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainMenuActivity.this);
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
		tvWelCome = findViewById_(R.id.tvWelcome);
		tvWelCome.setText("欢迎" + mBiz.getUser().getName() + "参加考试");
	}

}
