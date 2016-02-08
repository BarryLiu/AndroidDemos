package io.barryliu.driverexam.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.R;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class DBManager {
	
	private final int BUFFER_SIZE = 400000;
	public static final String DB_NAME = "question.db";
	public static final String PACKAGE_NAME = "";
	public static final String DB_PATH = "/data"
			+Environment.getDataDirectory().getAbsolutePath()+PACKAGE_NAME;
	
	private SQLiteDatabase database;
	private Context mContext;
	public DBManager(Context context){
		this.mContext = context;
	}
	public SQLiteDatabase getDatabase(){
		if(this.database == null)
			this.openDatabase();
		return this.database;
	}
	public void openDatabase(){
		this.database = this.openDatabase(DB_PATH+"/"+DB_NAME);
	}
	private SQLiteDatabase openDatabase(String dbfile){
		
		try {
			if(!(new File(dbfile).exists())){
				   InputStream is = null;// this.mContext.getResources().openRawResource(
//                       R.raw.question);			
				   FileOutputStream fos =new FileOutputStream(dbfile);
				   byte[] buffer =new byte[BUFFER_SIZE];
				   int count =0;
				   while((count = is.read(buffer))>=0){
					   fos.write(buffer,0,count);
				   }
				   fos.close();
				   is.close();
			}
			SQLiteDatabase db =SQLiteDatabase.openDatabase(dbfile, null, 0);
			return db;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
}
