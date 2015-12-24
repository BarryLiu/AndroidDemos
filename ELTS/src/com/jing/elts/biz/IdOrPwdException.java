package com.jing.elts.biz;

/**
 * 用于登陆验证,
 * 	当编号错误时,
 * 		抛出：请先注册
 * 	当密码输入时,
 *  	抛出: 密码错误异常
 * @author Barry
 *
 */
public class IdOrPwdException extends Exception {
	public IdOrPwdException(String message){
		super(message);
	}
	
	
}
