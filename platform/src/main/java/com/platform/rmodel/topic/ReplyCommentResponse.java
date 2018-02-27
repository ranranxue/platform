package com.platform.rmodel.topic;

import java.util.List;

import com.platform.model.BasicResponse;

public class ReplyCommentResponse extends BasicResponse{
	private List<CommentInfo> commentInfoList;

	public List<CommentInfo> getCommentInfoList() {
		return commentInfoList;
	}

	public void setCommentInfoList(List<CommentInfo> commentInfoList) {
		this.commentInfoList = commentInfoList;
	}

	
	

}
