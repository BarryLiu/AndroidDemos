package io.barryliu.fileediter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.net.Uri;

/**
 * 字符串的管理类 与 String.xml 有点像
 * @author Barry
 *
 */
public class ResUtils {
	
	public static String getMIME_MapTable(String fileName){
		if(MIME_Map == null){
			MIME_Map=new HashMap<String, String>();
			for (int i = 0; i < MIME_MapTable.length; i++) {
				MIME_Map.put(MIME_MapTable[i][0], MIME_MapTable[i][1]);
			}
		}
		int pos = fileName.lastIndexOf(".");
		if(pos <=0) pos=0;
		String dexStr=fileName.substring(pos,fileName.length());
		
		if(MIME_Map.containsKey(dexStr))
			return MIME_Map.get(dexStr);
		else
			return null;
	}
	public static Intent getByMIME_Map(File file){
		String type = ResUtils.getMIME_MapTable(file.getName());
		
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri,type);
		if(type==null)
			intent = null;
		return intent;
	}
	private static Map<String, String> MIME_Map;
	private static final String[][] MIME_MapTable={
			//{后缀名，MIME类型} 
			{".3gp", "video/3gpp"}, 
			{".apk", "application/vnd.android.package-archive"}, 
			{".asf", "video/x-ms-asf"}, 
			{".avi", "video/x-msvideo"}, 
			{".bin", "application/octet-stream"}, 
			{".bmp", "image/bmp"}, 
			{".c", "text/plain"}, 
			{".class", "application/octet-stream"}, 
			{".conf", "text/plain"}, 
			{".cpp", "text/plain"}, 
			{".doc", "application/msword"}, 
			{".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
			{".xls", "application/vnd.ms-excel"}, 
			{".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"}, 
			{".exe", "application/octet-stream"}, 
			{".gif", "image/gif"}, 
			{".gtar", "application/x-gtar"}, 
			{".gz", "application/x-gzip"}, 
			{".h", "text/plain"}, 
			{".htm", "text/html"}, 
			{".html", "text/html"}, 
			{".jar", "application/java-archive"}, 
			{".java", "text/plain"}, 
			{".jpeg", "image/jpeg"}, 
			{".jpg", "image/jpeg"}, 
			{".js", "application/x-javascript"}, 
			{".log", "text/plain"}, 
			{".m3u", "audio/x-mpegurl"}, 
			{".m4a", "audio/mp4a-latm"}, 
			{".m4b", "audio/mp4a-latm"}, 
			{".m4p", "audio/mp4a-latm"}, 
			{".m4u", "video/vnd.mpegurl"}, 
			{".m4v", "video/x-m4v"}, 
			{".mov", "video/quicktime"}, 
			{".mp2", "audio/x-mpeg"}, 
			{".mp3", "audio/x-mpeg"}, 
			{".mp4", "video/mp4"}, 
			{".mpc", "application/vnd.mpohun.certificate"}, 
			{".mpe", "video/mpeg"}, 
			{".mpeg", "video/mpeg"}, 
			{".mpg", "video/mpeg"}, 
			{".mpg4", "video/mp4"}, 
			{".mpga", "audio/mpeg"}, 
			{".msg", "application/vnd.ms-outlook"}, 
			{".ogg", "audio/ogg"}, 
			{".pdf", "application/pdf"}, 
			{".png", "image/png"}, 
			{".pps", "application/vnd.ms-powerpoint"}, 
			{".ppt", "application/vnd.ms-powerpoint"}, 
			{".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
			{".prop", "text/plain"}, 
			{".rc", "text/plain"}, 
			{".rmvb", "audio/x-pn-realaudio"}, 
			{".rtf", "application/rtf"}, 
			{".sh", "text/plain"}, 
			{".tar", "application/x-tar"}, 
			{".tgz", "application/x-compressed"}, 
			{".txt", "text/plain"}, 
			{".wav", "audio/x-wav"}, 
			{".wma", "audio/x-ms-wma"}, 
			{".wmv", "audio/x-ms-wmv"}, 
			{".wps", "application/vnd.ms-works"}, 
			{".xml", "text/plain"}, 
			{".z", "application/x-compress"}, 
			{".zip", "application/x-zip-compressed"}, 
			{"", "*/*"}};
	//Menu的名称
	public static String [] menuNames={"手机","sd卡","新建","粘贴","退出"};
	//Menu对应的图片
	 public static final int[] menuIds={
         R.drawable.menu_phone,
         R.drawable.menu_sdcard,
         R.drawable.menu_create,
         R.drawable.menu_palse,
         R.drawable.menu_exit
 };
	//以什么结尾的文件
	public static String[] iconDex = { ".rc", ".txt", ".png", ".mp3", ".mp4" };
	//以什么结尾的文件对应的图标
	public static int[] iconImage = { R.drawable.zip_icon, R.drawable.txt,
			R.drawable.image, R.drawable.audio, R.drawable.video };
	
	//将以什么结尾的文件与图标以键值对存储
	public static Map<String, Integer> resMap;
	//根据传入的文件实体类找出他所对应的图标
	public static int getIconRes(FileBean fb) {
		String fileName = fb.getFileName();
		if (resMap == null) {
			resMap = new HashMap<String, Integer>();
			for (int i = 0; i < iconDex.length; i++) {
				resMap.put(iconDex[i], iconImage[i]);
			}
		}
		
		//放回上一层和根目录
		if(FileManager.BACK_TO_ROOT.equals(fileName))
			return R.drawable.back_to_root; 
		else if(FileManager.BACK_TO_UP.equals(fileName))
			return R.drawable.back_to_up;
		//如果是文件夹
		else if(new File(fb.getFilePath()).isDirectory())
			return R.drawable.folder;
		
		
		int pos = fileName.lastIndexOf(".");
		if(pos <=0) pos=0;
		String dexStr=fileName.substring(pos,fileName.length());
		
		if (resMap.containsKey(dexStr))
			return resMap.get(dexStr);
		else
			return R.drawable.others;
	}
}
