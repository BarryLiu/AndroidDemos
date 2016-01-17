package com.example.myyoulookiguess;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 自定义弹出框 判断是不是相等
 * @author Barry
 *
 */
public class MainActivity extends Activity {
	int level = 0;
	private Button [] btnChooses = new Button[4];
	private int [] btnChooseIds={
			R.id.btn_1,
			R.id.btn_2,
			R.id.btn_3,
			R.id.btn_4
	};
	
	private ImageView iv_question;
	private Button btn_next;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
		
		iv_question.setImageResource(Resource.imgsId[level]);
	}

	private void initView() {
		iv_question =(ImageView) findViewById(R.id.iv_question);
		
		for (int i = 0; i < btnChooseIds.length; i++) {
			btnChooses[i] = (Button) findViewById(btnChooseIds[i]);
		}
		
	}
	
	public void cancelLetter(View v){
		Button clickBtn =(Button) v;
		clickBtn.setText("");
	}
	
	@SuppressLint("NewApi")
	public void getLetter(View v){
		Button clickBtn = (Button) v;
		for(int i=0 ; i<btnChooses.length;i++){
			String text = (String) btnChooses[i].getText();
			if("".equals(text)||text.isEmpty()){
				btnChooses[i].setText(clickBtn.getText());
				if(i<btnChooses.length-1)
					return;
			}
		}
		
		String answer = "";
		for (Button btn : btnChooses) {
			answer+=btn.getText().toString();
		}
		
		if(Resource.answer[level].equals(answer)){
			createDailog();
		}else{
			Toast.makeText(this, "错了", 0).show();
		}
	}
	
	public void nextQuestion(){
		//下一题清空数据
		for (int i = 0; i < btnChooses.length; i++) {
			btnChooses[i].setText("");
		}
		level++;
		iv_question.setImageResource(Resource.imgsId[level]);
	}
	
	
	
	 Dialog dialog;
	public void createDailog()
	{
	 	//创建对话框
		dialog = new Dialog(this,R.style.MyDailogStyle);
		
		WindowManager.LayoutParams lp =dialog.getWindow().getAttributes();
		
		//去掉title
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		//lp.
		lp.gravity = Gravity.TOP;
		lp.y = 0;
		//lp.alpha = 0.9f;
		//设置Dialong中的内容
		//需要将layout对应的视图创建出来
		dialog.setContentView(R.layout.dialog_layout);
		
		TextView tv_result = (TextView) dialog.findViewById(R.id.tv_dailog_result);
		//设置答案结果
		tv_result.setText(Resource.str_answers[level]);
		
		//找到下一题的按键
		Button btn_next = (Button) dialog.findViewById(R.id.btn_next);
		
		//实现点击事件
		btn_next.setOnClickListener(new OnClickListener() {
			//点击事件的方法
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				nextQuestion();
			}
		});
		
		
		dialog.show(); 
		
		
		/*Dialog d = new Dialog(this);
		d.setTitle("提示");
		View v = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
		
		TextView tv = (TextView) v.findViewById(R.id.tv_dailog_result);
		tv.setText("异曲同工");
		d.setContentView(v);
		
		d.show();*/
	}
}
