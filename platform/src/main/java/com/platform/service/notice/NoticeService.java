package com.platform.service.notice;

import com.platform.rmodel.notice.CreateNoticeRequest;
import com.platform.rmodel.notice.CreateNoticeResponse;
import com.platform.rmodel.notice.NoticeDeleteRequest;
import com.platform.rmodel.notice.NoticeDeleteResponse;
import com.platform.rmodel.notice.NoticeDetailRequest;
import com.platform.rmodel.notice.NoticeDetailResponse;
import com.platform.rmodel.notice.NoticeListResponse;

public interface NoticeService {
	/**
	 * 获取所有的公告消息
	 * @param request
	 * @return
	 */
	public NoticeListResponse getAllNoticesInfo();
	/**
	 * 根据公告的id来获取特定公告详情
	 * @param request
	 * @return
	 */
	public NoticeDetailResponse getNoticeDetail(NoticeDetailRequest request);
	
	/*****以下是管理员执行的公告操作******/
	/**
	 * 删除公告和相关的附件
	 * @param request
	 * @return
	 */
	public NoticeDeleteResponse deleteNotice(NoticeDeleteRequest request);
	/**
	 * 创建公告
	 * @param request
	 * @return
	 */
	public CreateNoticeResponse createNotice(CreateNoticeRequest request);
	
	
	
	
	
	

}
