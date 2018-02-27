package com.platform.rmodel.user;



import java.util.List;

import com.platform.model.BasicResponse;
import com.platform.rmodel.work.WorkInfo;


public class HomePageResponse extends BasicResponse{
	private String stuid;
	private String name;
	private String nickname;
	private String gender;
	private String self_introduction;
	private String head_url; 
	private Integer isManager;
	private String detail_introduction;
	private String managerId;
	private List<WorkInfo> worksList;
	
	
	
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

	

	public String getSelf_introduction() {
		return self_introduction;
	}

	public void setSelf_introduction(String self_introduction) {
		this.self_introduction = self_introduction;
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

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public List<WorkInfo> getWorksList() {
		return worksList;
	}

	public void setWorksList(List<WorkInfo> worksList) {
		this.worksList = worksList;
	}
	
	
	
	

}
