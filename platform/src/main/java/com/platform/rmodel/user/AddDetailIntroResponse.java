package com.platform.rmodel.user;

import java.util.List;

import com.platform.model.BasicResponse;
import com.platform.rmodel.work.WorkInfo;

public class AddDetailIntroResponse extends BasicResponse{
	private String content;
	private String virHome;
	private List<WorkInfo> worksList;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getVirHome() {
		return virHome;
	}
	public void setVirHome(String virHome) {
		this.virHome = virHome;
	}
	public List<WorkInfo> getWorksList() {
		return worksList;
	}
	public void setWorksList(List<WorkInfo> worksList) {
		this.worksList = worksList;
	}
	
	

}
