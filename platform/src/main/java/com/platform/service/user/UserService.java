package com.platform.service.user;

import com.platform.model.BasicResponse;
import com.platform.rmodel.user.AddDetailIntroRequest;
import com.platform.rmodel.user.AddDetailIntroResponse;
import com.platform.rmodel.user.AddUserRequest;
import com.platform.rmodel.user.AddUserResponse;
import com.platform.rmodel.user.AllUserResponse;
import com.platform.rmodel.user.ClassInfoRequest;
import com.platform.rmodel.user.ClassInfoResponse;
import com.platform.rmodel.user.DeleteUserRequest;
import com.platform.rmodel.user.DeleteUserResponse;

import com.platform.rmodel.user.ExitRequest;
import com.platform.rmodel.user.GetUsersRequest;
import com.platform.rmodel.user.GetUsersResponse;
import com.platform.rmodel.user.GradeInfoResponse;
import com.platform.rmodel.user.Head_urlUpdateRequest;
import com.platform.rmodel.user.Head_urlUpdateResponse;
import com.platform.rmodel.user.HomePageResponse;
import com.platform.rmodel.user.JudgeIsLoginRequest;
import com.platform.rmodel.user.JudgeIsLoginResponse;
import com.platform.rmodel.user.LoginRequest;
import com.platform.rmodel.user.LoginResponse;
import com.platform.rmodel.user.PasswordUpdateRequest;
import com.platform.rmodel.user.PersonInfoEditRequest;
import com.platform.rmodel.user.PersonInfoEditResponse;
import com.platform.rmodel.user.UserInfoRequest;
import com.platform.rmodel.user.addManagerRequest;

public interface UserService {
	/**
	 * 用户登录
	 * @param request
	 * @return
	 */
	public LoginResponse login(LoginRequest request);
	/**
	 * 用户退出
	 * @param request
	 * @return
	 */
	public BasicResponse exit(ExitRequest request);
	/**
	 * 根据用户的唯一标识来获取用户的基本信息
	 * @param request
	 * @return
	 */
	public HomePageResponse getPersonalHomePage(UserInfoRequest request);
	
	/**
	 * 修改用户的个人信息;
	 * @param request
	 * @return
	 */
	public PersonInfoEditResponse modifyPersonInfo(PersonInfoEditRequest request);
	/**
	 * 更改用户头像
	 * @param request
	 * @return
	 */
	public Head_urlUpdateResponse updateHead_url(Head_urlUpdateRequest request);
	/**
	 * 修改密码
	 * @param request
	 * @return
	 */
	public BasicResponse updatePassword(PasswordUpdateRequest request);
	
	/***以下是管理员的对用户进行的操作***/
	/**
	 * 得到所有用户
	 * @return
	 */
	public AllUserResponse getAllUser(UserInfoRequest request);
	/**
	 * 添加用户(涉及到user 、login 、authority db)
	 * @param request
	 * @return
	 */
	public AddUserResponse addUser(AddUserRequest request);
	/**
	 * 删除用户（涉及到user 、login 、authority db）
	 * @param request
	 * @return
	 */
	public DeleteUserResponse deleteUser(DeleteUserRequest request);
	
	/**
	 * 添加用户的详细介绍
	 * @param request
	 * @return
	 */
	public AddDetailIntroResponse addDetailIntro(AddDetailIntroRequest request);
	
	/**
	 * 判断是否登陆成功，成功即可返回isManager 的值
	 */
	public JudgeIsLoginResponse judgeIsLogin(JudgeIsLoginRequest request);
	/**
	 * 分类得到所有用户的年级 信息
	 * @return
	 */
	public GradeInfoResponse getGradeInfo();
	/**
	 * 根据年级来获取所有的班级信息
	 * @param request
	 * @return
	 */
	public ClassInfoResponse getClassInfo( ClassInfoRequest request);
	/**
	 * 根据班级信息来获取班级包括的所有学生
	 * @param request
	 * @return
	 */
	public GetUsersResponse getUserListByClass(GetUsersRequest request);
	
	/**
	 * 根据学号来获取用户的信息
	 * @param request
	 * @return
	 */
	public LoginResponse  GetPersonInfo(UserInfoRequest request);
	/**
	 * 增加管理员
	 * @param request
	 * @return
	 */
	public BasicResponse addManager(addManagerRequest request);
	
	
	
	
	
	
	
	
	
	

}
