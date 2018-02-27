package com.platform.rmodel.notice;

public class NoticeInfo {
	private Integer id;
	private String title;
	private String author_name;
	private Integer create_time;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Integer create_time) {
		this.create_time = create_time;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAuthor_name() {
		return author_name;
	}
	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}
	
	

}
