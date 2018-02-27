package com.platform.rmodel.topic;

public class CommentRequest {
	private Integer topic_id;
	private String content;
	private String stuid;
	public Integer getTopic_id() {
		return topic_id;
	}
	public void setTopic_id(Integer topic_id) {
		this.topic_id = topic_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStuid() {
		return stuid;
	}
	public void setStuid(String stuid) {
		this.stuid = stuid;
	}
	

}
