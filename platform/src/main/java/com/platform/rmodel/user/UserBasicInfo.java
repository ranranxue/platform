package com.platform.rmodel.user;

public class UserBasicInfo {
	private String stuid;
	private String name;
	private String grade;
	private String class_info;
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
	public Integer getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Integer create_time) {
		this.create_time = create_time;
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
	

}
