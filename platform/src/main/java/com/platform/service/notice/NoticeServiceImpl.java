package com.platform.service.notice;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.dao.AttachmentDAO;
import com.platform.dao.NoticeDAO;
import com.platform.dao.UserDAO;
import com.platform.data.ApiResultInfo;
import com.platform.data.StaticData;
import com.platform.data.TimeData;
import com.platform.model.Attachment;
import com.platform.model.Notice;
import com.platform.rmodel.notice.AttachmentInfo;
import com.platform.rmodel.notice.CreateNoticeRequest;
import com.platform.rmodel.notice.CreateNoticeResponse;
import com.platform.rmodel.notice.NoticeDeleteRequest;
import com.platform.rmodel.notice.NoticeDeleteResponse;
import com.platform.rmodel.notice.NoticeDetailRequest;
import com.platform.rmodel.notice.NoticeDetailResponse;
import com.platform.rmodel.notice.NoticeInfo;
import com.platform.rmodel.notice.NoticeListResponse;
import com.platform.util.TimeUtil;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
	private Logger logger = Logger.getLogger(NoticeServiceImpl.class);
	@Autowired
	private NoticeDAO noticeDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private AttachmentDAO attachmentDAO;

	public AttachmentDAO getAttachmentDAO() {
		return attachmentDAO;
	}

	public void setAttachmentDAO(AttachmentDAO attachmentDAO) {
		this.attachmentDAO = attachmentDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public NoticeDAO getNoticeDAO() {
		return noticeDAO;
	}

	public void setNoticeDAO(NoticeDAO noticeDAO) {
		this.noticeDAO = noticeDAO;
	}

	public NoticeListResponse getAllNoticesInfo() {
		List<Notice> noticeList = null;
		List<NoticeInfo> noticeInfoList = new ArrayList<NoticeInfo>();
		try {
			logger.debug("get all notices from notice db ");
			noticeList = noticeDAO.getAllNotice();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("fail to get all noticeList", e);
			NoticeListResponse response = new NoticeListResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (noticeList != null) {
			for (int i = 0; i < noticeList.size(); i++) {
				NoticeInfo noticeInfo = new NoticeInfo();
				String author_name = null;
				try {
					logger.debug(" get publisher_name from user by stuid" + noticeList.get(i).getPublisher());
					author_name = userDAO.getNameById(noticeList.get(i).getPublisher());
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("fail to get userName", e);
					NoticeListResponse response = new NoticeListResponse();
					response.setCode(ApiResultInfo.ResultCode.ServerError);
					response.setMsg(ApiResultInfo.ResultMsg.ServerError);
					return response;
				}
				noticeInfo.setId(noticeList.get(i).getId());
				noticeInfo.setTitle(noticeList.get(i).getTitle());
				noticeInfo.setAuthor_name(author_name);
				noticeInfo.setCreate_time(noticeList.get(i).getCreate_time());
				noticeInfoList.add(noticeInfo);
			}
		}
		logger.debug("start to set response");
		NoticeListResponse response = new NoticeListResponse();
		response.setCode(0);
		response.setMsg("get the all notices successfully!");
		response.setNoticeList(noticeInfoList);
		return response;
	}

	public NoticeDetailResponse getNoticeDetail(NoticeDetailRequest request) {
		// TODO Auto-generated method stub
		Notice notice = null;
		try {
			logger.debug(" start to get notice detail by notice_id" + request.getNotice_id() + "from notice db");
			notice = noticeDAO.getNoticeDetail(request.getNotice_id());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("noticeService error", e);
			NoticeDetailResponse response = new NoticeDetailResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (notice == null) {
			logger.debug("fail to get the notice from notice db");
			NoticeDetailResponse response = new NoticeDetailResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		String publisher_name = null;
		try {
			logger.debug(" get the publisher_name from user db using stuid" + notice.getPublisher());
			publisher_name = userDAO.getNameById(notice.getPublisher());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("noticeService error", e);
			NoticeDetailResponse response = new NoticeDetailResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (publisher_name == null) {
			logger.debug(" fail to get the publisher_name from user db");
			NoticeDetailResponse response = new NoticeDetailResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		List<AttachmentInfo> attachmentList = new ArrayList<AttachmentInfo>();
		List<Attachment> attachmentInfoList = null;
		try {
			logger.debug("get the notice attachmennt from attachment by notice id");
			attachmentInfoList = attachmentDAO.getAttachmentInfo(request.getNotice_id());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("attachmentDAO error ", e);
			NoticeDetailResponse response = new NoticeDetailResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (attachmentInfoList == null) {
			logger.debug("fail to get attachmennt info");
			NoticeDetailResponse response = new NoticeDetailResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		} else {
			for (int i = 0; i < attachmentInfoList.size(); i++) {
				AttachmentInfo attachmentInfo = new AttachmentInfo();
				attachmentInfo.setAttachment_name(attachmentInfoList.get(i).getAttachment_name());
				attachmentInfo
						.setAttachment_url(StaticData.QiNiuFilePath + attachmentInfoList.get(i).getAttachment_url());
				attachmentList.add(attachmentInfo);
			}
		}
		NoticeDetailResponse response = new NoticeDetailResponse();
		response.setCode(0);
		response.setMsg(" get the notice detail successfully!");
		response.setNotice_id(notice.getId());
		response.setTitle(notice.getTitle());
		response.setContent(notice.getContent());
		response.setPublisher_name(publisher_name);
		response.setCreate_time(notice.getCreate_time());
		response.setAttachmentList(attachmentList);
		return response;
	}

	public NoticeDeleteResponse deleteNotice(NoticeDeleteRequest request) {
		// TODO Auto-generated method stub
		// attention:不仅删除公告，公告的附件也需要删除
		Integer num = 0;
		try {
			logger.debug(" start to delete the notice from notice db using notice_id" + request.getNotice_id());
			num = noticeDAO.deleteNotice(request.getNotice_id());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(" noticeService error", e);
			NoticeDeleteResponse response = new NoticeDeleteResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (num == 0) {
			logger.error("fail to delete the notice");
			NoticeDeleteResponse response = new NoticeDeleteResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		logger.debug("delete the notice successfully!");
		NoticeDeleteResponse response = new NoticeDeleteResponse();
		response.setCode(0);
		response.setMsg("delete the notice successfully!");
		response.setNotice_id(request.getNotice_id());
		return response;
	}

	public CreateNoticeResponse createNotice(CreateNoticeRequest request) {
		// TODO Auto-generated method stub
		Integer num = 0;
		Notice notice = new Notice();
		notice.setTitle(request.getTitle());
		notice.setContent(request.getContent());
		notice.setPicture_url(request.getPicture_url());
		notice.setPublisher(request.getPublisher());
		notice.setCreate_time((int) TimeUtil.getCurrentTime(TimeData.TimeFormat.YMD));
		try {
			logger.debug(" create the notice in notice db ");
			num = noticeDAO.insertNotice(notice);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("noticeService error", e);
			CreateNoticeResponse response = new CreateNoticeResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (num == 0) {
			logger.debug(" fail to insert the notice ");
			CreateNoticeResponse response = new CreateNoticeResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		} else {
			CreateNoticeResponse response = new CreateNoticeResponse();
			response.setCode(0);
			response.setMsg("insert notice successfully!");
			response.setNotice_id(notice.getId());
			return response;
		}
	}

}
