package com.jing.elts.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable{
	private ArrayList<String> answers;	//标准答案
	private ArrayList<String> userAnswers;//考生的答案
	private int level;//考题难度
	private int score;//分值
	private String title;//考题
	private String options;//待选答案
	@Override
	public String toString() {
		return this.title+"\n"+this.options;
	}
	
	public Question(ArrayList<String> answers, 
			int level, int score, String title, String options) {
		super();
		this.answers = answers;
		this.userAnswers = new ArrayList<String>();
		this.level = level;
		this.score = score;
		this.title = title;
		this.options = options;
	}

	public ArrayList<String> getAnswers() {
		return answers;
	}
	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}
	public ArrayList<String> getUserAnswers() {
		return userAnswers;
	}
	public void setUserAnswers(ArrayList<String> userAnswers) {
		this.userAnswers = userAnswers;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	
}
