package com.platform.service.topic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.dao.CommentDAO;
import com.platform.dao.Stu_limitDAO;
import com.platform.dao.TopicDAO;
import com.platform.dao.UserDAO;
import com.platform.data.ApiResultInfo;
import com.platform.data.StaticData;
import com.platform.data.TimeData;
import com.platform.model.Comment;
import com.platform.model.Topic;
import com.platform.model.User;
import com.platform.rmodel.topic.CommentInfo;
import com.platform.rmodel.topic.CommentRequest;
import com.platform.rmodel.topic.CommentResponse;
import com.platform.rmodel.topic.ReplyCommentResponse;
import com.platform.rmodel.topic.ReplyTopic;
import com.platform.rmodel.topic.ReplyTopicRequest;
import com.platform.rmodel.topic.ReplyTopicResponse;
import com.platform.rmodel.topic.TopicCreateRequest;
import com.platform.rmodel.topic.TopicCreateResponse;
import com.platform.rmodel.topic.TopicDeleteRequest;
import com.platform.rmodel.topic.TopicDeleteResponse;
import com.platform.rmodel.topic.TopicDetailRequest;
import com.platform.rmodel.topic.TopicDetailResponse;
import com.platform.rmodel.topic.TopicInfo;
import com.platform.rmodel.topic.TopicListResponse;
import com.platform.util.TimeUtil;

@Service("topicService")
public class TopicServiceImpl implements TopicService {
	private Logger logger = Logger.getLogger(TopicServiceImpl.class);
	@Autowired
	private TopicDAO topicDAO;
	@Autowired
	private CommentDAO commentDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private Stu_limitDAO stu_limitDAO;
	

	public Stu_limitDAO getStu_limitDAO() {
		return stu_limitDAO;
	}

	public void setStu_limitDAO(Stu_limitDAO stu_limitDAO) {
		this.stu_limitDAO = stu_limitDAO;
	}
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public TopicDAO getTopicDAO() {
		return topicDAO;
	}

	public void setTopicDAO(TopicDAO topicDAO) {
		this.topicDAO = topicDAO;
	}

	public CommentDAO getCommentDAO() {
		return commentDAO;
	}

	public void setCommentDAO(CommentDAO commentDAO) {
		this.commentDAO = commentDAO;
	}

