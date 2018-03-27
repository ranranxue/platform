package com.platform.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.platform.model.Notice;

import com.platform.rmodel.notice.NoticeInfo;

public interface NoticeDAO {
	/**
	 * 获取最近的五个公告
	 * @return
	 */
	@Select("select id,title,create_time from notice order by id desc limit 4")
	public List<NoticeInfo> getRecentNoticeList();
	/**
	 * 获取所有的公告信息
	 * @return
	 */
	@Select("select id,title,publisher,create_time from notice")
	public List<Notice> getAllNotice();
	
	/**
	 * 根据公告id来获取公告的详细信息
	 * @param notice_id
	 * @return
	 */
	@Select("select id,title,content,picture_url,publisher,create_time from notice where id=#{notice_id}")
	public Notice getNoticeDetail(@Param("notice_id") Integer notice_id);

	
	/***以下是管理员对notice db的操作***/
	/**
	 * 根据notice_id来删除公告
	 * @param notice_id
	 * @return
	 */
	//@Delete("delete from notice where id=#{notice_id}")
	public void deleteMultiNotice(@Param("list") List<Integer> list);
	/**
	 * 插入一条公告并自动注入id
	 * @param notice
	 * @return
	 */
	@Insert("insert into notice(title,content,publisher,create_time) values(#{title},#{content},#{publisher},#{create_time});")
	@SelectKey(statement = "SELECT LAST_INSERT_ID() ", keyProperty = "id", before = false, resultType = int.class)
	public Integer insertNotice(Notice notice);
	
	
	
	

}
