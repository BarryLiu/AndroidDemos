package com.jing.elts.entity;

import java.io.Serializable;

public class ExamInfo implements Serializable {
	private String subjectTitle;
	private String limitTime;
	private String questionCount;
	private String uid;

	public ExamInfo() {
		super();
	}

	public ExamInfo(String subjectTitle, String limitTime,
			String questionCount, String uid) {
		super();
		this.subjectTitle = subjectTitle;
		this.limitTime = limitTime;
		this.questionCount = questionCount;
		this.uid = uid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getSubjectTitle() {
		return subjectTitle;
	}

	public void setSubjectTitle(String subjectTitle) {
		this.subjectTitle = subjectTitle;
	}

	public String getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(String limitTime) {
		this.limitTime = limitTime;
	}

	public String getQuestionCount() {
		return questionCount;
	}

	public void setQuestionCount(String questionCount) {
		this.questionCount = questionCount;
	}

	@Override
	public String toString() {
		return "考试科目:" + subjectTitle + "  考生编号:" + uid
				+ "\n考题数量:" + questionCount + "  考试时间:"+this.limitTime;
	}

}
