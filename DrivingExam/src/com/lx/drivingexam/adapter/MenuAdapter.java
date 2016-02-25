package com.lx.drivingexam.adapter;

import java.util.ArrayList;
import java.util.List;

import com.lx.drivingexam.R;
import com.lx.drivingexam.bean.MenuBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter{
	int selectPosition=0;
	int[] imgs=new int[]{
			R.drawable.home_km1,R.drawable.home_km2,
			R.drawable.home_km3,R.drawable.home_km4,
			R.drawable.lianxi_del,R.drawable.home_set};
	List<MenuBean> mList;
	LayoutInflater inflater;
	public void changeselectPosition(int selectPosition){
		this.selectPosition=selectPosition;
		this.notifyDataSetChanged();
	}
	public MenuAdapter(Context context){
		inflater=LayoutInflater.from(context);
		mList=new ArrayList<MenuBean>();
		mList.add(new MenuBean(imgs[0],"科目一","(理论考)"));
		mList.add(new MenuBean(imgs[1],"科目二","(小路考)"));
		mList.add(new MenuBean(imgs[2],"科目三","(大路考)"));
		mList.add(new MenuBean(imgs[3],"科目四","(理论考)"));
		mList.add(new MenuBean(imgs[4],"清空记录","(慎重)"));
		mList.add(new MenuBean(imgs[5],"设置",""));
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
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
		MenuBean mb=mList.get(position);
		if(convertView==null){
			convertView=inflater.inflate(R.layout.list_menu_item, null);
			vh=new ViewHolder();
			vh.iv_menu=(ImageView)convertView.findViewById(R.id.iv_menu);
			vh.iv_menu_select=(ImageView)convertView.findViewById(R.id.iv_menu_select);
			vh.tv_menu_name=(TextView)convertView.findViewById(R.id.tv_menu_name);
			vh.tv_menu_text=(TextView)convertView.findViewById(R.id.tv_menu_text);
			convertView.setTag(vh);
		}else{
			vh=(ViewHolder)convertView.getTag();
		}
		vh.iv_menu.setImageResource(mb.getId());
		if(selectPosition==position){
			vh.iv_menu_select.setImageResource(R.drawable.menu_select_true);
		}else{
			vh.iv_menu_select.setImageResource(R.drawable.ment_select_false);
		}
		vh.tv_menu_name.setText(mb.getKemu_name());
		vh.tv_menu_text.setText(mb.getKemu_text());
		return convertView;
	}
	class ViewHolder{
		ImageView iv_menu;
		ImageView iv_menu_select;
		TextView tv_menu_name;
		TextView tv_menu_text;
	}

}
