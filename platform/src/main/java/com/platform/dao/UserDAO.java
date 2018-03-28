package com.platform.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.platform.model.User;
import com.platform.rmodel.user.UserBasicInfo;
import com.platform.rmodel.user.UserInfo;

public interface UserDAO {
	/**
	 * 根据用户的登录名来获取用户的必要信息
	 * @param loginName
	 * @return
	 */
	@Select("select stuid ,name,nickname,gender,self_introduction,grade,phone,email,head_url,isManager,detail_introduction from user where stuid=#{loginName}")
	public User getUserInfo(@Param("loginName") String loginName);
	/**
	 * 根据用户的唯一标识来获取用户的姓名，性别，自我介绍，背景图片和头像图片等
	 * @param stuid
	 * @return
	 */
	/*
	@Select("select grade,stuid,name,gender,self_introduction,nickname,head_url,isManager,detail_introduction from user where stuid=#{stuid}")
	public User getUserInfoById(@Param("stuid") String stuid);
	*/
	/**
	 * 根据用户的唯一标识来判定这个用户是否是管理员
	 * @param stuid
	 * @return
	 */
	@Select("select isManager from user where stuid=#{stuid}")
	public Integer judgeIsManager(@Param("stuid") String stuid);
	/**
	 * 更新个人信息
	 * @param nickname
	 * @param gender
	 * @param name
	 * @param self_introduction
	 * @param phone
	 * @param email
	 * @param stuid
	 * @return
	 */
	
	@Update("update user set nickname=#{nickname},gender=#{gender},name=#{name},self_introduction=#{self_introduction},phone=#{phone},email=#{email} where stuid=#{stuid}")
	public Integer modifyUserInfo(@Param("nickname") String nickname,@Param("gender") String gender,@Param("name") String name,@Param("self_introduction") String self_introduction,@Param("phone") String phone,@Param("email") String email,@Param("stuid") String stuid);
	/**
	 * 修改用户的性别
	 * @param gender
	 * @return
	 */
	@Update("update user set gender=#{gender} where stuid=#{stuid}")
	public Integer modifyGender(@Param("gender") Integer gender,@Param("stuid") String stuid);
	/**
	 * 检查除去该用户之外电话号码的数量
	 * @param phone
	 * @param stuid
	 * @return
	 */
	@Select("select count(stuid) from user where phone=#{phone} and stuid!=#{stuid}")
	public Integer getPhoneNum(@Param("phone") String phone,@Param("stuid") String stuid);
	/**
	 * 修改用户的手机号码
	 * @param phone
	 * @param stuid
	 * @return
	 */
	@Update("update user set phone=#{phone} where stuid=#{stuid}")
	public Integer modifyPhone(@Param("phone") String phone,@Param("stuid") String stuid);
	
	/**
	 * 检查email的数量
	 * @param email
	 * @return
	 */
	@Select("select count(stuid) from user where email=#{email} and stuid!=#{stuid}")
	public Integer getEmailNum(@Param("email") String email,@Param("stuid") String stuid);
	/**
	 * 修改用户的邮箱
	 * @param email
	 * @param stuid
	 * @return
	 */
	@Update("update user set email=#{email} where stuid=#{stuid}")
	public Integer modifyEmail(@Param("email") String email,@Param("stuid") String stuid);
	
	/**
	 * 修改用户的自我介绍;
	 * @param self_introduction
	 * @param stuid
	 * @return
	 */
	@Update("update user set self_introduction=#{self_introduction} where stuid=#{stuid}")
	public Integer modifyIntroduction(@Param("self_introduction") String self_introduction,@Param("stuid") String stuid);
	/**
	 * 更新头像
	 * @param head_url
	 * @param stuid
	 * @return
	 */
	@Update("update user set head_url=#{head_url} where stuid=#{stuid}")
	public Integer modifyHeadImg(@Param("head_url") String head_url,@Param("stuid") String stuid);
	/**
	 * 根据用户的账号来获取用户的名字
	 * @param stuid
	 * @return
	 */
	@Select("select name from user where stuid=#{stuid}")
	public String getNameById(@Param("stuid") String stuid);
	
	
	/***以下是管理员对用户的一些操作****/
	/**
	 * 获取所有学生的学号和名字
	 * @return
	 */
	@Select("select stuid ,name,isManager from user where stuid!=#{stuid} order by create_time desc;")
	public List<UserBasicInfo> getAllUsers(@Param("stuid") String stuid);
	
