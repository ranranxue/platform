package com.platform.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface Manage_scopeDAO {
	/**
	 * 根据这个管理员的账号得到管理的学生范围
	 * @param manager_id
	 * @return
	 */
	@Select("select grade from manage_scope where manager_id=#{manager_id}")
	public List<String>  getManageGradeList(@Param("manager_id") String manager_id );
	//限制每个普通管理员不可以管理相同的年级
	@Select("select manager_id from manage_scope where grade=#{grade} where isManager!=2")
	public String getManagerIdByGrade(@Param("grade") String grade);
	
	

}
