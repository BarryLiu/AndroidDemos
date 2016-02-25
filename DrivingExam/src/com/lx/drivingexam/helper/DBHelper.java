package com.lx.drivingexam.helper;

import java.util.ArrayList;
import java.util.List;
import com.lx.drivingexam.MyApplication;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class DBHelper{
	public static List<Bundle> getQuestionBundle(SQLiteDatabase db,String sql,String[] selectionArgs){
		String str="select n.id as _id,n.*,c.[is_collect],w.[stocks_type] from "+StaticBean.TABLE_NAME+
				" as n left join "+StaticBean.TABLE_NAME_WRONGS+" as w on n.id=w.test_id left join "+
				StaticBean.TABLE_NAME_COLLECT+" as c on n.[id]=c.test_id ";
		//Log.d("show", str);
		List<Bundle> list=new ArrayList<Bundle>();
		Cursor cursor=null;
		try {
			cursor=db.rawQuery(str+sql, selectionArgs);
			String[] strs=new String[StaticBean.WEB_NOTE_COLUMNS.length];
			while(cursor.moveToNext()){
				Bundle qb=new Bundle();
				int _id=cursor.getInt(cursor.getColumnIndex("_id"));
				int kemu=cursor.getInt(cursor.getColumnIndex("kemu"));
				String type=cursor.getString(cursor.getColumnIndex(StaticBean.WEB_NOTE_TYPE));
				for (int i = 0; i < StaticBean.WEB_NOTE_COLUMNS.length; i++) {
					strs[i]=cursor.getString(cursor.getColumnIndex(
									StaticBean.WEB_NOTE_COLUMNS[i]));
				}
				int is_collect=cursor.getInt(cursor.getColumnIndex(StaticBean.IS_COLLECT));
				int stocks_type=cursor.getInt(cursor.getColumnIndex(StaticBean.STOCKS_TYPE));
				int diff_degree=cursor.getInt(cursor.getColumnIndex(StaticBean.DIFF_DEGREE));
				qb.putInt(StaticBean.WEB_NOTE_ID,_id);
				qb.putInt(StaticBean.WEB_NOTE_KEMU,kemu);
				qb.putInt(StaticBean.IS_COLLECT,is_collect);
				qb.putInt(StaticBean.STOCKS_TYPE,stocks_type);
				qb.putString(StaticBean.WEB_NOTE_TYPE,type);
				qb.putString(StaticBean.SELECT_ID, "");
				qb.putInt(StaticBean.IS_SHOW_EXPTION, 0);
				qb.putInt(StaticBean.DIFF_DEGREE, diff_degree);
				qb.putInt(StaticBean.IS_MOCK_EXAMINTION, 0);
				for (int i = 0; i < strs.length; i++) {
					qb.putString(StaticBean.WEB_NOTE_COLUMNS[i],strs[i]);
				}
				list.add(qb);
			}
		} catch (Exception e) {
			// TODO: handle exception
			//Log.d("show", e.getMessage());
		}finally{
			if(cursor!=null){
				cursor.close();
			}
		}
		return list;
	}
	/***收藏*/
	public static void insertCollect(SQLiteDatabase db,Bundle bundle){
		try {
			int test_id=bundle.getInt(StaticBean.WEB_NOTE_ID);
			int kemu=bundle.getInt(StaticBean.WEB_NOTE_KEMU);
			int is_collect=1;
			ContentValues values=new ContentValues();
			values.put(StaticBean.COLLECT_TEST_ID, test_id);
			values.put(StaticBean.IS_COLLECT, is_collect);
			values.put(StaticBean.COLLECT_KEMU_TYPE, kemu);
			values.put(StaticBean.COLLECT_CAR_TYPE, 0);
			db.insert(StaticBean.TABLE_NAME_COLLECT,null, values);
//			Log.d("show", sid+"");
			//插入一行数据
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/**添加我的错题*/
	public static void insertWrongs(SQLiteDatabase db,MyApplication myApp){
		try {
			int test_id=myApp.bundle.getInt(StaticBean.WEB_NOTE_ID);
			int stocks_type=myApp.bundle.getInt(StaticBean.STOCKS_TYPE);
			int kemu=myApp.bundle.getInt(StaticBean.WEB_NOTE_KEMU);
			ContentValues values=new ContentValues();
			values.put(StaticBean.COLLECT_TEST_ID, test_id);
			values.put(StaticBean.COLLECT_CAR_TYPE, 0);
			values.put(StaticBean.COLLECT_KEMU_TYPE, kemu);
			values.put(StaticBean.STOCKS_TYPE, stocks_type);
			db.insert(StaticBean.TABLE_NAME_WRONGS,null, values);
	        //插入一行数据
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/**修改我的错题*/
	public static void updateWrongs(SQLiteDatabase db,MyApplication myApp){
		try {
			int test_id=myApp.bundle.getInt(StaticBean.WEB_NOTE_ID);
			int stocks_type=myApp.bundle.getInt(StaticBean.STOCKS_TYPE);
			ContentValues values=new ContentValues();
			values.put(StaticBean.STOCKS_TYPE, stocks_type);
			db.update(StaticBean.TABLE_NAME_WRONGS, values, "test_id=?", new String[]{test_id+""});
	        //插入一行数据
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/**取消收藏*/
	public static void deleteCollect(SQLiteDatabase db,String id){
		try {
			db.delete(StaticBean.TABLE_NAME_COLLECT,StaticBean.COLLECT_TEST_ID+"=?", new String[]{id});
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/**清空所有收藏，错题*/
	public static void deleteAll(SQLiteDatabase db){
		db.beginTransaction();
		int cid=db.delete(StaticBean.TABLE_NAME_COLLECT,null,null);
		int wid=db.delete(StaticBean.TABLE_NAME_WRONGS,null,null);
		if(cid>0 || wid>0){
			db.setTransactionSuccessful();
		}
		db.endTransaction();
	}
	/**查询是否被收藏*/
	public boolean questCollect(SQLiteDatabase db,String id){
		String sql="select count(*) from "+StaticBean.TABLE_NAME+
				" where "+StaticBean.COLLECT_TEST_ID+"=?";
		Cursor cursor=db.rawQuery(sql, new String[]{id});
		if(cursor.getCount()>0){
			return true;
		}else{
			return false;
		}
	}
}
