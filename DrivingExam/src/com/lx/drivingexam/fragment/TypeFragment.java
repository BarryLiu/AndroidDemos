package com.lx.drivingexam.fragment;

import java.util.ArrayList;
import java.util.Collections;

import com.ab.fragment.AbAlertDialogFragment.AbDialogOnClickListener;
import com.ab.task.AbTaskItem;
import com.ab.task.AbTaskListener;
import com.ab.task.AbTaskPool;
import com.ab.util.AbDialogUtil;
import com.lx.drivingexam.MyApplication;
import com.lx.drivingexam.NodataActivity;
import com.lx.drivingexam.R;
import com.lx.drivingexam.ShowActivity;
import com.lx.drivingexam.helper.DBHelper;
import com.lx.drivingexam.helper.DBManager;
import com.lx.drivingexam.view.MyImageView;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TypeFragment extends Fragment{
	MyImageView iv_mock_examination;//模拟考试
	MyImageView iv_sort_order;//顺序练习
	MyImageView iv_random;//随机练习
	MyImageView iv_wrongs;//错题练习
	MyImageView iv_collect;//收藏
	LayoutInflater mInflater;
	DBManager dbm;
	SQLiteDatabase db;
	MyApplication myApp;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myApp=(MyApplication)getActivity().getApplication();
		mInflater=LayoutInflater.from(getActivity());
		dbm=new DBManager(getActivity());//获得数据库帮助类
		dbm.openDatabase();//把数据库从资源文件拷贝到  data/data/包名/ 目录下
		db=dbm.getDatabase();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		inflater.inflate(R.layout.activity_type, container);
		iv_sort_order=(MyImageView)getActivity().findViewById(R.id.iv_sort_order);
		iv_random=(MyImageView)getActivity().findViewById(R.id.iv_random);
		iv_wrongs=(MyImageView)getActivity().findViewById(R.id.iv_wrongs);
		iv_collect=(MyImageView)getActivity().findViewById(R.id.iv_collect);
		iv_mock_examination=(MyImageView)getActivity().findViewById(R.id.iv_mock_examination);
		
		
		iv_sort_order.setOnClickListener(iv_sort_order_click);
		iv_random.setOnClickListener(iv_random_click);
		iv_wrongs.setOnClickListener(iv_wrongs_click);
		iv_collect.setOnClickListener(iv_collect_click);
		iv_mock_examination.setOnClickListener(iv_mock_examination_click);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	/**随机练习*/
	OnClickListener iv_random_click=new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			myApp.PRACTISE_TYPE=1;
			initArgs(true);
		}
	};
	/**模拟考试*/
	OnClickListener iv_mock_examination_click=new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(myApp.KEMU==3){
				String body="考试科目 : 科目四理论(900题)\n考试车型 : 小车(c1,c2,c3)\n考试标准 : 50题,30分钟\n合格标准 : 满分100分,90分及格";
				showDialog(getActivity(),"模拟考试-科目四",body);
			}else{
				String body="考试科目 : 科目一理论(1073题)\n考试车型 : 小车(c1,c2,c3)\n考试标准 : 100题,45分钟\n合格标准 : 满分100分,90分及格";
				showDialog(getActivity(),"模拟考试-科目一",body);
			}
		}
	};
	/**顺序练习*/
	OnClickListener  iv_sort_order_click=new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			myApp.PRACTISE_TYPE=0;
			initArgs(true);
		}
	};
	/**错题练习*/
	OnClickListener  iv_wrongs_click=new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String selection=" where w.[stocks_type]=? and kemu=? and LicenseType like ?";
			String[] args;
			if(myApp.KEMU==3){
				args=new String[]{"2","4","A1%"};
			}else{
				args=new String[]{"2","1","A1%MNP"};
			}
			myApp.is_static_exption=true;
			myApp.is_static_judge=true;
			myApp.PRACTISE_TYPE=4;
			reloadData(selection,args);
		}
	};
	/**收藏*/
	OnClickListener  iv_collect_click=new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String selection=" where c.[is_collect]=? and kemu=? and LicenseType like ?";
			String[] args=new String[]{"1","1","A1%MNP"};
			if(myApp.KEMU==3){
				args=new String[]{"1","4","A1%"};
			}
			myApp.is_static_exption=true;
			myApp.is_static_judge=true;
			myApp.PRACTISE_TYPE=3;
			reloadData(selection,args);
		}
	};
	/***初始化List<Bundle>数据：myApp.bList中不存在数据就查询数据库获得新的数据*/
	public void initmyApp(){
		if(myApp.bList!=null){
			myApp.bList.clear();
		}else{
			myApp.bList=new ArrayList<Bundle>();
		}
		myApp.position=0;
	}
	/**重新加载数据*/
	private void reloadData(final String selection,final String[] args){
		//显示进度框
//		AbDialogUtil.showProgressDialog(getActivity(),R.drawable.progress_circular2,"正在获取题目...");
		AbTaskPool mAbTaskPool = AbTaskPool.getInstance();
		//定义异步执行的对象
		final AbTaskItem item = new AbTaskItem();
		item.setListener(new AbTaskListener() {
			@Override
			public void update() {
				//AbDialogUtil.removeDialog(getActivity());
				Intent intent;
				if(myApp.bList.size()<=0){
					intent=new Intent(getActivity(),NodataActivity.class);
				}else{
					intent=new Intent(getActivity(),ShowActivity.class);
				}
				startActivity(intent);
			}

			@Override
			public void get() {
	   		    try {
	   		    	initmyApp();
	   				myApp.bList=DBHelper.getQuestionBundle(db,selection, args);
	   				myApp.listSize=myApp.bList.size();
	   				switch (myApp.PRACTISE_TYPE) {
					case 1:
						Collections.shuffle(myApp.bList);
						break;
					case 2:
						Collections.shuffle(myApp.bList);
						if(myApp.KEMU==3){
							myApp.listSize=50;
						}else{
							myApp.listSize=100;
						}
						break;
					default:
						break;
					}
	   		    } catch (Exception e) {
	   		    }
		  };
		});
		//开始执行
		mAbTaskPool.execute(item);
	}
	private void initArgs(boolean bool){
		myApp.is_static_exption=bool;
		myApp.is_static_judge=bool;
		String selection=" where kemu=? and LicenseType like ?";
		String[] selectionArgs;
		if(myApp.KEMU==3){
			selectionArgs=new String[]{"4","A1%"};
		}else{
			selectionArgs=new String[]{"1","A1%MNP"};
		}
		reloadData(selection,selectionArgs);
	}
	private void showDialog1(Context context,String titile,String body){
		AbDialogUtil.showAlertDialog(context,
			R.drawable.ic_launcher1, titile, body,
			new AbDialogOnClickListener() {
				@Override
				public void onPositiveClick() {
					myApp.PRACTISE_TYPE=2;
					initArgs(false);
				}
				@Override
				public void onNegativeClick() {
				}
		});
	}
	private void showDialog(Context context,String titile,String body){
		View aView = mInflater.inflate(R.layout.dialog_text_button,null);
		AbDialogUtil.showDialog(aView);
		TextView title_choices=(TextView)aView.findViewById(R.id.title_choices);
		TextView choice_one_text=(TextView)aView.findViewById(R.id.choice_one_text);
		Button leftBtn1 = (Button)aView.findViewById(R.id.left_btn);
		Button rightBtn1 = (Button)aView.findViewById(R.id.right_btn);
		title_choices.setText(titile);
		choice_one_text.setText(body);
		//左边的按钮
		leftBtn1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				AbDialogUtil.removeDialog(getActivity());
			}
		});
		//右边的按钮
		rightBtn1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				AbDialogUtil.removeDialog(getActivity());
				myApp.PRACTISE_TYPE=2;
				initArgs(false);
			}
		});
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		System.gc();
	}
}
