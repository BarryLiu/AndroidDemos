package com.lx.drivingexam;

import java.util.ArrayList;
import java.util.List;
import com.ab.activity.AbActivity;
import com.ab.fragment.AbAlertDialogFragment.AbDialogOnClickListener;
import com.ab.util.AbDialogUtil;
import com.ab.view.titlebar.AbTitleBar;
import com.lx.drivingexam.adapter.GridAdapter;
import com.lx.drivingexam.adapter.MyGridAdapter;
import com.lx.drivingexam.bean.ToolsBean;
import com.lx.drivingexam.fragment.QuestionFragment;
import com.lx.drivingexam.helper.AdvancedCountdownTimer;
import com.lx.drivingexam.helper.DBHelper;
import com.lx.drivingexam.helper.DBManager;
import com.lx.drivingexam.helper.ShowImpl;
import com.lx.drivingexam.helper.StaticBean;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

public class ShowActivity extends AbActivity implements OnGestureListener,ShowImpl{
	private LayoutInflater mInflater;
	private View mView;
	private GridView gv_menu;
	private GridView gv_listing;
	private QuestionFragment fragment;
	private List<ToolsBean> tList;
	private MyGridAdapter adapter;
	private GridAdapter listingAdapter;
	private DBManager dbm;
	private SQLiteDatabase db;
	private String numlist;
	private GestureDetector mGestureDetector;
	private MyApplication myApp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_show);
		myApp=(MyApplication)getApplication();
		initView();
		initPop();
		addTitleBar();
		initViewPager();
		initTools();
	}
	/**初始化控件*/
	public void initView(){
		myApp.submitFalg=false;
		dbm=new DBManager(this);//获得数据库帮助类
		dbm.openDatabase();//把数据库从资源文件拷贝到  data/data/包名/ 目录下
		db=dbm.getDatabase();
		mGestureDetector = new GestureDetector(this,this);
		mInflater=LayoutInflater.from(this);
		lp = getWindow().getAttributes();
	}
	/**初始化Fragment*/
	private void initViewPager(){
		fragment=new QuestionFragment();
		FragmentManager fm=getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.add(R.id.frame_show, fragment);
//		ft.setCustomAnimations(R.anim.left_in,R.anim.left_out); 
		ft.commit();
	}
	/**初始化工具栏*/
	public void initTools(){
		gv_menu=(GridView)this.findViewById(R.id.gv_menu);
		tList=new ArrayList<ToolsBean>();
		adapter=new MyGridAdapter(this, tList);
		if(myApp.PRACTISE_TYPE==2){
			gv_menu.setNumColumns(3);
			gv_menu.setOnItemClickListener(listener_mock_examination);
		}else{
			gv_menu.setNumColumns(4);
			gv_menu.setOnItemClickListener(listener);
		}
		gv_menu.setAdapter(adapter);
	}
	/**工具栏的项单击事件*/
	OnItemClickListener listener=new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			switch (position) {
			case 0:
				showListing(view);
				break;
			case 1:
				myApp.showDialog(ShowActivity.this,mInflater);
				break;
			case 2:
				int collect=myApp.bundle.getInt(StaticBean.IS_COLLECT);
				if(collect==0){
					myApp.bundle.putInt(StaticBean.IS_COLLECT,1);
					DBHelper.insertCollect(db, myApp.bundle);
				}else{
					int web_note_id=myApp.bundle.getInt(StaticBean.WEB_NOTE_ID);
					myApp.bundle.putInt(StaticBean.IS_COLLECT,0);
					DBHelper.deleteCollect(db, web_note_id+"");
				}
				changeToolsData();
				break;
			case 3:
				int exption=myApp.bundle.getInt(StaticBean.IS_SHOW_EXPTION);
				fragment.changeExplain(exption);
				break;
			default:
				break;
			}
		}
	};
	/**模拟考试工具栏的项单击事件*/
	OnItemClickListener listener_mock_examination=new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			switch (position) {
			case 0:
				showListing(view);
				break;
			case 1:
				int collect=myApp.bundle.getInt(StaticBean.IS_COLLECT);
				if(collect==0){
					myApp.bundle.putInt(StaticBean.IS_COLLECT,1);
					DBHelper.insertCollect(db, myApp.bundle);
				}else{
					int web_note_id=myApp.bundle.getInt(StaticBean.WEB_NOTE_ID);
					myApp.bundle.putInt(StaticBean.IS_COLLECT,0);
					DBHelper.deleteCollect(db, web_note_id+"");
				}
				changeToolsData();
				break;
			case 2:
				submit();
				break;
			default:
				break;
			}
		}
	};
	/**题目列表的项单击事件*/
	OnItemClickListener gv_listing_listener=new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			closeListing();
			fragment.goFragment(position);
		}
	};
	android.view.View.OnClickListener bt_listing_click=new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			closeListing();
		}
	};
	/**初始化工具栏数据*/
	private void initToolsData(){
		tList.clear();
		numlist = myApp.position+1+"/"+myApp.listSize;
		int collect=myApp.bundle.getInt(StaticBean.IS_COLLECT,0);
		int exption=myApp.bundle.getInt(StaticBean.IS_SHOW_EXPTION,0);
		ToolsBean tools1=new ToolsBean(R.drawable.tools_selector_numlist,numlist,0);
		ToolsBean tools3=new ToolsBean(StaticBean.TOOLS_COLLECT[collect],
				StaticBean.TOOLS_COLLECT_TEXT[collect],collect);
		tList.add(tools1);
		if(myApp.PRACTISE_TYPE==2){
			ToolsBean tools5=new ToolsBean(R.drawable.exam_submit_selector, "交卷", 0);
			tList.add(tools3);
			tList.add(tools5);
		}else{
			ToolsBean tools2=new ToolsBean(R.drawable.tools_selector_set_b,"设置",0);
			ToolsBean tools4=new ToolsBean(StaticBean.TOOLS_EXPTION[exption],
					StaticBean.TOOLS_EXPTION_TEXT[exption],exption);
			tList.add(tools2);
			tList.add(tools3);
			tList.add(tools4);
		}
		
	}
	/**重新加载工具栏*/
	@Override
	public void changeToolsData(){
		initToolsData();
		adapter.changeData(tList);
		adapter.notifyDataSetChanged();
	}
	/***
	 * 添加头部性息 TitleBar
	 */
	private void addTitleBar(){
		AbTitleBar mAbTitleBar = this.getTitleBar();
		String titleText2="顺序练习";
		String titleText1="科目一";
		int mm=45;
		if(myApp.KEMU==3){
			titleText1="科目四";
			mm=30;
		}
		switch (myApp.PRACTISE_TYPE) {
		case 0:
			titleText2="顺序练习";
			break;
		case 1:
			titleText2="随机练习";
			break;
		case 2:
			titleText2="模拟考试";
			break;
		case 3:
			titleText2="我的收藏";
			break;
		case 4:
			titleText1="我的错题";
			break;
		default:
			break;
		}
		mAbTitleBar.setTitleText(titleText2+"-"+titleText1);
		mAbTitleBar.setLogo(R.drawable.button_selector_back);
        mAbTitleBar.setTitleBarBackground(R.drawable.top_bg);
        mAbTitleBar.setTitleTextMargin(40, 0, 0, 0);
        mAbTitleBar.setLogoLine(R.drawable.line);
        mAbTitleBar.setTitleBarGravity(Gravity.CENTER, Gravity.CENTER);
    	if(myApp.PRACTISE_TYPE==2){
    		addTimer(mAbTitleBar,mm);
    	}else{
    		mAbTitleBar.clearRightView();
    	}
	}
	AdvancedCountdownTimer atime=null;
	/**在TitleBar中添加倒计时*/
	public void addTimer(AbTitleBar mAbTitleBar,int minutes){
		View rightViewApp = mInflater.inflate(R.layout.app_btn, null);
    	mAbTitleBar.addRightView(rightViewApp);
    	final TextView tv_bar_time = (TextView)rightViewApp.findViewById(R.id.tv_bar_time);
    	atime=new AdvancedCountdownTimer(minutes*60*1000, 1000) {
			@Override
			public void onTick(long millisUntilFinished, int percent) {
				// TODO Auto-generated method stub
				String ss=millisUntilFinished/1000%60+"";
				if(ss.length()==1){
					ss=0+ss;
				}
				String time=millisUntilFinished/1000/60+":"+ss;
				tv_bar_time.setText(time);
			}
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				int[] mocks=getSubmitCount();
				myApp.submitFalg=true;
				Intent intent=new Intent(ShowActivity.this,CountActivity.class);
				intent.putExtra("mock0", mocks[0]);
				intent.putExtra("mock1", mocks[1]);
				intent.putExtra("mock2", mocks[2]);
				startActivityForResult(intent, 200);
			}
		};
		atime.start();
	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		float fy=e1.getY()-e2.getY();
		if(fy>-350 && fy<350){
			if (e1.getX()-e2.getX() > 100) {  
		        // fling right
				if(myApp.position<myApp.listSize-1){
					fragment.nextFragment();
					return true;
				}
		    } else if (e2.getX() - e1.getX() > 100) {  
		        // fling left 
		    	if(myApp.position>0){
		    		fragment.previousFragment();
		    		numlist=myApp.position+1+"/"+myApp.listSize;
		    		adapter.notifyDataSetChanged();
		    		return true;
		    	}
		    }
		}
		
		return false;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return mGestureDetector.onTouchEvent(event);
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		mGestureDetector.onTouchEvent(ev);
		super.dispatchTouchEvent(ev);
		return false;
	}
	private PopupWindow mPopupWindow;
	TextView tv_listing=null;
	Button bt_listing=null;
	private void initPop(){
		//View popupView = getLayoutInflater().inflate(R.layout.dialog_custom_view, null);
		mView = mInflater.inflate(R.layout.dialog_custom_view,null);
		mPopupWindow = new PopupWindow(mView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopupWindow.getContentView().setFocusableInTouchMode(true);
        mPopupWindow.getContentView().setFocusable(true);
        mPopupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
        mPopupWindow.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				 lp.alpha = 1f;
				 getWindow().setAttributes(lp);	
			}
		});
		gv_listing=(GridView)mView.findViewById(R.id.gv_listing);
		listingAdapter=new GridAdapter(this,myApp);
		gv_listing.setAdapter(listingAdapter);
		if(bt_listing==null||tv_listing==null){
			bt_listing=(Button)mView.findViewById(R.id.bt_listing);
			tv_listing=(TextView)mView.findViewById(R.id.tv_listing);
		}
		numlist = myApp.position+1+"/"+myApp.listSize;
		bt_listing.setOnClickListener(bt_listing_click);
		tv_listing.setText(numlist);
		gv_listing.setOnItemClickListener(gv_listing_listener);
	}
	WindowManager.LayoutParams lp;
	/**显示题目列表*/
	public void showListing(View view){
		if (mPopupWindow != null && !mPopupWindow.isShowing()) {
			mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
			listingAdapter.changeData();
			listingAdapter.notifyDataSetChanged();
			lp.alpha = 0.5f;
			tv_listing.setText(numlist);
			getWindow().setAttributes(lp);
		}
	}
	
	/**提交答案*/
	private void submit(){
		int[] mocks=getSubmitCount();
		if(mocks[0]>0){
			showDialog(this,"提交试卷","还有"+mocks[0]+"道题未做\n点击取消查看未做题\n点击确定提交试卷",mocks);
		}else{
			myApp.submitFalg=true;
			Intent intent=new Intent(ShowActivity.this,CountActivity.class);
			intent.putExtra("mock0", mocks[0]);
			intent.putExtra("mock1", mocks[1]);
			intent.putExtra("mock2", mocks[2]);
			startActivityForResult(intent, 200);
		}
	}
	/**提交答案*/
	private void submitClose(){
		int[] mocks=getSubmitCount();
		if(mocks[0]>0){
			showDialog(this,"提交试卷","还有"+mocks[0]+"道题未做\n点击取消查看未做题\n点击确定提交试卷",mocks);
		}else{
			showDialog(this,"提交试卷","题目已经全部完成",mocks);
		}
	}
	private int[] getSubmitCount(){
		int[] mocks=new int[]{0,0,0};
		for (int i=0;i<myApp.listSize;i++) {
			switch (myApp.bList.get(i).getInt(StaticBean.IS_MOCK_EXAMINTION)) {
			case 0:
				mocks[0]++;
				break;
			case 1:
				mocks[1]++;
				break;
			case 2:
				mocks[2]++;
				break;
			default:
				break;
			}
		}
		return mocks;
	}
	private void showDialog(Context context,String titile,String body,final int[] mocks){
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
				AbDialogUtil.removeDialog(ShowActivity.this);
				showListing(new View(ShowActivity.this));
			}
		});
		//右边的按钮
		rightBtn1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				AbDialogUtil.removeDialog(ShowActivity.this);
				myApp.submitFalg=true;
				Intent intent=new Intent(ShowActivity.this,CountActivity.class);
				intent.putExtra("mock0", mocks[0]);
				intent.putExtra("mock1", mocks[1]);
				intent.putExtra("mock2", mocks[2]);
				startActivityForResult(intent, 200);
			}
		});
	}
	/**关闭题目列表*/
	public void closeListing(){
		if (mPopupWindow != null && mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
		}
	}
	public SQLiteDatabase getSQLiteDatabase(){
		return this.db;
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		dbm.closeDatabase();
		if(atime!=null){
			atime.cancel();
		}
		System.gc();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        //重写方法后，我们就调用父类的方法，这样以便系统的方法可以调用,这句一肯不能忘记
        super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
           //现在返回:true,代表让系统能继续处理此按键的操作
        	if(myApp.submitFalg==false&&myApp.PRACTISE_TYPE==2){
    			submitClose();
    			return false;
    		}else{
    			return true;
    		}
           //返回false:代表该按键的处理到此结束，不响应系统的处理
            
        }

        return super.onKeyDown(keyCode, event);
    }
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if(arg0==200){
			this.finish();
		}
	}

}
