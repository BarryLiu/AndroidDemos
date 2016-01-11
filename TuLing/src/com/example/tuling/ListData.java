package com.example.tuling;

public class ListData {
	
	public static final int SEND = 1;
	public static final int RECEIVER = 2;
	private int flag;
	private String time;
	private String content;
	 
	
	
	public ListData(int flag, String time, String content) {
		super();
		this.flag = flag;
		this.time = time;
		this.content = content;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public int getFlag() {
		return flag;
	}


	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
 
	@Override
	public String toString() {
		return "ListData [flag=" + flag + ", time=" + time + ", content="
				+ content + "]";
	}
}
