package com.platform.rmodel.notice;

import java.util.List;

import com.platform.model.BasicResponse;

public class NoticeListResponse  extends BasicResponse{
	private List<NoticeInfo> noticeList;

	public List<NoticeInfo> getNoticeList() {
		return noticeList;
	}

	public void setNoticeList(List<NoticeInfo> noticeList) {
		this.noticeList = noticeList;
	}
	
	
	

}
