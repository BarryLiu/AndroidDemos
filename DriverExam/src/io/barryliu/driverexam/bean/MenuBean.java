package io.barryliu.driverexam.bean;

public class MenuBean {
	private int id;
	private String kemu_name;
	private String kemu_text;
	
	
	public MenuBean(int id, String kemu_name, String kemu_text) {
		super();
		this.id = id;
		this.kemu_name = kemu_name;
		this.kemu_text = kemu_text;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getKemu_name() {
		return kemu_name;
	}
	public void setKemu_name(String kemu_name) {
		this.kemu_name = kemu_name;
	}
	public String getKemu_text() {
		return kemu_text;
	}
	public void setKemu_text(String kemu_text) {
		this.kemu_text = kemu_text;
	}
	@Override
	public String toString() {
		return "MenuBean [id=" + id + ", kemu_name=" + kemu_name
				+ ", kemu_text=" + kemu_text + "]";
	}
	
	
}
