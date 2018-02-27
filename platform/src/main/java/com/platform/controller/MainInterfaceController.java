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
import com.platform.rmodel.notice.IndexInfoResponse;
import com.platform.service.works.WorksService;

@Controller
public class MainInterfaceController {
	private Logger logger = Logger.getLogger(MainInterfaceController.class);
	@Autowired
	private WorksService worksService;

	public WorksService getWorksService() {
		return worksService;
	}

	public void setWorksService(WorksService worksService) {
		this.worksService = worksService;
	}

	@RequestMapping("index")
	private @ResponseBody ApiResult getIndexInfo(HttpServletRequest requestHttp) {
		IndexInfoResponse response = null;

		try {
			logger.debug(" start to get index info  using worksService");
			response = worksService.getIndexInfo();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(ApiResultInfo.ResultMsg.ServerError);
			return ApiResultFactory.getServerError();
		}
		// 判断服务是否正常返回
		if (response == null) {
			logger.error("IndexInfo  response is null and fail to get the IndexInfoResponse");
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
