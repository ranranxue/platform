package com.platform.service.upload;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.platform.data.ApiResultInfo;
import com.platform.rmodel.upload.UploadFileListResponse;
import com.platform.service.common.QiNiuUploadThread;

@Service("qiNiuService")
public class QiNiuServiceImpl implements QiNiuService{ 

	Logger logger=Logger.getLogger(QiNiuServiceImpl.class);
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
		return threadPoolTaskExecutor;
	}

	public void setThreadPoolTaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
		this.threadPoolTaskExecutor = threadPoolTaskExecutor;
	}
	
	public void uploadAsyMutip(List<MultipartFile> fileMap,List<String> fileNewNameList){
		
		QiNiuUploadThread thread=new QiNiuUploadThread();
		thread.setFileMap(fileMap);
		thread.setFileNameList(fileNewNameList);
		logger.debug("init the thread successfully!");
		threadPoolTaskExecutor.execute(thread);
	}
	
	

	public UploadFileListResponse uploadlocal(List<MultipartFile> platformfiles, List<String> fileNewNameList){
		// TODO Auto-generated method stub
		for(int i=0;i<platformfiles.size();i++){
			try {
				InputStream input = new ByteArrayInputStream(platformfiles.get(i).getBytes());
				OutputStream output = new FileOutputStream("D:/usr/" + fileNewNameList.get(i));
				IOUtils.copy(input, output);
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("save the file error",e);
				UploadFileListResponse response=new UploadFileListResponse();
				response.setCode(ApiResultInfo.ResultCode.SaveLocalFileError);
				response.setMsg(ApiResultInfo.ResultMsg.SaveLocalFileError);
				return response;
			}
			
		}
		UploadFileListResponse response=new UploadFileListResponse();
		response.setCode(0);
		response.setMsg("save local file successfully!");
		response.setFileNameList(fileNewNameList);
		return response;
	}

	
	
	
	

}
