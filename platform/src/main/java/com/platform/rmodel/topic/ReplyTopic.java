package com.platform.rmodel.topic;

import java.util.List;

public class ReplyTopic {
	private Integer id;
	private String author_id;
	private String author_name;
	private String author_url;
	private String content;
	private Integer good;
	private Integer bad;
	private long create_time;
	private Integer commentNum;
	private Integer real_name;
	private List<CommentInfo> commentInfoList;//每个回复的评论
	
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
	public Integer getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}
	public Integer getReal_name() {
		return real_name;
	}
	public void setReal_name(Integer real_name) {
		this.real_name = real_name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(String author_id) {
		this.author_id = author_id;
	}
	public List<CommentInfo> getCommentInfoList() {
		return commentInfoList;
	}
	public void setCommentInfoList(List<CommentInfo> commentInfoList) {
		this.commentInfoList = commentInfoList;
	}
	

}
