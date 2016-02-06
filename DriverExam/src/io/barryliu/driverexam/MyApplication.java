package io.barryliu.driverexam;

import java.util.ArrayList;
import java.util.List;

import com.ab.util.AbDialogUtil;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MyApplication extends Application {
	public List<Bundle> bList = new ArrayList<Bundle>();

	public Bundle bundle;
	public int position = 0;
	public int listSize = 0;

	public int collect_id = 0, exption_id = 0;

	public int PRACTISE_TYPE = 0;

	public boolean is_static_exption = true;
	public boolean is_exption = true;
	public boolean is_next = true;
	public boolean is_static_judge = true;
	public int KEMU = 0;

	public boolean submitFalg = false;
	public boolean falg0;
	public boolean falg1;

	public void showDialog(final Context context, LayoutInflater inflater) {
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
		falg0 = is_next;
		falg1 = is_exption;
		View aView = inflater.inflate(R.layout.dialog_text_button1, null);

		final ImageView iv_table_off = (ImageView) aView
				.findViewById(R.id.iv_table_off);

		final ImageView iv_table_off1 = (ImageView) aView
				.findViewById(R.id.iv_table_off1);

		if (falg0) {
			iv_table_off.setImageResource(R.drawable.table_on);
		} else {
			iv_table_off.setImageResource(R.drawable.table_off);
		}
		if (falg1) {
			iv_table_off1.setImageResource(R.drawable.table_on);
		} else {
			iv_table_off1.setImageResource(R.drawable.table_off);
		}

		iv_table_off.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (falg0) {
					iv_table_off.setImageResource(R.drawable.table_off);
					falg0 = false;
				} else {
					iv_table_off.setImageResource(R.drawable.table_on);
					falg0 = true;
				}
			}
		});
		iv_table_off1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (falg1) {
					iv_table_off1.setImageResource(R.drawable.table_off);
					falg1 = false;
				} else {
					iv_table_off1.setImageResource(R.drawable.table_on);
					falg1 = true;
				}
			}
		});
		return aView;
	}
}
