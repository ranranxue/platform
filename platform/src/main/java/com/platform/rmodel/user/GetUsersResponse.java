package com.platform.rmodel.user;

import java.util.List;

import com.platform.model.BasicResponse;

public class GetUsersResponse  extends BasicResponse{
	private List<UserInfo> userList;

	public List<UserInfo> getUserList() {
		return userList;
	}

	public void setUserList(List<UserInfo> userList) {
		this.userList = userList;
	}
	

}
