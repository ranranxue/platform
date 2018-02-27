package com.platform.rmodel.topic;

import java.util.List;

import com.platform.model.BasicResponse;

public class TopicDetailResponse extends BasicResponse{
	private Integer topic_id;
	private String author_id;
	private String author_name;
	private String author_url;
	private String title;
	private String content;
	private Integer good;
	private Integer bad;
	private long create_time;
	private Integer replyNum;
	private List<ReplyTopic> replyTopicList;
	private Integer is_forum;
	
	
	public Integer getIs_forum() {
		return is_forum;
	}
	public void setIs_forum(Integer is_forum) {
		this.is_forum = is_forum;
	}
	public Integer getTopic_id() {
		return topic_id;
	}
	public void setTopic_id(Integer topic_id) {
		this.topic_id = topic_id;
	}
	public String getAuthor_name() {
		return author_name;
	}
	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}
	public String getAuthor_url() {
		return author_url;
	}
	public void setAuthor_url(String author_url) {
		this.author_url = author_url;
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
	public Integer getGood() {
		return good;
	}
	public void setGood(Integer good) {
		this.good = good;
	}
	public Integer getBad() {
		return bad;
	}
	public void setBad(Integer bad) {
		this.bad = bad;
	}
	public long getCreate_time() {
		return create_time;
	}
	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}
	public Integer getReplyNum() {
		return replyNum;
	}
	public void setReplyNum(Integer replyNum) {
		this.replyNum = replyNum;
	}
	public List<ReplyTopic> getReplyTopicList() {
		return replyTopicList;
	}
	public void setReplyTopicList(List<ReplyTopic> replyTopicList) {
		this.replyTopicList = replyTopicList;
	}
	public String getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(String author_id) {
		this.author_id = author_id;
	}
	
	

}
