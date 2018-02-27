package com.platform.rmodel.work;

public class UploadWorksRequest {
	private String stuid;
	private String title;
	private String description;
	private String label;
	private Integer format;
	private String works_url;
	public String getStuid() {
		return stuid;
	}
	public void setStuid(String stuid) {
		this.stuid = stuid;
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
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Integer getFormat() {
		return format;
	}
	public void setFormat(Integer format) {
		this.format = format;
	}
	public String getWorks_url() {
		return works_url;
	}
	public void setWorks_url(String works_url) {
		this.works_url = works_url;
	}
	
	

}
