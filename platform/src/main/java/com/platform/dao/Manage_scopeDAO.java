package com.platform.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.platform.model.Manage_scope;

public interface Manage_scopeDAO {
	/**
	 * 根据这个管理员的账号得到管理的学生范围
	 * @param manager_id
	 * @return
	 */
	@Select("select grade from manage_scope where manager_id=#{manager_id} ")
	public List<String>  getManageGradeList(@Param("manager_id") String manager_id );
	//限制每个普通管理员不可以管理相同的年级
	@Select("select manager_id from manage_scope where grade=#{grade} and isManager!=2")
	public String getManagerIdByGrade(@Param("grade") String grade);
	/**
	 * 根据管理员获取该管理员的管理班级
	 * @param manager_id
	 * @return
	 */
	@Select("select grade from manage_scope where manager_id=#{manager_id} order by grade")
	public List<String> getManagerGrades(@Param("manager_id") String manager_id);
	/**
	 * 插入管理的数据并返回id
	 * @param manager_id
	 * @param grade
	 * @param isManager
	 * @return
	 */
	@Insert("insert into manage_scope(manager_id,grade,isManager) values(#{manager_id},#{grade},#{isManager})")
	@SelectKey(statement = "SELECT LAST_INSERT_ID() ", keyProperty = "id", before = false, resultType = int.class)
	public Integer insertManageScope(Manage_scope manage_scope);
	/**
	 * 获取普通管理员管理的某个年级//暂时无用
	 * @param manager_id
	 * @return
	 */
	@Select("select grade from manage_scope where manager_id=#{manager_id}")
	public String getManagerGrade(@Param("manager_id") String manager_id);
	/**
	 * 更新管理员管理的年级
	 * @param grade
	 * @param manager_id
	 * @return
	 */
	@Update("update manage_scope set grade==#{grade} where manager_id=#{manager_id}")
	public Integer updateManageGrade(@Param("grade") String grade,@Param("manager_id") String manager_id);
	

}
