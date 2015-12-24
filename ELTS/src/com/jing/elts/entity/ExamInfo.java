package com.jing.elts.entity;

import java.io.Serializable;

public class ExamInfo implements Serializable {
	private String subjectTitle;
	private int limitTime;
	private int questionCount;
	private int uid;

	public ExamInfo() {
		super();
	}

	public ExamInfo(String subjectTitle, int limitTime, int questionCount,
			int uid) {
		super();
		this.subjectTitle = subjectTitle;
		this.limitTime = limitTime;
		this.questionCount = questionCount;
		this.uid = uid;
	}

	public String getSubjectTitle() {
		return subjectTitle;
	}

	public void setSubjectTitle(String subjectTitle) {
		this.subjectTitle = subjectTitle;
	}

	public int getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(int limitTime) {
		this.limitTime = limitTime;
	}

	public int getQuestionCount() {
		return questionCount;
	}

	public void setQuestionCount(int questionCount) {
		this.questionCount = questionCount;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "考试科目:" + subjectTitle + "  考生编号:" + uid + "\n考题数量:"
				+ questionCount + "  考试时间:" + this.limitTime;
	}

}
