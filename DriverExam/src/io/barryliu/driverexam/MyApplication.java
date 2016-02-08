package io.barryliu.driverexam;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.ab.util.AbDialogUtil;

public class MyApplication extends Application {
	public List<Bundle> bList=new ArrayList<Bundle>();
	 /**��ǰ��Ŀ����*/
   public Bundle bundle;
   /**��ǰ��Ŀ���*/
   public int position=0;
   /**��Ŀ����*/
   public int listSize=0;
   /**collect_id: �Ƿ��ղ� 0û���ղ�,1�ղ�
    * exption_id: �Ƿ���ʾ��0����ʾ,1��ʾ
    * **/
   public int collect_id=0,exption_id=0;
   /**��ϰ����0:˳����ϰ,1:�����ϰ,2:ģ�⿼��,3:�ղ�,4:����**/
   public int PRACTISE_TYPE=0;
   /**�Ƿ���ʾ ����1*/
   public boolean is_static_exption =true;
   /**�Ƿ���ʾ ����2*/
   public boolean is_exption =true;
   /**�Ƿ���ʾ ��ת��һҳ*/
   public boolean is_next=true;
   public boolean is_static_judge =true;
   /**��Ŀ0:��Ŀһ,1:��Ŀ����:��Ŀ��,3:��Ŀ��**/ 
   public int KEMU=0;
   /**�Ƿ��ύ���Ծ�*/
   public boolean submitFalg=false;
   public boolean falg0;
   public boolean falg1;
   public void showDialog(final Context context,LayoutInflater inflater){
		View aView=initDialogView(inflater);
		AbDialogUtil.showDialog(aView);
		Button rightBtn1 = (Button)aView.findViewById(R.id.right_btn_ok);
		//�ұߵİ�ť
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
		//�Ƿ��Զ���ת��һ��
		final ImageView iv_table_off=(ImageView)aView.findViewById(R.id.iv_table_off);
		//�Ƿ�Ĭ��չ����ʾ
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
