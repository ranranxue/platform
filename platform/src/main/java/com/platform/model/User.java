package com.platform.model;
/**
 * 
 * @author Xrr
 * @time 2017.10.6
 *
 */

public class User {
	private String stuid;
	private String name;
	private String nickname;
	private String gender;
	private String phone;
	private String email;
	private String head_url;
	private Integer isManager;
	private String grade;
	private String class_info;
	private String self_introduction;
	private String detail_introduction;
	private String virtual_homepage;
	
	private Integer create_time;

	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getClass_info() {
		return class_info;
	}
	public void setClass_info(String class_info) {
		this.class_info = class_info;
	}
	public String getStuid() {
		return stuid;
	}
	public void setStuid(String stuid) {
		this.stuid = stuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getSelf_introduction() {
		return self_introduction;
	}
	public void setSelf_introduction(String self_introduction) {
		this.self_introduction = self_introduction;
	}
	public Integer getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Integer create_time) {
		this.create_time = create_time;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDetail_introduction() {
		return detail_introduction;
	}
	public void setDetail_introduction(String detail_introduction) {
		this.detail_introduction = detail_introduction;
	}
	public String getVirtual_homepage() {
		return virtual_homepage;
	}
	public void setVirtual_homepage(String virtual_homepage) {
		this.virtual_homepage = virtual_homepage;
	}

	
	
	

}
