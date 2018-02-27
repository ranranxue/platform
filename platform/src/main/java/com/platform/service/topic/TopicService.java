package com.platform.service.topic;

import com.platform.rmodel.topic.CommentRequest;
import com.platform.rmodel.topic.CommentResponse;
import com.platform.rmodel.topic.ReplyCommentResponse;
import com.platform.rmodel.topic.ReplyTopicRequest;
import com.platform.rmodel.topic.ReplyTopicResponse;
import com.platform.rmodel.topic.TopicCreateRequest;
import com.platform.rmodel.topic.TopicCreateResponse;
import com.platform.rmodel.topic.TopicDeleteRequest;
import com.platform.rmodel.topic.TopicDeleteResponse;
import com.platform.rmodel.topic.TopicDetailRequest;
import com.platform.rmodel.topic.TopicDetailResponse;
import com.platform.rmodel.topic.TopicListResponse;

public interface TopicService {
	/**
	 * 得到所有话题的列表,
	 * attention:如果是管理员那么论坛即使禁言也可以获取列表信息，但是如果是普通用户一旦禁言之后不可以获取用户列表，提示论坛关闭状态
	 * @return
	 */
	public TopicListResponse getTopicList();
	/**
	 * 创建新的讨论话题,如果禁言了，返回讨论话题id为0
	 * @param request
	 * @return  
	 */
	public TopicCreateResponse createTopic(TopicCreateRequest request);
	/**
	 * 获取一个特定的讨论话题的详细内容
	 * @param request
	 * @return
	 */
	public TopicDetailResponse getTopicDetail(TopicDetailRequest request);
	/**
	 * 获取回复帖子的评论
	 * @param request
	 * @return
	 */
	public ReplyCommentResponse getCommentList(TopicDetailRequest request);
	/**
	 * 对回复的帖子发表评论
	 * @param request
	 * @return
	 */
	public CommentResponse commentTopic(CommentRequest request);
	/**
	 * 针对某个特定的讨论话题给出回帖
	 * @param request
	 * @return
	 */
	public ReplyTopicResponse insertTopicReply(ReplyTopicRequest request);
	/**
	 * 删除讨论的讨论的话题，回帖或者评论
	 * @param request
	 * @return
	 */
	public TopicDeleteResponse deleteTopic(TopicDeleteRequest request);
	
	
	

}
