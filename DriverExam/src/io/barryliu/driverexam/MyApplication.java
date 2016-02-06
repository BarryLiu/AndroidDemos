package io.barryliu.driverexam;

import java.util.ArrayList;
import java.util.List;

import com.ab.util.AbDialogUtil;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class MyApplication extends Application{
	public List<Bundle> bList = new ArrayList<Bundle>();
	 
	public Bundle bundle;
	public int position = 0;
	public int listSize = 0;
	
	public int collect_id =0 , exption_id=0;
	
	public int PRACTISE_TYPE = 0;
	
	public boolean is_static_exption = true;
	public boolean is_exption = true;
	public boolean is_next = true;
	public boolean is_static_judge=true;
	public int KEMU=0;
	
	
	public boolean submitFalg=false;
	public boolean falg0;
	public boolean falg1;
	public void showDialog(final Context context , LayoutInflater inflater) {
		View aView = initDialogView(inflater);
		AbDialogUtil.showDialog(aView);
		Button rightBtn1 = (Button) aView.findViewById(R.id.right_btn_ok);
		rightBtn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AbDialogUtil.removeDialog(context);
				is_next = falg0;
				is_exption = falg1;
			}
		});
		
	}
	private View initDialogView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		return null;
	}	
}
