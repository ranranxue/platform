package com.platform.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.platform.model.Topic;

public interface TopicDAO {
	/**
	 * 获得topic的列表
	 * @return
	 */
	@Select("select id ,title,author_id,create_time from topic where reply_id=0 order by id desc")
	public List<Topic> getTopicList();
	/**
	 * 获取该讨论话题的回帖数量
	 * @param reply_id
	 * @return
	 */
	@Select("select count(id) from topic where reply_id=#{reply_id}")
	public Integer getReplyNum(@Param("reply_id") Integer reply_id);
	/**
	 * 插入一个topic
	 * @param topic
	 * @return
	 */
	@Insert("insert into topic(title,content,author_id,create_time) values(#{title},#{content},#{author_id},#{create_time});")
	@SelectKey(statement = "SELECT LAST_INSERT_ID() ", keyProperty = "id", before = false, resultType = int.class)
	public Integer insertTopic(Topic topic);
	/**
	 * 得到topic 的详细内容
	 * @param id
	 * @return
	 */
	@Select("select id,title,content,author_id,good,bad,create_time from topic where id=#{id};")
	public Topic getTopicDetail(@Param("id") Integer id);
	/**
	 * 得到特定topic的回复的帖子信息
	 * @param reply_id
	 * @return
	 */
	@Select("select id ,content,author_id,good,bad,create_time,real_name from topic where reply_id=#{reply_id};")
	public List<Topic> getReplyTopicInfo(@Param("reply_id") Integer reply_id);
	
	
	/**
	 * 插入一个topicReply
	 * @param topic
	 * @return
	 */
	@Insert("insert into topic(content,author_id,reply_id,create_time,real_name) values(#{content},#{author_id},#{reply_id},#{create_time},#{real_name});")
	@SelectKey(statement = "SELECT LAST_INSERT_ID() ", keyProperty = "id", before = false, resultType = int.class)
	public Integer insertTopicReply(Topic topic);
	/**
	 * 获取topic 的所有回帖id
	 * @param id
	 * @return
	 */
	@Select("select id from topic where reply_id=#{id}")
	public List<Integer> getReplyId(@Param("id") Integer id);
	
	/**
	 * 删除 话题和话题的回帖
	 * @param id
	 * @return
	 */
	@Delete("delete from topic where id=#{id} or reply_id=#{id}")
	public Integer deleteTopic(@Param("id") Integer id);
	
	/**
	 * 删除特定的回帖 
	 * @param id
	 * @return
	 */
	@Delete("delete from topic where id=#{id}")
	public Integer deleteReply(@Param("id") Integer id);
	
	
	
	

}
