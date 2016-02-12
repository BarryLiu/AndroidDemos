package io.barryliu.driverexam.bean;

public class ToolsBean {
	private int tools_id;
	private String tools_text;
	private int tools_falg;
	public ToolsBean(int tools_id, String numlist,int tools_falg) {
		super();
		this.tools_id = tools_id;
		this.tools_text = numlist;
		this.tools_falg=tools_falg;
	}
	public ToolsBean(int tools_id, String numlist) {
		super();
		this.tools_id = tools_id;
		this.tools_text = numlist;
	}
	public ToolsBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getTools_id() {
		return tools_id;
	}
	public void setTools_id(int tools_id) {
		this.tools_id = tools_id;
	}
	public String getTools_text() {
		return tools_text;
	}
	public void setTools_text(String tools_text) {
		this.tools_text = tools_text;
	}
	public int getTools_falg() {
		return tools_falg;
	}
	public void setTools_falg(int tools_falg) {
		this.tools_falg = tools_falg;
	}
}
