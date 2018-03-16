package com.platform.dao;


import org.apache.ibatis.annotations.Select;

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
	
	
	
	

}
