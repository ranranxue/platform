package com.platform.dao;


import org.apache.ibatis.annotations.Select;

public interface Stu_limitDAO {
	/**
	 * 获取学生的论坛权限
	 * @return
	 */
	@Select("select forum from stu_limit where id=1")
	public Integer getForum();
	
	
	

}
