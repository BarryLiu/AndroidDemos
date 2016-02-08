package io.barryliu.driverexam.fragment;

import io.barryliu.driverexam.MyApplication;
import io.barryliu.driverexam.R;
import io.barryliu.driverexam.adapter.MenuAdapter;
import io.barryliu.driverexam.helper.DBManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.task.AbTaskItem;
import com.ab.task.AbTaskListener;
import com.ab.task.AbTaskPool;
import com.ab.util.AbDialogUtil;


public class MyFragment extends Fragment{
	ListView lv_menu;
	private MyApplication myApp=null;
	private LayoutInflater mInflater;
	MenuChangeListener menuChange;
	DBManager dbm;
	SQLiteDatabase db ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApp = (MyApplication) getActivity().getApplication();
		mInflater = LayoutInflater.from(getActivity());
		dbm = new DBManager(getActivity());
		db=dbm.getDatabase();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		menuChange = (MenuChangeListener) getActivity();
		lv_menu = (ListView) container.findViewById(R.id.listview_menu);
		
		final MenuAdapter adapter =new MenuAdapter(getActivity());
		lv_menu.setAdapter(adapter);
		lv_menu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					myApp.KEMU=0;
//					adapter.changeselectPosition(position);
					break;
				case 1:
					Toast.makeText(getActivity(), "未实现", Toast.LENGTH_SHORT).show();
					break;
				case 2:
					Toast.makeText(getActivity(), "未实现", Toast.LENGTH_SHORT).show();
					
					break;
				case 3:
					myApp.KEMU=3;
					//adapter.changeselectPosition(position);
					break;
				case 4:
					Toast.makeText(getActivity(), "未实现", Toast.LENGTH_SHORT).show();
					break;
				case 5:
					
					break;

				default:
					break;
				}
				menuChange.changeTitleText();
			}
		});
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	private void showDialog(Context context ,String title , String body){
		View aView = mInflater.inflate(R.layout.dialog_text_button1, null);
		AbDialogUtil.showDialog(aView);
		TextView title_choices = (TextView)aView.findViewById(R.id.title_choices);
		TextView choice_one_text=(TextView)aView.findViewById(R.id.choice_one_text);
//		aView.findViewById(R.id.)
//		
		title_choices.setText(title);
		choice_one_text.setText(body);
		
		//
		
		//
	}
	
	private void deleteData(){
		AbDialogUtil.showProgressDialog(getActivity(), R.drawable.progress_circular2, "正在清除");
		AbTaskPool mAbTaskPool =AbTaskPool.getInstance();
		
		final AbTaskItem item =new AbTaskItem();
		
		item.setListener(new AbTaskListener(){
			@Override
			public void update() {
				AbDialogUtil.removeDialog(getActivity());
				Toast.makeText(getActivity(), "清除成功", Toast.LENGTH_SHORT).show();
			}
			@Override
			public void get() {
//				
			}
		});
		
	}
	public interface MenuChangeListener{
		public void changeTitleText();
	}
}
