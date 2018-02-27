package com.platform.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.platform.model.Attachment;

public interface AttachmentDAO {
	/**
	 * 根据id来获取公告的附件
	 * @param notice_id
	 * @return
	 */
	@Select("select attachment_name,attachment_url from attachment where notice_id=#{notice_id}")
	public List<Attachment> getAttachmentInfo(@Param("notice_id") Integer notice_id);
	
	
	

}
