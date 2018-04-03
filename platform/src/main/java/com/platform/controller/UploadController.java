package com.platform.controller;



import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.platform.data.ApiResult;
import com.platform.data.ApiResultFactory;
import com.platform.data.TimeData;
import com.platform.rmodel.upload.UploadFileListResponse;
import com.platform.service.upload.QiNiuService;
import com.platform.util.NamedByTime;
import com.platform.util.TimeUtil;


@Controller
public class UploadController {
	private Logger logger = Logger.getLogger(UploadController.class);
	@Autowired
	private QiNiuService qiNiuService;

	public QiNiuService getQiNiuService() {
		return qiNiuService;
	}

	public void setQiNiuService(QiNiuService qiNiuService) {
		this.qiNiuService = qiNiuService;
	}


	@RequestMapping("upload")
	private @ResponseBody ApiResult uploadAsy(HttpServletRequest requestHttp, HttpServletResponse responseHttp,
			@RequestParam(value = "platformfile[]", required = false) List<MultipartFile> platformfiles) throws IOException {
		responseHttp.setHeader("Access-Control-Allow-Origin", "*");
		List<String> fileOrgNameList=new ArrayList<String>(); 
		List<String> fileNewNameList=new ArrayList<String>();
		if(!platformfiles.isEmpty()){
			for(int i=0;i<platformfiles.size();i++){
				fileOrgNameList.add(platformfiles.get(i).getOriginalFilename());
			}
			for(int i=0;i<fileOrgNameList.size();i++){
				int index = fileOrgNameList.get(i).lastIndexOf(".");
				String newName=NamedByTime.getQiNiuFileName() + "." + fileOrgNameList.get(i).substring(index + 1);
				fileNewNameList.add(newName);
			}	
		}else{
			logger.debug("get the platformfiles error");
			return ApiResultFactory.getLackParasError();
		}
		
		
		try {
			qiNiuService.uploadAsyMutip(platformfiles, fileNewNameList);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("qiNiuService excute error",e);
			return ApiResultFactory.getUploadFileError();
		}
		UploadFileListResponse response = new UploadFileListResponse();
		response.setCode(0);
		response.setMsg("asy-upload the file to qiniu successfully! ");
		response.setFileNameList(fileNewNameList);
		return new ApiResult(response);
	}
	
	@RequestMapping("upload_picture")
	private @ResponseBody ApiResult uploadImg(HttpServletRequest requestHttp, HttpServletResponse responseHttp,
			@RequestParam(value = "wangEditorFile", required=false) List<MultipartFile>  formFiles){
		System.out.println("upload img to service"+TimeUtil.getCurrentTime(TimeData.TimeFormat.YMDHMS));
		responseHttp.setHeader("Access-Control-Allow-Origin", "*");
		List<String> fileNameList = new ArrayList<String>();
		if(!formFiles.isEmpty()){
			fileNameList.add(formFiles.get(0).getOriginalFilename());
		}
		try {
			logger.debug("start to upload the wangEditor picture!");
			qiNiuService.uploadAsyMutip(formFiles, fileNameList);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("qiNiuService excute error",e);
			return ApiResultFactory.getUploadFileError();
		}
		UploadFileListResponse response=new UploadFileListResponse();
		response.setCode(0);
		response.setMsg("upload the wangEditor picture successfully!");
		response.setFileNameList(fileNameList);
		return new ApiResult(response);
	}
	
	
		
}
