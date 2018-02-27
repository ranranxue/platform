package com.platform.rmodel.topic;

import java.util.List;

import com.platform.model.BasicResponse;

public class TopicListResponse extends BasicResponse{
	private List<TopicInfo> topicInfoList;
	private Integer is_forum;
	

	public List<TopicInfo> getTopicInfoList() {
		return topicInfoList;
	}

	public void setTopicInfoList(List<TopicInfo> topicInfoList) {
		this.topicInfoList = topicInfoList;
	}

	public Integer getIs_forum() {
		return is_forum;
	}

	public void setIs_forum(Integer is_forum) {
		this.is_forum = is_forum;
	}

	
	
	
	

}
