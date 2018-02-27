package com.platform.rmodel.topic;

public class TopicDeleteRequest {
	private Integer id;//删除话题，回帖和评论
	private Integer status;//0代表话题，1代表回帖，2代表评论
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

}
