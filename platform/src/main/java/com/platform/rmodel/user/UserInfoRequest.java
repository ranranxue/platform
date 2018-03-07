package com.platform.rmodel.user;

public class UserInfoRequest {
	private String stuid;
	private Integer is_manageId;
	//是否需要管理员的id 0不需要 1需要
	

	public String getStuid() {
		return stuid;
	}

	public void setStuid(String stuid) {
		this.stuid = stuid;
	}

	public Integer getIs_manageId() {
		return is_manageId;
	}

	public void setIs_manageId(Integer is_manageId) {
		this.is_manageId = is_manageId;
	}
	
	

}
