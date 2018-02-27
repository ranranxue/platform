package com.platform.rmodel.user;

import com.platform.model.BasicResponse;

public class LoginResponse extends BasicResponse{
	private String sessionKey;//session中的key值；
	private String loginName;//用户名,这里的用户名指的是学号，唯一性
	private String nickname;//昵称
	private String name;//真实名字
	private String gender;//性别
	private String self_introduction;
	private String phone;
	private String email;
	
	private String head_url;//头像图片的链接
	private Integer isManager;//0是普通用户，1是管理员
	
	
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getSelf_introduction() {
		return self_introduction;
	}
	public void setSelf_introduction(String self_introduction) {
		this.self_introduction = self_introduction;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHead_url() {
		return head_url;
	}
	public void setHead_url(String head_url) {
		this.head_url = head_url;
	}
	public Integer getIsManager() {
		return isManager;
	}
	public void setIsManager(Integer isManager) {
		this.isManager = isManager;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
	
	
	
	
	
	

}
