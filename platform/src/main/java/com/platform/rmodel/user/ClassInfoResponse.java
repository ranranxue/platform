package com.platform.rmodel.user;

import java.util.List;

import com.platform.model.BasicResponse;

public class ClassInfoResponse extends BasicResponse {
	private List<String> classList;

	public List<String> getClassList() {
		return classList;
	}

	public void setClassList(List<String> classList) {
		this.classList = classList;
	}
	
	

}
