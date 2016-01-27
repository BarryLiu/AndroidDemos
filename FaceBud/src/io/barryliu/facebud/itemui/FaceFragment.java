package io.barryliu.facebud.itemui;

import io.barryliu.facebud.lisenter.MyItemLisenter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FaceFragment extends Fragment{
	int []res;
	int type ;
	boolean gender ;
	
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
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	
	
}
