package com.platform.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.platform.model.Comment;

public interface CommentDAO {
	/**
	 * 得到回帖的评论数量
	 * @param reply_id
	 * @return
	 */
	@Select("select count(id) from comment where reply_id=#{reply_id};")
	public Integer getCommentNum(@Param("reply_id") Integer reply_id);
	/**
	 * 得到特定回复帖子的评论信息
	 * @param reply_id
	 * @return
	 */
	@Select("select id,content,author_id,create_time from comment where reply_id=#{reply_id} order by id desc;")
	public List<Comment> getCommentList(@Param("reply_id") Integer reply_id);
	/**
	 * 插入一条comment自动注入id
	 * @param comment
	 * @return
	 */
	@Insert("insert into comment(content,author_id,reply_id,create_time) values(#{content},#{author_id},#{reply_id},#{create_time});")
	@SelectKey(statement = "SELECT LAST_INSERT_ID() ", keyProperty = "id", before = false, resultType = int.class)
	public Integer insertComment(Comment comment);
	
	/**
	 * 批量删除帖子id列表的评论
	 * @param list
	 */
	public void deleteCommentByMutiReplyId(@Param("list") List<Integer> list);
	/**
	 * 根据回帖的id来删除相应的所有评论
	 * @param id
	 * @return
	 */
	@Delete("delete from comment where reply_id=#{id}")
	public Integer deleteCommentByReplyId(@Param("id") Integer id);
	
	/**
	 * 根据评论的id来删除评论
	 * @param id
	 * @return
	 */
	@Delete("delete from comment where id=#{id}")
	public Integer deleteCommentById(@Param("id") Integer id);
	

}
