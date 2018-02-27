package com.platform.rmodel.user;

import java.util.List;

import com.platform.model.BasicResponse;

public class GradeInfoResponse extends BasicResponse{
	private List<String> gradeList;

	public List<String> getGradeList() {
		return gradeList;
	}

	public void setGradeList(List<String> gradeList) {
		this.gradeList = gradeList;
	}

	
	

}
