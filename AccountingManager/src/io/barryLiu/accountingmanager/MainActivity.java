package io.barryLiu.accountingmanager;

import io.barryLiu.accountingmanager.adapter.MyAdapter;
import io.barryLiu.accountingmanager.dao.AccountDao;
import io.barryLiu.accountingmanager.entity.AccountBean;
import io.barryLiu.accountingmanager.utils.MyPickerDialog;
import io.barryLiu.accountingmanager.utils.ResUtils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private SQLiteDatabase db;//访问数据
	private AccountDao aDao;  
	private ListView lv_account;
	private Calendar calendar;
	private TextView tv_shouru, tv_zhichu, tv_yuE, tv_curYearMonth;
	private List<AccountBean> list;
	private Button btn_jiyibi;
	 private GridView gv_menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());

		DBHelper dbHelper = new DBHelper(MainActivity.this, "accounts.db",
				null, 1);
		db = dbHelper.getWritableDatabase();
		aDao = new AccountDao(db);

		initView();
		list = aDao.queryAll();
		setAdapter();
		jisuan();
		setLisenter();
	}

	private void jisuan() {
		Double shouru = 0.0, zhichu = 0.0, yue = 0.0;
		for (AccountBean aBean : list) {
			if (aBean.getPayType() == 1)
				shouru += aBean.getMoney();
			else if (aBean.getPayType() == 0)
				zhichu += aBean.getMoney();
		}
		yue = shouru - zhichu;
		tv_shouru.setText(shouru + "");
		tv_zhichu.setText(zhichu + "");
		tv_yuE.setText(yue + "");
	}

	private void initView() {
		lv_account = (ListView) findViewById(R.id.lv_account);
		tv_shouru = (TextView) findViewById(R.id.tv_shouru);
		tv_zhichu = (TextView) findViewById(R.id.tv_zhichu);
		tv_yuE = (TextView) findViewById(R.id.tv_yuE);
		tv_curYearMonth = (TextView) findViewById(R.id.tv_curYearMonth);
		btn_jiyibi = (Button) findViewById(R.id.btn_jiyibi);
		gv_menu =(GridView) findViewById(R.id.gv_menu);
	}

	/**
	 * 设置监听事件
	 */
	private void setLisenter() {
		setLvLongClickLisenter();
		
	}

	private void setLvLongClickLisenter() {
		lv_account.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> view, View parent,
					int position, long id) {
				final MyAdapter adapter = (MyAdapter) view.getAdapter();
				final AccountBean aBean = (AccountBean) adapter
						.getItem(position);

				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setTitle("操作");
				builder.setItems(new String[] { "删除" }, new OnClickListener() {

					@Override
					public void onClick(DialogInterface d, int id) {

						Log.d("accountbean", aBean.toString());
						aDao.delete(aBean.get_id());

						list = aDao.queryAll();
						setAdapter();
						jisuan();
					}
				}).show();
				return true;
			}
		});
	}

	private void setAdapter() {

		MyAdapter adapter = new MyAdapter(MainActivity.this, list);
		lv_account.setAdapter(adapter);
		
		
		
		List<Map<String, Object>> menusData = ResUtils.getMenusData();
		for (Map<String, Object> map : menusData) {
			Log.d("debbbbb", map.toString());
		}
 		SimpleAdapter gvAdapter =new SimpleAdapter(MainActivity.this, ResUtils.getMenusData(), R.layout.gv_item, 
  				new String[]{"name","icon"},
  				new int[]{R.id.tv_name,R.id.iv_iconType});
 		gv_menu.setAdapter(gvAdapter);
		
	}

	private View v2;
	private Button btn_data;
	private Button btn_type;
	private static String arr[] = ResUtils.payTypeZhichu;// 当前选择收入或支出

	public void tojiyibi(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("记一笔");
		initViewv2();
		builder.setView(v2);
		builder.setPositiveButton("退出", null);
		builder.setNegativeButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				RadioGroup rg_payType = (RadioGroup) v2
						.findViewById(R.id.rg_payType);
				int payType = 0;
				switch (rg_payType.getCheckedRadioButtonId()) {
				case R.id.btn_zhichu:
					payType = 0;
					break;
				case R.id.btn_shouru:
					payType = 1;
					break;
				}
				EditText et_money = (EditText) v2.findViewById(R.id.et_money);
				String moneyStr = et_money.getText().toString();
				String type = btn_type.getText().toString();
				String data = btn_data.getText().toString();
				String comment = ((EditText) v2.findViewById(R.id.et_comment))
						.getText().toString();
				if (moneyStr.isEmpty()) {
					et_money.setError("金额不能为空");
					showTips("金额不能为空插入失败");
					return;
				}
				Double money = 0.0;
				try {
					money = Double.valueOf(moneyStr);
					AccountBean aBean = new AccountBean(null, payType, money, type,
							data, comment);
					aDao.insert(aBean);
					Toast.makeText(MainActivity.this, "记账成功", Toast.LENGTH_SHORT)
							.show();
					list = aDao.selectByData(tv_curYearMonth.getText().toString());
					setAdapter();
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		});
		builder.show();
	}

	// 找到记一笔中的控件
	private void initViewv2() {
		v2 = LayoutInflater.from(MainActivity.this).inflate(
				R.layout.layout_jiyibi, null);
		btn_type = (Button) v2.findViewById(R.id.btn_type);
		btn_data = (Button) v2.findViewById(R.id.btn_data);

		btn_type.setText(arr[0]);
		btn_data.setText(calendar.get(Calendar.YEAR) + "年"
				+ (calendar.get(Calendar.MONTH) + 1) + "月"
				+ calendar.get(Calendar.DAY_OF_MONTH) + "日");
	}

	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.btn_type:
			setBtn_typeClickListener();
			break;
		case R.id.btn_data:
			setBtn_dataClickListener(view);
			break;
		case R.id.btn_chaxun:
			setBtn_chaxunClickListener(view);
			break;
		case R.id.btn_shouru:
			arr = ResUtils.payTypeShouru;
			btn_type.setText(arr[0]);
			break;
		case R.id.btn_zhichu:
			arr = ResUtils.payTypeZhichu;
			btn_type.setText(arr[0]);
			break;
		}

	}

	public void showTips(String text) {
		Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
	}

	private void setBtn_chaxunClickListener(View view) {
		DatePickerDialog dpd = new MyPickerDialog(MainActivity.this,
				new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker arg0, int year, int month,
							int day) {
						String data = year + "年" + (month + 1) + "月";
						tv_curYearMonth.setText(data);

						list = aDao.selectByData(data);
						setAdapter();
						jisuan();
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		dpd.show();

	}

	private void setBtn_typeClickListener() {

		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("请选择类型");

		builder.setItems(arr, new OnClickListener() {
			@Override
			public void onClick(DialogInterface face, int witch) {
				btn_type.setText(arr[witch]);
			}
		}).show();
	}

	private void setBtn_dataClickListener(View view) {
		DatePickerDialog dpd = new DatePickerDialog(MainActivity.this,
				new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker p, int year, int month,
							int day) {
						btn_data.setText(year + "年" + (month + 1) + "月" + day
								+ "日");
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		dpd.show();
	}
}
