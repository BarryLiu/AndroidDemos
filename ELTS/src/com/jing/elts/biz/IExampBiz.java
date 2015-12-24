package com.jing.elts.biz;

import java.util.ArrayList;

import com.jing.elts.entity.ExamInfo;
import com.jing.elts.entity.Question;
import com.jing.elts.entity.User;

public interface IExampBiz {
	/**
	 * 登录验证
	 * 
	 * @param uid
	 *            ：输入的编号
	 * @param pwd
	 *            ：输入的密码
	 * @return
	 * @throws IdOrPwdException
	 */
	User login(int uid, String pwd) throws IdOrPwdException;

	/**
	 * 在考试的主菜单页面和考试页面调用
	 * 
	 * @return
	 */
	User getUser();
	/**
	 * 从服务端加载考题
	 */
	void loadQuestions();
	/**
	 * 开始考试 将一组考题随机打乱,并返回考试信息对象
	 * 
	 * @return
	 */
	ExamInfo beginExam();

	/**
	 * 为考试窗口返回一道考题
	 * 
	 * @param qid
	 * @return
	 */
	Question getQuestion(int qid);
	
	/**
	 * 保存考生选择的答案
	 * @param qid	考题的索引
	 * @param userAnswers 考生选择的答案
	 */
	void saveUserAnswers(int qid,ArrayList<String> userAnswers);
	/**
	 * 判题、打分
	 * @return
	 */
	int over();
	 
}
