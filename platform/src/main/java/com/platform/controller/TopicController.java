package com.platform.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platform.data.ApiResult;
import com.platform.data.ApiResultFactory;
import com.platform.data.ApiResultInfo;
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
import com.platform.service.topic.TopicService;
import com.platform.util.DataTypePaserUtil;
import com.platform.util.RedisUtil;
import com.platform.util.RequestUtil;

@Controller
public class TopicController {
	private Logger logger = Logger.getLogger(TopicController.class);
	@Autowired
	private TopicService topicService;

	public TopicService getTopicService() {
		return topicService;
	}

	public void setTopicService(TopicService topicService) {
		this.topicService = topicService;
	}

	@RequestMapping("topiclist")
	private @ResponseBody ApiResult getTopicList(HttpServletRequest requestHttp) {
		TopicListResponse response = null;
		try {
			logger.debug(" start to get the topic list from topicService");
			response = topicService.getTopicList();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(ApiResultInfo.ResultMsg.ServerError);
			return ApiResultFactory.getServerError();
		}
		// 判断服务是否正常返回
		if (response == null) {

			logger.error("topic list response is null");
			return ApiResultFactory.getServerError();
		}

		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {

			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		return new ApiResult(response);
	}



	@RequestMapping("createtopic")
	private @ResponseBody ApiResult topicCreate(HttpServletRequest requestHttp) {
		Map<String, String> requestParams = RequestUtil.getParameterMap(requestHttp);
		String[] paras = { "title", "content", "ticket" };
		boolean flag = RequestUtil.validate(paras, requestParams);

		if (flag == false) {
			logger.error(ApiResultInfo.ResultMsg.RequiredParasError);
			return ApiResultFactory.getLackParasError();
		}
		String stuid = RedisUtil.get(requestParams.get(paras[2]));
		if(stuid.equals(null)){
			logger.error("sessionkey invalid !");
			return ApiResultFactory.getSessionKeyError();
		}
		TopicCreateRequest request = new TopicCreateRequest();
		request.setStuid(stuid);
		request.setTitle(requestParams.get(paras[0]));
		request.setContent(requestParams.get(paras[1]));
		TopicCreateResponse response = null;
		try {
			logger.debug(" start to get the topicCreate response");
			response = topicService.createTopic(request);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(ApiResultInfo.ResultMsg.ServerError);
			return ApiResultFactory.getServerError();
		}
		// 判断服务是否正常返回
		if (response == null) {

			logger.error("create topic  response is null");
			return ApiResultFactory.getServerError();
		}

		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {

			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		return new ApiResult(response);

	}

	@RequestMapping("topicdetail")
	private @ResponseBody ApiResult topicDetail(HttpServletRequest requestHttp) {
		Map<String, String> requestParams = RequestUtil.getParameterMap(requestHttp);
		String[] paras = { "topic_id" };
		boolean flag = RequestUtil.validate(paras, requestParams);
		if (flag == false) {
			logger.error(ApiResultInfo.ResultMsg.RequiredParasError);
			return ApiResultFactory.getLackParasError();
		}
		TopicDetailRequest request = new TopicDetailRequest();
		request.setTopic_id(DataTypePaserUtil.StringToInteger(requestParams.get(paras[0])));
		TopicDetailResponse response = null;
		try {
			logger.debug("get the topic detail response from topicService");
			response = topicService.getTopicDetail(request);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(ApiResultInfo.ResultMsg.ServerError);
			return ApiResultFactory.getServerError();
		}
		// 判断服务是否正常返回
		if (response == null) {

			logger.error("topic detail  response is null");
			return ApiResultFactory.getServerError();
		}

		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {

			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		return new ApiResult(response);

	}

	@RequestMapping("commentinfo")
	private @ResponseBody ApiResult getCommentInfo(HttpServletRequest requestHttp) {
		Map<String, String> requestParams = RequestUtil.getParameterMap(requestHttp);
		String[] paras = { "topic_id" };
		boolean flag = RequestUtil.validate(paras, requestParams);
		if (flag == false) {
			logger.error(ApiResultInfo.ResultMsg.RequiredParasError);
			return ApiResultFactory.getLackParasError();
		}
		TopicDetailRequest request = new TopicDetailRequest();
		request.setTopic_id(DataTypePaserUtil.StringToInteger(requestParams.get(paras[0])));
		ReplyCommentResponse response = null;
		try {
			logger.debug(" get the commentList from topic Service");
			response = topicService.getCommentList(request);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(ApiResultInfo.ResultMsg.ServerError);
			return ApiResultFactory.getServerError();
		}
		// 判断服务是否正常返回
		if (response == null) {

			logger.error("commentList response is null");
			return ApiResultFactory.getServerError();
		}

		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {

			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		return new ApiResult(response);

	}

	@RequestMapping("comment")
	private @ResponseBody ApiResult comment(HttpServletRequest requestHttp) {
		Map<String, String> requestParams = RequestUtil.getParameterMap(requestHttp);
		String[] paras = { "topic_id", "content", "ticket" };
		boolean flag = RequestUtil.validate(paras, requestParams);
		if (flag == false) {
			logger.error(ApiResultInfo.ResultMsg.RequiredParasError);
			return ApiResultFactory.getLackParasError();
		}
		String stuid = RedisUtil.get(requestParams.get(paras[2]));
		CommentRequest request = new CommentRequest();
		request.setStuid(stuid);
		request.setTopic_id(DataTypePaserUtil.StringToInteger(requestParams.get(paras[0])));
		request.setContent(requestParams.get(paras[1]));
		CommentResponse response = null;
		try {
			logger.debug(" get the commentList from topic Service");
			response = topicService.commentTopic(request);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(ApiResultInfo.ResultMsg.ServerError);
			return ApiResultFactory.getServerError();
		}
		// 判断服务是否正常返回
		if (response == null) {

			logger.error("comment response is null");
			return ApiResultFactory.getServerError();
		}

		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {

			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		return new ApiResult(response);

	}

	@RequestMapping("replytopic")
	private @ResponseBody ApiResult insertReplyTopic(HttpServletRequest requestHttp) {
		Map<String, String> requestParams = RequestUtil.getParameterMap(requestHttp);
		String[] paras = { "ticket", "content", "reply_id", "real_name" };
		boolean flag = RequestUtil.validate(paras, requestParams);
		if (flag == false) {
			logger.error(ApiResultInfo.ResultMsg.RequiredParasError);
			return ApiResultFactory.getLackParasError();
		}
		String stuid = RedisUtil.get(requestParams.get(paras[0]));
		ReplyTopicRequest request = new ReplyTopicRequest();
		request.setStuid(stuid);
		request.setContent(requestParams.get(paras[1]));
		request.setReply_id(DataTypePaserUtil.StringToInteger(requestParams.get(paras[2])));
		request.setReal_name(DataTypePaserUtil.StringToInteger(requestParams.get(paras[3])));

		ReplyTopicResponse response = null;
		try {
			logger.debug("insert reply topic using topicService");
			response = topicService.insertTopicReply(request);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(ApiResultInfo.ResultMsg.ServerError);
			return ApiResultFactory.getServerError();
		}
		// 判断服务是否正常返回
		if (response == null) {

			logger.error("topic reply  response is null");
			return ApiResultFactory.getServerError();
		}
		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {

			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		return new ApiResult(response);

	}

	@RequestMapping("topic/delete")
	private @ResponseBody ApiResult deleteTopicInfo(HttpServletRequest requestHttp) {
		Map<String, String> requestParams = RequestUtil.getParameterMap(requestHttp);
		String[] paras = { "id", "status" };
		boolean flag = RequestUtil.validate(paras, requestParams);
		if (flag == false) {
			logger.error(ApiResultInfo.ResultMsg.RequiredParasError);
			return ApiResultFactory.getLackParasError();
		}
		TopicDeleteRequest request = new TopicDeleteRequest();
		request.setId(DataTypePaserUtil.StringToInteger(requestParams.get(paras[0])));
		request.setStatus(DataTypePaserUtil.StringToInteger(requestParams.get(paras[1])));
		TopicDeleteResponse response = null;
		try {
			logger.debug("delete topic infousing topicService ");
			response = topicService.deleteTopic(request);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(ApiResultInfo.ResultMsg.ServerError);
			return ApiResultFactory.getServerError();

		}
		// 判断服务是否正常返回
		if (response == null) {

			logger.error(" delete topic response is null");
			return ApiResultFactory.getServerError();
		}
		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {

			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		return new ApiResult(response);

	}

}
