package io.barryLiu.accountingmanager.dao;

import io.barryLiu.accountingmanager.entity.AccountBean;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AccountDao {

	private SQLiteDatabase db;

	private static final String TABLE_NAME = "account";
	private static final String COL_ID = "_id";
	private static final String COL_PAYTYPE = "payType";
	public static final String COL_MONEY = "money";
	public static final String COL_TYPE = "type";
	public static final String COL_DATA = "data";
	public static final String COL_COMMENT = "comment";

	public AccountDao(SQLiteDatabase db) {
		this.db = db;
	}

	public List<AccountBean> queryAll() {
		List<AccountBean> lists = new ArrayList<AccountBean>();

		Cursor cursor = db.rawQuery(" select * from " + TABLE_NAME, null);
		while (cursor.moveToNext()) {
			Integer id = cursor.getInt(0);
			Integer payType = cursor.getInt(1);
			Double money = cursor.getDouble(2);
			String type = cursor.getString(3);
			String data = cursor.getString(4);
			String comment = cursor.getString(5);

			AccountBean aBean = new AccountBean(id, payType, money, type, data,
					comment);
			lists.add(aBean);
		}
		return lists;
	}

	public void insert(AccountBean aBean) {
		db.execSQL(
				"insert into account values(null,?,?,?,?,?) ",
				new Object[] { aBean.getPayType(), 
						aBean.getMoney(),
						aBean.getType(), 
						aBean.getData(), 
						aBean.getComment() });
	}

	public boolean delete(int _id) {
		db.execSQL(" delete from account where _id = ? ",new Object[]{ _id});
		return true;
	}

	public void update(AccountBean aBean) {
		db.execSQL(" update account set payType = ?, money = ? , type = ?,data = ? ,comment = ? where _id = ?",
				new Object[]{aBean.getPayType(),aBean.getMoney(),aBean.getType(),aBean.getData(),aBean.getComment(),aBean.get_id()});
	}

	public List<AccountBean> selectByData(String yearMonth) {
		List<AccountBean> lists = new ArrayList<AccountBean>();
		Cursor cursor = db.rawQuery(" select * from " + TABLE_NAME +" where "+COL_DATA +" like ? ", new String[]{yearMonth+"%"});
		while (cursor.moveToNext()) {
			Integer id = cursor.getInt(0);
			Integer payType = cursor.getInt(1);
			Double money = cursor.getDouble(2);
			String type = cursor.getString(3);
			String data = cursor.getString(4);
			String comment = cursor.getString(5);

			AccountBean aBean = new AccountBean(id, payType, money, type, data,
					comment);
			lists.add(aBean);
		}
		return lists;
	}
}
