package com.platform.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.platform.model.Attachment;

public interface AttachmentDAO {
	/**
	 * 根据id来获取公告的附件
	 * @param notice_id
	 * @return
	 */
	@Select("select attachment_name,attachment_url from attachment where notice_id=#{notice_id}")
	public List<Attachment> getAttachmentInfo(@Param("notice_id") Integer notice_id);
	/**
	 * 批量删除公告的附件
	 * @param list
	 */
	public void deleteMultiAttachment(@Param("list") List<Integer> list);
	/**
	 * 插入附件并返回id
	 * @param attachment
	 * @return
	 */
	@Insert("insert into attachment(attachment_name,attachment_url,notice_id) values(#{attachment_name},#{attachment_url},#{notice_id});")
	@SelectKey(statement = "SELECT LAST_INSERT_ID() ", keyProperty = "id", before = false, resultType = int.class)
	public Integer insertAttachment(Attachment attachment);
	
	
	

}
