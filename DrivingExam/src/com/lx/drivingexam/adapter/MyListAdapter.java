package com.lx.drivingexam.adapter;

import com.lx.drivingexam.R;
import com.lx.drivingexam.helper.StaticBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyListAdapter extends BaseAdapter{
	String selectId="";
	String[] answer;
	String[] ans=StaticBean.ANS1;
	LayoutInflater inflater;
	public MyListAdapter(String[] answer,Context context){
		inflater=LayoutInflater.from(context);
		this.answer=answer;
	}
	public void changeData(String[] answer,String selectId){
		this.answer=answer;
		this.selectId=selectId;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return answer.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return answer[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vh;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.list_an_item, null);
			vh=new ViewHolder();
			vh.iv_an=(ImageView)convertView.findViewById(R.id.iv_an);
			vh.tv_an=(TextView)convertView.findViewById(R.id.tv_an);
			vh.tv_answer=(TextView)convertView.findViewById(R.id.tv_answer);
			convertView.setTag(vh);
		}else{
			vh=(ViewHolder)convertView.getTag();
		}
		if(selectId.contains(position+1+"")){
			convertView.setBackgroundResource(R.drawable.list_item1);
			vh.iv_an.setImageResource(R.drawable.yuan_n);
		}else{
			convertView.setBackgroundResource(R.drawable.list_item_selector);
			vh.iv_an.setImageResource(R.drawable.yuan_s);
		}
		vh.tv_answer.setText(answer[position]);
		vh.tv_an.setText(ans[position]);
		return convertView;
	}
	class ViewHolder{
		TextView tv_answer;
		TextView tv_an;
		ImageView iv_an;
	}
}
