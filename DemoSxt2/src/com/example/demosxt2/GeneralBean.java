package com.example.demosxt2;

public class GeneralBean {
	private int resid;
	private String name;
	@Override
	public String toString() {
		return "GeneralBean [resid=" + resid + ", name=" + name + "]";
	}
	public int getResid() {
		return resid;
	}
	public void setResid(int resid) {
		this.resid = resid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public GeneralBean(){}
	
	public GeneralBean(int resid, String name) {
		super();
		this.resid = resid;
		this.name = name;
	}
	
	
	
}
