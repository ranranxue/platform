package com.platform.rmodel.notice;



import java.util.List;

import com.platform.model.BasicResponse;

public class NoticeDetailResponse  extends BasicResponse{
	private Integer notice_id;
	private String title;
	private String content;
	private String picture_url;
	private String publisher_name;
	private Integer create_time;
	
	private List<AttachmentInfo> attachmentList;
	public Integer getNotice_id() {
		return notice_id;
	}
	public void setNotice_id(Integer notice_id) {
		this.notice_id = notice_id;
	}
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
	public String getPicture_url() {
		return picture_url;
	}
	public void setPicture_url(String picture_url) {
		this.picture_url = picture_url;
	}
	public String getPublisher_name() {
		return publisher_name;
	}
	public void setPublisher_name(String publisher_name) {
		this.publisher_name = publisher_name;
	}
	public Integer getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Integer create_time) {
		this.create_time = create_time;
	}
	public List<AttachmentInfo> getAttachmentList() {
		return attachmentList;
	}
	public void setAttachmentList(List<AttachmentInfo> attachmentList) {
		this.attachmentList = attachmentList;
	}
	
	

}
