package io.barryliu.facebud.itemui;

import io.barryliu.facebud.MainActivity;
import io.barryliu.facebud.lisenter.MyItemLisenter;
import io.barryliu.facebud.res.MyRes;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FacePageAdapter extends FragmentPagerAdapter{
	MyItemLisenter mLisenter;
	
	public FacePageAdapter(FragmentManager fm,MyItemLisenter lisenter) {
		super(fm);
		this.mLisenter = lisenter;
	}

	@Override
	public Fragment getItem(int arg0) {
		FaceFragment fragment = new FaceFragment();
		Bundle args =new Bundle();
		args.putInt(MyRes.TAG_TYPE, arg0);
		args.putBoolean(MyRes.TAG_ISBOY, MainActivity.isBoy);
		fragment.setArguments(args);
		
		fragment.setOnMyItemLisenter(mLisenter);
		return fragment;
	}

	@Override
	public int getCount() {
		
		return 12;
	}
	
	 

}
