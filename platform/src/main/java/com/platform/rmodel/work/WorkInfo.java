package com.platform.rmodel.work;

public class WorkInfo {
	private Integer id;//作品的编号唯一标识
	
	private String title;//作品的名字
	
	private String works_url;
	private Integer upload_time;//上传作品的时间
	private String  name;//上传者的名字;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getUpload_time() {
		return upload_time;
	}
	public void setUpload_time(Integer upload_time) {
		this.upload_time = upload_time;
	}
	public String getWorks_url() {
		return works_url;
	}
	public void setWorks_url(String works_url) {
		this.works_url = works_url;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	

}
