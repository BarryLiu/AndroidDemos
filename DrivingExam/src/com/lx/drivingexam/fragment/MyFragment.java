package com.lx.drivingexam.fragment;

import com.ab.task.AbTaskItem;
import com.ab.task.AbTaskListener;
import com.ab.task.AbTaskPool;
import com.ab.util.AbDialogUtil;
import com.lx.drivingexam.MyApplication;
import com.lx.drivingexam.R;
import com.lx.drivingexam.adapter.MenuAdapter;
import com.lx.drivingexam.helper.DBHelper;
import com.lx.drivingexam.helper.DBManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MyFragment extends Fragment{
	ListView lv_menu;
	MenuChangeListener menuChange;
	private MyApplication myApp=null;
	LayoutInflater mInflater;
	DBManager dbm;
	SQLiteDatabase db;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myApp=(MyApplication)getActivity().getApplication();
		mInflater=LayoutInflater.from(getActivity());
		dbm=new DBManager(getActivity());
		db=dbm.getDatabase();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		menuChange=(MenuChangeListener)getActivity();
		lv_menu=(ListView)container.findViewById(R.id.listview_menu);
		final MenuAdapter adapter=new MenuAdapter(getActivity());
		lv_menu.setAdapter(adapter);
		lv_menu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0://科目一
					myApp.KEMU=0;
					adapter.changeselectPosition(position);
					break;
				case 1://科目二
					//StaticBean.KEMU=1;
					Toast.makeText(getActivity(), "收集数据中,敬请期待", Toast.LENGTH_SHORT).show();
					break;
				case 2://科目三
					Toast.makeText(getActivity(), "收集数据中,敬请期待", Toast.LENGTH_SHORT).show();
					//StaticBean.KEMU=2;
					break;
				case 3://科目四
					myApp.KEMU=3;
					adapter.changeselectPosition(position);
					break;
				case 4:
					showDialog(getActivity(), "清除数据", "确定要清除\n收藏记录和错题记录吗?");
					break;
				case 5:
					myApp.showDialog(getActivity(),mInflater);
					break;
				default:
					break;
				}
				menuChange.changeTitleText();
			}

		});
		return super.onCreateView(inflater, container, savedInstanceState);
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
				deleteData();
			}
		});
	}
	/**清理数据*/
	private void deleteData(){
		//显示进度框
		AbDialogUtil.showProgressDialog(getActivity(),R.drawable.progress_circular2,"正在清除...");
		AbTaskPool mAbTaskPool = AbTaskPool.getInstance();
		//定义异步执行的对象
		final AbTaskItem item = new AbTaskItem();
		item.setListener(new AbTaskListener() {
			@Override
			public void update() {
				AbDialogUtil.removeDialog(getActivity());
				Toast.makeText(getActivity(), "清除成功", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void get() {
				DBHelper.deleteAll(db);
			};
		});
		//开始执行
		mAbTaskPool.execute(item);
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.gc();
	}
	public interface MenuChangeListener{
		public void changeTitleText();
	}
}
