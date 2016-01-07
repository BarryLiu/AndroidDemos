package com.jing.elts.dao;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;

import com.jing.elts.entity.ExamInfo;
import com.jing.elts.entity.Question;

public class ExamDao_JsonParsParser extends ExamDaoBase implements IExamDao,Serializable{
 
	
	public ExamDao_JsonParsParser(Context context) {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void loadUsers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Question> loadQuestions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExamInfo loadExamInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
