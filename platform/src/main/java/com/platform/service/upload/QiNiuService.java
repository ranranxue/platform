package com.platform.service.upload;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.platform.rmodel.upload.UploadFileListResponse;

public interface QiNiuService {
	/**
	 * 另外开一个线程来负责将文件上传七牛云;
	 * @param fileMap
	 * @param fileNameList
	 */
	public void uploadAsyMutip(List<MultipartFile> fileMap, List<String> fileNewNameList);
	
	/**
	 * 保存文件到本地webapps下面的fileDir文件夹
	 * @param fileMap
	 * @param fileNameList
	 * @param savedDir
	 */
	public UploadFileListResponse uploadlocal(List<MultipartFile> fileMap, List<String> fileNewNameList);

}
