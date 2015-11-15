package com.example.demosxt2;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	private ListView mLvGeneral;
	private List<GeneralBean> mGenerals;
	private GeneralAdapter mAdapter;
	
	int[] resid={
		R.drawable.baiqi,
		R.drawable.caocao,
		R.drawable.chengjisihan,
		R.drawable.hanxin, 
		R.drawable.lishimin,
		R.drawable.nuerhachi,
		R.drawable.sunbin,
		R.drawable.sunwu,
		R.drawable.yuefei,
		R.drawable.zhuyuanzhang
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initData();
		initView();
		
	}

	
	private void initView() {
		mLvGeneral = (ListView) findViewById(R.id.lvGeneral);
		mAdapter = new GeneralAdapter();// 创建适配器
		mLvGeneral.setAdapter(mAdapter);
		
	}


	private void initData() {
		//将资源中的数组转为java 数组
		String [] names= getResources().getStringArray(R.array.gerenls);
		
		mGenerals = new ArrayList<GeneralBean>();
		for (int i = 0; i < names.length; i++) {
			GeneralBean bean = new GeneralBean(resid[i],names[i]);
			mGenerals.add(bean);
		}
		
	}


	//代表适配器的数组
	class GeneralAdapter extends BaseAdapter{
   
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mGenerals.size();
		}

		@Override
		public Object getItem(int position) {
			return mGenerals.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			//拿到listviewitem 的布局， 转换成view 类型的对象
			View layout = View.inflate(MainActivity.this, R.layout.item_generals, null);
			//找到ImageView 
			ImageView ivThumb = (ImageView) layout.findViewById(R.id.ivThumb);
			//找到TextView
			TextView tvName = (TextView) layout.findViewById(R.id.tvName);
			GeneralBean bean = mGenerals.get(position);
			ivThumb.setImageResource(bean.getResid());
			tvName.setText(bean.getName());
			return layout;
		}
		
	}
 
}
