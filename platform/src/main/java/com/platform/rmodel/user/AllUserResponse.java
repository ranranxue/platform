package com.platform.rmodel.user;

import java.util.List;

import com.platform.model.BasicResponse;

public class AllUserResponse extends BasicResponse{
	private List<UserBasicInfo> stuList;

	public List<UserBasicInfo> getStuList() {
		return stuList;
	}

	public void setStuList(List<UserBasicInfo> stuList) {
		this.stuList = stuList;
	}
	

}
