package com.platform.rmodel.topic;

import com.platform.model.BasicResponse;

public class TopicCreateResponse extends BasicResponse {
	private Integer id;
	private String  title;
	private String author_id;
	
	private String author_name;//作者的名字
	private long create_time;
	private Integer totalNum;//回帖的数量，不包括回帖的评论
	
	
	public String getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(String author_id) {
		this.author_id = author_id;
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
	public String getAuthor_name() {
		return author_name;
	}
	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}
	public long getCreate_time() {
		return create_time;
	}
	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	

}
