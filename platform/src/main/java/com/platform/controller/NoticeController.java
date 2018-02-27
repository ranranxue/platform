package com.platform.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platform.data.ApiResult;
import com.platform.data.ApiResultFactory;
import com.platform.data.ApiResultInfo;
import com.platform.rmodel.notice.CreateNoticeRequest;
import com.platform.rmodel.notice.CreateNoticeResponse;
import com.platform.rmodel.notice.NoticeDeleteRequest;
import com.platform.rmodel.notice.NoticeDeleteResponse;
import com.platform.rmodel.notice.NoticeDetailRequest;
import com.platform.rmodel.notice.NoticeDetailResponse;
import com.platform.rmodel.notice.NoticeListResponse;
import com.platform.service.notice.NoticeService;
import com.platform.util.DataTypePaserUtil;
import com.platform.util.RequestUtil;

@Controller
public class NoticeController {
	private Logger logger = Logger.getLogger(NoticeController.class);
	@Autowired
	private NoticeService noticeService;

	public NoticeService getNoticeService() {
		return noticeService;
	}

	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	@RequestMapping("notices")
	public @ResponseBody ApiResult getAllNotices(HttpServletResponse responseHttp) {
		responseHttp.setHeader("Access-Control-Allow-Origin", "*");
		logger.debug("start to get all notices");
		NoticeListResponse response = null;
		try {
			response = noticeService.getAllNoticesInfo();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("fail to get response", e);
			return ApiResultFactory.getServerError();
		}
		// 判断服务是否正常返回
		if (response == null) {
			logger.error("response is null");
			return ApiResultFactory.getServerError();
		}

		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {

			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		return new ApiResult(response);
	}

	@RequestMapping("notice/detail")
	public @ResponseBody ApiResult getNoticeDetail(HttpServletRequest requestHttp) {
		Map<String, String> requestParams = RequestUtil.getParameterMap(requestHttp);
		String[] paras = { "notice_id" };
		boolean flag = RequestUtil.validate(paras, requestParams);
		if (flag == false) {
			logger.error(ApiResultInfo.ResultMsg.RequiredParasError);
			return ApiResultFactory.getLackParasError();
		}
		NoticeDetailRequest request = new NoticeDetailRequest();
		request.setNotice_id(DataTypePaserUtil.StringToInteger(requestParams.get(paras[0])));

		NoticeDetailResponse response = null;
		try {
			logger.debug(" start to get notice detail using noticeService");
			response = noticeService.getNoticeDetail(request);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(ApiResultInfo.ResultMsg.ServerError);
			return ApiResultFactory.getServerError();
		}
		// 检查服务返回是否正常
		if (response == null) {
			logger.debug("fail to get the notice detail response");
			return ApiResultFactory.getServerError();
		}
		//// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {

			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		return new ApiResult(response);

	}

	@RequestMapping("notice/delete")
	private @ResponseBody ApiResult deleteNotice(HttpServletRequest requestHttp) {
		Map<String, String> requestParams = RequestUtil.getParameterMap(requestHttp);
		String[] paras = { "notice_id" };
		boolean flag = RequestUtil.validate(paras, requestParams);
		if (flag == false) {
			logger.error(ApiResultInfo.ResultMsg.RequiredParasError);
			return ApiResultFactory.getLackParasError();
		}
		NoticeDeleteRequest request = new NoticeDeleteRequest();
		request.setNotice_id(DataTypePaserUtil.StringToInteger(requestParams.get(paras[0])));
		NoticeDeleteResponse response = null;
		try {
			logger.debug(" start to delete notice  using noticeService");
			response = noticeService.deleteNotice(request);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(ApiResultInfo.ResultMsg.ServerError);
			return ApiResultFactory.getServerError();
		}
		// 检查服务返回是否正常
		if (response == null) {
			logger.debug("fail to delete the notice response");
			return ApiResultFactory.getServerError();
		}
		//// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {

			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		return new ApiResult(response);

	}

	@RequestMapping("create/notice")
	private @ResponseBody ApiResult createNotice(HttpServletRequest requestHttp) {
		Map<String, String> requestParams = RequestUtil.getParameterMap(requestHttp);
		String[] paras = { "title", "content", "picture_url", "attachment_name", "attachment_url", "publisher" };
		boolean flag = RequestUtil.validate(paras, requestParams);
		if (flag == false) {
			logger.error(ApiResultInfo.ResultMsg.RequiredParasError);
			return ApiResultFactory.getLackParasError();
		}

		CreateNoticeRequest request = new CreateNoticeRequest();
		request.setTitle(requestParams.get(paras[0]));
		request.setContent(requestParams.get(paras[1]));
		request.setPicture_url(requestParams.get(paras[2]));
		request.setAttachment_name(requestParams.get(paras[3]));
		request.setAttachment_url(requestParams.get(paras[4]));
		request.setPublisher(requestParams.get(paras[5]));

		CreateNoticeResponse response = null;
		try {
			logger.debug(" start to create the notice using noticeService ");
			response = noticeService.createNotice(request);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(ApiResultInfo.ResultMsg.ServerError);
			return ApiResultFactory.getServerError();
		}
		// 检查服务返回是否正常
		if (response == null) {
			logger.debug("fail to create the notice and cann't get response");
			return ApiResultFactory.getServerError();
		}
		//// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {

			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		return new ApiResult(response);

	}

}
