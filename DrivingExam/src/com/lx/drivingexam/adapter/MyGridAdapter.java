package com.lx.drivingexam.adapter;

import java.util.List;

import com.lx.drivingexam.R;
import com.lx.drivingexam.bean.ToolsBean;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyGridAdapter extends BaseAdapter{
	
	List<ToolsBean> tList;
	LayoutInflater inflater;
	Context mContext;
	public MyGridAdapter(Context context,List<ToolsBean> tList){
		inflater=LayoutInflater.from(context);
		mContext = context;
		this.tList=tList;
	}
	public void changeData(List<ToolsBean> tList){
		this.tList=tList;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return tList.get(position);
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
		ToolsBean tools=tList.get(position);
		if(convertView==null){
			convertView=inflater.inflate(R.layout.tools_item, null);
			vh=new ViewHolder();
			vh.tools_iv_image=(ImageView)convertView.findViewById(R.id.tools_iv_image);
			vh.tools_tv_name=(TextView)convertView.findViewById(R.id.tools_tv_name);
			convertView.setTag(vh);
		}else{
			vh=(ViewHolder)convertView.getTag();
		}
		if(tools.getTools_falg()==0){
			vh.tools_tv_name.setTextColor(Color.BLACK);
		}else{
			vh.tools_tv_name.setTextColor(mContext.getResources().getColor(R.color.green_dark1));
		}
		vh.tools_tv_name.setText(tools.getTools_text());
		vh.tools_iv_image.setImageResource(tools.getTools_id());
		return convertView;
	}
	class ViewHolder{
		TextView tools_tv_name;
		ImageView tools_iv_image;
	}


}
