package com.lx.drivingexam.adapter;

import java.util.List;

import com.lx.drivingexam.MyApplication;
import com.lx.drivingexam.R;
import com.lx.drivingexam.helper.StaticBean;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter{
	List<Bundle> bList;
	LayoutInflater inflater;
	Context mContext;
	TextView listing_name;
	private MyApplication myApp;
	public GridAdapter(Context context,MyApplication myApp){
		inflater=LayoutInflater.from(context);
		mContext = context;
		this.myApp=myApp;
		this.bList=myApp.bList;
	}
	public void changeData(){
		this.bList=myApp.bList;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return myApp.listSize;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return bList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Bundle bundle=bList.get(position);
		if(convertView==null){
			convertView=inflater.inflate(R.layout.gv_listing_item, null);
			listing_name=(TextView)convertView.findViewById(R.id.tv_listing_name);
			convertView.setTag(listing_name);
		}else{
			listing_name=(TextView)convertView.getTag();
		}
		if(myApp.PRACTISE_TYPE==2){
			int is_answer=bundle.getInt(StaticBean.IS_MOCK_EXAMINTION);
			switch (is_answer) {
			case 0:
				convertView.setBackgroundResource(R.drawable.shape_isting0);
				break;
			default:
				convertView.setBackgroundResource(R.drawable.shape_isting_true);
				break;
			}
		}else{
			int stocks=bundle.getInt(StaticBean.STOCKS_TYPE);
			switch (stocks) {
			case 0:
				convertView.setBackgroundResource(R.drawable.shape_isting0);
				break;
			case 1:
				convertView.setBackgroundResource(R.drawable.shape_isting1);
				break;
			default:
				convertView.setBackgroundResource(R.drawable.shape_isting2);
				break;
			}
		}
		
		listing_name.setText(position+1+"");
		return convertView;
	}

}
