package io.barryliu.fileediter;

import java.io.File;

import android.content.Intent;
import android.net.Uri;

/**
 * 单例
 * 方法1:<br>
 * 一个为Activity提供不同跳转的类 
 * 调用getIntent方法得到不同文件类型的系统Intent
 * 但这样不够不太灵活    所以这个类没有被调用<br>
 * 
 *  方法2：<br>
 * 采用的是 ResUtils.getByMIME_Map(File file)的方法
 * @author Barry
 *
 */
public class FileIntent {
	private static FileIntent fileIntent;
	public  static FileIntent getFileIntent(){
		if(fileIntent == null)
			fileIntent =new FileIntent();
		return fileIntent;
	}
	private FileIntent(){}
	
	/**对外公开传入要打开的文件返回打开的Intent跳转*/
	public Intent getIntent(File file) {
		String fileName = file.getName();
		int pos = fileName.lastIndexOf(".");
		if (pos <= 0)
			pos = 0;
		String dexStr = fileName.substring(pos, fileName.length());
		Intent intent = null;
		if (".jpg".equals(dexStr))
			intent = getJpg(file);
		else if (".pdf".equals(dexStr))
			intent = getPdf(file);
		else if (".txt".equals(dexStr))
			intent = getTxt(file);
		else if (".mp3".equals(dexStr))
			intent = getMp3(file);
		else if (".avi".equals(dexStr))
			intent = getAvi(file);
		else if (".chm".equals(dexStr))
			intent = getChm(file);
		else if (".doc".equals(dexStr))
			intent = getDoc(file);
		else if (".xls".equals(dexStr))
			intent = getXls(file);
		else if (".ppt".equals(dexStr))
			intent = getPpt(file);

		//intent =getByMIME_Map(file);
		return intent;
	}
	
	/*
	 //方法2
	 private Intent getByMIME_Map(File file){
		String type = ResUtils.getMIME_MapTable(file.getName());
		
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri,type);
		return intent;
	}*/
	
	/**9. Intent to open the PPT file:*/
	private Intent getPpt(File file) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
		return intent;
	}

	/**8. Android Excel intent:*/
	private Intent getXls(File file) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "application/vnd.ms-excel");
		return intent;
	}

	/** 7. Intent to open a Word document:*/
	private Intent getDoc(File file) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "application/msword");
		return intent;
	}

	/** 6. Intent to open the CHM file:*/
	private Intent getChm(File file) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "application / x-chm");
		return intent;
	}

	/** 5. Intent to open the video file:*/
	private Intent getAvi(File file) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "video/*");
		return intent;
	}

	/** 4. Intent to open the audio file:*/
	private Intent getMp3(File file) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "audio/*");
		return intent;
	}

	/** 3. Intent to open a text file:*/
	private Intent getTxt(File file) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "text/plain");
		return intent;
	}

	/** 2. Intent to open a PDF file:*/
	private Intent getPdf(File file) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "application/pdf");
		return intent;
	}

	/** 1. Intent open a picture file */
	private Intent getJpg(File file) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "image/*");
		return intent;
	}
}
