package com.example.tuling;

import java.util.ArrayList;
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

public class MainActivity extends Activity implements HttpGetDateLisenter ,OnClickListener {
	private HttpData httpData;
	
	private ListView lv;
	private EditText sendtext;
	private Button send_btn;
	private String content_str;
	private TextAdapter adapter;
	
	private List<ListData> lists;
	
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
		
//		 listData = new ListData(getRandomWelcomeTips(), ListData.RECEIVER,
//		  		getTime());
//		lists.add(listData);
	}

	@Override
	public void getDataUrl(String data) {
 	System.out.println(data);
		parseText(data);
	}
	
	public void parseText(String str){
		try {
			JSONObject jb=new JSONObject(str);
			System.out.println(jb.getString("code"));
			System.out.println(jb.getString("text"));
			ListData listData  = new ListData(ListData.RECEIVER,"2015-1-9",jb.getString("text"));
			lists.add(listData);
			
			adapter.notifyDataSetChanged();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void onClick(View v) {
		String sendStr = sendtext.getText().toString();
		sendtext.setText("");
		ListData listData ;
		listData = new ListData(ListData.SEND, "2016-1-9", sendStr);
		lists.add(listData);

		httpData =(HttpData) new HttpData("http://www.tuling123.com/openapi/api?key=d5884be4e306aa365b7aa82308ddec88&info="+sendStr, this).execute();
	}
	 
}
