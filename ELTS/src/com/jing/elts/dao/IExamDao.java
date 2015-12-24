package com.jing.elts.dao;

import java.util.ArrayList;

import com.jing.elts.entity.ExamInfo;
import com.jing.elts.entity.Question;
import com.jing.elts.entity.User;

public interface IExamDao {
	
	/**
	 * 加载考题
	 * @return
	 */
	ArrayList<Question> loadQuestions();
	
	/**
	 * 加载考试信息
	 * @return
	 */
	ExamInfo loadExamInfo();
	/**
	 * 登录验证时,调用
	 * @param uid
	 * @return
	 */
	User findUser(int uid);
}
