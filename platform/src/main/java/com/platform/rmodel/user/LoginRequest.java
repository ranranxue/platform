package com.platform.rmodel.user;

public class LoginRequest {
	private String loginName;//用户的登陆名，即是用户的唯一标识id
	private String password;
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	

}
