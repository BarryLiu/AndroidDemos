package com.lx.drivingexam.fragment;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import com.lx.drivingexam.MyApplication;
import com.lx.drivingexam.R;
import com.lx.drivingexam.adapter.MyListAdapter;
import com.lx.drivingexam.helper.DBHelper;
import com.lx.drivingexam.helper.ShowImpl;
import com.lx.drivingexam.helper.StaticBean;
import com.lx.drivingexam.view.MyStarImageView;
import com.lx.drivingexam.view.NonScrollListView;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class QuestionFragment extends Fragment{
	MyApplication myApp;
	String numlist;
	private ViewHolder1 vh;
	private View rootView;
	public MyListAdapter adapter;
	SQLiteDatabase db;
	ShowImpl si;
	Map<Integer,Boolean> myselects=new HashMap<Integer, Boolean>();
	/**刷新Fragment*/
	public void changeFragment(){
		myApp.bundle=myApp.bList.get(myApp.position);
		Bundle bundle=myApp.bundle;
		if(vh.videoView.isPlaying()){
			vh.videoView.stopPlayback();
		}
		//重新加载多选数据
		initMyselects();
		//获得是否显示解释 并判断
		int exption=bundle.getInt(StaticBean.IS_SHOW_EXPTION);
		isExplain(exption);
		//获得选择的题目Id
		String selectId=bundle.getString(StaticBean.SELECT_ID);
		String answerTrue=bundle.getString(StaticBean.WEB_NOTE_COLUMNS[4]);
		if(selectId.equals("")){
			vh.ll_answer_true.setVisibility(View.GONE);
			vh.ll_answer_false.setVisibility(View.GONE);
			vh.listview_an.setEnabled(true);
		}else if(selectId.equals(answerTrue)){
			vh.ll_answer_true.setVisibility(View.VISIBLE);
			vh.ll_answer_false.setVisibility(View.GONE);
			vh.listview_an.setEnabled(false);
		}else{
			vh.ll_answer_true.setVisibility(View.GONE);
			vh.ll_answer_false.setVisibility(View.VISIBLE);
			vh.tv_answer_false.setText("您答错了,标准答案:"+getAswerTrue(answerTrue));
			vh.listview_an.setEnabled(false);
		}
		//获得 题目历史类型 0没做题，1正确题，2错题。
		if(myApp.is_static_judge){
			int stocks_type=bundle.getInt(StaticBean.STOCKS_TYPE);
			if(stocks_type==0||stocks_type==1) {
				vh.rl_question.setBackgroundResource(R.drawable.bomm_correct);
			}else{
				vh.rl_question.setBackgroundResource(R.drawable.bomm_wrong);
			}
		}else{
			vh.ll_answer_false.setVisibility(View.GONE);
			vh.ll_answer_true.setVisibility(View.GONE);
		}
		//获得题目类型：单选1,2,多选3
		String type=myApp.bundle.getString(StaticBean.WEB_NOTE_TYPE);
		if(type.equals("3")&&selectId.length()<2){
			vh.bt_ok.setVisibility(View.VISIBLE);
		}else{
			vh.bt_ok.setVisibility(View.GONE);
		}
		//重新加载工具栏数据
		si.changeToolsData();
		//获得选项数据
		String[] answer=getAnswer(bundle);
		//修改选项的数据
		adapter.changeData(answer,selectId);
		//通知选项的适配器，数据已经更改
		adapter.notifyDataSetChanged();
		//更改题目内容
		vh.tv_question.setText(bundle.getString("Question"));
		//更改解释内容
		vh.tv_explain.setText(bundle.getString("explain").replace("\\", "\\\\"));
		//获得视频地址 如果有则获得图片并设置
		String videoUrl=bundle.getString(StaticBean.WEB_NOTE_COLUMNS[9]);
		//获得图片地址 如果有则获得图片并设置
		String imgUrl=bundle.getString(StaticBean.WEB_NOTE_COLUMNS[6]);
		if(videoUrl!=null){
			vh.iv_question.setImageResource(R.drawable.home_bottom_bar);
			vh.videoView.setVisibility(View.VISIBLE);
			if(storeVideoFile(videoUrl)){
				vh.videoView.setVideoPath(getActivity().getFilesDir().getAbsolutePath()+"/"+videoUrl);
				vh.videoView.start();
			}
		}else if(imgUrl!=null){
			vh.videoView.setVisibility(View.GONE);
			vh.iv_question.setImageBitmap(getImageFromAssets(imgUrl));
		}else{
			vh.videoView.setVisibility(View.GONE);
			vh.iv_question.setImageResource(R.drawable.home_bottom_bar);
		}
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		si=(ShowImpl) getActivity();
		db=si.getSQLiteDatabase();
		myApp=(MyApplication)(getActivity().getApplication());
	}
	MyStarImageView iv_difficulty;
	/**创建视图*/
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		try {
			if(rootView==null){
				myApp.bundle=myApp.bList.get(myApp.position);
				rootView=inflater.inflate(R.layout.myfragment, null);
				vh=new ViewHolder1();
				vh.videoView=(VideoView)rootView.findViewById(R.id.videoView);
				vh.videoView.setOnCompletionListener(video_completion);
				vh.bt_ok=(Button)rootView.findViewById(R.id.bt_selects_ok);
				vh.bt_ok.setOnClickListener(bt_ok_click);
				vh.my_iv_star=(MyStarImageView)rootView.findViewById(R.id.my_iv_star);
				vh.tv_question=(TextView)rootView.findViewById(R.id.tv_show_question);
				vh.tv_answer_false=(TextView)rootView.findViewById(R.id.tv_answer_false);
				vh.ll_answer_true=(LinearLayout)rootView.findViewById(R.id.ll_answer_true);
				vh.ll_answer_false=(LinearLayout)rootView.findViewById(R.id.ll_answer_false);
				vh.rl_explain=(RelativeLayout)rootView.findViewById(R.id.rl_explain);
				vh.rl_question=(RelativeLayout)rootView.findViewById(R.id.rl_question);
				vh.tv_explain=(TextView)rootView.findViewById(R.id.tv_explain);
				vh.listview_an=(NonScrollListView)rootView.findViewById(R.id.listview_an);
				vh.listview_an.setOnItemClickListener(listener);
				vh.iv_question=(ImageView)rootView.findViewById(R.id.iv_question);
				adapter=new MyListAdapter(getAnswer(myApp.bundle), getActivity());
		        vh.listview_an.setAdapter(adapter);
			}
		    ViewGroup parent = (ViewGroup) rootView.getParent();  
		    if (parent != null) {  
		        parent.removeView(rootView);  
		    }
		    changeFragment();
		} catch (Exception e) {
			// TODO: handle exception
		}
        return rootView;
	}
	/**视频播放完毕的监听事件**/
	OnCompletionListener video_completion=new OnCompletionListener() {
		
		@Override
		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
			mp.start();
            mp.setLooping(true);
		}
	};
	/**确认的点击事件*/
	OnClickListener bt_ok_click=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String selectid="";
			for (int i = 0; i < 4; i++) {
				if(myselects.get(i)){
					selectid+=(i+1);
				}
			}
			if(selectid.length()<2){
				Toast.makeText(getActivity(), "至少选择两个答案", Toast.LENGTH_SHORT).show();
			}else{
				isTrue(selectid);
				vh.bt_ok.setVisibility(View.GONE);
				if(myApp.is_next){
					try {
						Thread.currentThread().sleep(500);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
					nextFragment();
				}
			}
		}
	};
	/**选项的点击事件*/
	OnItemClickListener listener=new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			String type=myApp.bundle.getString(StaticBean.WEB_NOTE_TYPE);
			ImageView iv_an=(ImageView)view.findViewById(R.id.iv_an);
			if("12".contains(type)){
				String selectid=position+1+"";
				isTrue(selectid);
				view.setBackgroundResource(R.drawable.list_item1);
				iv_an.setImageResource(R.drawable.yuan_n);
				if(myApp.is_next){
					nextFragment();
				}
			}else{
				if(myselects.get(position)){
					myselects.put(position, false);
					view.setBackgroundResource(R.drawable.list_item0);
					iv_an.setImageResource(R.drawable.yuan_s);
				}else{
					myselects.put(position, true);
					view.setBackgroundResource(R.drawable.list_item1);
					iv_an.setImageResource(R.drawable.yuan_n);
				}
			}
			
		}
	};
	private void isTrue(String selectid){
		String answer_true=myApp.bundle.getString(StaticBean.ANSWER_TRUE);
		int stocks_type=myApp.bundle.getInt(StaticBean.STOCKS_TYPE);
		if(selectid.equals(answer_true)){
			myApp.bundle.putInt(StaticBean.IS_MOCK_EXAMINTION, 1);
			vh.ll_answer_true.setVisibility(View.VISIBLE);
			if(stocks_type==0){
				myApp.bundle.putInt(StaticBean.STOCKS_TYPE, 1);
				DBHelper.insertWrongs(db, myApp);
			}
		}else{
			myApp.bundle.putInt(StaticBean.IS_MOCK_EXAMINTION, 2);
			vh.ll_answer_false.setVisibility(View.VISIBLE);
			vh.tv_answer_false.setText("您答错了,标准答案:"+getAswerTrue(answer_true));
			myApp.bundle.putInt(StaticBean.STOCKS_TYPE, 2);
			if(myApp.is_static_judge){
				vh.rl_question.setBackgroundResource(R.drawable.bomm_wrong);
			}
			if(stocks_type==0){
				DBHelper.insertWrongs(db, myApp);
			}else if(stocks_type==1){
				DBHelper.updateWrongs(db, myApp);
			}
		}
		if(myApp.is_static_judge==false){
			vh.ll_answer_false.setVisibility(View.GONE);
			vh.ll_answer_true.setVisibility(View.GONE);
		}
		myApp.bundle.putString(StaticBean.SELECT_ID, selectid);
		if(myApp.is_exption){
			changeExplain(0);
		}
		vh.listview_an.setEnabled(false);
	}
	/**点击显示或隐藏解释*/
	public void changeExplain(int exption_id){
		if(myApp.is_static_exption){
			switch (exption_id) {
			case 0:
				vh.rl_explain.setVisibility(View.VISIBLE);
				myApp.bundle.putInt(StaticBean.IS_SHOW_EXPTION,1);
				break;
			default:
				vh.rl_explain.setVisibility(View.GONE);
				myApp.bundle.putInt(StaticBean.IS_SHOW_EXPTION,0);
				break;
			}
		}else{
			vh.rl_explain.setVisibility(View.GONE);
		}
		si.changeToolsData();
	}
	/**判断是否显示解释*/
	public void isExplain(int exption_id){
		if(myApp.is_static_exption && myApp.is_exption){
			int difficulty=myApp.bundle.getInt(StaticBean.DIFF_DEGREE);
			vh.my_iv_star.changeData(difficulty);
			switch (exption_id) {
			case 0:
				vh.rl_explain.setVisibility(View.GONE);
				break;
			default:
				vh.rl_explain.setVisibility(View.VISIBLE);
				break;
			}
		}else{
			vh.rl_explain.setVisibility(View.GONE);
		}
	}
	/**获得正确答案的字母：比如把1变成A,13变成AC*/
	public String getAswerTrue(String str){
		str=str.replace("1", "A");
		str=str.replace("2", "B");
		str=str.replace("3", "C");
		str=str.replace("4", "D");
		return str;
	}
	/**读取assets的视频*/
	public boolean storeVideoFile(String VIDEO) {  
        try {  
            InputStream is = getResources().getAssets().open(VIDEO);  
            getActivity();
			//注意,这里用 MODE_WORLD_READABLE 是因为播放Video的是MediaPlayer进程,不是本进程  
            //为了让, MediaPlayer进程能读取此文件,所以设置为: MODE_WORLD_READABLE  
            FileOutputStream os = getActivity().openFileOutput(VIDEO, Context.MODE_WORLD_READABLE);  
            byte[] buffer = new byte[1024];  
            while (is.read(buffer) > -1) {  
                os.write(buffer);  
            }  
            is.close();  
            os.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
        return true;  
    }
	/**
	 * 获得Asets目录下名为finlName的图片 
	 * */
	private Bitmap getImageFromAssets(String fileName){
		Bitmap image = null;
		AssetManager am = getResources().getAssets();
		try{
			InputStream is = am.open(fileName);
			image = BitmapFactory.decodeStream(is);
			is.close();
		}catch (IOException e){
			e.printStackTrace();
		}
		return image;
	}
	/**获得选项数据**/
	public String[] getAnswer(Bundle bundle){
		String type=bundle.getString(StaticBean.WEB_NOTE_TYPE);
		String[] answer;
		if(type.equals("1")){
			answer=new String[]{"正确","错误"};
		}else{
			answer=new String[]{bundle.getString("An1"),
					bundle.getString("An2"),
					bundle.getString("An3"),
					bundle.getString("An4")};
		}
		return answer;
	}
	/**跳到下一个Fragment*/
	public void nextFragment(){
		if(myApp.position<myApp.listSize-1){
			myApp.position+=1;
			changeFragment();
		}else{
			Toast.makeText(getActivity(), "已经是最后一道题了", Toast.LENGTH_SHORT).show();
		}
	}
	/**跳到上一个Fragment*/
	public void previousFragment(){
		if(myApp.position>0){
			myApp.position-=1;
			changeFragment();
		}
	}
	/**跳到指定的Fragment*/
	public void goFragment(int fid){
		myApp.position=fid;
		changeFragment();
	}
	/**初始化多选*/
	private void initMyselects(){
		for (int i = 0; i < 4; i++) {
			myselects.put(i, false);
		}
	}
	class ViewHolder1{
		Button bt_ok;
		MyStarImageView my_iv_star;
		VideoView videoView;
		TextView tv_question;
		TextView tv_answer_false;
		TextView tv_explain;
		LinearLayout ll_answer_true;
		LinearLayout ll_answer_false;
		RelativeLayout rl_explain;
		RelativeLayout rl_question;
		NonScrollListView listview_an;
		ImageView iv_question;
	}
	
}
