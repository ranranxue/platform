package com.platform.service.works;

import com.platform.rmodel.notice.IndexInfoResponse;

import com.platform.rmodel.work.UploadWorksRequest;
import com.platform.rmodel.work.UploadWorksResponse;
import com.platform.rmodel.work.WorksDeleteRequest;
import com.platform.rmodel.work.WorksDeleteResponse;
import com.platform.rmodel.work.WorksListResponse;


public interface WorksService {
	/**
	 * 获取主页的相关内容;
	 * @return
	 */
	public IndexInfoResponse getIndexInfo();
	/**
	 * 删除作品
	 * @param request
	 * @return
	 */
	public WorksDeleteResponse deleteWorks(WorksDeleteRequest request);
	/**
	 * 上传作品
	 * @param request
	 * @return
	 */
	public UploadWorksResponse uploadWorks(UploadWorksRequest request);
	
	
	
	/**
	 * 获取所有的作品
	 * @return
	 */
	public WorksListResponse getWorksList();
	
	
	

}
