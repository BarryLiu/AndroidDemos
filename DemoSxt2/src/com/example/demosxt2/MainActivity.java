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
		mAdapter = new GeneralAdapter();// ����������
		mLvGeneral.setAdapter(mAdapter);
		
	}


	private void initData() {
		//����Դ�е�����תΪjava ����
		String [] names= getResources().getStringArray(R.array.gerenls);
		
		mGenerals = new ArrayList<GeneralBean>();
		for (int i = 0; i < names.length; i++) {
			GeneralBean bean = new GeneralBean(resid[i],names[i]);
			mGenerals.add(bean);
		}
		
	}


	//����������������
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
			//�õ�listviewitem �Ĳ��֣� ת����view ���͵Ķ���
			View layout = View.inflate(MainActivity.this, R.layout.item_generals, null);
			//�ҵ�ImageView 
			ImageView ivThumb = (ImageView) layout.findViewById(R.id.ivThumb);
			//�ҵ�TextView
			TextView tvName = (TextView) layout.findViewById(R.id.tvName);
			GeneralBean bean = mGenerals.get(position);
			ivThumb.setImageResource(bean.getResid());
			tvName.setText(bean.getName());
			return layout;
		}
		
	}
 
}
