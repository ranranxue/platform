package com.platform.service.works;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.dao.NoticeDAO;
import com.platform.dao.UserDAO;
import com.platform.dao.WorksDAO;
import com.platform.data.ApiResultInfo;
import com.platform.data.StaticData;
import com.platform.data.TimeData;
import com.platform.model.Works;
import com.platform.rmodel.notice.IndexInfoResponse;
import com.platform.rmodel.notice.NoticeInfo;
import com.platform.rmodel.work.UploadWorksRequest;
import com.platform.rmodel.work.UploadWorksResponse;
import com.platform.rmodel.work.WorkInfo;
import com.platform.rmodel.work.WorksDeleteRequest;
import com.platform.rmodel.work.WorksDeleteResponse;
import com.platform.rmodel.work.WorksListResponse;
import com.platform.util.TimeUtil;

@Service("worksService")
public class WorksServiceImpl implements WorksService {
	private Logger logger = Logger.getLogger(WorksServiceImpl.class);
	@Autowired
	private WorksDAO worksDAO;
	@Autowired
	private NoticeDAO noticeDAO;
	@Autowired
	private UserDAO userDAO;

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public WorksDAO getWorksDAO() {
		return worksDAO;
	}

	public void setWorksDAO(WorksDAO worksDAO) {
		this.worksDAO = worksDAO;
	}

	public NoticeDAO getNoticeDAO() {
		return noticeDAO;
	}

	public void setNoticeDAO(NoticeDAO noticeDAO) {
		this.noticeDAO = noticeDAO;
	}

	public IndexInfoResponse getIndexInfo() {
		// TODO Auto-generated method stub
		List<NoticeInfo> noticesList = null;
		try {
			logger.debug(" start to get the recent five notice list from notice db ");
			noticesList = noticeDAO.getRecentNoticeList();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("worksService error in the worksServiceImpl", e);
			IndexInfoResponse response = new IndexInfoResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (noticesList == null) {
			logger.debug(" fail to get recent five notices ");
			IndexInfoResponse response = new IndexInfoResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		List<WorkInfo> worksList = null;
		try {
			logger.debug(" start to get the excellent works from works db");
			worksList = worksDAO.getRecentFourWorks();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("worksService error in the worksServiceImpl", e);
			IndexInfoResponse response = new IndexInfoResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (worksList == null) {
			logger.debug(" fail to get recent excellent works ");
			IndexInfoResponse response = new IndexInfoResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		IndexInfoResponse response = new IndexInfoResponse();
		if (noticesList.size() == 0) {
			response.setNoticeList(new ArrayList<NoticeInfo>());
		} else {
			response.setNoticeList(noticesList);
		}
		if (worksList.size() == 0) {
			response.setWorksList(new ArrayList<WorkInfo>());
		} else {
			response.setWorksList(worksList);
		}
		response.setCode(0);
		response.setMsg("get the index info successfully!");
		return response;
	}

	public WorksListResponse getWorksList() {
		// TODO Auto-generated method stub
		Integer worksNum = -1;
		List<Works> worksList = null;
		List<WorkInfo> worksInfoList = new ArrayList<WorkInfo>();
		try {
			logger.debug("start to get the works num from works db ");
			worksNum = worksDAO.getWorksTotalNum();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("worksService error", e);
			WorksListResponse response = new WorksListResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (worksNum == -1) {
			logger.error("fail to get the works num");
			WorksListResponse response = new WorksListResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		} else if (worksNum == 0) {
			worksInfoList = new ArrayList<WorkInfo>();
		} else {
			try {
				logger.debug("start to get the all works from works db ");
				worksList = worksDAO.getAllWorks();
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("worksService error", e);
				WorksListResponse response = new WorksListResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
			}
			if (worksList == null) {
				logger.debug("fail to get the all  worksList");
				WorksListResponse response = new WorksListResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
			}
			// attention:不再需要判定列表是否为空，必定不为空
			for (int i = 0; i < worksList.size(); i++) {
				WorkInfo workInfo = new WorkInfo();
				workInfo.setId(worksList.get(i).getId());
				workInfo.setTitle(worksList.get(i).getTitle());
				String name = "";
				try {
					logger.debug("get the all works author's name from user db using author_id"
							+ worksList.get(i).getAuthor_id());
					name = userDAO.getNameById(worksList.get(i).getAuthor_id());
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("worksService error and fail to get the author's name", e);
					WorksListResponse response = new WorksListResponse();
					response.setCode(ApiResultInfo.ResultCode.ServerError);
					response.setMsg(ApiResultInfo.ResultMsg.ServerError);
					return response;
				}
				workInfo.setName(name);
				workInfo.setWorks_url(StaticData.QiNiuFilePath + worksList.get(i).getWorks_url());
				workInfo.setUpload_time(worksList.get(i).getUpload_time());
				worksInfoList.add(workInfo);
			}

		}
		WorksListResponse response = new WorksListResponse();
		response.setCode(0);
		response.setMsg(" get the worksList successfully !");
		response.setWorksNum(worksNum);
		response.setWorksList(worksInfoList);
		return response;
	}

	public WorksDeleteResponse deleteWorks(WorksDeleteRequest request) {
		// TODO Auto-generated method stub
		Integer deleteNum = 0;
		try {
			logger.debug(" delete the works from works db using works_id" + request.getWorks_id());
			deleteNum = worksDAO.deleteWorks(request.getWorks_id());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(" worksService error ", e);
			WorksDeleteResponse response = new WorksDeleteResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (deleteNum == 0) {
			logger.debug(" fail to delete the works");
			WorksDeleteResponse response = new WorksDeleteResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		} else {
			WorksDeleteResponse response = new WorksDeleteResponse();
			response.setCode(0);
			response.setMsg("delete the works successfully!");
			response.setWorks_id(request.getWorks_id());
			return response;
		}
	}

	public UploadWorksResponse uploadWorks(UploadWorksRequest request) {
		// TODO Auto-generated method stub
		Works works = new Works();
		works.setTitle(request.getTitle());
		works.setDescription(request.getDescription());

		works.setAuthor_id(request.getStuid());
		works.setWorks_url(request.getWorks_url());
		works.setUpload_time((int) TimeUtil.getCurrentTime(TimeData.TimeFormat.YMD));
		Integer insertNum = 0;
		try {
			logger.debug(" start to insert works into works db using notice");
			insertNum = worksDAO.insertWorks(works);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("worksService error", e);
			UploadWorksResponse response = new UploadWorksResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (insertNum == 0) {
			logger.debug("fail to insert works");
			UploadWorksResponse response = new UploadWorksResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		} else {
			logger.debug("insert works successfully!");
			UploadWorksResponse response = new UploadWorksResponse();
			response.setCode(0);
			response.setMsg("upload works successfully!");
			response.setWorks_id(works.getId());
			return response;
		}
	}
}
