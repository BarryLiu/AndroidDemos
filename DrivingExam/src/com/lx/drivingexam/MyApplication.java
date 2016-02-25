package com.lx.drivingexam;

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

public class MyApplication extends Application{
	public List<Bundle> bList=new ArrayList<Bundle>();
	 /**当前题目数据*/
    public Bundle bundle;
    /**当前题目题号*/
    public int position=0;
    /**题目总数*/
    public int listSize=0;
    /**collect_id: 是否收藏 0没有收藏,1收藏
     * exption_id: 是否显示答案0不显示,1显示
     * **/
    public int collect_id=0,exption_id=0;
    /**练习类型0:顺序练习,1:随机练习,2:模拟考试,3:收藏,4:错题**/
    public int PRACTISE_TYPE=0;
    /**是否显示 解释1*/
    public boolean is_static_exption =true;
    /**是否显示 解释2*/
    public boolean is_exption =true;
    /**是否显示 跳转下一页*/
    public boolean is_next=true;
    public boolean is_static_judge =true;
    /**科目0:科目一,1:科目二，:科目三,3:科目四**/ 
    public int KEMU=0;
    /**是否提交了试卷*/
    public boolean submitFalg=false;
    public boolean falg0;
    public boolean falg1;
    public void showDialog(final Context context,LayoutInflater inflater){
		View aView=initDialogView(inflater);
		AbDialogUtil.showDialog(aView);
		Button rightBtn1 = (Button)aView.findViewById(R.id.right_btn_ok);
		//右边的按钮
		rightBtn1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				AbDialogUtil.removeDialog(context);
				is_next=falg0;
				is_exption=falg1;
			}
		});
	}
	public View initDialogView(LayoutInflater inflater){
		falg0=is_next;
		falg1=is_exption;
		View aView = inflater.inflate(R.layout.dialog_text_button1,null);
		//是否自动跳转下一题
		final ImageView iv_table_off=(ImageView)aView.findViewById(R.id.iv_table_off);
		//是否默认展开提示
		final ImageView iv_table_off1=(ImageView)aView.findViewById(R.id.iv_table_off1);
		if(falg0){
			iv_table_off.setImageResource(R.drawable.table_on);
		}else{
			iv_table_off.setImageResource(R.drawable.table_off);
		}
		if(falg1){
			iv_table_off1.setImageResource(R.drawable.table_on);
		}else{
			iv_table_off1.setImageResource(R.drawable.table_off);
		}
		iv_table_off.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(falg0){
					iv_table_off.setImageResource(R.drawable.table_off);
					falg0=false;
				}else{
					iv_table_off.setImageResource(R.drawable.table_on);
					falg0=true;
				}
			}
		});
		iv_table_off1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(falg1){
					iv_table_off1.setImageResource(R.drawable.table_off);
					falg1=false;
				}else{
					iv_table_off1.setImageResource(R.drawable.table_on);
					falg1=true;
				}
			}
		});
		return aView;
	}
}
