package com.jing.elts.biz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.content.Context;

import com.jing.R;
import com.jing.elts.dao.ExamDao_JsonParsParser;
import com.jing.elts.dao.ExamDao_PulParser;
import com.jing.elts.dao.IExamDao;
import com.jing.elts.entity.ExamInfo;
import com.jing.elts.entity.Question;
import com.jing.elts.entity.User;

/**
 * 业务逻辑层
 * @author Barry
 *
 */
public class ExamBiz implements IExampBiz{
	User mUser;//登录者 当前考生
	ArrayList<Question> mQuestions;
	ExamInfo mExaminfo;
	IExamDao mDao;
	
	public ExamBiz(Context context){
		String parserMode=context.getString(R.string.parse_mode);
		if("json".equals(parserMode)){
			mDao=new ExamDao_JsonParsParser(context);
		}else if("pull_xml".equals(parserMode)){
			mDao=new ExamDao_PulParser(context);
		}else if("sax_xml".equals(parserMode)){
			
		}else if("txt".equals(parserMode)){
			
		}
	}
	@Override
	public User login(int uid, String pwd) throws IdOrPwdException {
		User user = mDao.findUser(uid);
		if(user==null)//没有这个用户
			throw new IdOrPwdException("请先注册");
		if(!user.getPassword().equals(pwd))
			throw new IdOrPwdException("密码错误");
		mUser=user;
		return mUser;
	}

	@Override
	public User getUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadQuestions() {
		 mQuestions = mDao.loadQuestions();
	}

	@Override
	public ExamInfo beginExam() {
		Collections.shuffle(mQuestions,new Random());
		for (int i = 0; i < mQuestions.size(); i++) {//修改每道题的题号标题
			Question q= mQuestions.get(i);
			String title = q.getTitle();
			title = (i+1)+title.substring(title.indexOf("."));			
			q.setTitle(title);
		}
		mExaminfo.setUid(mUser.getId());
		return mExaminfo;
	}

	@Override
	public Question getQuestion(int qid) {
		return mQuestions.get(qid);
	}

	@Override
	public void saveUserAnswers(int qid, ArrayList<String> userAnswers) {
		Question q = getQuestion(qid);
		q.setUserAnswers(userAnswers);
	}

	@Override
	public int over() {
		int total=0;
		for (Question q : mQuestions) {
			if(q.getAnswers().equals(q.getUserAnswers())){
				total+=q.getScore();
			}
		}
		return total;
	}

}
