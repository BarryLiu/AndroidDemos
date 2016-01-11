package io.barryLiu.accountingmanager.entity;

public class AccountBean {
	/*id*/
	private Integer _id;
	/*1 收入，0支出*/
	private Integer payType;
	/*金额*/
	private Double money;
	/*消费类型*/
	private String type;
	/*消费时间*/
	private String data;
	/*备注*/
	private String comment;
	public AccountBean(){
	}
	
	public AccountBean(Integer _id, Integer payType, Double money, String type,
			String data, String comment) {
		super();
		this._id = _id;
		this.payType = payType;
		this.money = money;
		this.type = type;
		this.data = data;
		this.comment = comment;
	}



	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Integer get_id() {
		return _id;
	}
	public void set_id(Integer _id) {
		this._id = _id;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "AccountBean [_id=" + _id + ", payType=" + payType + ", money="
				+ money + ", type=" + type + ", data=" + data + ", comment="
				+ comment + "]";
	}
}
