package com.platform.rmodel.authority;



import java.util.List;

import com.platform.model.BasicResponse;

public class ManagerAuthorityResponse extends BasicResponse{
	private List<Manage_scope> manageScopeList;
	private List<String> allGradesList;
	

	public List<Manage_scope> getManageScopeList() {
		return manageScopeList;
	}

	public void setManageScopeList(List<Manage_scope> manageScopeList) {
		this.manageScopeList = manageScopeList;
	}

	public List<String> getAllGradesList() {
		return allGradesList;
	}

	public void setAllGradesList(List<String> allGradesList) {
		this.allGradesList = allGradesList;
	}
	
	

}
