package io.barryliu.facebud.itemui;

import io.barryliu.facebud.R;
import io.barryliu.facebud.lisenter.MyItemLisenter;
import io.barryliu.facebud.res.MyRes;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class FaceFragment extends Fragment {
	int[] res;
	int type;
	boolean gender;

	MyItemLisenter onMyItemLisenter;

	public MyItemLisenter getOnMyItemLisenter() {
		return onMyItemLisenter;
	}

	public void setOnMyItemLisenter(MyItemLisenter onMyItemLisenter) {
		this.onMyItemLisenter = onMyItemLisenter;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Bundle bundle = getArguments();
		type = bundle.getInt(MyRes.TAG_TYPE);
		gender = bundle.getBoolean(MyRes.TAG_ISBOY);

		switch (type) {
		case 0:
			if (gender) {
				// boy 的发现图片地址集合
				res = MyRes.getBoyHair();
			} else {
				res = MyRes.getGirlHair();
			}
			break;
		case 1: 
			res = MyRes.getFaceShape();
			break;
		case 2:
			res = MyRes.getEyeBrow();
			break;
		case 3:
			res = MyRes.getEye();
			break;
		case 4:
			res = MyRes.getMouth();
			break;
		case 5:
			if(gender){
				res = MyRes.getFeature();
			}else{
				res = MyRes.getGirlFeature();
			}
			break;
		case 6:
			res = MyRes.getGlass();
			break;
		case 7:
			if(gender){
				res = MyRes.getBoyClothes();
			}else{
				res = MyRes.getGirlClothes();
			}
			break;
		case 8:
			res = MyRes.getHat();
			break;
		case 9:
			res = MyRes.getHobby();
			break;
		case 10:
			res = MyRes.getBackGround();
			break;
		case 11:
			res = MyRes.getPop();
			break;
		}

		
		GridView gridView =new GridView(getActivity());
		gridView.setNumColumns(3);
		gridView.setAdapter(new MyAdapter());
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				int b_res;
				if(type == 0){
					if(gender){
						b_res = MyRes.getRealBoyHair()[position];
					}else {
						b_res = MyRes.getRealGirlHair()[position];
					}
				}else if(type ==5){
					if(gender){
						b_res = MyRes.getRealBoyFeature()[position];
					}else{
						b_res = MyRes.getRealGirlFeature()[position];
					}
				}else {
					b_res = res[position];
				}
				if(b_res == R.drawable.show_no){
					b_res = R.drawable.show_nothing;
				}
				
				onMyItemLisenter.myItemClick(b_res, type);
			}
		});
		
		return gridView;
	}
	
	private class MyAdapter extends BaseAdapter{
		@Override
		public int getCount() {
			return res.length;
		}
		@Override
		public Object getItem(int position) {
			return res[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView =new ImageView(getActivity());
			imageView.setMinimumWidth(120);
			imageView.setMinimumHeight(120);
			
			imageView.setBackgroundResource(R.drawable.image_bg);
			
			imageView.setImageResource(res[position]);
			return imageView;
		}
		
		
	}
}
