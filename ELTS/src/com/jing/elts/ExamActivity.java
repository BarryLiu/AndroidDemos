package com.jing.elts;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.jing.R;
import com.jing.elts.biz.IExampBiz;
import com.jing.elts.entity.ExamInfo;
import com.jing.elts.entity.Question;

public class ExamActivity extends BaseActivity implements
		android.view.View.OnClickListener {
	IExampBiz mBiz;
	Gallery mGallery;
	QuestionAdapter mAdapter;
	ExamInfo mExamInfo;
	TextView mtvExamInfo, mtvLeftTime;
	EditText metQuestion;
	CheckBox[] mchkOptions;
	int mPotion;// 当前考题在集合中的索引
	Timer mTimer;// 计时器，显示倒计时的定时器(到时间交卷)
	ArrayList<Integer> mSelectedItems;// 所有做过的考题的索引

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exam);

		initData();// 初始化数据
		initView();
		beginExam();// 开始考试

		setListener();
	}

	private void setListener() {
		setOnItemselectedListener();
		for (CheckBox chk : mchkOptions) {
			chk.setOnClickListener(this);
		}
	}

	private void setOnItemselectedListener() {
		mGallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// 从四个复选框中获取选择的答案，并保存到当前考题中
				mBiz.saveUserAnswers(mPotion, getUserAnswerSFromCheckBoxs());
				setSelectedItem();// 修改Gallery中的item显示
				mPotion = position;// 存放新题目的索引
				showQuestion();// 显示新选择的题目和刷新四个复选框

			}

			private void showQuestion() {
				for (CheckBox chk : mchkOptions) {
					chk.setChecked(false);
				}
				Question q = mBiz.getQuestion(mPotion);
				metQuestion.setText(q.toString());// 显示新的题目
				// 取出考生做过的答案，1.做过2.没做过
				ArrayList<String> userAnswers = q.getUserAnswers();

				if (userAnswers.isEmpty()) {
					return;
				}
				for (int i = 0; i < userAnswers.size(); i++) {
					char answer = userAnswers.get(i).charAt(0);
					// ABCD -- 0123
					mchkOptions[answer - 65].setChecked(true);
				}

			}

			private ArrayList<String> getUserAnswerSFromCheckBoxs() {
				ArrayList<String> userAnswers = new ArrayList<String>();
				for (int i = 0; i < mchkOptions.length; i++) {
					if (mchkOptions[i].isChecked()) {
						userAnswers.add(mchkOptions[i].getText().toString());

					}
				}
				return userAnswers;
			}
		});
	}

	public void setSelectedItem() {
		// 若当前考题已做过，
		ArrayList<String> userAnswers = mBiz.getQuestion(mPotion)
				.getUserAnswers();
		if (mSelectedItems.contains(mPotion)) {
			if (userAnswers.isEmpty()) {
				mAdapter.removeSelectedItem(mPotion);
			}
		} else {// 没有做过
			if (userAnswers.isEmpty()) {
				mAdapter.addSelectedItem(mPotion);

			}
		}
	}

	/** 开始考试 */
	private void beginExam() {
		new Thread() {
			public void run() {
				mExamInfo = mBiz.beginExam();
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						mtvExamInfo.setText(mExamInfo.toString());
						mAdapter = new QuestionAdapter(mSelectedItems,
								ExamActivity.this, mExamInfo);
						mGallery.setAdapter(mAdapter);
					}
				});
				startTime();// 开始计时

			}

		}.start();
	}

	protected void startTime() {
		long startTime = System.currentTimeMillis();// 获取系统当前时间
		final long endTime = startTime + mExamInfo.getLimitTime() * 60 * 1000;// 计算结束的时间，单位(毫秒)
		mTimer = new Timer();
		mTimer.schedule(new TimerTask() {
			long minute, second;

			@Override
			public void run() {
				long leftTime = endTime - System.currentTimeMillis();
				minute = leftTime / 1000 / 60;// 剩余时间的分钟数
				second = leftTime / 1000 % 60;// 剩余时间的秒数

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mtvLeftTime.setText("剩余时间:" + minute + ":" + second);
					}
				});

			}
		}, 0, 1000);
		// 设置考试结束的时间响应
		mTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				mTimer.cancel();
				commit();
			}
		}, endTime);
	};

	private void initView() {
		mtvExamInfo = findViewById_(R.id.tvExamInfo);
		mtvLeftTime = findViewById_(R.id.tvLeftTime);
		metQuestion = findViewById_(R.id.etQuestion);

		mchkOptions = new CheckBox[4];
		mchkOptions[0] = findViewById_(R.id.chkA);
		mchkOptions[1] = findViewById_(R.id.chkB);
		mchkOptions[2] = findViewById_(R.id.chkC);
		mchkOptions[3] = findViewById_(R.id.chkD);
		mGallery = findViewById_(R.id.gallery);

	}

	private void initData() {
		mPotion = 0;
		mBiz = (IExampBiz) getIntent().getSerializableExtra("biz");
		mSelectedItems = new ArrayList<Integer>();
		mTimer = new Timer();
	}

	class QuestionAdapter extends BaseAdapter {
		ArrayList<Integer> selectedItems;
		Context context;
		ExamInfo examInfo;

		public QuestionAdapter(ArrayList<Integer> selectedItems,
				Context context, ExamInfo examInfo) {
			super();
			this.selectedItems = selectedItems;
			this.context = context;
			this.examInfo = examInfo;
		}

		/** 得到选择的考题 */
		public ArrayList<Integer> getSelectedItems() {
			return selectedItems;
		}

		/** 添加一条考题 */
		public void addSelectedItem(int position) {
			selectedItems.add(position);
			notifyDataSetChanged();// 通知适配器
		}

		/** 删除一条考题 */
		public void removeSelectedItem(Integer position) {
			selectedItems.remove(position);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return examInfo.getQuestionCount();
		}

		@Override
		public Object getItem(int position) {
			return selectedItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View layout = View.inflate(context, R.layout.item_question, null);
			ImageView ivThumb = (ImageView) layout.findViewById(R.id.ivThumb);
			TextView tvQuestion = (TextView) layout
					.findViewById(R.id.tvQuestion);
			tvQuestion.setText("题" + (position));

			if (selectedItems.contains(position)) {// 如果包含说明这题做过
				ivThumb.setImageResource(R.drawable.answer24x24);
			} else {
				ivThumb.setImageResource(R.drawable.ques24x24);
			}

			return layout;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.exam, menu);
		;

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.mi_commit) {
			commit();// 交卷
		}
		return super.onOptionsItemSelected(item);
	}

	/** 交卷 */
	private void commit() {
		int score = mBiz.over();
		AlertDialog.Builder builder = new AlertDialog.Builder(ExamActivity.this);
		builder.setIcon(R.drawable.exam_commit32x32);
		builder.setTitle("交卷");
		builder.setMessage(mBiz.getUser().getName() + "的成绩:" + score + "分");
		builder.setPositiveButton("返回", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				finish();
			}
		});
		builder.create().show();
	}

	@Override
	public void onClick(View v) {
		if (v instanceof CheckBox) {// 说明单机的是选择ABCD中的哪个答案
			if (mSelectedItems.contains(mPotion)) {
				for (CheckBox chk : mchkOptions) {
					if (chk.isChecked()) {
						return;
					}
				}
				mAdapter.removeSelectedItem(mPotion);
			} else {// 若未做过
				for (CheckBox chk : mchkOptions) {
					if (chk.isChecked()) {
						mAdapter.addSelectedItem(mPotion);
						break;
					}
				}
			}
		}
	}
}
