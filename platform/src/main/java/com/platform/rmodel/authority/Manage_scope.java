package com.platform.rmodel.authority;

import java.util.List;

public class Manage_scope {
	private String managerId;
	private String managerName;
	
	private List<String> gradeList;
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public List<String> getGradeList() {
		return gradeList;
	}
	public void setGradeList(List<String> gradeList) {
		this.gradeList = gradeList;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	

}
