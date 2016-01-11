package com.example.tuling;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * 聊天功能  <br>5分钟不聊天再聊显示时间
 * <br>
 * 解决 空格,回车数据传输问题
 * @author Barry
 *
 */
public class MainActivity extends Activity implements HttpGetDateLisenter ,OnClickListener {
	/**/
	private HttpData httpData;
	
	private ListView lv;
	private EditText sendtext;
	private Button send_btn;
	private TextAdapter adapter;
	//聊天内容
	private List<ListData> lists;
	//欢迎语数组
	private String[]welcome_array;
	
	private double currTime=0,oldTime=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		

		initView();
	}
	private void initView(){
		lists = new ArrayList<ListData>();
		
		lv = (ListView) findViewById(R.id.lv);
		sendtext = (EditText) findViewById(R.id.sendText);
		send_btn = (Button) findViewById(R.id.send_btn);
		lists = new ArrayList<ListData>();
		send_btn.setOnClickListener(this);
		adapter = new TextAdapter(lists, this);
		lv.setAdapter(adapter);
		ListData listData;
		
 		 listData = new ListData(ListData.RECEIVER, 
 				getTime(),getRandomWelcomeTips());
 		lists.add(listData);
	}
	private String getTime(){
 	currTime = System.currentTimeMillis();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		Date curDate =new Date();
		String str=sdf.format(curDate);
		if(currTime-oldTime>=5*60*1000){
			oldTime = currTime;
			return str;
		}else{
			return "";
		}
	}
	@Override
	public void getDataUrl(String data) {
		//System.out.println(data);
		parseText(data);
	}
	
	public void parseText(String str){
		try {
			JSONObject jb=new JSONObject(str);
			System.out.println(jb.getString("code"));
			System.out.println(jb.getString("text"));
			ListData listData  = new ListData(ListData.RECEIVER,getTime(),jb.getString("text"));
			lists.add(listData);
			
			adapter.notifyDataSetChanged();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	private String getRandomWelcomeTips(){
		String welcome_tip=null;
		welcome_array = this.getResources().getStringArray(R.array.welcome_tips);
		//随机得到数组范围内的下标
		int index = (int) (Math.random()*(welcome_array.length-1));
		welcome_tip = welcome_array[index];
		return welcome_tip;
	}
	@Override
	public void onClick(View v) {
		String sendStr = sendtext.getText().toString();
		sendtext.setText("");
		
		ListData listData ;
		listData = new ListData(ListData.SEND, getTime(), sendStr);
		lists.add(listData);
		
		if(lists.size()>20){
			for (int i = 0; i < lists.size(); i++) {
				
			}
		}
		//去掉空格和回车
				String dropk = sendStr.replace(" ", "");
				String droph = dropk.replace("\n", "");
		httpData =(HttpData) new HttpData("http://www.tuling123.com/openapi/api?key=d5884be4e306aa365b7aa82308ddec88&info="+droph, this).execute();
	}
	 
}
