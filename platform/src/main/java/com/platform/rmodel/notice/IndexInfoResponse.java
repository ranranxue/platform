package com.platform.rmodel.notice;

import java.util.List;

import com.platform.model.BasicResponse;
import com.platform.rmodel.work.WorkInfo;

public class IndexInfoResponse extends BasicResponse {
	private List<NoticeInfo> noticeList;
	private List<WorkInfo>   worksList;
	public List<NoticeInfo> getNoticeList() {
		return noticeList;
	}
	public void setNoticeList(List<NoticeInfo> noticeList) {
		this.noticeList = noticeList;
	}
	public List<WorkInfo> getWorksList() {
		return worksList;
	}
	public void setWorksList(List<WorkInfo> worksList) {
		this.worksList = worksList;
	}
	
	

}