	/**
	 * 根据grade(String)的数组来获取属于该数组的所有学生
	 */
	public List<UserBasicInfo> getUsersByMultiGrade(@Param("list") List<String> list);
	
	
	/**
	 * 获取stuid的数量
	 * @param stuid
	 * @return
	 */
	@Select("select count(stuid) from user where stuid=#{stuid}")
	public Integer getStuNum(@Param("stuid") String stuid);
	/**
	 * 插入一个用户,自动注入login对象的id
	 * @param user
	 * @return
	 */
	@Insert("insert into user(stuid,name,nickname,isManager,grade,class_info,virtual_homepage,create_time) values(#{stuid},#{name},#{nickname},#{isManager},#{grade},#{class_info},#{virtual_homepage},#{create_time});")
	@SelectKey(statement = "SELECT LAST_INSERT_ID() ", keyProperty = "id", before = false, resultType = int.class)
	public Integer insertUser(User user);
	/**
	 * 删除用户依据用户stuid
	 * @param stuid
	 * @return
	 */
	public void deleteByMultiUserId(@Param("list") List<String> list);
	/**
	 * 更新个人的详细信息
	 * @param stuid
	 * @param detail_introduction
	 * @return
	 */
	@Update("update user set detail_introduction=#{detail_introduction} where stuid=#{stuid}")
	public Integer updateUserDetailIntro(@Param("stuid") String stuid,@Param("detail_introduction") String detail_introduction);
	/**
	 * 得到这个人的是否是管理员的信息
	 * @param stuid
	 * @return
	 */
	@Select("select isManager from user where stuid=#{stuid} ")
	public Integer getIsManager(@Param("stuid") String stuid);
	
	/**
	 * 查询所有用户所在的年级
	 * @return
	 */
	@Select("select grade from user where isManager=0  group by grade")
	public List<String> getGrade();
	/**
	 * 根据特定年级获取该年级下的所有班级
	 * @param list
	 * @return
	 */
	@Select("select class_info from user where grade=#{grade}")
	public List<String> getClassByGrade(@Param("grade") String grade);
	/**
	 * 根据班级来获取班级里面所有学生信息
	 * @param class_info
	 * @return
	 */
	@Select("select stuid ,name ,head_url,self_introduction from user where class_info=#{class_info}")
	public List<UserInfo> getUserInfoByClass(@Param("class_info") String class_info);
	/**
	 * 根据用户的学号获取用户的头像信息
	 * @param stuid
	 * @return
	 */
	@Select("select head_url from user where stuid=#{stuid}")
	public String getHead_urlByStuid(@Param("stuid") String stuid);
	/**
	 * 获取所有的普通管理员
	 * @return
	 */
	@Select("select stuid from user where isManager=1")
	public List<String> getAllManagers();
	/**
	 * 
	 * @return
	 */
	@Select("select DISTINCT grade from user where isManager=0 order by grade")
	public List<String> getAllGrades();
	/**
	 * 插入一个普通管理员
	 * @param stuid
	 * @param name
	 * @param isManager
	 * @param virtual_homepage
	 * @param create_time
	 * @return
	 */
	@Insert("insert into user(stuid,name,isManager,virtual_homepage,create_time)values(#{stuid},#{name},#{isManager},#{virtual_homepage},#{create_time})")
	public Integer insertManager(@Param("stuid") String stuid,@Param("name") String name,@Param("isManager") Integer isManager,@Param("virtual_homepage") String virtual_homepage,@Param("create_time") Integer create_time);
	/**
	 * 批量插入用户
	 * @param list
	 */
	public void insertMultiUser(@Param("list") List<User> list);	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
