package com.platform.rmodel.work;

import java.util.List;

import com.platform.model.BasicResponse;

public class WorksListResponse extends BasicResponse{
	private Integer worksNum;//总的作品数量
	private List<WorkInfo>   worksList;

	public List<WorkInfo> getWorksList() {
		return worksList;
	}

	public void setWorksList(List<WorkInfo> worksList) {
		this.worksList = worksList;
	}

	public Integer getWorksNum() {
		return worksNum;
	}

	public void setWorksNum(Integer worksNum) {
		this.worksNum = worksNum;
	}

}
