package com.platform.rmodel.topic;

public class ReplyTopicRequest {
	private String stuid;
	private String content;
	private Integer reply_id;
	private Integer real_name;
	public String getStuid() {
		return stuid;
	}
	public void setStuid(String stuid) {
		this.stuid = stuid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getReply_id() {
		return reply_id;
	}
	public void setReply_id(Integer reply_id) {
		this.reply_id = reply_id;
	}
	public Integer getReal_name() {
		return real_name;
	}
	public void setReal_name(Integer real_name) {
		this.real_name = real_name;
	}
	

}
