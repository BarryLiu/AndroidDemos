package com.jing.elts.dao;

import java.util.ArrayList;

import android.content.Context;

import com.jing.elts.entity.ExamInfo;
import com.jing.elts.entity.Files;
import com.jing.elts.entity.Question;

public class ExamDao_PulParser extends ExamDaoBase implements IExamDao{
	Files mFiles;
	public  ExamDao_PulParser(Context context) {
		mFiles=getFiles(context);
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
	@Override
	protected void loadUsers() {

	}
}
