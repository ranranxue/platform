package com.platform.dao;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.platform.model.Stu_limit;

public interface Stu_limitDAO {
	/**
	 * 获取学生的论坛权限
	 * @return
	 */
	@Select("select forum from stu_limit where id=1")
	public Integer getForum();
	/**
	 * 获取学生的所有权限
	 * @return
	 */
	@Select("select forum ,edit_info, upload from stu_limit where id=1")
	public Stu_limit getAllStudentAuthority();
	/**
	 * 更新forum字段
	 * @param forum
	 * @return
	 */
	@Update("update stu_limit set forum =#{forum}")
	public Integer updateForum(@Param("forum") Integer forum);
	/**
	 * 更新edit_info字段
	 * @param edit_info
	 * @return
	 */
	@Update("update stu_limit set edit_info=#{edit_info}")
	public Integer updateEdit_info(@Param("edit_info") Integer edit_info);
	/**
	 * 更新upload字段
	 * @param upload
	 * @return
	 */
	@Update("update stu_limit set upload=#{upload}")
	public Integer updateUpload(@Param("upload") Integer upload);
	

}
