package com.platform.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.platform.model.Login;

public interface LoginDAO {
	/**
	 * 根据登录名和密码获取登陆id;
	 * 
	 * @param loginName
	 * @param password
	 * @return
	 */
	@Select("select id from login where stuid = #{loginName} and password=#{password} ;")
	public Integer getLoginId(@Param("loginName") String loginName, @Param("password") String password);
	/**
	 * 插入学生登陆的信息
	 * @param login
	 * @return
	 */
	@Insert("insert into login(stuid,password) values(#{stuid},#{password});")
	public Integer insertLogin(Login login);
	
	/**
	 * 删除用户依据用户stuid
	 * @param stuid
	 * @return
	 */
	
	public void deleteMultiUser(@Param("list") List<String> list);
	/**
	 * 判断用户的密码是否正确
	 * @param stuid
	 * @param password
	 * @return
	 */
	@Select("select count(id) from login where stuid=#{stuid} and password=#{password}")
	public Integer checkUserLogin(@Param("stuid") String stuid,@Param("password") String password);
	/**
	 * 更新密码
	 * @param password
	 * @param stuid
	 * @return
	 */
	@Update("update login set password=#{password} where stuid=#{stuid}")
	public Integer updatePassword(@Param("password") String password,@Param("stuid") String stuid);

}
