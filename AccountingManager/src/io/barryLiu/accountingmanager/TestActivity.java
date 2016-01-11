package io.barryLiu.accountingmanager;

import io.barryLiu.accountingmanager.dao.AccountDao;
import io.barryLiu.accountingmanager.entity.AccountBean;

import java.util.List;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TestActivity extends Activity {
	private AccountDao aDao;
	private SQLiteDatabase db;
	private EditText et_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		DBHelper dbHelper = new DBHelper(TestActivity.this, "test2.db", null, 1);

		Toast.makeText(TestActivity.this, dbHelper.toString(),
				Toast.LENGTH_SHORT).show();
		;
		db = dbHelper.getWritableDatabase();
		aDao = new AccountDao(db);

		et_id = (EditText) findViewById(R.id.et_id);
	}

	public void btnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_createTable:
			createTable();
			break;
		case R.id.btn_add:
			add();
			break;
		case R.id.btn_modify:
			modify();
			break;
		case R.id.btn_remove:
			remove();
			break;
		case R.id.btn_query:
			query();
			break;
		}
	}

	private void query() {
		List<AccountBean> list = aDao.queryAll();
		for (AccountBean aBean : list) {
			Log.d("accountBean", aBean.toString());
		}
		Toast.makeText(TestActivity.this, "查询完成", Toast.LENGTH_SHORT).show();
	}

	private void remove() {

		String id = et_id.getText().toString();

		try {
			aDao.delete(Integer.parseInt(id));
			Toast.makeText(TestActivity.this, "删除成功", Toast.LENGTH_SHORT)
					.show();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			Toast.makeText(TestActivity.this, "删除失败", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void modify() {
		String id = et_id.getText().toString();
		try {
			AccountBean aBean = new AccountBean(Integer.parseInt(id), 1, 21.0,
					"修改了", "2016-1-4", "必须吃1");
			aDao.update(aBean);
			Toast.makeText(TestActivity.this, "修改成功", Toast.LENGTH_SHORT)
					.show();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			Toast.makeText(TestActivity.this, "修改失败", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void add() {
		AccountBean aBean = new AccountBean(null, 1, 20.0, "吃饭", "2016-1-3",
				"必须吃");
		aDao.insert(aBean);
		Toast.makeText(TestActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
	}

	private void createTable() {
		Toast.makeText(TestActivity.this, aDao.toString(), Toast.LENGTH_SHORT)
				.show();
	}

}
