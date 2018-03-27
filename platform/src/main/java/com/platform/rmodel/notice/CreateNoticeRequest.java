package com.platform.rmodel.notice;

import java.util.List;

public class CreateNoticeRequest {
	private String title;
	private String content;
	private String publisher;
	private List<AttachmentInfo> attachmentList;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public List<AttachmentInfo> getAttachmentList() {
		return attachmentList;
	}
	public void setAttachmentList(List<AttachmentInfo> attachmentList) {
		this.attachmentList = attachmentList;
	}
	
	
	

}
