package com.platform.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platform.data.ApiResult;
import com.platform.data.ApiResultFactory;
import com.platform.data.ApiResultInfo;
import com.platform.rmodel.authority.StudentAuthorityResponse;
import com.platform.service.authority.AuthorityService;

@Controller
public class AuthorityController {
	private Logger logger = Logger.getLogger(AuthorityController.class);
	@Autowired
	private AuthorityService authorityService;

	public AuthorityService getAuthorityService() {
		return authorityService;
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	@RequestMapping("student_authority")
	private @ResponseBody ApiResult getStudentAuthority(HttpServletRequest requestHttp) {
		StudentAuthorityResponse response = null;
		try {
			logger.debug("start to get student authority using authorityService");
			response = authorityService.getStudentAuthority();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(ApiResultInfo.ResultMsg.ServerError);
			return ApiResultFactory.getServerError();
		}
		// 判断服务是否正常返回
		if (response == null) {
			logger.error("student authority response is null");
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