	public TopicListResponse getTopicList() {
		// TODO Auto-generated method stub
		List<TopicInfo> topicInfoList = new ArrayList<TopicInfo>();
		List<Topic> topicList = null;
		try {
			logger.debug("get topic list from topic db ");
			topicList = topicDAO.getTopicList();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("topicService  error", e);
			TopicListResponse response = new TopicListResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (topicList == null) {
			logger.debug("fail to get topic List");
			TopicListResponse response = new TopicListResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		} else if (topicList.size() == 0) {
			topicInfoList = new ArrayList<TopicInfo>();
		} else {
			for (int i = 0; i < topicList.size(); i++) {
				TopicInfo topicInfo = new TopicInfo();
				topicInfo.setId(topicList.get(i).getId());
				topicInfo.setTitle(topicList.get(i).getTitle());
				String author_name = "";
				try {

					logger.debug("get author_name from user db using author_id" + topicList.get(i).getAuthor_id());
					author_name = userDAO.getNameById(topicList.get(i).getAuthor_id());
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("topicService  error", e);
					TopicListResponse response = new TopicListResponse();
					response.setCode(ApiResultInfo.ResultCode.ServerError);
					response.setMsg(ApiResultInfo.ResultMsg.ServerError);
					return response;
				}
				if ("".equals(author_name)) {
					logger.debug("fail to get author_name");
					TopicListResponse response = new TopicListResponse();
					response.setCode(ApiResultInfo.ResultCode.ServerError);
					response.setMsg(ApiResultInfo.ResultMsg.ServerError);
					return response;
				} else {
					topicInfo.setAuthor_name(author_name);
				}
				Integer totalNum = -1;
				try {
					logger.debug(" get reply num from topic db using topic _id" + topicList.get(i).getId());
					totalNum = topicDAO.getReplyNum(topicList.get(i).getId());
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("topicService  error", e);
					TopicListResponse response = new TopicListResponse();
					response.setCode(ApiResultInfo.ResultCode.ServerError);
					response.setMsg(ApiResultInfo.ResultMsg.ServerError);
					return response;
				}
				if (totalNum == -1) {
					logger.debug("fail to get replyNum from topic db");
					TopicListResponse response = new TopicListResponse();
					response.setCode(ApiResultInfo.ResultCode.ServerError);
					response.setMsg(ApiResultInfo.ResultMsg.ServerError);
					return response;
				} else {
					topicInfo.setTotalNum(totalNum);
				}

				topicInfo.setCreate_time(topicList.get(i).getCreate_time());
				topicInfoList.add(topicInfo);
			}
		}
		Integer is_forum=-1;
		try {
			logger.debug("get stu_forum limit from stu_limit ");
			is_forum=stu_limitDAO.getForum();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("stu_limitDAO error", e);
			TopicListResponse response = new TopicListResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if(is_forum==-1){
			logger.debug("get the forum failed !");
			TopicListResponse response = new TopicListResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;	
		}
		logger.debug(" get the topicInfoList successfully!");
		TopicListResponse response = new TopicListResponse();
		response.setCode(0);
		response.setMsg(" get the topicInfoList successfully!");
		response.setTopicInfoList(topicInfoList);
		response.setIs_forum(is_forum);
		return response;
	}



	public TopicCreateResponse createTopic(TopicCreateRequest request) {
		// TODO Auto-generated method stub
		Integer insertNum = 0;
		Topic topic = new Topic();
		topic.setTitle(request.getTitle());
		topic.setContent(request.getContent());
		topic.setAuthor_id(request.getStuid());
		topic.setCreate_time(TimeUtil.getCurrentTime(TimeData.TimeFormat.YMDHM));
		logger.debug(request.getStuid());
		logger.debug("&&&&&&&"+topic.getAuthor_id());
		try {
			logger.debug(" insert into topic from topic db ");
			insertNum = topicDAO.insertTopic(topic);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("topicDAO error", e);
			TopicCreateResponse response = new TopicCreateResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;

		}
		if (insertNum == 0) {
			logger.debug("fail to insert topic");
			TopicCreateResponse response = new TopicCreateResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		String author_name = "";
		try {
			logger.debug(" get the author_name from user db using stuid" + request.getStuid());
			author_name = userDAO.getNameById(request.getStuid());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("userDAO error", e);
			TopicCreateResponse response = new TopicCreateResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;

		}
		if ("".equals(author_name)) {
			logger.debug("fail to get the author_name");
			TopicCreateResponse response = new TopicCreateResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		logger.debug(" create topic successfully!");
		TopicCreateResponse response = new TopicCreateResponse();
		response.setCode(0);
		response.setMsg(" create topic successfully!");
		response.setId(topic.getId());
		response.setTitle(request.getTitle());
		response.setAuthor_id(request.getStuid());
		response.setAuthor_name(author_name);
		response.setCreate_time(TimeUtil.getCurrentTime(TimeData.TimeFormat.YMDHM));
		response.setTotalNum(0);
		return response;

	}

	public TopicDetailResponse getTopicDetail(TopicDetailRequest request) {
		// TODO Auto-generated method stub
		Topic topic = null;
		try {
			logger.debug(" get the topic detail from topic db using topic_id" + request.getTopic_id());
			topic = topicDAO.getTopicDetail(request.getTopic_id());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("topicDAO error", e);
			TopicDetailResponse response = new TopicDetailResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (topic == null) {
			logger.debug("fail to get the topic detail");
			TopicDetailResponse response = new TopicDetailResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}

		User user = null;
		try {
			logger.debug("get the author_name and author_url from user db using suthor_id" + topic.getAuthor_id());
			user = userDAO.getUserInfo(topic.getAuthor_id());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("userDAO error", e);
			TopicDetailResponse response = new TopicDetailResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;

		}
		if (user == null) {
			logger.debug(" fail to get the userInfo from user db");
			TopicDetailResponse response = new TopicDetailResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		
		logger.debug("*****发帖的用户名"+user.getName()); 
		
		List<Topic> topicList = null;
		try {
			logger.debug(" get the topicList from db using topic_id" + request.getTopic_id());
			topicList = topicDAO.getReplyTopicInfo(request.getTopic_id());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("topicDAO error", e);
			TopicDetailResponse response = new TopicDetailResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (topicList == null) {
			logger.debug(" fail to get the reply tpic info");
			TopicDetailResponse response = new TopicDetailResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;

		}
		List<ReplyTopic> replyTopicList = new ArrayList<ReplyTopic>();
		for (int i = 0; i < topicList.size(); i++) {
			ReplyTopic replyTopic = new ReplyTopic();
			replyTopic.setId(topicList.get(i).getId());
			replyTopic.setContent(topicList.get(i).getContent());
			User replyUser = null;
			try {
				logger.debug("get the author_name and author_url from user db using suthor_id"
						+ topicList.get(i).getAuthor_id());
				replyUser = userDAO.getUserInfo(topicList.get(i).getAuthor_id());
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("userDAO error", e);
				TopicDetailResponse response = new TopicDetailResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;

			}
			if (replyUser == null) {
				logger.debug(" fail to get the userInfo from user db");
				TopicDetailResponse response = new TopicDetailResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
			}
			replyTopic.setAuthor_id(topicList.get(i).getAuthor_id());
			replyTopic.setAuthor_name(replyUser.getName());
			logger.debug("******回帖子的用户名"+replyUser.getName());
			replyTopic.setAuthor_url(StaticData.QiNiuFilePath + replyUser.getHead_url());
			replyTopic.setGood(topicList.get(i).getGood());
			replyTopic.setBad(topicList.get(i).getBad());
			replyTopic.setCreate_time(topicList.get(i).getCreate_time());
			replyTopic.setReal_name(topicList.get(i).getReal_name());
			Integer commentNum = -1;
			try {
				logger.debug(" get the commentNum from comment db using reply_id" + topicList.get(i).getId());
				commentNum = commentDAO.getCommentNum(topicList.get(i).getId());
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("commentDAO error", e);
				TopicDetailResponse response = new TopicDetailResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;

			}
			if (commentNum == -1) {
				logger.debug(" fail to get the commentNum from comment db");
				TopicDetailResponse response = new TopicDetailResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
			}
			replyTopic.setCommentNum(commentNum);
			//接受每一个回复帖子的所有评论
			
			List<Comment> commentList = null;
			try {
				logger.debug(" get commentList from comment db using topic_id" + topicList.get(i).getId());
				commentList = commentDAO.getCommentList(topicList.get(i).getId());
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("commentDAO error", e);
				TopicDetailResponse response = new TopicDetailResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
			}
			if (commentList == null) {
				logger.debug("fail to get the commentList");
				TopicDetailResponse response = new TopicDetailResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
			}
			List<CommentInfo> commentInfoList = new ArrayList<CommentInfo>();
			for (int j = 0; j < commentList.size(); j++) {
				CommentInfo comment = new CommentInfo();
				comment.setId(commentList.get(j).getId());
				comment.setContent(commentList.get(j).getContent());
				comment.setAuthor_id(commentList.get(j).getAuthor_id());
				String author_name = "";
				try {
					logger.debug("get the author_name from user db using author_id" + commentList.get(j).getAuthor_id());
					author_name = userDAO.getNameById(commentList.get(j).getAuthor_id());
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("userDAO error", e);
					TopicDetailResponse response = new TopicDetailResponse();
					response.setCode(ApiResultInfo.ResultCode.ServerError);
					response.setMsg(ApiResultInfo.ResultMsg.ServerError);
					return response;
				}
				if ("".equals(author_name)) {
					logger.debug("fail to get the author_name");
					TopicDetailResponse response = new TopicDetailResponse();
					response.setCode(ApiResultInfo.ResultCode.ServerError);
					response.setMsg(ApiResultInfo.ResultMsg.ServerError);
					return response;
				}
				comment.setAuthor_name(author_name);
				logger.debug("******评论的用户名"+author_name);
				comment.setCreate_time(commentList.get(j).getCreate_time());
				commentInfoList.add(comment);
			}
			replyTopic.setCommentInfoList(commentInfoList);
			replyTopicList.add(replyTopic);
		}
		Integer is_forum=-1;
		try {
			logger.debug("get stu_forum limit from stu_limit ");
			is_forum=stu_limitDAO.getForum();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("stu_limitDAO error", e);
			TopicDetailResponse response = new TopicDetailResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if(is_forum==-1){
			logger.debug("get the forum failed !");
			TopicDetailResponse response = new TopicDetailResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;	
		}
		logger.debug("get the topic detail successfully!");
		TopicDetailResponse response = new TopicDetailResponse();
		response.setCode(0);
		response.setMsg("get the topic detail successfully!");
		response.setTopic_id(topic.getId());
		response.setTitle(topic.getTitle());
		response.setContent(topic.getContent());
		response.setAuthor_id(topic.getAuthor_id());
		response.setAuthor_name(user.getName());
		
		
		
		response.setAuthor_url(StaticData.QiNiuFilePath + user.getHead_url());
		response.setGood(topic.getGood());
		response.setBad(topic.getBad());
		response.setCreate_time(topic.getCreate_time());
		response.setReplyNum(topicList.size());
		response.setReplyTopicList(replyTopicList);
		response.setIs_forum(is_forum);
		return response;
	}

	public ReplyCommentResponse getCommentList(TopicDetailRequest request) {
		// TODO Auto-generated method stub
		List<Comment> commentList = null;
		try {
			logger.debug(" get commentList from comment db using topic_id" + request.getTopic_id());
			commentList = commentDAO.getCommentList(request.getTopic_id());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("commentDAO error", e);
			ReplyCommentResponse response = new ReplyCommentResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (commentList == null) {
			logger.debug("fail to get the commentList");
			ReplyCommentResponse response = new ReplyCommentResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		List<CommentInfo> commentInfoList = new ArrayList<CommentInfo>();
		for (int i = 0; i < commentList.size(); i++) {
			CommentInfo comment = new CommentInfo();
			comment.setId(commentList.get(i).getId());
			comment.setContent(commentList.get(i).getContent());
			comment.setAuthor_id(commentList.get(i).getAuthor_id());
			String author_name = "";

			try {
				logger.debug("get the author_name from user db using author_id" + commentList.get(i).getAuthor_id());
				author_name = userDAO.getNameById(commentList.get(i).getAuthor_id());
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("userDAO error", e);
				ReplyCommentResponse response = new ReplyCommentResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
			}
			if ("".equals(author_name)) {
				logger.debug("fail to get the author_name");
				ReplyCommentResponse response = new ReplyCommentResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
			}
			comment.setAuthor_name(author_name);
			comment.setCreate_time(commentList.get(i).getCreate_time());
			commentInfoList.add(comment);

		}
		logger.debug(" get the reply topic commentList successfully!");
		ReplyCommentResponse response = new ReplyCommentResponse();
		response.setCode(0);
		response.setMsg(" get the reply topic commentList successfully!");
		response.setCommentInfoList(commentInfoList);
		return response;
	}

	public CommentResponse commentTopic(CommentRequest request) {
		// TODO Auto-generated method stub
		Comment comment = new Comment();
		comment.setContent(request.getContent());
		comment.setAuthor_id(request.getStuid());
		comment.setReply_id(request.getTopic_id());
		comment.setCreate_time(TimeUtil.getCurrentTime(TimeData.TimeFormat.YMDHM));
		Integer insertNum = -1;
		try {
			logger.debug(" insert comment into comment db using comment");
			insertNum = commentDAO.insertComment(comment);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("commentDAO error", e);
			CommentResponse response = new CommentResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (insertNum == -1 || insertNum == 0) {
			logger.debug("fail to insert a comment");
			CommentResponse response = new CommentResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		String author_name = "";
		try {
			logger.debug(" get author_name from user db using stuid" + request.getStuid());
			author_name = userDAO.getNameById(request.getStuid());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("userDAo error", e);
			CommentResponse response = new CommentResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if ("".equals(author_name)) {
			logger.debug("fail to get author_name from user db");
			CommentResponse response = new CommentResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		logger.debug("insert the comment successfully!");
		CommentResponse response = new CommentResponse();

		response.setCode(0);
		response.setMsg("insert the comment successfully!");
		response.setId(comment.getId());
		response.setContent(request.getContent());
		response.setAuthor_id(request.getStuid());
		response.setAuthor_name(author_name);
		response.setCreate_time(comment.getCreate_time());

		return response;
	}

	public ReplyTopicResponse insertTopicReply(ReplyTopicRequest request) {
		// TODO Auto-generated method stub
		Topic topic = new Topic();
		topic.setContent(request.getContent());
		topic.setAuthor_id(request.getStuid());
		topic.setReply_id(request.getReply_id());
		topic.setCreate_time(TimeUtil.getCurrentTime(TimeData.TimeFormat.YMDHM));
		topic.setReal_name(request.getReal_name());
		Integer insertNum = -1;
		try {
			logger.debug(" insert topic into topic db using topic");
			insertNum = topicDAO.insertTopicReply(topic);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("topicDAO error", e);
			ReplyTopicResponse response = new ReplyTopicResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (insertNum == -1 || insertNum == 0) {
			logger.debug("fail to insert topic reply!");
			ReplyTopicResponse response = new ReplyTopicResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		User user = null;
		try {
			logger.debug("get user info from user db using stuid" + request.getStuid());
			user = userDAO.getUserInfo(request.getStuid());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("userDAO error", e);
			ReplyTopicResponse response = new ReplyTopicResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (user == null) {
			logger.debug("fail to get the user info");
			ReplyTopicResponse response = new ReplyTopicResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		logger.debug(" insert topic reply successfully!");
		ReplyTopicResponse response = new ReplyTopicResponse();
		response.setCode(0);
		response.setMsg(" insert topic reply successfully!");
		response.setTopic_id(topic.getId());
		response.setContent(request.getContent());
		response.setAuthor_id(request.getStuid());
		response.setAuthor_name(user.getName());
		response.setAuthor_url(StaticData.QiNiuFilePath + user.getHead_url());
		response.setCreate_time(topic.getCreate_time());
		response.setGood(0);
		response.setBad(0);
		response.setReplyNum(0);
		return response;
	}

	public TopicDeleteResponse deleteTopic(TopicDeleteRequest request) {
		// TODO Auto-generated method stub
		if (request.getStatus() == 0) {
			// 删除话题
			List<Integer> replyIdList = null;
			try {
				logger.debug(" get reply topic id list from topic db using topic_id" + request.getId());
				replyIdList = topicDAO.getReplyId(request.getId());
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("topicDAO error", e);
				TopicDeleteResponse response = new TopicDeleteResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
			}
			if (replyIdList == null) {
				logger.debug(" fail to get replyIdList ");
				TopicDeleteResponse response = new TopicDeleteResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;

			} else {
				// 不管有没有回帖，直接删除回帖和话题即可
				Integer deleteNum = 0;
				try {
					logger.debug(
							" start to delete the topic and replyTopic from topic db using topic_id" + request.getId());
					deleteNum = topicDAO.deleteTopic(request.getId());
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("topicDAO error", e);
					TopicDeleteResponse response = new TopicDeleteResponse();
					response.setCode(ApiResultInfo.ResultCode.ServerError);
					response.setMsg(ApiResultInfo.ResultMsg.ServerError);
					return response;

				}
				if (deleteNum == 0) {
					logger.debug("fail to delete topic and replyTopic");
					TopicDeleteResponse response = new TopicDeleteResponse();
					response.setCode(ApiResultInfo.ResultCode.ServerError);
					response.setMsg(ApiResultInfo.ResultMsg.ServerError);
					return response;
				}
				if (replyIdList.size() > 0) {
					// 删除回帖的评论
					try {
						logger.debug("delete replyTopic's comment from comment db using replyIdList");
						commentDAO.deleteCommentByMutiReplyId(replyIdList);
					} catch (Exception e) {
						// TODO: handle exception
						logger.error("commentDAO error", e);
						TopicDeleteResponse response = new TopicDeleteResponse();
						response.setCode(ApiResultInfo.ResultCode.ServerError);
						response.setMsg(ApiResultInfo.ResultMsg.ServerError);
						return response;
					}
				}

			}

		} else if (request.getStatus() == 1) {
			// 删除回帖
			Integer deleteReplyNum = 0;
			try {
				logger.debug("delete the reply topic from topic db using topic_id" + request.getId());
				deleteReplyNum = topicDAO.deleteReply(request.getId());
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("topicDAO error", e);
				TopicDeleteResponse response = new TopicDeleteResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
			}
			if (deleteReplyNum == 0) {
				logger.debug("fail to delete the reply topic");
				TopicDeleteResponse response = new TopicDeleteResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
			}
			Integer commentNum = -1;
			try {
				logger.debug(" get total commentNum from comment db using reply_id" + request.getId());
				commentNum = commentDAO.getCommentNum(request.getId());
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("commentDAO error", e);
				TopicDeleteResponse response = new TopicDeleteResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
			}
			if (commentNum > 0) {
				// 有评论，删除该回帖的评论
				Integer deleteCommentNum = -1;
				try {
					logger.debug(" delete all reply topic  comment from comment db using reply_id" + request.getId());
					deleteCommentNum = commentDAO.deleteCommentByReplyId(request.getId());
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("commentDAO error", e);
					TopicDeleteResponse response = new TopicDeleteResponse();
					response.setCode(ApiResultInfo.ResultCode.ServerError);
					response.setMsg(ApiResultInfo.ResultMsg.ServerError);
					return response;
				}
				if (deleteCommentNum == 0) {
					logger.debug("fail to delete comment");
					TopicDeleteResponse response = new TopicDeleteResponse();
					response.setCode(ApiResultInfo.ResultCode.ServerError);
					response.setMsg(ApiResultInfo.ResultMsg.ServerError);
					return response;
				}
			}
		} else {
			// 删除评论
			Integer deleteCommentNum = -1;
			try {
				logger.debug(" delete all reply topic  comment from comment db using reply_id" + request.getId());
				deleteCommentNum = commentDAO.deleteCommentById(request.getId());
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("commentDAO error", e);
				TopicDeleteResponse response = new TopicDeleteResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
			}
			if (deleteCommentNum == 0) {
				logger.debug("fail to delete comment");
				TopicDeleteResponse response = new TopicDeleteResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
			}

		}
		logger.debug(" delete topic successfully!");
		TopicDeleteResponse response = new TopicDeleteResponse();
		response.setCode(0);
		response.setMsg(" delete topic successfully!");
		response.setId(request.getId());
		return response;
	}

}
