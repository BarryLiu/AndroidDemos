package io.barryLiu.accountingmanager.fragment;

import io.barryLiu.accountingmanager.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuFragment extends Fragment{
	View view ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		  view = LayoutInflater.from(getActivity()).inflate(R.layout.menu, null);
		
		
		ImageView ivType = (ImageView) view.findViewById(R.id.iv_iconType);
	  	TextView tvName = (TextView) view.findViewById(R.id.tv_name);
	  	ivType.setImageResource(R.drawable.user);
	  	tvName.setText("第"+bundle.getInt("position")+"页");
	  	
	  	
		switch((bundle.getInt("position")+1)){
		case 1:
			setUser();
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		}
		
		return view;
	}

	private void setUser() {
		view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_main, null);
		 
	}
	
}
