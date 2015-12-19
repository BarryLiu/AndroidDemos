package com.jing.elts.dao;

import java.io.Serializable;
import java.util.HashMap;

import android.content.Context;
import android.content.res.Resources;

import com.jing.elts.R;
import com.jing.elts.entity.Files;
import com.jing.elts.entity.User;

public abstract class ExamDaoBase implements Serializable {
	HashMap<Integer, User> mUsers;//存储下载的已注册用户
	public ExamDaoBase(){
		mUsers =new HashMap<Integer, User>();
	}
	/**
	 * 下载已注册的用户
	 */
	protected abstract void loadUsers();
	
	/**
	 * 获取下载的服务端地址和ExamInfo、Questions、Users的文件名
	 * @param context
	 * @return
	 */
	protected Files getFiles(Context context){
		Resources res = context.getResources();
		String rootUrl = res.getString(R.string.root_url);
		//获取配置文件中下载,解析文件的类型
		String parseMode = res.getString(R.string.parse_mode);
		String fnExamInfo=null;
		String fnQuestion=null;
		String fnUser=null;
		if("txt".equals(parseMode)){
			fnExamInfo=res.getString(R.string.exam_info_txt);
			fnQuestion=res.getString(R.string.question_txt);
			fnUser=res.getString(R.string.user_txt);
		}else if("json".equals(parseMode)){
			fnExamInfo=res.getString(R.string.exam_info_json);
			fnQuestion=res.getString(R.string.question_json);
			fnUser=res.getString(R.string.user_json);
		}else if("xml_pull".equals(parseMode)){
//			fnExamInfo=res.getString(R.string.exam_info_json);
//			fnQuestion=res.getString(R.string.question_json);
//			fnUser=res.getString(R.string.user_json);
		}else if("xml_sax".equals(parseMode)){
//			fnExamInfo=res.getString(R.string.exam_info_json);
//			fnQuestion=res.getString(R.string.question_json);
//			fnUser=res.getString(R.string.user_json);
		}
		
		Files  file=new Files(rootUrl, fnExamInfo, fnQuestion, fnUser);
		return file;
	}
	
	/**
	 * 业务逻辑层调用这个方法进行登陆验证
	 * @param uid
	 * @return
	 */
	public User findUser(int uid){
		return mUsers.get(uid);
	}
}
