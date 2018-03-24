package com.platform.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platform.data.ApiResult;
import com.platform.data.ApiResultFactory;
import com.platform.data.ApiResultInfo;
import com.platform.model.BasicResponse;
import com.platform.rmodel.work.UploadWorksRequest;
import com.platform.rmodel.work.UploadWorksResponse;
import com.platform.rmodel.work.WorksDeleteRequest;
import com.platform.rmodel.work.WorksListResponse;
import com.platform.service.works.WorksService;
import com.platform.util.DataTypePaserUtil;
import com.platform.util.RedisUtil;
import com.platform.util.RequestUtil;

@Controller
public class WorksController {
	private Logger logger = Logger.getLogger(WorksController.class);
	@Autowired
	private WorksService worksService;

	public WorksService getWorksService() {
		return worksService;
	}

	public void setWorksService(WorksService worksService) {
		this.worksService = worksService;
	}

	@RequestMapping("works/delete")
	private @ResponseBody ApiResult deleteWorks(HttpServletRequest requestHttp, HttpServletResponse responseHttp,
			@RequestParam(value = "deleteList[]", required = false) List<String> worksIdList) throws IOException {
		
		
		WorksDeleteRequest request = new WorksDeleteRequest();
		Integer worksIdLength=worksIdList.size();
		List<Integer> idList=new ArrayList<Integer>();
		for(int i=0;i<worksIdLength;i++){
			idList.add(DataTypePaserUtil.StringToInteger(worksIdList.get(i)));
		}
		
		BasicResponse response = null;
		try {
			logger.debug(" delete the works using worksService");
			response = worksService.deleteWorks(request);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(ApiResultInfo.ResultMsg.ServerError);
			return ApiResultFactory.getServerError();
		}
		// 判断服务是否正常返回
		if (response == null) {
			logger.debug("fail to get delete response from worksService");
			return ApiResultFactory.getServerError();
		}
		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {
			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		return new ApiResult(response);

	}

	@RequestMapping("works")
	private @ResponseBody ApiResult showExWorksList(HttpServletRequest requestHttp) {
		
		// request.setWorks_id(DataTypePaserUtil.StringToInteger(requestParams.get(paras[0])));
		WorksListResponse response = null;
		try {
			logger.debug(" get the worksList using worksService");
			response = worksService.getWorksList();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(ApiResultInfo.ResultMsg.ServerError);
			return ApiResultFactory.getServerError();
		}
		// 判断服务是否正常返回
		if (response == null) {
			logger.debug("fail to get the response from worksService");
			return ApiResultFactory.getServerError();
		}
		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {
			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		return new ApiResult(response);

	}

	@RequestMapping("uploadworks")
	private @ResponseBody ApiResult uploadWorks(HttpServletRequest requestHttp) {
		Map<String, String> requestParams = RequestUtil.getParameterMap(requestHttp);
		String[] paras = { "ticket", "title", "description", "label", "format", "works_url" };
		boolean flag = RequestUtil.validate(paras, requestParams);
		if (flag == false) {
			logger.error(ApiResultInfo.ResultMsg.RequiredParasError);
			return ApiResultFactory.getLackParasError();
		}
		String stuid = RedisUtil.get(requestParams.get(paras[0]));
		UploadWorksRequest request = new UploadWorksRequest();
		request.setStuid(stuid);
		request.setTitle(requestParams.get(paras[1]));
		request.setDescription(requestParams.get(paras[2]));
		request.setLabel(requestParams.get(paras[3]));
		request.setFormat(DataTypePaserUtil.StringToInteger(requestParams.get(paras[4])));
		request.setWorks_url(requestParams.get(paras[5]));

		UploadWorksResponse response = null;
		try {
			logger.debug("start to upload the works using worksService");
			response = worksService.uploadWorks(request);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(ApiResultInfo.ResultMsg.ServerError);
			return ApiResultFactory.getServerError();
		}
		// 判断服务是否正常返回
		if (response == null) {
			logger.debug("fail to get uploadWorks response from worksService");
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
