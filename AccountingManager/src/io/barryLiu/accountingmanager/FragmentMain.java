package io.barryLiu.accountingmanager;

import io.barryLiu.accountingmanager.fragment.MenuFragment;
import io.barryLiu.accountingmanager.utils.ResUtils;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class FragmentMain extends FragmentActivity{
	FragmentTabHost tabHost;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.fragment_main);
		

		tabHost =(FragmentTabHost) findViewById(R.id.fth_main);
		tabHost.setup(getApplication(), getSupportFragmentManager(), R.id.fl_main);
		
		for (int i = 0; i < 5; i++) {
			TabSpec tab = tabHost.newTabSpec(i+"");
			
			View view = LayoutInflater.from(this).inflate(R.layout.menu, null);

		  	ImageView ivType = (ImageView) view.findViewById(R.id.iv_iconType);
		  	TextView tvName = (TextView) view.findViewById(R.id.tv_name);
		  	ivType.setImageResource(ResUtils.menuIds[i]);
		  	tvName.setText(ResUtils.menuStrs[i]);
			tab.setIndicator(view);
			
			Bundle b = new Bundle();
			b.putInt("position", i);
			tabHost.addTab(tab, MenuFragment.class, b);
 		}
		
	}
	
}
