package com.jing.elts.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;

import com.jing.elts.entity.ExamInfo;
import com.jing.elts.entity.Files;
import com.jing.elts.entity.Question;
import com.jing.elts.entity.User;
import com.jing.elts.utils.HttpUtils;

public class ExamDao_PulParser extends ExamDaoBase implements IExamDao,Serializable {
	Files mFiles;

	public ExamDao_PulParser(Context context) {
		mFiles = getFiles(context);
		loadUsers();//加载所有用户
	}

	@Override
	public ArrayList<Question> loadQuestions() {
		ArrayList<Question> questions=new ArrayList<Question>();
		String url = mFiles.getUrl() + mFiles.getFnQuestion();
		InputStream in=null;
		try {

			  in = HttpUtils.getInputStream(url, null,
					HttpUtils.RequestMethod.GET);
			XmlPullParser parser = XmlPullParserFactory.newInstance()
					.newPullParser();
			parser.setInput(in, "utf-8");

			for (int eventType = XmlPullParser.START_DOCUMENT; eventType != XmlPullParser.END_DOCUMENT; eventType = parser
					.next()) {
				if(eventType==XmlPullParser.START_TAG){
					if("question".equals(eventType)){
						String answer = parser.getAttributeValue(null,"answer");
						ArrayList<String> answers=new ArrayList<String>();
						for (int i = 0; i < answer.length(); i++) {
								answers.add(answer.charAt(i)+"");
						}
						int score=Integer.parseInt(parser.getAttributeValue(null,"score"));
						int level=Integer.parseInt(parser.getAttributeValue(null, "level"));
						String title=parser.getAttributeValue(null,"title");
						StringBuilder options=new StringBuilder();
						for (int i = 4; i <= 7; i++) {
							options.append(parser.getAttributeValue(i)).append("\n");
						}
						Question q =new Question(answers, level, score, title, options.toString());
						questions.add(q);
					}
					
				}
			}
			return questions;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(in!=null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			HttpUtils.closeClient();
		}
		return null;
	}

	@Override
	public ExamInfo loadExamInfo() {
		String url = mFiles.getUrl() + mFiles.getFnExamInfo();
		InputStream in = null;
		try {
			in = HttpUtils.getInputStream(url, null,
					HttpUtils.RequestMethod.GET);
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();// 创建用于pull解析的pull解析器
			parser.setInput(in, "utf-8");
			ExamInfo examInfo = new ExamInfo();
			for (int eventType = XmlPullParser.START_DOCUMENT; eventType != XmlPullParser.END_DOCUMENT; eventType = parser
					.next()) {
				if (eventType == parser.START_TAG) {
					String tagName = parser.getName();
					if ("exam_info".equals(tagName)) {
						examInfo.setSubjectTitle(parser.getAttributeValue(0));
						examInfo.setLimitTime(Integer.parseInt(parser
								.getAttributeValue(1)));
						examInfo.setQuestionCount(Integer.parseInt(parser
								.getAttributeValue(2)));
						return examInfo;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	protected void loadUsers() {
		String url=mFiles.getUrl()+mFiles.getFnUser();
		InputStream in=null;
		try {
			in=HttpUtils.getInputStream(url, null, HttpUtils.RequestMethod.GET);
			XmlPullParser parser=XmlPullParserFactory.newInstance().newPullParser();
			parser.setInput(in,"utf-8");
			for (int eventType = XmlPullParser.START_DOCUMENT; eventType != parser.END_DOCUMENT	; eventType=parser.next()) {
				if(eventType==XmlPullParser.START_TAG){
					String tagName=parser.getName();
					if("user".equals(tagName)){
						int id = Integer.parseInt(parser.getAttributeValue(null,"id"));
						String name=parser.getAttributeValue(null, "name");
						String password=parser.getAttributeValue(null, "password");
						String phone=parser.getAttributeValue(null, "phone");
						String email=parser.getAttributeValue(null, "email");
						User user=new User(id, name, password, phone, email);
						mUsers.put(user.getId(), user);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			HttpUtils.closeClient();
		}
	}
}
