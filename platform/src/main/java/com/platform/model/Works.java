package com.platform.model;

public class Works {
	private Integer id;
	private String title;
	private String description;
	private String works_url;
	private String author_id;
	private Integer upload_time;

	
	public Integer getUpload_time() {
		return upload_time;
	}
	public void setUpload_time(Integer upload_time) {
		this.upload_time = upload_time;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getWorks_url() {
		return works_url;
	}
	public void setWorks_url(String works_url) {
		this.works_url = works_url;
	}
	public String getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(String author_id) {
		this.author_id = author_id;
	}

}
